package ui;

import automaton.FiniteAutomaton;
import automaton.NFA;
import automaton.io.xml.DefaultXMLAutomatonReader;
import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;
import graph.FAViewer;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Create by: huangcd
 * Date: 2009-12-31
 * Time: 11:44:32
 */
public class Main {
    private JTabbedPane FAViewsTab;
    private JPanel mainPanel;
    private JButton xmlButton;
    private JPanel controlPanel;
    private JPopupMenu popMenu;
    private JMenuItem toDFAItem;
    private ArrayList<FiniteAutomaton> faLists;

    public Main() {
        faLists = new ArrayList<FiniteAutomaton>();
        popMenu = new JPopupMenu();
        toDFAItem = new JMenuItem("to DFA");
        toDFAItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = FAViewsTab.getSelectedIndex();
                addFiniteAutomaton(((NFA) faLists.get(index)).toDFA());
            }
        });
        popMenu.add(toDFAItem);

        FAViewsTab.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    int tabIndex = FAViewsTab.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex != -1) {
                        FAViewsTab.remove(tabIndex);
                        faLists.remove(tabIndex);
                    }
                } else if (e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON3) {
                    int tabIndex = FAViewsTab.indexAtLocation(e.getX(), e.getY());
                    if (tabIndex != -1) {
                        if (faLists.get(tabIndex) instanceof NFA) {
                            popMenu.show(FAViewsTab, e.getX(), e.getY());
                        }
                    }
                }
            }
        });

        xmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();

                chooser.setFileFilter(new FileFilter() {
                    public boolean accept(File f) {
                        return ((f.getName().toLowerCase().endsWith(".xml")) ||
                                (f.isDirectory()));
                    }

                    public String getDescription() {
                        return "*.xml";
                    }
                });

                File selectedFile = null;
                int result = chooser.showOpenDialog(null);
                if (result == 0) {
                    selectedFile = chooser.getSelectedFile();
                }
                openFiniteAutomaton(selectedFile);
            }
        });
    }

    private void addFiniteAutomaton(FiniteAutomaton fa) {
        JComponent component = FAViewer.createViewer(fa, "epsilon");
        FAViewsTab.add(fa.getName(), component);
        FAViewsTab.setSelectedComponent(component);
        faLists.add(fa);
    }

    private void openFiniteAutomaton(File file) {
        DefaultXMLAutomatonReader reader = new DefaultXMLAutomatonReader();
        FiniteAutomaton fa = null;
        try {
            fa = reader.readFiniteAutomaton(file);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        addFiniteAutomaton(fa);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (Exception ignored) {

        }
        JFrame frame = new JFrame("Simple Graph Viewer");
        frame.add(new Main().$$$getRootComponent$$$());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension appSize = new Dimension(900, 800);
        Point location = new Point(
                (screenSize.width - appSize.width) / 2,
                (screenSize.height - appSize.height) / 2);
        frame.setSize(appSize);
        frame.setLocation(location);
        //frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, 0));
        FAViewsTab = new JTabbedPane();
        FAViewsTab.setTabPlacement(2);
        mainPanel.add(FAViewsTab, BorderLayout.CENTER);
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        xmlButton = new JButton();
        xmlButton.setText("open xml automaton");
        controlPanel.add(xmlButton);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
