package com.android.server.vr;

/* loaded from: classes2.dex */
public abstract class VrManagerInternal {
    public static final int NO_ERROR = 0;

    public abstract void addPersistentVrModeStateListener(android.service.vr.IPersistentVrStateCallbacks iPersistentVrStateCallbacks);

    public abstract int getVr2dDisplayId();

    public abstract int hasVrPackage(@android.annotation.NonNull android.content.ComponentName componentName, int i);

    public abstract boolean isCurrentVrListener(java.lang.String str, int i);

    public abstract void onScreenStateChanged(boolean z);

    public abstract void setPersistentVrModeEnabled(boolean z);

    public abstract void setVr2dDisplayProperties(android.app.Vr2dDisplayProperties vr2dDisplayProperties);

    public abstract void setVrMode(boolean z, @android.annotation.NonNull android.content.ComponentName componentName, int i, int i2, @android.annotation.NonNull android.content.ComponentName componentName2);
}
