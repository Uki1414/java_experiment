import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RectButton extends JButton implements State {
    private StateManager stateManager;
    private int startX, startY;
    private MyDrawing drawing;

    public RectButton(StateManager stateManager) {
        super("Rectangle");
        this.stateManager = stateManager;
        addActionListener(new RectListener());
    }

    public void mouseDown(int x, int y) {
        this.startX = x;
        this.startY = y;
        drawing = new MyRectangle(x, y, 0, 0, java.awt.Color.black, java.awt.Color.white, 1);
        stateManager.addDrawing(drawing);
    }

    public void mouseDrag(int x, int y) {
        if (drawing == null) return;
        
        int w = Math.abs(x - startX);
        int h = Math.abs(y - startY);
        int newX = Math.min(x, startX);
        int newY = Math.min(y, startY);
        
        drawing.setLocation(newX, newY);
        drawing.setSize(w, h);
        stateManager.setRepaint();
    }

    public void mouseUp(int x, int y) {
        if (drawing != null) {
            if (Math.abs(x - startX) == 0 || Math.abs(y - startY) == 0) {
                stateManager.removeDrawing(drawing);
            }
        }
        drawing = null;
    }

    class RectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(RectButton.this);
        }
    }
}