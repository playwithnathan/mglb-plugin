package me.playwithnathan.classes;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.playwithnathan.util.ConfigUtil;
import me.playwithnathan.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;

public class Board {
    private final String id;

    private File file;
    private FileConfiguration config;

    private String table;
    private String column;

    private String title;
    private String format;
    private String order;
    private int reload;
    private int entries;

    private World world;
    private double x;
    private double y;
    private double z;

    private Hologram hologram;
    private BukkitTask task;
    private boolean running;

    public Board(String id) {
        this.id = id;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public String getId() {
        return id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getTitle() {
        return TextUtil.color(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormatType() {
        return format;
    }

    public void setFormatType(String format) {
        this.format = format;
    }

    public String getFormat() {
        return ConfigUtil.getFormat(getFormatType());
    }

    public Location getLocation() {
        return new Location(world, x, y, z);
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void setWorld(String world) {
        this.world = Bukkit.getWorld(world);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getReload() {
        return reload;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public BukkitTask getTask() {
        return task;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
