package me.mckd.neptune.Worlds;

import me.mckd.neptune.Neptune;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

        ItemStack bed = new ItemStack(Material.BED);
        ItemMeta itemMeta = bed.getItemMeta();
        itemMeta.setDisplayName("ロビーに戻る");
        bed.setItemMeta(itemMeta);
        player.getInventory().setItem(35, bed);

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        if (!player.getWorld().getName().equals("arrow")) return;
        if (e.getCurrentItem().getType() == Material.BED) {
            player.performCommand("mvtp lobby");
        }
    }

    @EventHandler
    public void signClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.getWorld().getName().equals("arrow")) {
            return;
        }
        Block b = e.getClickedBlock();
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && b.getType() == Material.SIGN_POST) {
            Sign sign;
            sign = (Sign) b.getState();
            String line = sign.getLine(1);
            if (line.equals("stage 1")) {
                Location location = new Location(p.getWorld(), -1601, 197, 721);
                p.teleport(location);
            }
        }
    }
}
