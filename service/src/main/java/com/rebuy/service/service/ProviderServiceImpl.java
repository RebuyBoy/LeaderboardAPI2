package com.rebuy.service.service;

import com.rebuy.service.entity.Stake;
import com.rebuy.service.exceptions.ProviderNotFoundException;
import com.rebuy.service.service.interfaces.ProviderService;
import com.rebuy.service.service.interfaces.ResultService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

//@Service
//public class ProviderServiceImpl implements ProviderService {
//
//    private final ResultService resultService;
//
//    public ProviderServiceImpl(ResultService resultService) {
//        this.resultService = resultService;
//    }
//
//    @Override
//    public List<ProviderResponse> getProviders() {
//        return resultService.getAllProviders()
//                .stream()
//                .map(provider -> new ProviderResponse(provider.name(), provider.getDescription(), provider.getCurrency()))
//                .toList();
//    }
//
//    @Override
//    public ProviderDataResponse getProviderData(String providerString) {
//        Provider provider = getProvider(providerString);
//        LocalDate lastUpdate = resultService.getLastUpdateByProvider(provider);
//        List<ProviderData> providersData = getProviderData(provider);
//        return new ProviderDataResponse(lastUpdate, providersData);
//    }
//
//    private List<ProviderData> getProviderData(Provider provider) {
//        return resultService.getGameTypesDataByProvider(provider)
//                .stream()
//                .map(gameTypeData -> new ProviderData(gameTypeData, getStakeResponses(provider, gameTypeData)))
//                .toList();
//    }
//
//    private List<StakeResponse> getStakeResponses(Provider provider, GameType gameTypeDatum) {
//        return resultService.getStakesByProviderAndGameType(provider, gameTypeDatum)
//                .stream()
//                .sorted(Comparator.comparing(Stake::getStakeEquivalent).reversed())
//                .map(stake -> new StakeResponse(stake.getStakeEquivalent().toString(),stake.getName(),stake.getDescription()))
//                .toList();
//    }
//
//    private Provider getProvider(String provider) {
//        return Arrays.stream(Provider.values())
//                .filter(value -> value.name().equals(provider))
//                .findFirst()
//                .orElseThrow(() -> new ProviderNotFoundException("Provider not found by name " + provider));
//    }

//}
