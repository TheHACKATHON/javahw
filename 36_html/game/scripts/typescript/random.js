export class Random {
    static get(max) {
        return Math.floor(Math.random() * Math.floor(max));
    }
}
