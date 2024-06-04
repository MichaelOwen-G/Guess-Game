import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GuessGame {
  // nature of the guessed number
  enum GuessedNumber {
    LOWER,
    HIGHER,
    CORRECT
  }

  // create default game settings
  GuessGameSettings gameSettings = new GuessGameSettings();

  // sleep durations
  // for animated print outs
  private int sleepLong = 1500;
  private int sleepMedium = 500;
  private int sleepShort = 100;

  // launch the game
  public void launch() {
    // display welcome message
    System.out.println("======================");
    System.out.println("GUESS THE NUMBER GAME");
    System.out.println("======================");
    System.out.println();

    // create scanner
    Scanner scanner = new Scanner(System.in);

    // show game settings to the console
    gameSettings.showSettings(scanner);

    System.out.println();

    // run the game loop
    runGameLoop(scanner);

    // dispose scanner
    scanner.close();
  }

  /* 
  Create a game loop to provide multiple instances of the game
   so the player can retry after winning or failing
   */
  private void runGameLoop(Scanner scanner) {
    // game loop
    while (true) {
      System.out.println();

      sleep(sleepMedium);

      // prompt user to start game
      System.out.println("Press Enter to start game: ");

      // if user presses enter, start the game
      if (scanner.nextLine().equals("")) {
        System.out.println();

        // start instance of the guessing game
        gameInstance(scanner);
      }
      // if they press anything else, quit game
      else {
        break;
      }
    }
  }

  /*
  Handles the game logic
  */
  private void gameInstance(Scanner scanner) {
    // generate random number with game settings bounds
    int randomNumber = getRandomNumber(gameSettings.getUpperBound(), gameSettings.getLowerBound());

    // display random number message
    animatePrint(
        "I'm thinking of a number between " + gameSettings.getLowerBound() + " and " + gameSettings.getUpperBound(),
        sleepLong);

    // to keep count of guesses
    int trials = 0;

    // handle the guessing process
    handleGuesses(scanner, randomNumber, trials);
  }

  // generate random number
  private int getRandomNumber(int upperBound, int lowerBound) {
    return (int) (Math.random() * (upperBound - (lowerBound + 1)));
  }

  
  /* 
   Handles the guessing trials.
   The player can guess for the number of tries permitted
  */
  private void handleGuesses(Scanner scanner, int randomNumber, int trials) {
    System.out.println();

    // display remaining trials current one included
    animatePrint(gameSettings.getMaxAttempts() - trials + " Trials Remaining", sleepMedium);

    // increment trials
    trials++;

    // if the user has maxed out the number of attempts, display game over message
    // and exit the game
    if (trials > gameSettings.getMaxAttempts()) {
      displayGameOverMessage(randomNumber);
      return;
    }

    // prompt user to guess
    System.out.print("GUESS THE NUMBER: ");

    // get guess number entered
    int guess = scanner.nextInt();

    // get the nature of the guessed number
    GuessedNumber guessedNumber = natureOf(guess, randomNumber);

    // handle the nature of the guessed message
    switch (guessedNumber) {
      // if the guess was correct
      case CORRECT:
        // display win
        System.out.println("CORRECT! YOU WIN!");
        break;
      // if the guessed number was higher than the random number
      case HIGHER:
        // tell the user to guess lower
        animatePrint("LOOOWEEERRR!...", sleepLong);
        // let the user guess again
        handleGuesses(scanner, randomNumber, trials);
        break;
      // if the guessed number was lower than the random number
      case LOWER:
        // tell the user to guess higher
        animatePrint("HIIIGHHHHEEER!...", sleepLong);
        // let the user guess again
        handleGuesses(scanner, randomNumber, trials);
    }

  }

  // return the nature of the guessed number
  private GuessedNumber natureOf(int guess, int randomNumber) {
    if (guess == randomNumber) {
      return GuessedNumber.CORRECT;
    } else if (guess > randomNumber) {
      return GuessedNumber.HIGHER;
    } else {
      return GuessedNumber.LOWER;
    }
  }

  private void displayGameOverMessage(int correctNumber) {
    animatePrint("WAAASSTTTEEEDD !!!!", sleepLong);
    sleep(sleepMedium);
    animatePrint("The number was " + correctNumber, sleepLong);
    System.out.println();
    sleep(sleepShort);
    System.out.println("GAME OVER");
    System.out.println("=======================");
  }

  private void sleep(long milliseconds) {
    try {
      TimeUnit.MILLISECONDS.sleep(milliseconds);
    } catch (InterruptedException e) {
      System.out.println(e);
    }
  }

  private void animatePrint(String word, int animationDuration) {
    long sleepTime = (long) animationDuration / word.length();

    // loop over each letter and sleep between
    for (int i = 0; i < word.length(); i++) {
      System.out.print(word.charAt(i));
      sleep(sleepTime);
    }
    System.out.print("\n");
  }
}
