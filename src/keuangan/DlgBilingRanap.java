

package keuangan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgReturJual;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
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
import khanzahms.DlgPemberianDiet;
import khanzahms.DlgPemberianObat;
import khanzahms.DlgPeriksaLaboratorium;
import khanzahms.DlgRawatInap;
import khanzahms.DlgRawatJalan;
import khanzahms.DlgResepObat;
import khanzahms.DlgResepPulang;

/**
 *
 * @author perpustakaan
 */
public class DlgBilingRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabModeRwJlDr,tabModeTambahan,tabModePotongan,tabModeKamIn;

    public DlgPemberianObat beriobat=new DlgPemberianObat(null,false);
    public DlgRawatInap rawatinap=new DlgRawatInap(null,false);
    public DlgPeriksaLaboratorium periksalab=new DlgPeriksaLaboratorium(null,false);
    public DlgResepPulang reseppulang=new DlgResepPulang(null,false);
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pscekbilling,pscarirm,pscaripasien,psreg,pskamar,pscarialamat,psbiling,
            psdokterranap,psdokterralan,pscariobat,psobatlangsung,psobatoperasi,psreturobat,psdetaillab,
            pskamarin,psbiayasekali,psbiayaharian,psreseppulang,pstambahanbiaya,pspotonganbiaya,pstemporary,
            psralandokter,psranapdokter,psoperasi,psralanperawat,psranapperawat,psperiksalab,pssudahmasuk;
    private ResultSet rscekbilling,rscarirm,rscaripasien,rsreg,rskamar,rscarialamat,rsdetaillab,
            rsdokterranap,rsdokterralan,rscariobat,rsobatlangsung,rsobatoperasi,rsreturobat,
            rskamarin,rsbiayasekali,rsbiayaharian,rsreseppulang,rstambahanbiaya,rspotonganbiaya,
            rsralandokter,rsranapdokter,rsoperasi,rsralanperawat,rsranapperawat,rsperiksalab,rs2,rs3,rs4,rs5,rs6,rs8;
    private String biaya="",tambahan="",totals="";

    /** Creates new form DlgBiling
     * @param parent
     * @param modal */
    public DlgBilingRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(891,640);

        Object[] rowRwJlDr={"Pilih",
                            "Keterangan",
                            "Tagihan/Tindakan/Terapi",
                            "",
                            "Biaya",
                            "Jumlah",
                            "Tambahan",
                            "Total Biaya",""};
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
                column.setPreferredWidth(130);
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
        Object[] rowTambahan={"Tambahan Biaya",
                    "Besar Biaya"};
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
        Object[] rowPotongan={"Potongan Biaya",
                    "Besar Potongan"};
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

        //ubah lama inap
        Object[] rowUbahLama={"Kode Kamar","Nama Kamar","Lama Inap"};
        tabModeKamIn=new DefaultTableModel(null,rowUbahLama){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                 boolean a = false;
                 if (colIndex==2) {
                      a=true;
                 }
                 return a;
             }
              
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                  return types [columnIndex];
             }
        };
        tbUbahLama.setModel(tabModeKamIn);

        tbUbahLama.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbUbahLama.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        for (i = 0; i < 3; i++) {
            TableColumn column = tbUbahLama.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(350);
            }else if(i==2){
                column.setPreferredWidth(150);
            }
        }

        tbUbahLama.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TBayar.setDocument(new batasInput((byte)17).getOnlyAngka(TBayar));
        
        TBayar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void removeUpdate(DocumentEvent e) {isKembali();}
            @Override
            public void changedUpdate(DocumentEvent e) {isKembali();}
        });
           
      jLabel7.setVisible(false);
      DTPTempo.setVisible(false);
      
      try {
            pscekbilling=koneksi.prepareStatement("select count(billing.no_rawat) from billing where billing.no_rawat=?");
            pscarirm=koneksi.prepareStatement("select no_rkm_medis from reg_periksa where no_rawat=?");
            pscaripasien=koneksi.prepareStatement("select nm_pasien from pasien where no_rkm_medis=? ");
            psreg=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,DATE_FORMAT(reg_periksa.tgl_registrasi, '%e %M %Y') as registrasi,kamar_inap.kd_kamar,if(kamar_inap.tgl_keluar='0000-00-00',DATE_FORMAT(CURDATE(), '%e %M %Y'),DATE_FORMAT(kamar_inap.tgl_keluar, '%e %M %Y')) as keluar,  "+
                    "(to_days(if(kamar_inap.tgl_keluar='0000-00-00',CURDATE(),kamar_inap.tgl_keluar))-to_days(reg_periksa.tgl_registrasi)) as lama "+
                    "from reg_periksa inner join kamar_inap on reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.no_rawat=? "+
                    "order by kamar_inap.tgl_keluar desc limit 1");
            pskamar=koneksi.prepareStatement("select concat(kamar.kd_kamar,', ',bangsal.nm_bangsal) from bangsal inner join kamar "+
                                           "on kamar.kd_bangsal=bangsal.kd_bangsal where kamar.kd_kamar=?");
            pscarialamat=koneksi.prepareStatement("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten on pasien.kd_kel=kelurahan.kd_kel "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");
            psdokterranap=koneksi.prepareStatement("select dokter.nm_dokter from rawat_inap_dr "+
                            "inner join dokter on rawat_inap_dr.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=? group by rawat_inap_dr.kd_dokter");
            psdokterralan=koneksi.prepareStatement("select dokter.nm_dokter from rawat_jl_dr "+
                            "inner join dokter on rawat_jl_dr.kd_dokter=dokter.kd_dokter "+
                            "where no_rawat=? group by rawat_jl_dr.kd_dokter");
            pscariobat=koneksi.prepareStatement("select databarang.nama_brng,detail_pemberian_obat.biaya_obat,"+
                                           "sum(detail_pemberian_obat.jml) as jml,sum(detail_pemberian_obat.tambahan) as tambahan,"+
                                           "sum(detail_pemberian_obat.total) as total "+
                                           "from detail_pemberian_obat inner join databarang "+
                                           "on detail_pemberian_obat.kode_brng=databarang.kode_brng where "+
                                           "detail_pemberian_obat.no_rawat=? group by databarang.nama_brng");
            psobatlangsung=koneksi.prepareStatement("select besar_tagihan from tagihan_obat_langsung where "+
                    "no_rawat=? ");
            psobatoperasi=koneksi.prepareStatement("select obatbhp_ok.nm_obat,beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah, "+
                                           "(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                           "from obatbhp_ok inner join beri_obat_operasi "+
                                           "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat where "+
                                           "beri_obat_operasi.no_rawat=? group by obatbhp_ok.nm_obat");
            psreturobat=koneksi.prepareStatement(
                     "select databarang.nama_brng,detreturjual.h_retur, "+
                     "(detreturjual.jml_retur * -1) as jml, "+
                     "(detreturjual.subtotal * -1) as ttl from detreturjual inner join databarang inner join returjual "+
                     "on detreturjual.kode_brng=databarang.kode_brng "+
                     "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_rkm_medis=? "+
                     "and returjual.tgl_retur=? group by databarang.nama_brng");
            pskamarin=koneksi.prepareStatement(
                                           "select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar.trf_kamar,"+
                                           "(IFNULL(to_days(concat(tgl_keluar,' ',jam_keluar))-to_days(concat(tgl_masuk,' ',jam_masuk)),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk)))) as lama, "+
                                           "(IFNULL(to_days(concat(tgl_keluar,' ',jam_keluar))-to_days(concat(tgl_masuk,' ',jam_masuk)),to_days(NOW())-to_days(concat(tgl_masuk,' ',jam_masuk))))*kamar.trf_kamar as total "+
                                           "from kamar_inap inner join bangsal inner join kamar "+
                                           "on kamar_inap.kd_kamar=kamar.kd_kamar "+
                                           "and kamar.kd_bangsal=bangsal.kd_bangsal where "+
                                           "kamar_inap.no_rawat=? order by kamar_inap.tgl_masuk,kamar_inap.kd_kamar");
            psbiayasekali=koneksi.prepareStatement(
                        "select nama_biaya,besar_biaya,(besar_biaya*1) as total from biaya_sekali "+
                        " where kd_kamar=? order by nama_biaya");
            psbiayaharian=koneksi.prepareStatement(
                        "select nama_biaya,besar_biaya,jml,(jml*besar_biaya*?) as total from biaya_harian "+
                        " where kd_kamar=? order by nama_biaya");
            psreseppulang=koneksi.prepareStatement("select databarang.nama_brng,resep_pulang.harga,"+
                                           "resep_pulang.jml_barang,resep_pulang.dosis,resep_pulang.total "+
                                           "from resep_pulang inner join databarang "+
                                           "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                           "resep_pulang.no_rawat=? order by databarang.nama_brng");            
            pstambahanbiaya=koneksi.prepareStatement("select nama_biaya, besar_biaya from tambahan_biaya where no_rawat=?  ");
            pspotonganbiaya=koneksi.prepareStatement("select nama_pengurangan, besar_pengurangan from pengurangan_biaya where no_rawat=?  ");
            psralandokter=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrdr,count(jns_perawatan.nm_perawatan) as jml, "+
                                               "jns_perawatan.total_byrdr*count(jns_perawatan.nm_perawatan) as biaya "+
                                               "from rawat_jl_dr inner join jns_perawatan "+
                                               "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                                               "rawat_jl_dr.no_rawat=? group by jns_perawatan.nm_perawatan");
            psranapdokter=koneksi.prepareStatement("select jns_perawatan_inap.nm_perawatan,jns_perawatan_inap.total_byrdr,count(jns_perawatan_inap.nm_perawatan) as jml, "+
                                               "jns_perawatan_inap.total_byrdr*count(jns_perawatan_inap.nm_perawatan) as biaya "+
                                               "from rawat_inap_dr inner join jns_perawatan_inap "+
                                               "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "+
                                               "rawat_inap_dr.no_rawat=? group by jns_perawatan_inap.nm_perawatan");
            psoperasi=koneksi.prepareStatement("select paket_operasi.nm_perawatan,(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                                "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+"+
                                                "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                                "operasi.biayaasisten_anestesi+operasi.biayabidan+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                                "operasi.biayasewaok+operasi.biayasewavk+operasi.bagian_rs+operasi.omloop) as biaya "+
                                                "from operasi inner join paket_operasi "+
                                                "on operasi.kode_paket=paket_operasi.kode_paket where "+
                                                "operasi.no_rawat=?");
            psralanperawat=koneksi.prepareStatement("select jns_perawatan.nm_perawatan,jns_perawatan.total_byrpr,count(jns_perawatan.nm_perawatan) as jml, "+
                                           "jns_perawatan.total_byrpr*count(jns_perawatan.nm_perawatan) as biaya "+
                                           "from rawat_jl_pr inner join jns_perawatan "+
                                           "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                                           "rawat_jl_pr.no_rawat=? group by jns_perawatan.nm_perawatan ");
            psranapperawat=koneksi.prepareStatement("select jns_perawatan_inap.nm_perawatan,jns_perawatan_inap.total_byrpr,count(jns_perawatan_inap.nm_perawatan) as jml, "+
                                           "jns_perawatan_inap.total_byrpr*count(jns_perawatan_inap.nm_perawatan) as biaya "+
                                           "from rawat_inap_pr inner join jns_perawatan_inap "+
                                           "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "+
                                           "rawat_inap_pr.no_rawat=? group by jns_perawatan_inap.nm_perawatan");
            psperiksalab=koneksi.prepareStatement(
                    "select jns_perawatan_lab.nm_perawatan, count(periksa_lab.kd_jenis_prw) as jml,jns_perawatan_lab.total_byr as biaya, "+
                    "sum(periksa_lab.biaya) as total,jns_perawatan_lab.kd_jenis_prw "+
                    " from periksa_lab inner join jns_perawatan_lab "+
                    " on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw where "+
                    " periksa_lab.no_rawat=? group by jns_perawatan_lab.nm_perawatan  ");
            psdetaillab=koneksi.prepareStatement("select sum(detail_periksa_lab.biaya_item) as total from detail_periksa_lab where detail_periksa_lab.no_rawat=? "+
                    "and detail_periksa_lab.kd_jenis_prw=?");
            pssudahmasuk=koneksi.prepareStatement("select no,nm_perawatan, if(biaya<>0,biaya,'') as satu, if(jumlah<>0,jumlah,'') as dua,"+
                        "if(tambahan<>0,tambahan,'') as tiga, if(totalbiaya<>0,totalbiaya,'') as empat,pemisah,status "+
                        "from billing where no_rawat=?  order by noindex");
            psbiling=koneksi.prepareStatement("insert into billing values('0',?,?,?,?,?,?,?,?,?,?)");
            pstemporary=koneksi.prepareStatement(
                            "insert into temporary_bayar_ranap values('0',?,?,?,?,?,?,?,?,'','','','','','','','','')");
        } catch (SQLException e) {
            System.out.println(e);
        }
      
        
        beriobat.dlgobt.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {isRawat();}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        rawatinap.perawatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {isRawat();}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        reseppulang.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {isRawat();}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
    }
   
    private double ttl=0,y=0,subttl=0,uangmuka=0,sisapiutang,lab;
    private int x=0,z=0,i=0;
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnRawanInap = new javax.swing.JMenuItem();
        MnResepObat = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnDiet = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        ppTambahan = new javax.swing.JMenuItem();
        ppPotongan = new javax.swing.JMenuItem();
        MnUbahLamaInap = new javax.swing.JMenuItem();
        ppObat = new javax.swing.JMenuItem();
        MnReturJual = new javax.swing.JMenuItem();
        MnHapusTagihan = new javax.swing.JMenuItem();
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
        scrollPane2 = new widget.ScrollPane();
        tbPotongan = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        norawatpotongan = new widget.TextBox();
        BtnTambahPotongan = new widget.Button();
        BtnSimpanPotongan = new widget.Button();
        BtnHapusPotongan = new widget.Button();
        BtnKeluarPotongan = new widget.Button();
        WindowInput5 = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        scrollPane3 = new widget.ScrollPane();
        tbUbahLama = new widget.Table();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        norawatubahlama = new widget.TextBox();
        jLabel9 = new widget.Label();
        BtnSimpanUbahLama = new widget.Button();
        BtnKeluarUbahLama = new widget.Button();
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

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(java.awt.Color.darkGray);
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setIconTextGap(10);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawatJalan);

        MnRawanInap.setBackground(new java.awt.Color(255, 255, 255));
        MnRawanInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawanInap.setForeground(java.awt.Color.darkGray);
        MnRawanInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRawanInap.setText("Tagihan/Tindakan Rawat Inap");
        MnRawanInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawanInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawanInap.setIconTextGap(10);
        MnRawanInap.setName("MnRawanInap"); // NOI18N
        MnRawanInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawanInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawanInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawanInap);

        MnResepObat.setBackground(new java.awt.Color(255, 255, 255));
        MnResepObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepObat.setForeground(java.awt.Color.darkGray);
        MnResepObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnResepObat.setText("Resep Obat");
        MnResepObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepObat.setIconTextGap(10);
        MnResepObat.setName("MnResepObat"); // NOI18N
        MnResepObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnResepObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnResepObat);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(java.awt.Color.darkGray);
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setIconTextGap(10);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        MnDiet.setBackground(new java.awt.Color(255, 255, 255));
        MnDiet.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiet.setForeground(java.awt.Color.darkGray);
        MnDiet.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDiet.setText("Diet Pasien");
        MnDiet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiet.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiet.setIconTextGap(10);
        MnDiet.setName("MnDiet"); // NOI18N
        MnDiet.setPreferredSize(new java.awt.Dimension(250, 28));
        MnDiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDietActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDiet);

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

        MnUbahLamaInap.setBackground(new java.awt.Color(255, 255, 255));
        MnUbahLamaInap.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahLamaInap.setForeground(java.awt.Color.darkGray);
        MnUbahLamaInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahLamaInap.setText("Ubah Lama Inap");
        MnUbahLamaInap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnUbahLamaInap.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnUbahLamaInap.setIconTextGap(10);
        MnUbahLamaInap.setName("MnUbahLamaInap"); // NOI18N
        MnUbahLamaInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnUbahLamaInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahLamaInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbahLamaInap);

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
        MnHapusTagihan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnHapusTagihan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihan);

        WindowInput.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput.setName("WindowInput"); // NOI18N
        WindowInput.setUndecorated(true);
        WindowInput.setResizable(false);

        internalFrame2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Input Total BHP & Obat]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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
        panelisi1.add(BtnKeluar1);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowInput3.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Potongan Biaya ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

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

        internalFrame5.add(scrollPane2, java.awt.BorderLayout.CENTER);

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

        BtnKeluarPotongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarPotongan.setMnemonic('K');
        BtnKeluarPotongan.setText("Keluar");
        BtnKeluarPotongan.setToolTipText("Alt+K");
        BtnKeluarPotongan.setName("BtnKeluarPotongan"); // NOI18N
        BtnKeluarPotongan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarPotonganActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluarPotongan);

        internalFrame5.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        WindowInput5.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput5.setName("WindowInput5"); // NOI18N
        WindowInput5.setUndecorated(true);
        WindowInput5.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Manual Lama Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbUbahLama.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbUbahLama.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbUbahLama.setName("tbUbahLama"); // NOI18N
        scrollPane3.setViewportView(tbUbahLama);

        internalFrame6.add(scrollPane3, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("No.Rawat :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label17);

        norawatubahlama.setEditable(false);
        norawatubahlama.setName("norawatubahlama"); // NOI18N
        norawatubahlama.setPreferredSize(new java.awt.Dimension(150, 23));
        norawatubahlama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatubahlamaKeyPressed(evt);
            }
        });
        panelisi3.add(norawatubahlama);

        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi3.add(jLabel9);

        BtnSimpanUbahLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanUbahLama.setMnemonic('S');
        BtnSimpanUbahLama.setText("Simpan");
        BtnSimpanUbahLama.setToolTipText("Alt+S");
        BtnSimpanUbahLama.setName("BtnSimpanUbahLama"); // NOI18N
        BtnSimpanUbahLama.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanUbahLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanUbahLamaActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpanUbahLama);

        BtnKeluarUbahLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarUbahLama.setMnemonic('K');
        BtnKeluarUbahLama.setText("Keluar");
        BtnKeluarUbahLama.setToolTipText("Alt+K");
        BtnKeluarUbahLama.setName("BtnKeluarUbahLama"); // NOI18N
        BtnKeluarUbahLama.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarUbahLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarUbahLamaActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluarUbahLama);

        internalFrame6.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        WindowInput5.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Billing/Pembayaran Ranap Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
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
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-27" }));
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
        chkPiutang.setBounds(510, 10, 120, 23);

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
        DTPTempo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-27" }));
        DTPTempo.setDisplayFormat("yyyy-MM-dd");
        DTPTempo.setName("DTPTempo"); // NOI18N
        DTPTempo.setOpaque(false);
        DTPTempo.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTempo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTempoKeyPressed(evt);
            }
        });
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

        tbAdmin.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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
        WindowInput3.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnView,BtnSimpan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnView);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnViewActionPerformed
            Object[] options = {"Tagihan Masuk", "Piutang Pasien"};
            
            String input;
            int pilih = 0;
            try{
                input = (String)JOptionPane.showInputDialog(null,"Silahkan pilih yang mau ditampilkan!","Keuangan",JOptionPane.QUESTION_MESSAGE,null,options,"Nota 1");
                switch (input) {
                    case "Tagihan Masuk":
                        pilih=1;
                        break;
                    case "Piutang Pasien":
                        pilih=2;
                        break;
                }
            }catch(HeadlessException e){
                pilih=0;
            }        
            
            if(pilih>0){
                if(pilih==1){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgLhtBiaya billing=new DlgLhtBiaya(null,false);
                    billing.tampil();        
                    billing.setSize(this.getWidth(),this.getHeight());
                    billing.setLocationRelativeTo(this);
                    billing.setAlwaysOnTop(false);
                    billing.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else if(pilih==2){
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
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
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
                int kolom=tbAdmin.getSelectedColumn();
                if(kolom==1){
                    switch (tbAdmin.getValueAt(tbAdmin.getSelectedRow(), kolom).toString()) {
                        case "Tindakan Dokter":
                            rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true);
                            rawatinap.perawatan.emptTeks();
                            //perawatan.setModal(true);
                            rawatinap.perawatan.isCek();
                            rawatinap.perawatan.tampil();
                            rawatinap.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                            rawatinap.perawatan.setAlwaysOnTop(false);
                            rawatinap.perawatan.setVisible(true);
                            break;
                        case "Tindakan":
                            rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),"00","00","00",true);
                            rawatinap.perawatan.emptTeks();
                            //perawatan.setModal(true);
                            rawatinap.perawatan.isCek();
                            rawatinap.perawatan.tampil();
                            rawatinap.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                            rawatinap.perawatan.setAlwaysOnTop(false);
                            rawatinap.perawatan.setVisible(true);
                            break;
                        case "Resep Pulang":
                            reseppulang.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                            reseppulang.emptTeks();
                            reseppulang.isCek();
                            //reseppulang.setModal(true);
                            reseppulang.tampil(" order by databarang.nama_brng");
                            reseppulang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            reseppulang.setLocationRelativeTo(internalFrame1);
                            reseppulang.setAlwaysOnTop(false);
                            reseppulang.setVisible(true);
                            break;
                        case "Obat & BHP":
                            beriobat.dlgobt.emptTeks();
                            beriobat.dlgobt.isCek();
                            //dlgobt.setModal(true);
                            beriobat.dlgobt.setNoRm(TNoRw.getText(),"-",DTPTgl.getDate(),"00","00","00",true);
                            beriobat.dlgobt.tampil();
                            beriobat.dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                            beriobat.dlgobt.setAlwaysOnTop(false);
                            beriobat.dlgobt.setVisible(true);
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
                int kolom=tbAdmin.getSelectedColumn();
                if(kolom==1){
                    switch (tbAdmin.getValueAt(tbAdmin.getSelectedRow(), kolom).toString()) {
                        case "Tindakan Dokter":
                            rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_dr",DTPTgl.getDate(),"00","00","00",true);
                            rawatinap.perawatan.emptTeks();
                            //perawatan.setModal(true);
                            rawatinap.perawatan.isCek();
                            rawatinap.perawatan.tampil();
                            rawatinap.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                            rawatinap.perawatan.setAlwaysOnTop(false);
                            rawatinap.perawatan.setVisible(true);
                            break;
                        case "Tindakan":
                            rawatinap.perawatan.setNoRm(TNoRw.getText(),"rawat_inap_pr",DTPTgl.getDate(),"00","00","00",true);
                            rawatinap.perawatan.emptTeks();
                            //perawatan.setModal(true);
                            rawatinap.perawatan.isCek();
                            rawatinap.perawatan.tampil();
                            rawatinap.perawatan.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            rawatinap.perawatan.setLocationRelativeTo(internalFrame1);
                            rawatinap.perawatan.setAlwaysOnTop(false);
                            rawatinap.perawatan.setVisible(true);
                            break;
                        case "Resep Pulang":
                            reseppulang.setNoRm(TNoRw.getText(),"-",Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat=?",TNoRw.getText()),
                                    Sequel.cariIsi("select jam_reg from reg_periksa where no_rawat=?",TNoRw.getText()));
                            reseppulang.emptTeks();
                            reseppulang.isCek();
                            //reseppulang.setModal(true);
                            reseppulang.tampil(" order by databarang.nama_brng");
                            reseppulang.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            reseppulang.setLocationRelativeTo(internalFrame1);
                            reseppulang.setAlwaysOnTop(false);
                            reseppulang.setVisible(true);
                            break;
                        case "Obat & BHP":
                            beriobat.dlgobt.emptTeks();
                            beriobat.dlgobt.isCek();
                            //dlgobt.setModal(true);
                            beriobat.dlgobt.setNoRm(TNoRw.getText(),"-",DTPTgl.getDate(),"00","00","00",true);
                            beriobat.dlgobt.tampil();
                            beriobat.dlgobt.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                            beriobat.dlgobt.setLocationRelativeTo(internalFrame1);
                            beriobat.dlgobt.setAlwaysOnTop(false);
                            beriobat.dlgobt.setVisible(true);
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
            DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
            dlgrwjl.isCek();
            dlgrwjl.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgrwjl.setLocationRelativeTo(internalFrame1);
            dlgrwjl.emptTeks();
        
            dlgrwjl.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());    
            dlgrwjl.tampilDr();
            dlgrwjl.setAlwaysOnTop(false);
            dlgrwjl.setVisible(true);
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnRawanInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawanInapActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            rawatinap.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            rawatinap.setLocationRelativeTo(internalFrame1);
            rawatinap.emptTeks();    
            rawatinap.isCek();
            rawatinap.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());    
            rawatinap.tampilDr();
            rawatinap.setAlwaysOnTop(false);
            rawatinap.setVisible(true);
        }
}//GEN-LAST:event_MnRawanInapActionPerformed

