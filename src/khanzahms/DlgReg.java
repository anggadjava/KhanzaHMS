/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgReg.java
 *
 * Created on Jun 8, 2010, choose Tools | Templates
 * and open the template in10:28:56 PM
 */

package khanzahms;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.grafikperiksaperagama;
import fungsi.grafikperiksaperbulan;
import fungsi.grafikperiksaperdokter;
import fungsi.grafikperiksaperhari;
import fungsi.grafikperiksaperjk;
import fungsi.grafikperiksaperpekerjaan;
import fungsi.grafikperiksaperpoli;
import fungsi.grafikperiksapertahun;
import fungsi.grafiksql;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgReg extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgPasien pasien=new DlgPasien(null,false);
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs;
    private int pilihan=0,i=0;
    private Date cal=new Date();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgReg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"No.Reg","No.Rawat","Tanggal","Jam","Kd.Dokter","Dokter Dituju","Nomer RM",
            "Pasien","J.K.","Umur","Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan dg P.J.",
            "Biaya Regristrasi","Status","Jenis Bayar"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPetugas.setModel(tabMode);

        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(60);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(60);   
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(50);
            }else if(i==9){
                column.setPreferredWidth(50);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(200);
            }else if(i==13){
                column.setPreferredWidth(100);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(150);
            }
        }
        tbPetugas.setDefaultRenderer(Object.class, new WarnaTable());

        TNoReg.setDocument(new batasInput((byte)8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TNoRM.setDocument(new batasInput((byte)10).getKata(TNoRM));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        kdpnj.setDocument(new batasInput((byte)3).getKata(kdpnj));
        TPngJwb.setDocument(new batasInput((byte)30).getKata(TPngJwb));
        kdpoli.setDocument(new batasInput((byte)5).getKata(kdpoli));
        THbngn.setDocument(new batasInput((byte)20).getKata(THbngn));
        TAlmt.setDocument(new batasInput((byte)60).getKata(TAlmt));
        TBiaya.setDocument(new batasInput((byte)13).getOnlyAngka(TBiaya));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        CrPoli.setDocument(new batasInput((byte)100).getKata(CrPoli));
        CrDokter.setDocument(new batasInput((byte)100).getKata(CrDokter));
        jam();                

        
        pasien.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TNoRM.setText(pasien.getTextField().getText());   
                isPas();               
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
                kddokter.setText(dokter.getTextField().getText());  
                isPtg();
                isNumber();             
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
                isPoli();
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
                isPetugas();
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
        
        pasien.kab.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
               Kabupaten2.setText(pasien.kab.getTextField().getText());  
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
        
        pasien.kec.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                Kecamatan2.setText(pasien.kec.getTextField().getText());                  
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
        
        pasien.kel.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {              
                    Kelurahan2.setText(pasien.kel.getTextField().getText());          
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
        
        pasien.penjab.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                kdpnj.setText(pasien.penjab.getTextField().getText());
                Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
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
        isForm(); 
        try{
           ps=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_reg like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.stts_daftar like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? or "+
                " poliklinik.nm_poli like ? and  dokter.nm_dokter like ? and tgl_registrasi between ? and ? and  penjab.png_jawab like ? order by reg_periksa.no_rawat "); 
           ps3=koneksi.prepareStatement("select nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                    "namakeluarga,keluarga,pasien.kd_pj,penjab.png_jawab,if(tgl_daftar=?,'Baru','Lama') as daftar from pasien "+
                    "inner join kelurahan inner join kecamatan inner join kabupaten inner join penjab "+
                    "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_pj=penjab.kd_pj "+
                    "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                    "where pasien.no_rkm_medis=?");
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
        MnKamarInap = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnResepObat = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MnAntrian = new javax.swing.JMenuItem();
        MnResumePasien = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        MnLaporanHarianRalan = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganPoli = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganDokter = new javax.swing.JMenuItem();
        MnLaporanRekapJenisBayar = new javax.swing.JMenuItem();
        MnLaporanRekapRawatDarurat = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulanan = new javax.swing.JMenuItem();
        MnLaporanRekapKunjunganBulananPoli = new javax.swing.JMenuItem();
        MnLaporanRekapPenyakitRalan = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ppGrafikPerpoli = new javax.swing.JMenuItem();
        ppGrafikPerdokter = new javax.swing.JMenuItem();
        ppGrafikPerJK = new javax.swing.JMenuItem();
        ppGrafikPerPekerjaan = new javax.swing.JMenuItem();
        ppGrafikPerAgama = new javax.swing.JMenuItem();
        ppGrafikPerTahun = new javax.swing.JMenuItem();
        ppGrafikPerBulan = new javax.swing.JMenuItem();
        ppGrafikPerTanggal = new javax.swing.JMenuItem();
        ppGrafikDemografi = new javax.swing.JMenuItem();
        MnCetakSuratSakit = new javax.swing.JMenuItem();
        DlgRegisterRalan = new javax.swing.JDialog();
        internalFrame2 = new widget.InternalFrame();
        panelBiasa1 = new widget.PanelBiasa();
        menits1 = new widget.ComboBox();
        jams1 = new widget.ComboBox();
        TglRalan = new widget.Tanggal();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        ptgm = new widget.TextBox();
        jLabel27 = new widget.Label();
        jams2 = new widget.ComboBox();
        menits2 = new widget.ComboBox();
        jamp1 = new widget.ComboBox();
        menitp1 = new widget.ComboBox();
        jLabel28 = new widget.Label();
        jamp2 = new widget.ComboBox();
        menitp2 = new widget.ComboBox();
        jamm1 = new widget.ComboBox();
        menitm1 = new widget.ComboBox();
        jLabel29 = new widget.Label();
        jamm2 = new widget.ComboBox();
        menitm2 = new widget.ComboBox();
        ptgp = new widget.TextBox();
        ptgs = new widget.TextBox();
        BtnSeekPetugas = new widget.Button();
        BtnSeekPetugas1 = new widget.Button();
        BtnSeekPetugas2 = new widget.Button();
        BtnPrint1 = new widget.Button();
        BtnKeluar1 = new widget.Button();
        Kd2 = new widget.TextBox();
        DlgSakit = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TglSakit1 = new widget.Tanggal();
        jLabel31 = new widget.Label();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        jLabel32 = new widget.Label();
        TglSakit2 = new widget.Tanggal();
        jLabel33 = new widget.Label();
        lmsakit = new widget.TextBox();
        DlgDemografi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa3 = new widget.PanelBiasa();
        BtnPrint3 = new widget.Button();
        BtnKeluar3 = new widget.Button();
        Kelurahan2 = new widget.TextBox();
        BtnSeek8 = new widget.Button();
        Kecamatan2 = new widget.TextBox();
        BtnSeek9 = new widget.Button();
        Kabupaten2 = new widget.TextBox();
        BtnSeek10 = new widget.Button();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        BtnPrint4 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass8 = new widget.panelisi();
        jLabel14 = new widget.Label();
        CrDokter = new widget.TextBox();
        BtnSeek3 = new widget.Button();
        jLabel16 = new widget.Label();
        CrPoli = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TDokter = new widget.TextBox();
        TNoRw = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel9 = new widget.Label();
        DTPReg = new widget.Tanggal();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        TPngJwb = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TNoReg = new widget.TextBox();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        jLabel7 = new widget.Label();
        TAlmt = new widget.TextBox();
        BtnSeek = new widget.Button();
        TPasien = new widget.TextBox();
        jLabel22 = new widget.Label();
        THbngn = new widget.TextBox();
        ChkJln = new widget.CekBox();
        jLabel19 = new widget.Label();
        TPoli = new widget.TextBox();
        TBiaya = new widget.TextBox();
        kddokter = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        kdpoli = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel30 = new widget.Label();
        TStatus = new widget.TextBox();
        jLabel18 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(java.awt.Color.darkGray);
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnKamarInap.setBackground(new java.awt.Color(255, 255, 255));
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKamarInap.setForeground(java.awt.Color.darkGray);
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(java.awt.Color.darkGray);
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnBilling.setBackground(new java.awt.Color(255, 255, 255));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(java.awt.Color.darkGray);
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        MnResepObat.setBackground(new java.awt.Color(255, 255, 255));
        MnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepObat.setForeground(java.awt.Color.darkGray);
        MnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepObat.setText("Resep Obat");
        MnResepObat.setName("MnResepObat"); // NOI18N
        MnResepObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnResepObat);

        MnPeriksaLab.setBackground(new java.awt.Color(255, 255, 255));
        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(java.awt.Color.darkGray);
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Lab");
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPeriksaLab);

        MnRujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(java.awt.Color.darkGray);
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujuk);

        MnRujukMasuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(java.awt.Color.darkGray);
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujukMasuk);

        MnAntrian.setBackground(new java.awt.Color(255, 255, 255));
        MnAntrian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnAntrian.setForeground(java.awt.Color.darkGray);
        MnAntrian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnAntrian.setText("Antrian Pasien");
        MnAntrian.setName("MnAntrian"); // NOI18N
        MnAntrian.setPreferredSize(new java.awt.Dimension(250, 28));
        MnAntrian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnAntrianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnAntrian);

        MnResumePasien.setBackground(new java.awt.Color(255, 255, 255));
        MnResumePasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResumePasien.setForeground(java.awt.Color.darkGray);
        MnResumePasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResumePasien.setText("Resume Perawatan Pasien");
        MnResumePasien.setName("MnResumePasien"); // NOI18N
        MnResumePasien.setPreferredSize(new java.awt.Dimension(250, 28));
        MnResumePasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResumePasienActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnResumePasien);

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setForeground(java.awt.Color.darkGray);
        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu1.setText("Laporan");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(180, 30));

        MnLaporanHarianRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanHarianRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanHarianRalan.setForeground(java.awt.Color.darkGray);
        MnLaporanHarianRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanHarianRalan.setText("Laporan Harian Rawat Jalan");
        MnLaporanHarianRalan.setName("MnLaporanHarianRalan"); // NOI18N
        MnLaporanHarianRalan.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanHarianRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanHarianRalanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanHarianRalan);

        MnLaporanRekapKunjunganPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapKunjunganPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganPoli.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapKunjunganPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganPoli.setText("Laporan Rekap Kunjungan Per Poli");
        MnLaporanRekapKunjunganPoli.setName("MnLaporanRekapKunjunganPoli"); // NOI18N
        MnLaporanRekapKunjunganPoli.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapKunjunganPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganPoli);

        MnLaporanRekapKunjunganDokter.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapKunjunganDokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganDokter.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapKunjunganDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganDokter.setText("Laporan Rekap Kunjungan Per Dokter");
        MnLaporanRekapKunjunganDokter.setName("MnLaporanRekapKunjunganDokter"); // NOI18N
        MnLaporanRekapKunjunganDokter.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapKunjunganDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganDokterActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganDokter);

        MnLaporanRekapJenisBayar.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapJenisBayar.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapJenisBayar.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapJenisBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapJenisBayar.setText("Laporan RL 315 Cara bayar");
        MnLaporanRekapJenisBayar.setName("MnLaporanRekapJenisBayar"); // NOI18N
        MnLaporanRekapJenisBayar.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapJenisBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapJenisBayarActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapJenisBayar);

        MnLaporanRekapRawatDarurat.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapRawatDarurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapRawatDarurat.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapRawatDarurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapRawatDarurat.setText("Laporan RL 32 Rawat Darurat");
        MnLaporanRekapRawatDarurat.setName("MnLaporanRekapRawatDarurat"); // NOI18N
        MnLaporanRekapRawatDarurat.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapRawatDarurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapRawatDaruratActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapRawatDarurat);

        MnLaporanRekapKunjunganBulanan.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapKunjunganBulanan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulanan.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapKunjunganBulanan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulanan.setText("Laporan RL 51");
        MnLaporanRekapKunjunganBulanan.setName("MnLaporanRekapKunjunganBulanan"); // NOI18N
        MnLaporanRekapKunjunganBulanan.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapKunjunganBulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulanan);

        MnLaporanRekapKunjunganBulananPoli.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapKunjunganBulananPoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapKunjunganBulananPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setText("Laporan RL 52");
        MnLaporanRekapKunjunganBulananPoli.setName("MnLaporanRekapKunjunganBulananPoli"); // NOI18N
        MnLaporanRekapKunjunganBulananPoli.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapKunjunganBulananPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapKunjunganBulananPoliActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapKunjunganBulananPoli);

        MnLaporanRekapPenyakitRalan.setBackground(new java.awt.Color(255, 255, 255));
        MnLaporanRekapPenyakitRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanRekapPenyakitRalan.setForeground(java.awt.Color.darkGray);
        MnLaporanRekapPenyakitRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanRekapPenyakitRalan.setText("Laporan RL 53 Penyakit Ralan");
        MnLaporanRekapPenyakitRalan.setName("MnLaporanRekapPenyakitRalan"); // NOI18N
        MnLaporanRekapPenyakitRalan.setPreferredSize(new java.awt.Dimension(300, 28));
        MnLaporanRekapPenyakitRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanRekapPenyakitRalanActionPerformed(evt);
            }
        });
        jMenu1.add(MnLaporanRekapPenyakitRalan);

        jPopupMenu1.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setForeground(java.awt.Color.darkGray);
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        jMenu2.setText("Grafik Analisa");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu2.setIconTextGap(5);
        jMenu2.setName("jMenu2"); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(180, 30));

        ppGrafikPerpoli.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerpoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerpoli.setForeground(java.awt.Color.darkGray);
        ppGrafikPerpoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerpoli.setText("Grafik Kunjungan Per Poli");
        ppGrafikPerpoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerpoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerpoli.setIconTextGap(5);
        ppGrafikPerpoli.setName("ppGrafikPerpoli"); // NOI18N
        ppGrafikPerpoli.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerpoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerpoliActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerpoli);

        ppGrafikPerdokter.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerdokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerdokter.setForeground(java.awt.Color.darkGray);
        ppGrafikPerdokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerdokter.setText("Grafik Kunjungan Per Dokter");
        ppGrafikPerdokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerdokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerdokter.setIconTextGap(5);
        ppGrafikPerdokter.setName("ppGrafikPerdokter"); // NOI18N
        ppGrafikPerdokter.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerdokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerdokterActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerdokter);

        ppGrafikPerJK.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerJK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerJK.setForeground(java.awt.Color.darkGray);
        ppGrafikPerJK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerJK.setText("Grafik Kunjungan Per Jenis Kelamin");
        ppGrafikPerJK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerJK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerJK.setIconTextGap(5);
        ppGrafikPerJK.setName("ppGrafikPerJK"); // NOI18N
        ppGrafikPerJK.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerJKActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerJK);

        ppGrafikPerPekerjaan.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerPekerjaan.setForeground(java.awt.Color.darkGray);
        ppGrafikPerPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerPekerjaan.setText("Grafik Kunjungan Per Pekerjaan");
        ppGrafikPerPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerPekerjaan.setIconTextGap(5);
        ppGrafikPerPekerjaan.setName("ppGrafikPerPekerjaan"); // NOI18N
        ppGrafikPerPekerjaan.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerPekerjaanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerPekerjaan);

        ppGrafikPerAgama.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerAgama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerAgama.setForeground(java.awt.Color.darkGray);
        ppGrafikPerAgama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerAgama.setText("Grafik Kunjungan Per Agama");
        ppGrafikPerAgama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerAgama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerAgama.setIconTextGap(5);
        ppGrafikPerAgama.setName("ppGrafikPerAgama"); // NOI18N
        ppGrafikPerAgama.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerAgamaActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerAgama);

        ppGrafikPerTahun.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTahun.setForeground(java.awt.Color.darkGray);
        ppGrafikPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerTahun.setText("Grafik Kunjungan Per Tahun");
        ppGrafikPerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTahun.setIconTextGap(5);
        ppGrafikPerTahun.setName("ppGrafikPerTahun"); // NOI18N
        ppGrafikPerTahun.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTahunActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTahun);

        ppGrafikPerBulan.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerBulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerBulan.setForeground(java.awt.Color.darkGray);
        ppGrafikPerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerBulan.setText("Grafik Kunjungan Per Bulan");
        ppGrafikPerBulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerBulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerBulan.setIconTextGap(5);
        ppGrafikPerBulan.setName("ppGrafikPerBulan"); // NOI18N
        ppGrafikPerBulan.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerBulanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerBulan);

        ppGrafikPerTanggal.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikPerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTanggal.setForeground(java.awt.Color.darkGray);
        ppGrafikPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikPerTanggal.setText("Grafik Kunjungan Per Tanggal");
        ppGrafikPerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTanggal.setIconTextGap(5);
        ppGrafikPerTanggal.setName("ppGrafikPerTanggal"); // NOI18N
        ppGrafikPerTanggal.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTanggalActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTanggal);

        ppGrafikDemografi.setBackground(new java.awt.Color(255, 255, 255));
        ppGrafikDemografi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikDemografi.setForeground(java.awt.Color.darkGray);
        ppGrafikDemografi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppGrafikDemografi.setText("Demografi Pendaftar");
        ppGrafikDemografi.setActionCommand("Grafik Demografi Pasien");
        ppGrafikDemografi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikDemografi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikDemografi.setIconTextGap(5);
        ppGrafikDemografi.setName("ppGrafikDemografi"); // NOI18N
        ppGrafikDemografi.setPreferredSize(new java.awt.Dimension(300, 28));
        ppGrafikDemografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikDemografiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikDemografi);

        jPopupMenu1.add(jMenu2);

        MnCetakSuratSakit.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakSuratSakit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit.setForeground(java.awt.Color.darkGray);
        MnCetakSuratSakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakSuratSakit.setText("Surat Sakit");
        MnCetakSuratSakit.setName("MnCetakSuratSakit"); // NOI18N
        MnCetakSuratSakit.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakSuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakSuratSakit);

        DlgRegisterRalan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgRegisterRalan.setName("DlgRegisterRalan"); // NOI18N
        DlgRegisterRalan.setUndecorated(true);
        DlgRegisterRalan.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Register Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa1.setName("panelBiasa1"); // NOI18N
        panelBiasa1.setLayout(null);

        menits1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menits1.setName("menits1"); // NOI18N
        menits1.setOpaque(false);
        panelBiasa1.add(menits1);
        menits1.setBounds(120, 70, 48, 23);

        jams1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jams1.setName("jams1"); // NOI18N
        jams1.setOpaque(false);
        panelBiasa1.add(jams1);
        jams1.setBounds(70, 70, 48, 23);

        TglRalan.setEditable(false);
        TglRalan.setForeground(new java.awt.Color(50, 70, 50));
        TglRalan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        TglRalan.setDisplayFormat("yyyy-MM-dd");
        TglRalan.setName("TglRalan"); // NOI18N
        TglRalan.setOpaque(false);
        panelBiasa1.add(TglRalan);
        TglRalan.setBounds(70, 10, 100, 23);

        jLabel23.setText("Tanggal :");
        jLabel23.setName("jLabel23"); // NOI18N
        panelBiasa1.add(jLabel23);
        jLabel23.setBounds(0, 10, 66, 23);

        jLabel24.setText("P :");
        jLabel24.setName("jLabel24"); // NOI18N
        panelBiasa1.add(jLabel24);
        jLabel24.setBounds(0, 40, 66, 23);

        jLabel25.setText("S :");
        jLabel25.setName("jLabel25"); // NOI18N
        panelBiasa1.add(jLabel25);
        jLabel25.setBounds(0, 70, 66, 23);

        jLabel26.setText("M :");
        jLabel26.setName("jLabel26"); // NOI18N
        panelBiasa1.add(jLabel26);
        jLabel26.setBounds(0, 100, 66, 23);

        ptgm.setHighlighter(null);
        ptgm.setName("ptgm"); // NOI18N
        ptgm.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa1.add(ptgm);
        ptgm.setBounds(310, 100, 257, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("S.D.");
        jLabel27.setName("jLabel27"); // NOI18N
        panelBiasa1.add(jLabel27);
        jLabel27.setBounds(170, 70, 30, 23);

        jams2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jams2.setName("jams2"); // NOI18N
        jams2.setOpaque(false);
        panelBiasa1.add(jams2);
        jams2.setBounds(200, 70, 48, 23);

        menits2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menits2.setName("menits2"); // NOI18N
        menits2.setOpaque(false);
        panelBiasa1.add(menits2);
        menits2.setBounds(250, 70, 48, 23);

        jamp1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jamp1.setName("jamp1"); // NOI18N
        jamp1.setOpaque(false);
        panelBiasa1.add(jamp1);
        jamp1.setBounds(70, 40, 48, 23);

        menitp1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menitp1.setName("menitp1"); // NOI18N
        menitp1.setOpaque(false);
        panelBiasa1.add(menitp1);
        menitp1.setBounds(120, 40, 48, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("S.D.");
        jLabel28.setName("jLabel28"); // NOI18N
        panelBiasa1.add(jLabel28);
        jLabel28.setBounds(170, 40, 30, 23);

        jamp2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jamp2.setName("jamp2"); // NOI18N
        jamp2.setOpaque(false);
        panelBiasa1.add(jamp2);
        jamp2.setBounds(200, 40, 48, 23);

        menitp2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menitp2.setName("menitp2"); // NOI18N
        menitp2.setOpaque(false);
        panelBiasa1.add(menitp2);
        menitp2.setBounds(250, 40, 48, 23);

        jamm1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jamm1.setName("jamm1"); // NOI18N
        jamm1.setOpaque(false);
        panelBiasa1.add(jamm1);
        jamm1.setBounds(70, 100, 48, 23);

        menitm1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menitm1.setName("menitm1"); // NOI18N
        menitm1.setOpaque(false);
        panelBiasa1.add(menitm1);
        menitm1.setBounds(120, 100, 48, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("S.D.");
        jLabel29.setName("jLabel29"); // NOI18N
        panelBiasa1.add(jLabel29);
        jLabel29.setBounds(170, 100, 30, 23);

        jamm2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        jamm2.setName("jamm2"); // NOI18N
        jamm2.setOpaque(false);
        panelBiasa1.add(jamm2);
        jamm2.setBounds(200, 100, 48, 23);

        menitm2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        menitm2.setName("menitm2"); // NOI18N
        menitm2.setOpaque(false);
        panelBiasa1.add(menitm2);
        menitm2.setBounds(250, 100, 48, 23);

        ptgp.setHighlighter(null);
        ptgp.setName("ptgp"); // NOI18N
        ptgp.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa1.add(ptgp);
        ptgp.setBounds(310, 40, 257, 23);

        ptgs.setHighlighter(null);
        ptgs.setName("ptgs"); // NOI18N
        ptgs.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa1.add(ptgs);
        ptgs.setBounds(310, 70, 257, 23);

        BtnSeekPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas.setMnemonic('5');
        BtnSeekPetugas.setToolTipText("ALt+5");
        BtnSeekPetugas.setName("BtnSeekPetugas"); // NOI18N
        BtnSeekPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugasActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnSeekPetugas);
        BtnSeekPetugas.setBounds(570, 100, 28, 23);

        BtnSeekPetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas1.setMnemonic('5');
        BtnSeekPetugas1.setToolTipText("ALt+5");
        BtnSeekPetugas1.setName("BtnSeekPetugas1"); // NOI18N
        BtnSeekPetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas1ActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnSeekPetugas1);
        BtnSeekPetugas1.setBounds(570, 40, 28, 23);

        BtnSeekPetugas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeekPetugas2.setMnemonic('5');
        BtnSeekPetugas2.setToolTipText("ALt+5");
        BtnSeekPetugas2.setName("BtnSeekPetugas2"); // NOI18N
        BtnSeekPetugas2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekPetugas2ActionPerformed(evt);
            }
        });
        panelBiasa1.add(BtnSeekPetugas2);
        BtnSeekPetugas2.setBounds(570, 70, 28, 23);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Cetak");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        BtnPrint1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint1KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnPrint1);
        BtnPrint1.setBounds(20, 140, 100, 30);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('K');
        BtnKeluar1.setText("Keluar");
        BtnKeluar1.setToolTipText("Alt+K");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        BtnKeluar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar1KeyPressed(evt);
            }
        });
        panelBiasa1.add(BtnKeluar1);
        BtnKeluar1.setBounds(500, 140, 100, 30);

        internalFrame2.add(panelBiasa1, java.awt.BorderLayout.CENTER);

        DlgRegisterRalan.getContentPane().add(internalFrame2, java.awt.BorderLayout.CENTER);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgSakit.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit.setName("DlgSakit"); // NOI18N
        DlgSakit.setUndecorated(true);
        DlgSakit.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Sakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TglSakit1.setEditable(false);
        TglSakit1.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        TglSakit1.setDisplayFormat("yyyy-MM-dd");
        TglSakit1.setName("TglSakit1"); // NOI18N
        TglSakit1.setOpaque(false);
        panelBiasa2.add(TglSakit1);
        TglSakit1.setBounds(70, 10, 100, 23);

        jLabel31.setText("Lama Sakit :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelBiasa2.add(jLabel31);
        jLabel31.setBounds(297, 10, 110, 23);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Cetak");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(10, 50, 100, 30);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 50, 100, 30);

        jLabel32.setText("s/d");
        jLabel32.setName("jLabel32"); // NOI18N
        panelBiasa2.add(jLabel32);
        jLabel32.setBounds(176, 10, 20, 23);

        TglSakit2.setEditable(false);
        TglSakit2.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        TglSakit2.setDisplayFormat("yyyy-MM-dd");
        TglSakit2.setName("TglSakit2"); // NOI18N
        TglSakit2.setOpaque(false);
        panelBiasa2.add(TglSakit2);
        TglSakit2.setBounds(200, 10, 100, 23);

        jLabel33.setText("Tanggal :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 10, 66, 23);

        lmsakit.setHighlighter(null);
        lmsakit.setName("lmsakit"); // NOI18N
        lmsakit.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa2.add(lmsakit);
        lmsakit.setBounds(410, 10, 110, 23);

        internalFrame3.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgSakit.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        DlgDemografi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgDemografi.setName("DlgDemografi"); // NOI18N
        DlgDemografi.setUndecorated(true);
        DlgDemografi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Demografi Pendaftar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setLayout(null);

        BtnPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint3.setMnemonic('T');
        BtnPrint3.setText("Grafik");
        BtnPrint3.setToolTipText("Alt+T");
        BtnPrint3.setName("BtnPrint3"); // NOI18N
        BtnPrint3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint3ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnPrint3);
        BtnPrint3.setBounds(110, 110, 100, 30);

        BtnKeluar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar3.setMnemonic('K');
        BtnKeluar3.setText("Keluar");
        BtnKeluar3.setToolTipText("Alt+K");
        BtnKeluar3.setName("BtnKeluar3"); // NOI18N
        BtnKeluar3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar3ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnKeluar3);
        BtnKeluar3.setBounds(430, 110, 100, 30);

        Kelurahan2.setHighlighter(null);
        Kelurahan2.setName("Kelurahan2"); // NOI18N
        Kelurahan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa3.add(Kelurahan2);
        Kelurahan2.setBounds(105, 70, 350, 23);

        BtnSeek8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek8.setMnemonic('1');
        BtnSeek8.setToolTipText("ALt+1");
        BtnSeek8.setName("BtnSeek8"); // NOI18N
        BtnSeek8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek8ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnSeek8);
        BtnSeek8.setBounds(460, 70, 28, 23);

        Kecamatan2.setHighlighter(null);
        Kecamatan2.setName("Kecamatan2"); // NOI18N
        Kecamatan2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa3.add(Kecamatan2);
        Kecamatan2.setBounds(105, 40, 350, 23);

        BtnSeek9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek9.setMnemonic('1');
        BtnSeek9.setToolTipText("ALt+1");
        BtnSeek9.setName("BtnSeek9"); // NOI18N
        BtnSeek9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek9ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnSeek9);
        BtnSeek9.setBounds(460, 40, 28, 23);

        Kabupaten2.setHighlighter(null);
        Kabupaten2.setName("Kabupaten2"); // NOI18N
        Kabupaten2.setSelectionColor(new java.awt.Color(255, 255, 255));
        panelBiasa3.add(Kabupaten2);
        Kabupaten2.setBounds(105, 10, 350, 23);

        BtnSeek10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek10.setMnemonic('1');
        BtnSeek10.setToolTipText("ALt+1");
        BtnSeek10.setName("BtnSeek10"); // NOI18N
        BtnSeek10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek10ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnSeek10);
        BtnSeek10.setBounds(460, 10, 28, 23);

        jLabel34.setText("Kelurahan :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa3.add(jLabel34);
        jLabel34.setBounds(0, 70, 100, 23);

        jLabel35.setText("Kabupaten :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa3.add(jLabel35);
        jLabel35.setBounds(0, 10, 100, 23);

        jLabel36.setText("Kecamatan :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelBiasa3.add(jLabel36);
        jLabel36.setBounds(0, 40, 100, 23);

        BtnPrint4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint4.setMnemonic('T');
        BtnPrint4.setText("Cetak");
        BtnPrint4.setToolTipText("Alt+T");
        BtnPrint4.setName("BtnPrint4"); // NOI18N
        BtnPrint4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint4ActionPerformed(evt);
            }
        });
        panelBiasa3.add(BtnPrint4);
        BtnPrint4.setBounds(10, 110, 100, 30);

        internalFrame4.add(panelBiasa3, java.awt.BorderLayout.CENTER);

        DlgDemografi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Registrasi Periksa Hari Ini ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setAutoCreateRowSorter(true);
        tbPetugas.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPetugas.setComponentPopupMenu(jPopupMenu1);
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugasKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnSimpan);

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
        panelGlass6.add(BtnBatal);

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
        panelGlass6.add(BtnHapus);

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
        panelGlass6.add(BtnEdit);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
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

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

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
        panelGlass7.add(BtnCari);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel14.setText("Dokter :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel14);

        CrDokter.setEditable(false);
        CrDokter.setName("CrDokter"); // NOI18N
        CrDokter.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrDokter);

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
        panelGlass8.add(BtnSeek3);

        jLabel16.setText("Unit :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass8.add(jLabel16);

        CrPoli.setEditable(false);
        CrPoli.setName("CrPoli"); // NOI18N
        CrPoli.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass8.add(CrPoli);

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
        panelGlass8.add(BtnSeek4);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(490, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No. Reg. :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 77, 23);

        jLabel4.setText("No. Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 77, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(183, 102, 181, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(81, 42, 190, 23);

        jLabel8.setText("Tgl. Reg. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 77, 23);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 102, 77, 23);

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(180, 72, 36, 23);

        DTPReg.setEditable(false);
        DTPReg.setForeground(new java.awt.Color(50, 70, 50));
        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-28" }));
        DTPReg.setDisplayFormat("yyyy-MM-dd");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        DTPReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRegKeyPressed(evt);
            }
        });
        FormInput.add(DTPReg);
        DTPReg.setBounds(81, 72, 100, 23);

        jLabel20.setText("Png. Jawab :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(396, 42, 100, 23);

        jLabel21.setText("Almt Png. Jwb :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(396, 72, 100, 23);

        TPngJwb.setHighlighter(null);
        TPngJwb.setName("TPngJwb"); // NOI18N
        TPngJwb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPngJwbKeyPressed(evt);
            }
        });
        FormInput.add(TPngJwb);
        TPngJwb.setBounds(500, 42, 150, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(500, 12, 130, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormInput.add(TNoReg);
        TNoReg.setBounds(81, 12, 120, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.setOpaque(false);
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        FormInput.add(CmbJam);
        CmbJam.setBounds(220, 72, 48, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.setOpaque(false);
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(270, 72, 48, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.setOpaque(false);
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(320, 72, 48, 23);

        jLabel7.setText("No.Rekam Medik :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(396, 12, 100, 23);

        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(500, 72, 190, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("ALt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek);
        BtnSeek.setBounds(852, 12, 28, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(632, 12, 218, 23);

        jLabel22.setText("Hubungan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(648, 42, 98, 23);

        THbngn.setHighlighter(null);
        THbngn.setName("THbngn"); // NOI18N
        THbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THbngnKeyPressed(evt);
            }
        });
        FormInput.add(THbngn);
        THbngn.setBounds(750, 42, 130, 23);

        ChkJln.setBackground(new java.awt.Color(235, 255, 235));
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setForeground(new java.awt.Color(153, 0, 51));
        ChkJln.setSelected(true);
        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(371, 72, 23, 23);

        jLabel19.setText("Unit :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 132, 77, 23);

        TPoli.setEditable(false);
        TPoli.setName("TPoli"); // NOI18N
        FormInput.add(TPoli);
        TPoli.setBounds(148, 132, 120, 23);

        TBiaya.setName("TBiaya"); // NOI18N
        TBiaya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBiayaKeyPressed(evt);
            }
        });
        FormInput.add(TBiaya);
        TBiaya.setBounds(270, 132, 94, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(81, 102, 100, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("ALt+2");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(366, 102, 28, 23);

        kdpoli.setHighlighter(null);
        kdpoli.setName("kdpoli"); // NOI18N
        kdpoli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpoliKeyPressed(evt);
            }
        });
        FormInput.add(kdpoli);
        kdpoli.setBounds(80, 132, 66, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("ALt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek2);
        BtnSeek2.setBounds(366, 132, 28, 23);

        jLabel30.setText("Status :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(648, 72, 98, 23);

        TStatus.setEditable(false);
        TStatus.setHighlighter(null);
        TStatus.setName("TStatus"); // NOI18N
        FormInput.add(TStatus);
        TStatus.setBounds(750, 72, 130, 23);

        jLabel18.setText("Jenis Bayar :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(396, 102, 100, 23);

        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(500, 102, 70, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(572, 102, 279, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('2');
        BtnSeek5.setToolTipText("ALt+2");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSeek5);
        BtnSeek5.setBounds(852, 102, 28, 23);

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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRegKeyPressed
        Valid.pindah(evt,TNoRw,CmbJam);
}//GEN-LAST:event_DTPRegKeyPressed

    private void TPngJwbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPngJwbKeyPressed
        Valid.pindah(evt,TNoRM,THbngn);
}//GEN-LAST:event_TPngJwbKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPas();
        }else{
            Valid.pindah(evt,kdpoli,TPngJwb);
        }
}//GEN-LAST:event_TNoRMKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        Valid.pindah(evt,TCari,TNoRw);
}//GEN-LAST:event_TNoRegKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,DTPReg,CmbMenit);
}//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
}//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,kddokter);
}//GEN-LAST:event_CmbDetikKeyPressed

    private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
        Valid.pindah(evt,THbngn,kdpnj);
}//GEN-LAST:event_TAlmtKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        var.setStatus(true);
        //pasien.tampil();
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void THbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THbngnKeyPressed
        Valid.pindah(evt,TPngJwb,TAlmt);
}//GEN-LAST:event_THbngnKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(TPoli.getText().trim().equals("")){
            Valid.textKosong(TPoli,"poliklinik");
        }else if(TBiaya.getText().trim().equals("")){
            Valid.textKosong(TBiaya,"biaya regristrasi");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else{
            Sequel.menyimpan("reg_periksa","'"+TNoReg.getText()+"','"+
                    TNoRw.getText()+"','"+
                    DTPReg.getSelectedItem()+"','"+
                    CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+"','"+
                    kddokter.getText()+"','"+
                    TNoRM.getText()+"','"+
                    kdpoli.getText()+"','"+
                    TPngJwb.getText()+"','"+
                    TAlmt.getText()+"','"+
                    THbngn.getText()+"','"+
                    TBiaya.getText()+"','Belum','"+TStatus.getText()+"','Ralan','"+kdpnj.getText()+"'","No.Rawat");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpnj,BtnBatal);
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
        Valid.hapusTable(tabMode,TNoRw,"reg_periksa","no_rawat");
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
               Valid.MyReport("rptReg.jrxml","report","::[ Data Registrasi Periksa ]::","select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab  "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli  "+
                   "where dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.no_reg like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.tgl_registrasi like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.stts_daftar like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.p_jawab like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.almt_pj like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                   "dokter.nm_dokter like '%"+CrDokter.getText()+"%' and poliklinik.nm_poli like '%"+CrPoli.getText()+"%' and tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' and reg_periksa.hubunganpj like '%"+TCari.getText().trim()+"%' order by reg_periksa.no_rawat desc");   
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
        CrPoli.setText("");
        CrDokter.setText("");
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(TDokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(TPoli.getText().trim().equals("")){
            Valid.textKosong(TPoli,"poliklinik");
        }else if(TBiaya.getText().trim().equals("")){
            Valid.textKosong(TBiaya,"biaya regristrasi");
        }else{
            Sequel.queryu("update reg_periksa set no_rawat='"+TNoRw.getText()+"',no_reg='"+TNoReg.getText()+
                    "',tgl_registrasi='"+DTPReg.getSelectedItem()+
                    "',jam_reg='"+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem()+
                    "',kd_dokter='"+kddokter.getText()+"',no_rkm_medis='"+TNoRM.getText()+
                    "',kd_poli='"+kdpoli.getText()+
                    "',p_jawab='"+TPngJwb.getText()+
                    "',almt_pj='"+TAlmt.getText()+
                    "',biaya_reg='"+TBiaya.getText()+
                    "',hubunganpj='"+THbngn.getText()+"',stts_daftar='"+TStatus.getText()+"',kd_pj='"+kdpnj.getText()+"' where no_rawat='"+Kd2.getText()+"'");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void TBiayaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBiayaKeyPressed
        Valid.pindah(evt,kdpoli,BtnSimpan);
}//GEN-LAST:event_TBiayaKeyPressed

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                int kolom=tbPetugas.getSelectedColumn();
                if(kolom==0){
                    MnRawatJalanActionPerformed(null);
                }else if(kolom==1){
                    MnKamarInapActionPerformed(null);
                }else if(kolom==2){
                    MnPemberianObatActionPerformed(null);
                }else if(kolom==3){
                    MnBillingActionPerformed(null);
                }
            }
        }
        
}//GEN-LAST:event_tbPetugasMouseClicked

    private void tbPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                int kolom=tbPetugas.getSelectedColumn();
                if(kolom==0){
                    MnRawatJalanActionPerformed(null);
                }else if(kolom==1){
                    MnKamarInapActionPerformed(null);
                }else if(kolom==2){
                    MnPemberianObatActionPerformed(null);
                }else if(kolom==3){
                    MnBillingActionPerformed(null);
                }
            }
        }
}//GEN-LAST:event_tbPetugasKeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isNumber();
            isPtg();
        }else{
            isNumber();
            isPtg();
            Valid.pindah(evt,CmbDetik,kdpoli);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.tampil(" order by kd_dokter");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

private void kdpoliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpoliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPoli();
        }else{
            Valid.pindah(evt,kddokter,TNoRM);
        }
}//GEN-LAST:event_kdpoliKeyPressed

