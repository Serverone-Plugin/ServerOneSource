package de.robotix_00.serverone.source.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DefaultInvListener implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
	if (!(event.getWhoClicked() instanceof Player))
	    return;

	if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR
		|| !(event.getCurrentItem().hasItemMeta() || event.getCurrentItem().getItemMeta().hasLore()
			|| event.getCurrentItem().getItemMeta().hasDisplayName()))
	    return;

	final Player player = (Player) event.getWhoClicked();
	final String displayname = event.getCurrentItem().getItemMeta().getDisplayName();
	final List<String> lores = event.getCurrentItem().getItemMeta().getLore();

	if (lores == null)
	    return;
	if (!lores.contains("ß8MenuItem")) return;
	event.setCancelled(true);

	switch (displayname) {
	case "ß6ßlschlieﬂen":
	    player.closeInventory();
	    break;
	}
    }
}
