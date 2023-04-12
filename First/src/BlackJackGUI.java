import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class BlackJackGUI extends JFrame implements ActionListener {
    private JLabel playerLabel, dealerLabel, resultLabel, playerWinsLabel, dealerWinsLabel;
    private JButton rollButton, stayButton, newGameButton;
    private JTextField playerField, dealerField, playerWinsField, dealerWinsField;

    private int playerScore, dealerScore, playerWins, dealerWins;

    public BlackJackGUI() {
        setTitle("Black Jack");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        playerLabel = new JLabel("Your Score:");
        playerField = new JTextField();
        playerField.setEditable(false);

        dealerLabel = new JLabel("Dealer's Score:");
        dealerField = new JTextField();
        dealerField.setEditable(false);

        resultLabel = new JLabel("");

        playerWinsLabel = new JLabel("Player Wins:");
        playerWinsField = new JTextField();
        playerWinsField.setEditable(false);

        dealerWinsLabel = new JLabel("Dealer Wins:");
        dealerWinsField = new JTextField();
        dealerWinsField.setEditable(false);

        rollButton = new JButton("Roll");
        rollButton.addActionListener(this);

        stayButton = new JButton("Stay");
        stayButton.addActionListener(this);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(this);
        newGameButton.setEnabled(false);

        add(playerLabel);
        add(playerField);
        add(dealerLabel);
        add(dealerField);
        add(resultLabel);
        add(new JLabel(""));
        add(rollButton);
        add(stayButton);
        add(playerWinsLabel);
        add(playerWinsField);
        add(dealerWinsLabel);
        add(dealerWinsField);
        add(newGameButton);
        add(new JLabel(""));
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == rollButton) {
            Random random = new Random();
            int roll = random.nextInt(6) + 1;
            playerScore += roll;
            playerField.setText(Integer.toString(playerScore));

            if (playerScore > 21) {
                resultLabel.setText("Bust! Dealer wins!");
                dealerWins++;
                dealerWinsField.setText(Integer.toString(dealerWins));
                rollButton.setEnabled(false);
                stayButton.setEnabled(false);
                newGameButton.setEnabled(true);
            }
        } else if (e.getSource() == stayButton) {
            Random random = new Random();
            while (dealerScore < 17) {
                int roll = random.nextInt(6) + 1;
                dealerScore += roll;
                dealerField.setText(Integer.toString(dealerScore));
            }

            if (dealerScore > 21 || playerScore > dealerScore) {
                resultLabel.setText("You win!");
                playerWins++;
                playerWinsField.setText(Integer.toString(playerWins));
            } else if (playerScore == dealerScore) {
                resultLabel.setText("It's a tie!");
            } else {
                resultLabel.setText("Dealer wins!");
                dealerWins++;
                dealerWinsField.setText(Integer.toString(dealerWins));
            }

            rollButton.setEnabled(false);
            stayButton.setEnabled(false);
            newGameButton.setEnabled(true);
        } else if (e.getSource() == newGameButton) {
            playerScore = 0;
            dealerScore = 0;
            playerField.setText("");
            dealerField.setText("");
            resultLabel.setText("");
            rollButton.setEnabled(true);
            stayButton.setEnabled(true);
            newGameButton.setEnabled(false);
            playerWinsField.setText(Integer.toString(playerWins));
            dealerWinsField.setText(Integer.toString(dealerWins));
        }

        // Aktivieren des rollButton, wenn stayButton deaktiviert ist
        if (playerScore <= 21 && dealerScore >= 17 && e.getSource() == rollButton) {
            rollButton.setEnabled(true);
        }

        // Deaktivieren des newGameButton, wenn Spiel nicht beendet ist
        if (e.getSource() == rollButton || e.getSource() == stayButton) {
            newGameButton.setEnabled(false);
        }
    }

}