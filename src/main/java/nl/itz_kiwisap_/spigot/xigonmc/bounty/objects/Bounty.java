package nl.itz_kiwisap_.spigot.xigonmc.bounty.objects;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Bounty {

	private Player forplayer;
	private Player byplayer;
	private double worth;
	private boolean ininventory = false;
	
	public Bounty(OfflinePlayer player, OfflinePlayer byplayer2, double worth) {
		this.forplayer = (Player) player;
		this.byplayer = (Player) byplayer2;
		this.worth = worth;
	}
	
	public void setWorth(double worth) { this.worth = worth; }
	public void setIsInInventory(boolean bool) { this.ininventory = bool; }
	
	public Player getForPlayer() { return this.forplayer; }
	public Player getByPlayer() { return this.byplayer; }
	public double getWorth() { return this.worth; }
	public boolean isInInventory() { return this.ininventory; }
}
