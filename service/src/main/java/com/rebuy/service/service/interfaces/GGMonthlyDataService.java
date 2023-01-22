package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.client.gg.GroupsResponse;

public interface GGMonthlyDataService {

    GroupsResponse getGroupResponse();

    void deleteGroupResponseCache();

}
