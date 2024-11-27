package com.android.server.credentials.metrics;

/* loaded from: classes.dex */
public class CandidateBrowsingPhaseMetric {
    private int mEntryEnum = com.android.server.credentials.metrics.EntryEnum.UNKNOWN.getMetricCode();
    private int mProviderUid = -1;

    public void setEntryEnum(int i) {
        this.mEntryEnum = i;
    }

    public int getEntryEnum() {
        return this.mEntryEnum;
    }

    public void setProviderUid(int i) {
        this.mProviderUid = i;
    }

    public int getProviderUid() {
        return this.mProviderUid;
    }
}
