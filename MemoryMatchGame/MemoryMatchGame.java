import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryMatchGame extends JFrame implements ActionListener {
    private final int GRID_SIZE = 4; // 4x4 grid
    private JButton[] buttons; // Buttons representing cards
    private String[] icons; // Card icons (simple text representation for this example)
    private JButton firstButton = null, secondButton = null; // To store selected buttons
    private boolean isCheckingMatch = false;
    private int movesCount = 0;
    private JLabel movesLabel;

    public MemoryMatchGame() {
        setTitle("Memory Match Game");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the game grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        buttons = new JButton[GRID_SIZE * GRID_SIZE];

        // Create icons for cards (simple text-based icons)
        ArrayList<String> iconList = new ArrayList<>();
        for (int i = 0; i < (GRID_SIZE * GRID_SIZE) / 2; i++) {
            iconList.add("Icon" + i);
            iconList.add("Icon" + i);
        }
        Collections.shuffle(iconList);
        icons = iconList.toArray(new String[0]);

        // Initialize buttons
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 20));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(String.valueOf(i));
            gridPanel.add(buttons[i]);
        }

        // Moves count display
        movesLabel = new JLabel("Moves: 0");
        add(movesLabel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isCheckingMatch) return; // Prevent interaction during a match check

        int index = Integer.parseInt(e.getActionCommand());
        JButton clickedButton = buttons[index];

        // If button is already flipped, ignore
        if (clickedButton.getText().length() > 0) return;

        // Reveal the card
        clickedButton.setText(icons[index]);

        // Check if this is the first or second card selected
        if (firstButton == null) {
            firstButton = clickedButton;
        } else {
            secondButton = clickedButton;
            movesCount++;
            movesLabel.setText("Moves: " + movesCount);
            isCheckingMatch = true;

            // Check for match with a delay
            Timer timer = new Timer(1000, event -> {
                if (!firstButton.getText().equals(secondButton.getText())) {
                    // No match - hide cards again
                    firstButton.setText("");
                    secondButton.setText("");
                }
                firstButton = null;
                secondButton = null;
                isCheckingMatch = false;
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public static void main(String[] args) {
        new MemoryMatchGame();
    }
}
