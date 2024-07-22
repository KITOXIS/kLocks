package net.starfal.klocks.Configuration;

import net.kyori.adventure.text.Component;
import net.starfal.klocks.Functions.Color;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

import static org.bukkit.Material.AIR;

public class kLocksCMD implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("klocks")){
            if (sender.hasPermission("klocks.admin")){
                if (args.length == 1){
                    if (args[0].equalsIgnoreCase("reload")) {
                        Settings.getInstance().reload();
                        String message = (String) Settings.getInstance().getLang("kLocks.Reloaded");
                        message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                        sender.sendMessage(Color.format(message));
                        if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                            Component bar = Color.format(message);
                            sender.sendActionBar(bar);
                        }
                    } else if (args[0].equalsIgnoreCase("forceunlock")) {
                        Player player = (Player) sender;
                        BlockState block = player.getTargetBlock(null, Settings.getInstance().getInt("Locking.Locking-Range")).getState();
                        if (block.getType().equals(AIR)) {
                            String message = (String) Settings.getInstance().getLang("General.You-Are-Not-Looking-At-Block");
                            message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                            sender.sendMessage(Color.format(message));
                            if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                Component bar = Color.format(message);
                                sender.sendActionBar(bar);
                            }
                        } else {
                            if (block instanceof Lockable) {
                                if (((Lockable) block).isLocked()) {
                                    Lockable lockable = (Lockable) block;
                                    String oldCode = lockable.getLock();
                                    lockable.setLock(null);
                                    block.update();
                                    String message = (String) Settings.getInstance().getLang("kLocks.Force-Unlock");
                                    String blockName = block.getType().toString();
                                    blockName = blockName.substring(0, 1).toUpperCase() + blockName.substring(1).toLowerCase();
                                    blockName = blockName.replace("_", " ");
                                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                    message = message.replace("%code%", oldCode);
                                    message = message.replace("%block%", blockName);
                                    sender.sendMessage(Color.format(message));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                        Component bar = Color.format(message);
                                        sender.sendActionBar(bar);
                                    }
                                } else {
                                    String message = (String) Settings.getInstance().getLang("kLocks.This-Block-Is-Not-Locked");
                                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                    sender.sendMessage(Color.format(message));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                        Component bar = Color.format(message);
                                        sender.sendActionBar(bar);
                                    }
                                }
                            } else {
                                String message = (String) Settings.getInstance().getLang("General.This-Block-Is-Not-Lockable");
                                message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                sender.sendMessage(Color.format(message));
                                if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                    Component bar = Color.format(message);
                                    sender.sendActionBar(bar);
                                }

                            }
                        }
                    } else if (args[0].equalsIgnoreCase("getcode")) {
                        Player player = (Player) sender;
                        BlockState block = player.getTargetBlock(null, Settings.getInstance().getInt("Locking.Locking-Range")).getState();
                        if (block.getType().equals(AIR)) {
                            String message = (String) Settings.getInstance().getLang("General.You-Are-Not-Looking-At-Block");
                            message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                            sender.sendMessage(Color.format(message));
                            if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                Component bar = Color.format(message);
                                sender.sendActionBar(bar);
                            }
                        } else {
                            if (block instanceof Lockable) {
                                if (((Lockable) block).isLocked()) {
                                    Lockable lockable = (Lockable) block;
                                    String code = lockable.getLock();
                                    String message = (String) Settings.getInstance().getLang("kLocks.Get-Code");
                                    String blockName = block.getType().toString();
                                    blockName = blockName.substring(0, 1).toUpperCase() + blockName.substring(1).toLowerCase();
                                    blockName = blockName.replace("_", " ");
                                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                    message = message.replace("%code%", code);
                                    message = message.replace("%block%", blockName);
                                    sender.sendMessage(Color.format(message));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                        Component bar = Color.format(message);
                                        sender.sendActionBar(bar);
                                    }
                                } else {
                                    String message = (String) Settings.getInstance().getLang("kLocks.This-Block-Is-Not-Locked");
                                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                    sender.sendMessage(Color.format(message));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                        Component bar = Color.format(message);
                                        sender.sendActionBar(bar);
                                    }
                                }
                            } else {
                                String message = (String) Settings.getInstance().getLang("General.This-Block-Is-Not-Lockable");
                                message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                                sender.sendMessage(Color.format(message));
                                if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                    Component bar = Color.format(message);
                                    sender.sendActionBar(bar);
                                }

                            }
                        }
                    }
                }else if (args.length == 0){
                    String message = (String) Settings.getInstance().getLang("kLocks.Usage");
                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                    sender.sendMessage(Color.format(message));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component bar = Color.format(message);
                        sender.sendActionBar(bar);
                    }
                }else{
                    String message = (String) Settings.getInstance().getLang("kLocks.Usage");
                    message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                    sender.sendMessage(Color.format(message));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component bar = Color.format(message);
                        sender.sendActionBar(bar);
                    }
                }
            }else {
                String message = (String) Settings.getInstance().getLang("General.No-Permission");
                message = message.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
                sender.sendMessage(Color.format(message));
                if (Settings.getInstance().getBoolean("General.Action-Bars")){
                    Component bar = Color.format(message);
                    sender.sendActionBar(bar);
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length == 1){
            return List.of("reload", "forceunlock", "getcode");
        }else{
            return null;
        }
    }
}
