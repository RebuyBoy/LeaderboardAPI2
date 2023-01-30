package com.rebuy.service.dto.api;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DateAndCount {
    LocalDate getDate();

    Long getCount();

    BigDecimal getPrizes();
}
