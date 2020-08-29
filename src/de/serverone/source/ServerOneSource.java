package de.serverone.source;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.serverone.source.util.DefaultInvListener;

public class ServerOneSource extends JavaPlugin {
    ServerOneSource plugin;
    private static boolean worldGuardIsEnabled;
    
    public void onEnable() {
	plugin = this;
	
	PluginManager pm = plugin.getServer().getPluginManager();
	pm.registerEvents(new DefaultInvListener(), this);
	
	if(this.getServer().getPluginManager().getPlugin("WorldGuard") != null)  worldGuardIsEnabled = true;
    }
    
    public boolean worldGuardIsEnabled() {
	return worldGuardIsEnabled;
    }
}
