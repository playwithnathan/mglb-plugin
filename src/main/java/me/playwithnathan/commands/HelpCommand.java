package me.playwithnathan.commands;

import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends CommandManager {
    public HelpCommand(@NotNull Player player) {
        PlayerUtil.message(player, "&f&lMinigame Leaderboard Commands",
                "&e/mglb list &7Displays all leaderboards.",
                "&e/mglb create <id> <table> <column> <title> <format> &7Create a leaderboard.",
                "&e/mglb delete <id> &7Delete a leaderboard.",
                "&e/mglb info <id> &7Get all the config of a leaderboard.",
                "&e/mglb reload &7Reload all leaderboards config."
        );
    }
}