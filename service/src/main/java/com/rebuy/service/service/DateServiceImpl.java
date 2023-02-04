package com.rebuy.service.service;

import com.rebuy.service.entity.DateLB;
import com.rebuy.service.repository.DateRepository;
import com.rebuy.service.service.interfaces.DateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DateServiceImpl implements DateService {

    private final DateRepository dateRepository;

    public DateServiceImpl(DateRepository dateRepository) {
        this.dateRepository = dateRepository;
    }

    @Override
    public DateLB saveIfNotExist(DateLB dateLB) {
        Optional<DateLB> date = getByDate(dateLB.getDate());
        return date.orElseGet(() -> save(dateLB));
    }

    @Override
    public Optional<DateLB> getByDate(LocalDate date) {
        return dateRepository.getByDate(date);

    }

    @Override
    public DateLB save(DateLB dateLB) {
        return dateRepository.save(dateLB);
    }

}
