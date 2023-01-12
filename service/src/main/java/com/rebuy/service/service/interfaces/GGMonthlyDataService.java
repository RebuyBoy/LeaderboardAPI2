package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.client.gg.GroupsResponse;
import com.rebuy.service.entity.GameType;

public interface GGMonthlyDataService {

    GroupsResponse parseMonthlyData(GameType gameType);

    void deleteGroupResponseCache();

}
