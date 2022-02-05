package me.playwithnathan.commands;

import me.playwithnathan.Main;
import me.playwithnathan.classes.Board;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.Perms;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class CreateCommand {
    public CreateCommand(Player player, List<String> args) {
        final Permission perms = Main.getPermissions();

        if(!perms.has(player, Perms.ADMIN.getPerm())) {
            PlayerUtil.helpMessage(player);
            return;
        }

        // Id
        if(args.size() == 0) {
            PlayerUtil.message(player, "You need to include the board's id.");
            return;
        }

        for(Board board : BoardUtil.getBoards()) {
            if(board.getId().equals(args.get(0))) {
                PlayerUtil.message(player, "That id is already in use!");
                return;
            }
        }

        // Create new board
        Board board = new Board(args.get(0));

        // Table
        if(args.size() == 1) {
            PlayerUtil.message(player, "You need to include the board's table.");
            return;
        }
        board.setTable(args.get(1));

        // Column
        if(args.size() == 2) {
            PlayerUtil.message(player, "You need to include the board's column.", "This is the column of the statistic you want to display.");
            return;
        }
        board.setColumn(args.get(2));

        // Title
        if(args.size() == 3) {
            PlayerUtil.message(player, "You need to include the board's title.", "You can use &e%s &7in the title to create a space.");
            return;
        }
        board.setTitle(args.get(3).replace("%s", " "));

        // Format
        if(args.size() == 4) {
            PlayerUtil.message(player, "You need to include the board's format.", "Type &e/mglb formats &7to see all accepted values");
            return;
        }
        board.setFormatType(args.get(4));

        // Order
        if(args.size() == 5) {
            PlayerUtil.message(player, "You need to include the board's order.", "Accepted values are &ehigh &7or &elow &7describing which stats (the highest or lowest) will be displayed at the top.");
            return;
        }
        board.setOrder(args.get(5));

        // Reload
        if(args.size() == 6) {
            PlayerUtil.message(player, "You need to include the board's reload time.", "This is how often the board will reload in seconds.");
            return;
        }
        try {
            Integer.parseInt(args.get(6));
        } catch(Exception e) {
            e.printStackTrace();
            PlayerUtil.message(player, "&cInvalid reload time entered.");
        }
        board.setReload(Integer.parseInt(args.get(6)));

        // Entries
        if(args.size() == 7) {
            PlayerUtil.message(player, "You need to include the board's max entries.");
            return;
        }
        try {
            Integer.parseInt(args.get(7));
        } catch(Exception e) {
            e.printStackTrace();
            PlayerUtil.message(player, "&cInvalid entries entered.");
        }
        board.setEntries(Integer.parseInt(args.get(7)));

        // Location
        Location playerLoc = player.getLocation().add(0, 3, 0);
        board.setWorld(playerLoc.getWorld());
        board.setX(Math.round( playerLoc.getX() * 10.0 )/10.0);
        board.setY(Math.round( playerLoc.getY() * 10.0 )/10.0);
        board.setZ(Math.round( playerLoc.getZ() * 10.0 )/10.0);

        // Create board
        ConfigUtil.createBoard(board);
        BoardUtil.createBoard(board);
    }
}