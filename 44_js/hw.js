function isUpperCaseFirstLetter(str) {
    return /^[A-ZА-Я]/g.test(str);
}

function isValidEmail(str) {
    return /^([a-zA-Z0-9_.\-])+@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test(str);
}

function trimString(str) {
    str = str.replace(/^(\s{2,}|\t|\n)|(\s{2,}|\t|\n)$/, "");
    return str.replace(/(\s{2,}|\t|\n)/, "");
}

function thousandsSeparators(num) {
    let fullNumStr = String(num);
    let numStr = String(Math.floor(num));
    let idx = fullNumStr.indexOf('.');
    let f = "";
    if(idx !== -1)
    {
        f = fullNumStr.substr(idx);
    }
    let m = /\d{4}\b/g.exec(numStr);
    numStr =
      numStr.substr(0, m.index + 1) + "," +
      numStr.substr(m.index + 1) +
      f;

    return numStr;
}

