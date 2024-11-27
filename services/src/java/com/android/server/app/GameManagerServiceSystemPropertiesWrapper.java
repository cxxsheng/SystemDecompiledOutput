package com.android.server.app;

/* loaded from: classes.dex */
interface GameManagerServiceSystemPropertiesWrapper {
    @android.annotation.NonNull
    java.lang.String get(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);

    @android.annotation.NonNull
    boolean getBoolean(@android.annotation.NonNull java.lang.String str, boolean z);

    @android.annotation.NonNull
    int getInt(@android.annotation.NonNull java.lang.String str, int i);

    void set(@android.annotation.NonNull java.lang.String str, @android.annotation.Nullable java.lang.String str2);
}
