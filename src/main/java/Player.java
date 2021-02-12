public class Player {
    int x;
    int y;
    int xOld;
    int yOld;
    final char playerIcon ='O';
    int numberLife;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.xOld = x;
        this.yOld = y;
        this.numberLife = 3;
    }

    public void movePlayerUp() {
        yOld = y;
        xOld = x;
        y-=2;
    }

    public void movePlayerDown() {
        yOld = y;
        xOld = x;
        y+=2;
    }

    public void movePlayerLeft() {
        yOld = y;
        xOld = x;
        x-=2;
    }

    public void movePlayerRight() {
        yOld = y;
        xOld = x;
        x+=2;
    }

}
