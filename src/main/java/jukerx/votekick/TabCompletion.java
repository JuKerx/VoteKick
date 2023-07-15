package jukerx.votekick;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(final CommandSender sender, final Command command, final String alias, final String[] args) {
        Player p = (Player) sender;
        if (args.length == 1) {
            List<String> SubCommands = new ArrayList<>();
            SubCommands.add("start");
            SubCommands.add("yes");
            SubCommands.add("no");
            SubCommands.add("cancel");
            if (p.hasPermission("votekick.reload") || p.hasPermission("votekick.*")) {
                SubCommands.add("reload");
            }
            if (p.hasPermission("votekick.check") || p.hasPermission("votekick.*")) {
                SubCommands.add("check");
            }
            return SubCommands;
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("start")) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (int i = 0; i < players.length; i++) {
                    playerNames.add(players[i].getName());
                }
                return playerNames;
            } else {
                return Collections.emptyList();
            }
        }
        return null;
    }
}
