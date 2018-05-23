package com.wilobate.weirdPicks;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.wilobate.weirdPicks.updater.Updater;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WeirdPicks extends JavaPlugin implements Listener {
	public static WeirdPicks instance;
	public static Messages messages;
	public static Commands commands;

	class Startup implements Runnable {
		final WeirdPicks this$0;

		@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
		public void run() {
			WeirdPicks.this.getWorldGuard();
			WeirdPicks.this.getCommand("wpick").setExecutor(new Messages());

			WeirdPicks.getInstance().ePickLore = new ArrayList();
			WeirdPicks.getInstance().ePick = new ItemStack(WeirdPicks.getInstance().PICKAXE_MATERIAL, 1);
			WeirdPicks.getInstance().ePickMeta = WeirdPicks.getInstance().ePick.getItemMeta();
			WeirdPicks.getInstance().ePickMeta.setDisplayName(WeirdPicks.getInstance().ePickName);
			WeirdPicks.getInstance().ePickLore.add(WeirdPicks.getInstance().ePickLoreSt);
			WeirdPicks.getInstance().ePickMeta.setLore(WeirdPicks.getInstance().ePickLore);
			try {
				WeirdPicks.getInstance().ePickMeta.setUnbreakable(ePickUnbreakable);
			} catch (NoSuchMethodError e) {
				WeirdPicks.getInstance().ePickMeta.spigot().setUnbreakable(ePickUnbreakable);
			}
			WeirdPicks.getInstance().ePick.setItemMeta(WeirdPicks.getInstance().ePickMeta);

			WeirdPicks.getInstance().ebPickLore = new ArrayList();
			WeirdPicks.getInstance().ebPick = new ItemStack(WeirdPicks.getInstance().PICKAXE_MATERIAL, 1);
			WeirdPicks.getInstance().ebPickMeta = WeirdPicks.getInstance().ebPick.getItemMeta();
			WeirdPicks.getInstance().ebPickMeta.setDisplayName(WeirdPicks.getInstance().ebPickName);
			WeirdPicks.getInstance().ebPickLore.add(WeirdPicks.getInstance().ebPickLoreSt);
			WeirdPicks.getInstance().ebPickMeta.setLore(WeirdPicks.getInstance().ebPickLore);
			try {
				WeirdPicks.getInstance().ebPickMeta.setUnbreakable(ebPickUnbreakable);
			} catch (NoSuchMethodError e) {
				WeirdPicks.getInstance().ebPickMeta.spigot().setUnbreakable(ebPickUnbreakable);
			}
			WeirdPicks.getInstance().ebPick.setItemMeta(WeirdPicks.getInstance().ebPickMeta);

			WeirdPicks.getInstance().bPickLore = new ArrayList();
			WeirdPicks.getInstance().bPick = new ItemStack(WeirdPicks.getInstance().PICKAXE_MATERIAL, 1);
			WeirdPicks.getInstance().bPickMeta = WeirdPicks.getInstance().bPick.getItemMeta();
			WeirdPicks.getInstance().bPickMeta.setDisplayName(WeirdPicks.getInstance().bPickName);
			WeirdPicks.getInstance().bPickLore.add(WeirdPicks.getInstance().bPickLoreSt);
			WeirdPicks.getInstance().bPickMeta.setLore(WeirdPicks.getInstance().bPickLore);
			try {
				WeirdPicks.getInstance().bPickMeta.setUnbreakable(bPickUnbreakable);
			} catch (NoSuchMethodError e) {
				WeirdPicks.getInstance().bPickMeta.spigot().setUnbreakable(bPickUnbreakable);
			}
			WeirdPicks.getInstance().bPick.setItemMeta(WeirdPicks.getInstance().bPickMeta);

			WeirdPicks.getInstance().sPickLore = new ArrayList();
			WeirdPicks.getInstance().sPick = new ItemStack(WeirdPicks.getInstance().PICKAXE_MATERIAL, 1);
			WeirdPicks.getInstance().sPickMeta = WeirdPicks.getInstance().sPick.getItemMeta();
			WeirdPicks.getInstance().sPickMeta.setDisplayName(WeirdPicks.getInstance().sPickName);
			WeirdPicks.getInstance().sPickLore.add(WeirdPicks.getInstance().sPickLoreSt);
			WeirdPicks.getInstance().sPickMeta.setLore(WeirdPicks.getInstance().sPickLore);
			try {
				WeirdPicks.getInstance().sPickMeta.setUnbreakable(sPickUnbreakable);
			} catch (NoSuchMethodError e) {
				WeirdPicks.getInstance().sPickMeta.spigot().setUnbreakable(sPickUnbreakable);
			}
			WeirdPicks.getInstance().sPick.setItemMeta(WeirdPicks.getInstance().sPickMeta);

			WeirdPicks.getInstance().lAxeLore = new ArrayList();
			WeirdPicks.getInstance().lAxe = new ItemStack(WeirdPicks.getInstance().AXE_MATERIAL, 1);
			WeirdPicks.getInstance().lAxeMeta = WeirdPicks.getInstance().lAxe.getItemMeta();
			WeirdPicks.getInstance().lAxeMeta.setDisplayName(WeirdPicks.getInstance().lAxeName);
			WeirdPicks.getInstance().lAxeLore.add(WeirdPicks.getInstance().lAxeLoreSt);
			WeirdPicks.getInstance().lAxeMeta.setLore(WeirdPicks.getInstance().lAxeLore);
			try {
				WeirdPicks.getInstance().lAxeMeta.setUnbreakable(lAxeUnbreakable);
			} catch (NoSuchMethodError e) {
				WeirdPicks.getInstance().lAxeMeta.spigot().setUnbreakable(lAxeUnbreakable);
			}
			WeirdPicks.getInstance().lAxe.setItemMeta(WeirdPicks.getInstance().lAxeMeta);
		}

		Startup() {
			this.this$0 = WeirdPicks.this;
		}
	}

	public Material PICKAXE_MATERIAL = Material.DIAMOND_PICKAXE;
	public Material ORE_MATERIAL;
	public Material AXE_MATERIAL = Material.DIAMOND_AXE;
	public ArrayList<String> ePickLore;
	public ItemStack ePick;
	public ItemMeta ePickMeta;
	public String ePickName = "Explosive Pickaxe";
	public String ePickLoreSt = "Handle with Care!";
	public boolean ePickUnbreakable = false;
	public boolean ePickEnabled = true;
	public ArrayList<String> sPickLore;
	public ItemStack sPick;
	public ItemMeta sPickMeta;
	public String sPickName = "Smelter's Pickaxe";
	public String sPickLoreSt = "Hot to the Touch!";
	public boolean sPickUnbreakable = false;
	public boolean sPickEnabled = true;
	public ArrayList<String> lAxeLore;
	public ItemStack lAxe;
	public ItemMeta lAxeMeta;
	public String lAxeName = "Lumberjack's Axe";
	public String lAxeLoreSt = "Extra Sharp!";
	public boolean lAxeUnbreakable = false;
	public boolean lAxeEnabled = true;
	public ArrayList<String> bPickLore;
	public ItemStack bPick;
	public ItemMeta bPickMeta;
	public String bPickName = "Bountiful Pickaxe";
	public String bPickLoreSt = "Such Bounty, much wow!";
	public boolean bPickUnbreakable = false;
	public boolean bPickEnabled = true;
	public ArrayList<String> ebPickLore;
	public ItemStack ebPick;
	public ItemMeta ebPickMeta;
	public String ebPickName = "Explosive Bountiful Pickaxe";
	public String ebPickLoreSt = "Such Bounty, much pow!";
	public boolean ebPickUnbreakable = false;
	public boolean ebPickEnabled = true;
	public boolean natural = false;
	public int radius;
	public int radiusB;
	public List<ItemStack> pickaxes;
	public List<ItemStack> axes;
	public List<ItemStack> bNearOres;
	public List<String> oreList;
	public File configFile;
	public WorldGuardPlugin wg = null;
	public boolean canUpdate = true;
	public int currentConfigVersion = 2;

	public boolean EZBlocksHere = false;

	public void onLoad() {
		setInstance(this);
		setMessages(new Messages());
		setCommands(new Commands());
	}

	Server server = getServer();
	ConsoleCommandSender console = server.getConsoleSender();

	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);

		if (WeirdPicks.getCommands().isEZBlocks()) {
			console.sendMessage(ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " EZBlocks has been found");
			EZBlocksHere = true;
		}

		this.configFile = new File(getDataFolder() + "/config.yml");
		if (!this.configFile.exists()) {
			console.sendMessage(
					ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " No config file found, generating...");
			saveDefaultConfig();
		}
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
		if (this.currentConfigVersion != config.getInt("configVersion")) {
			File file = new File(getDataFolder() + "/config.yml");
			File bFile = new File(getDataFolder() + "/configBackup.yml");
			console.sendMessage(ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE
					+ " Config file out of date, generating new one...");
			file.renameTo(bFile);
			saveDefaultConfig();
		}
		LoadConfigValues();

		this.canUpdate = config.getBoolean("update-checker");
		if (this.canUpdate) {
			Updater updater = new Updater();
			String localVersion = this.getDescription().getVersion();
			console.sendMessage(
					ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " Checking for available updates...");
			console.sendMessage(ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " Current version: " + localVersion);
			updater.versionChecker(localVersion);
			String result = updater.getResult();
			String update = updater.getVersion();
			switch (result) {
			case "UPDATE_AVAILABLE":
				console.sendMessage(
						ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " Update " + update + " is available!");
				break;
			case "LATEST":
				console.sendMessage(
						ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " No update found, at least we tried!");
				break;
			case "CONNECTION_FAILED":
				console.sendMessage(
						ChatColor.AQUA + "[WeirdPicks]" + ChatColor.RED + " Could not connect to SpigotMC.org!");
				break;
			}
		} else {
			console.sendMessage(ChatColor.AQUA + "[WeirdPicks]" + ChatColor.BLUE + " Update checking disabled...");
		}
		Bukkit.getServer().getPluginManager().registerEvents(new Events(), this);
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Startup(), 10L);

		LoadPicks();

		getInstance().ePickLore = new ArrayList();
		getInstance().ePick = new ItemStack(getInstance().PICKAXE_MATERIAL, 1);
		getInstance().ePickMeta = getInstance().ePick.getItemMeta();
		getInstance().ePickMeta.setDisplayName(getInstance().ePickName);
		getInstance().ePickLore.add(getInstance().ePickLoreSt);
		getInstance().ePickMeta.setLore(getInstance().ePickLore);
		try {
			getInstance().ePickMeta.setUnbreakable(ePickUnbreakable);
		} catch (NoSuchMethodError e) {
			getInstance().ePickMeta.spigot().setUnbreakable(ePickUnbreakable);
		}
		getInstance().ePick.setItemMeta(getInstance().ePickMeta);

		getInstance().ebPickLore = new ArrayList();
		getInstance().ebPick = new ItemStack(getInstance().PICKAXE_MATERIAL, 1);
		getInstance().ebPickMeta = getInstance().ebPick.getItemMeta();
		getInstance().ebPickMeta.setDisplayName(getInstance().ebPickName);
		getInstance().ebPickLore.add(getInstance().ebPickLoreSt);
		getInstance().ebPickMeta.setLore(getInstance().ebPickLore);
		try {
			getInstance().ebPickMeta.setUnbreakable(ebPickUnbreakable);
		} catch (NoSuchMethodError e) {
			getInstance().ebPickMeta.spigot().setUnbreakable(ebPickUnbreakable);
		}
		getInstance().ebPick.setItemMeta(getInstance().ebPickMeta);

		getInstance().bPickLore = new ArrayList();
		getInstance().bPick = new ItemStack(getInstance().PICKAXE_MATERIAL, 1);
		getInstance().bPickMeta = getInstance().bPick.getItemMeta();
		getInstance().bPickMeta.setDisplayName(getInstance().bPickName);
		getInstance().bPickLore.add(getInstance().bPickLoreSt);
		getInstance().bPickMeta.setLore(getInstance().bPickLore);
		try {
			getInstance().bPickMeta.setUnbreakable(bPickUnbreakable);
		} catch (NoSuchMethodError e) {
			getInstance().bPickMeta.spigot().setUnbreakable(bPickUnbreakable);
		}
		getInstance().bPick.setItemMeta(getInstance().bPickMeta);

		getInstance().sPickLore = new ArrayList();
		getInstance().sPick = new ItemStack(getInstance().PICKAXE_MATERIAL, 1);
		getInstance().sPickMeta = getInstance().sPick.getItemMeta();
		getInstance().sPickMeta.setDisplayName(getInstance().sPickName);
		getInstance().sPickLore.add(getInstance().sPickLoreSt);
		getInstance().sPickMeta.setLore(getInstance().sPickLore);
		try {
			getInstance().sPickMeta.setUnbreakable(sPickUnbreakable);
		} catch (NoSuchMethodError e) {
			getInstance().sPickMeta.spigot().setUnbreakable(sPickUnbreakable);
		}
		getInstance().sPick.setItemMeta(getInstance().sPickMeta);

		getInstance().lAxeLore = new ArrayList();
		getInstance().lAxe = new ItemStack(getInstance().AXE_MATERIAL, 1);
		getInstance().lAxeMeta = getInstance().lAxe.getItemMeta();
		getInstance().lAxeMeta.setDisplayName(getInstance().lAxeName);
		getInstance().lAxeLore.add(getInstance().lAxeLoreSt);
		getInstance().lAxeMeta.setLore(getInstance().lAxeLore);
		try {
			getInstance().lAxeMeta.setUnbreakable(lAxeUnbreakable);
		} catch (NoSuchMethodError e) {
			getInstance().lAxeMeta.spigot().setUnbreakable(lAxeUnbreakable);
		}
		getInstance().lAxe.setItemMeta(getInstance().lAxeMeta);
	}

	public void onDisable() {
		console.sendMessage(ChatColor.AQUA + "[WeirdPicks]" + ChatColor.RED
				+ " You just used an Explosive Pickaxe on this plugin! :D");
	}

	public void LoadConfigValues() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
		this.radius = config.getInt("Explosion-Radius");
		this.radiusB = config.getInt("Bounty-Radius");
	}

	public void LoadPicks() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
		this.ePickName = config.getString("ePickName");
		this.ePickLoreSt = config.getString("ePickLoreSt");
		this.ePickEnabled = config.getBoolean("ePickEnable");
		this.ePickUnbreakable = config.getBoolean("ePickUnbreakable");

		this.ebPickName = config.getString("ebPickName");
		this.ebPickLoreSt = config.getString("ebPickLoreSt");
		this.ebPickEnabled = config.getBoolean("ebPickEnable");
		this.ebPickUnbreakable = config.getBoolean("ebPickUnbreakable");

		this.bPickName = config.getString("bPickName");
		this.bPickLoreSt = config.getString("bPickLoreSt");
		this.bPickEnabled = config.getBoolean("bPickEnable");
		this.bPickUnbreakable = config.getBoolean("bPickUnbreakable");

		this.sPickName = config.getString("sPickName");
		this.sPickLoreSt = config.getString("sPickLoreSt");
		this.sPickEnabled = config.getBoolean("sPickEnable");
		this.sPickUnbreakable = config.getBoolean("sPickUnbreakable");

		this.lAxeName = config.getString("lAxeName");
		this.lAxeLoreSt = config.getString("lAxeLoreSt");
		this.lAxeEnabled = config.getBoolean("lAxeEnable");
		this.lAxeUnbreakable = config.getBoolean("lAxeUnbreakable");
	}

	public FileConfiguration getSettings() {
		FileConfiguration config = YamlConfiguration.loadConfiguration(this.configFile);
		return config;
	}

	private WorldGuardPlugin getWorldGuard() {
		Plugin w = getServer().getPluginManager().getPlugin("WorldGuard");
		if ((w == null) || (!(w instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin) w;
	}

	public static WeirdPicks getInstance() {
		return instance;
	}

	public static void setInstance(WeirdPicks main) {
		instance = main;
	}

	public static Messages getMessages() {
		return messages;
	}

	public static Commands getCommands() {
		return commands;
	}

	public static void setMessages(Messages messages1) {
		messages = messages1;
	}

	public static void setCommands(Commands commands1) {
		commands = commands1;
	}
}