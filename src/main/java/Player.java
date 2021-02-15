import java.util.List;

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

    public boolean movePlayerUp(List<Position> obsticleList) {
        yOld = y;
        xOld = x;
        y-=2;
        for (Position p : obsticleList) {
            if (p.x == x && p.y == y) {
                return true;
            } else if (p.x == x && p.y == y + 1) {
                return true;
            }
        }
        return false;
    }

    public boolean movePlayerDown(List<Position> obsticleList) {
        yOld = y;
        xOld = x;
        y+=2;
        for (Position p : obsticleList) {
            if (p.x == x && p.y == y) {
                return true;
            } else if (p.x == x && p.y == y - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean movePlayerLeft(List<Position> obsticleList) {
        yOld = y;
        xOld = x;
        x-=2;
        for (Position p : obsticleList) {
            if (p.x == x && p.y == y) {
                return true;
            } else if (p.x == x+1 && p.y == y) {
                return true;
            }
        }
        return false;
    }

    public boolean movePlayerRight(List<Position> obsticleList) {
        yOld = y;
        xOld = x;
        x+=2;
        for (Position p : obsticleList) {
            if (p.x == x && p.y == y) {
                return true;
            } else if (p.x == x-1 && p.y == y) {
                return true;
            }
        }
        return false;
    }
}
