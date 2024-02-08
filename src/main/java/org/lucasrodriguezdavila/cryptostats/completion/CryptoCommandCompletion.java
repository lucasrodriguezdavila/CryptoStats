package org.lucasrodriguezdavila.cryptostats.completion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CryptoCommandCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        List<String> l = new ArrayList<>();
        if(sender instanceof Player){

        if(cmd.getName().equalsIgnoreCase("crypto") ){
            if (args.length >= 2) {
                String subCommand = args[0];
                if ((subCommand.equalsIgnoreCase("show") || subCommand.equalsIgnoreCase("hide"))) {
                    return l;
                }
                l.add("btc");
                l.add("ethereum");

                return l;
            }
            if (args.length >= 1) {
                l.add("show");
                l.add("hide");
                l.add("price");

                return l;
            }

            }
        }
        return l;
    }
}

