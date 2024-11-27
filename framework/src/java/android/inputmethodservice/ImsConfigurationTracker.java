package android.inputmethodservice;

/* loaded from: classes2.dex */
public final class ImsConfigurationTracker {
    private static final int CONFIG_CHANGED = -1;
    private android.content.res.Configuration mLastKnownConfig = null;
    private int mHandledConfigChanges = 0;
    private boolean mInitialized = false;

    public void onInitialize(int i) {
        com.android.internal.util.Preconditions.checkState(!this.mInitialized, "onInitialize can be called only once.");
        this.mInitialized = true;
        this.mHandledConfigChanges = i;
    }

    public void onBindInput(android.content.res.Resources resources) {
        if (this.mInitialized && this.mLastKnownConfig == null && resources != null) {
            this.mLastKnownConfig = new android.content.res.Configuration(resources.getConfiguration());
        }
    }

    public void setHandledConfigChanges(int i) {
        this.mHandledConfigChanges = i;
    }

    public void onConfigurationChanged(android.content.res.Configuration configuration, java.lang.Runnable runnable) {
        if (!this.mInitialized) {
            return;
        }
        int diffPublicOnly = this.mLastKnownConfig != null ? this.mLastKnownConfig.diffPublicOnly(configuration) : -1;
        if (((~this.mHandledConfigChanges) & diffPublicOnly) != 0) {
            runnable.run();
        }
        if (diffPublicOnly != 0) {
            this.mLastKnownConfig = new android.content.res.Configuration(configuration);
        }
    }
}
