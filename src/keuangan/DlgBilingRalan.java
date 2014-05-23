package keuangan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgCariObat;
import inventory.DlgPenjualan;
import inventory.DlgReturJual;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgCariDokter;
import khanzahms.DlgCariPoli;
import khanzahms.DlgPemberianObat;
import khanzahms.DlgPeriksaLaboratorium;
import khanzahms.DlgRawatJalan;

/**
 *
 * @author perpustakaan
 */
public class DlgBilingRalan extends javax.swing.JDialog {
    private DefaultTableModel tabModeRwJlDr;
    private final DefaultTableModel tabModeTambahan,tabModePotongan;
    
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb(); 
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPoli poli=new DlgCariPoli(null,false);    
    public  DlgCariObat dlgobt=new DlgCariObat(null,false);
    public  DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
    private double ttl=0,y=0,subttl=0,uangmuka=0,sisapiutang=0,obat=0,ralandokter=0,ranapdokter=0,ralanparamedis=0,ranapparamedis=0, kamar=0,bayar=0,total=0;
    private int i,r;
    private String biaya="",tambahan="",totals="",kdptg="",nmptg="";
    private PreparedStatement pscekbilling,pscarirm,pscaripasien,psreg,pscaripoli,pscarialamat,
            psdokterralan,pscariralandokter,pscariralanperawat,pscarilab,pscariobat,psdetaillab,
            psobatlangsung,psreturobat,pstambahan,psbiling,pstemporary,pspotongan,psbilling;
    private ResultSet rscekbilling,rscarirm,rscaripasien,rsreg,rscaripoli,rscarialamat,
            rsdokterralan,rscariralandokter,rscariralanperawat,rscarilab,rscariobat,rsdetaillab,
            rsobatlangsung,rsreturobat,rstambahan,rs2,rs3,rs4,rs5,rs6,rs8,rspotongan,rsbilling;

