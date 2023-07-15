package jukerx.votekick.commands;

import jukerx.votekick.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import static jukerx.votekick.VoteKick.colorize;

public class VoteKickCommand implements CommandExecutor {
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public VoteKickCommand(){
        subCommands.add(new StartVotekick()); // In the works
        subCommands.add(new Yes()); // Maybe finished?
        subCommands.add(new No()); // Maybe finished?
        subCommands.add(new CheckVotekick()); // Maybe finished?
        subCommands.add(new CancelVotekick()); // Completed
        subCommands.add(new Reload()); // More to add!
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).execute(p, args);
                    } else {
                        sender.sendMessage(colorize("&cUnrecognized argument! Try /votekick for a list of commands."));
                    }
                }
            } else if(args.length == 0) {
                p.sendMessage(colorize(""));
                for (int i = 0; i < getSubCommands().size(); i++) {
                    p.sendMessage(colorize("&a" + getSubCommands().get(i).getUsage() + " &7- &f" + getSubCommands().get(i).getDescription() + "&r"));
                }
                p.sendMessage(colorize(""));
            }
        } else {
            sender.sendMessage(colorize("&cYou must be a player to execute this command!"));
        }
        return true;
    }

    private ArrayList<SubCommand> getSubCommands(){
        return subCommands;
    }
}
