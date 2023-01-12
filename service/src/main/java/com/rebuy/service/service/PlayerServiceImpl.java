package com.rebuy.service.service;

import com.rebuy.service.entity.Country;
import com.rebuy.service.entity.Player;
import com.rebuy.service.repository.PlayerRepository;
import com.rebuy.service.service.interfaces.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> getByName(String name) {
        return playerRepository.getByName(name);
    }

    @Override
    public void updatePlayer(Player newPlayer, Player currentPlayer) {
        String oldCode = currentPlayer.getCountry().getCode();
        String newCode = newPlayer.getCountry().getCode();
        if (!newCode.equals(oldCode)) {
            currentPlayer.setCountry(new Country(newCode));
            save(currentPlayer);
        }
    }

    @Override
    public Player save(Player player) {
        return playerRepository.save(player);
    }

}
