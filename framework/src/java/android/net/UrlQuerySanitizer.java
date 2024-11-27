package android.net;

/* loaded from: classes2.dex */
public class UrlQuerySanitizer {
    private boolean mAllowUnregisteredParamaters;
    private boolean mPreferFirstRepeatedParameter;
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAllIllegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(0);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAllButNulLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(1535);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAllButWhitespaceLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(1532);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sURLLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(404);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sUrlAndSpaceLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(405);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAmpLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(128);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAmpAndSpaceLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(129);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sSpaceLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(1);
    private static final android.net.UrlQuerySanitizer.ValueSanitizer sAllButNulAndAngleBracketsLegal = new android.net.UrlQuerySanitizer.IllegalCharacterValueSanitizer(1439);
    private static final java.util.regex.Pattern plusOrPercent = java.util.regex.Pattern.compile("[+%]");
    private final java.util.HashMap<java.lang.String, android.net.UrlQuerySanitizer.ValueSanitizer> mSanitizers = new java.util.HashMap<>();
    private final java.util.HashMap<java.lang.String, java.lang.String> mEntries = new java.util.HashMap<>();
    private final java.util.ArrayList<android.net.UrlQuerySanitizer.ParameterValuePair> mEntriesList = new java.util.ArrayList<>();
    private android.net.UrlQuerySanitizer.ValueSanitizer mUnregisteredParameterValueSanitizer = getAllIllegal();

    public interface ValueSanitizer {
        java.lang.String sanitize(java.lang.String str);
    }

    public class ParameterValuePair {
        public java.lang.String mParameter;
        public java.lang.String mValue;

        public ParameterValuePair(java.lang.String str, java.lang.String str2) {
            this.mParameter = str;
            this.mValue = str2;
        }
    }

    public static class IllegalCharacterValueSanitizer implements android.net.UrlQuerySanitizer.ValueSanitizer {
        public static final int ALL_BUT_NUL_AND_ANGLE_BRACKETS_LEGAL = 1439;
        public static final int ALL_BUT_NUL_LEGAL = 1535;
        public static final int ALL_BUT_WHITESPACE_LEGAL = 1532;
        public static final int ALL_ILLEGAL = 0;
        public static final int ALL_OK = 2047;
        public static final int ALL_WHITESPACE_OK = 3;
        public static final int AMP_AND_SPACE_LEGAL = 129;
        public static final int AMP_LEGAL = 128;
        public static final int AMP_OK = 128;
        public static final int DQUOTE_OK = 8;
        public static final int GT_OK = 64;
        public static final int LT_OK = 32;
        public static final int NON_7_BIT_ASCII_OK = 4;
        public static final int NUL_OK = 512;
        public static final int OTHER_WHITESPACE_OK = 2;
        public static final int PCT_OK = 256;
        public static final int SCRIPT_URL_OK = 1024;
        public static final int SPACE_LEGAL = 1;
        public static final int SPACE_OK = 1;
        public static final int SQUOTE_OK = 16;
        public static final int URL_AND_SPACE_LEGAL = 405;
        public static final int URL_LEGAL = 404;
        private int mFlags;
        private static final java.lang.String JAVASCRIPT_PREFIX = "javascript:";
        private static final java.lang.String VBSCRIPT_PREFIX = "vbscript:";
        private static final int MIN_SCRIPT_PREFIX_LENGTH = java.lang.Math.min(JAVASCRIPT_PREFIX.length(), VBSCRIPT_PREFIX.length());

        public IllegalCharacterValueSanitizer(int i) {
            this.mFlags = i;
        }

        @Override // android.net.UrlQuerySanitizer.ValueSanitizer
        public java.lang.String sanitize(java.lang.String str) {
            if (str == null) {
                return null;
            }
            int length = str.length();
            if ((this.mFlags & 1024) == 0 && length >= MIN_SCRIPT_PREFIX_LENGTH) {
                java.lang.String lowerCase = str.toLowerCase(java.util.Locale.ROOT);
                if (lowerCase.startsWith(JAVASCRIPT_PREFIX) || lowerCase.startsWith(VBSCRIPT_PREFIX)) {
                    return "";
                }
            }
            if ((this.mFlags & 3) == 0) {
                str = trimWhitespace(str);
                length = str.length();
            }
            java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
            for (int i = 0; i < length; i++) {
                char charAt = str.charAt(i);
                if (!characterIsLegal(charAt)) {
                    if ((this.mFlags & 1) != 0) {
                        charAt = ' ';
                    } else {
                        charAt = '_';
                    }
                }
                sb.append(charAt);
            }
            return sb.toString();
        }