private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        pilihan=1;
        poli.emptTeks();
        poli.isCek();
        poli.tampil();
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

private void BtnSeek3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek3ActionPerformed
        pilihan=2;
        dokter.emptTeks();
        dokter.isCek();
        dokter.tampil(" order by kd_dokter");
        dokter.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnSeek3ActionPerformed

private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        pilihan=2;
        poli.emptTeks();
        poli.isCek();
        poli.tampil();
        poli.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
}//GEN-LAST:event_BtnSeek4ActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            var.setStatus(true);
            DlgKamarInap dlgki=new DlgKamarInap(null,false);
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText());   
            dlgki.tampil();
            dlgki.setVisible(true);
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
            dlgrwjl.isCek();
            dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwjl.setLocationRelativeTo(internalFrame1);
            dlgrwjl.emptTeks();
        
            dlgrwjl.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
            dlgrwjl.tampilDr();
            dlgrwjl.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgResepObat dlgro=new DlgResepObat(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());  
            dlgro.tampil();
            dlgro.setVisible(true);
        }
}//GEN-LAST:event_MnResepObatActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{            
            DlgRujuk dlgrjk=new DlgRujuk(null,false);
            dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.emptTeks();
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
            dlgrjk.tampil();
            dlgrjk.setVisible(true);
        }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
            dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrwinap.setLocationRelativeTo(internalFrame1);
            dlgrwinap.emptTeks(); 
            dlgrwinap.isCek();
            dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
            dlgrwinap.tampilPO();
            dlgrwinap.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
            dlgbil.TNoRw.setText(TNoRw.getText());  
            dlgbil.TBayar.setText("0");
            dlgbil.TKembali.setText("0");    
            dlgbil.isRawat(); 
            dlgbil.isKembali();
            dlgbil.isRawat();
            dlgbil.isCek();
            dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgbil.setLocationRelativeTo(internalFrame1);
            dlgbil.setVisible(true);
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void MnAntrianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnAntrianActionPerformed
       DlgAntrian dlgro=new DlgAntrian(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setVisible(true);
}//GEN-LAST:event_MnAntrianActionPerformed

