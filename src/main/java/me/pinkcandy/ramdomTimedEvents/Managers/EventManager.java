package me.pinkcandy.ramdomTimedEvents.Managers;

import me.pinkcandy.ramdomTimedEvents.Events.LiquidSwap;
import me.pinkcandy.ramdomTimedEvents.Events.LowGravity;
import me.pinkcandy.ramdomTimedEvents.Events.NoRegeneration;
import me.pinkcandy.ramdomTimedEvents.Events.YouAreZombie;
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
                new YouAreZombie(),
                new NoRegeneration(plugin),
                new LowGravity(plugin)
        );

        FileConfiguration config = plugin.getConfig();
        Random random = new Random();

        List<EventInterface> enabled = new ArrayList<>();

        for (EventInterface k : klasy) {
            String key = k.getName();
            ConfigurationSection section = config.getConfigurationSection(key);
            if (section != null && section.getBoolean("enabled", false)) {
                enabled.add(k);
            }
        }

        if (enabled.isEmpty()) {
            plugin.getLogger().warning("Żadna klasa nie ma enabled: true!");
            return;
        }

        EventInterface selected = enabled.get(random.nextInt(enabled.size()));

        ConfigurationSection section = config.getConfigurationSection(selected.getName());
        int time = (section != null) ? section.getInt("time", 10) : 10;

        selected.Start(time);
    }
}

