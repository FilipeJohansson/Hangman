<!DOCTYPE html>
<html lang="pt-br">

<head>
    <title>Hangman - Reset</title>
</head>

<body>
<%
	session.removeAttribute("wordToGuess");
	session.removeAttribute("word");
	session.removeAttribute("points");
	session.removeAttribute("misses");
	
	response.sendRedirect("index.jsp");
%>
</body>

</html>