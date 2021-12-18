package me.playwithnathan.util;

import me.playwithnathan.Main;
import me.playwithnathan.classes.Board;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ConfigUtil {
    // Reload config
    public void reload() {
        BoardUtil.deleteBoards();
        loadBoards();
    }

    // Load boards
    public void loadBoards() {
        FileConfiguration config = Main.getInstance().getConfig();

        for(String key : Objects.requireNonNull(config.getConfigurationSection("boards")).getKeys(false)) {
            String child = "boards." + key + ".";
            Board board = new Board(key);

            board.setTable(getString(child + "table"));
            board.setColumn(getString(child + "column"));
            board.setTitle(getString(child + "title"));
            board.setFormatType(getString(child + "format"));
            board.setOrder(getString(child + "order"));
            board.setReload(config.getInt(child + "reload"));
            board.setEntries(config.getInt(child + "entries"));

            String location = child + "location.";
            board.setWorld(getString(location + "world"));
            board.setX(config.getDouble(location + "x"));
            board.setY(config.getDouble(location + "y"));
            board.setZ(config.getDouble(location + "z"));

            BoardUtil.createBoard(board);
        }
    }

    // Save all
    public void save() {
        saveBoards();
        Main.getInstance().saveConfig();
    }

    // Save boards
    private void saveBoards() {
        FileConfiguration config = Main.getInstance().getConfig();

        for(Board board : BoardUtil.getBoards()) {
            String child = "boards." + board.getId() + ".";
            setString(child + "table", board.getTable());
            setString(child + "column", board.getColumn());
            setString(child + "title", TextUtil.normalizeColor(board.getTitle()));
            setString(child + "format", board.getFormatType());
            setString(child + "order", board.getOrder());
            config.set(child + "reload", board.getReload());
            config.set(child + "entries", board.getEntries());

            String location = child + "location.";
            setString(location + "world", board.getWorld().getName());
            config.set(location + "x", board.getX());
            config.set(location + "y", board.getY());
            config.set(location + "z", board.getZ());
        }
    }

    // Delete board
    public void deleteBoard(Board boardDelete) {
        Main.getInstance().getConfig().set("boards." + boardDelete.getId(), null);
        save();
    }

    // Static functions
    private String getString(String path) {
        return Objects.requireNonNull(Main.getInstance().getConfig().getString(path)).replaceAll("'", "");
    }

    private void setString(String path, String value) {
        //Main.getInstance().getConfig().set(path, "'" + value.replaceAll("'", "") + "'");
        Main.getInstance().getConfig().set(path, value);
    }

    public String getPerm(String perm) {
        return Main.getInstance().getConfig().getString("permissions." + perm);
    }

    public String getFormat(String formatType) {
        return Main.getInstance().getConfig().getString("format." + formatType);
    }

    public String getDB(String dir) {
        return Main.getInstance().getConfig().getString("database." + dir);
    }
}
