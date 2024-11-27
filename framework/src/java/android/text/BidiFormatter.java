package android.text;

/* loaded from: classes3.dex */
public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final java.lang.String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = 8234;
    private static final char PDF = 8236;
    private static final char RLE = 8235;
    private final android.text.TextDirectionHeuristic mDefaultTextDirectionHeuristic;
    private final int mFlags;
    private final boolean mIsRtlContext;
    private static android.text.TextDirectionHeuristic DEFAULT_TEXT_DIRECTION_HEURISTIC = android.text.TextDirectionHeuristics.FIRSTSTRONG_LTR;
    private static final char LRM = 8206;
    private static final java.lang.String LRM_STRING = java.lang.Character.toString(LRM);
    private static final char RLM = 8207;
    private static final java.lang.String RLM_STRING = java.lang.Character.toString(RLM);
    private static final android.text.BidiFormatter DEFAULT_LTR_INSTANCE = new android.text.BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    private static final android.text.BidiFormatter DEFAULT_RTL_INSTANCE = new android.text.BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private android.text.TextDirectionHeuristic mTextDirectionHeuristic;

        public Builder() {
            initialize(android.text.BidiFormatter.isRtlLocale(java.util.Locale.getDefault()));
        }

        public Builder(boolean z) {
            initialize(z);
        }

        public Builder(java.util.Locale locale) {
            initialize(android.text.BidiFormatter.isRtlLocale(locale));
        }

        private void initialize(boolean z) {
            this.mIsRtlContext = z;
            this.mTextDirectionHeuristic = android.text.BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public android.text.BidiFormatter.Builder stereoReset(boolean z) {
            if (z) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
            return this;
        }

        public android.text.BidiFormatter.Builder setTextDirectionHeuristic(android.text.TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDirectionHeuristic = textDirectionHeuristic;
            return this;
        }

        public android.text.BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristic == android.text.BidiFormatter.DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return android.text.BidiFormatter.getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new android.text.BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristic);
        }
    }

    public static android.text.BidiFormatter getInstance() {
        return getDefaultInstanceFromContext(isRtlLocale(java.util.Locale.getDefault()));
    }

    public static android.text.BidiFormatter getInstance(boolean z) {
        return getDefaultInstanceFromContext(z);
    }

    public static android.text.BidiFormatter getInstance(java.util.Locale locale) {
        return getDefaultInstanceFromContext(isRtlLocale(locale));
    }

    private BidiFormatter(boolean z, int i, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        this.mIsRtlContext = z;
        this.mFlags = i;
        this.mDefaultTextDirectionHeuristic = textDirectionHeuristic;
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public boolean getStereoReset() {
        return (this.mFlags & 2) != 0;
    }

    public java.lang.String markAfter(java.lang.CharSequence charSequence, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        boolean isRtl = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (isRtl || getExitDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (!this.mIsRtlContext) {
            return "";
        }
        if (!isRtl || getExitDir(charSequence) == -1) {
            return RLM_STRING;
        }
        return "";
    }

    public java.lang.String markBefore(java.lang.CharSequence charSequence, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        boolean isRtl = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (isRtl || getEntryDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (!this.mIsRtlContext) {
            return "";
        }
        if (!isRtl || getEntryDir(charSequence) == -1) {
            return RLM_STRING;
        }
        return "";
    }

    public boolean isRtl(java.lang.String str) {
        return isRtl((java.lang.CharSequence) str);
    }

    public boolean isRtl(java.lang.CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
    }

    public java.lang.String unicodeWrap(java.lang.String str, android.text.TextDirectionHeuristic textDirectionHeuristic, boolean z) {
        if (str == null) {
            return null;
        }
        return unicodeWrap((java.lang.CharSequence) str, textDirectionHeuristic, z).toString();
    }

    public java.lang.CharSequence unicodeWrap(java.lang.CharSequence charSequence, android.text.TextDirectionHeuristic textDirectionHeuristic, boolean z) {
        if (charSequence == null) {
            return null;
        }
        boolean isRtl = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
        if (getStereoReset() && z) {
            spannableStringBuilder.append((java.lang.CharSequence) markBefore(charSequence, isRtl ? android.text.TextDirectionHeuristics.RTL : android.text.TextDirectionHeuristics.LTR));
        }
        if (isRtl != this.mIsRtlContext) {
            spannableStringBuilder.append(isRtl ? RLE : LRE);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append(PDF);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z) {
            spannableStringBuilder.append((java.lang.CharSequence) markAfter(charSequence, isRtl ? android.text.TextDirectionHeuristics.RTL : android.text.TextDirectionHeuristics.LTR));
        }
        return spannableStringBuilder;
    }

    public java.lang.String unicodeWrap(java.lang.String str, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        return unicodeWrap(str, textDirectionHeuristic, true);
    }

    public java.lang.CharSequence unicodeWrap(java.lang.CharSequence charSequence, android.text.TextDirectionHeuristic textDirectionHeuristic) {
        return unicodeWrap(charSequence, textDirectionHeuristic, true);
    }

    public java.lang.String unicodeWrap(java.lang.String str, boolean z) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristic, z);
    }

    public java.lang.CharSequence unicodeWrap(java.lang.CharSequence charSequence, boolean z) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristic, z);
    }

    public java.lang.String unicodeWrap(java.lang.String str) {
        return unicodeWrap(str, this.mDefaultTextDirectionHeuristic, true);
    }

    public java.lang.CharSequence unicodeWrap(java.lang.CharSequence charSequence) {
        return unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristic, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.text.BidiFormatter getDefaultInstanceFromContext(boolean z) {
        return z ? DEFAULT_RTL_INSTANCE : DEFAULT_LTR_INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isRtlLocale(java.util.Locale locale) {
        return android.text.TextUtils.getLayoutDirectionFromLocale(locale) == 1;
    }

    private static int getExitDir(java.lang.CharSequence charSequence) {
        return new android.text.BidiFormatter.DirectionalityEstimator(charSequence, false).getExitDir();
    }

    private static int getEntryDir(java.lang.CharSequence charSequence) {
        return new android.text.BidiFormatter.DirectionalityEstimator(charSequence, false).getEntryDir();
    }

    public static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final java.lang.CharSequence text;

        static {
            for (int i = 0; i < 1792; i++) {
                DIR_TYPE_CACHE[i] = java.lang.Character.getDirectionality(i);
            }
        }

        public static byte getDirectionality(int i) {
            return java.lang.Character.getDirectionality(i);
        }

        DirectionalityEstimator(java.lang.CharSequence charSequence, boolean z) {
            this.text = charSequence;
            this.isHtml = z;
            this.length = charSequence.length();
        }

        int getEntryDir() {
            this.charIndex = 0;
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (this.charIndex < this.length && i == 0) {
                switch (dirTypeForward()) {
                    case 0:
                        if (i3 == 0) {
                            return -1;
                        }
                        break;
                    case 1:
                    case 2:
                        if (i3 == 0) {
                            return 1;
                        }
                        break;
                    case 9:
                    case 14:
                    case 15:
                        i3++;
                        i2 = -1;
                        continue;
                    case 16:
                    case 17:
                        i3++;
                        i2 = 1;
                        continue;
                    case 18:
                        i3--;
                        i2 = 0;
                        continue;
                }
                i = i3;
            }
            if (i == 0) {
                return 0;
            }
            if (i2 != 0) {
                return i2;
            }
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 14:
                    case 15:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return -1;
                        }
                    case 16:
                    case 17:
                        if (i != i3) {
                            i3--;
                            break;
                        } else {
                            return 1;
                        }
                    case 18:
                        i3++;
                        break;
                }
            }
            return 0;
        }

        int getExitDir() {
            this.charIndex = this.length;
            int i = 0;
            int i2 = 0;
            while (this.charIndex > 0) {
                switch (dirTypeBackward()) {
                    case 0:
                        if (i == 0) {
                            return -1;
                        }
                        if (i2 == 0) {
                            break;
                        }
                    case 1:
                    case 2:
                        if (i == 0) {
                            return 1;
                        }
                        if (i2 == 0) {
                            break;
                        }
                    case 9:
                    case 14:
                    case 15:
                        if (i2 == i) {
                            return -1;
                        }
                        i--;
                        continue;
                    case 16:
                    case 17:
                        if (i2 == i) {
                            return 1;
                        }
                        i--;
                        continue;
                    case 18:
                        i++;
                        continue;
                    default:
                        if (i2 == 0) {
                            break;
                        }
                }
                i2 = i;
            }
            return 0;
        }

        private static byte getCachedDirectionality(char c) {
            return c < 1792 ? DIR_TYPE_CACHE[c] : getDirectionality(c);
        }

        byte dirTypeForward() {
            this.lastChar = this.text.charAt(this.charIndex);
            if (java.lang.Character.isHighSurrogate(this.lastChar)) {
                int codePointAt = java.lang.Character.codePointAt(this.text, this.charIndex);
                this.charIndex += java.lang.Character.charCount(codePointAt);
                return getDirectionality(codePointAt);
            }
            this.charIndex++;
            byte cachedDirectionality = getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                if (this.lastChar == '<') {
                    return skipTagForward();
                }
                if (this.lastChar == '&') {
                    return skipEntityForward();
                }
                return cachedDirectionality;
            }
            return cachedDirectionality;
        }

        byte dirTypeBackward() {
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (java.lang.Character.isLowSurrogate(this.lastChar)) {
                int codePointBefore = java.lang.Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= java.lang.Character.charCount(codePointBefore);
                return getDirectionality(codePointBefore);
            }
            this.charIndex--;
            byte cachedDirectionality = getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                if (this.lastChar == '>') {
                    return skipTagBackward();
                }
                if (this.lastChar == ';') {
                    return skipEntityBackward();
                }
                return cachedDirectionality;
            }
            return cachedDirectionality;
        }

        private byte skipTagForward() {
            int i = this.charIndex;
            while (this.charIndex < this.length) {
                java.lang.CharSequence charSequence = this.text;
                int i2 = this.charIndex;
                this.charIndex = i2 + 1;
                this.lastChar = charSequence.charAt(i2);
                if (this.lastChar == '>') {
                    return (byte) 12;
                }
                if (this.lastChar == '\"' || this.lastChar == '\'') {
                    char c = this.lastChar;
                    while (this.charIndex < this.length) {
                        java.lang.CharSequence charSequence2 = this.text;
                        int i3 = this.charIndex;
                        this.charIndex = i3 + 1;
                        char charAt = charSequence2.charAt(i3);
                        this.lastChar = charAt;
                        if (charAt != c) {
                        }
                    }
                }
            }
            this.charIndex = i;
            this.lastChar = '<';
            return (byte) 13;
        }

        private byte skipTagBackward() {
            int i = this.charIndex;
            while (this.charIndex > 0) {
                java.lang.CharSequence charSequence = this.text;
                int i2 = this.charIndex - 1;
                this.charIndex = i2;
                this.lastChar = charSequence.charAt(i2);
                if (this.lastChar == '<') {
                    return (byte) 12;
                }
                if (this.lastChar == '>') {
                    break;
                }
                if (this.lastChar == '\"' || this.lastChar == '\'') {
                    char c = this.lastChar;
                    while (this.charIndex > 0) {
                        java.lang.CharSequence charSequence2 = this.text;
                        int i3 = this.charIndex - 1;
                        this.charIndex = i3;
                        char charAt = charSequence2.charAt(i3);
                        this.lastChar = charAt;
                        if (charAt != c) {
                        }
                    }
                }
            }
            this.charIndex = i;
            this.lastChar = '>';
            return (byte) 13;
        }

        private byte skipEntityForward() {
            while (this.charIndex < this.length) {
                java.lang.CharSequence charSequence = this.text;
                int i = this.charIndex;
                this.charIndex = i + 1;
                char charAt = charSequence.charAt(i);
                this.lastChar = charAt;
                if (charAt == ';') {
                    return (byte) 12;
                }
            }
            return (byte) 12;
        }

        private byte skipEntityBackward() {
            int i = this.charIndex;
            while (this.charIndex > 0) {
                java.lang.CharSequence charSequence = this.text;
                int i2 = this.charIndex - 1;
                this.charIndex = i2;
                this.lastChar = charSequence.charAt(i2);
                if (this.lastChar == '&') {
                    return (byte) 12;
                }
                if (this.lastChar == ';') {
                    break;
                }
            }
            this.charIndex = i;
            this.lastChar = ';';
            return (byte) 13;
        }
    }
}
