package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class YouAreZombie implements EventInterface {

    private BukkitTask task;
    private final RandomTimedEvents plugin;

    public YouAreZombie(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        plugin.getEventTimer().StartTimer(this, time);
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (isDay(player.getWorld()) && isUnderOpenSky(player)) {
                    player.setFireTicks(90);
                }
            }
        }, 0L, 20);
    }

    private boolean isDay(World world) {
        long time = world.getTime();
        return time >= 0 && time < 12500;
    }

    private boolean isUnderOpenSky(Player player) {
        Location loc = player.getLocation();
        int highestY = loc.getWorld().getHighestBlockYAt(loc);
        return loc.getBlockY() >= highestY;
    }

    @Override
    public void Stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    @Override
    public String getName() {
        return "YouAreZombie";
    }
}
