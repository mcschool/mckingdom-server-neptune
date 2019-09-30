package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class ArrowWarp implements Listener {

    Neptune plugin;
    String worldName = "arrow";

    public ArrowWarp(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPLayerChangeWorld(PlayerChangedWorldEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("arrow")) return;
        Player player = e.getPlayer();

        player.sendTitle("ArrowWarp", "アローワープ", 20, 40, 20);

        // インベントリクリア
        player.getInventory().clear();

        // arrowを渡す
        ItemStack arrow = new ItemStack(Material.ARROW, 64);
        player.getInventory().addItem(arrow);

        // bowを渡す
        ItemStack bow = new ItemStack(Material.BOW);
        player.getInventory().addItem(bow);
    }

    @EventHandler
    public void arrowShot(ProjectileHitEvent e) {
        if (!e.getEntity().getWorld().getName().equals("arrow")) return;
        if (e.getEntity() instanceof Arrow) {
            World world = e.getEntity().getWorld();
            Arrow arrow = (Arrow)e.getEntity();
            Location location = arrow.getLocation();
            Player player = (Player)e.getEntity().getShooter();
            player.teleport(location);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("arrow")) return;
        Player player = e.getPlayer();
        Double y = player.getLocation().getY();
        if (y < 180) {
            Location location = player.getWorld().getSpawnLocation();
            player.sendMessage("失敗...");
            player.teleport(location);
        }
    }
}
