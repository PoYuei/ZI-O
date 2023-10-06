import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;


public class CryptoUI {
    private JFrame frame;
    private JTextArea inputTextArea, outputTextArea, keyTextArea;
    private JButton encryptButton, decryptButton, generateKeyButton;

    public CryptoUI() {
        frame = new JFrame("S-DES Crypto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 600);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JPanel keyPanel = new JPanel(new BorderLayout());
        keyTextArea = new JTextArea("Enter your key here...");
        styleTextArea(keyTextArea);
        keyPanel.add(keyTextArea, BorderLayout.CENTER);

        generateKeyButton = new JButton("Generate Key");
        generateKeyButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        keyPanel.add(generateKeyButton, BorderLayout.EAST);
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyTextArea.setText(generateRandomBinaryKey());
            }
        });


        JPanel inputPanel = new JPanel(new BorderLayout());
        inputTextArea = new JTextArea("Input text here...");
        styleTextArea(inputTextArea);
        inputPanel.add(inputTextArea, BorderLayout.CENTER);


        JPanel buttonPanel = new JPanel();
        encryptButton = new JButton("加密");
        decryptButton = new JButton("解密");
        encryptButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        decryptButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);

        encryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                String key = keyTextArea.getText();
                String encrypted = encryptFunction(input, key);
                outputTextArea.setText(encrypted);
            }
        });

        decryptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputTextArea.getText();
                String key = keyTextArea.getText();
                String decrypted = decryptFunction(input, key);
                outputTextArea.setText(decrypted);
            }
        });

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputTextArea = new JTextArea("Output text here...");
        outputTextArea.setEditable(false);
        styleTextArea(outputTextArea);
        outputPanel.add(outputTextArea, BorderLayout.CENTER);

        constraints.weightx = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        constraints.weighty = 0.2;
        constraints.gridy = 0;
        frame.add(keyPanel, constraints);

        constraints.weighty = 0.3;
        constraints.gridy = 1;
        frame.add(inputPanel, constraints);

        constraints.weighty = 0.2;
        constraints.gridy = 2;
        frame.add(buttonPanel, constraints);

        constraints.weighty = 0.3;
        constraints.gridy = 3;
        frame.add(outputPanel, constraints);

        frame.setVisible(true);
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 16));
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private String generateRandomBinaryKey() {
        StringBuilder key = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            key.append(rand.nextInt(2));
        }
        return key.toString();
    }

    private String encryptFunction(String input, String key) {
        char[] inputArray = input.toCharArray();
        char[] keyArray = key.toCharArray();
        if (input.length() != 8 || !input.matches("[01]+")) {
            return Main.ACC_Des(0, input, keyArray);
        } else {
            return String.valueOf(Main.My_Des(0, inputArray, keyArray));
        }
    }

    private String decryptFunction(String input, String key) {
        char[] inputArray = input.toCharArray();
        char[] keyArray = key.toCharArray();
        if (input.length() != 8 || !input.matches("[01]+")) {
            return Main.ACC_Des(1, input, keyArray);
        } else {
            return String.valueOf(Main.My_Des(1, inputArray, keyArray));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CryptoUI();
            }
        });
    }
}
