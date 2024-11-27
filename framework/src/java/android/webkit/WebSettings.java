package android.webkit;

/* loaded from: classes4.dex */
public abstract class WebSettings {

    @android.annotation.SystemApi
    public static final long ENABLE_SIMPLIFIED_DARK_MODE = 214741472;
    public static final int FORCE_DARK_AUTO = 1;
    public static final int FORCE_DARK_OFF = 0;
    public static final int FORCE_DARK_ON = 2;
    public static final int LOAD_CACHE_ELSE_NETWORK = 1;
    public static final int LOAD_CACHE_ONLY = 3;
    public static final int LOAD_DEFAULT = -1;

    @java.lang.Deprecated
    public static final int LOAD_NORMAL = 0;
    public static final int LOAD_NO_CACHE = 2;
    public static final int MENU_ITEM_NONE = 0;
    public static final int MENU_ITEM_PROCESS_TEXT = 4;
    public static final int MENU_ITEM_SHARE = 1;
    public static final int MENU_ITEM_WEB_SEARCH = 2;
    public static final int MIXED_CONTENT_ALWAYS_ALLOW = 0;
    public static final int MIXED_CONTENT_COMPATIBILITY_MODE = 2;
    public static final int MIXED_CONTENT_NEVER_ALLOW = 1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface CacheMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ForceDark {
    }

    public enum LayoutAlgorithm {
        NORMAL,
        SINGLE_COLUMN,
        NARROW_COLUMNS,
        TEXT_AUTOSIZING
    }

    @java.lang.annotation.Target({java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.METHOD})
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    private @interface MenuItemFlags {
    }

    public enum PluginState {
        ON,
        ON_DEMAND,
        OFF
    }

    public enum RenderPriority {
        NORMAL,
        HIGH,
        LOW
    }

    @java.lang.Deprecated
    public abstract boolean enableSmoothTransition();

    @android.annotation.SystemApi
    public abstract boolean getAcceptThirdPartyCookies();

    public abstract boolean getAllowContentAccess();

    public abstract boolean getAllowFileAccess();

    public abstract boolean getAllowFileAccessFromFileURLs();

    public abstract boolean getAllowUniversalAccessFromFileURLs();

    public abstract boolean getBlockNetworkImage();

    public abstract boolean getBlockNetworkLoads();

    public abstract boolean getBuiltInZoomControls();

    public abstract int getCacheMode();

    public abstract java.lang.String getCursiveFontFamily();

    public abstract boolean getDatabaseEnabled();

    @java.lang.Deprecated
    public abstract java.lang.String getDatabasePath();

    public abstract int getDefaultFixedFontSize();

    public abstract int getDefaultFontSize();

    public abstract java.lang.String getDefaultTextEncodingName();

    @java.lang.Deprecated
    public abstract android.webkit.WebSettings.ZoomDensity getDefaultZoom();

    public abstract int getDisabledActionModeMenuItems();

    public abstract boolean getDisplayZoomControls();

    public abstract boolean getDomStorageEnabled();

    public abstract java.lang.String getFantasyFontFamily();

    public abstract java.lang.String getFixedFontFamily();

    public abstract boolean getJavaScriptCanOpenWindowsAutomatically();

    public abstract boolean getJavaScriptEnabled();

    public abstract android.webkit.WebSettings.LayoutAlgorithm getLayoutAlgorithm();

    @java.lang.Deprecated
    public abstract boolean getLightTouchEnabled();

    public abstract boolean getLoadWithOverviewMode();

    public abstract boolean getLoadsImagesAutomatically();

    public abstract boolean getMediaPlaybackRequiresUserGesture();

    public abstract int getMinimumFontSize();

    public abstract int getMinimumLogicalFontSize();

    public abstract int getMixedContentMode();

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract boolean getNavDump();

    public abstract boolean getOffscreenPreRaster();

    @java.lang.Deprecated
    public abstract android.webkit.WebSettings.PluginState getPluginState();

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract boolean getPluginsEnabled();

    public abstract boolean getSafeBrowsingEnabled();

    public abstract java.lang.String getSansSerifFontFamily();

    @java.lang.Deprecated
    public abstract boolean getSaveFormData();

    @java.lang.Deprecated
    public abstract boolean getSavePassword();

    public abstract java.lang.String getSerifFontFamily();

    public abstract java.lang.String getStandardFontFamily();

    public abstract int getTextZoom();

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract boolean getUseWebViewBackgroundForOverscrollBackground();

    public abstract boolean getUseWideViewPort();

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract int getUserAgent();

    public abstract java.lang.String getUserAgentString();

    @android.annotation.SystemApi
    public abstract boolean getVideoOverlayForEmbeddedEncryptedVideoEnabled();

    @android.annotation.SystemApi
    public abstract void setAcceptThirdPartyCookies(boolean z);

    public abstract void setAllowContentAccess(boolean z);

    public abstract void setAllowFileAccess(boolean z);

    @java.lang.Deprecated
    public abstract void setAllowFileAccessFromFileURLs(boolean z);

    @java.lang.Deprecated
    public abstract void setAllowUniversalAccessFromFileURLs(boolean z);

    public abstract void setBlockNetworkImage(boolean z);

    public abstract void setBlockNetworkLoads(boolean z);

    public abstract void setBuiltInZoomControls(boolean z);

    public abstract void setCacheMode(int i);

    public abstract void setCursiveFontFamily(java.lang.String str);

    public abstract void setDatabaseEnabled(boolean z);

