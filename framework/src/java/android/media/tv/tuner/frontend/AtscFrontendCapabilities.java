package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class AtscFrontendCapabilities extends android.media.tv.tuner.frontend.FrontendCapabilities {
    private final int mModulationCap;

    private AtscFrontendCapabilities(int i) {
        this.mModulationCap = i;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }
}
