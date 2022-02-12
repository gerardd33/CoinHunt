import { CompletedGameEntry } from '../data/CompletedGameEntry';
import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';

export abstract class GamePersistenceService {

  abstract saveGame(game: CompletedGame): void;

  abstract retrieveBestGamesEntries(difficulty: Difficulty, filter: CompletedGameFilter): Array<CompletedGameEntry>
}
