package khanzahms;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPenjualan;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgRBObatPoli;
import keuangan.DlgRBTindakanDokter;
import keuangan.DlgRBTindakanParamedis;
import keuangan.DlgRBTindakanPoli;
import keuangan.DlgRHTindakanDokter;
import keuangan.DlgRHTindakanParamedis;

/**
 *
 * @author dosen
 */
public final class DlgKasirRalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModekasir,tabModeobat;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psotomatis,psotomatis2,pskasir;
    private ResultSet rskasir;
    private Date cal=new Date();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private String bangsal=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1");
    public  DlgBilingRalan billing=new DlgBilingRalan(null,false);
    private int i=0,pilihan=0;
    public DlgKamarInap kamarinap=new DlgKamarInap(null,false);

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgKasirRalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] rowkasir={"Kd.Dokter","Dokter Dituju","Nomer RM","Pasien","Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan dg P.J.",
                           "Biaya Regristrasi","Status Periksa","No.Rawat","Tanggal","Jam","No.Reg"};
        tabModekasir=new DefaultTableModel(null,rowkasir){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKasirRalan.setModel(tabModekasir);

        tbKasirRalan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbKasirRalan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbKasirRalan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(10);
            }else if(i==11){
                column.setPreferredWidth(10);
            }else if(i==12){
                column.setPreferredWidth(10);
            }else if(i==13){
                column.setPreferredWidth(10);
            }
        }
        tbKasirRalan.setDefaultRenderer(Object.class, new WarnaTable());
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte)100).getKata(CrPoli));
        TotalObat.setDocument(new batasInput((byte)20).getOnlyAngka(TotalObat));
        CrPtg.setDocument(new batasInput((byte)100).getKata(CrPtg)); 
               
         billing.dokter.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                if(pilihan==1){
                    kddokter.setText(billing.dokter.getTextField().getText());
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
                }else if(pilihan==2){
                    Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",CrPtg,billing.dokter.getTextField().getText());
                    tampilkasir();
                } 
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
        
        billing.poli.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?",CrPoli,billing.poli.getTextField().getText());
                tampilkasir();
                //tampilkasir();
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
        TCari.requestFocus();  
        
        //obat
        Object[] rowobat={"Jumlah","Kode Barang","Nama Barang","Satuan","Letak Barang","Harga(Rp)","Stok","Jenis Obat"};
        tabModeobat=new DefaultTableModel(null,rowobat){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
        };
        
        
        try{
            psotomatis=koneksi.prepareStatement("select jns_perawatan.kd_jenis_prw,jns_perawatan.total_byrdr from set_otomatis_tindakan_ralan "+
                                "inner join jns_perawatan on set_otomatis_tindakan_ralan.kd_jenis_prw=jns_perawatan.kd_jenis_prw where kd_dokter=?");
            psotomatis2=koneksi.prepareStatement("insert into rawat_jl_dr values (?,?,?,?,?,?,?,?,?)");
            pskasir=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_reg like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and reg_periksa.stts like ? and reg_periksa.tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? order by reg_periksa.no_rawat desc");
        }catch(SQLException ex){
            System.out.println(ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnKamarInap = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        ppObat = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBayar = new javax.swing.JMenuItem();
        ppRalan = new javax.swing.JMenuItem();
        MnPemberianObat1 = new javax.swing.JMenuItem();
        ppDokter = new javax.swing.JMenuItem();
        MnPenjualan = new javax.swing.JMenuItem();
        MnRekapDokter = new javax.swing.JMenuItem();
        MnRekapParamedis = new javax.swing.JMenuItem();
        MnRekapDokter1 = new javax.swing.JMenuItem();
        MnRekapParamedis1 = new javax.swing.JMenuItem();
        MnRekapHarianPoli = new javax.swing.JMenuItem();
        MnRekapHarianObat = new javax.swing.JMenuItem();
        TNoRw = new widget.TextBox();
        WindowInput = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        TotalObat = new widget.TextBox();
        jLabel3 = new widget.Label();
        BtnCloseIn = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        WindowInput2 = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        BtnCloseIn1 = new widget.Button();
        BtnSimpan1 = new widget.Button();
        jLabel13 = new widget.Label();
        kddokter = new widget.TextBox();
        TDokter = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        Kd2 = new widget.TextBox();
        TKdPny = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKasirRalan = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrPtg = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(java.awt.Color.darkGray);
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setIconTextGap(5);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
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
        MnPemberianObat.setIconTextGap(5);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(java.awt.Color.darkGray);
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setIconTextGap(5);
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(java.awt.Color.darkGray);
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setIconTextGap(5);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        ppObat.setBackground(new java.awt.Color(255, 255, 255));
        ppObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppObat.setForeground(java.awt.Color.darkGray);
        ppObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppObat.setText("Total Tagihan Obat");
        ppObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppObat.setIconTextGap(5);
        ppObat.setName("ppObat"); // NOI18N
        ppObat.setPreferredSize(new java.awt.Dimension(200, 25));
        ppObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppObat);

        MnBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(java.awt.Color.darkGray);
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setIconTextGap(5);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        MnSudah.setBackground(new java.awt.Color(255, 255, 255));
        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(java.awt.Color.darkGray);
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSudah.setText("Set Sudah Periksa");
        MnSudah.setIconTextGap(5);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSudah);

        MnBelum.setBackground(new java.awt.Color(255, 255, 255));
        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(java.awt.Color.darkGray);
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBelum.setText("Set Belum Periksa");
        MnBelum.setIconTextGap(5);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBelum);

        MnBayar.setBackground(new java.awt.Color(255, 255, 255));
        MnBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBayar.setForeground(java.awt.Color.darkGray);
        MnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBayar.setText("Set Sudah Bayar");
        MnBayar.setIconTextGap(5);
        MnBayar.setName("MnBayar"); // NOI18N
        MnBayar.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBayarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBayar);

        ppRalan.setBackground(new java.awt.Color(255, 255, 255));
        ppRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRalan.setForeground(java.awt.Color.darkGray);
        ppRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRalan.setText("Buka Tindakan Ralan Masuk");
        ppRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRalan.setIconTextGap(5);
        ppRalan.setName("ppRalan"); // NOI18N
        ppRalan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRalan);

        MnPemberianObat1.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat1.setForeground(java.awt.Color.darkGray);
        MnPemberianObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat1.setText("Rekap Pemberian Obat");
        MnPemberianObat1.setIconTextGap(5);
        MnPemberianObat1.setName("MnPemberianObat1"); // NOI18N
        MnPemberianObat1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObat1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat1);

        ppDokter.setBackground(new java.awt.Color(255, 255, 255));
        ppDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDokter.setForeground(java.awt.Color.darkGray);
        ppDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDokter.setText("Ganti Dokter Poli");
        ppDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDokter.setIconTextGap(5);
        ppDokter.setName("ppDokter"); // NOI18N
        ppDokter.setPreferredSize(new java.awt.Dimension(200, 25));
        ppDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDokterActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDokter);

        MnPenjualan.setBackground(new java.awt.Color(255, 255, 255));
        MnPenjualan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenjualan.setForeground(java.awt.Color.darkGray);
        MnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenjualan.setText("Penjualan Obat/Alkes/Barang");
        MnPenjualan.setIconTextGap(5);
        MnPenjualan.setName("MnPenjualan"); // NOI18N
        MnPenjualan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenjualanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenjualan);

        MnRekapDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapDokter.setForeground(java.awt.Color.darkGray);
        MnRekapDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapDokter.setText("Rekap Harian Dokter ");
        MnRekapDokter.setIconTextGap(5);
        MnRekapDokter.setName("MnRekapDokter"); // NOI18N
        MnRekapDokter.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapDokterActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapDokter);

        MnRekapParamedis.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapParamedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapParamedis.setForeground(java.awt.Color.darkGray);
        MnRekapParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapParamedis.setText("Rekap Harian Paramedis");
        MnRekapParamedis.setIconTextGap(5);
        MnRekapParamedis.setName("MnRekapParamedis"); // NOI18N
        MnRekapParamedis.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapParamedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapParamedis);

        MnRekapDokter1.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapDokter1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapDokter1.setForeground(java.awt.Color.darkGray);
        MnRekapDokter1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapDokter1.setIconTextGap(5);
        MnRekapDokter1.setLabel("Rekap Bulanan Dokter ");
        MnRekapDokter1.setName("MnRekapDokter1"); // NOI18N
        MnRekapDokter1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapDokter1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapDokter1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapDokter1);

        MnRekapParamedis1.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapParamedis1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapParamedis1.setForeground(java.awt.Color.darkGray);
        MnRekapParamedis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapParamedis1.setText("Rekap Bulanan Paramedis");
        MnRekapParamedis1.setIconTextGap(5);
        MnRekapParamedis1.setName("MnRekapParamedis1"); // NOI18N
        MnRekapParamedis1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapParamedis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapParamedis1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapParamedis1);

        MnRekapHarianPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianPoli.setForeground(java.awt.Color.darkGray);
        MnRekapHarianPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianPoli.setText("Rekap Harian Poli");
        MnRekapHarianPoli.setIconTextGap(5);
        MnRekapHarianPoli.setName("MnRekapHarianPoli"); // NOI18N
        MnRekapHarianPoli.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapHarianPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianPoliActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapHarianPoli);

        MnRekapHarianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnRekapHarianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRekapHarianObat.setForeground(java.awt.Color.darkGray);
        MnRekapHarianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRekapHarianObat.setText("Rekap Harian Obat");
        MnRekapHarianObat.setIconTextGap(5);
        MnRekapHarianObat.setName("MnRekapHarianObat"); // NOI18N
        MnRekapHarianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRekapHarianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRekapHarianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRekapHarianObat);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setSelectionColor(new java.awt.Color(255, 255, 255));
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total Tagihan Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
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

        jLabel3.setText("Total :");
        jLabel3.setName("jLabel3"); // NOI18N
        internalFrame2.add(jLabel3);
        jLabel3.setBounds(0, 30, 57, 23);

        BtnCloseIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn.setMnemonic('3');
        BtnCloseIn.setText("Tutup");
        BtnCloseIn.setToolTipText("Alt+3");
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

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('1');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+1");
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
        internalFrame2.add(BtnSimpan);
        BtnSimpan.setBounds(255, 30, 100, 30);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal.setMnemonic('2');
        BtnBatal.setText("Hapus");
        BtnBatal.setToolTipText("Alt+2");
        BtnBatal.setName("BtnBatal"); // NOI18N
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
        internalFrame2.add(BtnBatal);
        BtnBatal.setBounds(360, 30, 100, 30);

        WindowInput.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowInput2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput2.setName("WindowInput2"); // NOI18N
        WindowInput2.setUndecorated(true);
        WindowInput2.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Dokter Poli ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame3.setLayout(null);

        BtnCloseIn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn1.setMnemonic('9');
        BtnCloseIn1.setText("Tutup");
        BtnCloseIn1.setToolTipText("Alt+9");
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
        BtnSimpan1.setMnemonic('8');
        BtnSimpan1.setText("Simpan");
        BtnSimpan1.setToolTipText("Alt+8");
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

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setSelectionColor(new java.awt.Color(255, 255, 255));
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.setSelectionColor(new java.awt.Color(255, 255, 255));

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setSelectionColor(new java.awt.Color(255, 255, 255));

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N
        Jam.setSelectionColor(new java.awt.Color(255, 255, 255));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Kasir Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKasirRalan.setAutoCreateRowSorter(true);
        tbKasirRalan.setToolTipText("Klik 2X Kd.Dokter= Jendela Tindakan, Dokter Dituju=Jendela Obat, Nomer RM=Jendela Billing, Pasien=Jendela Total Obat, Poliklinik=Set Sudah Periksa, Penanggung Jawab=Masukan tindakan otomatis");
        tbKasirRalan.setComponentPopupMenu(jPopupMenu1);
        tbKasirRalan.setName("tbKasirRalan"); // NOI18N
        tbKasirRalan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKasirRalanMouseClicked(evt);
            }
        });
        tbKasirRalan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKasirRalanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbKasirRalan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(405, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass6.add(TCari);

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
        panelGlass6.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 30));
        panelGlass6.add(LCount);

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
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass7.add(jLabel14);

        CrPtg.setEditable(false);
        CrPtg.setName("CrPtg"); // NOI18N
        CrPtg.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPtg);

        BtnSeek3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek3.setMnemonic('4');
        BtnSeek3.setToolTipText("ALt+4");
        BtnSeek3.setName("BtnSeek3"); // NOI18N
        BtnSeek3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek3ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek3);

        jLabel16.setText("Poliklinik :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass7.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(280, 23));
        panelGlass7.add(CrPoli);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('5');
        BtnSeek4.setToolTipText("ALt+5");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeek4);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass8.add(jLabel15);

        DTPCari1.setEditable(false);
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass8.add(jLabel17);

        DTPCari2.setEditable(false);
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(140, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari2);

        jLabel12.setText("Status Periksa :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(140, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah", "Belum", "Bayar" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setOpaque(false);
        cmbStatus.setPreferredSize(new java.awt.Dimension(308, 23));
        cmbStatus.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbStatusItemStateChanged(evt);
            }
        });
        panelGlass8.add(cmbStatus);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));
        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,cmbStatus,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CrPoli.setText("");
        CrPtg.setText("");
        TCari.setText("");
        tampilkasir();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampilkasir();
            TCari.setText("");
        }else{
            Valid.pindah(evt, TCari, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
        tampilkasir();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_DTPCari2KeyPressed

    private void tbKasirRalanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKasirRalanMouseClicked
        if(tabModekasir.getRowCount()!=0){
            try {
                getDatakasir();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    MnRawatJalanActionPerformed(null);
                }else if(i==1){
                    MnPemberianObatActionPerformed(null);
                }else if(i==2){
                    MnBillingActionPerformed(null);
                }else if(i==3){
                    ppObatActionPerformed(null);
                }else if(i==4){
                    MnSudahActionPerformed(null);
                    tampilkasir();
                }else if(i==5){
                    try {                        
                        psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                        rskasir=psotomatis.executeQuery();
                        if(rskasir.next()){                            
                            psotomatis2.setString(1,TNoRw.getText()); 
                            psotomatis2.setString(2,"-");
                            psotomatis2.setString(3,rskasir.getString(1));
                            psotomatis2.setString(4,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                            psotomatis2.setString(5,"-");
                            psotomatis2.setString(6,"-");
                            psotomatis2.setString(7,"-");
                            psotomatis2.setString(8,"-");
                            psotomatis2.setDouble(9,rskasir.getDouble("total_byrdr"));
                            psotomatis2.executeUpdate();
                        }
                        MnSudahActionPerformed(null);
                        tampilkasir();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanMouseClicked

    private void tbKasirRalanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKasirRalanKeyPressed
        if(tabModekasir.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDatakasir();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbKasirRalan.getSelectedColumn();
                if(i==0){
                    MnRawatJalanActionPerformed(null);
                }else if(i==1){
                    MnPemberianObatActionPerformed(null);
                }else if(i==2){
                    MnBillingActionPerformed(null);
                }else if(i==3){
                    ppObatActionPerformed(null);
                }else if(i==4){
                    MnSudahActionPerformed(null);
                    tampilkasir();
                }else if(i==5){
                    try {                        
                        psotomatis.setString(1,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                        rskasir=psotomatis.executeQuery();
                        if(rskasir.next()){
                            psotomatis2.setString(1,TNoRw.getText()); 
                            psotomatis2.setString(2,"-");
                            psotomatis2.setString(3,rskasir.getString(1));
                            psotomatis2.setString(4,tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString());
                            psotomatis2.setString(5,"-");
                            psotomatis2.setString(6,"-");
                            psotomatis2.setString(7,"-");
                            psotomatis2.setString(8,"-");
                            psotomatis2.setDouble(9,rskasir.getDouble("total_byrdr"));
                            psotomatis2.executeUpdate();
                            psotomatis2.close();
                        }
                        MnSudahActionPerformed(null);
                        tampilkasir();
                    } catch (SQLException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
}//GEN-LAST:event_tbKasirRalanKeyPressed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
       pilihan=2;
       billing.dokter.emptTeks();
        billing.dokter.isCek();
        billing.dokter.tampil(" order by kd_dokter");
        billing.dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        billing.poli.emptTeks();
        billing.poli.isCek();
        billing.poli.tampil();
        billing.poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.poli.setLocationRelativeTo(internalFrame1);
        billing.poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{        
            billing.dlgrwjl.perawatan.setNoRm(TNoRw.getText(),"-","-",tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),0).toString(),
                    tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),1).toString(),"rawat_jl_dr","-","-","-","-","","");   
            billing.dlgrwjl.perawatan.emptTeks();
            billing.dlgrwjl.perawatan.isCek();
            billing.dlgrwjl.perawatan.tampil();
            billing.dlgrwjl.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            billing.dlgrwjl.perawatan.setLocationRelativeTo(internalFrame1);
            billing.dlgrwjl.perawatan.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{   
            TKdPny.setText("-");
            billing.dlgobt.emptTeksobat();
            billing.dlgobt.isCek();
            billing.dlgobt.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'"),
                                Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat='"+TNoRw.getText()+"'"));
            billing.dlgobt.tampilobat();            
            billing.dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            billing.dlgobt.setLocationRelativeTo(internalFrame1);
            billing.dlgobt.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{            
            billing.TNoRw.setText(TNoRw.getText());  
            billing.TBayar.setText("0");
            billing.TKembali.setText("0");    
            billing.isRawat(); 
            billing.isKembali();
            billing.isCek();
            billing.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            billing.setLocationRelativeTo(internalFrame1);
            billing.setVisible(true);
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        //Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Sudah'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
}//GEN-LAST:event_MnSudahActionPerformed

private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
       if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
}//GEN-LAST:event_MnBelumActionPerformed

private void MnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBayarActionPerformed
      if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw,"stts='Bayar'");
            if(tabModekasir.getRowCount()!=0){tampilkasir();}
        }
}//GEN-LAST:event_MnBayarActionPerformed

private void ppRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRalanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
                DlgRawatJalan dlgrwjl2=new DlgRawatJalan(null,false);
                dlgrwjl2.isCek();
                dlgrwjl2.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                dlgrwjl2.setLocationRelativeTo(internalFrame1);
                dlgrwjl2.emptTeks();
            
                dlgrwjl2.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
                dlgrwjl2.tampilDr();
                dlgrwjl2.setVisible(true);
        }
}//GEN-LAST:event_ppRalanActionPerformed

private void TotalObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TotalObatKeyPressed
        Valid.pindah(evt,BtnCloseIn,BtnSimpan);

}//GEN-LAST:event_TotalObatKeyPressed

