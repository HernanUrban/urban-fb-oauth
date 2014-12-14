package com.urban.facebook.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class FacebookOauthServiceTest {
	OauthService facebookOauthService;

	@Before
	public void init() {
		facebookOauthService = new FacebookOauthService("1234567890",
				"abcdefghijklmopqrstuv");
	}

	@Test
	public void getOauthAuthorizationUrlTest() {
		String authUrl = facebookOauthService
				.getOauthAuthorizationUrl("http://www.google.com");
		assertNotNull(authUrl);
		assertEquals(
				"http://www.facebook.com/dialog/oauth?client_id=1234567890&redirect_uri=http%3A%2F%2Fwww.google.com&scope=email",
				authUrl);
	}

	@Test
	public void getLogoutUrl() {
		String logoutUrl = facebookOauthService.getOauthLogoutUrl(
				"accessTokenFake", "http://www.google.com");
		assertNotNull(logoutUrl);
		assertEquals(
				"http://www.facebook.com/logout.php?next=http%3A%2F%2Fwww.google.com&access_token=accessTokenFake",
				logoutUrl);

	}

}
