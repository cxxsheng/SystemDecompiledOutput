package android.media;

/* compiled from: ClosedCaptionRenderer.java */
/* loaded from: classes2.dex */
class Cea608CCParser {
    private static final int AOF = 34;
    private static final int AON = 35;
    private static final int BS = 33;
    private static final int CR = 45;
    private static final int DER = 36;
    private static final int EDM = 44;
    private static final int ENM = 46;
    private static final int EOC = 47;
    private static final int FON = 40;
    private static final int INVALID = -1;
    public static final int MAX_COLS = 32;
    public static final int MAX_ROWS = 15;
    private static final int MODE_PAINT_ON = 1;
    private static final int MODE_POP_ON = 3;
    private static final int MODE_ROLL_UP = 2;
    private static final int MODE_TEXT = 4;
    private static final int MODE_UNKNOWN = 0;
    private static final int RCL = 32;
    private static final int RDC = 41;
    private static final int RTD = 43;
    private static final int RU2 = 37;
    private static final int RU3 = 38;
    private static final int RU4 = 39;
    private static final int TR = 42;
    private static final char TS = 160;
    private final android.media.Cea608CCParser.DisplayListener mListener;
    private static final java.lang.String TAG = "Cea608CCParser";
    private static final boolean DEBUG = android.util.Log.isLoggable(TAG, 3);
    private int mMode = 1;
    private int mRollUpSize = 4;
    private int mPrevCtrlCode = -1;
    private android.media.Cea608CCParser.CCMemory mDisplay = new android.media.Cea608CCParser.CCMemory();
    private android.media.Cea608CCParser.CCMemory mNonDisplay = new android.media.Cea608CCParser.CCMemory();
    private android.media.Cea608CCParser.CCMemory mTextMem = new android.media.Cea608CCParser.CCMemory();

    /* compiled from: ClosedCaptionRenderer.java */
    interface DisplayListener {
        android.view.accessibility.CaptioningManager.CaptionStyle getCaptionStyle();

        void onDisplayChanged(android.text.SpannableStringBuilder[] spannableStringBuilderArr);
    }

    Cea608CCParser(android.media.Cea608CCParser.DisplayListener displayListener) {
        this.mListener = displayListener;
    }

    public void parse(byte[] bArr) {
        android.media.Cea608CCParser.CCData[] fromByteArray = android.media.Cea608CCParser.CCData.fromByteArray(bArr);
        for (int i = 0; i < fromByteArray.length; i++) {
            if (DEBUG) {
                android.util.Log.d(TAG, fromByteArray[i].toString());
            }
            if (!handleCtrlCode(fromByteArray[i]) && !handleTabOffsets(fromByteArray[i]) && !handlePACCode(fromByteArray[i]) && !handleMidRowCode(fromByteArray[i])) {
                handleDisplayableChars(fromByteArray[i]);
            }
        }
    }

    private android.media.Cea608CCParser.CCMemory getMemory() {
        switch (this.mMode) {
            case 1:
            case 2:
                return this.mDisplay;
            case 3:
                return this.mNonDisplay;
            case 4:
                return this.mTextMem;
            default:
                android.util.Log.w(TAG, "unrecoginized mode: " + this.mMode);
                return this.mDisplay;
        }
    }

    private boolean handleDisplayableChars(android.media.Cea608CCParser.CCData cCData) {
        if (!cCData.isDisplayableChar()) {
            return false;
        }
        if (cCData.isExtendedChar()) {
            getMemory().bs();
        }
        getMemory().writeText(cCData.getDisplayText());
        if (this.mMode == 1 || this.mMode == 2) {
            updateDisplay();
        }
        return true;
    }

    private boolean handleMidRowCode(android.media.Cea608CCParser.CCData cCData) {
        android.media.Cea608CCParser.StyleCode midRow = cCData.getMidRow();
        if (midRow != null) {
            getMemory().writeMidRowCode(midRow);
            return true;
        }
        return false;
    }

    private boolean handlePACCode(android.media.Cea608CCParser.CCData cCData) {
        android.media.Cea608CCParser.PAC pac = cCData.getPAC();
        if (pac != null) {
            if (this.mMode == 2) {
                getMemory().moveBaselineTo(pac.getRow(), this.mRollUpSize);
            }
            getMemory().writePAC(pac);
            return true;
        }
        return false;
    }

