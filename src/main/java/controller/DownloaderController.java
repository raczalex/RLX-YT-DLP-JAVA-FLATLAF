package controller;

import runnable.DownloadVideoRunnable;
import runnable.DownloadBinStartRunnable;
import view.DownloaderView;
import view.DownloaderVideoInfoView;
import view.DownloadBinView;
import model.DownloaderModel;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author raczalex
 */
public class DownloaderController {
    private DownloaderModel downloaderModel;
    private DownloaderView downloaderView;
    private DownloaderVideoInfoView downloaderVideoInfoView;
    private DownloadBinView downloadBinView;
    
    public DownloaderController(DownloaderModel downloaderModel,DownloaderView downloaderView){
        this.downloaderModel = downloaderModel;
        this.downloaderView = downloaderView;
        this.downloaderVideoInfoView = new DownloaderVideoInfoView(downloaderModel);
        this.downloadBinView = new DownloadBinView(downloaderModel);
        downloaderView.addButtonListener(new ButtonListener());
        downloaderView.addCheckboxListener(new CheckboxListener());
        downloaderView.addComboboxListener(new ComboboxListener());
        downloaderView.addJTableListener(new JTableListener());
        downloaderView.addFrameListener(new FrameListener());
//        downloaderView.addFocusListener(new TextfieldFocusListener());
        URLTextFieldChanged();
        PathTextFieldChanged();
        FilenameTextFieldChanged();
        CustomArgsTextFieldChanged();
    }
    
    // Event handling methods
    class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton currButton = (JButton) e.getSource();
            if (currButton == downloaderView.getBrowse_button()){
                System.out.println("REQUIRED: BROWSE BUTTON CLICKED");
                BrowseFiles();
            }
            else if (currButton == downloaderView.getAdd_button()){
                System.out.println("CONTROLS: ADD BUTTON CLICKED");
                downloaderModel.setOptional_download_filename(downloaderView.getFilename_textfield().getText());
                downloaderModel.setOptional_download_custom_args(downloaderView.getCustomargs_textfield().getText());
                
                String video_url = downloaderView.getUrl_textfield().getText();
                if (downloaderModel.checkRequired_video_url(video_url)){
                    if (downloaderModel.checkRequired_video_duplicate(video_url)){
                        downloaderModel.checkSet_video_type(video_url, false);
                        downloaderModel.setRequired_video_title(requestGetVideoTitle(downloaderModel.getRequired_video_url()));
                        downloaderModel.setRequired_video_url(video_url);
                        downloaderModel.addAdded_video_url(video_url);
                        
                        //downloaderModel.setRequired_download_path(downloaderView.getPath_textfield().getText());
                        //downloaderModel.setRequired_download_format((String) downloaderView.getFormat_combobox().getSelectedItem());
                        //downloaderModel.setOptional_download_filename(downloaderView.getFilename_textfield().getText());
                        //downloaderModel.setOptional_download_custom_args(downloaderView.getCustomargs_textfield().getText());
                        //downloaderModel.setOptional_download_sponsorblock((String) downloaderView.getSponsorblock_combobox().getSelectedItem());
                        
                        DefaultTableModel results_table_model = (DefaultTableModel) downloaderView.getResults_table().getModel();
                        results_table_model.addRow(new Object[]{downloaderModel.getRequired_video_title(),downloaderModel.getRequired_video_url(),
                            downloaderModel.getRequired_video_type(),downloaderModel.getRequired_download_format(),
                            downloaderModel.getRequired_video_size(),downloaderModel.getRequired_video_progress(),
                            downloaderModel.getRequired_video_status(),downloaderModel.getRequired_video_speed(),
                            downloaderModel.getRequired_video_eta()});
                    }
                }
            }
            else if (currButton == downloaderView.getRemove_button()){
                System.out.println("CONTROLS: REMOVE BUTTON CLICKED");
                int selectedRow = downloaderView.getResults_table().getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel results_table_model = (DefaultTableModel) downloaderView.getResults_table().getModel();
                    results_table_model.removeRow(selectedRow);
                    downloaderModel.removeAdded_video_url(downloaderModel.getSelected_video_url());
                    
                    downloaderModel.setSelected_video_title("N/A");
                    downloaderModel.setSelected_video_url("N/A");
                    downloaderModel.setSelected_video_type("N/A");
                    downloaderModel.setSelected_video_format("N/A");
                    downloaderModel.setSelected_video_size("N/A");
                    downloaderModel.setSelected_video_progress("N/A");
                    downloaderModel.setSelected_video_status("N/A");
                    downloaderModel.setSelected_video_speed("N/A");
                    downloaderModel.setSelected_video_eta("N/A");
                    
                    if (downloaderView.getResults_table().getRowCount() == 1){
                        downloaderModel.setRequired_video_title("N/A");
                        downloaderModel.setRequired_video_url("N/A");
                        downloaderModel.setRequired_video_type("N/A");
                        downloaderModel.setRequired_download_format("N/A");
                        downloaderModel.setRequired_video_size("N/A");
                        downloaderModel.setRequired_video_progress("N/A");
                        downloaderModel.setRequired_video_status("N/A");
                        downloaderModel.setRequired_video_speed("N/A");
                        downloaderModel.setRequired_video_eta("N/A");
                    }
                }
            }
            else if (currButton == downloaderView.getDownload_button()){
                System.out.println("CONTROLS: DOWNLOAD BUTTON CLICKED");
                List<String> buildCommand;
                int selectedRow = downloaderView.getResults_table().getSelectedRow();
                if (selectedRow != -1){
                    buildCommand = downloaderModel.initBuildCommand(downloaderModel.getSelected_video_url());
                    System.out.println(buildCommand);
                    downloadSelectedVideo(buildCommand, selectedRow);
                }
                //else if(!downloaderModel.getRequired_video_url().equals("N/A")){
                //    buildCommand = downloaderModel.initBuildCommand(downloaderModel.getRequired_video_url());
                //    processOutput(buildCommand, selectedRow);
                //}
                else{
                    System.out.println("No Video Selected!");
                }
            }
            else if (currButton == downloaderView.getDownload_all_button()){
                System.out.println("CONTROLS: DOWNLOAD ALL BUTTON CLICKED");
                List<String> buildCommand;
                List<String> videoUrls = downloaderModel.getAdded_video_urls();
                if (!videoUrls.isEmpty()){
                    for(int i =0;i<videoUrls.size();i++){
                        buildCommand = downloaderModel.initBuildCommand(videoUrls.get(i));
                        System.out.println(buildCommand);
                        System.out.println("VIDEO URLS: "+videoUrls);
                        System.out.println("INDEX: "+i);
                        downloadAllVideo(buildCommand, i);
                    }
                }
            }
        }
    }
    
