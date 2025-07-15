package me.pinkcandy.ramdomTimedEvents.Timers;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Klasa realizująca odliczanie czasu na podstawie wartości z configu.
 */
public class CountdownTimer {
    private final JavaPlugin plugin;
    private int totalSeconds;
    private int secondsLeft;
    private BukkitTask task;

    /**
     * Tworzy timer i pobiera wartość z configu.
     * @param plugin instancja pluginu
     */
    public CountdownTimer(JavaPlugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.totalSeconds = config.getInt("intrivial", 60); // domyślnie 60 sekund
        this.secondsLeft = totalSeconds;
    }

    /**
     * Rozpoczyna odliczanie.
     */
    public void start() {
        if (task != null && !task.isCancelled()) return;
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                secondsLeft--;
                if (secondsLeft <= 0) {
                    cancelTask();
                    BeforeEventTimer beforeEventTimer= new BeforeEventTimer(plugin);
                    beforeEventTimer.Start();
                }
        }, 20L, 20L);
    }

    /**
     * Anuluje task bez ustawiania pauzy.
     */
    private void cancelTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
    public void resume() {
        if (secondsLeft <= 0) return;
        start();
    }

    /**
     * Restartuje odliczanie od początku.
     */
    public void restart() {
        cancelTask();
        this.secondsLeft = totalSeconds;
        start();
    }

    /**
     * Zwraca ilość pozostałych sekund.
     * @return liczba sekund do końca
     */
    public int getSecondsLeft() {
        return secondsLeft;
    }
}