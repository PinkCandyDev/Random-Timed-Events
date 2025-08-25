package me.pinkcandy.randomTimedEvents.Events;

import me.pinkcandy.randomTimedEvents.Managers.EventInterface;
import me.pinkcandy.randomTimedEvents.RandomTimedEvents;

public class ExampleEvent implements EventInterface { // Each event need to implement EventInterface

    private final RandomTimedEvents plugin; //Event should have a reference to the main plugin class

    public ExampleEvent(RandomTimedEvents plugin) { // Constructor
        this.plugin = plugin;
    }

    @Override
    public void Start(int time) { // Method that starts the event time is a time variable from config under the event name
        plugin.getEventTimer().StartTimer(this, time); // Event should start a timer
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
