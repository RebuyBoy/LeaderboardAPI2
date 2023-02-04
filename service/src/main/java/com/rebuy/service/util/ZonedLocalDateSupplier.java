package com.rebuy.service.util;

import com.rebuy.service.constants.Constants;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class ZonedLocalDateSupplier {

    private ZonedLocalDateSupplier() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDate localDateNowGMTMinus8() {
        return ZonedDateTime
                .now(ZoneId.of(Constants.GMT_MINUS_8))
                .toLocalDate();
    }
}
