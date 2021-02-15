import java.util.List;

public class Monster {

    int x;
    int y;
    int xOld;
    int yOld;
    char monsterIcon;

    public Monster(int x, int y, char monsterIcon) {
        this.x = x;
        this.y = y;
        this.xOld = x;
        this.yOld = y;
        this.monsterIcon = monsterIcon;
    }

    public void moveMonster(Player player, List<Position> obsticleList) {

        // Saves monster position in order to be able to clean out screen later
        xOld = x;
        yOld = y;

        if (player.x < x) {
            x--;
            for (Position p : obsticleList) {
                if (p.x == x && p.y == y) {
                   x++;
                   y=+2;
                }
            }

        } else if (player.x > x) {
            x++;
            for (Position p : obsticleList) {
                if (p.x == x && p.y == y) {
                    x--;
                    y=+2;
                }
            }
        }

        if (player.y < y) {
            y--;
            for (Position p : obsticleList) {
                if (p.x == x && p.y == y) {
                    y++;
                    x=+2;
                }
            }
        } else if (player.y > y) {
            y++;
            for (Position p : obsticleList) {
                if (p.x == x && p.y == y) {
                    y--;
                    x=+2;
                }
            }
        }


    }
}
