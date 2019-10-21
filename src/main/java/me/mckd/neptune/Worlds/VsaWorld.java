package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldLoadEvent;

public class VsaWorld implements Listener {

    Neptune plugin;
    String worldName = "vsa";
    Location lobbyLocation = new Location(Bukkit.getWorld(this.worldName), -327, 98, -53);

    public VsaWorld(Neptune plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        Location location = new Location(Bukkit.getWorld(this.worldName), -327, 98, -53);

        player.setGameMode(GameMode.SURVIVAL);
        player.getWorld().setPVP(false);
        player.getInventory().clear();

        player.teleport(location);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        if (player.getGameMode() == GameMode.SURVIVAL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals(this.worldName)) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                done(player);
                e.setCancelled(true);
            }
        }
    }

    public void done(Player player) {
        player.sendTitle("CLEAR!!", "おめでとう！", 20, 20, 20);
        player.teleport(this.lobbyLocation);
    }
}
