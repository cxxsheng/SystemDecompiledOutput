package android.media;

/* loaded from: classes2.dex */
public abstract class SubtitleTrack implements android.media.MediaTimeProvider.OnMediaTimeListener {
    private static final java.lang.String TAG = "SubtitleTrack";
    private android.media.MediaFormat mFormat;
    private long mLastTimeMs;
    private long mLastUpdateTimeMs;
    private java.lang.Runnable mRunnable;
    protected android.media.MediaTimeProvider mTimeProvider;
    protected boolean mVisible;
    protected final android.util.LongSparseArray<android.media.SubtitleTrack.Run> mRunsByEndTime = new android.util.LongSparseArray<>();
    protected final android.util.LongSparseArray<android.media.SubtitleTrack.Run> mRunsByID = new android.util.LongSparseArray<>();
    protected final java.util.Vector<android.media.SubtitleTrack.Cue> mActiveCues = new java.util.Vector<>();
    public boolean DEBUG = false;
    protected android.os.Handler mHandler = new android.os.Handler();
    private long mNextScheduledTimeMs = -1;
    protected android.media.SubtitleTrack.CueList mCues = new android.media.SubtitleTrack.CueList();

    public interface RenderingWidget {

        public interface OnChangedListener {
            void onChanged(android.media.SubtitleTrack.RenderingWidget renderingWidget);
        }

        void draw(android.graphics.Canvas canvas);

        void onAttachedToWindow();

        void onDetachedFromWindow();

        void setOnChangedListener(android.media.SubtitleTrack.RenderingWidget.OnChangedListener onChangedListener);

        void setSize(int i, int i2);

        void setVisible(boolean z);
    }

    public abstract android.media.SubtitleTrack.RenderingWidget getRenderingWidget();

    public abstract void onData(byte[] bArr, boolean z, long j);

    public abstract void updateView(java.util.Vector<android.media.SubtitleTrack.Cue> vector);

    public SubtitleTrack(android.media.MediaFormat mediaFormat) {
        this.mFormat = mediaFormat;
        clearActiveCues();
        this.mLastTimeMs = -1L;
    }

    public final android.media.MediaFormat getFormat() {
        return this.mFormat;
    }

