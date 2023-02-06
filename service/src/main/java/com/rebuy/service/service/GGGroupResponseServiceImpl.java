package com.rebuy.service.service;

import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.entity.DateLB;
import com.rebuy.service.entity.GroupId;
import com.rebuy.service.exceptions.NoResultException;
import com.rebuy.service.service.interfaces.GGGroupResponseService;
import com.rebuy.service.service.interfaces.GGRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GGGroupResponseServiceImpl implements GGGroupResponseService {

    private static final Logger LOG = LoggerFactory.getLogger(GGGroupResponseServiceImpl.class);
    private static final String GROUP_ID_REGEX = "groupId=(\\d+)";
    private static final String GGN_MAIN_SHORT_DECK_URL = "https://play.pokerok138.com/promotions/promo-short-deck";
    private static final String GGN_GROUP_ID_REQUEST_FORMAT = "https://pml.good-game-network.com/lapi/leaderboard/groups/%s";

    private final GGRequestService requestService;
    private final GGGroupIdService groupIdService;

    public GGGroupResponseServiceImpl(GGRequestService requestService,
                                      GGGroupIdService groupIdService) {
        this.requestService = requestService;
        this.groupIdService = groupIdService;
    }

    @Override
    @Cacheable("groupResponse")
    public GroupsResponse getGroupResponse(LocalDate date) {
        try {
            GroupId groupIdValue = saveGroupId(date);
            LOG.debug("found group id {}", groupIdValue);
            String urlWithGroupId = generateGroupIdUrl(groupIdValue.getPromotionGroupId());
            LOG.debug("request group id: {}", urlWithGroupId);
            return requestService.groupIdRequest(urlWithGroupId);
        } catch (Exception e) {
            LOG.error("Parsing monthly data failed {}", e.getMessage());
        }
        throw new NoResultException("Group response request failed");
    }

    @Override
    @CacheEvict(value = "groupResponse", allEntries = true)
    public void clearCache() {
        LOG.info("emptying groupResponse cache");
    }

    @Override
    public GroupId saveGroupId(LocalDate date) {
        LOG.info("Getting group id for date {}", date);
        Optional<GroupId> groupIdOptional = groupIdService.getByDate(date);
        if (groupIdOptional.isPresent()) {
            return groupIdOptional.get();
        }
        if (isCurrentMonth(date)) {
            String groupIdValue = findGroupIdFromResponse(requestService.getHTMLBody(GGN_MAIN_SHORT_DECK_URL));
            return groupIdService.saveIfNotExists(
                    new GroupId.Builder()
                            .promotionGroupId(groupIdValue)
                            .date(new DateLB(date))
                            .build());
        }
        throw new NoResultException(String.format("Cant get group id for date: %s", date));
    }

    private boolean isCurrentMonth(LocalDate date) {
        LocalDate current = LocalDate.now();
        return current.getMonthValue() == date.getMonthValue()
                && current.getYear() == date.getYear();
    }

    private String findGroupIdFromResponse(String response) {
        Matcher matcher = Pattern.compile(GROUP_ID_REGEX).matcher(response);
        if (matcher.find()) {
            return matcher.group(1);
        }
        throw new NoResultException("Group id not found");
    }

    private String generateGroupIdUrl(String groupId) {
        return GGN_GROUP_ID_REQUEST_FORMAT.formatted(groupId);
    }

}
