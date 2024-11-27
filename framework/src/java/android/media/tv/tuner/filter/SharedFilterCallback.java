package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public interface SharedFilterCallback {
    void onFilterEvent(android.media.tv.tuner.filter.SharedFilter sharedFilter, android.media.tv.tuner.filter.FilterEvent[] filterEventArr);

    void onFilterStatusChanged(android.media.tv.tuner.filter.SharedFilter sharedFilter, int i);
}
