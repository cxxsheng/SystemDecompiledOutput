package android.media;

/* loaded from: classes2.dex */
public final class TimedText {
    private static final int FIRST_PRIVATE_KEY = 101;
    private static final int FIRST_PUBLIC_KEY = 1;
    private static final int KEY_BACKGROUND_COLOR_RGBA = 3;
    private static final int KEY_DISPLAY_FLAGS = 1;
    private static final int KEY_END_CHAR = 104;
    private static final int KEY_FONT_ID = 105;
    private static final int KEY_FONT_SIZE = 106;
    private static final int KEY_GLOBAL_SETTING = 101;
    private static final int KEY_HIGHLIGHT_COLOR_RGBA = 4;
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_SCROLL_DELAY = 5;
    private static final int KEY_START_CHAR = 103;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_BLINKING_TEXT_LIST = 8;
    private static final int KEY_STRUCT_FONT_LIST = 9;
    private static final int KEY_STRUCT_HIGHLIGHT_LIST = 10;
    private static final int KEY_STRUCT_HYPER_TEXT_LIST = 11;
    private static final int KEY_STRUCT_JUSTIFICATION = 15;
    private static final int KEY_STRUCT_KARAOKE_LIST = 12;
    private static final int KEY_STRUCT_STYLE_LIST = 13;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int KEY_STRUCT_TEXT_POS = 14;
    private static final int KEY_STYLE_FLAGS = 2;
    private static final int KEY_TEXT_COLOR_RGBA = 107;
    private static final int KEY_WRAP_TEXT = 6;
    private static final int LAST_PRIVATE_KEY = 107;
    private static final int LAST_PUBLIC_KEY = 16;
    private static final java.lang.String TAG = "TimedText";
    private int mBackgroundColorRGBA;
    private java.util.List<android.media.TimedText.CharPos> mBlinkingPosList;
    private int mDisplayFlags;
    private java.util.List<android.media.TimedText.Font> mFontList;
    private int mHighlightColorRGBA;
    private java.util.List<android.media.TimedText.CharPos> mHighlightPosList;
    private java.util.List<android.media.TimedText.HyperText> mHyperTextList;
    private android.media.TimedText.Justification mJustification;
    private java.util.List<android.media.TimedText.Karaoke> mKaraokeList;
    private final java.util.HashMap<java.lang.Integer, java.lang.Object> mKeyObjectMap;
    private int mScrollDelay;
    private java.util.List<android.media.TimedText.Style> mStyleList;
    private android.graphics.Rect mTextBounds;
    private java.lang.String mTextChars;
    private int mWrapText;

    public static final class CharPos {
        public final int endChar;
        public final int startChar;

        public CharPos(int i, int i2) {
            this.startChar = i;
            this.endChar = i2;
        }
    }

    public static final class Justification {
        public final int horizontalJustification;
        public final int verticalJustification;

        public Justification(int i, int i2) {
            this.horizontalJustification = i;
            this.verticalJustification = i2;
        }
    }

    public static final class Style {
        public final int colorRGBA;
        public final int endChar;
        public final int fontID;
        public final int fontSize;
        public final boolean isBold;
        public final boolean isItalic;
        public final boolean isUnderlined;
        public final int startChar;

        public Style(int i, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, int i5) {
            this.startChar = i;
            this.endChar = i2;
            this.fontID = i3;
            this.isBold = z;
            this.isItalic = z2;
            this.isUnderlined = z3;
            this.fontSize = i4;
            this.colorRGBA = i5;
        }
    }

    public static final class Font {
        public final int ID;
        public final java.lang.String name;

        public Font(int i, java.lang.String str) {
            this.ID = i;
            this.name = str;
        }
    }

    public static final class Karaoke {
        public final int endChar;
        public final int endTimeMs;
        public final int startChar;
        public final int startTimeMs;

        public Karaoke(int i, int i2, int i3, int i4) {
            this.startTimeMs = i;
            this.endTimeMs = i2;
            this.startChar = i3;
            this.endChar = i4;
        }
    }

    public static final class HyperText {
        public final java.lang.String URL;
        public final java.lang.String altString;
        public final int endChar;
        public final int startChar;

        public HyperText(int i, int i2, java.lang.String str, java.lang.String str2) {
            this.startChar = i;
            this.endChar = i2;
            this.URL = str;
            this.altString = str2;
        }
    }

