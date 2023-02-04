package com.rebuy.service.service;

import com.rebuy.service.entity.DateLB;
import com.rebuy.service.entity.GroupId;
import com.rebuy.service.repository.GroupIdRepository;
import com.rebuy.service.service.interfaces.DateService;
import com.rebuy.service.service.interfaces.GroupIdService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GGGroupIdService implements GroupIdService {

    private final GroupIdRepository groupIdRepository;
    private final DateService dateService;

    public GGGroupIdService(GroupIdRepository groupIdRepository,
                            DateService dateService) {
        this.groupIdRepository = groupIdRepository;
        this.dateService = dateService;
    }

    @Override
    public GroupId saveIfNotExists(GroupId groupId) {
        DateLB date = getDateLB(groupId.getDate());
        groupId.setDate(date);
        return getByGroupId(groupId)
                .orElseGet(() -> groupIdRepository.save(groupId));
    }

    private DateLB getDateLB(DateLB dateLB) {
        return dateService.saveIfNotExist(dateLB);
    }

    @Override
    public Optional<GroupId> getByGroupId(GroupId groupId) {
        return groupIdRepository.getByDateAndPromotionGroupId(
                groupId.getDate(),
                groupId.getPromotionGroupId());
    }

    public Optional<GroupId> getByDate(LocalDate date) {
        return groupIdRepository.getByDate(date);
    }

}
