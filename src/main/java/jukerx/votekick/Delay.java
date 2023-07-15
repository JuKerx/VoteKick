package jukerx.votekick;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Delay implements Listener {
    private static Plugin plugin = null;
    private int id = -1;

    public Delay(Plugin instance) {
        plugin = instance;
    }

    public Delay(Runnable runnable) {
        this(runnable, 0);
    }

    public Delay(Runnable runnable, long delay) {
        if (plugin.isEnabled()) {
            id = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
        } else {
            runnable.run();
        }
    }
}
