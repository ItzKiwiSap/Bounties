package nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.configuration;

import java.io.File;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

import nl.itz_kiwisap_.spigot.xigonmc.bounty.Bounties;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;

public class Resources {

	private Bounties plugin;
	private Resource messages;
	private Resource storage;

	public Resources(Bounties plugin) {
		this.plugin = plugin;

		this.messages = new Resource((Plugin) this.plugin, "messages.yml");
		this.storage = new Resource((Plugin) this.plugin, "storage.yml");
	}

	public void load() {
		this.messages.load();
		this.storage.load();
	}

	public void save() {
		this.messages.save();
		this.storage.save();
	}

	public void saveAndRemoveBounty(Bounty bounty) {		
		this.storage.set("Bounties." + bounty.getForPlayer().getUniqueId() + ".ByPlayer", bounty.getByPlayer().getName());
		this.storage.set("Bounties." + bounty.getForPlayer().getUniqueId() + ".Worth", bounty.getWorth());
		this.storage.save();
	}

	public void saveAndAddBounties() {
		File file = new File(plugin.getDataFolder(), "storage.yml");

		if(file.exists()) {
			if(this.storage.getConfigurationSection("Bounties") == null) return;
			for(String string : this.storage.getConfigurationSection("Bounties").getKeys(false)) {
				UUID uuid = UUID.fromString(string);
				OfflinePlayer player = plugin.getServer().getPlayer(uuid);

				String byplayername = this.storage.getString("Bounties." + string + ".ByPlayer");
				OfflinePlayer byplayer = plugin.getServer().getPlayer(byplayername);
				
				double worth = Double.valueOf(this.storage.getString("Bounties." + string + ".Worth"));

				plugin.getManager().getBounties().add(new Bounty(player, byplayer, worth));
			}
			this.storage.set("Bounties", null);
		}
	}

	public Resource getMessages() { return this.messages; }
	public Resource getStorage() { return this.storage; }
}
