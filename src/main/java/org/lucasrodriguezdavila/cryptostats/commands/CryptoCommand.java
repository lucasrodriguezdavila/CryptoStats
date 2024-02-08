package org.lucasrodriguezdavila.cryptostats.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.lucasrodriguezdavila.cryptostats.CryptoStats;
import org.lucasrodriguezdavila.cryptostats.models.CoinGecko;
import org.lucasrodriguezdavila.cryptostats.models.CryptoBoard;
import org.lucasrodriguezdavila.cryptostats.models.PlayerInfo;

public class CryptoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            BukkitScheduler scheduler = player.getServer().getScheduler();

            if (strings.length > 0) {
                if (strings[0].equalsIgnoreCase("show")) {
                    PlayerInfo playerInfo = CryptoStats.getPlayerInfo(player.getUniqueId().toString());
                    playerInfo.setScoreboardEnabled(true);
                    player.sendMessage("Scoreboard enabled");
                    CryptoBoard.createBoard(player);
                    return true;
                }
                if (strings[0].equalsIgnoreCase("hide")) {
                    PlayerInfo playerInfo = CryptoStats.getPlayerInfo(player.getUniqueId().toString());
                    playerInfo.setScoreboardEnabled(false);
                    player.sendMessage("Scoreboard disabled");
                    CryptoBoard.removeBoard(player);
                    return true;
                }
                if (strings[0].equalsIgnoreCase("price")) {
                    String coin = strings[1];
                    if (!(coin.equals("btc") || coin.equals("ethereum"))) {
                        player.sendMessage("Invalid coin");
                        return true;
                    }
                    // run getBitcoinAsUSD() in a separate thread
                    scheduler.runTaskAsynchronously(player.getServer().getPluginManager().getPlugin("CryptoStats"), () -> {
                        CoinGecko coinGecko = new CoinGecko();
                        String price = coin.equals("btc") ? coinGecko.getBitcoinAsUSD() : coinGecko.getEthereumAsUSD();
                        player.sendMessage("The price of " + coin + " is " + price);
                    });
                    return true;

                }
            }
            player.sendMessage("Invalid command");
            return true;
        }
        return false;
    }
}
