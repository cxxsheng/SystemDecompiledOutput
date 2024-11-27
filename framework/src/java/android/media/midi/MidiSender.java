package android.media.midi;

/* loaded from: classes2.dex */
public abstract class MidiSender {
    public abstract void onConnect(android.media.midi.MidiReceiver midiReceiver);

    public abstract void onDisconnect(android.media.midi.MidiReceiver midiReceiver);

    public void connect(android.media.midi.MidiReceiver midiReceiver) {
        if (midiReceiver == null) {
            throw new java.lang.NullPointerException("receiver null in MidiSender.connect");
        }
        onConnect(midiReceiver);
    }

    public void disconnect(android.media.midi.MidiReceiver midiReceiver) {
        if (midiReceiver == null) {
            throw new java.lang.NullPointerException("receiver null in MidiSender.disconnect");
        }
        onDisconnect(midiReceiver);
    }
}
