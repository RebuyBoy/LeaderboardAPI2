package com.rebuy.service.schedule;

import com.rebuy.service.constants.Constants;
import com.rebuy.service.entity.GroupId;
import com.rebuy.service.service.interfaces.ClientService;
import com.rebuy.service.service.interfaces.GGMonthlyDataService;
import com.rebuy.service.util.ZonedLocalDateSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.rebuy.service.constants.Constants.GMT_MINUS_8;

@Service
@EnableScheduling
public class GGScheduler {
    public static final String START_OF_NEW_DAY = "0 30 0 * * *";
    public static final String END_OF_DAY = "0 30 23 * * *";
    private static final Logger LOG = LoggerFactory.getLogger(GGScheduler.class);
    private final ClientService clientService;
    private final GGMonthlyDataService ggMonthlyDataService;

    public GGScheduler(ClientService clientService,
                       GGMonthlyDataService ggMonthlyDataService) {
        this.clientService = clientService;
        this.ggMonthlyDataService = ggMonthlyDataService;
    }

    @Scheduled(cron = START_OF_NEW_DAY, zone = Constants.GMT_MINUS_8)
    public void dailyPromotionData() {
        LocalDate prevDay = ZonedLocalDateSupplier.localDateNowGMTMinus8()
                .minusDays(1);
        LOG.info("scheduler start collecting data for day {}", prevDay);
        clientService.getAndSaveResults(prevDay);
    }

    @Scheduled(cron = END_OF_DAY, zone = GMT_MINUS_8)
    public void saveGroupId() {
        GroupId groupId = ggMonthlyDataService.saveGroupId(ZonedLocalDateSupplier.localDateNowGMTMinus8());
        ggMonthlyDataService.deleteGroupResponseCache();
        LOG.info("scheduler saved group id {} for date {}", groupId.getPromotionGroupId(), groupId.getDate());
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
