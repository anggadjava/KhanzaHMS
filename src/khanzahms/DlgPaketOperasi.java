/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public final class DlgPaketOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DecimalFormat df2 = new DecimalFormat("####");
    private double operator1=0,operator2=0,operator3=0,
               asistenoperator1=0,asistenoperator2=0,asistenoperator3=0,
               dokteranak=0,perawatresusitas=0,bidan=0,
               alat=0,anastesi=0,perawatluar=0,asistenanas=0,sewaok=0,sewavk=0,bagianrs=0,omloop=0;
    private PreparedStatement pstampil;
    private ResultSet rs;

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public DlgPaketOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"Kode Paket",
                      "Nama Operasi",
                      "Operator 1",
                      "Operator 2",
                      "Operator 3",
                      "Asisten Op 1",
                      "Asisten Op 2",
                      "Instrumen",
                      "dr Anestesi",
                      "Asisten Anestesi",
                      "dr Anak",
                      "Perawat Resus",
                      "Bidan",
                      "Perawat Luar",
                      "Alat",
                      "Sewa OK",
                      "Sewa VK",
                      "Bagian RS",
                      "Omloop",
                      "Total"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 20; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        

        TKd.setDocument(new batasInput((byte)8).getKata(TKd));
        TNm.setDocument(new batasInput((byte)80).getKata(TNm));
        TOperator1.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator1));
        TOperator2.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator2));
        TOperator3.setDocument(new batasInput((byte)13).getOnlyAngka(TOperator3));
        TAsisOperator1.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator1));
        TAsisOperator2.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator2));
        TAsisOperator3.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisOperator3));
        TAnastesi.setDocument(new batasInput((byte)13).getOnlyAngka(TAnastesi));
        TAsisAnastesi.setDocument(new batasInput((byte)13).getOnlyAngka(TAsisAnastesi));
        TAnak.setDocument(new batasInput((byte)13).getOnlyAngka(TAnak));
        TResusitas.setDocument(new batasInput((byte)13).getOnlyAngka(TResusitas));
        TBidan.setDocument(new batasInput((byte)13).getOnlyAngka(TBidan));
        TAlat.setDocument(new batasInput((byte)13).getOnlyAngka(TAlat));
        TPerawatLuar.setDocument(new batasInput((byte)13).getOnlyAngka(TPerawatLuar));
        TSewaOK.setDocument(new batasInput((byte)13).getOnlyAngka(TSewaOK));
        TSewaVk.setDocument(new batasInput((byte)13).getOnlyAngka(TSewaVk));
        TBagianRS.setDocument(new batasInput((byte)13).getOnlyAngka(TBagianRS));
        TOmloop.setDocument(new batasInput((byte)13).getOnlyAngka(TOmloop));
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
       
        ChkInput.setSelected(false);
        isForm(); 
        try{
            pstampil=koneksi.prepareStatement("select kode_paket, nm_perawatan, operator1, operator2, operator3, "+
                   "asisten_operator1, asisten_operator2, asisten_operator3, dokter_anak, "+
                   "perawaat_resusitas, alat, dokter_anestesi, asisten_anestesi, bidan, perawat_luar, sewa_ok,sewa_vk,bagian_rs,omloop, "+
                   "(operator1+operator2+operator3+asisten_operator1+asisten_operator2+asisten_operator3+"+
                   "dokter_anak+perawaat_resusitas+alat+dokter_anestesi+asisten_anestesi+bidan+perawat_luar+sewa_ok+sewa_vk+bagian_rs+omloop) as jumlah "+
                   "from paket_operasi where kode_paket like ? or "+
                   "nm_perawatan like ? order by kode_paket ");
        }catch(SQLException e){
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TKd = new widget.TextBox();
        jLabel8 = new widget.Label();
        TNm = new widget.TextBox();
        TOperator1 = new widget.TextBox();
        jLabel9 = new widget.Label();
        TAsisOperator1 = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        TAlat = new widget.TextBox();
        TSewaOK = new widget.TextBox();
        jLabel10 = new widget.Label();
        TAnastesi = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel15 = new widget.Label();
        TAsisAnastesi = new widget.TextBox();
        jLabel11 = new widget.Label();
        TPerawatLuar = new widget.TextBox();
        jLabel16 = new widget.Label();
        TTotal = new widget.TextBox();
        jLabel17 = new widget.Label();
        TOperator2 = new widget.TextBox();
        jLabel18 = new widget.Label();
        TOperator3 = new widget.TextBox();
        jLabel19 = new widget.Label();
        TAsisOperator2 = new widget.TextBox();
        jLabel20 = new widget.Label();
        TAsisOperator3 = new widget.TextBox();
        jLabel21 = new widget.Label();
        TAnak = new widget.TextBox();
        jLabel22 = new widget.Label();
        TResusitas = new widget.TextBox();
        jLabel23 = new widget.Label();
        TBidan = new widget.TextBox();
        jLabel24 = new widget.Label();
        TBagianRS = new widget.TextBox();
        jLabel25 = new widget.Label();
        TSewaVk = new widget.TextBox();
        jLabel26 = new widget.Label();
        TOmloop = new widget.TextBox();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Paket Tindakan Operasi/VK]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

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

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 227));
        FormInput.setLayout(null);

        jLabel3.setText("Kode Paket :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 96, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        FormInput.add(TKd);
        TKd.setBounds(100, 12, 100, 23);

        jLabel8.setText("Nama Tnd/Prw/Tagihan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(206, 12, 160, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(370, 12, 350, 23);

        TOperator1.setText("0");
        TOperator1.setHighlighter(null);
        TOperator1.setName("TOperator1"); // NOI18N
        TOperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator1KeyPressed(evt);
            }
        });
        FormInput.add(TOperator1);
        TOperator1.setBounds(100, 42, 122, 23);

        jLabel9.setText("Operator 1 : Rp");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 42, 96, 23);

        TAsisOperator1.setText("0");
        TAsisOperator1.setHighlighter(null);
        TAsisOperator1.setName("TAsisOperator1"); // NOI18N
        TAsisOperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator1KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator1);
        TAsisOperator1.setBounds(370, 42, 122, 23);

        jLabel12.setText("Asisten Operator 1 : Rp");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(225, 42, 140, 23);

        jLabel13.setText("Alat : Rp");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(495, 72, 98, 23);

        TAlat.setText("0");
        TAlat.setHighlighter(null);
        TAlat.setName("TAlat"); // NOI18N
        TAlat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlatKeyPressed(evt);
            }
        });
        FormInput.add(TAlat);
        TAlat.setBounds(598, 72, 122, 23);

        TSewaOK.setText("0");
        TSewaOK.setHighlighter(null);
        TSewaOK.setName("TSewaOK"); // NOI18N
        TSewaOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSewaOKKeyPressed(evt);
            }
        });
        FormInput.add(TSewaOK);
        TSewaOK.setBounds(598, 102, 122, 23);

        jLabel10.setText("Sewa Ok : RP");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(495, 102, 98, 23);

        TAnastesi.setText("0");
        TAnastesi.setHighlighter(null);
        TAnastesi.setName("TAnastesi"); // NOI18N
        TAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(TAnastesi);
        TAnastesi.setBounds(100, 132, 122, 23);

        jLabel14.setText("dr Anestesi : Rp");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 132, 96, 23);

        jLabel15.setText("Asisten Anestesi : Rp");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(225, 132, 140, 23);

        TAsisAnastesi.setText("0");
        TAsisAnastesi.setHighlighter(null);
        TAsisAnastesi.setName("TAsisAnastesi"); // NOI18N
        TAsisAnastesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisAnastesiKeyPressed(evt);
            }
        });
        FormInput.add(TAsisAnastesi);
        TAsisAnastesi.setBounds(370, 132, 122, 23);

        jLabel11.setText("Prw Luar : RP");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(495, 132, 98, 23);

        TPerawatLuar.setText("0");
        TPerawatLuar.setHighlighter(null);
        TPerawatLuar.setName("TPerawatLuar"); // NOI18N
        TPerawatLuar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPerawatLuarKeyPressed(evt);
            }
        });
        FormInput.add(TPerawatLuar);
        TPerawatLuar.setBounds(598, 132, 122, 23);

        jLabel16.setText("Total : Rp");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(495, 192, 98, 23);

        TTotal.setEditable(false);
        TTotal.setText("0");
        TTotal.setHighlighter(null);
        TTotal.setName("TTotal"); // NOI18N
        FormInput.add(TTotal);
        TTotal.setBounds(598, 192, 122, 23);

        jLabel17.setText("Operator 2 : Rp");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 72, 96, 23);

        TOperator2.setText("0");
        TOperator2.setHighlighter(null);
        TOperator2.setName("TOperator2"); // NOI18N
        TOperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator2KeyPressed(evt);
            }
        });
        FormInput.add(TOperator2);
        TOperator2.setBounds(100, 72, 122, 23);

        jLabel18.setText("Operator 3 : Rp");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 102, 96, 23);

        TOperator3.setText("0");
        TOperator3.setHighlighter(null);
        TOperator3.setName("TOperator3"); // NOI18N
        TOperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOperator3KeyPressed(evt);
            }
        });
        FormInput.add(TOperator3);
        TOperator3.setBounds(100, 102, 122, 23);

        jLabel19.setText("Asisten Operator 2 : Rp");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(225, 72, 140, 23);

        TAsisOperator2.setText("0");
        TAsisOperator2.setHighlighter(null);
        TAsisOperator2.setName("TAsisOperator2"); // NOI18N
        TAsisOperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator2KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator2);
        TAsisOperator2.setBounds(370, 72, 122, 23);

        jLabel20.setText("Instrumen : Rp");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(225, 102, 140, 23);

        TAsisOperator3.setText("0");
        TAsisOperator3.setHighlighter(null);
        TAsisOperator3.setName("TAsisOperator3"); // NOI18N
        TAsisOperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAsisOperator3KeyPressed(evt);
            }
        });
        FormInput.add(TAsisOperator3);
        TAsisOperator3.setBounds(370, 102, 122, 23);

        jLabel21.setText("dr Anak : Rp");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(0, 162, 96, 23);

        TAnak.setText("0");
        TAnak.setHighlighter(null);
        TAnak.setName("TAnak"); // NOI18N
        TAnak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAnakKeyPressed(evt);
            }
        });
        FormInput.add(TAnak);
        TAnak.setBounds(100, 162, 122, 23);

        jLabel22.setText("Perawat Resusitasi : Rp");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(225, 162, 140, 23);

        TResusitas.setText("0");
        TResusitas.setHighlighter(null);
        TResusitas.setName("TResusitas"); // NOI18N
        TResusitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TResusitasKeyPressed(evt);
            }
        });
        FormInput.add(TResusitas);
        TResusitas.setBounds(370, 162, 122, 23);

        jLabel23.setText("Bidan : Rp");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(495, 42, 98, 23);

        TBidan.setText("0");
        TBidan.setHighlighter(null);
        TBidan.setName("TBidan"); // NOI18N
        TBidan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBidanKeyPressed(evt);
            }
        });
        FormInput.add(TBidan);
        TBidan.setBounds(598, 42, 122, 23);

        jLabel24.setText("Bagian RS : Rp");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 192, 96, 23);

        TBagianRS.setText("0");
        TBagianRS.setHighlighter(null);
        TBagianRS.setName("TBagianRS"); // NOI18N
        TBagianRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBagianRSKeyPressed(evt);
            }
        });
        FormInput.add(TBagianRS);
        TBagianRS.setBounds(100, 192, 122, 23);

        jLabel25.setText("Sewa VK : Rp");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(225, 192, 140, 23);

        TSewaVk.setText("0");
        TSewaVk.setHighlighter(null);
        TSewaVk.setName("TSewaVk"); // NOI18N
        TSewaVk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSewaVkKeyPressed(evt);
            }
        });
        FormInput.add(TSewaVk);
        TSewaVk.setBounds(370, 192, 122, 23);

        jLabel26.setText("Omloop : Rp");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(495, 162, 98, 23);

        TOmloop.setText("0");
        TOmloop.setHighlighter(null);
        TOmloop.setName("TOmloop"); // NOI18N
        TOmloop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TOmloopKeyPressed(evt);
            }
        });
        FormInput.add(TOmloop);
        TOmloop.setBounds(598, 162, 122, 23);

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

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,TCari,TNm);
}//GEN-LAST:event_TKdKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TKd,TOperator1);
}//GEN-LAST:event_TNmKeyPressed

    private void TOperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TNm.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOperator2.requestFocus();
        }
}//GEN-LAST:event_TOperator1KeyPressed

    private void TAsisOperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBagianRS.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator2.requestFocus();
        }
}//GEN-LAST:event_TAsisOperator1KeyPressed

    private void TAlatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TBidan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TSewaOK.requestFocus();
        }
}//GEN-LAST:event_TAlatKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(TOperator1.getText().trim().equals("")){
            Valid.textKosong(TOperator1,"Operator 1");
        }else if(TOperator2.getText().trim().equals("")){
            Valid.textKosong(TOperator2,"Operator 2");
        }else if(TOperator3.getText().trim().equals("")){
            Valid.textKosong(TOperator3,"Operator 3");
        }else if(TAsisOperator1.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 1");
        }else if(TAsisOperator2.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 2");
        }else if(TAsisOperator3.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 3");
        }else if(TAlat.getText().trim().equals("")){
            Valid.textKosong(TAlat,"Alat OK");
        }else if(TAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAnastesi,"Dokter Anetesi");
        }else if(TAsisAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi,"Asisten Anestesi");
        }else if(TAnak.getText().trim().equals("")){
            Valid.textKosong(TAnak,"Dokter Anak");
        }else if(TResusitas.getText().trim().equals("")){
            Valid.textKosong(TResusitas,"Perawat Resusitas");
        }else if(TPerawatLuar.getText().trim().equals("")){
            Valid.textKosong(TPerawatLuar,"Perawat Luar");
        }else if(TBidan.getText().trim().equals("")){
            Valid.textKosong(TBidan,"Bidan");
        }else if(TSewaOK.getText().trim().equals("")){
            Valid.textKosong(TSewaOK,"Sewa OK");
        }else if(TSewaVk.getText().trim().equals("")){
            Valid.textKosong(TSewaVk,"Sewa VK");
        }else if(TBagianRS.getText().trim().equals("")){
            Valid.textKosong(TBagianRS,"Bagian RS");
        }else if(TOmloop.getText().trim().equals("")){
            Valid.textKosong(TOmloop,"Omloop");
        }else{
            Sequel.menyimpan("paket_operasi","'"+TKd.getText()+"', '"+TNm.getText()+"','"+TOperator1.getText()+
                    "','"+TOperator2.getText()+"','"+TOperator3.getText()+"','"+TAsisOperator1.getText()+
                    "','"+TAsisOperator2.getText()+"','"+TAsisOperator3.getText()+"','"+TAnak.getText()+
                    "','"+TResusitas.getText()+"','"+TAnastesi.getText()+"','"+TAsisAnastesi.getText()+
                    "','"+TBidan.getText()+"','"+TPerawatLuar.getText()+"','"+TSewaOK.getText()+
                    "','"+TAlat.getText()+"','"+TSewaVk.getText()+"','"+TBagianRS.getText()+"','"+TOmloop.getText()+"'","Kode Jenis");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TOmloop,BtnBatal);
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
        Valid.hapusTable(tabMode,TKd,"paket_operasi","kode_paket");
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKd.getText().trim().equals("")){
            Valid.textKosong(TKd,"Kode Jenis");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"Nama Perawatan");
        }else if(TOperator1.getText().trim().equals("")){
            Valid.textKosong(TOperator1,"Operator 1");
        }else if(TOperator2.getText().trim().equals("")){
            Valid.textKosong(TOperator2,"Operator 2");
        }else if(TOperator3.getText().trim().equals("")){
            Valid.textKosong(TOperator3,"Operator 3");
        }else if(TAsisOperator1.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 1");
        }else if(TAsisOperator2.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 2");
        }else if(TAsisOperator3.getText().trim().equals("")){
            Valid.textKosong(TAsisOperator1,"Asisten Operator 3");
        }else if(TAlat.getText().trim().equals("")){
            Valid.textKosong(TAlat,"Alat OK");
        }else if(TAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAnastesi,"Dokter Anetesi");
        }else if(TAsisAnastesi.getText().trim().equals("")){
            Valid.textKosong(TAsisAnastesi,"Asisten Anestesi");
        }else if(TAnak.getText().trim().equals("")){
            Valid.textKosong(TAnak,"Dokter Anak");
        }else if(TResusitas.getText().trim().equals("")){
            Valid.textKosong(TResusitas,"Perawat Resusitas");
        }else if(TPerawatLuar.getText().trim().equals("")){
            Valid.textKosong(TPerawatLuar,"Perawat Luar");
        }else if(TBidan.getText().trim().equals("")){
            Valid.textKosong(TBidan,"Bidan");
        }else if(TSewaOK.getText().trim().equals("")){
            Valid.textKosong(TSewaOK,"Sewa OK");
        }else if(TSewaVk.getText().trim().equals("")){
            Valid.textKosong(TSewaVk,"Sewa VK");
        }else if(TBagianRS.getText().trim().equals("")){
            Valid.textKosong(TBagianRS,"Bagian RS");
        }else if(TOmloop.getText().trim().equals("")){
            Valid.textKosong(TOmloop,"Omloop");
        }else{
            Sequel.mengedit("paket_operasi","kode_paket='"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0) +"'",
                    "kode_paket='"+TKd.getText()+"',nm_perawatan='"+TNm.getText()+"',operator1='"+TOperator1.getText()+"',operator2='"+TOperator2.getText()+"',operator3='"+TOperator3.getText()+
                    "',asisten_operator1='"+TAsisOperator1.getText()+"',asisten_operator2='"+TAsisOperator2.getText()+"',asisten_operator3='"+TAsisOperator3.getText()+
                    "',dokter_anak='"+TAnak.getText()+"',perawaat_resusitas='"+TResusitas.getText()+
                    "',alat='"+TAlat.getText()+"',dokter_anestesi='"+TAnastesi.getText()+"',asisten_anestesi='"+TAsisAnastesi.getText()+
                    "',bidan='"+TBidan.getText()+"',perawat_luar='"+TPerawatLuar.getText()+"',sewa_ok='"+TSewaOK.getText()+"',sewa_vk='"+TSewaVk.getText()+"',bagian_rs='"+TBagianRS.getText()+"',omloop='"+TOmloop.getText()+"'");
            if(tabMode.getRowCount()!=0){tampil();}
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Valid.MyReport("rptPaketOperasi.jrxml","report","::[ Data Paket Operasi ]::",
                   "select kode_paket, nm_perawatan,(operator1+operator2+operator3+asisten_operator1+asisten_operator2+asisten_operator3+"+
                   "dokter_anak+perawaat_resusitas+alat+dokter_anestesi+asisten_anestesi+bidan+perawat_luar+sewa_ok+sewa_vk+bagian_rs+omloop) as jumlah "+
                   "from paket_operasi where kode_paket like '%"+TCari.getText()+"%' or "+
                   "nm_perawatan like '%"+TCari.getText()+"%' order by kode_paket ");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
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

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void TSewaOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSewaOKKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAlat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TPerawatLuar.requestFocus();
        }
}//GEN-LAST:event_TSewaOKKeyPressed