    public TimedText(android.os.Parcel parcel) {
        this.mKeyObjectMap = new java.util.HashMap<>();
        this.mDisplayFlags = -1;
        this.mBackgroundColorRGBA = -1;
        this.mHighlightColorRGBA = -1;
        this.mScrollDelay = -1;
        this.mWrapText = -1;
        this.mBlinkingPosList = null;
        this.mHighlightPosList = null;
        this.mKaraokeList = null;
        this.mFontList = null;
        this.mStyleList = null;
        this.mHyperTextList = null;
        this.mTextBounds = null;
        this.mTextChars = null;
        if (!parseParcel(parcel)) {
            this.mKeyObjectMap.clear();
            throw new java.lang.IllegalArgumentException("parseParcel() fails");
        }
    }

    public TimedText(java.lang.String str, android.graphics.Rect rect) {
        this.mKeyObjectMap = new java.util.HashMap<>();
        this.mDisplayFlags = -1;
        this.mBackgroundColorRGBA = -1;
        this.mHighlightColorRGBA = -1;
        this.mScrollDelay = -1;
        this.mWrapText = -1;
        this.mBlinkingPosList = null;
        this.mHighlightPosList = null;
        this.mKaraokeList = null;
        this.mFontList = null;
        this.mStyleList = null;
        this.mHyperTextList = null;
        this.mTextBounds = null;
        this.mTextChars = null;
        this.mTextChars = str;
        this.mTextBounds = rect;
    }

    public java.lang.String getText() {
        return this.mTextChars;
    }

