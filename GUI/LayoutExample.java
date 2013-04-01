import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Container;
 
public class LayoutExample extends JFrame {
    public LayoutExample() {
        this.setTitle("FlowLayoutDemo");
        // get the top-level container in the Frame (= Window)
        Container contentPane = this.getContentPane();
 
        // set the layout of this container
        contentPane.setLayout(new FlowLayout());
 
        // add buttons in this container
        this.add((new JButton("Button 1")));
        this.add((new JButton("Button 2")));
        this.add((new JButton("Button 3")));
        this.add((new JButton("Long-Named Button 4")));
        this.add((new JButton("5")));
 
        // exit the application when clicking on the right close-button
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
 
    public static void main(String[] args) {
        LayoutExample example = new LayoutExample();
        example.setVisible(true);
    }
}