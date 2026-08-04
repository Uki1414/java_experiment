import javax.swing.*;
import java.awt.event.*;

public class SelectButton extends JButton implements State {

    private StateManager stateManager;
    private int lastX, lastY;

    public SelectButton(StateManager stateManager) {
        super("Select");
        this.stateManager = stateManager;
        addActionListener(new SelectListener());
    }

    public void mouseDown(int x, int y) {
        stateManager.setSelected(x, y);
        lastX = x;
        lastY = y;
        stateManager.setRepaint();
    }

    public void mouseDrag(int x, int y) {
        int dx = x - lastX;
        int dy = y - lastY;

        stateManager.move(dx, dy);

        lastX = x;
        lastY = y;
        stateManager.setRepaint();
    }

    public void mouseUp(int x, int y) {

    }

    class SelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(SelectButton.this);
        }
    }
}