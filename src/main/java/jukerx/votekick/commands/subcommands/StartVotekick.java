package jukerx.votekick.commands.subcommands;

import jukerx.votekick.Delay;
import jukerx.votekick.VoteKick;
import jukerx.votekick.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static jukerx.votekick.VoteKick.*;

public class StartVotekick extends SubCommand implements Listener {
    @Override
    public String getName() {
        return "start";
    }
    @Override
    public String getDescription() {
        return "Start a vote kick on someone";
    }
    @Override
    public String getUsage() {
        return "/vk start (player)";
    }

    @Override
    public void execute(Player player, String[] args) {
        if (args.length > 1) {
            Player target = Bukkit.getPlayer(args[1]);
            String playerName = player.getName();
            if (args[1].equalsIgnoreCase(playerName)) {
                player.sendMessage(colorize("&cYou cannot vote kick yourself!"));
            } else if (!VoteStarted) { // if vote kick hasn't started yet
                if (target != null) {
                    String targetName = target.getName();
                    if (VoteTarget == null) { // If vote target is already being kicked (!VoteTarget.equalsIgnoreCase(targetName))
                        VoteStarted = true;
                        VoteTarget = targetName;
                        VoteHost = playerName;
                        VotedPlayers.add(playerName);
                        VotedPlayers.add(targetName);
                        VoteYes.add(playerName);
                        VoteNo.add(targetName);
                        Bukkit.broadcastMessage(colorize("&a" + VoteHost + " has started a vote kick against " + VoteTarget));
                        Bukkit.broadcastMessage(colorize("&aType &e/vk yes &aor &e/vk no &ato vote."));
                        if (VoteKick.getPlugin().getConfig().getBoolean("Event Audio")) {
                            for (Player AllPlayers : Bukkit.getServer().getOnlinePlayers()) {
                                AllPlayers.playSound(AllPlayers.getLocation(), VoteKick.getPlugin().getConfig().getString("Event Sound"), (float) getPlugin().getConfig().getDouble("Volume"), (float) VoteKick.getPlugin().getConfig().getDouble("Pitch"));
                            }
                        }
                        while (VoteStarted) {
                            if (VoteKick.getPlugin().getConfig().getBoolean("Countdown Warning")) {
                                new Delay(() -> {
                                    for (Player AllPlayers : Bukkit.getServer().getOnlinePlayers()) {
                                        AllPlayers.sendActionBar(colorize("&c30 seconds left to vote!"));
                                        if (VoteKick.getPlugin().getConfig().getBoolean("Event Audio")) {
                                            AllPlayers.playSound(AllPlayers.getLocation(), VoteKick.getPlugin().getConfig().getString("Event Sound"), (float) getPlugin().getConfig().getDouble("Volume"), (float) VoteKick.getPlugin().getConfig().getDouble("Pitch"));
                                        }
                                    }
                                }, 20 * VoteKick.getPlugin().getConfig().getInt("Countdown") / 2);
                                break;
                            }
                            if (VoteYes.size() > VoteNo.size()) {
                                new Delay(() -> {
                                    target.kickPlayer(colorize(VoteKick.getPlugin().getConfig().getString("Vote Kick Message")));
                                    Bukkit.broadcastMessage(colorize("&aVote kick ended " + VoteYes.size() + " to " + VoteNo.size() + "! &7" + VoteTarget + " &ahas been kicked."));
                                    if (VoteKick.getPlugin().getConfig().getBoolean("Event Audio")) {
                                        for (Player AllPlayers : Bukkit.getServer().getOnlinePlayers()) {
                                            AllPlayers.playSound(AllPlayers.getLocation(), VoteKick.getPlugin().getConfig().getString("Event Sound"), (float) getPlugin().getConfig().getDouble("Volume"), (float) VoteKick.getPlugin().getConfig().getDouble("Pitch"));
                                        }
                                    }
                                    VoteBanned.add(VoteTarget);
                                    EndVotekick();
                                }, 20 * VoteKick.getPlugin().getConfig().getInt("Countdown"));
                                new Delay(() -> {
                                    VoteBanned.clear();
                                    Bukkit.broadcastMessage(colorize("&f" + VoteTarget + " &ais now able to join!"));
                                }, 20 * VoteKick.getPlugin().getConfig().getInt("Vote Ban Duration"));
                            } else {
                                new Delay(() -> {
                                    Bukkit.broadcastMessage(colorize("&aVote kick failed " + VoteYes.size() + " to " + VoteNo.size() + "! &f" + VoteTarget + " &ahas not been kicked."));
                                    if (VoteKick.getPlugin().getConfig().getBoolean("Event Audio")) {
                                        for (Player AllPlayers : Bukkit.getServer().getOnlinePlayers()) {
                                            AllPlayers.playSound(AllPlayers.getLocation(), VoteKick.getPlugin().getConfig().getString("Event Sound"), (float) getPlugin().getConfig().getDouble("Volume"), (float) VoteKick.getPlugin().getConfig().getDouble("Pitch"));
                                        }
                                    }
                                    EndVotekick();
                                }, 20 * VoteKick.getPlugin().getConfig().getInt("Countdown"));
                            }
                            break;
                        }
                    }
                } else {
                    player.sendMessage(colorize("&cPlease specify an online player!"));
                }
            } else {
                if (target != null) {
                    if (!VoteTarget.equalsIgnoreCase(target.getName())) {
                        player.sendMessage(colorize("&cThere is already an active vote kick!"));
                    } else {
                        player.sendMessage(colorize("&cYou have already started a vote kick on this player!"));
                    }
                } else {
                    if (!VoteStarted) {
                        player.sendMessage(colorize("&cPlease specify an online player!"));
                    } else {
                        player.sendMessage(colorize("&cThere is already an active vote kick!"));
                    }
                }
            }
        } else {
            player.sendMessage(colorize("&cPlease specify a player!"));
        }
    }
}
