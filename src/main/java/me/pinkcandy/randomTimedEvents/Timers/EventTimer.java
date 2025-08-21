package me.pinkcandy.randomTimedEvents.Timers;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EventTimer {

    private final JavaPlugin plugin;
    private final EventInterface event;
    private final int totalTime;
    private int secondsLeft;

    private BossBar bossBar;
    private BukkitTask task;

    public EventTimer(JavaPlugin plugin, EventInterface event, int time) {
        this.plugin = plugin;
        this.event = event;
        this.totalTime = time;
        this.secondsLeft = time;

        startTimer();
    }

    private void startTimer() {
        createBossBar();

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            updateBossBar();

            if (secondsLeft <= 0) {
                stopBossBar();
                event.Stop(); // wywołanie Stop() z klasy
                CountdownTimer countdownTimer = new CountdownTimer(plugin);
                countdownTimer.restart();
                return;
            }

            secondsLeft--;
        }, 0L, 20L);
    }

    public void Stop(CommandSender sender) {
        if (task == null || task.isCancelled())
        {
            sender.sendMessage("§cEvent is not running.");
        }
        else
        {
            task.cancel();
            stopBossBar();
            event.Stop();
            CountdownTimer countdownTimer = new CountdownTimer(plugin);
            countdownTimer.restart();
        }
    }

    private void createBossBar() {
        bossBar = Bukkit.createBossBar(getBarTitle(), BarColor.RED, BarStyle.SOLID);
        bossBar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
    }

    private void updateBossBar() {
        if (bossBar == null) return;
        bossBar.setTitle(getBarTitle());
        double progress = Math.max(0.0, (double) secondsLeft / totalTime);
        bossBar.setProgress(progress);
    }

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

    private String getBarTitle() {
        return "§6[" + event.getName() + "] will end in §c" + secondsLeft + "s";
    }
}
