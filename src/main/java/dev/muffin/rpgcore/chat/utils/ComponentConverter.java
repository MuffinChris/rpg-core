package dev.muffin.rpgcore.chat.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import java.util.ArrayList;
import java.util.List;

public class ComponentConverter {

    public static Component getComponentFromString(String string) {
        return PlainTextComponentSerializer.plainText().deserialize(ChatColor.color(string));
    }

    public static List<Component> getComponentListFromStringList(List<String> stringList) {
        List<Component> componentList = new ArrayList<>();
        for (String str : stringList) {
            componentList.add(getComponentFromString(str));
        }
        return componentList;
    }

}
