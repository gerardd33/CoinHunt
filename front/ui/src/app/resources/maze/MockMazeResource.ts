import { MazeResource } from './MazeResource';
import { Difficulty } from '../../data/Difficulty';
import { Observable, of } from 'rxjs';
import { FieldContent, Maze } from '../../data/FieldContent';

export class MockMazeResource extends MazeResource {

  easyMaze: Maze = [
    [FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.COIN],
    [FieldContent.EMPTY, FieldContent.WALL, FieldContent.EMPTY],
    [FieldContent.PLAYER, FieldContent.EMPTY, FieldContent.EMPTY]
  ];

  mediumMaze: Maze = [
    [FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.EMPTY, FieldContent.WALL],
    [FieldContent.PLAYER, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.COIN, FieldContent.WALL],
    [FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.WALL, FieldContent.COIN, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.WALL],
    [FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.WALL]
  ];

  hardMaze: Maze = [
    [FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.EMPTY, FieldContent.WALL, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.PLAYER, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.COIN, FieldContent.WALL, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.WALL, FieldContent.COIN, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.WALL, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.WALL, FieldContent.EMPTY, FieldContent.WALL],
    [FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY, FieldContent.EMPTY],
    [FieldContent.EMPTY, FieldContent.WALL, FieldContent.WALL, FieldContent.COIN, FieldContent.WALL, FieldContent.EMPTY, FieldContent.WALL]
  ];

  retrieveMaze(difficulty: Difficulty): Observable<Maze> {
    switch (difficulty) {
      case Difficulty.EASY:
        return of(this.easyMaze);
      case Difficulty.MEDIUM:
        return of(this.mediumMaze);
      case Difficulty.HARD:
        return of(this.hardMaze);
    }
  }

}
