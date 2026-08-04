import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.Enumeration;
import java.util.Vector;

public class SelectButton extends JButton implements State {

    private StateManager stateManager;
    private int lastX, lastY;
    private int startX, startY;
    private MyRectangle selectionRect = null;
    private MyDrawing resizingDrawing = null;
    private int resizeMode = -1;
    private int originalX, originalY, originalW, originalH;

    public SelectButton(StateManager stateManager) {
        super("Select");
        this.stateManager = stateManager;
        addActionListener(new SelectListener());
    }

    public void mouseDown(int x, int y) {
        resizingDrawing = null;
        resizeMode = -1;

        for (MyDrawing d : stateManager.getSelectedDrawings()) {
            int mode = d.getResizeMode(x, y);
            if (mode != -1) {
                resizingDrawing = d;
                resizeMode = mode;
                originalX = d.getX();
                originalY = d.getY();
                originalW = d.getW();
                originalH = d.getH();
                lastX = x;
                lastY = y;
                return;
            }
        }

        boolean onShape = false;
        for (MyDrawing d : stateManager.getSelectedDrawings()) {
            if (d.getSelected() && d.contains(x, y)) {
                onShape = true;
                break;
            }
        }

        if (!onShape) {
            stateManager.setSelected(x, y);
        }

        if (stateManager.getSelectedDrawings().isEmpty()) {
            startX = x;
            startY = y;
            selectionRect = new MyRectangle(x, y, 0, 0, Color.black, new Color(0,0,0,0), 1);
            selectionRect.setDashed(true); // 破線を設定
            stateManager.mediator().addDrawing(selectionRect); // Mediatorを直接使って図形を追加
        }
        lastX = x;
        lastY = y;
        stateManager.setRepaint();
    }

    public void mouseDrag(int x, int y) {
        if (resizingDrawing != null) {
            int gx = originalX;
            int gy = originalY;
            int gw = originalW;
            int gh = originalH;

            switch (resizeMode) {
                case 0:
                    gx = x;
                    gy = y;
                    gw = originalX + originalW - x;
                    gh = originalY + originalH - y;
                    break;
                case 1:
                    gy = y;
                    gh = originalY + originalH - y;
                    break;
                case 2:
                    gy = y;
                    gw = x - originalX;
                    gh = originalY + originalH - y;
                    break;
                case 3:
                    gw = x - originalX;
                    break;
                case 4:
                    gw = x - originalX;
                    gh = y - originalY;
                    break;
                case 5:
                    gh = y - originalY;
                    break;
                case 6:
                    gx = x;
                    gw = originalX + originalW - x;
                    gh = y - originalY;
                    break;
                case 7:
                    gx = x;
                    gw = originalX + originalW - x;
                    break;
            }

            if (gw < 0) {
                gx += gw;
                gw = -gw;
            }
            if (gh < 0) {
                gy += gh;
                gh = -gh;
            }
            resizingDrawing.resize(gx, gy, gw, gh);
        }else if (selectionRect != null){
            int w = Math.abs(x - startX);
            int h = Math.abs(y - startY);
            int newX = Math.min(x, startX);
            int newY = Math.min(y, startY);

            selectionRect.setLocation(newX, newY);
            selectionRect.setSize(w, h);

            stateManager.clearSelection();
            Vector<MyDrawing> selected = stateManager.getSelectedDrawings();

            java.awt.Rectangle selectBounds = new java.awt.Rectangle(newX, newY, w, h);

            java.util.Enumeration<MyDrawing> e = stateManager.drawingsElements();
            while (e.hasMoreElements()) {
                MyDrawing d = e.nextElement();
                if (d != selectionRect) { 
                    if (d.intersects(selectBounds)) {
                        selected.add(d); 
                        d.setSelected(true); 
                    }
                }
            }
        }else{
            int dx = x - lastX;
            int dy = y - lastY;

            stateManager.move(dx, dy);

            lastX = x;
            lastY = y;
        }
        stateManager.setRepaint();
    }

    public void mouseUp(int x, int y) {
        resizingDrawing = null;
        resizeMode = -1;
        if (selectionRect != null) {
            stateManager.removeDrawing(selectionRect);
            selectionRect = null;
            stateManager.setRepaint();
        }
    }

    class SelectListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            stateManager.setState(SelectButton.this);
        }
    }
}
