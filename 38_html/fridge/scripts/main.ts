class Product{
    private name: string;
    private imagePath: string;
    
    public constructor(name: string, imagePath: string){
        this.name = name;
        this.imagePath = imagePath;
    }

    public toHTML(): HTMLElement{
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

prods.appendChild(new Product("Eggs", "./../icons/Food_C203-128.png").toHTML());