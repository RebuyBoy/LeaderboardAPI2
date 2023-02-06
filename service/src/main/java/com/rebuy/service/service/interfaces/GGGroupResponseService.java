package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.entity.GroupId;

import java.time.LocalDate;

public interface GGGroupResponseService {

    GroupsResponse getGroupResponse(LocalDate firstDayOfMonth);

    GroupId saveGroupId(LocalDate date);

    void clearCache();

}
