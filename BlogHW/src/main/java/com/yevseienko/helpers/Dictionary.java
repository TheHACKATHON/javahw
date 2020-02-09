package com.yevseienko.helpers;

public class Dictionary {
  public static class SessionAttribute {
    public static final String USER = "user";
    public static final String LAST_EMAIL = "lastEmail";

    public static final String CURRENT_USERS_COUNT = "currentUsers";
    public static final String TOTAL_USERS_COUNT = "totalUsers";
  }

  public static class URL {
    public static final String ADMIN = "/admin";
    public static final String LOGIN = "/admin/login";
    public static final String LOGOUT = "/admin/logout";
    public static final String ADMIN_ARTICLE = "/admin/article";
    public static final String DELETE_ARTICLE = "/admin/article/delete";

    public static final String HOME = "/";
    public static final String ARTICLE = "/article";
  }

  public static class JSP {
    public static final String ADMIN = "/WEB-INF/pages/admin/index.jsp";
    public static final String LOGIN = "/WEB-INF/pages/admin/login.jsp";
    public static final String EDIT_ARTICLE = "/WEB-INF/pages/admin/article.jsp";

    public static final String HOME = "/WEB-INF/pages/index.jsp";
    public static final String VIEW_ARTICLE = "/WEB-INF/pages/article.jsp";

    public static class Error {
      public static final String CODE_404 = "/WEB-INF/pages/errors/404.jsp";
      public static final String CODE_401 = "/WEB-INF/pages/errors/401.jsp";
    }
  }

  public static class ErrorMessage{
    public static final String WRONG_PASSWORD =
        "Your email address or password is incorrect!";
  }

  public static class StoredProcedures{
    public static final String GET_ARTICLES = "get_posts_sp";
    public static final String GET_ARTICLE = "get_post_sp";
    public static final String CREATE_ARTICLE = "create_post_sp";
    public static final String UPDATE_ARTICLE = "update_post_sp";
    public static final String DELETE_ARTICLE = "delete_post_sp";

    public static final String GET_USER_BY_EMAIL = "get_user_by_email_sp";
  }
}
