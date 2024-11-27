package android.media.tv.tuner.filter;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public interface FilterCallback {
    void onFilterEvent(android.media.tv.tuner.filter.Filter filter, android.media.tv.tuner.filter.FilterEvent[] filterEventArr);

    void onFilterStatusChanged(android.media.tv.tuner.filter.Filter filter, int i);
}
