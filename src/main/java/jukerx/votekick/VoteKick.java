package jukerx.votekick;

import jukerx.votekick.commands.VoteKickCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class VoteKick extends JavaPlugin implements Listener {
    private static VoteKick plugin;

    public static String VoteTarget; // The player who is being vote kicked
    public static String VoteHost; // The player who started the vote kick
    public static List<String> VotedPlayers = new ArrayList<>(); // Players who voted
    public static List<String> VoteYes = new ArrayList<>(); // Players who voted Yes
    public static List<String> VoteNo = new ArrayList<>(); // Players who voted No
    public static List<String> VoteBanned = new ArrayList<>(); // Vote Banned players
    public static boolean VoteStarted = false;

    @Override
    public void onEnable() {
        getCommand("votekick").setExecutor(new VoteKickCommand());
        getCommand("votekick").setTabCompleter(new TabCompletion());

        plugin = this;

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage("§a        VoteKick plugin §bv1.0");
        Bukkit.getConsoleSender().sendMessage("§e           Made by JuKer");
        Bukkit.getConsoleSender().sendMessage("");

        VoteBanned.clear();

        new Delay(this);
    }

    @Override
    public void onDisable() {
        VoteBanned.clear();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        if (VoteBanned.contains(e.getPlayer().getName())) {
            e.getPlayer().kickPlayer(colorize(getConfig().getStringList("Vote Kick Message").toString()));
            Bukkit.broadcastMessage(colorize("&f" + e.getPlayer().getName() + " &atried to join but they were recently vote kicked."));
        }
    }

    public static void EndVotekick() {
        VoteTarget = null;
        VoteHost = null;
        VotedPlayers.clear();
        VoteYes.clear();
        VoteNo.clear();
        VoteStarted = false;
    }

    public static VoteKick getPlugin() {
        return plugin;
    }
    public static String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
