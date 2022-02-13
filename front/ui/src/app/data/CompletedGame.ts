import { Difficulty } from './Difficulty';
import { GameStep } from './GameStep';

export interface CompletedGame {
  difficulty: Difficulty,
  steps: Array<GameStep>,
  userId: string,
  totalTimeInMilliseconds: number,
  startTime: number
}
