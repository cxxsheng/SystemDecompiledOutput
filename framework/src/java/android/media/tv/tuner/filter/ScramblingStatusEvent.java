package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class ScramblingStatusEvent extends android.media.tv.tuner.filter.FilterEvent {
    private final int mScramblingStatus;

    private ScramblingStatusEvent(int i) {
        this.mScramblingStatus = i;
    }

    public int getScramblingStatus() {
        return this.mScramblingStatus;
    }
}
