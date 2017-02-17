package com.gmail.thelimeglass.versionControl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.gmail.thelimeglass.Utils.ReflectionUtil;

public class ClientBorderManager {
	
	/**
	 * @author LimeGlass https://github.com/TheLimeGlass
	 */
	
	public static Object getWorldBorder(Player player) {
		try {
			Object nmsPlayer = ReflectionUtil.getHandle(player);
			Object world = nmsPlayer.getClass().getField("world").get(nmsPlayer);
			return world.getClass().getMethod("getWorldBorder").invoke(world);
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getBorderPacket(Player player, String borderAction) {
		try {
			Class<Enum> enumWorldBorderAction = (Class<Enum>) ReflectionUtil.getNMSClass("PacketPlayOutWorldBorder$EnumWorldBorderAction");
			Enum enumType = Enum.valueOf(enumWorldBorderAction, borderAction);
			Class<?> border = ReflectionUtil.getNMSClass("PacketPlayOutWorldBorder");
			Object packet = border.getConstructor(ReflectionUtil.getNMSClass("WorldBorder"), enumWorldBorderAction).newInstance(getWorldBorder(player), enumType);
			return packet;
		} catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setSize(Player player, int size) {
		try {
			Object packet = getBorderPacket(player, "SET_SIZE");
			try {
				Field field = packet.getClass().getDeclaredField("f");
				field.setAccessible(true);
				field.setInt(packet, size);
				field.setAccessible(!field.isAccessible());
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReflectionUtil.sendPacket(player, packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public static void setCenter(Player player, double x, double z) {
		try {
			Object packet = getBorderPacket(player, "SET_CENTER");
			try {
				Field xCoord = packet.getClass().getDeclaredField("c");
				xCoord.setAccessible(true);
				xCoord.set(packet, x);
				xCoord.setAccessible(!xCoord.isAccessible());
				Field zCoord = packet.getClass().getDeclaredField("d");
				zCoord.setAccessible(true);
				zCoord.set(packet, z);
				zCoord.setAccessible(!zCoord.isAccessible());
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReflectionUtil.sendPacket(player, packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public static Location getCenter(Player player) {
		try {
			Object packet = getBorderPacket(player, "SET_CENTER");
			try {
				Field xCoord = packet.getClass().getDeclaredField("c");
				xCoord.setAccessible(true);
				double x = xCoord.getDouble(packet);
				xCoord.setAccessible(!xCoord.isAccessible());
				Field zCoord = packet.getClass().getDeclaredField("d");
				zCoord.setAccessible(true);
				double z = zCoord.getDouble(packet);
				zCoord.setAccessible(!zCoord.isAccessible());
				return new Location(player.getWorld(), x, 0, z);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReflectionUtil.sendPacket(player, packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setWarningBlocks(Player player, int warning) {
		try {
			Object packet = getBorderPacket(player, "SET_WARNING_BLOCKS");
			try {
				Field warn = packet.getClass().getDeclaredField("i");
				warn.setAccessible(true);
				warn.setInt(packet, warning);
				warn.setAccessible(!warn.isAccessible());
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReflectionUtil.sendPacket(player, packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	public static int getWarningBlocks(Player player) {
		try {
			Object packet = getBorderPacket(player, "SET_WARNING_BLOCKS");
			try {
				Field warn = packet.getClass().getDeclaredField("i");
				warn.setAccessible(true);
				int finalWarn = warn.getInt(packet);
				warn.setAccessible(!warn.isAccessible());
				return finalWarn;
			} catch (Exception e) {
				e.printStackTrace();
			}
			ReflectionUtil.sendPacket(player, packet);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return 69;
	}
}