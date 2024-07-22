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
                    Lock.getInstance().lock(p, null);
                }else if (args.length == 1) {
                    Lock.getInstance().lock(p, args[0]);
                }else {
                    String message = (String) Settings.getInstance().getLang("Lock.Usage");
                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                    Component editedMessage = Color.format(message);
                    p.sendMessage(editedMessage);
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        p.sendActionBar(editedMessage);
                    }
                }
            }
        }
        return true;
    }
}
