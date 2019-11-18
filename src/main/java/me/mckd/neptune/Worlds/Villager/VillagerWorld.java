package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class VillagerWorld implements Listener {

    Neptune plugin;
    String worldName = "villager";

    public VillagerWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) {
            return;
        }
        // スケジューラーを開始する
        new SpawnDiamondScheduler(this.plugin, player.getWorld()).runTaskTimer(this.plugin, 0, 80);

        World world =player.getWorld();
        if (world.getPlayers().size() == 1) {
            new JoinCheckScheduler(this.plugin, world);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().getWorld().getName().equals(this.worldName)) {
            return;
        }

        if (player.getLocation().getY() < 180) {
            Location location = new Location(player.getWorld(), -821, 201, -308);
            player.sendTitle("ReSpawned!", "", 20, 40, 20);
            player.teleport(location);
        }
    }

    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e){
        World world = e.getEntity().getWorld();
        if (!world.getName().equals("villager")) {
            return;
        }
        e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
        Player player=e.getPlayer();
        if(!e.getPlayer().getWorld().getName().equals(this.worldName)){
            return;
        }
        new SpawnDiamondScheduler(this.plugin,player.getWorld()).runTaskTimer(this.plugin, 0 , 100);
    }
}
