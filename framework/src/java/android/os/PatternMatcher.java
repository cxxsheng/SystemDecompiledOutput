package android.os;

/* loaded from: classes3.dex */
public class PatternMatcher implements android.os.Parcelable {
    private static final int MAX_PATTERN_STORAGE = 2048;
    private static final int NO_MATCH = -1;
    private static final int PARSED_MODIFIER_ONE_OR_MORE = -8;
    private static final int PARSED_MODIFIER_RANGE_START = -5;
    private static final int PARSED_MODIFIER_RANGE_STOP = -6;
    private static final int PARSED_MODIFIER_ZERO_OR_MORE = -7;
    private static final int PARSED_TOKEN_CHAR_ANY = -4;
    private static final int PARSED_TOKEN_CHAR_SET_INVERSE_START = -2;
    private static final int PARSED_TOKEN_CHAR_SET_START = -1;
    private static final int PARSED_TOKEN_CHAR_SET_STOP = -3;
    public static final int PATTERN_ADVANCED_GLOB = 3;
    public static final int PATTERN_LITERAL = 0;
    public static final int PATTERN_PREFIX = 1;
    public static final int PATTERN_SIMPLE_GLOB = 2;
    public static final int PATTERN_SUFFIX = 4;
    private static final java.lang.String TAG = "PatternMatcher";
    private static final int TOKEN_TYPE_ANY = 1;
    private static final int TOKEN_TYPE_INVERSE_SET = 3;
    private static final int TOKEN_TYPE_LITERAL = 0;
    private static final int TOKEN_TYPE_SET = 2;
    private final int[] mParsedPattern;
    private final java.lang.String mPattern;
    private final int mType;
    private static final int[] sParsedPatternScratch = new int[2048];
    public static final android.os.Parcelable.Creator<android.os.PatternMatcher> CREATOR = new android.os.Parcelable.Creator<android.os.PatternMatcher>() { // from class: android.os.PatternMatcher.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PatternMatcher createFromParcel(android.os.Parcel parcel) {
            return new android.os.PatternMatcher(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.PatternMatcher[] newArray(int i) {
            return new android.os.PatternMatcher[i];
        }
    };

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface PatternType {
    }

    public PatternMatcher(java.lang.String str, int i) {
        this.mPattern = str;
        this.mType = i;
        if (this.mType == 3) {
            this.mParsedPattern = parseAndVerifyAdvancedPattern(str);
        } else {
            this.mParsedPattern = null;
        }
    }

    public final java.lang.String getPath() {
        return this.mPattern;
    }

    public final int getType() {
        return this.mType;
    }

    public boolean match(java.lang.String str) {
        return matchPattern(str, this.mPattern, this.mParsedPattern, this.mType);
    }

    public java.lang.String toString() {
        java.lang.String str;
        switch (this.mType) {
            case 0:
                str = "LITERAL: ";
                break;
            case 1:
                str = "PREFIX: ";
                break;
            case 2:
                str = "GLOB: ";
                break;
            case 3:
                str = "ADVANCED: ";
                break;
            case 4:
                str = "SUFFIX: ";
                break;
            default:
                str = "? ";
                break;
        }
        return "PatternMatcher{" + str + this.mPattern + "}";
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mPattern);
        protoOutputStream.write(1159641169922L, this.mType);
        protoOutputStream.end(start);
    }

