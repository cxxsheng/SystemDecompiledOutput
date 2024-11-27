package com.android.internal.midi;

/* loaded from: classes4.dex */
public class MidiEventMultiScheduler {
    private com.android.internal.midi.MidiEventMultiScheduler.MultiLockMidiEventScheduler[] mMidiEventSchedulers;
    private int mNumEventSchedulers;
    private int mNumClosedSchedulers = 0;
    private final java.lang.Object mMultiLock = new java.lang.Object();

    private class MultiLockMidiEventScheduler extends com.android.internal.midi.MidiEventScheduler {
        private MultiLockMidiEventScheduler() {
        }

        @Override // com.android.internal.midi.EventScheduler
        public void close() {
            synchronized (com.android.internal.midi.MidiEventMultiScheduler.this.mMultiLock) {
                com.android.internal.midi.MidiEventMultiScheduler.this.mNumClosedSchedulers++;
            }
            super.close();
        }

        @Override // com.android.internal.midi.EventScheduler
        protected java.lang.Object getLock() {
            return com.android.internal.midi.MidiEventMultiScheduler.this.mMultiLock;
        }

        public boolean isEventBufferEmptyLocked() {
            return this.mEventBuffer.isEmpty();
        }

        public long getLowestTimeLocked() {
            return this.mEventBuffer.firstKey().longValue();
        }
    }

    public MidiEventMultiScheduler(int i) {
        this.mNumEventSchedulers = i;
        this.mMidiEventSchedulers = new com.android.internal.midi.MidiEventMultiScheduler.MultiLockMidiEventScheduler[i];
        for (int i2 = 0; i2 < i; i2++) {
            this.mMidiEventSchedulers[i2] = new com.android.internal.midi.MidiEventMultiScheduler.MultiLockMidiEventScheduler();
        }
    }

    public boolean waitNextEvent() throws java.lang.InterruptedException {
        synchronized (this.mMultiLock) {
            while (true) {
                if (this.mNumClosedSchedulers >= this.mNumEventSchedulers) {
                    return false;
                }
                long nanoTime = java.lang.System.nanoTime();
                long j = Long.MAX_VALUE;
                for (com.android.internal.midi.MidiEventMultiScheduler.MultiLockMidiEventScheduler multiLockMidiEventScheduler : this.mMidiEventSchedulers) {
                    if (!multiLockMidiEventScheduler.isEventBufferEmptyLocked()) {
                        j = java.lang.Math.min(j, multiLockMidiEventScheduler.getLowestTimeLocked());
                    }
                }
                if (j <= nanoTime) {
                    return true;
                }
                long j2 = ((j - nanoTime) / 1000000) + 1;
                if (j2 > 2147483647L) {
                    j2 = 2147483647L;
                }
                this.mMultiLock.wait(j2);
            }
        }
    }

    public int getNumEventSchedulers() {
        return this.mNumEventSchedulers;
    }

    public com.android.internal.midi.MidiEventScheduler getEventScheduler(int i) {
        return this.mMidiEventSchedulers[i];
    }

    public void close() {
        for (com.android.internal.midi.MidiEventMultiScheduler.MultiLockMidiEventScheduler multiLockMidiEventScheduler : this.mMidiEventSchedulers) {
            multiLockMidiEventScheduler.close();
        }
    }
}
