package com.rebuy.service.repository;

import com.rebuy.service.entity.GameType;
import com.rebuy.service.entity.GroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GroupIdRepository extends JpaRepository<GroupId,Integer> {

    Optional<GroupId> getByDateAndPromotionGroupIdAndGameType(LocalDate date, String groupId, GameType gameType);

}
