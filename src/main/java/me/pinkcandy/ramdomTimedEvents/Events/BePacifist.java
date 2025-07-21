package me.pinkcandy.ramdomTimedEvents.Events;

import me.pinkcandy.ramdomTimedEvents.Managers.EventInterface;
import me.pinkcandy.ramdomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class BePacifist implements EventInterface, Listener {

    private final JavaPlugin plugin;

    public BePacifist(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void Stop() {
        EntityDamageByEntityEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    private void OnEntityHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player)
        {
            event.setCancelled(true);
        }
    }

    @Override
    public String getName() {
        return "BePacifist";
    }
}
