package me.pinkcandy.randomTimedEvents.Timers;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitTask;

public class EventTimer implements Listener {

    private final RandomTimedEvents plugin;
    private EventInterface event;
    private int totalTime;
    private int secondsLeft;

    private BossBar bossBar;
    private BukkitTask task;

    public EventTimer(RandomTimedEvents plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void StartTimer(EventInterface event, int time) {
        this.event = event;
        this.totalTime = time;
        this.secondsLeft = time;
        createBossBar();

        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            updateBossBar();

            if (secondsLeft <= 0) {
                stopBossBar();
                event.Stop();
                CountdownTimer countdownTimer = plugin.getCountdownTimer();
                countdownTimer.restart();
                return;
            }

            secondsLeft--;
        }, 0L, 20L);
    }

    public void Stop(CommandSender sender) {
        if (!isRunning())
        {
            sender.sendMessage("§cEvent is not running.");
        }
        else
        {
            task.cancel();
            stopBossBar();
            event.Stop();
            CountdownTimer countdownTimer = plugin.getCountdownTimer();
            countdownTimer.restart();
        }
    }

    public Boolean isRunning() {
        return task != null && !task.isCancelled();
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

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (bossBar != null) {
            bossBar.addPlayer(player);
        }
    }
    private String getBarTitle() {
        return "§6[" + event.getName() + "] will end in §c" + secondsLeft + "s";
    }
}
