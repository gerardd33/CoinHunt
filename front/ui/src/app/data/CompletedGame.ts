import { Difficulty } from './Difficulty';
import { GameStep } from './GameStep';
import { Maze, MazeResponse } from './FieldContent';

export interface CompletedGame {
  difficulty: Difficulty,
  steps: Array<GameStep>,
  userId: string,
  totalTimeInMilliseconds: number,
  startTime: number,
  maze: MazeResponse
}
