/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vista.usuarios;

import Controlador.UsuarioControlador;
import Modelo.Usuario;
import Util.RegistroAccesoUtil;

import javax.swing.JOptionPane;

/**
 *
 * @author david
 */
public class GestionUsuariosEditar extends javax.swing.JFrame {

    private Usuario usuario;
    private UsuarioControlador usuarioControlador = new UsuarioControlador();
    private GestionarUsuarioInterfaz ventanaPrincipal;
    private int idAdmin;
    private String nombreAdmin;

    // ✅ Constructor principal con parámetros
    public GestionUsuariosEditar(Usuario usuario, GestionarUsuarioInterfaz ventanaPrincipal, int idAdmin, String nombreAdmin) {
        this.usuario = usuario;
        this.ventanaPrincipal = ventanaPrincipal;
        this.idAdmin = idAdmin;
        this.nombreAdmin = nombreAdmin;
        initComponents();
        setLocationRelativeTo(null);

        txteditarnombreUsuario.setText(usuario.getNombreUsuario());
        txteditarusario.setText(usuario.getUsuario());
        txteditarContra.setText(usuario.getContrasena());
        cboxeditarRolU.setSelectedItem(usuario.getRol());
    }

    // ✅ Constructor vacío agregado para evitar error de compilación
    public GestionUsuariosEditar() {
        initComponents();
        setLocationRelativeTo(null);
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
        btncerrarEU = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txteditarnombreUsuario = new javax.swing.JTextField();
        txteditarusario = new javax.swing.JTextField();
        cboxeditarRolU = new javax.swing.JComboBox<>();
        btnactualizaUsuarios = new javax.swing.JButton();
        btncancelarEU = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txteditarContra = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 102, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circuito.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Editar Usuario");

        btncerrarEU.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btncerrarEU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/boton-salir-a-la-aplicacion.png"))); // NOI18N
        btncerrarEU.setText("Cerrar");
        btncerrarEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrarEUActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(129, 129, 129)
                .addComponent(jLabel2)
                .addGap(89, 89, 89)
                .addComponent(btncerrarEU)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btncerrarEU))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Editar Usuario");

        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Complete los datos del usuario  ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nombre Completo :");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Usuario :");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Rol :");

        cboxeditarRolU.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Gerente", "Vendedor" }));

        btnactualizaUsuarios.setBackground(new java.awt.Color(204, 204, 204));
        btnactualizaUsuarios.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnactualizaUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/procesamiento-de-datos.png"))); // NOI18N
        btnactualizaUsuarios.setText("Actualizar");
        btnactualizaUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizaUsuariosActionPerformed(evt);
            }
        });

        btncancelarEU.setBackground(new java.awt.Color(204, 255, 255));
        btncancelarEU.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btncancelarEU.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancelar.png"))); // NOI18N
        btncancelarEU.setText("Limpiar");
        btncancelarEU.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelarEUActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setText("Contraseña :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(60, 60, 60)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txteditarusario, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                                    .addComponent(txteditarnombreUsuario)
                                    .addComponent(cboxeditarRolU, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txteditarContra)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(btnactualizaUsuarios)
                        .addGap(65, 65, 65)
                        .addComponent(btncancelarEU)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txteditarnombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txteditarusario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txteditarContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboxeditarRolU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnactualizaUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btncancelarEU, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncerrarEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrarEUActionPerformed
       if (ventanaPrincipal != null) {
            ventanaPrincipal.cargarUsuarios();
            ventanaPrincipal.setVisible(true);
        }
        this.dispose();
    }//GEN-LAST:event_btncerrarEUActionPerformed

    private void btnactualizaUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizaUsuariosActionPerformed
        String nombreUsuario = txteditarnombreUsuario.getText().trim();
        String usuarioE = txteditarusario.getText().trim();
        String contrasena = txteditarContra.getText().trim();
        String rol = cboxeditarRolU.getSelectedItem().toString().trim();

        usuario.setNombreUsuario(nombreUsuario);
        usuario.setUsuario(usuarioE);
        usuario.setContrasena(contrasena);
        usuario.setRol(rol);

        boolean actualizado = usuarioControlador.actualizarUsuario(usuario);

        if (actualizado) {
            RegistroAccesoUtil.registrarAcceso(
                    idAdmin,
                    nombreAdmin,
                    "Gestión de Usuarios",
                    "Editó al usuario: " + usuario.getUsuario()
            );
            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
            ventanaPrincipal.cargarUsuarios();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar usuario. Verifica los datos.");
        }
    }//GEN-LAST:event_btnactualizaUsuariosActionPerformed

    private void btncancelarEUActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelarEUActionPerformed
        txteditarnombreUsuario.setText("");
        txteditarusario.setText("");
        txteditarContra.setText("");
        cboxeditarRolU.setSelectedIndex(0);
    }//GEN-LAST:event_btncancelarEUActionPerformed

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
            java.util.logging.Logger.getLogger(GestionUsuariosEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GestionUsuariosEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GestionUsuariosEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GestionUsuariosEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GestionUsuariosEditar().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizaUsuarios;
    private javax.swing.JButton btncancelarEU;
    private javax.swing.JButton btncerrarEU;
    private javax.swing.JComboBox<String> cboxeditarRolU;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txteditarContra;
    private javax.swing.JTextField txteditarnombreUsuario;
    private javax.swing.JTextField txteditarusario;
    // End of variables declaration//GEN-END:variables
}
