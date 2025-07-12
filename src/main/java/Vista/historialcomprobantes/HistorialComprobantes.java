/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista.historialcomprobantes;

import Conexion.CConexion;
import Controlador.VentaControlador;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author david
 */
public class HistorialComprobantes extends javax.swing.JFrame {

    /**
     * Creates new form HistorialComprobantes
     */
    public HistorialComprobantes() {
        initComponents();

        // Simular placeholder
        txtbuscarhistorialC.setText("Buscar cliente o numero...");
        txtbuscarhistorialC.setForeground(Color.GRAY);

        txtbuscarhistorialC.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtbuscarhistorialC.getText().equals("Buscar cliente o numero...")) {
                    txtbuscarhistorialC.setText("");
                    txtbuscarhistorialC.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtbuscarhistorialC.getText().trim().isEmpty()) {
                    txtbuscarhistorialC.setText("Buscar cliente o numero...");
                    txtbuscarhistorialC.setForeground(Color.GRAY);
                }
            }
        });

        aplicarFiltros(); // Carga inicial

         // Filtrar cuando cambia algo
        txtbuscarhistorialC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                aplicarFiltros();
            }
        });

        jComboBox1.addActionListener(e -> aplicarFiltros());
        cboxestadosHC.addActionListener(e -> aplicarFiltros());
        dccalendarioHV.addPropertyChangeListener("date", evt -> aplicarFiltros());

    }
    
    
    private void cargarComprobantes(String filtroTexto, String tipo, String estado, java.util.Date fecha) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"Número", "Tipo", "Cliente", "Fecha", "Hora", "Total", "Estado"});

        try (Connection con = CConexion.getConexion()) {
            StringBuilder sql = new StringBuilder(
                    "SELECT v.id_venta AS numero, v.tipo_comprobante AS tipo, "
                    + "c.nombre_cliente AS cliente, v.fecha_venta AS fecha, "
                    + "TIME(v.fecha_venta) AS hora, v.total, 'Emitido' AS estado "
                    + "FROM ventas v "
                    + "JOIN clientes c ON v.id_cliente = c.id_cliente "
                    + "WHERE 1=1"
            );

            if (filtroTexto != null && !filtroTexto.trim().isEmpty() && !filtroTexto.equals("Buscar cliente o numero...")) {
                sql.append(" AND (c.nombre_cliente LIKE ? OR v.id_venta LIKE ?)");
            }

            if (!"Todos los tipos".equalsIgnoreCase(tipo)) {
                sql.append(" AND v.tipo_comprobante = ?");
            }

            if (!"Todos los estados".equalsIgnoreCase(estado)) {
                // Solo si implementas campo estado en tabla ventas
                if (estado.equalsIgnoreCase("Emitido")) {
                    sql.append(" AND 1=1"); // no hace nada, lo dejas así
                } else if (estado.equalsIgnoreCase("Anulado")) {
                    sql.append(" AND 1=0"); // no muestra nada porque aún no tienes campo "estado"
                }
            }

            if (fecha != null) {
                sql.append(" AND DATE(v.fecha_venta) = ?");
            }

            PreparedStatement ps = con.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (filtroTexto != null && !filtroTexto.trim().isEmpty() && !filtroTexto.equals("Buscar cliente o numero...")) {
                ps.setString(paramIndex++, "%" + filtroTexto + "%");
                ps.setString(paramIndex++, "%" + filtroTexto + "%");
            }

            if (!"Todos los tipos".equalsIgnoreCase(tipo)) {
                ps.setString(paramIndex++, tipo.toLowerCase());
            }

            if (fecha != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fecha.getTime()));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("numero"),
                    rs.getString("tipo"),
                    rs.getString("cliente"),
                    rs.getDate("fecha"),
                    rs.getString("hora"),
                    rs.getDouble("total"),
                    rs.getString("estado")
                };
                modelo.addRow(fila);
            }

            jTable1.setModel(modelo);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar comprobantes: " + e.getMessage());
        }
    }
    
    public void mostrarPDFEnVisor(int idVenta) {
        VentaControlador vc = new VentaControlador();
        byte[] datosPDF = vc.obtenerPDFVenta(idVenta);

        if (datosPDF != null) {
            try {
                // Guardar temporalmente el PDF
                File tempFile = File.createTempFile("comprobante_", ".pdf");
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(datosPDF);
                }

                // Abrir el PDF con el visor predeterminado
                Desktop.getDesktop().open(tempFile);

            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "No se pudo abrir el comprobante.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el comprobante.");
        }
    }


    private void aplicarFiltros() {
        String texto = txtbuscarhistorialC.getText().trim();
        String tipo = (String) jComboBox1.getSelectedItem();
        String estado = (String) cboxestadosHC.getSelectedItem();
        java.util.Date fecha = dccalendarioHV.getDate();
        cargarComprobantes(texto, tipo, estado, fecha);
    }

    private void descargarComprobantePDF() {
        int fila = jTable1.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un comprobante para descargar.");
            return;
        }

        int idVenta = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());

        try {
            CConexion conector = new CConexion();
            Connection con = conector.estableceConexion();

            String sql = "SELECT pdf FROM ventas WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                byte[] pdfBytes = rs.getBytes("pdf");

                if (pdfBytes == null) {
                    JOptionPane.showMessageDialog(this, "Esta venta no tiene un comprobante PDF guardado.");
                    return;
                }

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Guardar comprobante como");
                fileChooser.setSelectedFile(new File("comprobante_" + idVenta + ".pdf"));

                int userSelection = fileChooser.showSaveDialog(this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File archivoDestino = fileChooser.getSelectedFile();
                    try (FileOutputStream fos = new FileOutputStream(archivoDestino)) {
                        fos.write(pdfBytes);
                    }

                    JOptionPane.showMessageDialog(this, "PDF descargado con éxito en:\n" + archivoDestino.getAbsolutePath());
                }

            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la venta en la base de datos.");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al descargar el comprobante.");
        }
    }





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtbuscarhistorialC = new javax.swing.JTextPane();
        jComboBox1 = new javax.swing.JComboBox<>();
        dccalendarioHV = new com.toedter.calendar.JDateChooser();
        cboxestadosHC = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        btndescargarHC = new javax.swing.JButton();
        btnverpdfdeHC = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circuito.png"))); // NOI18N

        jLabel2.setText("ElectroNova S.A.C");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Historial de Comprobantes");

        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Cerrar.png"))); // NOI18N
        jButton1.setText("Cerrar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel1)
                .addGap(123, 123, 123)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(40, 40, 40))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jButton1)))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Comprobantes Emitidos");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Consulte y gestione todos los comprobantes generados");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Comprobantes"));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Número", "Tipo", "Cliente", "Fecha", "Hora", "Total", "Estado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(1).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(15);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(8);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(20);
        }

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Buscar");

        txtbuscarhistorialC.setText("Buscar cliente o numero...");
        jScrollPane2.setViewportView(txtbuscarhistorialC);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los tipos", "Boleta", "Factura" }));

        cboxestadosHC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los estados", "Emitido", "Anulado" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(dccalendarioHV, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(cboxestadosHC, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dccalendarioHV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboxestadosHC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btndescargarHC.setBackground(new java.awt.Color(204, 204, 204));
        btndescargarHC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/archivo.png"))); // NOI18N
        btndescargarHC.setText("Descargar");
        btndescargarHC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndescargarHCActionPerformed(evt);
            }
        });

        btnverpdfdeHC.setBackground(new java.awt.Color(255, 204, 204));
        btnverpdfdeHC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/archivo-pdf.png"))); // NOI18N
        btnverpdfdeHC.setText("Ver");
        btnverpdfdeHC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnverpdfdeHCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btndescargarHC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnverpdfdeHC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(btndescargarHC)
                .addGap(76, 76, 76)
                .addComponent(btnverpdfdeHC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnverpdfdeHCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnverpdfdeHCActionPerformed
        int fila = jTable1.getSelectedRow();
        if (fila != -1) {
            int idVenta = Integer.parseInt(jTable1.getValueAt(fila, 0).toString());
            mostrarPDFEnVisor(idVenta);
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un comprobante primero.");
        }
    

    }//GEN-LAST:event_btnverpdfdeHCActionPerformed

    private void btndescargarHCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndescargarHCActionPerformed
     descargarComprobantePDF();

    }//GEN-LAST:event_btndescargarHCActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistorialComprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistorialComprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistorialComprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistorialComprobantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistorialComprobantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btndescargarHC;
    private javax.swing.JButton btnverpdfdeHC;
    private javax.swing.JComboBox<String> cboxestadosHC;
    private com.toedter.calendar.JDateChooser dccalendarioHV;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane txtbuscarhistorialC;
    // End of variables declaration//GEN-END:variables
}
