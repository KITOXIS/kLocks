package net.starfal.klocks.Locking;

import net.kyori.adventure.text.Component;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Functions.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UnlockCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("unlock")){
            if(sender instanceof Player p){
                if(args.length == 0) {
                    if (Settings.getInstance().getBoolean("Locking.Unlocking")) {
                        Unlock.getInstance().unlock(p);
                    }else {
                        p.sendMessage(Color.format("&cUnlocking blocks is disabled!"));
                        if (Settings.getInstance().getBoolean("General.Action-Bars")){
                            Component message = Component.text(Color.format("&cUnlocking blocks is disabled!"));
                            p.sendActionBar(message);
                        }
                    }
                }else if(args.length >= 1){
                    p.sendMessage(Color.format("&cUsage: /unlock"));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component message = Component.text(Color.format("&cUsage: /unlock"));
                        p.sendActionBar(message);
                    }
                }
            }
        }
        return true;
    }
}
