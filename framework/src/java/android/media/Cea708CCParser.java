package android.media;

/* compiled from: Cea708CaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea708CCParser {
    public static final int CAPTION_EMIT_TYPE_BUFFER = 1;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CLW = 4;
    public static final int CAPTION_EMIT_TYPE_COMMAND_CWX = 3;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DFX = 16;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLC = 10;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLW = 8;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DLY = 9;
    public static final int CAPTION_EMIT_TYPE_COMMAND_DSW = 5;
    public static final int CAPTION_EMIT_TYPE_COMMAND_HDW = 6;
    public static final int CAPTION_EMIT_TYPE_COMMAND_RST = 11;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPA = 12;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPC = 13;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SPL = 14;
    public static final int CAPTION_EMIT_TYPE_COMMAND_SWA = 15;
    public static final int CAPTION_EMIT_TYPE_COMMAND_TGW = 7;
    public static final int CAPTION_EMIT_TYPE_CONTROL = 2;
    private static final boolean DEBUG = false;
    private static final java.lang.String MUSIC_NOTE_CHAR = new java.lang.String("♫".getBytes(java.nio.charset.StandardCharsets.UTF_8), java.nio.charset.StandardCharsets.UTF_8);
    private static final java.lang.String TAG = "Cea708CCParser";
    private final java.lang.StringBuffer mBuffer = new java.lang.StringBuffer();
    private int mCommand = 0;
    private android.media.Cea708CCParser.DisplayListener mListener;

    /* compiled from: Cea708CaptionRenderer.java */
    interface DisplayListener {
        void emitEvent(android.media.Cea708CCParser.CaptionEvent captionEvent);
    }

    Cea708CCParser(android.media.Cea708CCParser.DisplayListener displayListener) {
        this.mListener = new android.media.Cea708CCParser.DisplayListener() { // from class: android.media.Cea708CCParser.1
            @Override // android.media.Cea708CCParser.DisplayListener
            public void emitEvent(android.media.Cea708CCParser.CaptionEvent captionEvent) {
            }
        };
        if (displayListener != null) {
            this.mListener = displayListener;
        }
    }

    private void emitCaptionEvent(android.media.Cea708CCParser.CaptionEvent captionEvent) {
        emitCaptionBuffer();
        this.mListener.emitEvent(captionEvent);
    }

    private void emitCaptionBuffer() {
        if (this.mBuffer.length() > 0) {
            this.mListener.emitEvent(new android.media.Cea708CCParser.CaptionEvent(1, this.mBuffer.toString()));
            this.mBuffer.setLength(0);
        }
    }

    public void parse(byte[] bArr) {
        int i = 0;
        while (i < bArr.length) {
            i = parseServiceBlockData(bArr, i);
        }
        emitCaptionBuffer();
    }

    private int parseServiceBlockData(byte[] bArr, int i) {
        this.mCommand = bArr[i] & 255;
        int i2 = i + 1;
        if (this.mCommand == 16) {
            return parseExt1(bArr, i2);
        }
        if (this.mCommand >= 0 && this.mCommand <= 31) {
            return parseC0(bArr, i2);
        }
        if (this.mCommand >= 128 && this.mCommand <= 159) {
            return parseC1(bArr, i2);
        }
        if (this.mCommand >= 32 && this.mCommand <= 127) {
            return parseG0(bArr, i2);
        }
        if (this.mCommand >= 160 && this.mCommand <= 255) {
            return parseG1(bArr, i2);
        }
        return i2;
    }

    private int parseC0(byte[] bArr, int i) {
        if (this.mCommand >= 24 && this.mCommand <= 31) {
            if (this.mCommand == 24) {
                try {
                    if (bArr[i] == 0) {
                        this.mBuffer.append((char) bArr[i + 1]);
                    } else {
                        this.mBuffer.append(new java.lang.String(java.util.Arrays.copyOfRange(bArr, i, i + 2), com.google.android.mms.pdu.CharacterSets.MIMENAME_EUC_KR));
                    }
                } catch (java.io.UnsupportedEncodingException e) {
                    android.util.Log.e(TAG, "P16 Code - Could not find supported encoding", e);
                }
            }
            return i + 2;
        }
        if (this.mCommand >= 16 && this.mCommand <= 23) {
            return i + 1;
        }
        switch (this.mCommand) {
            case 0:
            default:
                return i;
            case 3:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(2, java.lang.Character.valueOf((char) this.mCommand)));
                return i;
            case 8:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(2, java.lang.Character.valueOf((char) this.mCommand)));
                return i;
            case 12:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(2, java.lang.Character.valueOf((char) this.mCommand)));
                return i;
            case 13:
                this.mBuffer.append('\n');
                return i;
            case 14:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(2, java.lang.Character.valueOf((char) this.mCommand)));
                return i;
        }
    }

    private int parseC1(byte[] bArr, int i) {
        switch (this.mCommand) {
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(3, java.lang.Integer.valueOf(this.mCommand - 128)));
                break;
            case 136:
                int i2 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(4, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i2;
            case 137:
                int i3 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(5, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i3;
            case 138:
                int i4 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(6, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i4;
            case 139:
                int i5 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(7, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i5;
            case 140:
                int i6 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(8, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i6;
            case 141:
                int i7 = i + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(9, java.lang.Integer.valueOf(bArr[i] & 255)));
                return i7;
            case 142:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(10, null));
                break;
            case 143:
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(11, null));
                break;
            case 144:
                int i8 = (bArr[i] & 240) >> 4;
                int i9 = bArr[i] & 3;
                int i10 = (bArr[i] & 12) >> 2;
                int i11 = i + 1;
                boolean z = (bArr[i11] & 128) != 0;
                boolean z2 = (bArr[i11] & 64) != 0;
                int i12 = (bArr[i11] & 56) >> 3;
                int i13 = bArr[i11] & 7;
                int i14 = i + 2;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(12, new android.media.Cea708CCParser.CaptionPenAttr(i9, i10, i8, i13, i12, z2, z)));
                return i14;
            case 145:
                android.media.Cea708CCParser.CaptionColor captionColor = new android.media.Cea708CCParser.CaptionColor((bArr[i] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (bArr[i] & 48) >> 4, (bArr[i] & 12) >> 2, bArr[i] & 3);
                int i15 = i + 1;
                android.media.Cea708CCParser.CaptionColor captionColor2 = new android.media.Cea708CCParser.CaptionColor((bArr[i15] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (bArr[i15] & 48) >> 4, (bArr[i15] & 12) >> 2, bArr[i15] & 3);
                int i16 = i15 + 1;
                android.media.Cea708CCParser.CaptionColor captionColor3 = new android.media.Cea708CCParser.CaptionColor(0, (bArr[i16] & 48) >> 4, (bArr[i16] & 12) >> 2, bArr[i16] & 3);
                int i17 = i16 + 1;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(13, new android.media.Cea708CCParser.CaptionPenColor(captionColor, captionColor2, captionColor3)));
                return i17;
            case 146:
                int i18 = i + 2;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(14, new android.media.Cea708CCParser.CaptionPenLocation(bArr[i] & 15, bArr[i + 1] & 63)));
                return i18;
            case 151:
                android.media.Cea708CCParser.CaptionColor captionColor4 = new android.media.Cea708CCParser.CaptionColor((bArr[i] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >> 6, (bArr[i] & 48) >> 4, (bArr[i] & 12) >> 2, bArr[i] & 3);
                int i19 = i + 1;
                int i20 = i + 2;
                int i21 = ((bArr[i19] & com.android.internal.midi.MidiConstants.STATUS_PROGRAM_CHANGE) >> 6) | ((bArr[i20] & 128) >> 5);
                android.media.Cea708CCParser.CaptionColor captionColor5 = new android.media.Cea708CCParser.CaptionColor(0, (bArr[i19] & 48) >> 4, (bArr[i19] & 12) >> 2, bArr[i19] & 3);
                boolean z3 = (bArr[i20] & 64) != 0;
                int i22 = (bArr[i20] & 48) >> 4;
                int i23 = (bArr[i20] & 12) >> 2;
                int i24 = bArr[i20] & 3;
                int i25 = i + 3;
                int i26 = (bArr[i25] & 240) >> 4;
                int i27 = (bArr[i25] & 12) >> 2;
                int i28 = bArr[i25] & 3;
                int i29 = i + 4;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(15, new android.media.Cea708CCParser.CaptionWindowAttr(captionColor4, captionColor5, i21, z3, i22, i23, i24, i27, i26, i28)));
                return i29;
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
                int i30 = this.mCommand - 152;
                boolean z4 = (bArr[i] & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) != 0;
                boolean z5 = (bArr[i] & 16) != 0;
                boolean z6 = (bArr[i] & 8) != 0;
                int i31 = bArr[i] & 7;
                int i32 = i + 1;
                boolean z7 = (bArr[i32] & 128) != 0;
                int i33 = bArr[i32] & Byte.MAX_VALUE;
                int i34 = bArr[i + 2] & 255;
                int i35 = i + 3;
                int i36 = (bArr[i35] & 240) >> 4;
                int i37 = bArr[i35] & 15;
                int i38 = bArr[i + 4] & 63;
                int i39 = i + 5;
                int i40 = (bArr[i39] & 56) >> 3;
                int i41 = bArr[i39] & 7;
                int i42 = i + 6;
                emitCaptionEvent(new android.media.Cea708CCParser.CaptionEvent(16, new android.media.Cea708CCParser.CaptionWindow(i30, z4, z5, z6, i31, z7, i33, i34, i36, i37, i38, i41, i40)));
                return i42;
        }
        return i;
    }

    private int parseG0(byte[] bArr, int i) {
        if (this.mCommand == 127) {
            this.mBuffer.append(MUSIC_NOTE_CHAR);
        } else {
            this.mBuffer.append((char) this.mCommand);
        }
        return i;
    }

    private int parseG1(byte[] bArr, int i) {
        this.mBuffer.append((char) this.mCommand);
        return i;
    }

    private int parseExt1(byte[] bArr, int i) {
        this.mCommand = bArr[i] & 255;
        int i2 = i + 1;
        if (this.mCommand >= 0 && this.mCommand <= 31) {
            return parseC2(bArr, i2);
        }
        if (this.mCommand >= 128 && this.mCommand <= 159) {
            return parseC3(bArr, i2);
        }
        if (this.mCommand >= 32 && this.mCommand <= 127) {
            return parseG2(bArr, i2);
        }
        if (this.mCommand >= 160 && this.mCommand <= 255) {
            return parseG3(bArr, i2);
        }
        return i2;
    }

    private int parseC2(byte[] bArr, int i) {
        if (this.mCommand < 0 || this.mCommand > 7) {
            if (this.mCommand >= 8 && this.mCommand <= 15) {
                return i + 1;
            }
            if (this.mCommand >= 16 && this.mCommand <= 23) {
                return i + 2;
            }
            if (this.mCommand >= 24 && this.mCommand <= 31) {
                return i + 3;
            }
            return i;
        }
        return i;
    }

    private int parseC3(byte[] bArr, int i) {
        if (this.mCommand >= 128 && this.mCommand <= 135) {
            return i + 4;
        }
        if (this.mCommand >= 136 && this.mCommand <= 143) {
            return i + 5;
        }
        return i;
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0002. Please report as an issue. */
    private int parseG2(byte[] bArr, int i) {
        switch (this.mCommand) {
        }
        return i;
    }

    private int parseG3(byte[] bArr, int i) {
        return i;
    }

    /* compiled from: Cea708CaptionRenderer.java */
    private static class Const {
        public static final int CODE_C0_BS = 8;
        public static final int CODE_C0_CR = 13;
        public static final int CODE_C0_ETX = 3;
        public static final int CODE_C0_EXT1 = 16;
        public static final int CODE_C0_FF = 12;
        public static final int CODE_C0_HCR = 14;
        public static final int CODE_C0_NUL = 0;
        public static final int CODE_C0_P16 = 24;
        public static final int CODE_C0_RANGE_END = 31;
        public static final int CODE_C0_RANGE_START = 0;
        public static final int CODE_C0_SKIP1_RANGE_END = 23;
        public static final int CODE_C0_SKIP1_RANGE_START = 16;
        public static final int CODE_C0_SKIP2_RANGE_END = 31;
        public static final int CODE_C0_SKIP2_RANGE_START = 24;
        public static final int CODE_C1_CLW = 136;
        public static final int CODE_C1_CW0 = 128;
        public static final int CODE_C1_CW1 = 129;
        public static final int CODE_C1_CW2 = 130;
        public static final int CODE_C1_CW3 = 131;
        public static final int CODE_C1_CW4 = 132;
        public static final int CODE_C1_CW5 = 133;
        public static final int CODE_C1_CW6 = 134;
        public static final int CODE_C1_CW7 = 135;
        public static final int CODE_C1_DF0 = 152;
        public static final int CODE_C1_DF1 = 153;
        public static final int CODE_C1_DF2 = 154;
        public static final int CODE_C1_DF3 = 155;
        public static final int CODE_C1_DF4 = 156;
        public static final int CODE_C1_DF5 = 157;
        public static final int CODE_C1_DF6 = 158;
        public static final int CODE_C1_DF7 = 159;
        public static final int CODE_C1_DLC = 142;
        public static final int CODE_C1_DLW = 140;
        public static final int CODE_C1_DLY = 141;
        public static final int CODE_C1_DSW = 137;
        public static final int CODE_C1_HDW = 138;
        public static final int CODE_C1_RANGE_END = 159;
        public static final int CODE_C1_RANGE_START = 128;
        public static final int CODE_C1_RST = 143;
        public static final int CODE_C1_SPA = 144;
        public static final int CODE_C1_SPC = 145;
        public static final int CODE_C1_SPL = 146;
        public static final int CODE_C1_SWA = 151;
        public static final int CODE_C1_TGW = 139;
        public static final int CODE_C2_RANGE_END = 31;
        public static final int CODE_C2_RANGE_START = 0;
        public static final int CODE_C2_SKIP0_RANGE_END = 7;
        public static final int CODE_C2_SKIP0_RANGE_START = 0;
        public static final int CODE_C2_SKIP1_RANGE_END = 15;
        public static final int CODE_C2_SKIP1_RANGE_START = 8;
        public static final int CODE_C2_SKIP2_RANGE_END = 23;
        public static final int CODE_C2_SKIP2_RANGE_START = 16;
        public static final int CODE_C2_SKIP3_RANGE_END = 31;
        public static final int CODE_C2_SKIP3_RANGE_START = 24;
        public static final int CODE_C3_RANGE_END = 159;
        public static final int CODE_C3_RANGE_START = 128;
        public static final int CODE_C3_SKIP4_RANGE_END = 135;
        public static final int CODE_C3_SKIP4_RANGE_START = 128;
        public static final int CODE_C3_SKIP5_RANGE_END = 143;
        public static final int CODE_C3_SKIP5_RANGE_START = 136;
        public static final int CODE_G0_MUSICNOTE = 127;
        public static final int CODE_G0_RANGE_END = 127;
        public static final int CODE_G0_RANGE_START = 32;
        public static final int CODE_G1_RANGE_END = 255;
        public static final int CODE_G1_RANGE_START = 160;
        public static final int CODE_G2_BLK = 48;
        public static final int CODE_G2_NBTSP = 33;
        public static final int CODE_G2_RANGE_END = 127;
        public static final int CODE_G2_RANGE_START = 32;
        public static final int CODE_G2_TSP = 32;
        public static final int CODE_G3_CC = 160;
        public static final int CODE_G3_RANGE_END = 255;
        public static final int CODE_G3_RANGE_START = 160;

        private Const() {
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionColor {
        public static final int OPACITY_FLASH = 1;
        public static final int OPACITY_SOLID = 0;
        public static final int OPACITY_TRANSLUCENT = 2;
        public static final int OPACITY_TRANSPARENT = 3;
        public final int blue;
        public final int green;
        public final int opacity;
        public final int red;
        private static final int[] COLOR_MAP = {0, 15, 240, 255};
        private static final int[] OPACITY_MAP = {255, 254, 128, 0};

        public CaptionColor(int i, int i2, int i3, int i4) {
            this.opacity = i;
            this.red = i2;
            this.green = i3;
            this.blue = i4;
        }

        public int getArgbValue() {
            return android.graphics.Color.argb(OPACITY_MAP[this.opacity], COLOR_MAP[this.red], COLOR_MAP[this.green], COLOR_MAP[this.blue]);
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionEvent {
        public final java.lang.Object obj;
        public final int type;

        public CaptionEvent(int i, java.lang.Object obj) {
            this.type = i;
            this.obj = obj;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenAttr {
        public static final int OFFSET_NORMAL = 1;
        public static final int OFFSET_SUBSCRIPT = 0;
        public static final int OFFSET_SUPERSCRIPT = 2;
        public static final int PEN_SIZE_LARGE = 2;
        public static final int PEN_SIZE_SMALL = 0;
        public static final int PEN_SIZE_STANDARD = 1;
        public final int edgeType;
        public final int fontTag;
        public final boolean italic;
        public final int penOffset;
        public final int penSize;
        public final int textTag;
        public final boolean underline;

        public CaptionPenAttr(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
            this.penSize = i;
            this.penOffset = i2;
            this.textTag = i3;
            this.fontTag = i4;
            this.edgeType = i5;
            this.underline = z;
            this.italic = z2;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenColor {
        public final android.media.Cea708CCParser.CaptionColor backgroundColor;
        public final android.media.Cea708CCParser.CaptionColor edgeColor;
        public final android.media.Cea708CCParser.CaptionColor foregroundColor;

        public CaptionPenColor(android.media.Cea708CCParser.CaptionColor captionColor, android.media.Cea708CCParser.CaptionColor captionColor2, android.media.Cea708CCParser.CaptionColor captionColor3) {
            this.foregroundColor = captionColor;
            this.backgroundColor = captionColor2;
            this.edgeColor = captionColor3;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionPenLocation {
        public final int column;
        public final int row;

        public CaptionPenLocation(int i, int i2) {
            this.row = i;
            this.column = i2;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionWindowAttr {
        public final android.media.Cea708CCParser.CaptionColor borderColor;
        public final int borderType;
        public final int displayEffect;
        public final int effectDirection;
        public final int effectSpeed;
        public final android.media.Cea708CCParser.CaptionColor fillColor;
        public final int justify;
        public final int printDirection;
        public final int scrollDirection;
        public final boolean wordWrap;

        public CaptionWindowAttr(android.media.Cea708CCParser.CaptionColor captionColor, android.media.Cea708CCParser.CaptionColor captionColor2, int i, boolean z, int i2, int i3, int i4, int i5, int i6, int i7) {
            this.fillColor = captionColor;
            this.borderColor = captionColor2;
            this.borderType = i;
            this.wordWrap = z;
            this.printDirection = i2;
            this.scrollDirection = i3;
            this.justify = i4;
            this.effectDirection = i5;
            this.effectSpeed = i6;
            this.displayEffect = i7;
        }
    }

    /* compiled from: Cea708CaptionRenderer.java */
    public static class CaptionWindow {
        public final int anchorHorizontal;
        public final int anchorId;
        public final int anchorVertical;
        public final int columnCount;
        public final boolean columnLock;
        public final int id;
        public final int penStyle;
        public final int priority;
        public final boolean relativePositioning;
        public final int rowCount;
        public final boolean rowLock;
        public final boolean visible;
        public final int windowStyle;

        public CaptionWindow(int i, boolean z, boolean z2, boolean z3, int i2, boolean z4, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
            this.id = i;
            this.visible = z;
            this.rowLock = z2;
            this.columnLock = z3;
            this.priority = i2;
            this.relativePositioning = z4;
            this.anchorVertical = i3;
            this.anchorHorizontal = i4;
            this.anchorId = i5;
            this.rowCount = i6;
            this.columnCount = i7;
            this.penStyle = i8;
            this.windowStyle = i9;
        }
    }
}
