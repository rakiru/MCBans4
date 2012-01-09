package com.mcbans.mcbans;

import com.mcbans.mcbans.commands.*;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * MCBans 4 main plugin
 *
 * @author Sean Gordon (rakiru)
 */
public class MCBansPlugin extends JavaPlugin {

	public static final String BUILD_NUMBER = "${env.bambooBuildNumber}";
	public final MCBansLogger log = new MCBansLogger(this);
	public final Config config = new Config(this);
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

		// Get plugin info from plugin.yml
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("Using bukkit permissions");

		// Register commands
		getCommand("mcbans").setExecutor(new MCBansCommand(this));
		getCommand("lookup").setExecutor(new LookupCommand(this));
		getCommand("ban").setExecutor(new BanCommand(this));
		getCommand("unban").setExecutor(new UnbanCommand(this));
		getCommand("kick").setExecutor(new KickCommand(this));
		getCommand("tempban").setExecutor(new TempbanCommand(this));

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

	/**
	 * Ban a player using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param type Type of ban: "g" = global, "l" = local
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void banPlayer(String playerName, String type, String reason, String adminName) {
	}

	/**
	 * Ban a player locally using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void banPlayer(String playerName, String reason, String adminName) {
		banPlayer(playerName, "l", reason);
	}

	/**
	 * Ban a player locally using the MCBans system for the default ban reason
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void banPlayer(String playerName, String adminName) {
		banPlayer(playerName, "l", config.getDefaultLocalBanReason());
	}

	/**
	 * Ban a player for a specific time using the MCBans system
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param reason Reason for the ban
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, float time, String units, String reason, String adminName) {
	}

	/**
	 * Ban a player for a specific time using the MCBans system for the default ban reason
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param time Number of units the ban should last
	 * @param units Units of time to measure in: "m" = minutes, "h" = hours, "d" = days
	 * @param adminName Name to be attached as player who placed the ban
	 */
	public void tempBanPlayer(String playerName, float time, String units, String adminName) {
		tempBanPlayer(playerName, time, units, config.getDefaultTempBanReason(), adminName);
	}

	/**
	 * Kick a player using the MCBans system (the MCBans plugin will log the kick)
	 *
	 * @param playerName Full name of the player to be banned (no partial names)
	 * @param adminName Name to be attached as player who kicked the player
	 */
	public void kickPlayer(String playerName, String adminName) {
	}
}
