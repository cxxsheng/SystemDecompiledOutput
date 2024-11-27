package android.util;

/* loaded from: classes3.dex */
public abstract class KeyValueSettingObserver {
    private static final java.lang.String TAG = "KeyValueSettingObserver";
    private final android.database.ContentObserver mObserver;
    private final android.util.KeyValueListParser mParser = new android.util.KeyValueListParser(',');
    private final android.content.ContentResolver mResolver;
    private final android.net.Uri mSettingUri;

    public abstract java.lang.String getSettingValue(android.content.ContentResolver contentResolver);

    public abstract void update(android.util.KeyValueListParser keyValueListParser);

    public KeyValueSettingObserver(android.os.Handler handler, android.content.ContentResolver contentResolver, android.net.Uri uri) {
        this.mObserver = new android.util.KeyValueSettingObserver.SettingObserver(handler);
        this.mResolver = contentResolver;
        this.mSettingUri = uri;
    }

    public void start() {
        this.mResolver.registerContentObserver(this.mSettingUri, false, this.mObserver);
        setParserValue();
        update(this.mParser);
    }

    public void stop() {
        this.mResolver.unregisterContentObserver(this.mObserver);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setParserValue() {
        java.lang.String settingValue = getSettingValue(this.mResolver);
        try {
            this.mParser.setString(settingValue);
        } catch (java.lang.IllegalArgumentException e) {
            android.util.Slog.e(TAG, "Malformed setting: " + settingValue);
        }
    }

    private class SettingObserver extends android.database.ContentObserver {
        private SettingObserver(android.os.Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            android.util.KeyValueSettingObserver.this.setParserValue();
            android.util.KeyValueSettingObserver.this.update(android.util.KeyValueSettingObserver.this.mParser);
        }
    }
}
