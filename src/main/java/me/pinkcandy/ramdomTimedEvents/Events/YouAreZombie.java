package me.pinkcandy.ramdomTimedEvents.Events;

import me.pinkcandy.ramdomTimedEvents.Managers.EventInterface;
import me.pinkcandy.ramdomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class YouAreZombie implements EventInterface {

    private BukkitTask task;

    @Override
    public void Start(int time) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(getClass());
        new EventTimer(plugin, this, time);
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (isDay(player.getWorld()) && isUnderOpenSky(player)) {
                    player.setFireTicks(90); // 3 sekundy
                }
            }
        }, 0L, 20); // co 2 sekundy
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

    private boolean isDay(World world) {
        long time = world.getTime();
        return time >= 0 && time < 12500; // Minecraftowy dzień: 0 - 12300 ticków
    }

    private boolean isUnderOpenSky(Player player) {
        Location loc = player.getLocation();
        int highestY = loc.getWorld().getHighestBlockYAt(loc);
        return loc.getBlockY() >= highestY;
    }
}
