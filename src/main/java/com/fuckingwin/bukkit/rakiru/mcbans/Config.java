/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fuckingwin.bukkit.rakiru.mcbans;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * @author Sean Gordon (rakiru)
 */
public class Config {

    private MCBansPlugin plugin;
    private File configFile;
    private YamlConfiguration config;

    public Config(MCBansPlugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        plugin.reloadConfig();
    }

    public String getKey() {
        return plugin.getConfig().getString("api-key", "API-KEY-HERE");
    }

    public String getDefaultLocalBanReason() {
        return plugin.getConfig().getString("default-reasons.local-ban", "You have been banned");
    }

    public String getDefaultTempBanReason() {
        return plugin.getConfig().getString("default-reasons.temp-ban", "You have been temporarily banned");
    }

    public String getDefaultKickReason() {
        return plugin.getConfig().getString("default-reasons.kick", "You have been kicked");
    }

    public boolean isDebug() {
        return plugin.getConfig().getBoolean("logging.debug-mode", false);
    }

    public boolean isLoggingActions() {
        return plugin.getConfig().getBoolean("logging.log-actions", true);
    }

    public String getLogFile() {
        return plugin.getConfig().getString("logging.log-file", "plugins/mcbans/actions.log");
    }

    public boolean isShowingJoinMessage() {
        return plugin.getConfig().getBoolean("on-join-mcbans-message", true);
    }

    public int getMinimumRep() {
        int minRep = plugin.getConfig().getInt("minimum-rep", 8);
        if (minRep > 10) minRep = 10;
        return minRep;
    }

    public int getCallbackInterval() {
        int callbackInterval = plugin.getConfig().getInt("callback-interval", 900000);
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
        return plugin.getConfig().getBoolean("alt-limit.enable-maximum-alts", false);
    }

    public int getMaximumAltLimit() {
        return plugin.getConfig().getInt("alt-limit.maximum-alts", 3);
    }

    // User connection throttling stuff

    public boolean isThrottlingUsers() {
        return plugin.getConfig().getBoolean("user-connection-throttle.enable-throttle", true);
    }

    public int getUserConnectionTime() {
        int time = plugin.getConfig().getInt("user-connection-throttle.connection-time-limit", 20);
        if (time < 1) time = 10;
        return time;
    }

    public int getUserConnectionCount() {
        int count = plugin.getConfig().getInt("user-connection-throttle.connection-count-limit", 2);
        if (count < 1) count = 1;
        return count;
    }

    public String getUserLockoutMessage() {
        return plugin.getConfig().getString("user-connection-throttle.lockout-message", "Connecting too quickly. Please wait a few minutes.");
    }

    public int getUserLockoutTime() {
        int time = plugin.getConfig().getInt("user-connection-throttle.lockout-time", 60);
        if (time < 1) time = 20;
        return time;
    }

    // Server connection throttling stuff

    public boolean isThrottlingServer() {
        return plugin.getConfig().getBoolean("server-connection-throttle.enable-throttle", true);
    }

    public int getServerConnectionTime() {
        int time = plugin.getConfig().getInt("server-connection-throttle.connection-time-limit", 15);
        if (time < 1) time = 10;
        return time;
    }

    public int getServerConnectionCount() {
        int count = plugin.getConfig().getInt("server-connection-throttle.connection-count-limit", 5);
        if (count < 1) count = 1;
        return count;
    }

    public String getServerLockoutMessage() {
        return plugin.getConfig().getString("server-connection-throttle.lockout-message", "Connecting too quickly. Please wait a few minutes.");
    }

    public int getServerLockoutTime() {
        int time = plugin.getConfig().getInt("server-connection-throttle.lockout-time", 20);
        if (time < 1) time = 10;
        return time;
    }
}
