package android.media;

/* loaded from: classes2.dex */
public class ExifInterface {
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final int DATA_DEFLATE_ZIP = 8;
    private static final int DATA_HUFFMAN_COMPRESSED = 2;
    private static final int DATA_JPEG = 6;
    private static final int DATA_JPEG_COMPRESSED = 7;
    private static final int DATA_LOSSY_JPEG = 34892;
    private static final int DATA_PACK_BITS_COMPRESSED = 32773;
    private static final int DATA_UNCOMPRESSED = 1;
    private static final android.media.ExifInterface.ExifTag[] EXIF_POINTER_TAGS;
    private static final android.media.ExifInterface.ExifTag[][] EXIF_TAGS;
    private static final android.media.ExifInterface.ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final android.media.ExifInterface.ExifTag[] IFD_GPS_TAGS;
    private static final android.media.ExifInterface.ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final android.media.ExifInterface.ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final android.media.ExifInterface.ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    private static final int IFD_TYPE_PREVIEW = 5;
    private static final int IFD_TYPE_PRIMARY = 0;
    private static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_PNG = 13;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final int IMAGE_TYPE_WEBP = 14;
    private static final byte MARKER = -1;
    private static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    private static final android.media.ExifInterface.ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final android.media.ExifInterface.ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final android.media.ExifInterface.ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final java.lang.String PEF_SIGNATURE = "PENTAX";
    private static final android.media.ExifInterface.ExifTag[] PEF_TAGS;
    private static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    private static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    private static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    private static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int PNG_CHUNK_CRC_BYTE_LENGTH = 4;
    private static final int PNG_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final java.lang.String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    private static final int REDUCED_RESOLUTION_IMAGE = 1;
    private static final short RW2_SIGNATURE = 85;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;

    @java.lang.Deprecated
    public static final java.lang.String TAG_APERTURE = "FNumber";
    public static final java.lang.String TAG_APERTURE_VALUE = "ApertureValue";
    public static final java.lang.String TAG_ARTIST = "Artist";
    public static final java.lang.String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final java.lang.String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final java.lang.String TAG_CFA_PATTERN = "CFAPattern";
    public static final java.lang.String TAG_COLOR_SPACE = "ColorSpace";
    public static final java.lang.String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final java.lang.String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final java.lang.String TAG_COMPRESSION = "Compression";
    public static final java.lang.String TAG_CONTRAST = "Contrast";
    public static final java.lang.String TAG_COPYRIGHT = "Copyright";
    public static final java.lang.String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final java.lang.String TAG_DATETIME = "DateTime";
    public static final java.lang.String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final java.lang.String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final java.lang.String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final java.lang.String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final java.lang.String TAG_DNG_VERSION = "DNGVersion";
    private static final java.lang.String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final java.lang.String TAG_EXIF_VERSION = "ExifVersion";
    public static final java.lang.String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final java.lang.String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final java.lang.String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final java.lang.String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final java.lang.String TAG_FILE_SOURCE = "FileSource";
    public static final java.lang.String TAG_FLASH = "Flash";
    public static final java.lang.String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final java.lang.String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final java.lang.String TAG_FOCAL_LENGTH = "FocalLength";
    public static final java.lang.String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final java.lang.String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final java.lang.String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final java.lang.String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final java.lang.String TAG_F_NUMBER = "FNumber";
    public static final java.lang.String TAG_GAIN_CONTROL = "GainControl";
    public static final java.lang.String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final java.lang.String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final java.lang.String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final java.lang.String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final java.lang.String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final java.lang.String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final java.lang.String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final java.lang.String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final java.lang.String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final java.lang.String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final java.lang.String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final java.lang.String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final java.lang.String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final java.lang.String TAG_GPS_DOP = "GPSDOP";
    public static final java.lang.String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final java.lang.String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final java.lang.String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final java.lang.String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final java.lang.String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final java.lang.String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final java.lang.String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final java.lang.String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final java.lang.String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final java.lang.String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final java.lang.String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final java.lang.String TAG_GPS_SPEED = "GPSSpeed";
    public static final java.lang.String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final java.lang.String TAG_GPS_STATUS = "GPSStatus";
    public static final java.lang.String TAG_GPS_TRACK = "GPSTrack";
    public static final java.lang.String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final java.lang.String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final java.lang.String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final java.lang.String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final java.lang.String TAG_IMAGE_LENGTH = "ImageLength";
    public static final java.lang.String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final java.lang.String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final java.lang.String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final java.lang.String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";

