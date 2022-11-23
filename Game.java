package project.game;

import java.util.Scanner;
import project.turn.Turn;
import project.ai.AI;
import base.vector.Vector;

public class Game {
    private String player1;
    private String player2;
    private boolean playAgainstAi;
    private boolean playAiAgainstAi;
    private Integer aiPlayer;
    private int countMatchstick = 13;
    private final String consoleSeparator = "*************";

    public Game() {}

    private void withdrawMatchstick(int n) {
        countMatchstick -= n;
    }

    private void configurePlayer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("NOTE: taper le nom IA si vous voulez jouer contre l'IA\n");
        System.out.print("Saisir le joueur 1 : ");

        this.player1 = scanner.next();

        System.out.print("Saisir le joueur 2 : ");

        this.player2 = scanner.next();

        if (this.player1.equals("IA") && this.player2.equals("IA")) {
            this.player1 = "IA(1)";
            this.player2 = "IA(2)";
            this.playAiAgainstAi = true;
            this.playAgainstAi = false;
        } else {
            if (this.player1.equals("IA")) {
                this.playAgainstAi = true;
                this.aiPlayer = 1;
            } else if (this.player2.equals("IA")) {
                this.playAgainstAi = true;
                this.aiPlayer = 2;
            } else {
                this.aiPlayer = null;
                this.playAgainstAi = false;
            }
        }
    }

    private void printCountMatchstick() {
        System.out.printf("Allumettes: %s\n", "| ".repeat(countMatchstick));
    }

    private void printPlayer1Resume() {
        System.out.printf("%s%s%s\n", consoleSeparator, this.player1, consoleSeparator);
        printCountMatchstick();
    }

    private void printPlayer2Resume() {
        System.out.printf("%s%s%s\n", consoleSeparator, this.player2, consoleSeparator);
        printCountMatchstick();
    }

    public void withdraw() {
        Scanner scanner = new Scanner(System.in);
        int n = 0;

        do {
            System.out.print("Saisir le nombre a retirer (1..3): ");
            n = scanner.nextInt();
        } while(!((n >= 1 && n <= 3) && n <= countMatchstick));

        withdrawMatchstick(n);
    }

	public void withdrawAI(int n) {
		System.out.printf("L'IA retire %d allumettes\n", n);
		withdrawMatchstick(n);
	}

    public void run() {
        var turn = new Turn();

        // 1. Configure les joueurs
        configurePlayer();

        if (playAgainstAi) {
			// 2. Charge l'IA
			var ai = new AI(this.player1.equals("AI") ? 1 : 2);

            while (countMatchstick > 1) {
                if (turn.whoNext() == 1) {
                    printPlayer1Resume();
                } else {
                    printPlayer2Resume();
                }

				if (ai.getPlayer() == turn.whoNext()) {
					int n = ai.run(turn.getCountTurn(), this.countMatchstick);

					withdrawAI(n);
				} else {
					int temp = this.countMatchstick;

					withdraw();
				}

                turn.nextTurn();
            }
        } else if (playAiAgainstAi) {
			// 2. Charge l'IA
			var ai1 = new AI(1);
			var ai2 = ai1;

			ai2.setPlayer(2);

            while (countMatchstick > 1) {
                if (turn.whoNext() == 1) {
                    printPlayer1Resume();
                } else {
                    printPlayer2Resume();
                }

				if (ai1.getPlayer() == turn.whoNext()) {
					int n = ai1.run(turn.getCountTurn(), this.countMatchstick);

					withdrawAI(n);
				} else {
					int n = ai2.run(turn.getCountTurn(), this.countMatchstick);

					withdrawAI(n);
				}

                turn.nextTurn();
            }
        } else {
            while (countMatchstick > 1) {
                if (turn.whoNext() == 1) {
                    printPlayer1Resume();
                } else {
                    printPlayer2Resume();
                }

                withdraw();

                turn.nextTurn();
            }
        }

        // 3. Determine qui a perdu
        if (countMatchstick == 0) {
            if (turn.whoNext() == 1) {
                System.out.printf("Le joueur %s a perdu\n", this.player2);
            } else {
                System.out.printf("Le joueur %s a perdu\n", this.player1);
            }
        } else {
            if (turn.whoNext() == 1) {
                System.out.printf("Le joueur %s a perdu\n", this.player1);
            } else {
                System.out.printf("Le joueur %s a perdu\n", this.player2);
            }
        }
    }
}
