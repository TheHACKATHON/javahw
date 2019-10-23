import { Game } from './typescript/game.js';
import { Player } from './typescript/player.js';
const root = document.getElementById("root");
const game = new Game("Game #777");
game.addPlayer(new Player("Player 1"));
game.addPlayer(new Player("Player 2"));
game.addPlayer(new Player("Player 3"));
if (game.start()) {
    root.appendChild(game.toHTML());
}