//    class TextfieldFocusListener implements FocusListener{
//        @Override
//        public void focusGained(FocusEvent e) {
//            if ((JTextField) e.getSource() == downloaderView.getUrl_textfield()) {
//                // Handle focus gained event for the text field
//                System.out.println("Text field gained focus");
//            }
//        }
//
//        @Override
//        public void focusLost(FocusEvent e) {
//            if ((JTextField) e.getSource() == downloaderView.getUrl_textfield()) {
//                // Handle focus lost event for the text field
//                System.out.println("Text field lost focus");
//            }
//        }
//    }
    
    class JTableListener implements MouseListener{
        @Override
        public void mousePressed(MouseEvent e){
            
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable currJTable = (JTable) e.getSource();
            if (currJTable == downloaderView.getResults_table()){
                // Check if a row is selected
                int selectedRow = downloaderView.getResults_table().getSelectedRow();
                
                if (selectedRow != -1) {
                    // Perform actions when a row is selected in the specific table
                    String selected_video_title = (String) downloaderView.getResults_table().getValueAt(selectedRow,0);
                    downloaderModel.setSelected_video_title(selected_video_title);
                    
                    String selected_video_url = (String) downloaderView.getResults_table().getValueAt(selectedRow,1);
                    downloaderModel.setSelected_video_url(selected_video_url);
                    
                    String selected_video_type = (String) downloaderView.getResults_table().getValueAt(selectedRow,2);
                    downloaderModel.setSelected_video_type(selected_video_type);
                    
                    String selected_video_format = (String) downloaderView.getResults_table().getValueAt(selectedRow,3);
                    downloaderModel.setSelected_video_format(selected_video_format);
                    
                    String selected_video_size = (String) downloaderView.getResults_table().getValueAt(selectedRow,4);
                    downloaderModel.setSelected_video_size(selected_video_size);
                    
                    String selected_video_progress = (String) downloaderView.getResults_table().getValueAt(selectedRow,5);
                    downloaderModel.setSelected_video_progress(selected_video_progress);
                    
                    String selected_video_status = (String) downloaderView.getResults_table().getValueAt(selectedRow,6);
                    downloaderModel.setSelected_video_status(selected_video_status);
                    
                    String selected_video_speed = (String) downloaderView.getResults_table().getValueAt(selectedRow,7);
                    downloaderModel.setSelected_video_speed(selected_video_speed);
                    
                    String selected_video_eta = (String) downloaderView.getResults_table().getValueAt(selectedRow,8);
                    downloaderModel.setSelected_video_eta(selected_video_eta);
                    
                    String selected_video_thumbnail_url = requestGetYoutubeThumbnailUrl(selected_video_url, selected_video_type);
                    downloaderModel.setSelected_video_thumbnail_url(selected_video_thumbnail_url);
                    
                    System.out.println(selected_video_thumbnail_url);
                    
                    Icon thumbnail_icon = createIconFromUrl(selected_video_thumbnail_url,691,410);
                    downloaderVideoInfoView.updateThumbnailTitle(thumbnail_icon, selected_video_title);
                    downloaderVideoInfoView.setVisible(true);
                    updateSecondaryFramePosition();
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    }
    
    class FrameListener implements ComponentListener{
        @Override
        public void componentMoved(ComponentEvent e){
          updateSecondaryFramePosition();
        }

        @Override
        public void componentResized(ComponentEvent e) {
            
        }

        @Override
        public void componentShown(ComponentEvent e) {
            
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            
        }
    }
    
    class ComboboxListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            JComboBox currComboBox = (JComboBox) e.getSource();
            String currComboBoxSelectedItem = (String) currComboBox.getSelectedItem();
            
            if (currComboBox == downloaderView.getFormat_combobox()) {
                downloaderModel.setRequired_download_format(currComboBoxSelectedItem);
                System.out.println("FORMAT: "+currComboBoxSelectedItem);
            }
            else if (currComboBox == downloaderView.getSponsorblock_combobox()){
                downloaderModel.setOptional_download_sponsorblock(currComboBoxSelectedItem);
                System.out.println("FORMAT: "+currComboBoxSelectedItem);
            }
        }
    }
    
    // Event handling method for checkboxes
    class CheckboxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox currCheckBox = (JCheckBox) e.getSource();
            Boolean isCurrCheckBoxSelected = currCheckBox.isSelected();
       
            if (currCheckBox == downloaderView.getMetadata_checkbox()){
                downloaderModel.setInclude_metadata_checked(isCurrCheckBoxSelected);
                System.out.println("METADATA: "+isCurrCheckBoxSelected);
            }
            else if (currCheckBox == downloaderView.getThumbnail_checkbox()){
                downloaderModel.setInclude_thumbnail_checked(isCurrCheckBoxSelected);
                System.out.println("THUMBNAIL: "+isCurrCheckBoxSelected);
            }
            else if (currCheckBox == downloaderView.getSubtitles_checkbox()){
                downloaderModel.setInclude_subtitles_checked(isCurrCheckBoxSelected);
                System.out.println("SUBTITLES: "+isCurrCheckBoxSelected);
            }
            else if (currCheckBox == downloaderView.getDark_checkbox()){
                downloaderModel.setDark_checked(isCurrCheckBoxSelected);
                System.out.println("DARK: "+isCurrCheckBoxSelected);
                if (isCurrCheckBoxSelected){
                    EventQueue.invokeLater(() -> {
                        FlatDarkLaf.setup();
                        FlatLaf.updateUI();
                    });
                }
                else{
                    EventQueue.invokeLater(() -> {
                        FlatLightLaf.setup();
                        FlatLaf.updateUI();
                    });
                }
                
            }
        }
    }
    
    private void URLTextFieldChanged(){
        // Add a DocumentListener to the JTextField
        downloaderView.getUrl_textfield().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            // Method to be called when the text changes
            private void textChanged() {
                // Get the new text from the JTextField
                String url = downloaderView.getUrl_textfield().getText();
                downloaderModel.setRequired_video_url(url);
                System.out.println("URL: "+url);
                
            }
        });
    }
    
    private void PathTextFieldChanged(){
        // Add a DocumentListener to the JTextField
        downloaderView.getPath_textfield().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            // Method to be called when the text changes
            private void textChanged() {
                // Get the new text from the JTextField
                String path = downloaderView.getPath_textfield().getText();
                downloaderModel.setRequired_download_path(path);
                System.out.println("PATH: "+path);
            }
        });
    }
    
    private void FilenameTextFieldChanged(){
        // Add a DocumentListener to the JTextField
        downloaderView.getFilename_textfield().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            // Method to be called when the text changes
            private void textChanged() {
                // Get the new text from the JTextField
                String optional_filename = downloaderView.getFilename_textfield().getText();
                downloaderModel.setOptional_download_filename(optional_filename);
                System.out.println("FILENAME: "+optional_filename);
            }
        });
    }
    
    private void BrowseFiles(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(downloaderView);

        // If a directory is selected, update the model and view
        if (result == JFileChooser.APPROVE_OPTION) {
            String folderPath = fileChooser.getSelectedFile().getAbsolutePath();
            downloaderModel.setRequired_download_path(folderPath);
            downloaderView.getPath_textfield().setText(folderPath);
        }
    }
    
    private void CustomArgsTextFieldChanged(){
        // Add a DocumentListener to the JTextField
        downloaderView.getCustomargs_textfield().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                textChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not used for plain text components
            }

            // Method to be called when the text changes
            private void textChanged() {
                // Get the new text from the JTextField
                String optional_customargs =  downloaderView.getCustomargs_textfield().getText();
                downloaderModel.setOptional_download_custom_args(optional_customargs);
                System.out.println("CUSTOM ARGS: "+optional_customargs);
            }
        });
    }
    
    public static String requestGetVideoTitle(String url) {
        try {
            URL videoUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) videoUrl.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String responseBody;
                try (Scanner sc = new Scanner(videoUrl.openStream())) {
                    responseBody = sc.useDelimiter("\\A").next();
                }

                String videoTitle = findStringBetween(responseBody, "<title>", "</title>").replace("- YouTube", "").toUpperCase();
                if (videoTitle.equals("YOUTUBE")){
                    videoTitle = "HOME PAGE";
                }
                videoTitle = unescapeHtml(videoTitle);
                return videoTitle;
            } else {
                return "NOT FOUND";
            }
        } catch (IOException e) {
            return "ERROR LOADING TITLE";
        }
    }
    
    public static String requestGetYoutubeThumbnailUrl(String selectedVideoUrl, String selectedVideoType) {
        String selectedVideoThumbnailUrl = "https://brighammscenter.org/media/14872/placeholder-dark.jpg?mode=crop&width=1200&height=630";
        if (selectedVideoType.equals("YouTube")) {
            String videoId = selectedVideoUrl.split("=")[1].split("&")[0];
            selectedVideoThumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
            return selectedVideoThumbnailUrl;
        } else if (selectedVideoType.equals("Playlist")) {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(selectedVideoUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String htmlContent;
                    try (Scanner sc = new Scanner(connection.getInputStream())) {
                        htmlContent = sc.useDelimiter("\\A").next();
                    }
                    String id = findStringBetween(htmlContent, "<meta property=\"og:image:height\" content=\"640\"><meta property=\"og:image\" content=\"https://i9.ytimg.com/s_p/", "/maxresdefault.jpg?sqp=");
                    String sqp = findStringBetween(htmlContent, "\"url\":\"https://i9.ytimg.com/s_p/" + id + "/maxresdefault.jpg?sqp=", "maxRatio\":").replace("\\u0026", "&");
                    String realSqp = sqp.split("&")[0];
                    String rs = findStringBetween(sqp, "&rs=", "&v");
                    String v = findStringBetween(sqp, "&v=", "\"");
                    selectedVideoThumbnailUrl = "https://i9.ytimg.com/s_p/" + id + "/maxresdefault.jpg?sqp=" + realSqp + "&rs=" + rs + "&v=" + v;
                    return selectedVideoThumbnailUrl;
                }
            } catch (IOException e) {
                System.out.println("Req Get Thumbnail URL ERROR");
            }
        }
        return selectedVideoThumbnailUrl;
    }
    
    public static Icon createIconFromUrl(String imageUrl,int maxWidth,int maxHeight){
        try{
            URL url = new URL(imageUrl);
            BufferedImage originalImage = ImageIO.read(url);
            
            int newWidth = originalImage.getWidth();
            int newHeight = originalImage.getHeight();

            // Resize the image while maintaining aspect ratio
            if (newWidth > maxWidth || newHeight > maxHeight) {
                double widthRatio = (double) maxWidth / newWidth;
                double heightRatio = (double) maxHeight / newHeight;
                double scaleRatio = Math.min(widthRatio, heightRatio);

                newWidth = (int) (originalImage.getWidth() * scaleRatio);
                newHeight = (int) (originalImage.getHeight() * scaleRatio);
            }
            
            Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        catch(IOException e){
            System.out.println("Create Icon From URL ERROR");
            return null;
        }
    }
    
    public void updateSecondaryFramePosition() {
        if (downloaderView.isVisible() && downloaderVideoInfoView.isVisible()) {
            Point mainFrameLocation = downloaderView.getLocation();
            Dimension mainFrameSize = downloaderView.getSize();

            downloaderVideoInfoView.setLocation(mainFrameLocation.x + mainFrameSize.width, mainFrameLocation.y);
        }
    }
    
    public static String unescapeHtml(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // Replace HTML entities with their corresponding characters
        input = input.replaceAll("&AMP;", "&");
        input = input.replaceAll("&LT;", "<");
        input = input.replaceAll("&GT;", ">");
        input = input.replaceAll("&QUOT;", "\"");
        input = input.replaceAll("&APOS;", "'");
        input = input.replaceAll("&NBSP;", " ");
        // Add more HTML entities as needed

        return input;
    }
    
    public static String findStringBetween(String string, String start, String end) {
        try {
            int startIndex = string.indexOf(start);
            if (startIndex >= 0) {
                startIndex += start.length();
                int endIndex = string.indexOf(end, startIndex);
                if (endIndex >= 0) {
                    return string.substring(startIndex, endIndex);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            // Handle or log the exception as needed
            System.out.println("FindStringBetween Error");
        }
        return null;
    }
    
    public void downloadBinFinishedInitState() {
        if (downloaderModel.checkDownloadBinMissings()) {
            if (!downloaderModel.checkDownloadBinDownloadedFilesExist()) {
                downloadBinView.setVisible(true);
                downloaderView.setVisible(false);
            }
        } else {
            downloadBinView.setVisible(false);
            downloaderView.setVisible(true);
        }
    }
    
    public void reqDownloadBin(String downloadBinUrl, String downloadBinFilename) {
        if (downloadBinFilename == null || downloadBinFilename.isEmpty()) {
            String filename = Paths.get(downloadBinUrl).getFileName().toString();
            downloaderModel.setDownload_bin_filename_basename(filename);
        }

        try {
            URL url = new URL(downloadBinUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                int totalSize = connection.getContentLength();
                int chunkSize = 1024 * 1024;  // 1 MB chunk size
                int downloadBinDownloaded = 0;
                long startTime = System.currentTimeMillis();
                
                try (InputStream inputStream = connection.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream(downloadBinFilename)) {
                    byte[] buffer = new byte[chunkSize];
                    int bytesRead;
                    
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                        downloadBinDownloaded += bytesRead;
                        
                        // Calculate progress percentage
                        int downloadBinProgress = (downloadBinDownloaded / totalSize) * 100;
                        
                        long downloadBinElapsedTime = (System.currentTimeMillis() -  startTime);
                        
                        double downloadBinDownloadSpeed = (double) downloadBinDownloaded / (1024 * 1024 * (downloadBinElapsedTime / 1000.0));  // in MB/s
                        
                        downloadBinView.updateData(downloadBinProgress, (int) downloadBinDownloadSpeed);
                    }
                }

                if (downloadBinDownloaded >= totalSize) {
                    addDownloadBinExecutePermission(downloadBinFilename);
                    downloaderModel.removeDownload_bin_missing(downloadBinUrl,downloadBinFilename);
                } 
                else 
                {
                    Files.deleteIfExists(Paths.get(downloadBinFilename));
                }
            }
        } catch (IOException e) {
            System.out.println("Req download bin error: "+e);
        }
    }
    
    public void addDownloadBinExecutePermission(String downloadBinFilename){
        if (!downloaderModel.getOs().contains("Windows")){
            try{
                File file = new File(downloadBinFilename);
                Path path = file.toPath();

                Set<PosixFilePermission> permissions = Files.getPosixFilePermissions(path);
                permissions.add(PosixFilePermission.OWNER_EXECUTE);
                permissions.add(PosixFilePermission.GROUP_EXECUTE);
                permissions.add(PosixFilePermission.OTHERS_EXECUTE);

                Files.setPosixFilePermissions(path, permissions);
            }
            catch (IOException e){
                System.out.println("Download bin execute permission error! "+e);
            }
        }
        
    }
    
    public void download_bin_start(){
        downloadBinFinishedInitState();
        DownloadBinStartRunnable downloadBinStartRunnable = new DownloadBinStartRunnable(downloadBinView,downloaderView,downloaderModel,this);
        new Thread(downloadBinStartRunnable).start();
    }

    public void downloadSelectedVideo(List<String>buildCommand,int selectedRow){
        DownloadVideoRunnable downloadVideoRunnable = new DownloadVideoRunnable(downloaderView.getResults_table(),buildCommand,selectedRow);
        new Thread(downloadVideoRunnable).start();
    }
    
    public void downloadAllVideo(List<String>buildCommand,int row){
        DownloadVideoRunnable downloadVideoRunnable = new DownloadVideoRunnable(downloaderView.getResults_table(),buildCommand,row);
        new Thread(downloadVideoRunnable).start();
    }
}
