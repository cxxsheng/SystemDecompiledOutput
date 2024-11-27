package com.android.server.vibrator;

/* loaded from: classes2.dex */
abstract class GroupedAggregatedLogRecords<T extends com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord> {
    private final int mAggregationTimeLimitMs;
    private final android.util.SparseArray<java.util.ArrayDeque<com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T>>> mGroupedRecords = new android.util.SparseArray<>();
    private final int mSizeLimit;

    interface SingleLogRecord {
        void dump(android.util.IndentingPrintWriter indentingPrintWriter);

        void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j);

        long getCreateUptimeMs();

        int getGroupKey();

        boolean mayAggregate(com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord singleLogRecord);
    }

    abstract void dumpGroupHeader(android.util.IndentingPrintWriter indentingPrintWriter, int i);

    abstract long findGroupKeyProtoFieldId(int i);

    GroupedAggregatedLogRecords(int i, int i2) {
        this.mSizeLimit = i;
        this.mAggregationTimeLimitMs = i2;
    }

    final synchronized com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T> add(T t) {
        try {
            int groupKey = t.getGroupKey();
            if (!this.mGroupedRecords.contains(groupKey)) {
                this.mGroupedRecords.put(groupKey, new java.util.ArrayDeque<>(this.mSizeLimit));
            }
            java.util.ArrayDeque<com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T>> arrayDeque = this.mGroupedRecords.get(groupKey);
            if (this.mAggregationTimeLimitMs > 0 && !arrayDeque.isEmpty()) {
                com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T> last = arrayDeque.getLast();
                if (last.mayAggregate(t, this.mAggregationTimeLimitMs)) {
                    last.record(t);
                    return null;
                }
            }
            com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T> removeFirst = arrayDeque.size() >= this.mSizeLimit ? arrayDeque.removeFirst() : null;
            arrayDeque.addLast(new com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<>(t));
            return removeFirst;
        } catch (java.lang.Throwable th) {
            throw th;
        }
    }

    final synchronized void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < this.mGroupedRecords.size(); i++) {
            try {
                dumpGroupHeader(indentingPrintWriter, this.mGroupedRecords.keyAt(i));
                indentingPrintWriter.increaseIndent();
                java.util.Iterator<com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T>> it = this.mGroupedRecords.valueAt(i).iterator();
                while (it.hasNext()) {
                    it.next().dump(indentingPrintWriter);
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    final synchronized void dump(android.util.proto.ProtoOutputStream protoOutputStream) {
        for (int i = 0; i < this.mGroupedRecords.size(); i++) {
            long findGroupKeyProtoFieldId = findGroupKeyProtoFieldId(this.mGroupedRecords.keyAt(i));
            java.util.Iterator<com.android.server.vibrator.GroupedAggregatedLogRecords.AggregatedLogRecord<T>> it = this.mGroupedRecords.valueAt(i).iterator();
            while (it.hasNext()) {
                it.next().dump(protoOutputStream, findGroupKeyProtoFieldId);
            }
        }
    }

    static final class AggregatedLogRecord<T extends com.android.server.vibrator.GroupedAggregatedLogRecords.SingleLogRecord> {
        private int mCount = 1;
        private final T mFirst;
        private T mLatest;

        AggregatedLogRecord(T t) {
            this.mFirst = t;
            this.mLatest = t;
        }

        T getLatest() {
            return this.mLatest;
        }

        synchronized boolean mayAggregate(T t, long j) {
            return this.mLatest.mayAggregate(t) && java.lang.Math.abs(this.mLatest.getCreateUptimeMs() - t.getCreateUptimeMs()) < j;
        }

        synchronized void record(T t) {
            this.mLatest = t;
            this.mCount++;
        }

        synchronized void dump(android.util.IndentingPrintWriter indentingPrintWriter) {
            try {
                this.mFirst.dump(indentingPrintWriter);
                if (this.mCount == 1) {
                    return;
                }
                if (this.mCount > 2) {
                    indentingPrintWriter.println("-> Skipping " + (this.mCount - 2) + " aggregated entries, latest:");
                }
                this.mLatest.dump(indentingPrintWriter);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }

        synchronized void dump(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
            this.mFirst.dump(protoOutputStream, j);
            if (this.mCount > 1) {
                this.mLatest.dump(protoOutputStream, j);
            }
        }
    }
}
