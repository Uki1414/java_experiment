import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.LinkedHashSet;
import java.util.Locale;
import javax.swing.*;

// ウインドウを表すクラス
public class MyApplication extends JFrame {

    StateManager stateManager;
    MyCanvas canvas;
    JMenuBar menuBar;

    public MyApplication() {
        super("My Painter");

        // メニューバーの設定
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");

        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    stateManager.mediator().open(f);
                }
            }
        });

        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    stateManager.mediator().save(f);
                }
            }
        });

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        canvas = new MyCanvas();
        canvas.setBackground(Color.white);

        canvas.setFocusable(true);

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

        TextButton textButton = new TextButton(stateManager);
        jp.add(textButton);

        SelectButton selectButton = new SelectButton(stateManager);
        jp.add(selectButton);

        JButton frontButton = new JButton("To Front");
        frontButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.bringToFront();
            }
        });
        jp.add(frontButton);

        JButton backButton = new JButton("To Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.sendToBack();
            }
        });
        jp.add(backButton);

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

        String[] colorNames = {"White", "Black", "Red", "Blue", "Green", "Other Colors..."};

        JComboBox<String> fillCombo = new JComboBox<>(colorNames);
        fillCombo.setSelectedItem("White");
        fillCombo.addActionListener(new ColorComboListener(stateManager, false, this));

        JComboBox<String> lineCombo = new JComboBox<>(colorNames);
        lineCombo.setSelectedItem("Black");
        lineCombo.addActionListener(new ColorComboListener(stateManager, true, this));

        String[] widthOptions = {"1", "3", "5", "7", "10", "15", "20"};
        JComboBox<String> widthCombo = new JComboBox<>(widthOptions);
        widthCombo.setSelectedItem("1");
        widthCombo.addActionListener(new LineWidthListener(stateManager));

        JPanel colorPanel = new JPanel();
        colorPanel.add(new JLabel("Fill:"));
        colorPanel.add(fillCombo);
        colorPanel.add(new JLabel("Line:"));
        colorPanel.add(lineCombo);
        colorPanel.add(new JLabel("Width:"));
        colorPanel.add(widthCombo);

        String[] fontNames = getFontNames();
        JComboBox<String> fontCombo = new JComboBox<>(fontNames);
        fontCombo.setMaximumRowCount(12);
        fontCombo.setSelectedItem(Font.DIALOG);
        fontCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stateManager.setFontName((String)fontCombo.getSelectedItem());
            }
        });

        String[] fontSizeOptions = {"8", "10", "12", "14", "18", "24", "32", "48", "64", "72"};
        JComboBox<String> fontSizeCombo = new JComboBox<>(fontSizeOptions);
        fontSizeCombo.setSelectedItem("12");
        fontSizeCombo.setEditable(true);
        fontSizeCombo.addActionListener(new ActionListener() {
            private int lastValidSize = 12;

            public void actionPerformed(ActionEvent e) {
                Object value = fontSizeCombo.getEditor().getItem();
                try {
                    int size = Integer.parseInt(value.toString().trim());
                    if (size < 1 || size > 300) {
                        throw new NumberFormatException();
                    }
                    lastValidSize = size;
                    stateManager.setFontSize(size);
                    fontSizeCombo.getEditor().setItem(String.valueOf(size));
                } catch (NumberFormatException ex) {
                    Toolkit.getDefaultToolkit().beep();
                    fontSizeCombo.getEditor().setItem(
                            String.valueOf(lastValidSize));
                }
            }
        });

        JCheckBox boldCheck = new JCheckBox("Bold");
        boldCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                stateManager.setBold(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        JCheckBox italicCheck = new JCheckBox("Italic");
        italicCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                stateManager.setItalic(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        JCheckBox underlineCheck = new JCheckBox("Underline");
        underlineCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                stateManager.setUnderline(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        JPanel fontPanel = new JPanel();
        fontPanel.add(new JLabel("Font:"));
        fontPanel.add(fontCombo);
        fontPanel.add(new JLabel("Size:"));
        fontPanel.add(fontSizeCombo);
        fontPanel.add(boldCheck);
        fontPanel.add(italicCheck);
        fontPanel.add(underlineCheck);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(3, 1)); 
        northPanel.add(jp);
        northPanel.add(colorPanel);
        northPanel.add(fontPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(canvas, BorderLayout.CENTER);

        canvas.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                canvas.requestFocusInWindow();
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

        canvas.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    stateManager.deleteSelected();
                }

                boolean isShortcut = e.isControlDown() || e.isMetaDown();

                if (isShortcut && e.getKeyCode() == KeyEvent.VK_C) {
                    stateManager.copy();  // Cmd + C でコピー
                }
                if (isShortcut && e.getKeyCode() == KeyEvent.VK_X) {
                    stateManager.cut();   // Cmd + X でカット（切り取り）
                }
                if (isShortcut && e.getKeyCode() == KeyEvent.VK_V) {
                    stateManager.paste(); // Cmd + V でペースト（貼り付け）
                }
            }
        });

        // WindowEvent リスナを設定
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
        return new Dimension(800, 600);
    }

    private String[] getFontNames() {
        GraphicsEnvironment environment =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        LinkedHashSet<String> availableFonts = new LinkedHashSet<String>();
        for (String name : environment.getAvailableFontFamilyNames(Locale.JAPAN)) {
            availableFonts.add(name);
        }
        for (String name : environment.getAvailableFontFamilyNames()) {
            availableFonts.add(name);
        }

        String[] preferredJapaneseFonts = {
            "Hiragino Sans", "Hiragino Kaku Gothic ProN",
            "Hiragino Mincho ProN", "Hiragino Maru Gothic ProN",
            "YuGothic", "Yu Gothic", "YuMincho", "Yu Mincho",
            "YuKyokasho", "YuKyokasho Yoko", "Osaka",
            "Noto Sans JP", "Noto Sans CJK JP",
            "Noto Serif JP", "Noto Serif CJK JP",
            "Meiryo", "MS Gothic", "MS Mincho"
        };

        LinkedHashSet<String> orderedFonts = new LinkedHashSet<String>();
        for (String preferred : preferredJapaneseFonts) {
            for (String available : availableFonts) {
                if (preferred.equalsIgnoreCase(available)) {
                    orderedFonts.add(available);
                    break;
                }
            }
        }
        orderedFonts.addAll(availableFonts);
        return orderedFonts.toArray(new String[orderedFonts.size()]);
    }
    
    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.pack();
        app.setVisible(true);
    }
}

