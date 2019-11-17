const logoutButton = document.querySelector("#logout");
function logout(){
    document.cookie = "authtoken=";
    location.reload();

}

logoutButton.addEventListener("click", logout);