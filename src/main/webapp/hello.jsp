<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title></title>
</head>
<body>
  <h1>Hello jsp</h1>
<a href="abc">abc</a>
<c:forEach begin="1" end="10" step="3" var="i">
  ${i}--
</c:forEach>
</body>
</html>
