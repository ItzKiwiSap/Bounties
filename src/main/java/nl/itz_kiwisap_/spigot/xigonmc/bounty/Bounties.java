package nl.itz_kiwisap_.spigot.xigonmc.bounty;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.commands.BountiesCommand;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.commands.BountyCommand;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.commands.TabCompletion;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.listeners.EntityDeath;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.listeners.InventoryClick;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.managers.Manager;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.objects.Bounty;
import nl.itz_kiwisap_.spigot.xigonmc.bounty.utils.configuration.Resources;

public class Bounties extends JavaPlugin {
	
	private static Bounties instance;
	private Resources resources = new Resources(this);
	private Economy econ;
	private Manager manager;
	
	@Override
	public void onEnable() {		
		instance = this;
		
		this.resources.load();
		this.manager = new Manager(this);
		
		loadCommands();
		
		new InventoryClick(this);
		new EntityDeath(this);
		
		this.resources.saveAndAddBounties();
		
		if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
	}
	
	@Override
	public void onDisable() {
		instance = null;
		
		for(Bounty bounty : this.manager.getBounties()) {
			this.resources.saveAndRemoveBounty(bounty);
		}
	}
	
	private void loadCommands() {
		getCommand("bounty").setExecutor(new BountyCommand());
		getCommand("bounty").setTabCompleter(new TabCompletion());
		getCommand("bounties").setExecutor(new BountiesCommand());
	}
	
	private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	public static Bounties getInstance() { return instance; }
	public Resources getResources() { return this.resources; }
	public Economy getEconomy() { return this.econ; }
	public Manager getManager() { return this.manager; }
}
