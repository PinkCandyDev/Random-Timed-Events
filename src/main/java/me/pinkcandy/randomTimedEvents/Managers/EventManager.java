package me.pinkcandy.randomTimedEvents.Managers;

import me.pinkcandy.randomTimedEvents.Events.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EventManager {
    private final JavaPlugin plugin;

    public EventManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    private List<EventInterface> getAllEvents() {
        return Arrays.asList(
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
                new AdhdInventory(plugin)
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
            plugin.getLogger().warning("Żadna klasa nie ma enabled: true!");
            return;
        }

        EventInterface selected = enabled.get(random.nextInt(enabled.size()));

        ConfigurationSection section = config.getConfigurationSection("events." + selected.getName());
        int time = (section != null) ? section.getInt("time", 10) : 10;

        selected.Start(time);
    }

    public void startEventByName(String eventName) {

        EventInterface found = null;
        for (EventInterface event : getAllEvents()) {
            if (event.getName().equalsIgnoreCase(eventName)) {
                found = event;
                break;
            }
        }
        if (found == null) {
            plugin.getLogger().warning("Nie znaleziono eventu: " + eventName);
            return;
        }

        ConfigurationSection section = plugin.getConfig().getConfigurationSection("events." + found.getName());
        if (section == null || !section.getBoolean("enabled", false)) {
            plugin.getLogger().warning("Event " + eventName + " nie jest włączony!");
            return;
        }

        int time = section.getInt("time", 10);
        found.Start(time);
    }
}

