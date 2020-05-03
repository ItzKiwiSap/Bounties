package nl.itz_kiwisap_.spigot.xigonmc.bounty.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.Utils;

public class EntityDeath implements Listener {

	private Bounties plugin;
	
	public EntityDeath(Bounties plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin) this.plugin);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		Entity dead = e.getEntity();
		
		if(dead instanceof Player) {
			Player p = (Player) dead;
			
			if(plugin.getManager().hasBounty(p)) {
				Player killer = e.getEntity().getKiller();
				if(killer == null || !(killer instanceof Player)) { return; }
				Bounty bounty = plugin.getManager().getBounty(p);
				
				plugin.getEconomy().withdrawPlayer(killer, bounty.getWorth());
				Utils.sendMessage(p, plugin.getResources().getMessages().getString("Messages.Killed.PlayerDeathAndLostBounty").replace("%killer%", killer.getName()).replace("%worth%", String.valueOf(bounty.getWorth())), true);
				Utils.sendMessage(killer, plugin.getResources().getMessages().getString("Messages.Killed.PlayerDeathAndReceivedBounty").replace("%player%", p.getName()).replace("%worth%", String.valueOf(bounty.getWorth())), true);
				plugin.getManager().getBounties().remove(bounty);
			}
		}
	}
}
