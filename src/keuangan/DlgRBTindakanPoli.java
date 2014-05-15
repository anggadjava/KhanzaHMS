package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgCariPoli;

public class DlgRBTindakanPoli extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pspoli,psdokter,pstindakan,pspasien,psobat,pslaborat,psdetailobat,psdetaillaborat,psdetailtindakan;
    private ResultSet rspoli,rsdokter,rstindakan,rsobat,rspasien,rslaborat,rsdetailobat,rsdetailtindakan,rsdetaillaborat; 
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");   
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private int i=0,a=0;
    private double obat=0,laborat=0,jm=0,biaya=0,ttlbiaya=0,detailobat=0,detailtindakan=0,detaillaborat=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBTindakanPoli(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Poliklinik","Jml.Pasien","Obat","Laborat","Jasa Medis","Total"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0;i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(350);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpoli.setDocument(new batasInput((byte)8).getKata(kdpoli));
                
        poli.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdpoli.setText(poli.getTextField().getText());
                Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());              
                prosesCari();
            }

            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });    
        
        try {
            pspoli=koneksi.prepareStatement("select kd_poli,nm_poli from poliklinik where  kd_poli like ?");
            psdokter=koneksi.prepareStatement(
                    "select dokter.kd_dokter,dokter.nm_dokter,count(dokter.kd_dokter) as jumlah from dokter inner join reg_periksa "+
                    "on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.kd_poli=? and "+
                    "tgl_registrasi between ? and ? group by dokter.kd_dokter");
            pstindakan=koneksi.prepareStatement(
                    "select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr inner join reg_periksa "+
                    "on reg_periksa.no_rawat=rawat_jl_dr.no_rawat where rawat_jl_dr.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");
            psobat=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.total) from detail_pemberian_obat inner join rawat_jl_dr inner join reg_periksa "+
                    "on detail_pemberian_obat.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rawat=rawat_jl_dr.no_rawat where rawat_jl_dr.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");    
            pslaborat=koneksi.prepareStatement(
                    "select sum(periksa_lab.biaya) from periksa_lab inner join rawat_jl_dr inner join reg_periksa "+
                    "on periksa_lab.no_rawat=rawat_jl_dr.no_rawat and reg_periksa.no_rawat=rawat_jl_dr.no_rawat where rawat_jl_dr.kd_dokter=? "+
                    "and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_poli=?");             
            pspasien=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,pasien.nm_pasien,reg_periksa.no_rawat "+
                    "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_dokter=? and reg_periksa.kd_poli=? order by reg_periksa.tgl_registrasi");
            psdetailtindakan=koneksi.prepareStatement(
                    "select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat=?");
            psdetailobat=koneksi.prepareStatement(
                    "select sum(detail_pemberian_obat.total) from detail_pemberian_obat where detail_pemberian_obat.no_rawat=?");
            psdetaillaborat=koneksi.prepareStatement(
                    "select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat=?");
        } catch (SQLException e) {
            System.out.println(e);
        }
     
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanPasien = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanPasien.setBackground(new java.awt.Color(242, 242, 242));
        ppTampilkanPasien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppTampilkanPasien.setForeground(java.awt.Color.darkGray);
        ppTampilkanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        ppTampilkanPasien.setText("Tampilkan Pasien");
        ppTampilkanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanPasien.setIconTextGap(5);
        ppTampilkanPasien.setName("ppTampilkanPasien"); // NOI18N
        ppTampilkanPasien.setPreferredSize(new java.awt.Dimension(150, 25));
        ppTampilkanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanPasienBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanPasien);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Pemasukan Tindakan Dokter Per Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Tindakan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("yyyy-MM-dd");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("yyyy-MM-dd");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl2);

        label17.setText("Poli :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        panelisi4.add(kdpoli);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmpoli);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelisi4.add(BtnSeek2);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi4.add(BtnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','','','','','','','','','',''","Rekap Harian Perpoli Dokter"); 
            }
            Valid.MyReport("rptRBTindakanPoli.jrxml","report","[ Rekap Bulanan Tindakan Dokter ]",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmpoli,kdpoli.getText());
           // TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmpoli,kdpoli.getText());
            //TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng=?", nmpoli,kdpoli.getText());
        }
    }//GEN-LAST:event_kdpoliKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdpoli.setText("");
        nmpoli.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        poli.tampil();
        poli.emptTeks();        
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void ppTampilkanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanPasienBtnPrintActionPerformed
     prosesCari2();
}//GEN-LAST:event_ppTampilkanPasienBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBTindakanPoli dialog = new DlgRBTindakanPoli(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.TextBox Kd2;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpoli;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmpoli;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanPasien;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void prosesCari() {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
       Valid.tabelKosong(tabMode);      
        try{   
           pspoli.setString(1,"%"+kdpoli.getText()+"%"); 
           rspoli=pspoli.executeQuery();
           i=1;
           ttlbiaya=0;
           while(rspoli.next()){
               tabMode.addRow(new Object[]{i+". ",rspoli.getString(2),"","","","",""});
               psdokter.setString(1,rspoli.getString(1));
               psdokter.setString(2,Tgl1.getSelectedItem().toString());
               psdokter.setString(3,Tgl2.getSelectedItem().toString());
               rsdokter=psdokter.executeQuery();
               a=1;
               while(rsdokter.next()){
                   biaya=0;
                   pstindakan.setString(1,rsdokter.getString(1));
                   pstindakan.setString(2,Tgl1.getSelectedItem().toString());
                   pstindakan.setString(3,Tgl2.getSelectedItem().toString());
                   pstindakan.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan.executeQuery();
                   if(rstindakan.next()){
                      biaya=rstindakan.getDouble(1); 
                   }
                   
                   obat=0;
                   psobat.setString(1,rsdokter.getString(1));
                   psobat.setString(2,Tgl1.getSelectedItem().toString());
                   psobat.setString(3,Tgl2.getSelectedItem().toString());
                   psobat.setString(4,rspoli.getString(1));
                   rsobat=psobat.executeQuery();
                   if(rsobat.next()){
                      obat=rsobat.getDouble(1); 
                   }
                   
                   laborat=0;
                   pslaborat.setString(1,rsdokter.getString(1));
                   pslaborat.setString(2,Tgl1.getSelectedItem().toString());
                   pslaborat.setString(3,Tgl2.getSelectedItem().toString());
                   pslaborat.setString(4,rspoli.getString(1));
                   rslaborat=pslaborat.executeQuery();
                   if(rslaborat.next()){
                      laborat=rslaborat.getDouble(1); 
                   }
                   
                   ttlbiaya=ttlbiaya+biaya+obat+laborat;
                   tabMode.addRow(new Object[]{"",a+". "+rsdokter.getString(2),rsdokter.getString(3),Valid.SetAngka(obat),Valid.SetAngka(laborat),Valid.SetAngka(biaya),Valid.SetAngka(biaya+obat+laborat)});
                   a++;
               }
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total Biaya :","","","","",Valid.SetAngka(ttlbiaya)});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(SQLException e){
            System.out.println("Error  "+e);
        }
        
    }
    
    public void prosesCari2() {
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
       Valid.tabelKosong(tabMode);      
        try{   
           pspoli.setString(1,"%"+kdpoli.getText()+"%"); 
           rspoli=pspoli.executeQuery();
           i=1;
           ttlbiaya=0;
           while(rspoli.next()){
               tabMode.addRow(new Object[]{i+". ",rspoli.getString(2),"","","","",""});
               psdokter.setString(1,rspoli.getString(1));
               psdokter.setString(2,Tgl1.getSelectedItem().toString());
               psdokter.setString(3,Tgl2.getSelectedItem().toString());
               rsdokter=psdokter.executeQuery();
               a=1;
               while(rsdokter.next()){
                   biaya=0;
                   pstindakan.setString(1,rsdokter.getString(1));
                   pstindakan.setString(2,Tgl1.getSelectedItem().toString());
                   pstindakan.setString(3,Tgl2.getSelectedItem().toString());
                   pstindakan.setString(4,rspoli.getString(1));
                   rstindakan=pstindakan.executeQuery();
                   if(rstindakan.next()){
                      biaya=rstindakan.getDouble(1); 
                   }
                   
                   obat=0;
                   psobat.setString(1,rsdokter.getString(1));
                   psobat.setString(2,Tgl1.getSelectedItem().toString());
                   psobat.setString(3,Tgl2.getSelectedItem().toString());
                   psobat.setString(4,rspoli.getString(1));
                   rsobat=psobat.executeQuery();
                   if(rsobat.next()){
                      obat=rsobat.getDouble(1); 
                   }
                   
                   laborat=0;
                   pslaborat.setString(1,rsdokter.getString(1));
                   pslaborat.setString(2,Tgl1.getSelectedItem().toString());
                   pslaborat.setString(3,Tgl2.getSelectedItem().toString());
                   pslaborat.setString(4,rspoli.getString(1));
                   rslaborat=pslaborat.executeQuery();
                   if(rslaborat.next()){
                      laborat=rslaborat.getDouble(1); 
                   }
                   
                   ttlbiaya=ttlbiaya+biaya+obat+laborat;
                   tabMode.addRow(new Object[]{"",a+". "+rsdokter.getString(2),rsdokter.getString(3),Valid.SetAngka(obat),Valid.SetAngka(laborat),Valid.SetAngka(biaya),Valid.SetAngka(biaya+obat+laborat)});
                                                        
                   pspasien.setString(1,Tgl1.getSelectedItem().toString());
                   pspasien.setString(2,Tgl2.getSelectedItem().toString());
                   pspasien.setString(3,rsdokter.getString(1));
                   pspasien.setString(4,rspoli.getString(1));
                   rspasien=pspasien.executeQuery();
                   while(rspasien.next()){
                       psdetailtindakan.setString(1,rspasien.getString(4));
                       rsdetailtindakan=psdetailtindakan.executeQuery();
                       if(rsdetailtindakan.next()){
                           detailtindakan=rsdetailtindakan.getDouble(1);
                       }
                       
                       psdetailobat.setString(1,rspasien.getString(4));
                       rsdetailobat=psdetailobat.executeQuery();
                       if(rsdetailobat.next()){
                           detailobat=rsdetailobat.getDouble(1);
                       }
                       
                       psdetaillaborat.setString(1,rspasien.getString(4));
                       rsdetaillaborat=psdetaillaborat.executeQuery();
                       if(rsdetaillaborat.next()){
                           detaillaborat=rsdetaillaborat.getDouble(1);
                       }
                       
                       tabMode.addRow(new Object[]{"",rspasien.getString(1)+" "+rspasien.getString(3),"",Valid.SetAngka(detailobat),Valid.SetAngka(detaillaborat),Valid.SetAngka(detailtindakan),Valid.SetAngka(detailtindakan+detailobat+detaillaborat)});
                   }
                   a++;
               }
               i++;
           }
           tabMode.addRow(new Object[]{">>","Total Biaya :","","","","",Valid.SetAngka(ttlbiaya)});
           this.setCursor(Cursor.getDefaultCursor());             
        }catch(SQLException e){
            System.out.println("Error  "+e);
        }
        
    }
    
    public void isCek(){
        // BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
