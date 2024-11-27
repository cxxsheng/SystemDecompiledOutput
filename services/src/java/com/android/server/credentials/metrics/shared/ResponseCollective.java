package com.android.server.credentials.metrics.shared;

/* loaded from: classes.dex */
public class ResponseCollective {
    private static final java.lang.String TAG = "ResponseCollective";
    private final java.util.Map<com.android.server.credentials.metrics.EntryEnum, java.lang.Integer> mEntryCounts;
    private final java.util.Map<java.lang.String, java.lang.Integer> mResponseCounts;

    public ResponseCollective(@android.annotation.NonNull java.util.Map<java.lang.String, java.lang.Integer> map, @android.annotation.NonNull java.util.Map<com.android.server.credentials.metrics.EntryEnum, java.lang.Integer> map2) {
        this.mResponseCounts = map == null ? new java.util.LinkedHashMap() : new java.util.LinkedHashMap(map);
        this.mEntryCounts = map2 == null ? new java.util.LinkedHashMap() : new java.util.LinkedHashMap(map2);
    }

    public java.lang.String[] getUniqueResponseStrings() {
        java.lang.String[] strArr = new java.lang.String[this.mResponseCounts.keySet().size()];
        this.mResponseCounts.keySet().toArray(strArr);
        return strArr;
    }

    public java.util.Map<com.android.server.credentials.metrics.EntryEnum, java.lang.Integer> getEntryCountsMap() {
        return java.util.Collections.unmodifiableMap(this.mEntryCounts);
    }

    public java.util.Map<java.lang.String, java.lang.Integer> getResponseCountsMap() {
        return java.util.Collections.unmodifiableMap(this.mResponseCounts);
    }

    public int[] getUniqueResponseCounts() {
        return this.mResponseCounts.values().stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public int[] getUniqueEntries() {
        return this.mEntryCounts.keySet().stream().mapToInt(new java.util.function.ToIntFunction() { // from class: com.android.server.credentials.metrics.shared.ResponseCollective$$ExternalSyntheticLambda0
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(java.lang.Object obj) {
                return ((com.android.server.credentials.metrics.EntryEnum) obj).ordinal();
            }
        }).toArray();
    }

    public int[] getUniqueEntryCounts() {
        return this.mEntryCounts.values().stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).toArray();
    }

    public int getCountForEntry(com.android.server.credentials.metrics.EntryEnum entryEnum) {
        return this.mEntryCounts.getOrDefault(entryEnum, 0).intValue();
    }

    public int getNumEntriesTotal() {
        return this.mEntryCounts.values().stream().mapToInt(new com.android.server.audio.AudioService$$ExternalSyntheticLambda0()).sum();
    }

    public com.android.server.credentials.metrics.shared.ResponseCollective combineCollectives(com.android.server.credentials.metrics.shared.ResponseCollective responseCollective) {
        if (this == responseCollective) {
            return this;
        }
        java.util.LinkedHashMap linkedHashMap = new java.util.LinkedHashMap(responseCollective.mResponseCounts);
        for (java.lang.String str : this.mResponseCounts.keySet()) {
            linkedHashMap.merge(str, this.mResponseCounts.get(str), new java.util.function.BiFunction() { // from class: com.android.server.credentials.metrics.shared.ResponseCollective$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    return java.lang.Integer.valueOf(java.lang.Integer.sum(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue()));
                }
            });
        }
        java.util.LinkedHashMap linkedHashMap2 = new java.util.LinkedHashMap(responseCollective.mEntryCounts);
        for (com.android.server.credentials.metrics.EntryEnum entryEnum : this.mEntryCounts.keySet()) {
            linkedHashMap2.merge(entryEnum, this.mEntryCounts.get(entryEnum), new java.util.function.BiFunction() { // from class: com.android.server.credentials.metrics.shared.ResponseCollective$$ExternalSyntheticLambda1
                @Override // java.util.function.BiFunction
                public final java.lang.Object apply(java.lang.Object obj, java.lang.Object obj2) {
                    return java.lang.Integer.valueOf(java.lang.Integer.sum(((java.lang.Integer) obj).intValue(), ((java.lang.Integer) obj2).intValue()));
                }
            });
        }
        return new com.android.server.credentials.metrics.shared.ResponseCollective(linkedHashMap, linkedHashMap2);
    }

    public static <T> java.util.Map<T, java.lang.Integer> combineTypeCountMaps(java.util.Map<T, java.lang.Integer> map, java.util.Map<T, java.lang.Integer> map2) {
        for (T t : map2.keySet()) {
            map.put(t, java.lang.Integer.valueOf(map.getOrDefault(t, 0).intValue() + map2.get(t).intValue()));
        }
        return map;
    }
}
