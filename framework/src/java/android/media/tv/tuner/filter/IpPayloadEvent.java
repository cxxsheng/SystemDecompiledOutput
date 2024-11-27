package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public class IpPayloadEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final int mDataLength;

    private IpPayloadEvent(int i) {
        this.mDataLength = i;
    }

    public int getDataLength() {
        return this.mDataLength;
    }
}
