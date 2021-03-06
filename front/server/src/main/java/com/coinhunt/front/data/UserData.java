package com.coinhunt.front.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserData {

	private final String userId;

	private final String email;

	private final String password;
}
