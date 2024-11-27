package android.text.method;

/* loaded from: classes3.dex */
public class DigitsKeyListener extends android.text.method.NumberKeyListener {
    private static final int DECIMAL = 2;
    private static final java.lang.String DEFAULT_DECIMAL_POINT_CHARS = ".";
    private static final java.lang.String DEFAULT_SIGN_CHARS = "-+";
    private static final char EN_DASH = 8211;
    private static final char MINUS_SIGN = 8722;
    private static final int SIGN = 1;
    private char[] mAccepted;
    private final boolean mDecimal;
    private java.lang.String mDecimalPointChars;
    private final java.util.Locale mLocale;
    private boolean mNeedsAdvancedInput;
    private final boolean mSign;
    private java.lang.String mSignChars;
    private final boolean mStringMode;
    private static final char HYPHEN_MINUS = '-';
    private static final char[][] COMPATIBILITY_CHARACTERS = {new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', HYPHEN_MINUS, '+'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'}, new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', HYPHEN_MINUS, '+', '.'}};
    private static final java.lang.Object sLocaleCacheLock = new java.lang.Object();
    private static final java.util.HashMap<java.util.Locale, android.text.method.DigitsKeyListener[]> sLocaleInstanceCache = new java.util.HashMap<>();
    private static final java.lang.Object sStringCacheLock = new java.lang.Object();
    private static final java.util.HashMap<java.lang.String, android.text.method.DigitsKeyListener> sStringInstanceCache = new java.util.HashMap<>();

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return this.mAccepted;
    }

    private boolean isSignChar(char c) {
        return this.mSignChars.indexOf(c) != -1;
    }

    private boolean isDecimalPointChar(char c) {
        return this.mDecimalPointChars.indexOf(c) != -1;
    }

    @java.lang.Deprecated
    public DigitsKeyListener() {
        this(null, false, false);
    }

    @java.lang.Deprecated
    public DigitsKeyListener(boolean z, boolean z2) {
        this(null, z, z2);
    }

    public DigitsKeyListener(java.util.Locale locale) {
        this(locale, false, false);
    }

    private void setToCompat() {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mAccepted = COMPATIBILITY_CHARACTERS[(this.mSign ? 1 : 0) | (this.mDecimal ? 2 : 0)];
        this.mNeedsAdvancedInput = false;
    }

    private void calculateNeedForAdvancedInput() {
        this.mNeedsAdvancedInput = !com.android.internal.util.ArrayUtils.containsAll(COMPATIBILITY_CHARACTERS[(this.mSign ? 1 : 0) | (this.mDecimal ? 2 : 0)], this.mAccepted);
    }

    private static java.lang.String stripBidiControls(java.lang.String str) {
        java.lang.String str2 = "";
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!android.icu.lang.UCharacter.hasBinaryProperty(charAt, 2)) {
                if (str2.isEmpty()) {
                    str2 = java.lang.String.valueOf(charAt);
                } else {
                    str2 = str2 + charAt;
                }
            }
        }
        return str2;
    }

    public DigitsKeyListener(java.util.Locale locale, boolean z, boolean z2) {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mSign = z;
        this.mDecimal = z2;
        this.mStringMode = false;
        this.mLocale = locale;
        if (locale == null) {
            setToCompat();
            return;
        }
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        if (!android.text.method.NumberKeyListener.addDigits(linkedHashSet, locale)) {
            setToCompat();
            return;
        }
        if (z || z2) {
            android.icu.text.DecimalFormatSymbols decimalFormatSymbols = android.icu.text.DecimalFormatSymbols.getInstance(locale);
            if (z) {
                java.lang.String stripBidiControls = stripBidiControls(decimalFormatSymbols.getMinusSignString());
                java.lang.String stripBidiControls2 = stripBidiControls(decimalFormatSymbols.getPlusSignString());
                if (stripBidiControls.length() > 1 || stripBidiControls2.length() > 1) {
                    setToCompat();
                    return;
                }
                char charAt = stripBidiControls.charAt(0);
                char charAt2 = stripBidiControls2.charAt(0);
                linkedHashSet.add(java.lang.Character.valueOf(charAt));
                linkedHashSet.add(java.lang.Character.valueOf(charAt2));
                this.mSignChars = "" + charAt + charAt2;
                if (charAt == 8722 || charAt == 8211) {
                    linkedHashSet.add(java.lang.Character.valueOf(HYPHEN_MINUS));
                    this.mSignChars += HYPHEN_MINUS;
                }
            }
            if (z2) {
                java.lang.String decimalSeparatorString = decimalFormatSymbols.getDecimalSeparatorString();
                if (decimalSeparatorString.length() > 1) {
                    setToCompat();
                    return;
                } else {
                    java.lang.Character valueOf = java.lang.Character.valueOf(decimalSeparatorString.charAt(0));
                    linkedHashSet.add(valueOf);
                    this.mDecimalPointChars = valueOf.toString();
                }
            }
        }
        this.mAccepted = android.text.method.NumberKeyListener.collectionToArray(linkedHashSet);
        calculateNeedForAdvancedInput();
    }

    private DigitsKeyListener(java.lang.String str) {
        this.mDecimalPointChars = ".";
        this.mSignChars = DEFAULT_SIGN_CHARS;
        this.mSign = false;
        this.mDecimal = false;
        this.mStringMode = true;
        this.mLocale = null;
        this.mAccepted = new char[str.length()];
        str.getChars(0, str.length(), this.mAccepted, 0);
        this.mNeedsAdvancedInput = false;
    }

    @java.lang.Deprecated
    public static android.text.method.DigitsKeyListener getInstance() {
        return getInstance(false, false);
    }

    @java.lang.Deprecated
    public static android.text.method.DigitsKeyListener getInstance(boolean z, boolean z2) {
        return getInstance(null, z, z2);
    }

    public static android.text.method.DigitsKeyListener getInstance(java.util.Locale locale) {
        return getInstance(locale, false, false);
    }

    public static android.text.method.DigitsKeyListener getInstance(java.util.Locale locale, boolean z, boolean z2) {
        int i = (z2 ? 2 : 0) | (z ? 1 : 0);
        synchronized (sLocaleCacheLock) {
            android.text.method.DigitsKeyListener[] digitsKeyListenerArr = sLocaleInstanceCache.get(locale);
            if (digitsKeyListenerArr != null && digitsKeyListenerArr[i] != null) {
                return digitsKeyListenerArr[i];
            }
            if (digitsKeyListenerArr == null) {
                digitsKeyListenerArr = new android.text.method.DigitsKeyListener[4];
                sLocaleInstanceCache.put(locale, digitsKeyListenerArr);
            }
            android.text.method.DigitsKeyListener digitsKeyListener = new android.text.method.DigitsKeyListener(locale, z, z2);
            digitsKeyListenerArr[i] = digitsKeyListener;
            return digitsKeyListener;
        }
    }

    public static android.text.method.DigitsKeyListener getInstance(java.lang.String str) {
        android.text.method.DigitsKeyListener digitsKeyListener;
        synchronized (sStringCacheLock) {
            digitsKeyListener = sStringInstanceCache.get(str);
            if (digitsKeyListener == null) {
                digitsKeyListener = new android.text.method.DigitsKeyListener(str);
                sStringInstanceCache.put(str, digitsKeyListener);
            }
        }
        return digitsKeyListener;
    }

    public static android.text.method.DigitsKeyListener getInstance(java.util.Locale locale, android.text.method.DigitsKeyListener digitsKeyListener) {
        if (digitsKeyListener.mStringMode) {
            return digitsKeyListener;
        }
        return getInstance(locale, digitsKeyListener.mSign, digitsKeyListener.mDecimal);
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        int i;
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        if (!this.mSign) {
            i = 2;
        } else {
            i = 4098;
        }
        if (this.mDecimal) {
            return i | 8192;
        }
        return i;
    }

    @Override // android.text.method.NumberKeyListener, android.text.InputFilter
    public java.lang.CharSequence filter(java.lang.CharSequence charSequence, int i, int i2, android.text.Spanned spanned, int i3, int i4) {
        java.lang.CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
        if (!this.mSign && !this.mDecimal) {
            return filter;
        }
        if (filter != null) {
            i2 = filter.length();
            charSequence = filter;
            i = 0;
        }
        int length = spanned.length();
        int i5 = -1;
        int i6 = -1;
        for (int i7 = 0; i7 < i3; i7++) {
            char charAt = spanned.charAt(i7);
            if (isSignChar(charAt)) {
                i6 = i7;
            } else if (isDecimalPointChar(charAt)) {
                i5 = i7;
            }
        }
        while (i4 < length) {
            char charAt2 = spanned.charAt(i4);
            if (isSignChar(charAt2)) {
                return "";
            }
            if (isDecimalPointChar(charAt2)) {
                i5 = i4;
            }
            i4++;
        }
        android.text.SpannableStringBuilder spannableStringBuilder = null;
        for (int i8 = i2 - 1; i8 >= i; i8--) {
            char charAt3 = charSequence.charAt(i8);
            boolean z = true;
            if (isSignChar(charAt3)) {
                if (i8 == i && i3 == 0 && i6 < 0) {
                    i6 = i8;
                    z = false;
                }
            } else if (!isDecimalPointChar(charAt3)) {
                z = false;
            } else if (i5 < 0) {
                i5 = i8;
                z = false;
            }
            if (z) {
                if (i2 == i + 1) {
                    return "";
                }
                if (spannableStringBuilder == null) {
                    spannableStringBuilder = new android.text.SpannableStringBuilder(charSequence, i, i2);
                }
                spannableStringBuilder.delete(i8 - i, (i8 + 1) - i);
            }
        }
        if (spannableStringBuilder != null) {
            return spannableStringBuilder;
        }
        if (filter != null) {
            return filter;
        }
        return null;
    }
}
