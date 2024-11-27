package com.android.server.usage;

/* loaded from: classes2.dex */
class UserBroadcastEvents {
    private android.util.ArrayMap<java.lang.String, android.util.ArraySet<com.android.server.usage.BroadcastEvent>> mBroadcastEvents = new android.util.ArrayMap<>();

    UserBroadcastEvents() {
    }

    @android.annotation.Nullable
    android.util.ArraySet<com.android.server.usage.BroadcastEvent> getBroadcastEvents(@android.annotation.NonNull java.lang.String str) {
        return this.mBroadcastEvents.get(str);
    }

    @android.annotation.NonNull
    android.util.ArraySet<com.android.server.usage.BroadcastEvent> getOrCreateBroadcastEvents(@android.annotation.NonNull java.lang.String str) {
        android.util.ArraySet<com.android.server.usage.BroadcastEvent> arraySet = this.mBroadcastEvents.get(str);
        if (arraySet == null) {
            android.util.ArraySet<com.android.server.usage.BroadcastEvent> arraySet2 = new android.util.ArraySet<>();
            this.mBroadcastEvents.put(str, arraySet2);
            return arraySet2;
        }
        return arraySet;
    }

    void onPackageRemoved(@android.annotation.NonNull java.lang.String str) {
        this.mBroadcastEvents.remove(str);
    }

    void onUidRemoved(int i) {
        clear(i);
    }

    void clear(int i) {
        for (int size = this.mBroadcastEvents.size() - 1; size >= 0; size--) {
            android.util.ArraySet<com.android.server.usage.BroadcastEvent> valueAt = this.mBroadcastEvents.valueAt(size);
            for (int size2 = valueAt.size() - 1; size2 >= 0; size2--) {
                if (valueAt.valueAt(size2).getSourceUid() == i) {
                    valueAt.removeAt(size2);
                }
            }
        }
    }

    void dump(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < this.mBroadcastEvents.size(); i++) {
            java.lang.String keyAt = this.mBroadcastEvents.keyAt(i);
            android.util.ArraySet<com.android.server.usage.BroadcastEvent> valueAt = this.mBroadcastEvents.valueAt(i);
            indentingPrintWriter.println(keyAt + ":");
            indentingPrintWriter.increaseIndent();
            if (valueAt.size() == 0) {
                indentingPrintWriter.println("<empty>");
            } else {
                for (int i2 = 0; i2 < valueAt.size(); i2++) {
                    com.android.server.usage.BroadcastEvent valueAt2 = valueAt.valueAt(i2);
                    indentingPrintWriter.println(valueAt2);
                    indentingPrintWriter.increaseIndent();
                    android.util.LongArrayQueue timestampsMs = valueAt2.getTimestampsMs();
                    for (int i3 = 0; i3 < timestampsMs.size(); i3++) {
                        if (i3 > 0) {
                            indentingPrintWriter.print(',');
                        }
                        android.util.TimeUtils.formatDuration(timestampsMs.get(i3), indentingPrintWriter);
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.decreaseIndent();
                }
            }
            indentingPrintWriter.decreaseIndent();
        }
    }
}
