package nl.itz_kiwisap_.spigot.xigonmc.bounty.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class TabCompletion implements TabCompleter {

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		List<String> completions = new ArrayList<String>();

		if (args.length == 1) {
			String partialCommand = args[0];
			List<String> commands = new ArrayList<>();
			if(sender.hasPermission("xigonmc.bounty.reload") || sender.isOp()) {
				commands.add("set");
				commands.add("reload");
			} else commands.add("set");
			StringUtil.copyPartialMatches(partialCommand, commands, completions);
		}

		if(args[0].equals("set")) {
			if (args.length == 2) {
				String partialPlayer = args[1];
				List<String> playerNames = new ArrayList<>();
				for(Player p : Bukkit.getOnlinePlayers()) playerNames.add(p.getName());
				StringUtil.copyPartialMatches(partialPlayer, playerNames, completions);
			}
		}

		Collections.sort(completions);
		return completions;
	}
}