private void MnResepObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgResepObat dlgro=new DlgResepObat(null,false);
            dlgro.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.emptTeks();
            dlgro.isCek();
            dlgro.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date());   
            dlgro.tampil();
            dlgro.setAlwaysOnTop(false);
            dlgro.setVisible(true);
        }
}//GEN-LAST:event_MnResepObatActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pasien belum dipilih...!!!");
            TNoRw.requestFocus();
        }else{
            DlgPemberianObat po=new DlgPemberianObat(null,false);
            po.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            po.setLocationRelativeTo(internalFrame1);
            po.emptTeks(); 
            po.isCek();
            po.setNoRm(TNoRw.getText(),DTPTgl.getDate(),new Date()); 
            po.tampilPO();
            po.setAlwaysOnTop(false);
            po.setVisible(true);
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
       Valid.pindah(evt,TNoRw,BtnSimpan);
}//GEN-LAST:event_DTPTglKeyPressed

private void MnHapusTagihanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanActionPerformed
     Valid.hapusTable(tabModeRwJlDr,TNoRw,"billing","no_rawat");         
         int reply2 = JOptionPane.showConfirmDialog(rootPane,"Anda sudah melakukan penghapusan data tagihan pasien.....!!!!\n"+
                                                             "Silahkan lakukan 'Jurnal Penyesuaian' agar transaksi akuntansi tetap balance.\n"+
                                                             "Anda ingin melakukan 'Jurnal Penyesuaian' sekarang...??",
                                                             "Konfirmasi",JOptionPane.YES_NO_OPTION);
         if (reply2 == JOptionPane.YES_OPTION) {
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

private void DTPTempoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTempoKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_DTPTempoKeyPressed

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
}//GEN-LAST:event_chkPiutangActionPerformed