    @java.lang.Deprecated
    public static final java.lang.String TAG_ISO = "ISOSpeedRatings";
    public static final java.lang.String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final java.lang.String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final java.lang.String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final java.lang.String TAG_LIGHT_SOURCE = "LightSource";
    public static final java.lang.String TAG_MAKE = "Make";
    public static final java.lang.String TAG_MAKER_NOTE = "MakerNote";
    public static final java.lang.String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final java.lang.String TAG_METERING_MODE = "MeteringMode";
    public static final java.lang.String TAG_MODEL = "Model";
    public static final java.lang.String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final java.lang.String TAG_OECF = "OECF";
    public static final java.lang.String TAG_OFFSET_TIME = "OffsetTime";
    public static final java.lang.String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final java.lang.String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final java.lang.String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final java.lang.String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final java.lang.String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final java.lang.String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final java.lang.String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final java.lang.String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final java.lang.String TAG_ORIENTATION = "Orientation";
    public static final java.lang.String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final java.lang.String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final java.lang.String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final java.lang.String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final java.lang.String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final android.media.ExifInterface.ExifTag TAG_RAF_IMAGE_SIZE;
    public static final java.lang.String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final java.lang.String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final java.lang.String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final java.lang.String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final java.lang.String TAG_RW2_ISO = "ISO";
    public static final java.lang.String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final java.lang.String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final java.lang.String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final java.lang.String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final java.lang.String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final java.lang.String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final java.lang.String TAG_SATURATION = "Saturation";
    public static final java.lang.String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final java.lang.String TAG_SCENE_TYPE = "SceneType";
    public static final java.lang.String TAG_SENSING_METHOD = "SensingMethod";
    public static final java.lang.String TAG_SHARPNESS = "Sharpness";
    public static final java.lang.String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final java.lang.String TAG_SOFTWARE = "Software";
    public static final java.lang.String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final java.lang.String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final java.lang.String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final java.lang.String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final java.lang.String TAG_SUBFILE_TYPE = "SubfileType";
    public static final java.lang.String TAG_SUBJECT_AREA = "SubjectArea";
    public static final java.lang.String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final java.lang.String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final java.lang.String TAG_SUBSEC_TIME = "SubSecTime";
    public static final java.lang.String TAG_SUBSEC_TIME_DIG = "SubSecTimeDigitized";
    public static final java.lang.String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final java.lang.String TAG_SUBSEC_TIME_ORIG = "SubSecTimeOriginal";
    public static final java.lang.String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final java.lang.String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final java.lang.String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final java.lang.String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final java.lang.String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final java.lang.String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final java.lang.String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final java.lang.String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    public static final java.lang.String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final java.lang.String TAG_USER_COMMENT = "UserComment";
    public static final java.lang.String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final java.lang.String TAG_WHITE_POINT = "WhitePoint";
    public static final java.lang.String TAG_XMP = "Xmp";
    public static final java.lang.String TAG_X_RESOLUTION = "XResolution";
    public static final java.lang.String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final java.lang.String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final java.lang.String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final java.lang.String TAG_Y_RESOLUTION = "YResolution";
    private static final int WEBP_CHUNK_SIZE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_VP8X_DEFAULT_LENGTH = 10;
    private static final int WEBP_FILE_SIZE_BYTE_LENGTH = 4;
    private static final byte WEBP_VP8L_SIGNATURE = 47;
    public static final int WHITEBALANCE_AUTO = 0;
    public static final int WHITEBALANCE_MANUAL = 1;
    private static final java.util.HashMap[] sExifTagMapsForReading;
    private static final java.util.HashMap[] sExifTagMapsForWriting;
    private static java.text.SimpleDateFormat sFormatterTz;
    private static final java.util.regex.Pattern sGpsTimestampPattern;
    private static final java.util.regex.Pattern sNonZeroTimePattern;
    private boolean mAreThumbnailStripsConsecutive;
    private android.content.res.AssetManager.AssetInputStream mAssetInputStream;
    private final java.util.HashMap[] mAttributes;
    private java.nio.ByteOrder mExifByteOrder;
    private int mExifOffset;
    private java.lang.String mFilename;
    private java.util.Set<java.lang.Integer> mHandledIfdOffsets;
    private boolean mHasThumbnail;
    private boolean mHasThumbnailStrips;
    private boolean mIsExifDataOnly;
    private boolean mIsInputStream;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private boolean mModified;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private java.io.FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;
    private boolean mXmpIsFromSeparateMarker;
    private static final java.lang.String TAG = "ExifInterface";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private static final byte MARKER_SOI = -40;
    private static final byte[] JPEG_SIGNATURE = {-1, MARKER_SOI, -1};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, 112};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, 49};
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] HEIF_BRAND_AVIF = {97, 118, 105, 102};
    private static final byte[] HEIF_BRAND_AVIS = {97, 118, 105, 115};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, android.hardware.biometrics.face.AcquiredInfo.MOUTH_COVERING_DETECTED, 10};
    private static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    private static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    private static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    private static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    private static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    private static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    private static final byte START_CODE = 42;
    private static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, START_CODE};
    private static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(java.nio.charset.Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(java.nio.charset.Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(java.nio.charset.Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(java.nio.charset.Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(java.nio.charset.Charset.defaultCharset());
    private static final java.lang.String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", android.app.admin.DevicePolicyResources.UNDEFINED, "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    private static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final java.lang.String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final java.lang.String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final java.lang.String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final java.lang.String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    private static final java.util.HashSet<java.lang.String> sTagSetForCompatibility = new java.util.HashSet<>(java.util.Arrays.asList("FNumber", TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
    private static final java.util.HashMap<java.lang.Integer, java.lang.Integer> sExifPointerTagMap = new java.util.HashMap<>();
    private static final java.nio.charset.Charset ASCII = java.nio.charset.Charset.forName("US-ASCII");
    private static final byte[] IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(ASCII);
    private static final byte[] IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(ASCII);
    private static java.text.SimpleDateFormat sFormatter = new java.text.SimpleDateFormat("yyyy:MM:dd HH:mm:ss", java.util.Locale.US);

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ExifStreamType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static {
        int i = 3;
        int i2 = 4;
        int i3 = 6;
        int i4 = 1;
        int i5 = 2;
        int i6 = 5;
        int i7 = 7;
        int i8 = 4;
        int i9 = 3;
        int i10 = 23;
        IFD_TIFF_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new android.media.ExifInterface.ExifTag(TAG_SUBFILE_TYPE, 255, i2), new android.media.ExifInterface.ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new android.media.ExifInterface.ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new android.media.ExifInterface.ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new android.media.ExifInterface.ExifTag(TAG_COMPRESSION, 259, i), new android.media.ExifInterface.ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new android.media.ExifInterface.ExifTag(TAG_IMAGE_DESCRIPTION, 270, i5), new android.media.ExifInterface.ExifTag(TAG_MAKE, 271, i5), new android.media.ExifInterface.ExifTag(TAG_MODEL, 272, i5), new android.media.ExifInterface.ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new android.media.ExifInterface.ExifTag(TAG_ORIENTATION, 274, i), new android.media.ExifInterface.ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new android.media.ExifInterface.ExifTag(TAG_ROWS_PER_STRIP, 278, i9, i8), new android.media.ExifInterface.ExifTag(TAG_STRIP_BYTE_COUNTS, 279, i9, i8), new android.media.ExifInterface.ExifTag(TAG_X_RESOLUTION, 282, i6), new android.media.ExifInterface.ExifTag(TAG_Y_RESOLUTION, 283, i6), new android.media.ExifInterface.ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new android.media.ExifInterface.ExifTag(TAG_RESOLUTION_UNIT, 296, i), new android.media.ExifInterface.ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new android.media.ExifInterface.ExifTag(TAG_SOFTWARE, 305, i5), new android.media.ExifInterface.ExifTag(TAG_DATETIME, 306, i5), new android.media.ExifInterface.ExifTag(TAG_ARTIST, 315, i5), new android.media.ExifInterface.ExifTag(TAG_WHITE_POINT, 318, i6), new android.media.ExifInterface.ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i6), new android.media.ExifInterface.ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new android.media.ExifInterface.ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new android.media.ExifInterface.ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i6), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new android.media.ExifInterface.ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i6), new android.media.ExifInterface.ExifTag(TAG_COPYRIGHT, 33432, i5), new android.media.ExifInterface.ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new android.media.ExifInterface.ExifTag(TAG_GPS_INFO_IFD_POINTER, android.opengl.GLES30.GL_DRAW_BUFFER0, i2), new android.media.ExifInterface.ExifTag(TAG_RW2_SENSOR_TOP_BORDER, i2, i2), new android.media.ExifInterface.ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, i6, i2), new android.media.ExifInterface.ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, i3, i2), new android.media.ExifInterface.ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, i7, i2), new android.media.ExifInterface.ExifTag(TAG_RW2_ISO, i10, i), new android.media.ExifInterface.ExifTag(TAG_RW2_JPG_FROM_RAW, 46, i7), new android.media.ExifInterface.ExifTag(TAG_XMP, 700, i4)};
        int i11 = 10;
        int i12 = 4;
        int i13 = 3;
        IFD_EXIF_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_EXPOSURE_TIME, 33434, i6), new android.media.ExifInterface.ExifTag("FNumber", 33437, i6), new android.media.ExifInterface.ExifTag(TAG_EXPOSURE_PROGRAM, 34850, i), new android.media.ExifInterface.ExifTag(TAG_SPECTRAL_SENSITIVITY, android.opengl.GLES30.GL_MAX_DRAW_BUFFERS, i5), new android.media.ExifInterface.ExifTag("ISOSpeedRatings", android.opengl.GLES30.GL_DRAW_BUFFER2, i), new android.media.ExifInterface.ExifTag(TAG_OECF, android.opengl.GLES30.GL_DRAW_BUFFER3, i7), new android.media.ExifInterface.ExifTag(TAG_EXIF_VERSION, 36864, i5), new android.media.ExifInterface.ExifTag(TAG_DATETIME_ORIGINAL, 36867, i5), new android.media.ExifInterface.ExifTag(TAG_DATETIME_DIGITIZED, 36868, i5), new android.media.ExifInterface.ExifTag(TAG_OFFSET_TIME, 36880, i5), new android.media.ExifInterface.ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, i5), new android.media.ExifInterface.ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, i5), new android.media.ExifInterface.ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, i7), new android.media.ExifInterface.ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, i6), new android.media.ExifInterface.ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, i11), new android.media.ExifInterface.ExifTag(TAG_APERTURE_VALUE, 37378, i6), new android.media.ExifInterface.ExifTag(TAG_BRIGHTNESS_VALUE, 37379, i11), new android.media.ExifInterface.ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, i11), new android.media.ExifInterface.ExifTag(TAG_MAX_APERTURE_VALUE, 37381, i6), new android.media.ExifInterface.ExifTag(TAG_SUBJECT_DISTANCE, 37382, i6), new android.media.ExifInterface.ExifTag(TAG_METERING_MODE, 37383, i), new android.media.ExifInterface.ExifTag(TAG_LIGHT_SOURCE, 37384, i), new android.media.ExifInterface.ExifTag(TAG_FLASH, 37385, i), new android.media.ExifInterface.ExifTag(TAG_FOCAL_LENGTH, 37386, i6), new android.media.ExifInterface.ExifTag(TAG_SUBJECT_AREA, 37396, i), new android.media.ExifInterface.ExifTag(TAG_MAKER_NOTE, 37500, i7), new android.media.ExifInterface.ExifTag(TAG_USER_COMMENT, 37510, i7), new android.media.ExifInterface.ExifTag(TAG_SUBSEC_TIME, 37520, i5), new android.media.ExifInterface.ExifTag("SubSecTimeOriginal", 37521, i5), new android.media.ExifInterface.ExifTag("SubSecTimeDigitized", 37522, i5), new android.media.ExifInterface.ExifTag(TAG_FLASHPIX_VERSION, android.hardware.usb.UsbManager.USB_DATA_TRANSFER_RATE_40G, i7), new android.media.ExifInterface.ExifTag(TAG_COLOR_SPACE, 40961, i), new android.media.ExifInterface.ExifTag(TAG_PIXEL_X_DIMENSION, 40962, i13, i12), new android.media.ExifInterface.ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, i13, i12), new android.media.ExifInterface.ExifTag(TAG_RELATED_SOUND_FILE, 40964, i5), new android.media.ExifInterface.ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new android.media.ExifInterface.ExifTag(TAG_FLASH_ENERGY, 41483, i6), new android.media.ExifInterface.ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, i7), new android.media.ExifInterface.ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, i6), new android.media.ExifInterface.ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, i6), new android.media.ExifInterface.ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, i), new android.media.ExifInterface.ExifTag(TAG_SUBJECT_LOCATION, 41492, i), new android.media.ExifInterface.ExifTag(TAG_EXPOSURE_INDEX, 41493, i6), new android.media.ExifInterface.ExifTag(TAG_SENSING_METHOD, 41495, i), new android.media.ExifInterface.ExifTag(TAG_FILE_SOURCE, 41728, i7), new android.media.ExifInterface.ExifTag(TAG_SCENE_TYPE, 41729, i7), new android.media.ExifInterface.ExifTag(TAG_CFA_PATTERN, 41730, i7), new android.media.ExifInterface.ExifTag(TAG_CUSTOM_RENDERED, 41985, i), new android.media.ExifInterface.ExifTag(TAG_EXPOSURE_MODE, 41986, i), new android.media.ExifInterface.ExifTag(TAG_WHITE_BALANCE, 41987, i), new android.media.ExifInterface.ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, i6), new android.media.ExifInterface.ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, i), new android.media.ExifInterface.ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, i), new android.media.ExifInterface.ExifTag(TAG_GAIN_CONTROL, 41991, i), new android.media.ExifInterface.ExifTag(TAG_CONTRAST, 41992, i), new android.media.ExifInterface.ExifTag(TAG_SATURATION, 41993, i), new android.media.ExifInterface.ExifTag(TAG_SHARPNESS, 41994, i), new android.media.ExifInterface.ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, i7), new android.media.ExifInterface.ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, i), new android.media.ExifInterface.ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, i5), new android.media.ExifInterface.ExifTag(TAG_DNG_VERSION, 50706, i4), new android.media.ExifInterface.ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, i13, i12)};
        IFD_GPS_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_GPS_VERSION_ID, 0, i4), new android.media.ExifInterface.ExifTag(TAG_GPS_LATITUDE_REF, i4, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_LATITUDE, i5, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_LONGITUDE_REF, i, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_LONGITUDE, i2, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_ALTITUDE_REF, i6, i4), new android.media.ExifInterface.ExifTag(TAG_GPS_ALTITUDE, i3, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_TIMESTAMP, i7, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_SATELLITES, 8, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_STATUS, 9, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_MEASURE_MODE, 10, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DOP, 11, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_SPEED_REF, 12, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_SPEED, 13, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_TRACK_REF, 14, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_TRACK, 15, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_IMG_DIRECTION, 17, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_MAP_DATUM, 18, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_LATITUDE, 20, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_LONGITUDE, 22, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_BEARING_REF, i10, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_BEARING, 24, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DEST_DISTANCE, 26, i6), new android.media.ExifInterface.ExifTag(TAG_GPS_PROCESSING_METHOD, 27, i7), new android.media.ExifInterface.ExifTag(TAG_GPS_AREA_INFORMATION, 28, i7), new android.media.ExifInterface.ExifTag(TAG_GPS_DATESTAMP, 29, i5), new android.media.ExifInterface.ExifTag(TAG_GPS_DIFFERENTIAL, 30, i)};
        IFD_INTEROPERABILITY_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_INTEROPERABILITY_INDEX, i4, i5)};
        IFD_THUMBNAIL_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new android.media.ExifInterface.ExifTag(TAG_SUBFILE_TYPE, 255, i2), new android.media.ExifInterface.ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, i13, i12), new android.media.ExifInterface.ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new android.media.ExifInterface.ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new android.media.ExifInterface.ExifTag(TAG_COMPRESSION, 259, i), new android.media.ExifInterface.ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new android.media.ExifInterface.ExifTag(TAG_IMAGE_DESCRIPTION, 270, i5), new android.media.ExifInterface.ExifTag(TAG_MAKE, 271, i5), new android.media.ExifInterface.ExifTag(TAG_MODEL, 272, i5), new android.media.ExifInterface.ExifTag(TAG_STRIP_OFFSETS, 273, i13, i12), new android.media.ExifInterface.ExifTag(TAG_THUMBNAIL_ORIENTATION, 274, i), new android.media.ExifInterface.ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new android.media.ExifInterface.ExifTag(TAG_ROWS_PER_STRIP, 278, i13, i12), new android.media.ExifInterface.ExifTag(TAG_STRIP_BYTE_COUNTS, 279, i13, i12), new android.media.ExifInterface.ExifTag(TAG_X_RESOLUTION, 282, i6), new android.media.ExifInterface.ExifTag(TAG_Y_RESOLUTION, 283, i6), new android.media.ExifInterface.ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new android.media.ExifInterface.ExifTag(TAG_RESOLUTION_UNIT, 296, i), new android.media.ExifInterface.ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new android.media.ExifInterface.ExifTag(TAG_SOFTWARE, 305, i5), new android.media.ExifInterface.ExifTag(TAG_DATETIME, 306, i5), new android.media.ExifInterface.ExifTag(TAG_ARTIST, 315, i5), new android.media.ExifInterface.ExifTag(TAG_WHITE_POINT, 318, i6), new android.media.ExifInterface.ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i6), new android.media.ExifInterface.ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new android.media.ExifInterface.ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new android.media.ExifInterface.ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i6), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new android.media.ExifInterface.ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new android.media.ExifInterface.ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i6), new android.media.ExifInterface.ExifTag(TAG_COPYRIGHT, 33432, i5), new android.media.ExifInterface.ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new android.media.ExifInterface.ExifTag(TAG_GPS_INFO_IFD_POINTER, android.opengl.GLES30.GL_DRAW_BUFFER0, i2), new android.media.ExifInterface.ExifTag(TAG_DNG_VERSION, 50706, i4), new android.media.ExifInterface.ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        TAG_RAF_IMAGE_SIZE = new android.media.ExifInterface.ExifTag(TAG_STRIP_OFFSETS, 273, i);
        ORF_MAKER_NOTE_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, i7), new android.media.ExifInterface.ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i2), new android.media.ExifInterface.ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i2)};
        ORF_CAMERA_SETTINGS_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, i2), new android.media.ExifInterface.ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, i2)};
        ORF_IMAGE_PROCESSING_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_ORF_ASPECT_FRAME, com.android.internal.telephony.gsm.SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED, i)};
        PEF_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_COLOR_SPACE, 55, i)};
        EXIF_TAGS = new android.media.ExifInterface.ExifTag[][]{IFD_TIFF_TAGS, IFD_EXIF_TAGS, IFD_GPS_TAGS, IFD_INTEROPERABILITY_TAGS, IFD_THUMBNAIL_TAGS, IFD_TIFF_TAGS, ORF_MAKER_NOTE_TAGS, ORF_CAMERA_SETTINGS_TAGS, ORF_IMAGE_PROCESSING_TAGS, PEF_TAGS};
        EXIF_POINTER_TAGS = new android.media.ExifInterface.ExifTag[]{new android.media.ExifInterface.ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new android.media.ExifInterface.ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new android.media.ExifInterface.ExifTag(TAG_GPS_INFO_IFD_POINTER, android.opengl.GLES30.GL_DRAW_BUFFER0, i2), new android.media.ExifInterface.ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new android.media.ExifInterface.ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i4), new android.media.ExifInterface.ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, android.bluetooth.hci.BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i4)};
        sExifTagMapsForReading = new java.util.HashMap[EXIF_TAGS.length];
        sExifTagMapsForWriting = new java.util.HashMap[EXIF_TAGS.length];
        sFormatter.setTimeZone(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
        sFormatterTz = new java.text.SimpleDateFormat("yyyy:MM:dd HH:mm:ss XXX", java.util.Locale.US);
        sFormatterTz.setTimeZone(java.util.TimeZone.getTimeZone(android.text.format.Time.TIMEZONE_UTC));
        for (int i14 = 0; i14 < EXIF_TAGS.length; i14++) {
            sExifTagMapsForReading[i14] = new java.util.HashMap();
            sExifTagMapsForWriting[i14] = new java.util.HashMap();
            for (android.media.ExifInterface.ExifTag exifTag : EXIF_TAGS[i14]) {
                sExifTagMapsForReading[i14].put(java.lang.Integer.valueOf(exifTag.number), exifTag);
                sExifTagMapsForWriting[i14].put(exifTag.name, exifTag);
            }
        }
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[0].number), 5);
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[1].number), 1);
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[2].number), 2);
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[3].number), 3);
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[4].number), 7);
        sExifPointerTagMap.put(java.lang.Integer.valueOf(EXIF_POINTER_TAGS[5].number), 8);
        sNonZeroTimePattern = java.util.regex.Pattern.compile(".*[1-9].*");
        sGpsTimestampPattern = java.util.regex.Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        private Rational(long j, long j2) {
            if (j2 == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = j;
                this.denominator = j2;
            }
        }

        public java.lang.String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public double calculate() {
            return this.numerator / this.denominator;
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        private ExifAttribute(int i, int i2, byte[] bArr) {
            this(i, i2, -1L, bArr);
        }

        private ExifAttribute(int i, int i2, long j, byte[] bArr) {
            this.format = i;
            this.numberOfComponents = i2;
            this.bytesOffset = j;
            this.bytes = bArr;
        }

        public static android.media.ExifInterface.ExifAttribute createUShort(int[] iArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putShort((short) i);
            }
            return new android.media.ExifInterface.ExifAttribute(3, iArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createUShort(int i, java.nio.ByteOrder byteOrder) {
            return createUShort(new int[]{i}, byteOrder);
        }

        public static android.media.ExifInterface.ExifAttribute createULong(long[] jArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * jArr.length]);
            wrap.order(byteOrder);
            for (long j : jArr) {
                wrap.putInt((int) j);
            }
            return new android.media.ExifInterface.ExifAttribute(4, jArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createULong(long j, java.nio.ByteOrder byteOrder) {
            return createULong(new long[]{j}, byteOrder);
        }

        public static android.media.ExifInterface.ExifAttribute createSLong(int[] iArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * iArr.length]);
            wrap.order(byteOrder);
            for (int i : iArr) {
                wrap.putInt(i);
            }
            return new android.media.ExifInterface.ExifAttribute(9, iArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createSLong(int i, java.nio.ByteOrder byteOrder) {
            return createSLong(new int[]{i}, byteOrder);
        }

        public static android.media.ExifInterface.ExifAttribute createByte(java.lang.String str) {
            if (str.length() == 1 && str.charAt(0) >= '0' && str.charAt(0) <= '1') {
                return new android.media.ExifInterface.ExifAttribute(1, 1, new byte[]{(byte) (str.charAt(0) - '0')});
            }
            byte[] bytes = str.getBytes(android.media.ExifInterface.ASCII);
            return new android.media.ExifInterface.ExifAttribute(1, bytes.length, bytes);
        }

        public static android.media.ExifInterface.ExifAttribute createString(java.lang.String str) {
            byte[] bytes = (str + (char) 0).getBytes(android.media.ExifInterface.ASCII);
            return new android.media.ExifInterface.ExifAttribute(2, bytes.length, bytes);
        }

        public static android.media.ExifInterface.ExifAttribute createURational(android.media.ExifInterface.Rational[] rationalArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * rationalArr.length]);
            wrap.order(byteOrder);
            for (android.media.ExifInterface.Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new android.media.ExifInterface.ExifAttribute(5, rationalArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createURational(android.media.ExifInterface.Rational rational, java.nio.ByteOrder byteOrder) {
            return createURational(new android.media.ExifInterface.Rational[]{rational}, byteOrder);
        }

        public static android.media.ExifInterface.ExifAttribute createSRational(android.media.ExifInterface.Rational[] rationalArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * rationalArr.length]);
            wrap.order(byteOrder);
            for (android.media.ExifInterface.Rational rational : rationalArr) {
                wrap.putInt((int) rational.numerator);
                wrap.putInt((int) rational.denominator);
            }
            return new android.media.ExifInterface.ExifAttribute(10, rationalArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createSRational(android.media.ExifInterface.Rational rational, java.nio.ByteOrder byteOrder) {
            return createSRational(new android.media.ExifInterface.Rational[]{rational}, byteOrder);
        }

        public static android.media.ExifInterface.ExifAttribute createDouble(double[] dArr, java.nio.ByteOrder byteOrder) {
            java.nio.ByteBuffer wrap = java.nio.ByteBuffer.wrap(new byte[android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * dArr.length]);
            wrap.order(byteOrder);
            for (double d : dArr) {
                wrap.putDouble(d);
            }
            return new android.media.ExifInterface.ExifAttribute(12, dArr.length, wrap.array());
        }

        public static android.media.ExifInterface.ExifAttribute createDouble(double d, java.nio.ByteOrder byteOrder) {
            return createDouble(new double[]{d}, byteOrder);
        }

        public java.lang.String toString() {
            return android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START + android.media.ExifInterface.IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.Object getValue(java.nio.ByteOrder byteOrder) {
            byte b;
            try {
                android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(this.bytes);
                byteOrderedDataInputStream.setByteOrder(byteOrder);
                boolean z = true;
                int i = 0;
                switch (this.format) {
                    case 1:
                    case 6:
                        if (this.bytes.length == 1 && this.bytes[0] >= 0 && this.bytes[0] <= 1) {
                            return new java.lang.String(new char[]{(char) (this.bytes[0] + 48)});
                        }
                        return new java.lang.String(this.bytes, android.media.ExifInterface.ASCII);
                    case 2:
                    case 7:
                        if (this.numberOfComponents >= android.media.ExifInterface.EXIF_ASCII_PREFIX.length) {
                            int i2 = 0;
                            while (true) {
                                if (i2 < android.media.ExifInterface.EXIF_ASCII_PREFIX.length) {
                                    if (this.bytes[i2] == android.media.ExifInterface.EXIF_ASCII_PREFIX[i2]) {
                                        i2++;
                                    } else {
                                        z = false;
                                    }
                                }
                            }
                            if (z) {
                                i = android.media.ExifInterface.EXIF_ASCII_PREFIX.length;
                            }
                        }
                        java.lang.StringBuilder sb = new java.lang.StringBuilder();
                        while (i < this.numberOfComponents && (b = this.bytes[i]) != 0) {
                            if (b >= 32) {
                                sb.append((char) b);
                            } else {
                                sb.append('?');
                            }
                            i++;
                        }
                        return sb.toString();
                    case 3:
                        int[] iArr = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr[i] = byteOrderedDataInputStream.readUnsignedShort();
                            i++;
                        }
                        return iArr;
                    case 4:
                        long[] jArr = new long[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            jArr[i] = byteOrderedDataInputStream.readUnsignedInt();
                            i++;
                        }
                        return jArr;
                    case 5:
                        android.media.ExifInterface.Rational[] rationalArr = new android.media.ExifInterface.Rational[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            rationalArr[i] = new android.media.ExifInterface.Rational(byteOrderedDataInputStream.readUnsignedInt(), byteOrderedDataInputStream.readUnsignedInt());
                            i++;
                        }
                        return rationalArr;
                    case 8:
                        int[] iArr2 = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr2[i] = byteOrderedDataInputStream.readShort();
                            i++;
                        }
                        return iArr2;
                    case 9:
                        int[] iArr3 = new int[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            iArr3[i] = byteOrderedDataInputStream.readInt();
                            i++;
                        }
                        return iArr3;
                    case 10:
                        android.media.ExifInterface.Rational[] rationalArr2 = new android.media.ExifInterface.Rational[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            rationalArr2[i] = new android.media.ExifInterface.Rational(byteOrderedDataInputStream.readInt(), byteOrderedDataInputStream.readInt());
                            i++;
                        }
                        return rationalArr2;
                    case 11:
                        double[] dArr = new double[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            dArr[i] = byteOrderedDataInputStream.readFloat();
                            i++;
                        }
                        return dArr;
                    case 12:
                        double[] dArr2 = new double[this.numberOfComponents];
                        while (i < this.numberOfComponents) {
                            dArr2[i] = byteOrderedDataInputStream.readDouble();
                            i++;
                        }
                        return dArr2;
                    default:
                        return null;
                }
            } catch (java.io.IOException e) {
                android.util.Log.w(android.media.ExifInterface.TAG, "IOException occurred during reading a value", e);
                return null;
            }
        }

        public double getDoubleValue(java.nio.ByteOrder byteOrder) {
            java.lang.Object value = getValue(byteOrder);
            if (value == null) {
                throw new java.lang.NumberFormatException("NULL can't be converted to a double value");
            }
            if (value instanceof java.lang.String) {
                return java.lang.Double.parseDouble((java.lang.String) value);
            }
            if (value instanceof long[]) {
                if (((long[]) value).length == 1) {
                    return r5[0];
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            if (value instanceof int[]) {
                if (((int[]) value).length == 1) {
                    return r5[0];
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                if (dArr.length == 1) {
                    return dArr[0];
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            if (value instanceof android.media.ExifInterface.Rational[]) {
                android.media.ExifInterface.Rational[] rationalArr = (android.media.ExifInterface.Rational[]) value;
                if (rationalArr.length == 1) {
                    return rationalArr[0].calculate();
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            throw new java.lang.NumberFormatException("Couldn't find a double value");
        }

        public int getIntValue(java.nio.ByteOrder byteOrder) {
            java.lang.Object value = getValue(byteOrder);
            if (value == null) {
                throw new java.lang.NumberFormatException("NULL can't be converted to a integer value");
            }
            if (value instanceof java.lang.String) {
                return java.lang.Integer.parseInt((java.lang.String) value);
            }
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                if (jArr.length == 1) {
                    return (int) jArr[0];
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                if (iArr.length == 1) {
                    return iArr[0];
                }
                throw new java.lang.NumberFormatException("There are more than one component");
            }
            throw new java.lang.NumberFormatException("Couldn't find a integer value");
        }

        public java.lang.String getStringValue(java.nio.ByteOrder byteOrder) {
            java.lang.Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof java.lang.String) {
                return (java.lang.String) value;
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            int i = 0;
            if (value instanceof long[]) {
                long[] jArr = (long[]) value;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (value instanceof int[]) {
                int[] iArr = (int[]) value;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (value instanceof double[]) {
                double[] dArr = (double[]) value;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (!(value instanceof android.media.ExifInterface.Rational[])) {
                return null;
            }
            android.media.ExifInterface.Rational[] rationalArr = (android.media.ExifInterface.Rational[]) value;
            while (i < rationalArr.length) {
                sb.append(rationalArr[i].numerator);
                sb.append('/');
                sb.append(rationalArr[i].denominator);
                i++;
                if (i != rationalArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        public int size() {
            return android.media.ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    private static class ExifTag {
        public final java.lang.String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        private ExifTag(java.lang.String str, int i, int i2) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = -1;
        }

        private ExifTag(java.lang.String str, int i, int i2, int i3) {
            this.name = str;
            this.number = i;
            this.primaryFormat = i2;
            this.secondaryFormat = i3;
        }
    }

    public ExifInterface(java.io.File file) throws java.io.IOException {
        this.mAttributes = new java.util.HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new java.util.HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = java.nio.ByteOrder.BIG_ENDIAN;
        if (file == null) {
            throw new java.lang.NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    public ExifInterface(java.lang.String str) throws java.io.IOException {
        this.mAttributes = new java.util.HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new java.util.HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = java.nio.ByteOrder.BIG_ENDIAN;
        if (str == null) {
            throw new java.lang.NullPointerException("filename cannot be null");
        }
        initForFilename(str);
    }

    public ExifInterface(java.io.FileDescriptor fileDescriptor) throws java.io.IOException {
        boolean z;
        java.io.FileInputStream fileInputStream;
        java.lang.Throwable th;
        this.mAttributes = new java.util.HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new java.util.HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = java.nio.ByteOrder.BIG_ENDIAN;
        if (fileDescriptor == null) {
            throw new java.lang.NullPointerException("fileDescriptor cannot be null");
        }
        android.os.ParcelFileDescriptor convertToModernFd = android.os.FileUtils.convertToModernFd(fileDescriptor);
        fileDescriptor = convertToModernFd != null ? convertToModernFd.getFileDescriptor() : fileDescriptor;
        this.mAssetInputStream = null;
        this.mFilename = null;
        if (isSeekableFD(fileDescriptor) && convertToModernFd == null) {
            this.mSeekableFileDescriptor = fileDescriptor;
            try {
                fileDescriptor = android.system.Os.dup(fileDescriptor);
                z = true;
            } catch (android.system.ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            this.mSeekableFileDescriptor = null;
            z = false;
        }
        this.mIsInputStream = false;
        try {
            fileInputStream = new java.io.FileInputStream(fileDescriptor);
            try {
                loadAttributes(fileInputStream);
                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                if (z) {
                    android.media.ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
            } catch (java.lang.Throwable th2) {
                th = th2;
                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                if (z) {
                    android.media.ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
                throw th;
            }
        } catch (java.lang.Throwable th3) {
            fileInputStream = null;
            th = th3;
        }
    }

    public ExifInterface(java.io.InputStream inputStream) throws java.io.IOException {
        this(inputStream, false);
    }

    public ExifInterface(java.io.InputStream inputStream, int i) throws java.io.IOException {
        this(inputStream, i == 1);
    }

    private ExifInterface(java.io.InputStream inputStream, boolean z) throws java.io.IOException {
        this.mAttributes = new java.util.HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new java.util.HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = java.nio.ByteOrder.BIG_ENDIAN;
        if (inputStream == null) {
            throw new java.lang.NullPointerException("inputStream cannot be null");
        }
        this.mFilename = null;
        if (z) {
            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream, 5000);
            if (!isExifDataOnly(bufferedInputStream)) {
                android.util.Log.w(TAG, "Given data does not follow the structure of an Exif-only data.");
                return;
            }
            this.mIsExifDataOnly = true;
            this.mAssetInputStream = null;
            this.mSeekableFileDescriptor = null;
            inputStream = bufferedInputStream;
        } else if (inputStream instanceof android.content.res.AssetManager.AssetInputStream) {
            this.mAssetInputStream = (android.content.res.AssetManager.AssetInputStream) inputStream;
            this.mSeekableFileDescriptor = null;
        } else {
            if (inputStream instanceof java.io.FileInputStream) {
                java.io.FileInputStream fileInputStream = (java.io.FileInputStream) inputStream;
                if (isSeekableFD(fileInputStream.getFD())) {
                    this.mAssetInputStream = null;
                    this.mSeekableFileDescriptor = fileInputStream.getFD();
                }
            }
            this.mAssetInputStream = null;
            this.mSeekableFileDescriptor = null;
        }
        loadAttributes(inputStream);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportedMimeType(java.lang.String str) {
        char c;
        if (str == null) {
            throw new java.lang.NullPointerException("mimeType shouldn't be null");
        }
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.ROOT);
        switch (lowerCase.hashCode()) {
            case -1875291391:
                if (lowerCase.equals("image/x-fuji-raf")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1635437028:
                if (lowerCase.equals("image/x-samsung-srw")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1594371159:
                if (lowerCase.equals("image/x-sony-arw")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1487464693:
                if (lowerCase.equals("image/heic")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1487464690:
                if (lowerCase.equals("image/heif")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1487394660:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_JPEG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1487018032:
                if (lowerCase.equals("image/webp")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1423313290:
                if (lowerCase.equals("image/x-adobe-dng")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -985160897:
                if (lowerCase.equals("image/x-panasonic-rw2")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -879258763:
                if (lowerCase.equals(com.google.android.mms.ContentType.IMAGE_PNG)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -332763809:
                if (lowerCase.equals("image/x-pentax-pef")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1378106698:
                if (lowerCase.equals("image/x-olympus-orf")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2099152104:
                if (lowerCase.equals("image/x-nikon-nef")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2099152524:
                if (lowerCase.equals("image/x-nikon-nrw")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2111234748:
                if (lowerCase.equals("image/x-canon-cr2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
                return true;
            default:
                return false;
        }
    }

    private android.media.ExifInterface.ExifAttribute getExifAttribute(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            java.lang.Object obj = this.mAttributes[i].get(str);
            if (obj != null) {
                return (android.media.ExifInterface.ExifAttribute) obj;
            }
        }
        return null;
    }

    public java.lang.String getAttribute(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return null;
        }
        if (!sTagSetForCompatibility.contains(str)) {
            return exifAttribute.getStringValue(this.mExifByteOrder);
        }
        if (str.equals(TAG_GPS_TIMESTAMP)) {
            if (exifAttribute.format != 5 && exifAttribute.format != 10) {
                return null;
            }
            if (((android.media.ExifInterface.Rational[]) exifAttribute.getValue(this.mExifByteOrder)).length != 3) {
                return null;
            }
            return java.lang.String.format("%02d:%02d:%02d", java.lang.Integer.valueOf((int) (r7[0].numerator / r7[0].denominator)), java.lang.Integer.valueOf((int) (r7[1].numerator / r7[1].denominator)), java.lang.Integer.valueOf((int) (r7[2].numerator / r7[2].denominator)));
        }
        try {
            return java.lang.Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
        } catch (java.lang.NumberFormatException e) {
            return null;
        }
    }

    public int getAttributeInt(java.lang.String str, int i) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return i;
        }
        try {
            return exifAttribute.getIntValue(this.mExifByteOrder);
        } catch (java.lang.NumberFormatException e) {
            return i;
        }
    }

    public double getAttributeDouble(java.lang.String str, double d) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute == null) {
            return d;
        }
        try {
            return exifAttribute.getDoubleValue(this.mExifByteOrder);
        } catch (java.lang.NumberFormatException e) {
            return d;
        }
    }

    public void setAttribute(java.lang.String str, java.lang.String str2) {
        java.lang.Object obj;
        int i;
        java.lang.String str3 = str2;
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        if (str3 != null && sTagSetForCompatibility.contains(str)) {
            if (str.equals(TAG_GPS_TIMESTAMP)) {
                java.util.regex.Matcher matcher = sGpsTimestampPattern.matcher(str3);
                if (!matcher.find()) {
                    android.util.Log.w(TAG, "Invalid value for " + str + " : " + str3);
                    return;
                }
                str3 = java.lang.Integer.parseInt(matcher.group(1)) + "/1," + java.lang.Integer.parseInt(matcher.group(2)) + "/1," + java.lang.Integer.parseInt(matcher.group(3)) + "/1";
            } else {
                try {
                    str3 = ((long) (java.lang.Double.parseDouble(str2) * 10000.0d)) + "/10000";
                } catch (java.lang.NumberFormatException e) {
                    android.util.Log.w(TAG, "Invalid value for " + str + " : " + str3);
                    return;
                }
            }
        }
        for (int i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            if ((i2 != 4 || this.mHasThumbnail) && (obj = sExifTagMapsForWriting[i2].get(str)) != null) {
                if (str3 == null) {
                    this.mAttributes[i2].remove(str);
                } else {
                    android.media.ExifInterface.ExifTag exifTag = (android.media.ExifInterface.ExifTag) obj;
                    android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat = guessDataFormat(str3);
                    if (exifTag.primaryFormat == guessDataFormat.first.intValue() || exifTag.primaryFormat == guessDataFormat.second.intValue()) {
                        i = exifTag.primaryFormat;
                    } else if (exifTag.secondaryFormat != -1 && (exifTag.secondaryFormat == guessDataFormat.first.intValue() || exifTag.secondaryFormat == guessDataFormat.second.intValue())) {
                        i = exifTag.secondaryFormat;
                    } else if (exifTag.primaryFormat == 1 || exifTag.primaryFormat == 7 || exifTag.primaryFormat == 2) {
                        i = exifTag.primaryFormat;
                    } else if (DEBUG) {
                        android.util.Log.d(TAG, "Given tag (" + str + ") value didn't match with one of expected formats: " + IFD_FORMAT_NAMES[exifTag.primaryFormat] + (exifTag.secondaryFormat == -1 ? "" : ", " + IFD_FORMAT_NAMES[exifTag.secondaryFormat]) + " (guess: " + IFD_FORMAT_NAMES[guessDataFormat.first.intValue()] + (guessDataFormat.second.intValue() != -1 ? ", " + IFD_FORMAT_NAMES[guessDataFormat.second.intValue()] : "") + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                    }
                    switch (i) {
                        case 1:
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createByte(str3));
                            break;
                        case 2:
                        case 7:
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createString(str3));
                            break;
                        case 3:
                            java.lang.String[] split = str3.split(",");
                            int[] iArr = new int[split.length];
                            for (int i3 = 0; i3 < split.length; i3++) {
                                iArr[i3] = java.lang.Integer.parseInt(split[i3]);
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createUShort(iArr, this.mExifByteOrder));
                            break;
                        case 4:
                            java.lang.String[] split2 = str3.split(",");
                            long[] jArr = new long[split2.length];
                            for (int i4 = 0; i4 < split2.length; i4++) {
                                jArr[i4] = java.lang.Long.parseLong(split2[i4]);
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createULong(jArr, this.mExifByteOrder));
                            break;
                        case 5:
                            java.lang.String[] split3 = str3.split(",");
                            android.media.ExifInterface.Rational[] rationalArr = new android.media.ExifInterface.Rational[split3.length];
                            for (int i5 = 0; i5 < split3.length; i5++) {
                                java.lang.String[] split4 = split3[i5].split("/");
                                rationalArr[i5] = new android.media.ExifInterface.Rational((long) java.lang.Double.parseDouble(split4[0]), (long) java.lang.Double.parseDouble(split4[1]));
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createURational(rationalArr, this.mExifByteOrder));
                            break;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            if (DEBUG) {
                                android.util.Log.d(TAG, "Data format isn't one of expected formats: " + i);
                                break;
                            } else {
                                break;
                            }
                        case 9:
                            java.lang.String[] split5 = str3.split(",");
                            int[] iArr2 = new int[split5.length];
                            for (int i6 = 0; i6 < split5.length; i6++) {
                                iArr2[i6] = java.lang.Integer.parseInt(split5[i6]);
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createSLong(iArr2, this.mExifByteOrder));
                            break;
                        case 10:
                            java.lang.String[] split6 = str3.split(",");
                            android.media.ExifInterface.Rational[] rationalArr2 = new android.media.ExifInterface.Rational[split6.length];
                            for (int i7 = 0; i7 < split6.length; i7++) {
                                java.lang.String[] split7 = split6[i7].split("/");
                                rationalArr2[i7] = new android.media.ExifInterface.Rational((long) java.lang.Double.parseDouble(split7[0]), (long) java.lang.Double.parseDouble(split7[1]));
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createSRational(rationalArr2, this.mExifByteOrder));
                            break;
                        case 12:
                            java.lang.String[] split8 = str3.split(",");
                            double[] dArr = new double[split8.length];
                            for (int i8 = 0; i8 < split8.length; i8++) {
                                dArr[i8] = java.lang.Double.parseDouble(split8[i8]);
                            }
                            this.mAttributes[i2].put(str, android.media.ExifInterface.ExifAttribute.createDouble(dArr, this.mExifByteOrder));
                            break;
                    }
                }
            }
        }
    }

    private boolean updateAttribute(java.lang.String str, android.media.ExifInterface.ExifAttribute exifAttribute) {
        boolean z = false;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            if (this.mAttributes[i].containsKey(str)) {
                this.mAttributes[i].put(str, exifAttribute);
                z = true;
            }
        }
        return z;
    }

    private void removeAttribute(java.lang.String str) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(str);
        }
    }

    private void loadAttributes(java.io.InputStream inputStream) {
        if (inputStream == null) {
            throw new java.lang.NullPointerException("inputstream shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            try {
                try {
                    this.mAttributes[i] = new java.util.HashMap();
                } catch (java.io.IOException | java.lang.OutOfMemoryError e) {
                    this.mIsSupportedFile = false;
                    android.util.Log.w(TAG, "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    addDefaultValuesForCompatibility();
                    if (!DEBUG) {
                        return;
                    }
                }
            } catch (java.lang.Throwable th) {
                addDefaultValuesForCompatibility();
                if (DEBUG) {
                    printAttributes();
                }
                throw th;
            }
        }
        if (!this.mIsExifDataOnly) {
            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(inputStream, 5000);
            java.io.BufferedInputStream bufferedInputStream2 = bufferedInputStream;
            this.mMimeType = getMimeType(bufferedInputStream);
            inputStream = bufferedInputStream;
        }
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(inputStream);
        if (!this.mIsExifDataOnly) {
            switch (this.mMimeType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 8:
                case 11:
                    getRawAttributes(byteOrderedDataInputStream);
                    break;
                case 4:
                    getJpegAttributes(byteOrderedDataInputStream, 0, 0);
                    break;
                case 7:
                    getOrfAttributes(byteOrderedDataInputStream);
                    break;
                case 9:
                    getRafAttributes(byteOrderedDataInputStream);
                    break;
                case 10:
                    getRw2Attributes(byteOrderedDataInputStream);
                    break;
                case 12:
                    getHeifAttributes(byteOrderedDataInputStream);
                    break;
                case 13:
                    getPngAttributes(byteOrderedDataInputStream);
                    break;
                case 14:
                    getWebpAttributes(byteOrderedDataInputStream);
                    break;
            }
        } else {
            getStandaloneAttributes(byteOrderedDataInputStream);
        }
        setThumbnailData(byteOrderedDataInputStream);
        this.mIsSupportedFile = true;
        addDefaultValuesForCompatibility();
        if (!DEBUG) {
            return;
        }
        printAttributes();
    }

    private static boolean isSeekableFD(java.io.FileDescriptor fileDescriptor) {
        try {
            android.system.Os.lseek(fileDescriptor, 0L, android.system.OsConstants.SEEK_CUR);
            return true;
        } catch (android.system.ErrnoException e) {
            if (DEBUG) {
                android.util.Log.d(TAG, "The file descriptor for the given input is not seekable");
                return false;
            }
            return false;
        }
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            android.util.Log.d(TAG, "The size of tag group[" + i + "]: " + this.mAttributes[i].size());
            for (java.util.Map.Entry entry : this.mAttributes[i].entrySet()) {
                android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) entry.getValue();
                android.util.Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + exifAttribute.toString() + ", tagValue: '" + exifAttribute.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    public void saveAttributes() throws java.io.IOException {
        java.io.FileOutputStream fileOutputStream;
        java.io.File createTempFile;
        java.io.FileInputStream fileInputStream;
        java.io.FileOutputStream fileOutputStream2;
        java.io.FileInputStream fileInputStream2;
        if (!isSupportedFormatForSavingAttributes()) {
            throw new java.io.IOException("ExifInterface only supports saving attributes for JPEG, PNG, and WebP formats.");
        }
        if (this.mIsInputStream || (this.mSeekableFileDescriptor == null && this.mFilename == null)) {
            throw new java.io.IOException("ExifInterface does not support saving attributes for the current input.");
        }
        if (this.mHasThumbnail && this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
            throw new java.io.IOException("ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips");
        }
        this.mModified = true;
        this.mThumbnailBytes = getThumbnail();
        java.io.FileInputStream fileInputStream3 = null;
        r0 = null;
        r0 = null;
        java.io.FileOutputStream fileOutputStream3 = null;
        java.io.FileInputStream fileInputStream4 = null;
        fileInputStream3 = null;
        try {
            createTempFile = java.io.File.createTempFile("temp", "tmp");
            if (this.mFilename != null) {
                fileInputStream = new java.io.FileInputStream(this.mFilename);
            } else if (this.mSeekableFileDescriptor != null) {
                android.system.Os.lseek(this.mSeekableFileDescriptor, 0L, android.system.OsConstants.SEEK_SET);
                fileInputStream = new java.io.FileInputStream(this.mSeekableFileDescriptor);
            } else {
                fileInputStream = null;
            }
            try {
                fileOutputStream = new java.io.FileOutputStream(createTempFile);
            } catch (java.lang.Exception e) {
                e = e;
                fileOutputStream = null;
            } catch (java.lang.Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (java.lang.Exception e2) {
            e = e2;
            fileOutputStream = null;
        } catch (java.lang.Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            android.media.ExifInterfaceUtils.copy(fileInputStream, fileOutputStream);
            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
            android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream);
            try {
                fileInputStream2 = new java.io.FileInputStream(createTempFile);
                try {
                    try {
                        if (this.mFilename != null) {
                            fileOutputStream2 = new java.io.FileOutputStream(this.mFilename);
                        } else if (this.mSeekableFileDescriptor != null) {
                            android.system.Os.lseek(this.mSeekableFileDescriptor, 0L, android.system.OsConstants.SEEK_SET);
                            fileOutputStream2 = new java.io.FileOutputStream(this.mSeekableFileDescriptor);
                        } else {
                            fileOutputStream2 = null;
                        }
                        try {
                            java.io.BufferedInputStream bufferedInputStream = new java.io.BufferedInputStream(fileInputStream2);
                            try {
                                java.io.BufferedOutputStream bufferedOutputStream = new java.io.BufferedOutputStream(fileOutputStream2);
                                try {
                                    if (this.mMimeType == 4) {
                                        saveJpegAttributes(bufferedInputStream, bufferedOutputStream);
                                    } else if (this.mMimeType == 13) {
                                        savePngAttributes(bufferedInputStream, bufferedOutputStream);
                                    } else if (this.mMimeType == 14) {
                                        saveWebpAttributes(bufferedInputStream, bufferedOutputStream);
                                    }
                                    bufferedOutputStream.close();
                                    bufferedInputStream.close();
                                    android.media.ExifInterfaceUtils.closeQuietly(fileInputStream2);
                                    android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                    createTempFile.delete();
                                    this.mThumbnailBytes = null;
                                } finally {
                                }
                            } finally {
                            }
                        } catch (java.lang.Exception e3) {
                            java.io.FileOutputStream fileOutputStream4 = fileOutputStream2;
                            e = e3;
                            fileOutputStream3 = fileOutputStream4;
                            java.io.FileInputStream fileInputStream5 = new java.io.FileInputStream(createTempFile);
                            try {
                                if (this.mFilename != null) {
                                    fileOutputStream3 = new java.io.FileOutputStream(this.mFilename);
                                } else if (this.mSeekableFileDescriptor != null) {
                                    try {
                                        android.system.Os.lseek(this.mSeekableFileDescriptor, 0L, android.system.OsConstants.SEEK_SET);
                                        fileOutputStream3 = new java.io.FileOutputStream(this.mSeekableFileDescriptor);
                                    } catch (android.system.ErrnoException e4) {
                                        throw new java.io.IOException("Failed to save new file. Original file may be corrupted since error occurred while trying to restore it.", e4);
                                    }
                                }
                                android.media.ExifInterfaceUtils.copy(fileInputStream5, fileOutputStream3);
                                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream5);
                                android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream3);
                                throw new java.io.IOException("Failed to save new file", e);
                            } catch (java.lang.Throwable th3) {
                                th = th3;
                                fileOutputStream2 = fileOutputStream3;
                                fileInputStream4 = fileInputStream5;
                                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream4);
                                android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                                createTempFile.delete();
                                throw th;
                            }
                        } catch (java.lang.Throwable th4) {
                            th = th4;
                            fileInputStream4 = fileInputStream2;
                            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream4);
                            android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                            createTempFile.delete();
                            throw th;
                        }
                    } catch (java.lang.Exception e5) {
                        e = e5;
                    }
                } catch (java.lang.Throwable th5) {
                    th = th5;
                    fileOutputStream2 = fileOutputStream3;
                }
            } catch (java.lang.Exception e6) {
                e = e6;
                fileInputStream2 = null;
            } catch (java.lang.Throwable th6) {
                th = th6;
                fileOutputStream2 = null;
                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream4);
                android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream2);
                createTempFile.delete();
                throw th;
            }
        } catch (java.lang.Exception e7) {
            e = e7;
            fileInputStream3 = fileInputStream;
            try {
                throw new java.io.IOException("Failed to copy original file to temp file", e);
            } catch (java.lang.Throwable th7) {
                th = th7;
                android.media.ExifInterfaceUtils.closeQuietly(fileInputStream3);
                android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (java.lang.Throwable th8) {
            th = th8;
            fileInputStream3 = fileInputStream;
            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream3);
            android.media.ExifInterfaceUtils.closeQuietly(fileOutputStream);
            throw th;
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean hasAttribute(java.lang.String str) {
        return getExifAttribute(str) != null;
    }

    public byte[] getThumbnail() {
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00c1  */
    /* JADX WARN: Type inference failed for: r1v1, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r1v15, types: [android.content.res.AssetManager$AssetInputStream, java.io.Closeable, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r1v2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public byte[] getThumbnailBytes() {
        java.io.FileDescriptor fileDescriptor;
        java.io.FileInputStream fileInputStream;
        java.lang.Throwable e;
        java.io.FileDescriptor fileDescriptor2;
        java.io.FileInputStream fileInputStream2;
        java.io.Closeable closeable = null;
        if (!this.mHasThumbnail) {
            return null;
        }
        ?? r1 = this.mThumbnailBytes;
        try {
            if (r1 != 0) {
                return this.mThumbnailBytes;
            }
            try {
                try {
                    if (this.mAssetInputStream != null) {
                        r1 = this.mAssetInputStream;
                        try {
                            if (!r1.markSupported()) {
                                android.util.Log.d(TAG, "Cannot read thumbnail from inputstream without mark/reset support");
                                android.media.ExifInterfaceUtils.closeQuietly(r1);
                                return null;
                            }
                            r1.reset();
                            fileDescriptor2 = null;
                            fileInputStream2 = r1;
                        } catch (android.system.ErrnoException | java.io.IOException e2) {
                            e = e2;
                            fileDescriptor2 = null;
                            fileInputStream = r1;
                            android.util.Log.d(TAG, "Encountered exception while getting thumbnail", e);
                            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                            if (fileDescriptor2 != null) {
                            }
                            return null;
                        } catch (java.lang.Throwable th) {
                            th = th;
                            fileDescriptor = null;
                            closeable = r1;
                            android.media.ExifInterfaceUtils.closeQuietly(closeable);
                            if (fileDescriptor != null) {
                                android.media.ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
                            }
                            throw th;
                        }
                    } else if (this.mFilename != null) {
                        fileDescriptor2 = null;
                        fileInputStream2 = new java.io.FileInputStream(this.mFilename);
                    } else if (this.mSeekableFileDescriptor != null) {
                        java.io.FileDescriptor dup = android.system.Os.dup(this.mSeekableFileDescriptor);
                        try {
                            android.system.Os.lseek(dup, 0L, android.system.OsConstants.SEEK_SET);
                            fileDescriptor2 = dup;
                            fileInputStream2 = new java.io.FileInputStream(dup);
                        } catch (android.system.ErrnoException | java.io.IOException e3) {
                            e = e3;
                            fileDescriptor2 = dup;
                            fileInputStream = null;
                            android.util.Log.d(TAG, "Encountered exception while getting thumbnail", e);
                            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                            if (fileDescriptor2 != null) {
                            }
                            return null;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            fileDescriptor = dup;
                            android.media.ExifInterfaceUtils.closeQuietly(closeable);
                            if (fileDescriptor != null) {
                            }
                            throw th;
                        }
                    } else {
                        fileInputStream2 = null;
                        fileDescriptor2 = null;
                    }
                    try {
                        if (fileInputStream2 == null) {
                            throw new java.io.FileNotFoundException();
                        }
                        if (fileInputStream2.skip(this.mThumbnailOffset) != this.mThumbnailOffset) {
                            throw new java.io.IOException("Corrupted image");
                        }
                        byte[] bArr = new byte[this.mThumbnailLength];
                        if (fileInputStream2.read(bArr) != this.mThumbnailLength) {
                            throw new java.io.IOException("Corrupted image");
                        }
                        this.mThumbnailBytes = bArr;
                        android.media.ExifInterfaceUtils.closeQuietly(fileInputStream2);
                        if (fileDescriptor2 != null) {
                            android.media.ExifInterfaceUtils.closeFileDescriptor(fileDescriptor2);
                        }
                        return bArr;
                    } catch (android.system.ErrnoException | java.io.IOException e4) {
                        e = e4;
                        fileInputStream = fileInputStream2;
                        android.util.Log.d(TAG, "Encountered exception while getting thumbnail", e);
                        android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                        if (fileDescriptor2 != null) {
                            android.media.ExifInterfaceUtils.closeFileDescriptor(fileDescriptor2);
                        }
                        return null;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                    fileDescriptor = null;
                }
            } catch (android.system.ErrnoException | java.io.IOException e5) {
                fileInputStream = null;
                e = e5;
                fileDescriptor2 = null;
            }
        } catch (java.lang.Throwable th4) {
            th = th4;
        }
    }

    public android.graphics.Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return android.graphics.BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (this.mThumbnailCompression == 1) {
            int length = this.mThumbnailBytes.length / 3;
            int[] iArr = new int[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 3;
                iArr[i] = (this.mThumbnailBytes[i2] << 16) + 0 + (this.mThumbnailBytes[i2 + 1] << 8) + this.mThumbnailBytes[i2 + 2];
            }
            android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_LENGTH);
            android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_WIDTH);
            if (exifAttribute != null && exifAttribute2 != null) {
                return android.graphics.Bitmap.createBitmap(iArr, exifAttribute2.getIntValue(this.mExifByteOrder), exifAttribute.getIntValue(this.mExifByteOrder), android.graphics.Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (this.mHasThumbnail) {
            return this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7;
        }
        return false;
    }

    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new java.lang.IllegalStateException("The underlying file has been modified since being parsed");
        }
        if (!this.mHasThumbnail) {
            return null;
        }
        if (!this.mHasThumbnailStrips || this.mAreThumbnailStripsConsecutive) {
            return new long[]{this.mThumbnailOffset, this.mThumbnailLength};
        }
        return null;
    }

    public long[] getAttributeRange(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        if (this.mModified) {
            throw new java.lang.IllegalStateException("The underlying file has been modified since being parsed");
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return new long[]{exifAttribute.bytesOffset, exifAttribute.bytes.length};
        }
        return null;
    }

    public byte[] getAttributeBytes(java.lang.String str) {
        if (str == null) {
            throw new java.lang.NullPointerException("tag shouldn't be null");
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = getExifAttribute(str);
        if (exifAttribute != null) {
            return exifAttribute.bytes;
        }
        return null;
    }

    public boolean getLatLong(float[] fArr) {
        java.lang.String attribute = getAttribute(TAG_GPS_LATITUDE);
        java.lang.String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        java.lang.String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        java.lang.String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (attribute != null && attribute2 != null && attribute3 != null && attribute4 != null) {
            try {
                fArr[0] = convertRationalLatLonToFloat(attribute, attribute2);
                fArr[1] = convertRationalLatLonToFloat(attribute3, attribute4);
                return true;
            } catch (java.lang.IllegalArgumentException e) {
            }
        }
        return false;
    }

    public double getAltitude(double d) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d;
        }
        return attributeDouble * (attributeInt != 1 ? 1 : -1);
    }

    public long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    public long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute("SubSecTimeDigitized"), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    public long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute("SubSecTimeOriginal"), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    private static long parseDateTime(java.lang.String str, java.lang.String str2, java.lang.String str3) {
        java.util.Date parse;
        java.util.Date parse2;
        if (str == null || !sNonZeroTimePattern.matcher(str).matches()) {
            return -1L;
        }
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        try {
            synchronized (sFormatter) {
                parse = sFormatter.parse(str, parsePosition);
            }
            if (str3 != null) {
                java.lang.String str4 = str + " " + str3;
                java.text.ParsePosition parsePosition2 = new java.text.ParsePosition(0);
                synchronized (sFormatterTz) {
                    parse2 = sFormatterTz.parse(str4, parsePosition2);
                }
                parse = parse2;
            }
            if (parse == null) {
                return -1L;
            }
            long time = parse.getTime();
            if (str2 != null) {
                try {
                    long parseLong = java.lang.Long.parseLong(str2);
                    while (parseLong > 1000) {
                        parseLong /= 10;
                    }
                    return time + parseLong;
                } catch (java.lang.NumberFormatException e) {
                    return time;
                }
            }
            return time;
        } catch (java.lang.IllegalArgumentException e2) {
            return -1L;
        }
    }

    public long getGpsDateTime() {
        java.util.Date parse;
        java.lang.String attribute = getAttribute(TAG_GPS_DATESTAMP);
        java.lang.String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute == null || attribute2 == null || (!sNonZeroTimePattern.matcher(attribute).matches() && !sNonZeroTimePattern.matcher(attribute2).matches())) {
            return -1L;
        }
        java.lang.String str = attribute + ' ' + attribute2;
        java.text.ParsePosition parsePosition = new java.text.ParsePosition(0);
        try {
            synchronized (sFormatter) {
                parse = sFormatter.parse(str, parsePosition);
            }
            if (parse == null) {
                return -1L;
            }
            return parse.getTime();
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.IllegalArgumentException e) {
            return -1L;
        }
    }

    public static float convertRationalLatLonToFloat(java.lang.String str, java.lang.String str2) {
        try {
            java.lang.String[] split = str.split(",");
            java.lang.String[] split2 = split[0].split("/");
            double parseDouble = java.lang.Double.parseDouble(split2[0].trim()) / java.lang.Double.parseDouble(split2[1].trim());
            java.lang.String[] split3 = split[1].split("/");
            double parseDouble2 = java.lang.Double.parseDouble(split3[0].trim()) / java.lang.Double.parseDouble(split3[1].trim());
            java.lang.String[] split4 = split[2].split("/");
            double parseDouble3 = parseDouble + (parseDouble2 / 60.0d) + ((java.lang.Double.parseDouble(split4[0].trim()) / java.lang.Double.parseDouble(split4[1].trim())) / 3600.0d);
            if (!str2.equals(android.hardware.gnss.GnssSignalType.CODE_TYPE_S)) {
                if (!str2.equals(android.hardware.gnss.GnssSignalType.CODE_TYPE_W)) {
                    return (float) parseDouble3;
                }
            }
            return (float) (-parseDouble3);
        } catch (java.lang.ArrayIndexOutOfBoundsException | java.lang.NumberFormatException e) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void initForFilename(java.lang.String str) throws java.io.IOException {
        java.io.FileInputStream fileInputStream;
        java.lang.Throwable th;
        android.os.ParcelFileDescriptor parcelFileDescriptor;
        java.io.FileInputStream fileInputStream2;
        this.mAssetInputStream = null;
        this.mFilename = str;
        this.mIsInputStream = false;
        try {
            fileInputStream = new java.io.FileInputStream(str);
            try {
                parcelFileDescriptor = android.os.FileUtils.convertToModernFd(fileInputStream.getFD());
                try {
                    if (parcelFileDescriptor != null) {
                        android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                        java.io.FileInputStream fileInputStream3 = new java.io.FileInputStream(parcelFileDescriptor.getFileDescriptor());
                        try {
                            this.mSeekableFileDescriptor = null;
                            fileInputStream2 = fileInputStream3;
                        } catch (java.lang.Throwable th2) {
                            th = th2;
                            fileInputStream = fileInputStream3;
                            android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                            if (parcelFileDescriptor != null) {
                            }
                            throw th;
                        }
                    } else {
                        if (isSeekableFD(fileInputStream.getFD())) {
                            this.mSeekableFileDescriptor = fileInputStream.getFD();
                        }
                        fileInputStream2 = fileInputStream;
                    }
                } catch (java.lang.Throwable th3) {
                    th = th3;
                }
                try {
                    loadAttributes(fileInputStream2);
                    android.media.ExifInterfaceUtils.closeQuietly(fileInputStream2);
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                } catch (java.lang.Throwable th4) {
                    fileInputStream = fileInputStream2;
                    th = th4;
                    android.media.ExifInterfaceUtils.closeQuietly(fileInputStream);
                    if (parcelFileDescriptor != null) {
                        parcelFileDescriptor.close();
                    }
                    throw th;
                }
            } catch (java.lang.Throwable th5) {
                th = th5;
                parcelFileDescriptor = null;
            }
        } catch (java.lang.Throwable th6) {
            fileInputStream = null;
            th = th6;
            parcelFileDescriptor = null;
        }
    }

    private int getMimeType(java.io.BufferedInputStream bufferedInputStream) throws java.io.IOException {
        bufferedInputStream.mark(5000);
        byte[] bArr = new byte[5000];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        if (isJpegFormat(bArr)) {
            return 4;
        }
        if (isRafFormat(bArr)) {
            return 9;
        }
        if (isHeifFormat(bArr)) {
            return 12;
        }
        if (isOrfFormat(bArr)) {
            return 7;
        }
        if (isRw2Format(bArr)) {
            return 10;
        }
        if (isPngFormat(bArr)) {
            return 13;
        }
        if (isWebpFormat(bArr)) {
            return 14;
        }
        return 0;
    }

    private static boolean isJpegFormat(byte[] bArr) throws java.io.IOException {
        for (int i = 0; i < JPEG_SIGNATURE.length; i++) {
            if (bArr[i] != JPEG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRafFormat(byte[] bArr) throws java.io.IOException {
        byte[] bytes = RAF_SIGNATURE.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bArr[i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeifFormat(byte[] bArr) throws java.io.IOException {
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream;
        long readInt;
        byte[] bArr2;
        long j;
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            try {
                byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(bArr);
                try {
                    readInt = byteOrderedDataInputStream.readInt();
                    bArr2 = new byte[4];
                    byteOrderedDataInputStream.read(bArr2);
                } catch (java.lang.Exception e) {
                    e = e;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (DEBUG) {
                        android.util.Log.d(TAG, "Exception parsing HEIF file type box.", e);
                    }
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    return false;
                } catch (java.lang.Throwable th) {
                    th = th;
                    byteOrderedDataInputStream2 = byteOrderedDataInputStream;
                    if (byteOrderedDataInputStream2 != null) {
                        byteOrderedDataInputStream2.close();
                    }
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
            if (!java.util.Arrays.equals(bArr2, HEIF_TYPE_FTYP)) {
                byteOrderedDataInputStream.close();
                return false;
            }
            if (readInt == 1) {
                readInt = byteOrderedDataInputStream.readLong();
                j = 16;
                if (readInt < 16) {
                    byteOrderedDataInputStream.close();
                    return false;
                }
            } else {
                j = 8;
            }
            if (readInt > bArr.length) {
                readInt = bArr.length;
            }
            long j2 = readInt - j;
            if (j2 < 8) {
                byteOrderedDataInputStream.close();
                return false;
            }
            byte[] bArr3 = new byte[4];
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (long j3 = 0; j3 < j2 / 4; j3++) {
                if (byteOrderedDataInputStream.read(bArr3) != 4) {
                    byteOrderedDataInputStream.close();
                    return false;
                }
                if (j3 != 1) {
                    if (java.util.Arrays.equals(bArr3, HEIF_BRAND_MIF1)) {
                        z = true;
                    } else if (java.util.Arrays.equals(bArr3, HEIF_BRAND_HEIC)) {
                        z2 = true;
                    } else if (java.util.Arrays.equals(bArr3, HEIF_BRAND_AVIF) || java.util.Arrays.equals(bArr3, HEIF_BRAND_AVIS)) {
                        z3 = true;
                    }
                    if (z && (z2 || z3)) {
                        byteOrderedDataInputStream.close();
                        return true;
                    }
                }
            }
            byteOrderedDataInputStream.close();
            return false;
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private boolean isOrfFormat(byte[] bArr) throws java.io.IOException {
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream;
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(bArr);
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
            byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
            short readShort = byteOrderedDataInputStream.readShort();
            boolean z = readShort == 20306 || readShort == 21330;
            byteOrderedDataInputStream.close();
            return z;
        } catch (java.lang.Exception e2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (java.lang.Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private boolean isRw2Format(byte[] bArr) throws java.io.IOException {
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream;
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream2 = null;
        try {
            byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(bArr);
        } catch (java.lang.Exception e) {
        } catch (java.lang.Throwable th) {
            th = th;
        }
        try {
            this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
            byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
            short readShort = byteOrderedDataInputStream.readShort();
            byteOrderedDataInputStream.close();
            boolean z = readShort == 85;
            byteOrderedDataInputStream.close();
            return z;
        } catch (java.lang.Exception e2) {
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            return false;
        } catch (java.lang.Throwable th2) {
            th = th2;
            byteOrderedDataInputStream2 = byteOrderedDataInputStream;
            if (byteOrderedDataInputStream2 != null) {
                byteOrderedDataInputStream2.close();
            }
            throw th;
        }
    }

    private boolean isPngFormat(byte[] bArr) throws java.io.IOException {
        for (int i = 0; i < PNG_SIGNATURE.length; i++) {
            if (bArr[i] != PNG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isWebpFormat(byte[] bArr) throws java.io.IOException {
        for (int i = 0; i < WEBP_SIGNATURE_1.length; i++) {
            if (bArr[i] != WEBP_SIGNATURE_1[i]) {
                return false;
            }
        }
        for (int i2 = 0; i2 < WEBP_SIGNATURE_2.length; i2++) {
            if (bArr[WEBP_SIGNATURE_1.length + i2 + 4] != WEBP_SIGNATURE_2[i2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isExifDataOnly(java.io.BufferedInputStream bufferedInputStream) throws java.io.IOException {
        bufferedInputStream.mark(IDENTIFIER_EXIF_APP1.length);
        byte[] bArr = new byte[IDENTIFIER_EXIF_APP1.length];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        for (int i = 0; i < IDENTIFIER_EXIF_APP1.length; i++) {
            if (bArr[i] != IDENTIFIER_EXIF_APP1[i]) {
                return false;
            }
        }
        return true;
    }

    private void getJpegAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, int i, int i2) throws java.io.IOException {
        if (DEBUG) {
            android.util.Log.d(TAG, "getJpegAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(java.nio.ByteOrder.BIG_ENDIAN);
        byteOrderedDataInputStream.seek(i);
        byte readByte = byteOrderedDataInputStream.readByte();
        byte b = -1;
        if (readByte != -1) {
            throw new java.io.IOException("Invalid marker: " + java.lang.Integer.toHexString(readByte & 255));
        }
        int i3 = i + 1;
        if (byteOrderedDataInputStream.readByte() != -40) {
            throw new java.io.IOException("Invalid marker: " + java.lang.Integer.toHexString(readByte & 255));
        }
        int i4 = i3 + 1;
        while (true) {
            byte readByte2 = byteOrderedDataInputStream.readByte();
            if (readByte2 != b) {
                throw new java.io.IOException("Invalid marker:" + java.lang.Integer.toHexString(readByte2 & 255));
            }
            int i5 = i4 + 1;
            byte readByte3 = byteOrderedDataInputStream.readByte();
            if (DEBUG) {
                android.util.Log.d(TAG, "Found JPEG segment indicator: " + java.lang.Integer.toHexString(readByte3 & 255));
            }
            int i6 = i5 + 1;
            if (readByte3 != -39 && readByte3 != -38) {
                int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort() - 2;
                int i7 = i6 + 2;
                if (DEBUG) {
                    android.util.Log.d(TAG, "JPEG segment: " + java.lang.Integer.toHexString(readByte3 & 255) + " (length: " + (readUnsignedShort + 2) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                }
                if (readUnsignedShort >= 0) {
                    switch (readByte3) {
                        case -64:
                        case -63:
                        case android.hardware.security.keymint.ErrorCode.KEY_REQUIRES_UPGRADE /* -62 */:
                        case -61:
                        case -59:
                        case -58:
                        case -57:
                        case -55:
                        case -54:
                        case -53:
                        case -51:
                        case -50:
                        case -49:
                            if (byteOrderedDataInputStream.skipBytes(1) != 1) {
                                throw new java.io.IOException("Invalid SOFx");
                            }
                            this.mAttributes[i2].put(i2 != 4 ? TAG_IMAGE_LENGTH : TAG_THUMBNAIL_IMAGE_LENGTH, android.media.ExifInterface.ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                            this.mAttributes[i2].put(i2 != 4 ? TAG_IMAGE_WIDTH : TAG_THUMBNAIL_IMAGE_WIDTH, android.media.ExifInterface.ExifAttribute.createULong(byteOrderedDataInputStream.readUnsignedShort(), this.mExifByteOrder));
                            readUnsignedShort -= 5;
                            break;
                        case -31:
                            byte[] bArr = new byte[readUnsignedShort];
                            byteOrderedDataInputStream.readFully(bArr);
                            int i8 = i7 + readUnsignedShort;
                            if (android.media.ExifInterfaceUtils.startsWith(bArr, IDENTIFIER_EXIF_APP1)) {
                                long length = i7 + IDENTIFIER_EXIF_APP1.length;
                                byte[] copyOfRange = java.util.Arrays.copyOfRange(bArr, IDENTIFIER_EXIF_APP1.length, readUnsignedShort);
                                this.mExifOffset = (int) length;
                                readExifSegment(copyOfRange, i2);
                            } else if (android.media.ExifInterfaceUtils.startsWith(bArr, IDENTIFIER_XMP_APP1)) {
                                long length2 = i7 + IDENTIFIER_XMP_APP1.length;
                                byte[] copyOfRange2 = java.util.Arrays.copyOfRange(bArr, IDENTIFIER_XMP_APP1.length, readUnsignedShort);
                                if (getAttribute(TAG_XMP) == null) {
                                    this.mAttributes[0].put(TAG_XMP, new android.media.ExifInterface.ExifAttribute(1, copyOfRange2.length, length2, copyOfRange2));
                                    this.mXmpIsFromSeparateMarker = true;
                                }
                            }
                            readUnsignedShort = 0;
                            i7 = i8;
                            break;
                        case -2:
                            byte[] bArr2 = new byte[readUnsignedShort];
                            if (byteOrderedDataInputStream.read(bArr2) == readUnsignedShort) {
                                if (getAttribute(TAG_USER_COMMENT) == null) {
                                    this.mAttributes[1].put(TAG_USER_COMMENT, android.media.ExifInterface.ExifAttribute.createString(new java.lang.String(bArr2, ASCII)));
                                }
                                readUnsignedShort = 0;
                                break;
                            } else {
                                throw new java.io.IOException("Invalid exif");
                            }
                    }
                    if (readUnsignedShort < 0) {
                        throw new java.io.IOException("Invalid length");
                    }
                    if (byteOrderedDataInputStream.skipBytes(readUnsignedShort) != readUnsignedShort) {
                        throw new java.io.IOException("Invalid JPEG segment");
                    }
                    i4 = i7 + readUnsignedShort;
                    b = -1;
                } else {
                    throw new java.io.IOException("Invalid length");
                }
            }
        }
        byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
    }

    private void getRawAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute;
        parseTiffHeaders(byteOrderedDataInputStream, byteOrderedDataInputStream.available());
        readImageFileDirectory(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 0);
        updateImageSizeValues(byteOrderedDataInputStream, 5);
        updateImageSizeValues(byteOrderedDataInputStream, 4);
        validateImages();
        if (this.mMimeType == 8 && (exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE)) != null) {
            android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream2 = new android.media.ExifInterface.ByteOrderedDataInputStream(exifAttribute.bytes);
            byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
            byteOrderedDataInputStream2.seek(6L);
            readImageFileDirectory(byteOrderedDataInputStream2, 9);
            android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[9].get(TAG_COLOR_SPACE);
            if (exifAttribute2 != null) {
                this.mAttributes[1].put(TAG_COLOR_SPACE, exifAttribute2);
            }
        }
    }

    private void getRafAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        byteOrderedDataInputStream.skipBytes(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        byteOrderedDataInputStream.read(bArr);
        byteOrderedDataInputStream.skipBytes(4);
        byteOrderedDataInputStream.read(bArr2);
        int i = java.nio.ByteBuffer.wrap(bArr).getInt();
        int i2 = java.nio.ByteBuffer.wrap(bArr2).getInt();
        getJpegAttributes(byteOrderedDataInputStream, i, 5);
        byteOrderedDataInputStream.seek(i2);
        byteOrderedDataInputStream.setByteOrder(java.nio.ByteOrder.BIG_ENDIAN);
        int readInt = byteOrderedDataInputStream.readInt();
        if (DEBUG) {
            android.util.Log.d(TAG, "numberOfDirectoryEntry: " + readInt);
        }
        for (int i3 = 0; i3 < readInt; i3++) {
            int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            if (readUnsignedShort == TAG_RAF_IMAGE_SIZE.number) {
                short readShort = byteOrderedDataInputStream.readShort();
                short readShort2 = byteOrderedDataInputStream.readShort();
                android.media.ExifInterface.ExifAttribute createUShort = android.media.ExifInterface.ExifAttribute.createUShort(readShort, this.mExifByteOrder);
                android.media.ExifInterface.ExifAttribute createUShort2 = android.media.ExifInterface.ExifAttribute.createUShort(readShort2, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, createUShort2);
                if (DEBUG) {
                    android.util.Log.d(TAG, "Updated to length: " + ((int) readShort) + ", width: " + ((int) readShort2));
                    return;
                }
                return;
            }
            byteOrderedDataInputStream.skipBytes(readUnsignedShort2);
        }
    }

    private void getHeifAttributes(final android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        java.lang.String str;
        java.lang.String str2;
        java.lang.String str3;
        int i;
        android.media.MediaMetadataRetriever mediaMetadataRetriever = new android.media.MediaMetadataRetriever();
        try {
            mediaMetadataRetriever.setDataSource(new android.media.MediaDataSource() { // from class: android.media.ExifInterface.1
                long mPosition;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws java.io.IOException {
                }

                @Override // android.media.MediaDataSource
                public int readAt(long j, byte[] bArr, int i2, int i3) throws java.io.IOException {
                    if (i3 == 0) {
                        return 0;
                    }
                    if (j < 0) {
                        return -1;
                    }
                    try {
                        if (this.mPosition != j) {
                            if (this.mPosition >= 0 && j >= this.mPosition + byteOrderedDataInputStream.available()) {
                                return -1;
                            }
                            byteOrderedDataInputStream.seek(j);
                            this.mPosition = j;
                        }
                        if (i3 > byteOrderedDataInputStream.available()) {
                            i3 = byteOrderedDataInputStream.available();
                        }
                        int read = byteOrderedDataInputStream.read(bArr, i2, i3);
                        if (read >= 0) {
                            this.mPosition += read;
                            return read;
                        }
                    } catch (java.io.IOException e) {
                    }
                    this.mPosition = -1L;
                    return -1;
                }

                @Override // android.media.MediaDataSource
                public long getSize() throws java.io.IOException {
                    return -1L;
                }
            });
            java.lang.String extractMetadata = mediaMetadataRetriever.extractMetadata(33);
            java.lang.String extractMetadata2 = mediaMetadataRetriever.extractMetadata(34);
            java.lang.String extractMetadata3 = mediaMetadataRetriever.extractMetadata(26);
            java.lang.String extractMetadata4 = mediaMetadataRetriever.extractMetadata(17);
            if (android.media.MediaMetrics.Value.YES.equals(extractMetadata3)) {
                str = mediaMetadataRetriever.extractMetadata(29);
                str2 = mediaMetadataRetriever.extractMetadata(30);
                str3 = mediaMetadataRetriever.extractMetadata(31);
            } else if (!android.media.MediaMetrics.Value.YES.equals(extractMetadata4)) {
                str = null;
                str2 = null;
                str3 = null;
            } else {
                str = mediaMetadataRetriever.extractMetadata(18);
                str2 = mediaMetadataRetriever.extractMetadata(19);
                str3 = mediaMetadataRetriever.extractMetadata(24);
            }
            if (str != null) {
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, android.media.ExifInterface.ExifAttribute.createUShort(java.lang.Integer.parseInt(str), this.mExifByteOrder));
            }
            if (str2 != null) {
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, android.media.ExifInterface.ExifAttribute.createUShort(java.lang.Integer.parseInt(str2), this.mExifByteOrder));
            }
            if (str3 != null) {
                switch (java.lang.Integer.parseInt(str3)) {
                    case 90:
                        i = 6;
                        break;
                    case 180:
                        i = 3;
                        break;
                    case 270:
                        i = 8;
                        break;
                    default:
                        i = 1;
                        break;
                }
                this.mAttributes[0].put(TAG_ORIENTATION, android.media.ExifInterface.ExifAttribute.createUShort(i, this.mExifByteOrder));
            }
            if (extractMetadata != null && extractMetadata2 != null) {
                int parseInt = java.lang.Integer.parseInt(extractMetadata);
                int parseInt2 = java.lang.Integer.parseInt(extractMetadata2);
                if (parseInt2 <= 6) {
                    throw new java.io.IOException("Invalid exif length");
                }
                byteOrderedDataInputStream.seek(parseInt);
                byte[] bArr = new byte[6];
                if (byteOrderedDataInputStream.read(bArr) != 6) {
                    throw new java.io.IOException("Can't read identifier");
                }
                int i2 = parseInt + 6;
                int i3 = parseInt2 - 6;
                if (!java.util.Arrays.equals(bArr, IDENTIFIER_EXIF_APP1)) {
                    throw new java.io.IOException("Invalid identifier");
                }
                byte[] bArr2 = new byte[i3];
                if (byteOrderedDataInputStream.read(bArr2) != i3) {
                    throw new java.io.IOException("Can't read exif");
                }
                this.mExifOffset = i2;
                readExifSegment(bArr2, 0);
            }
            java.lang.String extractMetadata5 = mediaMetadataRetriever.extractMetadata(41);
            java.lang.String extractMetadata6 = mediaMetadataRetriever.extractMetadata(42);
            if (extractMetadata5 != null && extractMetadata6 != null) {
                int parseInt3 = java.lang.Integer.parseInt(extractMetadata5);
                int parseInt4 = java.lang.Integer.parseInt(extractMetadata6);
                long j = parseInt3;
                byteOrderedDataInputStream.seek(j);
                byte[] bArr3 = new byte[parseInt4];
                if (byteOrderedDataInputStream.read(bArr3) != parseInt4) {
                    throw new java.io.IOException("Failed to read XMP from HEIF");
                }
                if (getAttribute(TAG_XMP) == null) {
                    this.mAttributes[0].put(TAG_XMP, new android.media.ExifInterface.ExifAttribute(1, parseInt4, j, bArr3));
                }
            }
            if (DEBUG) {
                android.util.Log.d(TAG, "Heif meta: " + str + "x" + str2 + ", rotation " + str3);
            }
        } finally {
            mediaMetadataRetriever.release();
        }
    }

    private void getStandaloneAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        byteOrderedDataInputStream.skipBytes(IDENTIFIER_EXIF_APP1.length);
        byte[] bArr = new byte[byteOrderedDataInputStream.available()];
        byteOrderedDataInputStream.readFully(bArr);
        this.mExifOffset = IDENTIFIER_EXIF_APP1.length;
        readExifSegment(bArr, 0);
    }

    private void getOrfAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        getRawAttributes(byteOrderedDataInputStream);
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (exifAttribute != null) {
            android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream2 = new android.media.ExifInterface.ByteOrderedDataInputStream(exifAttribute.bytes);
            byteOrderedDataInputStream2.setByteOrder(this.mExifByteOrder);
            byte[] bArr = new byte[ORF_MAKER_NOTE_HEADER_1.length];
            byteOrderedDataInputStream2.readFully(bArr);
            byteOrderedDataInputStream2.seek(0L);
            byte[] bArr2 = new byte[ORF_MAKER_NOTE_HEADER_2.length];
            byteOrderedDataInputStream2.readFully(bArr2);
            if (java.util.Arrays.equals(bArr, ORF_MAKER_NOTE_HEADER_1)) {
                byteOrderedDataInputStream2.seek(8L);
            } else if (java.util.Arrays.equals(bArr2, ORF_MAKER_NOTE_HEADER_2)) {
                byteOrderedDataInputStream2.seek(12L);
            }
            readImageFileDirectory(byteOrderedDataInputStream2, 6);
            android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            android.media.ExifInterface.ExifAttribute exifAttribute3 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (exifAttribute2 != null && exifAttribute3 != null) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, exifAttribute2);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, exifAttribute3);
            }
            android.media.ExifInterface.ExifAttribute exifAttribute4 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (exifAttribute4 != null) {
                int[] iArr = (int[]) exifAttribute4.getValue(this.mExifByteOrder);
                if (iArr[2] > iArr[0] && iArr[3] > iArr[1]) {
                    int i = (iArr[2] - iArr[0]) + 1;
                    int i2 = (iArr[3] - iArr[1]) + 1;
                    if (i < i2) {
                        int i3 = i + i2;
                        i2 = i3 - i2;
                        i = i3 - i2;
                    }
                    android.media.ExifInterface.ExifAttribute createUShort = android.media.ExifInterface.ExifAttribute.createUShort(i, this.mExifByteOrder);
                    android.media.ExifInterface.ExifAttribute createUShort2 = android.media.ExifInterface.ExifAttribute.createUShort(i2, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, createUShort);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, createUShort2);
                }
            }
        }
    }

    private void getRw2Attributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        getRawAttributes(byteOrderedDataInputStream);
        if (((android.media.ExifInterface.ExifAttribute) this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW)) != null) {
            getJpegAttributes(byteOrderedDataInputStream, this.mRw2JpgFromRawOffset, 5);
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[0].get(TAG_RW2_ISO);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[1].get("ISOSpeedRatings");
        if (exifAttribute != null && exifAttribute2 == null) {
            this.mAttributes[1].put("ISOSpeedRatings", exifAttribute);
        }
    }

    private void getPngAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        if (DEBUG) {
            android.util.Log.d(TAG, "getPngAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(java.nio.ByteOrder.BIG_ENDIAN);
        byteOrderedDataInputStream.skipBytes(PNG_SIGNATURE.length);
        int length = PNG_SIGNATURE.length + 0;
        while (true) {
            try {
                int readInt = byteOrderedDataInputStream.readInt();
                int i = length + 4;
                byte[] bArr = new byte[4];
                if (byteOrderedDataInputStream.read(bArr) != 4) {
                    throw new java.io.IOException("Encountered invalid length while parsing PNG chunktype");
                }
                int i2 = i + 4;
                if (i2 == 16 && !java.util.Arrays.equals(bArr, PNG_CHUNK_TYPE_IHDR)) {
                    throw new java.io.IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                }
                if (!java.util.Arrays.equals(bArr, PNG_CHUNK_TYPE_IEND)) {
                    if (java.util.Arrays.equals(bArr, PNG_CHUNK_TYPE_EXIF)) {
                        byte[] bArr2 = new byte[readInt];
                        if (byteOrderedDataInputStream.read(bArr2) != readInt) {
                            throw new java.io.IOException("Failed to read given length for given PNG chunk type: " + android.media.ExifInterfaceUtils.byteArrayToHexString(bArr));
                        }
                        int readInt2 = byteOrderedDataInputStream.readInt();
                        java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
                        crc32.update(bArr);
                        crc32.update(bArr2);
                        if (((int) crc32.getValue()) != readInt2) {
                            throw new java.io.IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + readInt2 + ", calculated CRC value: " + crc32.getValue());
                        }
                        this.mExifOffset = i2;
                        readExifSegment(bArr2, 0);
                        validateImages();
                        return;
                    }
                    int i3 = readInt + 4;
                    byteOrderedDataInputStream.skipBytes(i3);
                    length = i2 + i3;
                } else {
                    return;
                }
            } catch (java.io.EOFException e) {
                throw new java.io.IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void getWebpAttributes(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        if (DEBUG) {
            android.util.Log.d(TAG, "getWebpAttributes starting with: " + byteOrderedDataInputStream);
        }
        byteOrderedDataInputStream.setByteOrder(java.nio.ByteOrder.LITTLE_ENDIAN);
        byteOrderedDataInputStream.skipBytes(WEBP_SIGNATURE_1.length);
        int readInt = byteOrderedDataInputStream.readInt() + 8;
        int skipBytes = byteOrderedDataInputStream.skipBytes(WEBP_SIGNATURE_2.length) + 8;
        while (true) {
            try {
                byte[] bArr = new byte[4];
                if (byteOrderedDataInputStream.read(bArr) != 4) {
                    throw new java.io.IOException("Encountered invalid length while parsing WebP chunktype");
                }
                int readInt2 = byteOrderedDataInputStream.readInt();
                int i = skipBytes + 4 + 4;
                if (java.util.Arrays.equals(WEBP_CHUNK_TYPE_EXIF, bArr)) {
                    byte[] bArr2 = new byte[readInt2];
                    if (byteOrderedDataInputStream.read(bArr2) != readInt2) {
                        throw new java.io.IOException("Failed to read given length for given PNG chunk type: " + android.media.ExifInterfaceUtils.byteArrayToHexString(bArr));
                    }
                    this.mExifOffset = i;
                    readExifSegment(bArr2, 0);
                    this.mExifOffset = i;
                    return;
                }
                if (readInt2 % 2 == 1) {
                    readInt2++;
                }
                int i2 = i + readInt2;
                if (i2 == readInt) {
                    return;
                }
                if (i2 > readInt) {
                    throw new java.io.IOException("Encountered WebP file with invalid chunk size");
                }
                int skipBytes2 = byteOrderedDataInputStream.skipBytes(readInt2);
                if (skipBytes2 != readInt2) {
                    throw new java.io.IOException("Encountered WebP file with invalid chunk size");
                }
                skipBytes = i + skipBytes2;
            } catch (java.io.EOFException e) {
                throw new java.io.IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private void saveJpegAttributes(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute;
        if (DEBUG) {
            android.util.Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream = new android.media.ExifInterface.ByteOrderedDataOutputStream(outputStream, java.nio.ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() != -1) {
            throw new java.io.IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-1);
        if (dataInputStream.readByte() != -40) {
            throw new java.io.IOException("Invalid marker");
        }
        byteOrderedDataOutputStream.writeByte(-40);
        if (getAttribute(TAG_XMP) != null && this.mXmpIsFromSeparateMarker) {
            exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[0].remove(TAG_XMP);
        } else {
            exifAttribute = null;
        }
        byteOrderedDataOutputStream.writeByte(-1);
        byteOrderedDataOutputStream.writeByte(-31);
        writeExifSegment(byteOrderedDataOutputStream);
        if (exifAttribute != null) {
            this.mAttributes[0].put(TAG_XMP, exifAttribute);
        }
        byte[] bArr = new byte[4096];
        while (dataInputStream.readByte() == -1) {
            byte readByte = dataInputStream.readByte();
            switch (readByte) {
                case -39:
                case -38:
                    byteOrderedDataOutputStream.writeByte(-1);
                    byteOrderedDataOutputStream.writeByte(readByte);
                    android.media.ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream);
                    return;
                case -31:
                    int readUnsignedShort = dataInputStream.readUnsignedShort() - 2;
                    if (readUnsignedShort < 0) {
                        throw new java.io.IOException("Invalid length");
                    }
                    byte[] bArr2 = new byte[6];
                    if (readUnsignedShort >= 6) {
                        if (dataInputStream.read(bArr2) != 6) {
                            throw new java.io.IOException("Invalid exif");
                        }
                        if (java.util.Arrays.equals(bArr2, IDENTIFIER_EXIF_APP1)) {
                            int i = readUnsignedShort - 6;
                            if (dataInputStream.skipBytes(i) != i) {
                                throw new java.io.IOException("Invalid length");
                            }
                            break;
                        }
                    }
                    byteOrderedDataOutputStream.writeByte(-1);
                    byteOrderedDataOutputStream.writeByte(readByte);
                    byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort + 2);
                    if (readUnsignedShort >= 6) {
                        readUnsignedShort -= 6;
                        byteOrderedDataOutputStream.write(bArr2);
                    }
                    while (readUnsignedShort > 0) {
                        int read = dataInputStream.read(bArr, 0, java.lang.Math.min(readUnsignedShort, 4096));
                        if (read >= 0) {
                            byteOrderedDataOutputStream.write(bArr, 0, read);
                            readUnsignedShort -= read;
                        }
                    }
                    break;
                default:
                    byteOrderedDataOutputStream.writeByte(-1);
                    byteOrderedDataOutputStream.writeByte(readByte);
                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                    byteOrderedDataOutputStream.writeUnsignedShort(readUnsignedShort2);
                    int i2 = readUnsignedShort2 - 2;
                    if (i2 < 0) {
                        throw new java.io.IOException("Invalid length");
                    }
                    while (i2 > 0) {
                        int read2 = dataInputStream.read(bArr, 0, java.lang.Math.min(i2, 4096));
                        if (read2 >= 0) {
                            byteOrderedDataOutputStream.write(bArr, 0, read2);
                            i2 -= read2;
                        }
                    }
                    break;
                    break;
            }
        }
        throw new java.io.IOException("Invalid marker");
    }

    private void savePngAttributes(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        if (DEBUG) {
            android.util.Log.d(TAG, "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        java.io.DataInputStream dataInputStream = new java.io.DataInputStream(inputStream);
        android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream = new android.media.ExifInterface.ByteOrderedDataOutputStream(outputStream, java.nio.ByteOrder.BIG_ENDIAN);
        android.media.ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, PNG_SIGNATURE.length);
        if (this.mExifOffset == 0) {
            int readInt = dataInputStream.readInt();
            byteOrderedDataOutputStream.writeInt(readInt);
            android.media.ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, readInt + 4 + 4);
        } else {
            android.media.ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream, ((this.mExifOffset - PNG_SIGNATURE.length) - 4) - 4);
            dataInputStream.skipBytes(dataInputStream.readInt() + 4 + 4);
        }
        java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
        try {
            android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new android.media.ExifInterface.ByteOrderedDataOutputStream(byteArrayOutputStream, java.nio.ByteOrder.BIG_ENDIAN);
            writeExifSegment(byteOrderedDataOutputStream2);
            byte[] byteArray = ((java.io.ByteArrayOutputStream) byteOrderedDataOutputStream2.mOutputStream).toByteArray();
            byteOrderedDataOutputStream.write(byteArray);
            java.util.zip.CRC32 crc32 = new java.util.zip.CRC32();
            crc32.update(byteArray, 4, byteArray.length - 4);
            byteOrderedDataOutputStream.writeInt((int) crc32.getValue());
            byteArrayOutputStream.close();
            android.media.ExifInterfaceUtils.copy(dataInputStream, byteOrderedDataOutputStream);
        } catch (java.lang.Throwable th) {
            try {
                byteArrayOutputStream.close();
            } catch (java.lang.Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void saveWebpAttributes(java.io.InputStream inputStream, java.io.OutputStream outputStream) throws java.io.IOException {
        int i;
        int i2;
        int i3;
        if (DEBUG) {
            android.util.Log.d(TAG, "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(inputStream, java.nio.ByteOrder.LITTLE_ENDIAN);
        android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream = new android.media.ExifInterface.ByteOrderedDataOutputStream(outputStream, java.nio.ByteOrder.LITTLE_ENDIAN);
        android.media.ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, WEBP_SIGNATURE_1.length);
        byteOrderedDataInputStream.skipBytes(WEBP_SIGNATURE_2.length + 4);
        java.io.ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            try {
                java.io.ByteArrayOutputStream byteArrayOutputStream2 = new java.io.ByteArrayOutputStream();
                try {
                    android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream2 = new android.media.ExifInterface.ByteOrderedDataOutputStream(byteArrayOutputStream2, java.nio.ByteOrder.LITTLE_ENDIAN);
                    if (this.mExifOffset != 0) {
                        android.media.ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, ((this.mExifOffset - ((WEBP_SIGNATURE_1.length + 4) + WEBP_SIGNATURE_2.length)) - 4) - 4);
                        byteOrderedDataInputStream.skipBytes(4);
                        int readInt = byteOrderedDataInputStream.readInt();
                        if (readInt % 2 != 0) {
                            readInt++;
                        }
                        byteOrderedDataInputStream.skipBytes(readInt);
                        writeExifSegment(byteOrderedDataOutputStream2);
                    } else {
                        byte[] bArr = new byte[4];
                        if (byteOrderedDataInputStream.read(bArr) != 4) {
                            throw new java.io.IOException("Encountered invalid length while parsing WebP chunk type");
                        }
                        boolean z = true;
                        if (java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8X)) {
                            int readInt2 = byteOrderedDataInputStream.readInt();
                            byte[] bArr2 = new byte[readInt2 % 2 == 1 ? readInt2 + 1 : readInt2];
                            byteOrderedDataInputStream.read(bArr2);
                            bArr2[0] = (byte) (8 | bArr2[0]);
                            if (((bArr2[0] >> 1) & 1) != 1) {
                                z = false;
                            }
                            byteOrderedDataOutputStream2.write(WEBP_CHUNK_TYPE_VP8X);
                            byteOrderedDataOutputStream2.writeInt(readInt2);
                            byteOrderedDataOutputStream2.write(bArr2);
                            if (z) {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_ANIM, null);
                                while (true) {
                                    byte[] bArr3 = new byte[4];
                                    inputStream.read(bArr3);
                                    if (!java.util.Arrays.equals(bArr3, WEBP_CHUNK_TYPE_ANMF)) {
                                        break;
                                    } else {
                                        copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream2, bArr3);
                                    }
                                }
                                writeExifSegment(byteOrderedDataOutputStream2);
                            } else {
                                copyChunksUpToGivenChunkType(byteOrderedDataInputStream, byteOrderedDataOutputStream2, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                                writeExifSegment(byteOrderedDataOutputStream2);
                            }
                        } else if (java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8) || java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8L)) {
                            int readInt3 = byteOrderedDataInputStream.readInt();
                            int i4 = readInt3 % 2 == 1 ? readInt3 + 1 : readInt3;
                            byte[] bArr4 = new byte[3];
                            if (java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8)) {
                                byteOrderedDataInputStream.read(bArr4);
                                byte[] bArr5 = new byte[3];
                                if (byteOrderedDataInputStream.read(bArr5) != 3 || !java.util.Arrays.equals(WEBP_VP8_SIGNATURE, bArr5)) {
                                    throw new java.io.IOException("Encountered error while checking VP8 signature");
                                }
                                i = byteOrderedDataInputStream.readInt();
                                i4 -= 10;
                                i3 = (i << 2) >> 18;
                                i2 = (i << 18) >> 18;
                                z = false;
                            } else if (!java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8L)) {
                                i = 0;
                                z = false;
                                i2 = 0;
                                i3 = 0;
                            } else {
                                if (byteOrderedDataInputStream.readByte() != 47) {
                                    throw new java.io.IOException("Encountered error while checking VP8L signature");
                                }
                                i = byteOrderedDataInputStream.readInt();
                                i2 = ((i << 18) >> 18) + 1;
                                i3 = ((i << 4) >> 18) + 1;
                                if ((i & 268435456) == 0) {
                                    z = false;
                                }
                                i4 -= 5;
                            }
                            byteOrderedDataOutputStream2.write(WEBP_CHUNK_TYPE_VP8X);
                            byteOrderedDataOutputStream2.writeInt(10);
                            byte[] bArr6 = new byte[10];
                            if (z) {
                                bArr6[0] = (byte) (bArr6[0] | 16);
                            }
                            bArr6[0] = (byte) (bArr6[0] | 8);
                            int i5 = i2 - 1;
                            int i6 = i3 - 1;
                            bArr6[4] = (byte) i5;
                            bArr6[5] = (byte) (i5 >> 8);
                            bArr6[6] = (byte) (i5 >> 16);
                            bArr6[7] = (byte) i6;
                            bArr6[8] = (byte) (i6 >> 8);
                            bArr6[9] = (byte) (i6 >> 16);
                            byteOrderedDataOutputStream2.write(bArr6);
                            byteOrderedDataOutputStream2.write(bArr);
                            byteOrderedDataOutputStream2.writeInt(readInt3);
                            if (java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8)) {
                                byteOrderedDataOutputStream2.write(bArr4);
                                byteOrderedDataOutputStream2.write(WEBP_VP8_SIGNATURE);
                                byteOrderedDataOutputStream2.writeInt(i);
                            } else if (java.util.Arrays.equals(bArr, WEBP_CHUNK_TYPE_VP8L)) {
                                byteOrderedDataOutputStream2.write(47);
                                byteOrderedDataOutputStream2.writeInt(i);
                            }
                            android.media.ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2, i4);
                            writeExifSegment(byteOrderedDataOutputStream2);
                        }
                    }
                    android.media.ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream2);
                    byteOrderedDataOutputStream.writeInt(byteArrayOutputStream2.size() + WEBP_SIGNATURE_2.length);
                    byteOrderedDataOutputStream.write(WEBP_SIGNATURE_2);
                    byteArrayOutputStream2.writeTo(byteOrderedDataOutputStream);
                    android.media.ExifInterfaceUtils.closeQuietly(byteArrayOutputStream2);
                } catch (java.lang.Exception e) {
                    e = e;
                    throw new java.io.IOException("Failed to save WebP file", e);
                } catch (java.lang.Throwable th) {
                    th = th;
                    byteArrayOutputStream = byteArrayOutputStream2;
                    android.media.ExifInterfaceUtils.closeQuietly(byteArrayOutputStream);
                    throw th;
                }
            } catch (java.lang.Exception e2) {
                e = e2;
            }
        } catch (java.lang.Throwable th2) {
            th = th2;
        }
    }

    private void copyChunksUpToGivenChunkType(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr, byte[] bArr2) throws java.io.IOException {
        while (true) {
            byte[] bArr3 = new byte[4];
            if (byteOrderedDataInputStream.read(bArr3) != 4) {
                throw new java.io.IOException("Encountered invalid length while copying WebP chunks up tochunk type " + new java.lang.String(bArr, ASCII) + (bArr2 == null ? "" : " or " + new java.lang.String(bArr2, ASCII)));
            }
            copyWebPChunk(byteOrderedDataInputStream, byteOrderedDataOutputStream, bArr3);
            if (!java.util.Arrays.equals(bArr3, bArr)) {
                if (bArr2 != null && java.util.Arrays.equals(bArr3, bArr2)) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void copyWebPChunk(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream, byte[] bArr) throws java.io.IOException {
        int readInt = byteOrderedDataInputStream.readInt();
        byteOrderedDataOutputStream.write(bArr);
        byteOrderedDataOutputStream.writeInt(readInt);
        if (readInt % 2 == 1) {
            readInt++;
        }
        android.media.ExifInterfaceUtils.copy(byteOrderedDataInputStream, byteOrderedDataOutputStream, readInt);
    }

    private void readExifSegment(byte[] bArr, int i) throws java.io.IOException {
        android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream = new android.media.ExifInterface.ByteOrderedDataInputStream(bArr);
        parseTiffHeaders(byteOrderedDataInputStream, bArr.length);
        readImageFileDirectory(byteOrderedDataInputStream, i);
    }

    private void addDefaultValuesForCompatibility() {
        java.lang.String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, android.media.ExifInterface.ExifAttribute.createString(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, android.media.ExifInterface.ExifAttribute.createUShort(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    private java.nio.ByteOrder readByteOrder(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        short readShort = byteOrderedDataInputStream.readShort();
        switch (readShort) {
            case 18761:
                if (DEBUG) {
                    android.util.Log.d(TAG, "readExifSegment: Byte Align II");
                }
                return java.nio.ByteOrder.LITTLE_ENDIAN;
            case 19789:
                if (DEBUG) {
                    android.util.Log.d(TAG, "readExifSegment: Byte Align MM");
                }
                return java.nio.ByteOrder.BIG_ENDIAN;
            default:
                throw new java.io.IOException("Invalid byte order: " + java.lang.Integer.toHexString(readShort));
        }
    }

    private void parseTiffHeaders(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws java.io.IOException {
        this.mExifByteOrder = readByteOrder(byteOrderedDataInputStream);
        byteOrderedDataInputStream.setByteOrder(this.mExifByteOrder);
        int readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
        if (this.mMimeType != 7 && this.mMimeType != 10 && readUnsignedShort != 42) {
            throw new java.io.IOException("Invalid start code: " + java.lang.Integer.toHexString(readUnsignedShort));
        }
        int readInt = byteOrderedDataInputStream.readInt();
        if (readInt < 8 || readInt >= i) {
            throw new java.io.IOException("Invalid first Ifd offset: " + readInt);
        }
        int i2 = readInt - 8;
        if (i2 > 0 && byteOrderedDataInputStream.skipBytes(i2) != i2) {
            throw new java.io.IOException("Couldn't jump to first Ifd: " + i2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0128  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void readImageFileDirectory(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws java.io.IOException {
        short s;
        int i2;
        long j;
        boolean z;
        short s2;
        java.lang.String str;
        int i3;
        int i4;
        long readUnsignedShort;
        int i5 = i;
        this.mHandledIfdOffsets.add(java.lang.Integer.valueOf(byteOrderedDataInputStream.mPosition));
        if (byteOrderedDataInputStream.mPosition + 2 > byteOrderedDataInputStream.mLength) {
            return;
        }
        short readShort = byteOrderedDataInputStream.readShort();
        if (byteOrderedDataInputStream.mPosition + (readShort * 12) > byteOrderedDataInputStream.mLength || readShort <= 0) {
            return;
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "numberOfDirectoryEntry: " + ((int) readShort));
        }
        short s3 = 0;
        while (s3 < readShort) {
            int readUnsignedShort2 = byteOrderedDataInputStream.readUnsignedShort();
            int readUnsignedShort3 = byteOrderedDataInputStream.readUnsignedShort();
            int readInt = byteOrderedDataInputStream.readInt();
            long peek = byteOrderedDataInputStream.peek() + 4;
            android.media.ExifInterface.ExifTag exifTag = (android.media.ExifInterface.ExifTag) sExifTagMapsForReading[i5].get(java.lang.Integer.valueOf(readUnsignedShort2));
            if (!DEBUG) {
                s = readShort;
            } else {
                s = readShort;
                android.util.Log.d(TAG, java.lang.String.format("ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d", java.lang.Integer.valueOf(i), java.lang.Integer.valueOf(readUnsignedShort2), exifTag != null ? exifTag.name : null, java.lang.Integer.valueOf(readUnsignedShort3), java.lang.Integer.valueOf(readInt)));
            }
            if (exifTag == null) {
                if (!DEBUG) {
                    i2 = readUnsignedShort2;
                } else {
                    android.util.Log.d(TAG, "Skip the tag entry since tag number is not defined: " + readUnsignedShort2);
                    i2 = readUnsignedShort2;
                }
            } else {
                if (readUnsignedShort3 <= 0) {
                    i2 = readUnsignedShort2;
                } else if (readUnsignedShort3 >= IFD_FORMAT_BYTES_PER_FORMAT.length) {
                    i2 = readUnsignedShort2;
                } else {
                    i2 = readUnsignedShort2;
                    j = readInt * IFD_FORMAT_BYTES_PER_FORMAT[readUnsignedShort3];
                    if (j < 0 || j > 2147483647L) {
                        if (DEBUG) {
                            android.util.Log.d(TAG, "Skip the tag entry since the number of components is invalid: " + readInt);
                        }
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        byteOrderedDataInputStream.seek(peek);
                        s2 = s3;
                    } else {
                        if (j <= 4) {
                            s2 = s3;
                            str = TAG_COMPRESSION;
                            i3 = readUnsignedShort3;
                            i4 = readInt;
                        } else {
                            int readInt2 = byteOrderedDataInputStream.readInt();
                            if (!DEBUG) {
                                s2 = s3;
                            } else {
                                s2 = s3;
                                android.util.Log.d(TAG, "seek to data offset: " + readInt2);
                            }
                            if (this.mMimeType == 7) {
                                if (exifTag.name == TAG_MAKER_NOTE) {
                                    this.mOrfMakerNoteOffset = readInt2;
                                    i3 = readUnsignedShort3;
                                    i4 = readInt;
                                } else if (i5 != 6 || exifTag.name != TAG_ORF_THUMBNAIL_IMAGE) {
                                    i3 = readUnsignedShort3;
                                    i4 = readInt;
                                } else {
                                    this.mOrfThumbnailOffset = readInt2;
                                    this.mOrfThumbnailLength = readInt;
                                    android.media.ExifInterface.ExifAttribute createUShort = android.media.ExifInterface.ExifAttribute.createUShort(6, this.mExifByteOrder);
                                    i3 = readUnsignedShort3;
                                    i4 = readInt;
                                    android.media.ExifInterface.ExifAttribute createULong = android.media.ExifInterface.ExifAttribute.createULong(this.mOrfThumbnailOffset, this.mExifByteOrder);
                                    android.media.ExifInterface.ExifAttribute createULong2 = android.media.ExifInterface.ExifAttribute.createULong(this.mOrfThumbnailLength, this.mExifByteOrder);
                                    this.mAttributes[4].put(TAG_COMPRESSION, createUShort);
                                    this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, createULong);
                                    this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, createULong2);
                                }
                            } else {
                                i3 = readUnsignedShort3;
                                i4 = readInt;
                                if (this.mMimeType == 10 && exifTag.name == TAG_RW2_JPG_FROM_RAW) {
                                    this.mRw2JpgFromRawOffset = readInt2;
                                }
                            }
                            long j2 = readInt2;
                            long j3 = j2 + j;
                            int i6 = byteOrderedDataInputStream.mLength;
                            str = TAG_COMPRESSION;
                            if (j3 <= i6) {
                                byteOrderedDataInputStream.seek(j2);
                            } else {
                                if (DEBUG) {
                                    android.util.Log.d(TAG, "Skip the tag entry since data offset is invalid: " + readInt2);
                                }
                                byteOrderedDataInputStream.seek(peek);
                            }
                        }
                        java.lang.Integer num = sExifPointerTagMap.get(java.lang.Integer.valueOf(i2));
                        if (DEBUG) {
                            android.util.Log.d(TAG, "nextIfdType: " + num + " byteCount: " + j);
                        }
                        if (num == null) {
                            int peek2 = byteOrderedDataInputStream.peek() + this.mExifOffset;
                            byte[] bArr = new byte[(int) j];
                            byteOrderedDataInputStream.readFully(bArr);
                            android.media.ExifInterface.ExifAttribute exifAttribute = new android.media.ExifInterface.ExifAttribute(i3, i4, peek2, bArr);
                            this.mAttributes[i].put(exifTag.name, exifAttribute);
                            if (exifTag.name == TAG_DNG_VERSION) {
                                this.mMimeType = 3;
                            }
                            if (((exifTag.name == TAG_MAKE || exifTag.name == TAG_MODEL) && exifAttribute.getStringValue(this.mExifByteOrder).contains(PEF_SIGNATURE)) || (exifTag.name == str && exifAttribute.getIntValue(this.mExifByteOrder) == 65535)) {
                                this.mMimeType = 8;
                            }
                            if (byteOrderedDataInputStream.peek() != peek) {
                                byteOrderedDataInputStream.seek(peek);
                            }
                        } else {
                            switch (i3) {
                                case 3:
                                    readUnsignedShort = byteOrderedDataInputStream.readUnsignedShort();
                                    break;
                                case 4:
                                    readUnsignedShort = byteOrderedDataInputStream.readUnsignedInt();
                                    break;
                                case 8:
                                    readUnsignedShort = byteOrderedDataInputStream.readShort();
                                    break;
                                case 9:
                                case 13:
                                    readUnsignedShort = byteOrderedDataInputStream.readInt();
                                    break;
                                default:
                                    readUnsignedShort = -1;
                                    break;
                            }
                            if (DEBUG) {
                                android.util.Log.d(TAG, java.lang.String.format("Offset: %d, tagName: %s", java.lang.Long.valueOf(readUnsignedShort), exifTag.name));
                            }
                            if (readUnsignedShort > 0 && readUnsignedShort < byteOrderedDataInputStream.mLength) {
                                if (!this.mHandledIfdOffsets.contains(java.lang.Integer.valueOf((int) readUnsignedShort))) {
                                    byteOrderedDataInputStream.seek(readUnsignedShort);
                                    readImageFileDirectory(byteOrderedDataInputStream, num.intValue());
                                } else if (DEBUG) {
                                    android.util.Log.d(TAG, "Skip jump into the IFD since it has already been read: IfdType " + num + " (at " + readUnsignedShort + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
                                }
                            } else if (DEBUG) {
                                android.util.Log.d(TAG, "Skip jump into the IFD since its offset is invalid: " + readUnsignedShort);
                            }
                            byteOrderedDataInputStream.seek(peek);
                        }
                    }
                    s3 = (short) (s2 + 1);
                    i5 = i;
                    readShort = s;
                }
                if (DEBUG) {
                    android.util.Log.d(TAG, "Skip the tag entry since data format is invalid: " + readUnsignedShort3);
                }
            }
            z = false;
            j = 0;
            if (z) {
            }
            s3 = (short) (s2 + 1);
            i5 = i;
            readShort = s;
        }
        if (byteOrderedDataInputStream.peek() + 4 <= byteOrderedDataInputStream.mLength) {
            int readInt3 = byteOrderedDataInputStream.readInt();
            if (DEBUG) {
                android.util.Log.d(TAG, java.lang.String.format("nextIfdOffset: %d", java.lang.Integer.valueOf(readInt3)));
            }
            long j4 = readInt3;
            if (j4 > 0 && readInt3 < byteOrderedDataInputStream.mLength) {
                if (!this.mHandledIfdOffsets.contains(java.lang.Integer.valueOf(readInt3))) {
                    byteOrderedDataInputStream.seek(j4);
                    if (this.mAttributes[4].isEmpty()) {
                        readImageFileDirectory(byteOrderedDataInputStream, 4);
                        return;
                    } else {
                        if (this.mAttributes[5].isEmpty()) {
                            readImageFileDirectory(byteOrderedDataInputStream, 5);
                            return;
                        }
                        return;
                    }
                }
                if (DEBUG) {
                    android.util.Log.d(TAG, "Stop reading file since re-reading an IFD may cause an infinite loop: " + readInt3);
                    return;
                }
                return;
            }
            if (DEBUG) {
                android.util.Log.d(TAG, "Stop reading file since a wrong offset may cause an infinite loop: " + readInt3);
            }
        }
    }

    private void retrieveJpegImageSize(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute;
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_LENGTH);
        android.media.ExifInterface.ExifAttribute exifAttribute3 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_WIDTH);
        if ((exifAttribute2 == null || exifAttribute3 == null) && (exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            getJpegAttributes(byteOrderedDataInputStream, exifAttribute.getIntValue(this.mExifByteOrder), i);
        }
    }

    private void setThumbnailData(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream) throws java.io.IOException {
        java.util.HashMap hashMap = this.mAttributes[4];
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_COMPRESSION);
        if (exifAttribute != null) {
            this.mThumbnailCompression = exifAttribute.getIntValue(this.mExifByteOrder);
            switch (this.mThumbnailCompression) {
                case 1:
                case 7:
                    if (isSupportedDataType(hashMap)) {
                        handleThumbnailFromStrips(byteOrderedDataInputStream, hashMap);
                        break;
                    }
                    break;
                case 6:
                    handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                    break;
            }
        }
        handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
    }

    private void handleThumbnailFromJfif(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, java.util.HashMap hashMap) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute != null && exifAttribute2 != null) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            if (this.mMimeType == 7) {
                intValue += this.mOrfMakerNoteOffset;
            }
            int min = java.lang.Math.min(intValue2, byteOrderedDataInputStream.getLength() - intValue);
            if (intValue > 0 && min > 0) {
                this.mHasThumbnail = true;
                this.mThumbnailOffset = this.mExifOffset + intValue;
                this.mThumbnailLength = min;
                this.mThumbnailCompression = 6;
                if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                    byte[] bArr = new byte[this.mThumbnailLength];
                    byteOrderedDataInputStream.seek(this.mThumbnailOffset);
                    byteOrderedDataInputStream.readFully(bArr);
                    this.mThumbnailBytes = bArr;
                }
            }
            if (DEBUG) {
                android.util.Log.d(TAG, "Setting thumbnail attributes with offset: " + intValue + ", length: " + min);
            }
        }
    }

    private void handleThumbnailFromStrips(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, java.util.HashMap hashMap) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_STRIP_OFFSETS);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_STRIP_BYTE_COUNTS);
        if (exifAttribute != null && exifAttribute2 != null) {
            long[] convertToLongArray = android.media.ExifInterfaceUtils.convertToLongArray(exifAttribute.getValue(this.mExifByteOrder));
            long[] convertToLongArray2 = android.media.ExifInterfaceUtils.convertToLongArray(exifAttribute2.getValue(this.mExifByteOrder));
            if (convertToLongArray == null || convertToLongArray.length == 0) {
                android.util.Log.w(TAG, "stripOffsets should not be null or have zero length.");
                return;
            }
            if (convertToLongArray2 == null || convertToLongArray2.length == 0) {
                android.util.Log.w(TAG, "stripByteCounts should not be null or have zero length.");
                return;
            }
            if (convertToLongArray.length != convertToLongArray2.length) {
                android.util.Log.w(TAG, "stripOffsets and stripByteCounts should have same length.");
                return;
            }
            int sum = (int) java.util.Arrays.stream(convertToLongArray2).sum();
            byte[] bArr = new byte[sum];
            this.mAreThumbnailStripsConsecutive = true;
            this.mHasThumbnailStrips = true;
            this.mHasThumbnail = true;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < convertToLongArray.length; i3++) {
                int i4 = (int) convertToLongArray[i3];
                int i5 = (int) convertToLongArray2[i3];
                if (i3 < convertToLongArray.length - 1 && i4 + i5 != convertToLongArray[i3 + 1]) {
                    this.mAreThumbnailStripsConsecutive = false;
                }
                int i6 = i4 - i;
                if (i6 < 0) {
                    android.util.Log.d(TAG, "Invalid strip offset value");
                }
                byteOrderedDataInputStream.seek(i6);
                int i7 = i + i6;
                byte[] bArr2 = new byte[i5];
                byteOrderedDataInputStream.read(bArr2);
                i = i7 + i5;
                java.lang.System.arraycopy(bArr2, 0, bArr, i2, i5);
                i2 += i5;
            }
            this.mThumbnailBytes = bArr;
            if (this.mAreThumbnailStripsConsecutive) {
                this.mThumbnailOffset = ((int) convertToLongArray[0]) + this.mExifOffset;
                this.mThumbnailLength = sum;
            }
        }
    }

    private boolean isSupportedDataType(java.util.HashMap hashMap) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute;
        int intValue;
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_BITS_PER_SAMPLE);
        if (exifAttribute2 != null) {
            int[] iArr = (int[]) exifAttribute2.getValue(this.mExifByteOrder);
            if (java.util.Arrays.equals(BITS_PER_SAMPLE_RGB, iArr)) {
                return true;
            }
            if (this.mMimeType == 3 && (exifAttribute = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((intValue = exifAttribute.getIntValue(this.mExifByteOrder)) == 1 && java.util.Arrays.equals(iArr, BITS_PER_SAMPLE_GREYSCALE_2)) || (intValue == 6 && java.util.Arrays.equals(iArr, BITS_PER_SAMPLE_RGB)))) {
                return true;
            }
        }
        if (DEBUG) {
            android.util.Log.d(TAG, "Unsupported data type value");
            return false;
        }
        return false;
    }

    private boolean isThumbnail(java.util.HashMap hashMap) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_IMAGE_LENGTH);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) hashMap.get(TAG_IMAGE_WIDTH);
        if (exifAttribute != null && exifAttribute2 != null) {
            int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            if (intValue <= 512 && intValue2 <= 512) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void validateImages() throws java.io.IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (exifAttribute != null && exifAttribute2 != null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, exifAttribute2);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            this.mAttributes[4] = this.mAttributes[5];
            this.mAttributes[5] = new java.util.HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            android.util.Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(4, TAG_ORIENTATION, TAG_THUMBNAIL_ORIENTATION);
        replaceInvalidTags(4, TAG_IMAGE_LENGTH, TAG_THUMBNAIL_IMAGE_LENGTH);
        replaceInvalidTags(4, TAG_IMAGE_WIDTH, TAG_THUMBNAIL_IMAGE_WIDTH);
    }

    private void updateImageSizeValues(android.media.ExifInterface.ByteOrderedDataInputStream byteOrderedDataInputStream, int i) throws java.io.IOException {
        android.media.ExifInterface.ExifAttribute createUShort;
        android.media.ExifInterface.ExifAttribute createUShort2;
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_DEFAULT_CROP_SIZE);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_TOP_BORDER);
        android.media.ExifInterface.ExifAttribute exifAttribute3 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_LEFT_BORDER);
        android.media.ExifInterface.ExifAttribute exifAttribute4 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        android.media.ExifInterface.ExifAttribute exifAttribute5 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (exifAttribute != null) {
            if (exifAttribute.format == 5) {
                android.media.ExifInterface.Rational[] rationalArr = (android.media.ExifInterface.Rational[]) exifAttribute.getValue(this.mExifByteOrder);
                createUShort = android.media.ExifInterface.ExifAttribute.createURational(rationalArr[0], this.mExifByteOrder);
                createUShort2 = android.media.ExifInterface.ExifAttribute.createURational(rationalArr[1], this.mExifByteOrder);
            } else {
                int[] iArr = (int[]) exifAttribute.getValue(this.mExifByteOrder);
                createUShort = android.media.ExifInterface.ExifAttribute.createUShort(iArr[0], this.mExifByteOrder);
                createUShort2 = android.media.ExifInterface.ExifAttribute.createUShort(iArr[1], this.mExifByteOrder);
            }
            this.mAttributes[i].put(TAG_IMAGE_WIDTH, createUShort);
            this.mAttributes[i].put(TAG_IMAGE_LENGTH, createUShort2);
            return;
        }
        if (exifAttribute2 != null && exifAttribute3 != null && exifAttribute4 != null && exifAttribute5 != null) {
            int intValue = exifAttribute2.getIntValue(this.mExifByteOrder);
            int intValue2 = exifAttribute4.getIntValue(this.mExifByteOrder);
            int intValue3 = exifAttribute5.getIntValue(this.mExifByteOrder);
            int intValue4 = exifAttribute3.getIntValue(this.mExifByteOrder);
            if (intValue2 > intValue && intValue3 > intValue4) {
                android.media.ExifInterface.ExifAttribute createUShort3 = android.media.ExifInterface.ExifAttribute.createUShort(intValue2 - intValue, this.mExifByteOrder);
                android.media.ExifInterface.ExifAttribute createUShort4 = android.media.ExifInterface.ExifAttribute.createUShort(intValue3 - intValue4, this.mExifByteOrder);
                this.mAttributes[i].put(TAG_IMAGE_LENGTH, createUShort3);
                this.mAttributes[i].put(TAG_IMAGE_WIDTH, createUShort4);
                return;
            }
            return;
        }
        retrieveJpegImageSize(byteOrderedDataInputStream, i);
    }

    private int writeExifSegment(android.media.ExifInterface.ByteOrderedDataOutputStream byteOrderedDataOutputStream) throws java.io.IOException {
        int[] iArr = new int[EXIF_TAGS.length];
        int[] iArr2 = new int[EXIF_TAGS.length];
        for (android.media.ExifInterface.ExifTag exifTag : EXIF_POINTER_TAGS) {
            removeAttribute(exifTag.name);
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                removeAttribute(TAG_STRIP_OFFSETS);
                removeAttribute(TAG_STRIP_BYTE_COUNTS);
            } else {
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT);
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            }
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            for (java.lang.Object obj : this.mAttributes[i].entrySet().toArray()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[i].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, android.media.ExifInterface.ExifAttribute.createUShort(0, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_STRIP_BYTE_COUNTS, android.media.ExifInterface.ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, android.media.ExifInterface.ExifAttribute.createULong(0L, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, android.media.ExifInterface.ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        for (int i2 = 0; i2 < EXIF_TAGS.length; i2++) {
            java.util.Iterator it = this.mAttributes[i2].entrySet().iterator();
            int i3 = 0;
            while (it.hasNext()) {
                int size = ((android.media.ExifInterface.ExifAttribute) ((java.util.Map.Entry) it.next()).getValue()).size();
                if (size > 4) {
                    i3 += size;
                }
            }
            iArr2[i2] = iArr2[i2] + i3;
        }
        int i4 = 8;
        for (int i5 = 0; i5 < EXIF_TAGS.length; i5++) {
            if (!this.mAttributes[i5].isEmpty()) {
                iArr[i5] = i4;
                i4 += (this.mAttributes[i5].size() * 12) + 2 + 4 + iArr2[i5];
            }
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, android.media.ExifInterface.ExifAttribute.createUShort(i4, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, android.media.ExifInterface.ExifAttribute.createULong(i4, this.mExifByteOrder));
            }
            this.mThumbnailOffset = this.mExifOffset + i4;
            i4 += this.mThumbnailLength;
        }
        if (this.mMimeType == 4) {
            i4 += 8;
        }
        if (DEBUG) {
            for (int i6 = 0; i6 < EXIF_TAGS.length; i6++) {
                android.util.Log.d(TAG, java.lang.String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", java.lang.Integer.valueOf(i6), java.lang.Integer.valueOf(iArr[i6]), java.lang.Integer.valueOf(this.mAttributes[i6].size()), java.lang.Integer.valueOf(iArr2[i6]), java.lang.Integer.valueOf(i4)));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, android.media.ExifInterface.ExifAttribute.createULong(iArr[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, android.media.ExifInterface.ExifAttribute.createULong(iArr[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, android.media.ExifInterface.ExifAttribute.createULong(iArr[3], this.mExifByteOrder));
        }
        switch (this.mMimeType) {
            case 4:
                byteOrderedDataOutputStream.writeUnsignedShort(i4);
                byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
                break;
            case 13:
                byteOrderedDataOutputStream.writeInt(i4);
                byteOrderedDataOutputStream.write(PNG_CHUNK_TYPE_EXIF);
                break;
            case 14:
                byteOrderedDataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
                byteOrderedDataOutputStream.writeInt(i4);
                break;
        }
        byteOrderedDataOutputStream.writeShort(this.mExifByteOrder == java.nio.ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        byteOrderedDataOutputStream.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (int i7 = 0; i7 < EXIF_TAGS.length; i7++) {
            if (!this.mAttributes[i7].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[i7].size());
                int size2 = iArr[i7] + 2 + (this.mAttributes[i7].size() * 12) + 4;
                for (java.util.Map.Entry entry2 : this.mAttributes[i7].entrySet()) {
                    int i8 = ((android.media.ExifInterface.ExifTag) sExifTagMapsForWriting[i7].get(entry2.getKey())).number;
                    android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) entry2.getValue();
                    int size3 = exifAttribute.size();
                    byteOrderedDataOutputStream.writeUnsignedShort(i8);
                    byteOrderedDataOutputStream.writeUnsignedShort(exifAttribute.format);
                    byteOrderedDataOutputStream.writeInt(exifAttribute.numberOfComponents);
                    if (size3 > 4) {
                        byteOrderedDataOutputStream.writeUnsignedInt(size2);
                        size2 += size3;
                    } else {
                        byteOrderedDataOutputStream.write(exifAttribute.bytes);
                        if (size3 < 4) {
                            while (size3 < 4) {
                                byteOrderedDataOutputStream.writeByte(0);
                                size3++;
                            }
                        }
                    }
                }
                if (i7 == 0 && !this.mAttributes[4].isEmpty()) {
                    byteOrderedDataOutputStream.writeUnsignedInt(iArr[4]);
                } else {
                    byteOrderedDataOutputStream.writeUnsignedInt(0L);
                }
                java.util.Iterator it2 = this.mAttributes[i7].entrySet().iterator();
                while (it2.hasNext()) {
                    android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) ((java.util.Map.Entry) it2.next()).getValue();
                    if (exifAttribute2.bytes.length > 4) {
                        byteOrderedDataOutputStream.write(exifAttribute2.bytes, 0, exifAttribute2.bytes.length);
                    }
                }
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && i4 % 2 == 1) {
            byteOrderedDataOutputStream.writeByte(0);
        }
        byteOrderedDataOutputStream.setByteOrder(java.nio.ByteOrder.BIG_ENDIAN);
        return i4;
    }

    private static android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat(java.lang.String str) {
        int intValue;
        int i;
        if (str.contains(",")) {
            java.lang.String[] split = str.split(",");
            android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat = guessDataFormat(split[0]);
            if (guessDataFormat.first.intValue() == 2) {
                return guessDataFormat;
            }
            for (int i2 = 1; i2 < split.length; i2++) {
                android.util.Pair<java.lang.Integer, java.lang.Integer> guessDataFormat2 = guessDataFormat(split[i2]);
                if (!java.util.Objects.equals(guessDataFormat2.first, guessDataFormat.first) && !java.util.Objects.equals(guessDataFormat2.second, guessDataFormat.first)) {
                    intValue = -1;
                } else {
                    intValue = guessDataFormat.first.intValue();
                }
                if (guessDataFormat.second.intValue() != -1 && (java.util.Objects.equals(guessDataFormat2.first, guessDataFormat.second) || java.util.Objects.equals(guessDataFormat2.second, guessDataFormat.second))) {
                    i = guessDataFormat.second.intValue();
                } else {
                    i = -1;
                }
                if (intValue == -1 && i == -1) {
                    return new android.util.Pair<>(2, -1);
                }
                if (intValue == -1) {
                    guessDataFormat = new android.util.Pair<>(java.lang.Integer.valueOf(i), -1);
                } else if (i == -1) {
                    guessDataFormat = new android.util.Pair<>(java.lang.Integer.valueOf(intValue), -1);
                }
            }
            return guessDataFormat;
        }
        if (str.contains("/")) {
            java.lang.String[] split2 = str.split("/");
            if (split2.length == 2) {
                try {
                    long parseDouble = (long) java.lang.Double.parseDouble(split2[0]);
                    long parseDouble2 = (long) java.lang.Double.parseDouble(split2[1]);
                    if (parseDouble >= 0 && parseDouble2 >= 0) {
                        if (parseDouble <= 2147483647L && parseDouble2 <= 2147483647L) {
                            return new android.util.Pair<>(10, 5);
                        }
                        return new android.util.Pair<>(5, -1);
                    }
                    return new android.util.Pair<>(10, -1);
                } catch (java.lang.NumberFormatException e) {
                }
            }
            return new android.util.Pair<>(2, -1);
        }
        try {
            java.lang.Long valueOf = java.lang.Long.valueOf(java.lang.Long.parseLong(str));
            if (valueOf.longValue() >= 0 && valueOf.longValue() <= 65535) {
                return new android.util.Pair<>(3, 4);
            }
            if (valueOf.longValue() < 0) {
                return new android.util.Pair<>(9, -1);
            }
            return new android.util.Pair<>(4, -1);
        } catch (java.lang.NumberFormatException e2) {
            try {
                java.lang.Double.parseDouble(str);
                return new android.util.Pair<>(12, -1);
            } catch (java.lang.NumberFormatException e3) {
                return new android.util.Pair<>(2, -1);
            }
        }
    }

    private static class ByteOrderedDataInputStream extends java.io.InputStream implements java.io.DataInput {
        private java.nio.ByteOrder mByteOrder;
        private java.io.DataInputStream mDataInputStream;
        private java.io.InputStream mInputStream;
        private final int mLength;
        private int mPosition;
        private static final java.nio.ByteOrder LITTLE_ENDIAN = java.nio.ByteOrder.LITTLE_ENDIAN;
        private static final java.nio.ByteOrder BIG_ENDIAN = java.nio.ByteOrder.BIG_ENDIAN;

        public ByteOrderedDataInputStream(java.io.InputStream inputStream) throws java.io.IOException {
            this(inputStream, java.nio.ByteOrder.BIG_ENDIAN);
        }

        ByteOrderedDataInputStream(java.io.InputStream inputStream, java.nio.ByteOrder byteOrder) throws java.io.IOException {
            this.mByteOrder = java.nio.ByteOrder.BIG_ENDIAN;
            this.mInputStream = inputStream;
            this.mDataInputStream = new java.io.DataInputStream(inputStream);
            this.mLength = this.mDataInputStream.available();
            this.mPosition = 0;
            this.mDataInputStream.mark(this.mLength);
            this.mByteOrder = byteOrder;
        }

        public ByteOrderedDataInputStream(byte[] bArr) throws java.io.IOException {
            this(new java.io.ByteArrayInputStream(bArr));
        }

        public void setByteOrder(java.nio.ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long j) throws java.io.IOException {
            if (this.mPosition > j) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                j -= this.mPosition;
            }
            int i = (int) j;
            if (skipBytes(i) != i) {
                throw new java.io.IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        @Override // java.io.InputStream
        public int available() throws java.io.IOException {
            return this.mDataInputStream.available();
        }

        @Override // java.io.InputStream
        public int read() throws java.io.IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws java.io.IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public java.lang.String readLine() throws java.io.IOException {
            android.util.Log.d(android.media.ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws java.io.IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        @Override // java.io.DataInput
        public char readChar() throws java.io.IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        @Override // java.io.DataInput
        public java.lang.String readUTF() throws java.io.IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws java.io.IOException {
            this.mPosition += i2;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            if (this.mDataInputStream.read(bArr, i, i2) != i2) {
                throw new java.io.IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws java.io.IOException {
            this.mPosition += bArr.length;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            if (this.mDataInputStream.read(bArr, 0, bArr.length) != bArr.length) {
                throw new java.io.IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public byte readByte() throws java.io.IOException {
            this.mPosition++;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            int read = this.mDataInputStream.read();
            if (read < 0) {
                throw new java.io.EOFException();
            }
            return (byte) read;
        }

        @Override // java.io.DataInput
        public short readShort() throws java.io.IOException {
            this.mPosition += 2;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new java.io.EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (short) ((read2 << 8) + read);
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (short) ((read << 8) + read2);
            }
            throw new java.io.IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public int readInt() throws java.io.IOException {
            this.mPosition += 4;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4) < 0) {
                throw new java.io.EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (read << 24) + (read2 << 16) + (read3 << 8) + read4;
            }
            throw new java.io.IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws java.io.IOException {
            int min = java.lang.Math.min(i, this.mLength - this.mPosition);
            int i2 = 0;
            while (i2 < min) {
                int skipBytes = this.mDataInputStream.skipBytes(min - i2);
                if (skipBytes <= 0) {
                    break;
                }
                i2 += skipBytes;
            }
            this.mPosition += i2;
            return i2;
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws java.io.IOException {
            this.mPosition += 2;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            if ((read | read2) < 0) {
                throw new java.io.EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (read2 << 8) + read;
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (read << 8) + read2;
            }
            throw new java.io.IOException("Invalid byte order: " + this.mByteOrder);
        }

        public long readUnsignedInt() throws java.io.IOException {
            return readInt() & 4294967295L;
        }

        @Override // java.io.DataInput
        public long readLong() throws java.io.IOException {
            this.mPosition += 8;
            if (this.mPosition > this.mLength) {
                throw new java.io.EOFException();
            }
            int read = this.mDataInputStream.read();
            int read2 = this.mDataInputStream.read();
            int read3 = this.mDataInputStream.read();
            int read4 = this.mDataInputStream.read();
            int read5 = this.mDataInputStream.read();
            int read6 = this.mDataInputStream.read();
            int read7 = this.mDataInputStream.read();
            int read8 = this.mDataInputStream.read();
            if ((read | read2 | read3 | read4 | read5 | read6 | read7 | read8) < 0) {
                throw new java.io.EOFException();
            }
            if (this.mByteOrder != LITTLE_ENDIAN) {
                if (this.mByteOrder == BIG_ENDIAN) {
                    return (read << 56) + (read2 << 48) + (read3 << 40) + (read4 << 32) + (read5 << 24) + (read6 << 16) + (read7 << 8) + read8;
                }
                throw new java.io.IOException("Invalid byte order: " + this.mByteOrder);
            }
            return (read8 << 56) + (read7 << 48) + (read6 << 40) + (read5 << 32) + (read4 << 24) + (read3 << 16) + (read2 << 8) + read;
        }

        @Override // java.io.DataInput
        public float readFloat() throws java.io.IOException {
            return java.lang.Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public double readDouble() throws java.io.IOException {
            return java.lang.Double.longBitsToDouble(readLong());
        }

        public int getLength() {
            return this.mLength;
        }
    }

    private static class ByteOrderedDataOutputStream extends java.io.FilterOutputStream {
        private java.nio.ByteOrder mByteOrder;
        final java.io.OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(java.io.OutputStream outputStream, java.nio.ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(java.nio.ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws java.io.IOException {
            this.mOutputStream.write(bArr);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws java.io.IOException {
            this.mOutputStream.write(bArr, i, i2);
        }

        public void writeByte(int i) throws java.io.IOException {
            this.mOutputStream.write(i);
        }

        public void writeShort(short s) throws java.io.IOException {
            if (this.mByteOrder == java.nio.ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((s >>> 0) & 255);
                this.mOutputStream.write((s >>> 8) & 255);
            } else if (this.mByteOrder == java.nio.ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((s >>> 8) & 255);
                this.mOutputStream.write((s >>> 0) & 255);
            }
        }

        public void writeInt(int i) throws java.io.IOException {
            if (this.mByteOrder == java.nio.ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((i >>> 0) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 24) & 255);
                return;
            }
            if (this.mByteOrder == java.nio.ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((i >>> 24) & 255);
                this.mOutputStream.write((i >>> 16) & 255);
                this.mOutputStream.write((i >>> 8) & 255);
                this.mOutputStream.write((i >>> 0) & 255);
            }
        }

        public void writeUnsignedShort(int i) throws java.io.IOException {
            writeShort((short) i);
        }

        public void writeUnsignedInt(long j) throws java.io.IOException {
            writeInt((int) j);
        }
    }

    private void swapBasedOnImageSize(int i, int i2) throws java.io.IOException {
        if (this.mAttributes[i].isEmpty() || this.mAttributes[i2].isEmpty()) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        android.media.ExifInterface.ExifAttribute exifAttribute = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_LENGTH);
        android.media.ExifInterface.ExifAttribute exifAttribute2 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i].get(TAG_IMAGE_WIDTH);
        android.media.ExifInterface.ExifAttribute exifAttribute3 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i2].get(TAG_IMAGE_LENGTH);
        android.media.ExifInterface.ExifAttribute exifAttribute4 = (android.media.ExifInterface.ExifAttribute) this.mAttributes[i2].get(TAG_IMAGE_WIDTH);
        if (exifAttribute == null || exifAttribute2 == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "First image does not contain valid size information");
                return;
            }
            return;
        }
        if (exifAttribute3 == null || exifAttribute4 == null) {
            if (DEBUG) {
                android.util.Log.d(TAG, "Second image does not contain valid size information");
                return;
            }
            return;
        }
        int intValue = exifAttribute.getIntValue(this.mExifByteOrder);
        int intValue2 = exifAttribute2.getIntValue(this.mExifByteOrder);
        int intValue3 = exifAttribute3.getIntValue(this.mExifByteOrder);
        int intValue4 = exifAttribute4.getIntValue(this.mExifByteOrder);
        if (intValue < intValue3 && intValue2 < intValue4) {
            java.util.HashMap hashMap = this.mAttributes[i];
            this.mAttributes[i] = this.mAttributes[i2];
            this.mAttributes[i2] = hashMap;
        }
    }

    private void replaceInvalidTags(int i, java.lang.String str, java.lang.String str2) {
        if (!this.mAttributes[i].isEmpty() && this.mAttributes[i].get(str) != null) {
            this.mAttributes[i].put(str2, this.mAttributes[i].get(str));
            this.mAttributes[i].remove(str);
        }
    }

    private boolean isSupportedFormatForSavingAttributes() {
        if (!this.mIsSupportedFile) {
            return false;
        }
        if (this.mMimeType == 4 || this.mMimeType == 13 || this.mMimeType == 14) {
            return true;
        }
        return false;
    }
}
