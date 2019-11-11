/* jshint esversion: 6 */

const thead = document.querySelector("table > thead");
const tbody = document.querySelector("table > tbody");
const colCount = 25;
const rowCount = 40;
const maxRand = 100;

function getRandomInt(max) {
  return Math.floor(Math.random() * Math.floor(max));
}

// заполнение таблицы
function init() {
  const tr = document.createElement("tr");
  thead.appendChild(tr);

	for (let i = 0; i < rowCount; i++){
		const trBody = document.createElement("tr");
		for (let j = 0; j < colCount + 1; j++) {

			if(i == 0){
				const th = document.createElement("th");
				const text = document.createElement("span");
				if (j !== 0) {
					text.textContent = `Header ${j}`;
				}
				th.appendChild(text);
					tr.appendChild(th);
			}

			const td = document.createElement("td");
			const text = document.createElement("span");

			if(j == 0){
				text.textContent = i+1;
			} else {
				text.textContent = getRandomInt(maxRand);
			}
			
			td.appendChild(text);
			trBody.appendChild(td);
			tbody.appendChild(trBody);
		}
	}
}

init();
