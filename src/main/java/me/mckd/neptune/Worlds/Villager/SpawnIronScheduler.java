package me.mckd.neptune.Worlds.Villager;

import me.mckd.neptune.Neptune;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnIronScheduler extends BukkitRunnable {
    Neptune plugin;
    World world;
    int count = 0;

    public SpawnIronScheduler(Neptune plugin,World world){
        this.plugin=plugin;
        this.world=world;
    }

    @Override
    public void run(){
    }

}
