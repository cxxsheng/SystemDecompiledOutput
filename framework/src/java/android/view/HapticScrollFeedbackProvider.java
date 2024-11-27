package android.view;

/* loaded from: classes4.dex */
public class HapticScrollFeedbackProvider implements android.view.ScrollFeedbackProvider {
    private static final boolean INITIAL_END_OF_LIST_HAPTICS_ENABLED = false;
    private static final java.lang.String TAG = "HapticScrollFeedbackProvider";
    private static final int TICK_INTERVAL_NO_TICK = 0;
    private int mAxis;
    private boolean mCanPlayLimitFeedback;
    private int mDeviceId;
    private final boolean mDisabledIfViewPlaysScrollHaptics;
    private boolean mHapticScrollFeedbackEnabled;
    private int mSource;
    private int mTickIntervalPixels;
    private int mTotalScrollPixels;
    private final android.view.View mView;
    private final android.view.ViewConfiguration mViewConfig;

    public HapticScrollFeedbackProvider(android.view.View view) {
        this(view, android.view.ViewConfiguration.get(view.getContext()), true);
    }

    public HapticScrollFeedbackProvider(android.view.View view, android.view.ViewConfiguration viewConfiguration, boolean z) {
        this.mDeviceId = -1;
        this.mAxis = -1;
        this.mSource = -1;
        this.mTickIntervalPixels = 0;
        this.mTotalScrollPixels = 0;
        this.mCanPlayLimitFeedback = false;
        this.mHapticScrollFeedbackEnabled = false;
        this.mView = view;
        this.mViewConfig = viewConfiguration;
        this.mDisabledIfViewPlaysScrollHaptics = z;
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollProgress(int i, int i2, int i3, int i4) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (!this.mHapticScrollFeedbackEnabled) {
            return;
        }
        if (i4 != 0) {
            this.mCanPlayLimitFeedback = true;
        }
        if (this.mTickIntervalPixels == 0) {
            return;
        }
        this.mTotalScrollPixels += i4;
        if (java.lang.Math.abs(this.mTotalScrollPixels) >= this.mTickIntervalPixels) {
            this.mTotalScrollPixels %= this.mTickIntervalPixels;
            this.mView.performHapticFeedback(18);
        }
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onScrollLimit(int i, int i2, int i3, boolean z) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (!this.mHapticScrollFeedbackEnabled || !this.mCanPlayLimitFeedback) {
            return;
        }
        this.mView.performHapticFeedback(20);
        this.mCanPlayLimitFeedback = false;
    }

    @Override // android.view.ScrollFeedbackProvider
    public void onSnapToItem(int i, int i2, int i3) {
        maybeUpdateCurrentConfig(i, i2, i3);
        if (!this.mHapticScrollFeedbackEnabled) {
            return;
        }
        this.mView.performHapticFeedback(19);
        this.mCanPlayLimitFeedback = true;
    }

    private void maybeUpdateCurrentConfig(int i, int i2, int i3) {
        if (this.mAxis != i3 || this.mSource != i2 || this.mDeviceId != i) {
            if (this.mDisabledIfViewPlaysScrollHaptics && i2 == 4194304 && this.mViewConfig.isViewBasedRotaryEncoderHapticScrollFeedbackEnabled()) {
                this.mHapticScrollFeedbackEnabled = false;
                return;
            }
            this.mSource = i2;
            this.mAxis = i3;
            this.mDeviceId = i;
            this.mHapticScrollFeedbackEnabled = this.mViewConfig.isHapticScrollFeedbackEnabled(i, i3, i2);
            this.mCanPlayLimitFeedback = false;
            this.mTotalScrollPixels = 0;
            updateTickIntervals(i, i2, i3);
        }
    }

    private void updateTickIntervals(int i, int i2, int i3) {
        int i4;
        if (this.mHapticScrollFeedbackEnabled) {
            i4 = this.mViewConfig.getHapticScrollFeedbackTickInterval(i, i3, i2);
        } else {
            i4 = 0;
        }
        this.mTickIntervalPixels = i4;
    }
}
