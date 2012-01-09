package com.mcbans.mcbans.calls;

import com.mcbans.mcbans.MCBansPlugin;
import com.mcbans.mcbans.utils.JSONHandler;
import com.mcbans.mcbans.utils.PlayerMethods;
import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sean
 */
public class Lookup implements Runnable {

	private final MCBansPlugin plugin;
	private final String playerName;
	private final CommandSender sender;
	private final String senderName;

	public Lookup(MCBansPlugin plugin, String playerName, CommandSender sender, String senderName) {
		this.plugin = plugin;
		this.playerName = playerName;
		this.sender = sender;
		this.senderName = senderName;
	}

	public void run() {
		HashMap<String, String> urlItems = new HashMap<String, String>();
		JSONHandler webHandler = new JSONHandler(plugin);
		urlItems.put("player", playerName);
		urlItems.put("admin", senderName);
		urlItems.put("exec", "playerLookup");
		JSONObject result = webHandler.handleJob(urlItems);
		try {
			ArrayList<String> lines = new ArrayList();
			lines.add(plugin.lang.getFormat("lookupSummary", playerName, result.getString("total"), result.getString("reputation")));
			//Show global bans, if any
			if (result.getJSONArray("global").length() > 0) {
				lines.add(plugin.lang.getFormat("lookupGlobalBansHeader"));
				for (int i = 0; i < result.getJSONArray("global").length(); i++) {
					lines.add(result.getJSONArray("global").getString(i));
				}
			}
			//Show local bans, if any
			if (result.getJSONArray("local").length() > 0) {
				lines.add(plugin.lang.getFormat("lookupLocalBansHeader"));
				for (int i = 0; i < result.getJSONArray("local").length(); i++) {
					lines.add(result.getJSONArray("local").getString(i));
				}
			}

			//Send the lines to the command sender in the main thread
			PlayerMethods.message(plugin, sender, lines);
		} catch (JSONException ex) {
			plugin.log.debug(ex.toString());
		} catch (NullPointerException ex) {
			plugin.log.debug(ex.toString());
		}
	}
}
