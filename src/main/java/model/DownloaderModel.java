package model;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import utils.Pair;

public class DownloaderModel {
    private String downloader_window_title = "RLX - YT-DLP - DOWNLOAD";
    private String info_window_title = "RLX - YT-DLP - INFO";
    private String download_bin_window_title = "RLX - YT-DLP - BINARIES";
    private String required_video_prev_url = "N/A";
    private String required_video_title = "N/A";
    private String required_video_url = "N/A";
    private String required_video_type = "N/A";
    private String required_video_size = "N/A";
    private String required_video_progress = "N/A";
    private String required_video_status = "N/A";
    private String required_video_speed = "N/A";
    private String required_video_eta = "N/A";
    private String required_video_thumbnail_url = "";
    private String required_download_path = System.getProperty("user.home")+"\\Downloads";
    private String optional_download_filename = "%(title)s.%(ext)s";
    private String required_download_format = "MP4";
    private String optional_download_custom_args = "N/A";
    private String optional_download_sponsorblock = "NONE";
    private Boolean include_metadata_checked = false;
    private Boolean include_thumbnail_checked = false;
    private Boolean include_subtitles_checked = false;
    private Boolean dark_checked = false;
    private String selected_video_title = "N/A";
    private String selected_video_url = "N/A";
    private String selected_video_type = "N/A";
    private String selected_video_format = "MP4";
    private String selected_video_size = "N/A";
    private String selected_video_progress = "N/A";
    private String selected_video_status = "N/A";
    private String selected_video_speed = "N/A";
    private String selected_video_eta = "N/A";
    private String selected_video_thumbnail_url = "N/A";
    private String yt_dlp_full_path = "";
    private String os = initOs();
    private List<String> added_video_urls = new ArrayList<>();
    private String download_bin_url = "";
    private String download_bin_filename = "";
    private String download_bin_filename_basename = "";
    private List<Pair<String,String>> download_bin_missings = new ArrayList<>();
    private Map<String, Map<String, String>> download_bin_binaries;
    private final String [] download_bin_required_files = new String[]{"ffmpeg","ffprobe","yt-dlp"};
    private String[] video_types;
    private String[] optional_download_sponsorblock_values;
    private String[] results_table_cols;
    
    public DownloaderModel(){
        this.download_bin_binaries = initDownloadBinMissings();
    }
    
    public String initOs(){
        String os;
        os = System.getProperty("os.name");
        if (os.startsWith("Windows")){
            os = "Windows";
        }
        else if (os.startsWith("Mac")){
            os = "Darwin";
        }
        else if (os.startsWith("Linux")){
            os = "Linux";
        }
        else{
            
        }
        return os;
    }

    public void setDownload_bin_binaries(Map<String, Map<String, String>> download_bin_binaries) {
        this.download_bin_binaries = download_bin_binaries;
    }
    
    public void addDownload_bin_binaries(String key,Map <String,String> value ){
        this.download_bin_binaries.put(key,value);
    }

    public Map<String, Map<String, String>> getDownload_bin_binaries() {
        return download_bin_binaries;
    }

    public void setDownload_bin_url(String download_bin_url) {
        this.download_bin_url = download_bin_url;
    }

    public void setDownload_bin_filename(String download_bin_filename) {
        this.download_bin_filename = download_bin_filename;
    }

    public void setDownload_bin_filename_basename(String download_bin_filename_basename) {
        this.download_bin_filename_basename = download_bin_filename_basename;
    }

    public void setDownload_bin_missings(List<Pair<String,String>> download_bin_missings) {
        this.download_bin_missings = download_bin_missings;
    }
    
    public void removeDownload_bin_missing(String key,String value){
        Pair<String,String> pairToRemove = new Pair<>(key,value);
        this.download_bin_missings.remove(pairToRemove);
    }

    public String getDownload_bin_url() {
        return download_bin_url;
    }

    public String getDownload_bin_filename() {
        return download_bin_filename;
    }

    public String getDownload_bin_filename_basename() {
        return download_bin_filename_basename;
    }

    public List<Pair<String,String>> getDownload_bin_missings() {
        return download_bin_missings;
    }
    
    public void addDownload_bin_missing(String key,String value){
        this.download_bin_missings.add(new Pair<>(key,value));
    }

