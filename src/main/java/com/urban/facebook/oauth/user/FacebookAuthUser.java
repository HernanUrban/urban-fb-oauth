package com.urban.facebook.oauth.user;

import java.io.Serializable;

/**
 * Authorized User domain class
 * @author hernan.urban
 *
 */
public class FacebookAuthUser implements Serializable {

	private static final long serialVersionUID = 8636360545089956651L;
	private String id;
	private String first_name;
	private String last_name;
	private String email;
	private String gender;
	private String accessToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
