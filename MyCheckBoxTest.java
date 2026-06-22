import javax.swing.*;
import java.awt.*;

public class MyCheckBoxTest extends JFrame{
  public MyCheckBoxTest(){
    super("MyCheckBoxTest");

    JPanel jp = new JPanel();
    jp.setLayout(new FlowLayout());
    getContentPane().add(jp);

    JCheckBox dashCheck = new JCheckBox("Dashed");
    jp.add(dashCheck);

    JCheckBox boldCheck = new JCheckBox("Bold");
    jp.add(boldCheck);

    setSize(300, 300);
  }

  public static void main(String[] args){
    MyCheckBoxTest myapp = new MyCheckBoxTest();
    myapp.setVisible(true);
  }
}