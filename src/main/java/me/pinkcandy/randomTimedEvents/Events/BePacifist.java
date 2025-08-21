package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;

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
        Entity damager = event.getDamager();
        if (damager instanceof Player)
        {
            event.setCancelled(true);
        }
        else if (damager instanceof Arrow)
        {
            ProjectileSource shooter = ((Arrow) damager).getShooter();
            if (shooter instanceof Player)
            {
                event.setCancelled(true);
            }

        }
    }

    @Override
    public String getName() {
        return "BePacifist";
    }
}
