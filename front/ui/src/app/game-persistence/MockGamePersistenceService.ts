import { GamePersistenceService } from './GamePersistenceService';
import { Injectable } from '@angular/core';
import { CompletedGame } from '../data/CompletedGame';

@Injectable()
export class MockGamePersistenceService extends GamePersistenceService {

  saveGame(game: CompletedGame): void {
    console.log(game + " saved!");
  }

}
