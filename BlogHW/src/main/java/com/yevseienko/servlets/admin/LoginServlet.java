package com.yevseienko.servlets.admin;

import com.yevseienko.data.interfaces.IAuthDataService;
import com.yevseienko.helpers.AppContext;
import com.yevseienko.helpers.Dictionary;
import com.yevseienko.models.user.BasicUser;
import com.yevseienko.validators.EmailValidator;
import com.yevseienko.validators.interfaces.IValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = Dictionary.URL.LOGIN, name = "login")
public class LoginServlet extends HttpServlet {
  private static final String EMAIL_PARAM = "email";
  private static final String PASSWORD_PARAM = "password";
  private static final String VALIDATE_ERROR_ATTR = "validationError";
  private static final String EMAIL_VALIDATION_ERROR_ATTR = "emailValidationError";

  private IAuthDataService authService;

  @Override
  public void init() {
    authService = AppContext.getBean(IAuthDataService.class);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    HttpSession session = req.getSession(true);
    BasicUser user = (BasicUser) session.getAttribute(Dictionary.SessionAttribute.USER);
    if (user != null) {
      resp.sendRedirect(Dictionary.URL.ADMIN);
      return;
    }
    req.getRequestDispatcher(Dictionary.JSP.LOGIN).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(true);
    StringBuilder validationError = new StringBuilder();
    String emailValidationError = "";

    String email = req.getParameter(EMAIL_PARAM);
    String password = req.getParameter(PASSWORD_PARAM);

    IValidator emailValidator = new EmailValidator();
    if (email != null && email.isEmpty() || !emailValidator.isValid(email)) {
      emailValidationError = emailValidator.getErrorText();
    }
    else if (password != null && password.isEmpty()) {
      validationError.append(Dictionary.ErrorMessage.WRONG_PASSWORD);
    }
    else {
      BasicUser user = authService.login(email, password);
      if(user == null){
        validationError.append(Dictionary.ErrorMessage.WRONG_PASSWORD);
      } else {
        session.setAttribute(Dictionary.SessionAttribute.USER, user);
      }
    }

    session.setAttribute(Dictionary.SessionAttribute.LAST_EMAIL, email);
    String error = validationError.toString();
    if (!error.isEmpty() || !emailValidationError.isEmpty()) {
      req.setAttribute(VALIDATE_ERROR_ATTR, error);
      req.setAttribute(EMAIL_VALIDATION_ERROR_ATTR, emailValidationError);
      req.getRequestDispatcher(Dictionary.JSP.LOGIN).forward(req, resp);
      return;
    }

    resp.sendRedirect(Dictionary.URL.ADMIN);
  }
}
