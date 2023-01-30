package com.rebuy.service.repository;

import com.rebuy.service.entity.GroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GroupIdRepository extends JpaRepository<GroupId, Integer> {

    Optional<GroupId> getByDateAndPromotionGroupId(LocalDate date, String groupId);

    Optional<GroupId> getByDate(LocalDate date);

}
