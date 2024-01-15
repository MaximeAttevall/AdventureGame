import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Grotta2 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Grotta2().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Grotta 2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button1 = createButton("Button 1");
        JButton button2 = createButton("Button 2");
        JButton button3 = createButton("Button 3");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);

        frame.getContentPane().add(buttonPanel);
        frame.setSize(300, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Pixel Emulator", Font.BOLD, 18)); // Custom retro font
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(new Color(255, 0, 0)); // Set button color to red

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(139, 0, 0)); // Dark red on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(new Color(255, 0, 0)); // Red when not hovered
            }
        });

        button.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        try {
            JOptionPane.showMessageDialog(null, "Button '" + text + "' clicked!");
            
            if (text.equals("Button 1") || text.equals("Button 2")) {
                func.incrementDeathcounter();
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle or log the exception
        }
    }
});


        return button;
    }
}
