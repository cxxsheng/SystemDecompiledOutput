package android.text.method;

/* loaded from: classes3.dex */
public class TextKeyListener extends android.text.method.BaseKeyListener implements android.text.SpanWatcher {
    static final int AUTO_CAP = 1;
    static final int AUTO_PERIOD = 4;
    static final int AUTO_TEXT = 2;
    static final int SHOW_PASSWORD = 8;
    private android.text.method.TextKeyListener.Capitalize mAutoCap;
    private boolean mAutoText;
    private android.text.method.TextKeyListener.SettingsObserver mObserver;
    private int mPrefs;
    private boolean mPrefsInited;
    private java.lang.ref.WeakReference<android.content.ContentResolver> mResolver;
    private static android.text.method.TextKeyListener[] sInstance = new android.text.method.TextKeyListener[android.text.method.TextKeyListener.Capitalize.values().length * 2];
    static final java.lang.Object ACTIVE = new android.text.NoCopySpan.Concrete();
    static final java.lang.Object CAPPED = new android.text.NoCopySpan.Concrete();
    static final java.lang.Object INHIBIT_REPLACEMENT = new android.text.NoCopySpan.Concrete();
    static final java.lang.Object LAST_TYPED = new android.text.NoCopySpan.Concrete();

    public enum Capitalize {
        NONE,
        SENTENCES,
        WORDS,
        CHARACTERS
    }

    public TextKeyListener(android.text.method.TextKeyListener.Capitalize capitalize, boolean z) {
        this.mAutoCap = capitalize;
        this.mAutoText = z;
    }

    public static android.text.method.TextKeyListener getInstance(boolean z, android.text.method.TextKeyListener.Capitalize capitalize) {
        int ordinal = (capitalize.ordinal() * 2) + (z ? 1 : 0);
        if (sInstance[ordinal] == null) {
            sInstance[ordinal] = new android.text.method.TextKeyListener(capitalize, z);
        }
        return sInstance[ordinal];
    }

    public static android.text.method.TextKeyListener getInstance() {
        return getInstance(false, android.text.method.TextKeyListener.Capitalize.NONE);
    }

    public static boolean shouldCap(android.text.method.TextKeyListener.Capitalize capitalize, java.lang.CharSequence charSequence, int i) {
        if (capitalize == android.text.method.TextKeyListener.Capitalize.NONE) {
            return false;
        }
        if (capitalize == android.text.method.TextKeyListener.Capitalize.CHARACTERS) {
            return true;
        }
        return android.text.TextUtils.getCapsMode(charSequence, i, capitalize == android.text.method.TextKeyListener.Capitalize.WORDS ? 8192 : 16384) != 0;
    }

    @Override // android.text.method.KeyListener
    public int getInputType() {
        return makeTextContentType(this.mAutoCap, this.mAutoText);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyDown(view, editable, i, keyEvent);
    }

    @Override // android.text.method.MetaKeyKeyListener, android.text.method.KeyListener
    public boolean onKeyUp(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyUp(view, editable, i, keyEvent);
    }

    @Override // android.text.method.BaseKeyListener, android.text.method.KeyListener
    public boolean onKeyOther(android.view.View view, android.text.Editable editable, android.view.KeyEvent keyEvent) {
        return getKeyListener(keyEvent).onKeyOther(view, editable, keyEvent);
    }

    public static void clear(android.text.Editable editable) {
        editable.clear();
        editable.removeSpan(ACTIVE);
        editable.removeSpan(CAPPED);
        editable.removeSpan(INHIBIT_REPLACEMENT);
        editable.removeSpan(LAST_TYPED);
        for (android.text.method.QwertyKeyListener.Replaced replaced : (android.text.method.QwertyKeyListener.Replaced[]) editable.getSpans(0, editable.length(), android.text.method.QwertyKeyListener.Replaced.class)) {
            editable.removeSpan(replaced);
        }
    }

