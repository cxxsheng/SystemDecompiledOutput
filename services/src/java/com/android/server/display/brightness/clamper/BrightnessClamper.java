package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
abstract class BrightnessClamper<T> {

    @android.annotation.NonNull
    protected final com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener mChangeListener;

    @android.annotation.NonNull
    protected final android.os.Handler mHandler;
    protected float mBrightnessCap = 1.0f;
    protected boolean mIsActive = false;

    protected enum Type {
        THERMAL,
        POWER,
        WEAR_BEDTIME_MODE
    }

    @android.annotation.NonNull
    abstract com.android.server.display.brightness.clamper.BrightnessClamper.Type getType();

    abstract void onDeviceConfigChanged();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract void onDisplayChanged(T t);

    abstract void stop();

    BrightnessClamper(android.os.Handler handler, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener) {
        this.mHandler = handler;
        this.mChangeListener = clamperChangeListener;
    }

    float getBrightnessCap() {
        return this.mBrightnessCap;
    }

    float getCustomAnimationRate() {
        return -1.0f;
    }

    boolean isActive() {
        return this.mIsActive;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void dump(java.io.PrintWriter printWriter) {
        printWriter.println("BrightnessClamper:" + getType());
        printWriter.println(" mBrightnessCap: " + this.mBrightnessCap);
        printWriter.println(" mIsActive: " + this.mIsActive);
    }
}
