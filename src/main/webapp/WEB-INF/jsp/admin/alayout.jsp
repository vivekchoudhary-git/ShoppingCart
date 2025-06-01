<%-- <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> --%>
    
    <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
    <!-- testing only (manually setting the showSearch value "true" -->
    <%-- <c:set var="showSearch" value="true" /> --%>
    
    
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  -->              <!--   notice- this is different from veerpal sir alayout.jsp by vivek -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body style="margin: 0px;">
<table width="100%" height="100%" cellspacing="6">

<!-- header starts -->
<tr><td colspan="2" height="10%" style="border : 1px solid rgb(3,78,252)"><tiles:insertAttribute name="ah"></tiles:insertAttribute></td></tr>
<!-- Conditionally include search bar (suggested by chatGPT) -->
<c:if test="${showSearch}">
  <tr>
    <td colspan="2">
      <jsp:include page="/WEB-INF/jsp/admin/search.jsp" />
    </td>
  </tr>
</c:if>
<!-- header ends -->

<!-- body starts -->
<tr><td height="80%"><tiles:insertAttribute name="ab"></tiles:insertAttribute></td></tr>
<!-- body ends -->

<!-- footer starts -->
<tr><td colspan="2" style="background-color:rgb(3,78,252);" ><tiles:insertAttribute name="af"></tiles:insertAttribute></td></tr>
<!-- footer ends -->

</table>
</body>
</html>


                       <!--                                                               NOTE :
                         layout.jsp file is different from other jsp files kindly notice the details and dont use normal jsp file to create layout jsp by vivek  -->                                                          