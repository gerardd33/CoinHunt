import { Component, Input, OnInit } from '@angular/core';
import { FieldContent } from '../data/FieldContent';

@Component({
  selector: 'app-maze-field',
  templateUrl: './maze-field.component.html',
  styleUrls: ['./maze-field.component.css']
})
export class MazeFieldComponent implements OnInit {

  @Input()
  content: FieldContent;

  constructor() { }

  ngOnInit(): void {
  }

  getClass(): string {
    switch (this.content) {
      case FieldContent.COIN:
        return 'bg-warning';

      case FieldContent.WALL:
        return 'bg-dark';

      case FieldContent.PLAYER:
        return 'bg-success';

      case FieldContent.EMPTY:
        return 'bg-white';

    }

    return '';
  }

  isEmpty(): boolean {
    return this.content === FieldContent.EMPTY;
  }
}
