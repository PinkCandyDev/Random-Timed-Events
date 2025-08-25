package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class NoJumping implements EventInterface, Listener {

    private final RandomTimedEvents plugin;

    public NoJumping(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        plugin.getEventTimer().StartTimer(this, time);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void OnPlayerMove(PlayerMoveEvent event)
    {
        if (event.getPlayer().getVelocity().getY() >= 0 && !event.getPlayer().isOnGround())
        {
            event.setCancelled(true);
        }
    }

    @Override
    public void Stop() {
        PlayerMoveEvent.getHandlerList().unregister(this);
    }

    @Override
    public String getName() {
        return "NoJumping";
    }

}
