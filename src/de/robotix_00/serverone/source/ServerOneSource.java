package de.robotix_00.serverone.source;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.robotix_00.serverone.source.util.DefaultInvListener;

public class ServerOneSource extends JavaPlugin {
    ServerOneSource plugin;
    
    public void onEnable() {
	plugin = this;
	
	PluginManager pm = plugin.getServer().getPluginManager();
	pm.registerEvents(new DefaultInvListener(), this);
    }
}
