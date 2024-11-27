package android.text.method;

/* loaded from: classes3.dex */
public class DateKeyListener extends android.text.method.NumberKeyListener {
    private static final java.lang.String SYMBOLS_TO_IGNORE = "yMLd";
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;
    private static final java.lang.String[] SKELETONS = {"yMd", "yM", "Md"};

    @java.lang.Deprecated
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '-', '.'};
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final java.util.HashMap<java.util.Locale, android.text.method.DateKeyListener> sInstanceCache = new java.util.HashMap<>();

    @Override // android.text.method.KeyListener
    public int getInputType() {
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        return 20;
    }

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return this.mCharacters;
    }

    @java.lang.Deprecated
    public DateKeyListener() {
        this(null);
    }

    public DateKeyListener(java.util.Locale locale) {
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        if (android.text.method.NumberKeyListener.addDigits(linkedHashSet, locale) && android.text.method.NumberKeyListener.addFormatCharsFromSkeletons(linkedHashSet, locale, SKELETONS, SYMBOLS_TO_IGNORE)) {
            this.mCharacters = android.text.method.NumberKeyListener.collectionToArray(linkedHashSet);
            this.mNeedsAdvancedInput = !com.android.internal.util.ArrayUtils.containsAll(CHARACTERS, this.mCharacters);
        } else {
            this.mCharacters = CHARACTERS;
            this.mNeedsAdvancedInput = false;
        }
    }

    @java.lang.Deprecated
    public static android.text.method.DateKeyListener getInstance() {
        return getInstance(null);
    }

    public static android.text.method.DateKeyListener getInstance(java.util.Locale locale) {
        android.text.method.DateKeyListener dateKeyListener;
        synchronized (sLock) {
            dateKeyListener = sInstanceCache.get(locale);
            if (dateKeyListener == null) {
                dateKeyListener = new android.text.method.DateKeyListener(locale);
                sInstanceCache.put(locale, dateKeyListener);
            }
        }
        return dateKeyListener;
    }
}
