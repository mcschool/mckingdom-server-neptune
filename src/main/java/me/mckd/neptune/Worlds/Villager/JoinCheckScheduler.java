package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class JoinCheckScheduler extends BukkitRunnable {

    Neptune plugin;
    World world;
    int count = 20;

    public JoinCheckScheduler (Neptune plugin, World world) {
        this.plugin = plugin;
        this.world = world;
    }

    @Override
    public void run() {
        List<Player> players = this.world.getPlayers();
        for (Player p: players) {
            p.sendMessage(String.valueOf(this.count));
        }

        if (this.count < 1) {
            int playerCount = players.size();
            if (playerCount > 1) {
                VillagerWorld v = new VillagerWorld(plugin);
                for (int i=0; i < players.size(); i++) {
                    Player p = players.get(i);
                    Location location;
                    // チームRED
                    if (i % 4 == 0) {
                        location = new Location(this.world, 0, 4, 0);
                        this.setHelmet(p, Color.RED);
                        p.teleport(location);
                    }
                    // チームBLUE
                    if (i % 4 == 1) {
                        location = new Location(this.world, 0, 4, 0);
                        this.setHelmet(p, Color.BLUE);
                        p.teleport(location);
                    }
                    // チームGREEN
                    if (i % 4 == 2) {
                        location = new Location(this.world, 0, 4, 0);
                        this.setHelmet(p, Color.GREEN);
                        p.teleport(location);
                    }
                    // チームYELLOW
                    if (i % 4 == 3) {
                        location = new Location(this.world, 0, 4, 0);
                        this.setHelmet(p, Color.YELLOW);
                        p.teleport(location);
                    }
                }
            } else {
                for (Player p : players) {
                    p.sendMessage("まだ集まっていません...");
                }
            }
            // スケジューラー終了
            this.cancel();
        }
    }

    public void setHelmet(Player player, Color color) {
        // ヘルメット準備
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        // ヘルメットのメタ情報準備
        LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
        // カラー設定
        meta.setColor(color);
        // ヘルメットにメタ情報セット
        helmet.setItemMeta(meta);
        //
        player.getEquipment().setHelmet(helmet);
    }

}
