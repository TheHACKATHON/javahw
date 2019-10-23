import { Random } from "./random.js";
import { CardSuits } from "./suits.js";
import { CardColors } from "./colors.js";
import { CardRanks } from "./rank.js";
import { SuitColors } from "./suitColor.js";

export class Card {
  private rank: CardRanks;
  private color: CardColors;
  private suit: CardSuits;
  public static maxCards: number =
    (Object.keys(CardSuits).length / 2) *
    Object.keys(CardColors).length *
    CardRanks.getLength();

  public constructor(number: CardRanks, suit: CardSuits) {
    this.rank = number;
    this.suit = suit;
    this.color = SuitColors.get(suit);
  }

  public toHTML(): HTMLElement {
    const divCard = document.createElement("div");
    divCard.classList.add("card");
    divCard.classList.add(this.color.toString().toLocaleLowerCase());

    const p = document.createElement("p");
    p.textContent = this.rank.toString();
    p.classList.add(this.suit.toString().toLocaleLowerCase());

    divCard.appendChild(p);
    return divCard;
  }

  public equals(card: Card): boolean {
    return (
      this.suit == card.suit && this.rank.toString() == card.rank.toString()
    );
  }

  public static getRandomCard(): Card {
    return new Card(
      CardRanks.getRandomRank(),
      Card.getRandomEnumKeys<CardSuits>(CardSuits)
    );
  }

  private static getRandomEnumKeys<T>(enumeration): T {
    const keys = Object.keys(enumeration).filter(
      k => !(Math.abs(Number(k)) + 1)
    );
    const randId = Random.get(Object.keys(enumeration).length);
    return enumeration[keys[randId]];
  }
}
