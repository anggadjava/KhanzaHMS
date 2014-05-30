/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
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
public final class DlgCariPerawatanRalan extends javax.swing.JDialog {
    private final DefaultTableModel TabModeTindakan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement pstindakan,pscaribiaya,pssimpandokter,pssimpanperawat;
    private ResultSet rstindakan,rscari;
    private String pilihtable="", kd_pj="",kd_poli="";
    private boolean[] pilih; 
    private double biaya=0;
    private String[] kode,nama,kategori,totaldr,totalpr;
    private int jml=0,i=0,index=0;
    public  DlgJnsPerawatanRalan perawatan=new DlgJnsPerawatanRalan(null,false);
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    public  DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);

    /**
     * Creates new form DlgPenyakit
     * @param parent
     * @param modal
     */
    public DlgCariPerawatanRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        Object[] row={"Pilih","Kode","Nama Perawatan","Kategori Perawatan","Tarif Dokter","Tarif Perawat"};
        /*Object[] row={"Pilih",
                      "Kode",
                      "Nama Perawatan",
                      "Kategori Perawatan"};*/
        TabModeTindakan=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,  
                java.lang.Object.class,java.lang.Object.class, java.lang.Object.class
             };
             /*Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };*/
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakan.setModel(TabModeTindakan);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbTindakan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        /*for (i = 0; i < 4; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
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
        for (i = 0; i < 6; i++) {
            TableColumn column = tbTindakan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbTindakan.setDefaultRenderer(Object.class, new WarnaTable());
        TCariTindakan.setDocument(new batasInput((byte)100).getKata(TCariTindakan));
        TCariTindakan.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
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
                KdPtg.setText(dokter.getTextField().getText());   
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,dokter.getTextField().getText());
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
                KdPtg.setText(petugas.getTextField().getText());   
                Sequel.cariIsi("select nama from petugas where nip=?",NmDokter,petugas.getTextField().getText());
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
                KdPny.setText(dlgpnykt.getTextField().getText());     
                Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",NmPenyakit,KdPny.getText());
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
            pstindakan=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.nm_perawatan,kategori_perawatan.nm_kategori,"+
                   "jns_perawatan.total_byrdr,jns_perawatan.total_byrpr "+
                   "from jns_perawatan inner join kategori_perawatan "+
                   "on jns_perawatan.kd_kategori=kategori_perawatan.kd_kategori  "+
                   "where jns_perawatan.kd_pj=? and jns_perawatan.kd_poli=? and jns_perawatan.kd_jenis_prw like ? or "+
                    " jns_perawatan.kd_pj=? and jns_perawatan.kd_poli=? and jns_perawatan.nm_perawatan like ? or "+
                    " jns_perawatan.kd_pj=? and jns_perawatan.kd_poli=? and kategori_perawatan.nm_kategori like ? order by jns_perawatan.nm_perawatan ");          
            pscaribiaya=koneksi.prepareStatement("select total_byrdr,total_byrpr from jns_perawatan where kd_jenis_prw=?");
            pssimpandokter=koneksi.prepareStatement("insert into rawat_jl_dr values(?,?,?,?,?,?,?,?,?)");
            pssimpanperawat=koneksi.prepareStatement("insert into rawat_jl_pr values(?,?,?,?,?,?,?,?,?)");
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

        Popup = new javax.swing.JPopupMenu();
        ppOrder = new javax.swing.JMenu();
        ppOrderKode = new javax.swing.JMenuItem();
        ppOrderNama = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppDokter = new javax.swing.JMenuItem();
        ppPetugas = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        TNoRw = new widget.TextBox();
        TSuhu = new widget.TextBox();
        TTensi = new widget.TextBox();
        THasil = new widget.TextBox();
        TPrkmbngn = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbTindakan = new widget.Table();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCariTindakan = new widget.TextBox();
        BtnCariTindakan = new widget.Button();
        BtnAllTindakan = new widget.Button();
        BtnTambahTindakan = new widget.Button();
        BtnSimpanTindakan = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        KdPtg = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        jLabel12 = new widget.Label();
        KdPny = new widget.TextBox();
        NmPenyakit = new widget.TextBox();
        BtnSeek2 = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppOrder.setBackground(new java.awt.Color(242, 242, 242));
        ppOrder.setForeground(new java.awt.Color(102, 51, 0));
        ppOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrder.setText("Urutkan Berdasar");
        ppOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppOrder.setIconTextGap(8);
        ppOrder.setName("ppOrder"); // NOI18N

        ppOrderKode.setBackground(new java.awt.Color(255, 255, 255));
        ppOrderKode.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppOrderKode.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderKode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderKode.setText("Kode Perawatan/Tindakan");
        ppOrderKode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderKode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderKode.setIconTextGap(8);
        ppOrderKode.setName("ppOrderKode"); // NOI18N
        ppOrderKode.setPreferredSize(new java.awt.Dimension(300, 25));
        ppOrderKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderKodeActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderKode);

        ppOrderNama.setBackground(new java.awt.Color(255, 255, 255));
        ppOrderNama.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppOrderNama.setForeground(new java.awt.Color(102, 51, 0));
        ppOrderNama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        ppOrderNama.setText("Nama Perawatan");
        ppOrderNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppOrderNama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppOrderNama.setIconTextGap(8);
        ppOrderNama.setName("ppOrderNama"); // NOI18N
        ppOrderNama.setPreferredSize(new java.awt.Dimension(300, 25));
        ppOrderNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppOrderNamaActionPerformed(evt);
            }
        });
        ppOrder.add(ppOrderNama);

        Popup.add(ppOrder);

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

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.setSelectionColor(new java.awt.Color(255, 255, 255));

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.setSelectionColor(new java.awt.Color(255, 255, 255));

        THasil.setHighlighter(null);
        THasil.setName("THasil"); // NOI18N
        THasil.setSelectionColor(new java.awt.Color(255, 255, 255));

        TPrkmbngn.setHighlighter(null);
        TPrkmbngn.setName("TPrkmbngn"); // NOI18N
        TPrkmbngn.setSelectionColor(new java.awt.Color(255, 255, 255));

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tarif Tagihan/Perawatan/Tindakan Rawat Jalan]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbTindakan.setAutoCreateRowSorter(true);
        tbTindakan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTindakan.setComponentPopupMenu(Popup);
        tbTindakan.setName("tbTindakan"); // NOI18N
        tbTindakan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTindakanMouseClicked(evt);
            }
        });
        tbTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTindakanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbTindakan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCariTindakan.setName("TCariTindakan"); // NOI18N
        TCariTindakan.setPreferredSize(new java.awt.Dimension(350, 23));
        TCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(TCariTindakan);

        BtnCariTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariTindakan.setMnemonic('1');
        BtnCariTindakan.setToolTipText("Alt+1");
        BtnCariTindakan.setName("BtnCariTindakan"); // NOI18N
        BtnCariTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariTindakanActionPerformed(evt);
            }
        });
        BtnCariTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCariTindakan);

        BtnAllTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAllTindakan.setMnemonic('2');
        BtnAllTindakan.setToolTipText("2Alt+2");
        BtnAllTindakan.setName("BtnAllTindakan"); // NOI18N
        BtnAllTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAllTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllTindakanActionPerformed(evt);
            }
        });
        BtnAllTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllTindakanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAllTindakan);

        BtnTambahTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambahTindakan.setMnemonic('3');
        BtnTambahTindakan.setToolTipText("Alt+3");
        BtnTambahTindakan.setName("BtnTambahTindakan"); // NOI18N
        BtnTambahTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambahTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahTindakanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambahTindakan);

        BtnSimpanTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanTindakan.setMnemonic('S');
        BtnSimpanTindakan.setToolTipText("Alt+S");
        BtnSimpanTindakan.setName("BtnSimpanTindakan"); // NOI18N
        BtnSimpanTindakan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpanTindakan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanTindakanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpanTindakan);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(55, 23));
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
        FormInput.setPreferredSize(new java.awt.Dimension(710, 44));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 9));

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(jLabel5);

        KdPtg.setHighlighter(null);
        KdPtg.setName("KdPtg"); // NOI18N
        KdPtg.setPreferredSize(new java.awt.Dimension(70, 23));
        KdPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPtgKeyPressed(evt);
            }
        });
        FormInput.add(KdPtg);

        NmDokter.setEditable(false);
        NmDokter.setHighlighter(null);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(190, 23));
        FormInput.add(NmDokter);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeekDokter);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(jLabel12);

        KdPny.setText("-");
        KdPny.setName("KdPny"); // NOI18N
        KdPny.setPreferredSize(new java.awt.Dimension(60, 23));
        KdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPnyKeyPressed(evt);
            }
        });
        FormInput.add(KdPny);

        NmPenyakit.setEditable(false);
        NmPenyakit.setText("-");
        NmPenyakit.setName("NmPenyakit"); // NOI18N
        NmPenyakit.setPreferredSize(new java.awt.Dimension(190, 23));
        FormInput.add(NmPenyakit);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('2');
        BtnSeek2.setToolTipText("Alt+2");
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
        FormInput.add(BtnSeek2);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariTindakanActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariTindakan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariTindakanKeyPressed

    private void BtnCariTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariTindakanActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariTindakanActionPerformed

    private void BtnCariTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariTindakanActionPerformed(null);
        }else{
            Valid.pindah(evt, TCariTindakan, BtnAllTindakan);
        }
}//GEN-LAST:event_BtnCariTindakanKeyPressed

    private void BtnAllTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllTindakanActionPerformed
        TCariTindakan.setText("");
        tampil();
}//GEN-LAST:event_BtnAllTindakanActionPerformed

    private void BtnAllTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllTindakanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllTindakanActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCariTindakan, TCariTindakan);
        }
}//GEN-LAST:event_BtnAllTindakanKeyPressed

    private void tbTindakanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTindakanMouseClicked
        if(tbTindakan.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbTindakanMouseClicked

    private void tbTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTindakanKeyPressed
        if(tbTindakan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    i=tbTindakan.getSelectedColumn();
                    if(i==1){
                        if(tbTindakan.getSelectedRow()>-1){
                          tbTindakan.setValueAt(true,tbTindakan.getSelectedRow(),0);   
                        }                               
                        TCariTindakan.setText("");
                        TCariTindakan.requestFocus();
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
                TCariTindakan.requestFocus();
            }
        }
}//GEN-LAST:event_tbTindakanKeyPressed

    private void ppOrderKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderKodeActionPerformed
        tampil();
    }//GEN-LAST:event_ppOrderKodeActionPerformed

    private void ppOrderNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppOrderNamaActionPerformed
        tampil();
    }//GEN-LAST:event_ppOrderNamaActionPerformed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
             dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahTindakanActionPerformed
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
    }//GEN-LAST:event_BtnTambahTindakanActionPerformed

private void BtnSimpanTindakanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanTindakanActionPerformed
        if(TNoRw.getText().trim().equals("")||KdPny.getText().trim().equals("")||KdPtg.getText().trim().equals("")){
            Valid.textKosong(TCariTindakan,"Data");
        }else{
            try {                
                if(pilihtable.equals("rawat_jl_dr")||pilihtable.equals("rawat_jl_pr")){
                    koneksi.setAutoCommit(false);
                    for(i=0;i<tbTindakan.getRowCount();i++){ 
                        if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                            pscaribiaya.setString(1,tbTindakan.getValueAt(i,1).toString());
                            rscari=pscaribiaya.executeQuery();
                            if(rscari.next()){
                                switch (pilihtable) {
                                    case "rawat_jl_dr":
                                        biaya=rscari.getDouble(1);
                                        pssimpandokter.setString(1,TNoRw.getText());
                                        pssimpandokter.setString(2,KdPny.getText());
                                        pssimpandokter.setString(3,tbTindakan.getValueAt(i,1).toString());
                                        pssimpandokter.setString(4,KdPtg.getText());
                                        pssimpandokter.setString(5,TSuhu.getText());
                                        pssimpandokter.setString(6,TTensi.getText());
                                        pssimpandokter.setString(7,THasil.getText());
                                        pssimpandokter.setString(8,TPrkmbngn.getText());
                                        pssimpandokter.setDouble(9,biaya);
                                        pssimpandokter.executeUpdate();
                                        break;
                                    case "rawat_jl_pr":
                                        biaya=rscari.getDouble(2);
                                        pssimpanperawat.setString(1,TNoRw.getText());
                                        pssimpanperawat.setString(2,KdPny.getText());
                                        pssimpanperawat.setString(3,tbTindakan.getValueAt(i,1).toString());
                                        pssimpanperawat.setString(4,KdPtg.getText());
                                        pssimpanperawat.setString(5,TSuhu.getText());
                                        pssimpanperawat.setString(6,TTensi.getText());
                                        pssimpanperawat.setString(7,THasil.getText());
                                        pssimpanperawat.setString(8,TPrkmbngn.getText());
                                        pssimpanperawat.setDouble(9,biaya);  
                                        pssimpanperawat.executeUpdate();
                                        break;
                                }
                            }                            
                           // if(!biaya.equals("0")){                           
                            //}else{
                            //    JOptionPane.showMessageDialog(null,"Maaf, tindakan "+tbKamar.getValueAt(r,2).toString()+" tidak bisa dimasukkan\ntidak sesuai antara rawat paramedis/rawat dokter ..!!!");
                            //}                                   
                        }                           
                    }  
                    //rs.close();
                    //ps.close();
                    //ps2.close();
                    koneksi.setAutoCommit(true);
                }   
                dispose();
            } catch (SQLException ex) {
                System.out.println(ex);
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data yang sama dimasukkan sebelumnya...!");
            }
        }
}//GEN-LAST:event_BtnSimpanTindakanActionPerformed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            for(i=0;i<tbTindakan.getRowCount();i++){ 
                tbTindakan.setValueAt(false,i,0);
            }
}//GEN-LAST:event_ppBersihkanActionPerformed

private void KdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",NmPenyakit,KdPny.getText());
        }else{            
            Valid.pindah(evt,TNoRw,THasil);
        }
}//GEN-LAST:event_KdPnyKeyPressed

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
        Valid.pindah(evt,KdPny,THasil);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void KdPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPtgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            switch (pilihtable) {
                case "rawat_jl_dr":
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",NmDokter,KdPtg.getText());
                    break;
                case "rawat_jl_pr":
                    Sequel.cariIsi("select nama from petugas where nip=?",NmDokter,KdPtg.getText());
                    break;
            }
        }else{            
            Valid.pindah(evt,TPrkmbngn,BtnSimpanTindakan);
        }
}//GEN-LAST:event_KdPtgKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        switch (pilihtable) {
            case "rawat_jl_dr":
                dokter.emptTeks();
                dokter.isCek();
                dokter.tampil(" order by kd_dokter");
                dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dokter.setLocationRelativeTo(internalFrame1);
                dokter.setAlwaysOnTop(false);
                dokter.setVisible(true);
                break;
            case "rawat_jl_pr":
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
    pilihtable="rawat_jl_dr";
    jLabel5.setText("Dokter :");
    KdPtg.setText("");
    NmDokter.setText("");
        
}//GEN-LAST:event_ppDokterActionPerformed

private void ppPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPetugasActionPerformed
    pilihtable="rawat_jl_pr";
    jLabel5.setText("Perawat :");
    KdPtg.setText("");
    NmDokter.setText("");    
}//GEN-LAST:event_ppPetugasActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPerawatanRalan dialog = new DlgCariPerawatanRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAllTindakan;
    private widget.Button BtnCariTindakan;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSimpanTindakan;
    private widget.Button BtnTambahTindakan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.TextBox KdPny;
    private widget.TextBox KdPtg;
    private widget.Label LCount;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPenyakit;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCariTindakan;
    private widget.TextBox THasil;
    private widget.TextBox TNoRw;
    private widget.TextBox TPrkmbngn;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private widget.TextBox Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.Label jLabel5;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppDokter;
    private javax.swing.JMenu ppOrder;
    private javax.swing.JMenuItem ppOrderKode;
    private javax.swing.JMenuItem ppOrderNama;
    private javax.swing.JMenuItem ppPetugas;
    private widget.Table tbTindakan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try{     
            jml=0;
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml]; 
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
            for(i=0;i<tbTindakan.getRowCount();i++){
                if(tbTindakan.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbTindakan.getValueAt(i,1).toString();
                    nama[index]=tbTindakan.getValueAt(i,2).toString();
                    kategori[index]=tbTindakan.getValueAt(i,3).toString();
                    totaldr[index]=tbTindakan.getValueAt(i,4).toString();
                    totalpr[index]=tbTindakan.getValueAt(i,5).toString();
                    index++;
                }
            }       

            Valid.tabelKosong(TabModeTindakan);

            for(i=0;i<jml;i++){
                TabModeTindakan.addRow(new Object[] {pilih[i],kode[i],nama[i],kategori[i],totaldr[i],totalpr[i]});
            }
            
            pstindakan.setString(1,kd_pj.trim());
            pstindakan.setString(2,kd_poli.trim());
            pstindakan.setString(3,"%"+TCariTindakan.getText().trim()+"%");
            pstindakan.setString(4,kd_pj.trim());
            pstindakan.setString(5,kd_poli.trim());
            pstindakan.setString(6,"%"+TCariTindakan.getText().trim()+"%");
            pstindakan.setString(7,kd_pj.trim());
            pstindakan.setString(8,kd_poli.trim());
            pstindakan.setString(9,"%"+TCariTindakan.getText().trim()+"%");
            rstindakan=pstindakan.executeQuery();
            while(rstindakan.next()){
                TabModeTindakan.addRow(new Object[] {false,rstindakan.getString(1),
                               rstindakan.getString(2),
                               rstindakan.getString(3),
                               Valid.SetAngka(rstindakan.getDouble(4)),
                               Valid.SetAngka(rstindakan.getDouble(5))});
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tbTindakan.getRowCount());
    }

    public void emptTeks() {
        Kd2.setText("");  
        TCariTindakan.setText("");
         for(i=0;i<tbTindakan.getRowCount();i++){ 
                tbTindakan.setValueAt(false,i,0);
         }
        TCariTindakan.requestFocus();
    }

    private void getData() {
        if(tbTindakan.getSelectedRow()!= -1){
            Kd2.setText("");
            Kd2.setText(tbTindakan.getValueAt(tbTindakan.getSelectedRow(),1).toString());
            if(TNoRw.getText().trim().equals("")||KdPny.getText().trim().equals("")||KdPtg.getText().trim().equals("")){
                Valid.textKosong(TCariTindakan,"Data");
                for(i=0;i<tbTindakan.getRowCount();i++){ 
                    tbTindakan.setValueAt(false,i,0);
                }
            }else{               
                   if(pilihtable.equals("rawat_jl_dr")||pilihtable.equals("rawat_jl_pr")){
                            if(tbTindakan.getValueAt(tbTindakan.getSelectedRow(),0).toString().equals("true")){
                                switch (pilihtable) {
                                    case "rawat_jl_dr":
                                        biaya=Sequel.cariIsiAngka("select total_byrdr from jns_perawatan where kd_jenis_prw=?",tbTindakan.getValueAt(tbTindakan.getSelectedRow(),1).toString());
                                        break;
                                    case "rawat_jl_pr":
                                        biaya=Sequel.cariIsiAngka("select total_byrpr from jns_perawatan where kd_jenis_prw=?",tbTindakan.getValueAt(tbTindakan.getSelectedRow(),1).toString());
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
        return tbTindakan;
    }
    
    public Button getButton(){
        return BtnSimpanTindakan;
    }
    
    public void isCek(){
        BtnTambahTindakan.setEnabled(var.gettindakan());
        TCariTindakan.requestFocus();
    }
    
    public void setNoRm(String norwt,String kdpenyakit,String nmpenyakit,String kdpetugas,String nmpetugas, String pilihtable,
            String suhu, String tensi,String hasil, String perkembangan,String tanggal, String jam) {
        /*if(pilihtable.equals("rawat_jl_dr")){
                                  
        }else if(pilihtable.equals("rawat_jl_pr")){
                                  
        }  */
        KdPny.setText(kdpenyakit);
        NmPenyakit.setText(nmpenyakit);
        TNoRw.setText(norwt);
        KdPtg.setText(kdpetugas);
        NmDokter.setText(nmpetugas);
        this.pilihtable=pilihtable;
        this.kd_pj=Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat='"+norwt+"'");
        this.kd_poli=Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat='"+norwt+"'");
        switch (pilihtable) {
            case "rawat_jl_dr":
                jLabel5.setText("Dokter :");
                break;
            case "rawat_jl_pr":
                jLabel5.setText("Perawat :");
                break;
        }
        TSuhu.setText(suhu);
        TTensi.setText(tensi);
        THasil.setText(hasil);
        TPrkmbngn.setText(perkembangan);
        Tanggal.setText(tanggal);
        Jam.setText(jam);
        TCariTindakan.requestFocus();
    }
}
