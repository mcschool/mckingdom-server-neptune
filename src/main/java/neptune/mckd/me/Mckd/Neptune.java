package neptune.mckd.me.Mckd;

import neptune.mckd.me.Mckd.Worlds.LobbyWorld;
import org.bukkit.plugin.java.JavaPlugin;

public final class Neptune extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        new LobbyWorld(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
