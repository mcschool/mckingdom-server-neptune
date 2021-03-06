package me.mckd.neptune.Worlds.ArrowWarp;

import me.mckd.neptune.Neptune;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
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
        player.getInventory().setItem(8, bed);


        FileConfiguration config = plugin.getConfig();
        Location location = player.getWorld().getSpawnLocation();
        config.set(player.getUniqueId().toString() + "-arrow-check-point-x", location.getX());
        config.set(player.getUniqueId().toString() + "-arrow-check-point-y", location.getY());
        config.set(player.getUniqueId().toString() + "-arrow-check-point-z", location.getZ());

    }

    @EventHandler
    public void arrowShot(ProjectileHitEvent e) {
        if (!e.getEntity().getWorld().getName().equals("arrow")) return;
        if (e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();
            Location location = arrow.getLocation();
            Double y = location.getY();
            if (y > 170) {
                Player player = (Player) e.getEntity().getShooter();
                player.teleport(location);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!e.getPlayer().getWorld().getName().equals("arrow")) return;
        Player player = e.getPlayer();
        Double y = player.getLocation().getY();
        if (y < 180) {
            FileConfiguration config = plugin.getConfig();
            Double sx = config.getDouble(player.getUniqueId().toString() + "-arrow-check-point-x" , e.getPlayer().getLocation().getX());
            Double sy = config.getDouble(player.getUniqueId().toString() + "-arrow-check-point-y" , e.getPlayer().getLocation().getY());
            Double sz = config.getDouble(player.getUniqueId().toString() + "-arrow-check-point-z" , e.getPlayer().getLocation().getZ());
            Location location = new Location(e.getPlayer().getWorld(),sx,sy,sz);

            //Location location = player.getWorld().getSpawnLocation();
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
            // 看板にstage1と書いてあったら
            if (line.equals("stage 1")) {
                Location location = new Location(p.getWorld(), -1601, 197, 721);
                p.teleport(location);
            }
            // ここまで
            if (line.equals("１マスコース")) {
                Location location = new Location(p.getWorld(), -1546 ,197,  691);
                p.teleport(location);

            }

        }
    }

    // 11.26
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!e.getEntity().getWorld().getName().equals("arrow")) {
            return;
        }
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() != null && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    public void stageClear(Player player) {
        player.sendTitle("クリア!", "おめでとう!", 20, 20, 20);
        player.setGameMode(GameMode.SPECTATOR);
        new ArrowClearScheduler(player).runTaskTimer(this.plugin, 0, 20);

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("arrow")) {
            return;
        }
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                if(player.getGameMode()!= GameMode.SPECTATOR) {
                    this.stageClear(player);
                    e.setCancelled(true);
                }
            }
            if (e.getClickedBlock().getType() == Material.REDSTONE_BLOCK) {
                FileConfiguration config = plugin.getConfig();
                Double x = e.getPlayer().getLocation().getX();
                Double y = e.getPlayer().getLocation().getY();
                Double z = e.getPlayer().getLocation().getZ();
                player.sendMessage("セーブしました。" + String.valueOf(x) + "/" + String.valueOf(y) + "/" + String.valueOf(z));
                config.set(player.getUniqueId().toString() + "-arrow-check-point-x", e.getPlayer().getLocation().getX());
                config.set(player.getUniqueId().toString() + "-arrow-check-point-y", e.getPlayer().getLocation().getY());
                config.set(player.getUniqueId().toString() + "-arrow-check-point-z", e.getPlayer().getLocation().getZ());
            }
        }
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if(e.getMaterial() == Material.BED) {
                player.performCommand("mvtp lobby");
            }
            if (e.getMaterial() == Material.COMPASS) {
                World world = Bukkit.getWorld("arrow");
                Location location = new Location(world,-1546,196,723);
                player.teleport(location);
            }
        }

    }

}
