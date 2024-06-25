package net.starfal.klocks.Locking;

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
                    ChangeCode.getInstance().changecode(p);
                }else if(args.length >= 1){
                    p.sendMessage(Color.format("&cUsage: /changecode"));
                }
            }
        }
        return true;
    }
}
