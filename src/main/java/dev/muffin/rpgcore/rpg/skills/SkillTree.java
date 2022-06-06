package dev.muffin.rpgcore.rpg.skills;

import java.util.HashMap;
import java.util.Map;

public class SkillTree {

    private Map<Integer, String> warriorTree;
    private Map<String, Integer> warriorTreeReq;

    private void generateWarriorTree() {
        warriorTree = new HashMap<>();
        warriorTreeReq = new HashMap<>();

        warriorTree.put(0, "Cleave");
        warriorTree.put(1, "Shatterstrike");

        warriorTreeReq.put("Cleave", -1);
        warriorTreeReq.put("Shatterstrike", 0);
    }

    public SkillTree() {
        generateWarriorTree();
    }

}
