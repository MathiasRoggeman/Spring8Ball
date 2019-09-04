<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' href='/resources/css/bootstrap.min.css'>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class='well'>
<h1>Ask a question!</h1>
<br/>
<form method=POST action='questionAction' onsubmit='return validate()'>
		<div class='form-group row'>
		 	<div class='col-xs-4'>
				<label for='question'>Question: </label>
		    		<input type='text' class='form-control' name='question' id='question'>
	    		</div>
	    	</div>
		<input type=SUBMIT value='Save'>
		<br/><br/>
<a href='/'>List all</a>
</form>

<br/><br/>
</div>
</body>
</html>