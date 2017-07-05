//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package net.doodcraft.oshcon.bukkit.enderpads.util;

import net.doodcraft.oshcon.bukkit.enderpads.EnderPadsPlugin;
import net.doodcraft.oshcon.bukkit.enderpads.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

public class ReflectionUtil {
    public ReflectionUtil() {
    }

    public static void sendActionBar(Player player, String message) {
        if (Compatibility.isSupported(EnderPadsPlugin.version, "1.8.3", "1.11.2")) {
            send18Actionbar(player, message);
        } else if (Compatibility.isSupported(EnderPadsPlugin.version, "1.12", "1.12")) {
            send112Actionbar(player, message);
        } else {
            player.sendMessage(StaticMethods.addColor(message));
        }
    }

    private static void send18Actionbar(Player player, String message) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(new Class[]{getNMSClass("IChatBaseComponent"), Byte.TYPE});
            Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + message + "\"}"});
            Object packet = constructor.newInstance(icbc, (byte) 2);
            Object entityPlayer = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNMSClass("Packet")}).invoke(playerConnection, new Object[]{packet});
        } catch (Exception var7) {
            player.sendMessage(StaticMethods.addColor(message));
            if (Settings.debug.booleanValue()) {
                var7.printStackTrace();
            }
        }

    }

    private static void send112Actionbar(Player player, String message) {
        try {
            Constructor<?> constructor = getNMSClass("PacketPlayOutChat").getConstructor(new Class[]{getNMSClass("IChatBaseComponent"), getNMSClass("ChatMessageType")});
            Object icbc = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[]{String.class}).invoke((Object) null, new Object[]{"{\"text\":\"" + message + "\"}"});
            Object packet = constructor.newInstance(new Object[]{icbc, getNMSClass("ChatMessageType").getEnumConstants()[2]});
            Object entityPlayer = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
            playerConnection.getClass().getMethod("sendPacket", new Class[]{getNMSClass("Packet")}).invoke(playerConnection, new Object[]{packet});
        } catch (Exception var7) {
            player.sendMessage(StaticMethods.addColor(message));
            if (Settings.debug.booleanValue()) {
                var7.printStackTrace();
            }
        }

    }

    private static Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + getVersion() + "." + name);
        } catch (ClassNotFoundException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
