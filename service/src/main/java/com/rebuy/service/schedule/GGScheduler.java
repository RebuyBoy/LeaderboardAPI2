package com.rebuy.service.schedule;

import com.rebuy.service.constants.Constants;
import com.rebuy.service.service.interfaces.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.rebuy.service.constants.Constants.GMT_MINUS_8;

@Service
@EnableScheduling
public class GGScheduler {
    public static final String EVERY_DAY_3_MINUTES_AFTER_MIDNIGHT_CRON = "0 3 0 * * *";
    private static final Logger LOG = LoggerFactory.getLogger(GGScheduler.class);
    private final ClientService clientService;

    public GGScheduler(ClientService clientService) {
        this.clientService = clientService;
    }

    @Scheduled(cron = EVERY_DAY_3_MINUTES_AFTER_MIDNIGHT_CRON, zone = Constants.GMT_MINUS_8)
    public void dailyPromotionData() {
        LocalDate prevDay = ZonedDateTime
                .now(ZoneId.of(GMT_MINUS_8))
                .minusDays(1)
                .toLocalDate();
        LOG.info("scheduler start collecting data for day {}", prevDay);
        clientService.getAndSaveResults(prevDay);
    }
    //TODO RETRY
}

//    Cron
// ┌───────────── second (0-59)
// │ ┌───────────── minute (0 - 59)
// │ │ ┌───────────── hour (0 - 23)
// │ │ │ ┌───────────── day of the month (1 - 31)
// │ │ │ │ ┌───────────── month (1 - 12) (or JAN-DEC)
// │ │ │ │ │ ┌───────────── day of the week (0 - 7)
// │ │ │ │ │ │          (or MON-SUN -- 0 or 7 is Sunday)
// │ │ │ │ │ │
// * * * * * *


