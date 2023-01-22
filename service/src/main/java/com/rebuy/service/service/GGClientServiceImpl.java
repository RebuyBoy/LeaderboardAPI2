package com.rebuy.service.service;

import com.rebuy.service.converters.ResultResponseConverter;
import com.rebuy.service.converters.StakeConverter;
import com.rebuy.service.dto.api.response.ResultResponse;
import com.rebuy.service.dto.client.gg.GGResultResponse;
import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.dto.client.gg.SetsResponse;
import com.rebuy.service.dto.client.gg.SubsetsResponse;
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
import java.util.ArrayList;
import java.util.List;

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

    //TODO add another method remove boolean save to db
    @Override
    public List<ResultResponse> parseResults(LocalDate date, Stake stake, boolean saveToDB) {
        LOG.info("Parsing data stake: {} date: {} is started", stake, date);
        date = date.minusDays(1);
        GroupsResponse groupsResponse = verifyGroupResponse(date);
        SetsResponse sets = findSetByDate(groupsResponse, date);
        List<GGResultResponse> ggResultDTOS = new ArrayList<>();
        for (SubsetsResponse subset : sets.getSubsets()) {
            String subsetStake = subset.getStake();
            if (subsetStake.startsWith(stake.getDescription())) {
                int promotionId = subset.getPromotionId();
                ggResultDTOS = getGGResultDTOS(promotionId, stake.getDescription());
                for (GGResultResponse ggResultDTO : ggResultDTOS) {
                    System.out.println(ggResultDTO);
                }
                break;
            }
        }
        List<ResultResponse> results = new ArrayList<>();
        if (saveToDB) {
            saveResults(date, stake, ggResultDTOS);
        } else {
            results = ggResultDTOS
                    .stream()
                    .map(result -> new ResultResponse.Builder()
                            .name(result.getName())
                            .points(result.getPoints())
                            .rank(result.getRank())
                            .prize(result.getPrize())
                            .build())
                    .toList();
        }
        return results;
    }

    private GroupsResponse verifyGroupResponse(LocalDate date) {
        GroupsResponse groupsResponse = monthlyDataService.getGroupResponse();
        //TODO fix cash is null
        if (groupsResponse == null || isOutdatedMonth(groupsResponse, date)) {
            monthlyDataService.deleteGroupResponseCache();
            groupsResponse = monthlyDataService.getGroupResponse();
        }
        return groupsResponse;
    }

    private boolean isOutdatedMonth(GroupsResponse groupsResponse, LocalDate date) {
        return groupsResponse.getStartedAt().getMonthValue() != (date.getMonthValue());
    }

    private void saveResults(LocalDate date, Stake stake, List<GGResultResponse> resultDTOS) {
        resultDTOS.stream()
                .map(resultDTO -> resultConverter.convert(resultDTO, date, stake))
                .forEach(resultService::saveIfNotExists);
    }

    private List<GGResultResponse> getGGResultDTOS(int promotionId, String stake) {
        String url = generatePromotionUrl(StakeConverter.toUrlPart(stake), promotionId);
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

}
