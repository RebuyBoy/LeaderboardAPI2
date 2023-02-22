package com.rebuy.service.repository;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.dto.api.PlaceAndPoints;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("SELECT r FROM Result r")
    @Cacheable("AllResults")
    List<Result> getAll();

    @Query("SELECT d.date AS date, COUNT(r) AS count, SUM(r.prize) AS prizes " +
            "FROM Result r " +
            "INNER JOIN DateLB d ON r.dateLB.id = d.id " +
            "GROUP BY d.date " +
            "ORDER BY d.date DESC")
    List<DateAndCount> getGroupedByDateCountAndPrizeSum();

    @Transactional
    @Modifying
    @Query("DELETE FROM Result r " +
            "       WHERE r.dateLB.id = (SELECT d.id FROM DateLB d " +
            "                                  WHERE d.date = :date)")
    int deleteByDate(@Param("date") LocalDate date);

    @Query("SELECT r.rank AS rank, AVG(r.points) AS points " +
            "FROM Result r " +
            "JOIN r.dateLB d ON r.dateLB.id = d.id " +
            "WHERE r.stake = :stake " +
            "AND (cast(:start as timestamp) IS NULL OR d.date >= :start) " +
            "AND (cast(:end as timestamp) IS NULL OR d.date <= :end) " +
            "GROUP BY r.rank " +
            "ORDER BY r.rank ")
    List<PlaceAndPoints> getAveragePoints(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("stake") Stake stake,
            Pageable pageable
    );

}
