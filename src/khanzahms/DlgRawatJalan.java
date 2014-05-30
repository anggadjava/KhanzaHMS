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

import keuangan.DlgBilingRalan;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import khanzahms.DlgPasien;

/**
 *
 * @author dosen
 */
public final class DlgRawatJalan extends javax.swing.JDialog {
    private final DefaultTableModel tabModeDr;
    private final DefaultTableModel tabModePr;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private DlgPasien pasien=new DlgPasien(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs;

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public DlgRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"No.Rawat",
                    "No.Rekam Medis",
                    "Nama Pasien",
                    "ICD/Penyakit",
                    "Perawatan/Tindakan",
                    "Kode Dokter",
                    "Dokter Yg Menangani",
                    "Suhu Tubuh",
                    "Tensi",
                    "Hasil Pemeriksaan",
                    "Perkembangan",
                    "Biaya",
                    "Tgl.Rawat"};
        tabModeDr=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRawatDr.setModel(tabModeDr);
        //tampilDr();

        tbRawatDr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatDr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbRawatDr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }
        }
        tbRawatDr.setDefaultRenderer(Object.class, new WarnaTable());

        Object[] row2={"No.Rawat",
                    "No.Rekam Medis",
                    "Nama Pasien",
                    "ICD/Penyakit",
                    "Perawatan/Tindakan",
                    "NIP",
                    "Perawat Yg Menangani",
                    "Suhu Tubuh",
                    "Tensi",
                    "Hasil Pemeriksaan",
                    "Perkembangan",
                    "Biaya",
                    "Tgl.Rawat"};
        tabModePr=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRawatPr.setModel(tabModePr);
        //tampilPr();

        tbRawatPr.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbRawatPr.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 13; i++) {
            TableColumn column = tbRawatPr.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(200);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }
        }
        tbRawatPr.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPrw.setDocument(new batasInput((byte)8).getKata(TKdPrw));
        TSuhu.setDocument(new batasInput((byte)5).getKata(TSuhu));
        TTensi.setDocument(new batasInput((byte)7).getKata(TTensi));
        THasil.setDocument(new batasInput((byte)100).getKata(THasil));
        TKdPny.setDocument(new batasInput((byte)10).getKata(TKdPny));
        TPrkmbngn.setDocument(new batasInput((byte)100).getKata(TPrkmbngn));        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
                
        perawatan.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdPrw.setText(perawatan.getTextField().getText());   
                isJns();
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
                kdptg.setText(petugas.getTextField().getText());   
                Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,petugas.getTextField().getText());
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
        ChkInput.setSelected(false);
        //isForm(); 
        try{
            ps=koneksi.prepareStatement("select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "penyakit.nm_penyakit,concat(rawat_jl_dr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.suhu_tubuh,"+
                   "rawat_jl_dr.tensi,rawat_jl_dr.hasil,rawat_jl_dr.perkembangan,rawat_jl_dr.biaya_rawat,reg_periksa.tgl_registrasi "+
                   "from pasien inner join penyakit inner join reg_periksa "+
                   "inner join jns_perawatan inner join dokter inner join rawat_jl_dr "+
                   "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                   "and penyakit.kd_penyakit=rawat_jl_dr.kd_penyakit "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                   "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.no_rawat like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and penyakit.nm_penyakit like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.suhu_tubuh like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.tensi like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.hasil like ? or "+
                    " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.perkembangan like ? "+
                   "order by rawat_jl_dr.no_rawat desc");
              
            ps2=koneksi.prepareStatement("select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "penyakit.nm_penyakit,concat(rawat_jl_pr.kd_jenis_prw,' ',jns_perawatan.nm_perawatan),rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.suhu_tubuh,"+
                   "rawat_jl_pr.tensi,rawat_jl_pr.hasil,rawat_jl_pr.perkembangan,rawat_jl_pr.biaya_rawat,reg_periksa.tgl_registrasi "+
                   "from pasien inner join penyakit inner join reg_periksa "+
                   "inner join jns_perawatan inner join petugas inner join rawat_jl_pr "+
                   "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                   "and penyakit.kd_penyakit=rawat_jl_pr.kd_penyakit "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                   "and rawat_jl_pr.nip=petugas.nip "+
                   "where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.no_rawat like ? or "+
                   "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                   "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and penyakit.nm_penyakit like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and jns_perawatan.nm_perawatan like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.nip like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and petugas.nama like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.suhu_tubuh like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.tensi like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.hasil like ? or "+
                   " reg_periksa.tgl_registrasi between ? and ? and rawat_jl_pr.perkembangan like ? "+
                   "order by rawat_jl_pr.no_rawat desc");
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public  DlgCariPerawatanRalan perawatan=new DlgCariPerawatanRalan(null,false);
    private DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TBiayaPr = new javax.swing.JTextField();
        TBiayaDr = new javax.swing.JTextField();
        internalFrame1 = new widget.InternalFrame();
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
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbRawatDr = new widget.Table();
        panelGlass7 = new widget.panelisi();
        jLabel5 = new widget.Label();
        KdDok = new widget.TextBox();
        BtnSeekDokter = new widget.Button();
        TDokter = new widget.TextBox();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        tbRawatPr = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel13 = new widget.Label();
        kdptg = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        TPerawat = new widget.TextBox();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TSuhu = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        TTensi = new widget.TextBox();
        jLabel11 = new widget.Label();
        BtnSeek1 = new widget.Button();
        jLabel7 = new widget.Label();
        TKdPrw = new widget.TextBox();
        TNmPrw = new widget.TextBox();
        THasil = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        TPrkmbngn = new widget.TextBox();
        jLabel12 = new widget.Label();
        TKdPny = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        TNmPny = new widget.TextBox();
        jLabel15 = new widget.Label();
        TglRawat = new widget.TextBox();

        TBiayaPr.setEditable(false);
        TBiayaPr.setText("0");
        TBiayaPr.setName("TBiayaPr"); // NOI18N

        TBiayaDr.setEditable(false);
        TBiayaDr.setText("0");
        TBiayaDr.setName("TBiayaDr"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perawatan/Tindakan Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(380, 23));
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

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(50, 70, 40));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbRawatDr.setAutoCreateRowSorter(true);
        tbRawatDr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatDr.setName("tbRawatDr"); // NOI18N
        tbRawatDr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatDrMouseClicked(evt);
            }
        });
        tbRawatDr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatDrKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbRawatDr);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(null);

        jLabel5.setText("Dokter :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass7.add(jLabel5);
        jLabel5.setBounds(0, 10, 70, 23);

        KdDok.setHighlighter(null);
        KdDok.setName("KdDok"); // NOI18N
        KdDok.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDokKeyPressed(evt);
            }
        });
        panelGlass7.add(KdDok);
        KdDok.setBounds(74, 10, 130, 23);

        BtnSeekDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekDokter.setMnemonic('4');
        BtnSeekDokter.setToolTipText("ALt+4");
        BtnSeekDokter.setName("BtnSeekDokter"); // NOI18N
        BtnSeekDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekDokterActionPerformed(evt);
            }
        });
        panelGlass7.add(BtnSeekDokter);
        BtnSeekDokter.setBounds(728, 10, 28, 23);

        TDokter.setEditable(false);
        TDokter.setHighlighter(null);
        TDokter.setName("TDokter"); // NOI18N
        panelGlass7.add(TDokter);
        TDokter.setBounds(206, 10, 520, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Dokter :.", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setWarnaAtas(new java.awt.Color(217, 217, 217));
        internalFrame3.setWarnaBawah(new java.awt.Color(217, 217, 217));
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        tbRawatPr.setAutoCreateRowSorter(true);
        tbRawatPr.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbRawatPr.setName("tbRawatPr"); // NOI18N
        tbRawatPr.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbRawatPrMouseClicked(evt);
            }
        });
        tbRawatPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbRawatPrKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbRawatPr);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass10.setLayout(null);

        jLabel13.setText("Petugas :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass10.add(jLabel13);
        jLabel13.setBounds(0, 10, 70, 23);

        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelGlass10.add(kdptg);
        kdptg.setBounds(74, 10, 130, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelGlass10.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(728, 10, 28, 23);

        TPerawat.setEditable(false);
        TPerawat.setBackground(new java.awt.Color(202, 202, 202));
        TPerawat.setHighlighter(null);
        TPerawat.setName("TPerawat"); // NOI18N
        panelGlass10.add(TPerawat);
        TPerawat.setBounds(206, 10, 520, 23);

        internalFrame3.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab(".: Penanganan Petugas :.", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(260, 137));
        FormInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 95, 23);

        jLabel4.setText("Tensi :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(719, 72, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(98, 12, 153, 23);

        TSuhu.setFocusTraversalPolicyProvider(true);
        TSuhu.setName("TSuhu"); // NOI18N
        TSuhu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSuhuKeyPressed(evt);
            }
        });
        FormInput.add(TSuhu);
        TSuhu.setBounds(673, 72, 70, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(253, 12, 140, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(395, 12, 477, 23);

        TTensi.setHighlighter(null);
        TTensi.setName("TTensi"); // NOI18N
        TTensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTensiKeyPressed(evt);
            }
        });
        FormInput.add(TTensi);
        TTensi.setBounds(802, 72, 70, 23);

        jLabel11.setText("Tndkn/Tghan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 72, 95, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('3');
        BtnSeek1.setToolTipText("Alt+3");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(532, 72, 28, 23);

        jLabel7.setText("Suhu Badan :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(580, 72, 90, 23);

        TKdPrw.setName("TKdPrw"); // NOI18N
        TKdPrw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPrwKeyPressed(evt);
            }
        });
        FormInput.add(TKdPrw);
        TKdPrw.setBounds(98, 72, 100, 23);

        TNmPrw.setEditable(false);
        TNmPrw.setName("TNmPrw"); // NOI18N
        FormInput.add(TNmPrw);
        TNmPrw.setBounds(200, 72, 330, 23);

        THasil.setHighlighter(null);
        THasil.setName("THasil"); // NOI18N
        THasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THasilKeyPressed(evt);
            }
        });
        FormInput.add(THasil);
        THasil.setBounds(98, 102, 280, 23);

        jLabel8.setText("Hasil Periksa :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(null);
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 102, 95, 23);

        jLabel9.setText("Perkembangan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(409, 102, 110, 23);

        TPrkmbngn.setHighlighter(null);
        TPrkmbngn.setName("TPrkmbngn"); // NOI18N
        TPrkmbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPrkmbngnKeyPressed(evt);
            }
        });
        FormInput.add(TPrkmbngn);
        TPrkmbngn.setBounds(522, 102, 350, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 42, 95, 23);

        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(98, 42, 100, 23);

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
        BtnSeek2.setBounds(532, 42, 28, 23);

        TNmPny.setEditable(false);
        TNmPny.setName("TNmPny"); // NOI18N
        FormInput.add(TNmPny);
        TNmPny.setBounds(200, 42, 330, 23);

        jLabel15.setText("Tgl.Rawat :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(609, 42, 120, 23);

        TglRawat.setFocusTraversalPolicyProvider(true);
        TglRawat.setName("TglRawat"); // NOI18N
        TglRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRawatKeyPressed(evt);
            }
        });
        FormInput.add(TglRawat);
        TglRawat.setBounds(732, 42, 140, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,TKdPrw);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TSuhuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSuhuKeyPressed
        Valid.pindah(evt,THasil,TTensi);
}//GEN-LAST:event_TSuhuKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
            Valid.textKosong(TKdPrw,"perawatan");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(KdDok,"Dokter");
        }else if(TBiayaDr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan dokter..!!!");
            KdDok.requestFocus();
        }else if(TBiayaPr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan perawat/paramedis..!!!");
            kdptg.requestFocus();
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Sequel.menyimpan("rawat_jl_dr","'"+TNoRw.getText()+"','"+
                            TKdPny.getText()+"','"+
                            TKdPrw.getText()+"','"+
                            KdDok.getText()+"','"+
                            TSuhu.getText()+"','"+
                            TTensi.getText()+"','"+
                            THasil.getText()+"','"+
                            TPrkmbngn.getText()+"','"+
                            TBiayaDr.getText()+"'","No.Rawat, Jenis Perawatan dan Dokter");
                tampilDr();
            }else if(TabRawat.getSelectedIndex()==1){
                Sequel.menyimpan("rawat_jl_pr","'"+TNoRw.getText()+"','"+
                            TKdPny.getText()+"','"+
                            TKdPrw.getText()+"','"+
                            kdptg.getText()+"','"+
                            TSuhu.getText()+"','"+
                            TTensi.getText()+"','"+
                            THasil.getText()+"','"+
                            TPrkmbngn.getText()+"','"+
                            TBiayaPr.getText()+"'","No.Rawat, Jenis Perawatan, dan Petugas");
                tampilPr();
            }
            
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,KdDok,BtnBatal);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,kdptg,BtnBatal);
            }
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
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                try{
                    Sequel.queryu("delete from rawat_jl_dr where no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+TKdPrw.getText()+"' and kd_dokter='"+KdDok.getText()+"' and kd_penyakit='"+TKdPny.getText()+"'");
                    tampilDr(); 
                }catch(Exception e){
                    //System.out.println("Pesan Error : "+e);
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
            }               
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TNoRw.requestFocus();
            }else if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
            }else if(!(TPasien.getText().trim().equals(""))){
                try{
                    Sequel.queryu("delete from rawat_jl_pr where no_rawat='"+TNoRw.getText()+"' and kd_jenis_prw='"+TKdPrw.getText()+"' and nip='"+kdptg.getText()+"' and kd_penyakit='"+TKdPny.getText()+"'");
                    tampilPr();
                }catch(Exception e){
                    //System.out.println("Pesan Error : "+e);
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(TabRawat.getSelectedIndex()==0){
            if(tabModeDr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModeDr.getRowCount()!=0){
                    String tgl=" reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
                    Valid.MyReport("rptJlnDr.jrxml","report","::[ Data Rawat Jalan Yang Ditangani Dokter ]::",
                           "select rawat_jl_dr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "penyakit.nm_penyakit,jns_perawatan.nm_perawatan,rawat_jl_dr.kd_dokter,dokter.nm_dokter,rawat_jl_dr.suhu_tubuh,"+
                           "rawat_jl_dr.tensi,rawat_jl_dr.hasil,rawat_jl_dr.perkembangan,rawat_jl_dr.biaya_rawat,reg_periksa.tgl_registrasi "+
                           "from pasien inner join penyakit inner join reg_periksa "+
                           "inner join jns_perawatan inner join dokter inner join rawat_jl_dr "+
                           "on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                           "and penyakit.kd_penyakit=rawat_jl_dr.kd_penyakit "+
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "and rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "and rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                           "where "+tgl+"and rawat_jl_dr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.suhu_tubuh like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.tensi like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.hasil like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_dr.perkembangan like '%"+TCari.getText().trim()+"%' "+
                           "order by rawat_jl_dr.no_rawat desc");
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if(tabModePr.getRowCount()==0){
                 JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                 BtnBatal.requestFocus();
            }else if(tabModePr.getRowCount()!=0){
                String tgl=" reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
                    Valid.MyReport("rptJlnPr.jrxml","report","::[ Data Rawat Jalan Yang Ditangani Perawat ]::",
                           "select rawat_jl_pr.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                           "penyakit.nm_penyakit,jns_perawatan.nm_perawatan,rawat_jl_pr.nip,petugas.nama,rawat_jl_pr.suhu_tubuh,"+
                           "rawat_jl_pr.tensi,rawat_jl_pr.hasil,rawat_jl_pr.perkembangan,rawat_jl_pr.biaya_rawat,reg_periksa.tgl_registrasi "+
                           "from pasien inner join penyakit inner join reg_periksa "+
                           "inner join jns_perawatan inner join petugas inner join rawat_jl_pr "+
                           "on rawat_jl_pr.no_rawat=reg_periksa.no_rawat "+
                           "and penyakit.kd_penyakit=rawat_jl_pr.kd_penyakit "+
                           "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                           "and rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                           "and rawat_jl_pr.nip=petugas.nip "+
                           "where "+tgl+"and rawat_jl_pr.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and jns_perawatan.nm_perawatan like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.nip like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.suhu_tubuh like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.tensi like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.hasil like '%"+TCari.getText().trim()+"%' or "+
                            tgl+"and rawat_jl_pr.perkembangan like '%"+TCari.getText().trim()+"%' "+
                           "order by rawat_jl_pr.no_rawat desc");
            }
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
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
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
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TTensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTensiKeyPressed
        Valid.pindah(evt,TSuhu,TPrkmbngn);
    }//GEN-LAST:event_TTensiKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampilDr();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilPr();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbRawatDrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatDrMouseClicked
        if(tabModeDr.getRowCount()!=0){
            try {
                getDataDr();
            } catch (java.lang.NullPointerException e) {
            }
            
            
        }
}//GEN-LAST:event_tbRawatDrMouseClicked

    private void tbRawatDrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatDrKeyPressed
        if(tabModeDr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataDr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
        }
}//GEN-LAST:event_tbRawatDrKeyPressed

    private void tbRawatPrMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbRawatPrMouseClicked
        if(tabModePr.getRowCount()!=0){
            try {
                getDataPr();
            } catch (java.lang.NullPointerException e) {
            }
            
        }
}//GEN-LAST:event_tbRawatPrMouseClicked

    private void tbRawatPrKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbRawatPrKeyPressed
        if(tabModePr.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataPr();
                } catch (java.lang.NullPointerException e) {
                }
            }
            
           
        }
}//GEN-LAST:event_tbRawatPrKeyPressed

