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

import keuangan.DlgRekeningTahun;
import keuangan.DlgBubes;
import inventory.DlgKonversi;
import keuangan.DlgBayarPiutang;
import keuangan.DlgRekening;
import keuangan.DlgRHTindakanDokter;
import keuangan.DlgRBTindakanParamedis;
import keuangan.DlgRBTindakanDokter;
import keuangan.DlgJurnal;
import keuangan.DlgRHTindakanParamedis;
import keuangan.DlgLhtBiayaRalan;
import keuangan.DlgLhtBiaya;
import keuangan.DlgLabaRugi;
import keuangan.DlgLhtPiutang;
import inventory.DlgReturBeli;
import inventory.DlgReturPiutang;
import inventory.DlgReturJual;
import inventory.DlgProyeksiJual;
import inventory.DlgSirkulasiBarang;
import inventory.DlgPiutang;
import inventory.DlgPembelian;
import inventory.DlgPenjualan;
import inventory.DlgCashflow;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventaris.DlgBarangInventaris;
import inventaris.DlgInventaris;
import inventaris.DlgJenisInventaris;
import inventaris.DlgKategoriInventaris;
import inventaris.DlgMerkInventaris;
import inventaris.DlgProdusenInventaris;
import inventaris.DlgRuangInventaris;
import inventaris.DlgSirkulasi;
import inventory.DlgInputStok;
import inventory.DlgStokPasien;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import keuangan.DlgLhtBiayaRanap;
import keuangan.DlgRBTindakanPoli;
import khanzahms.DlgFrekuensiPenyakitRalan;
import khanzahms.DlgFrekuensiPenyakitRalan;
import khanzahms.DlgFrekuensiPenyakitRalan;
import khanzahms.DlgFrekuensiPenyakitRalan;
import khanzahms.DlgFrekuensiPenyakitRanap;
import khanzahms.DlgFrekuensiPenyakitRanap;
import khanzahms.DlgFrekuensiPenyakitRanap;
import khanzahms.DlgFrekuensiPenyakitRanap;
import khanzahms.DlgIKBBayi;
import khanzahms.DlgIKBBayi;
import khanzahms.DlgIKBBayi;
import khanzahms.DlgIKBBayi;
import khanzahms.DlgJadwal;
import khanzahms.DlgJadwal;
import khanzahms.DlgJadwal;
import khanzahms.DlgJadwal;
import khanzahms.DlgJnsPerawatanLab;
import khanzahms.DlgJnsPerawatanLab;
import khanzahms.DlgObatPenyakit;
import khanzahms.DlgObatPenyakit;
import khanzahms.DlgObatPenyakit;
import khanzahms.DlgObatPenyakit;
import khanzahms.DlgPaketOperasi;
import khanzahms.DlgPaketOperasi;
import khanzahms.DlgPaketOperasi;
import khanzahms.DlgPaketOperasi;
import khanzahms.DlgPasienMati;
import khanzahms.DlgPasienMati;
import khanzahms.DlgPasienMati;
import khanzahms.DlgPasienMati;
import khanzahms.DlgPemberianDiet;
import khanzahms.DlgPemberianDiet;
import khanzahms.DlgPemberianDiet;
import khanzahms.DlgPemberianDiet;
import khanzahms.DlgPenelusuranLogin;
import khanzahms.DlgPenelusuranLogin;
import khanzahms.DlgPenelusuranLogin;
import khanzahms.DlgPenelusuranLogin;
import khanzahms.DlgResepObat;
import khanzahms.DlgResepObat;
import khanzahms.DlgResepObat;
import khanzahms.DlgResepObat;
import khanzahms.DlgResumePerawatan;
import khanzahms.DlgResumePerawatan;
import khanzahms.DlgResumePerawatan;
import khanzahms.DlgResumePerawatan;
import khanzahms.DlgRujuk;
import khanzahms.DlgRujuk;
import khanzahms.DlgRujuk;
import khanzahms.DlgRujuk;
import khanzahms.DlgRunTeks;
import khanzahms.DlgRunTeks;
import khanzahms.DlgRunTeks;
import khanzahms.DlgRunTeks;
import khanzahms.DlgSetAplikasi;
import khanzahms.DlgSetAplikasi;
import khanzahms.DlgSetAplikasi;
import khanzahms.DlgSetAplikasi;
import khanzahms.DlgSetHarga;
import khanzahms.DlgSetHarga;
import khanzahms.DlgSetHarga;
import khanzahms.DlgSetHarga;
import khanzahms.DlgSetJam;
import khanzahms.DlgSetJam;
import khanzahms.DlgSetJam;
import khanzahms.DlgSetJam;
import khanzahms.DlgSetOtoLokasi;
import khanzahms.DlgSetOtoLokasi;
import khanzahms.DlgSetOtoLokasi;
import khanzahms.DlgSetOtoLokasi;
import khanzahms.DlgSetOtoRalan;
import khanzahms.DlgSetOtoRalan;
import khanzahms.DlgSetOtoRalan;
import khanzahms.DlgSetOtoRalan;
import khanzahms.DlgSetPenjabLab;
import khanzahms.DlgSetPenjabLab;
import khanzahms.DlgSuplier;
import khanzahms.DlgSuplier;
import khanzahms.DlgSuplier;
import khanzahms.DlgSuplier;
import khanzahms.DlgTagihanOperasi;
import khanzahms.DlgTagihanOperasi;
import khanzahms.DlgTagihanOperasi;
import khanzahms.DlgTagihanOperasi;
import khanzahms.DlgUser;
import khanzahms.DlgUser;
import khanzahms.DlgUser;
import khanzahms.DlgUser;
import khanzahms.DlgVakum;
import khanzahms.DlgVakum;
import khanzahms.DlgVakum;
import khanzahms.DlgVakum;


/**
 *
 * @author perpustakaan
 */
public class frmUtama extends javax.swing.JFrame {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final DlgKasirRalan kasirralan=new DlgKasirRalan(this,false);
    private final DlgAbout About=new DlgAbout(this,false);    
    private static frmUtama myInstance;
    private PreparedStatement ps;
    private ResultSet rs;
    /** Creates new form frmUtama */
    private frmUtama() {
        super();
        initComponents();
        setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        
        this.setSize(screen.width,screen.height);
        //desktop.setPreferredSize(new Dimension(800,1000));
        //desktop.setAutoscrolls(true);
        edAdmin.setDocument(new batasInput((byte)100).getKata(edAdmin));
        edPwd.setDocument(new batasInput((byte)100).getKata(edPwd));
        PassLama.setDocument(new batasInput((byte)100).getKata(PassLama));
        Passbaru1.setDocument(new batasInput((byte)100).getKata(Passbaru1));
        PassBaru2.setDocument(new batasInput((byte)100).getKata(PassBaru2));

        DlgLogin.setSize(344,201);
        DlgLogin.setVisible(false);
        DlgLogin.setLocationRelativeTo(null);
        
        WindowInput.setSize(349,180);
        //1032303068509
        WindowInput.setVisible(false);
        WindowInput.setLocationRelativeTo(null);

        Calendar cal = Calendar.getInstance();
        lblTgl.setText(cal.get(Calendar.DATE)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
        try {
            ps=koneksi.prepareStatement("select nama_instansi, alamat_instansi, kabupaten, propinsi, aktifkan, wallpaper from setting");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public static frmUtama getInstance() {
        if (myInstance == null)
            myInstance = new frmUtama();

        return myInstance;
    }
    
 
    //private DlgMenu menu=new DlgMenu(this,false); 
    private final Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgLogin = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        internalFrame3 = new widget.InternalFrame();
        panelGlass1 = new usu.widget.glass.PanelGlass();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        edAdmin = new widget.PasswordBox();
        edPwd = new widget.PasswordBox();
        jLabel6 = new javax.swing.JLabel();
        BtnLogin = new widget.Button();
        BtnCancel = new widget.Button();
        WindowInput = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        PassLama = new widget.TextBox();
        jLabel9 = new widget.Label();
        BtnClosePass = new widget.Button();
        BtnSimpanPass = new widget.Button();
        jLabel10 = new widget.Label();
        Passbaru1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        PassBaru2 = new widget.TextBox();
        DlgHome = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        panelisi2 = new widget.panelisi();
        label36 = new widget.Label();
        button1 = new widget.Button();
        label35 = new widget.Label();
        cmbMenu = new widget.ComboBox();
        Panelmenu = new widget.panelGlass();
        btnBarcode = new widget.ButtonBig();
        btnICD = new widget.ButtonBig();
        btnObat = new widget.ButtonBig();
        btnObatPenyakit = new widget.ButtonBig();
        btnKamar = new widget.ButtonBig();
        btnTindakanRalan = new widget.ButtonBig();
        btnDokter = new widget.ButtonBig();
        btnPegawai = new widget.ButtonBig();
        btnPasien = new widget.ButtonBig();
        btnRegistrasi = new widget.ButtonBig();
        btnRalan = new widget.ButtonBig();
        btnKamarInap = new widget.ButtonBig();
        btnRanap = new widget.ButtonBig();
        btnResepObat = new widget.ButtonBig();
        btnRujukPasien = new widget.ButtonBig();
        btnBeriObat = new widget.ButtonBig();
        btnPasienMati = new widget.ButtonBig();
        btnAdmin = new widget.ButtonBig();
        btnUser = new widget.ButtonBig();
        btnVakum = new widget.ButtonBig();
        btnDisplay = new widget.ButtonBig();
        btnSetupHarga = new widget.ButtonBig();
        btnSuplier = new widget.ButtonBig();
        btnJnsBarang = new widget.ButtonBig();
        btnKonversi = new widget.ButtonBig();
        btnSatuan = new widget.ButtonBig();
        btnCashFlow = new widget.ButtonBig();
        btnBubes = new widget.ButtonBig();
        btnPostingJurnal = new widget.ButtonBig();
        btnRekeningTahun = new widget.ButtonBig();
        btnRekening = new widget.ButtonBig();
        btnPembelian = new widget.ButtonBig();
        btnPenjualan = new widget.ButtonBig();
        btnPiutang = new widget.ButtonBig();
        btnBayarPiutang = new widget.ButtonBig();
        btnOpname = new widget.ButtonBig();
        btnReturBeli = new widget.ButtonBig();
        btnReturJual = new widget.ButtonBig();
        btnSirkulasi = new widget.ButtonBig();
        btnKeuntungan = new widget.ButtonBig();
        btnLabaRugi = new widget.ButtonBig();
        btnReturPiutang = new widget.ButtonBig();
        btnAnalisaKamar = new widget.ButtonBig();
        btnRHDOkter = new widget.ButtonBig();
        btnRBDokter = new widget.ButtonBig();
        btnTagihanMasuk = new widget.ButtonBig();
        btnResume = new widget.ButtonBig();
        btnDiet = new widget.ButtonBig();
        btnRHParamedis = new widget.ButtonBig();
        btnRBParamedis = new widget.ButtonBig();
        btnKasir = new widget.ButtonBig();
        btnLahir = new widget.ButtonBig();
        btnSetBiayaHarian = new widget.ButtonBig();
        btnJenisInventaris = new widget.ButtonBig();
        btnKategoriInventaris = new widget.ButtonBig();
        btnLihatPiutang = new widget.ButtonBig();
        btnLaboratorium = new widget.ButtonBig();
        btnRalanMasuk = new widget.ButtonBig();
        btnSetupAplikasi = new widget.ButtonBig();
        btnSetOtoRalan = new widget.ButtonBig();
        btnRanapMasuk = new widget.ButtonBig();
        btnProdusenInventaris = new widget.ButtonBig();
        btnSetBiayaMasukSekali = new widget.ButtonBig();
        btnPaketOperasi = new widget.ButtonBig();
        btnTagihanOperasi = new widget.ButtonBig();
        BtnJadwal = new widget.ButtonBig();
        btnMerkInventaris = new widget.ButtonBig();
        btnRuangInventaris = new widget.ButtonBig();
        btnBarangInventaris = new widget.ButtonBig();
        btnInventaris = new widget.ButtonBig();
        btnSirkulasiInventaris = new widget.ButtonBig();
        btnFrekuensiRalan = new widget.ButtonBig();
        btnFrekuensiRanap = new widget.ButtonBig();
        btnSetupOtoLokasi = new widget.ButtonBig();
        btnTagihanPoli = new widget.ButtonBig();
        btnRujukMasuk = new widget.ButtonBig();
        btnTracker = new widget.ButtonBig();
        btnTindakanRanap = new widget.ButtonBig();
        btnSetupJamInap = new widget.ButtonBig();
        btnStokObatPasien = new widget.ButtonBig();
        btnTarifLab = new widget.ButtonBig();
        btnSetPenjab = new widget.ButtonBig();
        internalFrame1 = new widget.InternalFrame();
        BtnMenu = new widget.ButtonBig();
        jSeparator4 = new javax.swing.JSeparator();
        BtnLog = new widget.ButtonBig();
        BtnToolPasien = new widget.ButtonBig();
        BtnToolReg = new widget.ButtonBig();
        jSeparator5 = new javax.swing.JSeparator();
        BtnToolRanap = new widget.ButtonBig();
        BtnToolRalan = new widget.ButtonBig();
        jSeparator6 = new javax.swing.JSeparator();
        BtnClose = new widget.ButtonBig();
        internalFrame4 = new widget.InternalFrame();
        lblStts = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblUser = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        lblTgl = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        PanelUtama = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        PanelWall = new usu.widget.glass.PanelGlass();
        panelGlass3 = new usu.widget.glass.PanelGlass();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        MenuBar = new widget.MenuBar();
        jMenu1 = new javax.swing.JMenu();
        MnLogin = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        MnGantiPassword = new javax.swing.JMenuItem();
        MenuKeluar = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();

        DlgLogin.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgLogin.setModal(true);
        DlgLogin.setName("DlgLogin"); // NOI18N
        DlgLogin.setUndecorated(true);
        DlgLogin.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(80, 120, 40)));
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setWarnaAtas(new java.awt.Color(100, 140, 60));
        internalFrame2.setWarnaBawah(new java.awt.Color(100, 140, 60));
        internalFrame2.setLayout(null);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(100, 125, 90), 1, true), ":: Silahkan Anda Login ::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 13), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaAtas(new java.awt.Color(195, 215, 170));
        internalFrame3.setWarnaBawah(new java.awt.Color(245, 255, 220));
        internalFrame3.setLayout(null);

