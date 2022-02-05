package me.playwithnathan.util;

import me.playwithnathan.Main;
import me.playwithnathan.classes.Board;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    private static final Main instance = Main.getInstance();

    private static File dir;

    // Reload config
    public static void reload() {
        BoardUtil.deleteBoards();
        loadBoards();
    }

    // Load boards
    public static void loadBoards() {
        // Folder
        dir = new File(instance.getDataFolder() + File.separator + "boards");
        if(!dir.exists()) dir.mkdir();

        // Files
        File[] files = dir.listFiles();
        if(files == null || files.length == 0) return;

        for(File file : files) {
            if(!file.exists()) continue;
            Board board = new Board(file.getName().replace(".yml", ""));
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);

            board.setFile(file);
            board.setConfig(config);

            board.setTable(config.getString("table"));
            board.setColumn(config.getString("column"));
            board.setTitle(config.getString("title"));
            board.setFormatType(config.getString("format"));
            board.setOrder(config.getString("order"));
            board.setReload(config.getInt("reload"));
            board.setEntries(config.getInt("entries"));

            board.setWorld(config.getString("location.world"));
            board.setX(config.getDouble("location.x"));
            board.setY(config.getDouble("location.y"));
            board.setZ(config.getDouble("location.z"));

            BoardUtil.createBoard(board);
        }
    }

    // Save all
    public static void save() {
        saveBoards();
        Main.getInstance().saveConfig();
    }

    // Save boards
    public static void saveBoards() {
        for(Board board : BoardUtil.getBoards()) {
            // If file doesn't exist yet then make it
            if(board.getFile() == null) createBoard(board);
            FileConfiguration config = board.getConfig();
            if(config == null) config = new YamlConfiguration();

            config.set("table", board.getTable());
            config.set("column", board.getColumn());
            config.set("title", TextUtil.normalizeColor(board.getTitle()));
            config.set("format", board.getFormatType());
            config.set("order", board.getOrder());
            config.set("reload", board.getReload());
            config.set("entries", board.getEntries());

            config.set("location.world", board.getWorld().getName());
            config.set("location.x", board.getX());
            config.set("location.y", board.getY());
            config.set("location.z", board.getZ());

            try {
                config.save(board.getFile());
            } catch(Exception ignored) {}
        }
    }

    public static void createBoard(Board board) {
        File file = new File(dir.getPath(), board.getId() + ".yml");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch(IOException ignored) {}
        }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);

            board.setFile(file);
            board.setConfig(config);

            config.set("table", board.getTable());
            config.set("column", board.getColumn());
            config.set("title", TextUtil.normalizeColor(board.getTitle()));
            config.set("format", board.getFormatType());
            config.set("order", board.getOrder());
            config.set("reload", board.getReload());
            config.set("entries", board.getEntries());

            config.set("location.world", board.getWorld().getName());
            config.set("location.x", board.getX());
            config.set("location.y", board.getY());
            config.set("location.z", board.getZ());

            config.save(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void delete(File file) {
        if(file.exists()) file.delete();
    }

    public static String getFormat(String formatType) {
        return Main.getInstance().getConfig().getString("format." + formatType);
    }
}