    public android.graphics.Rect getBounds() {
        return this.mTextBounds;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private boolean parseParcel(android.os.Parcel parcel) {
        java.lang.Object obj;
        parcel.setDataPosition(0);
        if (parcel.dataAvail() == 0) {
            return false;
        }
        int readInt = parcel.readInt();
        if (readInt == 102) {
            int readInt2 = parcel.readInt();
            if (readInt2 != 7) {
                return false;
            }
            this.mKeyObjectMap.put(java.lang.Integer.valueOf(readInt2), java.lang.Integer.valueOf(parcel.readInt()));
            if (parcel.readInt() != 16) {
                return false;
            }
            parcel.readInt();
            byte[] createByteArray = parcel.createByteArray();
            if (createByteArray == null || createByteArray.length == 0) {
                this.mTextChars = null;
            } else {
                this.mTextChars = new java.lang.String(createByteArray);
            }
        } else if (readInt != 101) {
            android.util.Log.w(TAG, "Invalid timed text key found: " + readInt);
            return false;
        }
        while (parcel.dataAvail() > 0) {
            int readInt3 = parcel.readInt();
            if (!isValidKey(readInt3)) {
                android.util.Log.w(TAG, "Invalid timed text key found: " + readInt3);
                return false;
            }
            switch (readInt3) {
                case 1:
                    this.mDisplayFlags = parcel.readInt();
                    obj = java.lang.Integer.valueOf(this.mDisplayFlags);
                    break;
                case 2:
                case 7:
                default:
                    obj = null;
                    break;
                case 3:
                    this.mBackgroundColorRGBA = parcel.readInt();
                    obj = java.lang.Integer.valueOf(this.mBackgroundColorRGBA);
                    break;
                case 4:
                    this.mHighlightColorRGBA = parcel.readInt();
                    obj = java.lang.Integer.valueOf(this.mHighlightColorRGBA);
                    break;
                case 5:
                    this.mScrollDelay = parcel.readInt();
                    obj = java.lang.Integer.valueOf(this.mScrollDelay);
                    break;
                case 6:
                    this.mWrapText = parcel.readInt();
                    obj = java.lang.Integer.valueOf(this.mWrapText);
                    break;
                case 8:
                    readBlinkingText(parcel);
                    obj = this.mBlinkingPosList;
                    break;
                case 9:
                    readFont(parcel);
                    obj = this.mFontList;
                    break;
                case 10:
                    readHighlight(parcel);
                    obj = this.mHighlightPosList;
                    break;
                case 11:
                    readHyperText(parcel);
                    obj = this.mHyperTextList;
                    break;
                case 12:
                    readKaraoke(parcel);
                    obj = this.mKaraokeList;
                    break;
                case 13:
                    readStyle(parcel);
                    obj = this.mStyleList;
                    break;
                case 14:
                    this.mTextBounds = new android.graphics.Rect(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    obj = null;
                    break;
                case 15:
                    this.mJustification = new android.media.TimedText.Justification(parcel.readInt(), parcel.readInt());
                    obj = this.mJustification;
                    break;
            }
            if (obj != null) {
                if (this.mKeyObjectMap.containsKey(java.lang.Integer.valueOf(readInt3))) {
                    this.mKeyObjectMap.remove(java.lang.Integer.valueOf(readInt3));
                }
                this.mKeyObjectMap.put(java.lang.Integer.valueOf(readInt3), obj);
            }
        }
        return true;
    }

    private void readStyle(android.os.Parcel parcel) {
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        int i = -1;
        int i2 = -1;
        int i3 = -1;
        int i4 = -1;
        int i5 = -1;
        boolean z4 = false;
        while (!z4 && parcel.dataAvail() > 0) {
            switch (parcel.readInt()) {
                case 2:
                    int readInt = parcel.readInt();
                    if (readInt % 2 != 1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (readInt % 4 < 2) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (readInt / 4 != 1) {
                        z3 = false;
                        break;
                    } else {
                        z3 = true;
                        break;
                    }
                case 103:
                    i = parcel.readInt();
                    break;
                case 104:
                    i2 = parcel.readInt();
                    break;
                case 105:
                    i3 = parcel.readInt();
                    break;
                case 106:
                    i4 = parcel.readInt();
                    break;
                case 107:
                    i5 = parcel.readInt();
                    break;
                default:
                    parcel.setDataPosition(parcel.dataPosition() - 4);
                    z4 = true;
                    break;
            }
        }
        android.media.TimedText.Style style = new android.media.TimedText.Style(i, i2, i3, z, z2, z3, i4, i5);
        if (this.mStyleList == null) {
            this.mStyleList = new java.util.ArrayList();
        }
        this.mStyleList.add(style);
    }

    private void readFont(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            android.media.TimedText.Font font = new android.media.TimedText.Font(parcel.readInt(), new java.lang.String(parcel.createByteArray(), 0, parcel.readInt()));
            if (this.mFontList == null) {
                this.mFontList = new java.util.ArrayList();
            }
            this.mFontList.add(font);
        }
    }

    private void readHighlight(android.os.Parcel parcel) {
        android.media.TimedText.CharPos charPos = new android.media.TimedText.CharPos(parcel.readInt(), parcel.readInt());
        if (this.mHighlightPosList == null) {
            this.mHighlightPosList = new java.util.ArrayList();
        }
        this.mHighlightPosList.add(charPos);
    }

    private void readKaraoke(android.os.Parcel parcel) {
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            android.media.TimedText.Karaoke karaoke = new android.media.TimedText.Karaoke(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
            if (this.mKaraokeList == null) {
                this.mKaraokeList = new java.util.ArrayList();
            }
            this.mKaraokeList.add(karaoke);
        }
    }

    private void readHyperText(android.os.Parcel parcel) {
        android.media.TimedText.HyperText hyperText = new android.media.TimedText.HyperText(parcel.readInt(), parcel.readInt(), new java.lang.String(parcel.createByteArray(), 0, parcel.readInt()), new java.lang.String(parcel.createByteArray(), 0, parcel.readInt()));
        if (this.mHyperTextList == null) {
            this.mHyperTextList = new java.util.ArrayList();
        }
        this.mHyperTextList.add(hyperText);
    }

    private void readBlinkingText(android.os.Parcel parcel) {
        android.media.TimedText.CharPos charPos = new android.media.TimedText.CharPos(parcel.readInt(), parcel.readInt());
        if (this.mBlinkingPosList == null) {
            this.mBlinkingPosList = new java.util.ArrayList();
        }
        this.mBlinkingPosList.add(charPos);
    }

    private boolean isValidKey(int i) {
        if ((i >= 1 && i <= 16) || (i >= 101 && i <= 107)) {
            return true;
        }
        return false;
    }

    private boolean containsKey(int i) {
        if (isValidKey(i) && this.mKeyObjectMap.containsKey(java.lang.Integer.valueOf(i))) {
            return true;
        }
        return false;
    }

    private java.util.Set keySet() {
        return this.mKeyObjectMap.keySet();
    }

    private java.lang.Object getObject(int i) {
        if (containsKey(i)) {
            return this.mKeyObjectMap.get(java.lang.Integer.valueOf(i));
        }
        throw new java.lang.IllegalArgumentException("Invalid key: " + i);
    }
}
