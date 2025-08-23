package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.Timers.EventTimer;
import org.bukkit.plugin.java.JavaPlugin;

public class ExampleEvent implements EventInterface {

    private final JavaPlugin plugin; //Event should have a reference to the main plugin class

    public ExampleEvent(JavaPlugin plugin) { // Constructor
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) { // Method that starts the event time is a time variable from config under the event name
        new EventTimer(plugin, this, time); // Event should start a timer
        // Make event work infinite, EventTimer will stop it after time
    }

    @Override
    public void Stop() {
        // Method that stops the event, Make sure to unregister all listeners here
    }

    @Override
    public String getName() {
        return "ExampleEvent"; // Return the name of the event here, should be the same as the class name and in config
    }


    //Eatch event need to be registered in the EventManager class
}
