import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// ウインドウを表すクラス
public class MyApplication extends JFrame{
  public MyApplication(){
    super("My Painter");

    JPanel jp = new JPanel();
    jp.setLayout(new BorderLayout());
    this.getContentPane().add(jp);

    MyCanvas canvas = new MyCanvas();
    
    canvas.addDrawing(new MyOval(150, 20));
    canvas.addDrawing(new MyHendecagonal(20, 150));
    canvas.addDrawing(new MyRoundRect(150, 150));
    canvas.addDrawing(new MyRectangle(20, 20, 100, 80, Color.black, Color.red, 2));

    jp.add(BorderLayout.CENTER, canvas);

    // WindowEvent リスナを設定(無名クラスを利用している)
    this.addWindowListener(
      new WindowAdapter(){
        // ウインドウが閉じたら終了する処理
        public void windowClosing(WindowEvent e){
          System.exit(1);
        }
      }
    );
  }

  public static void main(String[] args){
    MyApplication app = new MyApplication();
    app.setSize(400,300);
    app.setVisible(true);
  }
}