private void BtnCloseInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseInActionPerformed
        WindowInput.dispose();
}//GEN-LAST:event_BtnCloseInActionPerformed

private void BtnCloseInKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCloseInKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal, TotalObat);}
}//GEN-LAST:event_BtnCloseInKeyPressed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.menyimpan("tagihan_obat_langsung","'"+TNoRw.getText()+"','"+TotalObat.getText()+"'","No.Rawat");
            WindowInput.setVisible(false);
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        Valid.pindah(evt,TotalObat,BtnBatal);
}//GEN-LAST:event_BtnSimpanKeyPressed

private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Sequel.queryu("delete from tagihan_obat_langsung where no_rawat=? ",TNoRw.getText());
            WindowInput.setVisible(false);
        }
}//GEN-LAST:event_BtnBatalActionPerformed

private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatalActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatalKeyPressed

private void ppObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppObatActionPerformed
    TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));  
    WindowInput.setSize(590,80);
    WindowInput.setLocationRelativeTo(internalFrame1);
    WindowInput.setVisible(true);
}//GEN-LAST:event_ppObatActionPerformed

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
            Valid.editTable(tabModekasir,"reg_periksa","no_rawat",TNoRw," kd_dokter='"+kddokter.getText()+"'");
            tampilkasir();
            WindowInput2.dispose();
        }
}//GEN-LAST:event_BtnSimpan1ActionPerformed

