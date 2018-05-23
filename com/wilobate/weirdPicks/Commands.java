package com.wilobate.weirdPicks;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands {
	public void doGiveEPick(Player p) {
		p.getInventory().addItem(new ItemStack[] { WeirdPicks.getInstance().ePick });
		p.sendMessage(ChatColor.AQUA + "Added mighty Pickaxe!");
	}

	public void doGiveBPick(Player p) {
		p.getInventory().addItem(new ItemStack[] { WeirdPicks.getInstance().bPick });
		p.sendMessage(ChatColor.AQUA + "Added mighty Pickaxe!");
	}

	public void doGiveEBPick(Player p) {
		p.getInventory().addItem(new ItemStack[] { WeirdPicks.getInstance().ebPick });
		p.sendMessage(ChatColor.AQUA + "Added mighty Pickaxe!");
	}

	public void doGiveSPick(Player p) {
		p.getInventory().addItem(new ItemStack[] { WeirdPicks.getInstance().sPick });
		p.sendMessage(ChatColor.AQUA + "Added mighty Pickaxe!");
	}

	public void doGiveLAxe(Player p) {
		p.getInventory().addItem(new ItemStack[] { WeirdPicks.getInstance().lAxe });
		p.sendMessage(ChatColor.AQUA + "Added mighty Axe!");
	}

	public boolean isPickaxe(ItemStack item) {
		if (item.getType() == WeirdPicks.getInstance().PICKAXE_MATERIAL) {
			return true;
		}
		return false;
	}

	public boolean isEPickaxe(Player p, ItemStack item) {
		if (!WeirdPicks.getInstance().ePickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ePickLoreSt))) {
			return true;
		}
		return false;
	}

	public boolean isBPickaxe(Player p, ItemStack item) {
		if (!WeirdPicks.getInstance().bPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().bPickLoreSt))) {
			return true;
		}
		return false;
	}

	public boolean isEBPickaxe(Player p, ItemStack item) {
		if (!WeirdPicks.getInstance().ebPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ebPickLoreSt))) {
			return true;
		}
		return false;
	}

	public boolean isSPickaxe(Player p, ItemStack item) {
		if (!WeirdPicks.getInstance().sPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().sPickLoreSt))) {
			return true;
		}
		return false;
	}

	public boolean isLumberAxe(Player p, ItemStack item) {
		if (!WeirdPicks.getInstance().lAxeEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().lAxeLoreSt))) {
			return true;
		}
		return false;
	}

	public boolean isFortEPick(ItemStack item) {
		if (!WeirdPicks.getInstance().ePickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ePickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return true;
		}
		return false;
	}

	public boolean isSilkEPick(ItemStack item) {
		if (!WeirdPicks.getInstance().ePickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ePickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) {
			return true;
		}
		return false;
	}

	public int FortEPickLvL(ItemStack item) {
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ePickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return item.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return 0;
	}

	public boolean isFortBPick(ItemStack item) {
		if (!WeirdPicks.getInstance().bPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().bPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return true;
		}
		return false;
	}

	public boolean isSilkBPick(ItemStack item) {
		if (!WeirdPicks.getInstance().bPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().bPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) {
			return true;
		}
		return false;
	}

	public int FortBPickLvL(ItemStack item) {
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().bPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return item.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return 0;
	}

	public boolean isFortEBPick(ItemStack item) {
		if (!WeirdPicks.getInstance().ebPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ebPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return true;
		}
		return false;
	}

	public boolean isSilkEBPick(ItemStack item) {
		if (!WeirdPicks.getInstance().ebPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ebPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) {
			return true;
		}
		return false;
	}

	public int FortEBPickLvL(ItemStack item) {
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().ebPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return item.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return 0;
	}

	public boolean isFortSPick(ItemStack item) {
		if (!WeirdPicks.getInstance().sPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().sPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return true;
		}
		return false;
	}

	public boolean isSilkSPick(ItemStack item) {
		if (!WeirdPicks.getInstance().sPickEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().sPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH))) {
			return true;
		}
		return false;
	}

	public int FortSPickLvL(ItemStack item) {
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().sPickLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return item.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return 0;
	}

	public boolean isFortLAxe(ItemStack item) {
		if (!WeirdPicks.getInstance().lAxeEnabled) {
			return false;
		}
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().lAxeLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return true;
		}
		return false;
	}

	public int FortLAxeLvL(ItemStack item) {
		if ((item.getItemMeta().getLore() != null)
				&& (item.getItemMeta().getLore().contains(WeirdPicks.getInstance().lAxeLoreSt))
				&& (item.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_BLOCKS))) {
			return item.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS);
		}
		return 0;
	}

	public boolean isEZBlocks() {
		if (WeirdPicks.getInstance().getServer().getPluginManager().getPlugin("EZBlocks") != null) {
			return true;
		}
		return false;
	}

	public int getNumDrops(int i) {
		if (i <= 0) {
			return 1;
		}

		/*
		 * int j = 2; int k = 35 - i * 5; if (i > 100) { j = (int)(j + Math.round(i -
		 * 40.0D)); } if (i > 50) { k = 1; } else if (i > 25) { k = 2; } else if (i >
		 * 15) { k = 3; } else if (i > 8) { k = 4; } if (k < 5) { i = 100; } int l = k;
		 * int i1 = new Random().nextInt(100) + 1; while (l <= k * (i + 1)) { if (i1 <=
		 * l) { return j; } l += k; j++; }
		 */

		int drop = ThreadLocalRandom.current().nextInt(1, i + 1);

		return drop;
	}

	public boolean getNatural() {
		return WeirdPicks.getInstance().natural;
	}

	public int getRadius() {
		return WeirdPicks.getInstance().radius;
	}

	public int getBRadius() {
		return WeirdPicks.getInstance().radiusB;
	}
}
