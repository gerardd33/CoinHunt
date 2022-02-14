import { LevelInfo } from '../../data/LevelInfo';
import { Difficulty } from '../../data/Difficulty';
import { Observable } from 'rxjs';
import { Resource } from '../../Resource';

export abstract class LevelInfoResource extends Resource {

  abstract retrieveLevelInfo(difficulty: Difficulty): Observable<LevelInfo>;

}
