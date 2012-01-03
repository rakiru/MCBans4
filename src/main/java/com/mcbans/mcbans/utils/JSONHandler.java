/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcbans.mcbans.utils;

import com.mcbans.mcbans.MCBansPlugin;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Sean
 */
@SuppressWarnings("unchecked")
public class JSONHandler {

	private MCBansPlugin plugin;
	private String apiKey;

	public JSONHandler(MCBansPlugin plugin) {
		this.plugin = plugin;
		apiKey = plugin.config.getKey();
	}

	public JSONObject getData(String json_text) {
		try {
			JSONObject json = new JSONObject(json_text);
			return json;
		} catch (JSONException ex) {
			plugin.log.debug(ex.toString());
		}
		return null;
	}

	public HashMap<String, String> mainRequest(HashMap<String, String> items) {
		HashMap<String, String> out = new HashMap<String, String>();
		String url_req = this.parseURL(items);
		String json_text = this.requestFromAPI(url_req);
		JSONObject output = this.getData(json_text);
		if (output != null) {

			Iterator<String> i = output.keys();
			if (i != null) {
				while (i.hasNext()) {
					String next = i.next();
					try {
						out.put(next, output.getString(next));
					} catch (JSONException ex) {
						plugin.log.debug(ex.toString());
					}
				}
			}
		}
		return out;
	}

	public JSONObject handleJob(HashMap<String, String> items) {
		String urlReq = parseURL(items);
		String jsonText = requestFromAPI(urlReq);
		JSONObject output = getData(jsonText);
		return output;
	}

	public String requestFromAPI(String data) {
		try {
			plugin.log.debug("Sending request!");
			URL url = new URL("http://72.10.39.172/v2/" + this.apiKey);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			connection.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(data);
			writer.flush();
			StringBuilder buffer = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			String result = buffer.toString();
			plugin.log.debug(result);
			writer.close();
			reader.close();
			return result;
		} catch (Exception ex) {
			plugin.log.debug("Fetch Data Error");
			plugin.log.debug(ex.toString());
		}
		return "";
	}

	public String parseURL(HashMap<String, String> items) {
		String data = "";
		try {
			for (Map.Entry<String, String> entry : items.entrySet()) {
				String key = entry.getKey();
				String val = entry.getValue();
				if (!data.equals("")) {
					data += "&";
				}
				data += URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(val, "UTF-8");
			}
		} catch (UnsupportedEncodingException ex) {
			plugin.log.debug(ex.toString());
		}
		return data;
	}
}
