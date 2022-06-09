/*
Game class - test based adventure game

In this game, the player will create their character
then, they will go on a short quest in a dungeon,
eventually getting one of a few endings

State info:
  Will need character name, age, and race, all
  instance variables
  Also need character abilities, local variables
  Finally variables for choices, also local
  Need hashmaps for rooms/items/other map, will be instance variables

Method info:
  Need constructor, create player, hashmaps for rooms and stuff, etc.
  Play method, where game is played using 
  

Class info:
  Game class, where game starts and runs, calls Player and RoomType classes at various locations
  Player class, where player creates their character and info about them is stored
  RoomType class, where enemies/treasure are created
  Combat class, details how combat goes SCRAPPED, COMBAT IN GAME CLASS
  Wait class, can be used to wait for a bit before printing stuff to console, used for pauses/pacing SCRAPPED, SIMPLY A METHOD IN GAME
  Gameover class, if player has <= 0 hp, game ends SCRAPPED, SIMPLY A METHOD IN GAME
  Room class, where rooms are created and used SCRAPPED, DONE IN GAME CLASS

Author - Kenny Sorrell

Change history:
5/12: Created
5/14: Design established, class comments written
5/19: Implementation: code in Game started, Player and RoomType created
5/20: Implementation: code draft in Game, Player, and RoomType written
5/21: Implementation: added feature, going to cleared room has a cleared room, enemy/treasure is not respawned, code draft in Game, Player, and RoomType complete
5/21: Bug fixes, made inputs non-case sensitive (lowercase and uppercase works), added default cases to combat and chest opening

Future plans:
1. Fix bugs with wrong input, make it so it repeats command, not do weird stuff, also fix any other encountered bugs
2. Balance game better (combat is atrocious)
3. Make Hype yourself up (an option during combat) actually do something besides print encouragement
4. Make a new run-away option during combat, other options too
5. Adding more room and enemy variety
6. Implementation of level up/get stronger system
7. Add sizing option for dungeon
8. Add map displaying dungeon using String[][] and symbols (- for unexplored, x for cleared monster, t for cleared treasure)
9. Edit/add wait calls in code to make console more legible while playing
10. improve code quality, this class (Game.java) is a big mess rn
11. Add weapons/armor/consumables
12. Add play again option
13. Make scoreboard displaying high scores
14. Add more character customization in beginning
15. Make game fun lol
*/

import java.util.HashMap;
import java.util.Scanner;

public class Game {

  private HashMap<String, Integer> pastScores = new HashMap<String, Integer>();
  private String[][] dungeon;
  private boolean[][] clearedMap;
  private int[] currentRoom;
  private boolean bossAlive;
  private int score;
  Player player;
  Scanner scan = new Scanner(System.in);
  
  public Game() {
    System.out.println("Welcome to the dungeon!");
    boolean keepGoing = true;
    while(keepGoing) {
      String command = Utils.inputStr("\nWhat would you like to do: show past scores(S), play game (P), quit(Q)? ");
      switch (command) {
  
        case "S", "s":
          showPastScores();
          break;
  
        case "P", "p":
          initializeGame();
          break;
  
        case "Q", "q":
          keepGoing = false;
          break;
          
      }
    }
  }

  public void showPastScores() {
    System.out.println("\nHere are all past scores: ");
    for(String i : pastScores.keySet()) {
      System.out.println("Name: " + i + "     Score: " + pastScores.get(i));
    }
  }

  public void initializeGame() {
    dungeon = new String[5][5];
    clearedMap = new boolean[5][5];
    currentRoom = new int[2];
    bossAlive = true;
    score = 0;
    play();
  }

  public void play() {
    System.out.println ("Welcome to the Dungeon, first, you will need to make your adventurer.");
    createCharacter();

    System.out.println("\nCreating Dungeon...");
    createDungeon();
    System.out.println("Dungeon created");
    wait(1);
    
    System.out.println ("\nNow, let's enter the dungeon!");
    initialRoom();
    
    moving();
    
  }

