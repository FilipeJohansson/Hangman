package hangman.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class App {
    private static WebDriver driver;
    // private static List<String> urls = EnviromentConfig.getUrls();

    @Test
    private void smoke() {
        driver = EnviromentConfig.getDriver();
        driver.get(EnviromentConfig.getIndex());
        System.out.println("[+] Smoking: " + EnviromentConfig.getIndex());

        System.out.println("=> wordIsVisible()");
        wordIsVisible();

        System.out.println("=> guessFormIsVisible()");
        guessFormIsVisible();

        System.out.println("=> resetGameIsVisible()");
        resetGameIsVisible();

        System.out.println("=> othersAreVisible()");
        othersAreVisible();

        System.out.println("=> stickyBoyIsNotVisible()");
        stickyBoyIsNotVisible();

        // Close browser
        driver.quit();
    }

    /**
     * Test if the word to guess is visible
     */
    private void wordIsVisible() {
        WebElement word = driver.findElement(By.id("word"));
        Assert.assertTrue(word.isDisplayed(), "[!] Word is not visible");
    }

    /**
     * Test if the guess form and their elements are visible
     */
    private void guessFormIsVisible() {
        WebElement guessForm = driver.findElement(By.id("guessForm"));
        Assert.assertTrue(guessForm.isDisplayed(), "[!] Guess Form is not visible");

        // List<WebElement> guessForm = driver.findElements(By.id("guessForm"));

        WebElement guessInput = driver.findElement(By.id("guessInput"));
        Assert.assertTrue(guessInput.isDisplayed(), "[!] Guess Input is not visible");

        WebElement guessButton = driver.findElement(By.id("guessButton"));
        Assert.assertTrue(guessButton.isDisplayed(), "[!] Guess Button is not visible");
    }

    /**
     * Test if the reset button is visible
     */
    private void resetGameIsVisible() {
        WebElement resetButton = driver.findElement(By.id("resetButton"));
        Assert.assertTrue(resetButton.isDisplayed(), "[!] Reset Button is not visible");
    }

    /**
     * Test if other elements are visible
     */
    private void othersAreVisible() {
        WebElement navbar = driver.findElement(By.className("navbar"));
        Assert.assertTrue(navbar.isDisplayed(), "[!] Navbar is not visible");
    }

    /**
     * Test if sticky boy is not visible
     */
    private void stickyBoyIsNotVisible() {
        List<WebElement> stickBoy = driver.findElements(By.className("stick"));
        Assert.assertTrue(stickBoy.isEmpty(), "[!] Sticky boy is enabled");
    }

    /**
     * Test if a wrong guess is correctly passed through get parameter and if the
     * game correctly registered this wrong guess
     */
    @Test
    private void guessWrong() {
        driver = EnviromentConfig.getDriver();
        driver.get(EnviromentConfig.getIndex());

        char guess = 'X'; // Setting a guess

        // Find the input and send letter
        WebElement guessInput = driver.findElement(By.id("guessInput"));
        guessInput.sendKeys(String.valueOf(guess));

        // Click on guess button
        WebElement guessButton = driver.findElement(By.id("guessButton"));
        guessButton.click();

        // Verify if get param contain the letter
        String expectedUrlContains = "?guess=" + guess;
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlContains),
                "[!] Actual url is not the expected: " + expectedUrlContains);

        /*
         * Verify if the guess is right registered considering that words on file.xml
         * don't have 'X' letter on it
         */
        WebElement word = driver.findElement(By.id("word"));
        Assert.assertTrue(!word.getText().contains(String.valueOf(guess)),
                "[!] Word have a letter not expected: " + guess);

        // Verify if the wrong guess is correctly put on misses
        WebElement misses = driver.findElement(By.id("misses"));
        Assert.assertTrue(misses.getText().contains(String.valueOf(guess)),
                "[!] Guess don't have the expected letter: " + guess);

        List<WebElement> stickBoy = driver.findElements(By.className("stick"));
        Assert.assertTrue(stickBoy.isEmpty(), "[!] Sticky boy isn't enabled");

        // Close browser
        driver.quit();
    }

    /**
     * Test the game reset
     */
    @Test
    private void resetGame() {
        driver = EnviromentConfig.getDriver();
        driver.get(EnviromentConfig.getIndex());

        char guess = 'X'; // Setting a guess

        // Find the input and send letter
        WebElement guessInput = driver.findElement(By.id("guessInput"));
        guessInput.sendKeys(String.valueOf(guess));

        // Click on guess button
        WebElement guessButton = driver.findElement(By.id("guessButton"));
        guessButton.click();

        // Click on reset button
        WebElement resetButton = driver.findElement(By.id("resetButton"));
        resetButton.click();

        findAsReseted(driver);

        // Close browser
        driver.quit();
    }

    /**
     * Test a game lose
     */
    @Test
    private void loseGame() {
        driver = EnviromentConfig.getDriver();
        driver.get(EnviromentConfig.getIndex());

        tryLettersToLose(driver);

        // Try to find the state
        WebElement loseState = driver.findElement(By.name("lose"));
        Assert.assertTrue(loseState.isDisplayed(), "[!] Lose State is not visible");

        // Try to find play again button
        WebElement playAgainButton = driver.findElement(By.id("playAgain"));
        Assert.assertTrue(playAgainButton.isDisplayed(), "[!] Play Again Button is not visible");

        // Try to find guess form
        List<WebElement> guessForm = driver.findElements(By.id("guessForm"));
        Assert.assertTrue(guessForm.isEmpty(), "[!] Guess form is visible");

        // Close driver
        driver.close();
    }

    /**
     * Test if play again button is working
     */
    @Test
    private void playAgain() {
        driver = EnviromentConfig.getDriver();
        driver.get(EnviromentConfig.getIndex());

        tryLettersToLose(driver);

        // Try to find play again button
        WebElement playAgainButton = driver.findElement(By.id("playAgain"));
        Assert.assertTrue(playAgainButton.isDisplayed(), "[!] Play Again Button is not visible");

        // Click on play again button
        playAgainButton.click();

        findAsReseted(driver);

        // Close driver
        driver.close();
    }

    /**
     * Put letters that don't have os file.xml to lose the game
     * 
     * @param driver
     */
    private static void tryLettersToLose(WebDriver driver) {
        List<Character> letters = Arrays.asList('x', 'z', 'f', 'j', 'k', 'w');

        for (char letter : letters) {
            // Find the input and send letter
            WebElement guessInput = driver.findElement(By.id("guessInput"));
            guessInput.sendKeys(String.valueOf(letter));

            // Click on guess button
            WebElement guessButton = driver.findElement(By.id("guessButton"));
            guessButton.click();
        }
    }

    /**
     * Find if all elements are reseted
     * @param driver
     */
    private static void findAsReseted(WebDriver driver) {
        WebElement word = driver.findElement(By.id("word"));
        // Passing through all letters trying to find if have a letter
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < word.getText().length(); i++)
            if (word.getText().charAt(i) != '_')
                positions.add(i);

        Assert.assertTrue(positions.isEmpty(), "[!] Word was not reseted");

        // Verify if the wrong guess is correctly put on misses
        List<WebElement> misses = driver.findElements(By.id("misses"));
        Assert.assertTrue(misses.isEmpty(), "[!] Guess was not reseted");

        List<WebElement> stickBoy = driver.findElements(By.className("stick"));
        Assert.assertTrue(stickBoy.isEmpty(), "[!] Sticky boy is enabled");
    }
}