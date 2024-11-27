package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class DvbcFrontendCapabilities extends android.media.tv.tuner.frontend.FrontendCapabilities {
    private final int mAnnexCap;
    private final long mFecCap;
    private final int mModulationCap;

    private DvbcFrontendCapabilities(int i, long j, int i2) {
        this.mModulationCap = i;
        this.mFecCap = j;
        this.mAnnexCap = i2;
    }

    public int getModulationCapability() {
        return this.mModulationCap;
    }

    @java.lang.Deprecated
    public int getFecCapability() {
        if (this.mFecCap > 2147483647L) {
            return 0;
        }
        return (int) this.mFecCap;
    }

    public long getCodeRateCapability() {
        return this.mFecCap;
    }

    public int getAnnexCapability() {
        return this.mAnnexCap;
    }
}