  public void initialRoom() {
    currentRoom[0] = 2;
    currentRoom[1] = 2;
    wait(2);
    System.out.println("\nYou awake with a start and sit up, the floor is cold, the ceiling is high, and the walls are bare, you remark that there are four gaping holes leading to other rooms, one on each of the four dull, gray walls.");
    wait(6);
    System.out.println("\nYou remember your name, " + player.getName() + ".");
    wait(2);
    System.out.println("\nYou remember nothing else...");
    wait(3);
    System.out.println("\nYou stand up, there is nothing in this room, perhaps you should enter a different one?");
    clearedMap[currentRoom[0]][currentRoom[1]] = true;
  }

  public void moving() {
    System.out.println ("");
    while (bossAlive && player.aliveOrDead()) {
      wait(1);
      String command = Utils.inputStr("\nWhere do you want to go: North (N), South (S), East (E), or West (W)? ");
      switch (command) {
          
        case "N", "n":
          if(currentRoom[0] >= 1) {
            System.out.println("You enter the room to the North");
            currentRoom[0] -= 1;
          } else {
            System.out.println("How odd, you went through the door to the North, but you ended up further to the South?");
            currentRoom[0] = 4;
          }
          wait(1);
          System.out.println("Your location is now x: " + (currentRoom[1] + 1) + " y: " + (currentRoom[0] + 1));
          wait(1);
          exploreRoom();
          break;
          
        case "S", "s":
          if(currentRoom[0] <= 3) {
            System.out.println("You enter the room to the South");
            currentRoom[0] += 1;
          } else {
            System.out.println("Your location is now x: " + (currentRoom[1] + 1) + " y: " + (currentRoom[0] + 1));
            currentRoom[0] = 0;
          }
          wait(1);
          System.out.println("Your location is now x: " + (currentRoom[1] + 1) + " y: " + (currentRoom[0] + 1));
          wait(1);
          exploreRoom();
          break;
          
        case "E", "e":
          if(currentRoom[1] <= 3) {
            System.out.println("You enter the room to the East");
            currentRoom[1] += 1;
          } else {
            System.out.println("How odd, you went through the door to the East, but you ended up further to the West?");
            currentRoom[1] = 0;
          }
          wait(1);
          System.out.println("Your location is now x: " + (currentRoom[1] + 1) + " y: " + (currentRoom[0] + 1));
          wait(1);
          exploreRoom();
          break;
          
        case "W", "w":
          if(currentRoom[1] >= 1) {
            System.out.println("You enter the room to the West");
            currentRoom[1] -= 1;
          } else {
            System.out.println("How odd, you went through the door to the West, but you ended up further to the East?");
            currentRoom[1] = 4;
          }
          wait(1);
          System.out.println("Your location is now x: " + (currentRoom[1] + 1) + " y: " + (currentRoom[0] + 1));
          wait(1);
          exploreRoom();
          break;
      }
    }
    System.out.println("YAY! You beat the boss! Good job!");
  }

