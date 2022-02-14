import { StepDirection } from './StepDirection';

export interface GameStep {
  direction: StepDirection,
  millisecondsSinceLastStep: number
}
