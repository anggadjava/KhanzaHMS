/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
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
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author perpustakaan
 */
public final class DlgResepObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public DlgResepObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Rawat",
                      "No.Rekam Medis",
                      "Nama Pasien",
                      "Kode Penyakit",
                      "Nama Penyakit",
                      "Kode Obat",
                      "Obat Penyakit",
                      "Dosis Obat",
                      "Tgl.Rawat"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResep.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 9; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(100);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TKdPny.setDocument(new batasInput((byte)10).getKata(TKdPny));
        TKdOb.setDocument(new batasInput((byte)15).getKata(TKdOb));
        TDosis.setDocument(new batasInput((byte)25).getKata(TDosis));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        
        
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
        
        dlgobt.getTable().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TKdOb.setText(dlgobt.getTextField().getText());    
                isOb();
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
        
        reg.getButton().addActionListener((ActionEvent e) -> {
            TNoRw.setText(reg.getTextField().getText());
            isRawat();
            isPsien();
        });
        ChkInput.setSelected(false);
        //isForm(); 
    }

    DlgCariPenyakit dlgpnykt=new DlgCariPenyakit(null,false);
    DlgBarang dlgobt=new DlgBarang(null,false);
    DlgReg reg=new DlgReg(null,false);

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
        MnKamarInap = new javax.swing.JMenuItem();
        MnRawanInap = new javax.swing.JMenuItem();
        MnRujuk = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
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
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TKdPny = new widget.TextBox();
        TKdOb = new widget.TextBox();
        jLabel10 = new widget.Label();
        TDosis = new widget.TextBox();
        TNmOb = new widget.TextBox();
        jLabel3 = new widget.Label();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        BtnSeek = new widget.Button();
        TNoRM = new widget.TextBox();
        BtnSeek1 = new widget.Button();
        TNmPny = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        jLabel11 = new widget.Label();
        TglRawat = new widget.TextBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnRawatJalan.setBackground(new java.awt.Color(255, 255, 255));
        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnRawatJalan.setForeground(java.awt.Color.darkGray);
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/addressbook-edit24.png"))); // NOI18N
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
        MnKamarInap.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnKamarInap.setForeground(java.awt.Color.darkGray);
        MnKamarInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/home24.png"))); // NOI18N
        MnKamarInap.setText("Kamar Inap");
        MnKamarInap.setName("MnKamarInap"); // NOI18N
        MnKamarInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnKamarInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKamarInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnKamarInap);

        MnRawanInap.setBackground(new java.awt.Color(255, 255, 255));
        MnRawanInap.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnRawanInap.setForeground(java.awt.Color.darkGray);
        MnRawanInap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Stethoscope.png"))); // NOI18N
        MnRawanInap.setText("Tagihan/Tindakan Rawat Inap");
        MnRawanInap.setName("MnRawanInap"); // NOI18N
        MnRawanInap.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRawanInap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawanInapActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRawanInap);

        MnRujuk.setBackground(new java.awt.Color(255, 255, 255));
        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnRujuk.setForeground(java.awt.Color.darkGray);
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/new_page.png"))); // NOI18N
        MnRujuk.setText("Rujuk Pasien");
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(250, 28));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnRujuk);

        MnPemberianObat.setBackground(new java.awt.Color(255, 255, 255));
        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MnPemberianObat.setForeground(java.awt.Color.darkGray);
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Vial-O.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat");
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(250, 28));
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPemberianObat);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Resep Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setAutoCreateRowSorter(true);
        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setComponentPopupMenu(jPopupMenu1);
        tbResep.setName("tbResep"); // NOI18N
        tbResep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbResepMouseClicked(evt);
            }
        });
        tbResep.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbResepKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbResep);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(55, 30));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(52, 30));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-04" }));
        DTPCari1.setDisplayFormat("yyyy-MM-dd");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2014-05-04" }));
        DTPCari2.setDisplayFormat("yyyy-MM-dd");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(440, 137));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(76, 12, 150, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(350, 12, 343, 23);

        TKdPny.setHighlighter(null);
        TKdPny.setName("TKdPny"); // NOI18N
        TKdPny.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdPnyKeyPressed(evt);
            }
        });
        FormInput.add(TKdPny);
        TKdPny.setBounds(76, 42, 150, 23);

        TKdOb.setHighlighter(null);
        TKdOb.setName("TKdOb"); // NOI18N
        TKdOb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdObKeyPressed(evt);
            }
        });
        FormInput.add(TKdOb);
        TKdOb.setBounds(76, 72, 150, 23);

        jLabel10.setText("Dosis :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 102, 73, 23);

        TDosis.setHighlighter(null);
        TDosis.setName("TDosis"); // NOI18N
        TDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TDosisKeyPressed(evt);
            }
        });
        FormInput.add(TDosis);
        TDosis.setBounds(76, 102, 330, 23);

        TNmOb.setEditable(false);
        TNmOb.setHighlighter(null);
        TNmOb.setName("TNmOb"); // NOI18N
        FormInput.add(TNmOb);
        TNmOb.setBounds(228, 72, 465, 23);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 73, 23);

        jLabel12.setText("Penyakit :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 42, 73, 23);

        jLabel13.setText("Obat :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 72, 73, 23);

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
        FormInput.add(BtnSeek);
        BtnSeek.setBounds(695, 12, 28, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(228, 12, 120, 23);

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
        FormInput.add(BtnSeek1);
        BtnSeek1.setBounds(695, 42, 28, 23);

        TNmPny.setEditable(false);
        TNmPny.setName("TNmPny"); // NOI18N
        FormInput.add(TNmPny);
        TNmPny.setBounds(228, 42, 465, 23);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
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
        BtnSeek2.setBounds(695, 72, 28, 23);

        jLabel11.setText("Tanggal Perawatan :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(420, 102, 150, 23);

        TglRawat.setEditable(false);
        TglRawat.setHighlighter(null);
        TglRawat.setName("TglRawat"); // NOI18N
        TglRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglRawatKeyPressed(evt);
            }
        });
        FormInput.add(TglRawat);
        TglRawat.setBounds(573, 102, 120, 23);

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
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,TKdPny);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TKdPnyKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdPnyKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPny();
        }else{            
            Valid.pindah(evt,TNoRw,TKdOb);
        }
}//GEN-LAST:event_TKdPnyKeyPressed

    private void TKdObKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdObKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isOb();
        }else{            
            Valid.pindah(evt,TKdPny,TDosis);
        }
}//GEN-LAST:event_TKdObKeyPressed

    private void TDosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TDosisKeyPressed
        Valid.pindah(evt,TKdOb,BtnSimpan);
}//GEN-LAST:event_TDosisKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(TKdPny.getText().trim().equals("")||TNmPny.getText().trim().equals("")){
            Valid.textKosong(TKdPny,"penyakit");
        }else if(TKdOb.getText().trim().equals("")||TNmOb.getText().trim().equals("")){
            Valid.textKosong(TKdOb,"databarang");
        }else if(TDosis.getText().trim().equals("")){
            Valid.textKosong(TDosis,"dosis databarang");
        }else{
            Sequel.menyimpan("resep_obat","'"+TNoRw.getText()+"','"+
                    TKdPny.getText()+"','"+
                    TKdOb.getText()+"','"+
                    TDosis.getText()+"'","No.Rawat,Penyakit dan Obat");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TNmOb,BtnBatal);
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
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
             JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(!(TPasien.getText().trim().equals(""))){
            try{
                Sequel.queryu("delete from resep_obat where no_rawat='"+TNoRw.getText()+"' and kd_penyakit='"+TKdPny.getText()+"' and kode_brng='"+TKdOb.getText()+"'");
                tampil();
            }catch(Exception e){
                System.out.println("Pesan Error : "+e);
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
            }
        }

        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal,BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
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
            String tgl=" reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
            Valid.MyReport("rptResep.jrxml","report","::[ Rekam Data Pemberian Resep ]::","select resep_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "resep_obat.kd_penyakit,penyakit.nm_penyakit, "+
                   "resep_obat.kode_brng,databarang.nama_brng,resep_obat.dosis_obat,reg_periksa.tgl_registrasi "+
                   "from resep_obat inner join reg_periksa inner join pasien inner join penyakit inner join databarang "+
                   "on resep_obat.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and resep_obat.kd_penyakit=penyakit.kd_penyakit "+
                   "and resep_obat.kode_brng=databarang.kode_brng "+
                   "where "+tgl+"and resep_obat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.dosis_obat like '%"+TCari.getText().trim()+"%' "+
                   "order by resep_obat.no_rawat");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, TNmOb);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        reg.tampil();
        reg.emptTeks();
        reg.isCek();
        reg.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        reg.setLocationRelativeTo(internalFrame1);
        reg.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,TNoRw,TKdPny);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        dlgpnykt.emptTeks();
        dlgpnykt.isCek();
        dlgpnykt.tampil();
        dlgpnykt.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgpnykt.setLocationRelativeTo(internalFrame1);
        dlgpnykt.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,TKdPny,TKdOb);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        dlgobt.emptTeks();
        dlgobt.tampil(" order by databarang.nama_brng");
        dlgobt.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgobt.setLocationRelativeTo(internalFrame1);
        dlgobt.setVisible(true);
}//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        Valid.pindah(evt,TKdOb,TDosis);
}//GEN-LAST:event_BtnSeek2KeyPressed

    private void tbResepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbResepMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbResepMouseClicked

    private void tbResepKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbResepKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbResepKeyPressed

