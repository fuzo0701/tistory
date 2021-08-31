<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Upload Progress Bar</title>
</head>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<body>
	<form enctype="multipart/form-data">
		<input type="file" name="data"> <input type="submit">
	</form>
	<br />
	<br />
	<div class="progres-label"></div>
	<div id="progressbar"></div>
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.form/4.2.2/jquery.form.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
	<script>
		$(function() {
			var progressbar = $("#progressbar");
			var progressLabel = $(".progress-label");
				progressbar.progressbar({
					value : true,
					change : function() {
						progressLabel.text("Current Progress: "
								+ progressbar.progressbar("value") + "%");
					},
					complete : function() {
						progressLabel.text("Complete!");
						$(".ui-dialog button").last().trigger("focus");
						}
				});
                $('form').ajaxForm({
						url : "upload",
						type : "POST",
						beforeSubmit : function(arr, $form, options) {
							progressbar.progressbar("value", 0);
						},
						uploadProgress : function(event, position, total,
								percentComplete) {
							progressbar.progressbar("value", percentComplete);
						},
						success : function(text, status, xhr, element) {
							progressbar.progressbar("value", 100);
						}
					});
		});
	</script>
</body>
</html>