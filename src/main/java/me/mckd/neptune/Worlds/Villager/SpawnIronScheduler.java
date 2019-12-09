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

    public SpawnIronScheduler(Neptune plugin, World world) {
        this.plugin = plugin;
        this.world = world;
    }

    @Override
    public void run() {
        Location location = new Location(this.world, -822, 201, -355);
        this.world.dropItemNaturally(location, new ItemStack(Material.IRON_INGOT));


        Location location2 = new Location(this.world, -822, 201, -259);
        this.world.dropItemNaturally(location2, new ItemStack(Material.IRON_INGOT));


        Location location3 = new Location(this.world, -870, 201, -307);
        this.world.dropItemNaturally(location3, new ItemStack(Material.IRON_INGOT));

        Location location4 = new Location(this.world, -774, 201, -307);
        this.world.dropItemNaturally(location4, new ItemStack(Material.IRON_INGOT));

        if (this.count > 500) {
            this.cancel();
        }
    }
}