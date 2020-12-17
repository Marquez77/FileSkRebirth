package com.marquez.fileskrebirth.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import ch.njol.skript.registrations.Classes;
import ch.njol.skript.variables.FlatFileStorage;
import ch.njol.skript.variables.Variables;

public class StringUtil {
	
	public static String worldToString(World world) {
		if(world != null) {
			return world.getName();
		}
		return "";
	}
	
	public static World stringToWorld(String world) {
		return Bukkit.getWorld(world);
	}

	public static String blockToString(Block block) {
		if(block != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("block=").append(locationToString(block.getLocation()));
			return sb.toString();
		}
		return "";
	}
	
	public static Block stringToBlock(String block) {
		String loc = block.split("=")[1];
		Location location = stringToLocation(loc);
		World world = location.getWorld();
		Block b = world.getBlockAt(location);
		return b;
	}

	public static String locationToString(Location loc) {
		if(loc != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("world:").append(worldToString(loc.getWorld())).append(",")
			.append("x:").append(loc.getX()).append(",")
			.append("y:").append(loc.getY()).append(",")
			.append("z:").append(loc.getZ()).append(",")
			.append("pitch:").append(loc.getPitch()).append(",")
			.append("yaw:").append(loc.getYaw());
			return sb.toString();
		}
		return "";
	}

	public static Location stringToLocation(String loc) {
		World world = null;
		double x = 0, y = 0, z = 0;
		float pitch = 0, yaw = 0;
		String[] contents = loc.split(",");
		for(String s : contents) {
			String[] splited = s.split(":");
			switch(splited[0]) {
			case "world":
				world = stringToWorld(splited[1]);
				break;
			case "x":
				x = Double.parseDouble(splited[1]);
				break;
			case "y":
				y = Double.parseDouble(splited[1]);
				break;
			case "z":
				z = Double.parseDouble(splited[1]);
				break;
			case "pitch":
				pitch = Float.parseFloat(splited[1]);
				break;
			case "yaw":
				yaw = Float.parseFloat(splited[1]);
				break;
			}
		}
		return new Location(world, x, y, z, yaw, pitch);
	}

