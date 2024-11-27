package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class Atsc3PlpInfo {
    private final boolean mLlsFlag;
    private final int mPlpId;

    private Atsc3PlpInfo(int i, boolean z) {
        this.mPlpId = i;
        this.mLlsFlag = z;
    }

    public int getPlpId() {
        return this.mPlpId;
    }

    public boolean getLlsFlag() {
        return this.mLlsFlag;
    }
}