private void KdDokKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDokKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
        }else{            
            Valid.pindah(evt,TPrkmbngn,BtnSimpan);
        }
}//GEN-LAST:event_KdDokKeyPressed

private void BtnSeekDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.tampil(" order by kd_dokter");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeekDokterActionPerformed

private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,kdptg.getText());
        }else{
            Valid.pindah(evt,TPrkmbngn,BtnSimpan);
        }
}//GEN-LAST:event_kdptgKeyPressed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void TKdPrwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPrwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isJns();
        }else{            
            Valid.pindah(evt,TKdPny,THasil);
        }
}//GEN-LAST:event_TKdPrwKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(TDokter,"Dokter");
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(TPerawat,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                perawatan.setNoRm(TNoRw.getText(),TKdPny.getText(),TNmPny.getText(),KdDok.getText(),TDokter.getText(),"rawat_jl_dr",TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText(),"","");                        
            }else if(TabRawat.getSelectedIndex()==1){
                perawatan.setNoRm(TNoRw.getText(),TKdPny.getText(),TNmPny.getText(),kdptg.getText(),TPerawat.getText(),"rawat_jl_pr",TSuhu.getText(),TTensi.getText(),THasil.getText(),TPrkmbngn.getText(),"","");                        
            }
            perawatan.emptTeks();
            perawatan.isCek();
            perawatan.tampil();
            perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            perawatan.setLocationRelativeTo(internalFrame1);
            perawatan.setVisible(true);
        }    
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,TKdPrw,THasil);
}//GEN-LAST:event_BtnSeek1KeyPressed