private void TAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAnastesiKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator3.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAnak.requestFocus();
        }
}//GEN-LAST:event_TAnastesiKeyPressed

private void TAsisAnastesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisAnastesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAnastesi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TResusitas.requestFocus();
        }
}//GEN-LAST:event_TAsisAnastesiKeyPressed

private void TPerawatLuarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPerawatLuarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TSewaOK.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOmloop.requestFocus();
        }
}//GEN-LAST:event_TPerawatLuarKeyPressed

private void TOperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TOperator3.requestFocus();
        }
}//GEN-LAST:event_TOperator2KeyPressed

private void TOperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOperator3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TOperator2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAnastesi.requestFocus();
        }
}//GEN-LAST:event_TOperator3KeyPressed

private void TAsisOperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator3.requestFocus();
        }
}//GEN-LAST:event_TAsisOperator2KeyPressed

private void TAsisOperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAsisOperator3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisOperator2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisAnastesi.requestFocus();
        }
}//GEN-LAST:event_TAsisOperator3KeyPressed

private void TAnakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAnakKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAnastesi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBagianRS.requestFocus();
        }
}//GEN-LAST:event_TAnakKeyPressed

private void TResusitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TResusitasKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAsisAnastesi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TSewaVk.requestFocus();
        } 
}//GEN-LAST:event_TResusitasKeyPressed

