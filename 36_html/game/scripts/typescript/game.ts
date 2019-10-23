import { Player } from "./player.js";
import { Card } from "./card.js";

export class Game {
  private name: string;
  private players: Player[] = new Array();
  private static readonly cardsPerPlayer: number = 6;

  public constructor(name: string) {
    this.name = name;
  }

  public start(): boolean {
    if (this.players.length > 1) {
      this.players.forEach(p => {
        p.clearCards();
      });

      this.issueCards();
      return true;
    }
    return false;
  }

  private issueCards(): void {
    let card: Card = null;
    this.players.forEach(player => {
      for (let i = 0; i < Game.cardsPerPlayer; i++) {
        do {
          card = Card.getRandomCard();
          player.addCard(card);
        } while (this.players.some(p => p.containsCard(card)));

        // TODO: переделать на нормальный рандом
      }
    });
  }

  public addPlayer(player: Player): boolean {
    if (this.players.length + 1 < Card.maxCards / Game.cardsPerPlayer) {
      this.players.push(player);
      return true;
    }
    return false;
  }

  public toHTML(): HTMLElement {
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
