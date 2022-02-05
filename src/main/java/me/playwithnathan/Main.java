package me.playwithnathan;

import me.playwithnathan.classes.Database;
import me.playwithnathan.classes.TabComplete;
import me.playwithnathan.commands.CommandManager;
import me.playwithnathan.util.BoardUtil;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.TextUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {
    private final Logger log = getLogger();
    private static Main instance;
    private static Permission perms;

    @Override
    public void onEnable() {
        instance = this;

        // Dependencies
        createDepend("Vault");
        createDepend("HolographicDisplays");
        createDepend("MinigameMaker");

        RegisteredServiceProvider<Permission> rspPerms = getServer().getServicesManager().getRegistration(Permission.class);
        if(rspPerms == null) throw new RuntimeException("Disabled because a Permission plugin wasn't found");
        perms = rspPerms.getProvider();

        // Database
        Database.connect();

        // Load boards
        ConfigUtil.loadBoards();

        // Register commands
        registerCommand(new CommandManager(), "mglb");

        // Register tab complete
        Objects.requireNonNull(this.getCommand("mglb")).setTabCompleter(new TabComplete());

        // Plugin is on
        log(Level.INFO, "&aPlugin Enabled");
    }

    public void log(Level level, String msg) {
        log.log(level, TextUtil.color(msg));
    }

    /**
     * Create a dependent plugin
     * @param plugin the plugin we depend on
     */
    private void createDepend(String plugin) throws RuntimeException {
        if(Bukkit.getPluginManager().getPlugin(plugin) == null) throw new RuntimeException("Disabled because " + plugin + " wasn't found");
    }

    /**
     * Register a command
     * @param executor the file. Syntax: new file()
     * @param command(s) will be run by the executor
     */
    private void registerCommand(CommandExecutor executor, String... command) {
        // Only one command
        if(command.length == 1) {
            Objects.requireNonNull(this.getCommand(command[0])).setExecutor(executor);
            return;
        }

        for(String cmd : command)
            Objects.requireNonNull(this.getCommand(cmd)).setExecutor(executor);
    }

    public static Main getInstance() {
        return instance;
    }

    public static Permission getPermissions() {
        return perms;
    }

    @Override
    public void onDisable() {
        // Database
        Database.disconnect();

        // Remove all boards
        BoardUtil.deleteBoards();

        // Plugin is off
        log(Level.INFO, "&aPlugin Disabled");
    }
}