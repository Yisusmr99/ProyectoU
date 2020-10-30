package forms;
import global.ENV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class EmployeType extends JFrame{
    private JPanel frame;
    private JTextField description;
    private JLabel resultDescription;
    private JTextField idE;
    private JButton buscarButton;
    private JButton agregarButton;
    private JScrollPane table;

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Descripcion");

        ResultSet rs = ENV.getInstance().db.selectWithTableName("EmployeType");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Description")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table1 = new JTable();
        table1.setModel(model);
        table.getViewport ().add (table1);
    }

    public EmployeType(){
        setBounds(0,0,500,400);
        add(frame);
        loadTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Description",description.getText());
                ENV.getInstance().db.insertWithTableName(parameters, "EmployeType");
                description.setText("");
                loadTable();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "";
                ResultSet rs = ENV.getInstance().db.searchOnTableWithId("EmployeType",Integer.parseInt(idE.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Description");
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultDescription.setText(responseName);
                }else{
                    resultDescription.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });
    }
}
