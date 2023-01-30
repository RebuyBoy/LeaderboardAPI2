package com.rebuy.service.repository;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {

    @Query("SELECT r FROM Result r " +
            "JOIN r.date d " +
            "WHERE (:stake IS NULL OR r.stake = :stake)" +
            "AND (cast(:start as timestamp) IS NULL OR d.date >= :start) " +
            "AND (cast(:end as timestamp) IS NULL OR d.date <= :end)")
    List<Result> getResults(
            @Param("start") LocalDate start,
            @Param("end") LocalDate end,
            @Param("stake") Stake stake
    );
}

