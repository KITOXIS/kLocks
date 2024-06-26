package net.starfal.klocks.Configuration;

import net.starfal.klocks.kLocks;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class Settings {
    private final static Settings instance = new Settings();
    private Settings() {
    }
    public static Settings getInstance() {
        return instance;
    }

    private File file;
    private YamlConfiguration config;
    private File enmsgFile;
    private YamlConfiguration messages_en;
    public void load(){
        file = new File(kLocks.getInstance().getDataFolder(), "settings.yml");
        enmsgFile = new File(kLocks.getInstance().getDataFolder(), "languages/en.yml");
        if (!file.exists()) {
            kLocks.getInstance().saveResource("settings.yml", false);
        }
        if (!enmsgFile.exists()) {
            kLocks.getInstance().saveResource("languages/en.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        config.options().parseComments(true);
        messages_en = YamlConfiguration.loadConfiguration(enmsgFile);
        messages_en.options().parseComments(true);
        try {
            config.save(file);
            messages_en.save(enmsgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void save(){
        try {
            config.save(file);
            messages_en.save(enmsgFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void set(String path, Object value){
        config.set(path, value);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setMSG(String path, Object value){
        messages_en.set(path, value);
        try {
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Object get(String path){
        return config.get(path);
    }
    public Object getMSG(String path){
        return messages_en.get(path);
    }
    public boolean getBoolean(String path){
        return config.getBoolean(path);
    }
    public String getString(String path){
        return config.getString(path);
    }
    public int getInt(String path){
        return config.getInt(path);
    }
    public List getList(String path){
        return config.getList(path);
    }
    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
        messages_en = YamlConfiguration.loadConfiguration(enmsgFile);
    }
}
