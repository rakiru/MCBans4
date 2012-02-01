package com.mcbans.mcbans.api;

import com.mcbans.mcbans.MCBansPlugin;
import java.util.HashMap;

/**
 *
 * @author Sean
 */
public class MCBansPluginManager {

	private MCBansPlugin plugin;
	private HashMap<Character, FlagHandler> flags = new HashMap<Character, FlagHandler>();

	public MCBansPluginManager(MCBansPlugin plugin) {
		this.plugin = plugin;
	}

	public FlagHandler getHandler(char flag) {
		return flags.get(flag);
	}

	public FlagHandler registerHandler(char flag, FlagHandler handler) {
		return flags.put(flag, handler);
	}
}
