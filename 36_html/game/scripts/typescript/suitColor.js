import { CardSuits } from "./suits.js";
import { CardColors } from "./colors.js";
const suitColor = new Map();
suitColor.set(CardSuits.Hearts, CardColors.Red);
suitColor.set(CardSuits.Diamonds, CardColors.Red);
suitColor.set(CardSuits.Spades, CardColors.Black);
suitColor.set(CardSuits.Clubs, CardColors.Black);
export { suitColor as SuitColors };
