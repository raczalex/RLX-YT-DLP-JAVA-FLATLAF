package raczalex.rlx_yt_dlp_java_flatlaf;

import controller.DownloaderController;
import view.DownloaderView;
import model.DownloaderModel;
import com.formdev.flatlaf.FlatLightLaf;

/**
 *
 * @author xd
 */
public class RLX_YT_DLP_JAVA_FLATLAF {

    public static void main(String[] args) {
        FlatLightLaf.registerCustomDefaultsSource("styles");
        FlatLightLaf.setup();
        // Create model, view, and controller instances
        DownloaderModel downloaderModel = new DownloaderModel();
        DownloaderView downloaderView = new DownloaderView(downloaderModel);
        DownloaderController downloaderController = new DownloaderController(downloaderModel, downloaderView);
        downloaderController.download_bin_start();
    }
}
