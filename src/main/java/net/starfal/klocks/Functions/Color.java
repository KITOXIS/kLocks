package net.starfal.klocks.Functions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

public class Color {
    public static String oldformat(String miniMessage) {
        if (miniMessage == null) {
            return null;
        }
        MiniMessage mm = MiniMessage.miniMessage();
        Component message = mm.deserialize(miniMessage);
        String legacyMessage = LegacyComponentSerializer.legacyAmpersand().serialize(message);
        return ChatColor.translateAlternateColorCodes('&', legacyMessage);
    }
    public static Component format(String miniMessage) {
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(miniMessage);
    }
}
