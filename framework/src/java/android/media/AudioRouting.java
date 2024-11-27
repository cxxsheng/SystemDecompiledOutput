package android.media;

/* loaded from: classes2.dex */
public interface AudioRouting {

    public interface OnRoutingChangedListener {
        void onRoutingChanged(android.media.AudioRouting audioRouting);
    }

    void addOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener, android.os.Handler handler);

    android.media.AudioDeviceInfo getPreferredDevice();

    android.media.AudioDeviceInfo getRoutedDevice();

    void removeOnRoutingChangedListener(android.media.AudioRouting.OnRoutingChangedListener onRoutingChangedListener);

    boolean setPreferredDevice(android.media.AudioDeviceInfo audioDeviceInfo);
}
