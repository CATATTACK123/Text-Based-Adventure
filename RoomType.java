public class RoomType {
  private String type;
  private int health;
  private int maxHP;
  private int atk;
  private int def;
  private int lvl;

  public RoomType(String t, int l) {
    this.type = t;
    this.lvl = l;
    if(this.type.equals("Skeleton")) {
      this.maxHP = 3 * this.lvl;
      this.health = 3 * this.lvl;
      this.atk = 2 + (int)(0.5 * this.lvl);
      this.def = 0 + (int)(0.5 * this.lvl);
    } else if(this.type.equals("Zombie")) {
      this.maxHP = 5 * this.lvl;
      this.health = 5 * this.lvl;
      this.atk = 1 + (int)(0.8 * this.lvl);
      this.def = 1 + (int)(0.8 * this.lvl);
    } else if(this.type.equals("Boss")) {
      this.maxHP = 10 * this.lvl;
      this.health = 10 * this.lvl;
      this.atk = 3 + (int)(2 * this.lvl);
      this.def = 2 + (int)(2 * this.lvl);
    } else if(this.type.equals("Treasure")) {
      this.maxHP = 1;
      this.health = 1;
      this.atk = 0;
      this.def = 0;
    }
  }

  public String getType() {
    return type;
  }
  
  public int getHP() {
    if(health < 0) {
      health = 0;
    }
    return health;
  }

  public void minusHP(int h) {
    this.health -= h;
  }

  public int getMaxHP( ){
    return health;
  }
  
  public int getDef() {
    return def;
  }

  public int getAtk() {
    return atk;
  }

  public int getLvl() {
    return lvl;
  }

  public boolean getAlive() {
    return health > 0;
  }
}