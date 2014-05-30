/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */

package khanzahms;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgPeriksaLaboratorium extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PreparedStatement pstarif,pstampil,pstampil2,pstampil3,pstampil4,pssimpanperiksa,psdetailpriksa,pscariperawatan;
    private ResultSet rstarif,rstampil,rscari;
    private boolean[] pilih; 
    private String[] kode,nama,total;
    private int jml=0,i=0,index=0;
    private String kamar,namakamar;
    

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgPeriksaLaboratorium(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"Pemeriksaan","Hasil","Satuan","Nilai Rujukan","Keterangan","",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = false;
                    if ((colIndex==1)||(colIndex==3)||(colIndex==4)||(colIndex==6)) {
                        a=true;
                    }
                    return a;
              }
        };
        
        tbPemeriksaan.setModel(tabMode);
        //tampilPr();

        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(300);
            }else if(i==5){
                column.setPreferredWidth(8);
            }else if(i==6){
                column.setPreferredWidth(90);
            }
        }
        
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());
        
        Object[] row2={"Pilih","Kode Periksa","Nama Pemeriksaan","Tarif"};
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTarif.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTarif.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTarif.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbTarif.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else{
                column.setPreferredWidth(95);
            }
        }
        tbTarif.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Jk.setDocument(new batasInput((byte)5).getKata(Jk));
        Umur.setDocument(new batasInput((byte)5).getKata(Umur));
        KdPtg.setDocument(new batasInput((byte)10).getKata(KdPtg));
        Pemeriksaan.setDocument(new batasInput((byte)100).getKata(Pemeriksaan));      
        
        petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KdPtg.setText(petugas.getTextField().getText());   
                Sequel.cariIsi("select nama from petugas where nip=?",NmPtg,petugas.getTextField().getText());
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
         
        ChkJln.setSelected(true);
        jam();
        try {            
            pstarif=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,jns_perawatan_lab.total_byr,penjab.png_jawab "+
                    "from jns_perawatan_lab inner join penjab on penjab.kd_pj=jns_perawatan_lab.kd_pj where "+
                    " jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.kd_jenis_prw like ? or  jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                    " jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.bagian_rs like ? or jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.tarif_tindakan_petugas like ? or "+
                    " jns_perawatan_lab.kd_pj like ? and jns_perawatan_lab.total_byr like ?  "+
                    "order by jns_perawatan_lab.kd_jenis_prw");            
            pssimpanperiksa=koneksi.prepareStatement(
                    "insert into periksa_lab values(?,?,?,?,?,?,?,?)");
            pstampil=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_ld,biaya_item "+
                                "from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil2=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_la,biaya_item "+
                                "from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil3=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pd,biaya_item "+
                                "from template_laboratorium where kd_jenis_prw=? order by id_template");
            pstampil4=koneksi.prepareStatement("select id_template, Pemeriksaan, satuan, nilai_rujukan_pa,biaya_item "+
                                "from template_laboratorium where kd_jenis_prw=? order by id_template");
            psdetailpriksa=koneksi.prepareStatement(
                                    "insert into detail_periksa_lab values(?,?,?,?,?,?,?,?,?)");
            pscariperawatan=koneksi.prepareStatement("select kd_jenis_prw from template_laboratorium where id_template=?");
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

        Penjab = new widget.TextBox();
        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        Alamat = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnCari = new widget.Button();
        jLabel10 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel11 = new widget.Label();
        jLabel7 = new widget.Label();
        jLabel9 = new widget.Label();
        Pemeriksaan = new widget.TextBox();
        jLabel12 = new widget.Label();
        KdPtg = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        NmPtg = new widget.TextBox();
        jLabel15 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        jLabel16 = new widget.Label();
        Dokterperujuk = new widget.TextBox();
        BtnCari1 = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbTarif = new widget.Table();
        btnTarif = new widget.Button();
        Dokterpj = new widget.TextBox();
        rbAnak = new widget.RadioButton();
        rbDewasa = new widget.RadioButton();

        Penjab.setEditable(false);
        Penjab.setFocusTraversalPolicyProvider(true);
        Penjab.setName("Penjab"); // NOI18N
        Penjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenjabKeyPressed(evt);
            }
        });

        Jk.setEditable(false);
        Jk.setFocusTraversalPolicyProvider(true);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Data Hasil Periksa Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPemeriksaan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        Scroll.setViewportView(tbPemeriksaan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('S');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+S");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

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
        panelGlass8.add(BtnPrint);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnCari);

        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(140, 30));
        panelGlass8.add(jLabel10);

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
        panelGlass8.add(BtnKeluar);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setOpaque(false);
        FormInput.setPreferredSize(new java.awt.Dimension(560, 269));
        FormInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        FormInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 168));
        PanelInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 92, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(95, 12, 148, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(245, 12, 125, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(372, 12, 400, 23);

        jLabel11.setText("Pemeriksaan :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(0, 102, 92, 23);

        jLabel7.setText("Dokter P.J. :");
        jLabel7.setName("jLabel7"); // NOI18N
        PanelInput.add(jLabel7);
        jLabel7.setBounds(0, 42, 92, 23);

        jLabel9.setText("Dokter Perujuk :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(0, 72, 92, 23);

        Pemeriksaan.setHighlighter(null);
        Pemeriksaan.setName("Pemeriksaan"); // NOI18N
        Pemeriksaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemeriksaanKeyPressed(evt);
            }
        });
        PanelInput.add(Pemeriksaan);
        Pemeriksaan.setBounds(245, 102, 465, 23);

        jLabel12.setText("Petugas Lab :");
        jLabel12.setName("jLabel12"); // NOI18N
        PanelInput.add(jLabel12);
        jLabel12.setBounds(310, 42, 87, 23);

        KdPtg.setName("KdPtg"); // NOI18N
        KdPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgKeyPressed(evt);
            }
        });
        PanelInput.add(KdPtg);
        KdPtg.setBounds(400, 42, 90, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('2');
        BtnSeek2.setToolTipText("Alt+2");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        PanelInput.add(BtnSeek2);
        BtnSeek2.setBounds(744, 42, 28, 23);

        NmPtg.setEditable(false);
        NmPtg.setName("NmPtg"); // NOI18N
        PanelInput.add(NmPtg);
        NmPtg.setBounds(492, 42, 249, 23);

        jLabel15.setText("Tgl.Periksa :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(310, 72, 87, 23);

        Tanggal.setEditable(false);
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        Tanggal.setDisplayFormat("yyyy-MM-dd");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        PanelInput.add(Tanggal);
        Tanggal.setBounds(400, 72, 110, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.setOpaque(false);
        PanelInput.add(CmbJam);
        CmbJam.setBounds(591, 72, 50, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.setOpaque(false);
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(643, 72, 50, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.setOpaque(false);
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(695, 72, 50, 23);

        ChkJln.setBackground(new java.awt.Color(235, 255, 235));
        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setForeground(new java.awt.Color(153, 0, 51));
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        PanelInput.add(ChkJln);
        ChkJln.setBounds(749, 72, 23, 23);

        jLabel16.setText("Jam Periksa :");
        jLabel16.setName("jLabel16"); // NOI18N
        PanelInput.add(jLabel16);
        jLabel16.setBounds(510, 72, 78, 23);

        Dokterperujuk.setHighlighter(null);
        Dokterperujuk.setName("Dokterperujuk"); // NOI18N
        Dokterperujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DokterperujukKeyPressed(evt);
            }
        });
        PanelInput.add(Dokterperujuk);
        Dokterperujuk.setBounds(95, 72, 215, 23);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        PanelInput.add(BtnCari1);
        BtnCari1.setBounds(713, 102, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N

        tbTarif.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTarif.setName("tbTarif"); // NOI18N
        tbTarif.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTarifMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbTarif);

        PanelInput.add(Scroll1);
        Scroll1.setBounds(95, 127, 677, 110);

        btnTarif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTarif.setMnemonic('2');
        btnTarif.setToolTipText("Alt+2");
        btnTarif.setName("btnTarif"); // NOI18N
        btnTarif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifActionPerformed(evt);
            }
        });
        PanelInput.add(btnTarif);
        btnTarif.setBounds(744, 102, 28, 23);

        Dokterpj.setHighlighter(null);
        Dokterpj.setName("Dokterpj"); // NOI18N
        Dokterpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DokterpjKeyPressed(evt);
            }
        });
        PanelInput.add(Dokterpj);
        Dokterpj.setBounds(95, 42, 215, 23);

        buttonGroup1.add(rbAnak);
        rbAnak.setText("Anak-Anak");
        rbAnak.setIconTextGap(1);
        rbAnak.setName("rbAnak"); // NOI18N
        rbAnak.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbAnakMouseClicked(evt);
            }
        });
        PanelInput.add(rbAnak);
        rbAnak.setBounds(165, 102, 80, 23);

        buttonGroup1.add(rbDewasa);
        rbDewasa.setSelected(true);
        rbDewasa.setText("Dewasa");
        rbDewasa.setIconTextGap(1);
        rbDewasa.setName("rbDewasa"); // NOI18N
        rbDewasa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbDewasaMouseClicked(evt);
            }
        });
        PanelInput.add(rbDewasa);
        rbDewasa.setBounds(95, 102, 80, 23);

        FormInput.add(PanelInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        jml=0;
        for(i=0;i<tbTarif.getRowCount();i++){
            if(tbTarif.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdPtg.getText().equals("")||NmPtg.getText().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else if(Dokterperujuk.getText().equals("")){
            Valid.textKosong(Dokterperujuk,"Dokter Pengirim");
        }else if(Dokterpj.getText().equals("")){
            Valid.textKosong(Dokterpj,"Dokter Penanggung Jawab");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else if(jml==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                ChkJln.setSelected(false);
                try {                    
                    koneksi.setAutoCommit(false);
                    for(i=0;i<tbTarif.getRowCount();i++){ 
                        if(tbTarif.getValueAt(i,0).toString().equals("true")){
                            pssimpanperiksa.setString(1,TNoRw.getText());
                            pssimpanperiksa.setString(2,KdPtg.getText());
                            pssimpanperiksa.setString(3,tbTarif.getValueAt(i,1).toString());
                            pssimpanperiksa.setString(4,Tanggal.getSelectedItem().toString());
                            pssimpanperiksa.setString(5,CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                            pssimpanperiksa.setString(6,Dokterperujuk.getText());
                            pssimpanperiksa.setString(7,tbTarif.getValueAt(i,3).toString());
                            pssimpanperiksa.setString(8,Dokterpj.getText());
                            pssimpanperiksa.executeUpdate();                           
                        }                        
                    }                    
                    
                    for(i=0;i<tbPemeriksaan.getRowCount();i++){ 
                        if(!tbPemeriksaan.getValueAt(i,5).toString().equals("")){
                            pscariperawatan.setString(1,tbPemeriksaan.getValueAt(i,5).toString());
                            rscari=pscariperawatan.executeQuery();
                            if(rscari.next()){                                
                                psdetailpriksa.setString(1,TNoRw.getText());
                                psdetailpriksa.setString(2,rscari.getString(1));
                                psdetailpriksa.setString(3,Tanggal.getSelectedItem().toString());
                                psdetailpriksa.setString(4,CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
                                psdetailpriksa.setString(5,tbPemeriksaan.getValueAt(i,5).toString());
                                psdetailpriksa.setString(6,tbPemeriksaan.getValueAt(i,1).toString());
                                psdetailpriksa.setString(7,tbPemeriksaan.getValueAt(i,3).toString());
                                psdetailpriksa.setString(8,tbPemeriksaan.getValueAt(i,4).toString());
                                psdetailpriksa.setString(9,tbPemeriksaan.getValueAt(i,6).toString());
                                psdetailpriksa.executeUpdate();
                            }                            
                        }                        
                    }
                    koneksi.setAutoCommit(true);
                } catch (SQLException e) {
                    System.out.println(e);
                }                
            }        
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
               Valid.pindah(evt,Pemeriksaan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnPrint);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        jml=0;
        for(i=0;i<tbTarif.getRowCount();i++){
            if(tbTarif.getValueAt(i,0).toString().equals("true")){
                jml++;
            }
        }
        if(TNoRw.getText().equals("")||TNoRM.getText().equals("")||TPasien.getText().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KdPtg.getText().equals("")||NmPtg.getText().equals("")){
            Valid.textKosong(KdPtg,"Petugas");
        }else if(Dokterperujuk.getText().equals("")){
            Valid.textKosong(Dokterperujuk,"Dokter Pengirim");
        }else if(Dokterpj.getText().equals("")){
            Valid.textKosong(Dokterpj,"Dokter Penanggung Jawab");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else if(jml==0){
            Valid.textKosong(Pemeriksaan,"Data Pemeriksaan");
        }else{
            Sequel.queryu("delete from temporary");
            int row=tbPemeriksaan.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tbPemeriksaan.getValueAt(i,0).toString()+"','"+
                                tbPemeriksaan.getValueAt(i,1).toString()+"','"+
                                tbPemeriksaan.getValueAt(i,2).toString()+"','"+
                                tbPemeriksaan.getValueAt(i,3).toString()+"','"+
                                tbPemeriksaan.getValueAt(i,4).toString()+"','','','','','','','','','','','',''","Data User"); 
            }
            Map<String, Object> param = new HashMap<>();
            param.put("noperiksa",TNoRw.getText());
            param.put("norm",TNoRM.getText());
            param.put("namapasien",TPasien.getText());
            param.put("jkel",Jk.getText());
            param.put("umur",Umur.getText());
            param.put("pengirim",Dokterperujuk.getText());
            param.put("tanggal",Tanggal.getSelectedItem());
            param.put("penjab",Dokterpj.getText());
            param.put("petugas",NmPtg.getText());
            param.put("alamat",Alamat.getText());
            param.put("kamar",kamar);
            param.put("namakamar",namakamar);
            param.put("jam",CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
            Valid.MyReport("rptPeriksaLab.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                    "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
            ChkJln.setSelected(false);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
           // Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Pemeriksaan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void PemeriksaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemeriksaanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampiltarif();
        }else{
            Valid.pindah(evt,Tanggal,BtnSimpan);
        }
}//GEN-LAST:event_PemeriksaanKeyPressed

private void KdPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NmPtg,petugas.getTextField().getText());
        }else{            
            Valid.pindah(evt,Dokterpj,Dokterperujuk);
        }
}//GEN-LAST:event_KdPtgKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
    DlgCariPeriksaLab form=new DlgCariPeriksaLab(null,false);
    form.tampil();
    form.setSize(this.getWidth(),this.getHeight());
    form.setLocationRelativeTo(this);
    form.setVisible(true);
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
   try{
       tabMode.removeRow(tbPemeriksaan.getSelectedRow());
   }catch(Exception ex){
       JOptionPane.showMessageDialog(null,"Pilih dulu data yang mau dihapus..!!");
   }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void DokterperujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DokterperujukKeyPressed
        Valid.pindah(evt, KdPtg,Tanggal);
    }//GEN-LAST:event_DokterperujukKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
       tampiltarif();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void btnTarifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanLab tariflab=new DlgJnsPerawatanLab(null,false);
        tariflab.tampil();
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        tariflab.setLocationRelativeTo(internalFrame1);
        tariflab.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifActionPerformed

    private void PenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenjabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PenjabKeyPressed

    private void tbTarifMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTarifMouseClicked
        if(tabMode2.getRowCount()!=0){
            try {
                tampil();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTarifMouseClicked

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, Dokterperujuk, Pemeriksaan);
    }//GEN-LAST:event_TanggalKeyPressed

    private void DokterpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DokterpjKeyPressed
       Valid.pindah(evt,BtnKeluar,KdPtg);
    }//GEN-LAST:event_DokterpjKeyPressed

    private void rbDewasaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbDewasaMouseClicked
        tbTarifMouseClicked(evt);
    }//GEN-LAST:event_rbDewasaMouseClicked

    private void rbAnakMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbAnakMouseClicked
        tbTarifMouseClicked(evt);
    }//GEN-LAST:event_rbAnakMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPeriksaLaboratorium dialog = new DlgPeriksaLaboratorium(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox Dokterperujuk;
    private widget.TextBox Dokterpj;
    private javax.swing.JPanel FormInput;
    private widget.TextBox Jk;
    private widget.TextBox KdPtg;
    private widget.TextBox NmPtg;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox Pemeriksaan;
    private widget.TextBox Penjab;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.Button btnTarif;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel3;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass8;
    private widget.RadioButton rbAnak;
    private widget.RadioButton rbDewasa;
    private widget.Table tbPemeriksaan;
    private widget.Table tbTarif;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try {
            for(i=0;i<tbTarif.getRowCount();i++){ 
                if(tbTarif.getValueAt(i,0).toString().equals("true")){
                    tabMode.addRow(new Object[]{tbTarif.getValueAt(i,2).toString(),"","","","",""});
                    if(Jk.getText().equals("L")&&(rbDewasa.isSelected()==true)){
                        pstampil.setString(1,tbTarif.getValueAt(i,1).toString());
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            tabMode.addRow(new Object[]{"   "+rstampil.getString("Pemeriksaan"),"",
                                     rstampil.getString("satuan"),
                                     rstampil.getString("nilai_rujukan_ld"),"",
                                     rstampil.getString("id_template"),
                                     rstampil.getString("biaya_item")});
                        }                        
                    }else if(Jk.getText().equals("L")&&(rbAnak.isSelected()==true)){
                        pstampil2.setString(1,tbTarif.getValueAt(i,1).toString());
                        rstampil=pstampil2.executeQuery();
                        while(rstampil.next()){
                            tabMode.addRow(new Object[]{"   "+rstampil.getString("Pemeriksaan"),"",
                                     rstampil.getString("satuan"),
                                     rstampil.getString("nilai_rujukan_la"),"",
                                     rstampil.getString("id_template"),
                                     rstampil.getString("biaya_item")});
                        }                        
                    }else if(Jk.getText().equals("P")&&(rbDewasa.isSelected()==true)){
                        pstampil3.setString(1,tbTarif.getValueAt(i,1).toString());
                        rstampil=pstampil3.executeQuery();
                        while(rstampil.next()){
                            tabMode.addRow(new Object[]{"   "+rstampil.getString("Pemeriksaan"),"",
                                     rstampil.getString("satuan"),
                                     rstampil.getString("nilai_rujukan_pd"),"",
                                     rstampil.getString("id_template"),
                                     rstampil.getString("biaya_item")});
                        }                        
                    }else if(Jk.getText().equals("P")&&(rbAnak.isSelected()==true)){
                        pstampil4.setString(1,tbTarif.getValueAt(i,1).toString());
                        rstampil=pstampil4.executeQuery();
                        while(rstampil.next()){
                            tabMode.addRow(new Object[]{"   "+rstampil.getString("Pemeriksaan"),"",
                                     rstampil.getString("satuan"),
                                     rstampil.getString("nilai_rujukan_pa"),"",
                                     rstampil.getString("id_template"),
                                     rstampil.getString("biaya_item")});
                        }                        
                    }                              
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
    }
    public void emptTeks() {
        TNoRw.setText("");
        TNoRM.setText("");
        TPasien.setText("");
        Umur.setText("");
        Jk.setText("");
        Pemeriksaan.setText("");
        Dokterpj.requestFocus();  
    }

    private void isRawat(){
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
        Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",Penjab);
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=(select kd_dokter from reg_periksa where no_rawat='"+TNoRw.getText()+"') ",Dokterperujuk);
        
        kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+TNoRw.getText()+"' order by tgl_masuk desc limit 1");
        if(!kamar.equals("")){
            namakamar=Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                    " where kamar.kd_kamar='"+kamar+"' ");            
            kamar="Kamar";
        }else if(kamar.equals("")){
            kamar="Poli";
            namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                    "where reg_periksa.no_rawat='"+TNoRw.getText()+"'");
        }
        
        
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select jk from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select umur from pasien where no_rkm_medis=?",Umur,TNoRM.getText());
        Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ",Alamat,TNoRM.getText());
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    public void setNoRm(String norwt) {
        TNoRw.setText(norwt);
        Dokterpj.setText(Sequel.cariIsi("select * from set_pjlab"));
        isRawat();
        isPsien();   
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getperiksa_lab());
        BtnPrint.setEnabled(var.getperiksa_lab());
        BtnHapus.setEnabled(var.getperiksa_lab());
        btnTarif.setEnabled(var.gettindakan());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            FormInput.setPreferredSize(new Dimension(WIDTH,269));
            PanelInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            FormInput.setPreferredSize(new Dimension(WIDTH,20));
            PanelInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void tampiltarif() {          
        try{
            jml=0;
            for(i=0;i<tbTarif.getRowCount();i++){
                if(tbTarif.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            total=new String[jml];

            index=0; 
            for(i=0;i<tbTarif.getRowCount();i++){
                if(tbTarif.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTarif.getValueAt(i,1).toString();
                    nama[index]=tbTarif.getValueAt(i,2).toString();
                    total[index]=tbTarif.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabMode2);
            for(i=0;i<jml;i++){
                tabMode2.addRow(new Object[] {pilih[i],kode[i],nama[i],total[i]});
            }       
        
            pstarif.setString(1,"%"+Penjab.getText().trim()+"%");
            pstarif.setString(2,"%"+Pemeriksaan.getText().trim()+"%");
            pstarif.setString(3,"%"+Penjab.getText().trim()+"%");
            pstarif.setString(4,"%"+Pemeriksaan.getText().trim()+"%");            
            pstarif.setString(5,"%"+Penjab.getText().trim()+"%");
            pstarif.setString(6,"%"+Pemeriksaan.getText().trim()+"%");
            pstarif.setString(7,"%"+Penjab.getText().trim()+"%");
            pstarif.setString(8,"%"+Pemeriksaan.getText().trim()+"%");
            pstarif.setString(9,"%"+Penjab.getText().trim()+"%");            
            pstarif.setString(10,"%"+Pemeriksaan.getText().trim()+"%");

            rstarif=pstarif.executeQuery();
            while(rstarif.next()){                
                tabMode2.addRow(new Object[]{false,rstarif.getString(1),rstarif.getString(2),rstarif.getDouble(3)});
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }


}