  public void exploreRoom() {
    RoomType roomType = new RoomType(dungeon[currentRoom[0]][currentRoom[1]], 1);
    String type = roomType.getType();
    if(!clearedMap[currentRoom[0]][currentRoom[1]]) {
      if(type.equals("Treasure")) {
        System.out.println("\n\nThe room is smaller than the others, but unremarkable overall, save for a small chest on display in the middle of the room.");
        wait(1);
        String command = Utils.inputStr("\nOpen the chest (O), break the chest (B), or leave (L). ");
        switch(command) {
          case "O", "o":
            int trap = (int)(Math.random() * 10);
            int gold = (int)(Math.random() * 26 + 15);
            score += gold;
            clearedMap[currentRoom[0]][currentRoom[1]] = true;
            if(trap >= 7) {
              player.attacked(1);
              System.out.println("You open the chest, gaining " + gold + " gold and bringing your score to " + score + ", but the chest was trapped! You lose 1 health points, bringing your remaining health points down to " + player.getHP());
              if(!player.aliveOrDead()){
                loss("Treasure");
              }
            } else {
              System.out.println("You open the chest without incident, gaining " + gold + " gold and bringing your score to " + score + ".");
            }
            break;
            
          case "B", "b":
            gold = (int)(Math.random() * 16 + 10);
            score += gold;
            clearedMap[currentRoom[0]][currentRoom[1]] = true;
            System.out.println("You break open the chest, gaining " + gold + " gold and bringing your score to " + score + ".");
            roomType.minusHP(1);
            break;
            
          case "L", "l":
            System.out.println("You leave, perhaps it's for the best.");
            break;

          default:
              System.out.println("You do nothing and leave, but through the wrong input, how rebellious.");
        }
      } else if(type.equals("Skeleton") || type.equals("Zombie")) {
        System.out.println("\n\nThe room is unremarkable save for a " + type + " standing motionless in the middle, that is, until you entered the room.");
        wait(1);
        while(!clearedMap[currentRoom[0]][currentRoom[1]]) {
          String command = Utils.inputStr("\nAttack the monster (A), Sleep (S), Hype yourself up (H) ");
          switch(command) {
            case "A", "a":
              int damage = player.getAtk() - roomType.getDef();
              if(damage < 1) {
                damage = 1;
              }
              roomType.minusHP(damage);
              System.out.println("You attack the " + type + ", hitting it with all your might and doing " + damage + " damage and leaving the monster with " + roomType.getHP() + " health points.");
              break;
              
            case "S", "s":
              int heal = 2;
              if(player.getHP() < player.getMaxHP() - heal) {
                player.changeHP(heal);
              } else if(player.getHP() < player.getMaxHP()) {
                heal = player.getMaxHP() - player.getHP();
                player.changeHP(heal);
              } else {
                heal = 0;
              }
              System.out.println("You take a quick nap, recovering " + heal + " health points.");
              break;
              
            case "H", "h":
              System.out.println("YOU GOT THIS. YEAH. YOU GO. YOU ARE THE ONE AND ONLY " + player.getName().toUpperCase() + ". WOOOOOOOOO.");
              break;

            default:
              System.out.println("You do nothing.");
          }

          if(roomType.getAlive()) {
            wait(1);
            int monsterDamage = roomType.getAtk() - player.getDef();
            if(monsterDamage <= 0) {
              monsterDamage = 1;
            }
            player.attacked(monsterDamage);
            System.out.println("The " + type + " attacks, hitting you for " + monsterDamage + " damage and leaving you with " + player.getHP() + " health points.");
            if(!player.aliveOrDead()) {
              clearedMap[currentRoom[0]][currentRoom[1]] = true;
              loss(type);
            }
          } else {
            clearedMap[currentRoom[0]][currentRoom[1]] = true;
            clearRoom(type);
          }
        }
      } else {
        System.out.println("\n\nYou enter a dark chamber, every surface is scorched black, as if it had been cooked like a Costco rotisserie chicken. You see a massive figure in the center, oh no! It's the big boss!");
        wait(1);
        while(!clearedMap[currentRoom[0]][currentRoom[1]]) {
          String command = Utils.inputStr("\nAttack the monster (A), Sleep (S), or Hype yourself up (H) ");
          switch(command) {
            case "A", "a":
              int damage = player.getAtk() - roomType.getDef();
              if(damage < 1) {
                damage = 1;
              }
              roomType.minusHP(damage);
              System.out.println("You attack the " + type + ", hitting it with all your might and doing " + damage + " damage and leaving the monster with " + roomType.getHP() + " health points.");
              break;
              
            case "S", "s":
              int heal = 2;
              if(player.getHP() < player.getMaxHP() - heal) {
                player.changeHP(heal);
              } else if(player.getHP() < player.getMaxHP()) {
                heal = player.getMaxHP() - player.getHP();
                player.changeHP(heal);
              } else {
                heal = 0;
              }
              System.out.println("You take a quick nap, recovering " + heal + " health points.");
              break;
              
            case "H", "h":
              System.out.println("YOU GOT THIS. YEAH. YOU GO. YOU ARE THE ONE AND ONLY " + player.getName().toUpperCase() + ". WOOOOOOOOO.");
              break;

            default:
              System.out.println("You do nothing.");
          }

          if(roomType.getAlive()) {
            wait(1);
            int monsterDamage = roomType.getAtk() - player.getDef();
            if(monsterDamage <= 0) {
              monsterDamage = 1;
            }
            player.attacked(monsterDamage);
            roomType.minusHP(-1);
            System.out.println("The " + type + " attacks, hitting you for " + monsterDamage + " damage and leaving you with " + player.getHP() + " health points. Oh no! It also healed 1 hp!");
            if(!player.aliveOrDead()) {
              clearedMap[currentRoom[0]][currentRoom[1]] = true;
              loss(type);
            }
          } else {
            clearedMap[currentRoom[0]][currentRoom[1]] = true;
            victory();
          }
        }
      }
    } else {
      System.out.println("\n\nThis room is already cleared, move along.");
    }
  }

