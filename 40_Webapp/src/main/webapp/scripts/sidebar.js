const sidebar = document.querySelector(".sidebar");
const sidebarBtn = sidebar.querySelector(".sidebar-btn");
const sidebarClass = "right";
const sidebarClassDefault = "left";
const sidebarCookieName = "menuPosition";

sidebarBtn.addEventListener("click", () => {
    sidebar.classList.toggle(sidebarClass);
    if(sidebar.classList.contains(sidebarClass)){
        sidebarBtn.textContent = "Left";
        document.cookie = `${sidebarCookieName}=${sidebarClass}`;
    } else {
        sidebarBtn.textContent = "Right";
        document.cookie = `${sidebarCookieName}=${sidebarClassDefault}`;
    }
});