    /** Creates new form DlgBiling
     * @param parent
     * @param modal */
    public DlgBilingRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Object[] rowRwJlDr={"Pilih","Keterangan","Tagihan/Tindakan/Terapi","","Biaya","Jumlah","Tambahan","Total Biaya",""};
        tabModeRwJlDr=new DefaultTableModel(null,rowRwJlDr){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                    boolean a = true;
                    if (colIndex==1) {
                        a=false;
                    }
                    return a;
              }
              
              Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbAdmin.setModel(tabModeRwJlDr);

        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbAdmin.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbAdmin.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 9; i++) {
            TableColumn column = tbAdmin.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(160);
            }else if(i==2){
                column.setPreferredWidth(450);
            }else if(i==3){
                column.setPreferredWidth(15);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(60);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(10);
            }
        }

        tbAdmin.setDefaultRenderer(Object.class, new WarnaTable());
        //tambahan biaya
        Object[] rowTambahan={"Tambahan Biaya","Besar Biaya"};
        tabModeTambahan=new DefaultTableModel(null,rowTambahan){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){return true;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTambahan.setModel(tabModeTambahan);

        tbTambahan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbTambahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbTambahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(150);
            }
        }
        tbTambahan.setDefaultRenderer(Object.class, new WarnaTable());
        
        //potongan biaya
        Object[] rowPotongan={"Potongan Biaya","Besar Potongan"};
        tabModePotongan=new DefaultTableModel(null,rowPotongan){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){return true;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPotongan.setModel(tabModePotongan);

        tbPotongan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPotongan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPotongan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(300);
            }else if(i==1){
                column.setPreferredWidth(150);
            }
        }
        tbPotongan.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        kdpoli.setDocument(new batasInput((byte)5).getKata(kdpoli));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        TBayar.setDocument(new batasInput((byte)17).getOnlyAngka(TBayar));

        TBayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });     
        
        dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kddokter.setText(dokter.getTextField().getText());
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
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
        
        poli.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdpoli.setText(poli.getTextField().getText());  
                Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
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
        
        dlgrwjl.perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {isRawat();isKembali();}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });

        
        dlgobt.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {isRawat();isKembali();}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
           
        jLabel7.setVisible(false);
        DTPTempo.setVisible(false);
        try {
            pscekbilling=koneksi.prepareStatement("select count(billing.no_rawat) from billing where billing.no_rawat=?");
            pscarirm=koneksi.prepareStatement("select no_rkm_medis from reg_periksa where no_rawat=?");
            pscaripasien=koneksi.prepareStatement("select nm_pasien from pasien where no_rkm_medis=? ");
            psreg=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,"+
                    "reg_periksa.kd_poli,reg_periksa.no_rawat,reg_periksa.biaya_reg "+
                    "from reg_periksa where reg_periksa.no_rawat=?");
            pscaripoli=koneksi.prepareStatement("select nm_poli from poliklinik where kd_poli=?");
            pscarialamat=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");      
            psdokterralan=koneksi.prepareStatement("select dokter.nm_dokter from rawat_jl_dr "+
                            "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=? group by rawat_jl_dr.kd_dokter");
            pscariralandokter=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrdr,count(jns_perawatan.nm_perawatan) as jml, "+
                                           "jns_perawatan.total_byrdr*count(jns_perawatan.nm_perawatan) as biaya "+
                                           "from rawat_jl_dr inner join jns_perawatan "+
                                           "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                                           "rawat_jl_dr.no_rawat=? group by jns_perawatan.nm_perawatan");
            pscariralanperawat=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrpr,count(jns_perawatan.nm_perawatan) as jml, "+
                                           "jns_perawatan.total_byrpr*count(jns_perawatan.nm_perawatan) as biaya "+
                                           "from rawat_jl_pr inner join jns_perawatan "+
                                           "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                                           "rawat_jl_pr.no_rawat=? group by jns_perawatan.nm_perawatan ");
            pscarilab=koneksi.prepareStatement("select jns_perawatan_lab.nm_perawatan, count(periksa_lab.kd_jenis_prw) as jml,jns_perawatan_lab.total_byr as biaya, "+
                    "sum(periksa_lab.biaya) as total "+
                    " from periksa_lab inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "+
                    " periksa_lab.no_rawat=? group by periksa_lab.kd_jenis_prw  ");
            psdetaillab=koneksi.prepareStatement("select sum(detail_periksa_lab.biaya_item) as total from detail_periksa_lab where detail_periksa_lab.no_rawat=? ");
            pscariobat=koneksi.prepareStatement("select databarang.nama_brng,detail_pemberian_obat.biaya_obat,"+
                                           "sum(detail_pemberian_obat.jml) as jml,sum(detail_pemberian_obat.tambahan) as tambahan,"+
                                           "sum(detail_pemberian_obat.total) as total "+
                                           "from detail_pemberian_obat inner join databarang "+
                                           "on detail_pemberian_obat.kode_brng=databarang.kode_brng where "+
                                           "detail_pemberian_obat.no_rawat=? group by databarang.nama_brng");
            psobatlangsung=koneksi.prepareStatement("select besar_tagihan from tagihan_obat_langsung where "+
                    "no_rawat=? ");
            psreturobat=koneksi.prepareStatement("select databarang.nama_brng,detreturjual.h_retur, "+
                        "(detreturjual.jml_retur * -1) as jml, "+
                        "(detreturjual.subtotal * -1) as ttl from detreturjual inner join databarang inner join returjual "+
                        "on detreturjual.kode_brng=databarang.kode_brng "+
                        "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_rkm_medis=? "+
                        "and returjual.tgl_retur=? group by databarang.nama_brng");
            pstambahan=koneksi.prepareStatement("select nama_biaya, besar_biaya from tambahan_biaya where no_rawat=?  ");
            psbiling=koneksi.prepareStatement("insert into billing values('0',?,?,?,?,?,?,?,?,?,?)");
            pstemporary=koneksi.prepareStatement(
                            "insert into temporary_bayar_ralan values('0',?,?,?,?,?,?,?,?,'','','','','','','','','')");
            pspotongan=koneksi.prepareStatement(
                        "select nama_pengurangan,besar_pengurangan from pengurangan_biaya where no_rawat=?");
            psbilling=koneksi.prepareStatement("select no,nm_perawatan, if(biaya<>0,biaya,'') as satu, if(jumlah<>0,jumlah,'') as dua,"+
                        "if(tambahan<>0,tambahan,'') as tiga, if(totalbiaya<>0,totalbiaya,'') as empat,pemisah,status "+
                        "from billing where no_rawat=?  order by noindex");
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppDokter = new javax.swing.JMenuItem();
        ppPoli = new javax.swing.JMenuItem();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        ppTambahan = new javax.swing.JMenuItem();
        ppPotongan = new javax.swing.JMenuItem();
        ppObat = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnReturJual = new javax.swing.JMenuItem();
        MnHapusTagihan = new javax.swing.JMenuItem();
        WindowInput2 = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan1 = new widget.Button();
        jLabel13 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel8 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan2 = new widget.Button();
        BtnBatal1 = new widget.Button();
        WindowInput3 = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbTambahan = new widget.Table();
        panelisi1 = new widget.panelisi();
        label15 = new widget.Label();
        norawat = new widget.TextBox();
        BtnTambah = new widget.Button();
        BtnSimpan3 = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar1 = new widget.Button();
        WindowInput4 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel14 = new widget.Label();
        kdpoli = new widget.TextBox();
        nmpoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        WindowInput5 = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbPotongan = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        norawatpotongan = new widget.TextBox();
        BtnTambahPotongan = new widget.Button();
        BtnSimpanPotongan = new widget.Button();
        BtnHapusPotongan = new widget.Button();
        BtnKeluarPotongan = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        panelGlass1 = new widget.panelisi();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel4 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        panelGlass2 = new widget.panelisi();
        BtnKeluar = new widget.Button();
        TtlSemua = new widget.TextBox();
        BtnSimpan = new widget.Button();
        BtnView = new widget.Button();
        chkPiutang = new widget.CekBox();
        TBayar = new widget.TextBox();
        TKembali = new widget.TextBox();
        jLabel6 = new widget.Label();
        DTPTempo = new widget.Tanggal();
        chkPrint = new widget.CekBox();
        jLabel7 = new widget.Label();
        jLabel5 = new widget.Label();
        Scroll = new widget.ScrollPane();
        tbAdmin = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppDokter.setBackground(new java.awt.Color(255, 255, 255));
        ppDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDokter.setForeground(java.awt.Color.darkGray);
        ppDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDokter.setText("Ganti Dokter Poli");
        ppDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDokter.setIconTextGap(10);
        ppDokter.setName("ppDokter"); // NOI18N
        ppDokter.setPreferredSize(new java.awt.Dimension(250, 25));
        ppDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDokterActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDokter);

        ppPoli.setBackground(new java.awt.Color(255, 255, 255));
        ppPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPoli.setForeground(java.awt.Color.darkGray);
        ppPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPoli.setText("Ganti Poliklinik");
        ppPoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPoli.setIconTextGap(10);
        ppPoli.setName("ppPoli"); // NOI18N
        ppPoli.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPoliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPoli);

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(java.awt.Color.darkGray);
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setIconTextGap(10);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(java.awt.Color.darkGray);
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setIconTextGap(10);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(java.awt.Color.darkGray);
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setIconTextGap(10);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        ppTambahan.setBackground(new java.awt.Color(255, 255, 255));
        ppTambahan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTambahan.setForeground(java.awt.Color.darkGray);
        ppTambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTambahan.setText("Tambahan Biaya");
        ppTambahan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTambahan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTambahan.setIconTextGap(10);
        ppTambahan.setName("ppTambahan"); // NOI18N
        ppTambahan.setPreferredSize(new java.awt.Dimension(250, 25));
        ppTambahan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTambahanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTambahan);

        ppPotongan.setBackground(new java.awt.Color(255, 255, 255));
        ppPotongan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPotongan.setForeground(java.awt.Color.darkGray);
        ppPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPotongan.setText("Potongan Biaya");
        ppPotongan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPotongan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPotongan.setIconTextGap(10);
        ppPotongan.setName("ppPotongan"); // NOI18N
        ppPotongan.setPreferredSize(new java.awt.Dimension(250, 25));
        ppPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPotonganActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPotongan);

        ppObat.setBackground(new java.awt.Color(255, 255, 255));
        ppObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppObat.setForeground(java.awt.Color.darkGray);
        ppObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppObat.setText("Tagihan BHP & Obat");
        ppObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppObat.setIconTextGap(10);
        ppObat.setName("ppObat"); // NOI18N
        ppObat.setPreferredSize(new java.awt.Dimension(250, 25));
        ppObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppObat);

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(java.awt.Color.darkGray);
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPenjualan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPenjualan.setIconTextGap(10);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjualan);

        MnReturJual.setBackground(new java.awt.Color(255, 255, 255));
        MnReturJual.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnReturJual.setForeground(java.awt.Color.darkGray);
        MnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnReturJual.setText("Retur Obat/Barang/Alkes");
        MnReturJual.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnReturJual.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnReturJual.setIconTextGap(10);
        MnReturJual.setName("MnReturJual"); // NOI18N
        MnReturJual.setPreferredSize(new java.awt.Dimension(250, 25));
        MnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnReturJualActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnReturJual);

        MnHapusTagihan.setBackground(new java.awt.Color(255, 255, 255));
        MnHapusTagihan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihan.setForeground(java.awt.Color.darkGray);
        MnHapusTagihan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihan.setText("Hapus Nota Salah");
        MnHapusTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnHapusTagihan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnHapusTagihan.setIconTextGap(10);
        MnHapusTagihan.setName("MnHapusTagihan"); // NOI18N
        MnHapusTagihan.setPreferredSize(new java.awt.Dimension(250, 25));
        MnHapusTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihan);

        WindowInput2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput2.setName("WindowInput2"); // NOI18N
        WindowInput2.setUndecorated(true);
        WindowInput2.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Dokter Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame3.setLayout(null);

        BtnCloseIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn1.setMnemonic('P');
        BtnCloseIn1.setText("Tutup");
        BtnCloseIn1.setToolTipText("Alt+P");
        BtnCloseIn1.setName("BtnCloseIn1"); // NOI18N
        BtnCloseIn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn1ActionPerformed(evt);
            }
        });
        BtnCloseIn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseIn1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnCloseIn1);
        BtnCloseIn1.setBounds(510, 30, 100, 30);

        BtnSimpan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan1.setMnemonic('S');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+S");
        BtnSimpan1.setName("BtnSimpan1"); // NOI18N
        BtnSimpan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan1ActionPerformed(evt);
            }
        });
        BtnSimpan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan1KeyPressed(evt);
            }
        });
        internalFrame3.add(BtnSimpan1);
        BtnSimpan1.setBounds(405, 30, 100, 30);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        internalFrame3.add(jLabel13);
        jLabel13.setBounds(0, 32, 77, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setSelectionColor(new java.awt.Color(255, 255, 255));
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        internalFrame3.add(kddokter);
        kddokter.setBounds(81, 32, 100, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        TDokter.setSelectionColor(new java.awt.Color(255, 255, 255));
        TDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDokterKeyPressed(evt);
            }
        });
        internalFrame3.add(TDokter);
        TDokter.setBounds(183, 32, 181, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('7');
        BtnSeek1.setToolTipText("ALt+7");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnSeek1);
        BtnSeek1.setBounds(366, 32, 28, 23);

        WindowInput2.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total BHP & Obat]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame2.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame2.setLayout(null);

        TotalObat.setHighlighter(null);
        TotalObat.setName("TotalObat"); // NOI18N
        TotalObat.setSelectionColor(new java.awt.Color(255, 255, 255));
        TotalObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TotalObatKeyPressed(evt);
            }
        });
        internalFrame2.add(TotalObat);
        TotalObat.setBounds(60, 30, 180, 23);

        jLabel8.setText("Total :");
        jLabel8.setName("jLabel8"); // NOI18N
        internalFrame2.add(jLabel8);
        jLabel8.setBounds(0, 30, 57, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('P');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+P");
        BtnCloseIn.setName("BtnCloseIn"); // NOI18N
        BtnCloseIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseInActionPerformed(evt);
            }
        });
        BtnCloseIn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCloseInKeyPressed(evt);
            }
        });
        internalFrame2.add(BtnCloseIn);
        BtnCloseIn.setBounds(465, 30, 100, 30);

        BtnSimpan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan2.setMnemonic('S');
        BtnSimpan2.setText("Simpan");
        BtnSimpan2.setToolTipText("Alt+S");
        BtnSimpan2.setName("BtnSimpan2"); // NOI18N
        BtnSimpan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan2ActionPerformed(evt);
            }
        });
        BtnSimpan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpan2KeyPressed(evt);
            }
        });
        internalFrame2.add(BtnSimpan2);
        BtnSimpan2.setBounds(255, 30, 100, 30);

        BtnBatal1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal1.setMnemonic('H');
        BtnBatal1.setText("Hapus");
        BtnBatal1.setToolTipText("Alt+H");
        BtnBatal1.setName("BtnBatal1"); // NOI18N
        BtnBatal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatal1ActionPerformed(evt);
            }
        });
        BtnBatal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatal1KeyPressed(evt);
            }
        });
        internalFrame2.add(BtnBatal1);
        BtnBatal1.setBounds(360, 30, 100, 30);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowInput3.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput3.setName("WindowInput3"); // NOI18N
        WindowInput3.setUndecorated(true);
        WindowInput3.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Tambah Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbTambahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan.setName("tbTambahan"); // NOI18N
        scrollPane1.setViewportView(tbTambahan);

        internalFrame4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label15);

        norawat.setEditable(false);
        norawat.setName("norawat"); // NOI18N
        norawat.setPreferredSize(new java.awt.Dimension(150, 23));
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        panelisi1.add(norawat);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi1.add(BtnTambah);

        BtnSimpan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan3.setMnemonic('S');
        BtnSimpan3.setText("Simpan");
        BtnSimpan3.setToolTipText("Alt+S");
        BtnSimpan3.setName("BtnSimpan3"); // NOI18N
        BtnSimpan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan3ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnSimpan3);

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
        panelisi1.add(BtnHapus);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluar1.setMnemonic('P');
        BtnKeluar1.setText("Tutup");
        BtnKeluar1.setToolTipText("Alt+P");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKeluar1);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowInput3.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Poliklinik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('P');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+P");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(510, 30, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(405, 30, 100, 30);

        jLabel14.setText("Poli Dituju :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame5.add(jLabel14);
        jLabel14.setBounds(0, 32, 77, 23);

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.setSelectionColor(new java.awt.Color(255, 255, 255));
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        internalFrame5.add(kdpoli);
        kdpoli.setBounds(81, 32, 100, 23);

        nmpoli.setEditable(false);
        nmpoli.setName("nmpoli"); // NOI18N
        nmpoli.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame5.add(nmpoli);
        nmpoli.setBounds(183, 32, 181, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('7');
        BtnSeek4.setToolTipText("ALt+7");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSeek4);
        BtnSeek4.setBounds(366, 32, 28, 23);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowInput5.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput5.setName("WindowInput5"); // NOI18N
        WindowInput5.setUndecorated(true);
        WindowInput5.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Potongan Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbPotongan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPotongan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPotongan.setName("tbPotongan"); // NOI18N
        scrollPane2.setViewportView(tbPotongan);

        internalFrame6.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label16.setText("No.Rawat :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(label16);

        norawatpotongan.setEditable(false);
        norawatpotongan.setName("norawatpotongan"); // NOI18N
        norawatpotongan.setPreferredSize(new java.awt.Dimension(150, 23));
        norawatpotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatpotonganKeyPressed(evt);
            }
        });
        panelisi2.add(norawatpotongan);

        BtnTambahPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambahPotongan.setMnemonic('T');
        BtnTambahPotongan.setText("Tambah");
        BtnTambahPotongan.setToolTipText("Alt+T");
        BtnTambahPotongan.setName("BtnTambahPotongan"); // NOI18N
        BtnTambahPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambahPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnTambahPotongan);

        BtnSimpanPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanPotongan.setMnemonic('S');
        BtnSimpanPotongan.setText("Simpan");
        BtnSimpanPotongan.setToolTipText("Alt+S");
        BtnSimpanPotongan.setName("BtnSimpanPotongan"); // NOI18N
        BtnSimpanPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnSimpanPotongan);

        BtnHapusPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapusPotongan.setMnemonic('H');
        BtnHapusPotongan.setText("Hapus");
        BtnHapusPotongan.setToolTipText("Alt+H");
        BtnHapusPotongan.setName("BtnHapusPotongan"); // NOI18N
        BtnHapusPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapusPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnHapusPotongan);

        BtnKeluarPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarPotongan.setMnemonic('P');
        BtnKeluarPotongan.setText("Tutup");
        BtnKeluarPotongan.setToolTipText("Alt+P");
        BtnKeluarPotongan.setName("BtnKeluarPotongan"); // NOI18N
        BtnKeluarPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluarPotongan);

        internalFrame6.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowInput5.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Billing/Pembayaran Ralan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass1.setPreferredSize(new java.awt.Dimension(100, 45));
        panelGlass1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 10));

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass1.add(jLabel3);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(130, 23));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        panelGlass1.add(TNoRw);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass1.add(TNoRM);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.setPreferredSize(new java.awt.Dimension(310, 23));
        panelGlass1.add(TPasien);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('R');
        BtnCari.setToolTipText("Alt+R");
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
        panelGlass1.add(BtnCari);

        jLabel4.setText("Tanggal :");
        jLabel4.setName("jLabel4"); // NOI18N
        jLabel4.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass1.add(jLabel4);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(100, 100, 100));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-21" }));
        DTPTgl.setDisplayFormat("yyyy-MM-dd");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        panelGlass1.add(DTPTgl);

        internalFrame1.add(panelGlass1, java.awt.BorderLayout.PAGE_START);

        panelGlass2.setForeground(new java.awt.Color(153, 0, 51));
        panelGlass2.setPreferredSize(new java.awt.Dimension(100, 105));
        panelGlass2.setLayout(null);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        panelGlass2.add(BtnKeluar);
        BtnKeluar.setBounds(734, 60, 100, 30);

        TtlSemua.setEditable(false);
        TtlSemua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TtlSemua.setHighlighter(null);
        TtlSemua.setName("TtlSemua"); // NOI18N
        TtlSemua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TtlSemuaKeyPressed(evt);
            }
        });
        panelGlass2.add(TtlSemua);
        TtlSemua.setBounds(179, 10, 264, 25);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('B');
        BtnSimpan.setText("Bayar");
        BtnSimpan.setToolTipText("Alt+B");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        panelGlass2.add(BtnSimpan);
        BtnSimpan.setBounds(522, 60, 100, 30);

        BtnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnView.setMnemonic('L');
        BtnView.setText("Lihat");
        BtnView.setToolTipText("Alt+L");
        BtnView.setName("BtnView"); // NOI18N
        BtnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnViewActionPerformed(evt);
            }
        });
        BtnView.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnViewKeyPressed(evt);
            }
        });
        panelGlass2.add(BtnView);
        BtnView.setBounds(628, 60, 100, 30);

        chkPiutang.setText("Jadikan Piutang");
        chkPiutang.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkPiutang.setName("chkPiutang"); // NOI18N
        chkPiutang.setOpaque(false);
        chkPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPiutangActionPerformed(evt);
            }
        });
        panelGlass2.add(chkPiutang);
        chkPiutang.setBounds(520, 10, 110, 23);

        TBayar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TBayar.setHighlighter(null);
        TBayar.setName("TBayar"); // NOI18N
        panelGlass2.add(TBayar);
        TBayar.setBounds(179, 40, 264, 25);

        TKembali.setEditable(false);
        TKembali.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        TKembali.setHighlighter(null);
        TKembali.setName("TKembali"); // NOI18N
        panelGlass2.add(TKembali);
        TKembali.setBounds(179, 70, 264, 25);

        jLabel6.setText("Kembali : Rp.");
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass2.add(jLabel6);
        jLabel6.setBounds(0, 70, 177, 23);

        DTPTempo.setEditable(false);
        DTPTempo.setForeground(new java.awt.Color(100, 100, 100));
        DTPTempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-21" }));
        DTPTempo.setDisplayFormat("yyyy-MM-dd");
        DTPTempo.setName("DTPTempo"); // NOI18N
        DTPTempo.setOpaque(false);
        DTPTempo.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass2.add(DTPTempo);
        DTPTempo.setBounds(732, 10, 100, 23);

        chkPrint.setSelected(true);
        chkPrint.setText("<>> Total Tagihan : Rp.");
        chkPrint.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkPrint.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        chkPrint.setName("chkPrint"); // NOI18N
        chkPrint.setOpaque(false);
        panelGlass2.add(chkPrint);
        chkPrint.setBounds(0, 10, 178, 23);

        jLabel7.setText("Jatuh Tempo :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass2.add(jLabel7);
        jLabel7.setBounds(633, 10, 95, 23);

        jLabel5.setText("Bayar : Rp.");
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass2.add(jLabel5);
        jLabel5.setBounds(0, 40, 177, 23);

        internalFrame1.add(panelGlass2, java.awt.BorderLayout.PAGE_END);

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbAdmin.setToolTipText("");
        tbAdmin.setComponentPopupMenu(jPopupMenu1);
        tbAdmin.setName("tbAdmin"); // NOI18N
        tbAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAdminMouseClicked(evt);
            }
        });
        tbAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAdminKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbAdmin);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isRawat();
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        WindowInput.dispose();
        WindowInput2.dispose();
        WindowInput3.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnView,BtnSimpan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TtlSemuaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TtlSemuaKeyPressed
        Valid.pindah(evt,BtnKeluar,BtnSimpan);
    }//GEN-LAST:event_TtlSemuaKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        isHitung();
        uangmuka=0;sisapiutang=0;
        if(!TBayar.getText().trim().equals("")){
            uangmuka=Double.parseDouble(TBayar.getText());
        }
        sisapiutang=ttl-uangmuka;
        try {
            pscekbilling.setString(1,TNoRw.getText());
            rscekbilling=pscekbilling.executeQuery();
            if(rscekbilling.next()){
                i=rscekbilling.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }         
        
        if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(i>0){
            JOptionPane.showMessageDialog(null,"Maaf, data tagihan pasien dengan No.Rawat tersebut sudah pernah disimpan...!!!");
        }else{            
            try {  
                koneksi.setAutoCommit(false);                
                for(i=0;i<tabModeRwJlDr.getRowCount();i++){  
                    psbiling.setString(1,TNoRw.getText());
                    psbiling.setString(2,DTPTgl.getSelectedItem().toString());
                    psbiling.setString(3,tabModeRwJlDr.getValueAt(i,1).toString());
                    psbiling.setString(4,tabModeRwJlDr.getValueAt(i,2).toString().replaceAll("'","`"));
                    psbiling.setString(5,tabModeRwJlDr.getValueAt(i,3).toString());
                    try {                        
                        psbiling.setDouble(6,Valid.SetAngka(tabModeRwJlDr.getValueAt(i,4).toString()));
                    } catch (Exception e) {
                        psbiling.setDouble(6,0);
                    }
                    try {
                        psbiling.setDouble(7,Valid.SetAngka(tabModeRwJlDr.getValueAt(i,5).toString()));
                    } catch (Exception e) {
                        psbiling.setDouble(7,0);
                    }
                    try {
                        psbiling.setDouble(8,Valid.SetAngka(tabModeRwJlDr.getValueAt(i,6).toString()));                        
                    } catch (Exception e) {
                        psbiling.setDouble(8,0);   
                    }
                    try {
                        psbiling.setDouble(9,Valid.SetAngka(tabModeRwJlDr.getValueAt(i,7).toString())); 
                    } catch (Exception e) {
                        psbiling.setDouble(9,0);
                    }                    
                    psbiling.setString(10,tabModeRwJlDr.getValueAt(i,8).toString());
                    psbiling.executeUpdate();
                }
                
                if(chkPiutang.isSelected()==false){
                    rs2=koneksi.prepareStatement("select kd_rek from rekening where kd_rek='42000'").executeQuery();
                    if(!rs2.next()){
                        koneksi.prepareStatement("insert into rekening values('42000','PEMBAYARAN PASIEN','R','K')").executeUpdate();
                    }

                    rs3=koneksi.prepareStatement("select kd_rek from rekening where kd_rek='11120'").executeQuery();
                    if(!rs3.next()){
                        koneksi.prepareStatement("insert into rekening values('11120','KAS DI TANGAN','N','D')").executeUpdate();
                    }

                    rs4=koneksi.prepareStatement("select kd_rek from rekeningtahun where thn='"+DTPTgl.getSelectedItem().toString().substring(0,4) +"' and kd_rek='42000'").executeQuery();
                    if(!rs4.next()){
                        koneksi.prepareStatement("insert into rekeningtahun values('"+DTPTgl.getSelectedItem().toString().substring(0,4) +"','42000','0')").executeUpdate();
                    }

                    rs5=koneksi.prepareStatement("select kd_rek from rekeningtahun where thn='"+DTPTgl.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'").executeQuery();
                    if(!rs5.next()){
                        koneksi.prepareStatement("insert into rekeningtahun values('"+DTPTgl.getSelectedItem().toString().substring(0,4) +"','11120','0')").execute();
                    }

                    Sequel.queryu2("delete from tampjurnal");
                    Sequel.menyimpan("tampjurnal","'42000','PEMBAYARAN PASIEN','0','"+ttl+"'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','"+ttl+"','0'","Rekening"); 
                    jur.simpanJurnal(TNoRw.getText(),DTPTgl.getSelectedItem().toString(),"U","PEMBAYARAN PASIEN");
                }else if(chkPiutang.isSelected()==true){
                    Sequel.queryu2("insert into piutang_pasien values ('"+
                            TNoRw.getText()+"','"+DTPTgl.getSelectedItem()+"','"+TNoRM.getText()+"','Belum Lunas','"+ttl+"','"+
                            TBayar.getText()+"','"+sisapiutang+"','"+DTPTempo.getSelectedItem()+"')");
                    rs2=koneksi.prepareStatement("select kd_rek from rekening where kd_rek='71000'").executeQuery();
                    if(!rs2.next()){
                        koneksi.prepareStatement("insert into rekening values('71000','PIUTANG PASIEN','R','D')").executeUpdate();
                    }
                    
                    rs3=koneksi.prepareStatement("select kd_rek from rekening where kd_rek='11120'").executeQuery();
                    if(!rs3.next()){
                        koneksi.prepareStatement("insert into rekening values('11120','KAS DI TANGAN','N','D')").executeUpdate();
                    }
                    
                    rs4=koneksi.prepareStatement("select kd_rek from rekeningtahun where thn='"+DTPTgl.getSelectedItem().toString().substring(0,4) +"' and kd_rek='71000'").executeQuery();
                    if(!rs4.next()){
                        koneksi.prepareStatement("insert into rekeningtahun values('"+DTPTgl.getSelectedItem().toString().substring(0,4) +"','71000','0')").executeUpdate();
                    }
                    
                    rs5=koneksi.prepareStatement("select kd_rek from rekeningtahun where thn='"+DTPTgl.getSelectedItem().toString().substring(0,4) +"' and kd_rek='11120'").executeQuery();
                    if(!rs5.next()){
                        koneksi.prepareStatement("insert into rekeningtahun values('"+DTPTgl.getSelectedItem().toString().substring(0,4) +"','11120','0')").executeUpdate();
                    }
                    
                    Sequel.queryu2("delete from tampjurnal");
                    
                    Sequel.menyimpan("tampjurnal","'71000','PIUTANG PASIEN','"+sisapiutang+"','0'","Rekening");    
                    Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','0','"+sisapiutang+"'","Rekening"); 
                    jur.simpanJurnal(TNoRw.getText(),DTPTgl.getSelectedItem().toString(),"U","PIUTANG PASIEN");
                    
                            if(uangmuka>0){
                                rs6=koneksi.prepareStatement("select kd_rek from rekening where kd_rek='72000'").executeQuery();
                                if(!rs6.next()){
                                    koneksi.prepareStatement("insert into rekening values('72000','BAYAR PIUTANG','R','K')").executeUpdate();
                                }
                    
                                rs8=koneksi.prepareStatement("select kd_rek from rekeningtahun where thn='"+DTPTgl.getSelectedItem().toString().substring(0,4) +"' and kd_rek='72000'").executeQuery();
                                if(!rs8.next()){
                                    koneksi.prepareStatement("insert into rekeningtahun values('"+DTPTgl.getSelectedItem().toString().substring(0,4) +"','72000','0')").executeUpdate();
                                }
                                
                                Sequel.queryu2("delete from tampjurnal");
                    
                                Sequel.menyimpan("tampjurnal","'72000','BAYAR PIUTANG','0','"+(uangmuka)+"'","Rekening");    
                                Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','"+(uangmuka)+"','0'","Rekening"); 
                                jur.simpanJurnal(TNoRw.getText(),DTPTgl.getSelectedItem().toString(),"U","BAYAR PIUTANG");
                                
                                //rs6.close();
                                //rs8.close();
                            }
                    //rs2.close();
                    //rs3.close();
                    //rs4.close();
                    //rs5.close();     
                }
                
                Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw,"stts='Bayar'");
                koneksi.setAutoCommit(true);
            }catch (SQLException ex) {
                System.out.println("Pesan Error : "+ex);            
                JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Data yang sama dimasukkan sebelumnya...!");
            }
        }
        
        
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            i=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
            if(TNoRw.getText().trim().equals("")||TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
                Valid.textKosong(TNoRw,"Pasien");
            }else if(i<=0){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan koreksi dan simpan data nota pembayaran terlebih dahulu...!!!");
            }else if(tabModeRwJlDr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                //TCari.requestFocus();
            }else if(tabModeRwJlDr.getRowCount()!=0){
                try{
                    koneksi.setAutoCommit(false);
                    Sequel.queryu2("delete from temporary_bayar_ralan"); 
                    for(i=0;i<tabModeRwJlDr.getRowCount();i++){  
                        if(tabModeRwJlDr.getValueAt(i,0).toString().equals("true")){
                            biaya="";
                            try {
                                biaya=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,4).toString())); 
                            } catch (Exception e) {
                                biaya="";
                            }                            
                            tambahan="";
                            try {
                                tambahan=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,6).toString())); 
                            } catch (Exception e) {
                                tambahan="";
                            }
                            totals="";
                            try {
                                totals=Valid.SetAngka(Double.parseDouble(tabModeRwJlDr.getValueAt(i,7).toString())); 
                            } catch (Exception e) {
                                totals="";
                            }
                            pstemporary.setString(1,tabModeRwJlDr.getValueAt(i,1).toString().replaceAll("'","`"));
                            pstemporary.setString(2,tabModeRwJlDr.getValueAt(i,2).toString().replaceAll("'","`"));
                            pstemporary.setString(3,tabModeRwJlDr.getValueAt(i,3).toString().replaceAll("'","`"));
                            pstemporary.setString(4,biaya);
                            try {
                                pstemporary.setString(5,tabModeRwJlDr.getValueAt(i,5).toString().replaceAll("'","`"));
                            } catch (Exception e) {
                                pstemporary.setString(5,"");
                            }                            
                            pstemporary.setString(6,tambahan);
                            pstemporary.setString(7,totals);
                            try {
                               pstemporary.setString(8,tabModeRwJlDr.getValueAt(i,8).toString().replaceAll("'","`"));  
                            } catch (Exception e) {
                                pstemporary.setString(8,""); 
                            }                              
                            pstemporary.executeUpdate();                         
                        }                
                    }
                    //String norm=Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRw.getText());
                    //String pasien=Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?",norm);
                    String alamat=Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ",TNoRM.getText());
                    if(chkPiutang.isSelected()==false){
                        Sequel.menyimpan("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+DTPTgl.getSelectedItem()+
                                "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+ttl+"','Belum'","No.Rawat dan Tanggal");
                        Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL BAYAR',':','','','','','"+TtlSemua.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    }else if(chkPiutang.isSelected()==true){
                        if(TBayar.getText().equals("")){
                            Valid.textKosong(TBayar,"Uang Muka");
                        }else{
                            Sequel.menyimpan("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+DTPTgl.getSelectedItem()+
                                "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+TBayar.getText()+"','Belum'","No.Rawat dan Tanggal");
                            Sequel.menyimpan("temporary_bayar_ralan","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                            Sequel.menyimpan("temporary_bayar_ralan","'0','UANG MUKA',':','','','','','"+Valid.SetAngka(Double.parseDouble(TBayar.getText()))+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                            Sequel.menyimpan("temporary_bayar_ralan","'0','SISA PIUTANG',':','','','','','"+TKembali.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                        }                    
                    }

                    if(chkPrint.isSelected()==true){     
                        //Valid.MyReport("rptNotaBayar.jrxml","report","::[ Nota Pembayaran ]::",
                       //     "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ralan order by no asc");
                        Valid.panggilUrl("billing/LaporanBilling.php?petugas="+var.getkode().replaceAll(" ","_"));
                    }
                    koneksi.setAutoCommit(true);
                }catch(Exception ex){
                    System.out.println(ex);
                }                
               this.setCursor(Cursor.getDefaultCursor());
                
          }        
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnView);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnViewActionPerformed
            Object[] options = {"Tagihan Masuk", "Piutang Pasien"};
            
            String input;
            i = 0;
            try{
                input = (String)JOptionPane.showInputDialog(null,"Silahkan pilih yang mau ditampilkan!","Keuangan",JOptionPane.QUESTION_MESSAGE,null,options,"Nota 1");
                switch (input) {
                    case "Tagihan Masuk":
                        i=1;
                        break;
                    case "Piutang Pasien":
                        i=2;
                        break;
                }
            }catch(HeadlessException e){
                i=0;
            }        
            
            if(i>0){
                if(i==1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtBiaya billing=new DlgLhtBiaya(null,false);
                    billing.tampil();        
                    billing.setSize(this.getWidth(),this.getHeight());
                    billing.setLocationRelativeTo(this);
                    billing.setAlwaysOnTop(false);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else if(i==2){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtPiutang billing=new DlgLhtPiutang(null,false);
                    billing.tampil();        
                    billing.setSize(this.getWidth(),this.getHeight());
                    billing.setLocationRelativeTo(this);
                    billing.setAlwaysOnTop(false);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                } 
            }   
    }//GEN-LAST:event_BtnViewActionPerformed

    private void BtnViewKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnViewKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnViewActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnViewKeyPressed

private void tbAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAdminMouseClicked
       if(tabModeRwJlDr.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }            
            if(evt.getClickCount()==1){
                i=tbAdmin.getSelectedColumn();
                if(i==1){
                    switch (tbAdmin.getValueAt(tbAdmin.getSelectedRow(),i).toString()) {
                        case "Tindakan":
                            kdptg=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                            nmptg=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kdptg);
                            dlgrwjl.perawatan.setNoRm(TNoRw.getText(),"-","-",kdptg,nmptg,"rawat_jl_dr","-","-","-","-","","");
                            //perawatan.setModal(true);
                            dlgrwjl.perawatan.emptTeks();
                            dlgrwjl.perawatan.isCek();
                            dlgrwjl.perawatan.tampil();
                            dlgrwjl.perawatan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                            dlgrwjl.perawatan.setAlwaysOnTop(false);
                            dlgrwjl.perawatan.setVisible(true);
                            //dispose();
                            break;
                        case "Obat & BHP":
                            dlgobt.emptTeksobat();
                            //dlgobt.setModal(true);
                            dlgobt.isCek();
                            dlgobt.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                            dlgobt.tampilobat();
                            dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            dlgobt.setLocationRelativeTo(internalFrame1);
                            dlgobt.setAlwaysOnTop(false);
                            dlgobt.setVisible(true);
                            //dispose();
                            break;
                        case "Tambahan Biaya":
                            ppTambahanActionPerformed(null);
                            break;
                        case "Potongan Biaya":
                            ppPotonganActionPerformed(null);
                            break;
                    }
                     
                }
            }
        }
}//GEN-LAST:event_tbAdminMouseClicked