    private boolean handleTabOffsets(android.media.Cea608CCParser.CCData cCData) {
        int tabOffset = cCData.getTabOffset();
        if (tabOffset > 0) {
            getMemory().tab(tabOffset);
            return true;
        }
        return false;
    }

    private boolean handleCtrlCode(android.media.Cea608CCParser.CCData cCData) {
        int ctrlCode = cCData.getCtrlCode();
        if (this.mPrevCtrlCode != -1 && this.mPrevCtrlCode == ctrlCode) {
            this.mPrevCtrlCode = -1;
            return true;
        }
        switch (ctrlCode) {
            case 32:
                this.mMode = 3;
                break;
            case 33:
                getMemory().bs();
                break;
            case 34:
            case 35:
            default:
                this.mPrevCtrlCode = -1;
                return false;
            case 36:
                getMemory().der();
                break;
            case 37:
            case 38:
            case 39:
                this.mRollUpSize = ctrlCode - 35;
                if (this.mMode != 2) {
                    this.mDisplay.erase();
                    this.mNonDisplay.erase();
                }
                this.mMode = 2;
                break;
            case 40:
                android.util.Log.i(TAG, "Flash On");
                break;
            case 41:
                this.mMode = 1;
                break;
            case 42:
                this.mMode = 4;
                this.mTextMem.erase();
                break;
            case 43:
                this.mMode = 4;
                break;
            case 44:
                this.mDisplay.erase();
                updateDisplay();
                break;
            case 45:
                if (this.mMode == 2) {
                    getMemory().rollUp(this.mRollUpSize);
                } else {
                    getMemory().cr();
                }
                if (this.mMode == 2) {
                    updateDisplay();
                    break;
                }
                break;
            case 46:
                this.mNonDisplay.erase();
                break;
            case 47:
                swapMemory();
                this.mMode = 3;
                updateDisplay();
                break;
        }
        this.mPrevCtrlCode = ctrlCode;
        return true;
    }

    private void updateDisplay() {
        if (this.mListener != null) {
            this.mListener.onDisplayChanged(this.mDisplay.getStyledText(this.mListener.getCaptionStyle()));
        }
    }

