package dev.muffin.rpgcore.rpg.archetypes;

public class ArchetypeHandler {
    private Archetype warrior;

    public ArchetypeHandler() {
        warrior = new Warrior();
    }

    public Archetype getWarrior() {
        return warrior;
    }
}
