package android.hardware.camera2.params;

/* loaded from: classes.dex */
public final class MandatoryStreamCombination {
    private static final long STREAM_USE_CASE_CROPPED_RAW = 6;
    private static final long STREAM_USE_CASE_PREVIEW = 1;
    private static final long STREAM_USE_CASE_PREVIEW_VIDEO_STILL = 4;
    private static final long STREAM_USE_CASE_RECORD = 3;
    private static final long STREAM_USE_CASE_STILL_CAPTURE = 2;
    private static final long STREAM_USE_CASE_VIDEO_CALL = 5;
    private static final java.lang.String TAG = "MandatoryStreamCombination";
    private final java.lang.String mDescription;
    private final boolean mIsReprocessable;
    private final java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation> mStreamsInformation = new java.util.ArrayList<>();
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLegacyCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Simple preview, GPU video processing, or no-preview video recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "No-viewfinder still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-application video/image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Standard still imaging"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app processing plus still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Standard recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Preview plus in-app processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Still capture plus in-app processing")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLimitedCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution video recording with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution in-app video processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Two-input in-app video processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution recording with video snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution in-app processing with video snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Two-input in-app processing with still capture")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sBurstCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processsing")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sFullCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution GPU processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution in-app processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input in-app processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Video recording with maximum-size video snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Standard video recording plus maximum-resolution in-app processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Preview plus two-input maximum-resolution in-app processing")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sRawCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "No-preview DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Standard DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app processing plus DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Video recording with DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Preview with in-app processing and DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Two-input in-app processing plus DNG capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Still capture with simultaneous JPEG and DNG"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app processing with simultaneous JPEG and DNG")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLevel3Combinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with dynamic selection of output format")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLimitedPrivateReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "No-viewfinder still image reprocessing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL(Zero-Shutter-Lag) still imaging", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL still and in-app processing imaging", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL in-app processing with still capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLimitedYUVReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "No-viewfinder still image reprocessing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL(Zero-Shutter-Lag) still imaging", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL still and in-app processing imaging", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL in-app processing with still capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sFullPrivateReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution ZSL in-app video processing with regular preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution ZSL in-app processing with regular preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Maximum-resolution two-input ZSL in-app processing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL still capture and in-app processing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sFullYUVReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Maximum-resolution multi-frame image fusion in-app processing with regular preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Maximum-resolution multi-frame image fusion two-input in-app processing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "High-resolution ZSL in-app video processing with regular preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "ZSL still capture and in-app processing", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sRAWPrivateReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL still capture and preview with DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sRAWYUVReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing and preview with DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL two-input in-app processing and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL still capture and preview with DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Mutually exclusive ZSL in-app processing with still capture and DNG capture", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLevel3PrivateReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sLevel3YUVReprocCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL and RAW", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-app viewfinder analysis with ZSL, RAW, and JPEG reprocessing output", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sConcurrentStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "In-app video / image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "preview / preview to GPU"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "No view-finder still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Two-input in app video / image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "High resolution video recording with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "In-app video / image processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "In-app video / image processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard still image capture")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sConcurrentDepthOnlyStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(android.graphics.ImageFormat.DEPTH16, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA)}, "Depth capture for mesh based object rendering")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sUltraHighResolutionStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution YUV image capture with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution RAW_SENSOR image capture with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution JPEG image capture with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution YUV image capture with image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution RAW_SENSOR image capture with image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "No-viewfinder Ultra high resolution JPEG image capture with image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution YUV image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution RAW image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution JPEG image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution YUV image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution RAW image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD)}, "Ultra high resolution JPEG image capture with preview + app-based image analysis"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Ultra high resolution YUV image capture with preview + default", true), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Ultra high resolution RAW image capture with preview + default", true), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Ultra high resolution JPEG capture with preview + default", true)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sUltraHighResolutionReprocStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW remosaic reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW remosaic reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW -> JPEG reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW -> YUV reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW -> JPEG reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "In-app RAW -> YUV reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sUltraHighResolutionYUVReprocStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> JPEG reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> JPEG reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> YUV reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution YUV -> YUV reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.YUV)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sUltraHighResolutionPRIVReprocStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution PRIVATE -> JPEG reprocessing with separate preview", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Ultra high resolution PRIVATE -> JPEG reprocessing with in-app image analysis", android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE)};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] s10BitOutputStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "Simple preview, GPU video processing, or no-preview video recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(54, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM)}, "In-application video/image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Standard still imaging"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(54, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Maximum-resolution in-app processing with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(54, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(54, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "Maximum-resolution two-input in-app processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution video recording with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(54, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution recording with in-app snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution recording with video snapshot")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sStreamUseCaseCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1)}, "Simple preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1)}, "Simple in-application image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3)}, "Simple video recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3)}, "Simple in-application video processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Simple JPEG still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Simple YUV still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4)}, "Multi-purpose stream for preview, video and still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4)}, "Multi-purpose YUV stream for preview, video and still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 5)}, "Simple video call"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 5)}, "Simple YUV video call"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Preview with JPEG still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Preview with YUV still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3)}, "Preview with video recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3)}, "Preview with in-application video processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1)}, "Preview with in-application image processing"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 5)}, "Preview with video call"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 5)}, "Preview with YUV video call"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Multi-purpose stream with JPEG still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Multi-purpose stream with YUV still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Multi-purpose YUV stream with JPEG still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, 4), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Multi-purpose YUV stream with YUV still capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 2), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "YUV and JPEG concurrent still image capture (for testing)"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 2)}, "Preview, video record and JPEG video snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 3), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, 2)}, "Preview, in-application video processing and JPEG video snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2)}, "Preview, in-application image processing, and JPEG still image capture")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sCroppedRawStreamUseCaseCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Cropped RAW still image capture without preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Cropped RAW still image capture with preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "In-app image processing with cropped RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Preview with YUV and RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "In-app image processing with YUV and RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Preview with JPEG and RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 2), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "In-app image processing with JPEG and RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 3), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Preview with video recording and RAW snapshot"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Preview with in-app image processing and RAW still image capture"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, 1), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(32, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, 6)}, "Two input in-app processing and RAW still image capture")};
    private static android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] sPreviewStabilizedStreamCombinations = {new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Stabilized preview, GPU video processing, or no-preview stabilized recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Stabilized preview, GPU video processing, or no-preview stabilized recording"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard JPEG still imaging with stabilized preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard YUV still imaging with stabilized preview"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard YUV still imaging with stabilized in-app image processing stream"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(256, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p)}, "Standard JPEG still imaging with stabilized in-app image processing stream"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized"), new android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate(new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[]{new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(35, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p), new android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate(34, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW)}, "High-resolution video recording with preview both streams stabilized")};

    private enum ReprocessType {
        NONE,
        PRIVATE,
        YUV,
        REMOSAIC
    }

    private enum SizeThreshold {
        VGA,
        PREVIEW,
        RECORD,
        MAXIMUM,
        s720p,
        s1440p,
        FULL_RES
    }

    public static final class MandatoryStreamInformation {
        private final java.util.ArrayList<android.util.Size> mAvailableSizes;
        private final int mFormat;
        private final boolean mIs10BitCapable;
        private final boolean mIsInput;
        private final boolean mIsMaximumSize;
        private final boolean mIsUltraHighResolution;
        private final long mStreamUseCase;

        public MandatoryStreamInformation(java.util.List<android.util.Size> list, int i, boolean z) {
            this(list, i, z, false, false);
        }

        public MandatoryStreamInformation(java.util.List<android.util.Size> list, int i, boolean z, boolean z2) {
            this(list, i, z, z2, false);
        }

        public MandatoryStreamInformation(java.util.List<android.util.Size> list, int i, boolean z, boolean z2, boolean z3) {
            this(list, i, z, z2, z3, false);
        }

        public MandatoryStreamInformation(java.util.List<android.util.Size> list, int i, boolean z, boolean z2, boolean z3, boolean z4) {
            this(list, i, z, z2, z3, z4, 0L);
        }

        public MandatoryStreamInformation(java.util.List<android.util.Size> list, int i, boolean z, boolean z2, boolean z3, boolean z4, long j) {
            this.mAvailableSizes = new java.util.ArrayList<>();
            if (list.isEmpty()) {
                throw new java.lang.IllegalArgumentException("No available sizes");
            }
            this.mAvailableSizes.addAll(list);
            this.mFormat = android.hardware.camera2.params.StreamConfigurationMap.checkArgumentFormat(i);
            this.mIsMaximumSize = z;
            this.mIsInput = z2;
            this.mIsUltraHighResolution = z3;
            this.mIs10BitCapable = z4;
            this.mStreamUseCase = j;
        }

        public boolean isInput() {
            return this.mIsInput;
        }

        public boolean isUltraHighResolution() {
            return this.mIsUltraHighResolution;
        }

        public boolean isMaximumSize() {
            return this.mIsMaximumSize;
        }

        public boolean is10BitCapable() {
            return this.mIs10BitCapable;
        }

        public java.util.List<android.util.Size> getAvailableSizes() {
            return java.util.Collections.unmodifiableList(this.mAvailableSizes);
        }

        public int getFormat() {
            if (this.mIs10BitCapable && this.mFormat == 54) {
                return 35;
            }
            return this.mFormat;
        }

        public int get10BitFormat() {
            if (!this.mIs10BitCapable) {
                throw new java.lang.UnsupportedOperationException("10-bit output is not supported!");
            }
            return this.mFormat;
        }

        public long getStreamUseCase() {
            return this.mStreamUseCase;
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation)) {
                return false;
            }
            android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation mandatoryStreamInformation = (android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation) obj;
            if (this.mFormat != mandatoryStreamInformation.mFormat || this.mIsInput != mandatoryStreamInformation.mIsInput || this.mIsUltraHighResolution != mandatoryStreamInformation.mIsUltraHighResolution || this.mStreamUseCase != mandatoryStreamInformation.mStreamUseCase || this.mAvailableSizes.size() != mandatoryStreamInformation.mAvailableSizes.size()) {
                return false;
            }
            return this.mAvailableSizes.equals(mandatoryStreamInformation.mAvailableSizes);
        }

        public int hashCode() {
            return android.hardware.camera2.utils.HashCodeHelpers.hashCode(this.mFormat, java.lang.Boolean.hashCode(this.mIsInput), java.lang.Boolean.hashCode(this.mIsUltraHighResolution), this.mAvailableSizes.hashCode(), this.mStreamUseCase);
        }
    }

    public MandatoryStreamCombination(java.util.List<android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation> list, java.lang.String str, boolean z) {
        if (list.isEmpty()) {
            throw new java.lang.IllegalArgumentException("Empty stream information");
        }
        this.mStreamsInformation.addAll(list);
        this.mDescription = str;
        this.mIsReprocessable = z;
    }

    public java.lang.CharSequence getDescription() {
        return this.mDescription;
    }

    public boolean isReprocessable() {
        return this.mIsReprocessable;
    }

    public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation> getStreamsInformation() {
        return java.util.Collections.unmodifiableList(this.mStreamsInformation);
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof android.hardware.camera2.params.MandatoryStreamCombination)) {
            return false;
        }
        android.hardware.camera2.params.MandatoryStreamCombination mandatoryStreamCombination = (android.hardware.camera2.params.MandatoryStreamCombination) obj;
        if (this.mDescription != mandatoryStreamCombination.mDescription || this.mIsReprocessable != mandatoryStreamCombination.mIsReprocessable || this.mStreamsInformation.size() != mandatoryStreamCombination.mStreamsInformation.size()) {
            return false;
        }
        return this.mStreamsInformation.equals(mandatoryStreamCombination.mStreamsInformation);
    }

    public int hashCode() {
        return android.hardware.camera2.utils.HashCodeHelpers.hashCode(java.lang.Boolean.hashCode(this.mIsReprocessable), this.mDescription.hashCode(), this.mStreamsInformation.hashCode());
    }

    private static final class StreamTemplate {
        public int mFormat;
        public android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold mSizeThreshold;
        public long mStreamUseCase;

        public StreamTemplate(int i, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold sizeThreshold) {
            this(i, sizeThreshold, 0L);
        }

        public StreamTemplate(int i, android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold sizeThreshold, long j) {
            this.mFormat = i;
            this.mSizeThreshold = sizeThreshold;
            this.mStreamUseCase = j;
        }
    }

    private static final class StreamCombinationTemplate {
        public java.lang.String mDescription;
        public android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType mReprocessType;
        public android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] mStreamTemplates;
        public boolean mSubstituteYUV;

        public StreamCombinationTemplate(android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr, java.lang.String str) {
            this(streamTemplateArr, str, android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.NONE);
        }

        public StreamCombinationTemplate(android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr, java.lang.String str, android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType reprocessType) {
            this(streamTemplateArr, str, reprocessType, false);
        }

        public StreamCombinationTemplate(android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr, java.lang.String str, boolean z) {
            this(streamTemplateArr, str, android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.NONE, z);
        }

        public StreamCombinationTemplate(android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr, java.lang.String str, android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType reprocessType, boolean z) {
            this.mSubstituteYUV = false;
            this.mStreamTemplates = streamTemplateArr;
            this.mReprocessType = reprocessType;
            this.mDescription = str;
            this.mSubstituteYUV = z;
        }
    }

    public static final class Builder {
        private final android.util.Size kPreviewSizeBound = new android.util.Size(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, 1088);
        private int mCameraId;
        private java.util.List<java.lang.Integer> mCapabilities;
        private android.util.Size mDisplaySize;
        private int mHwLevel;
        private boolean mIsCroppedRawSupported;
        private boolean mIsHiddenPhysicalCamera;
        private boolean mIsPreviewStabilizationSupported;
        private android.hardware.camera2.params.StreamConfigurationMap mStreamConfigMap;
        private android.hardware.camera2.params.StreamConfigurationMap mStreamConfigMapMaximumResolution;

        public Builder(int i, int i2, android.util.Size size, java.util.List<java.lang.Integer> list, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap, android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap2, boolean z, boolean z2) {
            this.mIsPreviewStabilizationSupported = false;
            this.mIsCroppedRawSupported = false;
            this.mCameraId = i;
            this.mDisplaySize = size;
            this.mCapabilities = list;
            this.mStreamConfigMap = streamConfigurationMap;
            this.mStreamConfigMapMaximumResolution = streamConfigurationMap2;
            this.mHwLevel = i2;
            this.mIsHiddenPhysicalCamera = android.hardware.camera2.CameraManager.isHiddenPhysicalCamera(java.lang.Integer.toString(this.mCameraId));
            this.mIsPreviewStabilizationSupported = z;
            this.mIsCroppedRawSupported = z2;
        }

        private java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryStreamCombinationsInternal(android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr, boolean z) {
            boolean z2;
            android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr2 = streamCombinationTemplateArr;
            java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplateArr2.length);
            int length = streamCombinationTemplateArr2.length;
            int i = 0;
            while (i < length) {
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate streamCombinationTemplate = streamCombinationTemplateArr2[i];
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                arrayList2.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                for (android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate streamTemplate : streamCombinationTemplate.mStreamTemplates) {
                    java.util.List<android.util.Size> list = enumerateAvailableSizes.get(new android.util.Pair(streamTemplate.mSizeThreshold, new java.lang.Integer(streamTemplate.mFormat)));
                    if (z && streamTemplate.mFormat == 54 && !new java.util.HashSet(enumerateAvailableSizes.get(new android.util.Pair(streamTemplate.mSizeThreshold, new java.lang.Integer(35)))).equals(new java.util.HashSet(list))) {
                        android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "The supported 10-bit YUV sizes are different from the supported 8-bit YUV sizes!");
                        return null;
                    }
                    boolean z3 = streamTemplate.mSizeThreshold == android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM;
                    try {
                        int i2 = streamTemplate.mFormat;
                        if (z) {
                            z2 = streamTemplate.mFormat != 256;
                        } else {
                            z2 = false;
                        }
                        arrayList2.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(list, i2, z3, false, false, z2));
                    } catch (java.lang.IllegalArgumentException e) {
                        android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription);
                        return null;
                    }
                }
                try {
                    arrayList.add(new android.hardware.camera2.params.MandatoryStreamCombination(arrayList2, streamCombinationTemplate.mDescription, false));
                    i++;
                    streamCombinationTemplateArr2 = streamCombinationTemplateArr;
                } catch (java.lang.IllegalArgumentException e2) {
                    android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No stream information for mandatory combination: " + streamCombinationTemplate.mDescription);
                    return null;
                }
            }
            return java.util.Collections.unmodifiableList(arrayList);
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryPreviewStabilizedStreamCombinations() {
            android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr = android.hardware.camera2.params.MandatoryStreamCombination.sPreviewStabilizedStreamCombinations;
            if (!this.mIsPreviewStabilizationSupported) {
                android.util.Log.v(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Device does not support preview stabilization");
                return null;
            }
            return getAvailableMandatoryStreamCombinationsInternal(streamCombinationTemplateArr, false);
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatory10BitStreamCombinations() {
            android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr = android.hardware.camera2.params.MandatoryStreamCombination.s10BitOutputStreamCombinations;
            if (!is10BitOutputSupported()) {
                android.util.Log.v(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Device is not able to output 10-bit!");
                return null;
            }
            return getAvailableMandatoryStreamCombinationsInternal(streamCombinationTemplateArr, true);
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryStreamUseCaseCombinations() {
            java.lang.String str;
            if (!isCapabilitySupported(19)) {
                return null;
            }
            java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            java.lang.String str2 = android.hardware.camera2.params.MandatoryStreamCombination.TAG;
            if (enumerateAvailableSizes == null) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sStreamUseCaseCombinations));
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            int length = android.hardware.camera2.params.MandatoryStreamCombination.sStreamUseCaseCombinations.length;
            if (this.mIsCroppedRawSupported) {
                arrayList2.ensureCapacity(length + android.hardware.camera2.params.MandatoryStreamCombination.sCroppedRawStreamUseCaseCombinations.length);
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sCroppedRawStreamUseCaseCombinations));
            } else {
                arrayList2.ensureCapacity(length);
            }
            java.util.Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate streamCombinationTemplate = (android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate) it.next();
                java.util.ArrayList arrayList3 = new java.util.ArrayList();
                arrayList3.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
                int length2 = streamTemplateArr.length;
                int i = 0;
                while (i < length2) {
                    android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate streamTemplate = streamTemplateArr[i];
                    try {
                        str = str2;
                    } catch (java.lang.IllegalArgumentException e) {
                        str = str2;
                    }
                    try {
                        arrayList3.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(enumerateAvailableSizes.get(new android.util.Pair(streamTemplate.mSizeThreshold, new java.lang.Integer(streamTemplate.mFormat))), streamTemplate.mFormat, streamTemplate.mSizeThreshold == android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, false, false, false, streamTemplate.mStreamUseCase));
                        i++;
                        str2 = str;
                    } catch (java.lang.IllegalArgumentException e2) {
                        android.util.Log.e(str, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription);
                        return null;
                    }
                }
                java.lang.String str3 = str2;
                try {
                    arrayList2.add(new android.hardware.camera2.params.MandatoryStreamCombination(arrayList3, streamCombinationTemplate.mDescription, false));
                    str2 = str3;
                } catch (java.lang.IllegalArgumentException e3) {
                    android.util.Log.e(str3, "No stream information for mandatory combination: " + streamCombinationTemplate.mDescription);
                    return null;
                }
            }
            return java.util.Collections.unmodifiableList(arrayList2);
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryConcurrentStreamCombinations() {
            android.util.Size size;
            android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr = android.hardware.camera2.params.MandatoryStreamCombination.sConcurrentStreamCombinations;
            if (!isColorOutputSupported()) {
                android.util.Log.v(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Device is not backward compatible, depth streams are mandatory!");
                streamCombinationTemplateArr = android.hardware.camera2.params.MandatoryStreamCombination.sConcurrentDepthOnlyStreamCombinations;
            }
            android.util.Size size2 = new android.util.Size(640, 480);
            android.util.Size size3 = new android.util.Size(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH);
            android.util.Size size4 = new android.util.Size(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_HUSH_GESTURE);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplateArr.length);
            int length = streamCombinationTemplateArr.length;
            int i = 0;
            while (i < length) {
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate streamCombinationTemplate = streamCombinationTemplateArr[i];
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                arrayList2.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
                android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr = streamCombinationTemplate.mStreamTemplates;
                int length2 = streamTemplateArr.length;
                int i2 = 0;
                while (i2 < length2) {
                    android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate streamTemplate = streamTemplateArr[i2];
                    java.util.ArrayList arrayList3 = new java.util.ArrayList();
                    switch (streamTemplate.mSizeThreshold) {
                        case s1440p:
                            size = size4;
                            break;
                        case VGA:
                            size = size2;
                            break;
                        default:
                            size = size3;
                            break;
                    }
                    android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr2 = streamCombinationTemplateArr;
                    android.util.Size size5 = size2;
                    arrayList3.add(getMinSize(size, getMaxSize(this.mStreamConfigMap.getOutputSizes(streamTemplate.mFormat))));
                    try {
                        arrayList2.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(arrayList3, streamTemplate.mFormat, false));
                        i2++;
                        streamCombinationTemplateArr = streamCombinationTemplateArr2;
                        size2 = size5;
                    } catch (java.lang.IllegalArgumentException e) {
                        throw new java.lang.RuntimeException("No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription, e);
                    }
                }
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate[] streamCombinationTemplateArr3 = streamCombinationTemplateArr;
                android.util.Size size6 = size2;
                try {
                    arrayList.add(new android.hardware.camera2.params.MandatoryStreamCombination(arrayList2, streamCombinationTemplate.mDescription, false));
                    i++;
                    streamCombinationTemplateArr = streamCombinationTemplateArr3;
                    size2 = size6;
                } catch (java.lang.IllegalArgumentException e2) {
                    throw new java.lang.RuntimeException("No stream information for mandatory combination: " + streamCombinationTemplate.mDescription, e2);
                }
            }
            return java.util.Collections.unmodifiableList(arrayList);
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryMaximumResolutionStreamCombinations() {
            if (!isColorOutputSupported()) {
                android.util.Log.v(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Device is not backward compatible!, no mandatory maximum res streams");
                return null;
            }
            java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> arrayList = new java.util.ArrayList<>();
            arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionStreamCombinations));
            java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination> arrayList2 = new java.util.ArrayList<>();
            boolean isRemosaicReprocessingSupported = isRemosaicReprocessingSupported();
            android.util.Size[] inputSizes = this.mStreamConfigMapMaximumResolution.getInputSizes(35);
            android.util.Size[] inputSizes2 = this.mStreamConfigMapMaximumResolution.getInputSizes(34);
            int i = 0;
            if (isRemosaicReprocessingSupported) {
                i = 0 + android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionReprocStreamCombinations.length;
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionReprocStreamCombinations));
            }
            if (inputSizes != null && inputSizes.length != 0) {
                i += android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionYUVReprocStreamCombinations.length;
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionYUVReprocStreamCombinations));
            }
            if (inputSizes2 != null && inputSizes2.length != 0) {
                i += android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionPRIVReprocStreamCombinations.length;
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sUltraHighResolutionPRIVReprocStreamCombinations));
            }
            arrayList2.ensureCapacity(arrayList.size() + i);
            fillUHMandatoryStreamCombinations(arrayList2, arrayList);
            return java.util.Collections.unmodifiableList(arrayList2);
        }

        private android.hardware.camera2.params.MandatoryStreamCombination createUHSensorMandatoryStreamCombination(android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate streamCombinationTemplate, int i) {
            java.lang.String str;
            android.hardware.camera2.params.StreamConfigurationMap streamConfigurationMap;
            java.util.List<android.util.Size> list;
            int i2;
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.ensureCapacity(streamCombinationTemplate.mStreamTemplates.length);
            boolean z = streamCombinationTemplate.mReprocessType != android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.NONE;
            if (z) {
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                if (streamCombinationTemplate.mReprocessType == android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE) {
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(34)));
                    i2 = 34;
                } else if (streamCombinationTemplate.mReprocessType == android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.REMOSAIC) {
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(32)));
                    i2 = 32;
                } else {
                    arrayList2.add(getMaxSize(this.mStreamConfigMapMaximumResolution.getInputSizes(35)));
                    i2 = 35;
                }
                int i3 = i2;
                arrayList.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(arrayList2, i3, false, true, true));
                arrayList.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(arrayList2, i3, false, false, true));
            }
            java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Available size enumeration failed");
                return null;
            }
            android.util.Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(32);
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            if (outputSizes != null) {
                arrayList3.ensureCapacity(outputSizes.length);
                arrayList3.addAll(java.util.Arrays.asList(outputSizes));
            }
            for (android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate streamTemplate : streamCombinationTemplate.mStreamTemplates) {
                java.util.ArrayList arrayList4 = new java.util.ArrayList();
                int i4 = streamTemplate.mFormat;
                boolean z2 = streamTemplate.mSizeThreshold == android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.FULL_RES;
                if (!z2) {
                    streamConfigurationMap = this.mStreamConfigMap;
                } else {
                    streamConfigurationMap = this.mStreamConfigMapMaximumResolution;
                }
                boolean z3 = streamTemplate.mSizeThreshold == android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM;
                if (i != 0 && z3) {
                    i4 = i;
                }
                if (z2) {
                    android.util.Size[] outputSizes2 = streamConfigurationMap.getOutputSizes(i4);
                    android.util.Size[] highResolutionOutputSizes = streamConfigurationMap.getHighResolutionOutputSizes(i4);
                    android.util.Size maxSizeOrNull = getMaxSizeOrNull(outputSizes2);
                    android.util.Size maxSizeOrNull2 = getMaxSizeOrNull(highResolutionOutputSizes);
                    android.util.Size size = maxSizeOrNull != null ? maxSizeOrNull : maxSizeOrNull2;
                    if (maxSizeOrNull != null && maxSizeOrNull2 != null) {
                        size = getMaxSize(maxSizeOrNull, maxSizeOrNull2);
                    }
                    arrayList4.add(size);
                    list = arrayList4;
                } else if (i4 == 32) {
                    list = arrayList3;
                } else {
                    list = enumerateAvailableSizes.get(new android.util.Pair(streamTemplate.mSizeThreshold, new java.lang.Integer(i4)));
                }
                try {
                    arrayList.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(list, i4, z3, false, z2));
                } catch (java.lang.IllegalArgumentException e) {
                    throw new java.lang.RuntimeException("No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + streamCombinationTemplate.mDescription, e);
                }
            }
            switch (i) {
                case 32:
                    str = "RAW_SENSOR";
                    break;
                case 256:
                    str = "JPEG";
                    break;
                default:
                    str = "YUV";
                    break;
            }
            try {
                return new android.hardware.camera2.params.MandatoryStreamCombination(arrayList, streamCombinationTemplate.mDescription + " " + str + " still-capture", z);
            } catch (java.lang.IllegalArgumentException e2) {
                throw new java.lang.RuntimeException("No stream information for mandatory combination: " + streamCombinationTemplate.mDescription, e2);
            }
        }

        private void fillUHMandatoryStreamCombinations(java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination> arrayList, java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> arrayList2) {
            java.util.Iterator<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> it = arrayList2.iterator();
            while (it.hasNext()) {
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate next = it.next();
                arrayList.add(createUHSensorMandatoryStreamCombination(next, 0));
                if (next.mSubstituteYUV) {
                    arrayList.add(createUHSensorMandatoryStreamCombination(next, 32));
                    arrayList.add(createUHSensorMandatoryStreamCombination(next, 256));
                }
            }
        }

        public java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> getAvailableMandatoryStreamCombinations() {
            if (!isColorOutputSupported()) {
                android.util.Log.v(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Device is not backward compatible!");
                return null;
            }
            if (this.mCameraId < 0 && !isExternalCamera()) {
                android.util.Log.i(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Invalid camera id");
                return null;
            }
            java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> arrayList = new java.util.ArrayList<>();
            if (isHardwareLevelAtLeastLegacy()) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLegacyCombinations));
            }
            if (isHardwareLevelAtLeastLimited() || isExternalCamera()) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLimitedCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLimitedPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLimitedYUVReprocCombinations));
                }
            }
            if (isCapabilitySupported(6)) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sBurstCombinations));
            }
            if (isHardwareLevelAtLeastFull()) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sFullCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sFullPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sFullYUVReprocCombinations));
                }
            }
            if (isCapabilitySupported(3)) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sRawCombinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sRAWPrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sRAWYUVReprocCombinations));
                }
            }
            if (isHardwareLevelAtLeastLevel3()) {
                arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLevel3Combinations));
                if (isPrivateReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLevel3PrivateReprocCombinations));
                }
                if (isYUVReprocessingSupported()) {
                    arrayList.addAll(java.util.Arrays.asList(android.hardware.camera2.params.MandatoryStreamCombination.sLevel3YUVReprocCombinations));
                }
            }
            return generateAvailableCombinations(arrayList);
        }

        private java.util.List<android.hardware.camera2.params.MandatoryStreamCombination> generateAvailableCombinations(java.util.ArrayList<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> arrayList) {
            android.util.Size size;
            java.util.List<android.util.Size> list;
            java.util.List<android.util.Size> list2;
            int i;
            if (arrayList.isEmpty()) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No available stream templates!");
                return null;
            }
            java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> enumerateAvailableSizes = enumerateAvailableSizes();
            if (enumerateAvailableSizes == null) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Available size enumeration failed!");
                return null;
            }
            int i2 = 32;
            android.util.Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(32);
            java.util.ArrayList arrayList2 = new java.util.ArrayList();
            if (outputSizes != null) {
                arrayList2.ensureCapacity(outputSizes.length);
                arrayList2.addAll(java.util.Arrays.asList(outputSizes));
            }
            android.util.Size size2 = new android.util.Size(0, 0);
            if (isPrivateReprocessingSupported()) {
                size2 = getMaxSize(this.mStreamConfigMap.getInputSizes(34));
            }
            android.util.Size size3 = new android.util.Size(0, 0);
            if (isYUVReprocessingSupported()) {
                size3 = getMaxSize(this.mStreamConfigMap.getInputSizes(35));
            }
            java.util.ArrayList arrayList3 = new java.util.ArrayList();
            arrayList3.ensureCapacity(arrayList.size());
            java.util.Iterator<android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate> it = arrayList.iterator();
            java.util.List<android.util.Size> list3 = arrayList2;
            while (it.hasNext()) {
                android.hardware.camera2.params.MandatoryStreamCombination.StreamCombinationTemplate next = it.next();
                java.util.ArrayList arrayList4 = new java.util.ArrayList();
                arrayList4.ensureCapacity(next.mStreamTemplates.length);
                boolean z = next.mReprocessType != android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.NONE;
                if (z) {
                    java.util.ArrayList arrayList5 = new java.util.ArrayList();
                    if (next.mReprocessType == android.hardware.camera2.params.MandatoryStreamCombination.ReprocessType.PRIVATE) {
                        arrayList5.add(size2);
                        i = 34;
                    } else {
                        arrayList5.add(size3);
                        i = 35;
                    }
                    arrayList4.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(arrayList5, i, true, true));
                    arrayList4.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(arrayList5, i, true));
                }
                android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate[] streamTemplateArr = next.mStreamTemplates;
                int length = streamTemplateArr.length;
                int i3 = 0;
                java.util.List<android.util.Size> list4 = list3;
                while (i3 < length) {
                    android.hardware.camera2.params.MandatoryStreamCombination.StreamTemplate streamTemplate = streamTemplateArr[i3];
                    if (streamTemplate.mFormat == i2) {
                        size = size2;
                        list2 = list4;
                        list = list2;
                    } else {
                        size = size2;
                        list = list4;
                        list2 = enumerateAvailableSizes.get(new android.util.Pair(streamTemplate.mSizeThreshold, new java.lang.Integer(streamTemplate.mFormat)));
                    }
                    try {
                        arrayList4.add(new android.hardware.camera2.params.MandatoryStreamCombination.MandatoryStreamInformation(list2, streamTemplate.mFormat, streamTemplate.mSizeThreshold == android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM));
                        i3++;
                        size2 = size;
                        list4 = list;
                        i2 = 32;
                    } catch (java.lang.IllegalArgumentException e) {
                        android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No available sizes found for format: " + streamTemplate.mFormat + " size threshold: " + streamTemplate.mSizeThreshold + " combination: " + next.mDescription);
                        return null;
                    }
                }
                android.util.Size size4 = size2;
                java.util.List<android.util.Size> list5 = list4;
                try {
                    arrayList3.add(new android.hardware.camera2.params.MandatoryStreamCombination(arrayList4, next.mDescription, z));
                    size2 = size4;
                    list3 = list5;
                    i2 = 32;
                } catch (java.lang.IllegalArgumentException e2) {
                    android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No stream information for mandatory combination: " + next.mDescription);
                    return null;
                }
            }
            return java.util.Collections.unmodifiableList(arrayList3);
        }

        private java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> enumerateAvailableSizes() {
            android.util.Size maxCameraRecordingSize;
            int[] iArr = {32, 34, 35, 256, 54};
            new android.util.Size(0, 0);
            new android.util.Size(0, 0);
            android.util.Size size = new android.util.Size(640, 480);
            android.util.Size size2 = new android.util.Size(1280, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_PERMISSION_DENIED_RECEIVE_WAP_PUSH);
            android.util.Size size3 = new android.util.Size(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, com.android.internal.logging.nano.MetricsProto.MetricsEvent.ACTION_HUSH_GESTURE);
            if (isExternalCamera() || this.mIsHiddenPhysicalCamera) {
                maxCameraRecordingSize = getMaxCameraRecordingSize();
            } else {
                maxCameraRecordingSize = getMaxRecordingSize();
            }
            if (maxCameraRecordingSize == null) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Failed to find maximum recording size!");
                return null;
            }
            java.util.HashMap hashMap = new java.util.HashMap();
            for (int i = 0; i < 5; i++) {
                int i2 = iArr[i];
                java.lang.Integer num = new java.lang.Integer(i2);
                android.util.Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(i2);
                if (outputSizes == null) {
                    outputSizes = new android.util.Size[0];
                }
                hashMap.put(num, outputSizes);
            }
            java.util.List<android.util.Size> sizesWithinBound = getSizesWithinBound((android.util.Size[]) hashMap.get(new java.lang.Integer(34)), this.kPreviewSizeBound);
            if (sizesWithinBound == null || sizesWithinBound.isEmpty()) {
                android.util.Log.e(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "No preview sizes within preview size bound!");
                return null;
            }
            android.util.Size maxPreviewSize = getMaxPreviewSize(getAscendingOrderSizes(sizesWithinBound, false));
            java.util.HashMap<android.util.Pair<android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold, java.lang.Integer>, java.util.List<android.util.Size>> hashMap2 = new java.util.HashMap<>();
            for (int i3 = 0; i3 < 5; i3++) {
                java.lang.Integer num2 = new java.lang.Integer(iArr[i3]);
                android.util.Size[] sizeArr = (android.util.Size[]) hashMap.get(num2);
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.VGA, num2), getSizesWithinBound(sizeArr, size));
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.PREVIEW, num2), getSizesWithinBound(sizeArr, maxPreviewSize));
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.RECORD, num2), getSizesWithinBound(sizeArr, maxCameraRecordingSize));
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.MAXIMUM, num2), java.util.Arrays.asList(sizeArr));
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s720p, num2), getSizesWithinBound(sizeArr, size2));
                hashMap2.put(new android.util.Pair<>(android.hardware.camera2.params.MandatoryStreamCombination.SizeThreshold.s1440p, num2), getSizesWithinBound(sizeArr, size3));
            }
            return hashMap2;
        }

        private static java.util.List<android.util.Size> getSizesWithinBound(android.util.Size[] sizeArr, android.util.Size size) {
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.util.Size size2 : sizeArr) {
                if (size2.getWidth() <= size.getWidth() && size2.getHeight() <= size.getHeight()) {
                    arrayList.add(size2);
                }
            }
            return arrayList;
        }

        public static android.util.Size getMinSize(android.util.Size size, android.util.Size size2) {
            if (size == null || size2 == null) {
                throw new java.lang.IllegalArgumentException("sizes was empty");
            }
            if (size.getWidth() * size.getHeight() < size2.getHeight() * size2.getWidth()) {
                return size;
            }
            return size2;
        }

        public static android.util.Size getMaxSize(android.util.Size... sizeArr) {
            if (sizeArr == null || sizeArr.length == 0) {
                throw new java.lang.IllegalArgumentException("sizes was empty");
            }
            android.util.Size size = sizeArr[0];
            for (android.util.Size size2 : sizeArr) {
                if (size2.getWidth() * size2.getHeight() > size.getWidth() * size.getHeight()) {
                    size = size2;
                }
            }
            return size;
        }

        public static android.util.Size getMaxSizeOrNull(android.util.Size... sizeArr) {
            if (sizeArr == null || sizeArr.length == 0) {
                return null;
            }
            return getMaxSize(sizeArr);
        }

        private boolean isHardwareLevelAtLeast(int i) {
            int[] iArr = {2, 4, 0, 1, 3};
            if (i == this.mHwLevel) {
                return true;
            }
            for (int i2 = 0; i2 < 5; i2++) {
                int i3 = iArr[i2];
                if (i3 == i) {
                    return true;
                }
                if (i3 == this.mHwLevel) {
                    return false;
                }
            }
            return false;
        }

        private boolean isExternalCamera() {
            return this.mHwLevel == 4;
        }

        private boolean isHardwareLevelAtLeastLegacy() {
            return isHardwareLevelAtLeast(2);
        }

        private boolean isHardwareLevelAtLeastLimited() {
            return isHardwareLevelAtLeast(0);
        }

        private boolean isHardwareLevelAtLeastFull() {
            return isHardwareLevelAtLeast(1);
        }

        private boolean isHardwareLevelAtLeastLevel3() {
            return isHardwareLevelAtLeast(3);
        }

        private boolean isCapabilitySupported(int i) {
            return this.mCapabilities.contains(java.lang.Integer.valueOf(i));
        }

        private boolean isColorOutputSupported() {
            return isCapabilitySupported(0);
        }

        private boolean is10BitOutputSupported() {
            return isCapabilitySupported(18);
        }

        private boolean isPrivateReprocessingSupported() {
            return isCapabilitySupported(4);
        }

        private boolean isYUVReprocessingSupported() {
            return isCapabilitySupported(7);
        }

        private boolean isRemosaicReprocessingSupported() {
            return isCapabilitySupported(17);
        }

        private android.util.Size getMaxRecordingSize() {
            int i = 8;
            if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 8)) {
                i = 6;
                if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 6)) {
                    i = 5;
                    if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 5)) {
                        i = 4;
                        if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 4)) {
                            i = 7;
                            if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 7)) {
                                i = 3;
                                if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 3)) {
                                    i = 2;
                                    if (!android.media.CamcorderProfile.hasProfile(this.mCameraId, 2)) {
                                        i = -1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (i < 0) {
                return null;
            }
            android.media.CamcorderProfile camcorderProfile = android.media.CamcorderProfile.get(this.mCameraId, i);
            return new android.util.Size(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        }

        private android.util.Size getMaxCameraRecordingSize() {
            android.util.Size size = new android.util.Size(android.app.settings.SettingsEnums.SCREEN_RESOLUTION, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_CREATE_LOGICAL_LINK);
            android.util.Size[] outputSizes = this.mStreamConfigMap.getOutputSizes(android.media.MediaRecorder.class);
            java.util.ArrayList arrayList = new java.util.ArrayList();
            for (android.util.Size size2 : outputSizes) {
                if (size2.getWidth() <= size.getWidth() && size2.getHeight() <= size.getHeight()) {
                    arrayList.add(size2);
                }
            }
            for (android.util.Size size3 : getAscendingOrderSizes(arrayList, false)) {
                if (this.mStreamConfigMap.getOutputMinFrameDuration(android.media.MediaRecorder.class, size3) < 3.344481605351171E7d) {
                    android.util.Log.i(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "External camera " + this.mCameraId + " has max video size:" + size3);
                    return size3;
                }
            }
            android.util.Log.w(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Camera " + this.mCameraId + " does not support any 30fps video output");
            return size;
        }

        private android.util.Size getMaxPreviewSize(java.util.List<android.util.Size> list) {
            if (list != null) {
                for (android.util.Size size : list) {
                    if (this.mDisplaySize.getWidth() >= size.getWidth() && this.mDisplaySize.getHeight() >= size.getHeight()) {
                        return size;
                    }
                }
            }
            android.util.Log.w(android.hardware.camera2.params.MandatoryStreamCombination.TAG, "Camera " + this.mCameraId + " maximum preview size search failed with display size " + this.mDisplaySize);
            return this.kPreviewSizeBound;
        }

        public static class SizeComparator implements java.util.Comparator<android.util.Size> {
            @Override // java.util.Comparator
            public int compare(android.util.Size size, android.util.Size size2) {
                return android.hardware.camera2.params.StreamConfigurationMap.compareSizes(size.getWidth(), size.getHeight(), size2.getWidth(), size2.getHeight());
            }
        }

        private static java.util.List<android.util.Size> getAscendingOrderSizes(java.util.List<android.util.Size> list, boolean z) {
            android.hardware.camera2.params.MandatoryStreamCombination.Builder.SizeComparator sizeComparator = new android.hardware.camera2.params.MandatoryStreamCombination.Builder.SizeComparator();
            java.util.ArrayList arrayList = new java.util.ArrayList();
            arrayList.addAll(list);
            java.util.Collections.sort(arrayList, sizeComparator);
            if (!z) {
                java.util.Collections.reverse(arrayList);
            }
            return arrayList;
        }
    }
}
