import { Component, Input, OnInit } from '@angular/core';
import { Maze } from '../data/FieldContent';

@Component({
  selector: 'app-maze',
  templateUrl: './maze.component.html',
  styleUrls: ['./maze.component.css']
})
export class MazeComponent implements OnInit {

  @Input()
  maze: Maze;

  constructor() { }

  ngOnInit(): void {
  }

  getGridValues() {
    if (!this.maze) return {};

    let dimension: number = this.maze.length;

    return {
      'grid-template-columns': `repeat(${dimension}, 50px)`,
      'grid-template-rows': `repeat(${dimension}, 50px)`
    };
  }

}