private void tbAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAdminKeyPressed
        if(tabModeRwJlDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbAdmin.getSelectedColumn();
                if(i==1){
                    switch (tbAdmin.getValueAt(tbAdmin.getSelectedRow(), i).toString()) {
                        case "Tindakan":
                            kdptg=Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",TNoRw.getText());
                            nmptg=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kdptg);
                            dlgrwjl.perawatan.setNoRm(TNoRw.getText(),"-","-",kdptg,nmptg,"rawat_jl_dr","-","-","-","-","","");
                            dlgrwjl.perawatan.emptTeks();
                            //perawatan.setModal(true);
                            dlgrwjl.perawatan.isCek();
                            dlgrwjl.perawatan.tampil();
                            dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
                            dlgrwjl.perawatan.setAlwaysOnTop(false);
                            dlgrwjl.perawatan.setVisible(true);
                            //dispose();
                            break;
                        case "Obat & BHP":
                            dlgobt.emptTeksobat();
                            //dlgobt.setModal(true);
                            dlgobt.isCek();
                            dlgobt.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                            dlgobt.tampilobat();
                            dlgobt.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                            dlgobt.setLocationRelativeTo(internalFrame1);
                            dlgobt.setAlwaysOnTop(false);
                            dlgobt.setVisible(true);
                            //dispose();
                            break;
                        case "Tambahan Biaya":
                            ppTambahanActionPerformed(null);
                            break;
                        case "Potongan Biaya":
                            ppPotonganActionPerformed(null);
                            break;
                    }
                }
            }
        }
}//GEN-LAST:event_tbAdminKeyPressed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            dlgrwjl.isCek();
            dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwjl.setLocationRelativeTo(internalFrame1);
            dlgrwjl.emptTeks();
        
            dlgrwjl.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());    
            dlgrwjl.tampilDr();
            dlgrwjl.setAlwaysOnTop(false);
            dlgrwjl.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.emptTeks(); 
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date()); 
            dlgrwinap.tampilPO();
            dlgrwinap.setAlwaysOnTop(false);
            dlgrwinap.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TNoRw,BtnSimpan);
}//GEN-LAST:event_DTPTglKeyPressed

