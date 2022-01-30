import { CompletedGame } from '../data/CompletedGame';

export abstract class GamePersistenceService {

  abstract saveGame(game: CompletedGame): void;
}
