import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CheatUI {
    private JFrame frame;
    private JTextField p1Field, p2Field, c1Field, c2Field;
    private JTextArea resultArea;
    private JButton crackButton;
    private JPanel inputPanel, buttonPanel;

    public CheatUI() {
        frame = new JFrame("Key Cracker");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setBackground(new Color(135, 206, 235));  // Sky blue color

        p1Field = new JTextField(10);
        p2Field = new JTextField(10);
        c1Field = new JTextField(10);
        c2Field = new JTextField(10);
        resultArea = new JTextArea(10, 40);
        resultArea.setWrapStyleWord(true);
        resultArea.setLineWrap(true);
        crackButton = new JButton("Crack Key");

        inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        inputPanel.add(new JLabel("P1:"));
        inputPanel.add(p1Field);
        inputPanel.add(new JLabel("P2:"));
        inputPanel.add(p2Field);
        inputPanel.add(new JLabel("C1:"));
        inputPanel.add(c1Field);
        inputPanel.add(new JLabel("C2:"));
        inputPanel.add(c2Field);

        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(crackButton);

        crackButton.addActionListener(e -> crackKey());

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void crackKey() {
        resultArea.setText("");

        long start = System.currentTimeMillis();

        char[][] keys = cheat.grab_keys(p1Field.getText(), c1Field.getText(), null);
        keys = cheat.grab_keys(p2Field.getText(), c2Field.getText(), keys);

        long finish = System.currentTimeMillis();

        int m = cheat.m;

        resultArea.append("破解完成，结果为：" + m + "\n");
        for (int i = 0; i < m; i++) {
            resultArea.append(String.valueOf(keys[i]) + "\n");
        }
        resultArea.append("耗时：" + (finish - start) + "毫秒\n");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CheatUI());
    }
}