    @Override // android.text.SpanWatcher
    public void onSpanAdded(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanRemoved(android.text.Spannable spannable, java.lang.Object obj, int i, int i2) {
    }

    @Override // android.text.SpanWatcher
    public void onSpanChanged(android.text.Spannable spannable, java.lang.Object obj, int i, int i2, int i3, int i4) {
        if (obj == android.text.Selection.SELECTION_END) {
            spannable.removeSpan(ACTIVE);
        }
    }

    private android.text.method.KeyListener getKeyListener(android.view.KeyEvent keyEvent) {
        int keyboardType = keyEvent.getKeyCharacterMap().getKeyboardType();
        if (keyboardType == 3) {
            return android.text.method.QwertyKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (keyboardType == 1) {
            return android.text.method.MultiTapKeyListener.getInstance(this.mAutoText, this.mAutoCap);
        }
        if (keyboardType == 4 || keyboardType == 5) {
            return android.text.method.QwertyKeyListener.getInstanceForFullKeyboard();
        }
        return android.text.method.TextKeyListener.NullKeyListener.getInstance();
    }

    private static class NullKeyListener implements android.text.method.KeyListener {
        private static android.text.method.TextKeyListener.NullKeyListener sInstance;

        private NullKeyListener() {
        }

        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 0;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyDown(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyUp(android.view.View view, android.text.Editable editable, int i, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.text.method.KeyListener
        public boolean onKeyOther(android.view.View view, android.text.Editable editable, android.view.KeyEvent keyEvent) {
            return false;
        }

        @Override // android.text.method.KeyListener
        public void clearMetaKeyState(android.view.View view, android.text.Editable editable, int i) {
        }

        public static android.text.method.TextKeyListener.NullKeyListener getInstance() {
            if (sInstance != null) {
                return sInstance;
            }
            sInstance = new android.text.method.TextKeyListener.NullKeyListener();
            return sInstance;
        }
    }

    public void release() {
        if (this.mResolver != null) {
            android.content.ContentResolver contentResolver = this.mResolver.get();
            if (contentResolver != null) {
                contentResolver.unregisterContentObserver(this.mObserver);
                this.mResolver.clear();
            }
            this.mObserver = null;
            this.mResolver = null;
            this.mPrefsInited = false;
        }
    }

    private void initPrefs(android.content.Context context) {
        android.content.ContentResolver contentResolver = context.getContentResolver();
        this.mResolver = new java.lang.ref.WeakReference<>(contentResolver);
        if (this.mObserver == null) {
            this.mObserver = new android.text.method.TextKeyListener.SettingsObserver();
            contentResolver.registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, this.mObserver);
        }
        updatePrefs(contentResolver);
        this.mPrefsInited = true;
    }

    private class SettingsObserver extends android.database.ContentObserver {
        public SettingsObserver() {
            super(new android.os.Handler());
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (android.text.method.TextKeyListener.this.mResolver != null) {
                android.content.ContentResolver contentResolver = (android.content.ContentResolver) android.text.method.TextKeyListener.this.mResolver.get();
                if (contentResolver == null) {
                    android.text.method.TextKeyListener.this.mPrefsInited = false;
                    return;
                } else {
                    android.text.method.TextKeyListener.this.updatePrefs(contentResolver);
                    return;
                }
            }
            android.text.method.TextKeyListener.this.mPrefsInited = false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePrefs(android.content.ContentResolver contentResolver) {
        this.mPrefs = (android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.TEXT_AUTO_REPLACE, 1) > 0 ? 2 : 0) | (android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.TEXT_AUTO_CAPS, 1) > 0 ? 1 : 0) | (android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.TEXT_AUTO_PUNCTUATE, 1) > 0 ? 4 : 0) | (android.provider.Settings.System.getInt(contentResolver, android.provider.Settings.System.TEXT_SHOW_PASSWORD, 1) > 0 ? 8 : 0);
    }

    int getPrefs(android.content.Context context) {
        synchronized (this) {
            if (!this.mPrefsInited || this.mResolver.refersTo(null)) {
                initPrefs(context);
            }
        }
        return this.mPrefs;
    }
}
