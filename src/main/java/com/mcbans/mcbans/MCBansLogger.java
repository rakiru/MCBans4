package com.mcbans.mcbans;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adds a debug method, and prefixes all log messages with [MCBans]
 *
 * @author Sean
 */
public class MCBansLogger {

	public static final Logger logger = Logger.getLogger("Minecraft");
	private final MCBansPlugin plugin;

	public MCBansLogger(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Debug level
	 *
	 * @param s Message to log
	 * @param write Whether or not to write to MCBans log
	 */
	public void debug(String s, boolean write) {
		if (plugin.config.isDebug()) {
			logger.log(Level.INFO, "[MCBans DEBUG] " + s);
		}
		if (write) {
			write(s);
		}
	}

	/**
	 * Debug level - writes to MCBans log
	 * 
	 * @param s Message to log
	 */
	public void debug(String s) {
		debug(s, true);
	}

	/**
	 * Info level
	 *
	 * @param s Message to log
	 * @param write Whether or not to write to MCBans log
	 */
	public void info(String s, boolean write) {
		logger.log(Level.INFO, "[MCBans] " + s);
		if (write) {
			write(s);
		}
	}

	/**
	 * Info level - writes to MCBans log
	 *
	 * @param s Message to log
	 */
	public void info(String s) {
		info(s, true);
	}

	/**
	 * Warning level
	 *
	 * @param s Message to log
	 * @param write Whether or not to write to MCBans log
	 */
	public void warning(String s, boolean write) {
		logger.log(Level.WARNING, "[MCBans] " + s);
		if (write) {
			write(s);
		}
	}

	/**
	 * Warning level - writes to MCBans log
	 *
	 * @param s Message to log
	 */
	public void warning(String s) {
		warning(s, true);
	}

	/**
	 * Severe level
	 *
	 * @param s Message to log
	 * @param write Whether or not to write to MCBans log
	 */
	public void severe(String s, boolean write) {
		logger.log(Level.SEVERE, "[MCBans] " + s);
		if (write) {
			write(s);
		}
	}

	/**
	 * Warning level - writes to MCBans log
	 *
	 * @param s Message to log
	 */
	public void severe(String s) {
		severe(s, true);
	}

	/**
	 * Write message to file
	 * @param s
	 */
	public void write(String s) {
		//Not implemented yet
	}
}
