package net.starfal.klocks.Locking;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.KeybindComponent;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Functions.Color;
import net.starfal.klocks.kLocks;
import org.bukkit.Material;
import org.bukkit.Nameable;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;

import static org.bukkit.Material.AIR;

public class ChangeCode implements Listener {
    private static ChangeCode instance;
    public static ChangeCode getInstance() {
        if(instance == null) {
            instance = new ChangeCode();
        }
        return instance;
    }
    private HashMap<Player, String> code = new HashMap<>();
    private ArrayList<Player> waitingForInput = new ArrayList<>();
    private ArrayList<Player> waitingForInput2 = new ArrayList<>();
    public void changecode(Player p, String preCoded1, String preCoded2){
        var conf = Settings.getInstance();
        String prefix = (String) conf.getLang("General.Prefix");
        BlockState block = p.getTargetBlock(null, Settings.getInstance().getInt("Locking.Locking-Range")).getState();
        if (block.getType() == null || block.getType().equals(AIR)) {
            String msg = (String) conf.getLang("General.You-Are-Not-Looking-At-Block");
            msg = msg.replace("%prefix%", prefix);
            Component editedMessage = Color.format(msg);
            p.sendMessage(editedMessage);
            if (Settings.getInstance().getBoolean("General.Action-Bars")){
                p.sendActionBar(editedMessage);
            }
        } else {
            if (block instanceof Lockable) {
                Lockable lockable = (Lockable) block;
                if (!lockable.isLocked()) {
                    String msg = (String) conf.getLang("Change-Code.This-Block-Is-Not-Locked");
                    msg = msg.replace("%prefix%", prefix);
                    Component editedMessage = Color.format(msg);
                    p.sendMessage(editedMessage);
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        p.sendActionBar(editedMessage);
                    }
                } else {
                    if (waitingForInput.contains(p)){
                        String msg = (String) conf.getLang("General.Already-Waiting-For-Input");
                        msg = msg.replace("%prefix%", prefix);
                        Component editedMessage = Color.format(msg);
                        p.sendMessage(editedMessage);
                        if (Settings.getInstance().getBoolean("General.Action-Bars")){
                            p.sendActionBar(editedMessage);
                        }
                        return;
                    }
                    if (preCoded1 == null && preCoded2 == null) {
                        waitingForInput.add(p);
                        String msg = (String) conf.getLang("Change-Code.Enter-The-Old-Code");
                        msg = msg.replace("%prefix%", prefix);
                        Component editedMessage = Color.format(msg);
                        p.sendMessage(editedMessage);
                        if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                            p.sendActionBar(editedMessage);
                        }
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (code.containsKey(p)) {
                                    String pass = code.get(p);
                                    String truePass = ((Lockable) block).getLock();
                                    if (!pass.equals(truePass)) {
                                        String msg = (String) conf.getLang("Change-Code.Incorrect-Code");
                                        msg = msg.replace("%prefix%", prefix);
                                        Component editedMessage = Color.format(msg);
                                        p.sendMessage(editedMessage);
                                        if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                            p.sendActionBar(editedMessage);
                                        }
                                        code.remove(p);
                                        this.cancel();
                                    } else {
                                        block.update();
                                        String msg = (String) conf.getLang("Change-Code.Correct-Code");
                                        msg = msg.replace("%prefix%", prefix);
                                        Component editedMessage = Color.format(msg);
                                        p.sendMessage(editedMessage);
                                        if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                            p.sendActionBar(editedMessage);
                                        }
                                        String title2 = (String) conf.getLang("Change-Code.Correct-Code-Title.title");
                                        String subtitle2 = (String) conf.getLang("Change-Code.Correct-Code-Title.subtitle");
                                        final Component mainTitle = Color.format(title2);
                                        final Component subtitle = Color.format(subtitle2);
                                        final Long fadeIn = 500L;
                                        final Long stay = 1000L;
                                        final Long fadeOut = 500L;

                                        final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                        final Title title = Title.title(mainTitle, subtitle, times);

                                        p.showTitle(title);
                                        code.remove(p);
                                        waitingForInput2.add(p);
                                        new BukkitRunnable() {
                                            @Override
                                            public void run() {
                                                if (code.containsKey(p)) {
                                                    String pass = code.get(p);
                                                    String blockName = block.getType().toString();
                                                    blockName = blockName.substring(0, 1).toUpperCase() + blockName.substring(1).toLowerCase();
                                                    blockName = blockName.replace("_", " ");
                                                    lockable.setLock(pass);
                                                    block.update();
                                                    String msg = (String) conf.getLang("Change-Code.Code-Changed");
                                                    msg = msg.replace("%prefix%", prefix);
                                                    msg = msg.replace("%block%", blockName);
                                                    msg = msg.replace("%code%", pass);
                                                    Component editedMessage = Color.format(msg);
                                                    p.sendMessage(editedMessage);
                                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                                        p.sendActionBar(editedMessage);
                                                    }
                                                    String title2 = (String) conf.getLang("Change-Code.Code-Changed-Title.title");
                                                    title2 = title2.replace("%block%", blockName);
                                                    String subtitle2 = (String) conf.getLang("Change-Code.Code-Changed-Title.subtitle");
                                                    subtitle2 = subtitle2.replace("%code%", pass);
                                                    final Component mainTitle = Color.format(title2);
                                                    final Component subtitle = Color.format(subtitle2);
                                                    final Long fadeIn = 500L;
                                                    final Long stay = 1000L;
                                                    final Long fadeOut = 500L;

                                                    final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                                    final Title title = Title.title(mainTitle, subtitle, times);

                                                    p.showTitle(title);
                                                    code.remove(p);
                                                    this.cancel();
                                                } else if (waitingForInput2.contains(p)) {
                                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                                        String msg = (String) conf.getLang("General.Crouch-To-Exit");
                                                        Component message = Color.format(msg);
                                                        p.sendActionBar(message);
                                                    }
                                                    String title2 = (String) conf.getLang("Change-Code.Set-Code-Title.title");
                                                    String subtitle2 = (String) conf.getLang("Change-Code.Set-Code-Title.subtitle");
                                                    final Component mainTitle = Color.format(title2);
                                                    final Component subtitle = Color.format(subtitle2);
                                                    final Long fadeIn = 500L;
                                                    final Long stay = 1000L;
                                                    final Long fadeOut = 500L;

                                                    final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                                    final Title title = Title.title(mainTitle, subtitle, times);

                                                    p.showTitle(title);
                                                }
                                            }
                                        }.runTaskTimer(kLocks.getInstance(), 0, 20);
                                        this.cancel();
                                    }
                                } else if (waitingForInput.contains(p)) {
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                        String msg = (String) conf.getLang("General.Crouch-To-Exit");
                                        Component message = Color.format(msg);
                                        p.sendActionBar(message);
                                    }
                                    String title2 = (String) conf.getLang("Change-Code.Type-Code-Title.title");
                                    String subtitle2 = (String) conf.getLang("Change-Code.Type-Code-Title.subtitle");
                                    final Component mainTitle = Color.format(title2);
                                    final Component subtitle = Color.format(subtitle2);
                                    final Long fadeIn = 500L;
                                    final Long stay = 1000L;
                                    final Long fadeOut = 500L;

                                    final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                    final Title title = Title.title(mainTitle, subtitle, times);

                                    p.showTitle(title);
                                }
                            }
                        }.runTaskTimer(kLocks.getInstance(), 0, 20);
                    }else {
                        String truePass = ((Lockable) block).getLock();
                        if (preCoded1.equals(truePass)){
                            String blockName = block.getType().toString();
                            blockName = blockName.substring(0, 1).toUpperCase() + blockName.substring(1).toLowerCase();
                            blockName = blockName.replace("_", " ");
                            lockable.setLock(preCoded2);
                            block.update();
                            String msg = (String) conf.getLang("Change-Code.Code-Changed");
                            msg = msg.replace("%prefix%", prefix);
                            msg = msg.replace("%block%", blockName);
                            msg = msg.replace("%code%", preCoded2);
                            Component editedMessage = Color.format(msg);
                            p.sendMessage(editedMessage);
                            if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                                p.sendActionBar(editedMessage);
                            }
                            String title2 = (String) conf.getLang("Change-Code.Code-Changed-Title.title");
                            title2 = title2.replace("%block%", blockName);
                            String subtitle2 = (String) conf.getLang("Change-Code.Code-Changed-Title.subtitle");
                            subtitle2 = subtitle2.replace("%code%", preCoded2);
                            final Component mainTitle = Color.format(title2);
                            final Component subtitle = Color.format(subtitle2);
                            final Long fadeIn = 500L;
                            final Long stay = 1000L;
                            final Long fadeOut = 500L;

                            final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                            final Title title = Title.title(mainTitle, subtitle, times);

                            p.showTitle(title);
                        }
                    }
                }
            }else {
                String msg = (String) conf.getLang("General.This-Block-Is-Not-Lockable");
                msg = msg.replace("%prefix%", prefix);
                Component editedMessage = Color.format(msg);
                p.sendMessage(editedMessage);
                if (Settings.getInstance().getBoolean("General.Action-Bars")){
                    p.sendActionBar(editedMessage);
                }
            }
        }
    }
    @EventHandler
    public void chatHandler(AsyncChatEvent e){
        if (waitingForInput.contains(e.getPlayer())) {
            e.setCancelled(true);
            String code = PlainTextComponentSerializer.plainText().serialize(e.message());
            this.code.put(e.getPlayer(), code);
            waitingForInput.remove(e.getPlayer());
        }else if (waitingForInput2.contains(e.getPlayer())) {
            e.setCancelled(true);
            String code = PlainTextComponentSerializer.plainText().serialize(e.message());
            this.code.put(e.getPlayer(), code);
            waitingForInput2.remove(e.getPlayer());
        }
    }
    @EventHandler
    public void sneakHandler(PlayerToggleSneakEvent e){
        if (waitingForInput.contains(e.getPlayer())) {
            e.setCancelled(true);
            String msg = (String) Settings.getInstance().getLang("General.Cancelled");
            msg = msg.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
            e.getPlayer().sendMessage(Color.format(msg));
            if (Settings.getInstance().getBoolean("General.Action-Bars")){
                Component message = Color.format(msg);
                e.getPlayer().sendActionBar(message);
            }
            waitingForInput.remove(e.getPlayer());
        }else if (waitingForInput2.contains(e.getPlayer())) {
            e.setCancelled(true);
            String msg = (String) Settings.getInstance().getLang("General.Cancelled");
            msg = msg.replace("%prefix%", (String) Settings.getInstance().getLang("General.Prefix"));
            e.getPlayer().sendMessage(Color.format(msg));
            if (Settings.getInstance().getBoolean("General.Action-Bars")){
                Component message = Color.format(msg);
                e.getPlayer().sendActionBar(message);
            }
            waitingForInput2.remove(e.getPlayer());
        }
    }
}
