package com.mcbans.mcbans;

import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author Sean Gordon (rakiru)
 */
public class Config {

	private MCBansPlugin plugin;
	private File configFile;
	private FileConfiguration config;

	public Config(MCBansPlugin plugin) {
		this.plugin = plugin;
		//this.config = plugin.getConfig();
	}

	public void load() {
		plugin.reloadConfig();
		config = plugin.getConfig();
		config.addDefault("api-key", "API-KEY-HERE");
		config.addDefault("default-reasons.local-ban", "You have been banned");
		config.addDefault("default-reasons.temp-ban", "You have been temporarily banned");
		config.addDefault("default-reasons.kick", "You have been kicked");
		config.addDefault("logging.debug-mode", false);
		config.addDefault("logging.log-actions", true);
		config.addDefault("logging.log-file", "plugins/mcbans/actions.log");
		config.addDefault("on-join-mcbans-message", true);
		config.addDefault("minimum-rep", 8);
		config.addDefault("callback-interval", 900000);
		config.addDefault("alt-limit.enable-maximum-alts", false);
		config.addDefault("alt-limit.maximum-alts", 3);
		config.addDefault("user-connection-throttle.enable-throttle", true);
		config.addDefault("user-connection-throttle.connection-time-limit", 20);
		config.addDefault("user-connection-throttle.connection-count-limit", 2);
		config.addDefault("user-connection-throttle.lockout-message", "Connecting too quickly. Please wait a few minutes.");
		config.addDefault("user-connection-throttle.lockout-time", 60);
		config.addDefault("server-connection-throttle.enable-throttle", true);
		config.addDefault("server-connection-throttle.connection-time-limit", 15);
		config.addDefault("server-connection-throttle.connection-count-limit", 5);
		config.addDefault("server-connection-throttle.lockout-message", "Connecting too quickly. Please wait a few minutes.");
		config.addDefault("server-connection-throttle.lockout-time", 20);
		config.options().copyDefaults();
		plugin.saveConfig();
	}

	public String getKey() {
		return config.getString("api-key");
	}

	public String getDefaultLocalBanReason() {
		return config.getString("default-reasons.local-ban");
	}

	public String getDefaultTempBanReason() {
		return config.getString("default-reasons.temp-ban");
	}

	public String getDefaultKickReason() {
		return config.getString("default-reasons.kick");
	}

	public boolean isDebug() {
		return config.getBoolean("logging.debug-mode");
	}

	public boolean isLoggingActions() {
		return config.getBoolean("logging.log-actions");
	}

	public String getLogFile() {
		return config.getString("logging.log-file");
	}

	public boolean isShowingJoinMessage() {
		return config.getBoolean("on-join-mcbans-message");
	}

	public int getMinimumRep() {
		int minRep = config.getInt("minimum-rep");
		if (minRep > 10) {
			minRep = 10;
		}
		return minRep;
	}

	public int getCallbackInterval() {
		int callbackInterval = config.getInt("callback-interval");
		// Ensures callback interval is between 15 minutes and 1 hour
		if (callbackInterval < 60000) {
			callbackInterval = 60000;
		} else if (callbackInterval > 3600000) {
			callbackInterval = 3600000;
		}
		return callbackInterval;
	}

	// Alt account stuff
	public boolean isMaximumAltLimit() {
		return config.getBoolean("alt-limit.enable-maximum-alts");
	}

	public int getMaximumAltLimit() {
		return config.getInt("alt-limit.maximum-alts");
	}

	// User connection throttling stuff
	public boolean isThrottlingUsers() {
		return config.getBoolean("user-connection-throttle.enable-throttle");
	}

	public int getUserConnectionTime() {
		int time = config.getInt("user-connection-throttle.connection-time-limit");
		if (time < 1) {
			time = 10;
		}
		return time;
	}

	public int getUserConnectionCount() {
		int count = config.getInt("user-connection-throttle.connection-count-limit");
		if (count < 1) {
			count = 1;
		}
		return count;
	}

	public String getUserLockoutMessage() {
		return config.getString("user-connection-throttle.lockout-message");
	}

	public int getUserLockoutTime() {
		int time = config.getInt("user-connection-throttle.lockout-time");
		if (time < 1) {
			time = 20;
		}
		return time;
	}

	// Server connection throttling stuff
	public boolean isThrottlingServer() {
		return config.getBoolean("server-connection-throttle.enable-throttle");
	}

	public int getServerConnectionTime() {
		int time = config.getInt("server-connection-throttle.connection-time-limit");
		if (time < 1) {
			time = 10;
		}
		return time;
	}

	public int getServerConnectionCount() {
		int count = config.getInt("server-connection-throttle.connection-count-limit");
		if (count < 1) {
			count = 1;
		}
		return count;
	}

	public String getServerLockoutMessage() {
		return config.getString("server-connection-throttle.lockout-message");
	}

	public int getServerLockoutTime() {
		int time = config.getInt("server-connection-throttle.lockout-time");
		if (time < 1) {
			time = 10;
		}
		return time;
	}
}
