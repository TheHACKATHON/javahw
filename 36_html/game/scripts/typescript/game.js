import { Card } from "./card.js";
export class Game {
    constructor(name) {
        this.players = new Array();
        this.name = name;
    }
    ;
    start() {
        if (this.players.length > 1) {
            this.players.forEach((p) => {
                p.clearCards();
            });
            this.issueCards();
            return true;
        }
        return false;
    }
    issueCards() {
        let card = null;
        this.players.forEach(player => {
            for (let i = 0; i < Game.cardsPerPlayer; i++) {
                do {
                    card = Card.getRandomCard();
                } while (this.players.some(p => p.containsCard(card)));
                player.addCard(card);
            }
        });
    }
    addPlayer(player) {
        if (this.players.length + 1 < Card.maxCards / Game.cardsPerPlayer) {
            this.players.push(player);
            return true;
        }
        return false;
    }
    toHTML() {
        const game = document.createElement("div");
        game.classList.add("game");
        game.classList.add("block");
        const title = document.createElement("h1");
        title.textContent = this.name;
        game.appendChild(title);
        this.players.forEach(player => {
            game.appendChild(player.toHTML());
        });
        return game;
    }
}
Game.cardsPerPlayer = 6;