  //for when clear nonboss monster room
  public void clearRoom(String type) {
    int gold = (int)(Math.random() * 11 + 5);
    score += gold;
    System.out.println("The " + type + " dies, leaving behind " + gold + " gold coins and bringing your score to " + score + "!");
  }

  //for when kill boss
  public void victory() {
    int gold = (int)(Math.random() * 101 + 100);
    score += gold;
    bossAlive = false;
    System.out.println("You win! Good job! You received " + gold + " gold coins for defeating the boss, bringing your score to " + score + "!");
    pastScores.put(player.getName(), score);
  }
  
  //for when player dies
  public void loss(String type) {
    System.out.println("Dang, looks like you lost to that pesky " + type + ", your score was " + score + ".");
    pastScores.put(player.getName(), score);
  }
  
  public void printStats() {
    System.out.println("\nYour adventurer's stats are: ");
    System.out.println("Lvl: " + player.getLvl());
    System.out.println("HP: " + player.getHP());
    System.out.println("MaxHP: " + player.getMaxHP());
    System.out.println("Atk: " + player.getAtk());
    System.out.println("Def: " + player.getDef());
  }
  
  public void createCharacter() {
    String name;
    int maxHP = 1;
    int atk = 1;
    int def = 1;
    boolean valid = false;
    System.out.println("What is your adventurer's name?");
    name = scan.nextLine();
    while(!valid) {
      System.out.println("What's their build, tank or attacker?");
      String role = scan.nextLine().toLowerCase();
      if(role.equals("tank")) {
        maxHP = 25;
        atk = 8;
        def = 4;
        valid = true;
      } else if(role.equals("attacker")) {
        maxHP = 20;
        atk = 12;
        def = 2;
        valid = true;
      } else {
        System.out.println("Invalid role");
      }
    }
    player = new Player(name, maxHP, atk, def);
    System.out.println("Adventurer created!");
    printStats();
  }

  public void createDungeon() {
    String roomType;
    for(int i = 0; i < 5; i++) {
      for(int j = 0; j < 5; j++) {
        int roomMaker = (int)(Math.random() * 25);
        if(roomMaker < 10) {
          roomType = "Skeleton";
        } else if(roomMaker < 20) {
          roomType = "Zombie";
        } else if(roomMaker < 25) {
          roomType = "Treasure";
        } else {
          roomType = "Treasure";
        }
        dungeon[i][j] = roomType;
        clearedMap[i][j] = false;
      }
    }
    int bossRoomY = (int)(Math.random() * 5);
    int bossRoomX = (int)(Math.random() * 5);
    if(bossRoomY == 2 && bossRoomX == 2) {
      bossRoomY = 0;
      bossRoomX = 0;
    }
    dungeon[bossRoomY][bossRoomX] = "Boss";
  }
  
  //prevent interrupted exception when using Thread.sleep()
  //also easier to write wait() every time instead
  public void wait(int numSeconds) {
    try {
        Thread.sleep(numSeconds * 1000);
    } catch (InterruptedException e) {
    }
  }
}