        panelGlass1.setBackground(java.awt.Color.lightGray);
        panelGlass1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(145, 185, 145)));
        panelGlass1.setOpaqueImage(false);
        panelGlass1.setRound(false);
        panelGlass1.setLayout(null);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(125, 81, 81));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("ID Admin :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass1.add(jLabel4);
        jLabel4.setBounds(2, 12, 80, 23);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(125, 81, 81));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Password :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass1.add(jLabel5);
        jLabel5.setBounds(2, 40, 80, 23);

        edAdmin.setForeground(new java.awt.Color(125, 81, 81));
        edAdmin.setToolTipText("Silahkan masukkan ID Admin");
        edAdmin.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edAdmin.setName("edAdmin"); // NOI18N
        edAdmin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edAdminKeyPressed(evt);
            }
        });
        panelGlass1.add(edAdmin);
        edAdmin.setBounds(85, 12, 220, 23);

        edPwd.setForeground(new java.awt.Color(125, 81, 81));
        edPwd.setToolTipText("Silahkan masukkan password");
        edPwd.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        edPwd.setName("edPwd"); // NOI18N
        edPwd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                edPwdKeyPressed(evt);
            }
        });
        panelGlass1.add(edPwd);
        edPwd.setBounds(85, 40, 220, 23);

        internalFrame3.add(panelGlass1);
        panelGlass1.setBounds(-1, 30, 342, 76);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/LaST (Cobalt) Lock n Gear.png"))); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N
        internalFrame3.add(jLabel6);
        jLabel6.setBounds(120, 5, 135, 145);

        BtnLogin.setForeground(new java.awt.Color(185, 86, 86));
        BtnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/lock.png"))); // NOI18N
        BtnLogin.setMnemonic('Z');
        BtnLogin.setText("Log-in");
        BtnLogin.setToolTipText("Alt+Z");
        BtnLogin.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnLogin.setName("BtnLogin"); // NOI18N
        BtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLoginActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnLogin);
        BtnLogin.setBounds(12, 125, 105, 32);

        BtnCancel.setForeground(new java.awt.Color(185, 86, 86));
        BtnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnCancel.setMnemonic('Y');
        BtnCancel.setText("Batal");
        BtnCancel.setToolTipText("Alt+Y");
        BtnCancel.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnCancel.setName("BtnCancel"); // NOI18N
        BtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelActionPerformed(evt);
            }
        });
        internalFrame3.add(BtnCancel);
        BtnCancel.setBounds(222, 125, 105, 32);

        internalFrame2.add(internalFrame3);
        internalFrame3.setBounds(2, 15, 340, 170);

        DlgLogin.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setModal(true);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Password ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaBawah(new java.awt.Color(235, 245, 225));
        internalFrame6.setLayout(null);

        PassLama.setHighlighter(null);
        PassLama.setName("PassLama"); // NOI18N
        PassLama.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame6.add(PassLama);
        PassLama.setBounds(128, 30, 190, 23);

        jLabel9.setText("Password Lama :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame6.add(jLabel9);
        jLabel9.setBounds(0, 30, 125, 23);

        BtnClosePass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnClosePass.setMnemonic('2');
        BtnClosePass.setText("Tutup");
        BtnClosePass.setToolTipText("Alt+2");
        BtnClosePass.setName("BtnClosePass"); // NOI18N
        BtnClosePass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnClosePassActionPerformed(evt);
            }
        });
        BtnClosePass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnClosePassKeyPressed(evt);
            }
        });
        internalFrame6.add(BtnClosePass);
        BtnClosePass.setBounds(230, 130, 100, 30);

        BtnSimpanPass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanPass.setMnemonic('1');
        BtnSimpanPass.setText("Simpan");
        BtnSimpanPass.setToolTipText("Alt+1");
        BtnSimpanPass.setName("BtnSimpanPass"); // NOI18N
        BtnSimpanPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanPassActionPerformed(evt);
            }
        });
        BtnSimpanPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanPassKeyPressed(evt);
            }
        });
        internalFrame6.add(BtnSimpanPass);
        BtnSimpanPass.setBounds(20, 130, 100, 30);

        jLabel10.setText("Password Baru :");
        jLabel10.setName("jLabel10"); // NOI18N
        internalFrame6.add(jLabel10);
        jLabel10.setBounds(0, 60, 125, 23);

        Passbaru1.setHighlighter(null);
        Passbaru1.setName("Passbaru1"); // NOI18N
        Passbaru1.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame6.add(Passbaru1);
        Passbaru1.setBounds(128, 60, 190, 23);

        jLabel12.setText("Password Baru :");
        jLabel12.setName("jLabel12"); // NOI18N
        internalFrame6.add(jLabel12);
        jLabel12.setBounds(0, 90, 125, 23);

        PassBaru2.setHighlighter(null);
        PassBaru2.setName("PassBaru2"); // NOI18N
        PassBaru2.setSelectionColor(new java.awt.Color(255, 255, 255));
        internalFrame6.add(PassBaru2);
        PassBaru2.setBounds(128, 90, 190, 23);

        WindowInput.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgHome.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgHome.setAlwaysOnTop(true);
        DlgHome.setIconImage(null);
        DlgHome.setName("DlgHome"); // NOI18N
        DlgHome.setUndecorated(true);
        DlgHome.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Menu Utama ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(40, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setPreferredSize(new java.awt.Dimension(1412, 2653));
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 39));
        panelisi2.setWarnaBawah(new java.awt.Color(245, 250, 240));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        label36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(1, 23));
        panelisi2.add(label36);

        button1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        button1.setGlassColor(new java.awt.Color(255, 255, 255));
        button1.setMinimumSize(new java.awt.Dimension(28, 23));
        button1.setName("button1"); // NOI18N
        button1.setPreferredSize(new java.awt.Dimension(25, 23));
        button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button1ActionPerformed(evt);
            }
        });
        panelisi2.add(button1);

        label35.setForeground(new java.awt.Color(40, 70, 50));
        label35.setText("Tampilkan Menu :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi2.add(label35);

        cmbMenu.setForeground(new java.awt.Color(40, 70, 50));
        cmbMenu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Registrasi, Tagihan Ranap & Ralan, Rekam Medis, Pelayanan & Billing Pasien", "Presensi Pegawai Rumah Sakit", "Transaksi Inventory Barang Apotek/Rumah Sakit", "Manajemen & Penggajian Pegawai Rumah Sakit", "Inventaris Barang Rumah Sakit", "Parkir Rumah Sakit", "Pengolahan Data Pasien, Rekam Medik, Tindakan Dokter, Ralan & Ranap", "Keuangan Rumah Sakit", "Pengaturan Program Aplikasi HMS" }));
        cmbMenu.setName("cmbMenu"); // NOI18N
        cmbMenu.setOpaque(false);
        cmbMenu.setPreferredSize(new java.awt.Dimension(470, 23));
        cmbMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbMenuItemStateChanged(evt);
            }
        });
        panelisi2.add(cmbMenu);

        internalFrame5.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        Panelmenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Panelmenu.setName("Panelmenu"); // NOI18N
        Panelmenu.setLayout(new java.awt.GridLayout(0, 7));

        btnBarcode.setForeground(new java.awt.Color(40, 70, 50));
        btnBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484263_barcode.png"))); // NOI18N
        btnBarcode.setText("Barcode");
        btnBarcode.setIconTextGap(0);
        btnBarcode.setName("btnBarcode"); // NOI18N
        btnBarcode.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarcodeActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarcode);

        btnICD.setForeground(new java.awt.Color(40, 70, 50));
        btnICD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnICD.setText("ICD/Penyakit");
        btnICD.setIconTextGap(0);
        btnICD.setName("btnICD"); // NOI18N
        btnICD.setPreferredSize(new java.awt.Dimension(200, 90));
        btnICD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnICDActionPerformed(evt);
            }
        });
        Panelmenu.add(btnICD);

        btnObat.setForeground(new java.awt.Color(40, 70, 50));
        btnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360815855_laboratory.png"))); // NOI18N
        btnObat.setText("Obat/Alkes/BHP");
        btnObat.setIconTextGap(0);
        btnObat.setName("btnObat"); // NOI18N
        btnObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObat);

        btnObatPenyakit.setForeground(new java.awt.Color(40, 70, 50));
        btnObatPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360484848_applications-science.png"))); // NOI18N
        btnObatPenyakit.setText("Obat Penyakit");
        btnObatPenyakit.setIconTextGap(0);
        btnObatPenyakit.setName("btnObatPenyakit"); // NOI18N
        btnObatPenyakit.setPreferredSize(new java.awt.Dimension(200, 90));
        btnObatPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnObatPenyakitActionPerformed(evt);
            }
        });
        Panelmenu.add(btnObatPenyakit);

        btnKamar.setForeground(new java.awt.Color(40, 70, 50));
        btnKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnKamar.setText("Kamar");
        btnKamar.setIconTextGap(0);
        btnKamar.setName("btnKamar"); // NOI18N
        btnKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKamar);

        btnTindakanRalan.setForeground(new java.awt.Color(40, 70, 50));
        btnTindakanRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/plaster.png"))); // NOI18N
        btnTindakanRalan.setText("Tarif Ralan");
        btnTindakanRalan.setIconTextGap(0);
        btnTindakanRalan.setName("btnTindakanRalan"); // NOI18N
        btnTindakanRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTindakanRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTindakanRalan);

        btnDokter.setForeground(new java.awt.Color(40, 70, 50));
        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor2.png"))); // NOI18N
        btnDokter.setText("Dokter");
        btnDokter.setIconTextGap(0);
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDokter);

        btnPegawai.setForeground(new java.awt.Color(40, 70, 50));
        btnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/receptionist.png"))); // NOI18N
        btnPegawai.setText("Pegawai");
        btnPegawai.setIconTextGap(0);
        btnPegawai.setName("btnPegawai"); // NOI18N
        btnPegawai.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegawaiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPegawai);

        btnPasien.setForeground(new java.awt.Color(40, 70, 50));
        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/patient.png"))); // NOI18N
        btnPasien.setText("Pasien");
        btnPasien.setIconTextGap(0);
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPasien);

        btnRegistrasi.setForeground(new java.awt.Color(40, 70, 50));
        btnRegistrasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnRegistrasi.setText("Registrasi");
        btnRegistrasi.setIconTextGap(0);
        btnRegistrasi.setName("btnRegistrasi"); // NOI18N
        btnRegistrasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRegistrasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRegistrasi);

        btnRalan.setForeground(new java.awt.Color(40, 70, 50));
        btnRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/folder.png"))); // NOI18N
        btnRalan.setText("Tindakan Ralan");
        btnRalan.setIconTextGap(0);
        btnRalan.setName("btnRalan"); // NOI18N
        btnRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRalan);

        btnKamarInap.setForeground(new java.awt.Color(40, 70, 50));
        btnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Home.png"))); // NOI18N
        btnKamarInap.setText("Kamar Inap");
        btnKamarInap.setIconTextGap(0);
        btnKamarInap.setName("btnKamarInap"); // NOI18N
        btnKamarInap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKamarInapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKamarInap);

        btnRanap.setForeground(new java.awt.Color(40, 70, 50));
        btnRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnRanap.setText("Tindakan Ranap");
        btnRanap.setIconTextGap(0);
        btnRanap.setName("btnRanap"); // NOI18N
        btnRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRanap);

        btnResepObat.setForeground(new java.awt.Color(40, 70, 50));
        btnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stock_task.png"))); // NOI18N
        btnResepObat.setText("Resep Obat");
        btnResepObat.setIconTextGap(0);
        btnResepObat.setName("btnResepObat"); // NOI18N
        btnResepObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResepObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnResepObat);

        btnRujukPasien.setForeground(new java.awt.Color(40, 70, 50));
        btnRujukPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/reports.png"))); // NOI18N
        btnRujukPasien.setText("Rujukan Keluar");
        btnRujukPasien.setIconTextGap(0);
        btnRujukPasien.setName("btnRujukPasien"); // NOI18N
        btnRujukPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukPasien);

        btnBeriObat.setForeground(new java.awt.Color(40, 70, 50));
        btnBeriObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/first_aid_kit.png"))); // NOI18N
        btnBeriObat.setText("Beri Obat/BHP");
        btnBeriObat.setIconTextGap(0);
        btnBeriObat.setName("btnBeriObat"); // NOI18N
        btnBeriObat.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBeriObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBeriObatActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBeriObat);

        btnPasienMati.setForeground(new java.awt.Color(40, 70, 50));
        btnPasienMati.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Ambulance.png"))); // NOI18N
        btnPasienMati.setText("Pasien Meninggal");
        btnPasienMati.setIconTextGap(0);
        btnPasienMati.setName("btnPasienMati"); // NOI18N
        btnPasienMati.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPasienMati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienMatiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPasienMati);

        btnAdmin.setForeground(new java.awt.Color(40, 70, 50));
        btnAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/preferences-desktop-cryptography.png"))); // NOI18N
        btnAdmin.setText("Setup Admin");
        btnAdmin.setIconTextGap(0);
        btnAdmin.setName("btnAdmin"); // NOI18N
        btnAdmin.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdminActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAdmin);

        btnUser.setForeground(new java.awt.Color(40, 70, 50));
        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Login Manager.png"))); // NOI18N
        btnUser.setText("Setup User");
        btnUser.setIconTextGap(0);
        btnUser.setName("btnUser"); // NOI18N
        btnUser.setPreferredSize(new java.awt.Dimension(200, 90));
        btnUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUserActionPerformed(evt);
            }
        });
        Panelmenu.add(btnUser);

        btnVakum.setForeground(new java.awt.Color(40, 70, 50));
        btnVakum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486615_remove-from-database.png"))); // NOI18N
        btnVakum.setText("Vakum Table");
        btnVakum.setIconTextGap(0);
        btnVakum.setName("btnVakum"); // NOI18N
        btnVakum.setPreferredSize(new java.awt.Dimension(200, 90));
        btnVakum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVakumActionPerformed(evt);
            }
        });
        Panelmenu.add(btnVakum);

        btnDisplay.setForeground(new java.awt.Color(40, 70, 50));
        btnDisplay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/find.png"))); // NOI18N
        btnDisplay.setText("Display");
        btnDisplay.setIconTextGap(0);
        btnDisplay.setName("btnDisplay"); // NOI18N
        btnDisplay.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDisplay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisplayActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDisplay);

        btnSetupHarga.setForeground(new java.awt.Color(40, 70, 50));
        btnSetupHarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487093_price.png"))); // NOI18N
        btnSetupHarga.setText("Setup Harga");
        btnSetupHarga.setIconTextGap(0);
        btnSetupHarga.setName("btnSetupHarga"); // NOI18N
        btnSetupHarga.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupHargaActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupHarga);

        btnSuplier.setForeground(new java.awt.Color(40, 70, 50));
        btnSuplier.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/user3.png"))); // NOI18N
        btnSuplier.setText("Suplier");
        btnSuplier.setIconTextGap(0);
        btnSuplier.setName("btnSuplier"); // NOI18N
        btnSuplier.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSuplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuplierActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSuplier);

        btnJnsBarang.setForeground(new java.awt.Color(40, 70, 50));
        btnJnsBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Card_file.png"))); // NOI18N
        btnJnsBarang.setText("Jenis Barang");
        btnJnsBarang.setIconTextGap(0);
        btnJnsBarang.setName("btnJnsBarang"); // NOI18N
        btnJnsBarang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJnsBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJnsBarangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJnsBarang);

        btnKonversi.setForeground(new java.awt.Color(40, 70, 50));
        btnKonversi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/ark2.png"))); // NOI18N
        btnKonversi.setText("Konversi Satuan");
        btnKonversi.setIconTextGap(0);
        btnKonversi.setName("btnKonversi"); // NOI18N
        btnKonversi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKonversi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKonversiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKonversi);

        btnSatuan.setForeground(new java.awt.Color(40, 70, 50));
        btnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bag1.png"))); // NOI18N
        btnSatuan.setText("Satuan Barang");
        btnSatuan.setIconTextGap(0);
        btnSatuan.setName("btnSatuan"); // NOI18N
        btnSatuan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSatuanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSatuan);

        btnCashFlow.setForeground(new java.awt.Color(40, 70, 50));
        btnCashFlow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnCashFlow.setText("Cash Flow");
        btnCashFlow.setIconTextGap(0);
        btnCashFlow.setName("btnCashFlow"); // NOI18N
        btnCashFlow.setPreferredSize(new java.awt.Dimension(200, 90));
        btnCashFlow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCashFlowActionPerformed(evt);
            }
        });
        Panelmenu.add(btnCashFlow);

        btnBubes.setForeground(new java.awt.Color(40, 70, 50));
        btnBubes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnBubes.setText("Buku Besar");
        btnBubes.setIconTextGap(0);
        btnBubes.setName("btnBubes"); // NOI18N
        btnBubes.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBubes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBubesActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBubes);

        btnPostingJurnal.setForeground(new java.awt.Color(40, 70, 50));
        btnPostingJurnal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485642_edit-notes.png"))); // NOI18N
        btnPostingJurnal.setText("Posting Jurnal");
        btnPostingJurnal.setIconTextGap(0);
        btnPostingJurnal.setName("btnPostingJurnal"); // NOI18N
        btnPostingJurnal.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPostingJurnal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostingJurnalActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPostingJurnal);

        btnRekeningTahun.setForeground(new java.awt.Color(40, 70, 50));
        btnRekeningTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/money_bag.png"))); // NOI18N
        btnRekeningTahun.setText("Rekening Tahun");
        btnRekeningTahun.setIconTextGap(0);
        btnRekeningTahun.setName("btnRekeningTahun"); // NOI18N
        btnRekeningTahun.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekeningTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekeningTahunActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekeningTahun);

        btnRekening.setForeground(new java.awt.Color(40, 70, 50));
        btnRekening.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/kwrite.png"))); // NOI18N
        btnRekening.setText("Akun Rekening");
        btnRekening.setIconTextGap(0);
        btnRekening.setName("btnRekening"); // NOI18N
        btnRekening.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRekening.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRekeningActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRekening);

        btnPembelian.setForeground(new java.awt.Color(40, 70, 50));
        btnPembelian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487067_calculator.png"))); // NOI18N
        btnPembelian.setText("Beli/Pengadaan");
        btnPembelian.setIconTextGap(0);
        btnPembelian.setName("btnPembelian"); // NOI18N
        btnPembelian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPembelian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPembelianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPembelian);

        btnPenjualan.setForeground(new java.awt.Color(40, 70, 50));
        btnPenjualan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnPenjualan.setText("Penjualan");
        btnPenjualan.setIconTextGap(0);
        btnPenjualan.setName("btnPenjualan"); // NOI18N
        btnPenjualan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPenjualan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjualanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPenjualan);

        btnPiutang.setForeground(new java.awt.Color(40, 70, 50));
        btnPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist.png"))); // NOI18N
        btnPiutang.setText("Piutang");
        btnPiutang.setIconTextGap(0);
        btnPiutang.setName("btnPiutang"); // NOI18N
        btnPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPiutang);

        btnBayarPiutang.setForeground(new java.awt.Color(40, 70, 50));
        btnBayarPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/money_bag.png"))); // NOI18N
        btnBayarPiutang.setText("Bayar Piutang");
        btnBayarPiutang.setIconTextGap(0);
        btnBayarPiutang.setName("btnBayarPiutang"); // NOI18N
        btnBayarPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBayarPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBayarPiutang);

        btnOpname.setForeground(new java.awt.Color(40, 70, 50));
        btnOpname.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnOpname.setText("Stok Opname");
        btnOpname.setIconTextGap(0);
        btnOpname.setName("btnOpname"); // NOI18N
        btnOpname.setPreferredSize(new java.awt.Dimension(200, 90));
        btnOpname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpnameActionPerformed(evt);
            }
        });
        Panelmenu.add(btnOpname);

        btnReturBeli.setForeground(new java.awt.Color(40, 70, 50));
        btnReturBeli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816189_arrow_down.png"))); // NOI18N
        btnReturBeli.setText("Retur Ke Suplier");
        btnReturBeli.setIconTextGap(0);
        btnReturBeli.setName("btnReturBeli"); // NOI18N
        btnReturBeli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturBeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturBeliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturBeli);

        btnReturJual.setForeground(new java.awt.Color(40, 70, 50));
        btnReturJual.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486142_shopping_cart.png"))); // NOI18N
        btnReturJual.setText("Retur dr Pasien");
        btnReturJual.setIconTextGap(0);
        btnReturJual.setName("btnReturJual"); // NOI18N
        btnReturJual.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturJual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturJualActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturJual);

        btnSirkulasi.setForeground(new java.awt.Color(40, 70, 50));
        btnSirkulasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasi.setText("Sirkulasi Barang");
        btnSirkulasi.setIconTextGap(0);
        btnSirkulasi.setName("btnSirkulasi"); // NOI18N
        btnSirkulasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasi);

        btnKeuntungan.setForeground(new java.awt.Color(40, 70, 50));
        btnKeuntungan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/coins.png"))); // NOI18N
        btnKeuntungan.setText("Keuntungan Penjualan");
        btnKeuntungan.setIconTextGap(0);
        btnKeuntungan.setName("btnKeuntungan"); // NOI18N
        btnKeuntungan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKeuntungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeuntunganActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKeuntungan);

        btnLabaRugi.setForeground(new java.awt.Color(40, 70, 50));
        btnLabaRugi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486822_20.png"))); // NOI18N
        btnLabaRugi.setText("Keuangan");
        btnLabaRugi.setIconTextGap(0);
        btnLabaRugi.setName("btnLabaRugi"); // NOI18N
        btnLabaRugi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLabaRugi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLabaRugiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLabaRugi);

        btnReturPiutang.setForeground(new java.awt.Color(40, 70, 50));
        btnReturPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/custom-reports.png"))); // NOI18N
        btnReturPiutang.setText("Retur Piutang Pasien");
        btnReturPiutang.setIconTextGap(0);
        btnReturPiutang.setName("btnReturPiutang"); // NOI18N
        btnReturPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnReturPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnReturPiutang);

        btnAnalisaKamar.setForeground(new java.awt.Color(40, 70, 50));
        btnAnalisaKamar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Home.png"))); // NOI18N
        btnAnalisaKamar.setText("Informasi Kamar");
        btnAnalisaKamar.setIconTextGap(0);
        btnAnalisaKamar.setName("btnAnalisaKamar"); // NOI18N
        btnAnalisaKamar.setPreferredSize(new java.awt.Dimension(200, 90));
        btnAnalisaKamar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalisaKamarActionPerformed(evt);
            }
        });
        Panelmenu.add(btnAnalisaKamar);

        btnRHDOkter.setForeground(new java.awt.Color(40, 70, 50));
        btnRHDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRHDOkter.setText("Harian Dokter");
        btnRHDOkter.setIconTextGap(0);
        btnRHDOkter.setName("btnRHDOkter"); // NOI18N
        btnRHDOkter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHDOkterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHDOkter);

        btnRBDokter.setForeground(new java.awt.Color(40, 70, 50));
        btnRBDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnRBDokter.setText("Bulanan Dokter");
        btnRBDokter.setIconTextGap(0);
        btnRBDokter.setName("btnRBDokter"); // NOI18N
        btnRBDokter.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBDokterActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBDokter);

        btnTagihanMasuk.setForeground(new java.awt.Color(40, 70, 50));
        btnTagihanMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnTagihanMasuk.setText("Tagihan Masuk");
        btnTagihanMasuk.setIconTextGap(0);
        btnTagihanMasuk.setName("btnTagihanMasuk"); // NOI18N
        btnTagihanMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanMasuk);

        btnResume.setForeground(new java.awt.Color(40, 70, 50));
        btnResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360816018_tests.png"))); // NOI18N
        btnResume.setText("Resume Pasien");
        btnResume.setIconTextGap(0);
        btnResume.setName("btnResume"); // NOI18N
        btnResume.setPreferredSize(new java.awt.Dimension(200, 90));
        btnResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResumeActionPerformed(evt);
            }
        });
        Panelmenu.add(btnResume);

        btnDiet.setForeground(new java.awt.Color(40, 70, 50));
        btnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486936_pie-chart.png"))); // NOI18N
        btnDiet.setText("Diet Pasien");
        btnDiet.setIconTextGap(0);
        btnDiet.setName("btnDiet"); // NOI18N
        btnDiet.setPreferredSize(new java.awt.Dimension(200, 90));
        btnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDietActionPerformed(evt);
            }
        });
        Panelmenu.add(btnDiet);

        btnRHParamedis.setForeground(new java.awt.Color(40, 70, 50));
        btnRHParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnRHParamedis.setText("Harian Paramedis");
        btnRHParamedis.setIconTextGap(0);
        btnRHParamedis.setName("btnRHParamedis"); // NOI18N
        btnRHParamedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRHParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHParamedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRHParamedis);

        btnRBParamedis.setForeground(new java.awt.Color(40, 70, 50));
        btnRBParamedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360485894_add-notes.png"))); // NOI18N
        btnRBParamedis.setText("Bulanan Paramedis");
        btnRBParamedis.setIconTextGap(0);
        btnRBParamedis.setName("btnRBParamedis"); // NOI18N
        btnRBParamedis.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRBParamedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRBParamedisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRBParamedis);

        btnKasir.setForeground(new java.awt.Color(40, 70, 50));
        btnKasir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cashbox.png"))); // NOI18N
        btnKasir.setText("Kasir Ralan");
        btnKasir.setIconTextGap(0);
        btnKasir.setName("btnKasir"); // NOI18N
        btnKasir.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKasir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKasirActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKasir);

        btnLahir.setForeground(new java.awt.Color(40, 70, 50));
        btnLahir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/baby-girl.png"))); // NOI18N
        btnLahir.setText("Kelahiran Bayi");
        btnLahir.setIconTextGap(0);
        btnLahir.setName("btnLahir"); // NOI18N
        btnLahir.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLahir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLahirActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLahir);

        btnSetBiayaHarian.setForeground(new java.awt.Color(40, 70, 50));
        btnSetBiayaHarian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnSetBiayaHarian.setText("Biaya Harian");
        btnSetBiayaHarian.setIconTextGap(0);
        btnSetBiayaHarian.setName("btnSetBiayaHarian"); // NOI18N
        btnSetBiayaHarian.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetBiayaHarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetBiayaHarianActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetBiayaHarian);

        btnJenisInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnJenisInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/cabinet.png"))); // NOI18N
        btnJenisInventaris.setText("Jenis");
        btnJenisInventaris.setIconTextGap(0);
        btnJenisInventaris.setName("btnJenisInventaris"); // NOI18N
        btnJenisInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnJenisInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnJenisInventaris);

        btnKategoriInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnKategoriInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnKategoriInventaris.setText("Kategori");
        btnKategoriInventaris.setIconTextGap(0);
        btnKategoriInventaris.setName("btnKategoriInventaris"); // NOI18N
        btnKategoriInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnKategoriInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnKategoriInventaris);

        btnLihatPiutang.setForeground(new java.awt.Color(40, 70, 50));
        btnLihatPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/checklist_pencil-o.png"))); // NOI18N
        btnLihatPiutang.setText("Piutang Pasien");
        btnLihatPiutang.setIconTextGap(0);
        btnLihatPiutang.setName("btnLihatPiutang"); // NOI18N
        btnLihatPiutang.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLihatPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLihatPiutangActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLihatPiutang);

        btnLaboratorium.setForeground(new java.awt.Color(40, 70, 50));
        btnLaboratorium.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/sign-up.png"))); // NOI18N
        btnLaboratorium.setText("Periksa Lab");
        btnLaboratorium.setIconTextGap(0);
        btnLaboratorium.setName("btnLaboratorium"); // NOI18N
        btnLaboratorium.setPreferredSize(new java.awt.Dimension(200, 90));
        btnLaboratorium.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaboratoriumActionPerformed(evt);
            }
        });
        Panelmenu.add(btnLaboratorium);

        btnRalanMasuk.setForeground(new java.awt.Color(40, 70, 50));
        btnRalanMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/money_bag.png"))); // NOI18N
        btnRalanMasuk.setText("Ralan Masuk");
        btnRalanMasuk.setIconTextGap(0);
        btnRalanMasuk.setName("btnRalanMasuk"); // NOI18N
        btnRalanMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRalanMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRalanMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRalanMasuk);

        btnSetupAplikasi.setForeground(new java.awt.Color(40, 70, 50));
        btnSetupAplikasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/local_network.png"))); // NOI18N
        btnSetupAplikasi.setText("Setup Aplikasi");
        btnSetupAplikasi.setIconTextGap(0);
        btnSetupAplikasi.setName("btnSetupAplikasi"); // NOI18N
        btnSetupAplikasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupAplikasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupAplikasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupAplikasi);

        btnSetOtoRalan.setForeground(new java.awt.Color(40, 70, 50));
        btnSetOtoRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/stethoscope (1).png"))); // NOI18N
        btnSetOtoRalan.setText("Set Oto Ralan");
        btnSetOtoRalan.setIconTextGap(0);
        btnSetOtoRalan.setName("btnSetOtoRalan"); // NOI18N
        btnSetOtoRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetOtoRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetOtoRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetOtoRalan);

        btnRanapMasuk.setForeground(new java.awt.Color(40, 70, 50));
        btnRanapMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/money_bag.png"))); // NOI18N
        btnRanapMasuk.setText("Ranap Masuk");
        btnRanapMasuk.setIconTextGap(0);
        btnRanapMasuk.setName("btnRanapMasuk"); // NOI18N
        btnRanapMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRanapMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRanapMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRanapMasuk);

        btnProdusenInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnProdusenInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/industry.png"))); // NOI18N
        btnProdusenInventaris.setText("Produsen");
        btnProdusenInventaris.setIconTextGap(0);
        btnProdusenInventaris.setName("btnProdusenInventaris"); // NOI18N
        btnProdusenInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnProdusenInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdusenInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnProdusenInventaris);

        btnSetBiayaMasukSekali.setForeground(new java.awt.Color(40, 70, 50));
        btnSetBiayaMasukSekali.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnSetBiayaMasukSekali.setText("Biaya Masuk Sekali");
        btnSetBiayaMasukSekali.setIconTextGap(0);
        btnSetBiayaMasukSekali.setName("btnSetBiayaMasukSekali"); // NOI18N
        btnSetBiayaMasukSekali.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetBiayaMasukSekali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetBiayaMasukSekaliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetBiayaMasukSekali);

        btnPaketOperasi.setForeground(new java.awt.Color(40, 70, 50));
        btnPaketOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487111_stock_paste.png"))); // NOI18N
        btnPaketOperasi.setText("Tarif Operasi/VK");
        btnPaketOperasi.setIconTextGap(0);
        btnPaketOperasi.setName("btnPaketOperasi"); // NOI18N
        btnPaketOperasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnPaketOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaketOperasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnPaketOperasi);

        btnTagihanOperasi.setForeground(new java.awt.Color(40, 70, 50));
        btnTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/surgeon.png"))); // NOI18N
        btnTagihanOperasi.setText("Operasi/VK");
        btnTagihanOperasi.setIconTextGap(0);
        btnTagihanOperasi.setName("btnTagihanOperasi"); // NOI18N
        btnTagihanOperasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanOperasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanOperasi);

        BtnJadwal.setForeground(new java.awt.Color(40, 70, 50));
        BtnJadwal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor 3.png"))); // NOI18N
        BtnJadwal.setText("Jadwal Praktek");
        BtnJadwal.setIconTextGap(0);
        BtnJadwal.setName("BtnJadwal"); // NOI18N
        BtnJadwal.setPreferredSize(new java.awt.Dimension(200, 90));
        BtnJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJadwalActionPerformed(evt);
            }
        });
        Panelmenu.add(BtnJadwal);

        btnMerkInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnMerkInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bag1.png"))); // NOI18N
        btnMerkInventaris.setText("Merk");
        btnMerkInventaris.setIconTextGap(0);
        btnMerkInventaris.setName("btnMerkInventaris"); // NOI18N
        btnMerkInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnMerkInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMerkInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnMerkInventaris);

        btnRuangInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnRuangInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/bedroom.png"))); // NOI18N
        btnRuangInventaris.setText("Ruang");
        btnRuangInventaris.setIconTextGap(0);
        btnRuangInventaris.setName("btnRuangInventaris"); // NOI18N
        btnRuangInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRuangInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuangInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRuangInventaris);

        btnBarangInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnBarangInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/fax.png"))); // NOI18N
        btnBarangInventaris.setText("Koleksi");
        btnBarangInventaris.setIconTextGap(0);
        btnBarangInventaris.setName("btnBarangInventaris"); // NOI18N
        btnBarangInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnBarangInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBarangInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnBarangInventaris);

        btnInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486921_bar-code.png"))); // NOI18N
        btnInventaris.setText("Inventaris");
        btnInventaris.setIconTextGap(0);
        btnInventaris.setName("btnInventaris"); // NOI18N
        btnInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnInventaris);

        btnSirkulasiInventaris.setForeground(new java.awt.Color(40, 70, 50));
        btnSirkulasiInventaris.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487125_system-restart-panel.png"))); // NOI18N
        btnSirkulasiInventaris.setText("Sirkulasi Inventaris");
        btnSirkulasiInventaris.setIconTextGap(0);
        btnSirkulasiInventaris.setName("btnSirkulasiInventaris"); // NOI18N
        btnSirkulasiInventaris.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSirkulasiInventaris.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSirkulasiInventarisActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSirkulasiInventaris);

        btnFrekuensiRalan.setForeground(new java.awt.Color(40, 70, 50));
        btnFrekuensiRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Gnome-X-Office-Address-Book-48.png"))); // NOI18N
        btnFrekuensiRalan.setText("Penyakit Di Ralan");
        btnFrekuensiRalan.setIconTextGap(0);
        btnFrekuensiRalan.setName("btnFrekuensiRalan"); // NOI18N
        btnFrekuensiRalan.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRalanActionPerformed(evt);
            }
        });
        Panelmenu.add(btnFrekuensiRalan);

        btnFrekuensiRanap.setForeground(new java.awt.Color(40, 70, 50));
        btnFrekuensiRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/applications-office.png"))); // NOI18N
        btnFrekuensiRanap.setText("Penyakit Di Ranap");
        btnFrekuensiRanap.setIconTextGap(0);
        btnFrekuensiRanap.setName("btnFrekuensiRanap"); // NOI18N
        btnFrekuensiRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnFrekuensiRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFrekuensiRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnFrekuensiRanap);

        btnSetupOtoLokasi.setForeground(new java.awt.Color(40, 70, 50));
        btnSetupOtoLokasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/our_process_2.png"))); // NOI18N
        btnSetupOtoLokasi.setText("Setup Oto Lokasi");
        btnSetupOtoLokasi.setIconTextGap(0);
        btnSetupOtoLokasi.setName("btnSetupOtoLokasi"); // NOI18N
        btnSetupOtoLokasi.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupOtoLokasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupOtoLokasiActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupOtoLokasi);

        btnTagihanPoli.setForeground(new java.awt.Color(40, 70, 50));
        btnTagihanPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360486845_23.png"))); // NOI18N
        btnTagihanPoli.setText("Harian Poli");
        btnTagihanPoli.setIconTextGap(0);
        btnTagihanPoli.setName("btnTagihanPoli"); // NOI18N
        btnTagihanPoli.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTagihanPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTagihanPoliActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTagihanPoli);

        btnRujukMasuk.setForeground(new java.awt.Color(40, 70, 50));
        btnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/reports.png"))); // NOI18N
        btnRujukMasuk.setText("Rujukan Masuk");
        btnRujukMasuk.setIconTextGap(0);
        btnRujukMasuk.setName("btnRujukMasuk"); // NOI18N
        btnRujukMasuk.setPreferredSize(new java.awt.Dimension(200, 90));
        btnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRujukMasukActionPerformed(evt);
            }
        });
        Panelmenu.add(btnRujukMasuk);

        btnTracker.setForeground(new java.awt.Color(40, 70, 50));
        btnTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/receptionist.png"))); // NOI18N
        btnTracker.setText("Tracker Login");
        btnTracker.setIconTextGap(0);
        btnTracker.setName("btnTracker"); // NOI18N
        btnTracker.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrackerActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTracker);

        btnTindakanRanap.setForeground(new java.awt.Color(40, 70, 50));
        btnTindakanRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/doctor (2).png"))); // NOI18N
        btnTindakanRanap.setText("Tarif Ranap");
        btnTindakanRanap.setIconTextGap(0);
        btnTindakanRanap.setName("btnTindakanRanap"); // NOI18N
        btnTindakanRanap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTindakanRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTindakanRanapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTindakanRanap);

        btnSetupJamInap.setForeground(new java.awt.Color(40, 70, 50));
        btnSetupJamInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/Time.png"))); // NOI18N
        btnSetupJamInap.setText("Setup Jam KamIn");
        btnSetupJamInap.setIconTextGap(0);
        btnSetupJamInap.setName("btnSetupJamInap"); // NOI18N
        btnSetupJamInap.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetupJamInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetupJamInapActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetupJamInap);

        btnStokObatPasien.setForeground(new java.awt.Color(40, 70, 50));
        btnStokObatPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/1360487078_shipping.png"))); // NOI18N
        btnStokObatPasien.setText("Obat Di Ranap");
        btnStokObatPasien.setIconTextGap(0);
        btnStokObatPasien.setName("btnStokObatPasien"); // NOI18N
        btnStokObatPasien.setPreferredSize(new java.awt.Dimension(200, 90));
        btnStokObatPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStokObatPasienActionPerformed(evt);
            }
        });
        Panelmenu.add(btnStokObatPasien);

        btnTarifLab.setForeground(new java.awt.Color(40, 70, 50));
        btnTarifLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/address-book.png"))); // NOI18N
        btnTarifLab.setText("Tarif Lab");
        btnTarifLab.setIconTextGap(0);
        btnTarifLab.setName("btnTarifLab"); // NOI18N
        btnTarifLab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnTarifLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTarifLabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnTarifLab);

        btnSetPenjab.setForeground(new java.awt.Color(40, 70, 50));
        btnSetPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/48x48/user3.png"))); // NOI18N
        btnSetPenjab.setText("Set P.J. Lab");
        btnSetPenjab.setIconTextGap(0);
        btnSetPenjab.setName("btnSetPenjab"); // NOI18N
        btnSetPenjab.setPreferredSize(new java.awt.Dimension(200, 90));
        btnSetPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSetPenjabActionPerformed(evt);
            }
        });
        Panelmenu.add(btnSetPenjab);

        internalFrame5.add(Panelmenu, java.awt.BorderLayout.CENTER);

        DlgHome.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("::[ Khanza Hospital Management System 2014 ]::");
        setIconImages(null);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 170)));
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(40, 44));
        internalFrame1.setVerifyInputWhenFocusTarget(false);
        internalFrame1.setWarnaAtas(new java.awt.Color(235, 255, 210));
        internalFrame1.setWarnaBawah(new java.awt.Color(195, 215, 170));
        internalFrame1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 1));

        BtnMenu.setForeground(new java.awt.Color(80, 100, 80));
        BtnMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/1358372639_kmenuedit.png"))); // NOI18N
        BtnMenu.setMnemonic('1');
        BtnMenu.setText("Menu");
        BtnMenu.setToolTipText("Alt+1");
        BtnMenu.setEnabled(false);
        BtnMenu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnMenu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnMenu.setIconTextGap(3);
        BtnMenu.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnMenu.setName("BtnMenu"); // NOI18N
        BtnMenu.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnMenu.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnMenu.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnMenuActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnMenu);

        jSeparator4.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator4.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setName("jSeparator4"); // NOI18N
        jSeparator4.setOpaque(true);
        jSeparator4.setPreferredSize(new java.awt.Dimension(1, 38));
        internalFrame1.add(jSeparator4);

        BtnLog.setForeground(new java.awt.Color(80, 100, 80));
        BtnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/login2.png"))); // NOI18N
        BtnLog.setMnemonic('2');
        BtnLog.setText("Log In");
        BtnLog.setToolTipText("Alt+2");
        BtnLog.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnLog.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnLog.setIconTextGap(3);
        BtnLog.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnLog.setName("BtnLog"); // NOI18N
        BtnLog.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnLog.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnLog.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnLog);

        BtnToolPasien.setForeground(new java.awt.Color(80, 100, 80));
        BtnToolPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/family24.png"))); // NOI18N
        BtnToolPasien.setMnemonic('3');
        BtnToolPasien.setText("Pasien");
        BtnToolPasien.setToolTipText("Alt+3");
        BtnToolPasien.setEnabled(false);
        BtnToolPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolPasien.setIconTextGap(3);
        BtnToolPasien.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnToolPasien.setName("BtnToolPasien"); // NOI18N
        BtnToolPasien.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnToolPasien.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnToolPasien.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolPasienActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolPasien);

        BtnToolReg.setForeground(new java.awt.Color(80, 100, 80));
        BtnToolReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/PatientFile.png"))); // NOI18N
        BtnToolReg.setMnemonic('4');
        BtnToolReg.setText("Registrasi");
        BtnToolReg.setToolTipText("Alt+4");
        BtnToolReg.setEnabled(false);
        BtnToolReg.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolReg.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolReg.setIconTextGap(3);
        BtnToolReg.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnToolReg.setName("BtnToolReg"); // NOI18N
        BtnToolReg.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnToolReg.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnToolReg.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolRegActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolReg);

        jSeparator5.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator5.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 38));
        internalFrame1.add(jSeparator5);

        BtnToolRanap.setForeground(new java.awt.Color(80, 100, 80));
        BtnToolRanap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/home24.png"))); // NOI18N
        BtnToolRanap.setMnemonic('5');
        BtnToolRanap.setText("Kamar Inap");
        BtnToolRanap.setToolTipText("Alt+5");
        BtnToolRanap.setEnabled(false);
        BtnToolRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolRanap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolRanap.setIconTextGap(3);
        BtnToolRanap.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnToolRanap.setName("BtnToolRanap"); // NOI18N
        BtnToolRanap.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnToolRanap.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnToolRanap.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolRanapActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolRanap);

        BtnToolRalan.setForeground(new java.awt.Color(80, 100, 80));
        BtnToolRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-edit24.png"))); // NOI18N
        BtnToolRalan.setMnemonic('6');
        BtnToolRalan.setText("Kasir Ralan");
        BtnToolRalan.setToolTipText("Alt+6");
        BtnToolRalan.setEnabled(false);
        BtnToolRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnToolRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnToolRalan.setIconTextGap(3);
        BtnToolRalan.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnToolRalan.setName("BtnToolRalan"); // NOI18N
        BtnToolRalan.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnToolRalan.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnToolRalan.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnToolRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnToolRalanActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnToolRalan);

        jSeparator6.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator6.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator6.setName("jSeparator6"); // NOI18N
        jSeparator6.setOpaque(true);
        jSeparator6.setPreferredSize(new java.awt.Dimension(1, 38));
        internalFrame1.add(jSeparator6);

        BtnClose.setForeground(new java.awt.Color(80, 100, 80));
        BtnClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Delete.png"))); // NOI18N
        BtnClose.setMnemonic('7');
        BtnClose.setText("Keluar");
        BtnClose.setToolTipText("Alt+7");
        BtnClose.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BtnClose.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        BtnClose.setIconTextGap(3);
        BtnClose.setMargin(new java.awt.Insets(1, 2, 1, 0));
        BtnClose.setName("BtnClose"); // NOI18N
        BtnClose.setPreferredSize(new java.awt.Dimension(105, 40));
        BtnClose.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BtnClose.setVerticalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseActionPerformed(evt);
            }
        });
        internalFrame1.add(BtnClose);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.PAGE_START);

        internalFrame4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 170)));
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setPreferredSize(new java.awt.Dimension(330, 25));
        internalFrame4.setWarnaAtas(new java.awt.Color(195, 215, 170));
        internalFrame4.setWarnaBawah(new java.awt.Color(235, 255, 210));
        internalFrame4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        lblStts.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        lblStts.setForeground(new java.awt.Color(80, 100, 80));
        lblStts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblStts.setText("Status Admin :");
        lblStts.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblStts.setName("lblStts"); // NOI18N
        lblStts.setPreferredSize(new java.awt.Dimension(100, 23));
        internalFrame4.add(lblStts);

        jSeparator1.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator1.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setName("jSeparator1"); // NOI18N
        jSeparator1.setOpaque(true);
        jSeparator1.setPreferredSize(new java.awt.Dimension(1, 20));
        internalFrame4.add(jSeparator1);

        lblUser.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        lblUser.setForeground(new java.awt.Color(80, 100, 80));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setText("Log Out");
        lblUser.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblUser.setName("lblUser"); // NOI18N
        lblUser.setPreferredSize(new java.awt.Dimension(170, 23));
        internalFrame4.add(lblUser);

        jSeparator2.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator2.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setName("jSeparator2"); // NOI18N
        jSeparator2.setOpaque(true);
        jSeparator2.setPreferredSize(new java.awt.Dimension(1, 20));
        internalFrame4.add(jSeparator2);

        lblTgl.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        lblTgl.setForeground(new java.awt.Color(80, 100, 80));
        lblTgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTgl.setText("Tanggal");
        lblTgl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblTgl.setName("lblTgl"); // NOI18N
        lblTgl.setPreferredSize(new java.awt.Dimension(150, 23));
        internalFrame4.add(lblTgl);

        jSeparator3.setBackground(new java.awt.Color(155, 175, 130));
        jSeparator3.setForeground(new java.awt.Color(155, 175, 130));
        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setName("jSeparator3"); // NOI18N
        jSeparator3.setOpaque(true);
        jSeparator3.setPreferredSize(new java.awt.Dimension(1, 20));
        internalFrame4.add(jSeparator3);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(80, 100, 80));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        jLabel7.setText(" Didesain & dibuat oleh Khanza.Soft Media");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel7.setIconTextGap(3);
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(347, 23));
        internalFrame4.add(jLabel7);

        getContentPane().add(internalFrame4, java.awt.BorderLayout.PAGE_END);

        PanelUtama.setName("PanelUtama"); // NOI18N
        PanelUtama.setOpaque(false);
        PanelUtama.setLayout(new java.awt.BorderLayout());

        scrollPane1.setBorder(null);
        scrollPane1.setName("scrollPane1"); // NOI18N

        PanelWall.setBackground(new java.awt.Color(29, 29, 29));
        PanelWall.setBackgroundImage(new javax.swing.ImageIcon(getClass().getResource("/picture/wallpaper.jpg"))); // NOI18N
        PanelWall.setBackgroundImageType(usu.widget.constan.BackgroundConstan.BACKGROUND_IMAGE_STRECT);
        PanelWall.setBorder(javax.swing.BorderFactory.createEmptyBorder(150, 0, 150, 0));
        PanelWall.setPreferredSize(new java.awt.Dimension(200, 200));
        PanelWall.setRound(false);
        PanelWall.setWarna(new java.awt.Color(110, 110, 110));
        PanelWall.setLayout(new java.awt.BorderLayout());

        panelGlass3.setBackground(new java.awt.Color(255, 255, 255));
        panelGlass3.setOpaqueImage(false);
        panelGlass3.setPreferredSize(new java.awt.Dimension(200, 140));
        panelGlass3.setRound(false);
        panelGlass3.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(80, 100, 80));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("Your Businis Solution");
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(430, 30));
        panelGlass3.add(jLabel11);
        jLabel11.setBounds(210, 110, 680, 30);

        jLabel8.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(80, 100, 80));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Hospital .png"))); // NOI18N
        jLabel8.setText("Khanza HMS+, Hospital Management System");
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass3.add(jLabel8);
        jLabel8.setBounds(40, 0, 820, 150);

        PanelWall.add(panelGlass3, java.awt.BorderLayout.CENTER);

        scrollPane1.setViewportView(PanelWall);

        PanelUtama.add(scrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(PanelUtama, java.awt.BorderLayout.CENTER);

        MenuBar.setBackground(new java.awt.Color(0, 0, 0));
        MenuBar.setBorder(null);
        MenuBar.setForeground(new java.awt.Color(255, 204, 255));
        MenuBar.setName("MenuBar"); // NOI18N
        MenuBar.setPreferredSize(new java.awt.Dimension(227, 27));

        jMenu1.setBackground(new java.awt.Color(20, 0, 20));
        jMenu1.setForeground(new java.awt.Color(255, 255, 0));
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/85.png"))); // NOI18N
        jMenu1.setMnemonic('A');
        jMenu1.setText("Program");
        jMenu1.setToolTipText("Alt+A");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(100, 30));

        MnLogin.setBackground(new java.awt.Color(220, 220, 220));
        MnLogin.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLogin.setForeground(new java.awt.Color(153, 0, 51));
        MnLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/login2.png"))); // NOI18N
        MnLogin.setText("Log In");
        MnLogin.setName("MnLogin"); // NOI18N
        MnLogin.setPreferredSize(new java.awt.Dimension(200, 40));
        MnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLogActionPerformed(evt);
            }
        });
        jMenu1.add(MnLogin);

        jSeparator8.setBackground(new java.awt.Color(0, 102, 102));
        jSeparator8.setForeground(new java.awt.Color(0, 102, 102));
        jSeparator8.setName("jSeparator8"); // NOI18N
        jSeparator8.setPreferredSize(new java.awt.Dimension(0, 1));
        jMenu1.add(jSeparator8);

        MnGantiPassword.setBackground(new java.awt.Color(220, 220, 220));
        MnGantiPassword.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGantiPassword.setForeground(new java.awt.Color(153, 0, 51));
        MnGantiPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/73.png"))); // NOI18N
        MnGantiPassword.setText("Ubah Password");
        MnGantiPassword.setEnabled(false);
        MnGantiPassword.setName("MnGantiPassword"); // NOI18N
        MnGantiPassword.setPreferredSize(new java.awt.Dimension(200, 40));
        MnGantiPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGantiPasswordBtnLogActionPerformed(evt);
            }
        });
        jMenu1.add(MnGantiPassword);

        MenuKeluar.setBackground(new java.awt.Color(220, 220, 220));
        MenuKeluar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuKeluar.setForeground(new java.awt.Color(153, 0, 51));
        MenuKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Delete.png"))); // NOI18N
        MenuKeluar.setText("Keluar");
        MenuKeluar.setName("MenuKeluar"); // NOI18N
        MenuKeluar.setPreferredSize(new java.awt.Dimension(200, 40));
        MenuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuKeluarBtnKeluarActionPerformed(evt);
            }
        });
        jMenu1.add(MenuKeluar);

        MenuBar.add(jMenu1);

        jMenu4.setBackground(new java.awt.Color(20, 0, 20));
        jMenu4.setForeground(new java.awt.Color(255, 255, 0));
        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/63.png"))); // NOI18N
        jMenu4.setMnemonic('G');
        jMenu4.setText("Tentang Program");
        jMenu4.setToolTipText("Alt+G");
        jMenu4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu4.setName("jMenu4"); // NOI18N
        jMenu4.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                jMenu4MenuSelected(evt);
            }
        });
        jMenu4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu4MouseClicked(evt);
            }
        });
        MenuBar.add(jMenu4);

        setJMenuBar(MenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        DlgHome.dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void BtnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseActionPerformed
        isTutup();
        DlgHome.dispose();
        int jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau keluar dari program ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }//GEN-LAST:event_BtnCloseActionPerformed

    private void BtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelActionPerformed
        edAdmin.setText("");
        edPwd.setText("");
        DlgLogin.dispose();
    }//GEN-LAST:event_BtnCancelActionPerformed

    private void BtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLogActionPerformed
        switch (BtnLog.getText().trim()) {
            case "Log Out":
                BtnToolReg.setEnabled(false);
                BtnToolPasien.setEnabled(false);
                BtnToolRanap.setEnabled(false);
                BtnToolRalan.setEnabled(false);
                edAdmin.setText("");
                edPwd.setText("");
                BtnLog.setText("Log In");
                MnLogin.setText("Log In");
                lblStts.setText("Status Admin : ");
                lblUser.setText("Log Out");
                BtnMenu.setEnabled(false);
                isTutup();
                break;
            case "Log In":
                DlgLogin.setVisible(true);
                edAdmin.requestFocus();
                break;
        }
    }//GEN-LAST:event_BtnLogActionPerformed

    private void BtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLoginActionPerformed
        if(edAdmin.getText().trim().equals("")){
            Valid.textKosong(edAdmin,"ID User");
        }else if(edPwd.getText().trim().equals("")){
            Valid.textKosong(edPwd,"Password");
        }else {
            try {                               
                var.setData(edAdmin.getText(),edPwd.getText());        
                if(edAdmin.getText().equals("admin") && edPwd.getText().equals("akusayangsamakamu")){       
                    BtnMenu.setEnabled(true);             
                    BtnToolReg.setEnabled(true);
                    BtnToolPasien.setEnabled(true);
                    BtnToolRanap.setEnabled(true);
                    BtnToolRalan.setEnabled(true);
                    MnGantiPassword.setEnabled(false);

                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText("Admin Utama");
                }else if(var.getjml1()>=1){    
                    BtnMenu.setEnabled(true);
                    BtnToolReg.setEnabled(true);
                    BtnToolPasien.setEnabled(true);
                    BtnToolRanap.setEnabled(true);
                    BtnToolRalan.setEnabled(true);
                    MnGantiPassword.setEnabled(false);

                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText("Admin Utama");
                }else if(var.getjml2()>=1){  
                    BtnMenu.setEnabled(true);
                    DlgLogin.dispose();
                    BtnLog.setText("Log Out");
                    MnLogin.setText("Log Out");
                    lblStts.setText("Admin : ");
                    lblUser.setText(var.getkode());
                    MnGantiPassword.setEnabled(true);
                    BtnToolReg.setEnabled(var.getregistrasi());
                    BtnToolPasien.setEnabled(var.getpasien());
                    BtnToolRanap.setEnabled(var.getranap());
                    BtnToolRalan.setEnabled(var.getralan());
                    Sequel.menyimpan("tracker","'"+edAdmin.getText()+"',current_date(),current_time()","Login");
                }else if((var.getjml1()==0)&&(var.getjml2()==0)){
                    JOptionPane.showMessageDialog(null,"Maaf, Gagal login. ID User atau password ada yang salah ...!");
                    BtnToolReg.setEnabled(false);
                    BtnToolPasien.setEnabled(false);
                    BtnToolRanap.setEnabled(false);
                    BtnToolRalan.setEnabled(false);
                    MnGantiPassword.setEnabled(false);
                    edAdmin.setText("");
                    edPwd.setText("");           
                     
                    BtnMenu.setEnabled(false);

                    edAdmin.requestFocus();
                    BtnLog.setText("Log In");
                    MnLogin.setText("Log In");
                    lblStts.setText("Status Admin : ");
                    lblUser.setText("Log Out");   
                }
            } catch (HeadlessException e) {
                System.out.println("error : "+e);
            }
        }
    }//GEN-LAST:event_BtnLoginActionPerformed

    private void BtnToolPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolPasienActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        isTutup();
        kasirralan.kamarinap.reg.pasien.isCek();
        kasirralan.kamarinap.reg.pasien.tampil();
        kasirralan.kamarinap.reg.pasien.emptTeks();    
        kasirralan.kamarinap.reg.pasien.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.pasien.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.pasien.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
    }//GEN-LAST:event_BtnToolPasienActionPerformed

    private void BtnToolRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolRanapActionPerformed
        var.setStatus(false);
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.tampil();
        kasirralan.kamarinap.isCek();
        kasirralan.kamarinap.emptTeks();    
        kasirralan.kamarinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnToolRanapActionPerformed

