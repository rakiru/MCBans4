package com.mcbans.mcbans;

import com.mcbans.mcbans.api.MCBansPluginManager;
import com.mcbans.mcbans.commands.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;

/**
 * MCBans 4 main plugin
 *
 * @author Sean Gordon (rakiru)
 */
public class MCBansPlugin extends JavaPlugin {

	public static final String BUILD_NUMBER = "@@BUILDVERSION@@";
	public static final String GIT_REVISION = "@@GITREVISION@@";
	public final Config config = new Config(this);
	public final MCBansLogger log = new MCBansLogger(this);
	public final MCBansPluginManager pluginManager = new MCBansPluginManager(this);
	public final Language lang = new Language();
	private boolean isOnline = true;

	public void onDisable() {
		// Output to console that plugin is disabled
		PluginDescriptionFile pdfFile = this.getDescription();
		System.out.println(pdfFile.getName() + " version " + pdfFile.getVersion() + " disabled!");
	}

	public void onEnable() {
		// Load config
		config.load();

		// Setup logger
		log.setup();

		// Get plugin info from plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("Using bukkit permissions");

		// Register commands
		getCommand("mcbans").setExecutor(new MCBansCommand(this));
		getCommand("lookup").setExecutor(new LookupCommand(this));
		getCommand("ban").setExecutor(new BanCommand(this));
		getCommand("unban").setExecutor(new UnbanCommand(this));
		getCommand("kick").setExecutor(new KickCommand(this));

		// Output to console that plugin is enabled
		log.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " enabled!");
	}

	/**
	 * Gets the state of the MCBans plugin
	 * @return Whether the plugin is in online mode or not
	 */
	public boolean isOnline() {
		return isOnline;
	}

	/**
	 * Sets the state of the MCBans plugin
	 * @param isOnline Whether the plugin is in online mode or not
	 */
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

	public boolean checkPermission(CommandSender player, String permission) {
		if (player.hasPermission(permission)) {
			return true;
		} else {
			player.sendMessage(lang.getFormat("permissionDenied"));
			return false;
		}
	}
}
