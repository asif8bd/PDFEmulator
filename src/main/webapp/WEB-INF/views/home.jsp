<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Spring CRUD Example || ASIF</title>
<link href="<c:url value="resources/bootstrap.min.css" />"
	rel="stylesheet">
<link
	href="<c:url value="resources/style.css" />"
	rel="stylesheet">
<script src="resources/src-min/ace.js"></script>
<script src="resources/jquery.min.js"></script>

</head>
<body>

	<div class="container">
		<h2 style="text-align: center; color: green;">XSL to PDF
			generator</h2>
		<hr />
		<form method="post" action="generate" enctype="multipart/form-data">

			<div class="form-group col-md-6">
				<label for="xmlFile">XML File:</label> <input type="file"
					id="xmlFile" name="file" accept=".xml">

			</div>
			<div class="form-group col-md-6">
				<label for="xslFile">XSL File:</label> <input type="file"
					id="xslFile" name="file" accept=".xsl">

			</div>

			<div class="form-group col-md-6">
				<label for="pdfFile">PDF File Name</label> <input type="text"
					class="form-control" id="pdfFile" name="pdfName"
					placeholder="Enter PDF File name">
			</div>
			<div class="form-group col-md-6">
				<label for="fopVersion">FOP Version</label> <select
					class="form-control" id="fopVersion" name="fopVersion">
					<option value="0">FOP 0.20</option>
					<option value="1">FOP 0.94</option>
					<option value="2">FOP 1.10</option>
				</select>
			</div>
			<div class="text-center">
				<button type="submit" class="btn btn-primary">Submit</button>
			</div>
		</form>

		<c:if test="${myPdf!=null}">
			<p>
				<a href=" file:///${myPdf}" target="_blank">View PDF</a> at ${myPdf}
			</p>
		</c:if>
		<div>
			<h1>
				XSL Code Editor
				<h1>
					<xmp id="editor" style="height:200px;">${myxml}</xmp>
					<hr/>
					<textarea name="content" id="content" disabled="disabled"></textarea>
					<script>
						var editor = ace.edit("editor");
						editor.setTheme("ace/theme/monokai");
						editor.getSession().setMode("ace/mode/xml");
						var textarea = $('#content');
						editor.getSession().on('change', function() {
							textarea.val(editor.getSession().getValue());
						});

						textarea.val(editor.getSession().getValue());
						$('#content').flexText();
					</script>
		</div>
	</div>
</body>
</html>