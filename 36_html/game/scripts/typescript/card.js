import { Random } from "./random.js";
import { CardSuits } from "./suits.js";
import { CardColors } from "./colors.js";
import { CardRanks } from "./rank.js";
import { SuitColors } from "./suitColor.js";
export class Card {
    constructor(number, suit) {
        this.rank = number;
        this.suit = suit;
        this.color = SuitColors.get(suit);
    }
    toHTML() {
        const divCard = document.createElement("div");
        divCard.classList.add("card");
        divCard.classList.add(this.color.toString().toLocaleLowerCase());
        const p = document.createElement("p");
        p.textContent = this.rank.toString();
        p.classList.add(this.suit.toString().toLocaleLowerCase());
        divCard.appendChild(p);
        return divCard;
    }
    equals(card) {
        return this.suit == card.suit && this.rank.toString() == card.rank.toString();
    }
    static getRandomCard() {
        return new Card(CardRanks.getRandomRank(), Card.getRandomEnumKeys(CardSuits));
    }
    static getRandomEnumKeys(enumeration) {
        const keys = Object.keys(enumeration).filter(k => !(Math.abs(Number(k)) + 1));
        const randId = Random.get(Object.keys(enumeration).length);
        return enumeration[keys[randId]];
    }
}
Card.maxCards = (Object.keys(CardSuits).length / 2) *
    Object.keys(CardColors).length *
    CardRanks.getLength();
;
