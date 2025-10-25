package PQT_Form;
//librerias

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//importar la conexion
import PQT_Clases.CConexion;

public class FCliente extends javax.swing.JInternalFrame {

    CConexion con = new CConexion();
    Connection cn = con.getConexion();

    public FCliente() {
        initComponents();
        mostrarCompradores();

    }

    // Metodo Nuevo Comprador (adaptado a los 8 campos del diseño);
    public void nuevoComprador() {
        // Limpia todos los campos de entrada
        txt_id.setText("");
        txt_nom.setText("");
        txt_ape.setText("");
        txt_dir.setText("");
        txt_dis.setText("");
        txt_pro.setText("");
        rdb_m.setSelected(false);
        rdb_f.setSelected(false);
        txt_buscaEscrito.setText("");
        spn_eda.setValue(0);

        txt_id.enable(true);
        txt_id.requestFocus();
    }

    // Metodo Guardar Comprador
    public void guardarComprador() {

        try {
            // 1. CONSULTA DE INSERCIÓN ADAPTADA: 8 campos en la tabla 'comprador'
            PreparedStatement sql = cn.prepareStatement(
            "INSERT INTO cliente (Id_cliente, Ape_cli, Nom_cli, Dir_cli, Dis_cli, Pro_cli, Sex_cli, Eda_cli) " +
            "VALUES (?,?,?,?,?,?,?,?);");
            // Mapeo de campos del formulario a los 8 parámetros SQL
            sql.setString(1, txt_id.getText());
            sql.setString(2, txt_ape.getText());
            sql.setString(3, txt_nom.getText());
            sql.setString(4, txt_dir.getText());
            sql.setString(5, txt_dis.getText());
            sql.setString(6, txt_pro.getText());
            String sexo = "";
            if (rdb_m.isSelected()) {
                sexo = "M";
            } else if (rdb_f.isSelected()) {
                sexo = "F";
            };
            sql.setString(7, sexo);
            sql.setInt(8, Integer.parseInt(spn_eda.getValue().toString()));
            sql.executeUpdate();
            JOptionPane.showMessageDialog(null, "Comprador Guardado", "Mensaje: ", 0);
            mostrarCompradores();
            nuevoComprador();
        } catch (Exception e) {
            System.out.println("Error al guardar el comprador: " + e.getMessage());
        }
    }

    // Metodo Eliminar
    public void eliminarComprador() {
        try {
            // 2. CONSULTA DE ELIMINACIÓN ADAPTADA: Elimina de la tabla 'comprador'
            PreparedStatement sql = cn.prepareStatement(
            "DELETE FROM cliente WHERE Id_cliente = '" + txt_id.getText() + "'");
            int resultado = sql.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Datos Eliminados...");
                mostrarCompradores();
                nuevoComprador();
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar: " + e);
        }
    }

    // Metodo Actualizar
    public void actualizarComprador() {
        String sexo = "";
        if (rdb_m.isSelected()) {
            sexo = "M";
        } else if (rdb_f.isSelected()) {
            sexo = "F";
        }
        try {
            // 3. CONSULTA DE ACTUALIZACIÓN ADAPTADA: 8 campos en la tabla 'comprador'
            PreparedStatement sql = cn.prepareStatement(
                    "UPDATE comprador SET "
                    + "Ape_cli = '" + txt_ape.getText() + "', "
                    + "Nom_cli = '" + txt_nom.getText() + "', "
                    + "Dir_cli = '" + txt_dir.getText() + "', "
                    + "Dis_cli = '" + txt_dis.getText() + "', "
                    + "Pro_cli = '" + txt_pro.getText() + "', "
                    + "Sex_cli = '" + sexo + "', "
                    + "Eda_cli = " + spn_eda.getValue() + " "
                    + "WHERE Id_cliente = '" + txt_id.getText() + "'"
            );
            int resultado = sql.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Datos Actualizados...");
                mostrarCompradores();
                nuevoComprador();
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar: " + e);
        }
    }

