/* jshint esversion: 6*/

class ColorStyle{
  static get Blue(){
    return "block-blue";
  };
  static get Green(){
    return "block-green";
  };
  static get Orange(){
    return "block-orange";
  };
  static get Purple(){
    return "block-purple";
  };
}

class CurrencyStyle{
  static get Dollar(){
    return "price-bucks";
  };
  static get Euro(){
    return "price-euro";
  };
}

class PricePer{
  static get Month(){
    return "price-per-month";
  };
  static get Year(){
    return "price-per-year";
  };
}

class Block{
  constructor(link, title, price, pricePer, services, colorClass, currency){
    this.link = link;
    this.title = title;
    this.price = price;
    this.pricePer = pricePer;
    this.services = services;
    this.colorClass = colorClass;
    this.currency = currency;
  }

  toHTML(){
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

function init(){
  const root = document.querySelector("#root");
  const enterprise = new Block(
    "#",
    "Enterprise",
    69,
    PricePer.Month,
    ["10GB disk space", "100GB monthly bandwidth", "20 email accounts", "Unlimited subdomens"],
    ColorStyle.Purple,
    CurrencyStyle.Dollar
  );

  const pro = new Block(
    "#",
    "Professional",
    29,
    PricePer.Month,
    ["5GB disk space", "60GB monthly bandwidth", "10 email accounts", "Unlimited subdomens"],
    ColorStyle.Orange,
    CurrencyStyle.Dollar
  );

  const standart = new Block(
    "#",
    "Standart",
    19,
    PricePer.Month,
    ["3B disk space", "30GB monthly bandwidth", "5 email accounts", "Unlimited subdomens"],
    ColorStyle.Blue,
    CurrencyStyle.Dollar
  );

  const basic = new Block(
    "#",
    "Basic",
    9,
    PricePer.Month,
    ["1GB disk space", "10GB monthly bandwidth", "2 email accounts", "Unlimited subdomens"],
    ColorStyle.Green,
    CurrencyStyle.Dollar
  );

    root.appendChild(enterprise.toHTML());
    root.appendChild(pro.toHTML());
    root.appendChild(standart.toHTML());
    root.appendChild(basic.toHTML());
}

init();

