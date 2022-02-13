import { GamePersistenceService } from './GamePersistenceService';
import { Injectable } from '@angular/core';
import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { CompletedGameEntry } from '../data/CompletedGameEntry';
import { empty, Observable, of } from 'rxjs';

@Injectable()
export class MockGamePersistenceService extends GamePersistenceService {

  saveGame(game: CompletedGame): Observable<void> {
    console.log(game + " saved!");
    return of(void 0);
  }

  retrieveBestGamesEntries(difficulty: Difficulty, filter: CompletedGameFilter): Observable<Array<CompletedGameEntry>> {
    return of([
      { userId: 'user1', totalTimeInMilliseconds: 4545, startTime: Date.now() },
      { userId: 'user2', totalTimeInMilliseconds: 5545, startTime: Date.now() },
      { userId: 'user3', totalTimeInMilliseconds: 6545, startTime: Date.now() }
    ]);
  }

}
