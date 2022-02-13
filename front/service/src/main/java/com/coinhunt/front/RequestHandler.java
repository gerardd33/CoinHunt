package com.coinhunt.front;

import com.coinhunt.front.data.AuthTokenResponse;
import com.coinhunt.front.data.CompletedGame;
import com.coinhunt.front.data.CompletedGameEntry;
import com.coinhunt.front.data.Credentials;
import com.coinhunt.front.data.Difficulty;
import com.coinhunt.front.data.FieldContent;
import com.coinhunt.front.data.LevelInfo;
import com.coinhunt.front.data.Statistics;
import com.coinhunt.front.data.UserData;
import com.coinhunt.front.data.UserIdResponse;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class RequestHandler {

//	private final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

	@GetMapping("api/difficulty/info/")
	public ResponseEntity<LevelInfo> handleGameInfoRequest(@RequestParam(value = "difficulty") String difficulty) {
		// TODO - call Games

		return new ResponseEntity<>(
				new LevelInfo(Difficulty.valueOf(difficulty), "Test description", 3, 3, 1),
				HttpStatus.OK
		);
	}

	@GetMapping("api/game/new/")
	public ResponseEntity<List<List<FieldContent>>> handleNewGameRequest(
			@RequestParam(value = "difficulty") String difficulty,
			@RequestHeader(value = "Authorization") String authorizationHeader
	) {
		ResponseEntity<UserIdResponse> authenticationResponse = authenticate(authorizationHeader);

		if (authenticationResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		// TODO - call Games

		return new ResponseEntity<>(
				List.of(
					List.of(FieldContent.PLAYER, FieldContent.EMPTY, FieldContent.COIN),
					List.of(FieldContent.EMPTY, FieldContent.WALL, FieldContent.EMPTY),
					List.of(FieldContent.COIN, FieldContent.EMPTY, FieldContent.WALL)
				),
				HttpStatus.OK
		);
	}

	@PostMapping("api/game/save")
	public ResponseEntity handleSaveGameRequest(
			@RequestBody CompletedGame completedGame,
			@RequestHeader(value = "Authorization") String authorizationHeader
	) {
		ResponseEntity<UserIdResponse> authenticationResponse = authenticate(authorizationHeader);

		if (authenticationResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		// TODO - call Games

		return new ResponseEntity(HttpStatus.OK);
	}

	@GetMapping("api/leaderboard/")
	public List<CompletedGameEntry> handleBestScoresRequest(@RequestParam(value = "difficulty") String difficulty, @RequestParam(value = "filter") String filter) {
		// TODO - call Games

		return List.of(
				new CompletedGameEntry("user1id", 0, 0),
				new CompletedGameEntry("user2id", 1, 1)
		);
	}

	@GetMapping("api/user/best-scores/")
	public List<CompletedGameEntry> handleUserBestScoresRequest(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "difficulty") String difficulty,
			@RequestParam(value = "filter") String filter
	) {
		// TODO - call Games

		return List.of(
				new CompletedGameEntry("user2id", 10, 10),
				new CompletedGameEntry("user2id", 11, 11)
		);
	}

	@GetMapping("api/user/stats/")
	public Statistics handleUserStatsRequest(@RequestParam(value = "userId") String userId) {
		// TODO - call Games

		return new Statistics(
				123,
				Map.of(
						Difficulty.EASY, 1,
						Difficulty.MEDIUM, 2,
						Difficulty.HARD, 3
				),
				Map.of(
						Difficulty.EASY, 1,
						Difficulty.MEDIUM, 2,
						Difficulty.HARD, 3
				)
		);
	}

	@GetMapping("api/user/data")
	public UserData handleUserDataRequest(@RequestParam(value = "userId") String userId) {
		// TODO - call Users

		return new UserData("user1", 123L);
	}

	@PostMapping("api/account/register")
	public ResponseEntity<AuthTokenResponse> handleRegisterRequest(@RequestBody Credentials credentials) {
		// TODO - call Users

		return new ResponseEntity<>(new AuthTokenResponse("I am a token!"), HttpStatus.OK);
	}

	@PostMapping("api/account/login")
	public ResponseEntity<AuthTokenResponse> handleLoginRequest(@RequestBody Credentials credentials) {
		// TODO - call Users

		return new ResponseEntity<>(new AuthTokenResponse("I am a token!"), HttpStatus.OK);
	}

	@GetMapping("api/account/auth")
	public ResponseEntity<UserIdResponse> handleAuthenticateRequest(@RequestHeader(value = "Authorization") String authorizationHeader) {
		// TODO - call Users

		return new ResponseEntity<>(new UserIdResponse("userrr1"), HttpStatus.OK);
	}

	private ResponseEntity<UserIdResponse> authenticate(String authorizationHeader) {
		// TODO - call Users

		return new ResponseEntity<>(new UserIdResponse("userrr1"), HttpStatus.OK);
	}

}
