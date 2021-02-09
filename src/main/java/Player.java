public class Player {
    int x;
    int y;
    int xOld;
    int yOld;
    final char playerIcon ='X';
    int numberLife;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.xOld = x;
        this.yOld = y;
        this.numberLife = 3;
    }

    public void movePlayerUp() {
        y-=1;
    }

    public void movePlayerDown() {
        y+=1;
    }

    public void movePlayerLeft() {
        x-=1;
    }

    public void movePlayerRight() {
        x+=1;
    }

}
