package forms;
import global.ENV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class Agencies extends JFrame{
    private JTextField idA;
    private JButton borrarButton;
    private JButton buscarButton1;
    private JTextField direccion;
    private JTextField country;
    private JPanel frame;
    private JButton agregarButton;
    private JTable table1;
    private JButton buscarNameButton;
    private JTextField name;
    private JTextField nameSearch;
    private JTable table2;
    private JLabel resultName;
    private JLabel resultCountry;
    private JScrollPane table;

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Direccion");
        model.addColumn("Ciudad");

        ResultSet rs = ENV.getInstance().db.selectWithTableName("Agencies");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id"),
                        rs.getString("Name"),
                        rs.getString("Direccion"),
                        rs.getString("Country"),
                });
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table1 = new JTable();
        table1.setModel(model);
        table.getViewport ().add (table1);
    }

    public Agencies(){
        setBounds(0,0,500,400);
        add(frame);
        loadTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",name.getText());
                parameters.put("Direccion",direccion.getText());
                parameters.put("Country",country.getText());
                ENV.getInstance().db.insertWithTableName(parameters, "Agencies");
                name.setText("");
                direccion.setText("");
                country.setText("");
                loadTable();
            }
        });

        buscarNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Id");
                model.addColumn("Name");
                model.addColumn("Direccion");
                model.addColumn("Country");
                table2.setModel(model);
                ResultSet rs = ENV.getInstance().db.searchOnTableWithName("Agencies", nameSearch.getText() );
                try{
                    while (rs.next()){
                        model.addRow(new Object[]{rs.getString("Id"),
                                rs.getString("Name"),
                                rs.getString("Direccion"),
                                rs.getString("Country"),
                        });
                    }
                }catch(Exception e2){ System.out.println(e2);}
                table2.setModel(model);
                nameSearch.setText("");
            }
        });
        buscarButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "";
                String responseCountry = "";
                ResultSet rs = ENV.getInstance().db.searchOnTableWithId("Agencies",Integer.parseInt(idA.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                        responseCountry = rs.getString("Country");
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultName.setText(responseName);
                    resultCountry.setText(responseCountry);
                }else{
                    resultName.setText("");
                    resultCountry.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ENV.getInstance().db.deleteOnTableWithId("Agencies", Integer.parseInt(idA.getText()));
                loadTable();
                idA.setText("");
                resultName.setText("");
                resultCountry.setText("");
            }
        });

    }
}
