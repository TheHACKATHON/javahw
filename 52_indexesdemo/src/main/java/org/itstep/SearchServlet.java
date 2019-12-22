package org.itstep;

import com.google.gson.Gson;
import org.itstep.models.Person;
import org.itstep.models.SearchObject;
import org.itstep.models.SearchResponseObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

  private String url;
  private String user;
  private String password;
  private final String getUsersQuery = "SELECT * FROM `person` WHERE `first_name` = ? AND `last_name` = ?;";

  @Override
  public void init(ServletConfig config) throws ServletException {
    super.init(config);

    url = getServletContext().getInitParameter("db:url");
    user = getServletContext().getInitParameter("db:user");
    password = getServletContext().getInitParameter("db:pass");

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ServletContext servletContext = getServletContext();
    servletContext.getRequestDispatcher("/search.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    long startTime = System.currentTimeMillis();
    Gson gson = new Gson();

    String params = getParams(req.getInputStream());
    SearchObject obj =  gson.fromJson(params, SearchObject.class);

    ArrayList<Person> personList = getUsers(obj.getFirstName(), obj.getLastName());

    long elapsedTime = System.currentTimeMillis() - startTime;
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter pw = resp.getWriter();
    SearchResponseObject responseObject = new SearchResponseObject(personList, elapsedTime, personList.size());
    pw.write(gson.toJson(responseObject));
    pw.flush();
  }

  private ArrayList<Person> getUsers(String fname, String lname) {
    ArrayList<Person> personList = new ArrayList<>();
    try (Connection conn = DriverManager.getConnection(url, user, password)) {
      PreparedStatement preparedStatement = conn.prepareStatement(getUsersQuery);
      preparedStatement.setString(1, fname);
      preparedStatement.setString(2, lname);
      try (ResultSet rs = preparedStatement.executeQuery()) {
        while (rs.next()) {
          personList.add(new Person(
              rs.getInt(1),
              rs.getString(2),
              rs.getString(3),
              rs.getString(4)
          ));
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return personList;
  }

  private String getParams(InputStream stream) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] buf = new byte[32];
    int r=0;
    while( r >= 0 ) {
      r = stream.read(buf);
      if( r >= 0 ) os.write(buf, 0, r);
    }
    String s = new String(os.toByteArray(), "UTF-8");
    return URLDecoder.decode(s, "UTF-8");
  }
}
