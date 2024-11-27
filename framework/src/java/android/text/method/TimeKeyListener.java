package android.text.method;

/* loaded from: classes3.dex */
public class TimeKeyListener extends android.text.method.NumberKeyListener {
    private static final java.lang.String SKELETON_12HOUR = "hms";
    private static final java.lang.String SKELETON_24HOUR = "Hms";
    private static final java.lang.String SYMBOLS_TO_IGNORE = "ahHKkms";
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;
    public static final char[] CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', android.text.format.DateFormat.AM_PM, android.text.format.DateFormat.MINUTE, 'p', com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR};
    private static final java.lang.Object sLock = new java.lang.Object();
    private static final java.util.HashMap<java.util.Locale, android.text.method.TimeKeyListener> sInstanceCache = new java.util.HashMap<>();

    @Override // android.text.method.KeyListener
    public int getInputType() {
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        return 36;
    }

    @Override // android.text.method.NumberKeyListener
    protected char[] getAcceptedChars() {
        return this.mCharacters;
    }

    @java.lang.Deprecated
    public TimeKeyListener() {
        this(null);
    }

    public TimeKeyListener(java.util.Locale locale) {
        java.util.LinkedHashSet linkedHashSet = new java.util.LinkedHashSet();
        if (android.text.method.NumberKeyListener.addDigits(linkedHashSet, locale) && android.text.method.NumberKeyListener.addAmPmChars(linkedHashSet, locale) && android.text.method.NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_12HOUR, SYMBOLS_TO_IGNORE) && android.text.method.NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_24HOUR, SYMBOLS_TO_IGNORE)) {
            this.mCharacters = android.text.method.NumberKeyListener.collectionToArray(linkedHashSet);
            if (locale != null && "en".equals(locale.getLanguage())) {
                this.mNeedsAdvancedInput = false;
                return;
            } else {
                this.mNeedsAdvancedInput = !com.android.internal.util.ArrayUtils.containsAll(CHARACTERS, this.mCharacters);
                return;
            }
        }
        this.mCharacters = CHARACTERS;
        this.mNeedsAdvancedInput = false;
    }

    @java.lang.Deprecated
    public static android.text.method.TimeKeyListener getInstance() {
        return getInstance(null);
    }

    public static android.text.method.TimeKeyListener getInstance(java.util.Locale locale) {
        android.text.method.TimeKeyListener timeKeyListener;
        synchronized (sLock) {
            timeKeyListener = sInstanceCache.get(locale);
            if (timeKeyListener == null) {
                timeKeyListener = new android.text.method.TimeKeyListener(locale);
                sInstanceCache.put(locale, timeKeyListener);
            }
        }
        return timeKeyListener;
    }
}
