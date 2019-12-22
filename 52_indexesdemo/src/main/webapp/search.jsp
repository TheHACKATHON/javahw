<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Search</title>
</head>
<style>
    #search-form {
        display: flex;
        flex-flow: column nowrap;
    }

    #pre-loader {
        display: none;
    }

    #error {
        display: none;
    }
</style>
<body>
<form id="search-form">
    <label>
        <input type="text" name="first_name" required value="Masha">
        <span>First name</span>
    </label>
    <label>
        <input type="text" name="last_name" required value="Smith">
        <span>Last name</span>
    </label>
    <input type="submit">
</form>
<span>Request time: <span id="time"></span> ms</span>
<span>Count: <span id="count">0</span> </span>
<span id="pre-loader">Searching...</span>
<span id="error">Error</span>
<table>
    <thead>
    <tr>
        <th>Id</th>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Gender</th>
    </tr>
    </thead>
    <tbody>

    </tbody>
</table>
<script>
    const form = document.querySelector("#search-form");
    const fname = form.querySelector("input[name='first_name'");
    const lname = form.querySelector("input[name='last_name'");
    const tbody = document.querySelector("table tbody");
    const preloader = document.querySelector("#pre-loader");
    const error = document.querySelector("#error");
    const requestTime = document.querySelector("#time");
    const count = document.querySelector("#count");

    function handleJson(data) {
        data.personList.forEach(user => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${user.id}</td>
                <td>${user.fname}</td>
                <td>${user.lname}</td>
                <td>${user.gender}</td>
            `;
            tbody.append(row);
        });
        requestTime.textContent = data.requestTime;
        count.textContent = data.personCount;
        preloader.style.display = "none";
    }

    function handleError() {
        error.style.display = "block";
    }

    function onFormSubmit(e) {
        e.preventDefault();

        const data = {
            first_name: fname.value,
            last_name: lname.value
        };

        tbody.innerHTML = "";
        preloader.style.display = "block";

        fetch("search", {
            method: 'post',
            headers: {
                "Content-type": "application/json",
            },
            body: JSON.stringify(data),
        })
            .then(response => response.json())
            .then(handleJson)
            .catch(handleError);
    }

    form.addEventListener("submit", onFormSubmit);
</script>
</body>
</html>
