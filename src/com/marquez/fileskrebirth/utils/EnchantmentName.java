
package com.marquez.fileskrebirth.utils;

public class EnchantmentName {
	public static String enchNameToFriendlyName(String enchName) {
		switch (enchName) {
		case "PROTECTION_ENVIRONMENTAL":
			return "protection";
		case "PROTECTION_FIRE":
			return "fire protection";
		case "PROTECTION_FALL":
			return "feather falling";
		case "PROTECTION_EXPLOSIONS":
			return "blast protection";
		case "PROTECTION_PROJECTILE":
			return "projectile protection";
		case "OXYGEN":
			return "respiration";
		case "WATER_WORKER":
			return "aqua affinity";
		case "THORNS":
			return "thorns";
		case "DAMAGE_ALL":
			return "sharpness";
		case "DAMAGE_UNDEAD":
			return "smite";
		case "DAMAGE_ARTHROPODS":
			return "bane of arthropods";
		case "KNOCKBACK":
			return "knockback";
		case "FIRE_ASPECT":
			return "fire aspect";
		case "LOOT_BONUS_MOBS":
			return "looting";
		case "DIG_SPEED":
			return "efficiency";
		case "SILK_TOUCH":
			return "silk touch";
		case "DURABILITY":
			return "unbreaking";
		case "ARROW_DAMAGE":
			return "power";
		case "ARROW_KNOCKBACK":
			return "punch";
		case "ARROW_FIRE":
			return "flame";
		case "ARROW_INFINITE":
			return "infinity";
		}
		return "";
	}
}