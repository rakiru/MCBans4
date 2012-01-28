package com.mcbans.mcbans.commands;

import com.mcbans.mcbans.MCBansPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 *
 * @author rakiru
 */
public class MCBansCommand implements CommandExecutor {

	private final ChatColor COLOUR = ChatColor.GOLD;
	private final ChatColor COLOUR_ERROR = ChatColor.RED;
	private final ChatColor COLOUR_SUCCESS = ChatColor.GREEN;
	private final MCBansPlugin plugin;

	public MCBansCommand(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcbans.mcbans")) {
			sender.sendMessage(plugin.lang.getFormat("permissionDenied"));
			return true;
		}

		//Show usage if empty command
		if (args.length == 0) {
			return false;
		}

		if (args[0].equalsIgnoreCase("version")) {
			sender.sendMessage("MCBans version " + plugin.getDescription().getVersion());
			sender.sendMessage("BUILD: " + MCBansPlugin.BUILD_NUMBER);
			sender.sendMessage("GIT REV: " + MCBansPlugin.GIT_REVISION);
		} else if (args[0].equalsIgnoreCase("online")) {
			plugin.setOnline(true);
			sender.sendMessage(COLOUR + "MCBans is now running in online mode.");
		} else if (args[0].equalsIgnoreCase("offline")) {
			plugin.setOnline(false);
			sender.sendMessage(COLOUR + "MCBans is now running in offline mode.");
		} else if (args[0].equalsIgnoreCase("status")) {
			sender.sendMessage(COLOUR + "MCBans is currently running in " + (plugin.isOnline() ? COLOUR_SUCCESS + "online" : COLOUR_ERROR + "offline") + COLOUR + " mode.");
		} else if (args[0].equalsIgnoreCase("reload")) {
			if (args.length < 2) {
				sender.sendMessage(COLOUR_ERROR + "You must specify what you want to reload");
				return false;
			}
			if (args[1].equalsIgnoreCase("settings") || args[1].equalsIgnoreCase("config") || args[1].equalsIgnoreCase("conf")) {
				plugin.reloadConfig();
			} else if (args[1].equalsIgnoreCase("language") || args[1].equalsIgnoreCase("lang")) {
				plugin.lang.reload();
			} else {
				sender.sendMessage(COLOUR_ERROR + "Available options to reload: settings, language");
			}
		} else if (args[0].equalsIgnoreCase("magic")) {
			sender.sendMessage(ChatColor.MAGIC + "Indeed.");
		} else {
			return false;
		}

		// Everything went fine! \o/
		return true;
	}
}
