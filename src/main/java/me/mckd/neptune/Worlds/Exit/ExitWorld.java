package me.mckd.neptune.Worlds.Exit;

import me.mckd.neptune.Neptune;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class ExitWorld implements Listener {

    Neptune plugin;
    String worldName = "exit";
    int count = 5;

    public ExitWorld(Neptune plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerChangedWorldEvent(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        player.sendMessage("aaaaaa");
        if (!player.getWorld().getName().equals(this.worldName)) {
            return;
        }

        // 今ワールドにいるプレーヤーの人数を調べる
        List<Player> players = player.getWorld().getPlayers();
        int playerCount = players.size();
        String playerNum = String.valueOf(playerCount);
        for (Player p:players) {
            p.sendMessage("今"+playerNum + "人になった？");
        }
        // n人以上集まったらゲームを開始する
        if (playerCount >= 2) {
            this.start();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)  {
        Player player = e.getPlayer();
        if(!player.getWorld().getName().equals("exit")) {
            return;
        }
        if (e.getBlock().getType() == Material.LOG_2) {
            // 壊したブロックが原木だった場合
            e.setCancelled(true);
            Block block = e.getBlock();
            World world = block.getWorld();
            block.setType(Material.REDSTONE_BLOCK);
            // カウントを1減らす
            this.count--;
            List<Player> players = e.getBlock().getWorld().getPlayers();
            // 全プレーヤーにメッセージ
            for (Player p: players) {
                if (this.count == 0) {
                    p.sendTitle("全部壊した！", "", 20, 20, 20);
                } else {
                    p.sendTitle("あと" + String.valueOf(this.count) + "個", "", 20, 20, 20);
                }
            }
            // 原木を全部壊したら全員にメッセージ
            if (this.count == 0) {
                world.getBlockAt(new Location(world, -1026, 4, -1141)).setType(Material.AIR);
                world.getBlockAt(new Location(world, -1026, 5, -1141)).setType(Material.AIR);
            }
        }
    }

    // 11.25
    // ゲームを開始するプログラム
    private void start() {
        World world = Bukkit.getWorld("exit");
        List<Player> players = world.getPlayers();

        // レッドストーンに置き換わった原木を元に戻す
        world.getBlockAt(new Location(world, -1024, 4, -1118)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1004, 4, -1135)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1000, 4, -1086)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1030, 5, -1090)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world, -1052, 4, -1086)).setType(Material.LOG_2);

        // 鉄のフェンスを元に戻す
        world.getBlockAt(new Location(world, -1026, 4, -1141)).setType(Material.IRON_FENCE);
        world.getBlockAt(new Location(world, -1026, 5, -1141)).setType(Material.IRON_FENCE);
        // レッドストーンランプカウントリセット
        this.count = 5;

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0) {
                // 鬼
                player.setDisplayName("A");
                Location location = new Location(world, -1026, 5, -1138);
                player.teleport(location);
            } else {
                // 鬼じゃ無い方
                player.setDisplayName("B");
                Location location = new Location(world, -1024, 5, -1089);
                player.teleport(location);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(!e.getEntity().getWorld().getName().equals("exit")) {
            return;
        }
        // ダメージを受けたエンティティがプレーヤーの場合ろうやに入れる
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            // 牢屋の座標
            Location location = new Location(e.getEntity().getWorld(), -994, 31, -1138);
            // テレポートさせる
            player.teleport(location);
        }
    }



    // ブロック or 空中をクリック・右クリックした時の処理
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!player.getWorld().getName().equals("arrow")) {
            return;
        }
        // ブロックを右クリック
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            // エンダーチェストを右クリック
            if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                World world = player.getWorld();
                List<Player> players = world.getPlayers();
                for (Player p: players) {
                    p.sendTitle("Bの勝ち〜〜〜！", "", 40, 40, 20);
                    p.performCommand("mvtp lobby");
                }
            }
        }
    }

}
