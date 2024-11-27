package android.view.accessibility;

/* loaded from: classes4.dex */
public class CaptioningManager {
    private static final int DEFAULT_ENABLED = 0;
    private static final float DEFAULT_FONT_SCALE = 1.0f;
    private static final int DEFAULT_PRESET = 0;
    private static final boolean SYSTEM_AUDIO_CAPTIONING_DEFAULT_ENABLED = false;
    private final android.view.accessibility.AccessibilityManager mAccessibilityManager;
    private final android.database.ContentObserver mContentObserver;
    private final android.content.ContentResolver mContentResolver;
    private final android.content.Context mContext;
    private final android.content.res.Resources mResources;
    private final java.util.ArrayList<android.view.accessibility.CaptioningManager.CaptioningChangeListener> mListeners = new java.util.ArrayList<>();
    private final java.lang.Runnable mStyleChangedRunnable = new java.lang.Runnable() { // from class: android.view.accessibility.CaptioningManager.1
        @Override // java.lang.Runnable
        public void run() {
            android.view.accessibility.CaptioningManager.this.notifyUserStyleChanged();
        }
    };

    public interface SystemAudioCaptioningAccessing {
        boolean isSystemAudioCaptioningUiEnabled(int i);

        void setSystemAudioCaptioningEnabled(boolean z, int i);

        void setSystemAudioCaptioningUiEnabled(boolean z, int i);
    }

    public CaptioningManager(android.content.Context context) {
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mAccessibilityManager = (android.view.accessibility.AccessibilityManager) this.mContext.getSystemService(android.view.accessibility.AccessibilityManager.class);
        this.mContentObserver = new android.view.accessibility.CaptioningManager.MyContentObserver(new android.os.Handler(context.getMainLooper()));
        this.mResources = context.getResources();
    }

    public final boolean isEnabled() {
        return android.provider.Settings.Secure.getInt(this.mContentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_ENABLED, 0) == 1;
    }

    public final java.lang.String getRawLocale() {
        return android.provider.Settings.Secure.getString(this.mContentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_LOCALE);
    }

    public final java.util.Locale getLocale() {
        java.lang.String rawLocale = getRawLocale();
        if (!android.text.TextUtils.isEmpty(rawLocale)) {
            java.lang.String[] split = rawLocale.split(android.telecom.Logging.Session.SESSION_SEPARATION_CHAR_CHILD);
            switch (split.length) {
                case 1:
                    return new java.util.Locale(split[0]);
                case 2:
                    return new java.util.Locale(split[0], split[1]);
                case 3:
                    return new java.util.Locale(split[0], split[1], split[2]);
                default:
                    return null;
            }
        }
        return null;
    }

    public final float getFontScale() {
        return android.provider.Settings.Secure.getFloat(this.mContentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_FONT_SCALE, 1.0f);
    }

    public int getRawUserStyle() {
        return android.provider.Settings.Secure.getInt(this.mContentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_PRESET, 0);
    }

    public android.view.accessibility.CaptioningManager.CaptionStyle getUserStyle() {
        int rawUserStyle = getRawUserStyle();
        if (rawUserStyle == -1) {
            return android.view.accessibility.CaptioningManager.CaptionStyle.getCustomStyle(this.mContentResolver);
        }
        return android.view.accessibility.CaptioningManager.CaptionStyle.PRESETS[rawUserStyle];
    }

    public final boolean isSystemAudioCaptioningEnabled() {
        return android.provider.Settings.Secure.getIntForUser(this.mContentResolver, android.provider.Settings.Secure.ODI_CAPTIONS_ENABLED, 0, this.mContext.getUserId()) == 1;
    }

    @android.annotation.SystemApi
    public final void setSystemAudioCaptioningEnabled(boolean z) {
        if (this.mAccessibilityManager != null) {
            this.mAccessibilityManager.setSystemAudioCaptioningEnabled(z, this.mContext.getUserId());
        }
    }

    public final boolean isSystemAudioCaptioningUiEnabled() {
        return this.mAccessibilityManager != null && this.mAccessibilityManager.isSystemAudioCaptioningUiEnabled(this.mContext.getUserId());
    }

    @android.annotation.SystemApi
    public final void setSystemAudioCaptioningUiEnabled(boolean z) {
        if (this.mAccessibilityManager != null) {
            this.mAccessibilityManager.setSystemAudioCaptioningUiEnabled(z, this.mContext.getUserId());
        }
    }

