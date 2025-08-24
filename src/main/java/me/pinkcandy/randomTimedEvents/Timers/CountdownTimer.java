package me.pinkcandy.randomTimedEvents.Timers;

import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

public class CountdownTimer {
    private final RandomTimedEvents plugin;
    private int totalSeconds;
    private int secondsLeft;
    private BukkitTask task;

    public CountdownTimer(RandomTimedEvents plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.totalSeconds = config.getInt("intrivial", 60); // domyślnie 60 sekund
        this.secondsLeft = totalSeconds;
    }

    public void start() {
        if (task != null && !task.isCancelled()) return;
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
                secondsLeft--;
                if (secondsLeft <= 0) {
                    cancelTask();
                    AnnouncementTimer announcementTimer = new AnnouncementTimer(plugin);
                    announcementTimer.Start();
                }
        }, 20L, 20L);
    }

    public void cancelTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
    public void resume() {
        if (secondsLeft <= 0) return;
        start();
    }

    public void restart() {
        cancelTask();
        this.secondsLeft = totalSeconds;
        start();
    }

    public int getSecondsLeft() {
        return secondsLeft;
    }

    public boolean getTimerState() {
        if (task == null || task.isCancelled()) {
            return false;
        } else {
            return true;
        }
    }
}