        private java.lang.String trimWhitespace(java.lang.String str) {
            int length = str.length() - 1;
            int i = 0;
            while (i <= length && isWhitespace(str.charAt(i))) {
                i++;
            }
            int i2 = length;
            while (i2 >= i && isWhitespace(str.charAt(i2))) {
                i2--;
            }
            if (i == 0 && i2 == length) {
                return str;
            }
            return str.substring(i, i2 + 1);
        }

        private boolean isWhitespace(char c) {
            switch (c) {
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                case ' ':
                    return true;
                default:
                    return false;
            }
        }

        private boolean characterIsLegal(char c) {
            switch (c) {
                case 0:
                    if ((this.mFlags & 512) != 0) {
                        break;
                    }
                    break;
                case '\t':
                case '\n':
                case 11:
                case '\f':
                case '\r':
                    if ((this.mFlags & 2) != 0) {
                        break;
                    }
                    break;
                case ' ':
                    if ((this.mFlags & 1) != 0) {
                        break;
                    }
                    break;
                case '\"':
                    if ((this.mFlags & 8) != 0) {
                        break;
                    }
                    break;
                case '%':
                    if ((this.mFlags & 256) != 0) {
                        break;
                    }
                    break;
                case '&':
                    if ((this.mFlags & 128) != 0) {
                        break;
                    }
                    break;
                case '\'':
                    if ((this.mFlags & 16) != 0) {
                        break;
                    }
                    break;
                case '<':
                    if ((this.mFlags & 32) != 0) {
                        break;
                    }
                    break;
                case '>':
                    if ((this.mFlags & 64) != 0) {
                        break;
                    }
                    break;
                default:
                    if ((c >= ' ' && c < 127) || (c >= 128 && (this.mFlags & 4) != 0)) {
                        break;
                    }
                    break;
            }
            return true;
        }
    }

    public android.net.UrlQuerySanitizer.ValueSanitizer getUnregisteredParameterValueSanitizer() {
        return this.mUnregisteredParameterValueSanitizer;
    }