    public boolean check() {
        try {
            if (this.mType == 3) {
                return java.util.Arrays.equals(this.mParsedPattern, parseAndVerifyAdvancedPattern(this.mPattern));
            }
            return true;
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Log.w(TAG, "Failed to verify advanced pattern: " + e.getMessage());
            return false;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(this.mPattern);
        parcel.writeInt(this.mType);
        parcel.writeIntArray(this.mParsedPattern);
    }

    public PatternMatcher(android.os.Parcel parcel) {
        this.mPattern = parcel.readString();
        this.mType = parcel.readInt();
        this.mParsedPattern = parcel.createIntArray();
    }

    static boolean matchPattern(java.lang.String str, java.lang.String str2, int[] iArr, int i) {
        if (str == null) {
            return false;
        }
        if (i == 0) {
            return str2.equals(str);
        }
        if (i == 1) {
            return str.startsWith(str2);
        }
        if (i == 2) {
            return matchGlobPattern(str2, str);
        }
        if (i == 3) {
            return matchAdvancedPattern(iArr, str);
        }
        if (i != 4) {
            return false;
        }
        return str.endsWith(str2);
    }

    static boolean matchGlobPattern(java.lang.String str, java.lang.String str2) {
        int length = str.length();
        if (length <= 0) {
            return str2.length() <= 0;
        }
        int length2 = str2.length();
        char charAt = str.charAt(0);
        int i = 0;
        int i2 = 0;
        while (i < length && i2 < length2) {
            i++;
            char charAt2 = i < length ? str.charAt(i) : (char) 0;
            boolean z = charAt == '\\';
            if (z) {
                i++;
                char c = charAt2;
                charAt2 = i < length ? str.charAt(i) : (char) 0;
                charAt = c;
            }
            if (charAt2 == '*') {
                if (!z && charAt == '.') {
                    if (i >= length - 1) {
                        return true;
                    }
                    int i3 = i + 1;
                    char charAt3 = str.charAt(i3);
                    if (charAt3 == '\\') {
                        i3++;
                        charAt3 = i3 < length ? str.charAt(i3) : (char) 0;
                    }
                    while (str2.charAt(i2) != charAt3 && (i2 = i2 + 1) < length2) {
                    }
                    if (i2 == length2) {
                        return false;
                    }
                    i = i3 + 1;
                    charAt = i < length ? str.charAt(i) : (char) 0;
                    i2++;
                } else {
                    while (str2.charAt(i2) == charAt && (i2 = i2 + 1) < length2) {
                    }
                    i++;
                    charAt = i < length ? str.charAt(i) : (char) 0;
                }
            } else {
                if (charAt != '.' && str2.charAt(i2) != charAt) {
                    return false;
                }
                i2++;
                charAt = charAt2;
            }
        }
        if (i < length || i2 < length2) {
            return i == length + (-2) && str.charAt(i) == '.' && str.charAt(i + 1) == '*';
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x010a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static synchronized int[] parseAndVerifyAdvancedPattern(java.lang.String str) {
        int[] copyOf;
        boolean z;
        int parseInt;
        int i;
        synchronized (android.os.PatternMatcher.class) {
            int length = str.length();
            int i2 = 0;
            boolean z2 = false;
            int i3 = 0;
            boolean z3 = false;
            boolean z4 = false;
            while (i2 < length) {
                if (i3 > 2045) {
                    throw new java.lang.IllegalArgumentException("Pattern is too large!");
                }
                char charAt = str.charAt(i2);
                switch (charAt) {
                    case '*':
                        if (!z2) {
                            if (i3 == 0 || isParsedModifier(sParsedPatternScratch[i3 - 1])) {
                                throw new java.lang.IllegalArgumentException("Modifier must follow a token.");
                            }
                            sParsedPatternScratch[i3] = -7;
                            z = false;
                            i3++;
                            if (!z2) {
                                if (z4) {
                                    sParsedPatternScratch[i3] = charAt;
                                    z4 = false;
                                    i3++;
                                } else {
                                    int i4 = i2 + 2;
                                    if (i4 < length) {
                                        int i5 = i2 + 1;
                                        if (str.charAt(i5) == '-' && str.charAt(i4) != ']') {
                                            sParsedPatternScratch[i3] = charAt;
                                            i3++;
                                            i2 = i5;
                                            z4 = true;
                                        }
                                    }
                                    int i6 = i3 + 1;
                                    sParsedPatternScratch[i3] = charAt;
                                    sParsedPatternScratch[i6] = charAt;
                                    i3 = i6 + 1;
                                }
                            } else if (z3) {
                                int indexOf = str.indexOf(125, i2);
                                if (indexOf < 0) {
                                    throw new java.lang.IllegalArgumentException("Range not ended with '}'");
                                }
                                java.lang.String substring = str.substring(i2, indexOf);
                                int indexOf2 = substring.indexOf(44);
                                if (indexOf2 < 0) {
                                    try {
                                        parseInt = java.lang.Integer.parseInt(substring);
                                        i = parseInt;
                                    } catch (java.lang.NumberFormatException e) {
                                        throw new java.lang.IllegalArgumentException("Range number format incorrect", e);
                                    }
                                } else {
                                    int parseInt2 = java.lang.Integer.parseInt(substring.substring(0, indexOf2));
                                    if (indexOf2 == substring.length() - 1) {
                                        i = Integer.MAX_VALUE;
                                        parseInt = parseInt2;
                                    } else {
                                        i = java.lang.Integer.parseInt(substring.substring(indexOf2 + 1));
                                        parseInt = parseInt2;
                                    }
                                }
                                if (parseInt > i) {
                                    throw new java.lang.IllegalArgumentException("Range quantifier minimum is greater than maximum");
                                }
                                int i7 = i3 + 1;
                                sParsedPatternScratch[i3] = parseInt;
                                i3 = i7 + 1;
                                sParsedPatternScratch[i7] = i;
                                i2 = indexOf;
                            } else if (z) {
                                sParsedPatternScratch[i3] = charAt;
                                i3++;
                            }
                            i2++;
                        }
                        z = false;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case '+':
                        if (!z2) {
                            if (i3 == 0 || isParsedModifier(sParsedPatternScratch[i3 - 1])) {
                                throw new java.lang.IllegalArgumentException("Modifier must follow a token.");
                            }
                            sParsedPatternScratch[i3] = -8;
                            z = false;
                            i3++;
                            if (!z2) {
                            }
                            i2++;
                        }
                        z = false;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case '.':
                        if (!z2) {
                            sParsedPatternScratch[i3] = -4;
                            z = false;
                            i3++;
                            if (!z2) {
                            }
                            i2++;
                        }
                        z = false;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case '[':
                        if (z2) {
                            z = true;
                            if (!z2) {
                            }
                            i2++;
                        } else {
                            int i8 = i2 + 1;
                            if (str.charAt(i8) == '^') {
                                sParsedPatternScratch[i3] = -2;
                                i2 = i8;
                                i3++;
                            } else {
                                sParsedPatternScratch[i3] = -1;
                                i3++;
                            }
                            i2++;
                            z2 = true;
                        }
                        break;
                    case '\\':
                        i2++;
                        if (i2 >= length) {
                            throw new java.lang.IllegalArgumentException("Escape found at end of pattern!");
                        }
                        charAt = str.charAt(i2);
                        z = true;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case ']':
                        if (!z2) {
                            z = true;
                        } else {
                            int i9 = sParsedPatternScratch[i3 - 1];
                            if (i9 == -1 || i9 == -2) {
                                throw new java.lang.IllegalArgumentException("You must define characters in a set.");
                            }
                            sParsedPatternScratch[i3] = -3;
                            z2 = false;
                            z = false;
                            i3++;
                            z4 = false;
                        }
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case '{':
                        if (!z2) {
                            if (i3 == 0 || isParsedModifier(sParsedPatternScratch[i3 - 1])) {
                                throw new java.lang.IllegalArgumentException("Modifier must follow a token.");
                            }
                            sParsedPatternScratch[i3] = -5;
                            i2++;
                            i3++;
                            z3 = true;
                            z = false;
                            if (!z2) {
                            }
                            i2++;
                        }
                        z = false;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    case '}':
                        if (z3) {
                            sParsedPatternScratch[i3] = -6;
                            z3 = false;
                            i3++;
                            z = false;
                            if (!z2) {
                            }
                            i2++;
                        }
                        z = false;
                        if (!z2) {
                        }
                        i2++;
                        break;
                    default:
                        z = true;
                        if (!z2) {
                        }
                        i2++;
                        break;
                }
            }
            if (z2) {
                throw new java.lang.IllegalArgumentException("Set was not terminated!");
            }
            copyOf = java.util.Arrays.copyOf(sParsedPatternScratch, i3);
        }
        return copyOf;
    }

    private static boolean isParsedModifier(int i) {
        return i == -8 || i == -7 || i == -6 || i == -5;
    }

    static boolean matchAdvancedPattern(int[] iArr, java.lang.String str) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int matchChars;
        int length = iArr.length;
        int length2 = str.length();
        int i9 = 0;
        int i10 = 0;
        int i11 = 0;
        int i12 = 0;
        while (i9 < length) {
            int i13 = iArr[i9];
            switch (i13) {
                case -4:
                    i = i9 + 1;
                    i2 = i10;
                    i3 = i11;
                    i4 = 1;
                    break;
                case -3:
                default:
                    i2 = i9;
                    i = i9 + 1;
                    i3 = i11;
                    i4 = 0;
                    break;
                case -2:
                case -1:
                    if (i13 == -1) {
                        i5 = 2;
                    } else {
                        i5 = 3;
                    }
                    int i14 = i9 + 1;
                    do {
                        i9++;
                        if (i9 < length) {
                        }
                        int i15 = i9 - 1;
                        i = i9 + 1;
                        i2 = i14;
                        i3 = i15;
                        i4 = i5;
                        break;
                    } while (iArr[i9] != -3);
                    int i152 = i9 - 1;
                    i = i9 + 1;
                    i2 = i14;
                    i3 = i152;
                    i4 = i5;
            }
            if (i >= length) {
                i6 = i;
                i8 = 1;
                i7 = 1;
            } else {
                switch (iArr[i]) {
                    case -8:
                        i6 = i + 1;
                        i7 = Integer.MAX_VALUE;
                        i8 = 1;
                        break;
                    case -7:
                        i6 = i + 1;
                        i7 = Integer.MAX_VALUE;
                        i8 = 0;
                        break;
                    case -6:
                    default:
                        i6 = i;
                        i8 = 1;
                        i7 = 1;
                        break;
                    case -5:
                        int i16 = i + 1;
                        int i17 = iArr[i16];
                        int i18 = i16 + 1;
                        i6 = i18 + 2;
                        i8 = i17;
                        i7 = iArr[i18];
                        break;
                }
            }
            if (i8 > i7 || (matchChars = matchChars(str, i12, length2, i4, i8, i7, iArr, i2, i3)) == -1) {
                return false;
            }
            i12 += matchChars;
            i10 = i2;
            i11 = i3;
            i9 = i6;
        }
        if (i9 < length || i12 < length2) {
            return false;
        }
        return true;
    }

    private static int matchChars(java.lang.String str, int i, int i2, int i3, int i4, int i5, int[] iArr, int i6, int i7) {
        int i8 = 0;
        while (i8 < i5 && matchChar(str, i + i8, i2, i3, iArr, i6, i7)) {
            i8++;
        }
        if (i8 < i4) {
            return -1;
        }
        return i8;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static boolean matchChar(java.lang.String str, int i, int i2, int i3, int[] iArr, int i4, int i5) {
        if (i >= i2) {
            return false;
        }
        switch (i3) {
            case 0:
                if (str.charAt(i) == iArr[i4]) {
                    break;
                }
                break;
            case 2:
                while (i4 < i5) {
                    char charAt = str.charAt(i);
                    if (charAt >= iArr[i4] && charAt <= iArr[i4 + 1]) {
                        break;
                    } else {
                        i4 += 2;
                    }
                }
                break;
            case 3:
                while (i4 < i5) {
                    char charAt2 = str.charAt(i);
                    if (charAt2 >= iArr[i4] && charAt2 <= iArr[i4 + 1]) {
                        break;
                    } else {
                        i4 += 2;
                    }
                }
                break;
        }
        return false;
    }
}
