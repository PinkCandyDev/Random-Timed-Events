package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;


public class NoRegeneration implements EventInterface, Listener {

    private final RandomTimedEvents plugin;
    private boolean artificial;

    public NoRegeneration(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        plugin.getEventTimer().StartTimer(this, time);
        plugin.getLogger().info("Event NoRegeneration został włączony.");
    }

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        this.artificial =  plugin.getConfig().getBoolean("events.NoRegeneration.artificial", true);
        if (artificial) {
            switch (event.getRegainReason()) {
                case REGEN, SATIATED, MAGIC_REGEN, MAGIC:
                    event.setCancelled(true);
                    break;
                default:
                    break;
            }
        }
        else
        {
            switch (event.getRegainReason()) {
                case REGEN, SATIATED:
                    event.setCancelled(true);
                    break;
                default:
                    break;
            }
        }

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
}
