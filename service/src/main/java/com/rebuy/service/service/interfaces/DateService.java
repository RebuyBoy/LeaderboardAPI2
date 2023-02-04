package com.rebuy.service.service.interfaces;

import com.rebuy.service.entity.DateLB;

import java.time.LocalDate;
import java.util.Optional;

public interface DateService {

    DateLB saveIfNotExist(DateLB date);

    Optional<DateLB> getByDate(LocalDate date);

    DateLB save(DateLB dateLB);

}
