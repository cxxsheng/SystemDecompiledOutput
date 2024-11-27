package com.android.server.companion.virtual;

/* loaded from: classes.dex */
public abstract class VirtualDeviceManagerInternal {

    public interface AppsOnVirtualDeviceListener {
        void onAppsOnAnyVirtualDeviceChanged(java.util.Set<java.lang.Integer> set);
    }

    @android.annotation.NonNull
    public abstract java.util.Set<java.lang.String> getAllPersistentDeviceIds();

    public abstract int getBaseVirtualDisplayFlags(android.companion.virtual.IVirtualDevice iVirtualDevice);

    public abstract int getDeviceIdForDisplayId(int i);

    @android.annotation.NonNull
    public abstract android.util.ArraySet<java.lang.Integer> getDeviceIdsForUid(int i);

    public abstract int getDeviceOwnerUid(int i);

    @android.annotation.NonNull
    public abstract android.util.ArraySet<java.lang.Integer> getDisplayIdsForDevice(int i);

    @android.annotation.Nullable
    public abstract java.lang.String getPersistentIdForDevice(int i);

    @android.annotation.Nullable
    public abstract android.os.LocaleList getPreferredLocaleListForUid(int i);

    @android.annotation.Nullable
    public abstract android.companion.virtual.sensor.VirtualSensor getVirtualSensor(int i, int i2);

    public abstract boolean isAppRunningOnAnyVirtualDevice(int i);

    public abstract boolean isInputDeviceOwnedByVirtualDevice(int i);

    public abstract boolean isValidVirtualDeviceId(int i);

    public abstract void onAppsOnVirtualDeviceChanged();

    public abstract void onAuthenticationPrompt(int i);

    public abstract void onPersistentDeviceIdsRemoved(java.util.Set<java.lang.String> set);

    public abstract void onVirtualDisplayRemoved(android.companion.virtual.IVirtualDevice iVirtualDevice, int i);

    public abstract void registerAppsOnVirtualDeviceListener(@android.annotation.NonNull com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener);

    public abstract void registerPersistentDeviceIdRemovedListener(@android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer);

    public abstract void unregisterAppsOnVirtualDeviceListener(@android.annotation.NonNull com.android.server.companion.virtual.VirtualDeviceManagerInternal.AppsOnVirtualDeviceListener appsOnVirtualDeviceListener);

    public abstract void unregisterPersistentDeviceIdRemovedListener(@android.annotation.NonNull java.util.function.Consumer<java.lang.String> consumer);
}