    // Metodo Mostrar Compradores
    public void mostrarCompradores() {
        DefaultTableModel modeloTabla = new DefaultTableModel();

        // 4. Nombres de las Columnas de la Tabla (8 columnas)
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Apellido"); // Ape_cli
        modeloTabla.addColumn("Nombre");   // Nom_cli
        modeloTabla.addColumn("Dirección");// Dir_cli
        modeloTabla.addColumn("Distrito"); // Dis_cli
        modeloTabla.addColumn("Provincia");// Pro_cli
        modeloTabla.addColumn("Sexo");     // Sex_cli
        modeloTabla.addColumn("Edad");     // Eda_cli

        tbl_Clientes.setModel(modeloTabla);

        // 5. Sentencia SQL: 'comprador'
        String sql = "SELECT * from cliente";

        // Arreglo de tipo String para 8 campos
        String datos[] = new String[8];

        Statement st;
        try {
            st = cn.createStatement();
            ResultSet res = st.executeQuery(sql);

            while (res.next()) {
                datos[0] = res.getString(1); // Id_cliente
                datos[1] = res.getString(2); // Ape_cli
                datos[2] = res.getString(3); // Nom_cli
                datos[3] = res.getString(4); // Dir_cli
                datos[4] = res.getString(5); // Dis_cli
                datos[5] = res.getString(6); // Pro_cli
                datos[6] = res.getString(7); // Sex_cli
                datos[7] = res.getString(8); // Eda_cli

                modeloTabla.addRow(datos);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar compradores: " + e.getMessage()); // Imprime el error real
            e.printStackTrace();
        }

    }

    // para buscar compradores
    private void buscarCompradores() {
        String criterio = txt_buscaEscrito.getText().trim(); // Este cambo deberia buscar el crliente que se escriba

        DefaultTableModel modeloTabla = new DefaultTableModel();
        // Nombres de las Columnas (8 columnas)
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Apellido");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Distrito");
        modeloTabla.addColumn("Provincia");
        modeloTabla.addColumn("Sexo");
        modeloTabla.addColumn("Edad");

        tbl_Clientes.setModel(modeloTabla);

        try {
            // 6. CONSULTA DE BÚSQUEDA ADAPTADA: Buscar por ID, Nombre o Apellido/Dirección
            String sql = "SELECT * FROM cliente WHERE "
                    + "Id_cliente LIKE ? OR "
                    + "Nom_cli LIKE ? OR "
                    + "Ape_cli LIKE ? OR "
                    + "Dir_cli LIKE ?";

            PreparedStatement st = cn.prepareStatement(sql);
            String parametro = "%" + criterio + "%";
            st.setString(1, parametro);
            st.setString(2, parametro);
            st.setString(3, parametro);
            st.setString(4, parametro);

            ResultSet res = st.executeQuery();
            String datos[] = new String[8]; // 8 campos

            while (res.next()) {
                datos[0] = res.getString(1);
                datos[1] = res.getString(2);
                datos[2] = res.getString(3);
                datos[3] = res.getString(4);
                datos[4] = res.getString(5);
                datos[5] = res.getString(6);
                datos[6] = res.getString(7);
                datos[7] = res.getString(8);

                modeloTabla.addRow(datos);
            }

        } catch (Exception e) {
            System.out.println("Error en búsqueda: " + e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_id = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_nom = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_ape = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_dir = new javax.swing.JTextField();
        spn_eda = new javax.swing.JSpinner();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_dis = new javax.swing.JTextField();
        txt_pro = new javax.swing.JTextField();
        rdb_m = new javax.swing.JRadioButton();
        rdb_f = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btn_nuevo = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Clientes = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        txt_buscaEscrito = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setName(""); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GESTIÓN DE COMPRADOR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 18))); // NOI18N

        jLabel1.setText("ID");

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jLabel3.setText("Apellidos:");

        jLabel4.setText("Dirección:");

        jLabel5.setText("Provincia:");

        jLabel6.setText("Sexo:");

        jLabel7.setText("Edad:");

        jLabel8.setText("Distrito:");

        rdb_m.setText("M");