    @java.lang.Deprecated
    public abstract void setDatabasePath(java.lang.String str);

    public abstract void setDefaultFixedFontSize(int i);

    public abstract void setDefaultFontSize(int i);

    public abstract void setDefaultTextEncodingName(java.lang.String str);

    @java.lang.Deprecated
    public abstract void setDefaultZoom(android.webkit.WebSettings.ZoomDensity zoomDensity);

    public abstract void setDisabledActionModeMenuItems(int i);

    public abstract void setDisplayZoomControls(boolean z);

    public abstract void setDomStorageEnabled(boolean z);

    @java.lang.Deprecated
    public abstract void setEnableSmoothTransition(boolean z);

    public abstract void setFantasyFontFamily(java.lang.String str);

    public abstract void setFixedFontFamily(java.lang.String str);

    @java.lang.Deprecated
    public abstract void setGeolocationDatabasePath(java.lang.String str);

    public abstract void setGeolocationEnabled(boolean z);

    public abstract void setJavaScriptCanOpenWindowsAutomatically(boolean z);

    public abstract void setJavaScriptEnabled(boolean z);

    public abstract void setLayoutAlgorithm(android.webkit.WebSettings.LayoutAlgorithm layoutAlgorithm);

    @java.lang.Deprecated
    public abstract void setLightTouchEnabled(boolean z);

    public abstract void setLoadWithOverviewMode(boolean z);

    public abstract void setLoadsImagesAutomatically(boolean z);

    public abstract void setMediaPlaybackRequiresUserGesture(boolean z);

    public abstract void setMinimumFontSize(int i);

    public abstract void setMinimumLogicalFontSize(int i);

    public abstract void setMixedContentMode(int i);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void setNavDump(boolean z);

    public abstract void setNeedInitialFocus(boolean z);

    public abstract void setOffscreenPreRaster(boolean z);

    @java.lang.Deprecated
    public abstract void setPluginState(android.webkit.WebSettings.PluginState pluginState);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void setPluginsEnabled(boolean z);

    @java.lang.Deprecated
    public abstract void setRenderPriority(android.webkit.WebSettings.RenderPriority renderPriority);

    public abstract void setSafeBrowsingEnabled(boolean z);

    public abstract void setSansSerifFontFamily(java.lang.String str);

    @java.lang.Deprecated
    public abstract void setSaveFormData(boolean z);

    @java.lang.Deprecated
    public abstract void setSavePassword(boolean z);

    public abstract void setSerifFontFamily(java.lang.String str);

    public abstract void setStandardFontFamily(java.lang.String str);

    public abstract void setSupportMultipleWindows(boolean z);

    public abstract void setSupportZoom(boolean z);

    public abstract void setTextZoom(int i);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void setUseWebViewBackgroundForOverscrollBackground(boolean z);

    public abstract void setUseWideViewPort(boolean z);

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public abstract void setUserAgent(int i);

    public abstract void setUserAgentString(java.lang.String str);

    @android.annotation.SystemApi
    public abstract void setVideoOverlayForEmbeddedEncryptedVideoEnabled(boolean z);

    public abstract boolean supportMultipleWindows();

    public abstract boolean supportZoom();

    @java.lang.Deprecated
    public enum TextSize {
        SMALLEST(50),
        SMALLER(75),
        NORMAL(100),
        LARGER(150),
        LARGEST(200);

        int value;

        TextSize(int i) {
            this.value = i;
        }
    }

    public enum ZoomDensity {
        FAR(150),
        MEDIUM(100),
        CLOSE(75);

        int value;

        ZoomDensity(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    @java.lang.Deprecated
    public synchronized void setTextSize(android.webkit.WebSettings.TextSize textSize) {
        setTextZoom(textSize.value);
    }

    @java.lang.Deprecated
    public synchronized android.webkit.WebSettings.TextSize getTextSize() {
        int textZoom = getTextZoom();
        android.webkit.WebSettings.TextSize textSize = null;
        int i = Integer.MAX_VALUE;
        for (android.webkit.WebSettings.TextSize textSize2 : android.webkit.WebSettings.TextSize.values()) {
            int abs = java.lang.Math.abs(textZoom - textSize2.value);
            if (abs == 0) {
                return textSize2;
            }
            if (abs < i) {
                textSize = textSize2;
                i = abs;
            }
        }
        if (textSize == null) {
            textSize = android.webkit.WebSettings.TextSize.NORMAL;
        }
        return textSize;
    }

    @java.lang.Deprecated
    public void setUseDoubleTree(boolean z) {
    }

    @java.lang.Deprecated
    public boolean getUseDoubleTree() {
        return false;
    }

    @java.lang.Deprecated
    public void setPluginsPath(java.lang.String str) {
    }

    @java.lang.Deprecated
    public void setAppCacheEnabled(boolean z) {
    }

    @java.lang.Deprecated
    public void setAppCachePath(java.lang.String str) {
    }

    @java.lang.Deprecated
    public void setAppCacheMaxSize(long j) {
    }

    @java.lang.Deprecated
    public java.lang.String getPluginsPath() {
        return "";
    }

    public static java.lang.String getDefaultUserAgent(android.content.Context context) {
        return android.webkit.WebViewFactory.getProvider().getStatics().getDefaultUserAgent(context);
    }

    public void setForceDark(int i) {
    }

    public int getForceDark() {
        return 1;
    }

    public void setAlgorithmicDarkeningAllowed(boolean z) {
    }

    public boolean isAlgorithmicDarkeningAllowed() {
        return false;
    }
}
