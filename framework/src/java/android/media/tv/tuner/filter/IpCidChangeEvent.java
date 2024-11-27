package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class IpCidChangeEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final int mCid;

    private IpCidChangeEvent(int i) {
        this.mCid = i;
    }

    public int getIpCid() {
        return this.mCid;
    }
}
