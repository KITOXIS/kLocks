package net.starfal.klocks.Locking;

import net.kyori.adventure.text.Component;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Functions.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LockCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("lock")){
            if(sender instanceof Player p){
                if(args.length == 0) {
                    Lock.getInstance().lock(p);
                }else if(args.length >= 1){
                    p.sendMessage(Color.format("&cUsage: /lock"));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component message = Component.text(Color.format("&cUsage: /lock"));
                        p.sendActionBar(message);
                    }
                }
            }
        }
        return true;
    }
}
