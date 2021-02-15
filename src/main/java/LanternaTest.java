import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO ljudefferkter
// TODO Anpassa frame
// TODO Färger

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

        int score = 0;
        KeyStroke keyStroke = terminal.pollInput();


        // final char monster ='M';
        boolean continueReadingInput = true;
        boolean isPlaying = true;
        final char block = '\u2588';


        Position position = new Position(0, 0);
        List<Position> obsticleList = new ArrayList<>();


        // Create frame parameters

        // Top frame
        for (int i = 0; i <= 79; i++) {
            obsticleList.add(new Position(i, 0));
        }

        //Bottom frame

        for (int i = 0; i <= 79; i++) {
            obsticleList.add(new Position(i, 23));
        }

        //left Frame

        for (int i = 0; i <= 23; i++) {
            obsticleList.add(new Position(0, i));
        }

        //Right frame

        for (int i = 0; i <= 23; i++) {
            obsticleList.add(new Position(79, i));
        }

        // Obstacles 1
        for (int i = 0; i <= 5; i++) {
            obsticleList.add(new Position(10, 5+i));
        }
        // Obstacles 2
        for (int i = 0; i <= 5; i++) {
            obsticleList.add(new Position(34, 15+i));
        }
        // Obstacles 3
        for (int i = 0; i <= 14; i++) {
            obsticleList.add(new Position(6+i, 15));
        }
        // Obstacles 4
        for (int i = 0; i <= 18; i++) {
            obsticleList.add(new Position(25+i, 13));
        }
        // Obstacles 5
        for (int i = 0; i <= 3; i++) {
            obsticleList.add(new Position(40+i, 8));
        }
        // Obstacles 6
        for (int i = 0; i <= 7; i++) {
            obsticleList.add(new Position(60+i, 4));
        }
        // Obstacles 7
        for (int i = 0; i <= 10; i++) {
            obsticleList.add(new Position(60+i, 18));
        }

        drawFrame(obsticleList, block);


        // create arrayList from monster

        List<Monster> monsterList = new ArrayList<>();

        //Set Monster position


        Random r = new Random();
        Monster monster1 = new Monster(r.nextInt(77), r.nextInt(23), '\u046A');
        monsterList.add(monster1);

        Monster monster2 = new Monster(r.nextInt(78), r.nextInt(23), '\u046A');
        monsterList.add(monster2);

        Monster monster3 = new Monster(r.nextInt(78), r.nextInt(23), '\u046A');
        monsterList.add(monster3);



        // create player object
        Player player = new Player(35, 12);

        do { // Outer loop for every new game

            // Set player lives
            player.numberLife = 3;

            // Set player score
            score = 0;
            continueReadingInput = true;

            // Set player starting position
            player.x = 35;
            player.y = 12;

            terminal.clearScreen();
            drawFrame(obsticleList, block);

            // draw player starting position
            terminal.setCursorPosition(player.x, player.y);
            terminal.putCharacter(player.playerIcon);


            // draw monster positions
            terminal.setCursorPosition(monster1.x, monster1.y);
            terminal.putCharacter(monster1.monsterIcon);

            terminal.setCursorPosition(monster2.x, monster2.y);
            terminal.putCharacter(monster2.monsterIcon);


            terminal.setCursorPosition(monster3.x, monster3.y);
            terminal.putCharacter(monster3.monsterIcon);

            //terminal.setForegroundColor(TextColor.ANSI.BLUE);
            //terminal.setBackgroundColor(TextColor.ANSI.BLUE);

            //terminal.setBackgroundColor(TextColor.ANSI.DEFAULT);

            drawFrame(obsticleList, block);
            //terminal.setForegroundColor(TextColor.ANSI.DEFAULT);
            terminal.flush();


            do { // Inner loop for every new life


                keyStroke = null;
                do {
                    Thread.sleep(5);// might throw Interrupted  Exception
                    keyStroke = terminal.pollInput();
                }
                while (keyStroke == null);

                Printreverse();

                KeyType type = keyStroke.getKeyType();
                Character c = keyStroke.getCharacter();// used Character, not char because it might be null

                boolean crashIntoObsticle = false;

                switch (type) {
                    case ArrowUp:
                        crashIntoObsticle = player.movePlayerUp(obsticleList);
                        break;

                    case ArrowDown:
                        crashIntoObsticle = player.movePlayerDown(obsticleList);
                        break;

                    case ArrowLeft:
                        crashIntoObsticle = player.movePlayerLeft(obsticleList);
                        break;

                    case ArrowRight:
                        crashIntoObsticle = player.movePlayerRight(obsticleList);
                        break;

                    default:
                        break;
                }

                drawFrame(obsticleList, block);

                // Check if we hit an obstacle, then we prevent player to pass the obstacle

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
                    CleanAndMove(player.x, player.y, player.xOld, player.yOld, player.playerIcon);
                } else {
                    CleanAndMove(player.x, player.y, player.xOld, player.yOld, player.playerIcon);
                    score++;
                }

                //Move monsters
                for (Monster m : monsterList) {

                    m.xOld = m.x;
                    m.xOld = m.y;
                    m.moveMonster(player, obsticleList);

                    CleanAndMove(m.x, m.y, m.xOld, m.yOld, m.monsterIcon);


                }

                // logging activities

                System.out.println(" Column:" + player.x + " Row:" + player.y + " " + type);

                // quit with q

                if (c == Character.valueOf('q'))
                    continueReadingInput = false;

                // decrease life when we die from monster

                for (Monster m : monsterList) {

                    // Check if we have hit a monster and if we have any lives left

                    if (m.x == player.x && m.y == player.y) {
                        if (player.numberLife > 1) {
                            player.numberLife--;
                            terminal.bell();


                            // Reset of all monster

                            for (Monster mon : monsterList) {
                                mon.xOld = mon.x;
                                mon.yOld = mon.y;
                                mon.x = r.nextInt(78);
                                mon.y = r.nextInt(23);
                                CleanAndMove(mon.x, mon.y, mon.xOld, mon.yOld, mon.monsterIcon);

                            }

                            Print(player.numberLife);
                        } else {
                            // exit life loop and goes to new game
                            terminal.bell();
                            continueReadingInput = false;
                        }
                    }
                }


            } while (continueReadingInput);

            terminal.clearScreen();
            printEndScreen(score);

            // Wait for player keystroke to restart outer loop / game

            keyStroke = null;

            do {
                Thread.sleep(5);// might throw Interrupted  Exception
                keyStroke = terminal.pollInput();
            }
            while (keyStroke == null);

            Printreverse();

            KeyType type = keyStroke.getKeyType();
            Character c = keyStroke.getCharacter();// used Character, not char because it might be null


            if (c == Character.valueOf('q'))
                isPlaying = false;

        } while (isPlaying);
        terminal.close();

    }

    public static void Print(int life) throws IOException {
        String lanternaPrint = "You lost a life - only " + life + " remaining";
        int length = lanternaPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 22, 11);

            terminal.putCharacter(lanternaPrint.charAt(column - 1));
            terminal.flush();
        }
    }

    public static void Printreverse() throws IOException {
        String lanternaPrint = "                                  ";
        int length = lanternaPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 22, 11);

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

    public static void printEndScreen(int score) throws IOException {
        String gameOverPrint = "GAME OVER";
        int length = gameOverPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 35, 8);

            terminal.putCharacter(gameOverPrint.charAt(column - 1));
            terminal.flush();
        }

        String scorePrint = "Your total score is: " + score ;
        length = scorePrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 28, 10);

            terminal.putCharacter(scorePrint.charAt(column - 1));
            terminal.flush();
        }

        String endTextPrint = "Press any key to play again or press q to exit";
        length = endTextPrint.length();

        for (int column = 1; column <= length; column++) {
            terminal.setCursorPosition(column + 17, 14);

            terminal.putCharacter(endTextPrint.charAt(column - 1));
            terminal.flush();
        }


    }

        public static void drawFrame(List<Position> frame, char block) throws IOException {

            for (Position list:frame) {
                terminal.setCursorPosition(list.x, list.y);
                terminal.putCharacter(block);
                terminal.flush();
            }

        }

}



