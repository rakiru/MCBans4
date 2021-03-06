package com.mcbans.mcbans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

/**
 * Adds a debug method, and prefixes all log messages with [MCBans]
 *
 * @author Sean
 */
public class MCBansLogger {

	public static final Logger logger = Logger.getLogger("Minecraft");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
	private final MCBansPlugin plugin;
	private File logFile;

	public MCBansLogger(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	public void setup() {
		logFile = new File(plugin.config.getLogFile());
	}

	/**
	 * Log actions (bans, etc.)
	 *
	 * @param s Message to log
	 * @param write Whether or not to write to MCBans log
	 */
	public void action(String s, boolean write) {
		if (plugin.config.isLoggingActions()) {
			logger.log(Level.INFO, "[MCBans] " + s);
			if (write) {
				write(s);
			}
		}
	}

	/**
	 * Log actions (bans, etc.) - writes to MCBans log
	 *
	 * @param s Message to log
	 */
	public void action(String s) {
		debug(s, true);
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
			if (write) {
				write("[DEBUG] " + s);
			}
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
		try {
			PrintWriter writer = new PrintWriter(logFile);
			writer.print(dateFormat.format(new Date()));
			writer.print(' ');
			writer.println(s);
			writer.flush();
		} catch (FileNotFoundException ex) {
			warning("Log file not found", false);
		}
	}
}
