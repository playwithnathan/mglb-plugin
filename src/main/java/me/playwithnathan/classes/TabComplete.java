package me.playwithnathan.classes;

import me.playwithnathan.Main;
import me.playwithnathan.util.ConfigUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        // Sender must be player
        if(!(sender instanceof Player)) return null;
        Player player = (Player) sender;

        Permission perms = Main.getPermissions();
        ConfigUtil configUtil = Main.getConfigUtil();

        if(args[0].isEmpty()) {
            // If player is admin
            if(perms.has(player, configUtil.getPerm("admin")))
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("help", "list", "create", "delete", "info", "formats", "reload"), new ArrayList<>());
        }

        return null;
    }
}
