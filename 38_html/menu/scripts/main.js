class MenuItem {
    constructor(name, imagePath, link) {
        this.name = name;
        this.imagePath = imagePath;
        this.link = link;
    }
    toHTML() {
        const menuItem = document.createElement("a");
        menuItem.href = this.link;
        const image = document.createElement("img");
        image.src = this.imagePath;
        const name = document.createElement("span");
        name.textContent = this.name;
        menuItem.appendChild(image);
        menuItem.appendChild(name);
        return menuItem;
    }
}

const menu = document.querySelector("nav#menu");
menu.appendChild(new MenuItem("Edit", "./icons/edit.png", "#").toHTML());
menu.appendChild(new MenuItem("Favorites", "./icons/favorites.png", "#").toHTML());
menu.appendChild(new MenuItem("History", "./icons/history.png", "#").toHTML());
menu.appendChild(new MenuItem("Security", "./icons/security.png", "#").toHTML());
menu.appendChild(new MenuItem("Settings", "./icons/settings.png", "#").toHTML());