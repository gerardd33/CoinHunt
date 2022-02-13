import { GamePersistenceService } from './GamePersistenceService';
import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { UserService } from '../user/UserService';

@Injectable()
export class HttpGamePersistenceService extends GamePersistenceService {

  constructor(private http: HttpClient,
              private userService: UserService) {
    super();
  }

  retrieveBestGames(difficulty: Difficulty, filter: CompletedGameFilter): Observable<Array<CompletedGame>> {
    return this.http.get<Array<CompletedGame>>(`${this.baseUrl}/api/leaderboard/`, {
      params: {
        difficulty: difficulty.toString(),
        filter: filter.toString()
      }
    });
  }

  saveGame(game: CompletedGame): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/api/game/save`, game, {
      headers: {
        Authorization: this.userService.getToken() as string
      }
    });
  }

}
