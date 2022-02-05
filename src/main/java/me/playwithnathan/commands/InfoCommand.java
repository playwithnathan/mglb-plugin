package me.playwithnathan.commands;

import me.playwithnathan.classes.Board;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.PlayerUtil;
import me.playwithnathan.util.TextUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class InfoCommand extends CommandManager {
    public InfoCommand(Player player, List<String> args) {
        if(args.size() == 0) {
            PlayerUtil.message(player, "You need to include the board's id.");
            return;
        }

        String id = args.get(0);
        Board board = null;

        for(Board test : BoardUtil.getBoards()) {
            if(test.getId().equals(id)) {
                board = test;
                break;
            }
        }

        if(board == null) {
            PlayerUtil.message(player, "That id is already in use!");
            return;
        }

        // Send info
        PlayerUtil.message(player,
                "&eFile: &7" + board.getFile().getName(),
                "&eId: &7" + board.getId(),
                "&eTable: &7" + board.getTable(),
                "&eColumn: &7" + board.getColumn(),
                "&eTitle: &7" + TextUtil.normalizeColor(board.getTitle()),
                "&eFormat: &7" + board.getFormatType(),
                "&eOrder: &7" + board.getOrder(),
                "&eReload: &7" + board.getReload(),
                "&eEntries: &7" + board.getEntries(),
                "&eWorld: &7" + board.getWorld().getName(),
                "&eX: &7" + board.getX(),
                "&eY: &7" + board.getY(),
                "&eZ: &7" + board.getZ()
        );
    }
}