private void MnHapusTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanActionPerformed
     Valid.hapusTable(tabModeRwJlDr,TNoRw,"billing","no_rawat");
         
         i= JOptionPane.showConfirmDialog(rootPane,"Anda sudah melakukan penghapusan data tagihan pasien.....!!!!\n"+
                                                             "Silahkan lakukan 'Jurnal Penyesuaian' agar transaksi akuntansi tetap balance.\n"+
                                                             "Anda ingin melakukan 'Jurnal Penyesuaian' sekarang...??",
                                                             "Konfirmasi",JOptionPane.YES_NO_OPTION);
         if (i == JOptionPane.YES_OPTION) {
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
             Sequel.menyimpan("tampjurnal","'42000','PEMBAYARAN PASIEN','"+ttl+"','0'","Rekening");    
             Sequel.menyimpan("tampjurnal","'11120','KAS DI TANGAN','0','"+ttl+"'","Rekening"); 
             DlgJurnal form=new DlgJurnal(null,false);
             form.emptTeks();  
             form.tampil();
             form.setData(TNoRw.getText());
             form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
             form.setLocationRelativeTo(internalFrame1);
             form.setAlwaysOnTop(false);
             form.setVisible(true);
             this.setCursor(Cursor.getDefaultCursor());
        }
        isRawat();
}//GEN-LAST:event_MnHapusTagihanActionPerformed

