package com.coinhunt.front;

import com.coinhunt.front.data.AuthTokenIdResponse;
import com.coinhunt.front.data.CompletedGame;
import com.coinhunt.front.data.Credentials;
import com.coinhunt.front.data.LevelInfo;
import com.coinhunt.front.data.UserData;
import com.coinhunt.front.data.UserIdResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
public class RequestHandler {

	private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	@Value("${api.games}")
	private String gamesBaseUrl;

	@Value("${api.users}")
	private String usersBaseUrl;

	private final RestTemplate restTemplate;

	public RequestHandler(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@GetMapping("api/difficulty/info/")
	public ResponseEntity<LevelInfo> handleGameInfoRequest(@RequestParam(value = "difficulty") String difficulty) {
		ResponseEntity<LevelInfo> gamesResponse =  restTemplate.getForEntity(
				String.format("%s/game/info/%s", this.gamesBaseUrl, difficulty),
				LevelInfo.class
		);

		if (gamesResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return gamesResponse;
	}

	@GetMapping("api/game/new/")
	public ResponseEntity<String> handleNewGameRequest(
			@RequestParam(value = "difficulty") String difficulty,
			@RequestHeader(value = "Authorization") String authorizationHeader
	) {
		ResponseEntity<String> authenticationResponse = authenticate(authorizationHeader);

		if (authenticationResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else if (authenticationResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ResponseEntity<String> gamesResponse = restTemplate.getForEntity(
				String.format("%s/game/new/%s", this.gamesBaseUrl, difficulty),
				String.class
		);

		if (gamesResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return gamesResponse;
	}

	@PostMapping("api/game/save")
	public ResponseEntity<?> handleSaveGameRequest(
			@RequestBody CompletedGame completedGame,
			@RequestHeader(value = "Authorization") String authorizationHeader
	) {
		ResponseEntity<String> authenticationResponse = authenticate(authorizationHeader);

		if (authenticationResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else if (authenticationResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		ResponseEntity<?> gamesResponse = restTemplate.postForEntity(
				String.format("%s/game/save", this.gamesBaseUrl),
				completedGame,
				Object.class
		);

		if (gamesResponse.getStatusCode() != HttpStatus.CREATED) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("api/leaderboard/")
	public ResponseEntity<CompletedGame[]> handleBestScoresRequest(
			@RequestParam(value = "difficulty") String difficulty,
			@RequestParam(value = "filter") String filter
	) {
		ResponseEntity<CompletedGame[]> gamesResponse = restTemplate.getForEntity(
				String.format("%s/leaderboard/%s/%s", this.gamesBaseUrl, difficulty, filter),
				CompletedGame[].class
		);

		if (gamesResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return gamesResponse;
	}

	@GetMapping("api/user/best-scores/")
	public ResponseEntity<CompletedGame[]> handleUserBestScoresRequest(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "difficulty") String difficulty,
			@RequestParam(value = "filter") String filter
	) {
		ResponseEntity<CompletedGame[]> gamesResponse = restTemplate.getForEntity(
				String.format("%s/leaderboard/user/%s/%s/%s", this.gamesBaseUrl, userId, difficulty, filter),
				CompletedGame[].class
		);

		if (gamesResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return gamesResponse;
	}

	@GetMapping("api/user/data")
	public ResponseEntity<UserData> handleUserDataRequest(@RequestParam(value = "userId") String userId) {
		ResponseEntity<UserData> usersResponse = restTemplate.getForEntity(
				String.format("%s/user/data/%s", this.usersBaseUrl, userId),
				UserData.class
		);

		if (usersResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		UserData data = usersResponse.getBody();
		return new ResponseEntity<>(new UserData(data.getUserId(), data.getEmail(), null), HttpStatus.OK);
	}

	@PostMapping("api/account/register")
	public ResponseEntity<?> handleRegisterRequest(@RequestBody UserData userData) {
		System.out.println(this.usersBaseUrl);
		System.out.println(this.gamesBaseUrl);

		ResponseEntity<?> usersResponse = restTemplate.postForEntity(
				String.format("%s/user/register", this.usersBaseUrl),
				userData,
				UserData.class
		);

		if (usersResponse.getStatusCode() != HttpStatus.CREATED) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("api/account/login")
	public ResponseEntity<AuthTokenIdResponse> handleLoginRequest(@RequestBody Credentials credentials) {
		ResponseEntity<String> usersResponse = restTemplate.postForEntity(
				String.format("%s/user/login", this.usersBaseUrl),
				credentials,
				String.class
		);

		if (usersResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(new AuthTokenIdResponse(usersResponse.getBody()), HttpStatus.OK);
	}

	@GetMapping("api/account/auth")
	public ResponseEntity<UserIdResponse> handleAuthenticateRequest(@RequestHeader(value = "Authorization") String authorizationHeader) {
		 ResponseEntity<String> usersResponse = authenticate(authorizationHeader);

		 return new ResponseEntity<>(new UserIdResponse(usersResponse.getBody()), HttpStatus.OK);
	}

	private ResponseEntity<String> authenticate(String authorizationHeader) {
		return restTemplate.postForEntity(
				String.format("%s/user/authenticate", this.usersBaseUrl),
				authorizationHeader,
				String.class
		);
	}

}
