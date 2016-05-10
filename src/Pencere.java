import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public final class Pencere {

    private static final JFrame frame = new JFrame();
    private static final JTextArea textArea = new JTextArea();

    protected static final void baslat() {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                frame.setResizable(false);
                frame.setTitle("Yaz Lab II - 1. Proje");
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                frame.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                frame.setBounds(681, 364, 687, 365);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(null);
                textArea.setFont(new Font("Segoe UI", Font.BOLD, 12));
                textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                textArea.setBackground(SystemColor.info);
                textArea.setAlignmentY(Component.TOP_ALIGNMENT);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                JScrollPane scroll = new JScrollPane(textArea);
                scroll.setBounds(10, 10, 660, 315);
                frame.getContentPane().add(scroll);
                frame.setVisible(true);
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.out.println(ex.getMessage());
            }
        });
    }

    protected static final void ekranaYaz(String metin) {
        textArea.append(metin + "\n");
    }
}
