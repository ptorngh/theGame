import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//TODO jvöjgaeö

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


        Position position = new Position(0,0);
        List<Position> obsticleList = new ArrayList<>();

        // Top frame
        for(int i = 0; i <= 79; i++) {
            terminal.setCursorPosition(i, 0);
            terminal.putCharacter(block);
            obsticleList.add(new Position(i,0));
            terminal.flush();
        }

        //Bottom frame

        for(int i = 0; i <= 79; i++) {
            terminal.setCursorPosition(i, 23);
            terminal.putCharacter(block);
            obsticleList.add(new Position(i,23));
            terminal.flush();
        }

        //left Frame

        for(int i = 0; i <= 23; i++) {
            terminal.setCursorPosition(0, i);
            terminal.putCharacter(block);
            obsticleList.add(new Position(0,i));
            terminal.flush();
        }

        //Right frame

        for(int i = 0; i <= 23; i++) {
            terminal.setCursorPosition(79, i);
            terminal.putCharacter(block);
            obsticleList.add(new Position(79, i));
            terminal.flush();
        }

        //Set Bomb/Monster position

        Random r = new Random();
        Position bombPosition = new Position(r.nextInt(78), r.nextInt(23));

        Position bombPosition2 = new Position(r.nextInt(78), r.nextInt(23));

        Position bombPosition3 = new Position(r.nextInt(78), r.nextInt(23));

        terminal.setCursorPosition(bombPosition.x, bombPosition.y);
        terminal.putCharacter('B');

        terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
        terminal.putCharacter('B');

        terminal.setCursorPosition(bombPosition3.x, bombPosition2.y);
        terminal.putCharacter('B');

        // create player object
        Player player = new Player(35,12);


        // set player starting position
        terminal.setCursorPosition(player.x, player.y);
        terminal.putCharacter(player.playerIcon);
        terminal.flush();






        do  {


            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);// might throw Interrupted  Exception
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);

            Printreverse();

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();// used Character, not char because it might be null



            switch(type) {
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

            for (Position p: obsticleList) {
                //System.out.println(" " + p.x + " " + p.y);
                if (p.x == player.x && p.y == player.y) {
                    crashIntoObsticle = true;
                }
            }

            if (crashIntoObsticle) {
                player.x = player.xOld;
                player.y = player.yOld;
                terminal.setCursorPosition(player.x, player.y);
                terminal.putCharacter(player.playerIcon);
                terminal.flush();
            }

            else {
                terminal.setCursorPosition(player.xOld, player.yOld);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(player.x, player.y);
                terminal.putCharacter(player.playerIcon);

                terminal.flush();
            }

            //Move bomb

            xOldBombsPos = bombPosition.x;
            yOldBombsPos = bombPosition.y;

            if (player.x < bombPosition.x) {
                bombPosition.x--;
            }
            else if (player.x > bombPosition.x){
                bombPosition.x++;
            }

            if (player.y < bombPosition.y) {
                bombPosition.y--;
            }
            else if (player.y > bombPosition.y) {
                bombPosition.y++;
            }
            terminal.setCursorPosition(xOldBombsPos, yOldBombsPos);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(bombPosition.x, bombPosition.y);
            terminal.putCharacter('B');

            terminal.flush();

            //Move bomb 2

            xOldBombsPos = bombPosition2.x;
            yOldBombsPos = bombPosition2.y;

            if (player.x < bombPosition2.x) {
                bombPosition2.x--;
            }
            else if (player.x > bombPosition2.x){
                bombPosition2.x++;
            }

            if (player.y < bombPosition2.y) {
                bombPosition2.y--;
            }
            else if (player.y > bombPosition2.y) {
                bombPosition2.y++;
            }
            terminal.setCursorPosition(xOldBombsPos, yOldBombsPos);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
            terminal.putCharacter('B');

            terminal.flush();


            //Move bomb 3

            xOldBombsPos = bombPosition3.x;
            yOldBombsPos = bombPosition3.y;

            if (player.x < bombPosition3.x) {
                bombPosition3.x--;
            }
            else if (player.x > bombPosition3.x){
                bombPosition3.x++;
            }

            if (player.y < bombPosition3.y) {
                bombPosition3.y--;
            }
            else if (player.y > bombPosition3.y) {
                bombPosition3.y++;
            }
            terminal.setCursorPosition(xOldBombsPos, yOldBombsPos);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
            terminal.putCharacter('B');

            terminal.flush();



            System.out.println(" Column:" + player.x + " Row:" + player.y + " " + type);

            // quit with q

            if (c == Character.valueOf('q'))
                continueReadingInput = false;

            // decrease life when we die from bomb

            if (bombPosition.x == player.x && bombPosition.y == player.y) {
                if (player.numberLife > 1) {
                    player.numberLife--;


                    // Reset av bomb 1
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter(' ');
                    bombPosition.x = r.nextInt(78);
                    bombPosition.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter('B');


                    // Reset av bomb 2
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter(' ');
                    bombPosition2.x = r.nextInt(78);
                    bombPosition2.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter('B');

                    // Reset av bomb 3
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter(' ');
                    bombPosition3.x = r.nextInt(78);
                    bombPosition3.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter('B');

                    terminal.flush();

                    Print(player.numberLife);
                } else {
                    continueReadingInput = false;
                }
            }
            // decrease life when we die from bomb 2

            if (bombPosition2.x == player.x && bombPosition2.y == player.y){
                if (player.numberLife > 1) {
                    player.numberLife--;

                    // Reset av bomb 1
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter(' ');
                    bombPosition.x = r.nextInt(78);
                    bombPosition.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter('B');


                    // Reset av bomb 2
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter(' ');
                    bombPosition2.x = r.nextInt(78);
                    bombPosition2.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter('B');

                    // Reset av bomb 3
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter(' ');
                    bombPosition3.x = r.nextInt(78);
                    bombPosition3.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter('B');

                    terminal.flush();
                    Print(player.numberLife);

                } else {
                    continueReadingInput = false;
                }
            }

            // decrease life when we die from bomb 3
            if (bombPosition2.x == player.x && bombPosition2.y == player.y){
                if (player.numberLife > 1) {
                    player.numberLife--;

                    // Reset av bomb 1
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter(' ');
                    bombPosition.x = r.nextInt(78);
                    bombPosition.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition.x, bombPosition.y);
                    terminal.putCharacter('B');


                    // Reset av bomb 2
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter(' ');
                    bombPosition2.x = r.nextInt(78);
                    bombPosition2.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition2.x, bombPosition2.y);
                    terminal.putCharacter('B');

                    // Reset av bomb 3
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter(' ');
                    bombPosition3.x = r.nextInt(78);
                    bombPosition3.y = r.nextInt(23);
                    terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
                    terminal.putCharacter('B');

                    terminal.flush();
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
            terminal.setCursorPosition(column+30, 4);

            terminal.putCharacter(lanternaPrint.charAt(column - 1));
            terminal.flush();
        }
    }

    public static void Printreverse() throws IOException {
        String lanternaPrint = "                                  ";
        int length = lanternaPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column+30, 4);

            terminal.putCharacter(lanternaPrint.charAt(column - 1));
            terminal.flush();
        }
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