package nl.itz_kiwisap_.spigot.xigonmc.bounty.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.menus.MainInventory;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.Utils;

public class BountiesCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can only be used in-game!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length == 0) {
			MainInventory.openMainInventory(p);
			return true;
		} else {
			for(String s : Bounties.getInstance().getResources().getMessages().getStringList("Messages.General.Help")) {
				Utils.sendMessage(p, s, false);
			}
			return true;
		}
	}
}
