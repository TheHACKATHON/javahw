import { Random } from "./random.js";

export class CardRanks {
  private rank: string;
  private static readonly ranks: CardRanks[] = new Array();

  private constructor(rank: string) {
    this.rank = rank;
    CardRanks.ranks.push(this);
  }

  public toString(): string {
    return this.rank;
  }

  public static getLength(): number {
    return this.ranks.length;
  }

  public static Two: CardRanks = new CardRanks("2");
  public static Three: CardRanks = new CardRanks("3");
  public static Four: CardRanks = new CardRanks("4");
  public static Five: CardRanks = new CardRanks("5");
  public static Six: CardRanks = new CardRanks("6");
  public static Seven: CardRanks = new CardRanks("7");
  public static Eight: CardRanks = new CardRanks("8");
  public static Nine: CardRanks = new CardRanks("9");
  public static Ten: CardRanks = new CardRanks("10");
  public static Jack: CardRanks = new CardRanks("J");
  public static Queen: CardRanks = new CardRanks("Q");
  public static King: CardRanks = new CardRanks("K");
  public static Ace: CardRanks = new CardRanks("A");

  public static getRandomRank(): CardRanks {
    const randIdx = Random.get(this.ranks.length);
    return this.ranks[randIdx];
  }
}
