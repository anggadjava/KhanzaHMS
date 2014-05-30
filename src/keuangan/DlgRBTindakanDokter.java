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
import khanzahms.DlgCariDokter;

public class DlgRBTindakanDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");   
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private int i=0,a=0;
    private double jm=0,totaljm=0;
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRBTindakanDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.",
                      "Nama Dokter",
                      "Tindakan Medis",
                      "Jasa Medis",
                      "Jumlah",
                      "Total"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 6; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)10).getKata(kddokter));
                
        dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kddokter.setText(dokter.getTextField().getText());
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText());              
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
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where kd_dokter like ? order by nm_dokter");
            ps2=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,jns_perawatan.tarif_tindakandr,"+
                       "count(jns_perawatan.nm_perawatan) as jml,"+
                       "jns_perawatan.tarif_tindakandr * count(jns_perawatan.nm_perawatan) as total "+
                       "from reg_periksa inner join jns_perawatan inner join rawat_jl_dr "+
                       "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                       "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                       "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                       " and jns_perawatan.total_byrdr>0 group by jns_perawatan.nm_perawatan order by jns_perawatan.nm_perawatan");   
            ps3=koneksi.prepareStatement("select jns_perawatan_inap.nm_perawatan,jns_perawatan_inap.tarif_tindakandr,"+
                       "count(jns_perawatan_inap.nm_perawatan) as jml, " +
                       "jns_perawatan_inap.tarif_tindakandr * count(jns_perawatan_inap.nm_perawatan) as total "+
                       "from jns_perawatan_inap inner join rawat_inap_dr "+
                       "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                       "where rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                       "and jns_perawatan_inap.total_byrdr>0 group by jns_perawatan_inap.nm_perawatan order by jns_perawatan_inap.nm_perawatan  ");
            ps4=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaoperator1,"+
                       "count(operasi.kode_paket) as jml, " +
                       "sum(operasi.biayaoperator1) as total "+
                       "from paket_operasi inner join operasi "+
                       "on operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                       "and operasi.biayaoperator1>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            ps5=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaoperator2,"+
                       "count(operasi.kode_paket) as jml, " +
                       "sum(operasi.biayaoperator2) as total "+
                       "from paket_operasi inner join operasi "+
                       "on operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                       "and operasi.biayaoperator2>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            ps6=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayaoperator3,"+
                       "count(operasi.kode_paket) as jml, " +
                       "sum(operasi.biayaoperator3) as total "+
                       "from paket_operasi inner join operasi "+
                       "on operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                       "and operasi.biayaoperator3>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            ps7=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayadokter_anak,"+
                       "count(operasi.kode_paket) as jml, " +
                       "sum(operasi.biayadokter_anak) as total "+
                       "from paket_operasi inner join operasi "+
                       "on operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                       "and operasi.biayadokter_anak>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            ps8=koneksi.prepareStatement("select paket_operasi.nm_perawatan,operasi.biayadokter_anestesi,"+
                       "count(operasi.kode_paket) as jml, " +
                       "sum(operasi.biayadokter_anestesi) as total "+
                       "from paket_operasi inner join operasi "+
                       "on operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? and operasi.biayadokter_anestesi>0 group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
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
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Bulanan Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(70, 23));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        panelisi4.add(kddokter);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmdokter);

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
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','','','','','','','','','','',''","Rekap Harian BulananDokter"); 
            }
            Valid.MyReport("rptRBTindakanDokter.jrxml","report","[ Rekap Bulanan Tindakan Dokter ]",
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

    private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?", nmdokter,kddokter.getText()); 
            BtnAll.requestFocus();
        }
    }//GEN-LAST:event_kddokterKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kddokter.setText("");
        nmdokter.setText("");
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
        dokter.tampil(" order by kd_dokter");
        dokter.emptTeks();        
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
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
            Valid.pindah(evt, kddokter, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRBTindakanDokter dialog = new DlgRBTindakanDokter(new javax.swing.JFrame(), true);
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
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{  
            ps.setString(1,"%"+kddokter.getText()+"%");
            rs=ps.executeQuery();
            i=1;
            totaljm=0;
            while(rs.next()){
               tabMode.addRow(new Object[]{""+i+".",rs.getString("nm_dokter"),"","","",""});   
               jm=0;
               //rawat jalan dokter            
               ps2.setString(1,Tgl1.getSelectedItem().toString());
               ps2.setString(2,Tgl2.getSelectedItem().toString());
               ps2.setString(3,rs.getString("kd_dokter"));
               rs2=ps2.executeQuery();
               if(rs2.next()){
                    tabMode.addRow(new Object[]{"","","Rawat Jalan :","","","",""});   
               }
               rs2.beforeFirst();
               a=1;
               while(rs2.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs2.getString("nm_perawatan")," "+Valid.SetAngka(rs2.getDouble("tarif_tindakandr")),
                       rs2.getString("jml"),Valid.SetAngka(rs2.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs2.getDouble("total");
               }
               //rs2.close();
               
               //rawat inap dokter   
               ps3.setString(1,Tgl1.getSelectedItem().toString());
               ps3.setString(2,Tgl2.getSelectedItem().toString());
               ps3.setString(3,rs.getString("kd_dokter"));
               rs3=ps3.executeQuery();
                              
               ps4.setString(1,Tgl1.getSelectedItem().toString());
               ps4.setString(2,Tgl2.getSelectedItem().toString());
               ps4.setString(3,rs.getString("kd_dokter"));
               rs4=ps4.executeQuery();          
               
               ps5.setString(1,Tgl1.getSelectedItem().toString());
               ps5.setString(2,Tgl2.getSelectedItem().toString());
               ps5.setString(3,rs.getString("kd_dokter"));
               rs5=ps5.executeQuery();
               
               ps6.setString(1,Tgl1.getSelectedItem().toString());
               ps6.setString(2,Tgl2.getSelectedItem().toString());
               ps6.setString(3,rs.getString("kd_dokter"));
               rs6=ps6.executeQuery();            
               
               ps7.setString(1,Tgl1.getSelectedItem().toString());
               ps7.setString(2,Tgl2.getSelectedItem().toString());
               ps7.setString(3,rs.getString("kd_dokter"));
               rs7=ps7.executeQuery();        
               
               ps8.setString(1,Tgl1.getSelectedItem().toString());
               ps8.setString(2,Tgl2.getSelectedItem().toString());
               ps8.setString(3,rs.getString("kd_dokter"));
               rs8=ps8.executeQuery();
               
               if((rs3.next())||(rs4.next())||(rs5.next())||(rs6.next())||(rs7.next())||(rs8.next())){
                    tabMode.addRow(new Object[]{"","","Rawat Inap :","","","",""});   
               }
               rs3.beforeFirst();
               a=1;
               while(rs3.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs3.getString("nm_perawatan")," "+Valid.SetAngka(rs3.getDouble("tarif_tindakandr")),
                       rs3.getString("jml"),Valid.SetAngka(rs3.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs3.getDouble("total");
               }
              // rs3.close();
               
               //dokter operasi               
               rs4.beforeFirst();
               while(rs4.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs4.getString("nm_perawatan")+" (Operator 1)"," "+Valid.SetAngka(rs4.getDouble("biayaoperator1")),
                       rs4.getString("jml"),Valid.SetAngka(rs4.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs4.getDouble("total");
               }
               //rs4.close();
               
               //dokter anasthesi               
               rs5.beforeFirst();
               while(rs5.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs5.getString("nm_perawatan")+" (Operator 2)"," "+Valid.SetAngka(rs5.getDouble("biayaoperator2")),
                       rs5.getString("jml"),Valid.SetAngka(rs5.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs5.getDouble("total");
               }
               //rs5.close();
               
               //dokter anasthesi               
               rs6.beforeFirst();
               while(rs6.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs6.getString("nm_perawatan")+" (Instrumen)"," "+Valid.SetAngka(rs6.getDouble("biayaoperator3")),
                       rs6.getString("jml"),Valid.SetAngka(rs6.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs6.getDouble("total");
               }
               //rs6.close();
               
               //dokter anasthesi               
               rs7.beforeFirst();
               while(rs7.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs7.getString("nm_perawatan")+" (dr Anak)"," "+Valid.SetAngka(rs7.getDouble("biayadokter_anak")),
                       rs7.getString("jml"),Valid.SetAngka(rs7.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs7.getDouble("total");
               }
               //rs7.close();
               
               //dokter anasthesi               
               rs8.beforeFirst();
               while(rs8.next()){
                   tabMode.addRow(new Object[]{"","",a+". "+rs8.getString("nm_perawatan")+" (dr Anestesi)"," "+Valid.SetAngka(rs8.getDouble("biayadokter_anestesi")),
                       rs8.getString("jml"),Valid.SetAngka(rs8.getDouble("total"))
                   });                   
                   a++;
                   jm=jm+rs8.getDouble("total");
               }
               //rs8.close();
               totaljm=totaljm+jm;
               if(jm>0){
                   tabMode.addRow(new Object[]{"<>> ","","Total : ","","",Valid.SetAngka(jm)});
               }    
               i++;
            } 
            if(totaljm>0){
                   tabMode.addRow(new Object[]{"<>> ","Total Jasa Medis :"," ","","",Valid.SetAngka(totaljm)});
            }            
        }catch(SQLException e){
            System.out.println("Error  "+e);
        }
        
    }
    
    public void isCek(){
        // BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