    public void setUnregisteredParameterValueSanitizer(android.net.UrlQuerySanitizer.ValueSanitizer valueSanitizer) {
        this.mUnregisteredParameterValueSanitizer = valueSanitizer;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAllIllegal() {
        return sAllIllegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAllButNulLegal() {
        return sAllButNulLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAllButWhitespaceLegal() {
        return sAllButWhitespaceLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getUrlLegal() {
        return sURLLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getUrlAndSpaceLegal() {
        return sUrlAndSpaceLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAmpLegal() {
        return sAmpLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAmpAndSpaceLegal() {
        return sAmpAndSpaceLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getSpaceLegal() {
        return sSpaceLegal;
    }

    public static final android.net.UrlQuerySanitizer.ValueSanitizer getAllButNulAndAngleBracketsLegal() {
        return sAllButNulAndAngleBracketsLegal;
    }

    public UrlQuerySanitizer() {
    }

    public UrlQuerySanitizer(java.lang.String str) {
        setAllowUnregisteredParamaters(true);
        parseUrl(str);
    }

    public void parseUrl(java.lang.String str) {
        java.lang.String str2;
        int indexOf = str.indexOf(63);
        if (indexOf >= 0) {
            str2 = str.substring(indexOf + 1);
        } else {
            str2 = "";
        }
        parseQuery(str2);
    }

    public void parseQuery(java.lang.String str) {
        clear();
        java.util.StringTokenizer stringTokenizer = new java.util.StringTokenizer(str, "&");
        while (stringTokenizer.hasMoreElements()) {
            java.lang.String nextToken = stringTokenizer.nextToken();
            if (nextToken.length() > 0) {
                int indexOf = nextToken.indexOf(61);
                if (indexOf < 0) {
                    parseEntry(nextToken, "");
                } else {
                    parseEntry(nextToken.substring(0, indexOf), nextToken.substring(indexOf + 1));
                }
            }
        }
    }

    public java.util.Set<java.lang.String> getParameterSet() {
        return this.mEntries.keySet();
    }

    public java.util.List<android.net.UrlQuerySanitizer.ParameterValuePair> getParameterList() {
        return this.mEntriesList;
    }

    public boolean hasParameter(java.lang.String str) {
        return this.mEntries.containsKey(str);
    }

    public java.lang.String getValue(java.lang.String str) {
        return this.mEntries.get(str);
    }

    public void registerParameter(java.lang.String str, android.net.UrlQuerySanitizer.ValueSanitizer valueSanitizer) {
        if (valueSanitizer == null) {
            this.mSanitizers.remove(str);
        }
        this.mSanitizers.put(str, valueSanitizer);
    }

    public void registerParameters(java.lang.String[] strArr, android.net.UrlQuerySanitizer.ValueSanitizer valueSanitizer) {
        for (java.lang.String str : strArr) {
            this.mSanitizers.put(str, valueSanitizer);
        }
    }

    public void setAllowUnregisteredParamaters(boolean z) {
        this.mAllowUnregisteredParamaters = z;
    }

    public boolean getAllowUnregisteredParamaters() {
        return this.mAllowUnregisteredParamaters;
    }

    public void setPreferFirstRepeatedParameter(boolean z) {
        this.mPreferFirstRepeatedParameter = z;
    }

    public boolean getPreferFirstRepeatedParameter() {
        return this.mPreferFirstRepeatedParameter;
    }

    protected void parseEntry(java.lang.String str, java.lang.String str2) {
        java.lang.String unescape = unescape(str);
        android.net.UrlQuerySanitizer.ValueSanitizer effectiveValueSanitizer = getEffectiveValueSanitizer(unescape);
        if (effectiveValueSanitizer == null) {
            return;
        }
        addSanitizedEntry(unescape, effectiveValueSanitizer.sanitize(unescape(str2)));
    }

    protected void addSanitizedEntry(java.lang.String str, java.lang.String str2) {
        this.mEntriesList.add(new android.net.UrlQuerySanitizer.ParameterValuePair(str, str2));
        if (this.mPreferFirstRepeatedParameter && this.mEntries.containsKey(str)) {
            return;
        }
        this.mEntries.put(str, str2);
    }

    public android.net.UrlQuerySanitizer.ValueSanitizer getValueSanitizer(java.lang.String str) {
        return this.mSanitizers.get(str);
    }

    public android.net.UrlQuerySanitizer.ValueSanitizer getEffectiveValueSanitizer(java.lang.String str) {
        android.net.UrlQuerySanitizer.ValueSanitizer valueSanitizer = getValueSanitizer(str);
        if (valueSanitizer == null && this.mAllowUnregisteredParamaters) {
            return getUnregisteredParameterValueSanitizer();
        }
        return valueSanitizer;
    }

    public java.lang.String unescape(java.lang.String str) {
        int i;
        java.util.regex.Matcher matcher = plusOrPercent.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        int start = matcher.start();
        int length = str.length();
        java.lang.StringBuilder sb = new java.lang.StringBuilder(length);
        sb.append(str.substring(0, start));
        while (start < length) {
            char charAt = str.charAt(start);
            if (charAt == '+') {
                charAt = ' ';
            } else if (charAt == '%' && (i = start + 2) < length) {
                char charAt2 = str.charAt(start + 1);
                char charAt3 = str.charAt(i);
                if (isHexDigit(charAt2) && isHexDigit(charAt3)) {
                    charAt = (char) ((decodeHexDigit(charAt2) * 16) + decodeHexDigit(charAt3));
                    start = i;
                }
            }
            sb.append(charAt);
            start++;
        }
        return sb.toString();
    }

    protected boolean isHexDigit(char c) {
        return decodeHexDigit(c) >= 0;
    }

    protected int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return (c - android.text.format.DateFormat.CAPITAL_AM_PM) + 10;
        }
        if (c >= 'a' && c <= 'f') {
            return (c - android.text.format.DateFormat.AM_PM) + 10;
        }
        return -1;
    }

    protected void clear() {
        this.mEntries.clear();
        this.mEntriesList.clear();
    }
}
