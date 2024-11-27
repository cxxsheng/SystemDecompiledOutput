package com.android.server.policy;

/* loaded from: classes2.dex */
public class BurnInProtectionHelper implements android.hardware.display.DisplayManager.DisplayListener, android.animation.Animator.AnimatorListener, android.animation.ValueAnimator.AnimatorUpdateListener {
    private static final java.lang.String ACTION_BURN_IN_PROTECTION = "android.internal.policy.action.BURN_IN_PROTECTION";
    public static final int BURN_IN_MAX_RADIUS_DEFAULT = -1;
    private static final int BURN_IN_SHIFT_STEP = 2;
    private static final long CENTERING_ANIMATION_DURATION_MS = 100;
    private static final boolean DEBUG = false;
    private static final java.lang.String TAG = "BurnInProtection";
    private final android.app.AlarmManager mAlarmManager;
    private boolean mBurnInProtectionActive;
    private final android.app.PendingIntent mBurnInProtectionIntent;
    private final int mBurnInRadiusMaxSquared;
    private final android.animation.ValueAnimator mCenteringAnimator;
    private final android.view.Display mDisplay;
    private final android.hardware.display.DisplayManagerInternal mDisplayManagerInternal;
    private boolean mFirstUpdate;
    private final int mMaxHorizontalBurnInOffset;
    private final int mMaxVerticalBurnInOffset;
    private final int mMinHorizontalBurnInOffset;
    private final int mMinVerticalBurnInOffset;
    private static final long BURNIN_PROTECTION_FIRST_WAKEUP_INTERVAL_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);
    private static final long BURNIN_PROTECTION_SUBSEQUENT_WAKEUP_INTERVAL_MS = java.util.concurrent.TimeUnit.MINUTES.toMillis(2);
    private static final long BURNIN_PROTECTION_MINIMAL_INTERVAL_MS = java.util.concurrent.TimeUnit.SECONDS.toMillis(10);
    private int mLastBurnInXOffset = 0;
    private int mXOffsetDirection = 1;
    private int mLastBurnInYOffset = 0;
    private int mYOffsetDirection = 1;
    private int mAppliedBurnInXOffset = 0;
    private int mAppliedBurnInYOffset = 0;
    private android.content.BroadcastReceiver mBurnInProtectionReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.policy.BurnInProtectionHelper.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.policy.BurnInProtectionHelper.this.updateBurnInProtection();
        }
    };

    public BurnInProtectionHelper(android.content.Context context, int i, int i2, int i3, int i4, int i5) {
        this.mMinHorizontalBurnInOffset = i;
        this.mMaxHorizontalBurnInOffset = i2;
        this.mMinVerticalBurnInOffset = i3;
        this.mMaxVerticalBurnInOffset = i4;
        if (i5 != -1) {
            this.mBurnInRadiusMaxSquared = i5 * i5;
        } else {
            this.mBurnInRadiusMaxSquared = -1;
        }
        this.mDisplayManagerInternal = (android.hardware.display.DisplayManagerInternal) com.android.server.LocalServices.getService(android.hardware.display.DisplayManagerInternal.class);
        this.mAlarmManager = (android.app.AlarmManager) context.getSystemService(com.android.server.am.HostingRecord.TRIGGER_TYPE_ALARM);
        context.registerReceiver(this.mBurnInProtectionReceiver, new android.content.IntentFilter(ACTION_BURN_IN_PROTECTION));
        android.content.Intent intent = new android.content.Intent(ACTION_BURN_IN_PROTECTION);
        intent.setPackage(context.getPackageName());
        intent.setFlags(1073741824);
        this.mBurnInProtectionIntent = android.app.PendingIntent.getBroadcast(context, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.E_AC3);
        android.hardware.display.DisplayManager displayManager = (android.hardware.display.DisplayManager) context.getSystemService("display");
        this.mDisplay = displayManager.getDisplay(0);
        displayManager.registerDisplayListener(this, null);
        this.mCenteringAnimator = android.animation.ValueAnimator.ofFloat(1.0f, com.android.server.wm.DesktopModeLaunchParamsModifier.DESKTOP_MODE_INITIAL_BOUNDS_SCALE);
        this.mCenteringAnimator.setDuration(CENTERING_ANIMATION_DURATION_MS);
        this.mCenteringAnimator.setInterpolator(new android.view.animation.LinearInterpolator());
        this.mCenteringAnimator.addListener(this);
        this.mCenteringAnimator.addUpdateListener(this);
    }

    public void startBurnInProtection() {
        if (!this.mBurnInProtectionActive) {
            this.mBurnInProtectionActive = true;
            this.mFirstUpdate = true;
            this.mCenteringAnimator.cancel();
            updateBurnInProtection();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBurnInProtection() {
        long j;
        if (this.mBurnInProtectionActive) {
            if (this.mFirstUpdate) {
                j = BURNIN_PROTECTION_FIRST_WAKEUP_INTERVAL_MS;
            } else {
                j = BURNIN_PROTECTION_SUBSEQUENT_WAKEUP_INTERVAL_MS;
            }
            if (this.mFirstUpdate) {
                this.mFirstUpdate = false;
            } else {
                adjustOffsets();
                this.mAppliedBurnInXOffset = this.mLastBurnInXOffset;
                this.mAppliedBurnInYOffset = this.mLastBurnInYOffset;
                this.mDisplayManagerInternal.setDisplayOffsets(this.mDisplay.getDisplayId(), this.mLastBurnInXOffset, this.mLastBurnInYOffset);
            }
            long currentTimeMillis = java.lang.System.currentTimeMillis();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
            long j2 = BURNIN_PROTECTION_MINIMAL_INTERVAL_MS + currentTimeMillis;
            this.mAlarmManager.setExact(3, elapsedRealtime + (((j2 - (j2 % j)) + j) - currentTimeMillis), this.mBurnInProtectionIntent);
            return;
        }
        this.mAlarmManager.cancel(this.mBurnInProtectionIntent);
        this.mCenteringAnimator.start();
    }

    public void cancelBurnInProtection() {
        if (this.mBurnInProtectionActive) {
            this.mBurnInProtectionActive = false;
            updateBurnInProtection();
        }
    }

    private void adjustOffsets() {
        do {
            int i = this.mXOffsetDirection * 2;
            this.mLastBurnInXOffset += i;
            if (this.mLastBurnInXOffset > this.mMaxHorizontalBurnInOffset || this.mLastBurnInXOffset < this.mMinHorizontalBurnInOffset) {
                this.mLastBurnInXOffset -= i;
                this.mXOffsetDirection *= -1;
                int i2 = this.mYOffsetDirection * 2;
                this.mLastBurnInYOffset += i2;
                if (this.mLastBurnInYOffset > this.mMaxVerticalBurnInOffset || this.mLastBurnInYOffset < this.mMinVerticalBurnInOffset) {
                    this.mLastBurnInYOffset -= i2;
                    this.mYOffsetDirection *= -1;
                }
            }
            if (this.mBurnInRadiusMaxSquared == -1) {
                return;
            }
        } while ((this.mLastBurnInXOffset * this.mLastBurnInXOffset) + (this.mLastBurnInYOffset * this.mLastBurnInYOffset) > this.mBurnInRadiusMaxSquared);
    }

    public void dump(java.lang.String str, java.io.PrintWriter printWriter) {
        printWriter.println(str + TAG);
        java.lang.String str2 = str + "  ";
        printWriter.println(str2 + "mBurnInProtectionActive=" + this.mBurnInProtectionActive);
        printWriter.println(str2 + "mHorizontalBurnInOffsetsBounds=(" + this.mMinHorizontalBurnInOffset + ", " + this.mMaxHorizontalBurnInOffset + ")");
        printWriter.println(str2 + "mVerticalBurnInOffsetsBounds=(" + this.mMinVerticalBurnInOffset + ", " + this.mMaxVerticalBurnInOffset + ")");
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str2);
        sb.append("mBurnInRadiusMaxSquared=");
        sb.append(this.mBurnInRadiusMaxSquared);
        printWriter.println(sb.toString());
        printWriter.println(str2 + "mLastBurnInOffset=(" + this.mLastBurnInXOffset + ", " + this.mLastBurnInYOffset + ")");
        printWriter.println(str2 + "mOfsetChangeDirections=(" + this.mXOffsetDirection + ", " + this.mYOffsetDirection + ")");
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayAdded(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayRemoved(int i) {
    }

    @Override // android.hardware.display.DisplayManager.DisplayListener
    public void onDisplayChanged(int i) {
        if (i == this.mDisplay.getDisplayId()) {
            if (this.mDisplay.getState() == 3 || this.mDisplay.getState() == 4 || this.mDisplay.getState() == 6) {
                startBurnInProtection();
            } else {
                cancelBurnInProtection();
            }
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(android.animation.Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(android.animation.Animator animator) {
        if (animator == this.mCenteringAnimator && !this.mBurnInProtectionActive) {
            this.mAppliedBurnInXOffset = 0;
            this.mAppliedBurnInYOffset = 0;
            this.mDisplayManagerInternal.setDisplayOffsets(this.mDisplay.getDisplayId(), 0, 0);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(android.animation.Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(android.animation.Animator animator) {
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(android.animation.ValueAnimator valueAnimator) {
        if (!this.mBurnInProtectionActive) {
            float floatValue = ((java.lang.Float) valueAnimator.getAnimatedValue()).floatValue();
            this.mDisplayManagerInternal.setDisplayOffsets(this.mDisplay.getDisplayId(), (int) (this.mAppliedBurnInXOffset * floatValue), (int) (this.mAppliedBurnInYOffset * floatValue));
        }
    }
}
