/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dokumen;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
/**
 *
 * @author USER
 */
public class diagnosis extends javax.swing.JFrame {

    String user = "root";
    String pwd= "";
    String url="jdbc:mysql://localhost/rekam_medis";
    Boolean isi= true;
    String sql;
    Statement s;
    ResultSet r;
    Connection con;
    String[]data;
    DefaultTableModel model;
    
    public diagnosis() {
        initComponents();
        setLocationRelativeTo(null);
        data = new String[5];
        con = new Koneksi().getConnect();
        model = (DefaultTableModel)jTable1.getModel();
        loadData();
    }
    public void loadData(){
        model.getDataVector().removeAllElements();
        sql = "select * from diagnosa";
        try{
            s = con.createStatement();
            r  = s.executeQuery(sql);
            while(r.next()){
                data [0]=r.getString("kd_diag");
                data [1]=r.getString("nm_pasien");
                data [2]=r.getString("nm_penyakit");
                data [3]=r.getString("nm_dokter");
                data [4]=r.getString("tgl_diag");
                model.addRow(data);
            }
            s.close();
            r.close();
        }catch(Exception e){
            System.out.println("error"+e.getMessage());
        }
    }
    void aktif(){
        txtpenyakit.setEnabled(true);
        txtTgl.setEnabled(true);
        txtDokter.setEnabled(true);
        txtPasien.setEnabled(true);
        cbDokter.setEnabled(true);
        cbPasien.setEnabled(true);
    }
    void nonaktif(){
        txtKode.setEnabled(false);
        txtpenyakit.setEnabled(false);
        txtTgl.setEnabled(false);
        txtDokter.setEnabled(false);
        txtPasien.setEnabled(false);
        cbDokter.setEnabled(false);
        cbPasien.setEnabled(false);
        btnBatal.setEnabled(false);
        btnTambah.setEnabled(true);
        btnSimpan.setEnabled(false);
        btnKeluar.setEnabled(true);
    }
    void bersih(){
        txtKode.setText("");
        txtpenyakit.setText("");
        txtTgl.setText("");
    }
    void isiKode(){
        try{
            Connection conn= DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql= "Select * from daftar";
            ResultSet rs= st.executeQuery(sql);
            while(rs.next()){
                cbPasien.addItem(rs.getString("kd_user"));
                cbDokter.addItem(rs.getString("kd_user"));
            }
        }catch (SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }
    void otomatis(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement (ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "Select right (kd_diag,3)+1 from diagnosa";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next ()) {
                rs.last();
                String kode = rs.getString(1);
                kode = kode.substring(0, kode.length() - 2);
                while (kode.length()<3) {
                    kode = "0"+kode;
                    txtKode.setText("DG"+kode);            
                }
            } else{
                txtKode.setText("DG001");
            }
        }catch(Exception e){
    }
    }
    /*void simpan(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql = "insert into diagnosa values('"+txtKode.getText()+"','"+txtpenyakit.getText()+"','"+txtTgl.getText()+"','"+txtPasien.getText()+"','"+txtDokter.getText()+"')";
            st.executeUpdate(sql);
            
            JOptionPane.showMessageDialog(this, "Diagnosis berhasil disimpan", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    void update(){
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql = "update diagnosa set nm_diag= '"+txtpenyakit.getText()+"'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Info", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
        formWindowActivated(null);
    }
    private Object[][]getData(){
        Object[][]data1=null;
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from diagnosa");
            rs.last();
            int rowCount=rs.getRow();
            rs.beforeFirst();
            data1=new Object[rowCount][2];
            int no=1;
            while(rs.next()){
                no=no+1;
                data1[no][0]=rs.getString("kd_diag");
                data1[no][1]=rs.getString("nm_penyakit");
            }
        }catch(SQLException e){
            System.out.println("Koneksi Gagal"+e.toString());
        }
        return data1;
    }*/
    void masukgrid(){
        try{
            Connection conn =DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement)conn.createStatement();
            String sql= "Insert into sementara values('"+cbDokter.getSelectedItem()+"','"+txtDokter.getText()+"')";
            st.execute(sql);
        }
        catch(SQLException e){
            System.out.println("Koneksi Gagal Masuk Grid"+e.toString());
        }
    }
    void hapusgrid(){
        try{
            Connection conn =DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            
            String sql= "delete from sementara";
            st.executeUpdate(sql);
        }
        catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }
    /*void tampil(){
        String[] columnNames = {"Kode Diagnosis", "Penyakit"};
        JTable table= new JTable(getData(), columnNames);
        table.setEnabled(false);
        jScrollPane1.setViewportView(table);
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTgl = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtpenyakit = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        btnKeluar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtDokter = new javax.swing.JTextField();
        cbDokter = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtPasien = new javax.swing.JTextField();
        cbPasien = new javax.swing.JComboBox<>();
        txtdbPasien = new javax.swing.JTextField();
        txtdbDokter = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setText("Tanggal: ");

        jLabel2.setText("Nama Penyakit:");

        txtpenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpenyakitActionPerformed(evt);
            }
        });

        jLabel3.setText("Kode Diagnosis");

        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });
        btnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnSimpanKeyPressed(evt);
            }
        });

        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        btnKeluar.setText("Keluar");
        btnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeluarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode Diagnosis", "Nama Pasien", "Nama Penyakit", "Dokter", "Tanggal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel4.setText("Dokter yang melaporkan:");

        cbDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbDokterActionPerformed(evt);
            }
        });

        jLabel5.setText("Nama Pasien:");

        cbPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPasienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(btnTambah)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSimpan)
                                .addGap(34, 34, 34)
                                .addComponent(btnBatal)
                                .addGap(18, 18, 18)
                                .addComponent(btnKeluar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(315, 315, 315)
                                        .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(219, 219, 219)
                                        .addComponent(jLabel3))
                                    .addComponent(txtpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtPasien))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(96, 96, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtdbDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtdbPasien, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbPasien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbDokter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtpenyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdbPasien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdbDokter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal)
                    .addComponent(btnKeluar)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        otomatis();
        isi= false;
        aktif();
        txtpenyakit.setEnabled(true);
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        txtTgl.setText(sdf.format(cal.getTime()));
        btnSimpan.setEnabled(true);
        btnBatal.setEnabled(true);
        btnTambah.setEnabled(false);
        otomatis();
        isiKode();
        txtpenyakit.grabFocus();
        cbDokter.grabFocus();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        sql = "insert into diagnosa values('"+txtKode.getText()+"','"+txtpenyakit.getText()+"','"+txtTgl.getText()+"','"+txtPasien.getText()+"','"+txtDokter.getText()+"')";
        try {
            s = con.createStatement();
            s.execute(sql);
            loadData();//refresh tabel
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            s.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        btnBatal.setEnabled(true);
        btnKeluar.setEnabled(true);
        btnTambah.setEnabled(true);
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        bersih();
        btnKeluar.setEnabled(true);
        btnTambah.setEnabled(true);
        btnBatal.setEnabled(false);
        btnSimpan.setEnabled(false);
    }//GEN-LAST:event_btnBatalActionPerformed

    private void btnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeluarActionPerformed
        this.dispose();
        MenuUtama menu = new MenuUtama();
        menu.setLocationRelativeTo(null);
        menu.setVisible(true);
    }//GEN-LAST:event_btnKeluarActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        cbPasien.removeAllItems();
        cbDokter.removeAllItems();
        aktif();
        bersih();
        //tampil();
        //isiKode();
        ImageIcon icon = new ImageIcon("src/medical-history.png");
        setIconImage(icon.getImage());
    }//GEN-LAST:event_formWindowActivated

    private void btnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnSimpanKeyPressed
    sql = "insert into diagnosa values('"+txtKode.getText()+"','"+txtpenyakit.getText()+"','"+txtTgl.getText()+"','"+txtPasien.getText()+"','"+txtDokter.getText()+"')";
        try {
            s = con.createStatement();
            s.execute(sql);
            loadData();//refresh tabel
            JOptionPane.showMessageDialog(null, "Data Telah Tersimpan");
            s.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanKeyPressed

    private void txtpenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpenyakitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpenyakitActionPerformed

    private void cbDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbDokterActionPerformed
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql= "Select * from daftar where kd_user='"+cbDokter.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txtDokter.setText(rs.getString("nm_user"));
                txtdbDokter.setText(rs.getString("peran"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbDokterActionPerformed

    private void cbPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPasienActionPerformed
        try{
            Connection conn = DriverManager.getConnection(url,user,pwd);
            Statement st = (Statement) conn.createStatement();
            String sql= "Select * from daftar where kd_user='"+cbPasien.getSelectedItem()+"'";
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txtPasien.setText(rs.getString("nm_user"));
                txtdbPasien.setText(rs.getString("peran"));
            }
        }catch(SQLException e){
            System.out.println("Koneksi gagal"+e.toString());
        }
    }//GEN-LAST:event_cbPasienActionPerformed

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
            java.util.logging.Logger.getLogger(diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(diagnosis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new diagnosis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnKeluar;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cbDokter;
    private javax.swing.JComboBox<String> cbPasien;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtDokter;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtPasien;
    private javax.swing.JTextField txtTgl;
    private javax.swing.JTextField txtdbDokter;
    private javax.swing.JTextField txtdbPasien;
    private javax.swing.JTextField txtpenyakit;
    // End of variables declaration//GEN-END:variables
}
