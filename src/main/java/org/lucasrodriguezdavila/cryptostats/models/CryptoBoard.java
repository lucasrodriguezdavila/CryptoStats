package org.lucasrodriguezdavila.cryptostats.models;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class CryptoBoard {
    public static void createBoard(Player player) {
        // Create a scoreboard for the player
        try {
            ScoreboardManager manager = player.getServer().getScoreboardManager();
            Scoreboard board = manager.getNewScoreboard();
            Objective objective = board.registerNewObjective("CryptoStats", "dummy", "CryptoStats");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score score = objective.getScore("Bitcoin");
            score.setScore(CoinGecko.bitcoin);

            score = objective.getScore("Ethereum");
            score.setScore(new Integer(String.valueOf(CoinGecko.ethereum).split("\\.")[0]));

            player.setScoreboard(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateBoard(Player player) {
        // Update the scoreboard for the player
        try {
            Scoreboard board = player.getScoreboard();
            Objective objective = board.getObjective("CryptoStats");
            Score score = objective.getScore("Bitcoin");
            score.setScore(CoinGecko.bitcoin);
            score = objective.getScore("Ethereum");
            score.setScore(new Integer(String.valueOf(CoinGecko.ethereum).split("\\.")[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeBoard(Player player) {
        // Remove the scoreboard for the player
        try {
            player.setScoreboard(player.getServer().getScoreboardManager().getNewScoreboard());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasBoard(Player player) {
        // Check if the player has a scoreboard
        try {
            return player.getScoreboard().getObjective("CryptoStats") != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
