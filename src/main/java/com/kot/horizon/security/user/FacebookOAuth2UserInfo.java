package com.kot.horizon.security.user;

import java.util.HashMap;
import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {
	public FacebookOAuth2UserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String) attributes.get("id");
	}

	@Override
	public String getEmail() {
		return (String) attributes.get("email");
	}

	@Override
	public String getFirsName() {
		Map<String, String> splitName = splitName((attributes.get("name").toString()));
		return splitName.get("firstName");
	}

	@Override
	public String getLastName() {
		Map<String, String> splitName = splitName((attributes.get("name").toString()));
		return splitName.get("lastName");
	}

	@Override
	public String getImageUrl() {
		if (attributes.containsKey("picture")) {
			Map<String, Object> pictureObj = (Map<String, Object>) attributes.get("picture");
			if (pictureObj.containsKey("data")) {
				Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get("data");
				if (dataObj.containsKey("url")) {
					return (String) dataObj.get("url");
				}
			}
		}
		return null;
	}

	private static Map<String, String> splitName(String name) {
		Map<String, String> nameArray = new HashMap<>();

		if (name.contains(" ")) {
			String[] arr = name.split(" ");
			nameArray.put("firstName", arr[0]);
			nameArray.put("lastName", arr[1]);
		} else {
			nameArray.put("lastName", name);
			nameArray.put("firstName", "");
		}
		return nameArray;
	}
}