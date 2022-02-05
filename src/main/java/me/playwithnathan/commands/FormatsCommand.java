package me.playwithnathan.commands;

import me.playwithnathan.classes.Board;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.PlayerUtil;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FormatsCommand extends CommandManager {
    public FormatsCommand(Player player) {
        // List command title
        PlayerUtil.message(player, "&f&lAll Formats");

        // Get all format types
        List<String> formats = new ArrayList<>();
        for(Board board : BoardUtil.getBoards()) {
            if(!formats.contains(board.getFormatType()))
                formats.add(board.getFormatType());
        }

        // Display format types + their format
        for(String format : formats)
            PlayerUtil.message(player, format + ": " + ConfigUtil.getFormat(format));
    }
}