private void MnResumePasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResumePasienActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgResumePerawatan resume=new DlgResumePerawatan(null,false);
            resume.setNoRm(TNoRM.getText()); 
            resume.isPasien();
            resume.tampil();
            resume.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
        }
}//GEN-LAST:event_MnResumePasienActionPerformed

private void MnLaporanHarianRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanHarianRalanActionPerformed
     DlgRegisterRalan.setSize(630,214);
     DlgRegisterRalan.setLocationRelativeTo(internalFrame1);
     DlgRegisterRalan.setVisible(true);
}//GEN-LAST:event_MnLaporanHarianRalanActionPerformed

private void BtnSeekPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugasActionPerformed
        pilihan=3;
        petugas.emptTeks();
        petugas.isCek();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugasActionPerformed

private void BtnSeekPetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas1ActionPerformed
     pilihan=1;
     petugas.emptTeks();
        petugas.isCek();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugas1ActionPerformed

private void BtnSeekPetugas2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekPetugas2ActionPerformed
    pilihan=2;
    petugas.emptTeks();
        petugas.isCek();
        petugas.tampil();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_BtnSeekPetugas2ActionPerformed

private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanRegisterRalan.php?tanggal="+TglRalan.getSelectedItem()+
                             "&jamp1="+jamp1.getSelectedItem()+":"+menitp1.getSelectedItem()+":00&jamp2="+jamp2.getSelectedItem()+":"+menitp2.getSelectedItem()+":00&petugasp="+ptgp.getText().replaceAll(" ","_")+
                             "&jams1="+jams1.getSelectedItem()+":"+menits1.getSelectedItem()+":00&jams2="+jams2.getSelectedItem()+":"+menits2.getSelectedItem()+":00&petugass="+ptgs.getText().replaceAll(" ","_")+
                             "&jamm1="+jamm1.getSelectedItem()+":"+menitm1.getSelectedItem()+":00&jamm2="+jamm2.getSelectedItem()+":"+menitm2.getSelectedItem()+":00&petugasm="+ptgm.getText().replaceAll(" ","_")+
                             "&unit="+CrPoli.getText().replaceAll(" ","_")+"&dokter="+CrDokter.getText().replaceAll(" ","_"));                       
        } 
}//GEN-LAST:event_BtnPrint1ActionPerformed

