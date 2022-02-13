import { CompletedGame } from '../data/CompletedGame';
import { Difficulty } from '../data/Difficulty';
import { CompletedGameFilter } from '../data/CompletedGameFilter';
import { Resource } from '../Resource';
import { Observable } from 'rxjs';

export abstract class GamePersistenceService extends Resource {

  abstract saveGame(game: CompletedGame): Observable<any>;

  abstract retrieveBestGames(difficulty: Difficulty, filter: CompletedGameFilter): Observable<Array<CompletedGame>>
}