private void MenuKeluarBtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuKeluarBtnKeluarActionPerformed
        int jawab=JOptionPane.showConfirmDialog(null, "Yakin anda mau keluar dari aplikasi ini ????","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(jawab==JOptionPane.YES_OPTION){
            this.dispose();
            System.exit(0);
        }
}//GEN-LAST:event_MenuKeluarBtnKeluarActionPerformed

private void jMenu4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu4MouseClicked
     isTutup();
     About.setSize(PanelWall.getWidth(), PanelWall.getHeight());
     About.setLocationRelativeTo(PanelWall);  
     About.setVisible(true);
     DlgHome.dispose();
}//GEN-LAST:event_jMenu4MouseClicked

private void jMenu4MenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_jMenu4MenuSelected
    isTutup();
    About.setSize(PanelWall.getWidth(), PanelWall.getHeight());
    About.setLocationRelativeTo(PanelWall);  
    About.setVisible(true);
    DlgHome.dispose();
}//GEN-LAST:event_jMenu4MenuSelected

private void edAdminKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edAdminKeyPressed
        Valid.pindah(evt,BtnCancel, edPwd);
}//GEN-LAST:event_edAdminKeyPressed

private void edPwdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_edPwdKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnLoginActionPerformed(null);
        }else{
            Valid.pindah(evt,edAdmin, BtnLogin);
        }
}//GEN-LAST:event_edPwdKeyPressed

