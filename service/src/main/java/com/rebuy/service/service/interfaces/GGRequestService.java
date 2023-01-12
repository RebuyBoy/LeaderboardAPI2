package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.client.gg.GGResultResponse;
import com.rebuy.service.dto.client.gg.GroupsResponse;

import java.util.List;

public interface GGRequestService {

    String getHTMLBody(String url);

    List<GGResultResponse> promotionIdRequest(String url);

    GroupsResponse groupIdRequest(String promoUrl);

}
