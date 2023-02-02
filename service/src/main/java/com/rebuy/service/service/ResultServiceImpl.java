package com.rebuy.service.service;

import com.rebuy.service.dto.api.DateAndCount;
import com.rebuy.service.entity.DateLB;
import com.rebuy.service.entity.Player;
import com.rebuy.service.entity.Result;
import com.rebuy.service.entity.Stake;
import com.rebuy.service.repository.ResultRepository;
import com.rebuy.service.service.interfaces.DateService;
import com.rebuy.service.service.interfaces.PlayerService;
import com.rebuy.service.service.interfaces.ResultService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final PlayerService playerService;
    private final DateService dateService;

    public ResultServiceImpl(ResultRepository resultRepository, PlayerService playerService, DateService dateService) {
        this.resultRepository = resultRepository;
        this.playerService = playerService;
        this.dateService = dateService;
    }

    @Override
    public List<Result> get(LocalDate from, LocalDate to, Stake stake) {
        return resultRepository.getResults(from, to, stake);
    }

    @Override
    public Result saveIfNotExists(Result result) {
        Player player = getPlayer(result.getPlayer());
        DateLB date = getDateLB(result);

        result.setPlayer(player);
        result.setDate(date);

        return saveResult(result);
    }

    @Override
    public void deleteByDate(LocalDate date) {
        resultRepository.deleteByDate(date);
    }

    @Override
    public List<DateAndCount> getResultNumber() {
        return resultRepository.getGroupedByDateCountAndPrizeSum();
    }

    private Result saveResult(Result result) {
        ExampleMatcher ignoringIdMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id");
        Example<Result> example = Example.of(result, ignoringIdMatcher);

        if (!resultRepository.exists(example)) {
            return resultRepository.save(result);
        }

        return result;
    }

    private DateLB getDateLB(Result result) {
        return dateService.createIfNotExist(result.getDate());
    }

    private Player getPlayer(Player newPlayer) {
        Optional<Player> optionalPlayer = playerService.getByName(newPlayer.getName());

        if (optionalPlayer.isPresent()) {
            Player currentPlayer = optionalPlayer.get();
            playerService.updatePlayer(newPlayer, currentPlayer);
            return currentPlayer;
        } else {
            return playerService.save(newPlayer);
        }
    }

}
