package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class AdhdInventory implements EventInterface, Listener {

    private final RandomTimedEvents plugin;

    public AdhdInventory(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) {
        new EventTimer(plugin, this, time);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHeldItemChange(PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack[] contents = player.getInventory().getContents();

        List<ItemStack> items = new ArrayList<>();
        Collections.addAll(items, contents);
        
        Collections.shuffle(items);

        player.getInventory().setContents(items.toArray(new ItemStack[0]));
        player.updateInventory();
    }

    @Override
    public void Stop() {
        PlayerItemHeldEvent.getHandlerList().unregister(this);
    }

    @Override
    public String getName() {
        return "ADHDInventory";
    }
}
