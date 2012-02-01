package com.mcbans.mcbans;

import java.util.HashMap;

/**
 *
 * @author Sean
 */
public enum BanType {

	GLOBAL_BAN ('g', "globalBan", "mcbans.ban.global"),
	LOCAL_BAN ('l', "localBan",  "mcbans.ban.local"),
	TEMP_BAN ('t', "tempBan",  "mcbans.ban.temp"),
	UNBAN ('u', "unBan",  "mcbans.ban.unban");

	private final char code;
	private final String apiAction;
	private final String permission;
	private static final HashMap<Character, BanType> flags = new HashMap<Character, BanType>();

	private BanType(char code, String apiAction, String permission) {
		this.code = code;
		this.apiAction = apiAction;
		this.permission = permission;
	}

	public char getChar() {
		return code;
	}

	public String getPermission() {
		return permission;
	}

	public String getAPICode() {
		return apiAction;
	}

	public static BanType getByChar(char code) {
		return flags.get(code);
	}

    static {
        for (BanType flag : BanType.values()) {
            flags.put(flag.getChar(), flag);
        }
    }
}
