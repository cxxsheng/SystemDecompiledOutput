package com.android.server.am;

@android.annotation.SystemApi(client = android.annotation.SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes.dex */
public interface ActivityManagerLocal {
    @android.annotation.SuppressLint({"RethrowRemoteException"})
    boolean bindSdkSandboxService(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.content.ServiceConnection serviceConnection, int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2) throws android.os.RemoteException;

    @android.annotation.SuppressLint({"RethrowRemoteException"})
    boolean bindSdkSandboxService(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.content.ServiceConnection serviceConnection, int i, @android.annotation.NonNull android.os.IBinder iBinder, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, @android.annotation.NonNull android.content.Context.BindServiceFlags bindServiceFlags) throws android.os.RemoteException;

    @android.annotation.SuppressLint({"RethrowRemoteException"})
    boolean bindSdkSandboxService(@android.annotation.NonNull android.content.Intent intent, @android.annotation.NonNull android.content.ServiceConnection serviceConnection, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2, int i2) throws android.os.RemoteException;

    boolean canAllowWhileInUsePermissionInFgs(int i, int i2, @android.annotation.NonNull java.lang.String str);

    boolean canStartForegroundService(int i, int i2, @android.annotation.NonNull java.lang.String str);

    void killSdkSandboxClientAppProcess(@android.annotation.NonNull android.os.IBinder iBinder);

    @android.annotation.Nullable
    @android.annotation.SuppressLint({"RethrowRemoteException"})
    android.content.ComponentName startSdkSandboxService(@android.annotation.NonNull android.content.Intent intent, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2) throws android.os.RemoteException;

    boolean stopSdkSandboxService(@android.annotation.NonNull android.content.Intent intent, int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2);

    void tempAllowWhileInUsePermissionInFgs(int i, long j);
}
