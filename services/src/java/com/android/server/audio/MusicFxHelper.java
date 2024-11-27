package com.android.server.audio;

/* loaded from: classes.dex */
public class MusicFxHelper {
    static final int MSG_EFFECT_CLIENT_GONE = 1101;
    private static final java.lang.String TAG = "AS.MusicFxHelper";

    @android.annotation.NonNull
    private final com.android.server.audio.AudioService.AudioHandler mAudioHandler;

    @android.annotation.NonNull
    private final android.content.Context mContext;
    private final java.lang.Object mClientUidMapLock = new java.lang.Object();
    private final java.lang.String mPackageName = getClass().getPackage().getName();
    private final java.lang.String mMusicFxPackageName = "com.android.musicfx";
    private android.os.IBinder mUidObserverToken = null;

    @com.android.internal.annotations.GuardedBy({"mClientUidMapLock"})
    private com.android.server.audio.MusicFxHelper.MySparseArray mClientUidSessionMap = new com.android.server.audio.MusicFxHelper.MySparseArray();
    private final android.app.IUidObserver mEffectUidObserver = new android.app.UidObserver() { // from class: com.android.server.audio.MusicFxHelper.1
        public void onUidGone(int i, boolean z) {
            android.util.Log.w(com.android.server.audio.MusicFxHelper.TAG, " send MSG_EFFECT_CLIENT_GONE");
            com.android.server.audio.MusicFxHelper.this.mAudioHandler.sendMessageAtTime(com.android.server.audio.MusicFxHelper.this.mAudioHandler.obtainMessage(com.android.server.audio.MusicFxHelper.MSG_EFFECT_CLIENT_GONE, i, 0, null), 0L);
        }
    };
    private android.content.ServiceConnection mMusicFxBindConnection = new android.content.ServiceConnection() { // from class: com.android.server.audio.MusicFxHelper.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.util.Log.d(com.android.server.audio.MusicFxHelper.TAG, " service connected to " + componentName);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            android.util.Log.d(com.android.server.audio.MusicFxHelper.TAG, " service disconnected from " + componentName);
        }
    };

    private static class PackageSessions {
        java.lang.String mPackageName;
        java.util.List<java.lang.Integer> mSessions;

        private PackageSessions() {
        }
    }

    private class MySparseArray extends android.util.SparseArray<com.android.server.audio.MusicFxHelper.PackageSessions> {
        private final java.lang.String mMusicFxPackageName;

        private MySparseArray() {
            this.mMusicFxPackageName = "com.android.musicfx";
        }

        @Override // android.util.SparseArray
        @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
        public void put(int i, com.android.server.audio.MusicFxHelper.PackageSessions packageSessions) {
            int i2;
            if (size() == 0) {
                try {
                    i2 = android.app.ActivityManager.getService().getPackageProcessState("com.android.musicfx", com.android.server.audio.MusicFxHelper.this.mPackageName);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.audio.MusicFxHelper.TAG, "RemoteException with getPackageProcessState: " + e);
                    i2 = 20;
                }
                if (i2 > 6) {
                    com.android.server.audio.MusicFxHelper.this.mContext.bindServiceAsUser(new android.content.Intent().setClassName("com.android.musicfx", "com.android.musicfx.KeepAliveService"), com.android.server.audio.MusicFxHelper.this.mMusicFxBindConnection, 1, android.os.UserHandle.of(com.android.server.audio.MusicFxHelper.this.getCurrentUserId()));
                    android.util.Log.i(com.android.server.audio.MusicFxHelper.TAG, "bindService to com.android.musicfx");
                }
                android.util.Log.i(com.android.server.audio.MusicFxHelper.TAG, "com.android.musicfx procState " + i2);
            }
            try {
                if (com.android.server.audio.MusicFxHelper.this.mUidObserverToken == null) {
                    com.android.server.audio.MusicFxHelper.this.mUidObserverToken = android.app.ActivityManager.getService().registerUidObserverForUids(com.android.server.audio.MusicFxHelper.this.mEffectUidObserver, 2, -1, com.android.server.audio.MusicFxHelper.this.mPackageName, new int[]{i});
                    android.util.Log.i(com.android.server.audio.MusicFxHelper.TAG, "registered to observer with UID " + i);
                } else if (get(i) == null) {
                    android.app.ActivityManager.getService().addUidToObserver(com.android.server.audio.MusicFxHelper.this.mUidObserverToken, com.android.server.audio.MusicFxHelper.this.mPackageName, i);
                    android.util.Log.i(com.android.server.audio.MusicFxHelper.TAG, " UID " + i + " add to observer");
                }
            } catch (android.os.RemoteException e2) {
                android.util.Log.e(com.android.server.audio.MusicFxHelper.TAG, "RemoteException with UID observer add/register: " + e2);
            }
            super.put(i, (int) packageSessions);
        }

        @Override // android.util.SparseArray
        public void remove(int i) {
            if (get(i) != null) {
                try {
                    android.app.ActivityManager.getService().removeUidFromObserver(com.android.server.audio.MusicFxHelper.this.mUidObserverToken, com.android.server.audio.MusicFxHelper.this.mPackageName, i);
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(com.android.server.audio.MusicFxHelper.TAG, "RemoteException with removeUidFromObserver: " + e);
                }
            }
            super.remove(i);
            if (size() == 0) {
                try {
                    android.app.ActivityManager.getService().unregisterUidObserver(com.android.server.audio.MusicFxHelper.this.mEffectUidObserver);
                } catch (android.os.RemoteException e2) {
                    android.util.Log.e(com.android.server.audio.MusicFxHelper.TAG, "RemoteException with unregisterUidObserver: " + e2);
                }
                com.android.server.audio.MusicFxHelper.this.mUidObserverToken = null;
                com.android.server.audio.MusicFxHelper.this.mContext.unbindService(com.android.server.audio.MusicFxHelper.this.mMusicFxBindConnection);
                android.util.Log.i(com.android.server.audio.MusicFxHelper.TAG, "last session closed, unregister UID observer, and unbind com.android.musicfx");
            }
        }
    }

    MusicFxHelper(@android.annotation.NonNull android.content.Context context, @android.annotation.NonNull com.android.server.audio.AudioService.AudioHandler audioHandler) {
        this.mContext = context;
        this.mAudioHandler = audioHandler;
    }

    @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"})
    public void handleAudioEffectBroadcast(android.content.Context context, android.content.Intent intent) {
        java.lang.String str = intent.getPackage();
        if (str != null) {
            android.util.Log.w(TAG, "effect broadcast already targeted to " + str);
            return;
        }
        android.content.pm.PackageManager packageManager = context.getPackageManager();
        java.util.List<android.content.pm.ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null && queryBroadcastReceivers.size() != 0) {
            android.content.pm.ResolveInfo resolveInfo = queryBroadcastReceivers.get(0);
            java.lang.String stringExtra = intent.getStringExtra("android.media.extra.PACKAGE_NAME");
            if (resolveInfo != null) {
                try {
                    if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.packageName != null) {
                        int packageUidAsUser = packageManager.getPackageUidAsUser(stringExtra, android.content.pm.PackageManager.PackageInfoFlags.of(4194304L), getCurrentUserId());
                        intent.addFlags(32);
                        intent.setPackage(resolveInfo.activityInfo.packageName);
                        if (setMusicFxServiceWithObserver(intent, packageUidAsUser, stringExtra)) {
                            context.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
                            return;
                        }
                        return;
                    }
                } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                    android.util.Log.e(TAG, "Not able to find UID from package: " + stringExtra + " error: " + e);
                }
            }
        }
        android.util.Log.w(TAG, "couldn't find receiver package for effect intent");
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
    @com.android.internal.annotations.GuardedBy({"mClientUidMapLock"})
    private boolean handleAudioEffectSessionOpen(int i, java.lang.String str, int i2) {
        android.util.Log.d(TAG, str + " UID " + i + " open MusicFx session " + i2);
        com.android.server.audio.MusicFxHelper.PackageSessions packageSessions = this.mClientUidSessionMap.get(java.lang.Integer.valueOf(i).intValue());
        if (packageSessions != null && packageSessions.mSessions != null) {
            if (packageSessions.mSessions.contains(java.lang.Integer.valueOf(i2))) {
                android.util.Log.e(TAG, "Audio session " + i2 + " already open for UID: " + i + ", package: " + str + ", abort");
                return false;
            }
            if (packageSessions.mPackageName != str) {
                android.util.Log.w(TAG, "Inconsistency package names for UID open: " + i + " prev: " + packageSessions.mPackageName + ", now: " + str);
                return false;
            }
        } else {
            packageSessions = new com.android.server.audio.MusicFxHelper.PackageSessions();
            packageSessions.mSessions = new java.util.ArrayList();
            packageSessions.mPackageName = str;
        }
        packageSessions.mSessions.add(java.lang.Integer.valueOf(i2));
        this.mClientUidSessionMap.put(java.lang.Integer.valueOf(i).intValue(), packageSessions);
        return true;
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
    @com.android.internal.annotations.GuardedBy({"mClientUidMapLock"})
    private boolean handleAudioEffectSessionClose(int i, java.lang.String str, int i2) {
        android.util.Log.d(TAG, str + " UID " + i + " close MusicFx session " + i2);
        com.android.server.audio.MusicFxHelper.PackageSessions packageSessions = this.mClientUidSessionMap.get(java.lang.Integer.valueOf(i).intValue());
        if (packageSessions == null) {
            android.util.Log.e(TAG, str + " UID " + i + " does not exist in map, abort");
            return false;
        }
        if (packageSessions.mPackageName != str) {
            android.util.Log.w(TAG, "Inconsistency package names for UID " + i + " close, prev: " + packageSessions.mPackageName + ", now: " + str);
            return false;
        }
        if (packageSessions.mSessions != null && packageSessions.mSessions.size() != 0) {
            if (!packageSessions.mSessions.contains(java.lang.Integer.valueOf(i2))) {
                android.util.Log.e(TAG, str + " UID " + i + " session " + i2 + " does not exist in map, abort");
                return false;
            }
            packageSessions.mSessions.remove(java.lang.Integer.valueOf(i2));
        }
        if (packageSessions.mSessions == null || packageSessions.mSessions.size() == 0) {
            this.mClientUidSessionMap.remove(java.lang.Integer.valueOf(i).intValue());
            return true;
        }
        this.mClientUidSessionMap.put(java.lang.Integer.valueOf(i).intValue(), packageSessions);
        return true;
    }

    @android.annotation.RequiresPermission(anyOf = {"android.permission.INTERACT_ACROSS_USERS_FULL", "android.permission.INTERACT_ACROSS_USERS", "android.permission.INTERACT_ACROSS_PROFILES"})
    private boolean setMusicFxServiceWithObserver(android.content.Intent intent, int i, java.lang.String str) {
        int intExtra = intent.getIntExtra("android.media.extra.AUDIO_SESSION", 0);
        if (intExtra == 0) {
            android.util.Log.e(TAG, str + " intent have no invalid audio session");
            return false;
        }
        synchronized (this.mClientUidMapLock) {
            try {
                if (intent.getAction().equals("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION")) {
                    return handleAudioEffectSessionOpen(i, str, intExtra);
                }
                return handleAudioEffectSessionClose(i, str, intExtra);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCurrentUserId() {
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            int i = android.app.ActivityManager.getService().getCurrentUser().id;
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (android.os.RemoteException e) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"})
    private void handleEffectClientUidGone(int i) {
        synchronized (this.mClientUidMapLock) {
            try {
                android.util.Log.d(TAG, "handle MSG_EFFECT_CLIENT_GONE uid: " + i + " mapSize: " + this.mClientUidSessionMap.size());
                com.android.server.audio.MusicFxHelper.PackageSessions packageSessions = this.mClientUidSessionMap.get(java.lang.Integer.valueOf(i).intValue());
                if (packageSessions != null) {
                    android.util.Log.i(TAG, "UID " + i + " gone, closing all sessions");
                    for (java.lang.Integer num : packageSessions.mSessions) {
                        android.content.Intent intent = new android.content.Intent("android.media.action.CLOSE_AUDIO_EFFECT_CONTROL_SESSION");
                        intent.putExtra("android.media.extra.PACKAGE_NAME", packageSessions.mPackageName);
                        intent.putExtra("android.media.extra.AUDIO_SESSION", num);
                        intent.addFlags(32);
                        intent.setPackage("com.android.musicfx");
                        this.mContext.sendBroadcastAsUser(intent, android.os.UserHandle.ALL);
                    }
                    this.mClientUidSessionMap.remove(java.lang.Integer.valueOf(i).intValue());
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @android.annotation.RequiresPermission(allOf = {"android.permission.INTERACT_ACROSS_USERS"})
    void handleMessage(android.os.Message message) {
        switch (message.what) {
            case MSG_EFFECT_CLIENT_GONE /* 1101 */:
                android.util.Log.w(TAG, " handle MSG_EFFECT_CLIENT_GONE");
                handleEffectClientUidGone(message.arg1);
                break;
            default:
                android.util.Log.e(TAG, "Unexpected msg to handle in MusicFxHelper: " + message.what);
                break;
        }
    }
}
