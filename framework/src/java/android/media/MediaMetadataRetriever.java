package android.media;

/* loaded from: classes2.dex */
public class MediaMetadataRetriever implements java.lang.AutoCloseable {
    private static final int EMBEDDED_PICTURE_TYPE_ANY = 65535;
    public static final int METADATA_KEY_ALBUM = 1;
    public static final int METADATA_KEY_ALBUMARTIST = 13;
    public static final int METADATA_KEY_ARTIST = 2;
    public static final int METADATA_KEY_AUTHOR = 3;
    public static final int METADATA_KEY_BITRATE = 20;
    public static final int METADATA_KEY_BITS_PER_SAMPLE = 39;
    public static final int METADATA_KEY_CAPTURE_FRAMERATE = 25;
    public static final int METADATA_KEY_CD_TRACK_NUMBER = 0;
    public static final int METADATA_KEY_COLOR_RANGE = 37;
    public static final int METADATA_KEY_COLOR_STANDARD = 35;
    public static final int METADATA_KEY_COLOR_TRANSFER = 36;
    public static final int METADATA_KEY_COMPILATION = 15;
    public static final int METADATA_KEY_COMPOSER = 4;
    public static final int METADATA_KEY_DATE = 5;
    public static final int METADATA_KEY_DISC_NUMBER = 14;
    public static final int METADATA_KEY_DURATION = 9;
    public static final int METADATA_KEY_EXIF_LENGTH = 34;
    public static final int METADATA_KEY_EXIF_OFFSET = 33;
    public static final int METADATA_KEY_GENRE = 6;
    public static final int METADATA_KEY_HAS_AUDIO = 16;
    public static final int METADATA_KEY_HAS_IMAGE = 26;
    public static final int METADATA_KEY_HAS_VIDEO = 17;
    public static final int METADATA_KEY_IMAGE_COUNT = 27;
    public static final int METADATA_KEY_IMAGE_HEIGHT = 30;
    public static final int METADATA_KEY_IMAGE_PRIMARY = 28;
    public static final int METADATA_KEY_IMAGE_ROTATION = 31;
    public static final int METADATA_KEY_IMAGE_WIDTH = 29;
    public static final int METADATA_KEY_IS_DRM = 22;
    public static final int METADATA_KEY_LOCATION = 23;
    public static final int METADATA_KEY_MIMETYPE = 12;
    public static final int METADATA_KEY_NUM_TRACKS = 10;
    public static final int METADATA_KEY_SAMPLERATE = 38;
    public static final int METADATA_KEY_TIMED_TEXT_LANGUAGES = 21;
    public static final int METADATA_KEY_TITLE = 7;

    @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
    public static final int METADATA_KEY_VIDEO_CODEC_MIME_TYPE = 40;
    public static final int METADATA_KEY_VIDEO_FRAME_COUNT = 32;
    public static final int METADATA_KEY_VIDEO_HEIGHT = 19;
    public static final int METADATA_KEY_VIDEO_ROTATION = 24;
    public static final int METADATA_KEY_VIDEO_WIDTH = 18;
    public static final int METADATA_KEY_WRITER = 11;
    public static final int METADATA_KEY_XMP_LENGTH = 42;
    public static final int METADATA_KEY_XMP_OFFSET = 41;
    public static final int METADATA_KEY_YEAR = 8;
    public static final int OPTION_CLOSEST = 3;
    public static final int OPTION_CLOSEST_SYNC = 2;
    public static final int OPTION_NEXT_SYNC = 1;
    public static final int OPTION_PREVIOUS_SYNC = 0;
    private static final java.lang.String[] STANDARD_GENRES = {"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native American", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebob", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Afro-Punk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop"};
    private static final java.lang.String TAG = "MediaMetadataRetriever";
    private long mNativeContext;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Option {
    }

    private native java.util.List<android.graphics.Bitmap> _getFrameAtIndex(int i, int i2, android.media.MediaMetadataRetriever.BitmapParams bitmapParams);

    private native android.graphics.Bitmap _getFrameAtTime(long j, int i, int i2, int i3, android.media.MediaMetadataRetriever.BitmapParams bitmapParams);

    private native android.graphics.Bitmap _getImageAtIndex(int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams);

    private native void _setDataSource(android.media.MediaDataSource mediaDataSource) throws java.lang.IllegalArgumentException;

