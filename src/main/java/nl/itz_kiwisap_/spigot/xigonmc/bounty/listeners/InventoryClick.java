package nl.itz_kiwisap_.spigot.xigonmc.bounty.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.menus.AllBountiesInventory;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.menus.MainInventory;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.menus.YourBountiesInventory;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;

public class InventoryClick implements Listener {

	private Bounties plugin;
	
	public InventoryClick(Bounties plugin) {
		this.plugin = plugin;
		Bukkit.getServer().getPluginManager().registerEvents(this, (Plugin) this.plugin);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()) return;
		
		if(e.getClickedInventory() == MainInventory.getInventory()) {
			e.setCancelled(true);
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.Main.AllBounties.DisplayName").replace('&', '§')))) {
				AllBountiesInventory.getInventories().clear();
				AllBountiesInventory.openAllBountiesInventory(this.plugin, p, 1);
				for(Bounty bounty : plugin.getManager().getBounties()) {
					bounty.setIsInInventory(false);
				}
			}
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.Main.YourBounties.DisplayName").replace('&', '§')))) {
				YourBountiesInventory.getInventories().clear();
				YourBountiesInventory.openAllBountiesInventory(this.plugin, p, 1);
				for(Bounty bounty : plugin.getManager().getBounties()) {
					if(bounty.getByPlayer().getUniqueId().toString().equals(p.getUniqueId().toString())) {
						bounty.setIsInInventory(false);
					}
				}
			}
		}
		
		if(AllBountiesInventory.getInventories().contains(e.getClickedInventory())) {
			e.setCancelled(true);
			
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Next.DisplayName").replace('&', '§')))) {
				AllBountiesInventory.openAllBountiesInventory(plugin, p, AllBountiesInventory.getInventories().indexOf(e.getClickedInventory()) + 1);
			}
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Previous.DisplayName").replace('&', '§')))) {
				AllBountiesInventory.openAllBountiesInventory(plugin, p, AllBountiesInventory.getInventories().indexOf(e.getClickedInventory()) - 1);
			}
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Close.DisplayName").replace('&', '§')))) {
				p.closeInventory();
			}
		}
		
		if(YourBountiesInventory.getInventories().contains(e.getClickedInventory())) {
			e.setCancelled(true);
			
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.YourBounties.Next.DisplayName").replace('&', '§')))) {
				YourBountiesInventory.openAllBountiesInventory(plugin, p, YourBountiesInventory.getInventories().indexOf(e.getClickedInventory()) + 1);
			}
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.YourBounties.Previous.DisplayName").replace('&', '§')))) {
				YourBountiesInventory.openAllBountiesInventory(plugin, p, YourBountiesInventory.getInventories().indexOf(e.getClickedInventory()) - 1);
			}
			if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equals(ChatColor.stripColor(plugin.getResources().getMessages().getString("Messages.Inventories.YourBounties.Close.DisplayName").replace('&', '§')))) {
				p.closeInventory();
			}
		}
	}
}
