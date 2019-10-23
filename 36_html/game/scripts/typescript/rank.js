import { Random } from "./random.js";
export class CardRanks {
    constructor(rank) {
        this.rank = rank;
        CardRanks.ranks.push(this);
    }
    toString() {
        return this.rank;
    }
    static getLength() {
        return this.ranks.length;
    }
    static getRandomRank() {
        const randIdx = Random.get(this.ranks.length);
        return this.ranks[randIdx];
    }
}
CardRanks.ranks = new Array();
CardRanks.Two = new CardRanks("2");
CardRanks.Three = new CardRanks("3");
CardRanks.Four = new CardRanks("4");
CardRanks.Five = new CardRanks("5");
CardRanks.Six = new CardRanks("6");
CardRanks.Seven = new CardRanks("7");
CardRanks.Eight = new CardRanks("8");
CardRanks.Nine = new CardRanks("9");
CardRanks.Ten = new CardRanks("10");
CardRanks.Jack = new CardRanks("J");
CardRanks.Queen = new CardRanks("Q");
CardRanks.King = new CardRanks("K");
CardRanks.Ace = new CardRanks("A");
