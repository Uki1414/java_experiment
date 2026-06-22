
import com.sun.net.httpserver.Headers;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// ウインドウを表すクラス
public class MyApplication extends JFrame {

    StateManager stateManager;
    MyCanvas canvas;

    public MyApplication() {
        super("My Painter");

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);

        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout());

        stateManager = new StateManager(canvas);

        RectButton rectButton = new RectButton(stateManager);
        jp.add(rectButton);

        OvalButton ovalButton = new OvalButton(stateManager);
        jp.add(ovalButton);

        RoundRectButton roundRectButton = new RoundRectButton(stateManager);
        jp.add(roundRectButton);

        HendecagonalButton hendecagonalButton = new HendecagonalButton(stateManager);
        jp.add(hendecagonalButton);

        JCheckBox dashCheck = new JCheckBox("Dashed");
        dashCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
                stateManager.setDashed(isSelected);
            }
        });
        jp.add(dashCheck);

        JCheckBox shadowCheck = new JCheckBox("Shadow");
        shadowCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                boolean isSelected = (e.getStateChange() == ItemEvent.SELECTED);
                stateManager.setShadow(isSelected);
            }
        });
        jp.add(shadowCheck);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jp, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                stateManager.mouseDown(e.getX(), e.getY());
            }

            public void mouseReleased(MouseEvent e) {
                stateManager.mouseUp(e.getX(), e.getY());
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                stateManager.mouseDrag(e.getX(), e.getY());
            }
        });

        // WindowEvent リスナを設定(無名クラスを利用している)
        this.addWindowListener(
                new WindowAdapter() {
            // ウインドウが閉じたら終了する処理
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        }
        );
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 400);
    }
    
    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}
