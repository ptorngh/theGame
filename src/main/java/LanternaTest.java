import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


// TODO Bryta upp
// TODO Göra monster till en klass
// TODO Lägga upp spelplanen i egen klass
// TODO Byta ut B och X mot roligare karaktärer
// TODO Flytta reset bomberna till egen metod
// TODO Remove playerIcon after moving

public class LanternaTest {

    static DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
    static Terminal terminal;

    static {
        try {
            terminal = terminalFactory.createTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {


        terminal.setCursorVisible(false);


        // KeyStroke keyStroke = terminal.pollInput();


        int xOldBombsPos;
        int yOldBombsPos;

        // final char monster ='M';
        boolean continueReadingInput = true;
        final char block = '\u2588';


        Position position = new Position(0, 0);
        List<Position> obsticleList = new ArrayList<>();

        // Top frame
        for (int i = 0; i <= 79; i++) {
            terminal.setCursorPosition(i, 0);
            terminal.putCharacter(block);
            obsticleList.add(new Position(i, 0));
            terminal.flush();
        }

        //Bottom frame

        for (int i = 0; i <= 79; i++) {
            terminal.setCursorPosition(i, 23);
            terminal.putCharacter(block);
            obsticleList.add(new Position(i, 23));
            terminal.flush();
        }

        //left Frame

        for (int i = 0; i <= 23; i++) {
            terminal.setCursorPosition(0, i);
            terminal.putCharacter(block);
            obsticleList.add(new Position(0, i));
            terminal.flush();
        }

        //Right frame

        for (int i = 0; i <= 23; i++) {
            terminal.setCursorPosition(79, i);
            terminal.putCharacter(block);
            obsticleList.add(new Position(79, i));
            terminal.flush();
        }


        //Set Bomb/Monster position

        Random r = new Random();
        Monster monster1 = new Monster(r.nextInt(78), r.nextInt(23), '\u046A');

        Monster monster2 = new Monster(r.nextInt(78), r.nextInt(23), '\u046A');

        Monster monster3 = new Monster(r.nextInt(78), r.nextInt(23), '\u046A');

        terminal.setCursorPosition(monster1.x, monster1.y);
        terminal.putCharacter(monster1.monsterIcon);

        terminal.setCursorPosition(monster2.x, monster2.y);
        terminal.putCharacter(monster2.monsterIcon);

        terminal.setCursorPosition(monster3.x, monster3.y);
        terminal.putCharacter(monster3.monsterIcon);

        // create player object
        Player player = new Player(35, 12);


        // set player starting position
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(player.playerIcon);
        terminal.flush();


        do {


            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);// might throw Interrupted  Exception
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);

            Printreverse();

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();// used Character, not char because it might be null


            switch (type) {
                case ArrowUp:
                    player.movePlayerUp();
                    break;

                case ArrowDown:
                    player.movePlayerDown();
                    break;

                case ArrowLeft:
                    player.movePlayerLeft();
                    break;

                case ArrowRight:
                    player.movePlayerRight();
                    break;

                default:
                    break;
            }


            // Check if we hit an obstacle, then we prevent player to pass the obstacle

            boolean crashIntoObsticle = false;

            for (Position p : obsticleList) {
                //System.out.println(" " + p.x + " " + p.y);
                if (p.x == player.x && p.y == player.y) {
                    crashIntoObsticle = true;
                }
            }

            // Move player after checking if it crashes into obstacle
            if (crashIntoObsticle) {

                player.x = player.xOld;
                player.y = player.yOld;
                CleanAndMove(player.x, player.y, player.xOld,player.yOld, player.playerIcon);
            } else {
                CleanAndMove(player.x, player.y, player.xOld,player.yOld, player.playerIcon);
            }

            //Move monster 1

            monster1.xOld = monster1.x;
            monster1.xOld = monster1.y;
            monster1.moveMonster(player);

            CleanAndMove(monster1.x, monster1.y, monster1.xOld, monster1.yOld, monster1.monsterIcon);

            //Move monster 2

            monster2.xOld = monster2.x;
            monster2.xOld = monster2.y;
            monster2.moveMonster(player);

            CleanAndMove(monster2.x, monster2.y, monster2.xOld, monster2.yOld, monster2.monsterIcon);

            //Move monster 3

            monster3.xOld = monster3.x;
            monster3.xOld = monster3.y;
            monster3.moveMonster(player);

            CleanAndMove(monster3.x, monster3.y, monster3.xOld, monster3.yOld, monster3.monsterIcon);


            // logging activities

            System.out.println(" Column:" + player.x + " Row:" + player.y + " " + type);

            // quit with q

            if (c == Character.valueOf('q'))
                continueReadingInput = false;

            // decrease life when we die from monster

            if (monster1.x == player.x && monster1.y == player.y) {
                if (player.numberLife > 1) {
                    player.numberLife--;


                    // Reset av monster 1

                    monster1.x = r.nextInt(78);
                    monster1.y = r.nextInt(23);
                    CleanAndMove(monster1.x, monster1.y, monster1.xOld, monster1.yOld, monster1.monsterIcon);

                    // Reset av monster 2

                    monster2.x = r.nextInt(78);
                    monster2.y = r.nextInt(23);
                    CleanAndMove(monster2.x, monster2.y, monster2.xOld, monster2.yOld, monster2.monsterIcon);

                    // Reset av monster 3

                    monster3.x = r.nextInt(78);
                    monster3.y = r.nextInt(23);
                    CleanAndMove(monster3.x, monster3.y, monster3.xOld, monster3.yOld, monster3.monsterIcon);

                    Print(player.numberLife);
                } else {
                    continueReadingInput = false;
                }
            }
            // decrease life when we die from bomb 2

            if (monster2.x == player.x && monster2.y == player.y) {
                if (player.numberLife > 1) {
                    player.numberLife--;

                    // Reset av monster 1

                    monster1.x = r.nextInt(78);
                    monster1.y = r.nextInt(23);
                    CleanAndMove(monster1.x, monster1.y, monster1.xOld, monster1.yOld, monster1.monsterIcon);

                    // Reset av monster 2

                    monster2.x = r.nextInt(78);
                    monster2.y = r.nextInt(23);
                    CleanAndMove(monster2.x, monster2.y, monster2.xOld, monster2.yOld, monster2.monsterIcon);

                    // Reset av monster 3

                    monster3.x = r.nextInt(78);
                    monster3.y = r.nextInt(23);
                    CleanAndMove(monster3.x, monster3.y, monster3.xOld, monster3.yOld, monster3.monsterIcon);

                    Print(player.numberLife);

                } else {
                    continueReadingInput = false;
                }
            }

            // decrease life when we die from bomb 3
            if (monster3.x == player.x && monster3.y == player.y) {
                if (player.numberLife > 1) {
                    player.numberLife--;

                    // Reset av monster 1

                    monster1.x = r.nextInt(78);
                    monster1.y = r.nextInt(23);
                    CleanAndMove(monster1.x, monster1.y, monster1.xOld, monster1.yOld, monster1.monsterIcon);

                    // Reset av monster 2

                    monster2.x = r.nextInt(78);
                    monster2.y = r.nextInt(23);
                    CleanAndMove(monster2.x, monster2.y, monster2.xOld, monster2.yOld, monster2.monsterIcon);

                    // Reset av monster 3

                    monster3.x = r.nextInt(78);
                    monster3.y = r.nextInt(23);
                    CleanAndMove(monster3.x, monster3.y, monster3.xOld, monster3.yOld, monster3.monsterIcon);

                    Print(player.numberLife);

                } else {
                    continueReadingInput = false;
                }
            }


        } while (continueReadingInput);

        terminal.close();

    }

    public static void Print(int life) throws IOException {
        String lanternaPrint = "You lost a life - only " + life + " remaining";
        int length = lanternaPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 30, 4);

            terminal.putCharacter(lanternaPrint.charAt(column - 1));
            terminal.flush();
        }
    }

    public static void Printreverse() throws IOException {
        String lanternaPrint = "                                  ";
        int length = lanternaPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 30, 4);

            terminal.putCharacter(lanternaPrint.charAt(column - 1));
            terminal.flush();
        }
    }

    public static void CleanAndMove(int x, int y, int xOld, int yOld, char icon) throws IOException {

        // Clean out old position icon, and draw icon on new position
        terminal.setCursorPosition(xOld, yOld);
        terminal.putCharacter(' ');
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(icon);
        terminal.flush();
    }

}



/*
 String lanternaPrint = "This is a String printed out in Lanterna by iterating over the characters";
        int length = lanternaPrint.length();

        for(int column = 1; column < length; column++) {
            terminal.setCursorPosition(column, 4);

            terminal.putCharacter(lanternaPrint.charAt(column-1));
            terminal.flush();
        }
 */