	public static String itemToString(ItemStack item) {
		byte[] data = Variables.serialize(item).data;
		try {
			Method method = FlatFileStorage.class.getDeclaredMethod("encode", byte[].class);
			method.setAccessible(true);
			String encoded = (String)method.invoke(null, data);
			return encoded;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch(SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if(item != null) {
//			String itemString = "";
//			String mat = item.getType().toString();
//			String amount = ((Integer) item.getAmount()).toString();
//			Map<Enchantment, Integer> enchants = item.getEnchantments();
//			String fullEnchantmentString = "";
//			String displayName = "";
//			String loreString = "";
//			if(item.hasItemMeta()) {
//				try {
//					displayName = item.getItemMeta().getDisplayName();
//					displayName = displayName.replace(" ", "_");
//				}
//				catch(NullPointerException e) {}
//				try {
//					List<String> lore = item.getItemMeta().getLore();
//					String loreString2 = "";
//					for(int x = 0; x < lore.size(); x ++) {
//						loreString2 = loreString + lore.get(x).replace(" ", "_");
//						if(x != lore.size() - 1) {
//							loreString2 = loreString2 + "|";
//						}
//					}
//					loreString = loreString2;
//				}
//				catch(NullPointerException e) {}
//			}
//			Set<Entry<Enchantment, Integer>> exampleEntry = enchants.entrySet();
//			for(Entry<Enchantment, Integer> e : exampleEntry) {
//				Enchantment ench = e.getKey();
//				String lvl = ((Integer) e.getValue()).toString();
//				String enchName = EnchantmentName.enchNameToFriendlyName(ench.getName());
//				enchName = enchName.replace(" ", "_");
//				fullEnchantmentString = fullEnchantmentString + " " + enchName + ":" + lvl;
//			}
//			itemString = mat + " " + amount;
//			if(displayName != null && !displayName.equals(""))
//				itemString = itemString + " name:" + displayName;
//			if(loreString != null && !loreString.equals(""))
//				itemString = itemString + " lore:" + loreString;
//			if(fullEnchantmentString != null && !fullEnchantmentString.equals(""))
//				itemString = itemString + fullEnchantmentString;
//			return itemString;
//		}
		return "";
	}

	public static ItemStack stringToItem(String item) {
		try {
			Method method = FlatFileStorage.class.getDeclaredMethod("decode", String.class);
			method.setAccessible(true);
			byte[] data = (byte[])method.invoke(null, item);
			return (ItemStack)Classes.deserialize("itemstack", data);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch(SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
//		String[] itemSplit = item.split(" ");
//		List<String> itemWordList = Arrays.asList(itemSplit);
//		String materialName = itemWordList.get(0);
//		Material mat = Material.valueOf(materialName.toUpperCase());
//		int amount = 0;
//		try {
//			amount = Integer.valueOf(itemWordList.get(1));
//		}
//		catch(ArrayIndexOutOfBoundsException e) {
//			amount = 1;
//		}
//		String name = null;
//		for(String word : itemWordList) {
//			if(word.contains("name:")) {
//				String[] nameArray = word.split(":");
//				name = ChatColor.translateAlternateColorCodes('&', nameArray[1]);
//				name = name.replace("_", " ");
//			}
//		}
//		List<String> lore = null;
//		for(String word : itemWordList) {
//			if(word.contains("lore:")) {
//				String[] fullLoreArray = word.split(":");
//				String loreString = ChatColor.translateAlternateColorCodes('&', fullLoreArray[1]);
//				loreString = loreString.replace("_", " ");
//				String[] loreArray = loreString.split("\\|");
//				lore = Arrays.asList(loreArray);
//			}
//		}
//		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
//		for(String word : itemWordList) {
//			if(word.contains("protection:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, lvl);
//			}
//			if(word.contains("fire_protection:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.PROTECTION_FIRE, lvl);
//			}
//			if(word.contains("feather_falling:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.PROTECTION_FALL, lvl);
//			}
//			if(word.contains("blast_protection:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.PROTECTION_EXPLOSIONS, lvl);
//			}
//			if(word.contains("projectile_protection:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.PROTECTION_PROJECTILE, lvl);
//			}
//			if(word.contains("respiration:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.OXYGEN, lvl);
//			}
//			if(word.contains("aqua_affinity:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.WATER_WORKER, lvl);
//			}
//			if(word.contains("thorns:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.THORNS, lvl);
//			}
//			if(word.contains("sharpness:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.DAMAGE_ALL, lvl);
//			}
//			if(word.contains("smite:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.DAMAGE_UNDEAD, lvl);
//			}
//			if(word.contains("bane_of_arthropods:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.DAMAGE_ARTHROPODS, lvl);
//			}
//			if(word.contains("knockback:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.KNOCKBACK, lvl);
//			}
//			if(word.contains("fire_aspect:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.FIRE_ASPECT, lvl);
//			}
//			if(word.contains("looting:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.LOOT_BONUS_MOBS, lvl);
//			}
//			if(word.contains("efficiency:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.DIG_SPEED, lvl);
//			}
//			if(word.contains("silk_touch:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.SILK_TOUCH, lvl);
//			}
//			if(word.contains("unbreaking:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.DURABILITY, lvl);
//			}
//			if(word.contains("fortune:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.LOOT_BONUS_BLOCKS, lvl);
//			}
//			if(word.contains("power:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.ARROW_DAMAGE, lvl);
//			}
//			if(word.contains("punch:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.ARROW_KNOCKBACK, lvl);
//			}
//			if(word.contains("flame:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.ARROW_FIRE, lvl);
//			}
//			if(word.contains("infinity:")) {
//				String[] fullArray = word.split(":");
//				int lvl = Integer.valueOf(fullArray[1]);
//				enchantments.put(Enchantment.ARROW_INFINITE, lvl);
//			}
//		}
//		ItemStack itemStack = new ItemStack(mat, amount);
//		ItemMeta itemMeta = itemStack.getItemMeta();
//		if(name != null)
//			itemMeta.setDisplayName(name);
//		if(lore != null)
//			itemMeta.setLore(lore);
//		itemStack.setItemMeta(itemMeta);
//		itemStack.addUnsafeEnchantments(enchantments);
//		return itemStack;
	}
}