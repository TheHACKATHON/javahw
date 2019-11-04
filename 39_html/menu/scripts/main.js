/* jshint esversion: 6 */
class MenuItem {
    constructor(name, imagePath, link) {
        this.name = name;
        this.imagePath = imagePath;
        this.link = link;
    }
    toHTML() {
        const menuItem = document.createElement("div");
        menuItem.classList.add("item");
        const a = document.createElement("a");
        a.href = this.link;
        const image = document.createElement("img");
        image.src = this.imagePath;
        image.alt = "menu-item";
        const name = document.createElement("span");
        name.textContent = this.name;
        a.appendChild(image);
        a.appendChild(name);
        menuItem.appendChild(a);
        return menuItem;
    }
}

const menu = document.querySelector("nav#menu");
menu.appendChild(new MenuItem("Edit", "./icons/edit.png", "#").toHTML());
menu.appendChild(new MenuItem("Favorites", "./icons/favorites.png", "#").toHTML());
menu.appendChild(new MenuItem("History", "./icons/history.png", "#").toHTML());
menu.appendChild(new MenuItem("Security", "./icons/security.png", "#").toHTML());
menu.appendChild(new MenuItem("Settings", "./icons/settings.png", "#").toHTML());