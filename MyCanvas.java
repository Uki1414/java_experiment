import java.util.*;
import java.awt.*;
import javax.swing.*;

public class MyCanvas extends JPanel{
  Mediator mediator;

  public MyCanvas(){
    mediator = new Mediator(this);
    setLayout(null);
  }

  public Mediator getMediator(){
    return mediator;
  }

  protected void paintComponent(Graphics g){
    super.paintComponent(g);

    Enumeration<MyDrawing> e = mediator.drawingsElements();
    while(e.hasMoreElements()){
      MyDrawing d = e.nextElement();
      d.draw(g);
    }
  }
}
