import { LevelInfo } from '../../data/LevelInfo';
import { Difficulty } from '../../data/Difficulty';
import { Observable } from 'rxjs';

export abstract class LevelInfoResource {

  abstract retrieveLevelInfo(difficulty: Difficulty): Observable<LevelInfo>;

}