private void BtnPrint1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrint1KeyPressed

private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
   DlgRegisterRalan.dispose();    
}//GEN-LAST:event_BtnKeluar1ActionPerformed

private void BtnKeluar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluar1KeyPressed

private void ppGrafikPerpoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerpoliActionPerformed
       tampil();
       grafikperiksaperpoli kas=new grafikperiksaperpoli("Grafik Periksa Per Unit/Poli Tanggal "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerpoliActionPerformed

private void ppGrafikPerdokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerdokterActionPerformed
      tampil();
       grafikperiksaperdokter kas=new grafikperiksaperdokter("Grafik Periksa Per Dokter "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerdokterActionPerformed

private void ppGrafikPerJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerJKActionPerformed
      tampil();
       grafikperiksaperjk kas=new grafikperiksaperjk("Grafik Periksa Per Jenis Kelamin "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerJKActionPerformed

private void ppGrafikPerPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerPekerjaanActionPerformed
       tampil();
       grafikperiksaperpekerjaan kas=new grafikperiksaperpekerjaan("Grafik Periksa Per Pekerjaan "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerPekerjaanActionPerformed

private void ppGrafikPerAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerAgamaActionPerformed
     tampil();
       grafikperiksaperagama kas=new grafikperiksaperagama("Grafik Periksa Per Agama "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerAgamaActionPerformed

private void ppGrafikPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTahunActionPerformed
      tampil();
       grafikperiksapertahun kas=new grafikperiksapertahun("Grafik Periksa Per Tahun "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerTahunActionPerformed

private void ppGrafikPerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerBulanActionPerformed
      tampil();
       grafikperiksaperbulan kas=new grafikperiksaperbulan("Grafik Periksa Per Bulan "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerBulanActionPerformed

private void ppGrafikPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTanggalActionPerformed
      tampil();
       grafikperiksaperhari kas=new grafikperiksaperhari("Grafik Periksa Per Hari "+DTPCari1.getSelectedItem()+" S.D. "+DTPCari2.getSelectedItem(),"where tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ");
       kas.setSize(this.getWidth(), this.getHeight());        
       kas.setLocationRelativeTo(this);
       kas.setVisible(true);
}//GEN-LAST:event_ppGrafikPerTanggalActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText());  
            dlgro.tampil();
            dlgro.tampiltarif();
            dlgro.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnCetakSuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitActionPerformed
     DlgSakit.setSize(550,121);
     DlgSakit.setLocationRelativeTo(internalFrame1);
     DlgSakit.setVisible(true);
}//GEN-LAST:event_MnCetakSuratSakitActionPerformed

private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
      if(TPasien.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
      }else{
          Map<String, Object> parameter = new HashMap<>();
            parameter.put("hari",lmsakit.getText());
            parameter.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
            parameter.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
          Valid.MyReport("rptSuratSakit.jrxml","report","::[ Surat Sakit ]::",
                        "select reg_periksa.no_rawat, "+
                        "pasien.nm_pasien,pasien.jk,pasien.umur,pasien.pekerjaan,pasien.alamat "+
                        "from reg_periksa inner join pasien "+
                        "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",parameter);
      }
}//GEN-LAST:event_BtnPrint2ActionPerformed

private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
   DlgSakit.dispose();
}//GEN-LAST:event_BtnKeluar2ActionPerformed

private void BtnPrint3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint3ActionPerformed
                
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                        " reg_periksa inner join pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                        " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and kelurahan.nm_kel='"+Kelurahan2.getText()+"'  and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"'   ", 
                        "pasien.alamat","Area");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                        " reg_periksa inner join pasien inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel "+
                         "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"'  ", 
                         "kelurahan.nm_kel","Kelurahan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                         " reg_periksa inner join pasien inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab "+
                         "and pasien.kd_kec=kecamatan.kd_kec and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where kabupaten.nm_kab='"+Kabupaten2.getText()+"'  and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"'  ", 
                         "kecamatan.nm_kec","Kecamatan");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                grafiksql kas=new grafiksql("::[ Data Demografi Per Kabupaten ]::",
                         " reg_periksa inner join pasien inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ", 
                          "kabupaten.nm_kab","Kabupaten");
                kas.setSize(this.getWidth(), this.getHeight());        
                kas.setLocationRelativeTo(this);
                kas.setVisible(true);
            } 
}//GEN-LAST:event_BtnPrint3ActionPerformed

