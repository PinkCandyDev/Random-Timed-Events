package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class NoRegeneration implements EventInterface, Listener {

    private final JavaPlugin plugin;

    public NoRegeneration(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        new EventTimer(plugin, this, time);
        plugin.getLogger().info("Event NoRegeneration został włączony.");
    }

    @Override
    public void Stop() {
        EntityRegainHealthEvent.getHandlerList().unregister(this);
        plugin.getLogger().info("Event NoRegeneration został wyłączony.");
    }

    @Override
    public String getName() {
        return "NoRegeneration";
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        switch (event.getRegainReason()) {
            case REGEN, SATIATED, MAGIC_REGEN, MAGIC:
                event.setCancelled(true);
                break;
            default:
                break;
        }
    }
}
