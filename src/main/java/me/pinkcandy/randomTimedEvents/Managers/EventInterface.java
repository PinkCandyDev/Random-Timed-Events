package me.pinkcandy.randomTimedEvents.Managers; // ← zmień na swój pakiet!

public interface EventInterface {
    void Start(int time); //Event start instructions
    void Stop(); //Event stop instructions
    String getName(); //Config and class name
}

