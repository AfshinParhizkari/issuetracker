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
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/api-ui">API-UI</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/api-json">API-JSON</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Story
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/api/stories">Stories</a></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/api/story?issueid=">Create Story</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="${pageContext.request.contextPath}/rst/stories/plan">Planning</a></li>
                    </ul>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Bug</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Developer</a>
                </li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/h2/">H2 database</a></li>
            </ul>
        </div>
    </div>
</nav>
<article>
    <br>
        there are three parts in this app:</br>
        <ul>Story has seven services:
            <li>X:GET: return All stories if the issueID be empty or One story via issueID</li>
            <li>X:GET /capacity: the default based on requirement is 10 epv per developer. for better test you can change it</li>
            <li>X:DELETE: delete a story via issueID</li>
            <li>X:POST: new story insert in h2 database</li>
            <li>X:PUT: current story update in h2 database</li>
            <li>X:PUT: /status: developers can change status of stories but completed</li>
            <li>X:GET: /plan: this service can assign the stories to developers in different sprints</li>
        </ul>
        <ul>Bug has six services:
            <li>X:GET: return All bugs if the issueID be empty or One bug via issueID</li>
            <li>X:DELETE: delete a bug via issueID</li>
            <li>X:POST: new bug insert in h2 database</li>
            <li>X:PUT: current bug update in h2 database</li>
            <li>X:PUT: /status: developers can change status of bugs but resolved</li>
            <li>X:PUT: /assignment: developers can assign bug to themselves but resolved</li>
        </ul>
        <ul>Developer has five services:
            <li>X:GET: return All developers if the devID be empty or One developer via devID</li>
            <li>X:GET: /load: fill sprint number that return all developer's load</li>
            <li>X:DELETE: delete a developer via devID</li>
            <li>X:POST: new developer insert in h2 database</li>
            <li>X:PUT: current developer update in h2 database</li>
        </ul>
    </section>
</article><br>

<footer class="navbar navbar-default navbar-fixed-bottom"><p>Developed by Afshin Parhizkari</p></footer>
</body>
</html>