private void TglRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglRawatKeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_TglRawatKeyPressed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbResep.requestFocus();
      }else{
                DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
                dlgrwjl.isCek();
                dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwjl.setLocationRelativeTo(internalFrame1);
                dlgrwjl.emptTeks();

                dlgrwjl.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());    
                dlgrwjl.tampilDr();
                dlgrwjl.setVisible(true);
                //this.dispose();
     }
        
       
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnKamarInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKamarInapActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbResep.requestFocus();
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
                //this.dispose();
        }
}//GEN-LAST:event_MnKamarInapActionPerformed

private void MnRawanInapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawanInapActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbResep.requestFocus();
      }else{
                 DlgRawatInap dlgrwinap=new DlgRawatInap(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.emptTeks();    
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());   
                dlgrwinap.tampilDr();
                dlgrwinap.setVisible(true);
                //this.dispose();
      }
           
        
}//GEN-LAST:event_MnRawanInapActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbResep.requestFocus();
      }else{
                DlgRujuk dlgrjk=new DlgRujuk(null,false);
                dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrjk.setLocationRelativeTo(internalFrame1);
                dlgrjk.emptTeks();
                dlgrjk.isCek();
                dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                dlgrjk.tampil();
                dlgrjk.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
      if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbResep.requestFocus();
      }else{
                DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.emptTeks(); 
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                dlgrwinap.tampilPO();
                dlgrwinap.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgResepObat dialog = new DlgResepObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSeek2;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnKamarInap;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnRawanInap;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TDosis;
    private widget.TextBox TKdOb;
    private widget.TextBox TKdPny;
    private widget.TextBox TNmOb;
    private widget.TextBox TNmPny;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TglRawat;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbResep;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String tgl=" reg_periksa.tgl_registrasi between '"+DTPCari1.getSelectedItem()+"' and '"+DTPCari2.getSelectedItem()+"' ";
        String sql="select resep_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                   "resep_obat.kd_penyakit,penyakit.nm_penyakit, "+
                   "resep_obat.kode_brng,databarang.nama_brng,resep_obat.dosis_obat,reg_periksa.tgl_registrasi "+
                   "from resep_obat inner join reg_periksa inner join pasien inner join penyakit inner join databarang "+
                   "on resep_obat.no_rawat=reg_periksa.no_rawat "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and resep_obat.kd_penyakit=penyakit.kd_penyakit "+
                   "and resep_obat.kode_brng=databarang.kode_brng "+
                   "where "+tgl+"and resep_obat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.kd_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and penyakit.nm_penyakit like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                    tgl+"and resep_obat.dosis_obat like '%"+TCari.getText().trim()+"%' "+
                   "order by resep_obat.no_rawat";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
        Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               rs.getString(3),
                               rs.getString(4),
                               rs.getString(5),
                               rs.getString(6),
                               rs.getString(7),
                               rs.getString(8),
                               rs.getString(9)};
                tabMode.addRow(data);
            }
        }catch(SQLException e){
            System.out.println("Error : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        //TNoRw.setText("");
        //TKdPny.setText("");
        //TNmPny.setText("");
        TKdOb.setText("");
        TNmOb.setText("");
        TDosis.setText("");
        TNoRw.requestFocus();
    }

    private void getData() {
        if(tbResep.getSelectedRow()!= -1){
            TNoRw.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
            TKdPny.setText(tbResep.getValueAt(tbResep.getSelectedRow(),3).toString());
            TKdOb.setText(tbResep.getValueAt(tbResep.getSelectedRow(),5).toString());
            TDosis.setText(tbResep.getValueAt(tbResep.getSelectedRow(),7).toString());
            TglRawat.setText(tbResep.getValueAt(tbResep.getSelectedRow(),8).toString());
            isRawat();
            isPny();
            isPsien();
            isOb();            
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
         Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TglRawat);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }

    private void isPny() {
        Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit='"+TKdPny.getText()+"' ",TNmPny);
    }

    private void isOb() {
       Sequel.cariIsi("select nama_brng from databarang where kode_brng='"+TKdOb.getText()+"' ",TNmOb);
    }
    
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();        
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
        BtnSimpan.setEnabled(var.getresep());
        BtnHapus.setEnabled(var.getresep());
        BtnPrint.setEnabled(var.getresep());
        
        MnKamarInap.setEnabled(var.getkamin());
        MnPemberianObat.setEnabled(var.getberiobat());
        MnRawanInap.setEnabled(var.getranap());
        MnRawatJalan.setEnabled(var.getralan());
        //MnResepObat.setEnabled(var.getresep());
        MnRujuk.setEnabled(var.getrujuk());
    }
}
