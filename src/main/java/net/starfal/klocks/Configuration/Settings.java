package net.starfal.klocks.Configuration;

import net.starfal.klocks.kLocks;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

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
        YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));

        for (String key : defaultConfig.getKeys(true)) {
            if (!config.contains(key)) {
                config.set(key, defaultConfig.get(key));
            }
        }

        YamlConfiguration defaultMessages = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("languages/en.yml"))));

        for (String key : defaultMessages.getKeys(true)) {
            if (!config.contains(key)) {
                config.set(key, defaultMessages.get(key));
            }
        }
        
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
        Object value = config.get(path);
        if (value == null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));
            if (defaultConfig.contains(path)) {
                value = defaultConfig.get(path);
                config.set(path, value);
                save();
                reload();
            }
        }
        return value;
    }
    public Object getLang(String path){
        Object value = messages_en.get(path);
        if (value == null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("languages/en.yml"))));
            if (defaultConfig.contains(path)) {
                value = defaultConfig.get(path);
                messages_en.set(path, value);
                save();
                reload();
            }
        }
        return value;
    }
    public boolean getBoolean(String path){
        if (config.contains(path)) {
            return config.getBoolean(path);
        } else {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));
            if (defaultConfig.contains(path)) {
                boolean value = defaultConfig.getBoolean(path);
                config.set(path, value);
                save();
                reload();
                return value;
            }
        }
        return false;
    }
    public String getString(String path){
        if (config.contains(path)) {
            return config.getString(path);
        } else {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));
            if (defaultConfig.contains(path)) {
                String value = defaultConfig.getString(path);
                config.set(path, value);
                save();
                reload();
                return value;
            }
        }
        return null;
    }
    public int getInt(String path){
        if (config.contains(path)) {
            return config.getInt(path);
        } else {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));
            if (defaultConfig.contains(path)) {
                int value = defaultConfig.getInt(path);
                config.set(path, value);
                save();
                reload();
                return value;
            }
        }
        return 0;
    }
    public List getList(String path){
        if (config.contains(path)) {
            return config.getList(path);
        } else {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(Objects.requireNonNull(kLocks.getInstance().getResource("settings.yml"))));
            if (defaultConfig.contains(path)) {
                List value = defaultConfig.getList(path);
                config.set(path, value);
                save();
                reload();
                return value;
            }
        }
        return null;
    }
    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
        messages_en = YamlConfiguration.loadConfiguration(enmsgFile);
    }
}