class ColorComboListener implements ActionListener {
    private StateManager stateManager;
    private boolean isLine;
    private JFrame parentFrame;

    public ColorComboListener(StateManager stateManager, boolean isLine, JFrame parent) {
        this.stateManager = stateManager;
        this.isLine = isLine;
        this.parentFrame = parent;
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String selectedName = (String) cb.getSelectedItem();
        
        Color color = null;

        if (selectedName.equals("White")) color = Color.white;
        else if (selectedName.equals("Black")) color = Color.black;
        else if (selectedName.equals("Red")) color = Color.red;
        else if (selectedName.equals("Blue")) color = Color.blue;
        else if (selectedName.equals("Green")) color = Color.green;
        else if (selectedName.equals("Other Colors...")) {
            String title = isLine ? "枠線の色を選択" : "塗りつぶしの色を選択";
            color = JColorChooser.showDialog(parentFrame, title, Color.black);
            
            if (color == null) {
                return; 
            }
        }

        if (color != null) {
            if (isLine) {
                stateManager.setLineColor(color);
            } else {
                stateManager.setFillColor(color);
            }
        }
    }
}

class LineWidthListener implements ActionListener {
    private StateManager stateManager;

    public LineWidthListener(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox) e.getSource();
        String selectedStr = (String) cb.getSelectedItem();
        int width = Integer.parseInt(selectedStr);
        stateManager.setLineWidth(width);
    }
}
