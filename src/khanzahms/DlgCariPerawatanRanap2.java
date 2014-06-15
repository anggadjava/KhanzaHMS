/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPenyakit.java
 *
 * Created on May 23, 2010, 12:57:16 AM
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
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class DlgCariPerawatanRanap2 extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psjenisperawatan,psinputrawatdr,psinputrawatpr,pstampil,pstindakan,pstindakan2,pshapustindakan,pshapustindakan2;
    private ResultSet rs,rstindakan;
    private String pilihtable="",kd_pj="",kd_bangsal="";
    private boolean[] pagi,siang,sore,malam; 
    private boolean pg=false,sg=false,sr=false,mlm=false;
    private double biaya=0;
    private String[] kode,nama,kategori,totaldr,totalpr;
    private int jml=0,i=0,index=0;
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);
    public  DlgJnsPerawatanRanap perawatan=new DlgJnsPerawatanRanap(null,false);
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariPerawatanRanap2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Pagi","Siang","Sore","Malam","Kode","Nama Perawatan","Kategori Perawatan","Tarif Dokter","Tarif Perawat"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==1)||(colIndex==2)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Boolean.class,java.lang.Boolean.class,java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /*for (i = 0; i < 4; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }*/
        for (i = 0; i < 9; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(40);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(450);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        THasil.setDocument(new batasInput((byte)100).getKata(THasil));
        TPrkmbngn.setDocument(new batasInput((byte)100).getKata(TPrkmbngn));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)5).getKata(TTensi));
        TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                tampil();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                tampil();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                tampil();
            }

        });
        
        
        dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KdDok.setText(dokter.getTextField().getText());   
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,dokter.getTextField().getText());
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
        
        petugas.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                KdDok.setText(petugas.getTextField().getText());   
                Sequel.cariIsi("select nama from petugas where nip=?",TDokter,petugas.getTextField().getText());
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
        
        dlgpnykt.getTabel().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPny.setText(dlgpnykt.getTextField().getText());     
                Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
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
             psjenisperawatan=koneksi.prepareStatement("select total_byrdr,total_byrpr from jns_perawatan_inap where kd_jenis_prw=?");   
             pstampil=koneksi.prepareStatement("select jns_perawatan_inap.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan_inap.material,jns_perawatan_inap.tarif_tindakandr,jns_perawatan_inap.tarif_tindakanpr,"+
                   "jns_perawatan_inap.total_byrdr,jns_perawatan_inap.total_byrpr "+
                   "from jns_perawatan_inap inner join kategori_perawatan "+
                   "on jns_perawatan_inap.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan_inap.kd_pj=? and jns_perawatan_inap.kd_bangsal=? and jns_perawatan_inap.kd_jenis_prw like ? or "+
                    " jns_perawatan_inap.kd_pj=? and jns_perawatan_inap.kd_bangsal=? and jns_perawatan_inap.nm_perawatan like ? or "+
                    " jns_perawatan_inap.kd_pj=? and jns_perawatan_inap.kd_bangsal=? and kategori_perawatan.nm_kategori like ? order by jns_perawatan_inap.nm_perawatan");
             psinputrawatdr=koneksi.prepareStatement("insert into rawat_inap_dr values(?,?,?,?,?,?,?,?,?,?,?)");
             psinputrawatpr=koneksi.prepareStatement("insert into rawat_inap_pr values(?,?,?,?,?,?,?,?,?,?,?)");
             pstindakan=koneksi.prepareStatement("select rawat_inap_dr.kd_jenis_prw from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+
                    "and rawat_inap_dr.tgl_perawatan=? and rawat_inap_dr.kd_jenis_prw=? and rawat_inap_dr.jam_rawat between ? and ?");
             pstindakan2=koneksi.prepareStatement("select rawat_inap_pr.kd_jenis_prw from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+
                    "and rawat_inap_pr.tgl_perawatan=? and rawat_inap_pr.kd_jenis_prw=? and rawat_inap_pr.jam_rawat between ? and ?");
             pshapustindakan=koneksi.prepareStatement("delete from rawat_inap_dr where rawat_inap_dr.no_rawat=? "+
                    "and rawat_inap_dr.tgl_perawatan=? and rawat_inap_dr.kd_jenis_prw=? and rawat_inap_dr.jam_rawat=?");
             pshapustindakan2=koneksi.prepareStatement("delete from rawat_inap_pr where rawat_inap_pr.no_rawat=? "+
                    "and rawat_inap_pr.tgl_perawatan=? and rawat_inap_pr.kd_jenis_prw=? and rawat_inap_pr.jam_rawat=?");
        }catch(SQLException e){
            System.out.println(e);
        }
        
        TCari.requestFocus();
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppDokter = new javax.swing.JMenuItem();
        ppPetugas = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        TNoRw = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSimpan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TSuhu = new widget.TextBox();
        jLabel7 = new widget.Label();
        TTensi = new widget.TextBox();
        jLabel8 = new widget.Label();
        THasil = new widget.TextBox();
        TPrkmbngn = new widget.TextBox();
        jLabel9 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        TNmPny = new widget.TextBox();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        jLabel10 = new widget.Label();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(102, 51, 0));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Centang/Tindakan Terpilih");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(250, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppDokter.setBackground(new java.awt.Color(255, 255, 255));
        ppDokter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppDokter.setForeground(new java.awt.Color(102, 51, 0));
        ppDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppDokter.setText("Ubah Ke Tindakan Dokter");
        ppDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDokter.setIconTextGap(8);
        ppDokter.setName("ppDokter"); // NOI18N
        ppDokter.setPreferredSize(new java.awt.Dimension(250, 25));
        ppDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDokterActionPerformed(evt);
            }
        });
        Popup.add(ppDokter);

        ppPetugas.setBackground(new java.awt.Color(255, 255, 255));
        ppPetugas.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppPetugas.setForeground(new java.awt.Color(102, 51, 0));
        ppPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        ppPetugas.setText("Ubah Ke Tindakan Petugas");
        ppPetugas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPetugas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPetugas.setIconTextGap(8);
        ppPetugas.setName("ppPetugas"); // NOI18N
        ppPetugas.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPetugasActionPerformed(evt);
            }
        });
        Popup.add(ppPetugas);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tarif Tagihan/Perawatan/Tindakan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKamar.setComponentPopupMenu(Popup);
        tbKamar.setName("tbKamar"); // NOI18N
        tbKamar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKamarMouseClicked(evt);
            }
        });
        tbKamar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKamarKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('4');
        BtnKeluar.setToolTipText("Alt+4");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(710, 104));
        FormInput.setLayout(null);

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(696, 40, 50, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        FormInput.add(TSuhu);
        TSuhu.setBounds(625, 40, 56, 23);

        jLabel7.setText("Suhu Badan :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(543, 40, 80, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        FormInput.add(TTensi);
        TTensi.setBounds(748, 40, 56, 23);

        jLabel8.setText("Hasil Periksa :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(null);
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 70, 90, 23);

        THasil.setHighlighter(null);
        THasil.setName("THasil"); // NOI18N
        THasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THasilKeyPressed(evt);
            }
        });
        FormInput.add(THasil);
        THasil.setBounds(93, 70, 275, 23);

        TPrkmbngn.setHighlighter(null);
        TPrkmbngn.setName("TPrkmbngn"); // NOI18N
        TPrkmbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPrkmbngnKeyPressed(evt);
            }
        });
        FormInput.add(TPrkmbngn);
        TPrkmbngn.setBounds(522, 70, 282, 23);

        jLabel9.setText("Perkembangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(419, 70, 100, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-26" }));
        DTPTgl.setDisplayFormat("yyyy-MM-dd");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(625, 10, 130, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 40, 90, 23);

        TKdPny.setText("-");
        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(93, 40, 100, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('2');
        BtnSeek2.setToolTipText("Alt+2");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
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
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(486, 40, 28, 23);

        TNmPny.setEditable(false);
        TNmPny.setText("-");
        TNmPny.setName("TNmPny"); // NOI18N
        FormInput.add(TNmPny);
        TNmPny.setBounds(194, 40, 290, 23);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 90, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        FormInput.add(KdDok);
        KdDok.setBounds(93, 10, 100, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(194, 10, 290, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(486, 10, 28, 23);

        jLabel10.setText("Tanggal :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(543, 10, 80, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbKamarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKamarMouseClicked
        if(tbKamar.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbKamarMouseClicked

    private void tbKamarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKamarKeyPressed
        if(tbKamar.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbKamar.getSelectedColumn();
                    if(i==1){
                        int baris=tbKamar.getSelectedRow();
                        if(baris>-1){
                          tbKamar.setValueAt(true,tbKamar.getSelectedRow(),0);   
                        }                               
                        TCari.setText("");
                        TCari.requestFocus();
                    }                    
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbKamarKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //perawatan.setModal(true);
        perawatan.emptTeks();
        perawatan.tampil();
        perawatan.isCek();
        perawatan.setSize(internalFrame1.getWidth()+40,internalFrame1.getHeight()+40);
        perawatan.setLocationRelativeTo(internalFrame1);
        perawatan.setAlwaysOnTop(false);
        perawatan.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")||KdDok.getText().trim().equals("")){
            Valid.textKosong(TCari,"Penyakit & Dokter");
        }else{
            try {          
                if(pilihtable.equals("rawat_inap_dr")||pilihtable.equals("rawat_inap_pr")){
                    koneksi.setAutoCommit(false);   
                    for(i=0;i<tbKamar.getRowCount();i++){                                                              
                        if(tbKamar.getValueAt(i,0).toString().equals("true")||tbKamar.getValueAt(i,1).toString().equals("true")||tbKamar.getValueAt(i,2).toString().equals("true")||tbKamar.getValueAt(i,3).toString().equals("true")){
                            psjenisperawatan.setString(1,tbKamar.getValueAt(i,4).toString());
                            rs=psjenisperawatan.executeQuery();
                            if(rs.next()){
                                switch (pilihtable) {
                                    case "rawat_inap_dr":    
                                        biaya=rs.getDouble(1);
                                        if(tbKamar.getValueAt(i,0).toString().equals("true")){
                                            try {                                                
                                                psinputrawatdr.setString(1,TNoRw.getText());
                                                psinputrawatdr.setString(2,TKdPny.getText());
                                                psinputrawatdr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdr.setString(4,KdDok.getText());
                                                psinputrawatdr.setString(5,TSuhu.getText());
                                                psinputrawatdr.setString(6,TTensi.getText());
                                                psinputrawatdr.setString(7,THasil.getText());
                                                psinputrawatdr.setString(8,TPrkmbngn.getText());
                                                psinputrawatdr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatdr.setString(10,"07:00:00");
                                                psinputrawatdr.setDouble(11,biaya);
                                                psinputrawatdr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan.setString(1,TNoRw.getText());
                                            pshapustindakan.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan.setString(4,"07:00:00");
                                            pshapustindakan.executeUpdate();                                            
                                        }
                                        
                                        if(tbKamar.getValueAt(i,1).toString().equals("true")){
                                            try {
                                                psinputrawatdr.setString(1,TNoRw.getText());
                                                psinputrawatdr.setString(2,TKdPny.getText());
                                                psinputrawatdr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdr.setString(4,KdDok.getText());
                                                psinputrawatdr.setString(5,TSuhu.getText());
                                                psinputrawatdr.setString(6,TTensi.getText());
                                                psinputrawatdr.setString(7,THasil.getText());
                                                psinputrawatdr.setString(8,TPrkmbngn.getText());
                                                psinputrawatdr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatdr.setString(10,"12:00:00");
                                                psinputrawatdr.setDouble(11,biaya);
                                                psinputrawatdr.executeUpdate();
                                            } catch (Exception e) {
                                            }                                            
                                        }else{
                                            pshapustindakan.setString(1,TNoRw.getText());
                                            pshapustindakan.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan.setString(4,"12:00:00");
                                            pshapustindakan.executeUpdate();                                            
                                        }
                                        
                                        if(tbKamar.getValueAt(i,2).toString().equals("true")){
                                            try {
                                                psinputrawatdr.setString(1,TNoRw.getText());
                                                psinputrawatdr.setString(2,TKdPny.getText());
                                                psinputrawatdr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdr.setString(4,KdDok.getText());
                                                psinputrawatdr.setString(5,TSuhu.getText());
                                                psinputrawatdr.setString(6,TTensi.getText());
                                                psinputrawatdr.setString(7,THasil.getText());
                                                psinputrawatdr.setString(8,TPrkmbngn.getText());
                                                psinputrawatdr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatdr.setString(10,"16:00:00");
                                                psinputrawatdr.setDouble(11,biaya);
                                                psinputrawatdr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan.setString(1,TNoRw.getText());
                                            pshapustindakan.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan.setString(4,"16:00:00");
                                            pshapustindakan.executeUpdate();                                            
                                        }
                                        
                                        if(tbKamar.getValueAt(i,3).toString().equals("true")){
                                            try {
                                                psinputrawatdr.setString(1,TNoRw.getText());
                                                psinputrawatdr.setString(2,TKdPny.getText());
                                                psinputrawatdr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatdr.setString(4,KdDok.getText());
                                                psinputrawatdr.setString(5,TSuhu.getText());
                                                psinputrawatdr.setString(6,TTensi.getText());
                                                psinputrawatdr.setString(7,THasil.getText());
                                                psinputrawatdr.setString(8,TPrkmbngn.getText());
                                                psinputrawatdr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatdr.setString(10,"20:00:00");
                                                psinputrawatdr.setDouble(11,biaya);
                                                psinputrawatdr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan.setString(1,TNoRw.getText());
                                            pshapustindakan.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan.setString(4,"20:00:00");
                                            pshapustindakan.executeUpdate();                                            
                                        }
                                        
                                        break;
                                    case "rawat_inap_pr":                 
                                        biaya=rs.getDouble(2);                                        
                                        if(tbKamar.getValueAt(i,0).toString().equals("true")){
                                            try {
                                                psinputrawatpr.setString(1,TNoRw.getText());
                                                psinputrawatpr.setString(2,TKdPny.getText());
                                                psinputrawatpr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatpr.setString(4,KdDok.getText());
                                                psinputrawatpr.setString(5,TSuhu.getText());
                                                psinputrawatpr.setString(6,TTensi.getText());
                                                psinputrawatpr.setString(7,THasil.getText());
                                                psinputrawatpr.setString(8,TPrkmbngn.getText());
                                                psinputrawatpr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatpr.setString(10,"07:00:00");
                                                psinputrawatpr.setDouble(11,biaya);  
                                                psinputrawatpr.executeUpdate();
                                            } catch (Exception e) {
                                            }                                            
                                        }else{
                                            pshapustindakan2.setString(1,TNoRw.getText());
                                            pshapustindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan2.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan2.setString(4,"07:00:00");
                                            pshapustindakan2.executeUpdate();                                            
                                        }
                                        
                                        if(tbKamar.getValueAt(i,1).toString().equals("true")){
                                            try {
                                                psinputrawatpr.setString(1,TNoRw.getText());
                                                psinputrawatpr.setString(2,TKdPny.getText());
                                                psinputrawatpr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatpr.setString(4,KdDok.getText());
                                                psinputrawatpr.setString(5,TSuhu.getText());
                                                psinputrawatpr.setString(6,TTensi.getText());
                                                psinputrawatpr.setString(7,THasil.getText());
                                                psinputrawatpr.setString(8,TPrkmbngn.getText());
                                                psinputrawatpr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatpr.setString(10,"12:00:00");
                                                psinputrawatpr.setDouble(11,biaya);  
                                                psinputrawatpr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan2.setString(1,TNoRw.getText());
                                            pshapustindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan2.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan2.setString(4,"12:00:00");
                                            pshapustindakan2.executeUpdate();                                            
                                        }
                                        
                                        if(tbKamar.getValueAt(i,2).toString().equals("true")){
                                            try {
                                                psinputrawatpr.setString(1,TNoRw.getText());
                                                psinputrawatpr.setString(2,TKdPny.getText());
                                                psinputrawatpr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatpr.setString(4,KdDok.getText());
                                                psinputrawatpr.setString(5,TSuhu.getText());
                                                psinputrawatpr.setString(6,TTensi.getText());
                                                psinputrawatpr.setString(7,THasil.getText());
                                                psinputrawatpr.setString(8,TPrkmbngn.getText());
                                                psinputrawatpr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatpr.setString(10,"16:00:00");
                                                psinputrawatpr.setDouble(11,biaya);  
                                                psinputrawatpr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan2.setString(1,TNoRw.getText());
                                            pshapustindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan2.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan2.setString(4,"16:00:00");
                                            pshapustindakan2.executeUpdate();                                            
                                        }       
                                        
                                        if(tbKamar.getValueAt(i,3).toString().equals("true")){
                                            try {
                                                psinputrawatpr.setString(1,TNoRw.getText());
                                                psinputrawatpr.setString(2,TKdPny.getText());
                                                psinputrawatpr.setString(3,tbKamar.getValueAt(i,4).toString());
                                                psinputrawatpr.setString(4,KdDok.getText());
                                                psinputrawatpr.setString(5,TSuhu.getText());
                                                psinputrawatpr.setString(6,TTensi.getText());
                                                psinputrawatpr.setString(7,THasil.getText());
                                                psinputrawatpr.setString(8,TPrkmbngn.getText());
                                                psinputrawatpr.setString(9,DTPTgl.getSelectedItem().toString());
                                                psinputrawatpr.setString(10,"20:00:00");
                                                psinputrawatpr.setDouble(11,biaya);  
                                                psinputrawatpr.executeUpdate();
                                            } catch (Exception e) {
                                            }
                                        }else{
                                            pshapustindakan2.setString(1,TNoRw.getText());
                                            pshapustindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                                            pshapustindakan2.setString(3,tbKamar.getValueAt(i,4).toString());
                                            pshapustindakan2.setString(4,"20:00:00");
                                            pshapustindakan2.executeUpdate();                                            
                                        }
                                        break;
                                }
                            }                                  
                        }                           
                    } 
                    koneksi.setAutoCommit(true);
                }  
                tampil2();
            } catch (Exception ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbKamar.getRowCount();i++){ 
                tbKamar.setValueAt(false,i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,THasil,TTensi);
}//GEN-LAST:event_TSuhuKeyPressed

private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,DTPTgl);
}//GEN-LAST:event_TTensiKeyPressed

private void THasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THasilKeyPressed
        Valid.pindah(evt,TPrkmbngn,TSuhu);
}//GEN-LAST:event_THasilKeyPressed

private void TPrkmbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPrkmbngnKeyPressed
        Valid.pindah(evt,THasil,BtnSimpan);
}//GEN-LAST:event_TPrkmbngnKeyPressed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
      // Valid.pindah(evt,TTensi,cmbJam);
}//GEN-LAST:event_DTPTglKeyPressed

private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
        }else{            
            Valid.pindah(evt,TNoRw,THasil);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dlgpnykt.emptTeks();
        dlgpnykt.isCek();
        dlgpnykt.tampil();
        dlgpnykt.setSize(this.getWidth()-40,this.getHeight()-40);
        dlgpnykt.setLocationRelativeTo(internalFrame1);
        dlgpnykt.setAlwaysOnTop(false);
        dlgpnykt.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        Valid.pindah(evt,TKdPny,THasil);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            switch (pilihtable) {
                case "rawat_inap_dr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
                    break;
                case "rawat_inap_pr":
                    Sequel.cariIsi("select nama from petugas where nip=?",TDokter,KdDok.getText());
                    break;
            }
        }else{            
            Valid.pindah(evt,TPrkmbngn,BtnSimpan);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        switch (pilihtable) {
            case "rawat_inap_dr":
                dokter.emptTeks();
                dokter.isCek();
                dokter.tampil(" order by kd_dokter");
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;
            case "rawat_inap_pr":
                petugas.emptTeks();
                petugas.isCek();
                petugas.tampil();
                petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                petugas.setLocationRelativeTo(internalFrame1);
                petugas.setAlwaysOnTop(false);
                petugas.setVisible(true);
        }        
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void ppDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDokterActionPerformed
    pilihtable="rawat_inap_dr";
    jLabel5.setText("Dokter :");
    KdDok.setText("");
    TDokter.setText("");
        
}//GEN-LAST:event_ppDokterActionPerformed

private void ppPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasActionPerformed
    pilihtable="rawat_inap_pr";
    jLabel5.setText("Perawat :");
    KdDok.setText("");
    TDokter.setText("");    
}//GEN-LAST:event_ppPetugasActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPerawatanRanap2 dialog = new DlgCariPerawatanRanap2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek2;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kd2;
    private widget.TextBox KdDok;
    private widget.Label LCount;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THasil;
    private widget.TextBox TKdPny;
    private widget.TextBox TNmPny;
    private widget.TextBox TNoRw;
    private widget.TextBox TPrkmbngn;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppDokter;
    private javax.swing.JMenuItem ppPetugas;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() { 
        try{  
            jml=0;
            for(i=0;i<tbKamar.getRowCount();i++){
                if(tbKamar.getValueAt(i,0).toString().equals("true")||tbKamar.getValueAt(i,1).toString().equals("true")||
                        tbKamar.getValueAt(i,2).toString().equals("true")||tbKamar.getValueAt(i,3).toString().equals("true")){
                    jml++;
                }
            }

            pagi=null;
            pagi=new boolean[jml]; 
            siang=null;
            siang=new boolean[jml];
            sore=null;
            sore=new boolean[jml];
            malam=null;
            malam=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            kategori=null;
            kategori=new String[jml];
            totaldr=null;
            totaldr=new String[jml];
            totalpr=null;
            totalpr=new String[jml];    

            
            index=0;        
            for(i=0;i<tbKamar.getRowCount();i++){
                    if(tbKamar.getValueAt(i,0).toString().equals("true")||tbKamar.getValueAt(i,1).toString().equals("true")||tbKamar.getValueAt(i,2).toString().equals("true")||tbKamar.getValueAt(i,3).toString().equals("true")){
                        pagi[index]=Boolean.parseBoolean(tbKamar.getValueAt(i,0).toString());
                        siang[index]=Boolean.parseBoolean(tbKamar.getValueAt(i,1).toString());
                        sore[index]=Boolean.parseBoolean(tbKamar.getValueAt(i,2).toString());
                        malam[index]=Boolean.parseBoolean(tbKamar.getValueAt(i,3).toString());
                        kode[index]=tbKamar.getValueAt(i,4).toString();
                        nama[index]=tbKamar.getValueAt(i,5).toString();
                        kategori[index]=tbKamar.getValueAt(i,6).toString();
                        totaldr[index]=tbKamar.getValueAt(i,7).toString();
                        totalpr[index]=tbKamar.getValueAt(i,8).toString();
                        index++;
                    }
            }                  

            Valid.tabelKosong(tabMode);

            for(i=0;i<jml;i++){
                tabMode.addRow(new Object[] {pagi[i],siang[i],sore[i],malam[i],kode[i],nama[i],kategori[i],totaldr[i],totalpr[i]});
            }
            pstampil.setString(1,kd_pj);
            pstampil.setString(2,kd_bangsal.trim());
            pstampil.setString(3,"%"+TCari.getText().trim()+"%");
            pstampil.setString(4,kd_pj);
            pstampil.setString(5,kd_bangsal.trim());
            pstampil.setString(6,"%"+TCari.getText().trim()+"%");
            pstampil.setString(7,kd_pj);
            pstampil.setString(8,kd_bangsal.trim());
            pstampil.setString(9,"%"+TCari.getText().trim()+"%");
            rs=pstampil.executeQuery();
            while(rs.next()){
                pg=false;
                sg=false;
                sr=false;
                mlm=false;
                switch (pilihtable) {
                    case "rawat_inap_dr":
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"00:00:01");
                        pstindakan.setString(5,"10:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            pg=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"10:00:01");
                        pstindakan.setString(5,"15:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            sg=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"15:00:01");
                        pstindakan.setString(5,"19:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            sr=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"19:00:01");
                        pstindakan.setString(5,"23:59:59");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            mlm=true;
                        }
                        break;
                    case "rawat_inap_pr":
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"00:00:01");
                        pstindakan2.setString(5,"10:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            pg=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"10:00:01");
                        pstindakan2.setString(5,"15:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            sg=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"15:00:01");
                        pstindakan2.setString(5,"19:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            sr=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"19:00:01");
                        pstindakan2.setString(5,"23:59:59");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            mlm=true;
                        }
                        break;
                }
                index=0;
                for(i=0;i<jml;i++){
                    if(kode[i].equals(rs.getString(1))){
                        index++;
                    }
                }
                if(index==0){
                    tabMode.addRow(new Object[] {pg,sg,sr,mlm,rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               Valid.SetAngka(rs.getDouble(4)),
                               Valid.SetAngka(rs.getDouble(5))});
                }
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tbKamar.getRowCount());
    }
    
    public void tampil2() { 
        try{ 
            Valid.tabelKosong(tabMode);
            pstampil.setString(1,kd_pj);
            pstampil.setString(2,kd_bangsal.trim());
            pstampil.setString(3,"%"+TCari.getText().trim()+"%");
            pstampil.setString(4,kd_pj);
            pstampil.setString(5,kd_bangsal.trim());
            pstampil.setString(6,"%"+TCari.getText().trim()+"%");
            pstampil.setString(7,kd_pj);
            pstampil.setString(8,kd_bangsal.trim());
            pstampil.setString(9,"%"+TCari.getText().trim()+"%");
            rs=pstampil.executeQuery();
            while(rs.next()){
                pg=false;
                sg=false;
                sr=false;
                mlm=false;
                switch (pilihtable) {
                    case "rawat_inap_dr":
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"00:00:01");
                        pstindakan.setString(5,"10:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            pg=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"10:00:01");
                        pstindakan.setString(5,"15:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            sg=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"15:00:01");
                        pstindakan.setString(5,"19:00:00");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            sr=true;
                        }
                        
                        pstindakan.setString(1,TNoRw.getText());
                        pstindakan.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan.setString(3,rs.getString(1));
                        pstindakan.setString(4,"19:00:01");
                        pstindakan.setString(5,"23:59:59");
                        rstindakan=pstindakan.executeQuery();
                        if(rstindakan.next()){
                            mlm=true;
                        }
                        break;
                    case "rawat_inap_pr":
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"00:00:01");
                        pstindakan2.setString(5,"10:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            pg=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"10:00:01");
                        pstindakan2.setString(5,"15:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            sg=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"15:00:01");
                        pstindakan2.setString(5,"19:00:00");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            sr=true;
                        }
                        
                        pstindakan2.setString(1,TNoRw.getText());
                        pstindakan2.setString(2,DTPTgl.getSelectedItem().toString());
                        pstindakan2.setString(3,rs.getString(1));
                        pstindakan2.setString(4,"19:00:01");
                        pstindakan2.setString(5,"23:59:59");
                        rstindakan=pstindakan2.executeQuery();
                        if(rstindakan.next()){
                            mlm=true;
                        }
                        break;
                }
                tabMode.addRow(new Object[] {pg,sg,sr,mlm,rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               Valid.SetAngka(rs.getDouble(4)),
                               Valid.SetAngka(rs.getDouble(5))});
                /*tabMode.addRow(new Object[] {false,rs.getString(1),
                               rs.getString(2),
                               rs.getString(3)});*/
            }
            //rs.close();
            //ps.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tbKamar.getRowCount());
    }


    public void emptTeks() {
        Kd2.setText("");  
        TCari.setText("");
         for(i=0;i<tbKamar.getRowCount();i++){ 
                tbKamar.setValueAt(false,i,0);
         }
        TCari.requestFocus();
    }

    private void getData() {
        if(tbKamar.getSelectedRow()!= -1){
            Kd2.setText("");
            Kd2.setText(tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString());
            if(TNoRw.getText().trim().equals("")||TKdPny.getText().trim().equals("")||KdDok.getText().trim().equals("")){
                Valid.textKosong(TCari,"Penyakit & Dokter/Paramedis");
                for(i=0;i<tbKamar.getRowCount();i++){ 
                    tbKamar.setValueAt(false,i,0);
                    tbKamar.setValueAt(false,i,1);
                    tbKamar.setValueAt(false,i,2);
                    tbKamar.setValueAt(false,i,3);
                }
            }else{               
                    if(pilihtable.equals("rawat_inap_dr")||pilihtable.equals("rawat_inap_pr")){
                         if(tbKamar.getValueAt(tbKamar.getSelectedRow(),0).toString().equals("true")||tbKamar.getValueAt(tbKamar.getSelectedRow(),1).toString().equals("true")||tbKamar.getValueAt(tbKamar.getSelectedRow(),2).toString().equals("true")||tbKamar.getValueAt(tbKamar.getSelectedRow(),3).toString().equals("true")){
                             switch (pilihtable) {
                                 case "rawat_inap_dr":
                                     biaya=Sequel.cariIsiAngka("select total_byrdr from jns_perawatan_inap where kd_jenis_prw=?",tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString());
                                     break;
                                 case "rawat_inap_pr":                             
                                     biaya=Sequel.cariIsiAngka("select total_byrpr from jns_perawatan_inap where kd_jenis_prw=?",tbKamar.getValueAt(tbKamar.getSelectedRow(),4).toString());
                                     break;
                             }
                         }  
                    }     
            }
        }
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbKamar;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void isCek(){
        BtnTambah.setEnabled(var.gettindakan());
        TCari.requestFocus();
    }
    
    public void setNoRm(String norwt,String pilihtable,Date tanggal,String jam,String menit,String detik,boolean status) {
        TNoRw.setText(norwt);
        KdDok.setText("");
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat='"+TNoRw.getText()+"'");
        this.kd_bangsal=Sequel.cariIsi(
                "select bangsal.kd_bangsal from bangsal inner join kamar inner join kamar_inap "+
                "on bangsal.kd_bangsal=kamar.kd_bangsal and kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where no_rawat='"+TNoRw.getText()+"' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1");
        this.pilihtable=pilihtable;
        switch (pilihtable) {
            case "rawat_inap_dr":
                jLabel5.setText("Dokter :");
                break;
            case "rawat_inap_pr":
                jLabel5.setText("Perawat :");
                break;
        }
        DTPTgl.setDate(tanggal);
        TCari.requestFocus();
        
        for(i=0;i<tbKamar.getRowCount();i++){ 
                    tbKamar.setValueAt(false,i,0);
                    tbKamar.setValueAt(false,i,1);
                    tbKamar.setValueAt(false,i,2);
                    tbKamar.setValueAt(false,i,3);
        }
    }
    
    public void setPetugas(String kode, String nama,String suhu,String tensi, String Hasil, String perkembangan){
        KdDok.setText(kode);
        TDokter.setText(nama);
        TSuhu.setText(suhu);
        TTensi.setText(tensi);
        THasil.setText(Hasil);
        TPrkmbngn.setText(perkembangan);
    }
    
    
}
