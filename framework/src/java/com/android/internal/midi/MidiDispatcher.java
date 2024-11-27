package com.android.internal.midi;

/* loaded from: classes4.dex */
public final class MidiDispatcher extends android.media.midi.MidiReceiver {
    private final com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler mFailureHandler;
    private final java.util.concurrent.CopyOnWriteArrayList<android.media.midi.MidiReceiver> mReceivers;
    private final android.media.midi.MidiSender mSender;

    public interface MidiReceiverFailureHandler {
        void onReceiverFailure(android.media.midi.MidiReceiver midiReceiver, java.io.IOException iOException);
    }

    public MidiDispatcher() {
        this(null);
    }

    public MidiDispatcher(com.android.internal.midi.MidiDispatcher.MidiReceiverFailureHandler midiReceiverFailureHandler) {
        this.mReceivers = new java.util.concurrent.CopyOnWriteArrayList<>();
        this.mSender = new android.media.midi.MidiSender() { // from class: com.android.internal.midi.MidiDispatcher.1
            @Override // android.media.midi.MidiSender
            public void onConnect(android.media.midi.MidiReceiver midiReceiver) {
                com.android.internal.midi.MidiDispatcher.this.mReceivers.add(midiReceiver);
            }

            @Override // android.media.midi.MidiSender
            public void onDisconnect(android.media.midi.MidiReceiver midiReceiver) {
                com.android.internal.midi.MidiDispatcher.this.mReceivers.remove(midiReceiver);
            }
        };
        this.mFailureHandler = midiReceiverFailureHandler;
    }

    public int getReceiverCount() {
        return this.mReceivers.size();
    }

    public android.media.midi.MidiSender getSender() {
        return this.mSender;
    }

    @Override // android.media.midi.MidiReceiver
    public void onSend(byte[] bArr, int i, int i2, long j) throws java.io.IOException {
        java.util.Iterator<android.media.midi.MidiReceiver> it = this.mReceivers.iterator();
        while (it.hasNext()) {
            android.media.midi.MidiReceiver next = it.next();
            try {
                next.send(bArr, i, i2, j);
            } catch (java.io.IOException e) {
                this.mReceivers.remove(next);
                if (this.mFailureHandler != null) {
                    this.mFailureHandler.onReceiverFailure(next, e);
                }
            }
        }
    }

    @Override // android.media.midi.MidiReceiver
    public void onFlush() throws java.io.IOException {
        java.util.Iterator<android.media.midi.MidiReceiver> it = this.mReceivers.iterator();
        while (it.hasNext()) {
            android.media.midi.MidiReceiver next = it.next();
            try {
                next.flush();
            } catch (java.io.IOException e) {
                this.mReceivers.remove(next);
                if (this.mFailureHandler != null) {
                    this.mFailureHandler.onReceiverFailure(next, e);
                }
            }
        }
    }
}
