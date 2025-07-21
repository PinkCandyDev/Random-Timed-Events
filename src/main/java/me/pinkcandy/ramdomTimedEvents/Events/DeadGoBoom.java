package me.pinkcandy.ramdomTimedEvents.Events;

import me.pinkcandy.ramdomTimedEvents.Managers.EventInterface;
import me.pinkcandy.ramdomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadGoBoom implements EventInterface, Listener {

    private final JavaPlugin plugin;
    private boolean playerDeathEvent;
    private boolean entityDeathEvent;
    private boolean setFire;

    public DeadGoBoom(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);
        this.playerDeathEvent = plugin.getConfig().getBoolean("events.DeadGoBoom.players", true);
        this.entityDeathEvent = plugin.getConfig().getBoolean("events.DeadGoBoom.mobs", true);
        this.setFire = plugin.getConfig().getBoolean("events.DeadGoBoom.setFire", true);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void Stop() {
        EntityDeathEvent.getHandlerList().unregister(this);
    }

    @Override
    public String getName() {
        return "DeadGoBoom";
    }

    @EventHandler
    public void OnEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player && playerDeathEvent)
        {
            entity.getWorld().createExplosion(entity.getLocation(), 3.0F, setFire, true);
        }
        else if (entityDeathEvent && !(entity instanceof Player))
        {
            entity.getWorld().createExplosion(entity.getLocation(), 3.0F, setFire, true);
        }
    }
}
