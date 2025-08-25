package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MoveOrDie implements EventInterface, Listener {

    private final RandomTimedEvents plugin;
    private BukkitTask checkMoveTask;
    private final Map<UUID, Location> lastLocations = new HashMap<>();

    public MoveOrDie(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        plugin.getEventTimer().StartTimer(this, time);
        checkMoveTask = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            plugin.getServer().getOnlinePlayers().forEach(player -> {
                Location last = lastLocations.get(player.getUniqueId());
                Location current = player.getLocation();

                if (last != null && current.distance(last) <= 0.06) {
                    player.damage(1.5);
                }

                lastLocations.put(player.getUniqueId(), current.clone());

            });
        }, 0L, 1L);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        lastLocations.remove(event.getPlayer().getUniqueId());
    }

    @Override
    public void Stop() {
        if (checkMoveTask != null) {
            checkMoveTask.cancel();
            checkMoveTask = null;
        }
        lastLocations.clear();
    }

    @Override
    public String getName() {
        return "MoveOrDie";
    }
}