private void MnDietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDietActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
      }else{
                DlgPemberianDiet form=new DlgPemberianDiet(null,false);
                form.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
                form.setLocationRelativeTo(internalFrame1);
                form.emptTeks(); 
                form.isCek();
                form.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate());
                form.tampil();
                form.setAlwaysOnTop(false);
                form.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnDietActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            periksalab.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
            periksalab.setLocationRelativeTo(internalFrame1);
            periksalab.emptTeks();
            periksalab.setNoRm(TNoRw.getText());  
            periksalab.tampil();
            periksalab.setAlwaysOnTop(false);
            periksalab.setVisible(true);
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void ppObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppObatActionPerformed
    TotalObat.setText(Sequel.cariIsi("select besar_tagihan from tagihan_obat_langsung where no_rawat=?",TNoRw.getText()));  
    WindowInput.setSize(590,80);
    WindowInput.setLocationRelativeTo(internalFrame1);
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
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            WindowInput.dispose();
        }else{Valid.pindah(evt, BtnBatal1, TotalObat);}
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
            isRawat();
            isKembali();
        }
}//GEN-LAST:event_BtnBatal1ActionPerformed

private void BtnBatal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatal1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBatal1ActionPerformed(null);
        }else{Valid.pindah(evt, BtnSimpan, BtnCloseIn);}
}//GEN-LAST:event_BtnBatal1KeyPressed

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
        for(int r=0;r<tbTambahan.getRowCount();r++){
            if(Valid.SetAngka(tbTambahan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("tambahan_biaya","'"+norawat.getText()+"','"+tbTambahan.getValueAt(r,0).toString()+
                        "','"+tbTambahan.getValueAt(r,1).toString()+"'","Tambahan Biaya");
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

private void ppTambahanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTambahanActionPerformed
    norawat.setText(TNoRw.getText());
    tampilTambahan(norawat.getText());
    WindowInput3.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    WindowInput3.setLocationRelativeTo(internalFrame1);
    WindowInput3.setAlwaysOnTop(false);
    WindowInput3.setVisible(true);
}//GEN-LAST:event_ppTambahanActionPerformed

private void MnReturJualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnReturJualActionPerformed
      var.setStatus(false);       
       this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgReturJual returjual=new DlgReturJual(null,false);
        returjual.emptTeks();
        returjual.tampil();  
        returjual.isCek(); 
        returjual.setPasien(Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRw.getText()));
        returjual.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        returjual.setLocationRelativeTo(internalFrame1);
        returjual.setAlwaysOnTop(false);
        returjual.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_MnReturJualActionPerformed

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
        for(int r=0;r<tbPotongan.getRowCount();r++){
            if(Valid.SetAngka(tbPotongan.getValueAt(r,1).toString())>0){
                Sequel.menyimpan("pengurangan_biaya","'"+norawatpotongan.getText()+"','"+tbPotongan.getValueAt(r,0).toString()+
                        "','"+tbPotongan.getValueAt(r,1).toString()+"'","Potongan Biaya");
            }
        }
        isRawat();
        isKembali();
        WindowInput4.dispose();
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
  WindowInput4.dispose();
}//GEN-LAST:event_BtnKeluarPotonganActionPerformed

