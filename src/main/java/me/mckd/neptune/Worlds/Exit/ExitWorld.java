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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
    private void start() {
        World world = Bukkit.getWorld("exit");
        List<Player> players =world.getPlayers();

        world.getBlockAt(new Location(world,-1023,4,-1117)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1004,4,-1135)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1001,4,-1087)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1031,5,-1091)).setType(Material.LOG_2);
        world.getBlockAt(new Location(world,-1053,4,-1087)).setType(Material.LOG_2);

        world.getBlockAt(new Location(world,-1026,4,-1141)).setType(Material.IRON_FENCE);
        world.getBlockAt(new Location(world,-1026,5,-1141)).setType(Material.IRON_FENCE);

        this.count = 5;

        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (i == 0) {
                player.setDisplayName("A");
                Location location = new Location(world, -1026, 5, -1138);
                player.teleport(location);
                this.setStatus(player);
            }else {
                player.setDisplayName("B");
                Location location = new Location(world,-1024,5,-1089);
                player.teleport(location);
                // 装備を整える関数を実行
                this.setEquipment(player);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if(!e.getEntity().getWorld().getName().equals("exit")) {
            return;
        }

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            Location location = new Location(e.getEntity().getWorld(),-997,29,-1080);

            player.teleport(location);
        }
    }

    public void setEquipment(Player player) {
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);

        player.getEquipment().setHelmet(helmet);
        player.getEquipment().setChestplate(chestPlate);
        player.getEquipment().setBoots(boots);
        player.getEquipment().setLeggings(leggings);
    }


    public void setStatus(Player player) {
    }
}
