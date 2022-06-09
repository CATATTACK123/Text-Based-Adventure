public class Player {
  private String name;
  private int health;
  private int maxHP;
  private int atk;
  private int def;
  private int lvl;

  public Player(String n, int mHP, int a, int d){
    name = n;
    maxHP = mHP;
    health = mHP;
    atk = a;
    def = d;
    lvl = 1;
  }

  public String getName(){
    return name;
  }

  public int getHP(){
    return health;
  }
  
  public void setHP(int h){
    health = h;
  }

  public int changeHP(int h){
    int he = h;
    health += h;
    if(health > maxHP){
      he = health - maxHP;
      health = maxHP;
    }
    return he;
  }

  public int attacked(int a){
    int d = a - def;
    if(d <= 0){
      d = 1;
    }
    health -= d;
    if(health < 0){
      health = 0;
    }
    return health;
  }

  public int getMaxHP(){
    return maxHP;
  }
  
  public void plusMaxHP(int maxH){
    maxHP += maxH;
    health += maxH;
  }
  
  public int getDef(){
    return def;
  }
  
  public void plusDef(int d){
    def += d;
  }

  public int getAtk(){
    return atk;
  }
  
  public void plusAtk(int a){
    atk += a;
  }

  public int getLvl(){
    return lvl;
  }
  
  public void plusLvl(int l){
    lvl += l;
  }

  public boolean aliveOrDead(){
    return health > 0;
  }
}