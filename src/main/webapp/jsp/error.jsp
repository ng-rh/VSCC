<%-- 
    Document   : error
    Created on : 11 Oct, 2022, 10:45:47 AM
    Author     : aaditya
--%>

  <%@page import="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Errors</title>
    </head>
    <body>
        
        
       
        <%  if(request.getAttribute("error")!=null) {  %>
        
        <% Map<String,String> error=(Map<String,String>)request.getAttribute("error");    %>
        
        <% for(String key:error.keySet()) {              %>
        <h3> <%= error.get(key)  %>  </h3>
        
        <%} }%>
        
        
        
        
        <div class="row">
            <input type="button" onclick="window.history.back()" value="Back"/>
            
            
        </div>
        
    </body>
</html>
