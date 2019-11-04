/* jshint esversion: 6*/
import { StyleColor, StyleCurrency, StylePricePer } from "./styleHelpers.js";
import { Block } from "./block.js";

function init() {
  const root = document.querySelector("#root");
  const enterprise = new Block(
    "#",
    "Enterprise",
    69,
    StylePricePer.Month,
    [
      "10GB disk space",
      "100GB monthly bandwidth",
      "20 email accounts",
      "Unlimited subdomens"
    ],
    StyleColor.Purple,
    StyleCurrency.Dollar
  );

  const pro = new Block(
    "#",
    "Professional",
    29,
    StylePricePer.Month,
    [
      "5GB disk space",
      "60GB monthly bandwidth",
      "10 email accounts",
      "Unlimited subdomens"
    ],
    StyleColor.Orange,
    StyleCurrency.Dollar
  );

  const standart = new Block(
    "#",
    "Standart",
    19,
    StylePricePer.Month,
    [
      "3B disk space",
      "30GB monthly bandwidth",
      "5 email accounts",
      "Unlimited subdomens"
    ],
    StyleColor.Blue,
    StyleCurrency.Dollar
  );

  const basic = new Block(
    "#",
    "Basic",
    9,
    StylePricePer.Month,
    [
      "1GB disk space",
      "10GB monthly bandwidth",
      "2 email accounts",
      "Unlimited subdomens"
    ],
    StyleColor.Green,
    StyleCurrency.Dollar
  );

  root.appendChild(enterprise.toHTML());
  root.appendChild(pro.toHTML());
  root.appendChild(standart.toHTML());
  root.appendChild(basic.toHTML());
}

init();
