package com.urban.facebook.oauth;

import com.urban.facebook.oauth.user.FacebookAuthUser;

/**
 * Oauth interface to authenticate and logout a user.
 * @author hernan.urban
 *
 */
public interface OauthService {

	/**
	 * Generates the authorization URI for a given return URL
	 * @param returnUrl the return URL
	 * @return the Oauth authorization URL
	 */
	public String getOauthAuthorizationUrl(String returnUrl);

	/**
	 * Retrieves the access token for a given authorization code
	 * @param oauthCode the authorization code
	 * @param returnUrl the white-listed return URL
	 * @return the user Access Token
	 */
	public String getOauthAccessToken(String oauthCode, String returnUrl);

	/**
	 * Generates the Oauth logout URI for a given access token
	 * @param accessToken the access token
	 * @param returnUrl the white-listed return URL
	 * @return the Logout URL
	 */
	public String getOauthLogoutUrl(String accessToken, String returnUrl);

	/**
	 * Authenticates a user for a given access token 
	 * @param accessToken the user access token
	 * @return the authenticated {@link FacebookAuthUser} 
	 */
	public FacebookAuthUser authenticateUser(String accessToken);
}
