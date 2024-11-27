package com.android.internal.midi;

/* loaded from: classes4.dex */
public class MidiEventScheduler extends com.android.internal.midi.EventScheduler {
    private static final int POOL_EVENT_SIZE = 16;
    private static final java.lang.String TAG = "MidiEventScheduler";
    private android.media.midi.MidiReceiver mReceiver = new com.android.internal.midi.MidiEventScheduler.SchedulingReceiver();

    private class SchedulingReceiver extends android.media.midi.MidiReceiver {
        private SchedulingReceiver() {
        }

        @Override // android.media.midi.MidiReceiver
        public void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
            com.android.internal.midi.MidiEventScheduler.MidiEvent createScheduledEvent = com.android.internal.midi.MidiEventScheduler.this.createScheduledEvent(bArr, i, i2, j);
            if (createScheduledEvent != null) {
                com.android.internal.midi.MidiEventScheduler.this.add(createScheduledEvent);
            }
        }

        @Override // android.media.midi.MidiReceiver
        public void onFlush() {
            com.android.internal.midi.MidiEventScheduler.this.flush();
        }
    }

    public static class MidiEvent extends com.android.internal.midi.EventScheduler.SchedulableEvent {
        public int count;
        public byte[] data;

        private MidiEvent(int i) {
            super(0L);
            this.count = 0;
            this.data = new byte[i];
        }

        private MidiEvent(byte[] bArr, int i, int i2, long j) {
            super(j);
            this.count = 0;
            this.data = new byte[i2];
            java.lang.System.arraycopy(bArr, i, this.data, 0, i2);
            this.count = i2;
        }

        public java.lang.String toString() {
            java.lang.String str = "Event: ";
            for (int i = 0; i < this.count; i++) {
                str = str + ((int) this.data[i]) + ", ";
            }
            return str;
        }
    }

    public com.android.internal.midi.MidiEventScheduler.MidiEvent createScheduledEvent(byte[] bArr, int i, int i2, long j) {
        com.android.internal.midi.MidiEventScheduler.MidiEvent midiEvent;
        int i3 = 16;
        if (i2 > 16) {
            return new com.android.internal.midi.MidiEventScheduler.MidiEvent(bArr, i, i2, j);
        }
        com.android.internal.midi.MidiEventScheduler.MidiEvent midiEvent2 = (com.android.internal.midi.MidiEventScheduler.MidiEvent) removeEventfromPool();
        if (midiEvent2 != null) {
            midiEvent = midiEvent2;
        } else {
            midiEvent = new com.android.internal.midi.MidiEventScheduler.MidiEvent(i3);
        }
        java.lang.System.arraycopy(bArr, i, midiEvent.data, 0, i2);
        midiEvent.count = i2;
        midiEvent.setTimestamp(j);
        return midiEvent;
    }

    @Override // com.android.internal.midi.EventScheduler
    public void addEventToPool(com.android.internal.midi.EventScheduler.SchedulableEvent schedulableEvent) {
        if ((schedulableEvent instanceof com.android.internal.midi.MidiEventScheduler.MidiEvent) && ((com.android.internal.midi.MidiEventScheduler.MidiEvent) schedulableEvent).data.length == 16) {
            super.addEventToPool(schedulableEvent);
        }
    }

    public android.media.midi.MidiReceiver getReceiver() {
        return this.mReceiver;
    }
}
