package khanzahms;
import keuangan.Jurnal;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
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

public class DlgAnalisaKamin extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3,ps4;
    private ResultSet rs,rs2;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");   
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgAnalisaKamin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"","","","","","","","",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdbangsal.setDocument(new batasInput((byte)5).getKata(kdbangsal));
                
        bangsal.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdbangsal.setText(bangsal.getTextField().getText());
                Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText());              
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
            ps=koneksi.prepareStatement("select kd_bangsal,nm_bangsal from bangsal where kd_bangsal like ? order by nm_bangsal");
            ps2=koneksi.prepareStatement("select kd_kamar,trf_kamar,kelas from kamar where kd_bangsal=? and status='ISI'");
            ps3=koneksi.prepareStatement("select kd_kamar,trf_kamar from kamar where kd_bangsal=? and status='KOSONG'");
            ps4=koneksi.prepareStatement("select reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "kamar_inap.kd_kamar,kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.tgl_keluar,kamar_inap.jam_keluar,kamar_inap.ttl_biaya " +
                           "from kamar_inap inner join reg_periksa inner join pasien inner join kamar inner join bangsal " +
                           "on kamar_inap.no_rawat=reg_periksa.no_rawat " +
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis " +
                           "and kamar_inap.kd_kamar=kamar.kd_kamar " +
                           "and kamar.kd_bangsal=bangsal.kd_bangsal " +
                           "where  kamar.kd_bangsal=? and kamar_inap.tgl_masuk between ? and ? order by kamar_inap.tgl_masuk,kamar_inap.kd_kamar,reg_periksa.no_rkm_medis ");
        } catch (SQLException e) {
            System.out.println(e);
        }
     
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdbangsal = new widget.TextBox();
        nmbangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Analisa Kamar Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Inap :");
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

        label17.setText("Kamar :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kdbangsal.setName("kdbangsal"); // NOI18N
        kdbangsal.setPreferredSize(new java.awt.Dimension(70, 23));
        kdbangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbangsalKeyPressed(evt);
            }
        });
        panelisi4.add(kdbangsal);

        nmbangsal.setEditable(false);
        nmbangsal.setName("nmbangsal"); // NOI18N
        nmbangsal.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmbangsal);

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
        BtnAll.setIconTextGap(3);
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
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
        BtnPrint.setIconTextGap(3);
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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(450, 30));
        panelisi1.add(label9);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setIconTextGap(3);
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
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','','','','','','',''","Sirkulasi Barang Keluar Masuk"); 
            }
            Valid.MyReport("rptSirkulasi.jrxml","report","::[ Transaksi Pembelian Barang ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_bangsal from bangsal where kd_bangsal=?", nmbangsal,kdbangsal.getText()); 
        }
    }//GEN-LAST:event_kdbangsalKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdbangsal.setText("");
        nmbangsal.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        bangsal.tampil();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        prosesCari();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DlgAnalisaKamin dialog = new DlgAnalisaKamin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
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
    private widget.TextBox kdbangsal;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmbangsal;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            ps.setString(1,"%"+kdbangsal.getText()+"%");
            rs=ps.executeQuery();
            i=1;
            while(rs.next()){
               tabMode.addRow(new Object[]{i+". Kamar : "+rs.getString("nm_bangsal"),"","","","","","","","",""});
               tabMode.addRow(new Object[]{"","Kondisi kamar saat ini :","Isi : "+Sequel.cariInteger("select count(kd_bangsal) from kamar where kd_bangsal=? and status='ISI' ",rs.getString("kd_bangsal")),"","","","","","",""});
               
               ps2.setString(1,rs.getString("kd_bangsal"));
               rs2=ps2.executeQuery();
               while(rs2.next()){
                   tabMode.addRow(new Object[]{"","",rs2.getString("kd_kamar")+" "+rs2.getString("kelas"),Valid.SetAngka(rs2.getDouble("trf_kamar")),"","",",",""});
               }
               
               tabMode.addRow(new Object[]{"","","Kosong : "+Sequel.cariInteger("select count(kd_bangsal) from kamar where kd_bangsal=? and status='KOSONG' ",rs.getString("kd_bangsal")),"","","","","","",""});
               
               ps3.setString(1,rs.getString("kd_bangsal"));
               rs2=ps3.executeQuery();
               while(rs2.next()){
                   tabMode.addRow(new Object[]{"","",rs2.getString("kd_kamar"),Valid.SetAngka(rs2.getDouble("trf_kamar")),"","","","",""});
               }                            
               
               ps4.setString(1,rs.getString("kd_bangsal"));
               ps4.setString(2,Tgl1.getSelectedItem().toString());
               ps4.setString(3,Tgl2.getSelectedItem().toString());
               rs2=ps4.executeQuery();
               
               tabMode.addRow(new Object[]{"","Rincian Penggunaan : ","Pasien","No.Bad","Tgl.Masuk","Tgl.Pulang","Total Biaya","",""});               
                   
               rs2.beforeFirst();
               int no=1;
               while(rs2.next()){
                   tabMode.addRow(new Object[]{
                       "","",no+". "+rs2.getString("no_rkm_medis")+", "+rs2.getString("nm_pasien"),rs2.getString("kd_kamar"),
                       rs2.getString("tgl_masuk")+" "+rs2.getString("jam_masuk"),rs2.getString("tgl_keluar")+" "+rs2.getString("jam_keluar"),Valid.SetAngka(rs2.getDouble("ttl_biaya")),"",""
                   });no++;
               }               
               i++;
            } 
                        
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }
    
    public void isCek(){
         BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