private void BtnSimpan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpan1KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSimpan1KeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            if(pilihan==1){
                kddokter.setText(billing.dokter.getTextField().getText());
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
            }else if(pilihan==2){
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",CrPtg,billing.dokter.getTextField().getText());
                tampilkasir();
            } 
        }else{
            if(pilihan==1){
                kddokter.setText(billing.dokter.getTextField().getText());
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
            }else if(pilihan==2){
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",CrPtg,billing.dokter.getTextField().getText());
                tampilkasir();
            } 
            Valid.pindah(evt,BtnCloseIn1,BtnSimpan1);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void TDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDokterKeyPressed
        
}//GEN-LAST:event_TDokterKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
       pilihan=1;
       billing.dokter.emptTeks();
        billing.dokter.isCek();
        billing.dokter.tampil(" order by kd_dokter");
        billing.dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        billing.dokter.setLocationRelativeTo(internalFrame1);
        billing.dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void ppDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDokterActionPerformed
    WindowInput2.setSize(630,80);
    WindowInput2.setLocationRelativeTo(internalFrame1);
    WindowInput2.setVisible(true);
}//GEN-LAST:event_ppDokterActionPerformed

private void MnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenjualanActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{            
            DlgPenjualan penjualan=new DlgPenjualan(null,false);
            penjualan.isCek();
            penjualan.setPasien(Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"'"));
            penjualan.tampil();    
            penjualan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            penjualan.setLocationRelativeTo(internalFrame1);
            penjualan.setVisible(true);
        }
}//GEN-LAST:event_MnPenjualanActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            kamarinap.billing.periksalab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            kamarinap.billing.periksalab.setLocationRelativeTo(internalFrame1);
            kamarinap.billing.periksalab.emptTeks();
            kamarinap.billing.periksalab.isCek();
            kamarinap.billing.periksalab.setNoRm(TNoRw.getText());  
            kamarinap.billing.periksalab.tampiltarif();
            kamarinap.billing.periksalab.tampil();
            kamarinap.billing.periksalab.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabModekasir.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbKasirRalan.requestFocus();
        }else{
            var.setStatus(true);
            kamarinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            kamarinap.setLocationRelativeTo(internalFrame1);
            kamarinap.emptTeks();
            kamarinap.isCek();
            kamarinap.setNoRm(TNoRw.getText());   
            kamarinap.tampil();
            kamarinap.setVisible(true);
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void cmbStatusItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbStatusItemStateChanged
    tampilkasir();
}//GEN-LAST:event_cmbStatusItemStateChanged

