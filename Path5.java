//package AdventureGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Path5 extends JFrame{
    private JPanel button_panel;
    private JButton[] buttons = new JButton[3];
    private int gameState = 0;
    private JLabel description;
    private Image backgroundIMG= new ImageIcon("Images/Path5/lakeBG.jpg").getImage();

    public Path5(){
        setTitle("AdventureGame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initiateUI();
    }

    private void initiateUI(){
        button_panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image bgImage = new ImageIcon(backgroundIMG).getImage();
                if (backgroundIMG != null){
                    g.drawImage(bgImage, 0, 0, this.getWidth(), this.getHeight(), this);
                }
            }
        };
        description = new JLabel("", SwingConstants.CENTER);
        description.setBounds(50, 150, 700, 100); // Justera texten
        description.setForeground(Color.WHITE); // Justera textfärg
        description.setFont(new Font("Serif", Font.BOLD, 24));
        button_panel.add(description);
        button_panel.setLayout(null);
        addButtons();
        add(button_panel);
    }

    private void addButtons(){
        for (int i = 0; i < buttons.length; i++){
            buttons[i] = new JButton("choice " + (i + 1));
            buttons[i].setBounds(20, 400 + 50 * i, 200, 30);
            buttons[i].addActionListener(new ButtonListener());
            button_panel.add(buttons[i]);
        }
        updateScene();
    }

    private void updateScene(){
        switch (gameState){
            case 0:
                description.setText("Den mörka passagen öpnnar upp till en underjordisk flod");
                buttons[0].setText("Gå läng med en smal klippkant");
                buttons[1].setText("Simma");
                buttons[2].setText("Använd en rutten eka");
                break;
            case 1:
                description.setText("längs med klippkanten hittar du en gammal karta bland svampar");
                buttons[0].setText("Följ ett ekande ljud genom dimman");
                buttons[1].setText("Navigera med hjälp av kartan");
                buttons[2].setText("Följ ett spår av självlysande svampar");
                break;
            case 2:
                description.setText("Svamparna leder dig till ett vilande monster");
                buttons[0].setText("Kasta sten för att distrahera monstret");
                buttons[1].setText("Mata monstret med svamparna");
                buttons[2].setText("Smyg runt monstret");
                break;
                //Lägg till fler case här
        }
        //Justerar storleken på knapparna
        for (JButton button : buttons) {
            adjustButtonSize(button);
        }
    }

    private void updateBG(String imagePath) {
        backgroundIMG = new ImageIcon(imagePath).getImage();
        button_panel.repaint(); //Byter bakgrundsbild
    }

    private void adjustButtonSize(JButton button) {
        FontMetrics metrics = button.getFontMetrics(button.getFont());
        int width = metrics.stringWidth(button.getText()) + 40; //40 är marginalen i px
        int height = button.getHeight();
        button.setPreferredSize(new Dimension(width, height));
        button.setBounds(button.getX(), button.getY(), width, height);
    }

    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JButton clickedButton = (JButton) e.getSource();
            
            try{
                switch (gameState){
                    case 0:
                        if (clickedButton == buttons[1] || clickedButton == buttons[2]){
                            updateBG("Images/Path5/drownedBG.jpg");
                            for (JButton button : buttons) {
                                button.setVisible(false); // eller button.setEnabled(false) om du vill inaktivera dem istället
                            }
                            updateScene();
                            description.setText("Du drunknade");
                            func.Die();
                        } else {
                            updateBG("Images/Path5/shroomsBG.jpg");
                            proceedToNextState();
                        }
                        break;
                    case 1:
                    
                        if (clickedButton == buttons[0] || clickedButton == buttons[1]){
                            updateBG("Images/Path5/wrongTurnBG.jpg");
                            for (JButton button : buttons) {
                                button.setVisible(false); // eller button.setEnabled(false) om du vill inaktivera dem istället
                            }
                            updateScene();
                            description.setText("Det ledde dig till ett stup");
                            func.Die();
                        } else {
                            updateBG("Images/Path5/monsterGuardBG.jpg");
                            proceedToNextState();
                        }
                        break;
                    case 2:
                        if (clickedButton == buttons[0] || clickedButton == buttons[1]){
                            updateBG("Images/Path5/wokeMonsterBG.jpg");
                            for (JButton button : buttons) {
                                button.setVisible(false); // eller button.setEnabled(false) om du vill inaktivera dem istället
                            }
                            updateScene();
                            description.setText("Monstret åt upp dig");
                            func.Die();
                        } else {
                            updateBG("Images/win.png");
                            for (JButton button : buttons) {
                                button.setVisible(false); // eller button.setEnabled(false) om du vill inaktivera dem istället
                            }
                            updateScene();
                            description.setText("Du hittade utgången");
                            func.Win();
                        }
                        break;
                    // Lägg till fler case om nödvändigt
                }
            }
            catch(IOException x){
                x.printStackTrace();
            }
        }
        
        private void proceedToNextState() {
            gameState = (gameState + 1) % 3;
            updateScene();
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Path5 window = new Path5();
            window.setVisible(true);
        });
    }
}
