package dev.muffin.rpgcore.rpg.classes;

public class ClassHandler {
    private RPGClass warrior;

    public ClassHandler() {
        warrior = new Warrior();
    }

    public RPGClass getWarrior() {
        return warrior;
    }
}
