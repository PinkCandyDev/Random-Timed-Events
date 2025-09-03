package me.pinkcandy.randomTimedEvents.Managers;

import me.pinkcandy.randomTimedEvents.Events.*;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EventManager {
    private final RandomTimedEvents plugin;

    public EventManager(RandomTimedEvents plugin) {
        this.plugin = plugin;
    }

    private List<EventInterface> getAllEvents() {
        return Arrays.asList(
//              new ExampleEvent(plugin),   Eatch event need to be registered here
                new LiquidSwap(plugin),
                new YouAreZombie(plugin),
                new NoRegeneration(plugin),
                new LowGravity(plugin),
                new DeadGoBoom(plugin),
                new BePacifist(plugin),
                new NoJumping(plugin),
                new AdventureTime(plugin),
                new WallHack(plugin),
                new MoveOrDie(plugin),
                new AdhdInventory(plugin),
                new RandomTeleport(plugin)
        );
    }

    public void StartRamdomClass() {

        FileConfiguration config = plugin.getConfig();
        Random random = new Random();

        List<EventInterface> enabled = new ArrayList<>();

        ConfigurationSection eventsSection = config.getConfigurationSection("events");
        if (eventsSection != null) {
            for (EventInterface k : getAllEvents()) {
                String key = k.getName();
                ConfigurationSection section = eventsSection.getConfigurationSection(key);
                if (section != null && section.getBoolean("enabled", false)) {
                    enabled.add(k);
                }
            }
        }

        if (enabled.isEmpty()) {
            plugin.getLogger().warning("§cNo events are enabled in the configuration!");
            return;
        }

        EventInterface selected = enabled.get(random.nextInt(enabled.size()));

        ConfigurationSection section = config.getConfigurationSection("events." + selected.getName());
        int time = (section != null) ? section.getInt("time", 10) : 10;

        selected.Start(time);
        plugin.getLogger().info("Event " + selected.getName() + " was started for " + time + " seconds.");
    }

    public void startEventByName(String eventName, CommandSender sender) {

        if (plugin.getEventTimer().isRunning())
        {
            sender.sendMessage("§cEvent is already running!");
            return;
        }
        EventInterface found = null;
        for (EventInterface event : getAllEvents()) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                found = event;
                break;
            }
        }
        if (found == null) {
            plugin.getLogger().warning("§cEvent " + eventName + "doesn't exist!");
            return;
        }

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("events." + found.getName());

        int time = section.getInt("time", 10);
        found.Start(time);
        plugin.getLogger().info("Event " + found.getName() + " was force started for " + time + " seconds.");
    }
}