private void BtnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnMenuActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        isTutup();
        try{
            com.sun.awt.AWTUtilities.setWindowOpacity(DlgHome,0.7f);
        }catch(Exception e){
            
        }
        
        DlgHome.setSize(PanelUtama.getWidth()-25, PanelUtama.getHeight()-25);
        /*var.setTinggi(PanelUtama.getHeight());
        var.setLebar(PanelUtama.getWidth());*/
        isTampil();
        isMenu();
        DlgHome.setLocationRelativeTo(PanelUtama);
        DlgHome.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnMenuActionPerformed

private void BtnToolRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolRalanActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.tampilkasir();
        kasirralan.isCek();
        kasirralan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.setLocationRelativeTo(PanelUtama);
        kasirralan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnToolRalanActionPerformed

private void BtnToolRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnToolRegActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        isTutup();
        kasirralan.kamarinap.reg.tampil();
        kasirralan.kamarinap.reg.emptTeks();    
        kasirralan.kamarinap.reg.isCek();
        kasirralan.kamarinap.reg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());   
}//GEN-LAST:event_BtnToolRegActionPerformed

private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
    if(this.getState()==1){
        DlgHome.dispose();
    }
}//GEN-LAST:event_formWindowStateChanged

private void MnGantiPasswordBtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGantiPasswordBtnLogActionPerformed
    PassLama.setText("");
    Passbaru1.setText("");
    PassBaru2.setText("");
    WindowInput.setVisible(true);
}//GEN-LAST:event_MnGantiPasswordBtnLogActionPerformed

