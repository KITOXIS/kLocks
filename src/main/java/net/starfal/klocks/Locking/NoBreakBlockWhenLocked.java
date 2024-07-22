package net.starfal.klocks.Locking;


import io.papermc.paper.event.block.BlockBreakBlockEvent;
import net.starfal.klocks.Configuration.Settings;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.util.Iterator;

public class NoBreakBlockWhenLocked implements Listener {
    private static NoBreakBlockWhenLocked instance;
    public static NoBreakBlockWhenLocked getInstance() {
        if(instance == null) {
            instance = new NoBreakBlockWhenLocked();
        }
        return instance;
    }
    @EventHandler
    public void blockBreak(BlockBreakEvent event){
        if (Settings.getInstance().getBoolean("Chest-Invulnerability.Breaking")) {
            BlockState block = event.getBlock().getState();
            if (block instanceof Lockable lockable) {
                if (lockable.isLocked()) {
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        if (Settings.getInstance().getBoolean("Chest-Invulnerability.Exploding")) {
            Iterator<Block> it = event.blockList().iterator();
            while (it.hasNext()) {
                Block block = it.next();
                if (block.getState() instanceof Lockable lockable) {
                    if (lockable.isLocked()) {
                        it.remove();
                    }
                }
            }
        }
    }
    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event){
        BlockState sourceBlock = event.getSource().getLocation().getBlock().getState();
        BlockState destinationBlock = event.getDestination().getLocation().getBlock().getState();

        if ((sourceBlock instanceof Lockable && ((Lockable) sourceBlock).isLocked()) ||
                (destinationBlock instanceof Lockable && ((Lockable) destinationBlock).isLocked())) {
            if (Settings.getInstance().getBoolean("Chest-Invulnerability.Inventory-Moving")) {
                event.setCancelled(true);
            }
        }
    }
}