private void chkPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPiutangActionPerformed
  if(chkPiutang.isSelected()==true){
      jLabel5.setText("Uang Muka : Rp.");
      jLabel6.setText("Sisa Piutang : Rp.");
      jLabel7.setVisible(true);
      DTPTempo.setVisible(true);
  }else if(chkPiutang.isSelected()==false){
      jLabel5.setText("Bayar : Rp.");
      jLabel6.setText("Kembali : Rp.");      
      jLabel7.setVisible(false);
      DTPTempo.setVisible(false);
  }
  isKembali();
}//GEN-LAST:event_chkPiutangActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModeRwJlDr.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            DlgPenjualan penjualan=new DlgPenjualan(null,false); 
            penjualan.isCek();
            penjualan.setPasien(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
            penjualan.tampil();    
            penjualan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            penjualan.setLocationRelativeTo(internalFrame1);
            penjualan.setAlwaysOnTop(false);
            penjualan.setVisible(true);
        }
}//GEN-LAST:event_MnPenjualanActionPerformed

private void ppDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDokterActionPerformed
    WindowInput2.setSize(630,80);
    WindowInput2.setLocationRelativeTo(internalFrame1);
    WindowInput2.setAlwaysOnTop(false);
    WindowInput2.setVisible(true);
}//GEN-LAST:event_ppDokterActionPerformed

