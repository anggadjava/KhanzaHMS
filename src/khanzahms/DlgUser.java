/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgUser.java
 *
 * Created on 22 Jun 10, 0:10:23
 */

package khanzahms;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgUser extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    

    /** Creates new form DlgUser
     * @param parent
     * @param modal */
    public DlgUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(706,674);

        Object[] row={"ID User",
                      "Pengguna",
                      "Password",
                      "Jabatan",
                      "Spesialis",
                      "Tagihan Operasi",
                      "Ktg.Prw",
                      "Jns.Prw",
                      "Poli",
                      "Bangsal",
                      "Kamar",
                      "Ktg.Pny",
                      "Penyakit",
                      "Obat",
                      "Obt.Pny",
                      "Petugas",
                      "Dokter",
                      "Jdw.Dokter",
                      "Pasien",
                      "Paket Operasi",
                      "Diet Pasien",
                      "Regristrasi",
                      "Kelahiran Bayi",
                      "Periksa Lab",
                      "Kmr.Inap",
                      "Billing Ralan",
                      "Billing Ranap",
                      "Kasir Ralan",
                      "Suplier",
                      "Satuan Barang",
                      "Rw.Jalan",
                      "Resep",
                      "Rujuk",
                      "Rw.Inap",
                      "Konversi Satuan",
                      "Br.Obat",
                      "Stok Opname",
                      "Pembelian",
                      "Penjualan",
                      "Piutang",
                      "Bayar Piutang",
                      "Retur Jual",
                      "Retur Beli",
                      "Retur Piutang",
                      "Keuntungan Penjualan",
                      "Sirkulasi Barang",
                      "Akun Rekening",
                      "Rekening Tahun",
                      "Pas.Meninggal",
                      "Posting Jurnal",
                      "Buku Besar",
                      "Cash Flow",
                      "Laba Rugi" };
        
        tabMode=new DefaultTableModel(null,row){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){
                  return false;
              }              
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbUser.setModel(tabMode);
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbUser.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbUser.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 53; i++) {
            TableColumn column = tbUser.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbUser.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)30).getKata(TKd));
        TNm.setDocument(new batasInput((byte)50).getKata(TNm));

        TKd.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                isUser();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isUser();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isUser();
            }
        });

        dlgdok.setLocationRelativeTo(null);
        dlgptg.setLocationRelativeTo(null);
        
        dlgptg.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                TKd.setText(dlgptg.getTextField().getText());
                Sequel.cariIsi("select nama from petugas where nip='"+TKd.getText()+"'",TNm);
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
        
        dlgdok.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) { 
                TKd.setText(dlgdok.getTextField().getText());
                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter='"+TKd.getText()+"'",TNm);
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
    }

    DlgCariDokter dlgdok=new DlgCariDokter(null,false);
    DlgCariPetugas dlgptg=new DlgCariPetugas(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do falseT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbUser = new widget.Table();
        jPanel13 = new javax.swing.JPanel();
        scrollPane1 = new widget.ScrollPane();
        jPanel7 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        ktg_penyakit = new widget.CekBox();
        obat = new widget.CekBox();
        penyakit = new widget.CekBox();
        bangsal = new widget.CekBox();
        kamar = new widget.CekBox();
        ktg_prw = new widget.CekBox();
        jns_prw = new widget.CekBox();
        spesialis = new widget.CekBox();
        dokter = new widget.CekBox();
        jadwal = new widget.CekBox();
        jabatan = new widget.CekBox();
        petugas = new widget.CekBox();
        poli = new widget.CekBox();
        pasien = new widget.CekBox();
        reg = new widget.CekBox();
        rw_jln = new widget.CekBox();
        kamar_inap = new widget.CekBox();
        rw_inp = new widget.CekBox();
        resep = new widget.CekBox();
        rujuk = new widget.CekBox();
        br_obat = new widget.CekBox();
        pasien_mati = new widget.CekBox();
        tagihan_operasi = new widget.CekBox();
        obat_penyakit = new widget.CekBox();
        paket_operasi = new widget.CekBox();
        diet_pasien = new widget.CekBox();
        kelahiran_bayi = new widget.CekBox();
        periksa_lab = new widget.CekBox();
        billing_ralan = new widget.CekBox();
        billing_ranap = new widget.CekBox();
        kasirralan = new widget.CekBox();
        suplier = new widget.CekBox();
        satuan = new widget.CekBox();
        konversi = new widget.CekBox();
        stok_opname = new widget.CekBox();
        pembelian = new widget.CekBox();
        penjualan = new widget.CekBox();
        piutang = new widget.CekBox();
        bayar_piutang = new widget.CekBox();
        retur_jual = new widget.CekBox();
        retur_beli = new widget.CekBox();
        retur_piutang = new widget.CekBox();
        keuntungan_jual = new widget.CekBox();
        sirkulasi = new widget.CekBox();
        akun_rekening = new widget.CekBox();
        rekening_tahun = new widget.CekBox();
        posting_jurnal = new widget.CekBox();
        bubes = new widget.CekBox();
        cashflow = new widget.CekBox();
        keuangan = new widget.CekBox();
        panelGlass5 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TKd = new widget.TextBox();
        TNm = new widget.TextBox();
        BtnSeek = new widget.Button();
        BtnSeek1 = new widget.Button();
        TNmUser = new widget.TextBox();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup User ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(185, 185, 185)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbUser.setAutoCreateRowSorter(true);
        tbUser.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbUser.setName("tbUser"); // NOI18N
        tbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUserMouseClicked(evt);
            }
        });
        tbUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbUserKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbUser);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel13.setName("jPanel13"); // NOI18N
        jPanel13.setOpaque(false);
        jPanel13.setPreferredSize(new java.awt.Dimension(100, 250));
        jPanel13.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setBorder(null);
        scrollPane1.setName("scrollPane1"); // NOI18N

        jPanel7.setBackground(new java.awt.Color(0, 102, 102));
        jPanel7.setName("jPanel7"); // NOI18N
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(523, 394));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(215, 225, 215));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(230, 235, 225), 1, true), ".: Hak Akses Yang Diberikan ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 2, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(0, 5));

        ktg_penyakit.setBackground(new java.awt.Color(245, 255, 235));
        ktg_penyakit.setForeground(new java.awt.Color(50, 70, 50));
        ktg_penyakit.setText("Ktg.Pny");
        ktg_penyakit.setName("ktg_penyakit"); // NOI18N
        jPanel1.add(ktg_penyakit);

        obat.setBackground(new java.awt.Color(245, 255, 235));
        obat.setForeground(new java.awt.Color(50, 70, 50));
        obat.setText("Obat");
        obat.setName("obat"); // NOI18N
        jPanel1.add(obat);

        penyakit.setBackground(new java.awt.Color(245, 255, 235));
        penyakit.setForeground(new java.awt.Color(50, 70, 50));
        penyakit.setText("Penyakit");
        penyakit.setName("penyakit"); // NOI18N
        jPanel1.add(penyakit);

        bangsal.setBackground(new java.awt.Color(245, 255, 235));
        bangsal.setForeground(new java.awt.Color(50, 70, 50));
        bangsal.setText("Bangsal");
        bangsal.setName("bangsal"); // NOI18N
        jPanel1.add(bangsal);

        kamar.setBackground(new java.awt.Color(245, 255, 235));
        kamar.setForeground(new java.awt.Color(50, 70, 50));
        kamar.setText("Kamar");
        kamar.setName("kamar"); // NOI18N
        jPanel1.add(kamar);

        ktg_prw.setBackground(new java.awt.Color(245, 255, 235));
        ktg_prw.setForeground(new java.awt.Color(50, 70, 50));
        ktg_prw.setText("Ktg.Tindakan");
        ktg_prw.setName("ktg_prw"); // NOI18N
        jPanel1.add(ktg_prw);

        jns_prw.setBackground(new java.awt.Color(245, 255, 235));
        jns_prw.setForeground(new java.awt.Color(50, 70, 50));
        jns_prw.setText("Tindakan");
        jns_prw.setName("jns_prw"); // NOI18N
        jPanel1.add(jns_prw);

        spesialis.setBackground(new java.awt.Color(245, 255, 235));
        spesialis.setForeground(new java.awt.Color(50, 70, 50));
        spesialis.setText("Spesialis");
        spesialis.setName("spesialis"); // NOI18N
        jPanel1.add(spesialis);

        dokter.setBackground(new java.awt.Color(245, 255, 235));
        dokter.setForeground(new java.awt.Color(50, 70, 50));
        dokter.setText("Dokter");
        dokter.setName("dokter"); // NOI18N
        jPanel1.add(dokter);

        jadwal.setBackground(new java.awt.Color(245, 255, 235));
        jadwal.setForeground(new java.awt.Color(50, 70, 50));
        jadwal.setText("Jadwal Praktek");
        jadwal.setName("jadwal"); // NOI18N
        jPanel1.add(jadwal);

        jabatan.setBackground(new java.awt.Color(245, 255, 235));
        jabatan.setForeground(new java.awt.Color(50, 70, 50));
        jabatan.setText("Jabatan");
        jabatan.setName("jabatan"); // NOI18N
        jPanel1.add(jabatan);

        petugas.setBackground(new java.awt.Color(245, 255, 235));
        petugas.setForeground(new java.awt.Color(50, 70, 50));
        petugas.setText("Petugas");
        petugas.setName("petugas"); // NOI18N
        jPanel1.add(petugas);

        poli.setBackground(new java.awt.Color(245, 255, 235));
        poli.setForeground(new java.awt.Color(50, 70, 50));
        poli.setText("Poliklinik");
        poli.setName("poli"); // NOI18N
        jPanel1.add(poli);

        pasien.setBackground(new java.awt.Color(245, 255, 235));
        pasien.setForeground(new java.awt.Color(50, 70, 50));
        pasien.setText("Pasien");
        pasien.setName("pasien"); // NOI18N
        jPanel1.add(pasien);

        reg.setBackground(new java.awt.Color(245, 255, 235));
        reg.setForeground(new java.awt.Color(50, 70, 50));
        reg.setText("Registrasi");
        reg.setName("reg"); // NOI18N
        jPanel1.add(reg);

        rw_jln.setBackground(new java.awt.Color(245, 255, 235));
        rw_jln.setForeground(new java.awt.Color(50, 70, 50));
        rw_jln.setText("Rawat Jalan");
        rw_jln.setName("rw_jln"); // NOI18N
        jPanel1.add(rw_jln);

        kamar_inap.setBackground(new java.awt.Color(245, 255, 235));
        kamar_inap.setForeground(new java.awt.Color(50, 70, 50));
        kamar_inap.setText("Kamar Inap");
        kamar_inap.setName("kamar_inap"); // NOI18N
        jPanel1.add(kamar_inap);

        rw_inp.setBackground(new java.awt.Color(245, 255, 235));
        rw_inp.setForeground(new java.awt.Color(50, 70, 50));
        rw_inp.setText("Rawat Inap");
        rw_inp.setName("rw_inp"); // NOI18N
        jPanel1.add(rw_inp);

        resep.setBackground(new java.awt.Color(245, 255, 235));
        resep.setForeground(new java.awt.Color(50, 70, 50));
        resep.setText("Resep Obat");
        resep.setName("resep"); // NOI18N
        jPanel1.add(resep);

        rujuk.setBackground(new java.awt.Color(245, 255, 235));
        rujuk.setForeground(new java.awt.Color(50, 70, 50));
        rujuk.setText("Rujuk Pasien");
        rujuk.setName("rujuk"); // NOI18N
        jPanel1.add(rujuk);

        br_obat.setBackground(new java.awt.Color(245, 255, 235));
        br_obat.setForeground(new java.awt.Color(50, 70, 50));
        br_obat.setText("Pemberian Obat");
        br_obat.setName("br_obat"); // NOI18N
        jPanel1.add(br_obat);

        pasien_mati.setBackground(new java.awt.Color(245, 255, 235));
        pasien_mati.setForeground(new java.awt.Color(50, 70, 50));
        pasien_mati.setText("Pasien Meninggal");
        pasien_mati.setName("pasien_mati"); // NOI18N
        jPanel1.add(pasien_mati);

        tagihan_operasi.setBackground(new java.awt.Color(245, 255, 235));
        tagihan_operasi.setForeground(new java.awt.Color(50, 70, 50));
        tagihan_operasi.setText("Tagihan Operasi");
        tagihan_operasi.setName("tagihan_operasi"); // NOI18N
        jPanel1.add(tagihan_operasi);

        obat_penyakit.setBackground(new java.awt.Color(245, 255, 235));
        obat_penyakit.setForeground(new java.awt.Color(50, 70, 50));
        obat_penyakit.setText("Obat Pny");
        obat_penyakit.setName("obat_penyakit"); // NOI18N
        jPanel1.add(obat_penyakit);

        paket_operasi.setBackground(new java.awt.Color(245, 255, 235));
        paket_operasi.setForeground(new java.awt.Color(50, 70, 50));
        paket_operasi.setText("Paket Operasi");
        paket_operasi.setName("paket_operasi"); // NOI18N
        jPanel1.add(paket_operasi);

        diet_pasien.setBackground(new java.awt.Color(245, 255, 235));
        diet_pasien.setForeground(new java.awt.Color(50, 70, 50));
        diet_pasien.setText("Diet Pasien");
        diet_pasien.setName("diet_pasien"); // NOI18N
        jPanel1.add(diet_pasien);

        kelahiran_bayi.setBackground(new java.awt.Color(245, 255, 235));
        kelahiran_bayi.setForeground(new java.awt.Color(50, 70, 50));
        kelahiran_bayi.setText("Kelahiran Bayi");
        kelahiran_bayi.setToolTipText("");
        kelahiran_bayi.setName("kelahiran_bayi"); // NOI18N
        jPanel1.add(kelahiran_bayi);

        periksa_lab.setBackground(new java.awt.Color(245, 255, 235));
        periksa_lab.setForeground(new java.awt.Color(50, 70, 50));
        periksa_lab.setText("Periksa Lab");
        periksa_lab.setName("periksa_lab"); // NOI18N
        jPanel1.add(periksa_lab);

        billing_ralan.setBackground(new java.awt.Color(245, 255, 235));
        billing_ralan.setForeground(new java.awt.Color(50, 70, 50));
        billing_ralan.setText("Billing Ralan");
        billing_ralan.setName("billing_ralan"); // NOI18N
        jPanel1.add(billing_ralan);

        billing_ranap.setBackground(new java.awt.Color(245, 255, 235));
        billing_ranap.setForeground(new java.awt.Color(50, 70, 50));
        billing_ranap.setText("Billing Ranap");
        billing_ranap.setName("billing_ranap"); // NOI18N
        jPanel1.add(billing_ranap);

        kasirralan.setBackground(new java.awt.Color(245, 255, 235));
        kasirralan.setForeground(new java.awt.Color(50, 70, 50));
        kasirralan.setText("Kasir Ralan");
        kasirralan.setName("kasirralan"); // NOI18N
        jPanel1.add(kasirralan);

        suplier.setBackground(new java.awt.Color(245, 255, 235));
        suplier.setForeground(new java.awt.Color(50, 70, 50));
        suplier.setText("Suplier");
        suplier.setName("suplier"); // NOI18N
        jPanel1.add(suplier);

        satuan.setBackground(new java.awt.Color(245, 255, 235));
        satuan.setForeground(new java.awt.Color(50, 70, 50));
        satuan.setText("Satuan Barang");
        satuan.setName("satuan"); // NOI18N
        jPanel1.add(satuan);

        konversi.setBackground(new java.awt.Color(245, 255, 235));
        konversi.setForeground(new java.awt.Color(50, 70, 50));
        konversi.setText("Konversi Satuan");
        konversi.setName("konversi"); // NOI18N
        jPanel1.add(konversi);

        stok_opname.setBackground(new java.awt.Color(245, 255, 235));
        stok_opname.setForeground(new java.awt.Color(50, 70, 50));
        stok_opname.setText("Stok Opname");
        stok_opname.setName("stok_opname"); // NOI18N
        jPanel1.add(stok_opname);

        pembelian.setBackground(new java.awt.Color(245, 255, 235));
        pembelian.setForeground(new java.awt.Color(50, 70, 50));
        pembelian.setText("Pembelian");
        pembelian.setName("pembelian"); // NOI18N
        jPanel1.add(pembelian);

        penjualan.setBackground(new java.awt.Color(245, 255, 235));
        penjualan.setForeground(new java.awt.Color(50, 70, 50));
        penjualan.setText("Penjualan");
        penjualan.setName("penjualan"); // NOI18N
        jPanel1.add(penjualan);

        piutang.setBackground(new java.awt.Color(245, 255, 235));
        piutang.setForeground(new java.awt.Color(50, 70, 50));
        piutang.setText("Piutang");
        piutang.setName("piutang"); // NOI18N
        jPanel1.add(piutang);

        bayar_piutang.setBackground(new java.awt.Color(245, 255, 235));
        bayar_piutang.setForeground(new java.awt.Color(50, 70, 50));
        bayar_piutang.setText("Bayar Piutang");
        bayar_piutang.setName("bayar_piutang"); // NOI18N
        jPanel1.add(bayar_piutang);

        retur_jual.setBackground(new java.awt.Color(245, 255, 235));
        retur_jual.setForeground(new java.awt.Color(50, 70, 50));
        retur_jual.setText("Retur Jual");
        retur_jual.setName("retur_jual"); // NOI18N
        jPanel1.add(retur_jual);

        retur_beli.setBackground(new java.awt.Color(245, 255, 235));
        retur_beli.setForeground(new java.awt.Color(50, 70, 50));
        retur_beli.setText("Retur Beli");
        retur_beli.setName("retur_beli"); // NOI18N
        jPanel1.add(retur_beli);

        retur_piutang.setBackground(new java.awt.Color(245, 255, 235));
        retur_piutang.setForeground(new java.awt.Color(50, 70, 50));
        retur_piutang.setText("Retur Piutang");
        retur_piutang.setName("retur_piutang"); // NOI18N
        jPanel1.add(retur_piutang);

        keuntungan_jual.setBackground(new java.awt.Color(245, 255, 235));
        keuntungan_jual.setForeground(new java.awt.Color(50, 70, 50));
        keuntungan_jual.setText("Keuntungan Jual");
        keuntungan_jual.setName("keuntungan_jual"); // NOI18N
        jPanel1.add(keuntungan_jual);

        sirkulasi.setBackground(new java.awt.Color(245, 255, 235));
        sirkulasi.setForeground(new java.awt.Color(50, 70, 50));
        sirkulasi.setText("Sirkulasi Barang");
        sirkulasi.setName("sirkulasi"); // NOI18N
        jPanel1.add(sirkulasi);

        akun_rekening.setBackground(new java.awt.Color(245, 255, 235));
        akun_rekening.setForeground(new java.awt.Color(50, 70, 50));
        akun_rekening.setText("Akun Rekening");
        akun_rekening.setName("akun_rekening"); // NOI18N
        jPanel1.add(akun_rekening);

        rekening_tahun.setBackground(new java.awt.Color(245, 255, 235));
        rekening_tahun.setForeground(new java.awt.Color(50, 70, 50));
        rekening_tahun.setText("Rekening Tahun");
        rekening_tahun.setName("rekening_tahun"); // NOI18N
        jPanel1.add(rekening_tahun);

        posting_jurnal.setBackground(new java.awt.Color(245, 255, 235));
        posting_jurnal.setForeground(new java.awt.Color(50, 70, 50));
        posting_jurnal.setText("Posting Jurnal");
        posting_jurnal.setName("posting_jurnal"); // NOI18N
        jPanel1.add(posting_jurnal);

        bubes.setBackground(new java.awt.Color(245, 255, 235));
        bubes.setForeground(new java.awt.Color(50, 70, 50));
        bubes.setText("Buku Besar");
        bubes.setName("bubes"); // NOI18N
        jPanel1.add(bubes);

        cashflow.setBackground(new java.awt.Color(245, 255, 235));
        cashflow.setForeground(new java.awt.Color(50, 70, 50));
        cashflow.setText("Cash Flow");
        cashflow.setName("cashflow"); // NOI18N
        jPanel1.add(cashflow);

        keuangan.setBackground(new java.awt.Color(245, 255, 235));
        keuangan.setForeground(new java.awt.Color(50, 70, 50));
        keuangan.setText("Laba Rugi");
        keuangan.setName("keuangan"); // NOI18N
        jPanel1.add(keuangan);

        jPanel7.add(jPanel1, java.awt.BorderLayout.CENTER);

        scrollPane1.setViewportView(jPanel7);

        jPanel13.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 47));
        panelGlass5.setLayout(null);

        jLabel3.setText("Dokter/Petugas :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass5.add(jLabel3);
        jLabel3.setBounds(10, 12, 100, 23);

        jLabel4.setText("Password :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass5.add(jLabel4);
        jLabel4.setBounds(449, 12, 60, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        panelGlass5.add(TKd);
        TKd.setBounds(107, 12, 107, 23);

        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        panelGlass5.add(TNm);
        TNm.setBounds(511, 12, 180, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSeek);
        BtnSeek.setBounds(216, 12, 28, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("Alt+2");
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
        panelGlass5.add(BtnSeek1);
        BtnSeek1.setBounds(245, 12, 28, 23);

        TNmUser.setEditable(false);
        TNmUser.setName("TNmUser"); // NOI18N
        TNmUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmUserKeyPressed(evt);
            }
        });
        panelGlass5.add(TNmUser);
        TNmUser.setBounds(275, 12, 160, 23);

        jPanel13.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel13, java.awt.BorderLayout.PAGE_START);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Preview.png"))); // NOI18N
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

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")||TNmUser.getText().trim().equals("")){
            Valid.textKosong(TKd,"User");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Password");
        }else{
            Sequel.menyimpan("user","AES_ENCRYPT('"+TKd.getText()+"','nur'),AES_ENCRYPT('"+TNm.getText()+"','windi')," +
                    "'"+jabatan.isSelected()+"',"+
                    "'"+spesialis.isSelected()+"',"+
                    "'"+tagihan_operasi.isSelected()+"',"+
                    "'"+ktg_prw.isSelected()+"',"+
                    "'"+jns_prw.isSelected()+"',"+
                    "'"+poli.isSelected()+"',"+
                    "'"+bangsal.isSelected()+"',"+
                    "'"+kamar.isSelected()+"',"+
                    "'"+ktg_penyakit.isSelected()+"',"+
                    "'"+penyakit.isSelected()+"',"+
                    "'"+obat.isSelected()+"',"+
                    "'"+obat_penyakit.isSelected()+"',"+
                    "'"+petugas.isSelected()+"',"+
                    "'"+dokter.isSelected()+"',"+
                    "'"+jadwal.isSelected()+"',"+
                    "'"+pasien.isSelected()+"',"+
                    "'"+paket_operasi.isSelected()+"',"+
                    "'"+diet_pasien.isSelected()+"',"+
                    "'"+reg.isSelected()+"',"+
                    "'"+kelahiran_bayi.isSelected()+"',"+
                    "'"+periksa_lab.isSelected()+"',"+
                    "'"+kamar_inap.isSelected()+"',"+
                    "'"+billing_ralan.isSelected()+"',"+
                    "'"+billing_ranap.isSelected()+"',"+
                    "'"+kasirralan.isSelected()+"',"+
                    "'"+suplier.isSelected()+"',"+
                    "'"+satuan.isSelected()+"',"+
                    "'"+rw_jln.isSelected()+"',"+
                    "'"+resep.isSelected()+"',"+
                    "'"+rujuk.isSelected()+"',"+
                    "'"+rw_inp.isSelected()+"',"+
                    "'"+konversi.isSelected()+"',"+
                    "'"+br_obat.isSelected()+"',"+
                    "'"+stok_opname.isSelected()+"',"+
                    "'"+pembelian.isSelected()+"',"+
                    "'"+penjualan.isSelected()+"',"+
                    "'"+piutang.isSelected()+"',"+
                    "'"+bayar_piutang.isSelected()+"',"+
                    "'"+retur_jual.isSelected()+"',"+
                    "'"+retur_beli.isSelected()+"',"+
                    "'"+retur_piutang.isSelected()+"',"+
                    "'"+keuntungan_jual.isSelected()+"',"+
                    "'"+sirkulasi.isSelected()+"',"+
                    "'"+akun_rekening.isSelected()+"',"+
                    "'"+rekening_tahun.isSelected()+"',"+
                    "'"+pasien_mati.isSelected()+"',"+
                    "'"+posting_jurnal.isSelected()+"',"+
                    "'"+bubes.isSelected()+"',"+
                    "'"+cashflow.isSelected()+"',"+
                    "'"+keuangan.isSelected()+"'","User");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TNm,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TKd.requestFocus();
        }else if(TNm.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(! TNm.getText().trim().equals("")){
            Sequel.queryu("delete from user where id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKd.getText().trim().equals("")||TNmUser.getText().trim().equals("")){
            Valid.textKosong(TKd,"User");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Password");
        }else{
           
            Sequel.queryu("update user set password=AES_ENCRYPT('"+TNm.getText()+"','windi'),"+
                    "jabatan='"+jabatan.isSelected()+"',"+
                    "spesialis='"+spesialis.isSelected()+"',"+
                    "tagihan_operasi='"+tagihan_operasi.isSelected()+"',"+
                    "ktg_prw='"+ktg_prw.isSelected()+"',"+
                    "jns_prw='"+jns_prw.isSelected()+"',"+
                    "poli='"+poli.isSelected()+"',"+
                    "bangsal='"+bangsal.isSelected()+"',"+
                    "kamar='"+kamar.isSelected()+"',"+
                    "ktg_penyakit='"+ktg_penyakit.isSelected()+"',"+
                    "penyakit='"+penyakit.isSelected()+"',"+
                    "obat='"+obat.isSelected()+"',"+
                    "obat_penyakit='"+obat_penyakit.isSelected()+"',"+
                    "petugas='"+petugas.isSelected()+"',"+
                    "dokter='"+dokter.isSelected()+"',"+
                    "jadwal='"+jadwal.isSelected()+"',"+
                    "pasien='"+pasien.isSelected()+"',"+
                    "paket_operasi='"+paket_operasi.isSelected()+"',"+
                    "diet_pasien='"+diet_pasien.isSelected()+"',"+
                    "reg='"+reg.isSelected()+"',"+
                    "kelahiran_bayi='"+kelahiran_bayi.isSelected()+"',"+
                    "periksa_lab='"+periksa_lab.isSelected()+"',"+
                    "kamar_inap='"+kamar_inap.isSelected()+"',"+
                    "billing_ralan='"+billing_ralan.isSelected()+"',"+
                    "billing_ranap='"+billing_ranap.isSelected()+"',"+
                    "biling='"+kasirralan.isSelected()+"',"+
                    "suplier='"+suplier.isSelected()+"',"+
                    "satuan='"+satuan.isSelected()+"',"+
                    "rw_jln='"+rw_jln.isSelected()+"',"+
                    "resep='"+resep.isSelected()+"',"+
                    "rujuk='"+rujuk.isSelected()+"',"+
                    "rw_inp='"+rw_inp.isSelected()+"',"+
                    "konversi='"+konversi.isSelected()+"',"+
                    "br_obat='"+br_obat.isSelected()+"',"+
                    "stok_opname='"+stok_opname.isSelected()+"',"+
                    "pembelian='"+pembelian.isSelected()+"',"+
                    "penjualan='"+penjualan.isSelected()+"',"+
                    "piutang='"+piutang.isSelected()+"',"+
                    "bayar_piutang='"+bayar_piutang.isSelected()+"',"+
                    "retur_jual='"+retur_jual.isSelected()+"',"+
                    "retur_beli='"+retur_beli.isSelected()+"',"+
                    "retur_piutang='"+retur_piutang.isSelected()+"',"+
                    "keuntungan_jual='"+keuntungan_jual.isSelected()+"',"+
                    "sirkulasi='"+sirkulasi.isSelected()+"',"+
                    "akun_rekening='"+akun_rekening.isSelected()+"',"+
                    "rekening_tahun='"+rekening_tahun.isSelected()+"',"+
                    "pasien_mati='"+pasien_mati.isSelected()+"',"+
                    "posting_jurnal='"+posting_jurnal.isSelected()+"',"+
                    "bubes='"+bubes.isSelected()+"',"+
                    "cashflow='"+cashflow.isSelected()+"',"+
                    "keuangan='"+keuangan.isSelected()+"' where id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')");
            tampil();
            emptTeks();
        }

}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,BtnKeluar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,BtnSimpan,TNm);
}//GEN-LAST:event_TKdKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TNmKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        dlgdok.emptTeks();
        dlgdok.isCek();
        dlgdok.tampil("");
        //dlgdok.setModal(true);
        dlgdok.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgdok.setLocationRelativeTo(internalFrame1);
        dlgdok.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,TKd,TNm);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        dlgptg.emptTeks();
        dlgptg.isCek();
        dlgptg.tampil();
        //dlgptg.setModal(true);
        dlgptg.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgptg.setLocationRelativeTo(internalFrame1);
        dlgptg.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,TKd,TNm);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void TNmUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmUserKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUserMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbUserMouseClicked

    private void tbUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbUserKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbUserKeyPressed

