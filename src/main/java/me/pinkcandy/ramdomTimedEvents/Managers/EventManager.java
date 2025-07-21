package me.pinkcandy.ramdomTimedEvents.Managers;

import me.pinkcandy.ramdomTimedEvents.Events.*;
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

    public void StartRamdomClass() {
        List<EventInterface> klasy = Arrays.asList(
                new LiquidSwap(plugin),
                new YouAreZombie(plugin),
                new NoRegeneration(plugin),
                new LowGravity(plugin),
                new DeadGoBoom(plugin)
        );

        FileConfiguration config = plugin.getConfig();
        Random random = new Random();

        List<EventInterface> enabled = new ArrayList<>();

        ConfigurationSection eventsSection = config.getConfigurationSection("events");
        if (eventsSection != null) {
            for (EventInterface k : klasy) {
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

// Poprawne pobranie sekcji spod "events.<nazwa>"
        ConfigurationSection section = config.getConfigurationSection("events." + selected.getName());
        int time = (section != null) ? section.getInt("time", 10) : 10;

        selected.Start(time);
    }
}

