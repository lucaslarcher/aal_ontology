<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Individuals</title>
</head>
<body>
<form name="f1" method="get" action="#">
       <select name="clr">
           <option>Red</option>
           <option>Blue</option>   
           <option>Green</option>
           <option>Pink</option>
       </select>
     <input type="submit" name="submit" value="Select Color"/>
    </form>
    <%-- To display selected value from dropdown list. --%>
     <% 
                String s=request.getParameter("clr");
                if (s !=null)
                {
                    out.println("Selected Color is : "+s);
                    request.setAttribute("meleca", "1");
                }
                
      %>
</body>
</html>