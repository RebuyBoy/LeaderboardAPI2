package com.rebuy.service.dto.api.response;

import com.rebuy.service.dto.api.AggregatedResult;

import java.util.List;

public class ResultResponse {

    private ProviderResponse provider;
    private List<AggregatedResult> aggregatedResults;


    public ResultResponse(ProviderResponse provider, List<AggregatedResult> aggregatedResults) {
        this.provider = provider;
        this.aggregatedResults = aggregatedResults;
    }

    public ProviderResponse getProvider() {
        return provider;
    }

    public void setProvider(ProviderResponse provider) {
        this.provider = provider;
    }

    public List<AggregatedResult> getAggregatedResults() {
        return aggregatedResults;
    }

    public void setAggregatedResults(List<AggregatedResult> aggregatedResults) {
        this.aggregatedResults = aggregatedResults;
    }

}
