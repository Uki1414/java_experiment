import java.awt.event.*;
import javax.swing.*;

public class RoundRectButton extends JButton implements State {
    private StateManager stateManager;
    private int startX, startY;
    private MyDrawing drawing;

    public RoundRectButton(StateManager stateManager) {
        super("RoundRect");
        this.stateManager = stateManager;
        addActionListener(new RoundRectListener());
    }

    public void mouseDown(int x, int y) {
        this.startX = x;
        this.startY = y;
        drawing = new MyRoundRect(x, y, 0, 0, java.awt.Color.black, java.awt.Color.white, 1);
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

    class RoundRectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(RoundRectButton.this);
        }
    }
}