/* jshint esversion: 6*/

export class Block {
  constructor(link, title, price, pricePer, services, colorClass, currency) {
    this.link = link;
    this.title = title;
    this.price = price;
    this.pricePer = pricePer;
    this.services = services;
    this.colorClass = colorClass;
    this.currency = currency;
  }

  toHTML() {
    const block = document.createElement("div");
    block.classList.add("block");
    block.classList.add(this.colorClass);

    {
      // head
      const head = document.createElement("div");
      head.classList.add("head");

      const title = document.createElement("h3");
      title.textContent = this.title;

      head.appendChild(title);
      block.appendChild(head);
    }

    {
      // hr
      const hr = document.createElement("hr");
      block.appendChild(hr);
    }

    {
      // price
      const price = document.createElement("div");
      price.classList.add("price");
      price.classList.add(this.currency);
      price.classList.add(this.pricePer);

      const span = document.createElement("span");
      span.textContent = this.price;

      price.appendChild(span);
      block.appendChild(price);
    }

    {
      // services
      const services = document.createElement("div");
      services.classList.add("services");

      const ul = document.createElement("ul");
      services.appendChild(ul);

      this.services.forEach(service => {
        const li = document.createElement("li");
        li.textContent = service;
        ul.appendChild(li);
      });
      block.appendChild(services);
    }

    {
      // buy
      const btn = document.createElement("a");
      btn.classList.add("btn");
      btn.setAttribute("href", this.link);
      btn.textContent = "Buy";
      block.appendChild(btn);
    }

    return block;
  }
}