private void TBidanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBidanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TSewaVk.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAlat.requestFocus();
        }
}//GEN-LAST:event_TBidanKeyPressed

private void TBagianRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBagianRSKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TAnak.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TAsisOperator1.requestFocus();
        }
}//GEN-LAST:event_TBagianRSKeyPressed

private void TSewaVkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSewaVkKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TResusitas.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            TBidan.requestFocus();
        }
}//GEN-LAST:event_TSewaVkKeyPressed

private void TOmloopKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TOmloopKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isjml();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            isjml();
            TPerawatLuar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isjml();
            BtnSimpan.requestFocus();
        }
}//GEN-LAST:event_TOmloopKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPaketOperasi dialog = new DlgPaketOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlat;
    private widget.TextBox TAnak;
    private widget.TextBox TAnastesi;
    private widget.TextBox TAsisAnastesi;
    private widget.TextBox TAsisOperator1;
    private widget.TextBox TAsisOperator2;
    private widget.TextBox TAsisOperator3;
    private widget.TextBox TBagianRS;
    private widget.TextBox TBidan;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TNm;
    private widget.TextBox TOmloop;
    private widget.TextBox TOperator1;
    private widget.TextBox TOperator2;
    private widget.TextBox TOperator3;
    private widget.TextBox TPerawatLuar;
    private widget.TextBox TResusitas;
    private widget.TextBox TSewaOK;
    private widget.TextBox TSewaVk;
    private widget.TextBox TTotal;
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
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{            
            pstampil.setString(1,"%"+TCari.getText()+"%");
            pstampil.setString(2,"%"+TCari.getText()+"%");
            rs=pstampil.executeQuery();
            while(rs.next()){
                String[] data={rs.getString("kode_paket"),
                               rs.getString("nm_perawatan"),
                               Valid.SetAngka(rs.getDouble("operator1")),
                               Valid.SetAngka(rs.getDouble("operator2")),
                               Valid.SetAngka(rs.getDouble("operator3")),
                               Valid.SetAngka(rs.getDouble("asisten_operator1")),
                               Valid.SetAngka(rs.getDouble("asisten_operator2")),
                               Valid.SetAngka(rs.getDouble("asisten_operator3")),
                               Valid.SetAngka(rs.getDouble("dokter_anestesi")),
                               Valid.SetAngka(rs.getDouble("asisten_anestesi")),
                               Valid.SetAngka(rs.getDouble("dokter_anak")),
                               Valid.SetAngka(rs.getDouble("perawaat_resusitas")),
                               Valid.SetAngka(rs.getDouble("bidan")),
                               Valid.SetAngka(rs.getDouble("perawat_luar")),
                               Valid.SetAngka(rs.getDouble("alat")),
                               Valid.SetAngka(rs.getDouble("sewa_ok")),
                               Valid.SetAngka(rs.getDouble("sewa_vk")),
                               Valid.SetAngka(rs.getDouble("bagian_rs")),
                               Valid.SetAngka(rs.getDouble("omloop")),
                               Valid.SetAngka(rs.getDouble("jumlah"))};
                tabMode.addRow(data);
            }  
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TKd.setText("");
        TNm.setText("");
        TOperator1.setText("0");
        TOperator2.setText("0");
        TOperator3.setText("0");
        TAsisOperator1.setText("0");
        TAsisOperator2.setText("0");
        TAsisOperator3.setText("0");
        TAnastesi.setText("0");
        TAsisAnastesi.setText("0");
        TAnak.setText("0");
        TResusitas.setText("0");
        TBidan.setText("0");
        TAlat.setText("0");
        TPerawatLuar.setText("0");
        TSewaOK.setText("0");
        TSewaVk.setText("0");
        TBagianRS.setText("0");
        TOmloop.setText("0");
        //Valid.autoNomer(" paket_operasi ","JP",6,TKd);
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_paket,4),signed)),0) from paket_operasi  ","PK",6,TKd);
        TKd.requestFocus();
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            TKd.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
            TNm.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            TOperator1.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            TOperator2.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            TOperator3.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
            TAsisOperator1.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
            TAsisOperator2.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString());
            TAsisOperator3.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
            TAnastesi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
            TAsisAnastesi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString());
            TAnak.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString());
            TResusitas.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString());
            TBidan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),12).toString());
            TPerawatLuar.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString());
            TAlat.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString());
            TSewaOK.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),15).toString());
            TSewaVk.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),16).toString());
            TBagianRS.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),17).toString());
            TOmloop.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),18).toString());
            TTotal.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),19).toString());
        }
    }


    private void isjml(){       

        if((!TOperator1.getText().equals(""))){
           operator1=Double.parseDouble(TOperator1.getText().trim());            
        }
        if((!TOperator2.getText().equals(""))){
           operator2=Double.parseDouble(TOperator2.getText().trim());            
        }
        if((!TOperator3.getText().equals(""))){
           operator3=Double.parseDouble(TOperator3.getText().trim());            
        }
        if((!TAsisOperator1.getText().equals(""))){
           asistenoperator1=Double.parseDouble(TAsisOperator1.getText().trim());            
        }
        if((!TAsisOperator2.getText().equals(""))){
           asistenoperator2=Double.parseDouble(TAsisOperator2.getText().trim());            
        }
        if((!TAsisOperator3.getText().equals(""))){
           asistenoperator3=Double.parseDouble(TAsisOperator3.getText().trim());            
        }
        if((!TAnastesi.getText().equals(""))){
           anastesi=Double.parseDouble(TAnastesi.getText().trim());            
        }
        if((!TAsisAnastesi.getText().equals(""))){
           asistenanas=Double.parseDouble(TAsisAnastesi.getText().trim());            
        }
        if((!TAnak.getText().equals(""))){
           dokteranak=Double.parseDouble(TAnak.getText().trim());            
        }
        if((!TResusitas.getText().equals(""))){
           perawatresusitas=Double.parseDouble(TResusitas.getText().trim());            
        }
        if((!TBidan.getText().equals(""))){
           bidan=Double.parseDouble(TBidan.getText().trim());            
        }
        if((!TAlat.getText().equals(""))){
           alat=Double.parseDouble(TAlat.getText().trim());            
        }
        if((!TSewaOK.getText().equals(""))){
           sewaok=Double.parseDouble(TSewaOK.getText().trim());            
        }
        if((!TPerawatLuar.getText().equals(""))){
           perawatluar=Double.parseDouble(TPerawatLuar.getText().trim());            
        }
        if((!TSewaVk.getText().equals(""))){
           sewavk=Double.parseDouble(TSewaVk.getText().trim());            
        }
        if((!TBagianRS.getText().equals(""))){
           bagianrs=Double.parseDouble(TBagianRS.getText().trim());            
        }
        if((!TOmloop.getText().equals(""))){
           omloop=Double.parseDouble(TOmloop.getText().trim());            
        }
        
        TTotal.setText(""+(operator1+operator2+operator3+asistenoperator1+
                asistenoperator2+asistenoperator3+dokteranak+perawatresusitas+bidan+
               alat+anastesi+perawatluar+asistenanas+sewaok+sewavk+bagianrs+omloop));
    }
    
    public JTextField getTextField(){
        return TKd;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,248));
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
        BtnSimpan.setEnabled(var.getpaket_operasi());
        BtnHapus.setEnabled(var.getpaket_operasi());
        BtnEdit.setEnabled(var.getpaket_operasi());
        BtnPrint.setEnabled(var.getpaket_operasi());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }

    
}
