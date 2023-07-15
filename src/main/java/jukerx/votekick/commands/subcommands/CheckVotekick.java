package jukerx.votekick.commands.subcommands;

import jukerx.votekick.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static jukerx.votekick.VoteKick.*;

public class CheckVotekick extends SubCommand {
    @Override
    public String getName() {
        return "check";
    }

    @Override
    public String getDescription() {
        return "Status of the ongoing vote kick";
    }

    @Override
    public String getUsage() {
        return "/vk check";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (player.hasPermission("votekick.check") || player.hasPermission("votekick.*")) {
            if (VoteStarted) {
                player.sendMessage(colorize("&6Target: &f" + VoteTarget));
                player.sendMessage(colorize("&eHost: &f" + VoteHost));
                player.sendMessage(colorize("&aYes: &f" + VoteYes.size()));
                player.sendMessage(colorize("&cNo: &f" + VoteNo.size()));
            } else {
                player.sendMessage(colorize("&cThere is not an active vote kick at the moment!"));
            }
        } else {
            player.sendMessage(colorize("&cYou don't have permission to use this command!"));
        }
    }
}
