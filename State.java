import java.util.HashMap;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class State implements Serializable {
  public String name;
  public HashMap<String, Integer> pastScores;

  public void printScores () {
    System.out.println("Here are all the past scores: ");
    for(String i : pastScores.keySet()) {
      System.out.println("Name: " + i + "     Score: " + pastScores.get(i));
    }
  }
  
  public boolean save () {
    if (name == null)
      return false;

    String fileName = name + "State.ser";
    
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(this);
      oos.close();
      fos.close();
      return true;
    } catch (IOException e) {
      System.err.println(e);
      return false;
    }
  }
  
  public static State restore (String name) {
    String fileName = name + "State.ser";
    
    try {
		  FileInputStream fis = new FileInputStream(fileName);
      ObjectInputStream ois = new ObjectInputStream(fis);
      State s = (State) ois.readObject();
	    ois.close();
	    fis.close();
      return s;
	  } catch(Exception e) {
	    return null;
	  }
  }  
}