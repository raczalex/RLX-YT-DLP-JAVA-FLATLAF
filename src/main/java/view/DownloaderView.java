package view;

import model.DownloaderModel;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raczalex
 */

public class DownloaderView extends javax.swing.JFrame {
    private DownloaderModel downloaderModel;
    
    public DownloaderView(DownloaderModel downloaderModel) {
        initComponents();
        this.downloaderModel = downloaderModel;
        this.setTitle(downloaderModel.getDownloader_window_title());
        initData();
        String[] video_types = new String[]{"YouTube", "Playlist", "Other"};
        downloaderModel.setVideo_types(video_types);
        createResultsTableModel();
    }
    
    private void initData(){
        url_textfield.setText(downloaderModel.getRequired_video_url());
        path_textfield.setText(downloaderModel.getRequired_download_path());
        format_combobox.setSelectedItem(downloaderModel.getRequired_download_format());
        filename_textfield.setText(downloaderModel.getOptional_download_filename());
        customargs_textfield.setText(downloaderModel.getOptional_download_custom_args());
        sponsorblock_combobox.setSelectedItem(downloaderModel.getOptional_download_sponsorblock());
    }
    
    private void createResultsTableModel(){
        String[] results_table_cols = new String[]{"Title", "Url", "Type","Format","Est Size","Progress","Status","Speed","ETA"};
        downloaderModel.setResults_table_cols(results_table_cols);
            
        results_table.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            results_table_cols
        ));
        
        DefaultTableModel results_table_model = (DefaultTableModel) results_table.getModel();
        results_table_model.setRowCount(0);
        DefaultTableCellRenderer resultsCenterRenderer = new DefaultTableCellRenderer();
        resultsCenterRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        results_table.setDefaultRenderer(Object.class, resultsCenterRenderer);
        
        results_table.setDefaultEditor(Object.class, null);
        results_table.getTableHeader().setReorderingAllowed(false);
    }
    
    // Attach event listener to the button
    public void addButtonListener(ActionListener listener) {
        browse_button.addActionListener(listener);
        add_button.addActionListener(listener);
        remove_button.addActionListener(listener);
        download_button.addActionListener(listener);
        download_all_button.addActionListener(listener);
    }
    
    public void addJTableListener(MouseListener listener){
        results_table.addMouseListener(listener);
    }
    
    public void addFrameListener(ComponentListener listener){
        this.addComponentListener(listener);
    }

    // Attach event listener to the text field
    //public void addTextFieldListener(ActionListener listener) {
        //textField.addActionListener(listener);
    //}

    // Attach event listener to checkboxes
    public void addCheckboxListener(ActionListener listener) {
        metadata_checkbox.addActionListener(listener);
        thumbnail_checkbox.addActionListener(listener);
        subtitles_checkbox.addActionListener(listener);
        dark_checkbox.addActionListener(listener);
        //checkbox2.addActionListener(listener);
    }

    public void addComboboxListener(ActionListener listener){
        format_combobox.addActionListener(listener);
        sponsorblock_combobox.addActionListener(listener);
    }
    
