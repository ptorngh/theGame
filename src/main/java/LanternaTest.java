import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



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

        int xPlayer =35;
        int yPlayer = 12;
        int xOldPlayer;
        int yOldPlayer;
        int xOldBombsPos;
        int yOldBombsPos;
        final char player ='X';
       // final char monster ='M';
        boolean continueReadingInput = true;
        final char block = '\u2588';
        int numberLife = 3;

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





        terminal.setCursorPosition(xPlayer, yPlayer);
        terminal.putCharacter(player);
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

            xOldPlayer = xPlayer;
            yOldPlayer = yPlayer;

            switch(type) {
                case ArrowUp:
                    yPlayer -=2;
                    break;

                case ArrowDown:
                   yPlayer +=2;
                   break;

                case ArrowLeft:
                    xPlayer-=2;
                    break;

                case ArrowRight:
                    xPlayer+=2;
                    break;

                default:
                    break;
            }

            boolean crashIntoObsticle = false;

            for (Position p: obsticleList) {
                //System.out.println(" " + p.x + " " + p.y);
                if (p.x == xPlayer && p.y == yPlayer) {
                    crashIntoObsticle = true;
                }
            }

            if (crashIntoObsticle) {
                xPlayer = xOldPlayer;
                yPlayer = yOldPlayer;
                terminal.setCursorPosition(xPlayer, yPlayer);
                terminal.putCharacter(player);
                terminal.flush();
            }

            else {
                terminal.setCursorPosition(xOldPlayer, yOldPlayer);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(xPlayer, yPlayer);
                terminal.putCharacter(player);

                terminal.flush();
            }

            //Move bomb 1

            xOldBombsPos = bombPosition.x;
            yOldBombsPos = bombPosition.y;

            if (xPlayer < bombPosition.x) {
                bombPosition.x--;
            }
            else if (xPlayer > bombPosition.x){
                bombPosition.x++;
            }

            if (yPlayer < bombPosition.y) {
                bombPosition.y--;
            }
            else if (yPlayer > bombPosition.y) {
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

            if (xPlayer < bombPosition2.x) {
                bombPosition2.x--;
            }
            else if (xPlayer > bombPosition2.x){
                bombPosition2.x++;
            }

            if (yPlayer < bombPosition2.y) {
                bombPosition2.y--;
            }
            else if (yPlayer > bombPosition2.y) {
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

            if (xPlayer < bombPosition3.x) {
                bombPosition3.x--;
            }
            else if (xPlayer > bombPosition3.x){
                bombPosition3.x++;
            }

            if (yPlayer < bombPosition3.y) {
                bombPosition3.y--;
            }
            else if (yPlayer > bombPosition3.y) {
                bombPosition3.y++;
            }
            terminal.setCursorPosition(xOldBombsPos, yOldBombsPos);
            terminal.putCharacter(' ');
            terminal.setCursorPosition(bombPosition3.x, bombPosition3.y);
            terminal.putCharacter('B');

            terminal.flush();



            System.out.println(" Column:" + xPlayer + " Row:" + yPlayer + " " + type);

            if (c == Character.valueOf('q'))
                continueReadingInput = false;

            if (bombPosition.x == xPlayer && bombPosition.y == yPlayer) {
                if (numberLife > 1) {
                    numberLife--;


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

                    Print(numberLife);
                } else {
                    continueReadingInput = false;
                }
            }

            if (bombPosition2.x == xPlayer && bombPosition2.y == yPlayer){
                if (numberLife > 1) {
                    numberLife--;

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
                    Print(numberLife);

                } else {
                    continueReadingInput = false;
                }
            }

            if (bombPosition2.x == xPlayer && bombPosition2.y == yPlayer){
                if (numberLife > 1) {
                    numberLife--;

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
                    Print(numberLife);

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