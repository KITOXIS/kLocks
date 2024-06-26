package net.starfal.klocks;


import net.kyori.adventure.text.minimessage.MiniMessage;
import net.starfal.klocks.Configuration.Settings;
import net.starfal.klocks.Configuration.kLocksCMD;
import net.starfal.klocks.Locking.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class kLocks extends JavaPlugin {
    private static kLocks instance;
    public static kLocks getInstance() {
        if(instance == null) {
            instance = new kLocks();
        }
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        load();
        log("<gradient:blue:green>kLock <green>| enabled!");
    }

    @Override
    public void onDisable() {
        log("<gradient:blue:green>kLock <red>| disabled!");
    }
    public void load(){
        Settings.getInstance().load();
        this.getCommand("lock").setExecutor(new LockCMD());
        this.getCommand("unlock").setExecutor(new UnlockCMD());
        this.getCommand("changecode").setExecutor(new ChangeCodeCMD());
        this.getCommand("klocks").setExecutor(new kLocksCMD());
        this.getCommand("klocks").setTabCompleter(new kLocksCMD());
        this.getServer().getPluginManager().registerEvents(Lock.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(Unlock.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(ChangeCode.getInstance(), this);
        this.getServer().getPluginManager().registerEvents(NoBreakBlockWhenLocked.getInstance(), this);
    }
    public void log(String message){
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize(message));
    }
}
