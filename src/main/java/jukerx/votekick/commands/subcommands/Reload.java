package jukerx.votekick.commands.subcommands;

import jukerx.votekick.VoteKick;
import jukerx.votekick.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.scanner.ScannerException;

import static jukerx.votekick.VoteKick.VoteBanned;
import static jukerx.votekick.VoteKick.colorize;

public class Reload extends SubCommand {
    @Override
    public String getName() {
        return "reload";
    }
    @Override
    public String getDescription() {
        return "Reload the configuration file";
    }
    @Override
    public String getUsage() {
        return "/vk reload";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (player.hasPermission("votekick.reload") || player.hasPermission("votekick.*")) {
            try {
                VoteKick.getPlugin().reloadConfig();
                player.sendMessage(colorize("&aSuccessfully reloaded configuration file!"));
            } catch (ScannerException e) {
                e.printStackTrace();
                player.sendMessage(colorize("&cYour configuration file has errors!"));
            }
        } else {
            player.sendMessage(colorize("&cYou don't have permission to use this command!"));
        }
    }
}