    public void addCaptioningChangeListener(android.view.accessibility.CaptioningManager.CaptioningChangeListener captioningChangeListener) {
        synchronized (this.mListeners) {
            if (this.mListeners.isEmpty()) {
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_ENABLED);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_FOREGROUND_COLOR);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_BACKGROUND_COLOR);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_WINDOW_COLOR);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_TYPE);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_COLOR);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_TYPEFACE);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_FONT_SCALE);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_LOCALE);
                registerObserver(android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_PRESET);
                registerObserver(android.provider.Settings.Secure.ODI_CAPTIONS_ENABLED);
                registerObserver(android.provider.Settings.Secure.ODI_CAPTIONS_VOLUME_UI_ENABLED);
            }
            this.mListeners.add(captioningChangeListener);
        }
    }

    private void registerObserver(java.lang.String str) {
        this.mContentResolver.registerContentObserver(android.provider.Settings.Secure.getUriFor(str), false, this.mContentObserver);
    }

    public void removeCaptioningChangeListener(android.view.accessibility.CaptioningManager.CaptioningChangeListener captioningChangeListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(captioningChangeListener);
            if (this.mListeners.isEmpty()) {
                this.mContentResolver.unregisterContentObserver(this.mContentObserver);
            }
        }
    }

    public boolean isCallCaptioningEnabled() {
        try {
            return this.mResources.getBoolean(com.android.internal.R.bool.config_systemCaptionsServiceCallsEnabled);
        } catch (android.content.res.Resources.NotFoundException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyEnabledChanged() {
        boolean isEnabled = isEnabled();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onEnabledChanged(isEnabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyUserStyleChanged() {
        android.view.accessibility.CaptioningManager.CaptionStyle userStyle = getUserStyle();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onUserStyleChanged(userStyle);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyLocaleChanged() {
        java.util.Locale locale = getLocale();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onLocaleChanged(locale);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyFontScaleChanged() {
        float fontScale = getFontScale();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onFontScaleChanged(fontScale);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemAudioCaptionChanged() {
        boolean isSystemAudioCaptioningEnabled = isSystemAudioCaptioningEnabled();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSystemAudioCaptioningChanged(isSystemAudioCaptioningEnabled);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifySystemAudioCaptionUiChanged() {
        boolean isSystemAudioCaptioningUiEnabled = isSystemAudioCaptioningUiEnabled();
        synchronized (this.mListeners) {
            java.util.Iterator<android.view.accessibility.CaptioningManager.CaptioningChangeListener> it = this.mListeners.iterator();
            while (it.hasNext()) {
                it.next().onSystemAudioCaptioningUiChanged(isSystemAudioCaptioningUiEnabled);
            }
        }
    }

    private class MyContentObserver extends android.database.ContentObserver {
        private final android.os.Handler mHandler;

        public MyContentObserver(android.os.Handler handler) {
            super(handler);
            this.mHandler = handler;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, android.net.Uri uri) {
            java.lang.String path = uri.getPath();
            java.lang.String substring = path.substring(path.lastIndexOf(47) + 1);
            if (android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_ENABLED.equals(substring)) {
                android.view.accessibility.CaptioningManager.this.notifyEnabledChanged();
                return;
            }
            if (android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_LOCALE.equals(substring)) {
                android.view.accessibility.CaptioningManager.this.notifyLocaleChanged();
                return;
            }
            if (android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_FONT_SCALE.equals(substring)) {
                android.view.accessibility.CaptioningManager.this.notifyFontScaleChanged();
                return;
            }
            if (android.provider.Settings.Secure.ODI_CAPTIONS_ENABLED.equals(substring)) {
                android.view.accessibility.CaptioningManager.this.notifySystemAudioCaptionChanged();
            } else if (android.provider.Settings.Secure.ODI_CAPTIONS_VOLUME_UI_ENABLED.equals(substring)) {
                android.view.accessibility.CaptioningManager.this.notifySystemAudioCaptionUiChanged();
            } else {
                this.mHandler.removeCallbacks(android.view.accessibility.CaptioningManager.this.mStyleChangedRunnable);
                this.mHandler.post(android.view.accessibility.CaptioningManager.this.mStyleChangedRunnable);
            }
        }
    }

    public static final class CaptionStyle {
        private static final int COLOR_NONE_OPAQUE = 255;
        public static final int COLOR_UNSPECIFIED = 16777215;
        public static final int EDGE_TYPE_DEPRESSED = 4;
        public static final int EDGE_TYPE_DROP_SHADOW = 2;
        public static final int EDGE_TYPE_NONE = 0;
        public static final int EDGE_TYPE_OUTLINE = 1;
        public static final int EDGE_TYPE_RAISED = 3;
        public static final int EDGE_TYPE_UNSPECIFIED = -1;
        public static final int PRESET_CUSTOM = -1;
        public final int backgroundColor;
        public final int edgeColor;
        public final int edgeType;
        public final int foregroundColor;
        private final boolean mHasBackgroundColor;
        private final boolean mHasEdgeColor;
        private final boolean mHasEdgeType;
        private final boolean mHasForegroundColor;
        private final boolean mHasWindowColor;
        private android.graphics.Typeface mParsedTypeface;
        public final java.lang.String mRawTypeface;
        public final int windowColor;
        private static final android.view.accessibility.CaptioningManager.CaptionStyle WHITE_ON_BLACK = new android.view.accessibility.CaptioningManager.CaptionStyle(-1, -16777216, 0, -16777216, 255, null);
        private static final android.view.accessibility.CaptioningManager.CaptionStyle BLACK_ON_WHITE = new android.view.accessibility.CaptioningManager.CaptionStyle(-16777216, -1, 0, -16777216, 255, null);
        private static final android.view.accessibility.CaptioningManager.CaptionStyle YELLOW_ON_BLACK = new android.view.accessibility.CaptioningManager.CaptionStyle(-256, -16777216, 0, -16777216, 255, null);
        private static final android.view.accessibility.CaptioningManager.CaptionStyle YELLOW_ON_BLUE = new android.view.accessibility.CaptioningManager.CaptionStyle(-256, -16776961, 0, -16777216, 255, null);
        private static final android.view.accessibility.CaptioningManager.CaptionStyle UNSPECIFIED = new android.view.accessibility.CaptioningManager.CaptionStyle(16777215, 16777215, -1, 16777215, 16777215, null);
        public static final android.view.accessibility.CaptioningManager.CaptionStyle[] PRESETS = {WHITE_ON_BLACK, BLACK_ON_WHITE, YELLOW_ON_BLACK, YELLOW_ON_BLUE, UNSPECIFIED};
        private static final android.view.accessibility.CaptioningManager.CaptionStyle DEFAULT_CUSTOM = WHITE_ON_BLACK;
        public static final android.view.accessibility.CaptioningManager.CaptionStyle DEFAULT = WHITE_ON_BLACK;

        private CaptionStyle(int i, int i2, int i3, int i4, int i5, java.lang.String str) {
            this.mHasForegroundColor = hasColor(i);
            this.mHasBackgroundColor = hasColor(i2);
            this.mHasEdgeType = i3 != -1;
            this.mHasEdgeColor = hasColor(i4);
            this.mHasWindowColor = hasColor(i5);
            this.foregroundColor = this.mHasForegroundColor ? i : -1;
            this.backgroundColor = this.mHasBackgroundColor ? i2 : -16777216;
            this.edgeType = this.mHasEdgeType ? i3 : 0;
            this.edgeColor = this.mHasEdgeColor ? i4 : -16777216;
            this.windowColor = this.mHasWindowColor ? i5 : 255;
            this.mRawTypeface = str;
        }

        public static boolean hasColor(int i) {
            return (i >>> 24) != 0 || (i & 16776960) == 0;
        }

        public android.view.accessibility.CaptioningManager.CaptionStyle applyStyle(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
            return new android.view.accessibility.CaptioningManager.CaptionStyle(captionStyle.hasForegroundColor() ? captionStyle.foregroundColor : this.foregroundColor, captionStyle.hasBackgroundColor() ? captionStyle.backgroundColor : this.backgroundColor, captionStyle.hasEdgeType() ? captionStyle.edgeType : this.edgeType, captionStyle.hasEdgeColor() ? captionStyle.edgeColor : this.edgeColor, captionStyle.hasWindowColor() ? captionStyle.windowColor : this.windowColor, captionStyle.mRawTypeface != null ? captionStyle.mRawTypeface : this.mRawTypeface);
        }

        public boolean hasBackgroundColor() {
            return this.mHasBackgroundColor;
        }

        public boolean hasForegroundColor() {
            return this.mHasForegroundColor;
        }

        public boolean hasEdgeType() {
            return this.mHasEdgeType;
        }

        public boolean hasEdgeColor() {
            return this.mHasEdgeColor;
        }

        public boolean hasWindowColor() {
            return this.mHasWindowColor;
        }

        public android.graphics.Typeface getTypeface() {
            if (this.mParsedTypeface == null && !android.text.TextUtils.isEmpty(this.mRawTypeface)) {
                this.mParsedTypeface = android.graphics.Typeface.create(this.mRawTypeface, 0);
            }
            return this.mParsedTypeface;
        }

        public static android.view.accessibility.CaptioningManager.CaptionStyle getCustomStyle(android.content.ContentResolver contentResolver) {
            java.lang.String str;
            android.view.accessibility.CaptioningManager.CaptionStyle captionStyle = DEFAULT_CUSTOM;
            int i = android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_FOREGROUND_COLOR, captionStyle.foregroundColor);
            int i2 = android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_BACKGROUND_COLOR, captionStyle.backgroundColor);
            int i3 = android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_TYPE, captionStyle.edgeType);
            int i4 = android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_EDGE_COLOR, captionStyle.edgeColor);
            int i5 = android.provider.Settings.Secure.getInt(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_WINDOW_COLOR, captionStyle.windowColor);
            java.lang.String string = android.provider.Settings.Secure.getString(contentResolver, android.provider.Settings.Secure.ACCESSIBILITY_CAPTIONING_TYPEFACE);
            if (string != null) {
                str = string;
            } else {
                str = captionStyle.mRawTypeface;
            }
            return new android.view.accessibility.CaptioningManager.CaptionStyle(i, i2, i3, i4, i5, str);
        }
    }

    public static abstract class CaptioningChangeListener {
        public void onEnabledChanged(boolean z) {
        }

        public void onUserStyleChanged(android.view.accessibility.CaptioningManager.CaptionStyle captionStyle) {
        }

        public void onLocaleChanged(java.util.Locale locale) {
        }

        public void onFontScaleChanged(float f) {
        }

        public void onSystemAudioCaptioningChanged(boolean z) {
        }

        public void onSystemAudioCaptioningUiChanged(boolean z) {
        }
    }
}
