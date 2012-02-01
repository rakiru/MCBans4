package com.mcbans.mcbans.api;

/**
 *
 * @author Sean
 */
public interface FlagHandler {

	public String getPermission();
	public void run(String playerName, String adminName, String reason, String flags, String[] rawArgs);
}
