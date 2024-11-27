package android.media;

/* loaded from: classes2.dex */
public class MediaFile {

    @java.lang.Deprecated
    private static final int FIRST_AUDIO_FILE_TYPE = 1;

    @java.lang.Deprecated
    private static final int LAST_AUDIO_FILE_TYPE = 10;

    @java.lang.Deprecated
    private static final java.util.HashMap<java.lang.String, android.media.MediaFile.MediaFileType> sFileTypeMap = new java.util.HashMap<>();

    @java.lang.Deprecated
    private static final java.util.HashMap<java.lang.String, java.lang.Integer> sFileTypeToFormatMap = new java.util.HashMap<>();
    private static final java.util.HashMap<java.lang.String, java.lang.Integer> sMimeTypeToFormatMap = new java.util.HashMap<>();
    private static final java.util.HashMap<java.lang.Integer, java.lang.String> sFormatToMimeTypeMap = new java.util.HashMap<>();

    @java.lang.Deprecated
    public static class MediaFileType {
        public final int fileType;
        public final java.lang.String mimeType;

        MediaFileType(int i, java.lang.String str) {
            this.fileType = i;
            this.mimeType = str;
        }
    }

    static {
        addFileType(12297, "audio/mpeg");
        addFileType(12296, com.google.android.mms.ContentType.AUDIO_X_WAV);
        addFileType(android.mtp.MtpConstants.FORMAT_WMA, "audio/x-ms-wma");
        addFileType(android.mtp.MtpConstants.FORMAT_OGG, com.google.android.mms.ContentType.AUDIO_OGG2);
        addFileType(android.mtp.MtpConstants.FORMAT_AAC, com.google.android.mms.ContentType.AUDIO_AAC);
        addFileType(android.mtp.MtpConstants.FORMAT_FLAC, android.media.MediaFormat.MIMETYPE_AUDIO_FLAC);
        addFileType(12295, "audio/x-aiff");
        addFileType(android.mtp.MtpConstants.FORMAT_MP2, "audio/mpeg");
        addFileType(12299, "video/mpeg");
        addFileType(android.mtp.MtpConstants.FORMAT_MP4_CONTAINER, com.google.android.mms.ContentType.VIDEO_MP4);
        addFileType(android.mtp.MtpConstants.FORMAT_3GP_CONTAINER, "video/3gpp");
        addFileType(android.mtp.MtpConstants.FORMAT_3GP_CONTAINER, com.google.android.mms.ContentType.VIDEO_3G2);
        addFileType(12298, "video/avi");
        addFileType(android.mtp.MtpConstants.FORMAT_WMV, "video/x-ms-wmv");
        addFileType(12300, "video/x-ms-asf");
        addFileType(android.mtp.MtpConstants.FORMAT_EXIF_JPEG, com.google.android.mms.ContentType.IMAGE_JPEG);
        addFileType(android.mtp.MtpConstants.FORMAT_GIF, com.google.android.mms.ContentType.IMAGE_GIF);
        addFileType(android.mtp.MtpConstants.FORMAT_PNG, com.google.android.mms.ContentType.IMAGE_PNG);
        addFileType(android.mtp.MtpConstants.FORMAT_BMP, com.google.android.mms.ContentType.IMAGE_X_MS_BMP);
        addFileType(android.mtp.MtpConstants.FORMAT_HEIF, "image/heif");
        addFileType(android.mtp.MtpConstants.FORMAT_DNG, "image/x-adobe-dng");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/tiff");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-canon-cr2");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-nikon-nrw");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-sony-arw");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-panasonic-rw2");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-olympus-orf");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-pentax-pef");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF, "image/x-samsung-srw");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF_EP, "image/tiff");
        addFileType(android.mtp.MtpConstants.FORMAT_TIFF_EP, "image/x-nikon-nef");
        addFileType(android.mtp.MtpConstants.FORMAT_JP2, "image/jp2");
        addFileType(android.mtp.MtpConstants.FORMAT_JPX, "image/jpx");
        addFileType(android.mtp.MtpConstants.FORMAT_M3U_PLAYLIST, "audio/x-mpegurl");
        addFileType(android.mtp.MtpConstants.FORMAT_PLS_PLAYLIST, "audio/x-scpls");
        addFileType(android.mtp.MtpConstants.FORMAT_WPL_PLAYLIST, "application/vnd.ms-wpl");
        addFileType(android.mtp.MtpConstants.FORMAT_ASX_PLAYLIST, "video/x-ms-asf");
        addFileType(12292, "text/plain");
        addFileType(12293, "text/html");
        addFileType(android.mtp.MtpConstants.FORMAT_XML_DOCUMENT, "text/xml");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_WORD_DOCUMENT, "application/msword");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_WORD_DOCUMENT, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_EXCEL_SPREADSHEET, "application/vnd.ms-excel");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_EXCEL_SPREADSHEET, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_POWERPOINT_PRESENTATION, "application/vnd.ms-powerpoint");
        addFileType(android.mtp.MtpConstants.FORMAT_MS_POWERPOINT_PRESENTATION, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    }

    @java.lang.Deprecated
    static void addFileType(java.lang.String str, int i, java.lang.String str2) {
    }

    private static void addFileType(int i, java.lang.String str) {
        if (!sMimeTypeToFormatMap.containsKey(str)) {
            sMimeTypeToFormatMap.put(str, java.lang.Integer.valueOf(i));
        }
        if (!sFormatToMimeTypeMap.containsKey(java.lang.Integer.valueOf(i))) {
            sFormatToMimeTypeMap.put(java.lang.Integer.valueOf(i), str);
        }
    }

    @java.lang.Deprecated
    public static boolean isAudioFileType(int i) {
        return false;
    }

    @java.lang.Deprecated
    public static boolean isVideoFileType(int i) {
        return false;
    }

    @java.lang.Deprecated
    public static boolean isImageFileType(int i) {
        return false;
    }

    @java.lang.Deprecated
    public static boolean isPlayListFileType(int i) {
        return false;
    }

    @java.lang.Deprecated
    public static boolean isDrmFileType(int i) {
        return false;
    }

    @java.lang.Deprecated
    public static android.media.MediaFile.MediaFileType getFileType(java.lang.String str) {
        return null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isDocumentMimeType(java.lang.String str) {
        char c;
        if (str == null) {
            return false;
        }
        java.lang.String normalizeMimeType = normalizeMimeType(str);
        if (normalizeMimeType.startsWith("text/")) {
            return true;
        }
        java.lang.String lowerCase = normalizeMimeType.toLowerCase(java.util.Locale.ROOT);
        switch (lowerCase.hashCode()) {
            case -2135180893:
                if (lowerCase.equals("application/vnd.stardivision.calc")) {
                    c = '$';
                    break;
                }
                c = 65535;
                break;
            case -2135135086:
                if (lowerCase.equals("application/vnd.stardivision.draw")) {
                    c = '&';
                    break;
                }
                c = 65535;
                break;
            case -2134883067:
                if (lowerCase.equals("application/vnd.stardivision.mail")) {
                    c = ')';
                    break;
                }
                c = 65535;
                break;
            case -2134882730:
                if (lowerCase.equals("application/vnd.stardivision.math")) {
                    c = '*';
                    break;
                }
                c = 65535;
                break;
            case -2008589971:
                if (lowerCase.equals("application/epub+zip")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1782746503:
                if (lowerCase.equals("application/vnd.oasis.opendocument.chart")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case -1765899696:
                if (lowerCase.equals("application/vnd.stardivision.chart")) {
                    c = '%';
                    break;
                }
                c = 65535;
                break;
            case -1747277413:
                if (lowerCase.equals("application/vnd.sun.xml.writer.template")) {
                    c = '6';
                    break;
                }
                c = 65535;
                break;
            case -1719571662:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case -1653115602:
                if (lowerCase.equals("application/vnd.ms-excel.sheet.macroenabled.12")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1628346451:
                if (lowerCase.equals("application/vnd.sun.xml.writer")) {
                    c = '4';
                    break;
                }
                c = 65535;
                break;
            case -1590813831:
                if (lowerCase.equals("application/vnd.sun.xml.calc.template")) {
                    c = '.';
                    break;
                }
                c = 65535;
                break;
            case -1506009513:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.template")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case -1316922187:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-template")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case -1293459259:
                if (lowerCase.equals("application/vnd.ms-word.document.macroenabled.12")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1248334925:
                if (lowerCase.equals("application/pdf")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1248332507:
                if (lowerCase.equals("application/rtf")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1095881539:
                if (lowerCase.equals("application/vnd.ms-powerpoint.slideshow.macroenabled.12")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1073633483:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.presentation")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case -1071817359:
                if (lowerCase.equals("application/vnd.ms-powerpoint")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1050893613:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            case -1033484950:
                if (lowerCase.equals("application/vnd.sun.xml.draw.template")) {
                    c = '0';
                    break;
                }
                c = 65535;
                break;
            case -943935167:
                if (lowerCase.equals("application/vnd.oasis.opendocument.formula")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case -878977693:
                if (lowerCase.equals("application/vnd.ms-excel.template.macroenabled.12")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -779959281:
                if (lowerCase.equals("application/vnd.sun.xml.calc")) {
                    c = '-';
                    break;
                }
                c = 65535;
                break;
            case -779913474:
                if (lowerCase.equals("application/vnd.sun.xml.draw")) {
                    c = '/';
                    break;
                }
                c = 65535;
                break;
            case -779661118:
                if (lowerCase.equals("application/vnd.sun.xml.math")) {
                    c = '3';
                    break;
                }
                c = 65535;
                break;
            case -676675015:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-web")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case -481744190:
                if (lowerCase.equals("application/x-mspublisher")) {
                    c = '7';
                    break;
                }
                c = 65535;
                break;
            case -479218428:
                if (lowerCase.equals("application/vnd.sun.xml.writer.global")) {
                    c = '5';
                    break;
                }
                c = 65535;
                break;
            case -396757341:
                if (lowerCase.equals("application/vnd.sun.xml.impress.template")) {
                    c = '2';
                    break;
                }
                c = 65535;
                break;
            case -366307023:
                if (lowerCase.equals("application/vnd.ms-excel")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -300783143:
                if (lowerCase.equals("application/vnd.ms-excel.sheet.binary.macroenabled.12")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -291851672:
                if (lowerCase.equals("application/vnd.oasis.opendocument.presentation-template")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case -151068779:
                if (lowerCase.equals("application/vnd.ms-powerpoint.addin.macroenabled.12")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -109382304:
                if (lowerCase.equals("application/vnd.oasis.opendocument.spreadsheet-template")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 65167651:
                if (lowerCase.equals("application/vnd.ms-powerpoint.template.macroenabled.12")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case 428819984:
                if (lowerCase.equals("application/vnd.oasis.opendocument.graphics")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 571050671:
                if (lowerCase.equals("application/vnd.stardivision.writer-global")) {
                    c = ',';
                    break;
                }
                c = 65535;
                break;
            case 669516689:
                if (lowerCase.equals("application/vnd.stardivision.impress")) {
                    c = android.text.format.DateFormat.QUOTE;
                    break;
                }
                c = 65535;
                break;
            case 694663701:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.template")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 776322612:
                if (lowerCase.equals("application/vnd.stardivision.impress-packed")) {
                    c = '(';
                    break;
                }
                c = 65535;
                break;
            case 904647503:
                if (lowerCase.equals("application/msword")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1060806194:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.template")) {
                    c = '#';
                    break;
                }
                c = 65535;
                break;
            case 1377360791:
                if (lowerCase.equals("application/vnd.oasis.opendocument.graphics-template")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 1383644708:
                if (lowerCase.equals("application/vnd.ms-word.template.macroenabled.12")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 1383977381:
                if (lowerCase.equals("application/vnd.sun.xml.impress")) {
                    c = '1';
                    break;
                }
                c = 65535;
                break;
            case 1436962847:
                if (lowerCase.equals("application/vnd.oasis.opendocument.presentation")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 1461751133:
                if (lowerCase.equals("application/vnd.oasis.opendocument.text-master")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 1577426419:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.presentationml.slideshow")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1643664935:
                if (lowerCase.equals("application/vnd.oasis.opendocument.spreadsheet")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 1673742401:
                if (lowerCase.equals("application/vnd.stardivision.writer")) {
                    c = '+';
                    break;
                }
                c = 65535;
                break;
            case 1750539587:
                if (lowerCase.equals("application/vnd.ms-powerpoint.presentation.macroenabled.12")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case 1993842850:
                if (lowerCase.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 2094058325:
                if (lowerCase.equals("application/vnd.ms-excel.addin.macroenabled.12")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 2117577984:
                if (lowerCase.equals("application/vnd.oasis.opendocument.database")) {
                    c = 17;
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
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
                return true;
            default:
                return false;
        }
    }

    public static boolean isExifMimeType(java.lang.String str) {
        return isImageMimeType(str);
    }

    public static boolean isAudioMimeType(java.lang.String str) {
        return normalizeMimeType(str).startsWith("audio/");
    }

    public static boolean isVideoMimeType(java.lang.String str) {
        return normalizeMimeType(str).startsWith("video/");
    }

    public static boolean isImageMimeType(java.lang.String str) {
        return normalizeMimeType(str).startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isPlayListMimeType(java.lang.String str) {
        char c;
        java.lang.String normalizeMimeType = normalizeMimeType(str);
        switch (normalizeMimeType.hashCode()) {
            case -1165508903:
                if (normalizeMimeType.equals("audio/x-scpls")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -979095690:
                if (normalizeMimeType.equals("application/x-mpegurl")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -622808459:
                if (normalizeMimeType.equals("application/vnd.apple.mpegurl")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -432766831:
                if (normalizeMimeType.equals("audio/mpegurl")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 264230524:
                if (normalizeMimeType.equals("audio/x-mpegurl")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1872259501:
                if (normalizeMimeType.equals("application/vnd.ms-wpl")) {
                    c = 0;
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
                return true;
            default:
                return false;
        }
    }

    public static boolean isDrmMimeType(java.lang.String str) {
        return normalizeMimeType(str).equals("application/x-android-drm-fl");
    }

    public static java.lang.String getFileTitle(java.lang.String str) {
        int i;
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf >= 0 && (i = lastIndexOf + 1) < str.length()) {
            str = str.substring(i);
        }
        int lastIndexOf2 = str.lastIndexOf(46);
        if (lastIndexOf2 > 0) {
            return str.substring(0, lastIndexOf2);
        }
        return str;
    }

    public static java.lang.String getFileExtension(java.lang.String str) {
        int lastIndexOf;
        if (str == null || (lastIndexOf = str.lastIndexOf(46)) < 0) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    @java.lang.Deprecated
    public static int getFileTypeForMimeType(java.lang.String str) {
        return 0;
    }

    public static java.lang.String getMimeType(java.lang.String str, int i) {
        java.lang.String mimeTypeForFile = getMimeTypeForFile(str);
        if (!"application/octet-stream".equals(mimeTypeForFile)) {
            return mimeTypeForFile;
        }
        return getMimeTypeForFormatCode(i);
    }

    public static java.lang.String getMimeTypeForFile(java.lang.String str) {
        java.lang.String guessMimeTypeFromExtension = libcore.content.type.MimeMap.getDefault().guessMimeTypeFromExtension(getFileExtension(str));
        return guessMimeTypeFromExtension != null ? guessMimeTypeFromExtension : "application/octet-stream";
    }

    public static java.lang.String getMimeTypeForFormatCode(int i) {
        java.lang.String str = sFormatToMimeTypeMap.get(java.lang.Integer.valueOf(i));
        return str != null ? str : "application/octet-stream";
    }

    public static int getFormatCode(java.lang.String str, java.lang.String str2) {
        int formatCodeForMimeType = getFormatCodeForMimeType(str2);
        if (formatCodeForMimeType != 12288) {
            return formatCodeForMimeType;
        }
        return getFormatCodeForFile(str);
    }

    public static int getFormatCodeForFile(java.lang.String str) {
        return getFormatCodeForMimeType(getMimeTypeForFile(str));
    }

    public static int getFormatCodeForMimeType(java.lang.String str) {
        if (str == null) {
            return 12288;
        }
        java.lang.Integer num = sMimeTypeToFormatMap.get(str);
        if (num != null) {
            return num.intValue();
        }
        java.lang.String normalizeMimeType = normalizeMimeType(str);
        java.lang.Integer num2 = sMimeTypeToFormatMap.get(normalizeMimeType);
        if (num2 != null) {
            return num2.intValue();
        }
        if (normalizeMimeType.startsWith("audio/")) {
            return android.mtp.MtpConstants.FORMAT_UNDEFINED_AUDIO;
        }
        if (normalizeMimeType.startsWith("video/")) {
            return android.mtp.MtpConstants.FORMAT_UNDEFINED_VIDEO;
        }
        if (!normalizeMimeType.startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
            return 12288;
        }
        return 14336;
    }

    private static java.lang.String normalizeMimeType(java.lang.String str) {
        java.lang.String guessMimeTypeFromExtension;
        libcore.content.type.MimeMap mimeMap = libcore.content.type.MimeMap.getDefault();
        java.lang.String guessExtensionFromMimeType = mimeMap.guessExtensionFromMimeType(str);
        if (guessExtensionFromMimeType == null || (guessMimeTypeFromExtension = mimeMap.guessMimeTypeFromExtension(guessExtensionFromMimeType)) == null) {
            return str != null ? str : "application/octet-stream";
        }
        return guessMimeTypeFromExtension;
    }
}
