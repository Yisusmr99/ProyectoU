package forms;
import helpers.ENV;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.HashMap;

public class CreateProductType extends JFrame{
    private JTextField description;
    private JButton agregarButton;
    private JPanel frame;
    private JTextField idP;
    private JButton buscarButton;
    private JTable table1;
    private JLabel resultDescription;

    private void loadTable(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Descripcion");

        ResultSet rs = ENV.getInstance().db.selectWithTableName("ProductType");
        try{
            while (rs.next()){
                model.addRow(new Object[]{rs.getString("Id")
                        , rs.getString("Description")});
            }
        }catch(Exception e2){ System.out.println(e2);}
        table1.setModel(model);
    }

    public CreateProductType(){
        setBounds(0,0,500,400);
        add(frame);
        loadTable();
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Description",description.getText());
                ENV.getInstance().db.insertWithTableName(parameters, "ProductType");
                description.setText("");
                loadTable();
            }
        });

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String responseName = "";
                ResultSet rs = ENV.getInstance().db.searchOnTableWithId("ProductType",Integer.parseInt(idP.getText()));
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
