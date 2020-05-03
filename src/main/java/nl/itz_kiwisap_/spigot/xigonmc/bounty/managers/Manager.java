package nl.itz_kiwisap_.spigot.xigonmc.bounty.managers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;

public class Manager {

	private Bounties plugin;
	private List<Bounty> bounties;
	
	public Manager(Bounties plugin) {
		this.plugin = plugin;
		this.bounties = new ArrayList<>();
	}
	
	public ItemStack getItem(Bounty bounty) {
		if(this.bounties == null || this.bounties.isEmpty()) return null;
		else {
			if(this.bounties.contains(bounty)) {
				ItemStack item = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta itemm = (SkullMeta) item.getItemMeta();
				itemm.setOwningPlayer(bounty.getForPlayer());
				itemm.setDisplayName(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Bounty.DisplayName").replace('&', '§').replace("%player%", bounty.getForPlayer().getName()));
				List<String> lore = new ArrayList<>();
				for(String s : plugin.getResources().getMessages().getStringList("Messages.Inventories.AllBounties.Bounty.Lore")) {
					lore.add(s.replace('&', '§').replace("%byplayer%", bounty.getByPlayer().getName())
							.replace("%worth%", String.valueOf(bounty.getWorth())));
				}
				itemm.setLore(lore);
				itemm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				itemm.addItemFlags(ItemFlag.HIDE_PLACED_ON);
				item.setItemMeta(itemm);
				
				return item;
			}
			return null;
		}
	}
	
	public boolean hasBounty(Player p) {
		if(this.bounties == null || this.bounties.isEmpty()) return false;
		else {
			for(Bounty bounty : this.bounties) {
				if(bounty.getForPlayer().getUniqueId().toString().equals(p.getUniqueId().toString())) {
					return true;
				}
			}
			return false;
		}
	}
	
	public Bounty getBounty(Player p) {
		if(this.bounties == null || this.bounties.isEmpty()) return null;
		else {
			for(Bounty bounty : this.bounties) {
				if(bounty.getForPlayer().getUniqueId().toString().equals(p.getUniqueId().toString())) {
					return bounty;
				}
			}
			return null;
		}
	}
	
	public List<Bounty> getBounties() { return this.bounties; }
}