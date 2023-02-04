package com.rebuy.service.repository;

import com.rebuy.service.entity.DateLB;
import com.rebuy.service.entity.GroupId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GroupIdRepository extends JpaRepository<GroupId, Integer> {

    Optional<GroupId> getByDateAndPromotionGroupId(DateLB dateLB, String groupId);

    @Query("SELECT g " +
            "FROM GroupId g " +
            "INNER JOIN DateLB d ON g.date.id = d.id " +
            "WHERE d.date = :date")
    Optional<GroupId> getByDate(@Param("date") LocalDate date);

}
