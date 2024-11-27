package android.media.tv.tuner.frontend;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class IptvFrontendCapabilities extends android.media.tv.tuner.frontend.FrontendCapabilities {
    private final int mProtocolCap;

    private IptvFrontendCapabilities(int i) {
        this.mProtocolCap = i;
    }

    public int getProtocolCapability() {
        return this.mProtocolCap;
    }
}
