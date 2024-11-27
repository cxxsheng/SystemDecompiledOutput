package com.android.server.job;

/* loaded from: classes2.dex */
class PendingJobQueue {
    private final android.util.Pools.Pool<com.android.server.job.PendingJobQueue.AppJobQueue> mAppJobQueuePool = new android.util.Pools.SimplePool(8);
    private final android.util.SparseArray<com.android.server.job.PendingJobQueue.AppJobQueue> mCurrentQueues = new android.util.SparseArray<>();
    private final java.util.PriorityQueue<com.android.server.job.PendingJobQueue.AppJobQueue> mOrderedQueues = new java.util.PriorityQueue<>(new java.util.Comparator() { // from class: com.android.server.job.PendingJobQueue$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(java.lang.Object obj, java.lang.Object obj2) {
            int lambda$new$0;
            lambda$new$0 = com.android.server.job.PendingJobQueue.lambda$new$0((com.android.server.job.PendingJobQueue.AppJobQueue) obj, (com.android.server.job.PendingJobQueue.AppJobQueue) obj2);
            return lambda$new$0;
        }
    });
    private int mSize = 0;
    private boolean mOptimizeIteration = true;
    private int mPullCount = 0;
    private boolean mNeedToResetIterators = false;

    PendingJobQueue() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$new$0(com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue, com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue2) {
        long peekNextTimestamp = appJobQueue.peekNextTimestamp();
        long peekNextTimestamp2 = appJobQueue2.peekNextTimestamp();
        if (peekNextTimestamp == -1) {
            if (peekNextTimestamp2 == -1) {
                return 0;
            }
            return 1;
        }
        if (peekNextTimestamp2 == -1) {
            return -1;
        }
        int peekNextOverrideState = appJobQueue.peekNextOverrideState();
        int peekNextOverrideState2 = appJobQueue2.peekNextOverrideState();
        if (peekNextOverrideState != peekNextOverrideState2) {
            return java.lang.Integer.compare(peekNextOverrideState2, peekNextOverrideState);
        }
        return java.lang.Long.compare(peekNextTimestamp, peekNextTimestamp2);
    }

    void add(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue = getAppJobQueue(jobStatus.getSourceUid(), true);
        long peekNextTimestamp = appJobQueue.peekNextTimestamp();
        appJobQueue.add(jobStatus);
        this.mSize++;
        if (peekNextTimestamp != appJobQueue.peekNextTimestamp()) {
            this.mOrderedQueues.remove(appJobQueue);
            this.mOrderedQueues.offer(appJobQueue);
        }
    }

    void addAll(@android.annotation.NonNull android.util.ArraySet<com.android.server.job.controllers.JobStatus> arraySet) {
        android.util.SparseArray sparseArray = new android.util.SparseArray();
        for (int size = arraySet.size() - 1; size >= 0; size--) {
            com.android.server.job.controllers.JobStatus valueAt = arraySet.valueAt(size);
            java.util.List list = (java.util.List) sparseArray.get(valueAt.getSourceUid());
            if (list == null) {
                list = new java.util.ArrayList();
                sparseArray.put(valueAt.getSourceUid(), list);
            }
            list.add(valueAt);
        }
        for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
            getAppJobQueue(sparseArray.keyAt(size2), true).addAll((java.util.List) sparseArray.valueAt(size2));
        }
        this.mSize += arraySet.size();
        this.mOrderedQueues.clear();
    }

    void clear() {
        this.mSize = 0;
        for (int size = this.mCurrentQueues.size() - 1; size >= 0; size--) {
            com.android.server.job.PendingJobQueue.AppJobQueue valueAt = this.mCurrentQueues.valueAt(size);
            valueAt.clear();
            this.mAppJobQueuePool.release(valueAt);
        }
        this.mCurrentQueues.clear();
        this.mOrderedQueues.clear();
    }

    boolean contains(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue = this.mCurrentQueues.get(jobStatus.getSourceUid());
        if (appJobQueue == null) {
            return false;
        }
        return appJobQueue.contains(jobStatus);
    }

    private com.android.server.job.PendingJobQueue.AppJobQueue getAppJobQueue(int i, boolean z) {
        com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue = this.mCurrentQueues.get(i);
        if (appJobQueue == null && z) {
            com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue2 = (com.android.server.job.PendingJobQueue.AppJobQueue) this.mAppJobQueuePool.acquire();
            if (appJobQueue2 != null) {
                appJobQueue = appJobQueue2;
            } else {
                appJobQueue = new com.android.server.job.PendingJobQueue.AppJobQueue();
            }
            this.mCurrentQueues.put(i, appJobQueue);
        }
        return appJobQueue;
    }

    @android.annotation.Nullable
    com.android.server.job.controllers.JobStatus next() {
        if (this.mNeedToResetIterators) {
            this.mOrderedQueues.clear();
            for (int size = this.mCurrentQueues.size() - 1; size >= 0; size--) {
                com.android.server.job.PendingJobQueue.AppJobQueue valueAt = this.mCurrentQueues.valueAt(size);
                valueAt.resetIterator(0L);
                this.mOrderedQueues.offer(valueAt);
            }
            this.mNeedToResetIterators = false;
            this.mPullCount = 0;
        } else if (this.mOrderedQueues.size() == 0) {
            for (int size2 = this.mCurrentQueues.size() - 1; size2 >= 0; size2--) {
                this.mOrderedQueues.offer(this.mCurrentQueues.valueAt(size2));
            }
            this.mPullCount = 0;
        }
        int size3 = this.mOrderedQueues.size();
        if (size3 == 0) {
            return null;
        }
        int min = this.mOptimizeIteration ? java.lang.Math.min(3, ((size3 - 1) >>> 2) + 1) : 1;
        com.android.server.job.PendingJobQueue.AppJobQueue peek = this.mOrderedQueues.peek();
        if (peek == null) {
            return null;
        }
        com.android.server.job.controllers.JobStatus next = peek.next();
        int i = this.mPullCount + 1;
        this.mPullCount = i;
        if (i >= min || ((next != null && peek.peekNextOverrideState() != next.overrideState) || peek.peekNextTimestamp() == -1)) {
            this.mOrderedQueues.poll();
            if (peek.peekNextTimestamp() != -1) {
                this.mOrderedQueues.offer(peek);
            }
            this.mPullCount = 0;
        }
        return next;
    }

    boolean remove(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
        com.android.server.job.PendingJobQueue.AppJobQueue appJobQueue = getAppJobQueue(jobStatus.getSourceUid(), false);
        if (appJobQueue == null) {
            return false;
        }
        long peekNextTimestamp = appJobQueue.peekNextTimestamp();
        if (!appJobQueue.remove(jobStatus)) {
            return false;
        }
        this.mSize--;
        if (appJobQueue.size() == 0) {
            this.mCurrentQueues.remove(jobStatus.getSourceUid());
            this.mOrderedQueues.remove(appJobQueue);
            appJobQueue.clear();
            this.mAppJobQueuePool.release(appJobQueue);
        } else if (peekNextTimestamp != appJobQueue.peekNextTimestamp()) {
            this.mOrderedQueues.remove(appJobQueue);
            this.mOrderedQueues.offer(appJobQueue);
        }
        return true;
    }

    void resetIterator() {
        this.mNeedToResetIterators = true;
    }

    @com.android.internal.annotations.VisibleForTesting
    void setOptimizeIteration(boolean z) {
        this.mOptimizeIteration = z;
    }

    int size() {
        return this.mSize;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class AppJobQueue {
        static final int NO_NEXT_OVERRIDE_STATE = -1;
        static final long NO_NEXT_TIMESTAMP = -1;
        private int mCurIndex;
        private final java.util.List<com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus> mJobs;
        private static final java.util.Comparator<com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus> sJobComparator = new java.util.Comparator() { // from class: com.android.server.job.PendingJobQueue$AppJobQueue$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                int lambda$static$0;
                lambda$static$0 = com.android.server.job.PendingJobQueue.AppJobQueue.lambda$static$0((com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) obj, (com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) obj2);
                return lambda$static$0;
            }
        };
        private static final android.util.Pools.Pool<com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus> mAdjustedJobStatusPool = new android.util.Pools.SimplePool(16);

        private AppJobQueue() {
            this.mJobs = new java.util.ArrayList();
            this.mCurIndex = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class AdjustedJobStatus {
            public long adjustedEnqueueTime;
            public com.android.server.job.controllers.JobStatus job;

            private AdjustedJobStatus() {
            }

            void clear() {
                this.adjustedEnqueueTime = 0L;
                this.job = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static /* synthetic */ int lambda$static$0(com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus, com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus2) {
            com.android.server.job.controllers.JobStatus jobStatus;
            com.android.server.job.controllers.JobStatus jobStatus2;
            int effectivePriority;
            int effectivePriority2;
            if (adjustedJobStatus == adjustedJobStatus2 || (jobStatus = adjustedJobStatus.job) == (jobStatus2 = adjustedJobStatus2.job)) {
                return 0;
            }
            if (jobStatus.overrideState != jobStatus2.overrideState) {
                return java.lang.Integer.compare(jobStatus2.overrideState, jobStatus.overrideState);
            }
            boolean isUserInitiated = jobStatus.getJob().isUserInitiated();
            if (isUserInitiated != jobStatus2.getJob().isUserInitiated()) {
                return isUserInitiated ? -1 : 1;
            }
            boolean isRequestedExpeditedJob = jobStatus.isRequestedExpeditedJob();
            if (isRequestedExpeditedJob != jobStatus2.isRequestedExpeditedJob()) {
                return isRequestedExpeditedJob ? -1 : 1;
            }
            if (java.util.Objects.equals(jobStatus.getNamespace(), jobStatus2.getNamespace()) && (effectivePriority = jobStatus.getEffectivePriority()) != (effectivePriority2 = jobStatus2.getEffectivePriority())) {
                return java.lang.Integer.compare(effectivePriority2, effectivePriority);
            }
            if (jobStatus.lastEvaluatedBias != jobStatus2.lastEvaluatedBias) {
                return java.lang.Integer.compare(jobStatus2.lastEvaluatedBias, jobStatus.lastEvaluatedBias);
            }
            return java.lang.Long.compare(jobStatus.enqueueTime, jobStatus2.enqueueTime);
        }

        void add(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus = (com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) mAdjustedJobStatusPool.acquire();
            if (adjustedJobStatus == null) {
                adjustedJobStatus = new com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus();
            }
            adjustedJobStatus.adjustedEnqueueTime = jobStatus.enqueueTime;
            adjustedJobStatus.job = jobStatus;
            int binarySearch = java.util.Collections.binarySearch(this.mJobs, adjustedJobStatus, sJobComparator);
            if (binarySearch < 0) {
                binarySearch = ~binarySearch;
            }
            this.mJobs.add(binarySearch, adjustedJobStatus);
            if (binarySearch < this.mCurIndex) {
                this.mCurIndex = binarySearch;
            }
            if (binarySearch > 0) {
                adjustedJobStatus.adjustedEnqueueTime = java.lang.Math.max(this.mJobs.get(binarySearch - 1).adjustedEnqueueTime, adjustedJobStatus.adjustedEnqueueTime);
            }
            int size = this.mJobs.size();
            if (binarySearch < size - 1) {
                while (binarySearch < size) {
                    com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus2 = this.mJobs.get(binarySearch);
                    if (adjustedJobStatus.adjustedEnqueueTime >= adjustedJobStatus2.adjustedEnqueueTime) {
                        adjustedJobStatus2.adjustedEnqueueTime = adjustedJobStatus.adjustedEnqueueTime;
                        binarySearch++;
                    } else {
                        return;
                    }
                }
            }
        }

        void addAll(@android.annotation.NonNull java.util.List<com.android.server.job.controllers.JobStatus> list) {
            int i = Integer.MAX_VALUE;
            for (int size = list.size() - 1; size >= 0; size--) {
                com.android.server.job.controllers.JobStatus jobStatus = list.get(size);
                com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus = (com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus) mAdjustedJobStatusPool.acquire();
                if (adjustedJobStatus == null) {
                    adjustedJobStatus = new com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus();
                }
                adjustedJobStatus.adjustedEnqueueTime = jobStatus.enqueueTime;
                adjustedJobStatus.job = jobStatus;
                int binarySearch = java.util.Collections.binarySearch(this.mJobs, adjustedJobStatus, sJobComparator);
                if (binarySearch < 0) {
                    binarySearch = ~binarySearch;
                }
                this.mJobs.add(binarySearch, adjustedJobStatus);
                if (binarySearch < this.mCurIndex) {
                    this.mCurIndex = binarySearch;
                }
                i = java.lang.Math.min(i, binarySearch);
            }
            int size2 = this.mJobs.size();
            for (int max = java.lang.Math.max(i, 1); max < size2; max++) {
                com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus2 = this.mJobs.get(max);
                adjustedJobStatus2.adjustedEnqueueTime = java.lang.Math.max(adjustedJobStatus2.adjustedEnqueueTime, this.mJobs.get(max - 1).adjustedEnqueueTime);
            }
        }

        void clear() {
            this.mJobs.clear();
            this.mCurIndex = 0;
        }

        boolean contains(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            return indexOf(jobStatus) >= 0;
        }

        private int indexOf(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            int size = this.mJobs.size();
            for (int i = 0; i < size; i++) {
                if (this.mJobs.get(i).job == jobStatus) {
                    return i;
                }
            }
            return -1;
        }

        @android.annotation.Nullable
        com.android.server.job.controllers.JobStatus next() {
            if (this.mCurIndex >= this.mJobs.size()) {
                return null;
            }
            java.util.List<com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus> list = this.mJobs;
            int i = this.mCurIndex;
            this.mCurIndex = i + 1;
            return list.get(i).job;
        }

        int peekNextOverrideState() {
            if (this.mCurIndex >= this.mJobs.size()) {
                return -1;
            }
            return this.mJobs.get(this.mCurIndex).job.overrideState;
        }

        long peekNextTimestamp() {
            if (this.mCurIndex >= this.mJobs.size()) {
                return -1L;
            }
            return this.mJobs.get(this.mCurIndex).adjustedEnqueueTime;
        }

        boolean remove(@android.annotation.NonNull com.android.server.job.controllers.JobStatus jobStatus) {
            int indexOf = indexOf(jobStatus);
            if (indexOf < 0) {
                return false;
            }
            com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus remove = this.mJobs.remove(indexOf);
            remove.clear();
            mAdjustedJobStatusPool.release(remove);
            if (indexOf < this.mCurIndex) {
                this.mCurIndex--;
            }
            return true;
        }

        void resetIterator(long j) {
            int i = 0;
            if (j == 0 || this.mJobs.size() == 0) {
                this.mCurIndex = 0;
                return;
            }
            int size = this.mJobs.size() - 1;
            while (i < size) {
                int i2 = (i + size) >>> 1;
                com.android.server.job.PendingJobQueue.AppJobQueue.AdjustedJobStatus adjustedJobStatus = this.mJobs.get(i2);
                if (adjustedJobStatus.adjustedEnqueueTime < j) {
                    i = i2 + 1;
                } else if (adjustedJobStatus.adjustedEnqueueTime > j) {
                    size = i2 - 1;
                } else {
                    size = i2;
                }
            }
            this.mCurIndex = size;
        }

        int size() {
            return this.mJobs.size();
        }
    }
}
