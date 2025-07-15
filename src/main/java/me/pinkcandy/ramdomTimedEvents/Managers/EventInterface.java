package me.pinkcandy.ramdomTimedEvents.Managers; // ← zmień na swój pakiet!

public interface EventInterface {
    void Start(int time);
    void Stop();             // ← Dodane
    String getName();        // ← Do bossbara, np. "KlasaA"
}

