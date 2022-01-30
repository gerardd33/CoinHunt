import { Difficulty } from '../../data/Difficulty';
import { Maze } from '../../data/FieldContent';
import { Observable } from 'rxjs';

export abstract class MazeResource {

  abstract retrieveMaze(difficulty: Difficulty): Observable<Maze>;
}
