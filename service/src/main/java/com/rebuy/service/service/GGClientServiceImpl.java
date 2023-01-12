package com.rebuy.service.service;

import com.rebuy.service.converters.ResultResponseConverter;
import com.rebuy.api.scope.dto.response.ResultResponse;
import com.rebuy.service.dto.client.gg.GGResultResponse;
import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.dto.client.gg.SetsResponse;
import com.rebuy.service.dto.client.gg.SubsetsResponse;
import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.Provider;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.exceptions.NoResultException;
import com.rebuy.service.service.interfaces.ClientService;
import com.rebuy.service.service.interfaces.GGMonthlyDataService;
import com.rebuy.service.service.interfaces.GGRequestService;
import com.rebuy.service.service.interfaces.ResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import static com.rebuy.service.constants.Constants.GAME_TYPES_PART_OF_URL;
import static com.rebuy.service.constants.Constants.GAME_TYPES_SUITABLE_STAKES;
import static com.rebuy.service.constants.Constants.GMT_MINUS_8;

@Service
public class GGClientServiceImpl implements ClientService {

    private static final String PROMO_URL_FORMAT = "https://pml.good-game-network.com/lapi/leaderboard/%s/?status=PENDING&status=OPTED_IN&status=COMPLETED&status=EXPIRED&limit=%s&hasSummary=true&hasSummaryPaidPrizes=true&hasSummaryPrizeItem=true";
    private static final Logger LOG = LoggerFactory.getLogger(GGClientServiceImpl.class);
    private final GGMonthlyDataService monthlyDataService;
    private final GGRequestService ggRequestService;
    private final ResultResponseConverter resultConverter;
    private final ResultService resultService;


    public GGClientServiceImpl(GGMonthlyDataService monthlyDataService,
                               GGRequestService ggRequestService,
                               ResultResponseConverter converter,
                               ResultService resultService) {

        this.monthlyDataService = monthlyDataService;
        this.ggRequestService = ggRequestService;
        this.resultConverter = converter;
        this.resultService = resultService;
    }

    @Override
    public void runDailyDataFlow() {
        List.of(GameType.values())
                .forEach(gameType -> runDailyDataFlow(daysBeforeNow(1), gameType));
    }

    @Override
    public void runDailyDataFlow(List<LocalDate> dates, GameType gameType) {
        for (LocalDate date : dates) {
            runDailyDataFlow(date, gameType);
        }
    }

    @Override
    public void runDailyDataFlow(LocalDate date, GameType gameType) {
        GroupsResponse groupsResponse = verifyGroupResponse(date, gameType);
        //TODO check date to be past one day from current
        try {
            LOG.info("Parsing data is started by date: {} and gameType: {}", date, gameType);
            SetsResponse sets = findSetByDate(groupsResponse, date);
            for (SubsetsResponse subset : sets.getSubsets()) {
                String stake = subset.getStake();
                stake = stake.substring(0, stake.indexOf('|'));
                //TODO fix changed format of stake $10 -> $10.00|1억
                if (GAME_TYPES_SUITABLE_STAKES.get(gameType).contains(stake)) {
                    handleData(date, subset, stake, gameType);
                }
            }
            LOG.info("Parsing data is finished");
        } catch (Exception e) {
            LOG.error("Parse data failed ", e);
        }
    }

    public List<ResultResponse> runDailyDataFlow(Stake stake, GameType gameType) {
        LOG.info("Parsing data by stake {} is started", stake);
        LocalDate currentDate = daysBeforeNow(0);
        GroupsResponse groupsResponse = verifyGroupResponse(currentDate, gameType);
        SetsResponse sets = findSetByDate(groupsResponse, currentDate);
        int promotionId = -1;
        for (SubsetsResponse subset : sets.getSubsets()) {
            String subsetStake = subset.getStake();
            if (subsetStake.startsWith(stake.getDescription())) {
                promotionId = subset.getPromotionId();
                break;
            }
        }
        return getGGResultDTOS(gameType, promotionId, stake.getDescription())
                .stream()
                .map(result -> new ResultResponse(result.getName(), result.getPoints()))
                .toList();

    }


    private GroupsResponse verifyGroupResponse(LocalDate date, GameType gameType) {
        GroupsResponse groupsResponse = monthlyDataService.parseMonthlyData(gameType);
        //TODO fix cash is null
        if (groupsResponse == null || isOutdatedMonth(groupsResponse, date)) {
            monthlyDataService.deleteGroupResponseCache();
            groupsResponse = monthlyDataService.parseMonthlyData(gameType);
        }
        return groupsResponse;
    }

    private boolean isOutdatedMonth(GroupsResponse groupsResponse, LocalDate date) {
        return groupsResponse.getStartedAt().getMonthValue() != (date.getMonthValue());
    }


    private void handleData(LocalDate date, SubsetsResponse subset, String stake, GameType gameType) {
        List<GGResultResponse> resultDTOS = getGGResultDTOS(gameType, subset.getPromotionId(), stake);
        saveResults(date, stake, resultDTOS, gameType);
    }

    private void saveResults(LocalDate date, String stake, List<GGResultResponse> resultDTOS, GameType gameType) {
        resultDTOS.stream()
                .map(resultDTO -> resultConverter.convert(resultDTO, date, stake, gameType, Provider.GGNETWORK))
                .forEach(resultService::saveIfNotExists);
    }

    private List<GGResultResponse> getGGResultDTOS(GameType gameType, int promotionId, String stake) {
        String url = generatePromotionUrl(getLimitValueByStake(gameType, stake), promotionId);
        return ggRequestService.promotionIdRequest(url);
    }

    private SetsResponse findSetByDate(GroupsResponse groupsResponse, LocalDate date) {
        return groupsResponse.getSets().stream()
                .filter(set -> set.getDate().equals(date))
                .findFirst()
                .orElseThrow(() -> new NoResultException("Promotions not found by date: " + date));
    }

    private String generatePromotionUrl(String stake, int promotionId) {
        return PROMO_URL_FORMAT.formatted(promotionId, stake);
    }

    private String getLimitValueByStake(GameType gameType, String stake) {
        Map<String, String> stakesToPartUrl = GAME_TYPES_PART_OF_URL.get(gameType);
        if (stakesToPartUrl.containsKey(stake)) {
            return stakesToPartUrl.get(stake);
        }
        throw new IllegalArgumentException("wrong stake: " + stake);
    }

    private LocalDate daysBeforeNow(int minusDays) {
        return ZonedDateTime
                .now(ZoneId.of(GMT_MINUS_8))
                .minusDays(minusDays)
                .toLocalDate();
    }

}