private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
}//GEN-LAST:event_Kd2KeyPressed

private void MnRekapDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapDokterActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHTindakanDokter rhtindakandokter=new DlgRHTindakanDokter(null,false);
        rhtindakandokter.prosesCari();
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapDokterActionPerformed

private void MnRekapParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapParamedisActionPerformed
      this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHTindakanParamedis rhtindakanparamedis=new DlgRHTindakanParamedis(null,false);
        rhtindakanparamedis.prosesCari();
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapParamedisActionPerformed

private void MnRekapDokter1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapDokter1ActionPerformed
   this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanDokter rhtindakandokter=new DlgRBTindakanDokter(null,false);
        rhtindakandokter.prosesCari();
        rhtindakandokter.isCek();    
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapDokter1ActionPerformed

private void MnRekapParamedis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapParamedis1ActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanParamedis rhtindakanparamedis=new DlgRBTindakanParamedis(null,false);
        rhtindakanparamedis.prosesCari();
        rhtindakanparamedis.isCek();    
        rhtindakanparamedis.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakanparamedis.setLocationRelativeTo(internalFrame1);
        rhtindakanparamedis.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapParamedis1ActionPerformed

private void MnRekapHarianPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianPoliActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanPoli rhtindakandokter=new DlgRBTindakanPoli(null,false);
        rhtindakandokter.prosesCari();
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnRekapHarianPoliActionPerformed

