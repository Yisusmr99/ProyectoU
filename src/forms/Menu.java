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
        setTitle("Jesús Morales 9989-18-19239");
        setBounds(0, 0, 350, 280);
        add(frame);
        tipoDeProductosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductType c = new ProductType();
                c.setLocationRelativeTo(null);
                c.setVisible(true);
            }
        });
        agenciasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Agencies a = new Agencies();
                a.setLocationRelativeTo(null);
                a.setVisible(true);
            }
        });
        productosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Products p = new Products();
                p.setLocationRelativeTo(null);
                p.setVisible(true);
            }
        });
        tipoDeEmpleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeType em = new EmployeType();
                em.setLocationRelativeTo(null);
                em  .setVisible(true);
            }
        });
        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employees em = new Employees();
                em.setLocationRelativeTo(null);
                em  .setVisible(true);
            }
        });
    }
}
