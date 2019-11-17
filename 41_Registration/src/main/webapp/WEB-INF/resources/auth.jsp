<jsp:useBean id="error" scope="request" type="java.lang.String"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../../style/reg-form.css">
    <title>Form</title>
</head>
<body>
<div class="error">
    <span><%=error%></span>
</div>
<form method="post" action="/login">
    <div class="block">
        <h3>Login form</h3>
        <label class="username side-icon">
            <input type="text" placeholder="Username" name="username" required>
        </label>
        <label class="passwd side-icon">
            <input type="password" name="password" placeholder="Password" required>
        </label>
    </div>
    <hr>
    <div class="form-footer">
        <input type="submit" value="Login">
    </div>
</form>

<form method="post" action="/registration">
    <div class="block">
        <h3>Registration form</h3>
        <label class="username side-icon">
            <input type="text" placeholder="Username" name="username" required>
        </label>
        <label class="email side-icon">
            <input type="email" placeholder="Email address" name="email" required>
        </label>
        <div>
            <label class="passwd side-icon">
                <input type="password" name="password" placeholder="Password" required>
            </label>
            <label class="conf-passwd side-icon">
                <input type="password" placeholder="Confirm password" name="conf-password" required>
            </label>
        </div>
    </div>
    <div class="block">
        <h3>Personal details</h3>
        <div>
            <label>
                <input type="text" name="first" placeholder="First name" required>
            </label>
            <label>
                <input type="text" name="last" placeholder="Last name" required>
            </label>
        </div>
        <div>
            <label>
                <select name="gender" required>
                    <option value="male">Male</option>
                    <option value="female">Female</option>
                    <option value="other">Other</option>
                </select>
            </label>
            <label>
                <input type="date" placeholder="mm/dd/yyyy" name="birth" required>
            </label>
        </div>
    </div>
    <div class="block">
        <label>
            <input type="checkbox" name="subscribe">
            I want to receive news and special offers
        </label>
        <label>
            <input type="checkbox" name="terms" required>
            I agree with the <a href="#">Terms and Conditions</a>
        </label>
    </div>
    <hr>
    <div class="form-footer">
        <input type="submit" value="Register">
    </div>
</form>

</body>
</html>