private void BtnClosePassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnClosePassActionPerformed
        WindowInput.dispose();
}//GEN-LAST:event_BtnClosePassActionPerformed

private void BtnClosePassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnClosePassKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            WindowInput.dispose();
        }else{Valid.pindah(evt, PassBaru2, PassLama);}
}//GEN-LAST:event_BtnClosePassKeyPressed

private void BtnSimpanPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanPassActionPerformed
        if(PassLama.getText().trim().equals("")){
            Valid.textKosong(PassLama,"Password Lama");
        }else if(Passbaru1.getText().trim().equals("")){
            Valid.textKosong(Passbaru1,"Password Baru");
        }else if(PassBaru2.getText().trim().equals("")){
            Valid.textKosong(PassBaru2,"Password Baru");
        }else if(!edPwd.getText().trim().equals(PassLama.getText())){
            JOptionPane.showMessageDialog(null,"Maaf, Password lama salah...!!!");
            PassLama.requestFocus();
        }else if(!Passbaru1.getText().trim().equals(PassBaru2.getText())){
            JOptionPane.showMessageDialog(null,"Maaf, Password Baru 1 dan Password Baru 2 tidak sesuai...!!!");
            PassBaru2.requestFocus();
        }else{
            Sequel.queryu("update user set password=AES_ENCRYPT('"+PassBaru2.getText()+"','windi')  where id_user=AES_ENCRYPT('"+lblUser.getText()+"','nur')");            
            WindowInput.setVisible(false);
        }
}//GEN-LAST:event_BtnSimpanPassActionPerformed

