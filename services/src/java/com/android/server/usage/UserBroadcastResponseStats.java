package com.android.server.usage;

/* loaded from: classes2.dex */
class UserBroadcastResponseStats {
    private android.util.ArrayMap<com.android.server.usage.BroadcastEvent, android.app.usage.BroadcastResponseStats> mResponseStats = new android.util.ArrayMap<>();

    UserBroadcastResponseStats() {
    }

    @android.annotation.Nullable
    android.app.usage.BroadcastResponseStats getBroadcastResponseStats(com.android.server.usage.BroadcastEvent broadcastEvent) {
        return this.mResponseStats.get(broadcastEvent);
    }

    @android.annotation.NonNull
    android.app.usage.BroadcastResponseStats getOrCreateBroadcastResponseStats(com.android.server.usage.BroadcastEvent broadcastEvent) {
        android.app.usage.BroadcastResponseStats broadcastResponseStats = this.mResponseStats.get(broadcastEvent);
        if (broadcastResponseStats == null) {
            android.app.usage.BroadcastResponseStats broadcastResponseStats2 = new android.app.usage.BroadcastResponseStats(broadcastEvent.getTargetPackage(), broadcastEvent.getIdForResponseEvent());
            this.mResponseStats.put(broadcastEvent, broadcastResponseStats2);
            return broadcastResponseStats2;
        }
        return broadcastResponseStats;
    }

    void populateAllBroadcastResponseStats(@android.annotation.NonNull java.util.List<android.app.usage.BroadcastResponseStats> list, @android.annotation.Nullable java.lang.String str, long j) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            com.android.server.usage.BroadcastEvent keyAt = this.mResponseStats.keyAt(size);
            if ((j == 0 || j == keyAt.getIdForResponseEvent()) && (str == null || str.equals(keyAt.getTargetPackage()))) {
                list.add(this.mResponseStats.valueAt(size));
            }
        }
    }

    void clearBroadcastResponseStats(@android.annotation.Nullable java.lang.String str, long j) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            com.android.server.usage.BroadcastEvent keyAt = this.mResponseStats.keyAt(size);
            if ((j == 0 || j == keyAt.getIdForResponseEvent()) && (str == null || str.equals(keyAt.getTargetPackage()))) {
                this.mResponseStats.removeAt(size);
            }
        }
    }

    void onPackageRemoved(@android.annotation.NonNull java.lang.String str) {
        for (int size = this.mResponseStats.size() - 1; size >= 0; size--) {
            if (this.mResponseStats.keyAt(size).getTargetPackage().equals(str)) {
                this.mResponseStats.removeAt(size);
            }
        }
    }

    void dump(@android.annotation.NonNull com.android.internal.util.IndentingPrintWriter indentingPrintWriter) {
        for (int i = 0; i < this.mResponseStats.size(); i++) {
            com.android.server.usage.BroadcastEvent keyAt = this.mResponseStats.keyAt(i);
            android.app.usage.BroadcastResponseStats valueAt = this.mResponseStats.valueAt(i);
            indentingPrintWriter.print(keyAt);
            indentingPrintWriter.print(" -> ");
            indentingPrintWriter.println(valueAt);
        }
    }
}
