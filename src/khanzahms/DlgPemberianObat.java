/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPemberianObat.java
 *
 * Created on 27 Mei 10, 14:52:31
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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import inventory.DlgCariKonversi;
import inventory.DlgCariObat2;
import inventory.DlgCariObat3;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author perpustakaan
 */
public class DlgPemberianObat extends javax.swing.JDialog {
    private final DefaultTableModel tabModePO;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    public  DlgCariObat2 dlgobt=new DlgCariObat2(null,false);
    public  DlgCariObat3 dlgobt2=new DlgCariObat3(null,false);
    public  DlgCariObatPenyakit dlgobtpny=new DlgCariObatPenyakit(null,false);
    private DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    private DlgPasien pasien=new DlgPasien(null,false);
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Date date = new Date();
    private String now=dateFormat.format(date),bangsal="",tgl="",pas="",sql="";
    private PreparedStatement ps;
    private ResultSet rs;

    /** Creates new form DlgPemberianObat
     * @param parent
     * @param modal */
    public DlgPemberianObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Pemberian","Jam Pemberian","No.Rawat","No.Rekam Medis","Nama Pasien","Kode Penyakit",
                    "Nama Penyakit","Diagnosa","Kode Obat","Obat Penyakit","Tambahan","Jumlah","Biaya Obat","Total"};
        tabModePO=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPemberianObat.setModel(tabModePO);
        //tampilPO("");

        tbPemberianObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemberianObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbPemberianObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(130);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(60);
            }else if(i==12){
                column.setPreferredWidth(130);
            }else if(i==13){
                column.setPreferredWidth(130);
            }
        }
        tbPemberianObat.setDefaultRenderer(Object.class, new WarnaTable());


        this.setLocation(8,1);
        setSize(885,674);

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPny.setDocument(new batasInput((byte)10).getKata(TKdPny));
        TDiagnosa.setDocument(new batasInput((int)1500).getKata(TDiagnosa));
        TKdOb.setDocument(new batasInput((byte)15).getKata(TKdOb));
        TJumlah.setDocument(new batasInput((byte)25).getKata(TJumlah));
        TTambahan.setDocument(new batasInput((byte)15).getOnlyAngka(TTambahan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCariPasien.setDocument(new batasInput((byte)20).getKata(TCariPasien));     

        dlgobtpny.penyakit.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPny.setText(dlgobtpny.penyakit.getTextField().getText());     
                isPny();
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
        
        dlgobt.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdOb.setText(dlgobt.getTextField().getText());    
                isOb();
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
        
        dlgobt2.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdOb.setText(dlgobt2.getTextField().getText());    
                isOb();
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
       
        
        pasien.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               TCariPasien.setText(pasien.getTextField().getText());                               
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
        
        ChkInput.setSelected(false);
        //isForm(); 
        jam();   
    }

    //private DlgCariObatPenyakit dlgobtpny=new DlgCariObatPenyakit(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPemberianObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel19 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCariPasien = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        jLabel8 = new widget.Label();
        TDiagnosa = new widget.TextBox();
        TJumlah = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        TNmPny = new widget.TextBox();
        DTPBeri = new widget.Tanggal();
        jLabel13 = new widget.Label();
        TKdOb = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        TNmOb = new widget.TextBox();
        TTambahan = new widget.TextBox();
        jLabel15 = new widget.Label();
        TCiriPny = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel11 = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        TBiayaObat = new widget.TextBox();
        jLabel17 = new widget.Label();
        TSatuan = new widget.TextBox();
        TTotal = new widget.TextBox();
        jLabel18 = new widget.Label();
        ChkJln = new widget.CekBox();
        BtnSeek5 = new widget.Button();
        BtnSeek6 = new widget.Button();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail Pemberian Obat/Barang/Alkes/Perlengkapan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPemberianObat.setAutoCreateRowSorter(true);
        tbPemberianObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPemberianObat.setName("tbPemberianObat"); // NOI18N
        tbPemberianObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPemberianObatMouseClicked(evt);
            }
        });
        tbPemberianObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPemberianObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPemberianObat);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
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
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass8.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass8.add(LCount);

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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel14.setText("Tgl.Beli :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(58, 23));
        panelGlass9.add(jLabel14);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari1);

        jLabel19.setText("s.d");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(18, 23));
        panelGlass9.add(jLabel19);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(DTPCari2);

        jLabel16.setText("No.RM :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(jLabel16);

        TCariPasien.setName("TCariPasien"); // NOI18N
        TCariPasien.setPreferredSize(new java.awt.Dimension(130, 23));
        TCariPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPasienKeyPressed(evt);
            }
        });
        panelGlass9.add(TCariPasien);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("Alt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        panelGlass9.add(BtnSeek4);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(865, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(-2, 12, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(81, 12, 175, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(258, 12, 143, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(25, 28));
        FormInput.add(TPasien);
        TPasien.setBounds(403, 12, 425, 23);

        jLabel7.setText("Tgl.Beri :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(450, 72, 59, 23);

        jLabel8.setText("Diagnosa :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(-2, 72, 80, 23);

        TDiagnosa.setHighlighter(null);
        TDiagnosa.setName("TDiagnosa"); // NOI18N
        TDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(TDiagnosa);
        TDiagnosa.setBounds(81, 72, 325, 23);

        TJumlah.setHighlighter(null);
        TJumlah.setName("TJumlah"); // NOI18N
        TJumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TJumlahKeyPressed(evt);
            }
        });
        FormInput.add(TJumlah);
        TJumlah.setBounds(81, 132, 90, 23);

        jLabel9.setText("Jumlah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(-2, 132, 80, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(-2, 42, 80, 23);

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(81, 42, 118, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("Alt+2");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(800, 42, 28, 23);

        TNmPny.setEditable(false);
        TNmPny.setName("TNmPny"); // NOI18N
        FormInput.add(TNmPny);
        TNmPny.setBounds(201, 42, 260, 23);

        DTPBeri.setEditable(false);
        DTPBeri.setForeground(new java.awt.Color(50, 70, 50));
        DTPBeri.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPBeri.setDisplayFormat("yyyy-MM-dd");
        DTPBeri.setName("DTPBeri"); // NOI18N
        DTPBeri.setOpaque(false);
        DTPBeri.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPBeri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPBeriKeyPressed(evt);
            }
        });
        FormInput.add(DTPBeri);
        DTPBeri.setBounds(512, 72, 100, 23);

        jLabel13.setText("Obt/Alkes :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(-2, 102, 80, 23);

        TKdOb.setName("TKdOb"); // NOI18N
        TKdOb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdObKeyPressed(evt);
            }
        });
        FormInput.add(TKdOb);
        TKdOb.setBounds(81, 102, 90, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('4');
        BtnSeek2.setToolTipText("Alt+4");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(545, 102, 28, 23);

        TNmOb.setEditable(false);
        TNmOb.setName("TNmOb"); // NOI18N
        FormInput.add(TNmOb);
        TNmOb.setBounds(173, 102, 288, 23);

        TTambahan.setHighlighter(null);
        TTambahan.setName("TTambahan"); // NOI18N
        TTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTambahanKeyPressed(evt);
            }
        });
        FormInput.add(TTambahan);
        TTambahan.setBounds(363, 132, 118, 23);

        jLabel15.setText("Biaya Tambahan Rp.");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(230, 132, 130, 23);

        TCiriPny.setEditable(false);
        TCiriPny.setName("TCiriPny"); // NOI18N
        FormInput.add(TCiriPny);
        TCiriPny.setBounds(463, 42, 335, 23);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('3');
        BtnSeek3.setToolTipText("Alt+3");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek3);
        BtnSeek3.setBounds(408, 72, 28, 23);

        jLabel11.setText("Jam :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(607, 72, 40, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setOpaque(false);
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(650, 72, 48, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setOpaque(false);
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(700, 72, 48, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setOpaque(false);
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(750, 72, 48, 23);

        TBiayaObat.setHighlighter(null);
        TBiayaObat.setName("TBiayaObat"); // NOI18N
        TBiayaObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBiayaObatKeyPressed(evt);
            }
        });
        FormInput.add(TBiayaObat);
        TBiayaObat.setBounds(710, 102, 118, 23);

        jLabel17.setText("Harga Obat Rp.");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(610, 102, 97, 23);

        TSatuan.setEditable(false);
        TSatuan.setName("TSatuan"); // NOI18N
        TSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSatuanKeyPressed(evt);
            }
        });
        FormInput.add(TSatuan);
        TSatuan.setBounds(463, 102, 80, 23);

        TTotal.setEditable(false);
        TTotal.setHighlighter(null);
        TTotal.setName("TTotal"); // NOI18N
        TTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTotalKeyPressed(evt);
            }
        });
        FormInput.add(TTotal);
        TTotal.setBounds(633, 132, 130, 23);

        jLabel18.setText("Total Biaya Rp.");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(500, 132, 130, 23);

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
        FormInput.add(ChkJln);
        ChkJln.setBounds(800, 72, 23, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('2');
        BtnSeek5.setToolTipText("Alt+2");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(173, 132, 28, 23);

        BtnSeek6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek6.setMnemonic('4');
        BtnSeek6.setToolTipText("Alt+4");
        BtnSeek6.setName("BtnSeek6"); // NOI18N
        BtnSeek6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek6ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek6);
        BtnSeek6.setBounds(575, 102, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampilPO();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,TCari,TKdPny);
}//GEN-LAST:event_TNoRwKeyPressed

    private void TDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDiagnosaKeyPressed
        Valid.pindah(evt,TKdPny,DTPBeri);
}//GEN-LAST:event_TDiagnosaKeyPressed

    private void TJumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TJumlahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TKdOb.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TTambahan.requestFocus();
        }
}//GEN-LAST:event_TJumlahKeyPressed

    private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPny();
        }else{            
            Valid.pindah(evt,TNoRw,TDiagnosa);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        dlgobtpny.penyakit.emptTeks();
        dlgobtpny.penyakit.isCek();
        dlgobtpny.penyakit.tampil();
        dlgobtpny.penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dlgobtpny.penyakit.setLocationRelativeTo(internalFrame1);
        dlgobtpny.penyakit.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void DTPBeriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPBeriKeyPressed
        Valid.pindah(evt,TDiagnosa,cmbJam);
}//GEN-LAST:event_DTPBeriKeyPressed

    private void TKdObKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdObKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isOb();
        }else{            
            Valid.pindah(evt,cmbDtk,TJumlah);
        }
    }//GEN-LAST:event_TKdObKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else {
            dlgobt.emptTeks();
            dlgobt.isCek();
            dlgobt.setNoRm(TNoRw.getText(),TKdPny.getText(),DTPBeri.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),false);
            dlgobt.tampil();
            dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgobt.setLocationRelativeTo(internalFrame1);
            dlgobt.setVisible(true);
        }        
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void TTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTambahanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TJumlah.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }
    }//GEN-LAST:event_TTambahanKeyPressed

    private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        dlgobtpny.setNoRm(TNoRw.getText(),TNmPny.getText());
        dlgobtpny.tampil();
        dlgobtpny.emptTeks();
        dlgobtpny.isCek();
        dlgobtpny.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dlgobtpny.setLocationRelativeTo(internalFrame1);
        dlgobtpny.setVisible(true);
    }//GEN-LAST:event_BtnSeek3ActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        bangsal=Sequel.cariIsi("select kamar.kd_bangsal from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? "+
                "order by tgl_masuk desc limit 1",TNoRw.getText());
        if(bangsal.equals("")){
            bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
        }
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdOb.getText().trim().equals("")||TNmOb.getText().trim().equals("")){
            Valid.textKosong(TKdOb,"databarang penyakit");
        }else if(TJumlah.getText().trim().equals("")){
            Valid.textKosong(TJumlah,"Jumlah Obat");
        }else if(TTambahan.getText().trim().equals("")){
            Valid.textKosong(TTambahan,"Biaya Tambahan");
        }else{          
            try {
                koneksi.prepareStatement("insert into detail_pemberian_obat values('"+DTPBeri.getSelectedItem()+"','"+
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                            TNoRw.getText()+"','"+
                            TKdPny.getText()+"','"+
                            TDiagnosa.getText()+"','"+
                            TKdOb.getText()+"','"+
                            TBiayaObat.getText()+"','"+
                            TJumlah.getText()+"','"+
                            TTambahan.getText()+"',(("+TBiayaObat.getText()+"*"+TJumlah.getText()+")+"+TTambahan.getText()+"))").execute();
                //Sequel.mengedit("databarang","kode_brng='"+TKdOb.getText()+"'","stok=stok-"+TJumlah.getText()+"");
                Sequel.menyimpan("gudangbarang","'"+TKdOb.getText()+"','"+bangsal+"','-"+TJumlah.getText()+"'", 
                                        "stok=stok-'"+TJumlah.getText()+"'","kode_brng='"+TKdOb.getText()+"' and kd_bangsal='"+bangsal+"'");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
            
            tampilPO();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TTambahan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        bangsal=Sequel.cariIsi("select kamar.kd_bangsal from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? "+
                "order by tgl_masuk desc limit 1",TNoRw.getText());
        if(bangsal.equals("")){
            bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
        }
        if(tabModePO.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+TNoRw.getText()+"' and kd_penyakit='"+TKdPny.getText()+"' and kode_brng='"+TKdOb.getText()+
                             "' and tgl_perawatan='"+DTPBeri.getSelectedItem()+"' and jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'");
                //Sequel.mengedit("databarang","kode_brng='"+TKdOb.getText()+"'","stok=stok+"+TJumlah.getText()+"");
                Sequel.menyimpan("gudangbarang","'"+TKdOb.getText()+"','"+bangsal+"','"+TJumlah.getText()+"'", 
                                        "stok=stok+'"+TJumlah.getText()+"'","kode_brng='"+TKdOb.getText()+"' and kd_bangsal='"+bangsal+"'");
                tampilPO();
            }catch(Exception e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampilPO();
        if(tabModePO.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabModePO.getRowCount()!=0){
            if(!TCariPasien.getText().equals("")){
               pas=" and reg_periksa.no_rkm_medis='"+TCariPasien.getText()+"' "; 
            }
            tgl=" detail_pemberian_obat.tgl_perawatan between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' "+pas;        
            Valid.MyReport("rptBrObt.jrxml","report","::[ Rekam Data Pemberian Obat (UMUM) ]::","select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                   "detail_pemberian_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "detail_pemberian_obat.kd_penyakit,penyakit.nm_penyakit,detail_pemberian_obat.diagnosa, "+
                   "detail_pemberian_obat.kode_brng,databarang.nama_brng,detail_pemberian_obat.tambahan,"+
                   "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.total "+
                   "from detail_pemberian_obat inner join reg_periksa inner join pasien inner join penyakit inner join databarang "+
                   "on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and detail_pemberian_obat.kd_penyakit=penyakit.kd_penyakit "+
                   "and detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                   "where  "+tgl+"and tgl_perawatan like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and detail_pemberian_obat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and detail_pemberian_obat.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and detail_pemberian_obat.diagnosa like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and detail_pemberian_obat.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                   tgl+"and databarang.nama_brng like '%"+TCari.getText().trim()+"%' "+
                   "order by detail_pemberian_obat.tgl_perawatan");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TCariPasien.setText("");
        tampilPO();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TCariPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPasienKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilPO();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSeek4.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            DTPCari2.requestFocus();
        }
    }//GEN-LAST:event_TCariPasienKeyPressed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        var.setStatus(true);
        pasien.tampil();
        pasien.emptTeks();    
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        Valid.pindah(evt,TCariPasien,DTPCari1);
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPBeri,cmbMnt);
}//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
}//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,TKdOb);
}//GEN-LAST:event_cmbDtkKeyPressed

    private void tbPemberianObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPemberianObatMouseClicked
        if(tabModePO.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
           
        }
}//GEN-LAST:event_tbPemberianObatMouseClicked

    private void tbPemberianObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPemberianObatKeyPressed
        if(tabModePO.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbPemberianObatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void TBiayaObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBiayaObatKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TBiayaObatKeyPressed

private void TSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSatuanKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TSatuanKeyPressed

private void TTotalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTotalKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TTotalKeyPressed

private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
bangsal=Sequel.cariIsi("select kamar.kd_bangsal from kamar inner join kamar_inap "+
                "on kamar.kd_kamar=kamar_inap.kd_kamar where kamar_inap.no_rawat=? "+
                "order by tgl_masuk desc limit 1",TNoRw.getText());
if(bangsal.equals("")){
    bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
}
if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdOb.getText().trim().equals("")||TNmOb.getText().trim().equals("")){
            Valid.textKosong(TKdOb,"databarang penyakit");
        }else if(bangsal.equals("")){
            Valid.textKosong(TCari,"Lokasi");
        }else if(TJumlah.getText().trim().equals("")){
            Valid.textKosong(TJumlah,"Jumlah Obat");
        }else if(TTambahan.getText().trim().equals("")){
            Valid.textKosong(TTambahan,"Biaya Tambahan");
        }else{          
            try {        
                Sequel.queryu("delete from detail_pemberian_obat where no_rawat='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),2).toString()+
                              "' and kd_penyakit='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),5).toString()+
                              "' and kode_brng='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),8).toString()+
                              "' and tgl_perawatan='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),0).toString()+
                              "' and jam='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(0,2)+
                              ":"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(3,5)+
                              ":"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(6,8)+"'");
                //Sequel.mengedit("databarang","kode_brng='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),8).toString()+
                //               "'","stok=stok+"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),11).toString()+"");
                
                Sequel.menyimpan("gudangbarang","'"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),8).toString()+"','"+bangsal+"','"+TJumlah.getText()+"'", 
                                        "stok=stok+'"+TJumlah.getText()+"'","kode_brng='"+tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),8).toString()+"' and kd_bangsal='"+bangsal+"'");
                koneksi.prepareStatement("insert into detail_pemberian_obat values('"+DTPBeri.getSelectedItem()+"','"+
                            cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"','"+
                            TNoRw.getText()+"','"+
                            TKdPny.getText()+"','"+
                            TDiagnosa.getText()+"','"+
                            TKdOb.getText()+"','"+
                            TBiayaObat.getText()+"','"+
                            TJumlah.getText()+"','"+
                            TTambahan.getText()+"',(("+TBiayaObat.getText()+"*"+TJumlah.getText()+")+"+TTambahan.getText()+"))").execute();                
                //Sequel.mengedit("databarang","kode_brng='"+TKdOb.getText()+"'","stok=stok-"+TJumlah.getText()+"");
                Sequel.menyimpan("gudangbarang","'"+TKdOb.getText()+"','"+bangsal+"','-"+TJumlah.getText()+"'", 
                                        "stok=stok-'"+TJumlah.getText()+"'","kode_brng='"+TKdOb.getText()+"' and kd_bangsal='"+bangsal+"'");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
            
            tampilPO();
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnSeek6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek6ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else {
            dlgobt2.setNoRm(TNoRw.getText(),TKdPny.getText(),DTPBeri.getDate());
            dlgobt2.tampil();
            dlgobt2.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgobt2.setLocationRelativeTo(internalFrame1);
            dlgobt2.setVisible(true);
        } 
    }//GEN-LAST:event_BtnSeek6ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPemberianObat dialog = new DlgPemberianObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek6;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.Tanggal DTPBeri;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TBiayaObat;
    private widget.TextBox TCari;
    private widget.TextBox TCariPasien;
    private widget.TextBox TCiriPny;
    private widget.TextBox TDiagnosa;
    private widget.TextBox TJumlah;
    private widget.TextBox TKdOb;
    private widget.TextBox TKdPny;
    private widget.TextBox TNmOb;
    private widget.TextBox TNmPny;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TSatuan;
    private widget.TextBox TTambahan;
    private widget.TextBox TTotal;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPemberianObat;
    // End of variables declaration//GEN-END:variables

    public void tampilPO() {
        pas="";
        if(!TCariPasien.getText().equals("")){
           pas=" and reg_periksa.no_rkm_medis='"+TCariPasien.getText()+"' "; 
        }
        tgl=" detail_pemberian_obat.tgl_perawatan between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' "+pas;        
        sql="select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                   "detail_pemberian_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "detail_pemberian_obat.kd_penyakit,penyakit.nm_penyakit,detail_pemberian_obat.diagnosa, "+
                   "detail_pemberian_obat.kode_brng,databarang.nama_brng,detail_pemberian_obat.tambahan,"+
                   "detail_pemberian_obat.jml,detail_pemberian_obat.biaya_obat,detail_pemberian_obat.total "+
                   "from detail_pemberian_obat inner join reg_periksa inner join pasien inner join penyakit inner join databarang "+
                   "on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and detail_pemberian_obat.kd_penyakit=penyakit.kd_penyakit "+
                   "and detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                   "where  "+tgl+"and tgl_perawatan like ? or "+
                   tgl+"and detail_pemberian_obat.no_rawat like ? or "+
                   tgl+"and reg_periksa.no_rkm_medis like ? or "+
                   tgl+"and pasien.nm_pasien like ? or "+
                   tgl+"and detail_pemberian_obat.kd_penyakit like ? or "+
                   tgl+"and penyakit.nm_penyakit like ? or "+
                   tgl+"and detail_pemberian_obat.diagnosa like ? or "+
                   tgl+"and detail_pemberian_obat.kode_brng like ? or "+
                   tgl+"and databarang.nama_brng like ? "+
                   "order by detail_pemberian_obat.tgl_perawatan";
        prosesCariPO(sql);
    }

    private void prosesCariPO(String sql) {
        Valid.tabelKosong(tabModePO);
        try{
            ps=koneksi.prepareStatement(sql);
            ps.setString(1,"%"+TCari.getText().trim()+"%");
            ps.setString(2,"%"+TCari.getText().trim()+"%");
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,"%"+TCari.getText().trim()+"%");
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,"%"+TCari.getText().trim()+"%");
            ps.setString(8,"%"+TCari.getText().trim()+"%");
            ps.setString(9,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                Object[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               Valid.SetAngka(rs.getDouble(11)),
                               rs.getDouble(12),
                               Valid.SetAngka(rs.getDouble(13)),
                               Valid.SetAngka3(rs.getDouble(14))};
                tabModePO.addRow(data);
            }
           // rs.close();
           // stat.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModePO.getRowCount());
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void isPny() {
        Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
        Sequel.cariIsi("select ciri_ciri from penyakit where kd_penyakit=? ",TCiriPny,TKdPny.getText());
    }

    private void isOb() {       
       Sequel.cariIsi("select nama_brng from databarang where kode_brng=? ",TNmOb,TKdOb.getText());
       Sequel.cariIsi("select h_retail from databarang where kode_brng=? ",TBiayaObat,TKdOb.getText());
       Sequel.cariIsi("select kode_sat from databarang where kode_brng=? ",TSatuan,TKdOb.getText());
    }

    public void emptTeks() {
        //TNoRw.setText("");
        //TNoRM.setText("");
        //TPasien.setText("");
        //TKdPny.setText("");
        //TNmPny.setText("");
        TKdOb.setText("");
        TNmOb.setText("");
        //TDiagnosa.setText("");
        TJumlah.setText("0");
        TTotal.setText("0");
        TTambahan.setText("0");
        TBiayaObat.setText("0");
        TNoRw.requestFocus();
        DTPBeri.setDate(new Date());
        TNoRw.requestFocus();
    }

    private void getData() {
        if(tbPemberianObat.getSelectedRow()!= -1){
            cmbJam.setSelectedItem(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(0,2));
            cmbMnt.setSelectedItem(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(3,5));
            cmbDtk.setSelectedItem(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),1).toString().substring(6,8));
            TNoRw.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),2).toString());
            isRawat();
            isPsien();
            TKdPny.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),5).toString());
            isPny();
            TDiagnosa.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),7).toString());
            TKdOb.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),8).toString());
            isOb();
            TTambahan.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),10).toString().replaceAll("\\.",""));
            TJumlah.setText(tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),11).toString());
            Valid.SetTgl(DTPBeri,tbPemberianObat.getValueAt(tbPemberianObat.getSelectedRow(),0).toString());
        }
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        isRawat();
        isPsien();   
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        TKdPny.setText("-");
        TNmPny.setText("-");
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,188));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(var.getberiobat());
        BtnHapus.setEnabled(var.getberiobat());
        BtnEdit.setEnabled(var.getberiobat());
        BtnPrint.setEnabled(var.getberiobat());
        
    }
    
    private void isjml(){
        Sequel.cariIsi("select ("+TBiayaObat.getText()+"*"+TJumlah.getText()+")+"+TTambahan.getText()+"", TTotal);
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
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
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
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }


}
