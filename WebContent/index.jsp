<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <title>Hangman</title>

    <!-- Bootstrap v5.0 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">

    <link rel="stylesheet" href="./css/style.css">

    <!-- Bootstrap icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">

    <!-- Bootstrap v5.0 JS -->
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>

    <!-- jQuery 3.6 -->
    <script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>

<body>
<jsp:useBean id="game" class="action.HangMan" />
<jsp:useBean id="xmlParse" class="action.XMLParse" />
<%
	int points = 0;
	if (session.getAttribute("points") == null) {
		game.setPoints(6);
		session.setAttribute("points", game.getPoints());
	}
	points = (Integer) session.getAttribute("points");
	
	String wordToGuess = "";
	if (session.getAttribute("wordToGuess") == null) {
		game.setWordToGuess(xmlParse.getRandomWord());
		session.setAttribute("wordToGuess", game.getWordToGuess());
		System.out.println("Word to guess: " + session.getAttribute("wordToGuess"));
	}
	wordToGuess = (String) session.getAttribute("wordToGuess");

	String word = "";
	if (session.getAttribute("word") == null) {
		game.setWord(wordToGuess);
		session.setAttribute("word", game.getWord());
	}
	word = (String) session.getAttribute("word");

	List<Integer> charPositionsInWord;

	String guessString = request.getParameter("guess");
	char guess = ' ';
	if (request.getParameter("guess") != null)
		if (guessString.length() > 0)
			guess = (char) request.getParameter("guess").toUpperCase().trim().charAt(0);

	List<Character> misses = (List<Character>) session.getAttribute("misses");
	if (misses == null)
		session.setAttribute("misses", game.getMisses());

	if (request.getParameter("guess") != null) {
		if (!game.verifyLetter(guess))
			System.out.println("You need to type a letter");
		else {

			charPositionsInWord = game.countChar((String) session.getAttribute("wordToGuess"), guess);

			if (charPositionsInWord.isEmpty()) {
				System.out.println("Wrong guess");
				if (!misses.contains(guess)) {
					misses.add(guess);
					game.setMisses(misses);
					session.setAttribute("misses", game.getMisses());
					System.out.println("Misses " + session.getAttribute("misses"));
					
					game.setPoints(--points);
					session.setAttribute("points", game.getPoints());
				}
			} else {
				System.out.println("Got it");
				for (int i = 0; i < charPositionsInWord.size(); i++) {
					session.setAttribute("word",
							game.replaceChar((String) session.getAttribute("word"), guess, charPositionsInWord.get(i)));
				}
			}

			word = (String) session.getAttribute("word");
			points = (Integer) session.getAttribute("points");
		}
	}
%>
	<nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <h3>Hangman</h3>
        </div>
    </nav>

    <main class="container">
        <div class="row px-5">
            <div class="col-md-12 mt-4 px-4">
                <div class="row">
                    <div class="row row-cols-1 row-cols-lg-3 mt-0 align-items-stretch g-3">
                        <div class="col d-flex">
                            <div id="stickyman" class="card bg-light overflow-hidden rounded-3">
                                <div class="wrapper">
                                	<% if (points < 6) { %>
                                    	<div id="sticky" class="head"></div>
                                    <% if (points < 5) { %>
                                    	<div id="sticky" class="torso"></div>
                                    <% if (points < 4) { %>
                                    	<div id="sticky" class="leftArm"></div>
                                    <% if (points < 3) { %>
                                    	<div id="sticky" class="rightArm"></div>
                                    <% if (points < 2) { %>
                                    	<div id="sticky" class="leftLeg"></div>
                                    <% if (points < 1) { %>
                                    	<div id="sticky" class="rightLeg"></div>
                                    <% } } } } } } %>                                    
                                </div> 
                            </div>
                        </div>

                        <div class="col-lg-8 d-flex">
                            <div class="card bg-light overflow-hidden rounded-3">
                                <header>
                                    <span>
                                        <b>Word:</b>
                                    </span>
									<h4 id="word"><%= word %></h4>
                                </header>
                                
                                <% if (points <= 0 || game.verifyWinning(word)) { %>
                                	<form action="reset.jsp" id="actionForm" class="row gy-2 gx-3 align-items-center">
                                <% if (points <= 0) { %>
	                                	<span id="state" name="lose">
	                                        <b>You lose :(</b>
	                                    </span>
	                            <% } %>
	                            <% if (game.verifyWinning(word)) { %>
	                                
	                                	<span id="state" name="won">
	                                        <b>You won! :D</b>
	                                    </span>
	                            <% } %>
	                            
	                                    <div class="col-auto">
	                                        <button type="submit" id="playAgain" class="btn btn-primary">Play Again</button>
	                                    </div>
	                                </form>
                                <% } else { %>
	                                <form action="index.jsp" id="guessForm" class="row gy-2 gx-3 align-items-center">
	                                    <div class="col-auto">
	                                        <label class="visually-hidden" for="autoSizingInput">Letter to guess</label>
	                                        <input type="text" id="guessInput" name="guess" maxlength="1" class="form-control" id="autoSizingInput" placeholder="Type a letter to guess">
	                                    </div>
	                                    <div class="col-auto">
	                                        <button type="submit" id="guessButton" class="btn btn-secondary">Guess</button>
	                                    </div>
	                                </form>
                                <% } %>

                                <footer>
                                    <div>
                                        <span>
                                            <b>Misses:</b>
                                        </span>
                                            <%
												if (misses != null) {
											%>
												<span id="misses">
													
												
											<%
												out.println(game.setUpMisses(misses));
											%>
												</span>
											<%
												}
											%>
                                    </div>
                                    
                                    <div class="card_reset">
                                        <form action="reset.jsp" id="resetForm" class="row gy-2 gx-3 align-items-center">
                                            <div class="col-auto">
                                                <button type="submit" id="resetButton" class="btn btn-danger">Reset</button>
                                            </div>
                                        </form>
                                    </div>
                                </footer>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </main>
</body>

</html>