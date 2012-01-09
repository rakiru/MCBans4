package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.MCBansPlugin;
import com.mcbans.mcbans.calls.Lookup;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author rakiru
 */
public class LookupCommand implements CommandExecutor {

	private final MCBansPlugin plugin;
	private static final Pattern pattern = Pattern.compile("^\\w{3,16}$");

	public LookupCommand(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcbans.lookup")) {
			sender.sendMessage(plugin.lang.getFormat("permissionDenied"));
			return true;
		}

		//lookup only supports one argument, a player name
		if (args.length != 1) {
			//Show usage
			return false;
		}
		//If plugin is in offline mode, no contact with server can happen
		if (!plugin.isOnline()) {
			sender.sendMessage(plugin.lang.getFormat("pluginOfflineMode"));
			return true;
		}
		//No point creating a new thread for an invalid player name
		// Between 3 and 16 characters (inclusive) containing only upper- and lower-case letters and underscores
		if (!pattern.matcher(args[0]).matches()) {
			sender.sendMessage(plugin.lang.getFormat("invalidPlayerName"));
			return true;
		}

		plugin.log.info(sender.getName() + " has looked up " + args[0]);

		//Start new lookup thread - it will get back to the command user when it's ready
		Thread lookup = new Thread(new Lookup(plugin, args[0], sender, sender.getName()));
		lookup.start();

		return true;
	}
}
