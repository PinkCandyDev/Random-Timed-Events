package me.pinkcandy.ramdomTimedEvents.Timers;

import me.pinkcandy.ramdomTimedEvents.Managers.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class BeforeEventTimer {
    private final JavaPlugin plugin;
    private int totalSeconds;
    private int secondsLeft;
    private BossBar bossBar;
    private BukkitTask task;

    /**
     * Konstruktor pobiera czas z configa.
     */
    public BeforeEventTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.totalSeconds = config.getInt("announcement", 30); // domyślnie 30 sekund
        this.secondsLeft = totalSeconds;
    }

    /**
     * Rozpoczyna event i pokazuje bossbar.
     */
    public void Start() {
        if (task != null && !task.isCancelled()) return;
        this.secondsLeft = totalSeconds;
        createBossBar();
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            updateBossBar();

            if (secondsLeft <= 0) {
                stopBossBar();
                EventManager eventManager = new EventManager(plugin);
                eventManager.StartRamdomClass();
                return;
            }

            secondsLeft--;
        }, 0L, 20L);
    }

    /**
     * Tworzy bossbar i dodaje wszystkich graczy.
     */
    private void createBossBar() {
        bossBar = Bukkit.createBossBar(getBarTitle(), BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
    }

    /**
     * Aktualizuje bossbar (tytuł i postęp).
     */
    private void updateBossBar() {
        if (bossBar == null) return;
        bossBar.setTitle(getBarTitle());
        double progress = Math.max(0, (double) secondsLeft / totalSeconds);
        bossBar.setProgress(progress);
    }

    /**
     * Usuwa bossbar i zatrzymuje task.
     */
    private void stopBossBar() {
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (bossBar != null) {
            bossBar.removeAll();
            bossBar.setVisible(false);
            bossBar = null;
        }
    }

    /**
     * Zwraca tytuł bossbara.
     */
    private String getBarTitle() {
        return "Event za " + secondsLeft + " sekund";
    }
}
