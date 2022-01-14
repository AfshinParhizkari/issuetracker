<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%--
  @Project     Issue tracker
  @Author      Afshin Parhizkari
  @Date        2022 - 01 - 14
  @Time        8:17 PM
  Created by   IntelliJ IDEA.
  Email:       Afshin.Parhizkari@gmail.com
  Description:
--%>
<html>
<head>
    <title>Issue Planning</title>
    <%--_______________________________________Base and Menu__________________________________--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${pageContext.request.contextPath}/statics/vendors/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/vendors/bootstrap/bootstrap.min-5.1.0.css">
    <script src="${pageContext.request.contextPath}/statics/vendors/bootstrap/bootstrap.min-5.1.0.js"></script>
    <%--_______________________________________DataTables______________________________________________--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/DataTables-1.11.0/css/jquery.dataTables.min.css">
    <script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/DataTables-1.11.0/js/jquery.dataTables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/datatables.min.css">
    <script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/datatables.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/Responsive-2.2.9/css/responsive.bootstrap5.min.css">
    <script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/Responsive-2.2.9/js/responsive.bootstrap5.min.js"></script>

</head>
<body class="container-fluid vh-100">
<header><p class="text-center">Welcome to Issue Planning application</p></header>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="http://localhost:8080/issue/h2/">H2 database</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/issue/api-ui">API-UI</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/issue/api-json">API-JSON</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/issue/api/stories">Stories</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="http://localhost:8080/issue/rst/story/plan">Planning</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Bugs</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Developers</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<article>
    <br>
        there are three parts in this app:</br>
        <ul>Story has six services:
            <li>find</li>
            <li>delete</li>
            <li>upsert</li>
            <li>setCapacity</li>
            <li>change status</li>
            <li>plan</li>
        </ul>
        <ul>Bug has five services:
            <li>find</li>
            <li>delete</li>
            <li>upsert</li>
            <li>assign</li>
            <li>change status</li>
        </ul>
        <ul>Developer has three services:
            <li>find</li>
            <li>delete</li>
            <li>upsert</li>
        </ul>
    </section>
</article>

<footer class="navbar navbar-default navbar-fixed-bottom"><p>Created by Afshin Parhizkari</p></footer>
</body>
</html>