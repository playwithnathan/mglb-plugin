package me.playwithnathan.util;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.playwithnathan.Main;
import me.playwithnathan.classes.Board;
import me.playwithnathan.classes.Database;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BoardUtil {
    private static final Main instance = Main.getInstance();
    private static final List<Board> boards = new ArrayList<>();

    public static List<Board> getBoards() {
        return boards;
    }

    public static void createBoard(Board board) {
        Hologram hologram = HologramsAPI.createHologram(instance, board.getLocation());
        appendLines(board, hologram, false);

        // Set reload
        board.setTask(new BukkitRunnable() {
            @Override
            public void run() {
                BoardUtil.reloadBoard(board);
            }
        }.runTaskTimer(instance, 20 * 60, 20L * board.getReload()));

        boards.add(board);
    }

    public static void reloadBoard(Board board) {
        Hologram hologram = board.getHologram();
        appendLines(board, hologram, true);
    }

    private static void appendLines(Board board, Hologram hologram, boolean clear) {
        // If process is already running then don't run again
        if(board.getRunning()) return;

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            // Process is running
            board.setRunning(true);

            List<String> uuids = Database.getPlayers(board.getTable());

            // Sort stats (bubble sort)
            for(int i = 0; i < uuids.size() - 1; ++i) {
                for(int j = 0; j < uuids.size() - i - 1; ++j) {
                    int uuidJ, uuidJ1;

                    try {
                        uuidJ = Integer.parseInt(Database.getStat(board, uuids.get(j)));
                    } catch(Exception e) {
                        uuidJ = 0;
                        //Database.setStat(board, uuids.get(j));
                    }

                    try {
                        uuidJ1 = Integer.parseInt(Database.getStat(board, uuids.get(j + 1)));
                    } catch(Exception e) {
                        uuidJ1 = 0;
                        //Database.setStat(board, uuids.get(j + 1));
                    }

                    // Compare the elements
                    if(board.getOrder().equals("low")) {
                        if(uuidJ > uuidJ1) {
                            // Swap the elements
                            String tmp = uuids.get(j);
                            uuids.set(j, uuids.get(j + 1));
                            uuids.set(j + 1, tmp);
                        }
                    } else {
                        if(uuidJ < uuidJ1) {
                            // Swap the elements
                            String tmp = uuids.get(j);
                            uuids.set(j, uuids.get(j + 1));
                            uuids.set(j + 1, tmp);
                        }
                    }
                }
            }

            // Clear all lines
            if(clear) Bukkit.getScheduler().runTask(instance, hologram::clearLines);

            // Title
            Bukkit.getScheduler().runTask(instance, () -> hologram.appendTextLine(TextUtil.color(board.getTitle())));

            // Display formatted players + stats
            int entries = 0;
            for(String uuid : uuids) {
                if(!Bukkit.getOfflinePlayer(UUID.fromString(uuid)).hasPlayedBefore()) continue; // Ignore players that have never played before

                entries++;
                if(board.getEntries() < entries) break;

                Bukkit.getScheduler().runTask(instance, () -> hologram.appendTextLine(TextUtil.color(
                        board.getFormat()
                            .replace("%player%", Objects.requireNonNull(Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName()))
                            .replace("%stat%", Database.getStat(board, uuid))
                )));
            }

            // Set hologram to board
            board.setHologram(hologram);

            // Process is finished
            board.setRunning(false);
        });
    }

    public static void deleteBoard(Board board) {
        board.getTask().cancel();
        if(!board.getHologram().isDeleted()) board.getHologram().delete();
        boards.remove(board);
        ConfigUtil.delete(board.getFile());
    }

    public static void deleteBoards() {
        Bukkit.getScheduler().cancelTasks(Main.getInstance());
        HologramsAPI.getHolograms(instance).forEach(hologram -> {
            if(!hologram.isDeleted()) hologram.delete();
        });
        boards.clear();
    }

}