package com.android.internal.app.procstats;

/* loaded from: classes4.dex */
public class DurationsTable extends com.android.internal.app.procstats.SparseMappingTable.Table {
    public DurationsTable(com.android.internal.app.procstats.SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public void addDurations(com.android.internal.app.procstats.DurationsTable durationsTable) {
        int keyCount = durationsTable.getKeyCount();
        for (int i = 0; i < keyCount; i++) {
            int keyAt = durationsTable.getKeyAt(i);
            addDuration(com.android.internal.app.procstats.SparseMappingTable.getIdFromKey(keyAt), durationsTable.getValue(keyAt));
        }
    }

    public void addDuration(int i, long j) {
        int orAddKey = getOrAddKey((byte) i, 1);
        setValue(orAddKey, getValue(orAddKey) + j);
    }
}
