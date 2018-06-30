package com.wilobate.weirdPicks;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Messages implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if ((sender instanceof ConsoleCommandSender)) {
			ConsoleCommandSender player = (ConsoleCommandSender) sender;
			if ((args.length == 0) && (cmd.getName().equalsIgnoreCase("wpick"))) {
				player.sendMessage(ChatColor.AQUA + "Command is /wpick give <player> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
				return true;
			}
			if ((args.length == 1) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("help"))) {
				player.sendMessage(ChatColor.AQUA + "Command is /wpick give <player> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
				return true;
			}
			if ((args.length == 1) && (cmd.getName().equalsIgnoreCase("wpick"))
					&& (args[0].equalsIgnoreCase("reload"))) {
				WeirdPicks.getInstance().LoadConfigValues();
				player.sendMessage(ChatColor.AQUA + "Config reloaded!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("xpPick"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveXPPick(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("EPick"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveEPick(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("BPick"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveBPick(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("EBPick"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveEBPick(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("SPick"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveSPick(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick")) && (args[0].equalsIgnoreCase("give"))
					&& (!args[1].isEmpty()) && (args[2].equalsIgnoreCase("LAxe"))) {
				String a1 = args[1];
				try {
					Player target = sender.getServer().getPlayer(a1);
					WeirdPicks.getCommands().doGiveLAxe(target);
				} catch (Exception e) {
					player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
							+ " does not exist!");
					return false;
				}
				player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE + a1
						+ ChatColor.AQUA + "!");
				return true;
			}
			player.sendMessage(ChatColor.AQUA + "Command is /wpick give <player> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
			return false;
		}
		if ((sender instanceof Player)) {
			Player player = (Player) sender;
			if (player.hasPermission("wpick.epick")) {
				if ((args.length == 0) && (cmd.getName().equalsIgnoreCase("wpick"))) {
					player.sendMessage(
							ChatColor.AQUA + "Command is /wpick give <player|self> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
					return true;
				}
				if ((args.length == 1) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("help"))) {
					player.sendMessage(
							ChatColor.AQUA + "Command is /wpick give <player|self> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
					return true;
				}
				if ((args.length == 1) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("reload"))) {
					player.sendMessage(ChatColor.AQUA + "Config reloaded!");
					WeirdPicks.getInstance().LoadConfigValues();
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("xpPick"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe!");
					WeirdPicks.getCommands().doGiveXPPick(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("EPick"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe!");
					WeirdPicks.getCommands().doGiveEPick(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("BPick"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe!");
					WeirdPicks.getCommands().doGiveBPick(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("EBPick"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe!");
					WeirdPicks.getCommands().doGiveEBPick(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("SPick"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe!");
					WeirdPicks.getCommands().doGiveSPick(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (args[1].equalsIgnoreCase("self"))
						&& (args[2].equalsIgnoreCase("LAxe"))) {
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty axe!");
					WeirdPicks.getCommands().doGiveLAxe(player);
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (!args[1].isEmpty())
						&& (args[2].equalsIgnoreCase("EPick"))) {
					String a1 = args[1];
					try {
						Player target = sender.getServer().getPlayer(a1);
						WeirdPicks.getCommands().doGiveEPick(target);
					} catch (Exception e) {
						player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
								+ " does not exist!");
						return false;
					}
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE
							+ a1 + ChatColor.AQUA + "!");
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (!args[1].isEmpty())
						&& (args[2].equalsIgnoreCase("BPick"))) {
					String a1 = args[1];
					try {
						Player target = sender.getServer().getPlayer(a1);
						WeirdPicks.getCommands().doGiveBPick(target);
					} catch (Exception e) {
						player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
								+ " does not exist!");
						return false;
					}
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE
							+ a1 + ChatColor.AQUA + "!");
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (!args[1].isEmpty())
						&& (args[2].equalsIgnoreCase("EBPick"))) {
					String a1 = args[1];
					try {
						Player target = sender.getServer().getPlayer(a1);
						WeirdPicks.getCommands().doGiveEBPick(target);
					} catch (Exception e) {
						player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
								+ " does not exist!");
						return false;
					}
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE
							+ a1 + ChatColor.AQUA + "!");
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (!args[1].isEmpty())
						&& (args[2].equalsIgnoreCase("SPick"))) {
					String a1 = args[1];
					try {
						Player target = sender.getServer().getPlayer(a1);
						WeirdPicks.getCommands().doGiveSPick(target);
					} catch (Exception e) {
						player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
								+ " does not exist!");
						return false;
					}
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE
							+ a1 + ChatColor.AQUA + "!");
					return true;
				}
				if ((args.length == 3) && (cmd.getName().equalsIgnoreCase("wpick"))
						&& (args[0].equalsIgnoreCase("give")) && (!args[1].isEmpty())
						&& (args[2].equalsIgnoreCase("LAxe"))) {
					String a1 = args[1];
					try {
						Player target = sender.getServer().getPlayer(a1);
						WeirdPicks.getCommands().doGiveLAxe(target);
					} catch (Exception e) {
						player.sendMessage(ChatColor.AQUA + "Player: " + ChatColor.DARK_BLUE + a1 + ChatColor.AQUA
								+ " does not exist!");
						return false;
					}
					player.sendMessage(ChatColor.AQUA + "You've summoned a mighty Pickaxe for " + ChatColor.DARK_BLUE
							+ a1 + ChatColor.AQUA + "!");
					return true;
				}
				player.sendMessage(ChatColor.AQUA + "Command is /wpick give <player> <XPPick|EPick|BPick|EBPick|SPick|LAxe>!");
				return false;
			}
			player.sendMessage(ChatColor.RED + "Sorry, you don't have permission to use this command!");
		}
		return false;
	}
}
