package com.rebuy.service.service;

import com.rebuy.service.entity.Country;
import com.rebuy.service.repository.CountryRepository;
import com.rebuy.service.service.interfaces.CountryService;
import org.springframework.stereotype.Service;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getByCode(String code) {
        return countryRepository.getByCode(code);
    }

}
