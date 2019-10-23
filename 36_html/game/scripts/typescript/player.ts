import { Card } from "./card.js";

export class Player {
  private name: string;
  private hand: Card[] = new Array();

  constructor(name: string) {
    this.name = name;
  }

  public toHTML(): HTMLElement {
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

  public addCard(card: Card): void {
    this.hand.push(card);
  }

  public clearCards(): void {
    this.hand.splice(0, this.hand.length);
  }

  public containsCard(card: Card): boolean {
    return this.hand.some(c => c.equals(card));
  }
}