private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TKd.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','','','','','','','','','','','','','',''","Data User"); 
            }
            Valid.MyReport("rptUser.jrxml","report","::[ Data User ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgUser dialog = new DlgUser(new javax.swing.JFrame(), true);
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
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSimpan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.TextBox TNm;
    private widget.TextBox TNmUser;
    private widget.CekBox akun_rekening;
    private widget.CekBox bangsal;
    private widget.CekBox bayar_piutang;
    private widget.CekBox billing_ralan;
    private widget.CekBox billing_ranap;
    private widget.CekBox br_obat;
    private widget.CekBox bubes;
    private widget.CekBox cashflow;
    private widget.CekBox diet_pasien;
    private widget.CekBox dokter;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel7;
    private widget.CekBox jabatan;
    private widget.CekBox jadwal;
    private widget.CekBox jns_prw;
    private widget.CekBox kamar;
    private widget.CekBox kamar_inap;
    private widget.CekBox kasirralan;
    private widget.CekBox kelahiran_bayi;
    private widget.CekBox keuangan;
    private widget.CekBox keuntungan_jual;
    private widget.CekBox konversi;
    private widget.CekBox ktg_penyakit;
    private widget.CekBox ktg_prw;
    private widget.CekBox obat;
    private widget.CekBox obat_penyakit;
    private widget.CekBox paket_operasi;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    private widget.CekBox pasien;
    private widget.CekBox pasien_mati;
    private widget.CekBox pembelian;
    private widget.CekBox penjualan;
    private widget.CekBox penyakit;
    private widget.CekBox periksa_lab;
    private widget.CekBox petugas;
    private widget.CekBox piutang;
    private widget.CekBox poli;
    private widget.CekBox posting_jurnal;
    private widget.CekBox reg;
    private widget.CekBox rekening_tahun;
    private widget.CekBox resep;
    private widget.CekBox retur_beli;
    private widget.CekBox retur_jual;
    private widget.CekBox retur_piutang;
    private widget.CekBox rujuk;
    private widget.CekBox rw_inp;
    private widget.CekBox rw_jln;
    private widget.CekBox satuan;
    private widget.ScrollPane scrollPane1;
    private widget.CekBox sirkulasi;
    private widget.CekBox spesialis;
    private widget.CekBox stok_opname;
    private widget.CekBox suplier;
    private widget.CekBox tagihan_operasi;
    private widget.Table tbUser;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String sql="select AES_DECRYPT(id_user,'nur'),AES_DECRYPT(password,'windi'),"+
                "jabatan,"+
                "spesialis,"+
                "tagihan_operasi,"+
                "ktg_prw,"+
                "jns_prw,"+
                "poli,"+
                "bangsal,"+
                "kamar,"+
                "ktg_penyakit,"+
                "penyakit,"+
                "obat,"+
                "obat_penyakit,"+
                "petugas,"+
                "dokter,"+
                "jadwal,"+
                "pasien,"+
                "paket_operasi,"+
                "diet_pasien,"+
                "reg,"+
                "kelahiran_bayi,"+
                "periksa_lab,"+
                "kamar_inap,"+
                "billing_ralan,"+
                "billing_ranap,"+
                "biling,"+
                "suplier,"+
                "satuan,"+
                "rw_jln,"+
                "resep,"+
                "rujuk,"+
                "rw_inp,"+
                "konversi,"+
                "br_obat,"+
                "stok_opname,"+
                "pembelian,"+
                "penjualan,"+
                "piutang,"+
                "bayar_piutang,"+
                "retur_jual,"+
                "retur_beli,"+
                "retur_piutang,"+
                "keuntungan_jual,"+
                "sirkulasi,"+
                "akun_rekening,"+
                "rekening_tahun,"+
                "pasien_mati,"+
                "posting_jurnal,"+
                "bubes,"+
                "cashflow,"+
                "keuangan from user order by AES_DECRYPT(id_user,'nur')";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            ResultSet rs=koneksi.createStatement().executeQuery(sql);            
            while(rs.next()){
                String user="";
                ResultSet rs2=koneksi.createStatement().executeQuery("select nm_dokter from dokter where kd_dokter='"+rs.getString(1)+"'");
                if(rs2.next()){
                    user=rs2.getString(1);
                }else{
                    ResultSet rs3=koneksi.createStatement().executeQuery("select nama from petugas where nip='"+rs.getString(1)+"'");
                    if(rs3.next()){
                        user=rs3.getString(1);
                    }
                }
                tabMode.addRow(new Object[]{rs.getString(1),
                               user,
                               rs.getString(2),
                               rs.getBoolean(3),
                               rs.getBoolean(4),
                               rs.getBoolean(5),
                               rs.getBoolean(6),
                               rs.getBoolean(7),
                               rs.getBoolean(8),
                               rs.getBoolean(9),
                               rs.getBoolean(10),
                               rs.getBoolean(11),
                               rs.getBoolean(12),
                               rs.getBoolean(13),
                               rs.getBoolean(14),
                               rs.getBoolean(15),
                               rs.getBoolean(16),
                               rs.getBoolean(17),
                               rs.getBoolean(18),
                               rs.getBoolean(19),
                               rs.getBoolean(20),
                               rs.getBoolean(21),
                               rs.getBoolean(22),
                               rs.getBoolean(23),
                               rs.getBoolean(24),
                               rs.getBoolean(25),
                               rs.getBoolean(26),
                               rs.getBoolean(27),
                               rs.getBoolean(28),
                               rs.getBoolean(29),
                               rs.getBoolean(30),
                               rs.getBoolean(31),
                               rs.getBoolean(32),
                               rs.getBoolean(33),
                               rs.getBoolean(34),
                               rs.getBoolean(35),
                               rs.getBoolean(36),
                               rs.getBoolean(37),
                               rs.getBoolean(38),
                               rs.getBoolean(39),
                               rs.getBoolean(40),
                               rs.getBoolean(41),
                               rs.getBoolean(42),
                               rs.getBoolean(43),
                               rs.getBoolean(44),
                               rs.getBoolean(45),
                               rs.getBoolean(46),
                               rs.getBoolean(47),
                               rs.getBoolean(48),
                               rs.getBoolean(49),
                               rs.getBoolean(50),
                               rs.getBoolean(51),
                               rs.getBoolean(52)});
             }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

    private void isUser() {
        try{
            ResultSet rs=koneksi.createStatement().executeQuery("select nm_dokter from dokter where kd_dokter='"+TKd.getText()+"'");
            if(rs.next()){
                TNmUser.setText(rs.getString(1));
            }else if(!rs.next()){
                rs=koneksi.createStatement().executeQuery("select nama from petugas where nip='"+TKd.getText()+"'");
                if(rs.next()){
                    TNmUser.setText(rs.getString(1));
                }else if(!rs.next()){
                    TNmUser.setText("");
                }
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
    }

    public void emptTeks() {
        TKd.setText("");
        TNm.setText("");
        
        jabatan.setSelected(false);
        spesialis.setSelected(false);
        tagihan_operasi.setSelected(false);
        ktg_prw.setSelected(false);
        jns_prw.setSelected(false);

        poli.setSelected(false);
        bangsal.setSelected(false);
        kamar.setSelected(false);

        ktg_penyakit.setSelected(false);
        penyakit.setSelected(false);
        obat.setSelected(false);
        obat_penyakit.setSelected(false);
        
        petugas.setSelected(false);
        dokter.setSelected(false);
        jadwal.setSelected(false);
        
        pasien.setSelected(false);
        paket_operasi.setSelected(false);
        diet_pasien.setSelected(false);
        
        reg.setSelected(false);
        kelahiran_bayi.setSelected(false);
        periksa_lab.setSelected(false);
        kamar_inap.setSelected(false);
        billing_ralan.setSelected(false);
        billing_ranap.setSelected(false);
        kasirralan.setSelected(false);
        suplier.setSelected(false);
        satuan.setSelected(false);
        
        rw_jln.setSelected(false);
        resep.setSelected(false);
        rujuk.setSelected(false);
        rw_inp.setSelected(false);
        br_obat.setSelected(false);
        konversi.setSelected(false);

        stok_opname.setSelected(false);
        pembelian.setSelected(false);
        penjualan.setSelected(false);
        piutang.setSelected(false);
        retur_jual.setSelected(false);
        bayar_piutang.setSelected(false);
        
        retur_beli.setSelected(false);
        retur_piutang.setSelected(false);
        keuntungan_jual.setSelected(false);
        sirkulasi.setSelected(false);
        rekening_tahun.setSelected(false);
        akun_rekening.setSelected(false);

        pasien_mati.setSelected(false);
        posting_jurnal.setSelected(false);
        bubes.setSelected(false);

        cashflow.setSelected(false);
        keuangan.setSelected(false);
        
        

        TKd.requestFocus();
    }

    private void getData() {
        int row=tbUser.getSelectedRow();
        if(row!= -1){
            TKd.setText(tbUser.getValueAt(row,0).toString());
            TNm.setText(tbUser.getValueAt(row,2).toString());
            
            jabatan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,3).toString()));
            spesialis.setSelected(Boolean.valueOf(tbUser.getValueAt(row,4).toString()));
            tagihan_operasi.setSelected(Boolean.valueOf(tbUser.getValueAt(row,5).toString()));
            ktg_prw.setSelected(Boolean.valueOf(tbUser.getValueAt(row,6).toString()));
            jns_prw.setSelected(Boolean.valueOf(tbUser.getValueAt(row,7).toString()));

            poli.setSelected(Boolean.valueOf(tbUser.getValueAt(row,8).toString()));
            bangsal.setSelected(Boolean.valueOf(tbUser.getValueAt(row,9).toString()));
            kamar.setSelected(Boolean.valueOf(tbUser.getValueAt(row,10).toString()));

            ktg_penyakit.setSelected(Boolean.valueOf(tbUser.getValueAt(row,11).toString()));
            penyakit.setSelected(Boolean.valueOf(tbUser.getValueAt(row,12).toString()));
            obat.setSelected(Boolean.valueOf(tbUser.getValueAt(row,13).toString()));
            obat_penyakit.setSelected(Boolean.valueOf(tbUser.getValueAt(row,14).toString()));

            petugas.setSelected(Boolean.valueOf(tbUser.getValueAt(row,15).toString()));
            dokter.setSelected(Boolean.valueOf(tbUser.getValueAt(row,16).toString()));
            jadwal.setSelected(Boolean.valueOf(tbUser.getValueAt(row,17).toString()));

            pasien.setSelected(Boolean.valueOf(tbUser.getValueAt(row,18).toString()));
            paket_operasi.setSelected(Boolean.valueOf(tbUser.getValueAt(row,19).toString()));
            diet_pasien.setSelected(Boolean.valueOf(tbUser.getValueAt(row,20).toString()));

            reg.setSelected(Boolean.valueOf(tbUser.getValueAt(row,21).toString()));
            kelahiran_bayi.setSelected(Boolean.valueOf(tbUser.getValueAt(row,22).toString()));
            periksa_lab.setSelected(Boolean.valueOf(tbUser.getValueAt(row,23).toString()));
            kamar_inap.setSelected(Boolean.valueOf(tbUser.getValueAt(row,24).toString()));
            billing_ralan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,25).toString()));
            billing_ranap.setSelected(Boolean.valueOf(tbUser.getValueAt(row,26).toString()));
            kasirralan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,27).toString()));
            suplier.setSelected(Boolean.valueOf(tbUser.getValueAt(row,28).toString()));
            satuan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,29).toString()));

            rw_jln.setSelected(Boolean.valueOf(tbUser.getValueAt(row,30).toString()));
            resep.setSelected(Boolean.valueOf(tbUser.getValueAt(row,31).toString()));
            rujuk.setSelected(Boolean.valueOf(tbUser.getValueAt(row,32).toString()));
            rw_inp.setSelected(Boolean.valueOf(tbUser.getValueAt(row,33).toString()));
            br_obat.setSelected(Boolean.valueOf(tbUser.getValueAt(row,34).toString()));
            konversi.setSelected(Boolean.valueOf(tbUser.getValueAt(row,35).toString()));

            stok_opname.setSelected(Boolean.valueOf(tbUser.getValueAt(row,36).toString()));
            pembelian.setSelected(Boolean.valueOf(tbUser.getValueAt(row,37).toString()));
            penjualan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,38).toString()));
            piutang.setSelected(Boolean.valueOf(tbUser.getValueAt(row,39).toString()));
            retur_jual.setSelected(Boolean.valueOf(tbUser.getValueAt(row,40).toString()));
            bayar_piutang.setSelected(Boolean.valueOf(tbUser.getValueAt(row,41).toString()));

            retur_beli.setSelected(Boolean.valueOf(tbUser.getValueAt(row,42).toString()));
            retur_piutang.setSelected(Boolean.valueOf(tbUser.getValueAt(row,43).toString()));
            keuntungan_jual.setSelected(Boolean.valueOf(tbUser.getValueAt(row,44).toString()));
            sirkulasi.setSelected(Boolean.valueOf(tbUser.getValueAt(row,45).toString()));
            rekening_tahun.setSelected(Boolean.valueOf(tbUser.getValueAt(row,46).toString()));
            akun_rekening.setSelected(Boolean.valueOf(tbUser.getValueAt(row,47).toString()));

            pasien_mati.setSelected(Boolean.valueOf(tbUser.getValueAt(row,48).toString()));
            posting_jurnal.setSelected(Boolean.valueOf(tbUser.getValueAt(row,49).toString()));
            bubes.setSelected(Boolean.valueOf(tbUser.getValueAt(row,50).toString()));

            cashflow.setSelected(Boolean.valueOf(tbUser.getValueAt(row,51).toString()));
            keuangan.setSelected(Boolean.valueOf(tbUser.getValueAt(row,52).toString()));
        }
    }

}
