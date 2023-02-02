package com.rebuy.service.repository;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("SELECT r " +
            "FROM Result r " +
            "JOIN r.date d " +
            "WHERE (:stake IS NULL OR r.stake = :stake)" +
            "AND (cast(:start as timestamp) IS NULL OR d.date >= :start) " +
            "AND (cast(:end as timestamp) IS NULL OR d.date <= :end)")
    List<Result> getResults(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("stake") Stake stake
    );

    @Query("SELECT d.date AS date, COUNT(r) AS count, SUM(r.prize) AS prizes " +
            "FROM Result r " +
            "INNER JOIN DateLB d ON r.date.id = d.id " +
            "GROUP BY d.date " +
            "ORDER BY d.date DESC")
    List<DateAndCount> getGroupedByDateCountAndPrizeSum();

    @Modifying
    @Query("DELETE FROM Result r " +
            "       WHERE r.date.id = (SELECT d.id FROM DateLB d " +
            "                                  WHERE d.date = :date)")
    int deleteByDate(@Param("date") LocalDate date);

}

