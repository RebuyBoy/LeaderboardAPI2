package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.client.gg.GroupsResponse;

import java.time.LocalDate;

public interface GGMonthlyDataService {

    GroupsResponse getGroupResponse(LocalDate firstDayOfMonth);

    void deleteGroupResponseCache();

}