private void BtnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar3ActionPerformed
   DlgDemografi.dispose();
}//GEN-LAST:event_BtnKeluar3ActionPerformed

private void BtnSeek8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek8ActionPerformed
   if(Kecamatan2.getText().equals("")){
       Valid.textKosong(Kecamatan2,"Kecamatan");
   }else{
       pasien.kel.tampil();
        pasien.kel.isCek();
        pasien.kel.emptTeks();
        pasien.kel.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.kel.setLocationRelativeTo(internalFrame1);
        pasien.kel.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek8ActionPerformed

private void BtnSeek9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek9ActionPerformed
   if(Kabupaten2.getText().equals("")){
       Valid.textKosong(Kabupaten2,"Kabupaten");
   }else{
       pasien.kec.tampil();
        pasien.kec.isCek();
        pasien.kec.emptTeks();
        pasien.kec.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.kec.setLocationRelativeTo(internalFrame1);
        pasien.kec.setVisible(true);
   }       
}//GEN-LAST:event_BtnSeek9ActionPerformed

private void BtnSeek10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek10ActionPerformed
    
       pasien.kab.tampil();
        pasien.kab.isCek();
        pasien.kab.emptTeks();
        pasien.kab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.kab.setLocationRelativeTo(internalFrame1);
        pasien.kab.setVisible(true);   
}//GEN-LAST:event_BtnSeek10ActionPerformed

