package de.serverone.source.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag.State;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

public class ServerOneWorldGuard {

    public static boolean canBreak(Location loc, Player player) {
	RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	RegionManager rgManager = container.get(BukkitAdapter.adapt(player.getWorld()));
	ApplicableRegionSet regions = rgManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
	
	for (ProtectedRegion r : regions) {
	    if (r.contains((BukkitAdapter.asBlockVector(loc)))) {
		boolean isOwner = r.getOwners().getUniqueIds().contains(player.getUniqueId());
		boolean isMember = r.getMembers().getUniqueIds().contains(player.getUniqueId());
		if (isOwner || isMember)
		    return true;
		return false;
	    }
	}
	if(rgManager.getRegion("__global__").getFlag(Flags.PASSTHROUGH) != State.DENY)
	    return true;
	return false;
    }

    public static ProtectedRegion getOwnersRegion(Player player) {
	RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
	RegionManager regions = container.get(BukkitAdapter.adapt(player.getWorld()));
	ApplicableRegionSet regionses = regions.getApplicableRegions(BukkitAdapter.asBlockVector(player.getLocation()));
	for (ProtectedRegion r : regionses) {
	    if(r.getOwners().contains(player.getUniqueId()))
		return r;
	}
	return null;
    }
}
