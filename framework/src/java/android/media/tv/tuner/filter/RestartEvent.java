package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public final class RestartEvent extends android.media.tv.tuner.filter.FilterEvent {
    public static final int NEW_FILTER_FIRST_START_ID = 0;
    private final int mStartId;

    private RestartEvent(int i) {
        this.mStartId = i;
    }

    public int getStartId() {
        return this.mStartId;
    }
}
