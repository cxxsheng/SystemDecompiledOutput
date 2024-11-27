package com.android.server.am;

/* loaded from: classes.dex */
public class OomAdjusterModernImpl extends com.android.server.am.OomAdjuster {
    static final int ADJ_SLOT_BACKUP_APP = 10;
    static final int ADJ_SLOT_CACHED_APP = 16;
    static final int ADJ_SLOT_FOREGROUND_APP = 4;
    static final int ADJ_SLOT_HEAVY_WEIGHT_APP = 11;
    static final int ADJ_SLOT_HOME_APP = 13;
    static final int ADJ_SLOT_INVALID = -1;
    static final int ADJ_SLOT_NATIVE = 0;
    static final int ADJ_SLOT_PERCEPTIBLE_APP = 7;
    static final int ADJ_SLOT_PERCEPTIBLE_LOW_APP = 9;
    static final int ADJ_SLOT_PERCEPTIBLE_MEDIUM_APP = 8;
    static final int ADJ_SLOT_PERCEPTIBLE_RECENT_FOREGROUND_APP = 5;
    static final int ADJ_SLOT_PERSISTENT_PROC = 2;
    static final int ADJ_SLOT_PERSISTENT_SERVICE = 3;
    static final int ADJ_SLOT_PREVIOUS_APP = 14;
    static final int ADJ_SLOT_SERVICE = 12;
    static final int ADJ_SLOT_SERVICE_B = 15;
    static final int ADJ_SLOT_SYSTEM = 1;
    static final int ADJ_SLOT_UNKNOWN = 17;
    static final int ADJ_SLOT_VISIBLE_APP = 6;
    static final java.lang.String TAG = "OomAdjusterModernImpl";
    private final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes mProcessRecordAdjNodes;
    private final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes mProcessRecordProcStateNodes;
    private final com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs mTmpOomAdjusterArgs;
    private final com.android.server.am.OomAdjusterModernImpl.UnreachedProcessCollector mUnreachedProcessCollector;
    static final int[] ADJ_SLOT_VALUES = {-1000, com.android.server.am.ProcessList.SYSTEM_ADJ, com.android.server.am.ProcessList.PERSISTENT_PROC_ADJ, com.android.server.am.ProcessList.PERSISTENT_SERVICE_ADJ, 0, 50, 100, 200, com.android.server.am.ProcessList.PERCEPTIBLE_MEDIUM_APP_ADJ, 250, 300, 400, 500, 600, com.android.server.am.ProcessList.PREVIOUS_APP_ADJ, com.android.server.am.ProcessList.SERVICE_B_ADJ, com.android.server.am.ProcessList.CACHED_APP_MIN_ADJ, 1001};
    static final int[] PROC_STATE_SLOTS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, -1};

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface AdjSlot {
    }

    static int adjToSlot(int i) {
        if (i >= ADJ_SLOT_VALUES[0] && i <= ADJ_SLOT_VALUES[ADJ_SLOT_VALUES.length - 1]) {
            int binarySearch = java.util.Arrays.binarySearch(ADJ_SLOT_VALUES, i);
            if (binarySearch >= 0) {
                return binarySearch;
            }
            return (-(binarySearch + 1)) - 1;
        }
        return ADJ_SLOT_VALUES.length - 1;
    }

    static int processStateToSlot(int i) {
        if (i >= 0 && i <= 19) {
            return i;
        }
        return PROC_STATE_SLOTS.length - 1;
    }

    static class ProcessRecordNode {
        static final int NODE_TYPE_ADJ = 1;
        static final int NODE_TYPE_PROC_STATE = 0;
        static final int NUM_NODE_TYPE = 2;

        @android.annotation.Nullable
        final com.android.server.am.ProcessRecord mApp;

        @android.annotation.Nullable
        com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode mNext;

        @android.annotation.Nullable
        com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode mPrev;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        @interface NodeType {
        }

        ProcessRecordNode(@android.annotation.Nullable com.android.server.am.ProcessRecord processRecord) {
            this.mApp = processRecord;
        }

        void unlink() {
            if (this.mPrev != null) {
                this.mPrev.mNext = this.mNext;
            }
            if (this.mNext != null) {
                this.mNext.mPrev = this.mPrev;
            }
            this.mNext = null;
            this.mPrev = null;
        }

        boolean isLinked() {
            return (this.mPrev == null || this.mNext == null) ? false : true;
        }

        public java.lang.String toString() {
            java.lang.StringBuilder sb = new java.lang.StringBuilder();
            sb.append("ProcessRecordNode{");
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
            sb.append(' ');
            sb.append(this.mApp);
            sb.append(' ');
            sb.append(this.mApp != null ? this.mApp.mState.getCurProcState() : -1);
            sb.append(' ');
            sb.append(this.mApp != null ? this.mApp.mState.getCurAdj() : 1001);
            sb.append(' ');
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mPrev)));
            sb.append(' ');
            sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this.mNext)));
            sb.append('}');
            return sb.toString();
        }
    }

    private class ProcessRecordNodes {
        private final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode[] mLastNode;
        private final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList[] mProcessRecordNodes;
        private final int mType;

        ProcessRecordNodes(int i, int i2) {
            this.mType = i;
            this.mProcessRecordNodes = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                this.mProcessRecordNodes[i3] = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList(i);
            }
            this.mLastNode = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode[i2];
        }

        int size() {
            return this.mProcessRecordNodes.length;
        }

        @com.android.internal.annotations.VisibleForTesting
        void reset() {
            for (int i = 0; i < this.mProcessRecordNodes.length; i++) {
                this.mProcessRecordNodes[i].reset();
                this.mLastNode[i] = null;
            }
        }

        void resetLastNodes() {
            for (int i = 0; i < this.mProcessRecordNodes.length; i++) {
                this.mLastNode[i] = this.mProcessRecordNodes[i].getLastNodeBeforeTail();
            }
        }

        void setLastNodeToHead(int i) {
            this.mLastNode[i] = this.mProcessRecordNodes[i].HEAD;
        }

        void forEachNewNode(int i, @android.annotation.NonNull java.util.function.Consumer<com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs> consumer) {
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = this.mLastNode[i].mNext;
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode2 = this.mProcessRecordNodes[i].TAIL;
            while (processRecordNode != processRecordNode2) {
                com.android.server.am.OomAdjusterModernImpl.this.mTmpOomAdjusterArgs.mApp = processRecordNode.mApp;
                com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode3 = processRecordNode.mNext;
                consumer.accept(com.android.server.am.OomAdjusterModernImpl.this.mTmpOomAdjusterArgs);
                processRecordNode = (processRecordNode3 != processRecordNode2 || processRecordNode.mNext == null || processRecordNode.mNext.mNext == null) ? processRecordNode3 : processRecordNode.mNext;
            }
        }

        int getNumberOfSlots() {
            return this.mProcessRecordNodes.length;
        }

        void moveAppTo(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i, int i2) {
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = processRecord.mLinkedNodes[this.mType];
            if (i != -1 && this.mLastNode[i] == processRecordNode) {
                this.mLastNode[i] = processRecordNode.mPrev;
            }
            append(processRecordNode, i2);
        }

        void moveAllNodesTo(int i, int i2) {
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList linkedProcessRecordList = this.mProcessRecordNodes[i];
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList linkedProcessRecordList2 = this.mProcessRecordNodes[i2];
            if (i != i2 && linkedProcessRecordList.HEAD.mNext != linkedProcessRecordList.TAIL) {
                linkedProcessRecordList.moveTo(linkedProcessRecordList2);
                this.mLastNode[i] = linkedProcessRecordList.getLastNodeBeforeTail();
            }
        }

        void moveAppToTail(com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = processRecord.mLinkedNodes[this.mType];
            switch (this.mType) {
                case 0:
                    int processStateToSlot = com.android.server.am.OomAdjusterModernImpl.processStateToSlot(processRecord.mState.getCurProcState());
                    if (this.mLastNode[processStateToSlot] == processRecordNode) {
                        this.mLastNode[processStateToSlot] = processRecordNode.mPrev;
                    }
                    this.mProcessRecordNodes[processStateToSlot].moveNodeToTail(processRecordNode);
                    break;
                case 1:
                    int adjToSlot = com.android.server.am.OomAdjusterModernImpl.adjToSlot(processRecord.mState.getCurRawAdj());
                    if (this.mLastNode[adjToSlot] == processRecordNode) {
                        this.mLastNode[adjToSlot] = processRecordNode.mPrev;
                    }
                    this.mProcessRecordNodes[adjToSlot].moveNodeToTail(processRecordNode);
                    break;
            }
        }

        void reset(int i) {
            this.mProcessRecordNodes[i].reset();
        }

        void unlink(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = processRecord.mLinkedNodes[this.mType];
            int currentSlot = getCurrentSlot(processRecord);
            if (currentSlot != -1 && this.mLastNode[currentSlot] == processRecordNode) {
                this.mLastNode[currentSlot] = processRecordNode.mPrev;
            }
            processRecordNode.unlink();
        }

        void append(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
            append(processRecord, getCurrentSlot(processRecord));
        }

        void append(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i) {
            append(processRecord.mLinkedNodes[this.mType], i);
        }

        void append(@android.annotation.NonNull com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode, int i) {
            processRecordNode.unlink();
            this.mProcessRecordNodes[i].append(processRecordNode);
        }

        private int getCurrentSlot(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
            switch (this.mType) {
                case 0:
                    return com.android.server.am.OomAdjusterModernImpl.processStateToSlot(processRecord.mState.getCurProcState());
                case 1:
                    return com.android.server.am.OomAdjusterModernImpl.adjToSlot(processRecord.mState.getCurRawAdj());
                default:
                    return -1;
            }
        }

        java.lang.String toString(int i, int i2) {
            return "lastNode=" + this.mLastNode[i] + " " + this.mProcessRecordNodes[i].toString(i2);
        }

        private static class LinkedProcessRecordList {
            final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode HEAD = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode(null);
            final com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode TAIL = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode(null);
            final int mNodeType;

            LinkedProcessRecordList(int i) {
                this.HEAD.mNext = this.TAIL;
                this.TAIL.mPrev = this.HEAD;
                this.mNodeType = i;
            }

            void append(@android.annotation.NonNull com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode) {
                processRecordNode.mNext = this.TAIL;
                processRecordNode.mPrev = this.TAIL.mPrev;
                this.TAIL.mPrev.mNext = processRecordNode;
                this.TAIL.mPrev = processRecordNode;
            }

            void moveTo(@android.annotation.NonNull com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes.LinkedProcessRecordList linkedProcessRecordList) {
                if (this.HEAD.mNext != this.TAIL) {
                    linkedProcessRecordList.TAIL.mPrev.mNext = this.HEAD.mNext;
                    this.HEAD.mNext.mPrev = linkedProcessRecordList.TAIL.mPrev;
                    linkedProcessRecordList.TAIL.mPrev = this.TAIL.mPrev;
                    this.TAIL.mPrev.mNext = linkedProcessRecordList.TAIL;
                    this.HEAD.mNext = this.TAIL;
                    this.TAIL.mPrev = this.HEAD;
                }
            }

            void moveNodeToTail(@android.annotation.NonNull com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode) {
                processRecordNode.unlink();
                append(processRecordNode);
            }

            @android.annotation.NonNull
            com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode getLastNodeBeforeTail() {
                return this.TAIL.mPrev;
            }

            @com.android.internal.annotations.VisibleForTesting
            void reset() {
                if (this.HEAD.mNext != this.TAIL) {
                    com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = this.HEAD.mNext;
                    this.TAIL.mPrev.mNext = null;
                    processRecordNode.mPrev = null;
                }
                this.HEAD.mNext = this.TAIL;
                this.TAIL.mPrev = this.HEAD;
            }

            java.lang.String toString(int i) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                sb.append("LinkedProcessRecordList{");
                sb.append(this.HEAD);
                sb.append(' ');
                sb.append(this.TAIL);
                sb.append('[');
                for (com.android.server.am.OomAdjusterModernImpl.ProcessRecordNode processRecordNode = this.HEAD.mNext; processRecordNode != this.TAIL; processRecordNode = processRecordNode.mNext) {
                    if (processRecordNode.mApp != null && processRecordNode.mApp.uid == i) {
                        sb.append(processRecordNode);
                        sb.append(',');
                    }
                }
                sb.append(']');
                sb.append('}');
                return sb.toString();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class OomAdjusterArgs {
        com.android.server.am.ProcessRecord mApp;
        int mCachedAdj;
        boolean mFullUpdate;
        long mNow;
        int mOomAdjReason;
        com.android.server.am.ProcessRecord mTopApp;

        @android.annotation.NonNull
        com.android.server.am.ActiveUids mUids;

        private OomAdjusterArgs() {
        }

        void update(com.android.server.am.ProcessRecord processRecord, long j, int i, int i2, @android.annotation.NonNull com.android.server.am.ActiveUids activeUids, boolean z) {
            this.mTopApp = processRecord;
            this.mNow = j;
            this.mCachedAdj = i;
            this.mOomAdjReason = i2;
            this.mUids = activeUids;
            this.mFullUpdate = z;
        }
    }

    private static class UnreachedProcessCollector implements java.util.function.Consumer<com.android.server.am.ProcessRecord> {
        public java.util.ArrayList<com.android.server.am.ProcessRecord> processList;

        private UnreachedProcessCollector() {
            this.processList = null;
        }

        @Override // java.util.function.Consumer
        public void accept(com.android.server.am.ProcessRecord processRecord) {
            if (processRecord.mState.isReachable()) {
                return;
            }
            processRecord.mState.setReachable(true);
            this.processList.add(processRecord);
        }
    }

    OomAdjusterModernImpl(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.ProcessList processList, com.android.server.am.ActiveUids activeUids) {
        this(activityManagerService, processList, activeUids, com.android.server.am.OomAdjuster.createAdjusterThread());
    }

    OomAdjusterModernImpl(com.android.server.am.ActivityManagerService activityManagerService, com.android.server.am.ProcessList processList, com.android.server.am.ActiveUids activeUids, com.android.server.ServiceThread serviceThread) {
        super(activityManagerService, processList, activeUids, serviceThread);
        this.mUnreachedProcessCollector = new com.android.server.am.OomAdjusterModernImpl.UnreachedProcessCollector();
        this.mProcessRecordProcStateNodes = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes(0, PROC_STATE_SLOTS.length);
        this.mProcessRecordAdjNodes = new com.android.server.am.OomAdjusterModernImpl.ProcessRecordNodes(1, ADJ_SLOT_VALUES.length);
        this.mTmpOomAdjusterArgs = new com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs();
    }

    void linkProcessRecordToList(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        this.mProcessRecordProcStateNodes.append(processRecord);
        this.mProcessRecordAdjNodes.append(processRecord);
    }

    void unlinkProcessRecordFromList(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        this.mProcessRecordProcStateNodes.unlink(processRecord);
        this.mProcessRecordAdjNodes.unlink(processRecord);
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.VisibleForTesting
    void resetInternal() {
        this.mProcessRecordProcStateNodes.reset();
        this.mProcessRecordAdjNodes.reset();
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessEndLocked(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        if (processRecord.mLinkedNodes[0] != null && processRecord.mLinkedNodes[0].isLinked()) {
            unlinkProcessRecordFromList(processRecord);
        }
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessStateChanged(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i) {
        updateProcStateSlotIfNecessary(processRecord, i);
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    void onProcessOomAdjChanged(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord, int i) {
        updateAdjSlotIfNecessary(processRecord, i);
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialAdj(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return 1001;
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialProcState(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return -1;
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    protected int getInitialCapability(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return 0;
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService"})
    protected boolean getInitialIsCurBoundByNonBgRestrictedApp(@android.annotation.NonNull com.android.server.am.ProcessRecord processRecord) {
        return false;
    }

    private void updateAdjSlotIfNecessary(com.android.server.am.ProcessRecord processRecord, int i) {
        int adjToSlot;
        int adjToSlot2;
        if (processRecord.mState.getCurRawAdj() != i && (adjToSlot = adjToSlot(processRecord.mState.getCurRawAdj())) != (adjToSlot2 = adjToSlot(i)) && adjToSlot != -1) {
            this.mProcessRecordAdjNodes.moveAppTo(processRecord, adjToSlot2, adjToSlot);
        }
    }

    private void updateProcStateSlotIfNecessary(com.android.server.am.ProcessRecord processRecord, int i) {
        int processStateToSlot;
        int processStateToSlot2;
        if (processRecord.mState.getCurProcState() != i && (processStateToSlot = processStateToSlot(processRecord.mState.getCurProcState())) != (processStateToSlot2 = processStateToSlot(i))) {
            this.mProcessRecordProcStateNodes.moveAppTo(processRecord, processStateToSlot2, processStateToSlot);
        }
    }

    @Override // com.android.server.am.OomAdjuster
    protected boolean performUpdateOomAdjLSP(com.android.server.am.ProcessRecord processRecord, int i) {
        com.android.server.am.ProcessRecord topApp = this.mService.getTopApp();
        android.os.Trace.traceBegin(64L, com.android.server.am.OomAdjuster.oomAdjReasonToString(i));
        this.mService.mOomAdjProfiler.oomAdjStarted();
        this.mAdjSeq++;
        int curRawAdj = processRecord.mState.getCurRawAdj();
        if (curRawAdj < 900) {
            curRawAdj = 1001;
        }
        int i2 = curRawAdj;
        com.android.server.am.ActiveUids activeUids = this.mTmpUidRecords;
        android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet = this.mTmpProcessSet;
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mTmpProcessList;
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        activeUids.clear();
        arraySet.clear();
        arraySet.add(processRecord);
        arrayList.clear();
        collectReachableProcessesLocked(arraySet, arrayList, activeUids);
        arraySet.addAll(arrayList);
        arrayList.clear();
        boolean performNewUpdateOomAdjLSP = performNewUpdateOomAdjLSP(i, topApp, arraySet, activeUids, false, uptimeMillis, i2);
        arrayList.addAll(arraySet);
        assignCachedAdjIfNecessary(arrayList);
        for (int size = activeUids.size() - 1; size >= 0; size--) {
            activeUids.valueAt(size).forEachProcess(new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjusterModernImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.OomAdjusterModernImpl.this.updateAppUidRecIfNecessaryLSP((com.android.server.am.ProcessRecord) obj);
                }
            });
        }
        updateUidsLSP(activeUids, elapsedRealtime);
        int size2 = arraySet.size();
        for (int i3 = 0; i3 < size2; i3++) {
            applyOomAdjLSP(arraySet.valueAt(i3), false, uptimeMillis, elapsedRealtime, i);
        }
        arraySet.clear();
        arrayList.clear();
        this.mService.mOomAdjProfiler.oomAdjEnded();
        android.os.Trace.traceEnd(64L);
        return performNewUpdateOomAdjLSP;
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void updateOomAdjInnerLSP(int i, com.android.server.am.ProcessRecord processRecord, java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList, com.android.server.am.ActiveUids activeUids, boolean z, boolean z2) {
        com.android.server.am.ActiveUids activeUids2;
        boolean z3 = arrayList == null;
        java.util.ArrayList<com.android.server.am.ProcessRecord> lruProcessesLOSP = z3 ? this.mProcessList.getLruProcessesLOSP() : arrayList;
        if (activeUids != null) {
            activeUids2 = activeUids;
        } else {
            int size = this.mActiveUids.size();
            com.android.server.am.ActiveUids activeUids3 = this.mTmpUidRecords;
            activeUids3.clear();
            for (int i2 = 0; i2 < size; i2++) {
                com.android.server.am.UidRecord valueAt = this.mActiveUids.valueAt(i2);
                activeUids3.put(valueAt.getUid(), valueAt);
            }
            activeUids2 = activeUids3;
        }
        if (z2) {
            android.os.Trace.traceBegin(64L, com.android.server.am.OomAdjuster.oomAdjReasonToString(i));
            this.mService.mOomAdjProfiler.oomAdjStarted();
        }
        long uptimeMillis = android.os.SystemClock.uptimeMillis();
        long elapsedRealtime = android.os.SystemClock.elapsedRealtime();
        long j = uptimeMillis - this.mConstants.mMaxEmptyTimeMillis;
        lruProcessesLOSP.size();
        this.mAdjSeq++;
        if (z3) {
            this.mNewNumServiceProcs = 0;
            this.mNewNumAServiceProcs = 0;
        }
        android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet = this.mTmpProcessSet;
        arraySet.clear();
        if (!z3) {
            arraySet.addAll(lruProcessesLOSP);
        }
        performNewUpdateOomAdjLSP(i, processRecord, arraySet, activeUids2, z3, uptimeMillis, 1001);
        assignCachedAdjIfNecessary(this.mProcessList.getLruProcessesLOSP());
        postUpdateOomAdjInnerLSP(i, activeUids2, uptimeMillis, elapsedRealtime, j);
        arraySet.clear();
        if (z2) {
            this.mService.mOomAdjProfiler.oomAdjEnded();
            android.os.Trace.traceEnd(64L);
        }
    }

    private boolean performNewUpdateOomAdjLSP(int i, com.android.server.am.ProcessRecord processRecord, android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet, com.android.server.am.ActiveUids activeUids, boolean z, long j, int i2) {
        java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList = this.mTmpProcessList2;
        arrayList.clear();
        int i3 = 0;
        if (!z) {
            collectExcludedClientProcessesLocked(arraySet, arrayList);
            int size = arraySet.size();
            while (i3 < size) {
                com.android.server.am.ProcessRecord valueAt = arraySet.valueAt(i3);
                valueAt.mState.resetCachedInfo();
                com.android.server.am.UidRecord uidRecord = valueAt.getUidRecord();
                if (uidRecord != null) {
                    uidRecord.reset();
                }
                i3++;
            }
        } else {
            java.util.ArrayList<com.android.server.am.ProcessRecord> lruProcessesLOSP = this.mProcessList.getLruProcessesLOSP();
            int size2 = lruProcessesLOSP.size();
            while (i3 < size2) {
                com.android.server.am.ProcessRecord processRecord2 = lruProcessesLOSP.get(i3);
                processRecord2.mState.resetCachedInfo();
                com.android.server.am.UidRecord uidRecord2 = processRecord2.getUidRecord();
                if (uidRecord2 != null) {
                    uidRecord2.reset();
                }
                i3++;
            }
        }
        updateNewOomAdjInnerLSP(i, processRecord, arraySet, arrayList, activeUids, i2, j, z);
        arrayList.clear();
        return true;
    }

    @com.android.internal.annotations.GuardedBy({"mService"})
    private void collectExcludedClientProcessesLocked(android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet, java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList) {
        int size = arraySet.size();
        for (int i = 0; i < size; i++) {
            arraySet.valueAt(i).mState.setReachable(true);
        }
        arrayList.clear();
        this.mUnreachedProcessCollector.processList = arrayList;
        for (int i2 = 0; i2 < size; i2++) {
            arraySet.valueAt(i2).forEachClient(this.mUnreachedProcessCollector);
        }
        this.mUnreachedProcessCollector.processList = null;
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            arrayList.get(size2).mState.setReachable(false);
        }
        int size3 = arraySet.size();
        for (int i3 = 0; i3 < size3; i3++) {
            arraySet.valueAt(i3).mState.setReachable(false);
        }
    }

    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    private void updateNewOomAdjInnerLSP(int i, com.android.server.am.ProcessRecord processRecord, android.util.ArraySet<com.android.server.am.ProcessRecord> arraySet, java.util.ArrayList<com.android.server.am.ProcessRecord> arrayList, com.android.server.am.ActiveUids activeUids, int i2, long j, boolean z) {
        this.mTmpOomAdjusterArgs.update(processRecord, j, i2, i, activeUids, z);
        this.mProcessRecordProcStateNodes.resetLastNodes();
        this.mProcessRecordAdjNodes.resetLastNodes();
        int size = this.mProcessRecordProcStateNodes.size() - 1;
        int size2 = this.mProcessRecordAdjNodes.size() - 1;
        this.mAdjSeq++;
        if (z) {
            java.util.ArrayList<com.android.server.am.ProcessRecord> lruProcessesLOSP = this.mProcessList.getLruProcessesLOSP();
            for (int i3 = size; i3 >= 0; i3--) {
                this.mProcessRecordProcStateNodes.reset(i3);
                this.mProcessRecordProcStateNodes.setLastNodeToHead(i3);
            }
            for (int size3 = lruProcessesLOSP.size() - 1; size3 >= 0; size3--) {
                this.mProcessRecordProcStateNodes.append(lruProcessesLOSP.get(size3), size);
            }
            for (int i4 = size2; i4 >= 0; i4--) {
                this.mProcessRecordAdjNodes.reset(i4);
                this.mProcessRecordAdjNodes.setLastNodeToHead(i4);
            }
            for (int size4 = lruProcessesLOSP.size() - 1; size4 >= 0; size4--) {
                this.mProcessRecordAdjNodes.append(lruProcessesLOSP.get(size4), size2);
            }
        } else {
            int size5 = arraySet.size();
            for (int i5 = 0; i5 < size5; i5++) {
                com.android.server.am.ProcessRecord valueAt = arraySet.valueAt(i5);
                int processStateToSlot = processStateToSlot(valueAt.mState.getCurProcState());
                int adjToSlot = adjToSlot(valueAt.mState.getCurRawAdj());
                this.mProcessRecordProcStateNodes.moveAppTo(valueAt, processStateToSlot, size);
                this.mProcessRecordAdjNodes.moveAppTo(valueAt, adjToSlot, size2);
            }
            this.mProcessRecordProcStateNodes.setLastNodeToHead(size);
            this.mProcessRecordAdjNodes.setLastNodeToHead(size2);
        }
        this.mProcessRecordProcStateNodes.forEachNewNode(this.mProcessRecordProcStateNodes.size() - 1, new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjusterModernImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(java.lang.Object obj) {
                com.android.server.am.OomAdjusterModernImpl.this.computeInitialOomAdjLSP((com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs) obj);
            }
        });
        if (!z) {
            int size6 = arrayList.size();
            for (int i6 = 0; i6 < size6; i6++) {
                this.mProcessRecordProcStateNodes.moveAppToTail(arrayList.get(i6));
            }
        }
        this.mProcessRecordAdjNodes.resetLastNodes();
        int size7 = this.mProcessRecordProcStateNodes.size() - 1;
        for (int i7 = 0; i7 < size7; i7++) {
            this.mProcessRecordProcStateNodes.forEachNewNode(i7, new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjusterModernImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.OomAdjusterModernImpl.this.computeHostOomAdjLSP((com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs) obj);
                }
            });
        }
        int size8 = this.mProcessRecordAdjNodes.size() - 1;
        for (int i8 = 0; i8 < size8; i8++) {
            this.mProcessRecordAdjNodes.forEachNewNode(i8, new java.util.function.Consumer() { // from class: com.android.server.am.OomAdjusterModernImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(java.lang.Object obj) {
                    com.android.server.am.OomAdjusterModernImpl.this.computeHostOomAdjLSP((com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    public void computeInitialOomAdjLSP(com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs oomAdjusterArgs) {
        com.android.server.am.UidRecord uidRecord;
        com.android.server.am.ProcessRecord processRecord = oomAdjusterArgs.mApp;
        int i = oomAdjusterArgs.mCachedAdj;
        com.android.server.am.ProcessRecord processRecord2 = oomAdjusterArgs.mTopApp;
        long j = oomAdjusterArgs.mNow;
        int i2 = oomAdjusterArgs.mOomAdjReason;
        com.android.server.am.ActiveUids activeUids = oomAdjusterArgs.mUids;
        boolean z = oomAdjusterArgs.mFullUpdate;
        if (activeUids != null && (uidRecord = processRecord.getUidRecord()) != null) {
            activeUids.put(uidRecord.getUid(), uidRecord);
        }
        computeOomAdjLSP(processRecord, i, processRecord2, z, j, false, false, i2, false);
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected int setIntermediateAdjLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2, int i3) {
        int intermediateAdjLSP = super.setIntermediateAdjLSP(processRecord, i, i2, i3);
        updateAdjSlotIfNecessary(processRecord, i2);
        return intermediateAdjLSP;
    }

    @Override // com.android.server.am.OomAdjuster
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    protected void setIntermediateProcStateLSP(com.android.server.am.ProcessRecord processRecord, int i, int i2) {
        super.setIntermediateProcStateLSP(processRecord, i, i2);
        updateProcStateSlotIfNecessary(processRecord, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @com.android.internal.annotations.GuardedBy({"mService", "mProcLock"})
    public void computeHostOomAdjLSP(com.android.server.am.OomAdjusterModernImpl.OomAdjusterArgs oomAdjusterArgs) {
        int i;
        int i2;
        int i3;
        com.android.server.am.ProcessProviderRecord processProviderRecord;
        char c;
        com.android.server.am.ProcessServiceRecord processServiceRecord;
        int i4;
        int i5;
        com.android.server.am.ProcessServiceRecord processServiceRecord2;
        boolean z;
        int i6;
        long j;
        com.android.server.am.ProcessRecord processRecord;
        com.android.server.am.ProcessRecord processRecord2 = oomAdjusterArgs.mApp;
        int i7 = oomAdjusterArgs.mCachedAdj;
        com.android.server.am.ProcessRecord processRecord3 = oomAdjusterArgs.mTopApp;
        long j2 = oomAdjusterArgs.mNow;
        int i8 = oomAdjusterArgs.mOomAdjReason;
        boolean z2 = oomAdjusterArgs.mFullUpdate;
        com.android.server.am.ProcessServiceRecord processServiceRecord3 = processRecord2.mServices;
        int numberOfConnections = processServiceRecord3.numberOfConnections() - 1;
        while (true) {
            i = com.android.server.am.ProcessList.SYSTEM_ADJ;
            i2 = 2;
            if (numberOfConnections < 0) {
                break;
            }
            com.android.server.am.ConnectionRecord connectionAt = processServiceRecord3.getConnectionAt(numberOfConnections);
            com.android.server.am.ProcessRecord processRecord4 = connectionAt.hasFlag(2) ? connectionAt.binding.service.isolationHostProc : connectionAt.binding.service.app;
            if (processRecord4 == null || processRecord4 == processRecord2) {
                i5 = numberOfConnections;
                processServiceRecord2 = processServiceRecord3;
                z = z2;
                i6 = i8;
                j = j2;
                processRecord = processRecord3;
            } else if (processRecord4.mState.getMaxAdj() >= -900 && processRecord4.mState.getMaxAdj() < 0) {
                i5 = numberOfConnections;
                processServiceRecord2 = processServiceRecord3;
                z = z2;
                i6 = i8;
                j = j2;
                processRecord = processRecord3;
            } else if (processRecord4.mState.getCurAdj() <= 0 && processRecord4.mState.getCurrentSchedulingGroup() > 0 && processRecord4.mState.getCurProcState() <= 2) {
                i5 = numberOfConnections;
                processServiceRecord2 = processServiceRecord3;
                z = z2;
                i6 = i8;
                j = j2;
                processRecord = processRecord3;
            } else if (processRecord4.isSdkSandbox && connectionAt.binding.attributedClient != null) {
                i5 = numberOfConnections;
                processServiceRecord2 = processServiceRecord3;
                z = z2;
                i6 = i8;
                j = j2;
                processRecord = processRecord3;
            } else {
                i5 = numberOfConnections;
                processServiceRecord2 = processServiceRecord3;
                z = z2;
                i6 = i8;
                j = j2;
                processRecord = processRecord3;
                computeServiceHostOomAdjLSP(connectionAt, processRecord4, processRecord2, j2, processRecord3, z2, false, false, i8, i7, false, false);
            }
            numberOfConnections = i5 - 1;
            processServiceRecord3 = processServiceRecord2;
            processRecord3 = processRecord;
            z2 = z;
            i8 = i6;
            j2 = j;
        }
        com.android.server.am.ProcessServiceRecord processServiceRecord4 = processServiceRecord3;
        boolean z3 = z2;
        int i9 = i8;
        long j3 = j2;
        com.android.server.am.ProcessRecord processRecord5 = processRecord3;
        int numberOfSdkSandboxConnections = processServiceRecord4.numberOfSdkSandboxConnections() - 1;
        while (numberOfSdkSandboxConnections >= 0) {
            com.android.server.am.ProcessServiceRecord processServiceRecord5 = processServiceRecord4;
            com.android.server.am.ConnectionRecord sdkSandboxConnectionAt = processServiceRecord5.getSdkSandboxConnectionAt(numberOfSdkSandboxConnections);
            com.android.server.am.ProcessRecord processRecord6 = sdkSandboxConnectionAt.binding.service.app;
            if (processRecord6 == null || processRecord6 == processRecord2) {
                processServiceRecord = processServiceRecord5;
                i4 = numberOfSdkSandboxConnections;
            } else if (processRecord6.mState.getMaxAdj() >= i && processRecord6.mState.getMaxAdj() < 0) {
                processServiceRecord = processServiceRecord5;
                i4 = numberOfSdkSandboxConnections;
            } else if (processRecord6.mState.getCurAdj() <= 0 && processRecord6.mState.getCurrentSchedulingGroup() > 0 && processRecord6.mState.getCurProcState() <= i2) {
                processServiceRecord = processServiceRecord5;
                i4 = numberOfSdkSandboxConnections;
            } else {
                processServiceRecord = processServiceRecord5;
                i4 = numberOfSdkSandboxConnections;
                computeServiceHostOomAdjLSP(sdkSandboxConnectionAt, processRecord6, processRecord2, j3, processRecord5, z3, false, false, i9, i7, false, false);
            }
            numberOfSdkSandboxConnections = i4 - 1;
            processServiceRecord4 = processServiceRecord;
            i2 = 2;
            i = com.android.server.am.ProcessList.SYSTEM_ADJ;
        }
        com.android.server.am.ProcessProviderRecord processProviderRecord2 = processRecord2.mProviders;
        int numberOfProviderConnections = processProviderRecord2.numberOfProviderConnections() - 1;
        while (numberOfProviderConnections >= 0) {
            com.android.server.am.ContentProviderConnection providerConnectionAt = processProviderRecord2.getProviderConnectionAt(numberOfProviderConnections);
            com.android.server.am.ProcessRecord processRecord7 = providerConnectionAt.provider.proc;
            if (processRecord7 == null || processRecord7 == processRecord2) {
                i3 = numberOfProviderConnections;
                processProviderRecord = processProviderRecord2;
            } else if (processRecord7.mState.getMaxAdj() < -900 || processRecord7.mState.getMaxAdj() >= 0) {
                if (processRecord7.mState.getCurAdj() > 0) {
                    c = 2;
                } else if (processRecord7.mState.getCurrentSchedulingGroup() <= 0) {
                    c = 2;
                } else {
                    c = 2;
                    if (processRecord7.mState.getCurProcState() <= 2) {
                        i3 = numberOfProviderConnections;
                        processProviderRecord = processProviderRecord2;
                    }
                }
                i3 = numberOfProviderConnections;
                processProviderRecord = processProviderRecord2;
                computeProviderHostOomAdjLSP(providerConnectionAt, processRecord7, processRecord2, j3, processRecord5, z3, false, false, i9, i7, false, false);
            } else {
                i3 = numberOfProviderConnections;
                processProviderRecord = processProviderRecord2;
            }
            numberOfProviderConnections = i3 - 1;
            processProviderRecord2 = processProviderRecord;
        }
    }
}
