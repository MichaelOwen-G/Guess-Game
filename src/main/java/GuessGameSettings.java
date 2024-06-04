import java.util.Scanner;

public class GuessGameSettings {
  private int maxAttempts = 3;
  private int lowerBound = 0;
  private int upperBound = 100;

  // get variables
  // maxAttempts
  public int getMaxAttempts() {
    return maxAttempts;
  }

  // lowerBound
  public int getLowerBound() {
    return lowerBound;
  }

  // upperBound
  public int getUpperBound() {
    return upperBound;
  }

  
  // configure settings
  public void configure(Scanner scanner) {
    System.out.println();

    // display settings message
    System.out.println("CONFIGURE " + "GAME SETTINGS");

    // clear buffer
    scanner.nextLine();

    // ask user for max attempts and set new max attempts
    maxAttempts = _promptForSetting(scanner, "max attempts", maxAttempts);

    // ask user for lower bound and update setting
    lowerBound = _promptForSetting(scanner, "lower bound", lowerBound);

    // ask user for upper bound and update
    upperBound = _promptForSetting(scanner, "upper bound", upperBound);

    System.out.println("Successfully configured game settings ");
  }

  public void showSettings(Scanner scanner) {
    // display current settings
    System.out.println(" ___________________");
    System.out.println("|");
    System.out.println("| GAME SETTINGS     ");
    System.out.println("|");
    System.out.println("|  Max Attempts: " + maxAttempts);
    System.out.println("|  Lower Bound:  " + lowerBound);
    System.out.println("|  Upper Bound:  " + upperBound);
    System.out.println("|___________________");

    System.out.println();

    // ask user if they want to change settings
    System.out.print("Would you like to change the settings? (y/n): ");

    // get input
    String input = scanner.nextLine().toString();

    // if user input is y, call configure method
    if (input.toLowerCase().equals("y")) {
      configure(scanner);
    }
  }

  private int _promptForSetting(Scanner scanner, String setting, int settingValue) {
    // display setting information
    System.out.println(":: " + setting.toUpperCase() + ": " + settingValue);
    System.out.println("   Press Enter to Choose Default");
    System.out.print("   Enter new " + setting.toLowerCase() + ": ");

    // init newValue
    int newValue = settingValue;

    // get input
    String input = scanner.nextLine();

    // try parsing the input to integer
    try {
      newValue = Integer.parseInt(input.trim());

      System.out.println("   Updated to " + newValue);

    }
    // if input was not an integer
    catch (NumberFormatException e) {

      // if enter was pressed return the old setting value
      if (input.equals("")) {
        System.out.println("   Defaulted");
      }
      // if not, display error, retry the process
      else {
        // dislay error
        System.out.println("   Invalid value entered, try again.");

        // recall method
        askForSetting(scanner, setting, settingValue);
      }
    }

    return newValue;
  }


}
