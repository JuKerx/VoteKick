package jukerx.votekick.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getName();
    public abstract String getDescription();
    public abstract String getUsage();
    public abstract void execute(Player player, String args[]);
}