    private native void _setDataSource(android.os.IBinder iBinder, java.lang.String str, java.lang.String[] strArr, java.lang.String[] strArr2) throws java.lang.IllegalArgumentException;

    private native void _setDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) throws java.lang.IllegalArgumentException;

    private native byte[] getEmbeddedPicture(int i);

    private native java.lang.String nativeExtractMetadata(int i);

    private final native void native_finalize();

    private static native void native_init();

    private native void native_setup();

    public native android.graphics.Bitmap getThumbnailImageAtIndex(int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams, int i2, int i3);

    public native void release() throws java.io.IOException;

    static {
        java.lang.System.loadLibrary("media_jni");
        native_init();
    }

    public MediaMetadataRetriever() {
        native_setup();
    }

    public void setDataSource(java.lang.String str) throws java.lang.IllegalArgumentException {
        if (str == null) {
            throw new java.lang.IllegalArgumentException("null path");
        }
        android.net.Uri parse = android.net.Uri.parse(str);
        java.lang.String scheme = parse.getScheme();
        if ("file".equals(scheme)) {
            str = parse.getPath();
        } else if (scheme != null) {
            setDataSource(str, new java.util.HashMap());
            return;
        }
        try {
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(str);
            try {
                setDataSource(fileInputStream.getFD(), 0L, 576460752303423487L);
                fileInputStream.close();
            } catch (java.lang.Throwable th) {
                try {
                    fileInputStream.close();
                } catch (java.lang.Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (java.io.FileNotFoundException e) {
            throw new java.lang.IllegalArgumentException(str + " does not exist");
        } catch (java.io.IOException e2) {
            throw new java.lang.IllegalArgumentException("couldn't open " + str);
        }
    }

    public void setDataSource(java.lang.String str, java.util.Map<java.lang.String, java.lang.String> map) throws java.lang.IllegalArgumentException {
        java.lang.String[] strArr = new java.lang.String[map.size()];
        java.lang.String[] strArr2 = new java.lang.String[map.size()];
        int i = 0;
        for (java.util.Map.Entry<java.lang.String, java.lang.String> entry : map.entrySet()) {
            strArr[i] = entry.getKey();
            strArr2[i] = entry.getValue();
            i++;
        }
        _setDataSource(android.media.MediaHTTPService.createHttpServiceBinderIfNecessary(str), str, strArr, strArr2);
    }

    public void setDataSource(java.io.FileDescriptor fileDescriptor, long j, long j2) throws java.lang.IllegalArgumentException {
        try {
            android.os.ParcelFileDescriptor convertToModernFd = android.os.FileUtils.convertToModernFd(fileDescriptor);
            try {
                if (convertToModernFd == null) {
                    _setDataSource(fileDescriptor, j, j2);
                } else {
                    _setDataSource(convertToModernFd.getFileDescriptor(), j, j2);
                }
                if (convertToModernFd != null) {
                    convertToModernFd.close();
                }
            } finally {
            }
        } catch (java.io.IOException e) {
            android.util.Log.w(TAG, "Ignoring IO error while setting data source", e);
        }
    }

    public void setDataSource(java.io.FileDescriptor fileDescriptor) throws java.lang.IllegalArgumentException {
        setDataSource(fileDescriptor, 0L, 576460752303423487L);
    }

    public void setDataSource(android.content.Context context, android.net.Uri uri) throws java.lang.IllegalArgumentException, java.lang.SecurityException {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("null uri");
        }
        java.lang.String scheme = uri.getScheme();
        if (scheme == null || scheme.equals("file")) {
            setDataSource(uri.getPath());
            return;
        }
        java.lang.AutoCloseable autoCloseable = null;
        try {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            try {
                boolean z = android.os.SystemProperties.getBoolean("fuse.sys.transcode_retriever_optimize", false);
                android.os.Bundle bundle = new android.os.Bundle();
                bundle.putBoolean("android.provider.extra.ACCEPT_ORIGINAL_MEDIA_FORMAT", true);
                android.content.res.AssetFileDescriptor openTypedAssetFileDescriptor = z ? contentResolver.openTypedAssetFileDescriptor(uri, "*/*", bundle) : contentResolver.openAssetFileDescriptor(uri, "r");
                if (openTypedAssetFileDescriptor == null) {
                    throw new java.lang.IllegalArgumentException("got null FileDescriptor for " + uri);
                }
                java.io.FileDescriptor fileDescriptor = openTypedAssetFileDescriptor.getFileDescriptor();
                if (!fileDescriptor.valid()) {
                    throw new java.lang.IllegalArgumentException("got invalid FileDescriptor for " + uri);
                }
                if (openTypedAssetFileDescriptor.getDeclaredLength() < 0) {
                    setDataSource(fileDescriptor);
                } else {
                    setDataSource(fileDescriptor, openTypedAssetFileDescriptor.getStartOffset(), openTypedAssetFileDescriptor.getDeclaredLength());
                }
                if (openTypedAssetFileDescriptor == null) {
                    return;
                }
                try {
                    openTypedAssetFileDescriptor.close();
                } catch (java.io.IOException e) {
                }
            } catch (java.io.FileNotFoundException e2) {
                throw new java.lang.IllegalArgumentException("could not access " + uri);
            }
        } catch (java.lang.SecurityException e3) {
            if (0 != 0) {
                try {
                    autoCloseable.close();
                } catch (java.io.IOException e4) {
                }
            }
            setDataSource(uri.toString());
        } catch (java.lang.Throwable th) {
            if (0 != 0) {
                try {
                    autoCloseable.close();
                } catch (java.io.IOException e5) {
                }
            }
            throw th;
        }
    }

    public void setDataSource(android.media.MediaDataSource mediaDataSource) throws java.lang.IllegalArgumentException {
        _setDataSource(mediaDataSource);
    }

    public java.lang.String extractMetadata(int i) {
        java.lang.String nativeExtractMetadata = nativeExtractMetadata(i);
        if (i == 6) {
            return convertGenreTag(nativeExtractMetadata);
        }
        return nativeExtractMetadata;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d2, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private java.lang.String convertGenreTag(java.lang.String str) {
        if (android.text.TextUtils.isEmpty(str)) {
            return null;
        }
        if (java.lang.Character.isDigit(str.charAt(0))) {
            try {
                int parseInt = java.lang.Integer.parseInt(str);
                if (parseInt >= 0 && parseInt < STANDARD_GENRES.length) {
                    return STANDARD_GENRES[parseInt];
                }
            } catch (java.lang.NumberFormatException e) {
            }
            return null;
        }
        java.lang.String str2 = null;
        java.lang.StringBuilder sb = null;
        while (true) {
            if (!android.text.TextUtils.isEmpty(str2)) {
                if (sb == null) {
                    sb = new java.lang.StringBuilder();
                }
                if (sb.length() != 0) {
                    sb.append(", ");
                }
                sb.append(str2);
            }
            if (!android.text.TextUtils.isEmpty(str)) {
                if (str.startsWith("(RX)")) {
                    str = str.substring(4);
                    str2 = "Remix";
                } else if (str.startsWith("(CR)")) {
                    str = str.substring(4);
                    str2 = "Cover";
                } else if (str.startsWith("((")) {
                    int indexOf = str.indexOf(41);
                    if (indexOf == -1) {
                        str2 = str.substring(1);
                        str = "";
                    } else {
                        int i = indexOf + 1;
                        java.lang.String substring = str.substring(1, i);
                        str = str.substring(i);
                        str2 = substring;
                    }
                } else if (str.startsWith(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_START)) {
                    int indexOf2 = str.indexOf(41);
                    if (indexOf2 == -1) {
                        return null;
                    }
                    try {
                        int parseInt2 = java.lang.Integer.parseInt(str.substring(1, indexOf2).toString());
                        if (parseInt2 < 0 || parseInt2 >= STANDARD_GENRES.length) {
                            break;
                        }
                        java.lang.String str3 = STANDARD_GENRES[parseInt2];
                        str = str.substring(indexOf2 + 1);
                        str2 = str3;
                    } catch (java.lang.NumberFormatException e2) {
                        return null;
                    }
                } else {
                    str2 = str;
                    str = "";
                }
            } else {
                if (sb == null || sb.length() == 0) {
                    return null;
                }
                return sb.toString();
            }
        }
    }

    public android.graphics.Bitmap getFrameAtTime(long j, int i) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Unsupported option: " + i);
        }
        return _getFrameAtTime(j, i, -1, -1, null);
    }

    public android.graphics.Bitmap getFrameAtTime(long j, int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Unsupported option: " + i);
        }
        return _getFrameAtTime(j, i, -1, -1, bitmapParams);
    }

    public android.graphics.Bitmap getScaledFrameAtTime(long j, int i, int i2, int i3) {
        validate(i, i2, i3);
        return _getFrameAtTime(j, i, i2, i3, null);
    }

    public android.graphics.Bitmap getScaledFrameAtTime(long j, int i, int i2, int i3, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        validate(i, i2, i3);
        return _getFrameAtTime(j, i, i2, i3, bitmapParams);
    }

    private void validate(int i, int i2, int i3) {
        if (i < 0 || i > 3) {
            throw new java.lang.IllegalArgumentException("Unsupported option: " + i);
        }
        if (i2 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid width: " + i2);
        }
        if (i3 <= 0) {
            throw new java.lang.IllegalArgumentException("Invalid height: " + i3);
        }
    }

    public android.graphics.Bitmap getFrameAtTime(long j) {
        return getFrameAtTime(j, 2);
    }

    public android.graphics.Bitmap getFrameAtTime() {
        return _getFrameAtTime(-1L, 2, -1, -1, null);
    }

    public static final class BitmapParams {
        private android.graphics.Bitmap.Config inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888;
        private android.graphics.Bitmap.Config outActualConfig = android.graphics.Bitmap.Config.ARGB_8888;

        public void setPreferredConfig(android.graphics.Bitmap.Config config) {
            if (config == null) {
                throw new java.lang.IllegalArgumentException("preferred config can't be null");
            }
            this.inPreferredConfig = config;
        }

        public android.graphics.Bitmap.Config getPreferredConfig() {
            return this.inPreferredConfig;
        }

        public android.graphics.Bitmap.Config getActualConfig() {
            return this.outActualConfig;
        }
    }

    public android.graphics.Bitmap getFrameAtIndex(int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        return getFramesAtIndex(i, 1, bitmapParams).get(0);
    }

    public android.graphics.Bitmap getFrameAtIndex(int i) {
        return getFramesAtIndex(i, 1).get(0);
    }

    public java.util.List<android.graphics.Bitmap> getFramesAtIndex(int i, int i2, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        return getFramesAtIndexInternal(i, i2, bitmapParams);
    }

    public java.util.List<android.graphics.Bitmap> getFramesAtIndex(int i, int i2) {
        return getFramesAtIndexInternal(i, i2, null);
    }

    private java.util.List<android.graphics.Bitmap> getFramesAtIndexInternal(int i, int i2, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        if (!android.media.MediaMetrics.Value.YES.equals(extractMetadata(17))) {
            throw new java.lang.IllegalStateException("Does not contain video or image sequences");
        }
        int parseInt = java.lang.Integer.parseInt(extractMetadata(32));
        if (i < 0 || i2 < 1 || i >= parseInt || i > parseInt - i2) {
            throw new java.lang.IllegalArgumentException("Invalid frameIndex or numFrames: " + i + ", " + i2);
        }
        return _getFrameAtIndex(i, i2, bitmapParams);
    }

    public android.graphics.Bitmap getImageAtIndex(int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        return getImageAtIndexInternal(i, bitmapParams);
    }

    public android.graphics.Bitmap getImageAtIndex(int i) {
        return getImageAtIndexInternal(i, null);
    }

    public android.graphics.Bitmap getPrimaryImage(android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        return getImageAtIndexInternal(-1, bitmapParams);
    }

    public android.graphics.Bitmap getPrimaryImage() {
        return getImageAtIndexInternal(-1, null);
    }

    private android.graphics.Bitmap getImageAtIndexInternal(int i, android.media.MediaMetadataRetriever.BitmapParams bitmapParams) {
        if (!android.media.MediaMetrics.Value.YES.equals(extractMetadata(26))) {
            throw new java.lang.IllegalStateException("Does not contain still images");
        }
        java.lang.String extractMetadata = extractMetadata(27);
        if (i >= java.lang.Integer.parseInt(extractMetadata)) {
            throw new java.lang.IllegalArgumentException("Invalid image index: " + extractMetadata);
        }
        return _getImageAtIndex(i, bitmapParams);
    }

    public byte[] getEmbeddedPicture() {
        return getEmbeddedPicture(65535);
    }

    @Override // java.lang.AutoCloseable
    public void close() throws java.io.IOException {
        release();
    }

    protected void finalize() throws java.lang.Throwable {
        try {
            native_finalize();
        } finally {
            super.finalize();
        }
    }
}
