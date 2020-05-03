package nl.itz_kiwisap_.spigot.xigonmc.bounty.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.Utils;

public class AllBountiesInventory {

	private static List<Inventory> inventories = new ArrayList<>();
	
	@SuppressWarnings("deprecation")
	public static void openAllBountiesInventory(Bounties plugin, Player p, int page) {
		int invSize = 54;
		int neededInventories = (plugin.getManager().getBounties() == null || plugin.getManager().getBounties().size() < 36) ? 1 : ((int) Math.ceil(plugin.getManager().getBounties().size() / invSize));
		
		for(int i = 0; i < neededInventories; i++) {
			Inventory inv = Bukkit.createInventory(null, invSize, Utils.tr(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Title").replace("%page%", String.valueOf(i + 1))));
			inventories.add(inv);
		}
		
		int headslot = 0;
		
		for(Inventory inv : inventories) {
			if(inventories.size() > 1) {
				ItemStack previous = new ItemStack(Material.PLAYER_HEAD, 1);
			    SkullMeta previousMeta = (SkullMeta) previous.getItemMeta();
			    previousMeta.setOwner("MHF_ArrowLeft");
			    previousMeta.setDisplayName(Utils.tr(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Previous.DisplayName")));
			    previous.setItemMeta(previousMeta);
			    
			    inv.setItem(47, previous);
			}

		    ItemStack close = new ItemStack(Material.BARRIER, 1);
		    ItemMeta closeMeta = close.getItemMeta();
		    closeMeta.setDisplayName(Utils.tr(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Close.DisplayName")));
		    close.setItemMeta(closeMeta);

		    if(inventories.get(inventories.size() - 1) != inv && inventories.get(0) != inv) {
			    ItemStack next = new ItemStack(Material.PLAYER_HEAD, 1);
			    SkullMeta nextMeta = (SkullMeta) next.getItemMeta();
			    nextMeta.setOwner("MHF_ArrowRight");
			    nextMeta.setDisplayName(Utils.tr(plugin.getResources().getMessages().getString("Messages.Inventories.AllBounties.Next.DisplayName")));
			    next.setItemMeta(nextMeta);
			    
			    inv.setItem(51, next);
		    }
		    
		    ItemStack glass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
			ItemMeta glassm = glass.getItemMeta();
			glassm.setDisplayName("§7");
			glass.setItemMeta(glassm);
			
			for(int i = 0; i < 9; i++) {
				inv.setItem(i, glass);
			}
			
			for(int i = 36; i < 45; i++) {
				inv.setItem(i, glass);
			}

		    inv.setItem(49, close);
		    
		    for(int i = 9; i < 45; i++) {
		    	if(plugin.getManager().getBounties() != null) {
				    for(Bounty bounty : plugin.getManager().getBounties()) {
				    	if(!bounty.isInInventory()) {
					    	headslot++;
					    	inv.setItem(headslot + 8, plugin.getManager().getItem(bounty));
					    	bounty.setIsInInventory(true);
				    	}
				    }
		    	}
		    }
		}
		
		p.openInventory(inventories.get(0));
	}
	
	public static List<Inventory> getInventories() { return inventories; }
}
