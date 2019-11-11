<%@ page import="com.yevseienko.models.Homework" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yevseienko.models.Task" %>
<jsp:useBean id="taskId" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="homeworkId" scope="request" type="java.lang.Integer"/>
<jsp:useBean id="menuPosition" scope="request" type="java.lang.String"/>
<div class="sidebar <%=menuPosition%>">
    <div class="side-title">
        <span>Homeworks</span>
        <button class="sidebar-btn"><%=menuPosition.equals("left") ? "Right" : "Left"%></button>
    </div>
    <div class="side-items">
        <%
            ArrayList<Homework> homeworks = (ArrayList<Homework>) request.getAttribute("homeworks");
            String checked = "";
            String selectedClass = "";
            for (Homework hw : homeworks) {
              if(hw.getId() == homeworkId){
                  checked = "checked";
              }
                out.print("<div data-id=\"" + hw.getId() + "\" class=\"item\"><input type=\"checkbox\" " + checked + " class=\"collapse\" id=\"" + hw.getId() + "\">" +
                        "<label for=\"" + hw.getId() + "\">" + hw.getName() + "</label>");
                for (Task task : hw.getTasks()) {

                  if(hw.getId() == homeworkId && task.getId() == taskId){
                      selectedClass = "selected";
                  }
                    out.print("<div data-path=\"" + task.getPath() + "\" class=\"item subItem " + selectedClass + "\">" +
                            "<a href=\"/homework?id=" + hw.getId() + "&task=" + task.getId() + "\">" + task.getName() + "</a></div>");
                    selectedClass = "";
                }
                out.print("</div>");
                if(!checked.isEmpty()){
                    checked = "";
                }
            }
        %>
    </div>
</div>