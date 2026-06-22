import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyApplication2 extends JFrame{
  public MyApplication2(){
    super("My Painter");

    JPanel jp = new JPanel();
    jp.setLayout(new BorderLayout());
    MyCanvas canvas = new MyCanvas();

    jp.add(BorderLayout.CENTER, canvas);
    getContentPane().add(jp);

    MyMouseAdapter ma = new MyMouseAdapter(canvas);
    canvas.addMouseListener(ma);

    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        System.exit(1);
      }
    });
  }

  public static void main(String[] args){
    MyApplication2 ma = new MyApplication2();
    ma.setSize(400,300);
    ma.setVisible(true);
  }
}

//マウスペイントを扱うクラス
class MyMouseAdapter extends MouseAdapter{
  private MyCanvas canvas;

  public MyMouseAdapter(MyCanvas canvas){
    this.canvas = canvas;
  }

  //マウスがクリックされたときの処理をここに記述
  public void mouseClicked(MouseEvent e){
    canvas.addDrawing(new MyRectangle(e.getX(), e.getY()));

    //キャンバスの再描画命令
    canvas.repaint();
  }
}