    protected void onData(android.media.SubtitleData subtitleData) {
        long startTimeUs = subtitleData.getStartTimeUs() + 1;
        onData(subtitleData.getData(), true, startTimeUs);
        setRunDiscardTimeMs(startTimeUs, (subtitleData.getStartTimeUs() + subtitleData.getDurationUs()) / 1000);
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0007, code lost:
    
        if (r6.mLastUpdateTimeMs > r8) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected synchronized void updateActiveCues(boolean z, long j) {
        if (!z) {
        }
        clearActiveCues();
        java.util.Iterator<android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue>> it = this.mCues.entriesBetween(this.mLastUpdateTimeMs, j).iterator();
        while (it.hasNext()) {
            android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue> next = it.next();
            android.media.SubtitleTrack.Cue cue = next.second;
            if (cue.mEndTimeMs == next.first.longValue()) {
                if (this.DEBUG) {
                    android.util.Log.v(TAG, "Removing " + cue);
                }
                this.mActiveCues.remove(cue);
                if (cue.mRunID == 0) {
                    it.remove();
                }
            } else if (cue.mStartTimeMs == next.first.longValue()) {
                if (this.DEBUG) {
                    android.util.Log.v(TAG, "Adding " + cue);
                }
                if (cue.mInnerTimesMs != null) {
                    cue.onTime(j);
                }
                this.mActiveCues.add(cue);
            } else if (cue.mInnerTimesMs != null) {
                cue.onTime(j);
            }
        }
        while (this.mRunsByEndTime.size() > 0 && this.mRunsByEndTime.keyAt(0) <= j) {
            removeRunsByEndTimeIndex(0);
        }
        this.mLastUpdateTimeMs = j;
    }

    private void removeRunsByEndTimeIndex(int i) {
        android.media.SubtitleTrack.Run valueAt = this.mRunsByEndTime.valueAt(i);
        while (valueAt != null) {
            android.media.SubtitleTrack.Cue cue = valueAt.mFirstCue;
            while (cue != null) {
                this.mCues.remove(cue);
                android.media.SubtitleTrack.Cue cue2 = cue.mNextInRun;
                cue.mNextInRun = null;
                cue = cue2;
            }
            this.mRunsByID.remove(valueAt.mRunID);
            android.media.SubtitleTrack.Run run = valueAt.mNextRunAtEndTimeMs;
            valueAt.mPrevRunAtEndTimeMs = null;
            valueAt.mNextRunAtEndTimeMs = null;
            valueAt = run;
        }
        this.mRunsByEndTime.removeAt(i);
    }

    protected void finalize() throws java.lang.Throwable {
        for (int size = this.mRunsByEndTime.size() - 1; size >= 0; size--) {
            removeRunsByEndTimeIndex(size);
        }
        super.finalize();
    }

    private synchronized void takeTime(long j) {
        this.mLastTimeMs = j;
    }

    protected synchronized void clearActiveCues() {
        if (this.DEBUG) {
            android.util.Log.v(TAG, "Clearing " + this.mActiveCues.size() + " active cues");
        }
        this.mActiveCues.clear();
        this.mLastUpdateTimeMs = -1L;
    }

    protected void scheduleTimedEvents() {
        if (this.mTimeProvider != null) {
            this.mNextScheduledTimeMs = this.mCues.nextTimeAfter(this.mLastTimeMs);
            if (this.DEBUG) {
                android.util.Log.d(TAG, "sched @" + this.mNextScheduledTimeMs + " after " + this.mLastTimeMs);
            }
            this.mTimeProvider.notifyAt(this.mNextScheduledTimeMs >= 0 ? this.mNextScheduledTimeMs * 1000 : -1L, this);
        }
    }

    @Override // android.media.MediaTimeProvider.OnMediaTimeListener
    public void onTimedEvent(long j) {
        if (this.DEBUG) {
            android.util.Log.d(TAG, "onTimedEvent " + j);
        }
        synchronized (this) {
            long j2 = j / 1000;
            updateActiveCues(false, j2);
            takeTime(j2);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    @Override // android.media.MediaTimeProvider.OnMediaTimeListener
    public void onSeek(long j) {
        if (this.DEBUG) {
            android.util.Log.d(TAG, "onSeek " + j);
        }
        synchronized (this) {
            long j2 = j / 1000;
            updateActiveCues(true, j2);
            takeTime(j2);
        }
        updateView(this.mActiveCues);
        scheduleTimedEvents();
    }

    @Override // android.media.MediaTimeProvider.OnMediaTimeListener
    public void onStop() {
        synchronized (this) {
            if (this.DEBUG) {
                android.util.Log.d(TAG, "onStop");
            }
            clearActiveCues();
            this.mLastTimeMs = -1L;
        }
        updateView(this.mActiveCues);
        this.mNextScheduledTimeMs = -1L;
        if (this.mTimeProvider != null) {
            this.mTimeProvider.notifyAt(-1L, this);
        }
    }

    public void show() {
        if (this.mVisible) {
            return;
        }
        this.mVisible = true;
        android.media.SubtitleTrack.RenderingWidget renderingWidget = getRenderingWidget();
        if (renderingWidget != null) {
            renderingWidget.setVisible(true);
        }
        if (this.mTimeProvider != null) {
            this.mTimeProvider.scheduleUpdate(this);
        }
    }

    public void hide() {
        if (!this.mVisible) {
            return;
        }
        if (this.mTimeProvider != null) {
            this.mTimeProvider.cancelNotifications(this);
        }
        android.media.SubtitleTrack.RenderingWidget renderingWidget = getRenderingWidget();
        if (renderingWidget != null) {
            renderingWidget.setVisible(false);
        }
        this.mVisible = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0056 A[Catch: all -> 0x0108, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x000e, B:7:0x001a, B:8:0x0038, B:9:0x002c, B:11:0x0034, B:12:0x003e, B:51:0x0045, B:16:0x0052, B:18:0x0056, B:19:0x009e, B:21:0x00a2, B:23:0x00a8, B:25:0x00b0, B:27:0x00b4, B:28:0x00bb, B:30:0x00d0, B:32:0x00d4, B:36:0x00dc, B:38:0x00e0, B:39:0x00e9, B:41:0x00ed, B:43:0x00f5, B:45:0x00fd, B:47:0x0103), top: B:2:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected synchronized boolean addCue(android.media.SubtitleTrack.Cue cue) {
        final long currentTimeUs;
        this.mCues.add(cue);
        if (cue.mRunID != 0) {
            android.media.SubtitleTrack.Run run = this.mRunsByID.get(cue.mRunID);
            if (run == null) {
                run = new android.media.SubtitleTrack.Run();
                this.mRunsByID.put(cue.mRunID, run);
                run.mEndTimeMs = cue.mEndTimeMs;
            } else if (run.mEndTimeMs < cue.mEndTimeMs) {
                run.mEndTimeMs = cue.mEndTimeMs;
            }
            cue.mNextInRun = run.mFirstCue;
            run.mFirstCue = cue;
        }
        if (this.mTimeProvider != null) {
            try {
                currentTimeUs = this.mTimeProvider.getCurrentTimeUs(false, true) / 1000;
            } catch (java.lang.IllegalStateException e) {
            }
            if (this.DEBUG) {
                android.util.Log.v(TAG, "mVisible=" + this.mVisible + ", " + cue.mStartTimeMs + " <= " + currentTimeUs + ", " + cue.mEndTimeMs + " >= " + this.mLastTimeMs);
            }
            if (!this.mVisible && cue.mStartTimeMs <= currentTimeUs && cue.mEndTimeMs >= this.mLastTimeMs) {
                if (this.mRunnable != null) {
                    this.mHandler.removeCallbacks(this.mRunnable);
                }
                this.mRunnable = new java.lang.Runnable() { // from class: android.media.SubtitleTrack.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (this) {
                            android.media.SubtitleTrack.this.mRunnable = null;
                            android.media.SubtitleTrack.this.updateActiveCues(true, currentTimeUs);
                            android.media.SubtitleTrack.this.updateView(android.media.SubtitleTrack.this.mActiveCues);
                        }
                    }
                };
                if (this.mHandler.postDelayed(this.mRunnable, 10L)) {
                    if (this.DEBUG) {
                        android.util.Log.v(TAG, "scheduling update");
                    }
                } else if (this.DEBUG) {
                    android.util.Log.w(TAG, "failed to schedule subtitle view update");
                }
                return true;
            }
            if (this.mVisible && cue.mEndTimeMs >= this.mLastTimeMs && (cue.mStartTimeMs < this.mNextScheduledTimeMs || this.mNextScheduledTimeMs < 0)) {
                scheduleTimedEvents();
            }
            return false;
        }
        currentTimeUs = -1;
        if (this.DEBUG) {
        }
        if (!this.mVisible) {
        }
        if (this.mVisible) {
            scheduleTimedEvents();
        }
        return false;
    }

    public synchronized void setTimeProvider(android.media.MediaTimeProvider mediaTimeProvider) {
        if (this.mTimeProvider == mediaTimeProvider) {
            return;
        }
        if (this.mTimeProvider != null) {
            this.mTimeProvider.cancelNotifications(this);
        }
        this.mTimeProvider = mediaTimeProvider;
        if (this.mTimeProvider != null) {
            this.mTimeProvider.scheduleUpdate(this);
        }
    }

    static class CueList {
        private static final java.lang.String TAG = "CueList";
        public boolean DEBUG = false;
        private java.util.SortedMap<java.lang.Long, java.util.Vector<android.media.SubtitleTrack.Cue>> mCues = new java.util.TreeMap();

        private boolean addEvent(android.media.SubtitleTrack.Cue cue, long j) {
            java.util.Vector<android.media.SubtitleTrack.Cue> vector = this.mCues.get(java.lang.Long.valueOf(j));
            if (vector == null) {
                vector = new java.util.Vector<>(2);
                this.mCues.put(java.lang.Long.valueOf(j), vector);
            } else if (vector.contains(cue)) {
                return false;
            }
            vector.add(cue);
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeEvent(android.media.SubtitleTrack.Cue cue, long j) {
            java.util.Vector<android.media.SubtitleTrack.Cue> vector = this.mCues.get(java.lang.Long.valueOf(j));
            if (vector != null) {
                vector.remove(cue);
                if (vector.size() == 0) {
                    this.mCues.remove(java.lang.Long.valueOf(j));
                }
            }
        }

        public void add(android.media.SubtitleTrack.Cue cue) {
            if (cue.mStartTimeMs >= cue.mEndTimeMs || !addEvent(cue, cue.mStartTimeMs)) {
                return;
            }
            long j = cue.mStartTimeMs;
            if (cue.mInnerTimesMs != null) {
                for (long j2 : cue.mInnerTimesMs) {
                    if (j2 > j && j2 < cue.mEndTimeMs) {
                        addEvent(cue, j2);
                        j = j2;
                    }
                }
            }
            addEvent(cue, cue.mEndTimeMs);
        }

        public void remove(android.media.SubtitleTrack.Cue cue) {
            removeEvent(cue, cue.mStartTimeMs);
            if (cue.mInnerTimesMs != null) {
                for (long j : cue.mInnerTimesMs) {
                    removeEvent(cue, j);
                }
            }
            removeEvent(cue, cue.mEndTimeMs);
        }

        public java.lang.Iterable<android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue>> entriesBetween(final long j, final long j2) {
            return new java.lang.Iterable<android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue>>() { // from class: android.media.SubtitleTrack.CueList.1
                @Override // java.lang.Iterable
                public java.util.Iterator<android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue>> iterator() {
                    if (android.media.SubtitleTrack.CueList.this.DEBUG) {
                        android.util.Log.d(android.media.SubtitleTrack.CueList.TAG, "slice (" + j + ", " + j2 + "]=");
                    }
                    try {
                        return android.media.SubtitleTrack.CueList.this.new EntryIterator(android.media.SubtitleTrack.CueList.this.mCues.subMap(java.lang.Long.valueOf(j + 1), java.lang.Long.valueOf(j2 + 1)));
                    } catch (java.lang.IllegalArgumentException e) {
                        return android.media.SubtitleTrack.CueList.this.new EntryIterator(null);
                    }
                }
            };
        }

        public long nextTimeAfter(long j) {
            try {
                java.util.SortedMap<java.lang.Long, java.util.Vector<android.media.SubtitleTrack.Cue>> tailMap = this.mCues.tailMap(java.lang.Long.valueOf(j + 1));
                if (tailMap == null) {
                    return -1L;
                }
                return tailMap.firstKey().longValue();
            } catch (java.lang.IllegalArgumentException e) {
                return -1L;
            } catch (java.util.NoSuchElementException e2) {
                return -1L;
            }
        }

        class EntryIterator implements java.util.Iterator<android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue>> {
            private long mCurrentTimeMs;
            private boolean mDone;
            private android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue> mLastEntry;
            private java.util.Iterator<android.media.SubtitleTrack.Cue> mLastListIterator;
            private java.util.Iterator<android.media.SubtitleTrack.Cue> mListIterator;
            private java.util.SortedMap<java.lang.Long, java.util.Vector<android.media.SubtitleTrack.Cue>> mRemainingCues;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return !this.mDone;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public android.util.Pair<java.lang.Long, android.media.SubtitleTrack.Cue> next() {
                if (this.mDone) {
                    throw new java.util.NoSuchElementException("");
                }
                this.mLastEntry = new android.util.Pair<>(java.lang.Long.valueOf(this.mCurrentTimeMs), this.mListIterator.next());
                this.mLastListIterator = this.mListIterator;
                if (!this.mListIterator.hasNext()) {
                    nextKey();
                }
                return this.mLastEntry;
            }

            @Override // java.util.Iterator
            public void remove() {
                if (this.mLastListIterator == null || this.mLastEntry.second.mEndTimeMs != this.mLastEntry.first.longValue()) {
                    throw new java.lang.IllegalStateException("");
                }
                this.mLastListIterator.remove();
                this.mLastListIterator = null;
                if (((java.util.Vector) android.media.SubtitleTrack.CueList.this.mCues.get(this.mLastEntry.first)).size() == 0) {
                    android.media.SubtitleTrack.CueList.this.mCues.remove(this.mLastEntry.first);
                }
                android.media.SubtitleTrack.Cue cue = this.mLastEntry.second;
                android.media.SubtitleTrack.CueList.this.removeEvent(cue, cue.mStartTimeMs);
                if (cue.mInnerTimesMs != null) {
                    for (long j : cue.mInnerTimesMs) {
                        android.media.SubtitleTrack.CueList.this.removeEvent(cue, j);
                    }
                }
            }

            public EntryIterator(java.util.SortedMap<java.lang.Long, java.util.Vector<android.media.SubtitleTrack.Cue>> sortedMap) {
                if (android.media.SubtitleTrack.CueList.this.DEBUG) {
                    android.util.Log.v(android.media.SubtitleTrack.CueList.TAG, sortedMap + "");
                }
                this.mRemainingCues = sortedMap;
                this.mLastListIterator = null;
                nextKey();
            }

            private void nextKey() {
                while (this.mRemainingCues != null) {
                    try {
                        this.mCurrentTimeMs = this.mRemainingCues.firstKey().longValue();
                        this.mListIterator = this.mRemainingCues.get(java.lang.Long.valueOf(this.mCurrentTimeMs)).iterator();
                        try {
                            this.mRemainingCues = this.mRemainingCues.tailMap(java.lang.Long.valueOf(this.mCurrentTimeMs + 1));
                        } catch (java.lang.IllegalArgumentException e) {
                            this.mRemainingCues = null;
                        }
                        this.mDone = false;
                        if (this.mListIterator.hasNext()) {
                            return;
                        }
                    } catch (java.util.NoSuchElementException e2) {
                        this.mDone = true;
                        this.mRemainingCues = null;
                        this.mListIterator = null;
                        return;
                    }
                }
                throw new java.util.NoSuchElementException("");
            }
        }

        CueList() {
        }
    }

    public static class Cue {
        public long mEndTimeMs;
        public long[] mInnerTimesMs;
        public android.media.SubtitleTrack.Cue mNextInRun;
        public long mRunID;
        public long mStartTimeMs;

        public void onTime(long j) {
        }
    }

    protected void finishedRun(long j) {
        android.media.SubtitleTrack.Run run;
        if (j != 0 && j != -1 && (run = this.mRunsByID.get(j)) != null) {
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public void setRunDiscardTimeMs(long j, long j2) {
        android.media.SubtitleTrack.Run run;
        if (j != 0 && j != -1 && (run = this.mRunsByID.get(j)) != null) {
            run.mEndTimeMs = j2;
            run.storeByEndTimeMs(this.mRunsByEndTime);
        }
    }

    public int getTrackType() {
        if (getRenderingWidget() == null) {
            return 3;
        }
        return 4;
    }

    private static class Run {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public long mEndTimeMs;
        public android.media.SubtitleTrack.Cue mFirstCue;
        public android.media.SubtitleTrack.Run mNextRunAtEndTimeMs;
        public android.media.SubtitleTrack.Run mPrevRunAtEndTimeMs;
        public long mRunID;
        private long mStoredEndTimeMs;

        private Run() {
            this.mEndTimeMs = -1L;
            this.mRunID = 0L;
            this.mStoredEndTimeMs = -1L;
        }

        public void storeByEndTimeMs(android.util.LongSparseArray<android.media.SubtitleTrack.Run> longSparseArray) {
            int indexOfKey = longSparseArray.indexOfKey(this.mStoredEndTimeMs);
            if (indexOfKey >= 0) {
                if (this.mPrevRunAtEndTimeMs == null) {
                    if (this.mNextRunAtEndTimeMs == null) {
                        longSparseArray.removeAt(indexOfKey);
                    } else {
                        longSparseArray.setValueAt(indexOfKey, this.mNextRunAtEndTimeMs);
                    }
                }
                removeAtEndTimeMs();
            }
            if (this.mEndTimeMs >= 0) {
                this.mPrevRunAtEndTimeMs = null;
                this.mNextRunAtEndTimeMs = longSparseArray.get(this.mEndTimeMs);
                if (this.mNextRunAtEndTimeMs != null) {
                    this.mNextRunAtEndTimeMs.mPrevRunAtEndTimeMs = this;
                }
                longSparseArray.put(this.mEndTimeMs, this);
                this.mStoredEndTimeMs = this.mEndTimeMs;
            }
        }

        public void removeAtEndTimeMs() {
            android.media.SubtitleTrack.Run run = this.mPrevRunAtEndTimeMs;
            if (this.mPrevRunAtEndTimeMs != null) {
                this.mPrevRunAtEndTimeMs.mNextRunAtEndTimeMs = this.mNextRunAtEndTimeMs;
                this.mPrevRunAtEndTimeMs = null;
            }
            if (this.mNextRunAtEndTimeMs != null) {
                this.mNextRunAtEndTimeMs.mPrevRunAtEndTimeMs = run;
                this.mNextRunAtEndTimeMs = null;
            }
        }
    }
}
