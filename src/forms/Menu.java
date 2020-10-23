package forms;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel frame;
    private JButton tipoDeProductosButton;
    private JButton productosButton;
    private JButton tipoDeEmpleadosButton;
    private JButton empleadosButton;
    private JButton agenciasButton;

    public Menu(){
        setBounds(0, 0, 350, 250);
        add(frame);
        tipoDeProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateProductType c = new CreateProductType();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
            }
        });
    }
}
