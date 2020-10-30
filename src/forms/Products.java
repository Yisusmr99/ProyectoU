package forms;
import global.ENV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class Products extends JFrame{
    private JPanel frame;
    private JTextField name;
    private JTextField description;
    private JTextField price;
    private JComboBox comboTipo;
    private JComboBox comboAgencies;
    private JTextField idP;
    private JLabel resultName;
    private JLabel resultDescription;
    private JLabel resultPrice;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JButton agregarButton;
    private JScrollPane table;
    private ArrayList<String> idsTipo = new ArrayList<>();
    private ArrayList<String> idsAgencies = new ArrayList<>();

    private void getProductType(){
        ResultSet rs = ENV.getInstance().db.selectWithTableName("ProductType");
        try{
            while (rs.next()){
                comboTipo.addItem(rs.getString("Description"));
                idsTipo.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }
    private void getAgencies(){
        ResultSet rs = ENV.getInstance().db.selectWithTableName("Agencies");
        try{
            while (rs.next()){
                comboAgencies.addItem(rs.getString("Name")+" - "+rs.getString("Direccion"));
                idsAgencies.add(rs.getString("Id"));
            }
        }catch(Exception e2){ System.out.println(e2);}
    }

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Descripcion");
        model.addColumn("Precio");
        model.addColumn("Id tipo de producto");
        model.addColumn("Id agencia");

        ResultSet rs = ENV.getInstance().db.selectWithTableName("Products");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Price"),
                        rs.getString("ProductTypeId"),
                        rs.getString("AgencyId"),
                });
            }
        }catch(Exception e2){ System.out.println(e2);}
        JTable table1 = new JTable();
        table1.setModel(model);
        table.getViewport ().add (table1);
    }

    public Products(){
        setBounds(0,0,500,400);
        add(frame);
        getProductType();
        getAgencies();
        loadTable();

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Name",name.getText());
                parameters.put("Description",description.getText());
                parameters.put("Price",price.getText());
                parameters.put("ProductTypeId",idsTipo.get(comboTipo.getSelectedIndex()));
                parameters.put("AgencyId",idsAgencies.get(comboAgencies.getSelectedIndex()));
                ENV.getInstance().db.insertWithTableName(parameters, "Products");
                name.setText("");
                description.setText("");
                price.setText("");
                loadTable();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "";
                String responseDescription = "";
                String responsePrice = "";
                ResultSet rs = ENV.getInstance().db.searchOnTableWithId("Products",Integer.parseInt(idP.getText()));
                try{
                    while (rs.next()){
                        responseName = rs.getString("Name");
                        responseDescription = rs.getString("Description");
                        responsePrice = (("Q ")+rs.getString("Price"));
                    }
                }catch(Exception e2){ System.out.println(e2);}
                if (responseName.length() != 0){
                    resultName.setText(responseName);
                    resultDescription.setText(responseDescription);
                    resultPrice.setText(responsePrice);
                }else{
                    resultName.setText("");
                    resultDescription.setText("");
                    resultPrice.setText("");
                    JOptionPane.showMessageDialog(null,"No hay resultados para la busqueda");
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ENV.getInstance().db.deleteOnTableWithId("Products", Integer.parseInt(idP.getText()));
                loadTable();
                idP.setText("");
                resultName.setText("");
                resultDescription.setText("");
                resultPrice.setText("");
            }
        });
    }
}
