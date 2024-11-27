package com.android.server.broadcastradio.hal2;

/* loaded from: classes.dex */
final class ProgramInfoCache {
    private static final int MAX_NUM_MODIFIED_PER_CHUNK = 100;
    private static final int MAX_NUM_REMOVED_PER_CHUNK = 500;
    private boolean mComplete;
    private final android.hardware.radio.ProgramList.Filter mFilter;
    private final android.util.ArrayMap<android.hardware.radio.ProgramSelector.Identifier, android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo>> mProgramInfoMap;

    ProgramInfoCache(@android.annotation.Nullable android.hardware.radio.ProgramList.Filter filter) {
        this.mProgramInfoMap = new android.util.ArrayMap<>();
        this.mComplete = true;
        this.mFilter = filter;
    }

    @com.android.internal.annotations.VisibleForTesting
    ProgramInfoCache(@android.annotation.Nullable android.hardware.radio.ProgramList.Filter filter, boolean z, android.hardware.radio.RadioManager.ProgramInfo... programInfoArr) {
        this.mProgramInfoMap = new android.util.ArrayMap<>();
        this.mComplete = true;
        this.mFilter = filter;
        this.mComplete = z;
        for (android.hardware.radio.RadioManager.ProgramInfo programInfo : programInfoArr) {
            putInfo(programInfo);
        }
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.hardware.radio.RadioManager.ProgramInfo> toProgramInfoList() {
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (int i = 0; i < this.mProgramInfoMap.size(); i++) {
            arrayList.addAll(this.mProgramInfoMap.valueAt(i).values());
        }
        return arrayList;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("ProgramInfoCache(mComplete = ");
        sb.append(this.mComplete);
        sb.append(", mFilter = ");
        sb.append(this.mFilter);
        sb.append(", mProgramInfoMap = [");
        for (int i = 0; i < this.mProgramInfoMap.size(); i++) {
            android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo> valueAt = this.mProgramInfoMap.valueAt(i);
            for (int i2 = 0; i2 < valueAt.size(); i2++) {
                sb.append(", ");
                sb.append(valueAt.valueAt(i2));
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean isComplete() {
        return this.mComplete;
    }

    @android.annotation.Nullable
    public android.hardware.radio.ProgramList.Filter getFilter() {
        return this.mFilter;
    }

    void updateFromHalProgramListChunk(android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk) {
        if (programListChunk.purge) {
            this.mProgramInfoMap.clear();
        }
        java.util.Iterator<android.hardware.broadcastradio.V2_0.ProgramInfo> it = programListChunk.modified.iterator();
        while (it.hasNext()) {
            putInfo(com.android.server.broadcastradio.hal2.Convert.programInfoFromHal(it.next()));
        }
        java.util.Iterator<android.hardware.broadcastradio.V2_0.ProgramIdentifier> it2 = programListChunk.removed.iterator();
        while (it2.hasNext()) {
            this.mProgramInfoMap.remove(com.android.server.broadcastradio.hal2.Convert.programIdentifierFromHal(it2.next()));
        }
        this.mComplete = programListChunk.complete;
    }

    java.util.List<android.hardware.radio.ProgramList.Chunk> filterAndUpdateFrom(com.android.server.broadcastradio.hal2.ProgramInfoCache programInfoCache, boolean z) {
        return filterAndUpdateFromInternal(programInfoCache, z, 100, 500);
    }

    @com.android.internal.annotations.VisibleForTesting
    java.util.List<android.hardware.radio.ProgramList.Chunk> filterAndUpdateFromInternal(com.android.server.broadcastradio.hal2.ProgramInfoCache programInfoCache, boolean z, int i, int i2) {
        boolean z2;
        if (z) {
            this.mProgramInfoMap.clear();
        }
        if (!this.mProgramInfoMap.isEmpty()) {
            z2 = z;
        } else {
            z2 = true;
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        for (int i3 = 0; i3 < this.mProgramInfoMap.size(); i3++) {
            arraySet2.addAll(this.mProgramInfoMap.valueAt(i3).keySet());
        }
        for (int i4 = 0; i4 < programInfoCache.mProgramInfoMap.size(); i4++) {
            if (passesFilter(programInfoCache.mProgramInfoMap.keyAt(i4))) {
                android.util.ArrayMap<android.hardware.radio.UniqueProgramIdentifier, android.hardware.radio.RadioManager.ProgramInfo> valueAt = programInfoCache.mProgramInfoMap.valueAt(i4);
                for (int i5 = 0; i5 < valueAt.size(); i5++) {
                    arraySet2.remove(valueAt.keyAt(i5));
                    android.hardware.radio.RadioManager.ProgramInfo valueAt2 = valueAt.valueAt(i5);
                    if (shouldIncludeInModified(valueAt2)) {
                        putInfo(valueAt2);
                        arraySet.add(valueAt2);
                    }
                }
            }
        }
        for (int i6 = 0; i6 < arraySet2.size(); i6++) {
            removeUniqueId((android.hardware.radio.UniqueProgramIdentifier) arraySet2.valueAt(i6));
        }
        this.mComplete = programInfoCache.mComplete;
        return buildChunks(z2, this.mComplete, arraySet, i, arraySet2, i2);
    }

    @android.annotation.Nullable
    java.util.List<android.hardware.radio.ProgramList.Chunk> filterAndApplyChunk(android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk) {
        return filterAndApplyChunkInternal(programListChunk, 100, 500);
    }

    @com.android.internal.annotations.VisibleForTesting
    @android.annotation.Nullable
    java.util.List<android.hardware.radio.ProgramList.Chunk> filterAndApplyChunkInternal(android.hardware.broadcastradio.V2_0.ProgramListChunk programListChunk, int i, int i2) {
        if (programListChunk.purge) {
            this.mProgramInfoMap.clear();
        }
        android.util.ArraySet arraySet = new android.util.ArraySet();
        java.util.Iterator<android.hardware.broadcastradio.V2_0.ProgramInfo> it = programListChunk.modified.iterator();
        while (it.hasNext()) {
            android.hardware.radio.RadioManager.ProgramInfo programInfoFromHal = com.android.server.broadcastradio.hal2.Convert.programInfoFromHal(it.next());
            if (passesFilter(programInfoFromHal.getSelector().getPrimaryId()) && shouldIncludeInModified(programInfoFromHal)) {
                putInfo(programInfoFromHal);
                arraySet.add(programInfoFromHal);
            }
        }
        android.util.ArraySet arraySet2 = new android.util.ArraySet();
        java.util.Iterator<android.hardware.broadcastradio.V2_0.ProgramIdentifier> it2 = programListChunk.removed.iterator();
        while (it2.hasNext()) {
            android.hardware.radio.ProgramSelector.Identifier programIdentifierFromHal = com.android.server.broadcastradio.hal2.Convert.programIdentifierFromHal(it2.next());
            if (programIdentifierFromHal != null && this.mProgramInfoMap.containsKey(programIdentifierFromHal)) {
                arraySet2.addAll(this.mProgramInfoMap.get(programIdentifierFromHal).keySet());
                this.mProgramInfoMap.remove(programIdentifierFromHal);
            }
        }
        if (arraySet.isEmpty() && arraySet2.isEmpty() && this.mComplete == programListChunk.complete && !programListChunk.purge) {
            return null;
        }
        this.mComplete = programListChunk.complete;
        return buildChunks(programListChunk.purge, this.mComplete, arraySet, i, arraySet2, i2);
    }

    private boolean passesFilter(android.hardware.radio.ProgramSelector.Identifier identifier) {
        if (this.mFilter == null) {
            return true;
        }
        if (!this.mFilter.getIdentifierTypes().isEmpty() && !this.mFilter.getIdentifierTypes().contains(java.lang.Integer.valueOf(identifier.getType()))) {
            return false;
        }
        if (this.mFilter.getIdentifiers().isEmpty() || this.mFilter.getIdentifiers().contains(identifier)) {
            return this.mFilter.areCategoriesIncluded() || !identifier.isCategoryType();
        }
        return false;
    }

    private void putInfo(android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        android.hardware.radio.ProgramSelector.Identifier primaryId = programInfo.getSelector().getPrimaryId();
        if (!this.mProgramInfoMap.containsKey(primaryId)) {
            this.mProgramInfoMap.put(primaryId, new android.util.ArrayMap<>());
        }
        this.mProgramInfoMap.get(primaryId).put(new android.hardware.radio.UniqueProgramIdentifier(programInfo.getSelector()), programInfo);
    }

    private void removeUniqueId(android.hardware.radio.UniqueProgramIdentifier uniqueProgramIdentifier) {
        android.hardware.radio.ProgramSelector.Identifier primaryId = uniqueProgramIdentifier.getPrimaryId();
        if (!this.mProgramInfoMap.containsKey(primaryId)) {
            return;
        }
        this.mProgramInfoMap.get(primaryId).remove(uniqueProgramIdentifier);
        if (this.mProgramInfoMap.get(primaryId).isEmpty()) {
            this.mProgramInfoMap.remove(primaryId);
        }
    }

    private boolean shouldIncludeInModified(android.hardware.radio.RadioManager.ProgramInfo programInfo) {
        android.hardware.radio.RadioManager.ProgramInfo programInfo2;
        android.hardware.radio.ProgramSelector.Identifier primaryId = programInfo.getSelector().getPrimaryId();
        if (!this.mProgramInfoMap.containsKey(primaryId)) {
            programInfo2 = null;
        } else {
            programInfo2 = this.mProgramInfoMap.get(primaryId).get(new android.hardware.radio.UniqueProgramIdentifier(programInfo.getSelector()));
        }
        if (programInfo2 == null) {
            return true;
        }
        if (this.mFilter == null || !this.mFilter.areModificationsExcluded()) {
            return !programInfo2.equals(programInfo);
        }
        return false;
    }

    private static int roundUpFraction(int i, int i2) {
        return (i / i2) + (i % i2 > 0 ? 1 : 0);
    }

    private static java.util.List<android.hardware.radio.ProgramList.Chunk> buildChunks(boolean z, boolean z2, @android.annotation.Nullable java.util.Collection<android.hardware.radio.RadioManager.ProgramInfo> collection, int i, @android.annotation.Nullable java.util.Collection<android.hardware.radio.UniqueProgramIdentifier> collection2, int i2) {
        int i3;
        int i4;
        java.util.Iterator<android.hardware.radio.RadioManager.ProgramInfo> it;
        java.util.Iterator<android.hardware.radio.UniqueProgramIdentifier> it2;
        int i5;
        if (z) {
            collection2 = null;
        }
        if (collection == null) {
            i3 = z ? 1 : 0;
        } else {
            i3 = java.lang.Math.max(z ? 1 : 0, roundUpFraction(collection.size(), i));
        }
        if (collection2 != null) {
            i3 = java.lang.Math.max(i3, roundUpFraction(collection2.size(), i2));
        }
        if (i3 == 0) {
            return new java.util.ArrayList();
        }
        if (collection == null) {
            i4 = 0;
            it = null;
        } else {
            i4 = roundUpFraction(collection.size(), i3);
            it = collection.iterator();
        }
        if (collection2 == null) {
            it2 = null;
            i5 = 0;
        } else {
            i5 = roundUpFraction(collection2.size(), i3);
            it2 = collection2.iterator();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList(i3);
        int i6 = 0;
        while (i6 < i3) {
            android.util.ArraySet arraySet = new android.util.ArraySet();
            android.util.ArraySet arraySet2 = new android.util.ArraySet();
            if (it != null) {
                for (int i7 = 0; i7 < i4 && it.hasNext(); i7++) {
                    arraySet.add(it.next());
                }
            }
            if (it2 != null) {
                for (int i8 = 0; i8 < i5 && it2.hasNext(); i8++) {
                    arraySet2.add(it2.next());
                }
            }
            boolean z3 = true;
            boolean z4 = z && i6 == 0;
            if (!z2 || i6 != i3 - 1) {
                z3 = false;
            }
            arrayList.add(new android.hardware.radio.ProgramList.Chunk(z4, z3, arraySet, arraySet2));
            i6++;
        }
        return arrayList;
    }
}
