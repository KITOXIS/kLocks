package net.starfal.klocks.Locking;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Functions.Color;
import net.starfal.klocks.kLocks;
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
import java.util.Objects;

public class Unlock implements Listener {
    private static Unlock instance;
    public static Unlock getInstance() {
        if(instance == null) {
            instance = new Unlock();
        }
        return instance;
    }
    private HashMap<Player, String> code = new HashMap<>();
    private ArrayList<Player> waitingForInput = new ArrayList<>();
    public void unlock(Player p){
        BlockState block = p.getTargetBlock(null, Settings.getInstance().getInt("Locking.Locking-Range")).getState();
        if (block == null) {
            p.sendMessage(Color.format("<red>You are not looking at a block!"));
            if (Settings.getInstance().getBoolean("General.Action-Bars")){
                Component message = Component.text(Color.format("<red>You are not looking at a block!"));
                p.sendActionBar(message);
            }
        } else {
            if (block instanceof Lockable) {
                Lockable lockable = (Lockable) block;
                if (!lockable.isLocked()) {
                    p.sendMessage(Color.format("<red>This block is not locked!"));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component message = Component.text(Color.format("<red>This block is not locked!"));
                        p.sendActionBar(message);
                    }
                } else {
                    if (waitingForInput.contains(p)){
                        p.sendMessage(Color.format("<red>Already waiting for input!"));
                        if (Settings.getInstance().getBoolean("General.Action-Bars")){
                            Component message = Component.text(Color.format("<red>Already waiting for input!"));
                            p.sendActionBar(message);
                        }
                        return;
                    }
                    waitingForInput.add(p);
                    p.sendMessage(Color.format("<green>Enter the code for this lock:"));
                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                        Component message = Component.text(Color.format("<green>Enter the code for this lock:"));
                        p.sendActionBar(message);
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (code.containsKey(p)) {
                                String pass = code.get(p);
                                String truePass = ((Lockable) block).getLock();
                                if (!pass.equals(truePass)){
                                    p.sendMessage(Color.format("<red>Incorrect code!"));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                                        Component message = Component.text(Color.format("<red>Incorrect code!"));
                                        p.sendActionBar(message);
                                    }
                                    code.remove(p);
                                    this.cancel();
                                }else {
                                    String blockName = block.getType().toString();
                                    blockName = blockName.substring(0, 1).toUpperCase() + blockName.substring(1).toLowerCase();
                                    blockName = blockName.replace("_", " ");
                                    lockable.setLock(null);
                                    block.update();
                                    p.sendMessage(Color.format("<green>" + blockName + " has been unlocked!"));
                                    if (Settings.getInstance().getBoolean("General.Action-Bars")){
                                        Component message = Component.text(Color.format("<green>" + blockName + " has been unlocked!"));
                                        p.sendActionBar(message);
                                    }
                                    final Component mainTitle = Component.text(Color.format("<green>" + blockName + " Unlocked!"));
                                    final Component subtitle = Component.text(Color.format(""));
                                    final Long fadeIn = 500L;
                                    final Long stay = 1000L;
                                    final Long fadeOut = 500L;

                                    final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                    final Title title = Title.title(mainTitle, subtitle, times);

                                    p.showTitle(title);
                                    code.remove(p);
                                    this.cancel();
                                }
                            } else if (waitingForInput.contains(p)) {
                                if (Settings.getInstance().getBoolean("General.Action-Bars")){
                                    Component message = Component.text(Color.format("&b&lCROUCH &r&7to exit."));
                                    p.sendActionBar(message);
                                }
                                final Component mainTitle = Component.text(Color.format("<yellow>Type Code"));
                                final Component subtitle = Component.text(Color.format("<gray>Type the code in chat."));
                                final Long fadeIn = 500L;
                                final Long stay = 1000L;
                                final Long fadeOut = 500L;

                                final Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn), Duration.ofMillis(stay), Duration.ofMillis(fadeOut));
                                final Title title = Title.title(mainTitle, subtitle, times);

                                p.showTitle(title);
                            }
                        }
                    }.runTaskTimer(kLocks.getInstance(), 0, 20);
                }
            }else {
                p.sendMessage(Color.format("<red>This block is not lockable!"));
                if (Settings.getInstance().getBoolean("General.Action-Bars")) {
                    Component message = Component.text(Color.format("<red>This block is not lockable!"));
                    p.sendActionBar(message);
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
        }
    }
    @EventHandler
    public void sneakHandler(PlayerToggleSneakEvent e){
        if (waitingForInput.contains(e.getPlayer())) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Color.format("<yellow>Cancelled!"));
            if (Settings.getInstance().getBoolean("General.Action-Bars")){
                Component message = Component.text(Color.format("<yellow>Cancelled!"));
                e.getPlayer().sendActionBar(message);
            }
            waitingForInput.remove(e.getPlayer());
        }
    }
}