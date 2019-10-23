export class Random {
  static get(max: number): number {
    return Math.floor(Math.random() * Math.floor(max));
  }
}
