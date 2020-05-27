import java.sql.SQLOutput;
import java.util.*;

public class Battleship {
    static String[][] map = new String[12][10];
    static int[] playerx = new int[5];
    static int[] playery = new int[5];
    static int[] compx = new int[5];
    static int[] compy = new int[5];
    static int[] displaycompx = new int[5];
    static int[] displaycompy = new int[5];
    static int playerscore = 5;
    static int compscore = 5;
    static ArrayList<Integer> compguessx = new ArrayList<>();
    static ArrayList<Integer> compguessy = new ArrayList<>();


    public static void drawMap() {
        //String[][] map = new String[12][10];

        for (int row = 0; row < map.length; row++) {
            if (row == 0 || row == 11) {
                System.out.println("  0123456789  ");
            } else {
                System.out.print(row - 1 + "|");
                for (int col = 0; col < map[row].length; col++) {
                    if (map[row][col] == null) {
                        map[row][col] = " ";
                        System.out.print(" ");
                    } else {
                        System.out.print(map[row][col]);
                    }
                }
                System.out.println("|" + (row - 1));
            }
        }
    }

    public static void main(String args[]) {
        drawMap();

        Scanner input = new Scanner(System.in);
        int count = 0;
        while (count < 5) {
            System.out.print("Enter X coordinate for your ship " + (count + 1) + " (0-9): ");
            int x = input.nextInt();
            while (!(x >= 0 && x <= 9)){
                System.out.print("Sorry, pick between 0 and 9: ");
                x = input.nextInt();
            }
            System.out.print("Enter Y coordinate for your ship " + (count + 1) + " (0-9): ");
            int y = input.nextInt();
            while (!(y >= 0 && y <= 9)){
                System.out.print("Sorry, pick between 0 and 9: ");
                y = input.nextInt();
            }
//            System.out.println(map[y+1][x]);
            if (map[y + 1][x].equals(" ")){
                map[y + 1][x] = "@";
                playerx[count] = x;
                playery[count] = y + 1;
                count += 1;
            } else {
                System.out.println("Sorry, that space is already taken.");
            }
        }
        drawMap();
        System.out.println(String.format("Your ships: %d | Computer's ships: %d", playerscore, compscore));

        count = 0;
        Random rand = new Random();
        System.out.println("Computer is deploying ships");
        while (count < 5) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if (map[y + 1][x].equals(" ")){
                compx[count] = x;
                displaycompx[count] = x;
                compy[count] = y + 1;
                displaycompy[count] = y;
                count += 1;
                System.out.println("Ship " + count + " has been deployed.");
            }
        }

        boolean notEnd = true;
        int[] gameover = {99, 99, 99, 99, 99};

        while (notEnd) {

            // Computer coordinates and guesses
            System.out.print("Would you like to see computer coordinates? (type yes or no): ");
            String ans = input.next();
            if (ans.equals("yes")) {
                System.out.println("Computer ship coordinates: ");
                System.out.println(Arrays.toString(displaycompx));
                System.out.println(Arrays.toString(displaycompy));
                System.out.println("Computer Guesses: ");

                System.out.println(compguessx.toString());
                System.out.println(compguessy.toString());
            }

            //Player Turn
            System.out.println("YOUR TURN");

            System.out.print("Enter X coordinate: ");
            int x = input.nextInt();
            while (!(x >= 0 && x <= 9)){
                System.out.print("Sorry, pick between 0 and 9: ");
                x = input.nextInt();
            }
            System.out.print("Enter Y coordinate: ");
            int y = input.nextInt();
            while (!(y >= 0 && y <= 9)){
                System.out.print("Sorry, pick between 0 and 9: ");
                y = input.nextInt();
            }

            for (int i = 0; i < compx.length; i++) {
                if (compx[i] == x && compy[i] == y + 1) {
                    System.out.println("BOOM! You sunk the ship!");
                    map[y + 1][x] = "!";
                    compx[i] = 99;
                    compy[i] = 99;
                    compscore -= 1;
                    break;
                } else if (playerx[i] == x && playery[i] == y + 1) {
                    System.out.println("Oh no, you sunk your own ship :(");
                    map[y + 1][x] = "x";
                    playerx[i] = 99;
                    playery[i] = 99;
                    playerscore -= 1;
                    break;
                } else if (i == compx.length - 1){
                    System.out.println("Sorry you missed.");
                    map[y + 1][x] = "-";
                }
            }
            drawMap();
            System.out.println(String.format("Your ships: %d | Computer's ships: %d", playerscore, compscore));

            //Computer Turn
            System.out.println("COMPUTER'S TURN");
            boolean repeat = true;
            x = rand.nextInt(10);
            y = rand.nextInt(10);

            while (repeat) {
                x = rand.nextInt(10);
                y = rand.nextInt(10);
                boolean found = false;
                for (int i = 0; i < compguessx.size(); i++) {
                    if (compguessx.get(i) == x && (compguessy.get(i) + 1) == y) {
                        found = true;
                    }
                }
                if (!found) {
                    repeat = false;
                    compguessx.add(x);
                    compguessy.add(y + 1);
                }
            }

            for (int i = 0; i < compx.length; i++) {
                if (compx[i] == x && compy[i] == y + 1) {
                    System.out.println("The Computer sunk one of its own ships");
                    map[y + 1][x] = "!";
                    compx[i] = 99;
                    compy[i] = 99;
                    compscore -= 1;
                    break;
                } else if (playerx[i] == x && playery[i] == y + 1) {
                    System.out.println("The Computer sunk one of your ships!");
                    map[y + 1][x] = "x";
                    playerx[i] = 99;
                    playery[i] = 99;
                    playerscore -= 1;
                    break;
                } else if (i == compx.length - 1){
                    System.out.println("Computer missed");
                }
            }
            drawMap();
            System.out.println(String.format("Your ships: %d | Computer's ships: %d", playerscore, compscore));


            // Game Over Condition
            if (Arrays.equals(compx, gameover)) {
                notEnd = false;
                System.out.println("Hooray! You win the battle :)");
            } else if (Arrays.equals(playerx, gameover)) {
                notEnd = false;
                System.out.println("Computer wins");
            }

//            this.super.getTotal();

        }
    }
}
