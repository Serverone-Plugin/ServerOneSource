package de.serverone.source.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Cooldown {
	private int cooldownTime;
	private JavaPlugin plugin;
	private List<String> list = new ArrayList<>();
	
	
	public Cooldown(int cooldownTime, JavaPlugin plugin) {
		this.cooldownTime = cooldownTime;
		this.plugin = plugin;
	}
	
	public boolean run(Player player) {
		if(list.contains(player.getName())) return false;
		else {
			list.add(player.getName());
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					list.remove(player.getName());
				}
			}, cooldownTime);
			return true;
		}
	}
}