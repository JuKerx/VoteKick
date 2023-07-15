package jukerx.votekick.commands.subcommands;

import jukerx.votekick.VoteKick;
import jukerx.votekick.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import static jukerx.votekick.VoteKick.*;

public class CancelVotekick extends SubCommand {
    @Override
    public String getName() {
        return "cancel";
    }

    @Override
    public String getDescription() {
        return "Cancel the ongoing vote kick";
    }

    @Override
    public String getUsage() {
        return "/vk cancel";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (VoteStarted) {
            if (VoteHost.equalsIgnoreCase(player.getName())) {
                Bukkit.broadcastMessage(colorize("&aVote kick for &f" + VoteTarget + " &ahas been cancelled!"));
                EndVotekick();
                if (VoteKick.getPlugin().getConfig().getBoolean("Event Audio")) {
                    for (Player AllPlayers : Bukkit.getOnlinePlayers()) {
                        AllPlayers.playSound(AllPlayers.getLocation(), VoteKick.getPlugin().getConfig().getString("Event Sound"), (float) getPlugin().getConfig().getDouble("Volume"), (float) VoteKick.getPlugin().getConfig().getDouble("Pitch"));
                    }
                }
            } else {
                player.sendMessage(colorize("&cYou are not the host of the ongoing vote kick!"));
            }
        } else {
            player.sendMessage(colorize("&cThere is not an active vote kick at the moment!"));
        }
    }
}