private void MnPemberianObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObat1ActionPerformed
     if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.emptTeks(); 
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObat1ActionPerformed

    private void MnRekapHarianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRekapHarianObatActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBObatPoli rhtindakandokter=new DlgRBObatPoli(null,false);
        rhtindakandokter.prosesCari(); 
        rhtindakandokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        rhtindakandokter.setLocationRelativeTo(internalFrame1);
        rhtindakandokter.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnRekapHarianObatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgKasirRalan dialog = new DlgKasirRalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn;
    private widget.Button BtnCloseIn1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan1;
    private widget.TextBox CrPoli;
    private widget.TextBox CrPtg;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Jam;
    private widget.TextBox Kd2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnBayar;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPemberianObat1;
    private javax.swing.JMenuItem MnPenjualan;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRekapDokter;
    private javax.swing.JMenuItem MnRekapDokter1;
    private javax.swing.JMenuItem MnRekapHarianObat;
    private javax.swing.JMenuItem MnRekapHarianPoli;
    private javax.swing.JMenuItem MnRekapParamedis;
    private javax.swing.JMenuItem MnRekapParamedis1;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox TKdPny;
    private widget.TextBox TNoRw;
    private widget.TextBox Tanggal;
    private widget.TextBox TotalObat;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput2;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppDokter;
    private javax.swing.JMenuItem ppObat;
    private javax.swing.JMenuItem ppRalan;
    private widget.Table tbKasirRalan;
    // End of variables declaration//GEN-END:variables

    public void tampilkasir() {                   
        Valid.tabelKosong(tabModekasir);
        try{   
            pskasir.setString(1,"%"+CrPoli.getText()+"%");
            pskasir.setString(2,"%"+CrPtg.getText()+"%");
            pskasir.setString(3,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(4,DTPCari1.getSelectedItem().toString());
            pskasir.setString(5,DTPCari2.getSelectedItem().toString());
            pskasir.setString(6,"%"+TCari.getText().trim()+"%");
            pskasir.setString(7,"%"+CrPoli.getText()+"%");
            pskasir.setString(8,"%"+CrPtg.getText()+"%");
            pskasir.setString(9,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(10,DTPCari1.getSelectedItem().toString());
            pskasir.setString(11,DTPCari2.getSelectedItem().toString());
            pskasir.setString(12,"%"+TCari.getText().trim()+"%");
            pskasir.setString(13,"%"+CrPoli.getText()+"%");
            pskasir.setString(14,"%"+CrPtg.getText()+"%");
            pskasir.setString(15,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(16,DTPCari1.getSelectedItem().toString());
            pskasir.setString(17,DTPCari2.getSelectedItem().toString());
            pskasir.setString(18,"%"+TCari.getText().trim()+"%");
            pskasir.setString(19,"%"+CrPoli.getText()+"%");
            pskasir.setString(20,"%"+CrPtg.getText()+"%");
            pskasir.setString(21,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(22,DTPCari1.getSelectedItem().toString());
            pskasir.setString(23,DTPCari2.getSelectedItem().toString());
            pskasir.setString(24,"%"+TCari.getText().trim()+"%");
            pskasir.setString(25,"%"+CrPoli.getText()+"%");
            pskasir.setString(26,"%"+CrPtg.getText()+"%");
            pskasir.setString(27,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(28,DTPCari1.getSelectedItem().toString());
            pskasir.setString(29,DTPCari2.getSelectedItem().toString());
            pskasir.setString(30,"%"+TCari.getText().trim()+"%");
            pskasir.setString(31,"%"+CrPoli.getText()+"%");
            pskasir.setString(32,"%"+CrPtg.getText()+"%");
            pskasir.setString(33,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(34,DTPCari1.getSelectedItem().toString());
            pskasir.setString(35,DTPCari2.getSelectedItem().toString());
            pskasir.setString(36,"%"+TCari.getText().trim()+"%");
            pskasir.setString(37,"%"+CrPoli.getText()+"%");
            pskasir.setString(38,"%"+CrPtg.getText()+"%");
            pskasir.setString(39,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(40,DTPCari1.getSelectedItem().toString());
            pskasir.setString(41,DTPCari2.getSelectedItem().toString());
            pskasir.setString(42,"%"+TCari.getText().trim()+"%");
            pskasir.setString(43,"%"+CrPoli.getText()+"%");
            pskasir.setString(44,"%"+CrPtg.getText()+"%");
            pskasir.setString(45,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(46,DTPCari1.getSelectedItem().toString());
            pskasir.setString(47,DTPCari2.getSelectedItem().toString());
            pskasir.setString(48,"%"+TCari.getText().trim()+"%");
            pskasir.setString(49,"%"+CrPoli.getText()+"%");
            pskasir.setString(50,"%"+CrPtg.getText()+"%");
            pskasir.setString(51,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(52,DTPCari1.getSelectedItem().toString());
            pskasir.setString(53,DTPCari2.getSelectedItem().toString());
            pskasir.setString(54,"%"+TCari.getText().trim()+"%");
            pskasir.setString(55,"%"+CrPoli.getText()+"%");
            pskasir.setString(56,"%"+CrPtg.getText()+"%");
            pskasir.setString(57,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(58,DTPCari1.getSelectedItem().toString());
            pskasir.setString(59,DTPCari2.getSelectedItem().toString());
            pskasir.setString(60,"%"+TCari.getText().trim()+"%");
            pskasir.setString(61,"%"+CrPoli.getText()+"%");
            pskasir.setString(62,"%"+CrPtg.getText()+"%");
            pskasir.setString(63,"%"+cmbStatus.getSelectedItem().toString().replaceAll("Semua","")+"%");
            pskasir.setString(64,DTPCari1.getSelectedItem().toString());
            pskasir.setString(65,DTPCari2.getSelectedItem().toString());
            pskasir.setString(66,"%"+TCari.getText().trim()+"%");
            rskasir=pskasir.executeQuery();
            while(rskasir.next()){
                tabModekasir.addRow(new String[] {rskasir.getString(5),
                               rskasir.getString(6),
                               rskasir.getString(7),
                               rskasir.getString(8),
                               rskasir.getString(9),
                               rskasir.getString(10),
                               rskasir.getString(11),
                               rskasir.getString(12),
                               Valid.SetAngka(rskasir.getDouble(13)),
                               rskasir.getString(14),
                               rskasir.getString("no_rawat"),
                               rskasir.getString("tgl_registrasi"),
                               rskasir.getString("jam_reg"),rskasir.getString(1)});
            }            
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModekasir.getRowCount());
    }


    private void getDatakasir() {
        if(tbKasirRalan.getSelectedRow()!= -1){
            TNoRw.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),10).toString());
            Tanggal.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),11).toString());
            Jam.setText(tbKasirRalan.getValueAt(tbKasirRalan.getSelectedRow(),12).toString());
        }
    }

    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
      public void isCek(){
        MnRawatJalan.setEnabled(var.getralan());
        ppRalan.setEnabled(var.getralan());
        MnPemberianObat.setEnabled(var.getberiobat());
        MnBilling.setEnabled(var.getbilling_ralan());    
    }
    

    


    
}
