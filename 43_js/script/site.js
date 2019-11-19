// task 1
var color;
function drawBox(color) {
  document.writeln(`<div style='background-color: ${color}; color: transparent'>-</div>`)
}
const setColor = () => {
  let color;
  if (Math.random() >= 0.5) {
    this.color = "#ff0000";
  }  else {
    color = "#000000";
  }
  console.log(color, this.color);
  drawBox(this.color ? this.color : color);
};

setColor();


// task 2
const ques = [
  "Обнаружив, что любимые джинсы порвались, вы\n" +
  "выкидываете их — 0\n" +
  "делаете еще несколько художественных разрезов и продолжаете носить — 1",

  "Подружка попросила вас побыть с ее ребенком-непоседой. Вы\n" +
  "включите ему телевизор или видеоигру  — 0\n" +
  "окунетесь в детство и вместе придумаете веселую увлекательную игру — 1",

  "Часто ли вам снятся сны?\n" +
  "Да, вы обычно помните сновидения, они яркие и интересные — 1\n" +
  "Нет, вы редко запоминаете сны — 0",

  "Какую работу вы предпочтете — ту, где известно, что и как делать, или ту, в которой необходимо что-то придумывать?\n" +
  "Интереснее самостоятельно искать решения проблем, даже если это требует временных затрат — 1 \n" +
  "Вам проще работать, когда существует четкий алгоритм — 0",

  "Подружка выходит замуж, и вам предстоит организовать выкуп невесты, вы\n" +
  "изучите литературу и подберете конкурсы  — 0 \n" +
  "постараетесь придумать конкурсы сами, чтобы они были оригинальными — 1",

  "Изучая меню в ресторане, вы, скорее всего, остановитесь на\n" +
  "знакомом блюде — 0\n" +
  "блюде с самым экстравагантным, названием — 1",

  "Если в процессе создания стенгазеты потребуется нарисовать какое-то животное, вы\n" +
  "постараетесь найти фотографию или другое изображение и срисуете — 0\n" +
  "нарисуете животное сами, даже если не очень хорошо имеете — 1",

  "Для новогоднего маскарада в детском саду необходимо найти малышу костюм. Вы \n" +
  "сошьете его сами — 1 \n" +
  "купите в магазине — 0",

  "Любили ли вы в детстве читать сказки?\n" +
  "Вам интереснее было смотреть мультики — 0\n" +
  "Да, вы любили читать не меньше, чем смотреть телевизор — 1",

  "Ваши фотографии в альбоме —\n" +
  "чаше традиционные — 0 \n" +
  "живые позы в необычных ракурсах — 1",
];

function task2() {
  const answ = new Int8Array(ques.length);
  f: for (let i = 0; i < ques.length; i++){
    let ans;
    do {
      ans = prompt(ques[i], 0);
      if(ans === null){
        break f;
      }
      ans = +ans;
    } while (!isFinite(ans) || ans < 0 || ans > 1);
    answ[i] = ans;
  }

  const units = answ.filter(a => a === 1).length;
  if (units < 4) {
    alert("Вы придерживаетесь традиционных взглядов на решение проблемных ситуаций.");
  } else if (units < 7) {
    alert("Вы достаточно креативны по натуре, но не всегда считаете нужным пользоваться этими способностями.");
  } else {
    alert("Вы очень творческий человек. Вы умеете видеть необычные решения, которые незаметны для окружающих.");
  }
}
task2();

// task 3
const arr = [3, 'a', 'a', 'a', 2, 3, 'a', 3, 'a', 2, 4, 9, 3];

function count(arr) {
  const map = new Map();
  let maxKey, maxVal = 0, next;
  for (let i = 0; i < arr.length; i++) {
    if (map.has(arr[i])) {
      next = map.get(arr[i]) + 1;
    } else {
      next = 1;
    }
    map.set(arr[i], next);
    if (next > maxVal) {
      maxKey = arr[i];
      maxVal = next;
    }
  }
  return {maxKey, maxVal, map};
}

const task3 = count(arr);
console.log(`Task 3: ${task3.maxKey} - ${task3.maxVal} times`);


// task 4
function deepClone(src) {
  return Object.assign({}, src);
}

const task4 = [1,2,[3,4,]];
console.log(`Task 4:`, deepClone(task4));

// task 5
function first(array, n = 1) {
  if(n > 0) {
    return array.slice(0, n);
  }
  return [];
}

console.log(`Task 5:`,
    first([7, 9, 0, -2],6)
);


// task 6

function everyEven(number) {
  if(isFinite(+number)) {
    let numberStr = number.toString();
    let result = "";
    for (let i = 0; i < numberStr.length; i++){
      result += numberStr[i];
      if(+numberStr[i] % 2 === 0) {
        result += "—";
      }
    }
    return result;
  }
}

console.log("Task 6:", everyEven(25468 ));

// task 7
function sqrtSum(array) {
  return array.reduce((a, b) => Math.sqrt(a) + Math.sqrt(b));
}
console.log("Task 7:", sqrtSum([234,423,231,354,234]));


// task 8
function swap(a, b) {
  a^=b;
  b^=a;
  a^=b;
}

function rand(max) {
  let  rand = Math.random() * (max);
  return Math.floor(rand);
}

function shuffle(array) {
  for(let k = array.length; k > 1; k--) {
    let j = rand(k);
    let t = array[k-1];
    array[k-1] = array[j];
    array[j] = t;
  }
  return array;
}
console.log("Task 8", shuffle([1,2,3,4,5,6,7,8,9,0]));


// task 9
function unique(array) {
  const map = count(array).map;
  const result = [];
  map.forEach((val, key) => {
    if(val === 1){
      result.push(key);
    }
  });
  return result;
}

console.log("Task 9:", unique([7, 9, 0, -2]),
    unique([7, 7, 0, -2]),
    unique([7, 9, 9, -2]),
    unique([1, 1, 1, 1]));
