package android.media;

@android.annotation.SystemApi
/* loaded from: classes2.dex */
public interface NearbyMediaDevicesProvider {
    void registerNearbyDevicesCallback(java.util.function.Consumer<java.util.List<android.media.NearbyDevice>> consumer);

    void unregisterNearbyDevicesCallback(java.util.function.Consumer<java.util.List<android.media.NearbyDevice>> consumer);
}
