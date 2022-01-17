<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isELIgnored="false" %>
<%--
  @Project     Issue tracker
  @Author      Afshin Parhizkari
  @Date        2022 - 01 - 18
  @Time        6:43 PM
  Created by   IntelliJ IDEA.
  Email:       Afshin.Parhizkari@gmail.com
  Description:
--%>
<html>
<head>
    <%--_______________________________________Base__________________________________--%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="${pageContext.request.contextPath}/statics/vendors/jquery-3.6.0.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/vendors/bootstrap/bootstrap.min-5.1.0.css">
    <title>Duty Action</title>
    <%--_______________________________________custom_____________________________________________________--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/css/dataEntry_jump.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/statics/fonts/font.css">
<script > /* Send Data to controller */
    function saveData() {
        let story = {};
        story['issueid'] = $('#issuepk').val();
        story['title'] = $('#titleid').val();
        story['description'] = $('#descriptionid').val();
        story['estimatedpoint'] = $('#epvid').val();
        jQuery.ajax({
            url:'${pageContext.request.contextPath}/rst/story/save',
            type:'put',
            contentType:'application/json; charset=utf-8',
            data:JSON.stringify(story),
            success: function(){//xhr.responseText
                alert("Story is persisted");
            },
            error: function (){
                alert("Story could not persist");
            }
        });
    }
    function deleteData() {
    jQuery.ajax({
        url:'${pageContext.request.contextPath}/rst/story/delete',
        type:'delete',
        contentType:'application/json; charset=utf-8',
        data:JSON.stringify({issueid: $('#issuepk').val()}),
        success: function(){//xhr.responseText
            alert("Story is deleted");
        },
        error: function (){
            alert("Story could not delete");
        }
    });
    }
</script>
</head>
<body class="container-fluid vh-100 rfs">
<div class="col-sm-10 col-md-6 col-lg-4 mx-5 is-quarter">
    <input id="issuepk" type="hidden" value="${story.issueid}"/>
    <div class="my-5"><input class="form-control rfs" title="enter the title of story" type="text" value="${story.title}" id="titleid"/><label class="rfs" for="titleid">Title</label></div>
    <div class="my-5"><input class="form-control rfs" title="enter the description of story" type="text" value="${story.description}" id="descriptionid"/><label class="rfs" for="descriptionid">Description</label></div>
    <div class="my-5"><input class="form-control rfs" title="enter the estimated point value of story" type="number" value="${story.estimatedpoint}" id="epvid"/><label class="rfs" for="epvid">Estimated Point Value</label></div>
    <button class="btn btn-outline-primary mx-2 float-start rfs" id="save" title="Save" onclick="saveData()"><i class="fa fa-save"></i>&nbsp;</button>
    <button id="delete" class="btn btn-outline-warning float-start rfs" title="Delete" onclick="deleteData()"><i class="fa fa-trash"></i>&nbsp;</button>
    <a type="button" class="btn btn-outline-secondary mx-2 rfs" title="close" href="${pageContext.request.contextPath}/api/stories" target="_self">&times;</a><br>
</div>
<script>
    $(document).ready( function () {
        $("#descriptionid").focus();
        $("#epvid").focus();
        $("#titleid").focus();
    });
    const inputs = document.getElementsByClassName("form-control");
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].addEventListener("focus", function (event) {
            this.nextElementSibling.classList.add("active");
        });
        inputs[i].addEventListener("focusout", function (event) {
            if (this.value === "") {
                this.nextElementSibling.classList.remove("active");
            }
        });
    }
</script>
</body>
</html>