package nl.itz_kiwisap_.spigot.xigonmc.bounty.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;

public class Utils {

	public static void sendMessage(Player p, String message, boolean prefix) {
		if(prefix) p.sendMessage(ChatColor.translateAlternateColorCodes('&', Bounties.getInstance().getResources().getMessages().getString("Messages.General.Prefix") + message));
		else p.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static List<String> tr(List<String> s) {
		List<String> news = new ArrayList<>();
		for(String st : s) {
			news.add(ChatColor.translateAlternateColorCodes('&', st));
		}
		return news;
	}
	
	public static String tr(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
}
