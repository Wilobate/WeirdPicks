package com.wilobate.weirdPicks.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class Updater {
	private String result = "";
	private String url = "https://api.spigotmc.org/legacy/update.php?resource=";
	private String id = "56336";
	private String remoteVersion = "CANNOT RETREIVE.";

	public void versionChecker(String localVersion) {
		try {
			HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
			connection.setRequestMethod("GET");
			remoteVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

			String[] lVersion = localVersion.split(Pattern.quote("."));
			String lMajor = lVersion[0];
			String lMinor = lVersion[1];
			String lRelease = lVersion[2];

			String lVersionCombined = lMajor + lMinor + lRelease;

			int LV = Integer.parseInt(lVersionCombined);

			String[] rVersion = remoteVersion.split(Pattern.quote("."));
			String rMajor = rVersion[0];
			String rMinor = rVersion[1];
			String rRelease = rVersion[2];

			String rVersionCombined = rMajor + rMinor + rRelease;

			int RV = Integer.parseInt(rVersionCombined);

			if (LV < RV) {
				result = "UPDATE_AVAILABLE";
			} else {
				result = "LATEST";
			}
		} catch (IOException e) {
			result = "CONNECTION_FAILED";
		}
	}

	public String getVersion() {
		return remoteVersion;
	}

	public String getResult() {
		return result;
	}
}