    private void swapMemory() {
        android.media.Cea608CCParser.CCMemory cCMemory = this.mDisplay;
        this.mDisplay = this.mNonDisplay;
        this.mNonDisplay = cCMemory;
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class StyleCode {
        static final int COLOR_BLUE = 2;
        static final int COLOR_CYAN = 3;
        static final int COLOR_GREEN = 1;
        static final int COLOR_INVALID = 7;
        static final int COLOR_MAGENTA = 6;
        static final int COLOR_RED = 4;
        static final int COLOR_WHITE = 0;
        static final int COLOR_YELLOW = 5;
        static final int STYLE_ITALICS = 1;
        static final int STYLE_UNDERLINE = 2;
        static final java.lang.String[] mColorMap = {"WHITE", "GREEN", "BLUE", "CYAN", "RED", "YELLOW", "MAGENTA", "INVALID"};
        final int mColor;
        final int mStyle;

        static android.media.Cea608CCParser.StyleCode fromByte(byte b) {
            int i;
            int i2 = (b >> 1) & 7;
            if ((b & 1) == 0) {
                i = 0;
            } else {
                i = 2;
            }
            if (i2 == 7) {
                i |= 1;
                i2 = 0;
            }
            return new android.media.Cea608CCParser.StyleCode(i, i2);
        }

        StyleCode(int i, int i2) {
            this.mStyle = i;
            this.mColor = i2;
        }

        boolean isItalics() {
            return (this.mStyle & 1) != 0;
        }

        boolean isUnderline() {
            return (this.mStyle & 2) != 0;
        }

        int getColor() {
            return this.mColor;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("{");
            sb.append(mColorMap[this.mColor]);
            if ((this.mStyle & 1) != 0) {
                sb.append(", ITALICS");
            }
            if ((this.mStyle & 2) != 0) {
                sb.append(", UNDERLINE");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class PAC extends android.media.Cea608CCParser.StyleCode {
        final int mCol;
        final int mRow;

        static android.media.Cea608CCParser.PAC fromBytes(byte b, byte b2) {
            int i;
            int i2 = new int[]{11, 1, 3, 12, 14, 5, 7, 9}[b & 7] + ((b2 & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) >> 5);
            int i3 = 0;
            if ((b2 & 1) == 0) {
                i = 0;
            } else {
                i = 2;
            }
            if ((b2 & 16) == 0) {
                int i4 = (b2 >> 1) & 7;
                if (i4 != 7) {
                    i3 = i4;
                } else {
                    i |= 1;
                }
                return new android.media.Cea608CCParser.PAC(i2, -1, i, i3);
            }
            return new android.media.Cea608CCParser.PAC(i2, ((b2 >> 1) & 7) * 4, i, 0);
        }

        PAC(int i, int i2, int i3, int i4) {
            super(i3, i4);
            this.mRow = i;
            this.mCol = i2;
        }

        boolean isIndentPAC() {
            return this.mCol >= 0;
        }

        int getRow() {
            return this.mRow;
        }

        int getCol() {
            return this.mCol;
        }

        @Override // android.media.Cea608CCParser.StyleCode
        public java.lang.String toString() {
            return java.lang.String.format("{%d, %d}, %s", java.lang.Integer.valueOf(this.mRow), java.lang.Integer.valueOf(this.mCol), super.toString());
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    public static class MutableBackgroundColorSpan extends android.text.style.CharacterStyle implements android.text.style.UpdateAppearance {
        private int mColor;

        public MutableBackgroundColorSpan(int i) {
            this.mColor = i;
        }

        public void setBackgroundColor(int i) {
            this.mColor = i;
        }

        public int getBackgroundColor() {
            return this.mColor;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(android.text.TextPaint textPaint) {
            textPaint.bgColor = this.mColor;
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCLineBuilder {
        private final java.lang.StringBuilder mDisplayChars;
        private final android.media.Cea608CCParser.StyleCode[] mMidRowStyles;
        private final android.media.Cea608CCParser.StyleCode[] mPACStyles;

        CCLineBuilder(java.lang.String str) {
            this.mDisplayChars = new java.lang.StringBuilder(str);
            this.mMidRowStyles = new android.media.Cea608CCParser.StyleCode[this.mDisplayChars.length()];
            this.mPACStyles = new android.media.Cea608CCParser.StyleCode[this.mDisplayChars.length()];
        }

        void setCharAt(int i, char c) {
            this.mDisplayChars.setCharAt(i, c);
            this.mMidRowStyles[i] = null;
        }

        void setMidRowAt(int i, android.media.Cea608CCParser.StyleCode styleCode) {
            this.mDisplayChars.setCharAt(i, ' ');
            this.mMidRowStyles[i] = styleCode;
        }

        void setPACAt(int i, android.media.Cea608CCParser.PAC pac) {
            this.mPACStyles[i] = pac;
        }

        char charAt(int i) {
            return this.mDisplayChars.charAt(i);
        }

        int length() {
            return this.mDisplayChars.length();
        }

        void applyStyleSpan(android.text.SpannableStringBuilder spannableStringBuilder, android.media.Cea608CCParser.StyleCode styleCode, int i, int i2) {
            if (styleCode.isItalics()) {
                spannableStringBuilder.setSpan(new android.text.style.StyleSpan(2), i, i2, 33);
            }
            if (styleCode.isUnderline()) {
                spannableStringBuilder.setSpan(new android.text.style.UnderlineSpan(), i, i2, 33);
            }
        }

        android.text.SpannableStringBuilder getStyledText(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            android.media.Cea608CCParser.StyleCode styleCode;
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(this.mDisplayChars);
            int i = -1;
            int i2 = -1;
            android.media.Cea608CCParser.StyleCode styleCode2 = null;
            for (int i3 = 0; i3 < this.mDisplayChars.length(); i3++) {
                if (this.mMidRowStyles[i3] != null) {
                    styleCode = this.mMidRowStyles[i3];
                } else if (this.mPACStyles[i3] != null && (i < 0 || i2 < 0)) {
                    styleCode = this.mPACStyles[i3];
                } else {
                    styleCode = null;
                }
                if (styleCode != null) {
                    if (i >= 0 && i2 >= 0) {
                        applyStyleSpan(spannableStringBuilder, styleCode, i, i3);
                    }
                    i = i3;
                    styleCode2 = styleCode;
                }
                if (this.mDisplayChars.charAt(i3) != 160) {
                    if (i2 < 0) {
                        i2 = i3;
                    }
                } else if (i2 >= 0) {
                    if (this.mDisplayChars.charAt(i2) != ' ') {
                        i2--;
                    }
                    int i4 = this.mDisplayChars.charAt(i3 + (-1)) == ' ' ? i3 : i3 + 1;
                    spannableStringBuilder.setSpan(new android.media.Cea608CCParser.MutableBackgroundColorSpan(captionStyle.backgroundColor), i2, i4, 33);
                    if (i >= 0) {
                        applyStyleSpan(spannableStringBuilder, styleCode2, i, i4);
                    }
                    i2 = -1;
                }
            }
            return spannableStringBuilder;
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCMemory {
        private final java.lang.String mBlankLine;
        private int mCol;
        private final android.media.Cea608CCParser.CCLineBuilder[] mLines = new android.media.Cea608CCParser.CCLineBuilder[17];
        private int mRow;

        CCMemory() {
            char[] cArr = new char[34];
            java.util.Arrays.fill(cArr, android.media.Cea608CCParser.TS);
            this.mBlankLine = new java.lang.String(cArr);
        }

        void erase() {
            for (int i = 0; i < this.mLines.length; i++) {
                this.mLines[i] = null;
            }
            this.mRow = 15;
            this.mCol = 1;
        }

        void der() {
            if (this.mLines[this.mRow] != null) {
                for (int i = 0; i < this.mCol; i++) {
                    if (this.mLines[this.mRow].charAt(i) != 160) {
                        for (int i2 = this.mCol; i2 < this.mLines[this.mRow].length(); i2++) {
                            this.mLines[i2].setCharAt(i2, android.media.Cea608CCParser.TS);
                        }
                        return;
                    }
                }
                this.mLines[this.mRow] = null;
            }
        }

        void tab(int i) {
            moveCursorByCol(i);
        }

        void bs() {
            moveCursorByCol(-1);
            if (this.mLines[this.mRow] != null) {
                this.mLines[this.mRow].setCharAt(this.mCol, android.media.Cea608CCParser.TS);
                if (this.mCol == 31) {
                    this.mLines[this.mRow].setCharAt(32, android.media.Cea608CCParser.TS);
                }
            }
        }

        void cr() {
            moveCursorTo(this.mRow + 1, 1);
        }

        void rollUp(int i) {
            for (int i2 = 0; i2 <= this.mRow - i; i2++) {
                this.mLines[i2] = null;
            }
            int i3 = (this.mRow - i) + 1;
            if (i3 < 1) {
                i3 = 1;
            }
            while (i3 < this.mRow) {
                int i4 = i3 + 1;
                this.mLines[i3] = this.mLines[i4];
                i3 = i4;
            }
            for (int i5 = this.mRow; i5 < this.mLines.length; i5++) {
                this.mLines[i5] = null;
            }
            this.mCol = 1;
        }

        void writeText(java.lang.String str) {
            for (int i = 0; i < str.length(); i++) {
                getLineBuffer(this.mRow).setCharAt(this.mCol, str.charAt(i));
                moveCursorByCol(1);
            }
        }

        void writeMidRowCode(android.media.Cea608CCParser.StyleCode styleCode) {
            getLineBuffer(this.mRow).setMidRowAt(this.mCol, styleCode);
            moveCursorByCol(1);
        }

        void writePAC(android.media.Cea608CCParser.PAC pac) {
            if (pac.isIndentPAC()) {
                moveCursorTo(pac.getRow(), pac.getCol());
            } else {
                moveCursorTo(pac.getRow(), 1);
            }
            getLineBuffer(this.mRow).setPACAt(this.mCol, pac);
        }

        android.text.SpannableStringBuilder[] getStyledText(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            java.util.ArrayList arrayList = new java.util.ArrayList(15);
            for (int i = 1; i <= 15; i++) {
                arrayList.add(this.mLines[i] != null ? this.mLines[i].getStyledText(captionStyle) : null);
            }
            return (android.text.SpannableStringBuilder[]) arrayList.toArray(new android.text.SpannableStringBuilder[15]);
        }

        private static int clamp(int i, int i2, int i3) {
            return i < i2 ? i2 : i > i3 ? i3 : i;
        }

        private void moveCursorTo(int i, int i2) {
            this.mRow = clamp(i, 1, 15);
            this.mCol = clamp(i2, 1, 32);
        }

        private void moveCursorToRow(int i) {
            this.mRow = clamp(i, 1, 15);
        }

        private void moveCursorByCol(int i) {
            this.mCol = clamp(this.mCol + i, 1, 32);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void moveBaselineTo(int i, int i2) {
            int i3;
            if (this.mRow == i) {
                return;
            }
            if (i >= i2) {
                i3 = i2;
            } else {
                i3 = i;
            }
            if (this.mRow < i3) {
                i3 = this.mRow;
            }
            if (i < this.mRow) {
                for (int i4 = i3 - 1; i4 >= 0; i4--) {
                    this.mLines[i - i4] = this.mLines[this.mRow - i4];
                }
            } else {
                for (int i5 = 0; i5 < i3; i5++) {
                    this.mLines[i - i5] = this.mLines[this.mRow - i5];
                }
            }
            for (int i6 = 0; i6 <= i - i2; i6++) {
                this.mLines[i6] = null;
            }
            while (true) {
                i++;
                if (i < this.mLines.length) {
                    this.mLines[i] = null;
                } else {
                    return;
                }
            }
        }

        private android.media.Cea608CCParser.CCLineBuilder getLineBuffer(int i) {
            if (this.mLines[i] == null) {
                this.mLines[i] = new android.media.Cea608CCParser.CCLineBuilder(this.mBlankLine);
            }
            return this.mLines[i];
        }
    }

    /* compiled from: ClosedCaptionRenderer.java */
    private static class CCData {
        private final byte mData1;
        private final byte mData2;
        private final byte mType;
        private static final java.lang.String[] mCtrlCodeMap = {"RCL", "BS", "AOF", "AON", com.android.internal.org.bouncycastle.asn1.ASN1Encoding.DER, "RU2", "RU3", "RU4", "FON", "RDC", "TR", "RTD", "EDM", "CR", "ENM", "EOC"};
        private static final java.lang.String[] mSpecialCharMap = {"®", "°", "½", "¿", "™", "¢", "£", "♪", "à", " ", "è", "â", "ê", "î", "ô", "û"};
        private static final java.lang.String[] mSpanishCharMap = {"Á", "É", "Ó", "Ú", "Ü", "ü", "‘", "¡", "*", "'", "—", "©", "℠", "•", "“", "”", "À", "Â", "Ç", "È", "Ê", "Ë", "ë", "Î", "Ï", "ï", "Ô", "Ù", "ù", "Û", "«", "»"};
        private static final java.lang.String[] mProtugueseCharMap = {"Ã", "ã", "Í", "Ì", "ì", "Ò", "ò", "Õ", "õ", "{", "}", "\\", "^", android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD, android.util.NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER, "~", "Ä", "ä", "Ö", "ö", "ß", "¥", "¤", "│", "Å", "å", "Ø", "ø", "┌", "┐", "└", "┘"};

        static android.media.Cea608CCParser.CCData[] fromByteArray(byte[] bArr) {
            int length = bArr.length / 3;
            android.media.Cea608CCParser.CCData[] cCDataArr = new android.media.Cea608CCParser.CCData[length];
            for (int i = 0; i < length; i++) {
                int i2 = i * 3;
                cCDataArr[i] = new android.media.Cea608CCParser.CCData(bArr[i2], bArr[i2 + 1], bArr[i2 + 2]);
            }
            return cCDataArr;
        }

        CCData(byte b, byte b2, byte b3) {
            this.mType = b;
            this.mData1 = b2;
            this.mData2 = b3;
        }

        int getCtrlCode() {
            if ((this.mData1 == 20 || this.mData1 == 28) && this.mData2 >= 32 && this.mData2 <= 47) {
                return this.mData2;
            }
            return -1;
        }

        android.media.Cea608CCParser.StyleCode getMidRow() {
            if ((this.mData1 == 17 || this.mData1 == 25) && this.mData2 >= 32 && this.mData2 <= 47) {
                return android.media.Cea608CCParser.StyleCode.fromByte(this.mData2);
            }
            return null;
        }

        android.media.Cea608CCParser.PAC getPAC() {
            if ((this.mData1 & 112) != 16 || (this.mData2 & 64) != 64) {
                return null;
            }
            if ((this.mData1 & 7) != 0 || (this.mData2 & com.android.net.module.util.NetworkStackConstants.TCPHDR_URG) == 0) {
                return android.media.Cea608CCParser.PAC.fromBytes(this.mData1, this.mData2);
            }
            return null;
        }

        int getTabOffset() {
            if ((this.mData1 == 23 || this.mData1 == 31) && this.mData2 >= 33 && this.mData2 <= 35) {
                return this.mData2 & 3;
            }
            return 0;
        }

        boolean isDisplayableChar() {
            return isBasicChar() || isSpecialChar() || isExtendedChar();
        }

        java.lang.String getDisplayText() {
            java.lang.String basicChars = getBasicChars();
            if (basicChars == null) {
                java.lang.String specialChar = getSpecialChar();
                if (specialChar == null) {
                    return getExtendedChar();
                }
                return specialChar;
            }
            return basicChars;
        }

        private java.lang.String ctrlCodeToString(int i) {
            return mCtrlCodeMap[i - 32];
        }

        private boolean isBasicChar() {
            return this.mData1 >= 32 && this.mData1 <= Byte.MAX_VALUE;
        }

        private boolean isSpecialChar() {
            return (this.mData1 == 17 || this.mData1 == 25) && this.mData2 >= 48 && this.mData2 <= 63;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isExtendedChar() {
            return (this.mData1 == 18 || this.mData1 == 26 || this.mData1 == 19 || this.mData1 == 27) && this.mData2 >= 32 && this.mData2 <= 63;
        }

        private char getBasicChar(byte b) {
            switch (b) {
                case 42:
                    return (char) 225;
                case 92:
                    return (char) 233;
                case 94:
                    return (char) 237;
                case 95:
                    return (char) 243;
                case 96:
                    return (char) 250;
                case 123:
                    return (char) 231;
                case 124:
                    return (char) 247;
                case 125:
                    return (char) 209;
                case 126:
                    return (char) 241;
                case Byte.MAX_VALUE:
                    return (char) 9608;
                default:
                    return (char) b;
            }
        }

        private java.lang.String getBasicChars() {
            if (this.mData1 >= 32 && this.mData1 <= Byte.MAX_VALUE) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder(2);
                sb.append(getBasicChar(this.mData1));
                if (this.mData2 >= 32 && this.mData2 <= Byte.MAX_VALUE) {
                    sb.append(getBasicChar(this.mData2));
                }
                return sb.toString();
            }
            return null;
        }

        private java.lang.String getSpecialChar() {
            if ((this.mData1 == 17 || this.mData1 == 25) && this.mData2 >= 48 && this.mData2 <= 63) {
                return mSpecialCharMap[this.mData2 - 48];
            }
            return null;
        }

        private java.lang.String getExtendedChar() {
            if ((this.mData1 == 18 || this.mData1 == 26) && this.mData2 >= 32 && this.mData2 <= 63) {
                return mSpanishCharMap[this.mData2 - com.android.net.module.util.NetworkStackConstants.TCPHDR_URG];
            }
            if ((this.mData1 == 19 || this.mData1 == 27) && this.mData2 >= 32 && this.mData2 <= 63) {
                return mProtugueseCharMap[this.mData2 - com.android.net.module.util.NetworkStackConstants.TCPHDR_URG];
            }
            return null;
        }

        public java.lang.String toString() {
            if (this.mData1 < 16 && this.mData2 < 16) {
                return java.lang.String.format("[%d]Null: %02x %02x", java.lang.Byte.valueOf(this.mType), java.lang.Byte.valueOf(this.mData1), java.lang.Byte.valueOf(this.mData2));
            }
            int ctrlCode = getCtrlCode();
            if (ctrlCode != -1) {
                return java.lang.String.format("[%d]%s", java.lang.Byte.valueOf(this.mType), ctrlCodeToString(ctrlCode));
            }
            int tabOffset = getTabOffset();
            if (tabOffset > 0) {
                return java.lang.String.format("[%d]Tab%d", java.lang.Byte.valueOf(this.mType), java.lang.Integer.valueOf(tabOffset));
            }
            android.media.Cea608CCParser.PAC pac = getPAC();
            if (pac != null) {
                return java.lang.String.format("[%d]PAC: %s", java.lang.Byte.valueOf(this.mType), pac.toString());
            }
            android.media.Cea608CCParser.StyleCode midRow = getMidRow();
            if (midRow != null) {
                return java.lang.String.format("[%d]Mid-row: %s", java.lang.Byte.valueOf(this.mType), midRow.toString());
            }
            if (isDisplayableChar()) {
                return java.lang.String.format("[%d]Displayable: %s (%02x %02x)", java.lang.Byte.valueOf(this.mType), getDisplayText(), java.lang.Byte.valueOf(this.mData1), java.lang.Byte.valueOf(this.mData2));
            }
            return java.lang.String.format("[%d]Invalid: %02x %02x", java.lang.Byte.valueOf(this.mType), java.lang.Byte.valueOf(this.mData1), java.lang.Byte.valueOf(this.mData2));
        }
    }
}
