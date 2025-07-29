import java.util.Scanner;

public class EloCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ELO первого игрока: ");
        double player1Elo = scanner.nextDouble();
        System.out.print("Введите ELO второго игрока: ");
        double player2Elo = scanner.nextDouble();
        System.out.print("Кто победил? (1 — первый, 2 — второй, 0 — ничья): ");
        int winner = scanner.nextInt();
        double kFactor1 = getKFactor(player1Elo);
        double kFactor2 = getKFactor(player2Elo);
        double expectedScore1 = 1.0 / (1.0 + Math.pow(10, (player2Elo - player1Elo) / 400.0));
        double expectedScore2 = 1.0 / (1.0 + Math.pow(10, (player1Elo - player2Elo) / 400.0));

        double actualScore1;
        double actualScore2;
        if (winner == 1) { 
            actualScore1 = 1.0;
            actualScore2 = 0.0;
        } else if (winner == 2) { 
            actualScore1 = 0.0;
            actualScore2 = 1.0;
        } else { 
            actualScore1 = 0.5;
            actualScore2 = 0.5;
        }

        double deltaElo1 = kFactor1 * (actualScore1 - expectedScore1);
        double deltaElo2 = kFactor2 * (actualScore2 - expectedScore2);

        if (Math.abs(deltaElo1) < 1.0 && winner != 0) { 
            if (deltaElo1 > 0) deltaElo1 = 1.0; 
            else if (deltaElo1 < 0) deltaElo1 = -1.0; 
        }
        if (Math.abs(deltaElo2) < 1.0 && winner != 0) { 
            if (deltaElo2 > 0) deltaElo2 = 1.0;
            else if (deltaElo2 < 0) deltaElo2 = -1.0;
        }
        if (winner == 0) {
             if (Math.round(deltaElo1) == 0 && Math.round(deltaElo2) == 0) {
                 if (expectedScore1 < 0.5 && expectedScore2 > 0.5) { 
                     if (deltaElo1 > 0) deltaElo1 = Math.max(deltaElo1, 1.0); 
                     if (deltaElo2 < 0) deltaElo2 = Math.min(deltaElo2, -1.0); 
                 } else if (expectedScore2 < 0.5 && expectedScore1 > 0.5) { 
                     if (deltaElo2 > 0) deltaElo2 = Math.max(deltaElo2, 1.0);
                     if (deltaElo1 < 0) deltaElo1 = Math.min(deltaElo1, -1.0);
                 }
             }
        }
        double newPlayer1Elo = Math.round(player1Elo + deltaElo1);
        double newPlayer2Elo = Math.round(player2Elo + deltaElo2);
        System.out.println("\nРезультаты дуэли:");
        System.out.println("Игрок 1: " + (int) player1Elo + " → " + (int) newPlayer1Elo + " (изменено: " + (int) Math.round(deltaElo1) + " баллов)");
        System.out.println("Игрок 2: " + (int) player2Elo + " → " + (int) newPlayer2Elo + " (изменено: " + (int) Math.round(deltaElo2) + " баллов)");

        scanner.close();
    }

    private static double getKFactor(double elo) {
        if (elo >= 4000) {
            return 5;
        } else if (elo >= 3500) {
            return 10;
        } else if (elo >= 3000) {
            return 15;
        } else if (elo >= 2400) {
            return 20;
        } else if (elo >= 2100) {
            return 30;
        } else {
            return 40;
        }
    }
}