private void BtnCloseIn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn1ActionPerformed
    WindowInput2.dispose();
}//GEN-LAST:event_BtnCloseIn1ActionPerformed

private void BtnCloseIn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseIn1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnCloseIn1KeyPressed

private void BtnSimpan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kddokter.getText().trim().equals("")||TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            Valid.editTable(tabModeRwJlDr,"rawat_jl_dr","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            isRawat();
            WindowInput2.dispose();
        }
}//GEN-LAST:event_BtnSimpan1ActionPerformed

private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSimpan1KeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else{
            Valid.pindah(evt,BtnCloseIn1,BtnSimpan1);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        
}//GEN-LAST:event_TDokterKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.tampil(" order by kd_dokter");
        dokter.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void ppObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppObatActionPerformed
    TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));  
    WindowInput.setSize(590,80);
    WindowInput.setLocationRelativeTo(internalFrame1);
    TotalObat.requestFocus();
    WindowInput.setAlwaysOnTop(false);
    WindowInput.setVisible(true);
}//GEN-LAST:event_ppObatActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnSimpan);
}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnSimpan, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan2ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowInput.setVisible(false);
            isRawat();
            isKembali();
        }
}//GEN-LAST:event_BtnSimpan2ActionPerformed

private void BtnSimpan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan2KeyPressed
        Valid.pindah(evt,TotalObat,BtnBatal1);
}//GEN-LAST:event_BtnSimpan2KeyPressed

private void BtnBatal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatal1ActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat=? ",TNoRw.getText());
            WindowInput.setVisible(false);
            isRawat();isKembali();
        }
}//GEN-LAST:event_BtnBatal1ActionPerformed

private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnBatal1ActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatal1KeyPressed

private void ppTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTambahanActionPerformed
    norawat.setText(TNoRw.getText());
    tampilTambahan(norawat.getText());
    WindowInput3.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
    WindowInput3.setLocationRelativeTo(internalFrame1);
    WindowInput3.setAlwaysOnTop(false);
    WindowInput3.setVisible(true);
}//GEN-LAST:event_ppTambahanActionPerformed

private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
    Valid.pindah(evt, BtnKeluar, BtnSimpan);
}//GEN-LAST:event_norawatKeyPressed

private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
    tabModeTambahan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan3ActionPerformed
    if(norawat.getText().trim().equals("")||(tbTambahan.getRowCount()<=0)){
        Valid.textKosong(norawat,"Data");
    }else{
        for(i=0;i<tbTambahan.getRowCount();i++){
            if(Valid.SetAngka(tbTambahan.getValueAt(i,1).toString())>0){
                Sequel.menyimpan("tambahan_biaya","'"+norawat.getText()+"','"+tbTambahan.getValueAt(i,0).toString()+
                        "','"+tbTambahan.getValueAt(i,1).toString()+"'","Tambahan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowInput3.dispose();
    }
}//GEN-LAST:event_BtnSimpan3ActionPerformed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    Sequel.queryu("delete from tambahan_biaya where no_rawat='"+norawat.getText()+
            "' and nama_biaya='"+tbTambahan.getValueAt(tbTambahan.getSelectedRow(),0).toString() +"'");
    tabModeTambahan.removeRow(tbTambahan.getSelectedRow());
    isRawat();
    isKembali();
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
    WindowInput3.dispose();
}//GEN-LAST:event_BtnKeluar1ActionPerformed

private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
    WindowInput4.dispose();
}//GEN-LAST:event_BtnCloseIn4ActionPerformed

private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }if(kdpoli.getText().trim().equals("")||nmpoli.getText().trim().equals("")){
            Valid.textKosong(kdpoli,"Poli");
        }else{
            Valid.editTable(tabModeRwJlDr,"reg_periksa","no_rawat",TNoRw," kd_poli='"+kdpoli.getText()+"'");
            isRawat();
            WindowInput4.dispose();
        }
}//GEN-LAST:event_BtnSimpan4ActionPerformed

private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", nmpoli,kdpoli.getText());
        }else{
            Valid.pindah(evt,BtnCloseIn4,BtnSimpan4);
        }
}//GEN-LAST:event_kdpoliKeyPressed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
   poli.emptTeks();
        poli.isCek();
        poli.tampil();
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void ppPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPoliActionPerformed
    WindowInput4.setSize(630,80);
    WindowInput4.setLocationRelativeTo(internalFrame1);
    WindowInput4.setAlwaysOnTop(false);
    WindowInput4.setVisible(true);
}//GEN-LAST:event_ppPoliActionPerformed

private void MnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnReturJualActionPerformed
      var.setStatus(false);       
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturJual returjual=new DlgReturJual(null,false);
        returjual.emptTeks();
        returjual.tampil();  
        returjual.isCek(); 
        returjual.setPasien(Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRw.getText()));
        returjual.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        returjual.setLocationRelativeTo(internalFrame1);
        returjual.setAlwaysOnTop(false);
        returjual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnReturJualActionPerformed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        isRawat();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TNoRw,DTPTgl);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void norawatpotonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatpotonganKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_norawatpotonganKeyPressed

private void BtnTambahPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahPotonganActionPerformed
  tabModePotongan.addRow(new Object[]{"",""});
}//GEN-LAST:event_BtnTambahPotonganActionPerformed

