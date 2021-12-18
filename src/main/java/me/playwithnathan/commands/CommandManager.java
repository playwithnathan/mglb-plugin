package me.playwithnathan.commands;

import me.playwithnathan.Main;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Sender must be player
        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        Permission perms = Main.getPermissions();
        ConfigUtil configUtil = Main.getConfigUtil();

        if(!perms.has(player, configUtil.getPerm("admin"))) {
            PlayerUtil.helpMessage(player);
            return true;
        }

        // Requires an arg
        if(args.length == 0) {
            PlayerUtil.helpMessage(player);
            return true;
        }

        // Convert the array-args into an arraylist-newArgs
        // This removes the "Actual command" from the args too
        List<String> newArgs = new ArrayList<>();
        for(String arg : args) {
            if(args[0].equals(arg)) continue;
            newArgs.add(arg);
        }

        // Actual command
        switch(args[0].toLowerCase()) {
            case "reload":
                new ReloadCommand(player);
                break;
            case "help":
                new HelpCommand(player);
                break;
            case "list":
                new ListCommand(player);
                break;
            case "formats":
                new FormatsCommand(player);
                break;
            case "create":
                new CreateCommand(player, newArgs);
                break;
            case "delete":
                new DeleteCommand(player, newArgs);
                break;
            case "info":
                new InfoCommand(player, newArgs);
                break;
            default:
                PlayerUtil.helpMessage(player);
                return true;
        }

        return true;
    }
}

