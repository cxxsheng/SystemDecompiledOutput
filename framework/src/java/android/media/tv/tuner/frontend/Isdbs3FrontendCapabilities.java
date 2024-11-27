package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Isdbs3FrontendCapabilities extends android.media.tv.tuner.frontend.FrontendCapabilities {
    private final int mCodeRateCap;
    private final int mModulationCap;

    private Isdbs3FrontendCapabilities(int i, int i2) {
        this.mModulationCap = i;
        this.mCodeRateCap = i2;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    public int getCodeRateCapability() {
        return this.mCodeRateCap;
    }
}