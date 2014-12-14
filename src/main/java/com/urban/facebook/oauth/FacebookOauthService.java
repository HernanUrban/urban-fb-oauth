package com.urban.facebook.oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.urban.facebook.oauth.user.FacebookAuthUser;

/**
 * Facebook implementation of {@link OauthService} interface.
 * 
 * @author hernan.urban
 * 
 */
public class FacebookOauthService implements OauthService {
	private String appId;
	private String appSecret;

	/**
	 * Default constructor.
	 */
	public FacebookOauthService() {
	}

	/**
	 * Parameterized constructor. 
	 * @param applicationId the Facebook application ID.
	 * @param applicationSecret the Facebook apllication Secret.
	 */
	public FacebookOauthService(String applicationId, String applicationSecret) {
		this.appId = applicationId;
		this.appSecret = applicationSecret;
	}

	@Override
	public String getOauthAuthorizationUrl(String returnUrl) {
		String fbLoginUrl = null;
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ this.appId + "&redirect_uri="
					+ URLEncoder.encode(returnUrl, "UTF-8") + "&scope=email";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return fbLoginUrl;
	}

	@Override
	public String getOauthAccessToken(String oauthCode, String returnUrl) {
		URL fbGraphUrl = null;
		String accessToken = null;
		String url = null;
		try {
			url = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + this.appId + "&redirect_uri="
					+ URLEncoder.encode(returnUrl, "UTF-8") + "&client_secret="
					+ this.appSecret + "&code=" + oauthCode;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			fbGraphUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		StringBuffer b = null;
		URLConnection fbConnection;
		try {
			fbConnection = fbGraphUrl.openConnection();
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(
					fbConnection.getInputStream()));
			String inputLine;
			b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Unable to connect with Facebook " + e);
		}

		accessToken = b.toString();
		if (accessToken.startsWith("{")) {
			throw new RuntimeException("ERROR: Access Token Invalid: "
					+ accessToken);
		}
		return accessToken;
	}

	@Override
	public String getOauthLogoutUrl(String accessToken, String returnUrl) {
		String logoutUrl = null;
		try {
			logoutUrl = "http://www.facebook.com/logout.php?next="
					+ URLEncoder.encode(returnUrl, "UTF-8") + "&access_token="
					+ accessToken;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return logoutUrl;
	}

	@Override
	public FacebookAuthUser authenticateUser(String accessToken) {
		String fbGraph = getFBGraph(accessToken);
		Gson gson = new Gson();
		FacebookAuthUser fbProfile = gson.fromJson(fbGraph, FacebookAuthUser.class);
		fbProfile.setAccessToken(accessToken);

		return fbProfile;
	}

	/**
	 * Provides the Graph API response for a given user access token.
	 * @param accessToken the user access token.
	 * @return the Facebook Graph API serialized response.
	 */
	private String getFBGraph(String accessToken) {
		String graph = null;
		URLConnection fbConnection;
		try {

			String g = "https://graph.facebook.com/me?" + accessToken;
			URL u = new URL(g);
			fbConnection = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					fbConnection.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
			System.out.println(graph);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("ERROR getting FB graph data. " + e);
		}
		return graph;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

}
