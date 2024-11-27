package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AnalogFrontendCapabilities extends android.media.tv.tuner.frontend.FrontendCapabilities {
    private final int mSifStandardCap;
    private final int mTypeCap;

    private AnalogFrontendCapabilities(int i, int i2) {
        this.mTypeCap = i;
        this.mSifStandardCap = i2;
    }

    public int getSignalTypeCapability() {
        return this.mTypeCap;
    }

    public int getSifStandardCapability() {
        return this.mSifStandardCap;
    }
}
