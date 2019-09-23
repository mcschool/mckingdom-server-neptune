package me.mckd.neptune;

import me.mckd.neptune.Worlds.LobbyWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class Neptune extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("@@@@@@@@@@@@@@");
        System.out.println("@@@@@@@@@@@@@@");
        System.out.println("HHHHHHHHHHHHHH");
        System.out.println("@@@@@@@@@@@@@@2");
        System.out.println("@@@@@@@@@@@@@@");
        // Plugin startup logic
        new LobbyWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
