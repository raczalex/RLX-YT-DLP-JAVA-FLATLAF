package runnable;

import view.DownloaderView;
import view.DownloadBinView;
import model.DownloaderModel;
import java.util.List;
import controller.DownloaderController;
import utils.Pair;

/**
 *
 * @author raczalex
 */
public class DownloadBinStartRunnable implements Runnable {
    private DownloadBinView downloadBinView;
    private DownloaderView downloaderView;
    private DownloaderModel downloaderModel;
    private DownloaderController downloaderController;
    
    public DownloadBinStartRunnable(DownloadBinView downloadBinView,DownloaderView downloaderView,DownloaderModel downloaderModel, DownloaderController downloaderController){
        this.downloadBinView = downloadBinView;
        this.downloaderView = downloaderView;
        this.downloaderModel = downloaderModel;
        this.downloaderController = downloaderController;
    }
    
    @Override
    public void run() {
        List<Pair<String, String>> downloadBinMissingFiles = downloaderModel.getDownload_bin_missings();
        int files = 0;
        for (Pair<String,String> downloadBinData : downloadBinMissingFiles){
            String downloadBinUrl = downloadBinData.getKey();
            String downloadBinFilename = downloadBinData.getValue();
            
            downloadBinView.createElements( 
            downloadBinFilename.contains("/") ? 
            downloadBinFilename.substring(downloadBinFilename.lastIndexOf("/") + 1) : 
            downloadBinFilename.substring(downloadBinFilename.lastIndexOf("\\") + 1));

            downloaderController.reqDownloadBin(downloadBinUrl, downloadBinFilename);
            files += 1;
            if (files == downloadBinMissingFiles.size()){
                downloadBinView.setVisible(false);
                downloaderView.setVisible(true);
            }
        }
    }
    
}