private void THasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THasilKeyPressed
        Valid.pindah(evt,TKdPrw,TSuhu);
}//GEN-LAST:event_THasilKeyPressed

private void TPrkmbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPrkmbngnKeyPressed
        if(TabRawat.getSelectedIndex()==0){
            Valid.pindah(evt,TTensi,KdDok);
        }else if(TabRawat.getSelectedIndex()==1){
            Valid.pindah(evt,TTensi,kdptg);
        }
}//GEN-LAST:event_TPrkmbngnKeyPressed

private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPny();
        }else{            
            Valid.pindah(evt,TNoRw,TKdPrw);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dlgpnykt.emptTeks();
        dlgpnykt.isCek();
        dlgpnykt.tampil();
        dlgpnykt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dlgpnykt.setLocationRelativeTo(internalFrame1);
        dlgpnykt.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        Valid.pindah(evt,TKdPny,TKdPrw);
}//GEN-LAST:event_BtnSeek2KeyPressed

private void TglRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRawatKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TglRawatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdPrw.getText().trim().equals("")||TNmPrw.getText().trim().equals("")){
            Valid.textKosong(TKdPrw,"perawatan");
        }else if(TDokter.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            Valid.textKosong(KdDok,"Dokter");
        }else if(TBiayaDr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==0)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan dokter..!!!");
            KdDok.requestFocus();
        }else if(TBiayaPr.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            JOptionPane.showMessageDialog(null,"Maaf, untuk tindakan perawat/paramedis..!!!");
            kdptg.requestFocus();
        }else if(TPerawat.getText().trim().equals("")&&(TabRawat.getSelectedIndex()==1)){
            Valid.textKosong(kdptg,"perawat");
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Sequel.mengedit("rawat_jl_dr","no_rawat='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),0)+
                        "' and kd_penyakit='"+Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString()+"' ")+
                        "' and kd_jenis_prw='"+Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString()+"' ")+
                        "' and kd_dokter='"+tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5)+"'",
                        "no_rawat='"+TNoRw.getText()+"',kd_penyakit='"+TKdPny.getText()+"',kd_jenis_prw='"+TKdPrw.getText()+
                        "',kd_dokter='"+KdDok.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+
                        "',hasil='"+THasil.getText()+"',perkembangan='"+TPrkmbngn.getText()+"',biaya_rawat='"+TBiayaDr.getText()+"'");
                tampilDr();
            }else if(TabRawat.getSelectedIndex()==1){
                Sequel.mengedit("rawat_jl_pr","no_rawat='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),0)+
                        "' and kd_penyakit='"+Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString()+"' ")+
                        "' and kd_jenis_prw='"+Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString()+"' ")+
                        "' and nip='"+tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5)+"'",
                        "no_rawat='"+TNoRw.getText()+"',kd_penyakit='"+TKdPny.getText()+"',kd_jenis_prw='"+TKdPrw.getText()+
                        "',nip='"+kdptg.getText()+"',suhu_tubuh='"+TSuhu.getText()+"',tensi='"+TTensi.getText()+
                        "',hasil='"+THasil.getText()+"',perkembangan='"+TPrkmbngn.getText()+"',biaya_rawat='"+TBiayaPr.getText()+"'");
                tampilPr();
            }
            
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRawatJalan dialog = new DlgRawatJalan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeekDokter;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdDok;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private javax.swing.JTextField TBiayaDr;
    private javax.swing.JTextField TBiayaPr;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THasil;
    private widget.TextBox TKdPny;
    private widget.TextBox TKdPrw;
    private widget.TextBox TNmPny;
    private widget.TextBox TNmPrw;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPerawat;
    private widget.TextBox TPrkmbngn;
    private widget.TextBox TSuhu;
    private widget.TextBox TTensi;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox TglRawat;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdptg;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbRawatDr;
    private widget.Table tbRawatPr;
    // End of variables declaration//GEN-END:variables

    public void tampilDr() {
        Valid.tabelKosong(tabModeDr);
        try{
            ps.setString(1,DTPCari1.getSelectedItem().toString());
            ps.setString(2,DTPCari2.getSelectedItem().toString());
            ps.setString(3,"%"+TCari.getText().trim()+"%");
            ps.setString(4,DTPCari1.getSelectedItem().toString());
            ps.setString(5,DTPCari2.getSelectedItem().toString());
            ps.setString(6,"%"+TCari.getText().trim()+"%");
            ps.setString(7,DTPCari1.getSelectedItem().toString());
            ps.setString(8,DTPCari2.getSelectedItem().toString());
            ps.setString(9,"%"+TCari.getText().trim()+"%");
            ps.setString(10,DTPCari1.getSelectedItem().toString());
            ps.setString(11,DTPCari2.getSelectedItem().toString());
            ps.setString(12,"%"+TCari.getText().trim()+"%");
            ps.setString(13,DTPCari1.getSelectedItem().toString());
            ps.setString(14,DTPCari2.getSelectedItem().toString());
            ps.setString(15,"%"+TCari.getText().trim()+"%");
            ps.setString(16,DTPCari1.getSelectedItem().toString());
            ps.setString(17,DTPCari2.getSelectedItem().toString());
            ps.setString(18,"%"+TCari.getText().trim()+"%");
            ps.setString(19,DTPCari1.getSelectedItem().toString());
            ps.setString(20,DTPCari2.getSelectedItem().toString());
            ps.setString(21,"%"+TCari.getText().trim()+"%");
            ps.setString(22,DTPCari1.getSelectedItem().toString());
            ps.setString(23,DTPCari2.getSelectedItem().toString());
            ps.setString(24,"%"+TCari.getText().trim()+"%");
            ps.setString(25,DTPCari1.getSelectedItem().toString());
            ps.setString(26,DTPCari2.getSelectedItem().toString());
            ps.setString(27,"%"+TCari.getText().trim()+"%");
            ps.setString(28,DTPCari1.getSelectedItem().toString());
            ps.setString(29,DTPCari2.getSelectedItem().toString());
            ps.setString(30,"%"+TCari.getText().trim()+"%");
            ps.setString(31,DTPCari1.getSelectedItem().toString());
            ps.setString(32,DTPCari2.getSelectedItem().toString());
            ps.setString(33,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               rs.getString(11),
                               Valid.SetAngka(rs.getDouble(12)),
                               rs.getString(13)};
                tabModeDr.addRow(data);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModeDr.getRowCount());
    }

    public void load(String param) {
        if(! param.equals("")){
            KdDok.setText(param);   
            KdDok.setEditable(false);
            BtnSeekDokter.setEnabled(false);
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,param);
            kdptg.setText(param);
            kdptg.setEditable(false);
            BtnSeekPetugas.setEnabled(false);
            Sequel.cariIsi("select nama from petugas where nip=?",TPerawat,param);
        }else if(param.equals("")){
            KdDok.setText("");   
            KdDok.setEditable(true);
            BtnSeekDokter.setEnabled(true);
            kdptg.setText("");
            kdptg.setEditable(true);
            BtnSeekPetugas.setEnabled(true);
        }        
    }

    public void emptTeks() {
        //TNoRw.setText("");
        TSuhu.setText("");
        TKdPrw.setText("");
        TNmPrw.setText("");
        TTensi.setText("");
        THasil.setText("");
        TPrkmbngn.setText("");
        TBiayaDr.setText("0");
        TBiayaPr.setText("0");
        TNoRw.requestFocus();  
    }

    private void isRawat(){
        Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=? ",TglRawat,TNoRw.getText());
    }

    private void isPsien(){
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }

    private void isJns(){
        Sequel.cariIsi("select nm_perawatan from jns_perawatan where kd_jenis_prw=? ",TNmPrw,TKdPrw.getText());
        Sequel.cariIsi("select total_byrdr from jns_perawatan where kd_jenis_prw=? ",TBiayaDr,TKdPrw.getText());
        Sequel.cariIsi("select total_byrpr from jns_perawatan where kd_jenis_prw=? ",TBiayaPr,TKdPrw.getText());
    }

    private void getDataDr() {
        if(tbRawatDr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),0).toString());
            TNoRM.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),1).toString());
            TPasien.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),2).toString());          
            TNmPny.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());       
            TNmPrw.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());
            KdDok.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),5).toString());
            TDokter.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),6).toString());
            TSuhu.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),7).toString());
            TTensi.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),8).toString());
            THasil.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),9).toString());
            TPrkmbngn.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),10).toString());
            TglRawat.setText(tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),12).toString());
            Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit=? ",TKdPny,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),3).toString());            
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());    
            Sequel.cariIsi("select nm_perawatan from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TNmPrw,tbRawatDr.getValueAt(tbRawatDr.getSelectedRow(),4).toString());    
        }
    }

    private void tampilPr() {        
        Valid.tabelKosong(tabModePr);
        try{          
            ps2.setString(1,DTPCari1.getSelectedItem().toString());
            ps2.setString(2,DTPCari2.getSelectedItem().toString());
            ps2.setString(3,"%"+TCari.getText().trim()+"%");
            ps2.setString(4,DTPCari1.getSelectedItem().toString());
            ps2.setString(5,DTPCari2.getSelectedItem().toString());
            ps2.setString(6,"%"+TCari.getText().trim()+"%");
            ps2.setString(7,DTPCari1.getSelectedItem().toString());
            ps2.setString(8,DTPCari2.getSelectedItem().toString());
            ps2.setString(9,"%"+TCari.getText().trim()+"%");
            ps2.setString(10,DTPCari1.getSelectedItem().toString());
            ps2.setString(11,DTPCari2.getSelectedItem().toString());
            ps2.setString(12,"%"+TCari.getText().trim()+"%");
            ps2.setString(13,DTPCari1.getSelectedItem().toString());
            ps2.setString(14,DTPCari2.getSelectedItem().toString());
            ps2.setString(15,"%"+TCari.getText().trim()+"%");
            ps2.setString(16,DTPCari1.getSelectedItem().toString());
            ps2.setString(17,DTPCari2.getSelectedItem().toString());
            ps2.setString(18,"%"+TCari.getText().trim()+"%");
            ps2.setString(19,DTPCari1.getSelectedItem().toString());
            ps2.setString(20,DTPCari2.getSelectedItem().toString());
            ps2.setString(21,"%"+TCari.getText().trim()+"%");
            ps2.setString(22,DTPCari1.getSelectedItem().toString());
            ps2.setString(23,DTPCari2.getSelectedItem().toString());
            ps2.setString(24,"%"+TCari.getText().trim()+"%");
            ps2.setString(25,DTPCari1.getSelectedItem().toString());
            ps2.setString(26,DTPCari2.getSelectedItem().toString());
            ps2.setString(27,"%"+TCari.getText().trim()+"%");
            ps2.setString(28,DTPCari1.getSelectedItem().toString());
            ps2.setString(29,DTPCari2.getSelectedItem().toString());
            ps2.setString(30,"%"+TCari.getText().trim()+"%");
            ps2.setString(31,DTPCari1.getSelectedItem().toString());
            ps2.setString(32,DTPCari2.getSelectedItem().toString());
            ps2.setString(33,"%"+TCari.getText().trim()+"%");
            rs=ps2.executeQuery();
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9),
                               rs.getString(10),
                               rs.getString(11),
                               Valid.SetAngka(rs.getDouble(12)),
                               rs.getString(13)};
                tabModePr.addRow(data);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabModePr.getRowCount());
    }

    private void getDataPr() {
        if(tbRawatPr.getSelectedRow()!= -1){
            TNoRw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),0).toString());
            TNoRM.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),1).toString());
            TPasien.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),2).toString());         
            TNmPny.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());     
            TNmPrw.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());
            kdptg.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),5).toString());
            TPerawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),6).toString());
            TSuhu.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),7).toString());
            TTensi.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),8).toString());
            THasil.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),9).toString());
            TPrkmbngn.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),10).toString());
            TglRawat.setText(tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),12).toString());
            Sequel.cariIsi("select kd_penyakit from penyakit where nm_penyakit=? ",TKdPny,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),3).toString());                    
            Sequel.cariIsi("select kd_jenis_prw from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TKdPrw,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());    
            Sequel.cariIsi("select nm_perawatan from jns_perawatan where concat(kd_jenis_prw,' ',nm_perawatan)=? ",TNmPrw,tbRawatPr.getValueAt(tbRawatPr.getSelectedRow(),4).toString());   
        }
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        KdDok.setText(Sequel.cariIsi("select kd_dokter from reg_periksa where no_rawat=?",norwt));
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        TKdPny.setText("-");
        TNmPny.setText("-");
        isRawat();
        isPsien();  
        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,KdDok.getText());
    }
    
    private void isPny() {
        Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",TNmPny,TKdPny.getText());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,158));
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
        BtnSimpan.setEnabled(var.getralan());
        BtnHapus.setEnabled(var.getralan());
        BtnEdit.setEnabled(var.getralan());
        BtnPrint.setEnabled(var.getralan());
        
    }



}
