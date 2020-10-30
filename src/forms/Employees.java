package forms;
import global.ENV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Employees extends JFrame{
    private JPanel frame;
    private JTextField name;
    private JTextField surname;
    private JComboBox comboAgencies;
    private JComboBox comboEmployees;
    private JTextField idE;
    private JLabel resultName;
    private JLabel resultSurname;
    private JButton buscarButton;
    private JButton agregarButton;
    private JButton eliminarButton;
    private JScrollPane table;
    private ArrayList<String> idsEmployees = new ArrayList<>();
    private ArrayList<String> idsAgencies = new ArrayList<>();

    private void getAgencies(){
        ResultSet rs = ENV.getInstance().db.selectWithTableName("Agencies");
        try{
            while (rs.next()){
                comboAgencies.addItem(rs.getString("Name")+" - "+rs.getString("Direccion"));
                idsAgencies.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }
    private void getEmployees(){
        ResultSet rs = ENV.getInstance().db.selectWithTableName("EmployeType");
        try{
            while (rs.next()){
                comboEmployees.addItem(rs.getString("Description"));
                idsEmployees.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }
    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Id tipo de empleado");
        model.addColumn("Id agencia");

        ResultSet rs = ENV.getInstance().db.selectWithTableName("Employees");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id"),
                        rs.getString("Name"),
                        rs.getString("Surname"),
                        rs.getString("EmployeTypeId"),
                        rs.getString("AgencyId"),
                });
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table1 = new JTable();
        table1.setModel(model);
        table.getViewport ().add (table1);
    }


    public Employees(){
        setBounds(0,0,500,400);
        add(frame);
        getEmployees();
        getAgencies();
        loadTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",name.getText());
                parameters.put("Surname",surname.getText());
                parameters.put("EmployeTypeId",idsEmployees.get(comboEmployees.getSelectedIndex()));
                parameters.put("AgencyId",idsAgencies.get(comboAgencies.getSelectedIndex()));
                ENV.getInstance().db.insertWithTableName(parameters, "Employees");
                name.setText("");
                surname.setText("");
                loadTable();
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "";
                String responseSurname = "";
                ResultSet rs = ENV.getInstance().db.searchOnTableWithId("Employees",Integer.parseInt(idE.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                        responseSurname = rs.getString("Surname");
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultName.setText(responseName);
                    resultSurname.setText(responseSurname);
                }else{
                    resultName.setText("");
                    resultSurname.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ENV.getInstance().db.deleteOnTableWithId("Employees", Integer.parseInt(idE.getText()));
                loadTable();
                idE.setText("");
                resultName.setText("");
                resultSurname.setText("");
            }
        });
    }
}
