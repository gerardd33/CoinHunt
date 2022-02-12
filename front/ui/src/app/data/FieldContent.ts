export enum FieldContent {
  EMPTY = "EMPTY",
  WALL = "WALL",
  PLAYER = "PLAYER",
  COIN = "COIN"
}

export type Maze = Array<Array<FieldContent>>;