    public String[] getDownload_bin_required_files() {
        return download_bin_required_files;
    }
    private String bin_path = initBinPath();

    public void setBin_path(String bin_path) {
        this.bin_path = bin_path;
    }

    public String getBin_path() {
        return bin_path;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOs() {
        return os;
    }

    public void setAdded_video_urls(List<String> added_video_urls) {
        this.added_video_urls = added_video_urls;
    }
    
    public void addAdded_video_url(String video_url){
        this.added_video_urls.add(video_url);
    }
    
    public void removeAdded_video_url(String video_url){
        this.added_video_urls.remove(video_url);
    }

    public List<String> getAdded_video_urls() {
        return added_video_urls;
    }
    
    public void setSelected_video_title(String selected_video_title) {
        this.selected_video_title = selected_video_title;
    }

    public void setSelected_video_url(String selected_video_url) {
        this.selected_video_url = selected_video_url;
    }

    public void setSelected_video_type(String selected_video_type) {
        this.selected_video_type = selected_video_type;
    }

    public void setSelected_video_format(String selected_video_format) {
        this.selected_video_format = selected_video_format;
    }

    public void setSelected_video_size(String selected_video_size) {
        this.selected_video_size = selected_video_size;
    }

    public void setSelected_video_progress(String selected_video_progress) {
        this.selected_video_progress = selected_video_progress;
    }

    public void setSelected_video_status(String selected_video_status) {
        this.selected_video_status = selected_video_status;
    }

    public void setSelected_video_speed(String selected_video_speed) {
        this.selected_video_speed = selected_video_speed;
    }

    public void setSelected_video_eta(String selected_video_eta) {
        this.selected_video_eta = selected_video_eta;
    }

    public void setSelected_video_thumbnail_url(String selected_video_thumbnail_url) {
        this.selected_video_thumbnail_url = selected_video_thumbnail_url;
    }

    public void setYt_dlp_full_path(String yt_dlp_full_path) {
        this.yt_dlp_full_path = yt_dlp_full_path;
    }

    public void setVideo_types(String[] video_types) {
        this.video_types = video_types;
    }

    public void setOptional_download_sponsorblock_values(String[] optional_download_sponsorblock_values) {
        this.optional_download_sponsorblock_values = optional_download_sponsorblock_values;
    }

    public void setResults_table_cols(String[] results_table_cols) {
        this.results_table_cols = results_table_cols;
    }

    public String getSelected_video_title() {
        return selected_video_title;
    }

    public String getSelected_video_url() {
        return selected_video_url;
    }

    public String getSelected_video_type() {
        return selected_video_type;
    }

    public String getSelected_video_format() {
        return selected_video_format;
    }

    public String getSelected_video_size() {
        return selected_video_size;
    }

    public String getSelected_video_progress() {
        return selected_video_progress;
    }

    public String getSelected_video_status() {
        return selected_video_status;
    }

    public String getSelected_video_speed() {
        return selected_video_speed;
    }

    public String getSelected_video_eta() {
        return selected_video_eta;
    }

    public String getSelected_video_thumbnail_url() {
        return selected_video_thumbnail_url;
    }

    public String getYt_dlp_full_path() {
        return yt_dlp_full_path;
    }

    public String[] getVideo_types() {
        return video_types;
    }

    public String[] getOptional_download_sponsorblock_values() {
        return optional_download_sponsorblock_values;
    }

    public String[] getResults_table_cols() {
        return results_table_cols;
    }

    public void setDownloader_window_title(String downloader_window_title) {
        this.downloader_window_title = downloader_window_title;
    }

    public void setInfo_window_title(String info_window_title) {
        this.info_window_title = info_window_title;
    }

    public void setDownload_bin_window_title(String download_bin_window_title) {
        this.download_bin_window_title = download_bin_window_title;
    }

    public void setRequired_video_prev_url(String required_video_prev_url) {
        this.required_video_prev_url = required_video_prev_url;
    }

    public void setRequired_video_title(String required_video_title) {
        this.required_video_title = required_video_title;
    }

    public void setRequired_video_url(String required_video_url) {
        this.required_video_url = required_video_url;
    }

    public void setRequired_video_type(String required_video_type) {
        this.required_video_type = required_video_type;
    }

    public void setRequired_video_size(String required_video_size) {
        this.required_video_size = required_video_size;
    }

    public void setRequired_video_progress(String required_video_progress) {
        this.required_video_progress = required_video_progress;
    }

    public void setRequired_video_status(String required_video_status) {
        this.required_video_status = required_video_status;
    }

    public void setRequired_video_speed(String required_video_speed) {
        this.required_video_speed = required_video_speed;
    }

    public void setRequired_video_eta(String required_video_eta) {
        this.required_video_eta = required_video_eta;
    }

    public void setRequired_video_thumbnail_url(String required_video_thumbnail_url) {
        this.required_video_thumbnail_url = required_video_thumbnail_url;
    }

    public void setRequired_download_path(String required_download_path) {
        this.required_download_path = required_download_path;
    }

    public void setOptional_download_filename(String optional_download_filename) {
        this.optional_download_filename = optional_download_filename;
    }

    public void setRequired_download_format(String required_download_format) {
        this.required_download_format = required_download_format;
    }

    public void setOptional_download_custom_args(String optional_download_custom_args) {
        this.optional_download_custom_args = optional_download_custom_args;
    }

    public void setOptional_download_sponsorblock(String optional_download_sponsorblock) {
        this.optional_download_sponsorblock = optional_download_sponsorblock;
    }

    public void setInclude_metadata_checked(Boolean include_metadata_checked) {
        this.include_metadata_checked = include_metadata_checked;
    }

    public void setInclude_thumbnail_checked(Boolean include_thumbnail_checked) {
        this.include_thumbnail_checked = include_thumbnail_checked;
    }

    public void setInclude_subtitles_checked(Boolean include_subtitles_checked) {
        this.include_subtitles_checked = include_subtitles_checked;
    }

    public void setDark_checked(Boolean dark_checked) {
        this.dark_checked = dark_checked;
    }

    public String getDownloader_window_title() {
        return downloader_window_title;
    }

    public String getInfo_window_title() {
        return info_window_title;
    }

    public String getDownload_bin_window_title() {
        return download_bin_window_title;
    }

    public String getRequired_video_prev_url() {
        return required_video_prev_url;
    }

    public String getRequired_video_title() {
        return required_video_title;
    }

    public String getRequired_video_url() {
        return required_video_url;
    }

    public String getRequired_video_type() {
        return required_video_type;
    }

    public String getRequired_video_size() {
        return required_video_size;
    }

    public String getRequired_video_progress() {
        return required_video_progress;
    }

    public String getRequired_video_status() {
        return required_video_status;
    }

    public String getRequired_video_speed() {
        return required_video_speed;
    }

    public String getRequired_video_eta() {
        return required_video_eta;
    }

    public String getRequired_video_thumbnail_url() {
        return required_video_thumbnail_url;
    }

    public String getRequired_download_path() {
        return required_download_path;
    }

    public String getOptional_download_filename() {
        return optional_download_filename;
    }

    public String getRequired_download_format() {
        return required_download_format;
    }

    public String getOptional_download_custom_args() {
        return optional_download_custom_args;
    }

    public String getOptional_download_sponsorblock() {
        return optional_download_sponsorblock;
    }

    public Boolean getInclude_metadata_checked() {
        return include_metadata_checked;
    }

    public Boolean getInclude_thumbnail_checked() {
        return include_thumbnail_checked;
    }

    public Boolean getInclude_subtitles_checked() {
        return include_subtitles_checked;
    }

    public Boolean getDark_checked() {
        return dark_checked;
    }
    
    public Boolean checkRequired_video_url(String url){
        if (url.startsWith("https://") || url.startsWith("http://")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public Boolean checkRequired_video_duplicate(String url){
        String prev_url = getRequired_video_prev_url();
        if (!url.equals(prev_url)){
            setRequired_video_prev_url(url);
            setRequired_video_url(url);
            return true;
        }
        else{
            System.out.println("REQUIRED VIDEO DUPLICATE! "+url);
            return false;
        }
    }
    
    public void checkSet_video_type(String url,Boolean selected){
        if (url.startsWith("https://www.youtube.com/watch?v=")){
            if (selected){
                setSelected_video_type(getVideo_types()[0]);
            }
            else{
                setRequired_video_type(getVideo_types()[0]);
            }
        }
        else if (url.startsWith("https://www.youtube.com/playlist?list=")){
            if (selected){
                setSelected_video_type(getVideo_types()[1]);
            }
            else{
                setRequired_video_type(getVideo_types()[1]);
            }
        }
        else{
            if (selected){
                setSelected_video_type(getVideo_types()[2]);
            }
            else{
                setRequired_video_type(getVideo_types()[2]);
            }
        }
    }
    
    public List<String> checkRequiredVideoFormat() {
        String requiredVideoFormat = getRequired_download_format().toLowerCase();
        return switch (requiredVideoFormat) {
            case "best" -> List.of();
            case "mp4" -> List.of("-f", "bv*[vcodec^=avc]+ba[ext=m4a]/b");
            case "m4a" -> List.of("-f","ba[ext=m4a]");
            default -> List.of("--extract-audio", "--audio-format", requiredVideoFormat, "--audio-quality", "0");
        };
    }

    public List<String> checkOptionalDownloadCustomArgs() {
        String optionalDownloadCustomArgs = getOptional_download_custom_args().toLowerCase();

        if (!optionalDownloadCustomArgs.isEmpty() && !optionalDownloadCustomArgs.equals("n/a") && !optionalDownloadCustomArgs.equals("your custom arguments")) {
            return List.of(optionalDownloadCustomArgs.split("\\s+"));
        } else {
            return null;
        }
    }

    public List<String> checkIncludeMetadata() {
        if (getInclude_metadata_checked()) {
            return List.of("--embed-metadata");
        } else {
            return null;
        }
    }

    public List<String> checkIncludeThumbnail() {
        if (getInclude_thumbnail_checked()) {
            return List.of("--embed-thumbnail");
        } else {
            return null;
        }
    }

    public List<String> checkIncludeSubtitles() {
        if (getInclude_subtitles_checked()) {
            return List.of("--write-auto-subs");
        } else {
            return null;
        }
    }

    public List<String> checkOptionalDownloadSponsorblock() {
        String optionalDownloadSponsorblock = getOptional_download_sponsorblock();
        return switch (optionalDownloadSponsorblock) {
            case "remove" -> List.of("--sponsorblock-remove", "all");
            case "mark" -> List.of("--sponsorblock-mark", "all");
            default -> null;
        };
    }
    
    public String initBinPath(){
        Path parentDir = Paths.get(System.getProperty("user.dir")); // Assuming the Java file is executed from the project root
        Path binPath = parentDir.resolve("bin");
        return binPath.toString();
    }
    
    public static Map<String, Map<String, String>> initDownloadBinMissings(){
        Map<String, Map<String, String>> missingBinaries = new HashMap<>();
        Map<String, String> linuxBinaries = new HashMap<>();
        linuxBinaries.put("ffmpeg", "ffmpeg-linux-v4.1");
        linuxBinaries.put("ffprobe", "ffprobe-linux64-v4.1");
        linuxBinaries.put("yt-dlp", "yt-dlp_linux");
        
        missingBinaries.put("Linux", linuxBinaries);
        
        Map<String, String> darwinBinaries = new HashMap<>();
        darwinBinaries.put("ffmpeg", "ffmpeg-osx64-v4.1");
        darwinBinaries.put("ffprobe", "ffprobe-osx64-v4.1");
        darwinBinaries.put("yt-dlp", "yt-dlp_macos");
        
        missingBinaries.put("Darwin", darwinBinaries);
        
        Map<String, String> windowsBinaries = new HashMap<>();
        windowsBinaries.put("ffmpeg", "ffmpeg-win64-v4.1.exe");
        windowsBinaries.put("ffprobe", "ffprobe-win64-v4.1.exe");
        windowsBinaries.put("yt-dlp","yt-dlp.exe");
        
        missingBinaries.put("Windows", windowsBinaries);
        
        return missingBinaries;
        
    }
    
    public Boolean checkDownloadBinDownloadedFilesExist(){
        List<Pair<String,String>> downloadBinMissings = getDownload_bin_missings();
        for (Pair<String,String> downloadReqData : downloadBinMissings){
            File file = new File(downloadReqData.getValue());
            if (!file.exists()){
                return false;
            }
        }
        return true;
    }
    
    public Boolean checkDownloadBinMissings(){
        initDownloadBinMissingDep();
        if (!getDownload_bin_missings().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    
    private Boolean isExecutableAvailable(String exe){
        String[]paths = System.getenv("PATH").split(File.pathSeparator);
        for (String path: paths){
            File file = new File(path,exe);
            if (file.exists()){
                return true;
            }
        }
        return false;
    }
    
    public void initDownloadBinMissingDep(){
        List<String> exes = new ArrayList<>();
        String[] downloadBinRequiredFiles = getDownload_bin_required_files();
        
        for (String exe : downloadBinRequiredFiles){
            if (!isExecutableAvailable(exe)){
                exes.add(exe);
            }
        }
        
        if (!exes.isEmpty()){
            File binPath = new File(getBin_path());
            if (!binPath.exists()){
                binPath.mkdirs();
            }
            
            for (String exe : exes){
                String downloadBinFilename = Paths.get(getBin_path(), (getOs().equals("Windows") ? exe + ".exe" : exe)).toString();
                
                if (!new File(downloadBinFilename).exists()){
                    String downloadBinUrl;
                    if (exe.equals("yt-dlp")){
                        downloadBinUrl = "https://github.com/yt-dlp/yt-dlp/releases/latest/download/" + getDownload_bin_binaries().get(getOs()).get(exe);
                    }
                    else{
                        downloadBinUrl = "https://github.com/imageio/imageio-binaries/raw/master/ffmpeg/" + getDownload_bin_binaries().get(getOs()).get(exe);
                    }
                    
                    addDownload_bin_missing(downloadBinUrl,downloadBinFilename);
                }
            }
        }
    }
    
    public void initYTDLPNamePath() {
        String[] exes = getDownload_bin_required_files();
        String ytDlpFullPath = getYt_dlp_full_path();
        for (String exe : exes) {
            if (new File(exe).exists()) {
                if (exe.equals("yt-dlp")) {
                    ytDlpFullPath = exe;
                    break;
                }
            }
        }

        if (ytDlpFullPath.equals("")) {
            String binPath = getBin_path();
            ytDlpFullPath = binPath + (getOs().contains("Windows") ? "\\yt-dlp.exe" : "/yt-dlp");
        }
        setYt_dlp_full_path(ytDlpFullPath);
    }
    
    public List<String> initBuildCommand(String videoUrl) {
        initYTDLPNamePath();

        List<String> buildCommand = new ArrayList<>();
        buildCommand.add(getYt_dlp_full_path());
        buildCommand.add(videoUrl);
        buildCommand.add("--hls-prefer-native");
        // buildCommand.add("--default-search \"ytsearch\"");
        buildCommand.add("--newline");
        buildCommand.add("--ignore-errors");
        buildCommand.add("--ignore-config");
        buildCommand.add("--no-simulate");
        buildCommand.add("--progress");
        // buildCommand.add("--default-search"); // Include --default-search once with its argument separated
        // buildCommand.add("ytsearch"); 
        buildCommand.add("--progress-template");
        buildCommand.add("%(progress.status)s %(progress._total_bytes_str)s %(progress._percent_str)s %(progress._speed_str)s %(progress._eta_str)s");
        buildCommand.add("--dump-json");
        buildCommand.add("-v");
        buildCommand.add("-o");
        buildCommand.add(getRequired_download_path() + "\\" + getOptional_download_filename());

        List<String> requiredVideoFormat = checkRequiredVideoFormat();
        buildCommand.addAll(requiredVideoFormat);

        List<String> optionalDownloadCustomArgs = checkOptionalDownloadCustomArgs();
        if (optionalDownloadCustomArgs != null) {
            buildCommand.addAll(optionalDownloadCustomArgs);
        }

        List<String> includeMetadata = checkIncludeMetadata();
        if (includeMetadata != null) {
            buildCommand.addAll(includeMetadata);
        }

        List<String> includeThumbnail = checkIncludeThumbnail();
        if (includeThumbnail != null) {
            buildCommand.addAll(includeThumbnail);
        }

        List<String> includeSubtitles = checkIncludeSubtitles();
        if (includeSubtitles != null) {
            buildCommand.addAll(includeSubtitles);
        }

        List<String> optionalDownloadSponsorblock = checkOptionalDownloadSponsorblock();
        if (optionalDownloadSponsorblock != null) {
            buildCommand.addAll(optionalDownloadSponsorblock);
        }

        return buildCommand;
    }
}
