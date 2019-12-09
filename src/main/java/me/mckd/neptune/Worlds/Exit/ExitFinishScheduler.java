package me.mckd.neptune.Worlds.Exit;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ExitFinishScheduler extends BukkitRunnable {
    World world;
    int count = 10;

    public  ExitFinishScheduler(World world) {
        this.world = world;
    }


    @Override
    public void run() {
        List<Player> players = this.world.getPlayers();
        this.count--;
        if(count<= 0) {
            for (Player p: players) {
                p.performCommand("mvtp lobby");
            }
            this.cancel();
        }else{
            for (Player p: players) {
                p.sendTitle(String.valueOf(count),"",0,20,0);
            }
        }
    }
}