private void BtnSimpanPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanPotonganActionPerformed
   if(norawatpotongan.getText().trim().equals("")||(tbPotongan.getRowCount()<=0)){
        Valid.textKosong(norawat,"Data");
    }else{
        for(r=0;r<tbPotongan.getRowCount();r++){
            if(Valid.SetAngka(tbPotongan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("pengurangan_biaya","'"+norawatpotongan.getText()+"','"+tbPotongan.getValueAt(r,0).toString()+
                        "','"+tbPotongan.getValueAt(r,1).toString()+"'","Potongan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowInput5.dispose();
    }
}//GEN-LAST:event_BtnSimpanPotonganActionPerformed

private void BtnHapusPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusPotonganActionPerformed
    Sequel.queryu("delete from pengurangan_biaya where no_rawat='"+norawatpotongan.getText()+
            "' and nama_pengurangan='"+tbPotongan.getValueAt(tbPotongan.getSelectedRow(),0).toString() +"'");
    tabModePotongan.removeRow(tbPotongan.getSelectedRow());
    isRawat();
    isKembali();
}//GEN-LAST:event_BtnHapusPotonganActionPerformed

private void BtnKeluarPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarPotonganActionPerformed
  WindowInput5.dispose();
}//GEN-LAST:event_BtnKeluarPotonganActionPerformed

private void ppPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPotonganActionPerformed
   norawatpotongan.setText(TNoRw.getText());
    tampilPotongan(norawatpotongan.getText());
    WindowInput5.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
    WindowInput5.setLocationRelativeTo(internalFrame1);
    WindowInput5.setAlwaysOnTop(false);
    WindowInput5.setVisible(true);
}//GEN-LAST:event_ppPotonganActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{           
            DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
            periksalab.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            periksalab.setLocationRelativeTo(internalFrame1);
            periksalab.emptTeks();
            periksalab.setNoRm(TNoRw.getText());  
            periksalab.tampil();
            periksalab.setAlwaysOnTop(false);
            periksalab.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBilingRalan dialog = new DlgBilingRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal1;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn1;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnHapus;
    private widget.Button BtnHapusPotongan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluarPotongan;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.Button BtnSimpan2;
    private widget.Button BtnSimpan3;
    private widget.Button BtnSimpan4;
    private widget.Button BtnSimpanPotongan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambahPotongan;
    private widget.Button BtnView;
    private widget.Tanggal DTPTempo;
    private widget.Tanggal DTPTgl;
    private javax.swing.JMenuItem MnHapusTagihan;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnReturJual;
    private widget.ScrollPane Scroll;
    public widget.TextBox TBayar;
    private widget.TextBox TDokter;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TotalObat;
    private widget.TextBox TtlSemua;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput2;
    private javax.swing.JDialog WindowInput3;
    private javax.swing.JDialog WindowInput4;
    private javax.swing.JDialog WindowInput5;
    private widget.CekBox chkPiutang;
    private widget.CekBox chkPrint;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpoli;
    private widget.Label label15;
    private widget.Label label16;
    private widget.TextBox nmpoli;
    private widget.TextBox norawat;
    private widget.TextBox norawatpotongan;
    private widget.panelisi panelGlass1;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppDokter;
    private javax.swing.JMenuItem ppObat;
    private javax.swing.JMenuItem ppPoli;
    private javax.swing.JMenuItem ppPotongan;
    private javax.swing.JMenuItem ppTambahan;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbAdmin;
    private widget.Table tbPotongan;
    private widget.Table tbTambahan;
    // End of variables declaration//GEN-END:variables

    public void isRawat() {
        try {            
            pscekbilling.setString(1,TNoRw.getText());
            rscekbilling=pscekbilling.executeQuery();
            if(rscekbilling.next()){
                i=rscekbilling.getInt(1);
            }            
            
            pscarirm.setString(1,TNoRw.getText());
            rscarirm=pscarirm.executeQuery();
            if(rscarirm.next()){
                TNoRM.setText(rscarirm.getString(1));
            }
            
            pscaripasien.setString(1,TNoRM.getText());
            rscaripasien=pscaripasien.executeQuery();
            if(rscaripasien.next()){
                TPasien.setText(rscaripasien.getString(1));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         //i=Sequel.cariInteger("select count(billing.no_rawat) from billing where billing.no_rawat=?",TNoRw.getText());
         //Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
         //Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
         if(i<=0){
             prosesCariReg();             
             tabModeRwJlDr.addRow(new Object[]{true,"Tindakan",":","","","","","","Ralan Dokter"});
             prosesCariRwJlDr();
             prosesCariRwJlPr();
             prosesCariPeriksaLab();
             tabModeRwJlDr.addRow(new Object[]{true,"Obat & BHP",":","","","","","","Obat"});
             prosesCariObat(); 
             tabModeRwJlDr.addRow(new Object[]{false,"Tambahan Biaya",":","","","","","0","Tambahan"});           
             try {
                pstambahan.setString(1,TNoRw.getText());
                rstambahan=pstambahan.executeQuery();
                while(rstambahan.next()){                    
                    tabModeRwJlDr.addRow(new Object[]{false,"",rstambahan.getString("nama_biaya"),":",
                               rstambahan.getString("besar_biaya"),"1","",rstambahan.getString("besar_biaya"),"Tambahan"});
                }
                //ps.close();
             } catch (SQLException ex) {
                System.out.println("Error : "+ex);
             }
             tabModeRwJlDr.addRow(new Object[]{false,"Potongan Biaya",":","","","","","0","Potongan"});           
             try {
                pspotongan.setString(1,TNoRw.getText());
                rspotongan=pspotongan.executeQuery();
                while(rspotongan.next()){                    
                    tabModeRwJlDr.addRow(new Object[]{false,"",rspotongan.getString("nama_pengurangan"),":",
                               rspotongan.getString("besar_pengurangan"),"1","","-"+rspotongan.getString("besar_pengurangan"),"Potongan"});
                }
                //ps.close();
             } catch (SQLException ex) {
                System.out.println("Error : "+ex);
             }
             tabModeRwJlDr.addRow(new Object[] {false,"","","","","","","","-"});
             isHitung();
         }else if(i>0){
             DTPTgl.setDate(Sequel.cariIsi2("select billing.tgl_byr from billing where billing.no_rawat='"+TNoRw.getText()+"'"));             
             Valid.tabelKosong(tabModeRwJlDr);
             try{                
                psbilling.setString(1,TNoRw.getText());
                rsbilling=psbilling.executeQuery();
                while(rsbilling.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,rsbilling.getString("no"),
                                    rsbilling.getString("nm_perawatan"),
                                    rsbilling.getString("pemisah"),
                                    rsbilling.getString("satu"),
                                    rsbilling.getString("dua"),
                                    rsbilling.getString("tiga"),
                                    rsbilling.getString("empat"),
                                    rsbilling.getString("status")});                      
                }
                //rs.close();
               isHitung();
            }catch(SQLException e){
                System.out.println("Error : "+e);
            }
         }
         
    }

    private void prosesCariReg() {        
        Valid.tabelKosong(tabModeRwJlDr);
        try{         
            psreg.setString(1,TNoRw.getText());
            rsreg=psreg.executeQuery();            
            if(rsreg.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"No.Nota",": "+TNoRw.getText(),"","","","","","-"});                
                pscaripoli.setString(1,rsreg.getString("kd_poli"));
                rscaripoli=pscaripoli.executeQuery();
                if(rscaripoli.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Unit/Instansi",": "+rscaripoli.getString(1),"","","","","","-"});
                }                
                tabModeRwJlDr.addRow(new Object[]{true,"Tgl.Registrasi",": "+rsreg.getString("tgl_registrasi"),"","","","","","-"});
                tabModeRwJlDr.addRow(new Object[]{true,"No.RM",": "+TNoRM.getText(),"","","","","","-"});
                tabModeRwJlDr.addRow(new Object[]{true,"Nama Pasien",": "+TPasien.getText(),"","","","","","-"});
                pscarialamat.setString(1,TNoRM.getText());
                rscarialamat=pscarialamat.executeQuery();
                if(rscarialamat.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Alamat Pasien",": "+rscarialamat.getString(1),"","","","","","-"});
                }
                
                //cari dokter yang menangandi                
                psdokterralan.setString(1,TNoRw.getText());
                rsdokterralan=psdokterralan.executeQuery();
                if(rsdokterralan.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Dokter ",":","","","","","","-"});  
                }
                rsdokterralan.beforeFirst();
                while(rsdokterralan.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"",rsdokterralan.getString("nm_dokter"),"","","","","","Dokter"});   
                }
                
                //rs3.close();
                //cari perawat yang menangandi
               /* PreparedStatement ps4=koneksi.prepareStatement("select petugas.nama from rawat_inap_pr "+
                            "inner join petugas on rawat_inap_pr.nip=petugas.nip "+
                            "where no_rawat=? group by rawat_inap_pr.nip");
                ps4.setString(1,TNoRw.getText());
                ResultSet rs4=ps4.executeQuery();
                PreparedStatement ps5=koneksi.prepareStatement("select petugas.nama from rawat_jl_pr "+
                            "inner join petugas on rawat_jl_pr.nip=petugas.nip "+
                            "where no_rawat=? group by rawat_jl_pr.nip");
                ps5.setString(1,TNoRw.getText());
                ResultSet rs5=ps5.executeQuery();
                if(rs4.next()||rs5.next()){
                    tabModeRwJlDr.addRow(new Object[]{false,"Parawat",":","","","","","","-"});  
                }
                rs4.beforeFirst();
                while(rs4.next()){
                    tabModeRwJlDr.addRow(new Object[]{false,"",rs4.getString("nama"),"","","","","","Perawat"});   
                }
                rs4.close();
                rs5.beforeFirst();
                while(rs5.next()){
                    tabModeRwJlDr.addRow(new Object[]{false,"",rs5.getString("nama"),"","","","","","Perawat"});   
                }
                rs5.close();*/
                tabModeRwJlDr.addRow(new Object[]{true,"Administrasi Rekam Medik",":","","","","",rsreg.getString("biaya_reg"),"Registrasi"});
            }            
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    private void prosesCariRwJlDr() {
        try{            
            pscariralandokter.setString(1,TNoRw.getText());
            rscariralandokter=pscariralandokter.executeQuery();
            subttl=0;
            while(rscariralandokter.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"",rscariralandokter.getString("nm_perawatan"),":",
                               rscariralandokter.getString("total_byrdr"),rscariralandokter.getString("jml"),"",rscariralandokter.getString("biaya"),"Ralan Dokter"});
                subttl=subttl+rscariralandokter.getDouble("biaya");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

   

    private void prosesCariRwJlPr() {
        try{            
            pscariralanperawat.setString(1,TNoRw.getText());
            rscariralanperawat=pscariralanperawat.executeQuery();
            subttl=0;
            while(rscariralanperawat.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"",rscariralanperawat.getString("nm_perawatan"),":",
                               rscariralanperawat.getString("total_byrpr"),rscariralanperawat.getString("jml"),"",rscariralanperawat.getString("biaya"),"Ralan Paramedis"});
                subttl=subttl+rscariralanperawat.getDouble("biaya");
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }
    
    private void prosesCariPeriksaLab() {
        try{
            pscarilab.setString(1,TNoRw.getText());
            rscarilab=pscarilab.executeQuery();
            subttl=0;
            while(rscarilab.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"",rscarilab.getString("nm_perawatan"),":",
                               rscarilab.getString("biaya"),rscarilab.getString("jml"),"",rscarilab.getString("total"),"Ralan Paramedis"});
                subttl=subttl+rscarilab.getDouble("total");
            }
            
            psdetaillab.setString(1,TNoRw.getText());
            rsdetaillab=psdetaillab.executeQuery();
            while(rsdetaillab.next()){  
                if(rsdetaillab.getDouble("total")>0){
                    tabModeRwJlDr.addRow(new Object[]{true,"","Laboratorium",":",
                               rsdetaillab.getDouble("total"),"1","0",rsdetaillab.getDouble("total"),"Ralan Paramedis"});
                    subttl=subttl+rsdetaillab.getDouble("total");
                }                
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

    private void prosesCariObat() {
        try{            
            psobatlangsung.setString(1,TNoRw.getText());
            rsobatlangsung=psobatlangsung.executeQuery();
            if(rsobatlangsung.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"","Obat & BHP ",":","","","",rsobatlangsung.getString("besar_tagihan"),"Obat"});
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        try{            
            pscariobat.setString(1,TNoRw.getText());
            rscariobat=pscariobat.executeQuery();
            subttl=0;
            while(rscariobat.next()){
                tabModeRwJlDr.addRow(new Object[]{false,"",rscariobat.getString("nama_brng"),":",
                               rscariobat.getString("biaya_obat"),rscariobat.getString("jml"),rscariobat.getString("tambahan"),
                               rscariobat.getString("total"),"Obat"});
                subttl=subttl+rscariobat.getDouble("total");
            }
            if(subttl>0){ 
                //tabModeRwJlDr.addRow(new Object[]{true,"","     "+Valid.SetAngka(subttl),"","","","","","TtlObat"});
                tabModeRwJlDr.addRow(new Object[]{true,"",""+Valid.SetAngka(subttl),"","","","","","TtlObat"});            
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }        
        
        try{
            psreturobat.setString(1,TNoRM.getText());
            psreturobat.setString(2,DTPTgl.getSelectedItem().toString());
            
            rsreturobat=psreturobat.executeQuery();     
            if(rsreturobat.next()){                
                tabModeRwJlDr.addRow(new Object[]{true,"","Retur Obat :","","","","","","Retur Obat"});          
            }
            rsreturobat.beforeFirst();
            while(rsreturobat.next()){
                Object[] data={true,"",rsreturobat.getString("nama_brng"),":",
                               rsreturobat.getString("h_retur"),rsreturobat.getString("jml"),"",
                               rsreturobat.getString("ttl"),"Retur Obat"};
                tabModeRwJlDr.addRow(data);
                subttl=subttl+rsreturobat.getDouble("ttl");
            }
            //rs.close();
            //stat.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }


    private void isHitung() {   
        ttl=0;
        y=0;
        int a=tbAdmin.getRowCount();
        for(i=0;i<a;i++){ 
            try {                
               y=Double.parseDouble(tabModeRwJlDr.getValueAt(i,7).toString());  
            } catch (Exception e) {
                y=0; 
            }
            ttl=ttl+y;             
        }
        TtlSemua.setText(Valid.SetAngka(ttl));
    }
    
      
    public void isCek(){
        BtnSimpan.setEnabled(var.getbilling_ralan());
        BtnView.setEnabled(var.getbilling_ralan());
    }
    
    private void getData() {
        i=tbAdmin.getSelectedRow();
        if(i!= -1){
            if((!tabModeRwJlDr.getValueAt(i,4).toString().equals(""))&&(!tabModeRwJlDr.getValueAt(i,5).toString().equals(""))){
               tabModeRwJlDr.setValueAt(Valid.SetAngka2(Valid.SetAngka(tabModeRwJlDr.getValueAt(i,4).toString())*Valid.SetAngka(tabModeRwJlDr.getValueAt(i,5).toString())+Valid.SetAngka(tabModeRwJlDr.getValueAt(i,6).toString())), i,7); 
            }
            
            ttl=0;
            y=0;

            obat=0;
            ralandokter=0;
            ranapdokter=0;
            ralanparamedis=0;
            ranapparamedis=0;
            kamar=0;
            for(r=0;r<tabModeRwJlDr.getRowCount();r++){ 
                if(!tabModeRwJlDr.getValueAt(r,7).toString().isEmpty()){
                    y=Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());  
                    switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                        case "Obat":
                            obat=obat+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Ralan Dokter":
                            ralandokter=ralandokter+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Ranap Dokter":
                            ranapdokter=ranapdokter+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Ralan Paramedis":
                            ralanparamedis=ralanparamedis+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;      
                        case "Ranap Paramedis":
                            ranapparamedis=ranapparamedis+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Kamar":
                            kamar=kamar+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                    }
                    
                }else if(tabModeRwJlDr.getValueAt(r,7).toString().isEmpty()){
                    y=0;                
                }
                ttl=ttl+y;
                switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                    case "TtlObat":
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(obat),r,2);
                        break;
                    case "TtlRanap Dokter":
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(ranapdokter),r,2);
                        break;
                    case "TtlRanap Paramedis":
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(ranapparamedis),r,2);
                        break;
                    case "TtlRalan Dokter":
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(ralandokter),r,2);
                        break;
                    case "TtlRalan Paramedis":      
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(ralanparamedis),r,2);
                        break;
                    case "TtlKamar":
                        tabModeRwJlDr.setValueAt(Valid.SetAngka(kamar),r,2);
                        break;
                }
            }
            TtlSemua.setText(Valid.SetAngka(ttl));
        }
    } 
    
    public void isKembali(){
       bayar=0;total=0;
        if(!TBayar.getText().trim().equals("")) {
            bayar=Double.parseDouble(TBayar.getText()); 
        }
        if(ttl>0) {
            total=ttl; 
        }
        if(chkPiutang.isSelected()==false){
            TKembali.setText(Valid.SetAngka(bayar-total));            
        }else{
            TKembali.setText(Valid.SetAngka(ttl-bayar)); 
        }     
    }
    
    public void tampilTambahan(String NoRawat) {
        norawat.setText(NoRawat);
        Valid.tabelKosong(tabModeTambahan);
        try{            
            pstambahan.setString(1,norawat.getText());
            rstambahan=pstambahan.executeQuery();
            while(rstambahan.next()){
                tabModeTambahan.addRow(new Object[]{rstambahan.getString(1),rstambahan.getString(2)});
            }       
           // rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }            
    }  
    
    private void tampilPotongan(String NoRawat) {
             norawatpotongan.setText(NoRawat);
             Valid.tabelKosong(tabModePotongan);
             try{                
                pspotongan.setString(1,TNoRw.getText());
                rspotongan=pspotongan.executeQuery();
                while(rspotongan.next()){                    
                    tabModePotongan.addRow(new Object[]{rspotongan.getString(1),rspotongan.getString(2)});
                }
                //ps.close();
             }catch (SQLException ex) {
                System.out.println("Error : "+ex);
             }
    }
}
