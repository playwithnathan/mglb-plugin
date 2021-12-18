package me.playwithnathan.commands;

import me.playwithnathan.Main;
import me.playwithnathan.classes.Board;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeleteCommand extends CommandManager {
    public DeleteCommand(@NotNull Player player, List<String> args) {
        final ConfigUtil configUtil = Main.getConfigUtil();
        final Permission perms = Main.getPermissions();

        // Must have "admin" perms
        if(!perms.has(player, configUtil.getPerm("admin"))) {
            PlayerUtil.helpMessage(player);
            return;
        }

        // Board id arg
        if(args.size() == 0) {
            PlayerUtil.message(player, "You need to include the board's id.");
            return;
        }

        for(Board board : BoardUtil.getBoards()) {
            // Match
             if(args.get(0).equalsIgnoreCase(board.getId())) {
                 BoardUtil.deleteBoard(board);
                return;
            }
        }

    }
}