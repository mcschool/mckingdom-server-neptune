package me.mckd.neptune;

import me.mckd.neptune.Worlds.*;
import me.mckd.neptune.Worlds.Beginers.BeginersWorld;
import me.mckd.neptune.Worlds.Villager.VillagerWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        new ArrowWarp(this);
        new BuildWorld(this);
        new VsaWorld(this);
        new VillagerWorld(this);
        new BeginersWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            player.sendMessage("label:" + label);
        }
        return true;
    }

}
