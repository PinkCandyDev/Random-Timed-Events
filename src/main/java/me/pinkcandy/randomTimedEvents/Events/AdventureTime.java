package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class AdventureTime implements EventInterface, Listener {

    private final RandomTimedEvents plugin;

    public AdventureTime(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        plugin.getEventTimer().StartTimer(this, time);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void OnBlockBreak(BlockBreakEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    private void OnBlockPlace(BlockPlaceEvent event)
    {
        event.setCancelled(true);
    }

    @Override
    public void Stop() {
        BlockBreakEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
    }

    @Override
    public String getName() {
        return "AdventureTime";
    }
}