private void ppPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPotonganActionPerformed
   norawatpotongan.setText(TNoRw.getText());
    tampilPotongan(norawatpotongan.getText());
    WindowInput4.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
    WindowInput4.setLocationRelativeTo(internalFrame1);
    WindowInput4.setAlwaysOnTop(false);
    WindowInput4.setVisible(true);
}//GEN-LAST:event_ppPotonganActionPerformed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        isRawat();
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCariKeyPressed

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
                    Sequel.queryu2("delete from temporary_bayar_ranap"); 
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
                    String alamat=Sequel.cariIsi("select alamat from pasien where no_rkm_medis=? ",TNoRM.getText());
                    if(chkPiutang.isSelected()==false){
                        Sequel.menyimpan("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+DTPTgl.getSelectedItem()+
                                "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+ttl+"','Belum'","No.Rawat dan Tanggal");
                        Sequel.menyimpan("temporary_bayar_ranap","'0','TOTAL BAYAR',':','','','','','"+TtlSemua.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                    }else if(chkPiutang.isSelected()==true){
                        if(TBayar.getText().equals("")){
                            Valid.textKosong(TBayar,"Uang Muka");
                        }else{
                            Sequel.menyimpan("tagihan_sadewa","'"+TNoRw.getText()+"','"+TNoRM.getText()+"','"+TPasien.getText()+"','"+alamat+"',concat('"+DTPTgl.getSelectedItem()+
                                "',' ',CURTIME()),'Pelunasan','"+ttl+"','"+TBayar.getText()+"','Belum'","No.Rawat dan Tanggal");
                            Sequel.menyimpan("temporary_bayar_ranap","'0','TOTAL TAGIHAN',':','','','','','"+TtlSemua.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                            Sequel.menyimpan("temporary_bayar_ranap","'0','UANG MUKA',':','','','','','"+Valid.SetAngka(Double.parseDouble(TBayar.getText()))+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                            Sequel.menyimpan("temporary_bayar_ranap","'0','SISA PIUTANG',':','','','','','"+TKembali.getText()+"','','','','','','','','','',''","Rekap Harian Tindakan Dokter"); 
                        }                    
                    }

                    if(chkPrint.isSelected()==true){     
                        //Valid.MyReport("rptNotaBayar.jrxml","report","::[ Nota Pembayaran ]::",
                       //     "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary_bayar_ralan order by no asc");
                        Valid.panggilUrl("billing/LaporanBilling2.php?petugas="+var.getkode().replaceAll(" ","_"));
                    }
                    koneksi.setAutoCommit(true);
                }catch(Exception ex){
                    System.out.println(ex);
                }                
               this.setCursor(Cursor.getDefaultCursor());
                
          }        
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void norawatubahlamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatubahlamaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_norawatubahlamaKeyPressed

    private void BtnSimpanUbahLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanUbahLamaActionPerformed
        if(norawatubahlama.getText().trim().equals("")||(tbUbahLama.getRowCount()<=0)){
            Valid.textKosong(norawatubahlama,"Data");
        }else{
            for(int r=0;r<tbUbahLama.getRowCount();r++){
                if(Valid.SetAngka(tbUbahLama.getValueAt(r,2).toString())>0){
                    Sequel.mengedit("kamar_inap","no_rawat='"+norawatubahlama.getText()+"' and kd_kamar='"+tbUbahLama.getValueAt(r,0)+"'",
                        "tgl_keluar=Date(date_add(concat(tgl_masuk,' ',jam_masuk),INTERVAL "+tbUbahLama.getValueAt(r,2).toString()+" DAY)),"+
                        "jam_keluar=time(date_add(concat(tgl_masuk,' ',jam_masuk),INTERVAL "+tbUbahLama.getValueAt(r,2).toString()+" DAY))");
                }
            }
            isRawat();
            isKembali();
            WindowInput5.dispose();
        }
    }//GEN-LAST:event_BtnSimpanUbahLamaActionPerformed

    private void BtnKeluarUbahLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarUbahLamaActionPerformed
        WindowInput5.dispose();
    }//GEN-LAST:event_BtnKeluarUbahLamaActionPerformed

    private void MnUbahLamaInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahLamaInapActionPerformed
        norawatubahlama.setText(TNoRw.getText());
        tampilUbahLama(norawatubahlama.getText());
        WindowInput5.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        WindowInput5.setLocationRelativeTo(internalFrame1);
        WindowInput5.setAlwaysOnTop(false);
        WindowInput5.setVisible(true);
    }//GEN-LAST:event_MnUbahLamaInapActionPerformed



    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBilingRanap dialog = new DlgBilingRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnHapusPotongan;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnKeluarPotongan;
    private widget.Button BtnKeluarUbahLama;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpan2;
    private widget.Button BtnSimpan3;
    private widget.Button BtnSimpanPotongan;
    private widget.Button BtnSimpanUbahLama;
    private widget.Button BtnTambah;
    private widget.Button BtnTambahPotongan;
    private widget.Button BtnView;
    private widget.Tanggal DTPTempo;
    private widget.Tanggal DTPTgl;
    private javax.swing.JMenuItem MnDiet;
    private javax.swing.JMenuItem MnHapusTagihan;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnRawanInap;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnResepObat;
    private javax.swing.JMenuItem MnReturJual;
    private javax.swing.JMenuItem MnUbahLamaInap;
    private widget.ScrollPane Scroll;
    public widget.TextBox TBayar;
    public widget.TextBox TKembali;
    private widget.TextBox TNoRM;
    public widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TotalObat;
    private widget.TextBox TtlSemua;
    private javax.swing.JDialog WindowInput;
    private javax.swing.JDialog WindowInput3;
    private javax.swing.JDialog WindowInput4;
    private javax.swing.JDialog WindowInput5;
    private widget.CekBox chkPiutang;
    private widget.CekBox chkPrint;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.TextBox norawat;
    private widget.TextBox norawatpotongan;
    private widget.TextBox norawatubahlama;
    private widget.panelisi panelGlass1;
    private widget.panelisi panelGlass2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppObat;
    private javax.swing.JMenuItem ppPotongan;
    private javax.swing.JMenuItem ppTambahan;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbAdmin;
    private widget.Table tbPotongan;
    private widget.Table tbTambahan;
    private widget.Table tbUbahLama;
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
         if(i<=0){
             prosesCariReg();                     
             prosesCariKamar();          
             prosesCariTindakanDokter();               
             prosesCariTindakanPerawat();                    
             prosesCariObat();    
             prosesResepPulang();
             prosesCariTambahan();  
             prosesCariPotongan();            
             tabModeRwJlDr.addRow(new Object[]{false,"","","","","","","","-"});
             tabModeRwJlDr.addRow(new Object[]{false,"","","","","","","","-"});
             isHitung();
         }else if(i>0){
             DTPTgl.setDate(Sequel.cariIsi2("select billing.tgl_byr from billing where billing.no_rawat='"+TNoRw.getText()+"'"));             
             Valid.tabelKosong(tabModeRwJlDr);
             try{                
                pssudahmasuk.setString(1,TNoRw.getText());
                rsreg=pssudahmasuk.executeQuery();
                while(rsreg.next()){
                        tabModeRwJlDr.addRow(new Object[]{true,rsreg.getString("no"),
                                    rsreg.getString("nm_perawatan"),
                                    rsreg.getString("pemisah"),
                                    rsreg.getString("satu"),
                                    rsreg.getString("dua"),
                                    rsreg.getString("tiga"),
                                    rsreg.getString("empat"),
                                    rsreg.getString("status")});  
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
            while(rsreg.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"No.Nota",": "+TNoRw.getText(),"","","","","","-"});
                
                pskamar.setString(1,rsreg.getString("kd_kamar"));
                rskamar=pskamar.executeQuery();
                if(rskamar.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Bangsal/Kamar",": "+rskamar.getString(1),"","","","","","-"});
                }                
                tabModeRwJlDr.addRow(new Object[]{true,"Tgl.Perawatan",": "+rsreg.getString("registrasi")+" s.d. "+rsreg.getString("keluar")+", Lama Rawat : "+rsreg.getString("lama")+" Hari","","","","","","-"});
                tabModeRwJlDr.addRow(new Object[]{true,"No.RM",": "+TNoRM.getText(),"","","","","","-"});
                tabModeRwJlDr.addRow(new Object[]{true,"Nama Pasien",": "+TPasien.getText(),"","","","","","-"});
                pscarialamat.setString(1,TNoRM.getText());
                rscarialamat=pscarialamat.executeQuery();
                if(rscarialamat.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Alamat Pasien",": "+rscarialamat.getString(1),"","","","","","-"});
                }                
                //cari dokter yang menangandi                
                psdokterranap.setString(1,TNoRw.getText());
                rsdokterranap=psdokterranap.executeQuery();                
                
                psdokterralan.setString(1,TNoRw.getText());
                rsdokterralan=psdokterralan.executeQuery();
                
                if(rsdokterralan.next()||rsdokterranap.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"Dokter ",":","","","","","","-"});  
                }
                x=1;
                rsdokterranap.beforeFirst();
                while(rsdokterranap.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsdokterranap.getString("nm_dokter"),"","","","","","Dokter"});x++;   
                }
                //rs2.close();
                rsdokterralan.beforeFirst();
                while(rsdokterralan.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsdokterralan.getString("nm_dokter"),"","","","","","Dokter"});x++;   
                }
                //rs3.close();
                //cari perawat yang menangandi
                /*PreparedStatement ps4=koneksi.prepareStatement("select petugas.nama from rawat_inap_pr "+
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
                x=1;
                rs4.beforeFirst();
                while(rs4.next()){
                    tabModeRwJlDr.addRow(new Object[]{false,"                           "+x+".",rs4.getString("nama"),"","","","","","Perawat"});x++;   
                }
                rs4.close();
                rs5.beforeFirst();
                while(rs5.next()){
                    tabModeRwJlDr.addRow(new Object[]{false,"                           "+x+".",rs5.getString("nama"),"","","","","","Perawat"});x++;   
                }
                rs5.close();
                tabModeRwJlDr.addRow(new Object[]{true,"Administrasi R.M.","",":","","","",rs.getString("biaya_reg"),"Registrasi"});*/
            }            
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
    }

    private void prosesCariObat() {
        tabModeRwJlDr.addRow(new Object[]{true,"Obat & BHP",":","","","","","","Obat"});
        x=1;
        subttl=0;
        try{
            pscariobat.setString(1,TNoRw.getText());
            rscariobat=pscariobat.executeQuery();
            while(rscariobat.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rscariobat.getString("nama_brng"),":",
                               rscariobat.getString("biaya_obat"),rscariobat.getString("jml"),rscariobat.getString("tambahan"),
                               rscariobat.getString("total"),"Obat"});x++;
                subttl=subttl+rscariobat.getDouble("total");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        try{            
            psobatlangsung.setString(1,TNoRw.getText());
            rsobatlangsung=psobatlangsung.executeQuery();
            x++;
            if(rsobatlangsung.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".","Obat & BHP",":","","","",rsobatlangsung.getString("besar_tagihan"),"Obat"});                
                subttl=subttl+rsobatlangsung.getDouble("besar_tagihan");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        try{            
            psobatoperasi.setString(1,TNoRw.getText());
            rsobatoperasi=psobatoperasi.executeQuery();
            while(rsobatoperasi.next()){
                 tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsobatoperasi.getString("nm_obat"),":",
                               rsobatoperasi.getString("hargasatuan"),rsobatoperasi.getString("jumlah"),"",
                               rsobatoperasi.getString("total"),"Obat"});x++;
                 subttl=subttl+rsobatoperasi.getDouble("total");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        if(x>1){
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Obat & BHP : "+Valid.SetAngka(subttl),"","","","","","TtlObat"});
        }
        
        x=1;
        subttl=0;        
        try{            
            psreturobat.setString(1,TNoRM.getText());
            psreturobat.setString(2,DTPTgl.getSelectedItem().toString());
            rsreturobat=psreturobat.executeQuery();
            if(rsreturobat.next()){                
                tabModeRwJlDr.addRow(new Object[]{true,"","Retur Obat :","","","","","","Retur Obat"});          
            }
            rsreturobat.beforeFirst();
            while(rsreturobat.next()){
                Object[] data={true,"                           "+x+".",rsreturobat.getString("nama_brng"),":",
                               rsreturobat.getString("h_retur"),rsreturobat.getString("jml"),"",
                               rsreturobat.getString("ttl"),"Retur Obat"};
                tabModeRwJlDr.addRow(data);x++;
                subttl=subttl+rsreturobat.getDouble("ttl");
            }
            //rs.close();
            //stat.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        if(x>1){
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Retur Obat : "+Valid.SetAngka(subttl),"","","","","","TtlRetur Obat"});
        }
    }


    private void prosesCariKamar() {
        tabModeRwJlDr.addRow(new Object[]{true,"Kamar Inap",":","","","","","","Kamar"}); 
        x=1;
        subttl=0;
        try{            
            pskamarin.setString(1,TNoRw.getText());
            rskamarin=pskamarin.executeQuery();
            while(rskamarin.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rskamarin.getString("kd_kamar")+", "+rskamarin.getString("nm_bangsal"),":",
                               rskamarin.getString("trf_kamar"),rskamarin.getString("lama"),"",rskamarin.getString("total"),"Kamar"});x++;
                subttl=subttl+rskamarin.getDouble("total");   
                
                psbiayasekali.setString(1,rskamarin.getString("kd_kamar"));
                rsbiayasekali=psbiayasekali.executeQuery();       
                if(rsbiayasekali.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"-","Biaya Kamar :","","","","","","Kamar"});          
                }
                rsbiayasekali.beforeFirst();
                z=1;
                while(rsbiayasekali.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsbiayasekali.getString("nama_biaya"),":",
                               rsbiayasekali.getString("besar_biaya"),"","",rsbiayasekali.getString("total"),"Kamar"});z++;  
                    subttl=subttl+rsbiayasekali.getDouble("total");   
                }
                //rs3.close();
                
                //biaya harian         
                psbiayaharian.setInt(1,rskamarin.getInt("lama"));             
                psbiayaharian.setString(2,rskamarin.getString("kd_kamar"));   
                rsbiayaharian=psbiayaharian.executeQuery();  
                if(rsbiayaharian.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"-","Biaya Harian :","","","","","","Kamar"});          
                }
                rsbiayaharian.beforeFirst();
                z=1;
                while(rsbiayaharian.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           ",rsbiayaharian.getString("nama_biaya"),":",
                               rsbiayaharian.getString("besar_biaya"),rsbiayaharian.getString("jml"),"",rsbiayaharian.getString("total"),"Kamar"});z++;     
                    subttl=subttl+rsbiayaharian.getDouble("total");   
                }
                //rs2.close();                
            }            
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        if(x>1){
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Kamar Inap : "+Valid.SetAngka(subttl),"","","","","","TtlKamar"});
        }
    }
    
    private void prosesResepPulang() {
        tabModeRwJlDr.addRow(new Object[]{true,"Resep Pulang",":","","","","","","Resep Pulang"});
        x=1;
        subttl=0;
        try{            
            psreseppulang.setString(1,TNoRw.getText());
            rsreseppulang=psreseppulang.executeQuery();
            while(rsreseppulang.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsreseppulang.getString("nama_brng")+" "+rsreseppulang.getString("dosis"),":",
                               rsreseppulang.getString("harga"),rsreseppulang.getDouble("jml_barang"),"",
                               rsreseppulang.getString("total"),"Resep Pulang"});x++;
                subttl=subttl+rsreseppulang.getDouble("total");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        if(x>1){ 
             tabModeRwJlDr.addRow(new Object[]{true,"","Total Resep Pulang : "+Valid.SetAngka(subttl),"","","","","","TtlResep Pulang"});
        }
    }


    public void isHitung() {              
        ttl=0;
        y=0;
        int row=tabModeRwJlDr.getRowCount();
        if(row>0){
            for(int r=0;r<row;r++){ 
            //System.out.println(r+". "+tabModeRwJlDr.getValueAt(r,7).toString());
                try {
                    y=Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());  
                } catch (Exception e) {
                   y=0;
                }  
                ttl=ttl+y;
            }
            TtlSemua.setText(Valid.SetAngka(ttl));            
        }     
    }

    
    public void isCek(){
        BtnSimpan.setEnabled(var.getbilling_ranap());
        BtnView.setEnabled(var.getbilling_ranap());
    }
    
    private void getData() {
        int row=tbAdmin.getSelectedRow();
        if(row!= -1){
            if((!tabModeRwJlDr.getValueAt(row,4).toString().equals(""))&&(!tabModeRwJlDr.getValueAt(row,5).toString().equals(""))){
               tabModeRwJlDr.setValueAt(Valid.SetAngka2(Valid.SetAngka(tabModeRwJlDr.getValueAt(row,4).toString())*Valid.SetAngka(tabModeRwJlDr.getValueAt(row,5).toString())+Valid.SetAngka(tabModeRwJlDr.getValueAt(row,6).toString())), row,7); 
            }
            
            ttl=0;
            y=0;

            int row2=tabModeRwJlDr.getRowCount();
            double obat=0,ranapdokter=0,ranapparamedis=0,tambahan=0,kamar=0,returobat=0,reseppulang=0;
            for(int r=0;r<row2;r++){ 
                if(!tabModeRwJlDr.getValueAt(r,7).toString().isEmpty()){
                    y=Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());  
                    switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                        case "Obat":
                            obat=obat+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Ranap Dokter":
                            ranapdokter=ranapdokter+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Ranap Paramedis":
                            ranapparamedis=ranapparamedis+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Tambahan":
                            tambahan=tambahan+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Retur Obat":
                            returobat=returobat+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());           
                            break;
                        case "Kamar":
                            kamar=kamar+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                        case "Resep Pulang":
                            reseppulang=reseppulang+Double.parseDouble(tabModeRwJlDr.getValueAt(r,7).toString());
                            break;
                    }                    
                }else if(tabModeRwJlDr.getValueAt(r,7).toString().isEmpty()){
                    y=0;                
                }
                ttl=ttl+y;
                switch (tabModeRwJlDr.getValueAt(r,8).toString()) {
                    case "TtlRanap Dokter":
                        tabModeRwJlDr.setValueAt("Total Tindakan Dokter : "+Valid.SetAngka(ranapdokter),r,2);
                        break;
                    case "TtlRanap Paramedis":
                        tabModeRwJlDr.setValueAt("Total Tindakan : "+Valid.SetAngka(ranapparamedis),r,2);
                        break;
                    case "TtlTambahan":
                        tabModeRwJlDr.setValueAt("Total Tambahan : "+Valid.SetAngka(tambahan),r,2);
                        break;
                    case "TtlObat":
                        tabModeRwJlDr.setValueAt("Total Obat & BHP : "+Valid.SetAngka(obat),r,2);
                        break;
                    case "TtlRetur Obat":
                        tabModeRwJlDr.setValueAt("Total Retur Obat : "+Valid.SetAngka(returobat),r,2);
                        break;         
                    case "TtlKamar":
                        tabModeRwJlDr.setValueAt("Total Kamar Inap : "+Valid.SetAngka(kamar),r,2);
                        break;
                    case "TtlResep Pulang":
                        tabModeRwJlDr.setValueAt("Total Resep Pulang : "+Valid.SetAngka(reseppulang),r,2);
                        break;
                }
            }
            TtlSemua.setText(Valid.SetAngka(ttl));
        }
    } 
    
    public void isKembali(){
       double bayar=0,total=0;
        if(!TBayar.getText().trim().equals("")) {
            bayar=Double.parseDouble(TBayar.getText()); 
        }
        if(ttl>0) {
            total=ttl; 
        }
        if(chkPiutang.isSelected()==false){
            TKembali.setText(Valid.SetAngka(bayar-total));            
        }else{
            TKembali.setText(Valid.SetAngka(ttl-Double.parseDouble(TBayar.getText()))); 
        }     
    }
    
    public void tampilTambahan(String NoRawat) {
        norawat.setText(NoRawat);
        Valid.tabelKosong(tabModeTambahan);
        try{
            pstambahanbiaya.setString(1,norawat.getText());
            rstambahanbiaya=pstambahanbiaya.executeQuery();
            while(rstambahanbiaya.next()){
                tabModeTambahan.addRow(new Object[]{rstambahanbiaya.getString(1),rstambahanbiaya.getString(2)});
            }       
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }            
    }
    
    public void tampilPotongan(String NoRawat) {
        norawatpotongan.setText(NoRawat);
        Valid.tabelKosong(tabModePotongan);
        try{            
            pspotonganbiaya.setString(1,norawatpotongan.getText());
            rspotonganbiaya=pspotonganbiaya.executeQuery();
            while(rspotonganbiaya.next()){
                tabModePotongan.addRow(new Object[]{rspotonganbiaya.getString(1),rspotonganbiaya.getString(2)});
            }       
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }            
    }

    private void prosesCariTindakanDokter(){
             tabModeRwJlDr.addRow(new Object[]{true,"Tindakan Dokter",":","","","","","","Ranap Dokter"});
             x=1;
             subttl=0;
             try{                
                psralandokter.setString(1,TNoRw.getText());
                rsralandokter=psralandokter.executeQuery();                
                while(rsralandokter.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsralandokter.getString("nm_perawatan"),":",
                                   rsralandokter.getString("total_byrdr"),rsralandokter.getString("jml"),"",rsralandokter.getString("biaya"),"Ranap Dokter"});x++;
                    subttl=subttl+rsralandokter.getDouble("biaya");
                }
                //rs.close();
             }catch(SQLException e){
                System.out.println("Error : "+e);
             }
             
             try{    
                psranapdokter.setString(1,TNoRw.getText());
                rsranapdokter=psranapdokter.executeQuery();
                while(rsranapdokter.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsranapdokter.getString("nm_perawatan"),":",
                                   rsranapdokter.getString("total_byrdr"),rsranapdokter.getString("jml"),"",rsranapdokter.getString("biaya"),"Ranap Dokter"});x++;
                    subttl=subttl+rsranapdokter.getDouble("biaya");
                }
                //rs.close();
             }catch(SQLException e){
                System.out.println("Error : "+e);
             }

             try{  
                psoperasi.setString(1,TNoRw.getText());
                rsoperasi=psoperasi.executeQuery();
                while(rsoperasi.next()){
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsoperasi.getString("nm_perawatan"),":",
                                   rsoperasi.getString("biaya"),"1","",rsoperasi.getString("biaya"),"Ranap Dokter"});x++;
                    subttl=subttl+rsoperasi.getDouble("biaya");
                }
                //rs.close();
             }catch(SQLException e){
                System.out.println("Error : "+e);
             }
             
             if(x>1){
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Tindakan Dokter : "+Valid.SetAngka(subttl),"","","","","","TtlRanap Dokter"});
             }
    }

    private void prosesCariTindakanPerawat() {
        tabModeRwJlDr.addRow(new Object[]{true,"Tindakan",":","","","","","","Ranap Paramedis"});
        x=1;
        subttl=0;
        try{            
            psralanperawat.setString(1,TNoRw.getText());
            rsralanperawat=psralanperawat.executeQuery();            
            while(rsralanperawat.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsralanperawat.getString("nm_perawatan"),":",
                               rsralanperawat.getString("total_byrpr"),rsralanperawat.getString("jml"),"",rsralanperawat.getString("biaya"),"Ranap Paramedis"});x++;
                subttl=subttl+rsralanperawat.getDouble("biaya");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        try{            
            psranapperawat.setString(1,TNoRw.getText());
            rsranapperawat=psranapperawat.executeQuery();
            while(rsranapperawat.next()){
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsranapperawat.getString("nm_perawatan"),":",
                               rsranapperawat.getString("total_byrpr"),rsranapperawat.getString("jml"),"",rsranapperawat.getString("biaya"),"Ranap Paramedis"});x++;
                subttl=subttl+rsranapperawat.getDouble("biaya");
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        try{
            psperiksalab.setString(1,TNoRw.getText());
            rsperiksalab=psperiksalab.executeQuery();
            while(rsperiksalab.next()){
                psdetaillab.setString(1,TNoRw.getText());
                psdetaillab.setString(2,rsperiksalab.getString("kd_jenis_prw"));
                rsdetaillab=psdetaillab.executeQuery();
                lab=0;
                while(rsdetaillab.next()){  
                    lab=rsdetaillab.getDouble("total");               
                }
                tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rsperiksalab.getString("nm_perawatan"),":",
                               rsperiksalab.getString("biaya"),rsperiksalab.getString("jml"),""+lab,""+(rsperiksalab.getDouble("total")+lab),"Ranap Paramedis"});x++;
                subttl=subttl+rsperiksalab.getDouble("total")+lab;
            }
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        
        if(x>1){
            tabModeRwJlDr.addRow(new Object[]{true,"","Total Tindakan : "+Valid.SetAngka(subttl),"","","","","","TtlRanap Paramedis"});
        }
    }

    private void prosesCariTambahan() {
             tabModeRwJlDr.addRow(new Object[]{true,"Tambahan Biaya",":","","","","","0","Tambahan"});       
             x=1;
             subttl=0;
             try {
                pstambahanbiaya.setString(1,TNoRw.getText());
                rstambahanbiaya=pstambahanbiaya.executeQuery();
                while(rstambahanbiaya.next()){                    
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rstambahanbiaya.getString("nama_biaya"),":",
                               rstambahanbiaya.getString("besar_biaya"),"1","",rstambahanbiaya.getString("besar_biaya"),"Tambahan"});x++;
                    subttl=subttl+rstambahanbiaya.getDouble("besar_biaya");
                }
                //ps.close();
             } catch (SQLException ex) {
                System.out.println("Error : "+ex);
             }
             if(x>1){
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Tambahan : "+Valid.SetAngka(subttl),"","","","","","TtlTambahan"});
             } 
    }
    
    private void prosesCariPotongan() {
             tabModeRwJlDr.addRow(new Object[]{true,"Potongan Biaya",":","","","","","0","Potongan"});       
             x=1;
             subttl=0;
             try {
                pspotonganbiaya.setString(1,TNoRw.getText());
                rspotonganbiaya=pspotonganbiaya.executeQuery();
                while(rspotonganbiaya.next()){                    
                    tabModeRwJlDr.addRow(new Object[]{true,"                           "+x+".",rspotonganbiaya.getString("nama_pengurangan"),":",
                               rspotonganbiaya.getString("besar_pengurangan"),"1","","-"+rspotonganbiaya.getString("besar_pengurangan"),"Potongan"});x++;
                    subttl=subttl+rspotonganbiaya.getDouble("besar_pengurangan");
                }
                //ps.close();
             } catch (SQLException ex) {
                System.out.println("Error : "+ex);
             }
             if(x>1){
                tabModeRwJlDr.addRow(new Object[]{true,"","Total Potongan : "+Valid.SetAngka(subttl),"","","","","","TtlPotongan"});
             } 
    }
    
    public void tampilUbahLama(String NoRawat) {
        norawatubahlama.setText(NoRawat);
        Valid.tabelKosong(tabModeKamIn);
        try{            
            pskamarin.setString(1,norawatubahlama.getText());
            rskamarin=pskamarin.executeQuery();
            while(rskamarin.next()){
                tabModeKamIn.addRow(new Object[]{rskamarin.getString("kd_kamar"),rskamarin.getString("nm_bangsal"),rskamarin.getString("lama")});
            }       
            //rs.close();
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }            
    }
}
