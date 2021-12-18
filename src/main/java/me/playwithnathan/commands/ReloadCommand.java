package me.playwithnathan.commands;

import me.playwithnathan.Main;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.PlayerUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand extends CommandManager {
    public ReloadCommand(@NotNull Player player) {
        final ConfigUtil configUtil = Main.getConfigUtil();
        final Permission perms = Main.getPermissions();

        // Must have admin perms
        if(!perms.has(player, configUtil.getPerm("admin"))) {
            PlayerUtil.helpMessage(player);
            return;
        }

        // Reload config + boards
        configUtil.reload();
    }
}