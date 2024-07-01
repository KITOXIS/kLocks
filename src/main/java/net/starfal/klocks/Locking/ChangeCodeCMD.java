package net.starfal.klocks.Locking;

import net.kyori.adventure.text.Component;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Functions.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChangeCodeCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if(cmd.getName().equalsIgnoreCase("changecode")){
            if(sender instanceof Player p){
                if(args.length == 0) {
                    if (Settings.getInstance().getBoolean("Locking.Changing-Code")){
                        ChangeCode.getInstance().changecode(p);
                    }else{
                        String msg = Settings.getInstance().getString("Change-Code.Changing-Is-Disabled");
                        msg = msg.replace("%prefix%", Settings.getInstance().getString("General.Prefix"));
                        Component message = Color.format(msg);
                        p.sendMessage(message);
                        if (Settings.getInstance().getBoolean("General.Action-Bars")){
                            p.sendActionBar(message);
                        }
                    }
                }else if(args.length >= 1){
                    String msg = Settings.getInstance().getString("Change-Code.Usage");
                    msg = msg.replace("%prefix%", Settings.getInstance().getString("General.Prefix"));
                    Component message = Color.format(msg);
                    p.sendMessage(message);
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        p.sendActionBar(message);
                    }
                }
            }
        }
        return true;
    }
}
