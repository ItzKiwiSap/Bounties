package nl.itz_kiwisap_.spigot.xigonmc.bounty.menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.Utils;

public class MainInventory {
	
	private static Inventory inv = Bukkit.createInventory(null, 27, Utils.tr(Bounties.getInstance().getResources().getMessages().getString("Messages.Inventories.Main.Title")));
	
	public static void openMainInventory(Player p) {		
		ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta glassm = glass.getItemMeta();
		glassm.setDisplayName("§7");
		glass.setItemMeta(glassm);
		
		ItemStack allbounties = new ItemStack(Material.MAP, 1);
		ItemMeta allbountiesm = allbounties.getItemMeta();
		allbountiesm.setDisplayName(Bounties.getInstance().getResources().getMessages().getString("Messages.Inventories.Main.AllBounties.DisplayName").replace('&', '§'));
		allbountiesm.setLore(Utils.tr(Bounties.getInstance().getResources().getMessages().getStringList("Messages.Inventories.Main.AllBounties.Lore")));
		allbounties.setItemMeta(allbountiesm);
		
		ItemStack yourbounties = new ItemStack(Material.PAPER, 1);
		ItemMeta yourbountiesm = yourbounties.getItemMeta();
		yourbountiesm.setDisplayName(Utils.tr(Bounties.getInstance().getResources().getMessages().getString("Messages.Inventories.Main.YourBounties.DisplayName")));
		yourbountiesm.setLore(Utils.tr(Bounties.getInstance().getResources().getMessages().getStringList("Messages.Inventories.Main.YourBounties.Lore")));
		yourbounties.setItemMeta(yourbountiesm);
		
		inv.setItem(11, allbounties);
		inv.setItem(15, yourbounties);
		for(int i = 0; i < inv.getSize(); i++) {
			if(i == 11 || i == 15) continue;
			inv.setItem(i, glass);
		}
		
		p.openInventory(inv);
	}
	
	public static Inventory getInventory() { return inv; }
}