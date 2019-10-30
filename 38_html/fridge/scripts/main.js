class Product {
    constructor(name, imagePath) {
        this.name = name;
        this.imagePath = imagePath;
    }
    toHTML() {
        const product = document.createElement("div");
        product.classList.add("product");
        const image = document.createElement("img");
        image.src = this.imagePath;
        const name = document.createElement("span");
        name.textContent = this.name;
        product.appendChild(image);
        product.appendChild(name);
        return product;
    }
}

const prods = document.querySelector("#products");
prods.appendChild(new Product("Pepper", "./icons/Food_C247-128.png").toHTML());
prods.appendChild(new Product("Apple", "./icons/Food_C240-128.png").toHTML());
prods.appendChild(new Product("Cheese", "./icons/Food_C217-128.png").toHTML());
prods.appendChild(new Product("Corn", "./icons/Food_C245-128.png").toHTML());
prods.appendChild(new Product("Mushroom", "./icons/Food_C239-128.png").toHTML());
prods.appendChild(new Product("Garlic", "./icons/Food_C238-128.png").toHTML());
prods.appendChild(new Product("Beef", "./icons/Food_C225-128.png").toHTML());
prods.appendChild(new Product("Fish", "./icons/Food_C205-128.png").toHTML());
prods.appendChild(new Product("Eggs", "./icons/Food_C203-128.png").toHTML());
prods.appendChild(new Product("Coriander", "./icons/Food_C235-128.png").toHTML());