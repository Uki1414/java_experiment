import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestGUI extends JFrame {
  JButton exitButton;

  public TestGUI () {
    super("TestGUI");

    //終了ボタンを作る
    exitButton = new JButton("Press to Exit");

    //終了ボタンが押されたときの処理を記述
    exitButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e){
        System.exit(0);
      }
    });

    //アプリケーションのコントロール配置領域(ContentPane)にラベルと終了ボタンを配置
    getContentPane().setLayout(new FlowLayout());
    getContentPane().add(new JLabel("Test Button:"));
    getContentPane().add(exitButton);
  }

  public static void main(String[] args){
    TestGUI t = new TestGUI();
    t.pack();
    t.setVisible(true);
  }
}