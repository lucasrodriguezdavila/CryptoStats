package org.lucasrodriguezdavila.cryptostats;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.lucasrodriguezdavila.cryptostats.commands.CryptoCommand;
import org.lucasrodriguezdavila.cryptostats.completion.CryptoCommandCompletion;
import org.lucasrodriguezdavila.cryptostats.models.CoinGecko;
import org.lucasrodriguezdavila.cryptostats.models.CryptoBoard;
import org.lucasrodriguezdavila.cryptostats.models.PlayerInfo;

import java.util.HashMap;
import java.util.UUID;

public final class CryptoStats extends JavaPlugin {
    FileConfiguration config = getConfig();
    public static HashMap<String, PlayerInfo> playersInfo = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        // get config file
        config.addDefault("COIN_GECKO_API_KEY", "put your api key here");
        config.options().copyDefaults(true);
        saveConfig();
        String apiKey = config.getString("COIN_GECKO_API_KEY");
        if (apiKey == null) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            CoinGecko.COIN_GECKO_API_KEY = apiKey;
        }
        try {
            this.getCommand("crypto").setExecutor(new CryptoCommand());
            this.getCommand("crypto").setTabCompleter(new CryptoCommandCompletion());

            // every 60 ticks (3 seconds) run the updateScoreboard method in a separate thread
            getServer().getScheduler().scheduleAsyncRepeatingTask(this, () -> {
                if (getServer().getOnlinePlayers().isEmpty()) {
                    return;
                }
                if (playersInfo.isEmpty()) {
                    return;
                }
                CoinGecko coinGecko = new CoinGecko();
                coinGecko.getBitcoinAsUSD();
                coinGecko.getEthereumAsUSD();

                for (PlayerInfo playerInfo : playersInfo.values()) {
                    if (playerInfo.isScoreboardEnabled()) {
                        Player player = getServer().getPlayer(UUID.fromString(playerInfo.getUid()));
                        if (player == null) {
                            playersInfo.remove(playerInfo.getUid());
                            continue;
                        }

                        if (CryptoBoard.hasBoard(player)) {
                            CryptoBoard.updateBoard(player);
                        } else {
                            CryptoBoard.createBoard(player);
                        }
                    }
                }
            }, 0L, 1200L);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static PlayerInfo getPlayerInfo(String uid) {
        if (playersInfo.containsKey(uid)) {
            return playersInfo.get(uid);
        } else {
            PlayerInfo playerInfo = new PlayerInfo(uid);
            playersInfo.put(uid, playerInfo);
            return playerInfo;
        }
    }

    public static void setPlayerInfo(String uid, PlayerInfo playerInfo) {
        playersInfo.put(uid, playerInfo);
    }
}
