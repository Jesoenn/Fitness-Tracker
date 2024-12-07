package lab4.ui;

import javax.swing.*;
import java.util.Objects;

public class Frame extends JFrame {
    public Frame(int width, int height) {
        this.setSize(width, height);
        this.setLayout(null);
        ImageIcon image =new ImageIcon(Objects.requireNonNull(getClass().getResource("/FrameIcon.jpeg")));
        this.setIconImage(image.getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public void clear(){
        getContentPane().removeAll();
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    public void addPanel(JPanel panel){
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
    public void addScrollPanel(JScrollPane panel){
        getContentPane().add(panel);
        revalidate();
        repaint();
    }
}
