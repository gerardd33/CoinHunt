import { Difficulty } from '../../data/Difficulty';
import { Maze } from '../../data/FieldContent';
import { Observable } from 'rxjs';
import { Resource } from '../../Resource';

export abstract class MazeResource extends Resource {

  abstract retrieveMaze(difficulty: Difficulty): Observable<Maze>;
}
