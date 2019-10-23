export class Player {
    constructor(name) {
        this.hand = new Array();
        this.name = name;
    }
    ;
    toHTML() {
        const player = document.createElement("div");
        player.classList.add("player");
        const playerName = document.createElement("h6");
        playerName.classList.add("player-name");
        playerName.textContent = this.name;
        const cards = document.createElement("div");
        cards.classList.add("cards");
        this.hand.forEach(card => {
            cards.appendChild(card.toHTML());
        });
        player.appendChild(playerName);
        player.appendChild(cards);
        return player;
    }
    addCard(card) {
        this.hand.push(card);
    }
    clearCards() {
        this.hand.splice(0, this.hand.length);
    }
    containsCard(card) {
        return this.hand.some(c => c.equals(card));
    }
}
;
