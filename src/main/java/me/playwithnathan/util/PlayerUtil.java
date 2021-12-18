package me.playwithnathan.util;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PlayerUtil {

    /**
     * Send a message to a player
     * @param player the player you want to message
     * @param messages the message
     */
    public static void message(@NotNull Player player, @NotNull String... messages) {
        player.sendMessage(TextUtil.color("&8➤ &7" + messages[0]));

        // Only one message
        if(messages.length == 1) return;

        // Multiple messages
        messages = Arrays.copyOfRange(messages, 1, messages.length);
        for(String msg : messages) player.sendMessage(TextUtil.color("&8➤ &7" + msg));
    }

    public static void helpMessage(@NotNull Player player) {
        message(player, "Invalid Command.", "Type &e/mglb help &7for help.");
    }

}
