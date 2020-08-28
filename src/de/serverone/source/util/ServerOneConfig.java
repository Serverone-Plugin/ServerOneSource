package de.serverone.source.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerOneConfig {
    static HashMap<JavaPlugin, HashMap<String, ServerOneConfig>> config_list = new HashMap<>();
    File datei;
    YamlConfiguration config;

    private ServerOneConfig(String folder, String name) {
	datei = new File(folder, name);
	config = YamlConfiguration.loadConfiguration(datei);
    }

    public static void loadConfig(JavaPlugin plugin, String path, String... configs) {
	HashMap<String, ServerOneConfig> list;
	if (!config_list.containsKey(plugin)) {
	    list = new HashMap<String, ServerOneConfig>();
	    config_list.put(plugin, list);
	} else {
	    list = config_list.get(plugin);
	}

	for (String config : configs) {
	    setupResource(path, config, plugin);
	    list.put(config, new ServerOneConfig(path, config));
	}
    }

    public static ServerOneConfig getConfig(JavaPlugin plugin, String name) {
	return config_list.get(plugin).get(name);
    }

    public YamlConfiguration getConfig() {
	return config;
    }

    // Moneycontroll
    public int getMoney(Player player) {
	return config.getInt(player.getUniqueId() + ".stats.money");
    }

    public void setMoney(Player player, int amount) {
	config.set(player.getUniqueId() + ".stats.money", amount);
    }

    public void addMoney(Player player, int amount) {
	config.set(player.getUniqueId() + ".stats.money", getMoney(player) + amount);
    }

    // Getter
    public int getPlayerInt(Player player, String path) {
	return config.getInt(player.getUniqueId() + "." + path);
    }

    public String getPlayerString(Player player, String path) {
	return config.getString(player.getUniqueId() + "." + path);
    }

    public double getPlayerDouble(Player player, String path) {
	return config.getDouble(player.getUniqueId() + "." + path);
    }

    public ConfigurationSection getConfigurationSection(String path) {
	return config.getConfigurationSection(path);
    }

    //
    public int getInt(String path) {
	return config.getInt(path);
    }

    public String getString(String path) {
	return config.getString(path);
    }

    public double getDouble(String path) {
	return config.getDouble(path);
    }

    @SuppressWarnings("unchecked")
    public List<String> getList(String path) {
	List<?> list = config.getList(path);
	if (list == null || list.size() == 0)
	    return new ArrayList<String>();
	if (list.get(0) instanceof String)
	    return (List<String>) list;
	return null;
    }

    // Setter
    public void setPlayer(Player player, String path, int value) {
	config.set(player.getUniqueId() + "." + path, value);
    }

    public void setPlayer(Player player, String path, String value) {
	config.set(player.getUniqueId() + "." + path, value);
    }

    public void setPlayer(Player player, String path, double value) {
	config.set(player.getUniqueId() + "." + path, value);
    }

    //
    public void set(String path, int value) {
	config.set(path, value);
    }

    public void set(String path, String value) {
	config.set(path, value);
    }

    public void set(String path, double value) {
	config.set(path, value);
    }

    public void set(String path, List<?> list) {
	config.set(path, list);
    }

    public void setPlayerString(Player player, String path, String value) {
	config.set(player.getUniqueId() + "." + path, value);
    }
    //

    // Save
    public void save() {
	try {
	    config.save(datei);
	} catch (IOException e) {
	    Bukkit.getConsoleSender().sendMessage("Could not save Config");
	}
    }

    public static void setupResource(String dataPath, String fileName, JavaPlugin plugin) {
	dataPath = "plugins/" + dataPath;
	File folder = new File(dataPath);
	File file = new File(dataPath, fileName);

	if (!folder.exists())
	    folder.mkdirs();
	if (file.exists())
	    return;

	try {
	    
	    file.createNewFile();
	} catch (IOException e) {
	    plugin.getLogger().info("Could not create config '" + fileName + "'");
	    return;
	}

	try {
	    DataInputStream inStream = new DataInputStream(plugin.getResource("ymls/"+fileName));
	    FileOutputStream stream = new FileOutputStream(file);
	    
	    int bufLen = 1024;
	    byte[] buf = new byte[bufLen];
	    int readLen;
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        while ((readLen = inStream.read(buf, 0, bufLen)) != -1)
	            outputStream.write(buf, 0, readLen);
	    
	    stream.write(outputStream.toByteArray());
	    
	    inStream.close();
	    stream.close();
	} catch (Exception e) {
	    plugin.getLogger().info("Could not load config '" + fileName + "'");
	    e.printStackTrace();
	}
    }

    public static void setupResource(String dataPath, String[] fileNames, JavaPlugin plugin) {
	for (String fileName : fileNames) {
	    setupResource(dataPath, fileName, plugin);
	}
    }
}
