package keuangan;
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
import khanzahms.DlgCariPetugas;

public class DlgRHTindakanParamedis extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6,ps7,ps8,ps9,ps10,ps11;
    private ResultSet rs,rs2,rs3,rs4,rs5,rs6,rs7,rs8,rs9,rs10,rs11;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");   
    private DlgCariPetugas bangsal=new DlgCariPetugas(null,false);
    private int i=0,a=0;
    private double total=0,totaljm=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgRHTindakanParamedis(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Nama Paramedis","Tgl.Tindakan","Nama Pasien","Tindakan Medis","Jasa Medis"};
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
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(130);
            }else if(i==3){
                column.setPreferredWidth(250);
            }else if(i==4){
                column.setPreferredWidth(250);
            }else if(i==5){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdbangsal.setDocument(new batasInput((byte)5).getKata(kdbangsal));
                
        bangsal.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                kdbangsal.setText(bangsal.getTextField().getText());
                Sequel.cariIsi("select nama from petugas where nip='"+bangsal.getTextField().getText()+"'",nmbangsal);              
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
        
        try{
            ps=koneksi.prepareStatement("select nip,nama from petugas where nip like ? order by nama");
            ps2=koneksi.prepareStatement("select pasien.nm_pasien,jns_perawatan.tarif_tindakanpr,"+
                       "jns_perawatan.nm_perawatan,reg_periksa.tgl_registrasi "+
                       "from pasien inner join reg_periksa  "+
                       "inner join jns_perawatan inner join rawat_jl_pr "+
                       "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                       "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                       "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.nip=? "+
                       " and jns_perawatan.total_byrpr>0 order by reg_periksa.tgl_registrasi,jns_perawatan.nm_perawatan");
            ps4=koneksi.prepareStatement("select jns_perawatan_lab.tarif_tindakan_petugas,pasien.nm_pasien,"+
                       "jns_perawatan_lab.nm_perawatan,periksa_lab.tgl_periksa,periksa_lab.jam "+
                       " from periksa_lab inner join reg_periksa inner join pasien inner join petugas inner join jns_perawatan_lab "+
                       " on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       " and periksa_lab.nip=petugas.nip and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                       " where periksa_lab.tgl_periksa between ? and ? and periksa_lab.nip=? order by periksa_lab.tgl_periksa,periksa_lab.jam,jns_perawatan_lab.nm_perawatan  ");
            ps3=koneksi.prepareStatement("select jns_perawatan_inap.tarif_tindakanpr,pasien.nm_pasien,jns_perawatan_inap.nm_perawatan,"+
                       "rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat " +
                       "from pasien inner join reg_periksa inner join jns_perawatan_inap inner join rawat_inap_pr "+
                       "on rawat_inap_pr.no_rawat=reg_periksa.no_rawat "+
                       "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "and rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                       "where rawat_inap_pr.tgl_perawatan between ? and ? and rawat_inap_pr.nip=? "+
                       " and jns_perawatan_inap.total_byrpr>0 order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,jns_perawatan_inap.nm_perawatan  ");
            ps5=koneksi.prepareStatement("select operasi.biayaasisten_operator1,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator1=? "+
                       " and operasi.biayaasisten_operator1>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");  
            ps6=koneksi.prepareStatement("select operasi.biayaasisten_operator2,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator2=? "+
                       " and operasi.biayaasisten_operator2>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
            ps7=koneksi.prepareStatement("select operasi.biayaasisten_operator3,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.asisten_operator3=? "+
                       " and operasi.biayaasisten_operator3>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
            ps8=koneksi.prepareStatement("select operasi.biayaperawaat_resusitas,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.perawaat_resusitas=? "+
                       " and operasi.biayaperawaat_resusitas>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");   
            ps9=koneksi.prepareStatement("select operasi.biayaasisten_anestesi,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.asisten_anestesi=? "+
                       " and operasi.biayaasisten_anestesi>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
            ps10=koneksi.prepareStatement("select operasi.biayabidan,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.bidan=? "+
                       " and operasi.biayabidan>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
            ps11=koneksi.prepareStatement("select operasi.biayaperawat_luar,pasien.nm_pasien,paket_operasi.nm_perawatan,"+
                       "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien inner join paket_operasi "+
                       "on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and operasi.kode_paket=paket_operasi.kode_paket "+
                       "where operasi.tgl_operasi between ? and ? and operasi.perawat_luar=? "+
                       " and operasi.biayaperawat_luar>0 order by operasi.tgl_operasi,paket_operasi.nm_perawatan");
        }catch(SQLException e){
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

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Harian Jasa Medis Paramedis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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

        label17.setText("Paramedis :");
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
                                tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
            }
            Valid.MyReport("rptRHTindakanParamedis.jrxml","report","::[ Rekap Harian Tindakan Paramedis ]::",
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

    private void kdbangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbangsalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbangsal.getText()+"'", nmbangsal);
           // TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbangsal.getText()+"'", nmbangsal);
            //TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+kdbangsal.getText()+"'", nmbangsal);
        }
    }//GEN-LAST:event_kdbangsalKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdbangsal.setText("");
        nmbangsal.setText("");
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
        var.setStatus(true);
        bangsal.tampil();
        bangsal.emptTeks();        
        bangsal.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
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
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            //Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRHTindakanParamedis dialog = new DlgRHTindakanParamedis(new javax.swing.JFrame(), true);
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
            totaljm=0;
            while(rs.next()){ 
               total=0;
               tabMode.addRow(new Object[]{i+".",rs.getString("nama"),"","","",""});   
               //rawat jalan               
               ps2.setString(1,Tgl1.getSelectedItem().toString());
               ps2.setString(2,Tgl2.getSelectedItem().toString());
               ps2.setString(3,rs.getString("nip"));
               rs2=ps2.executeQuery();
               rs2.last();
               if(rs2.getRow()>0){
                    tabMode.addRow(new Object[]{"","","Rawat Jalan :","","",""});   
               }
               rs2.beforeFirst();
               a=1;
               while(rs2.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs2.getString("tgl_registrasi"),rs2.getString("nm_pasien"),
                       rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakanpr"))
                   });                   
                   a++;
                   total=total+rs2.getDouble("tarif_tindakanpr");
               }
                              
               //periksa lab  
               ps4.setString(1,Tgl1.getSelectedItem().toString());
               ps4.setString(2,Tgl2.getSelectedItem().toString());
               ps4.setString(3,rs.getString("nip"));
               rs4=ps4.executeQuery();
               rs4.last();
               if(rs4.getRow()>0){
                    tabMode.addRow(new Object[]{"","","Periksa Lab : ","","",""});
               }               
               rs4.beforeFirst();
               a=1;
               while(rs4.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs4.getString("tgl_periksa")+" "+rs4.getString("jam"),rs4.getString("nm_pasien"),
                       rs4.getString("nm_perawatan"),Valid.SetAngka(rs4.getDouble("tarif_tindakanpr"))
                   });                   
                   a++;
                   total=total+rs4.getDouble("tarif_tindakanpr");
               }
               //rs4.close();
               
               //rawat inap
               ps3.setString(1,Tgl1.getSelectedItem().toString());
               ps3.setString(2,Tgl2.getSelectedItem().toString());
               ps3.setString(3,rs.getString("nip"));
               rs3=ps3.executeQuery();
               rs3.last();
               
               ps5.setString(1,Tgl1.getSelectedItem().toString());
               ps5.setString(2,Tgl2.getSelectedItem().toString());
               ps5.setString(3,rs.getString("nip"));
               rs5=ps5.executeQuery();
               rs5.last();
               
               ps6.setString(1,Tgl1.getSelectedItem().toString());
               ps6.setString(2,Tgl2.getSelectedItem().toString());
               ps6.setString(3,rs.getString("nip"));
               rs6=ps6.executeQuery();
               rs6.last();
               
               ps7.setString(1,Tgl1.getSelectedItem().toString());
               ps7.setString(2,Tgl2.getSelectedItem().toString());
               ps7.setString(3,rs.getString("nip"));
               rs7=ps7.executeQuery();
               rs7.last();               
               
               ps8.setString(1,Tgl1.getSelectedItem().toString());
               ps8.setString(2,Tgl2.getSelectedItem().toString());
               ps8.setString(3,rs.getString("nip"));
               rs8=ps8.executeQuery();
               rs8.last();               
               
               ps9.setString(1,Tgl1.getSelectedItem().toString());
               ps9.setString(2,Tgl2.getSelectedItem().toString());
               ps9.setString(3,rs.getString("nip"));
               rs9=ps9.executeQuery();
               rs9.last();
               
               ps10.setString(1,Tgl1.getSelectedItem().toString());
               ps10.setString(2,Tgl2.getSelectedItem().toString());
               ps10.setString(3,rs.getString("nip"));
               rs10=ps10.executeQuery();
               rs10.last();
                              
               ps11.setString(1,Tgl1.getSelectedItem().toString());
               ps11.setString(2,Tgl2.getSelectedItem().toString());
               ps11.setString(3,rs.getString("nip"));
               rs11=ps11.executeQuery();
               rs11.last();
               
               if((rs3.getRow()>0)||(rs5.getRow()>0)||(rs6.getRow()>0)||(rs7.getRow()>0)||(rs8.getRow()>0)||(rs9.getRow()>0)||(rs10.getRow()>0)||(rs11.getRow()>0)){
                    tabMode.addRow(new Object[]{"","","Rawat Inap :","","",""});   
               }
               rs3.beforeFirst();
               a=1;
               while(rs3.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat"),rs3.getString("nm_pasien"),
                       rs3.getString("nm_perawatan"),Valid.SetAngka(rs3.getDouble("tarif_tindakanpr"))
                   });                   
                   a++;
                   total=total+rs3.getDouble("tarif_tindakanpr");
               }
               
               //asisten operator              
               rs5.beforeFirst();
               while(rs5.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs5.getString("tgl_operasi"),rs5.getString("nm_pasien"),
                       rs5.getString("nm_perawatan")+" (Asisten Operator 1)",Valid.SetAngka(rs5.getDouble("biayaasisten_operator1"))
                   });                   
                   a++;
                   total=total+rs5.getDouble("biayaasisten_operator1");
               }
               
               //asisten anasthesi              
               rs6.beforeFirst();
               while(rs6.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs6.getString("tgl_operasi"),rs6.getString("nm_pasien"),
                       rs6.getString("nm_perawatan")+" (Asisten Operator 2)",Valid.SetAngka(rs6.getDouble("biayaasisten_operator2"))
                   });                   
                   a++;
                   total=total+rs6.getDouble("biayaasisten_operator2");
               }
               
               //perawat luar              
               rs7.beforeFirst();
               while(rs7.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs7.getString("tgl_operasi"),rs7.getString("nm_pasien"),
                       rs7.getString("nm_perawatan")+" (Asisten Operator 3)",Valid.SetAngka(rs7.getDouble("biayaasisten_operator3"))
                   });                   
                   a++;
                   total=total+rs7.getDouble("biayaasisten_operator3");
               }
               
               //perawat luar              
               rs8.beforeFirst();
               while(rs8.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs8.getString("tgl_operasi"),rs8.getString("nm_pasien"),
                       rs8.getString("nm_perawatan")+" (Perawat Resusitas)",Valid.SetAngka(rs8.getDouble("biayaperawaat_resusitas"))
                   });                   
                   a++;
                   total=total+rs8.getDouble("biayaperawaat_resusitas");
               }
               
               //perawat luar              
               rs9.beforeFirst();
               while(rs9.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs9.getString("tgl_operasi"),rs9.getString("nm_pasien"),
                       rs9.getString("nm_perawatan")+" (Asisten Anestesi)",Valid.SetAngka(rs9.getDouble("biayaasisten_anestesi"))
                   });                   
                   a++;
                   total=total+rs9.getDouble("biayaasisten_anestesi");
               }
               
               //perawat luar              
               rs10.beforeFirst();
               while(rs10.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs10.getString("tgl_operasi"),rs10.getString("nm_pasien"),
                       rs10.getString("nm_perawatan")+" (Bidan Operasi)",Valid.SetAngka(rs10.getDouble("biayabidan"))
                   });                   
                   a++;
                   total=total+rs10.getDouble("biayabidan");
               }
                          
               rs11.beforeFirst();
               while(rs11.next()){
                   tabMode.addRow(new Object[]{
                       "","",a+". "+rs11.getString("tgl_operasi"),rs11.getString("nm_pasien"),
                       rs11.getString("nm_perawatan")+" (Perawat Luar)",Valid.SetAngka(rs11.getDouble("biayaperawat_luar"))
                   });                   
                   a++;
                   total=total+rs11.getDouble("biayaperawat_luar");
               }
               
               if(total>0){
                  tabMode.addRow(new Object[]{"","","Total :","","",Valid.SetAngka(total)});                    
               }              
               i++;
               totaljm=totaljm+total;
            } 
            if(totaljm>0){
               tabMode.addRow(new Object[]{"<>> ","Total Jasa Medis :","","","",Valid.SetAngka(totaljm)});     
            }
                        
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }
    
    public void isCek(){
        // BtnPrint.setEnabled(var.getproyeksi());
    }
    
}
