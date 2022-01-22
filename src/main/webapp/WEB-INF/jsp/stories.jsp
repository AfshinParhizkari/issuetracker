<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	<%--_______________________________________Base and Menu__________________________________--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<script src="${pageContext.request.contextPath}/statics/vendors/jquery-3.6.0.min.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/vendors/bootstrap/bootstrap.min-5.1.0.css">
	<script src="${pageContext.request.contextPath}/statics/vendors/bootstrap/popper.min.js"></script>
	<script src="${pageContext.request.contextPath}/statics/vendors/bootstrap/bootstrap.min-5.1.0.js"></script>
	<%--_______________________________________DataTables______________________________________________--%>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/DataTables-1.11.0/css/jquery.dataTables.min.css">
	<script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/DataTables-1.11.0/js/jquery.dataTables.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/datatables.min.css">
	<script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/datatables.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/statics/vendors/DataTables/Responsive-2.2.9/css/responsive.bootstrap5.min.css">
	<script  type="text/javascript" charset="utf8" src="${pageContext.request.contextPath}/statics/vendors/DataTables/Responsive-2.2.9/js/responsive.bootstrap5.min.js"></script>
	<%--_______________________________________custom_____________________________________________________--%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/statics/fonts/font.css">
    <title>Stories</title>
<script>
	function dtLoad() {
		$('#table_id').DataTable({
			responsive: true,
			order: [1, 'creationdate'],
			deferRender: true,
			info: false,
			initComplete: function () {
				this.api().columns([1, 2, 8]).every(function () {
					let column = this;
					let select = $('<select><option value=""></option></select>')
							.appendTo($(column.footer()).empty())
							.on('change', function () {
								let val = $.fn.dataTable.util.escapeRegex(
										$(this).val()
								);
								column
										.search(val ? '^' + val + '$' : '', true, false)
										.draw();
							});
					column.data().unique().sort().each(function (d, j) {
						select.append('<option value="' + d + '">' + d + '</option>')
					});
				});
			}
		});
	}

	let developers;
	let stories;
	function getObjects() {
		jQuery.ajax({
			url:'${pageContext.request.contextPath}/rst/stories/',
			type:'get',
			data:{issueID: $('#pkey').val()},
			success: function(stories){
				let tableBlock = document.getElementById('table_body_id');
				// Clear table body to put new records
				$('#table_id').DataTable().destroy();
				tableBlock.innerHTML ="";
				/*returnData = JSON.parse(returnData);*/
				for (let i in stories)
					tableBlock.innerHTML = tableBlock.innerHTML+createColumn(stories[i]);
				dtLoad();
			},
			error: function (){
				alert("Stories could not fetch");
			}
		})
	}
	function createColumn(story) {
		 return "<tr><td></td>"+
				 "<td>"+getName(story.assignedev)+"</td>"+
				 "<td>"+story.status+"</td>"+
				 "<td>"+story.title+"</td>"+
				 "<td>"+story.description+"</td>"+
				 "<td>"+story.estimatedpoint+"</td>"+
				 "<td>"+story.creationdate+"</td>"+
				 "<td>"+story.sprint+"</td>"+
				 "<td><a href='${pageContext.request.contextPath}/api/story?issueid="+story.issueid+"'>"+story.issueid+"</a></td>"+
		 		 "</tr>";
	}
	function ifEmpty(val){
		return (val === undefined || val == null || val.length <= 0) ? "" : val;
	}
	function getName(code) {
		for (let i in developers) {
			let developer = developers[i];
			if (developer.devid==code)
				return developer.devname;
		}
		return "";
	}
</script>
</head>
<body class="container-fluid vh-100"><br>
	<div class="float-start mx-1 mb-1">
	<div class="input-group"><!-- find All / id -->
		<input class="form-control rfs" id="pkey" title="Please enter issueID" name="code" type="text" placeholder="issueID">
		<button class="btn btn-secondary float-start rfs" title="Search Story" onclick="getObjects()"><i class="fas fa-search"></i>&nbsp;</button>
	</div>
	</div>
	<a type="button" class="btn btn-primary rfs" title="New story" href="${pageContext.request.contextPath}/api/story?issueid=" target="_self"><i class="fa fa-pen-square"></i>&nbsp;</a>
	<a type="button" class="btn btn-outline-secondary rfs" title="close" href="${pageContext.request.contextPath}/" target="_self">&times;</a><br>
<table id="table_id" class="display w-100 rfs">
    <thead><tr>
		<th></th>
		<th>AssignedTo</th>
		<th>Status</th>
		<th>Title</th>
		<th>Description</th>
		<th>EPV</th>
		<th>Creation Date</th>
		<th>Sprint</th>
		<th>issueID</th>
	</tr></thead>
	<tbody id="table_body_id"></tbody>
	<tfoot><tr>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
		<th></th>
	</tr></tfoot>
</table>
<div class="modal" id="msgError"></div>
</div>
<script>
	$(document).ready( function () {
		jQuery.ajax({
			url:'${pageContext.request.contextPath}/rst/developers/',
			type:'get',
			contentType:'application/json; charset=utf-8',
			data:(""),
			success: function(returndata){
				developers=returndata;
			}
		})
	});
</script>
</body>
</html>