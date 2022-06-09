/*
Utils class - common utilities

Author - Marc Shepard
*/

import java.util.Scanner;

// Input class - consists of only static methods
public class Utils {
  // A scanner to support console input
  private static Scanner scan = new Scanner(System.in);

  // Return a string reply to a question
  public static String inputStr (String question) {
    System.out.print (question);
    return scan.nextLine();
  } 

  // Return a whole number reply to a question
  public static int inputNum (String question) {
    System.out.print (question);
    int num = -1;
    while (num <= 0) {
      try {
        String numStr = scan.nextLine();
        num = Integer.parseInt(numStr);
      } catch (Exception e) {}
      if (num <= 0)
        System.out.println ("Please enter a positive integer: ");
    }
    return num;
  }

  // Return a random number between min and max
  public static int randInt (int min, int max) {
    return min + (int)(Math.random() * (max-min+1));
  }

  // Returns a item from an array of Strings
  public static String randChoice (String[] strings) {
    return strings[randInt(0, strings.length - 1)];
  }
}