import { GamePersistenceService } from './GamePersistenceService';
import { Injectable } from '@angular/core';
import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { CompletedGameEntry } from '../data/CompletedGameEntry';

@Injectable()
export class MockGamePersistenceService extends GamePersistenceService {

  saveGame(game: CompletedGame): void {
    console.log(game + " saved!");
  }

  retrieveBestGamesEntries(difficulty: Difficulty, filter: CompletedGameFilter): Array<CompletedGameEntry> {
    return [
      { username: 'user1', totalTimeInMilliseconds: 4545, startTime: Date.now() },
      { username: 'user2', totalTimeInMilliseconds: 5545, startTime: Date.now() },
      { username: 'user3', totalTimeInMilliseconds: 6545, startTime: Date.now() }
    ];
  }

}
