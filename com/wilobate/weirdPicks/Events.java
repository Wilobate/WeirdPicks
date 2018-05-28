package com.wilobate.weirdPicks;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.clip.ezblocks.EZBlocks;

public class Events implements Listener {

	private ArrayList<Object> PlayerPlaced = new ArrayList<Object>();

	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt) {
		Entity damaged = evt.getEntity();
		if ((evt.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) && ((damaged instanceof Player))) {
			evt.setCancelled(true);
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockBroken(BlockBreakEvent evt) {
		Player player = evt.getPlayer();
		Block block = evt.getBlock();
		ItemStack item;

		WorldGuardPlugin worldGuard = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		Location SMloc = block.getLocation();
		double smX = SMloc.getBlockX();
		double smY = SMloc.getBlockY();
		double smZ = SMloc.getBlockZ();
		Location locWorldGuard = new Location(block.getWorld(), smX, smY, smZ);
		RegionContainer container = worldGuard.getRegionContainer();
		RegionManager regions = container.get(locWorldGuard.getWorld());
		ApplicableRegionSet setWorldGuard = regions.getApplicableRegions(BukkitUtil.toVector(locWorldGuard));
		LocalPlayer localPlayer = worldGuard.wrapPlayer(player);
		if ((setWorldGuard.testState(localPlayer, DefaultFlag.BLOCK_BREAK))
				&& (player.hasPermission("wpick.explode"))) {
			try {
				item = player.getInventory().getItemInMainHand();
			} catch (NoSuchMethodError e) {
				item = player.getItemInHand();
			}
			if (!(item == null || item.getType() == Material.AIR)) {
				if (WeirdPicks.getCommands().isBPickaxe(player, item)
						|| WeirdPicks.getCommands().isEBPickaxe(player, item)
						|| WeirdPicks.getCommands().isEPickaxe(player, item)
						|| WeirdPicks.getCommands().isSPickaxe(player, item)) {
					try {
						if (!item.getItemMeta().isUnbreakable()) {
							short durability = item.getDurability();
							item.setDurability((short) (durability + 1));
							player.updateInventory();
							if (durability >= 1561) {
								player.getInventory().remove(item);
								player.updateInventory();
							}
						}
					} catch (NoSuchMethodError e) {
						if (!item.getItemMeta().spigot().isUnbreakable()) {
							short durability = item.getDurability();
							item.setDurability((short) (durability + 1));
							player.updateInventory();
							if (durability >= 1561) {
								player.getInventory().remove(item);
								player.updateInventory();
							}
						}
					}
				}
			} else {
				return;
			}
			if (PlayerPlaced.contains(evt.getBlock().getLocation()) && !WeirdPicks.getCommands().isLumberAxe(player, item)) {
				PlayerPlaced.remove(evt.getBlock().getLocation());
				return;
			} else {
				if ((WeirdPicks.getCommands().isLumberAxe(player, item)) && ((evt.getBlock().getType() == Material.LOG)
						|| (evt.getBlock().getType() == Material.LOG_2))) {
					addBlockEZBlock(1, player, block);
					breakChain(evt.getBlock().getWorld(), player, block, evt.getBlock().getX(), evt.getBlock().getY(),
							evt.getBlock().getZ());
				}
				if (WeirdPicks.getCommands().isSPickaxe(player, item)) {
					if (evt.getBlock().getType() == Material.IRON_ORE) {
						if (WeirdPicks.getCommands().isFortSPick(item)) {
							int l = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortSPickLvL(item)).intValue());
							if (l != -1) {
								evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
										new ItemStack(Material.IRON_INGOT, l));
							}
						}
						evt.getBlock().setType(Material.AIR);
						if (!WeirdPicks.getCommands().isFortSPick(item)) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.IRON_INGOT));
						}
					}
					if (evt.getBlock().getType() == Material.GOLD_ORE) {
						if (WeirdPicks.getCommands().isFortSPick(item)) {
							int i1 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortSPickLvL(item)).intValue());
							if (i1 != -1) {
								evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
										new ItemStack(Material.GOLD_INGOT, i1));
							}
						}
						evt.getBlock().setType(Material.AIR);
						if (!WeirdPicks.getCommands().isFortSPick(item)) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.GOLD_INGOT));
						}
					}
					if ((evt.getBlock().getType() == Material.COBBLESTONE)
							|| (evt.getBlock().getType() == Material.STONE)) {
						if (WeirdPicks.getCommands().isFortSPick(item)) {
							int j1 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortSPickLvL(item)).intValue());
							if (j1 != -1) {
								evt.getBlock().setType(Material.AIR);
								evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
										new ItemStack(Material.STONE, j1));
							}
						}
						evt.getBlock().setType(Material.AIR);
						if (!WeirdPicks.getCommands().isFortSPick(item)) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.STONE));
						}
					}
					evt.getBlock().breakNaturally();
				}
				if (WeirdPicks.getCommands().isEPickaxe(player, item)) {
					Location loc = block.getLocation();
					double X = loc.getBlockX();
					double Y = loc.getBlockY();
					double Z = loc.getBlockZ();
					if (!WeirdPicks.getCommands().getNatural()) {
						int radius = WeirdPicks.getCommands().getRadius() - 2;
						int blocksBroken = 0;
						player.getWorld().createExplosion(loc, 0.0F);
						double minX = X - radius;
						double maxX = X + radius + 1.0D;
						double minY = Y - radius;
						double maxY = Y + radius + 1.0D;
						double minZ = Z - radius;
						double maxZ = Z + radius + 1.0D;
						for (double x = minX; x < maxX; x += 1.0D) {
							for (double y = minY; y < maxY; y += 1.0D) {
								for (double z = minZ; z < maxZ; z += 1.0D) {
									Location location = new Location(block.getWorld(), x, y, z);
									
									Vector v = new Vector(location.getX(), location.getBlockY(), location.getZ());
									ApplicableRegionSet set = worldGuard.getRegionManager(loc.getWorld())
											.getApplicableRegions(v);
									if ((set.testState(localPlayer, DefaultFlag.BLOCK_BREAK))
											&& (location.getBlock().getType() != Material.BEDROCK)
											&& (location.getBlock().getType() != Material.AIR)) {
										blocksBroken = blocksBroken + 1;

										if (location.getBlock().getType() == Material.COAL_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.COAL_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COAL, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.COAL_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.COAL));
											}
										}
										if (location.getBlock().getType() == Material.IRON_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.IRON_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.IRON_ORE, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.IRON_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.IRON_ORE));
											}
										}
										if (location.getBlock().getType() == Material.GOLD_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.GOLD_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.GOLD_ORE, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.GOLD_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.GOLD_ORE));
											}
										}
										if (location.getBlock().getType() == Material.REDSTONE_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE));
											}
										}
										if (location.getBlock().getType() == Material.LAPIS_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.LAPIS_ORE, j2));
													} else {
														ItemStack lapis = new ItemStack(Material.INK_SACK,
																ThreadLocalRandom.current().nextInt(1, 4) + j2,
																(short) 4);
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(), lapis);
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.LAPIS_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld()
														.dropItemNaturally(location.getBlock().getLocation(),
																new ItemStack(Material.INK_SACK,
																		ThreadLocalRandom.current().nextInt(1, 4),
																		(short) 4));
											}
										}
										if (location.getBlock().getType() == Material.DIAMOND_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND));
											}
										}
										if (location.getBlock().getType() == Material.EMERALD_ORE) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.EMERALD_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.EMERALD, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.EMERALD_ORE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.EMERALD));
											}
										}
										if ((location.getBlock().getType() == Material.COBBLESTONE)
												|| (location.getBlock().getType() == Material.STONE)) {
											if (WeirdPicks.getCommands().isFortEPick(item)) {
												int k2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEPickLvL(item))
																.intValue());
												if (k2 != -1) {
													if (WeirdPicks.getCommands().isSilkEPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																evt.getBlock().getLocation(),
																new ItemStack(Material.STONE, k2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COBBLESTONE, k2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEPick(item))
													&& (!WeirdPicks.getCommands().isFortEPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.STONE));
											}
											location.getBlock().setType(Material.AIR);
											if ((!WeirdPicks.getCommands().isFortEPick(item))
													&& (!WeirdPicks.getCommands().isSilkEPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.COBBLESTONE));
											}
										}
										if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
											location.getBlock().breakNaturally();
										}
									}
								}
							}
						}
						addBlockEZBlock(blocksBroken - 1, player, block);
					} else {
						player.getWorld().createExplosion(loc, WeirdPicks.getCommands().getRadius());
					}
				}
				if (WeirdPicks.getCommands().isBPickaxe(player, item)) {
					int Bradius = WeirdPicks.getCommands().getBRadius() - 2;
					Location Bloc = block.getLocation();
					double BX = Bloc.getBlockX();
					double BY = Bloc.getBlockY();
					double BZ = Bloc.getBlockZ();

					boolean fEmerald = false;
					boolean fEmeraldb = false;
					boolean fDiamond = false;
					boolean fDiamondb = false;
					boolean fGold = false;
					boolean fGoldb = false;
					boolean fIron = false;
					boolean fIronb = false;
					boolean fLapis = false;
					boolean fLapisb = false;
					boolean fRedstone = false;
					boolean fRedstoneb = false;
					boolean fCoal = false;
					boolean fCoalb = false;

					Location locationB = new Location(block.getWorld(), BX, BY, BZ);
					for (int x = -Bradius; x <= Bradius; x++) {
						for (int y = -Bradius; y <= Bradius; y++) {
							for (int z = -Bradius; z <= Bradius; z++) {
								double Ax = locationB.getBlockX() + x;
								double Ay = locationB.getBlockY() + y;
								double Az = locationB.getBlockZ() + z;
								Location loc = new Location(block.getWorld(), Ax, Ay, Az);
								if (!PlayerPlaced.contains(loc)) {
									if (block.getRelative(x, y, z).getType() == Material.EMERALD_ORE) {
										fEmerald = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.EMERALD_BLOCK) {
										fEmeraldb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.DIAMOND_ORE) {
										fDiamond = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.DIAMOND_BLOCK) {
										fDiamondb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.GOLD_ORE) {
										fGold = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.GOLD_BLOCK) {
										fGoldb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.IRON_ORE) {
										fIron = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.IRON_BLOCK) {
										fIronb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.LAPIS_ORE) {
										fLapis = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.LAPIS_BLOCK) {
										fLapisb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.REDSTONE_ORE) {
										fRedstone = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.REDSTONE_BLOCK) {
										fRedstoneb = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.COAL_ORE) {
										fCoal = true;
									}
									if (block.getRelative(x, y, z).getType() == Material.COAL_BLOCK) {
										fCoalb = true;
									}
								}
							}
						}
					}
					if (fEmeraldb) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_BLOCK, j2));
								}
							}
						}
						locationB.getBlock().setType(Material.AIR);
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.EMERALD_BLOCK));
						}
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.EMERALD_BLOCK));
						}
					}
					if (fEmerald) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.EMERALD_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.EMERALD, j2));
								}
							}
						}
						locationB.getBlock().setType(Material.AIR);
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.EMERALD_ORE));
						}
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.EMERALD));
						}
					}
					if ((fDiamondb) && (!fEmeraldb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.DIAMOND_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.DIAMOND_BLOCK));
						}
					}
					if ((fDiamond) && (!fEmerald)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.DIAMOND_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.DIAMOND, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.DIAMOND_ORE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.DIAMOND));
						}
					}
					if ((fGoldb) && (!fEmeraldb) && (!fDiamondb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.GOLD_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.GOLD_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.GOLD_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.GOLD_BLOCK));
						}
					}
					if ((fGold) && (!fEmerald) && (!fDiamond)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.GOLD_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.GOLD_ORE, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.GOLD_ORE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.GOLD_ORE));
						}
					}
					if ((fIronb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.IRON_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.IRON_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.IRON_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.IRON_BLOCK));
						}
					}
					if ((fIron) && (!fEmerald) && (!fDiamond) && (!fGold)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.IRON_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.IRON_ORE, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.IRON_ORE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.IRON_ORE));
						}
					}
					if ((fLapisb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.LAPIS_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.LAPIS_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.LAPIS_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.LAPIS_BLOCK));
						}
					}
					if ((fLapis) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.LAPIS_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.INK_SACK,
													ThreadLocalRandom.current().nextInt(1, 4), (short) 4));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.LAPIS_ORE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.INK_SACK, ThreadLocalRandom.current().nextInt(1, 4),
											(short) 4));
						}
					}
					if ((fRedstoneb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb) && (!fLapisb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.REDSTONE_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.REDSTONE_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.REDSTONE_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.REDSTONE_BLOCK));
						}
					}
					if ((fRedstone) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron) && (!fLapis)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(),
											new ItemStack(Material.REDSTONE_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.REDSTONE, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.REDSTONE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.REDSTONE));
						}
					}
					if ((fCoalb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb) && (!fLapisb)
							&& (!fRedstoneb)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.COAL_BLOCK, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.COAL_BLOCK, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.COAL_BLOCK));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.COAL_BLOCK));
						}
					}
					if ((fCoal) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron) && (!fLapis) && (!fRedstone)) {
						if (WeirdPicks.getCommands().isFortBPick(item)) {
							int j2 = WeirdPicks.getCommands().getNumDrops(
									Integer.valueOf(WeirdPicks.getCommands().FortBPickLvL(item)).intValue());
							if (j2 != -1) {
								if (WeirdPicks.getCommands().isSilkBPick(item)) {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.COAL_ORE, j2));
								} else {
									locationB.getBlock().getWorld().dropItemNaturally(
											locationB.getBlock().getLocation(), new ItemStack(Material.COAL, j2));
								}
							}
						}
						if ((WeirdPicks.getCommands().isSilkBPick(item))
								&& (!WeirdPicks.getCommands().isFortBPick(item))) {
							evt.getBlock().getWorld().dropItemNaturally(evt.getBlock().getLocation(),
									new ItemStack(Material.COAL_ORE));
						}
						locationB.getBlock().setType(Material.AIR);
						if ((!WeirdPicks.getCommands().isFortBPick(item))
								&& (!WeirdPicks.getCommands().isSilkBPick(item))) {
							locationB.getBlock().getWorld().dropItemNaturally(locationB.getBlock().getLocation(),
									new ItemStack(Material.COAL));
						}
					}
					locationB.getBlock().breakNaturally();
				}
				if (WeirdPicks.getCommands().isEBPickaxe(player, item)) {
					boolean fEmerald = false;
					boolean fEmeraldb = false;
					boolean fDiamond = false;
					boolean fDiamondb = false;
					boolean fGold = false;
					boolean fGoldb = false;
					boolean fIron = false;
					boolean fIronb = false;
					boolean fLapis = false;
					boolean fLapisb = false;
					boolean fRedstone = false;
					boolean fRedstoneb = false;
					boolean fCoal = false;
					boolean fCoalb = false;

					Location loc = block.getLocation();
					double X = loc.getBlockX();
					double Y = loc.getBlockY();
					double Z = loc.getBlockZ();
					if (!WeirdPicks.getCommands().getNatural()) {
						int radius = WeirdPicks.getCommands().getRadius() - 2;
						int blocksBroken = 0;
						player.getWorld().createExplosion(loc, 0.0F);
						double minX = X - radius;
						double maxX = X + radius + 1.0D;
						double minY = Y - radius;
						double maxY = Y + radius + 1.0D;
						double minZ = Z - radius;
						double maxZ = Z + radius + 1.0D;
						for (double x = minX; x < maxX; x += 1.0D) {
							for (double y = minY; y < maxY; y += 1.0D) {
								for (double z = minZ; z < maxZ; z += 1.0D) {
									Location location = new Location(block.getWorld(), x, y, z);

									Vector v = new Vector(location.getX(), location.getBlockY(), location.getZ());
									ApplicableRegionSet set = worldGuard.getRegionManager(loc.getWorld())
											.getApplicableRegions(v);
									if ((set.testState(localPlayer, DefaultFlag.BLOCK_BREAK))
											&& (location.getBlock().getType() != Material.BEDROCK)
											&& (location.getBlock().getType() != Material.AIR)) {
										blocksBroken = blocksBroken + 1;
										int Bradius = WeirdPicks.getCommands().getBRadius() - 2;
										for (int Ax = -Bradius; Ax <= Bradius; Ax++) {
											for (int Ay = -Bradius; Ay <= Bradius; Ay++) {
												for (int Az = -Bradius; Az <= Bradius; Az++) {
													int Bx = block.getRelative(Ax, Ay, Az).getX();
													int By = block.getRelative(Ax, Ay, Az).getY();
													int Bz = block.getRelative(Ax, Ay, Az).getZ();
													Location locB = new Location(block.getWorld(), Bx, By, Bz);
													Vector v1 = new Vector(locB.getX(), locB.getBlockY(), locB.getZ());
													ApplicableRegionSet set1 = worldGuard.getRegionManager(locB.getWorld()).getApplicableRegions(v1);
													if ((set1.testState(localPlayer, DefaultFlag.BLOCK_BREAK) )) {
														if (!PlayerPlaced.contains(locB)) {
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.EMERALD_ORE) {
																fEmerald = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.EMERALD_BLOCK) {
																fEmeraldb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.DIAMOND_ORE) {
																fDiamond = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.DIAMOND_BLOCK) {
																fDiamondb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.GOLD_ORE) {
																fGold = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.GOLD_BLOCK) {
																fGoldb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.IRON_ORE) {
																fIron = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.IRON_BLOCK) {
																fIronb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.LAPIS_ORE) {
																fLapis = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.LAPIS_BLOCK) {
																fLapisb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.REDSTONE_ORE) {
																fRedstone = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.REDSTONE_BLOCK) {
																fRedstoneb = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.COAL_ORE) {
																fCoal = true;
															}
															if (block.getRelative(Ax, Ay, Az)
																	.getType() == Material.COAL_BLOCK) {
																fCoalb = true;
															}
														}
													}
												}
											}
										}
										if (fEmeraldb) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.EMERALD_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.EMERALD_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.EMERALD_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.EMERALD_BLOCK));
											}
										}
										if (fEmerald) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.EMERALD_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.EMERALD, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.EMERALD_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.EMERALD));
											}
										}
										if ((fDiamondb) && (!fEmeraldb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND_BLOCK));
											}
										}
										if ((fDiamond) && (!fEmerald)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.DIAMOND, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.DIAMOND));
											}
										}
										if ((fGoldb) && (!fEmeraldb) && (!fDiamondb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.GOLD_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.GOLD_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.GOLD_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.GOLD_BLOCK));
											}
										}
										if ((fGold) && (!fEmerald) && (!fDiamond)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.GOLD_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.GOLD_ORE, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.GOLD_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.GOLD_ORE));
											}
										}
										if ((fIronb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.IRON_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.IRON_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.IRON_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.IRON_BLOCK));
											}
										}
										if ((fIron) && (!fEmerald) && (!fDiamond) && (!fGold)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.IRON_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.IRON_ORE, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.IRON_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.IRON_ORE));
											}
										}
										if ((fLapisb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.LAPIS_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.LAPIS_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.LAPIS_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.LAPIS_BLOCK));
											}
										}
										if ((fLapis) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.LAPIS_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.INK_SACK,
																		ThreadLocalRandom.current().nextInt(1, 4) + j2,
																		(short) 4));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.LAPIS_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.INK_SACK,
																ThreadLocalRandom.current().nextInt(1, 4), (short) 4));
											}
										}
										if ((fRedstoneb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb)
												&& (!fLapisb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE_BLOCK));
											}
										}
										if ((fRedstone) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron)
												&& (!fLapis)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.REDSTONE,
																		ThreadLocalRandom.current().nextInt(1, 3)
																				+ j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.REDSTONE,
																ThreadLocalRandom.current().nextInt(1, 3)));
											}
										}
										if ((fCoalb) && (!fEmeraldb) && (!fDiamondb) && (!fGoldb) && (!fIronb)
												&& (!fLapisb) && (!fRedstoneb)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COAL_BLOCK, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COAL_BLOCK, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(),
														new ItemStack(Material.COAL_BLOCK));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.COAL_BLOCK));
											}
										}
										if ((fCoal) && (!fEmerald) && (!fDiamond) && (!fGold) && (!fIron) && (!fLapis)
												&& (!fRedstone)) {
											if (WeirdPicks.getCommands().isFortEBPick(item)) {
												int j2 = WeirdPicks.getCommands()
														.getNumDrops(Integer
																.valueOf(WeirdPicks.getCommands().FortEBPickLvL(item))
																.intValue());
												if (j2 != -1) {
													if (WeirdPicks.getCommands().isSilkEBPick(item)) {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COAL_ORE, j2));
													} else {
														location.getBlock().getWorld().dropItemNaturally(
																location.getBlock().getLocation(),
																new ItemStack(Material.COAL, j2));
													}
												}
											}
											if ((WeirdPicks.getCommands().isSilkEBPick(item))
													&& (!WeirdPicks.getCommands().isFortEBPick(item))) {
												evt.getBlock().getWorld().dropItemNaturally(
														evt.getBlock().getLocation(), new ItemStack(Material.COAL_ORE));
											}
											if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
												location.getBlock().setType(Material.AIR);
											}
											if ((!WeirdPicks.getCommands().isFortEBPick(item))
													&& (!WeirdPicks.getCommands().isSilkEBPick(item))) {
												location.getBlock().getWorld().dropItemNaturally(
														location.getBlock().getLocation(),
														new ItemStack(Material.COAL));
											}
										}
										if (set.testState(localPlayer, DefaultFlag.BLOCK_BREAK)) {
											location.getBlock().breakNaturally();
										}
									}
								}
							}
						}
						addBlockEZBlock(blocksBroken - 1, player, block);
					}
				}
			}
		}
	}

	//Storing the player placed block so the pickaxes can check if the player placed block
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		Block block = evt.getBlockPlaced();
		Location loc = block.getLocation();
		double x = (int) loc.getBlockX();
		double y = (int) loc.getBlockY();
		double z = (int) loc.getBlockZ();
		Location locB = new Location(block.getWorld(), x, y, z);
		PlayerPlaced.add(locB);
	}

	public int blocksBroken = 1;

	//LAxe searching up for more logs
	//TODO Might make it search all directions so it will get the entire tree in some cases, maybe a config option if this is added
	public void breakChain(World world, Player player, Block block, int i, int j, int k) {
		world.getBlockAt(i, j, k).breakNaturally();
		if (world.getBlockAt(i, j + 1, k).getType() == Material.LOG
				|| world.getBlockAt(i, j + 1, k).getType() == Material.LOG_2) {
			breakChain(world, player, block, i, j + 1, k);
			addBlockEZBlock(blocksBroken, player, block);
		}
	}

	//Adds to the blocks mined count in EZBlocks plugin. The axe adds one less, but that is the correct amount it should add, trust
	public void addBlockEZBlock(int blocksBroken, Player player, Block block) {
		//Loops and adds 1 block to the EZBlocks count one by one
		while (blocksBroken > 0) {
			if (WeirdPicks.getInstance().EZBlocksHere) {
				EZBlocks ezblocks = (EZBlocks) WeirdPicks.getInstance().getServer().getPluginManager()
						.getPlugin("EZBlocks");
				ezblocks.getBreakHandler().handleBlockBreakEvent(player, block);
			}
			blocksBroken = blocksBroken - 1;
		}
	}
}
