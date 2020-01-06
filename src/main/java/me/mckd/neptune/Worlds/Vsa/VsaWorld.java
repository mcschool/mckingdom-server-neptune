package me.mckd.neptune.Worlds.Vsa;

import me.mckd.neptune.Neptune;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class VsaWorld implements Listener {

    Neptune plugin;
    String worldName = "vsa";
    Location lobbyLocation = new Location(Bukkit.getWorld(this.worldName), -327, 98, -53);
    String currentCourse = "";
    String status = "wait";
    int playerNum = 0;
    int clearPlayerNum = 0;

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

        // ブロックを右クリックした場合
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            // エンダーチェストをクリックした場合
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                done(player);
                e.setCancelled(true);
            }
            // 看板をクリックした場合
            if (e.getClickedBlock().getType() == Material.SIGN_POST) {
                this.signClicked(e);
            }
        }
        // 空中を右クリックした場合
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getMaterial() == Material.COMPASS) {
                player.performCommand("mvtp lobby");
            }
        }
    }

    // 看板がクリックされたときの処理
    public void signClicked(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        Sign sign = (Sign) block.getState();
        String line = sign.getLine(1);
        this.playerNum = e.getClickedBlock().getWorld().getPlayers().size();
        this.status = "playing";
        this.countDown(line);
    }
    public void countDown(String courseName){
        Location location = null;
        World world = Bukkit.getWorld("vsa");
        this.currentCourse = courseName;
        if(courseName.equals("1")){
            location = new Location(world,-310,90,-82);
            new Location(world,-306,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-307,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-308,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-309,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-310,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-311,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-312,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-313,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-314,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-315,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-316,90,-86).getBlock().setType(Material.FENCE);
            new Location(world,-317,90,-86).getBlock().setType(Material.FENCE);
        }
        if(courseName.equals("2")){
            location = new Location(world,-396,106,-205);
            new Location(world,-405,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-404,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-403,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-402,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-401,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-400,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-399,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-398,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-397,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-396,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-395,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-394,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-393,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-392,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-391,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-390,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-389,106,-210).getBlock().setType(Material.FENCE);
            new Location(world,-388,106,-210).getBlock().setType(Material.FENCE);



        }

        if(location == null) {
            List<Player>players = world.getPlayers();
            for (Player p: players) {
                p.sendMessage("コースが見つかりません");
            }
        }else{
            List<Player>players = world.getPlayers();
            for (Player p : players) {
                p.teleport(location);
            }
        }
        new BukkitRunnable() {
            int c = 10;
            @Override
            public  void run() {
                List<Player>players = world.getPlayers();
                for(Player p: players) {
                    p.sendTitle(String.valueOf(c),"",0,20,0);
                }
                c--;
                if (c <= 0) {
                    this.cancel();
                    start();
                }
            }
        }.runTaskTimer(this.plugin,0,20);
    }

    public  void start() {
        World world = Bukkit.getWorld("vsa");
        if(this.currentCourse.equals("1")) {
            new Location(world,-306,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-307,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-308,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-309,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-310,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-311,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-312,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-313,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-314,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-315,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-316,90,-86).getBlock().setType(Material.AIR);
            new Location(world,-317,90,-86).getBlock().setType(Material.AIR);

        }
        if(this.currentCourse.equals("2")) {
            new Location(world,-405,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-404,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-403,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-402,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-401,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-400,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-399,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-398,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-397,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-396,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-395,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-393,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-392,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-391,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-390,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-389,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-388,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-387,106,-210).getBlock().setType(Material.AIR);
            new Location(world,-386,106,-210).getBlock().setType(Material.AIR);


        }
    }

    public void done(Player player) {
        // player.sendTitle("CLEAR!!", "おめでとう！", 20, 20, 20);
        // player.teleport(this.lobbyLocation);
        // タイトルを表示
        player.sendTitle("終了！", "一位は" + player.getDisplayName() + "さん", 20, 20,20);
        World world = player.getWorld();
        Location lobbyLocation = this.lobbyLocation;


        if (this.status.equals("playing")) {
            new BukkitRunnable() {
                @Override
                public  void run() {
                    List<Player> players = world.getPlayers();
                    for (Player p: players) {
                        p.teleport(lobbyLocation);
                    }
                }
            }.runTaskLater(this.plugin,100);
        }
        this.status = "cleared";
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (event.getPlayer().getWorld().getName().equals(this.worldName)) {
            if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChangeEvent(FoodLevelChangeEvent event) {
        String worldname = event.getEntity().getName();
        if (worldName.equals(this.worldName)) {
            event.setCancelled(true);
            return;
        }
    }

    // 爆発
    @EventHandler
    public void onExplosionPrimeEvent(ExplosionPrimeEvent e) {
        if (worldName.equals(this.worldName)) {
            return;
        }
        e.setCancelled(true);
    }
}
