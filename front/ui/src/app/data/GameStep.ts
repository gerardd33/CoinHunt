import { StepDirection } from './StepDirection';

export interface GameStep {
  stepDirection: StepDirection,
  millisecondsSinceLastStep: number
}
