package jukerx.votekick.commands.subcommands;

import jukerx.votekick.commands.SubCommand;
import org.bukkit.entity.Player;

import static jukerx.votekick.VoteKick.*;

public class No extends SubCommand {
    @Override
    public String getName() {
        return "no";
    }

    @Override
    public String getDescription() {
        return "Vote no";
    }

    @Override
    public String getUsage() {
        return "/vk no";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (VoteStarted) {
            if (VoteTarget.equalsIgnoreCase(player.getName())) {
                player.sendMessage(colorize("&cYou cannot vote for yourself!"));
            } else {
                for (String votedPlayers : VotedPlayers) {
                    if (!votedPlayers.contains(player.getName())) {
                        VotedPlayers.add(player.getName());
                        VoteNo.add(player.getName());
                        player.sendMessage(colorize("&aYou have voted no to kick &f" + VoteTarget + "&a."));
                    } else {
                        player.sendMessage(colorize("&cYou have already voted!"));
                    }
                }
            }
        } else {
            player.sendMessage(colorize("&cThere is not an active vote kick at the moment!"));
        }
    }
}