        rdb_f.setText("F");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(txt_nom, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(txt_ape)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dir))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_pro)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7))
                            .addComponent(spn_eda, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                            .addComponent(jLabel8)
                            .addComponent(txt_dis)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdb_m)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rdb_f)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_dis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_ape, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rdb_m)
                    .addComponent(rdb_f))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_dir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spn_eda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/new.png"))); // NOI18N
        btn_nuevo.setText("Nuevo");
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/eli.png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/act.png"))); // NOI18N
        btn_actualizar.setText("Actualizar");
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });

        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PQT_Src/save.png"))); // NOI18N
        btn_guardar.setText("Guardar");
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_nuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(btn_actualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_guardar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_nuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_actualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        tbl_Clientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "null", "Title 6", "Title 7"
            }
        ));
        tbl_Clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Clientes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txt_buscaEscrito.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_buscaEscritoKeyTyped(evt);
            }
        });

        jLabel9.setText("Buscar Cliente ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(txt_buscaEscrito, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_buscaEscrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        nuevoComprador();
        // Habilita el botón Guardar para permitir la inserción de un nuevo registro
        btn_guardar.setEnabled(true);
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        guardarComprador(); //Llama al nuevo método que es la liena 17
    }//GEN-LAST:event_btn_guardarActionPerformed


    private void tbl_ClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ClientesMouseClicked
        int fila = tbl_Clientes.getSelectedRow();

        // Si se hace clic en una fila válida (fila >= 0)
        if (fila >= 0) {
            // Carga los 8 campos de la fila seleccionada a los componentes de entrada
            this.txt_id.setText(this.tbl_Clientes.getValueAt(fila, 0).toString()); // ID
            this.txt_ape.setText(this.tbl_Clientes.getValueAt(fila, 1).toString()); // Apellido (Ape_cli)
            this.txt_nom.setText(this.tbl_Clientes.getValueAt(fila, 2).toString()); // Nombre (Nom_cli)
            this.txt_dir.setText(this.tbl_Clientes.getValueAt(fila, 3).toString()); // Dirección (Dir_cli)
            this.txt_dis.setText(this.tbl_Clientes.getValueAt(fila, 4).toString()); // Distrito (Dis_cli)
            this.txt_pro.setText(this.tbl_Clientes.getValueAt(fila, 5).toString()); // Provincia (Pro_cli)

            // Manejo de los RadioButtons para Sexo (columna 7 -> índice 6)
            String sexo_cli = this.tbl_Clientes.getValueAt(fila, 6).toString();
            if (sexo_cli.equalsIgnoreCase("M")) {
                rdb_m.setSelected(true);
                rdb_f.setSelected(false);
            } else if (sexo_cli.equalsIgnoreCase("F")) {
                rdb_m.setSelected(false);
                rdb_f.setSelected(true);
            } else {
                rdb_m.setSelected(false); // Por si hay datos nulos o inesperados
                rdb_f.setSelected(false);
            }

            // Carga el valor del Spinner (Edad) (columna 8 -> índice 7)
            // Usar Integer.valueOf() porque es un JSpinner
            this.spn_eda.setValue(Integer.valueOf(this.tbl_Clientes.getValueAt(fila, 7).toString()));

            // Una vez cargados los datos, no se permite cambiar el ID ni Guardar (sólo Actualizar/Eliminar)
            txt_id.setEnabled(false);
            btn_guardar.setEnabled(false);
        }
    }//GEN-LAST:event_tbl_ClientesMouseClicked

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        actualizarComprador();
        // Vuelve a habilitar el botón guardar después de la operación (por si se va a Nuevo)
        btn_guardar.setEnabled(true);
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        eliminarComprador();
        // Vuelve a habilitar el botón guardar después de la operación (por si se va a Nuevo)
        btn_guardar.setEnabled(true);
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void txt_buscaEscritoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_buscaEscritoKeyTyped
        buscarCompradores();
    }//GEN-LAST:event_txt_buscaEscritoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdb_f;
    private javax.swing.JRadioButton rdb_m;
    private javax.swing.JSpinner spn_eda;
    private javax.swing.JTable tbl_Clientes;
    private javax.swing.JTextField txt_ape;
    private javax.swing.JTextField txt_buscaEscrito;
    private javax.swing.JTextField txt_dir;
    private javax.swing.JTextField txt_dis;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nom;
    private javax.swing.JTextField txt_pro;
    // End of variables declaration//GEN-END:variables

}
