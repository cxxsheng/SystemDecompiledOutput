package com.android.server.display.brightness.clamper;

/* loaded from: classes.dex */
public class BrightnessWearBedtimeModeClamper extends com.android.server.display.brightness.clamper.BrightnessClamper<com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData> {
    public static final int BEDTIME_MODE_OFF = 0;
    public static final int BEDTIME_MODE_ON = 1;
    private final android.content.Context mContext;
    private final android.database.ContentObserver mSettingsObserver;

    interface WearBedtimeModeData {
        float getBrightnessWearBedtimeModeCap();
    }

    BrightnessWearBedtimeModeClamper(android.os.Handler handler, android.content.Context context, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData wearBedtimeModeData) {
        this(new com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.Injector(), handler, context, clamperChangeListener, wearBedtimeModeData);
    }

    @com.android.internal.annotations.VisibleForTesting
    BrightnessWearBedtimeModeClamper(com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.Injector injector, android.os.Handler handler, android.content.Context context, com.android.server.display.brightness.clamper.BrightnessClamperController.ClamperChangeListener clamperChangeListener, com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData wearBedtimeModeData) {
        super(handler, clamperChangeListener);
        this.mContext = context;
        this.mBrightnessCap = wearBedtimeModeData.getBrightnessWearBedtimeModeCap();
        this.mSettingsObserver = new android.database.ContentObserver(this.mHandler) { // from class: com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                int i = android.provider.Settings.Global.getInt(com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.this.mContext.getContentResolver(), "bedtime_mode", 0);
                com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.this.mIsActive = i == 1;
                com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.this.mChangeListener.onChanged();
            }
        };
        injector.registerBedtimeModeObserver(context.getContentResolver(), this.mSettingsObserver);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    @android.annotation.NonNull
    com.android.server.display.brightness.clamper.BrightnessClamper.Type getType() {
        return com.android.server.display.brightness.clamper.BrightnessClamper.Type.WEAR_BEDTIME_MODE;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void onDeviceConfigChanged() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public void onDisplayChanged(final com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData wearBedtimeModeData) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.this.lambda$onDisplayChanged$0(wearBedtimeModeData);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDisplayChanged$0(com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.WearBedtimeModeData wearBedtimeModeData) {
        this.mBrightnessCap = wearBedtimeModeData.getBrightnessWearBedtimeModeCap();
        this.mChangeListener.onChanged();
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    void stop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }

    @com.android.internal.annotations.VisibleForTesting
    static class Injector {
        Injector() {
        }

        void registerBedtimeModeObserver(@android.annotation.NonNull android.content.ContentResolver contentResolver, @android.annotation.NonNull android.database.ContentObserver contentObserver) {
            contentResolver.registerContentObserver(android.provider.Settings.Global.getUriFor("bedtime_mode"), false, contentObserver, -1);
        }
    }
}
