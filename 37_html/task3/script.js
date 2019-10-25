/* jshint  esversion:6 */
// добавление к p.synonyms класса synonym
const synonymsP = document.querySelectorAll("p.synonyms");
synonymsP.forEach((s) => {
    let text = s.textContent;
    let startSynomynsIdx = text.indexOf(":") + 1;
    let head = text.substring(0, startSynomynsIdx).trim();
    let synonymsString = text.substring(startSynomynsIdx).trim();
    let synonymsArray = synonymsString.split(",");

    let result = head + " ";
    synonymsArray.forEach((s, i) => {
        let syn = s.trim();
        result += `<span class="synonym">${syn}</span>`;
        if(i + 1 < synonymsArray.length){
            result += ", ";
        }
    });
    s.innerHTML = result;    
});