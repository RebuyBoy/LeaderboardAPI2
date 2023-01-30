package com.rebuy.service.service;

import com.rebuy.service.entity.GroupId;
import com.rebuy.service.repository.GroupIdRepository;
import com.rebuy.service.service.interfaces.GroupIdService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GGGroupIdService implements GroupIdService {

    private final GroupIdRepository groupIdRepository;

    public GGGroupIdService(GroupIdRepository groupIdRepository) {
        this.groupIdRepository = groupIdRepository;
    }

    @Override
    public GroupId saveIfNotExists(GroupId groupId) {
        Optional<GroupId> exists = getByGroupId(groupId);
        return exists.isEmpty()
                ? groupIdRepository.save(groupId)
                : exists.get();
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
