/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 *
 * @author xd
 */
public class DownloadVideoRunnable implements Runnable {
    private JTable jTable;
    private List<String> buildCommand;
    private int selectedRow;
    
    public DownloadVideoRunnable(JTable jTable,List<String> buildCommand,int selectedRow){
        this.jTable = jTable;
        this.buildCommand = buildCommand;
        this.selectedRow = selectedRow;
    }
    
    @Override
    public void run() {
        ProcessBuilder processBuilder = new ProcessBuilder(buildCommand);
        processBuilder.redirectErrorStream(true);
        TableModel results_table_model = jTable.getModel();
        
        try{
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            
            String line;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                line = line.toLowerCase();
                if (line.startsWith("{")) {
                    results_table_model.setValueAt("Processing",selectedRow,6);
                } else if (line.startsWith("downloading")) {
                    List<String> downloadData = Arrays.asList(line.split("\\s+"));
                    System.out.println(downloadData);
                    results_table_model.setValueAt(downloadData.get(1),selectedRow, 4); // Est Size
                    results_table_model.setValueAt(downloadData.get(2),selectedRow, 5); // Progress
                    results_table_model.setValueAt("Downloading",selectedRow, 6); // Status
                    results_table_model.setValueAt(downloadData.get(3),selectedRow, 7); // Speed
                    results_table_model.setValueAt(downloadData.get(4),selectedRow, 8); // ETA

                } else if (line.startsWith("error")) {
                    results_table_model.setValueAt("ERROR",selectedRow, 4); // Est Size
                    results_table_model.setValueAt("ERROR",selectedRow, 6); // Status
                    results_table_model.setValueAt("ERROR",selectedRow, 7); // Speed
                } else if (line.contains("[merger], [extractaudio]")) {
                    results_table_model.setValueAt("Converting",selectedRow, 6);
                } else if (line.startsWith("finished")) {
                    results_table_model.setValueAt("Done",selectedRow, 6);
                } else if (line.contains("has already been downloaded")) {
                    results_table_model.setValueAt("Done",selectedRow, 6);
                }
            }
            try {
                process.waitFor();
                inputStream.close();
                reader.close();
            } catch (InterruptedException ex) {
                System.out.println("Process Output Error! interrupted");
            }
        }
        catch (IOException e){
            System.out.println("Process Output Error!: "+e);
        }
    }
    
}