private void BtnSimpanPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanPassKeyPressed
        Valid.pindah(evt,PassLama,PassBaru2);
}//GEN-LAST:event_BtnSimpanPassKeyPressed

    private void button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button1ActionPerformed
        DlgHome.dispose();
    }//GEN-LAST:event_button1ActionPerformed

    private void cmbMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbMenuItemStateChanged
        isTampil();
    }//GEN-LAST:event_cmbMenuItemStateChanged

    private void btnBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarcodeActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarcode barcode=new DlgBarcode(this,false);
        barcode.tampil("");
        //barcode.isCek();
        barcode.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        barcode.setLocationRelativeTo(PanelUtama);
        barcode.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarcodeActionPerformed

    private void btnICDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnICDActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.tampil();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.isCek();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.emptTeks();
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.beriobat.dlgobtpny.penyakit.penyakit.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnICDActionPerformed

    private void btnObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgobt.barang.tampil(" order by databarang.kode_brng ");
        kasirralan.billing.dlgobt.barang.emptTeks();
        kasirralan.billing.dlgobt.barang.isCek();
        kasirralan.billing.dlgobt.barang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatActionPerformed

    private void btnObatPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnObatPenyakitActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgObatPenyakit obatpenyakit=new DlgObatPenyakit(this,false);
        obatpenyakit.tampil("");
        obatpenyakit.isCek();
        obatpenyakit.emptTeks();
        obatpenyakit.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        obatpenyakit.setLocationRelativeTo(PanelUtama);
        obatpenyakit.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnObatPenyakitActionPerformed

    private void btnKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        var.setStatus(false);
        kasirralan.kamarinap.kamar.tampil();
        kasirralan.kamarinap.kamar.emptTeks();
        kasirralan.kamarinap.kamar.isCek();
        kasirralan.kamarinap.kamar.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.kamar.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.kamar.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKamarActionPerformed

    private void btnTindakanRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanRalanActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgrwjl.perawatan.perawatan.tampil();
        kasirralan.billing.dlgrwjl.perawatan.perawatan.emptTeks();
        kasirralan.billing.dlgrwjl.perawatan.perawatan.isCek();
        kasirralan.billing.dlgrwjl.perawatan.perawatan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgrwjl.perawatan.perawatan.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgrwjl.perawatan.perawatan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTindakanRalanActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.dokter.dokter.tampil("");
        kasirralan.kamarinap.reg.dokter.dokter.emptTeks();
        kasirralan.kamarinap.reg.dokter.dokter.isCek();
        kasirralan.kamarinap.reg.dokter.dokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.dokter.dokter.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.dokter.dokter.setAlwaysOnTop(false);
        kasirralan.kamarinap.reg.dokter.dokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegawaiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.petugas.petugas.tampil();
        kasirralan.kamarinap.reg.petugas.petugas.emptTeks();
        kasirralan.kamarinap.reg.petugas.petugas.isCek();
        kasirralan.kamarinap.reg.petugas.petugas.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.petugas.petugas.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.petugas.petugas.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPegawaiActionPerformed

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.pasien.tampil();
        kasirralan.kamarinap.reg.pasien.emptTeks();
        kasirralan.kamarinap.reg.pasien.isCek();
        kasirralan.kamarinap.reg.pasien.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.pasien.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.pasien.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnRegistrasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrasiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.reg.tampil();
        kasirralan.kamarinap.reg.emptTeks();
        kasirralan.kamarinap.reg.isCek();
        kasirralan.kamarinap.reg.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.reg.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.reg.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRegistrasiActionPerformed

    private void btnRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRalanActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgrwjl.tampilDr();
        kasirralan.billing.dlgrwjl.emptTeks();
        kasirralan.billing.dlgrwjl.isCek();
        kasirralan.billing.dlgrwjl.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgrwjl.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgrwjl.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRalanActionPerformed

    private void btnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKamarInapActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.tampil();
        kasirralan.kamarinap.isCek();
        kasirralan.kamarinap.emptTeks();
        kasirralan.kamarinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKamarInapActionPerformed

    private void btnRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanapActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.rawatinap.tampilDr();
        kasirralan.kamarinap.billing.rawatinap.emptTeks();
        kasirralan.kamarinap.billing.rawatinap.isCek();
        kasirralan.kamarinap.billing.rawatinap.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.rawatinap.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.rawatinap.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRanapActionPerformed

    private void btnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResepObatActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResepObat resep=new DlgResepObat(this,false);
        resep.tampil();
        resep.emptTeks();
        resep.isCek();
        resep.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        resep.setLocationRelativeTo(PanelUtama);
        resep.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnResepObatActionPerformed

    private void btnRujukPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukPasienActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRujuk rujuk=new DlgRujuk(this,false);
        rujuk.tampil();
        rujuk.emptTeks();
        rujuk.isCek();
        rujuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rujuk.setLocationRelativeTo(PanelUtama);
        rujuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukPasienActionPerformed

    private void btnBeriObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBeriObatActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.beriobat.tampilPO();
        kasirralan.kamarinap.billing.beriobat.emptTeks();
        kasirralan.kamarinap.billing.beriobat.isCek();
        kasirralan.kamarinap.billing.beriobat.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.beriobat.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.beriobat.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBeriObatActionPerformed

    private void btnPasienMatiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienMatiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPasienMati pasienmati=new DlgPasienMati(this,false);
        pasienmati.tampil();
        pasienmati.emptTeks();
        pasienmati.isCek();
        pasienmati.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pasienmati.setLocationRelativeTo(PanelUtama);
        pasienmati.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPasienMatiActionPerformed

    private void btnAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdminActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgAdmin admin=new DlgAdmin(this,false);
        admin.tampil();
        admin.emptTeks();
        admin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        admin.setLocationRelativeTo(PanelUtama);
        admin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAdminActionPerformed

    private void btnUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUserActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgUser user=new DlgUser(this,false);
        user.tampil();
        user.emptTeks();
        user.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        user.setLocationRelativeTo(PanelUtama);
        user.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnUserActionPerformed

    private void btnVakumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVakumActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgVakum vakum=new DlgVakum(this,false);
        vakum.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        vakum.setLocationRelativeTo(PanelUtama);
        vakum.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnVakumActionPerformed

    private void btnDisplayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisplayActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRunTeks runteks=new DlgRunTeks(this,false);
        runteks.tampil();
        runteks.emptTeks();
        runteks.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        runteks.setLocationRelativeTo(PanelUtama);
        runteks.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDisplayActionPerformed

    private void btnSetupHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupHargaActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetHarga setharga=new DlgSetHarga(this,false);
        setharga.tampil();
        setharga.emptTeks();
        setharga.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        setharga.setLocationRelativeTo(PanelUtama);
        setharga.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupHargaActionPerformed

    private void btnSuplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuplierActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSuplier suplier=new DlgSuplier(this,false);
        suplier.tampil();
        suplier.isCek();
        suplier.emptTeks();
        suplier.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        suplier.setLocationRelativeTo(PanelUtama);
        suplier.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSuplierActionPerformed

    private void btnJnsBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJnsBarangActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgobt.barang.jenis.jenis.tampil(" order by kdjns");
        kasirralan.billing.dlgobt.barang.jenis.jenis.isCek();
        kasirralan.billing.dlgobt.barang.jenis.jenis.emptTeks();
        kasirralan.billing.dlgobt.barang.jenis.jenis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.jenis.jenis.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.jenis.jenis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJnsBarangActionPerformed

    private void btnKonversiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKonversiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKonversi konversi=new DlgKonversi(this,false);
        konversi.tampil(" order by kode_sat");
        konversi.isCek();
        konversi.emptTeks();
        konversi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        konversi.setLocationRelativeTo(PanelUtama);
        konversi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKonversiActionPerformed

    private void btnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSatuanActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.billing.dlgobt.barang.satuan.satuan.tampil(" order by kode_sat");
        kasirralan.billing.dlgobt.barang.satuan.satuan.isCek();
        kasirralan.billing.dlgobt.barang.satuan.satuan.emptTeks();
        kasirralan.billing.dlgobt.barang.satuan.satuan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.billing.dlgobt.barang.satuan.satuan.setLocationRelativeTo(PanelUtama);
        kasirralan.billing.dlgobt.barang.satuan.satuan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSatuanActionPerformed

    private void btnCashFlowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCashFlowActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCashflow bubes=new DlgCashflow(this,false);
        bubes.prosesCari();
        bubes.isCek();
        bubes.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bubes.setLocationRelativeTo(PanelUtama);
        bubes.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnCashFlowActionPerformed

    private void btnBubesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBubesActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBubes bubes=new DlgBubes(this,false);
        bubes.prosesCari();
        bubes.isCek();
        bubes.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bubes.setLocationRelativeTo(PanelUtama);
        bubes.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBubesActionPerformed

    private void btnPostingJurnalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostingJurnalActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJurnal jurnal=new DlgJurnal(this,false);
        jurnal.tampil();
        jurnal.isCek();
        jurnal.emptTeks();
        jurnal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jurnal.setLocationRelativeTo(PanelUtama);
        jurnal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPostingJurnalActionPerformed

    private void btnRekeningTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekeningTahunActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekeningTahun rekeningtahun=new DlgRekeningTahun(this,false);
        rekeningtahun.tampil();
        rekeningtahun.isCek();
        rekeningtahun.emptTeks();
        rekeningtahun.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rekeningtahun.setLocationRelativeTo(PanelUtama);
        rekeningtahun.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekeningTahunActionPerformed

    private void btnRekeningActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRekeningActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRekening rekening=new DlgRekening(this,false);
        rekening.tampil();
        rekening.isCek();
        rekening.emptTeks();
        rekening.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rekening.setLocationRelativeTo(PanelUtama);
        rekening.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRekeningActionPerformed

    private void btnPembelianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPembelianActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPembelian pembelian=new DlgPembelian(this,false);
        pembelian.tampil();
        pembelian.isCek();
        pembelian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        pembelian.setLocationRelativeTo(PanelUtama);
        pembelian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPembelianActionPerformed

    private void btnPenjualanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjualanActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenjualan penjualan=new DlgPenjualan(this,false);
        penjualan.isCek();
        penjualan.tampil();
        penjualan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        penjualan.setLocationRelativeTo(PanelUtama);
        penjualan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPenjualanActionPerformed

    private void btnPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPiutangActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPiutang piutang=new DlgPiutang(this,false);
        piutang.emptTeks();
        piutang.tampil();
        piutang.isCek();
        piutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        piutang.setLocationRelativeTo(PanelUtama);
        piutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPiutangActionPerformed

    private void btnBayarPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarPiutangActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBayarPiutang bayarpiutang=new DlgBayarPiutang(this,false);
        bayarpiutang.tampil();
        bayarpiutang.emptTeks();
        bayarpiutang.isCek();
        bayarpiutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        bayarpiutang.setLocationRelativeTo(PanelUtama);
        bayarpiutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBayarPiutangActionPerformed

    private void btnOpnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpnameActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgInputStok opname=new DlgInputStok(this,false);
        opname.tampil();
        opname.isCek();
        opname.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        opname.setLocationRelativeTo(PanelUtama);
        opname.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnOpnameActionPerformed

    private void btnReturBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturBeliActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturBeli returbeli=new DlgReturBeli(this,false);
        returbeli.tampil();
        returbeli.isCek();
        returbeli.emptTeks();
        returbeli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returbeli.setLocationRelativeTo(PanelUtama);
        returbeli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturBeliActionPerformed

    private void btnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturJualActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturJual returjual=new DlgReturJual(this,false);
        returjual.emptTeks();
        returjual.tampil();
        returjual.isCek();
        returjual.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returjual.setLocationRelativeTo(PanelUtama);
        returjual.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturJualActionPerformed

    private void btnSirkulasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasiBarang sirkulasi=new DlgSirkulasiBarang(this,false);
        sirkulasi.prosesCari();
        sirkulasi.isCek();
        sirkulasi.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        sirkulasi.setLocationRelativeTo(PanelUtama);
        sirkulasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiActionPerformed

    private void btnKeuntunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeuntunganActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgProyeksiJual projul=new DlgProyeksiJual(this,false);
        projul.prosesCari();
        projul.isCek();
        projul.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        projul.setLocationRelativeTo(PanelUtama);
        projul.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKeuntunganActionPerformed

    private void btnLabaRugiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLabaRugiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLabaRugi labrug=new DlgLabaRugi(this,false);
        labrug.prosesCari();
        labrug.isCek();
        labrug.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        labrug.setLocationRelativeTo(PanelUtama);
        labrug.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLabaRugiActionPerformed

    private void btnReturPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturPiutangActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturPiutang returpiutang=new DlgReturPiutang(this,false);
        returpiutang.tampil();
        returpiutang.isCek();
        returpiutang.emptTeks();
        returpiutang.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        returpiutang.setLocationRelativeTo(PanelUtama);
        returpiutang.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnReturPiutangActionPerformed

    private void btnAnalisaKamarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalisaKamarActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgAnalisaKamin analisakamin=new DlgAnalisaKamin(this,false);
        analisakamin.prosesCari();
        analisakamin.isCek();
        analisakamin.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        analisakamin.setLocationRelativeTo(PanelUtama);
        analisakamin.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnAnalisaKamarActionPerformed

    private void btnRHDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHDOkterActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHTindakanDokter rhtindakandokter=new DlgRHTindakanDokter(this,false);
        rhtindakandokter.prosesCari();
        rhtindakandokter.isCek();
        rhtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakandokter.setLocationRelativeTo(PanelUtama);
        rhtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHDOkterActionPerformed

    private void btnRBDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBDokterActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanDokter rbtindakandokter=new DlgRBTindakanDokter(this,false);
        rbtindakandokter.prosesCari();
        rbtindakandokter.isCek();
        rbtindakandokter.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbtindakandokter.setLocationRelativeTo(PanelUtama);
        rbtindakandokter.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBDokterActionPerformed

    private void btnTagihanMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanMasukActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtBiaya billing=new DlgLhtBiaya(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanMasukActionPerformed

    private void btnResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResumeActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgResumePerawatan resume=new DlgResumePerawatan(this,false);
        resume.tampil();
        resume.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        resume.setLocationRelativeTo(PanelUtama);
        resume.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnResumeActionPerformed

    private void btnDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDietActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPemberianDiet diet=new DlgPemberianDiet(this,false);
        diet.tampil();
        diet.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        diet.setLocationRelativeTo(PanelUtama);
        diet.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnDietActionPerformed

    private void btnRHParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHParamedisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRHTindakanParamedis rhtindakanparamedis=new DlgRHTindakanParamedis(this,false);
        rhtindakanparamedis.prosesCari();
        rhtindakanparamedis.isCek();
        rhtindakanparamedis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rhtindakanparamedis.setLocationRelativeTo(PanelUtama);
        rhtindakanparamedis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRHParamedisActionPerformed

    private void btnRBParamedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRBParamedisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanParamedis rbtindakanparamedis=new DlgRBTindakanParamedis(this,false);
        rbtindakanparamedis.prosesCari();
        rbtindakanparamedis.isCek();
        rbtindakanparamedis.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbtindakanparamedis.setLocationRelativeTo(PanelUtama);
        rbtindakanparamedis.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRBParamedisActionPerformed

    private void btnKasirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKasirActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.tampilkasir();
        kasirralan.isCek();
        kasirralan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.setLocationRelativeTo(PanelUtama);
        kasirralan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKasirActionPerformed

    private void btnLahirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLahirActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgIKBBayi lahir=new DlgIKBBayi(this,false);
        lahir.tampil();
        lahir.isCek();
        lahir.emptTeks();
        lahir.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        lahir.setLocationRelativeTo(PanelUtama);
        lahir.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLahirActionPerformed

    private void btnSetBiayaHarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetBiayaHarianActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBiayaHarian biayaharian=new DlgBiayaHarian(this,false);
        biayaharian.emptTeks();
        biayaharian.tampil();
        biayaharian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        biayaharian.setLocationRelativeTo(PanelUtama);
        biayaharian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetBiayaHarianActionPerformed

    private void btnJenisInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisInventarisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJenisInventaris jnsinventaris=new DlgJenisInventaris(this,false);
        jnsinventaris.emptTeks();
        jnsinventaris.tampil();
        jnsinventaris.isCek();
        jnsinventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jnsinventaris.setLocationRelativeTo(PanelUtama);
        jnsinventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnJenisInventarisActionPerformed

    private void btnKategoriInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriInventarisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgKategoriInventaris ktginventaris=new DlgKategoriInventaris(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnKategoriInventarisActionPerformed

    private void btnLihatPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLihatPiutangActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtPiutang billing=new DlgLhtPiutang(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLihatPiutangActionPerformed

    private void btnLaboratoriumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaboratoriumActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.periksalab.tampil();
        kasirralan.kamarinap.billing.periksalab.tampiltarif();
        kasirralan.kamarinap.billing.periksalab.isCek();
        kasirralan.kamarinap.billing.periksalab.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.periksalab.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.periksalab.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnLaboratoriumActionPerformed

    private void btnRalanMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRalanMasukActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtBiayaRalan billing=new DlgLhtBiayaRalan(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRalanMasukActionPerformed

    private void btnSetupAplikasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupAplikasiActionPerformed
        var.setStatus(false);
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetAplikasi aplikasi=new DlgSetAplikasi(this,false);
        aplikasi.emptTeks();
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupAplikasiActionPerformed

    private void btnSetOtoRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetOtoRalanActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetOtoRalan aplikasi=new DlgSetOtoRalan(this,false);
        aplikasi.emptTeks();
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetOtoRalanActionPerformed

    private void btnRanapMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRanapMasukActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgLhtBiayaRanap billing=new DlgLhtBiayaRanap(this,false);
        billing.tampil();
        billing.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        billing.setLocationRelativeTo(PanelUtama);
        billing.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRanapMasukActionPerformed

    private void btnProdusenInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdusenInventarisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgProdusenInventaris produsen=new DlgProdusenInventaris(this,false);
        produsen.tampil();
        produsen.emptTeks();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnProdusenInventarisActionPerformed

    private void btnSetBiayaMasukSekaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetBiayaMasukSekaliActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBiayaSekaliMasuk biayaharian=new DlgBiayaSekaliMasuk(this,false);
        biayaharian.tampil();
        biayaharian.emptTeks();
        biayaharian.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        biayaharian.setLocationRelativeTo(PanelUtama);
        biayaharian.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetBiayaMasukSekaliActionPerformed

    private void btnPaketOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaketOperasiActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPaketOperasi produsen=new DlgPaketOperasi(this,false);
        produsen.emptTeks();
        produsen.tampil();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnPaketOperasiActionPerformed

    private void btnTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanOperasiActionPerformed
        var.setStatus(false);

        DlgTagihanOperasi produsen=new DlgTagihanOperasi(this,false);
        //produsen.emptTeks();
        produsen.tampil();
        produsen.tampil2();
        produsen.isCek();
        produsen.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        produsen.setLocationRelativeTo(PanelUtama);
        produsen.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanOperasiActionPerformed

    private void BtnJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJadwalActionPerformed
        var.setStatus(false);

        DlgJadwal jadwal=new DlgJadwal(this,false);
        jadwal.emptTeks();
        jadwal.tampil();
        jadwal.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        jadwal.setLocationRelativeTo(PanelUtama);
        jadwal.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnJadwalActionPerformed

    private void btnMerkInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMerkInventarisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgMerkInventaris ktginventaris=new DlgMerkInventaris(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnMerkInventarisActionPerformed

    private void btnRuangInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuangInventarisActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRuangInventaris ktginventaris=new DlgRuangInventaris(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRuangInventarisActionPerformed

    private void btnBarangInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBarangInventarisActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgBarangInventaris ktginventaris=new DlgBarangInventaris(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnBarangInventarisActionPerformed

    private void btnInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarisActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgInventaris ktginventaris=new DlgInventaris(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnInventarisActionPerformed

    private void btnSirkulasiInventarisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSirkulasiInventarisActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSirkulasi ktginventaris=new DlgSirkulasi(this,false);
        ktginventaris.tampil();
        ktginventaris.isCek();
        ktginventaris.emptTeks();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSirkulasiInventarisActionPerformed

    private void btnFrekuensiRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRalanActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalan ktginventaris=new DlgFrekuensiPenyakitRalan(this,false);
        ktginventaris.prosesCari();
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRalanActionPerformed

    private void btnFrekuensiRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFrekuensiRanapActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRanap ktginventaris=new DlgFrekuensiPenyakitRanap(this,false);
        ktginventaris.prosesCari();
        ktginventaris.isCek();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnFrekuensiRanapActionPerformed

    private void btnSetupOtoLokasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupOtoLokasiActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetOtoLokasi ktginventaris=new DlgSetOtoLokasi(this,false);
        ktginventaris.tampil();
        ktginventaris.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        ktginventaris.setLocationRelativeTo(PanelUtama);
        ktginventaris.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupOtoLokasiActionPerformed

    private void btnTagihanPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTagihanPoliActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgRBTindakanPoli rbpoli=new DlgRBTindakanPoli(this,false);
        rbpoli.prosesCari();
        rbpoli.isCek();
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTagihanPoliActionPerformed

    private void btnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRujukMasukActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.rujukmasuk.tampil();
        kasirralan.kamarinap.rujukmasuk.emptTeks();
        kasirralan.kamarinap.rujukmasuk.isCek();
        kasirralan.kamarinap.rujukmasuk.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.rujukmasuk.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.rujukmasuk.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnRujukMasukActionPerformed

    private void btnTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrackerActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenelusuranLogin rbpoli=new DlgPenelusuranLogin(this,false);
        rbpoli.tampil();
        rbpoli.isCek();
        rbpoli.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        rbpoli.setLocationRelativeTo(PanelUtama);
        rbpoli.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTrackerActionPerformed

    private void btnTindakanRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTindakanRanapActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.tampil();
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.emptTeks();
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.isCek();
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setLocationRelativeTo(PanelUtama);
        kasirralan.kamarinap.billing.rawatinap.perawatan.perawatan.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTindakanRanapActionPerformed

    private void btnSetupJamInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetupJamInapActionPerformed
        var.setStatus(false);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetJam form=new DlgSetJam(this,false);
        form.tampil();
        form.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        form.setLocationRelativeTo(PanelUtama);
        form.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetupJamInapActionPerformed

    private void btnStokObatPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStokObatPasienActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgStokPasien opname=new DlgStokPasien(this,false);
        opname.tampil();
        opname.isCek();
        opname.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        opname.setLocationRelativeTo(PanelUtama);
        opname.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnStokObatPasienActionPerformed

    private void btnTarifLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTarifLabActionPerformed
        var.setStatus(false);

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgJnsPerawatanLab tariflab=new DlgJnsPerawatanLab(this,false);
        tariflab.tampil();
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(PanelUtama.getWidth(),PanelUtama.getHeight());
        tariflab.setLocationRelativeTo(PanelUtama);
        tariflab.setVisible(true);
        DlgHome.dispose();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTarifLabActionPerformed

    private void btnSetPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSetPenjabActionPerformed
        var.setStatus(false);
        DlgHome.dispose();
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgSetPenjabLab aplikasi=new DlgSetPenjabLab(this,false);
        aplikasi.emptTeks();
        aplikasi.tampil();
        aplikasi.setSize(PanelUtama.getWidth(), PanelUtama.getHeight());
        aplikasi.setLocationRelativeTo(PanelUtama);
        aplikasi.setVisible(true);

        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnSetPenjabActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new frmUtama().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCancel;
    private widget.ButtonBig BtnClose;
    private widget.Button BtnClosePass;
    private widget.ButtonBig BtnJadwal;
    private widget.ButtonBig BtnLog;
    private widget.Button BtnLogin;
    private widget.ButtonBig BtnMenu;
    private widget.Button BtnSimpanPass;
    private widget.ButtonBig BtnToolPasien;
    private widget.ButtonBig BtnToolRalan;
    private widget.ButtonBig BtnToolRanap;
    private widget.ButtonBig BtnToolReg;
    private javax.swing.JDialog DlgHome;
    private javax.swing.JDialog DlgLogin;
    private widget.MenuBar MenuBar;
    private javax.swing.JMenuItem MenuKeluar;
    private javax.swing.JMenuItem MnGantiPassword;
    private javax.swing.JMenuItem MnLogin;
    private javax.swing.JPanel PanelUtama;
    private usu.widget.glass.PanelGlass PanelWall;
    private widget.panelGlass Panelmenu;
    private widget.TextBox PassBaru2;
    private widget.TextBox PassLama;
    private widget.TextBox Passbaru1;
    private javax.swing.JDialog WindowInput;
    private widget.ButtonBig btnAdmin;
    private widget.ButtonBig btnAnalisaKamar;
    private widget.ButtonBig btnBarangInventaris;
    private widget.ButtonBig btnBarcode;
    private widget.ButtonBig btnBayarPiutang;
    private widget.ButtonBig btnBeriObat;
    private widget.ButtonBig btnBubes;
    private widget.ButtonBig btnCashFlow;
    private widget.ButtonBig btnDiet;
    private widget.ButtonBig btnDisplay;
    private widget.ButtonBig btnDokter;
    private widget.ButtonBig btnFrekuensiRalan;
    private widget.ButtonBig btnFrekuensiRanap;
    private widget.ButtonBig btnICD;
    private widget.ButtonBig btnInventaris;
    private widget.ButtonBig btnJenisInventaris;
    private widget.ButtonBig btnJnsBarang;
    private widget.ButtonBig btnKamar;
    private widget.ButtonBig btnKamarInap;
    private widget.ButtonBig btnKasir;
    private widget.ButtonBig btnKategoriInventaris;
    private widget.ButtonBig btnKeuntungan;
    private widget.ButtonBig btnKonversi;
    private widget.ButtonBig btnLabaRugi;
    private widget.ButtonBig btnLaboratorium;
    private widget.ButtonBig btnLahir;
    private widget.ButtonBig btnLihatPiutang;
    private widget.ButtonBig btnMerkInventaris;
    private widget.ButtonBig btnObat;
    private widget.ButtonBig btnObatPenyakit;
    private widget.ButtonBig btnOpname;
    private widget.ButtonBig btnPaketOperasi;
    private widget.ButtonBig btnPasien;
    private widget.ButtonBig btnPasienMati;
    private widget.ButtonBig btnPegawai;
    private widget.ButtonBig btnPembelian;
    private widget.ButtonBig btnPenjualan;
    private widget.ButtonBig btnPiutang;
    private widget.ButtonBig btnPostingJurnal;
    private widget.ButtonBig btnProdusenInventaris;
    private widget.ButtonBig btnRBDokter;
    private widget.ButtonBig btnRBParamedis;
    private widget.ButtonBig btnRHDOkter;
    private widget.ButtonBig btnRHParamedis;
    private widget.ButtonBig btnRalan;
    private widget.ButtonBig btnRalanMasuk;
    private widget.ButtonBig btnRanap;
    private widget.ButtonBig btnRanapMasuk;
    private widget.ButtonBig btnRegistrasi;
    private widget.ButtonBig btnRekening;
    private widget.ButtonBig btnRekeningTahun;
    private widget.ButtonBig btnResepObat;
    private widget.ButtonBig btnResume;
    private widget.ButtonBig btnReturBeli;
    private widget.ButtonBig btnReturJual;
    private widget.ButtonBig btnReturPiutang;
    private widget.ButtonBig btnRuangInventaris;
    private widget.ButtonBig btnRujukMasuk;
    private widget.ButtonBig btnRujukPasien;
    private widget.ButtonBig btnSatuan;
    private widget.ButtonBig btnSetBiayaHarian;
    private widget.ButtonBig btnSetBiayaMasukSekali;
    private widget.ButtonBig btnSetOtoRalan;
    private widget.ButtonBig btnSetPenjab;
    private widget.ButtonBig btnSetupAplikasi;
    private widget.ButtonBig btnSetupHarga;
    private widget.ButtonBig btnSetupJamInap;
    private widget.ButtonBig btnSetupOtoLokasi;
    private widget.ButtonBig btnSirkulasi;
    private widget.ButtonBig btnSirkulasiInventaris;
    private widget.ButtonBig btnStokObatPasien;
    private widget.ButtonBig btnSuplier;
    private widget.ButtonBig btnTagihanMasuk;
    private widget.ButtonBig btnTagihanOperasi;
    private widget.ButtonBig btnTagihanPoli;
    private widget.ButtonBig btnTarifLab;
    private widget.ButtonBig btnTindakanRalan;
    private widget.ButtonBig btnTindakanRanap;
    private widget.ButtonBig btnTracker;
    private widget.ButtonBig btnUser;
    private widget.ButtonBig btnVakum;
    private widget.Button button1;
    private widget.ComboBox cmbMenu;
    private widget.PasswordBox edAdmin;
    private widget.PasswordBox edPwd;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private javax.swing.JLabel jLabel11;
    private widget.Label jLabel12;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private widget.Label label35;
    private widget.Label label36;
    private javax.swing.JLabel lblStts;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblUser;
    private usu.widget.glass.PanelGlass panelGlass1;
    private usu.widget.glass.PanelGlass panelGlass3;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane1;
    // End of variables declaration//GEN-END:variables
    public void isMenu(){
        //btnUnit.setEnabled(var.getpoli());
        btnICD.setEnabled(var.getpenyakit());
        btnObat.setEnabled(var.getobat());
        btnObatPenyakit.setEnabled(var.getobat_penyakit());
        btnKamar.setEnabled(var.getkamar());
        btnTindakanRalan.setEnabled(var.gettindakan());
        btnTindakanRanap.setEnabled(var.gettindakan());
        btnTarifLab.setEnabled(var.gettindakan());
        btnDokter.setEnabled(var.getdokter());
        btnPegawai.setEnabled(var.getpegawai());
        btnPasien.setEnabled(var.getpasien());
        btnRegistrasi.setEnabled(var.getregistrasi());
        btnRalan.setEnabled(var.getralan());
        btnTagihanOperasi.setEnabled(var.gettagihan_operasi());
        btnPaketOperasi.setEnabled(var.getpaket_operasi());
        btnDiet.setEnabled(var.getdiet_pasien());
        btnLahir.setEnabled(var.getkelahiran_bayi());
        btnLaboratorium.setEnabled(var.getperiksa_lab());
        btnKamarInap.setEnabled(var.getkamin());
        btnRanap.setEnabled(var.getranap());
        btnResepObat.setEnabled(var.getresep());
        btnRujukPasien.setEnabled(var.getrujuk());
        btnBeriObat.setEnabled(var.getberiobat());
        btnPasienMati.setEnabled(var.getpasienmati());       
        btnKasir.setEnabled(var.getbilling());

        //modul apotek        
        btnSatuan.setEnabled(var.getsatuan());
        btnKonversi.setEnabled(var.getkonversisatuan());
        btnJnsBarang.setEnabled(var.getjenis());
        btnSuplier.setEnabled(var.getsuplier());
        btnPembelian.setEnabled(var.getpembelian());
        btnPenjualan.setEnabled(var.getpenjualan());
        btnPiutang.setEnabled(var.getpiutang());
        btnBayarPiutang.setEnabled(var.getbayar());
        btnReturBeli.setEnabled(var.getreturbeli());
        btnReturJual.setEnabled(var.getreturjual());
        btnReturPiutang.setEnabled(var.getreturpiutang());
        btnKeuntungan.setEnabled(var.getkeuntungan());
        btnSirkulasi.setEnabled(var.getproyeksi());
        btnOpname.setEnabled(var.getstockopname());
        
        //modul keuangan
        btnRekening.setEnabled(var.getrekening());
        btnRekeningTahun.setEnabled(var.getrekening_tahun());
        btnPostingJurnal.setEnabled(var.getjurnal());
        btnBubes.setEnabled(var.getbubes());
        btnCashFlow.setEnabled(var.getcashflow());
        btnLabaRugi.setEnabled(var.getkeuangan());
        
        //modul presensi
        
        btnAdmin.setEnabled(var.getadmin());
        btnUser.setEnabled(var.getuser());
        btnTracker.setEnabled(var.getuser());
        btnVakum.setEnabled(var.getvakum());
        btnSetupAplikasi.setEnabled(var.getaplikasi()); 
        btnDisplay.setEnabled(var.getdisplay());
        btnSetupHarga.setEnabled(var.getsetharga());
        btnSetBiayaHarian.setEnabled(var.getbiayaharian());
        btnSetBiayaMasukSekali.setEnabled(var.getmasuksekali());
        btnSetOtoRalan.setEnabled(var.getotoralan());
    }
    
    public void isTampil(){
        Panelmenu.removeAll();
        if(cmbMenu.getSelectedIndex()==0){
            //Panelmenu.add(btnUnit);
            if(var.getpenyakit()==true){
                Panelmenu.add(btnICD);
            }
            
            if(var.getobat()==true){
                Panelmenu.add(btnObat);
            }
            
            if(var.getobat_penyakit()==true){
                Panelmenu.add(btnObatPenyakit);
            }    
            
            if(var.getkamar()==true){
                Panelmenu.add(btnKamar);
            }
            
            if(var.gettindakan()==true){
                Panelmenu.add(btnTindakanRalan);
                Panelmenu.add(btnTindakanRanap);
                Panelmenu.add(btnTarifLab);
            }
            
            if(var.getpaket_operasi()==true){
                Panelmenu.add(btnPaketOperasi);
            }
            
            if(var.getdokter()==true){
                Panelmenu.add(btnDokter);
                Panelmenu.add(BtnJadwal);
            }
            
            if(var.getpegawai()==true){
                Panelmenu.add(btnPegawai);
            }
            
            if(var.getpasien()==true){
                Panelmenu.add(btnPasien);
            }
            
            if(var.getregistrasi()==true){
                Panelmenu.add(btnRegistrasi);
            }
            
            if(var.getralan()==true){
                Panelmenu.add(btnRalan); 
            }
            
            if(var.getkamin()==true){
                Panelmenu.add(btnKamarInap);
            }
            
            if(var.getranap()==true){
                Panelmenu.add(btnRanap);
            }
            
            if(var.gettagihan_operasi()==true){
                Panelmenu.add(btnTagihanOperasi);
            }
            
            if(var.getresep()==true){
                Panelmenu.add(btnResepObat);
            }
            
            if(var.getrujuk()==true){
                Panelmenu.add(btnRujukPasien);
            }
            
            if(var.getberiobat()==true){
                Panelmenu.add(btnBeriObat);
            }
            
            if(var.getpasienmati()==true){
                Panelmenu.add(btnPasienMati);
            }
            
            if(var.getdiet_pasien()==true){
                Panelmenu.add(btnDiet);
            }
            
            if(var.getkelahiran_bayi()==true){
                Panelmenu.add(btnLahir);
            }
            
            if(var.getperiksa_lab()==true){
                Panelmenu.add(btnLaboratorium);
            }
            
            /*if(var.getbilling_ralan()==true){
                Panelmenu.add(btnBilling);
            }
            
            if(var.getbilling_ranap()==true){
                 Panelmenu.add(btnBillingRanap);
            }*/
           
            if(var.getbilling()==true){
                Panelmenu.add(btnKasir);
            }
            
            if(var.getbayar()==true){
               Panelmenu.add(btnLihatPiutang); 
            }
            
            Panelmenu.setLayout(new GridLayout(0,7));
        }else if(cmbMenu.getSelectedIndex()==1){ 
            if(var.getpegawai()==true){
                Panelmenu.add(btnPegawai);
            }
            Panelmenu.add(btnBarcode);
        }else if(cmbMenu.getSelectedIndex()==2){ 
            if(var.getpegawai()==true){
                Panelmenu.add(btnPegawai);
            }
            
            if(var.getsuplier()==true){
                Panelmenu.add(btnSuplier); 
            }
            
            if(var.getpasien()==true){
                Panelmenu.add(btnPasien); 
            }
            
            if(var.getsatuan()==true){
                Panelmenu.add(btnSatuan); 
            }
            
            if(var.getkonversisatuan()==true){
                Panelmenu.add(btnKonversi); 
            }    
             
            if(var.getjenis()==true){
                Panelmenu.add(btnJnsBarang);
            }
            
            if(var.getobat()==true){
                Panelmenu.add(btnObat);
            }
            
            if(var.getstockopname()==true){
                Panelmenu.add(btnOpname); 
                Panelmenu.add(btnStokObatPasien); 
            }
            
            if(var.getpembelian()==true){
                Panelmenu.add(btnPembelian);
            }
            
            if(var.getpenjualan()==true){
                Panelmenu.add(btnPenjualan);
            }
            
            if(var.getpiutang()==true){
                 Panelmenu.add(btnPiutang);   
            }
            
            if(var.getbayar()==true){
                Panelmenu.add(btnLihatPiutang);
            }
            
            if(var.getreturbeli()==true){
                Panelmenu.add(btnReturBeli); 
            }
            
            if(var.getreturjual()==true){
                Panelmenu.add(btnReturJual); 
            }
               
            if(var.getreturpiutang()==true){
                Panelmenu.add(btnReturPiutang); 
            }   
             
            if(var.getkeuntungan()==true){
                Panelmenu.add(btnKeuntungan);
            }
            
            if(var.getproyeksi()==true){
                Panelmenu.add(btnSirkulasi);
            }
                     
            Panelmenu.setLayout(new GridLayout(0,7));
        }else if(cmbMenu.getSelectedIndex()==4){ 
            Panelmenu.add(btnJenisInventaris); 
            Panelmenu.add(btnKategoriInventaris); 
            Panelmenu.add(btnMerkInventaris); 
            Panelmenu.add(btnRuangInventaris); 
            Panelmenu.add(btnProdusenInventaris);  
            Panelmenu.add(btnBarangInventaris);  
            Panelmenu.add(btnInventaris);  
            Panelmenu.add(btnPegawai);      
            Panelmenu.add(btnSirkulasiInventaris);   
            Panelmenu.setLayout(new GridLayout(0,4));
        }else if(cmbMenu.getSelectedIndex()==6){ 
            Panelmenu.add(btnAnalisaKamar);
            Panelmenu.add(btnTagihanPoli);    
            Panelmenu.add(btnRHDOkter);  
            Panelmenu.add(btnRBDokter);  
            Panelmenu.add(btnRHParamedis);  
            Panelmenu.add(btnRBParamedis); 
            Panelmenu.add(btnTagihanMasuk); 
            Panelmenu.add(btnRalanMasuk);   
            Panelmenu.add(btnRanapMasuk);   
            Panelmenu.add(btnLihatPiutang);    
            Panelmenu.add(btnResume);     
            Panelmenu.add(btnFrekuensiRalan);  
            Panelmenu.add(btnFrekuensiRanap);  
            Panelmenu.add(btnRujukMasuk);  
            Panelmenu.setLayout(new GridLayout(0,4));
        }else if(cmbMenu.getSelectedIndex()==7){ 
            Panelmenu.add(btnRekening);      
            Panelmenu.add(btnRekeningTahun);  
            Panelmenu.add(btnTagihanMasuk);          
            Panelmenu.add(btnPostingJurnal);   
            Panelmenu.add(btnBubes);       
            Panelmenu.add(btnCashFlow);   
            Panelmenu.add(btnLabaRugi);       
            Panelmenu.setLayout(new GridLayout(0,4));
        }else if(cmbMenu.getSelectedIndex()==8){   
            Panelmenu.add(btnSetupAplikasi);       
            Panelmenu.add(btnAdmin);
            Panelmenu.add(btnUser);
            Panelmenu.add(btnTracker);
            Panelmenu.add(btnVakum);  
            Panelmenu.add(btnDisplay);  
            Panelmenu.add(btnSetPenjab);
            Panelmenu.add(btnSetupHarga);
            Panelmenu.add(btnSetBiayaHarian);
            Panelmenu.add(btnSetBiayaMasukSekali);
            Panelmenu.add(btnSetOtoRalan);
            Panelmenu.add(btnSetupOtoLokasi);
            Panelmenu.add(btnSetupJamInap);
            Panelmenu.setLayout(new GridLayout(0,4));
        }  
        Panelmenu.repaint();
        DlgHome.setVisible(true);
    }
    
    public void isWall(){
        try{            
            rs=ps.executeQuery();
            while(rs.next()){
                jLabel8.setText(rs.getString(1));
                jLabel11.setText(rs.getString(2) +", "+rs.getString(3) +", "+rs.getString(4) +" ");
                if(rs.getString(5).equals("Yes")){
                    Blob blob = rs.getBlob(6);
                    PanelWall.setBackgroundImage(new javax.swing.ImageIcon(blob.getBytes(1, (int) (blob.length()))));
                    repaint();
                }
            }
        }catch(SQLException e){
            System.out.println("Error : Silahkan Set Aplikasi "+e);
        }
    }

    private void isTutup() {
        Window[] wins = Window.getWindows();
        for (Window win : wins) {
            if (win instanceof JDialog) {
                win.setVisible(false);
                win.dispose();
                //wins[i]=null;
            }
        }

    }

}
