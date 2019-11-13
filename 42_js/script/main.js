function sum(...numbers) {
  let result = 0;
  for (let i = 0; i < numbers.length; i++) {
    result += numbers[i];
  }
  return result;
}

function nod(a, b) {
  // todo
  return;
}

function dividers(num) {
  const result = [];

  for (let i = 1; i < num; i++) {
    if (num % i === 0) {
      result.push(i);
    }
  }
  return result;
}

function digitCount(num) {
  num = +num;
  if (!isFinite(num)) {
    console.error("NaN");
    return 0;
  }

  if (num < 0) {
    num = -num;
  }

  let counter = 0;
  do {
    num = Math.floor(num / 10);
    counter++;
  } while (num > 0);
  return counter;
}

function analize(...numbers) {
  let positive = 0;
  let negative = 0;
  let zero = 0;
  let even = 0;
  let odd = 0;
  let nan = 0;

  for(let num of numbers){
    if(isFinite(num)){
      if(num > 0){
        positive++;
      }
      else if (num < 0){
        negative++;
      } else {
        zero++;
      }

      if(num % 2 === 0){
        even++;
      }else  {
        odd++;
      }
    } else {
      nan++;
    }

  }

  return { positive, negative, zero, even, odd, nan };
}

function calc() {
  const operations = ['+', '-', '/', '*'];
  do{
    const num1 = prompt("Calculator:\nenter first number", 1);
    const num2 = prompt("enter second number", 1);
    const oper = prompt("enter operator", '+');

    if(!num1 || !num2 || !oper){
      break;
    }

    if(operations.some(o => o === oper) && isFinite(num1) && isFinite(num2)){
      alert(`Result: ${eval(`${num1} ${oper} ${num2}`)}`);
    } else {
      alert("arguments wrong")
    }
  } while (true);
}

function shiftNumber(num, shift) {
  if(isFinite(+num)){
    let numstr = num.toString();
    for (let i = 0; i < shift; i++){
      numstr += numstr.substr(0, 1);
      numstr = numstr.substr(1);
    }
    return +numstr;
  }
  return null;
}

function day() {
  const days = ["Mon", "Tue", "Wen", "Thu", "Fri", "Saturday", "Sunday"];
  let idx = new Date().getDay();
  let conf = false;
  do{
    conf = confirm(`${days[idx++]}. You want to see the next day?`);
    if(idx >= days.length){
      idx = 0;
    }
  } while(conf);

}


function multTable() {
  const root = document.querySelector(".root");

  const x = 10;
  const y = 9;

  if (isFinite(x) && isFinite(y) &&
    x > 0 && y > 0) {
    root.innerHTML = "";
    const table = document.createElement("table");
    root.appendChild(table);

    table.innerHTML =
      `
<thead>
    <tr>
      <th/>
    </tr>
</thead>
<tbody/>
`;
    const theadTr = document.querySelector("thead tr");
    const tbody = document.querySelector("tbody");

    for (let i = 2; i < x; i++) {
      const th = document.createElement("th");
      const header = document.createElement("span");
      header.textContent = i;
      th.appendChild(header);
      theadTr.appendChild(th);

      const tr = document.createElement("tr");

      const firstTd = document.createElement("td");
      firstTd.classList.add("bold");

      const tdHead = document.createElement("span");
      tdHead.textContent = i;

      firstTd.appendChild(tdHead);
      tr.appendChild(firstTd);

      for (let j = 1; j < y; j++) {
        const td = document.createElement("td");
        const span = document.createElement("span");
        span.textContent = `${i * j}`;
        td.appendChild(span);
        tr.appendChild(td);
      }
      tbody.appendChild(tr);
    }
  } else {
    root.appendChild(document.createTextNode("Введите нормальные числа!"));
  }
}

function game(min, max) {

  alert(`make a number between ${min} and ${max}`);

  let userAnswer;
  let myNumber = (max - min )/ 2;
  const answers = ['>', '<', '=='];

  do{
    userAnswer = prompt(`Your number is ${myNumber}?\n(enter ${answers.join(" or ")})`);

    switch (userAnswer) {
      case '>':
      {
        myNumber += myNumber / 2;
      }
      break;
      case '<':
      {
        myNumber -= myNumber / 2;
      }
        break;
    }

  }while (userAnswer !== "==");

  alert("game over");

}

game(0, 100);