private void BtnPrint4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Area");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select  pasien.alamat as area,count(pasien.alamat) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' "+
                   "and kelurahan.nm_kel='"+Kelurahan2.getText()+"' and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' group by pasien.alamat order by pasien.alamat",data);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Kelurahan");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kelurahan.nm_kel as area,count(kelurahan.nm_kel) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' group by kelurahan.nm_kel order by kelurahan.nm_kel",data);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kecamatan Kabupaten "+Kabupaten2.getText());
                data.put("area","Kecamatan");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kecamatan.nm_kec as area,count(kecamatan.nm_kec) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' group by kecamatan.nm_kec order by kecamatan.nm_kec",data);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kabupaten");
                data.put("area","Kabupaten");
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kabupaten ]::","select kabupaten.nm_kab as area,count(kabupaten.nm_kab) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' group by kabupaten.nm_kab order by kabupaten.nm_kab",data);
            }                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrint4ActionPerformed

private void ppGrafikDemografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikDemografiActionPerformed
        DlgDemografi.setSize(550,180);
        DlgDemografi.setLocationRelativeTo(internalFrame1);
        DlgDemografi.setVisible(true);
}//GEN-LAST:event_ppGrafikDemografiActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
   if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbPetugas.requestFocus();
      }else{
            
                DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
                rujukmasuk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                rujukmasuk.setLocationRelativeTo(internalFrame1);
                rujukmasuk.emptTeks();
                rujukmasuk.isCek();
                rujukmasuk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                rujukmasuk.tampil();
                rujukmasuk.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnRujukMasukActionPerformed

private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            TAlmt.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            BtnSimpan.requestFocus();      
        }
}//GEN-LAST:event_kdpnjKeyPressed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        var.setStatus(true);
        pasien.penjab.tampil("");
        pasien.penjab.isCek();
        pasien.penjab.emptTeks();
        pasien.penjab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        pasien.penjab.setLocationRelativeTo(internalFrame1);
        pasien.penjab.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void MnLaporanRekapKunjunganPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganPoliActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganPoli.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        } 
}//GEN-LAST:event_MnLaporanRekapKunjunganPoliActionPerformed

private void MnLaporanRekapKunjunganDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganDokterActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganDokter.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        } 
}//GEN-LAST:event_MnLaporanRekapKunjunganDokterActionPerformed

private void MnLaporanRekapJenisBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapJenisBayarActionPerformed
     if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganJenisBayar.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        }
}//GEN-LAST:event_MnLaporanRekapJenisBayarActionPerformed

private void MnLaporanRekapRawatDaruratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapRawatDaruratActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanRawatDarurat.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        }
}//GEN-LAST:event_MnLaporanRekapRawatDaruratActionPerformed

private void MnLaporanRekapKunjunganBulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganBulanan.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananActionPerformed

private void MnLaporanRekapKunjunganBulananPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else{
            Valid.panggilUrl("billing/LaporanKunjunganBulananPoli.php?tanggal1="+DTPCari1.getSelectedItem()+"&tanggal2="+DTPCari2.getSelectedItem());                       
        }
}//GEN-LAST:event_MnLaporanRekapKunjunganBulananPoliActionPerformed

    private void MnLaporanRekapPenyakitRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanRekapPenyakitRalanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgFrekuensiPenyakitRalan ktginventaris=new DlgFrekuensiPenyakitRalan(null,false);
        ktginventaris.prosesCari();
        ktginventaris.isCek();
        ktginventaris.setSize(this.getWidth()-40,this.getHeight()-40);
        ktginventaris.setLocationRelativeTo(this);
        ktginventaris.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnLaporanRekapPenyakitRalanActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgReg dialog = new DlgReg(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar3;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint3;
    private widget.Button BtnPrint4;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek10;
    private widget.Button BtnSeek2;
    private widget.Button BtnSeek3;
    private widget.Button BtnSeek4;
    private widget.Button BtnSeek5;
    private widget.Button BtnSeek8;
    private widget.Button BtnSeek9;
    private widget.Button BtnSeekPetugas;
    private widget.Button BtnSeekPetugas1;
    private widget.Button BtnSeekPetugas2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox CrDokter;
    private widget.TextBox CrPoli;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private javax.swing.JDialog DlgDemografi;
    private javax.swing.JDialog DlgRegisterRalan;
    private javax.swing.JDialog DlgSakit;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kabupaten2;
    private widget.TextBox Kd2;
    private widget.TextBox Kecamatan2;
    private widget.TextBox Kelurahan2;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnAntrian;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnCetakSuratSakit;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnLaporanHarianRalan;
    private javax.swing.JMenuItem MnLaporanRekapJenisBayar;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulanan;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganBulananPoli;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganDokter;
    private javax.swing.JMenuItem MnLaporanRekapKunjunganPoli;
    private javax.swing.JMenuItem MnLaporanRekapPenyakitRalan;
    private javax.swing.JMenuItem MnLaporanRekapRawatDarurat;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnResepObat;
    private javax.swing.JMenuItem MnResumePasien;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlmt;
    private widget.TextBox TBiaya;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THbngn;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TPngJwb;
    private widget.TextBox TPoli;
    private widget.TextBox TStatus;
    private widget.Tanggal TglRalan;
    private widget.Tanggal TglSakit1;
    private widget.Tanggal TglSakit2;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel10;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.ComboBox jamm1;
    private widget.ComboBox jamm2;
    private widget.ComboBox jamp1;
    private widget.ComboBox jamp2;
    private widget.ComboBox jams1;
    private widget.ComboBox jams2;
    private widget.TextBox kddokter;
    private widget.TextBox kdpnj;
    private widget.TextBox kdpoli;
    private widget.TextBox lmsakit;
    private widget.ComboBox menitm1;
    private widget.ComboBox menitm2;
    private widget.ComboBox menitp1;
    private widget.ComboBox menitp2;
    private widget.ComboBox menits1;
    private widget.ComboBox menits2;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa1;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa3;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private javax.swing.JMenuItem ppGrafikDemografi;
    private javax.swing.JMenuItem ppGrafikPerAgama;
    private javax.swing.JMenuItem ppGrafikPerBulan;
    private javax.swing.JMenuItem ppGrafikPerJK;
    private javax.swing.JMenuItem ppGrafikPerPekerjaan;
    private javax.swing.JMenuItem ppGrafikPerTahun;
    private javax.swing.JMenuItem ppGrafikPerTanggal;
    private javax.swing.JMenuItem ppGrafikPerdokter;
    private javax.swing.JMenuItem ppGrafikPerpoli;
    private widget.TextBox ptgm;
    private widget.TextBox ptgp;
    private widget.TextBox ptgs;
    private widget.Table tbPetugas;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);   
        try{  
            ps.setString(1,"%"+CrPoli.getText()+"%");
            ps.setString(2,"%"+CrDokter.getText()+"%");
            ps.setString(3,DTPCari1.getSelectedItem().toString());
            ps.setString(4,DTPCari2.getSelectedItem().toString());
            ps.setString(5,"%"+TCari.getText().trim()+"%");
            ps.setString(6,"%"+CrPoli.getText()+"%");
            ps.setString(7,"%"+CrDokter.getText()+"%");
            ps.setString(8,DTPCari1.getSelectedItem().toString());
            ps.setString(9,DTPCari2.getSelectedItem().toString());
            ps.setString(10,"%"+TCari.getText().trim()+"%");
            ps.setString(11,"%"+CrPoli.getText()+"%");
            ps.setString(12,"%"+CrDokter.getText()+"%");
            ps.setString(13,DTPCari1.getSelectedItem().toString());
            ps.setString(14,DTPCari2.getSelectedItem().toString());
            ps.setString(15,"%"+TCari.getText().trim()+"%");
            ps.setString(16,"%"+CrPoli.getText()+"%");
            ps.setString(17,"%"+CrDokter.getText()+"%");
            ps.setString(18,DTPCari1.getSelectedItem().toString());
            ps.setString(19,DTPCari2.getSelectedItem().toString());
            ps.setString(20,"%"+TCari.getText().trim()+"%");
            ps.setString(21,"%"+CrPoli.getText()+"%");
            ps.setString(22,"%"+CrDokter.getText()+"%");
            ps.setString(23,DTPCari1.getSelectedItem().toString());
            ps.setString(24,DTPCari2.getSelectedItem().toString());
            ps.setString(25,"%"+TCari.getText().trim()+"%");
            ps.setString(26,"%"+CrPoli.getText()+"%");
            ps.setString(27,"%"+CrDokter.getText()+"%");
            ps.setString(28,DTPCari1.getSelectedItem().toString());
            ps.setString(29,DTPCari2.getSelectedItem().toString());
            ps.setString(30,"%"+TCari.getText().trim()+"%");
            ps.setString(31,"%"+CrPoli.getText()+"%");
            ps.setString(32,"%"+CrDokter.getText()+"%");
            ps.setString(33,DTPCari1.getSelectedItem().toString());
            ps.setString(34,DTPCari2.getSelectedItem().toString());
            ps.setString(35,"%"+TCari.getText().trim()+"%");
            ps.setString(36,"%"+CrPoli.getText()+"%");
            ps.setString(37,"%"+CrDokter.getText()+"%");
            ps.setString(38,DTPCari1.getSelectedItem().toString());
            ps.setString(39,DTPCari2.getSelectedItem().toString());
            ps.setString(40,"%"+TCari.getText().trim()+"%");
            ps.setString(41,"%"+CrPoli.getText()+"%");
            ps.setString(42,"%"+CrDokter.getText()+"%");
            ps.setString(43,DTPCari1.getSelectedItem().toString());
            ps.setString(44,DTPCari2.getSelectedItem().toString());
            ps.setString(45,"%"+TCari.getText().trim()+"%");
            ps.setString(46,"%"+CrPoli.getText()+"%");
            ps.setString(47,"%"+CrDokter.getText()+"%");
            ps.setString(48,DTPCari1.getSelectedItem().toString());
            ps.setString(49,DTPCari2.getSelectedItem().toString());
            ps.setString(50,"%"+TCari.getText().trim()+"%");
            ps.setString(51,"%"+CrPoli.getText()+"%");
            ps.setString(52,"%"+CrDokter.getText()+"%");
            ps.setString(53,DTPCari1.getSelectedItem().toString());
            ps.setString(54,DTPCari2.getSelectedItem().toString());
            ps.setString(55,"%"+TCari.getText().trim()+"%");
            ps.setString(56,"%"+CrPoli.getText()+"%");
            ps.setString(57,"%"+CrDokter.getText()+"%");
            ps.setString(58,DTPCari1.getSelectedItem().toString());
            ps.setString(59,DTPCari2.getSelectedItem().toString());
            ps.setString(60,"%"+TCari.getText().trim()+"%");
            ps.setString(61,"%"+CrPoli.getText()+"%");
            ps.setString(62,"%"+CrDokter.getText()+"%");
            ps.setString(63,DTPCari1.getSelectedItem().toString());
            ps.setString(64,DTPCari2.getSelectedItem().toString());
            ps.setString(65,"%"+TCari.getText().trim()+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                tabMode.addRow(new String[] {rs.getString(1),
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
                               rs.getString(12),
                               rs.getString(13),
                               rs.getString(14),
                               Valid.SetAngka(rs.getDouble(15)),
                               rs.getString(16),
                               rs.getString(17)});
            }                    
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TNoReg.setText("");
        TNoRw.setText("");
        Kd2.setText("");
        //DTPReg.setDate(new Date());
        TNoRM.setText("");
        TPasien.setText("");
        TPngJwb.setText("");
        THbngn.setText("");
        TAlmt.setText("");
        TStatus.setText("");
        isNumber();       
        TNoRM.requestFocus();
        kdpnj.setText("");
        nmpnj.setText("");
    }

    private void getData() {
        if(tbPetugas.getSelectedRow()!= -1){
            TNoReg.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),0).toString());
            TNoRw.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            Kd2.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            Valid.SetTgl(DTPReg,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
            CmbJam.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString().substring(0,2));
            CmbMenit.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString().substring(3,5));
            CmbDetik.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString().substring(6,8));
            kddokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString());
            TDokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),5).toString());
            TNoRM.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),6).toString());            
            isPas();
            TPoli.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),10).toString());
            TPngJwb.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),11).toString());
            TAlmt.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),12).toString());
            THbngn.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),13).toString());
            TBiaya.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),14).toString());
            TStatus.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),15).toString());            
            nmpnj.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),16).toString());
            Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?", kdpnj,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?", kdpoli,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            isPtg();
        }
    }


    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
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

    private void isPas(){
        try {            
            ps3.setString(1,DTPReg.getSelectedItem().toString());
            ps3.setString(2,TNoRM.getText());
            rs=ps3.executeQuery();
            while(rs.next()){
                TPasien.setText(rs.getString("nm_pasien"));
                TAlmt.setText(rs.getString("asal"));
                TPngJwb.setText(rs.getString("namakeluarga"));
                THbngn.setText(rs.getString("keluarga"));
                kdpnj.setText(rs.getString("kd_pj"));
                nmpnj.setText(rs.getString("png_jawab"));
                TStatus.setText(rs.getString("daftar"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }

    private void isPtg(){
        if(pilihan==1){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(pilihan==2){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",CrDokter,dokter.getTextField().getText());
            tampil();
        }
    }
    
    private void isPetugas(){
        if(pilihan==1){
            Sequel.cariIsi("select nama from petugas where nip=?",ptgp,petugas.getTextField().getText());
        }else if(pilihan==2){
            Sequel.cariIsi("select nama from petugas where nip=?",ptgs,petugas.getTextField().getText());
        }else if(pilihan==3){
            Sequel.cariIsi("select nama from petugas where nip=?",ptgm,petugas.getTextField().getText());
        }
    }
    

    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    private void isPoli(){
        if(pilihan==1){
              Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?", TPoli,kdpoli.getText());
              Sequel.cariIsi("select registrasi from poliklinik where kd_poli=?",TBiaya,kdpoli.getText());
        }else if(pilihan==2){
              Sequel.cariIsi("select nm_poli from poliklinik where kd_poli=?",CrPoli,poli.getTextField().getText());
              tampil();
        }
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
        DTPReg.setDate(new Date());
        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        BtnSimpan.setEnabled(var.getregistrasi());
        BtnHapus.setEnabled(var.getregistrasi());
        BtnEdit.setEnabled(var.getregistrasi());
        BtnPrint.setEnabled(var.getregistrasi());
        MnBilling.setEnabled(var.getbilling_ralan());
        MnKamarInap.setEnabled(var.getkamin());
        MnPemberianObat.setEnabled(var.getberiobat());
        MnRawatJalan.setEnabled(var.getralan());
        MnResepObat.setEnabled(var.getresep());
        MnRujuk.setEnabled(var.getrujuk());
        MnPeriksaLab.setEnabled(var.getperiksa_lab());
    }
    
    private void isNumber(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+DTPReg.getSelectedItem()+"'",
                             "",3,TNoReg); 
        if(Kd2.getText().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+DTPReg.getSelectedItem()+"' ",dateformat.format(DTPReg.getDate())+"/",6,TNoRw);           
        }            
    }
}