//    public void addTextfieldFocusListener(FocusListener listener){
//        url_textfield.addFocusListener(listener);
//    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        url_label = new javax.swing.JLabel();
        url_textfield = new javax.swing.JTextField();
        required_label = new javax.swing.JLabel();
        path_label = new javax.swing.JLabel();
        path_textfield = new javax.swing.JTextField();
        browse_button = new javax.swing.JButton();
        format_label = new javax.swing.JLabel();
        format_combobox = new javax.swing.JComboBox<>();
        sponsorblock_label = new javax.swing.JLabel();
        sponsorblock_combobox = new javax.swing.JComboBox<>();
        filename_label = new javax.swing.JLabel();
        filename_textfield = new javax.swing.JTextField();
        optional_label = new javax.swing.JLabel();
        customargs_label = new javax.swing.JLabel();
        customargs_textfield = new javax.swing.JTextField();
        controls_label = new javax.swing.JLabel();
        add_button = new javax.swing.JButton();
        remove_button = new javax.swing.JButton();
        download_button = new javax.swing.JButton();
        download_all_button = new javax.swing.JButton();
        include_label = new javax.swing.JLabel();
        thumbnail_checkbox = new javax.swing.JCheckBox();
        dark_checkbox = new javax.swing.JCheckBox();
        subtitles_checkbox = new javax.swing.JCheckBox();
        theme_label = new javax.swing.JLabel();
        metadata_checkbox = new javax.swing.JCheckBox();
        results_jscrollpane = new javax.swing.JScrollPane();
        results_table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(943, 624));

        jPanel3.setPreferredSize(new java.awt.Dimension(943, 624));

        url_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        url_label.setText("URL");

        url_textfield.setText("N/A");

        required_label.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        required_label.setIcon(new FlatSVGIcon("required.svg"));
        required_label.setText("REQUIRED");

        path_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        path_label.setText("PATH");

        path_textfield.setText("N/A");

        browse_button.setText("BROWSE");

        format_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        format_label.setText("FORMAT");

        format_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BEST", "MP4", "MP3", "M4A", "WAV", "FLAC" }));
        format_combobox.setSelectedIndex(1);

        sponsorblock_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        sponsorblock_label.setText("SPONSORBLOCK");

        sponsorblock_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "REMOVE", "MARK" }));

        filename_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        filename_label.setText("FILENAME");

        filename_textfield.setText("%(title)s.%(ext)s");

        optional_label.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        optional_label.setIcon(new FlatSVGIcon("optional.svg"));
        optional_label.setText("OPTIONAL");

        customargs_label.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        customargs_label.setText("CUSTOM ARGS");

        customargs_textfield.setText("N/A");

        controls_label.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        controls_label.setIcon(new FlatSVGIcon("controls.svg"));
        controls_label.setText("CONTROLS");

        add_button.setText("ADD");

        remove_button.setText("REMOVE");

        download_button.setText("DOWNLOAD");

        download_all_button.setText("DOWNLOAD ALL");

        include_label.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        include_label.setIcon(new FlatSVGIcon("include.svg"));
        include_label.setText("INCLUDE");

        thumbnail_checkbox.setText("THUMBNAIL");

        dark_checkbox.setText("DARK");

        subtitles_checkbox.setText("SUBTITLES");

        theme_label.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        theme_label.setIcon(new FlatSVGIcon("theme.svg"));
        theme_label.setText("THEME");

        metadata_checkbox.setText("METADATA");

        results_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title", "Url", "Type", "Format","Est Size","Progress",
                "Status","Speed","ETA"
            }
        ));
        results_table.setFillsViewportHeight(true);
        results_jscrollpane.setViewportView(results_table);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(results_jscrollpane)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(path_textfield)
                            .addComponent(path_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(url_textfield)
                            .addComponent(url_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(required_label)
                            .addComponent(browse_button, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                            .addComponent(format_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(format_combobox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(customargs_textfield)
                                .addComponent(customargs_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(filename_textfield)
                                .addComponent(filename_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sponsorblock_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sponsorblock_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(optional_label))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(controls_label)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(add_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(remove_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(download_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(download_all_button, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(thumbnail_checkbox)
                            .addComponent(subtitles_checkbox)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(metadata_checkbox)
                                    .addComponent(include_label))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(theme_label)
                                    .addComponent(dark_checkbox))))))
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(optional_label)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(controls_label)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(filename_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filename_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(customargs_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(customargs_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(add_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(remove_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(download_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(download_all_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(required_label)
                        .addGap(18, 18, 18)
                        .addComponent(url_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(url_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(path_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(path_textfield, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(sponsorblock_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sponsorblock_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(browse_button, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(format_label, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(format_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(include_label)
                            .addComponent(theme_label))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(metadata_checkbox)
                            .addComponent(dark_checkbox))
                        .addGap(19, 19, 19)
                        .addComponent(thumbnail_checkbox)
                        .addGap(19, 19, 19)
                        .addComponent(subtitles_checkbox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(results_jscrollpane, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      
    }

    public JButton getAdd_button() {
        return add_button;
    }

    public JButton getBrowse_button() {
        return browse_button;
    }

    public JLabel getControls_label() {
        return controls_label;
    }

    public JLabel getCustomargs_label() {
        return customargs_label;
    }

    public JTextField getCustomargs_textfield() {
        return customargs_textfield;
    }

    public JCheckBox getDark_checkbox() {
        return dark_checkbox;
    }

    public JButton getDownload_all_button() {
        return download_all_button;
    }

    public JButton getDownload_button() {
        return download_button;
    }

    public JLabel getFilename_label() {
        return filename_label;
    }

    public JTextField getFilename_textfield() {
        return filename_textfield;
    }

    public JComboBox<String> getFormat_combobox() {
        return format_combobox;
    }

    public JLabel getFormat_label() {
        return format_label;
    }

    public JLabel getInclude_label() {
        return include_label;
    }

    public JPanel getjPanel3() {
        return jPanel3;
    }

    public JScrollPane getResults_jScrollPane() {
        return results_jscrollpane;
    }

    public JCheckBox getMetadata_checkbox() {
        return metadata_checkbox;
    }

    public JLabel getOptional_label() {
        return optional_label;
    }

    public JLabel getPath_label() {
        return path_label;
    }

    public JTextField getPath_textfield() {
        return path_textfield;
    }

    public JButton getRemove_button() {
        return remove_button;
    }

    public JLabel getRequired_label() {
        return required_label;
    }

    public JTable getResults_table() {
        return results_table;
    }

    public JComboBox<String> getSponsorblock_combobox() {
        return sponsorblock_combobox;
    }

    public JLabel getSponsorblock_label() {
        return sponsorblock_label;
    }

    public JCheckBox getSubtitles_checkbox() {
        return subtitles_checkbox;
    }

    public JLabel getTheme_label() {
        return theme_label;
    }

    public JCheckBox getThumbnail_checkbox() {
        return thumbnail_checkbox;
    }

    public JLabel getUrl_label() {
        return url_label;
    }

    public JTextField getUrl_textfield() {
        return url_textfield;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add_button;
    private javax.swing.JButton browse_button;
    private javax.swing.JLabel controls_label;
    private javax.swing.JLabel customargs_label;
    private javax.swing.JTextField customargs_textfield;
    private javax.swing.JCheckBox dark_checkbox;
    private javax.swing.JButton download_all_button;
    private javax.swing.JButton download_button;
    private javax.swing.JLabel filename_label;
    private javax.swing.JTextField filename_textfield;
    private javax.swing.JComboBox<String> format_combobox;
    private javax.swing.JLabel format_label;
    private javax.swing.JLabel include_label;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JCheckBox metadata_checkbox;
    private javax.swing.JLabel optional_label;
    private javax.swing.JLabel path_label;
    private javax.swing.JTextField path_textfield;
    private javax.swing.JButton remove_button;
    private javax.swing.JLabel required_label;
    private javax.swing.JScrollPane results_jscrollpane;
    private javax.swing.JTable results_table;
    private javax.swing.JComboBox<String> sponsorblock_combobox;
    private javax.swing.JLabel sponsorblock_label;
    private javax.swing.JCheckBox subtitles_checkbox;
    private javax.swing.JLabel theme_label;
    private javax.swing.JCheckBox thumbnail_checkbox;
    private javax.swing.JLabel url_label;
    private javax.swing.JTextField url_textfield;
    // End of variables declaration//GEN-END:variables
}
