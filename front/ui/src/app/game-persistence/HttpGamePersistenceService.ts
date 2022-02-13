import { GamePersistenceService } from './GamePersistenceService';
import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { CompletedGameEntry } from '../data/CompletedGameEntry';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class HttpGamePersistenceService extends GamePersistenceService {

  constructor(private http: HttpClient) {
    super();
  }

  retrieveBestGamesEntries(difficulty: Difficulty, filter: CompletedGameFilter): Observable<Array<CompletedGameEntry>> {
    return this.http.get<Array<CompletedGameEntry>>(`${this.baseUrl}/api/leaderboard/`, {
      params: {
        difficulty: difficulty.toString(),
        filter: filter.toString()
      }
    });
  }

  saveGame(game: CompletedGame): Observable<any> {
    return this.http.post<CompletedGameEntry[]>(`${this.baseUrl}/api/game/save`, game, {
      headers: {
        Authorization: "xd" // TODO
      }
    });
  }

}
