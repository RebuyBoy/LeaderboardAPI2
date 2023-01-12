package com.rebuy.service.service.interfaces;

import com.rebuy.service.entity.Player;

import java.util.Optional;

public interface PlayerService {

    Optional<Player> getByName(String name);

    void updatePlayer(Player newPlayer, Player currentPlayer);

    Player save(Player player);

}
