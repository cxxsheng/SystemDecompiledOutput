package android.media;

@java.lang.Deprecated
/* loaded from: classes2.dex */
public class Metadata {
    public static final int ALBUM = 8;
    public static final int ALBUM_ART = 18;
    public static final int ANY = 0;
    public static final int ARTIST = 9;
    public static final int AUDIO_BIT_RATE = 21;
    public static final int AUDIO_CODEC = 26;
    public static final int AUDIO_SAMPLE_RATE = 23;
    public static final int AUTHOR = 10;
    public static final int BIT_RATE = 20;
    public static final int BOOLEAN_VAL = 3;
    public static final int BYTE_ARRAY_VAL = 7;
    public static final int CD_TRACK_MAX = 16;
    public static final int CD_TRACK_NUM = 15;
    public static final int COMMENT = 6;
    public static final int COMPOSER = 11;
    public static final int COPYRIGHT = 7;
    public static final int DATE = 13;
    public static final int DATE_VAL = 6;
    public static final int DOUBLE_VAL = 5;
    public static final int DRM_CRIPPLED = 31;
    public static final int DURATION = 14;
    private static final int FIRST_CUSTOM = 8192;
    public static final int GENRE = 12;
    public static final int INTEGER_VAL = 2;
    private static final int LAST_SYSTEM = 31;
    private static final int LAST_TYPE = 7;
    public static final int LONG_VAL = 4;
    public static final int MIME_TYPE = 25;
    public static final int NUM_TRACKS = 30;
    public static final int PAUSE_AVAILABLE = 1;
    public static final int RATING = 17;
    public static final int SEEK_AVAILABLE = 4;
    public static final int SEEK_BACKWARD_AVAILABLE = 2;
    public static final int SEEK_FORWARD_AVAILABLE = 3;
    public static final int STRING_VAL = 1;
    private static final java.lang.String TAG = "media.Metadata";
    public static final int TITLE = 5;
    public static final int VIDEO_BIT_RATE = 22;
    public static final int VIDEO_CODEC = 27;
    public static final int VIDEO_FRAME = 19;
    public static final int VIDEO_FRAME_RATE = 24;
    public static final int VIDEO_HEIGHT = 28;
    public static final int VIDEO_WIDTH = 29;
    private static final int kInt32Size = 4;
    private static final int kMetaHeaderSize = 8;
    private static final int kMetaMarker = 1296389185;
    private static final int kRecordHeaderSize = 12;
    private final java.util.HashMap<java.lang.Integer, java.lang.Integer> mKeyToPosMap = new java.util.HashMap<>();
    private android.os.Parcel mParcel;
    public static final java.util.Set<java.lang.Integer> MATCH_NONE = java.util.Collections.EMPTY_SET;
    public static final java.util.Set<java.lang.Integer> MATCH_ALL = java.util.Collections.singleton(0);

    private boolean scanAllRecords(android.os.Parcel parcel, int i) {
        boolean z;
        int readInt;
        this.mKeyToPosMap.clear();
        int i2 = 0;
        while (true) {
            if (i <= 12) {
                z = false;
                break;
            }
            int dataPosition = parcel.dataPosition();
            int readInt2 = parcel.readInt();
            if (readInt2 <= 12) {
                android.util.Log.e(TAG, "Record is too short");
                z = true;
                break;
            }
            int readInt3 = parcel.readInt();
            if (!checkMetadataId(readInt3)) {
                z = true;
                break;
            }
            if (this.mKeyToPosMap.containsKey(java.lang.Integer.valueOf(readInt3))) {
                android.util.Log.e(TAG, "Duplicate metadata ID found");
                z = true;
                break;
            }
            this.mKeyToPosMap.put(java.lang.Integer.valueOf(readInt3), java.lang.Integer.valueOf(parcel.dataPosition()));
            readInt = parcel.readInt();
            if (readInt <= 0 || readInt > 7) {
                break;
            }
            try {
                parcel.setDataPosition(android.util.MathUtils.addOrThrow(dataPosition, readInt2));
                i -= readInt2;
                i2++;
            } catch (java.lang.IllegalArgumentException e) {
                android.util.Log.e(TAG, "Invalid size: " + e.getMessage());
                z = true;
            }
        }
        android.util.Log.e(TAG, "Invalid metadata type " + readInt);
        z = true;
        if (i == 0 && !z) {
            return true;
        }
        android.util.Log.e(TAG, "Ran out of data or error on record " + i2);
        this.mKeyToPosMap.clear();
        return false;
    }

    public boolean parse(android.os.Parcel parcel) {
        if (parcel.dataAvail() < 8) {
            android.util.Log.e(TAG, "Not enough data " + parcel.dataAvail());
            return false;
        }
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        if (parcel.dataAvail() + 4 < readInt || readInt < 8) {
            android.util.Log.e(TAG, "Bad size " + readInt + " avail " + parcel.dataAvail() + " position " + dataPosition);
            parcel.setDataPosition(dataPosition);
            return false;
        }
        int readInt2 = parcel.readInt();
        if (readInt2 != kMetaMarker) {
            android.util.Log.e(TAG, "Marker missing " + java.lang.Integer.toHexString(readInt2));
            parcel.setDataPosition(dataPosition);
            return false;
        }
        if (!scanAllRecords(parcel, readInt - 8)) {
            parcel.setDataPosition(dataPosition);
            return false;
        }
        this.mParcel = parcel;
        return true;
    }

    public java.util.Set<java.lang.Integer> keySet() {
        return this.mKeyToPosMap.keySet();
    }

    public boolean has(int i) {
        if (!checkMetadataId(i)) {
            throw new java.lang.IllegalArgumentException("Invalid key: " + i);
        }
        return this.mKeyToPosMap.containsKey(java.lang.Integer.valueOf(i));
    }

    public java.lang.String getString(int i) {
        checkType(i, 1);
        return this.mParcel.readString();
    }

    public int getInt(int i) {
        checkType(i, 2);
        return this.mParcel.readInt();
    }

    public boolean getBoolean(int i) {
        checkType(i, 3);
        return this.mParcel.readInt() == 1;
    }

    public long getLong(int i) {
        checkType(i, 4);
        return this.mParcel.readLong();
    }

    public double getDouble(int i) {
        checkType(i, 5);
        return this.mParcel.readDouble();
    }

    public byte[] getByteArray(int i) {
        checkType(i, 7);
        return this.mParcel.createByteArray();
    }

    public java.util.Date getDate(int i) {
        checkType(i, 6);
        long readLong = this.mParcel.readLong();
        java.lang.String readString = this.mParcel.readString();
        if (readString.length() == 0) {
            return new java.util.Date(readLong);
        }
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone(readString));
        calendar.setTimeInMillis(readLong);
        return calendar.getTime();
    }

    public static int lastSytemId() {
        return 31;
    }

    public static int firstCustomId() {
        return 8192;
    }

    public static int lastType() {
        return 7;
    }

    private boolean checkMetadataId(int i) {
        if (i <= 0 || (31 < i && i < 8192)) {
            android.util.Log.e(TAG, "Invalid metadata ID " + i);
            return false;
        }
        return true;
    }

    private void checkType(int i, int i2) {
        this.mParcel.setDataPosition(this.mKeyToPosMap.get(java.lang.Integer.valueOf(i)).intValue());
        int readInt = this.mParcel.readInt();
        if (readInt != i2) {
            throw new java.lang.IllegalStateException("Wrong type " + i2 + " but got " + readInt);
        }
    }
}
