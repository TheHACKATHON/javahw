const main = document.querySelector("html > body > main");
const scripts = main.querySelectorAll("script");
const taskPath = main.getAttribute("data-taskPath");

const loadJS = function(url, implementationCode, location, type){
    const scriptTag = document.createElement('script');
    scriptTag.src = url;
    scriptTag.type = type;

    scriptTag.onload = implementationCode;
    scriptTag.onreadystatechange = implementationCode;

    location.appendChild(scriptTag);
};
const yourCodeToBeCalled = function(){ };

function addTaskPath(link) {
    return `${taskPath}/${link.replace("./", "")}`;
}

function replaceScr(el){
    const src = el.getAttribute("src");
    loadJS(addTaskPath(src), yourCodeToBeCalled, main, el.type);
}

function replaceLink(el){
    let link;
    if(el.href && el.tagName !== "A"){
        link = el.getAttribute("href");
        el.href = addTaskPath(link);
    } else if(el.src){
        link = el.getAttribute("src");
        el.src = addTaskPath(link);
    }
}

scripts.forEach(replaceScr);

setTimeout(() => {
    const links = main.querySelectorAll("*[href]");
    const links2 = main.querySelectorAll("*[src]:not(script)");

    links.forEach(replaceLink);
    links2.forEach(replaceLink);
}, 100);
