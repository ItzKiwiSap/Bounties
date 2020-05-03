package nl.itz_kiwisap_.spigot.xigonmc.bounty.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.Utils;

public class BountyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("This command can only be used in-game!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(args.length == 0) {
			for(String s : Bounties.getInstance().getResources().getMessages().getStringList("Messages.General.Help")) {
				Utils.sendMessage(p, s, false);
			}
			return true;
		} else if(args.length >= 1) {
			if(args[0].equalsIgnoreCase("set")) {
				if(args.length == 1 || args.length == 2) {
					Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.General.MissingArguments"), true);
					return true;
				}
				
				String name = args[1];
				Player player = Bukkit.getPlayer(name);
				
				if(player == null) {
					Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.Set.PlayerOffline"), true);
					return true;
				}
				
				if(player.getUniqueId().toString().equals(p.getUniqueId().toString())) {
					Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.Set.CannotBountyYourself"), true);
					return true;
				}
				
				String am = args[2];
				if(isDouble(am)) {
					double amount = Double.valueOf(am);
					
					if(Bounties.getInstance().getEconomy().getBalance(p) < amount) {
						Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.Set.NotEnoughMoney"), true);
						return true;
					}
					
					if(Bounties.getInstance().getManager().hasBounty(player)) {
						Bounty bounty = Bounties.getInstance().getManager().getBounty(player);
						
						bounty.setWorth(bounty.getWorth() + amount);
						amount = bounty.getWorth();
					} else Bounties.getInstance().getManager().getBounties().add(new Bounty(player, p, amount));
					
					Bounties.getInstance().getEconomy().depositPlayer(p, amount);
					Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.Set.Succesful").replace("%player%", player.getName()).replace("%worth%", String.valueOf(amount)), true);
					Utils.sendMessage(player, Bounties.getInstance().getResources().getMessages().getString("Messages.Set.SuccesfulToTarget").replace("%player%", player.getName()).replace("%worth%", String.valueOf(amount)), true);
					
					for(String s : Bounties.getInstance().getResources().getMessages().getStringList("Messages.Set.Broadcast")) {
						for(Player pl : Bukkit.getOnlinePlayers()) {
							Utils.sendMessage(pl, s.replace("%forplayer%", player.getName())
									.replace("%byplayer%", p.getName())
									.replace("%worth%", String.valueOf(amount)), false);
						}
					}
				}
			} else if(args[0].equalsIgnoreCase("reload")) {
				if(p.hasPermission("xigonmc.bounty.reload")) {
					Bounties.getInstance().getResources().load();
					Utils.sendMessage(p, Bounties.getInstance().getResources().getMessages().getString("Messages.Reload.Succesful"), true);
				}
			} else {
				for(String s : Bounties.getInstance().getResources().getMessages().getStringList("Messages.General.Help")) {
					Utils.sendMessage(p, s, false);
				}
				return true;
			}
		}
		
		return false;
	}
	
	private boolean isDouble(String s) {
		try {
			Double.valueOf(s);
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}