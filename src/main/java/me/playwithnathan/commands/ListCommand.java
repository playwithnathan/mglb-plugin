package me.playwithnathan.commands;

import me.playwithnathan.classes.Board;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ListCommand extends CommandManager {
    public ListCommand(@NotNull Player player) {
        // List command title
        PlayerUtil.message(player, "&f&lAll Boards");

        // Display board ids
        for(Board board : BoardUtil.getBoards())
            PlayerUtil.message(player, board.getId());
    }
}