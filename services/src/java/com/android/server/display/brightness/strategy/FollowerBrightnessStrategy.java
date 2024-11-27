package com.android.server.display.brightness.strategy;

/* loaded from: classes.dex */
public class FollowerBrightnessStrategy implements com.android.server.display.brightness.strategy.DisplayBrightnessStrategy {
    private float mBrightnessToFollow = Float.NaN;
    private boolean mBrightnessToFollowSlowChange = false;
    private final int mDisplayId;

    public FollowerBrightnessStrategy(int i) {
        this.mDisplayId = i;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public com.android.server.display.DisplayBrightnessState updateBrightness(android.hardware.display.DisplayManagerInternal.DisplayPowerRequest displayPowerRequest) {
        return com.android.server.display.brightness.BrightnessUtils.constructDisplayBrightnessState(10, this.mBrightnessToFollow, this.mBrightnessToFollow, getName(), this.mBrightnessToFollowSlowChange);
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public java.lang.String getName() {
        return "FollowerBrightnessStrategy";
    }

    public float getBrightnessToFollow() {
        return this.mBrightnessToFollow;
    }

    public void setBrightnessToFollow(float f, boolean z) {
        this.mBrightnessToFollow = f;
        this.mBrightnessToFollowSlowChange = z;
    }

    @Override // com.android.server.display.brightness.strategy.DisplayBrightnessStrategy
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("FollowerBrightnessStrategy:");
        printWriter.println("  mDisplayId=" + this.mDisplayId);
        printWriter.println("  mBrightnessToFollow:" + this.mBrightnessToFollow);
        printWriter.println("  mBrightnessToFollowSlowChange:" + this.mBrightnessToFollowSlowChange);
    }
}
