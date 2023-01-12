package com.rebuy.service.service.interfaces;

import com.rebuy.service.dto.api.response.ProviderDataResponse;
import com.rebuy.service.dto.api.response.ProviderResponse;

import java.util.List;

public interface ProviderService {

    List<ProviderResponse> getProviders();

    ProviderDataResponse getProviderData(String provider);

}
