import { Difficulty } from './Difficulty';

export interface LevelInfo {
  difficulty: Difficulty,
  description: string,
  mazeHeight: number,
  mazeWidth: number,
  numberOfCoins: number
}
