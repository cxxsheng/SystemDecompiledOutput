package android.content.pm;

/* loaded from: classes.dex */
public class ShortcutManager {
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;
    private static final java.lang.String TAG = "ShortcutManager";
    private final android.content.Context mContext;
    private final android.content.pm.IShortcutService mService;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ShortcutMatchFlags {
    }

    public ShortcutManager(android.content.Context context, android.content.pm.IShortcutService iShortcutService) {
        this.mContext = context;
        this.mService = iShortcutService;
    }

    public ShortcutManager(android.content.Context context) {
        this(context, android.content.pm.IShortcutService.Stub.asInterface(android.os.ServiceManager.getService("shortcut")));
    }

    public boolean setDynamicShortcuts(java.util.List<android.content.pm.ShortcutInfo> list) {
        try {
            return this.mService.setDynamicShortcuts(this.mContext.getPackageName(), new android.content.pm.ParceledListSlice(list), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.ShortcutInfo> getDynamicShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 2, injectMyUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.ShortcutInfo> getManifestShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 1, injectMyUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.ShortcutInfo> getShortcuts(int i) {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), i, injectMyUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean addDynamicShortcuts(java.util.List<android.content.pm.ShortcutInfo> list) {
        try {
            return this.mService.addDynamicShortcuts(this.mContext.getPackageName(), new android.content.pm.ParceledListSlice(list), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeDynamicShortcuts(java.util.List<java.lang.String> list) {
        try {
            this.mService.removeDynamicShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeAllDynamicShortcuts() {
        try {
            this.mService.removeAllDynamicShortcuts(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void removeLongLivedShortcuts(java.util.List<java.lang.String> list) {
        try {
            this.mService.removeLongLivedShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public java.util.List<android.content.pm.ShortcutInfo> getPinnedShortcuts() {
        try {
            return this.mService.getShortcuts(this.mContext.getPackageName(), 4, injectMyUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean updateShortcuts(java.util.List<android.content.pm.ShortcutInfo> list) {
        try {
            return this.mService.updateShortcuts(this.mContext.getPackageName(), new android.content.pm.ParceledListSlice(list), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(java.util.List<java.lang.String> list) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, 0, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(java.util.List<java.lang.String> list, int i) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, null, i, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void disableShortcuts(java.util.List<java.lang.String> list, java.lang.String str) {
        disableShortcuts(list, (java.lang.CharSequence) str);
    }

    public void disableShortcuts(java.util.List<java.lang.String> list, java.lang.CharSequence charSequence) {
        try {
            this.mService.disableShortcuts(this.mContext.getPackageName(), list, charSequence, 0, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void enableShortcuts(java.util.List<java.lang.String> list) {
        try {
            this.mService.enableShortcuts(this.mContext.getPackageName(), list, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getMaxShortcutCountForActivity() {
        return getMaxShortcutCountPerActivity();
    }

    public int getMaxShortcutCountPerActivity() {
        try {
            return this.mService.getMaxShortcutCountPerActivity(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getRemainingCallCount() {
        try {
            return this.mService.getRemainingCallCount(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public long getRateLimitResetTime() {
        try {
            return this.mService.getRateLimitResetTime(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRateLimitingActive() {
        try {
            return this.mService.getRemainingCallCount(this.mContext.getPackageName(), injectMyUserId()) == 0;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIconMaxWidth() {
        try {
            return this.mService.getIconMaxDimensions(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public int getIconMaxHeight() {
        try {
            return this.mService.getIconMaxDimensions(this.mContext.getPackageName(), injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportShortcutUsed(java.lang.String str) {
        try {
            this.mService.reportShortcutUsed(this.mContext.getPackageName(), str, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isRequestPinShortcutSupported() {
        try {
            return this.mService.isRequestPinItemSupported(injectMyUserId(), 1);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean requestPinShortcut(android.content.pm.ShortcutInfo shortcutInfo, android.content.IntentSender intentSender) {
        try {
            com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture = new com.android.internal.infra.AndroidFuture<>();
            this.mService.requestPinShortcut(this.mContext.getPackageName(), shortcutInfo, intentSender, injectMyUserId(), androidFuture);
            return java.lang.Boolean.parseBoolean((java.lang.String) getFutureOrThrow(androidFuture));
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public android.content.Intent createShortcutResultIntent(android.content.pm.ShortcutInfo shortcutInfo) {
        com.android.internal.infra.AndroidFuture<android.content.Intent> androidFuture = new com.android.internal.infra.AndroidFuture<>();
        try {
            this.mService.createShortcutResultIntent(this.mContext.getPackageName(), shortcutInfo, injectMyUserId(), androidFuture);
            android.content.Intent intent = (android.content.Intent) getFutureOrThrow(androidFuture);
            if (intent != null) {
                intent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
            }
            return intent;
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void onApplicationActive(java.lang.String str, int i) {
        try {
            this.mService.onApplicationActive(str, i);
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    protected int injectMyUserId() {
        return this.mContext.getUserId();
    }

    @android.annotation.SystemApi
    public java.util.List<android.content.pm.ShortcutManager.ShareShortcutInfo> getShareTargets(android.content.IntentFilter intentFilter) {
        try {
            return this.mService.getShareTargets(this.mContext.getPackageName(), intentFilter, injectMyUserId()).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    public static final class ShareShortcutInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.content.pm.ShortcutManager.ShareShortcutInfo> CREATOR = new android.os.Parcelable.Creator<android.content.pm.ShortcutManager.ShareShortcutInfo>() { // from class: android.content.pm.ShortcutManager.ShareShortcutInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.ShortcutManager.ShareShortcutInfo createFromParcel(android.os.Parcel parcel) {
                return new android.content.pm.ShortcutManager.ShareShortcutInfo(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.content.pm.ShortcutManager.ShareShortcutInfo[] newArray(int i) {
                return new android.content.pm.ShortcutManager.ShareShortcutInfo[i];
            }
        };
        private final android.content.pm.ShortcutInfo mShortcutInfo;
        private final android.content.ComponentName mTargetComponent;

        public ShareShortcutInfo(android.content.pm.ShortcutInfo shortcutInfo, android.content.ComponentName componentName) {
            if (shortcutInfo == null) {
                throw new java.lang.NullPointerException("shortcut info is null");
            }
            if (componentName == null) {
                throw new java.lang.NullPointerException("target component is null");
            }
            this.mShortcutInfo = shortcutInfo;
            this.mTargetComponent = componentName;
        }

        private ShareShortcutInfo(android.os.Parcel parcel) {
            this.mShortcutInfo = (android.content.pm.ShortcutInfo) parcel.readParcelable(android.content.pm.ShortcutInfo.class.getClassLoader(), android.content.pm.ShortcutInfo.class);
            this.mTargetComponent = (android.content.ComponentName) parcel.readParcelable(android.content.ComponentName.class.getClassLoader(), android.content.ComponentName.class);
        }

        public android.content.pm.ShortcutInfo getShortcutInfo() {
            return this.mShortcutInfo;
        }

        public android.content.ComponentName getTargetComponent() {
            return this.mTargetComponent;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeParcelable(this.mShortcutInfo, i);
            parcel.writeParcelable(this.mTargetComponent, i);
        }
    }

    @android.annotation.SystemApi
    public boolean hasShareTargets(java.lang.String str) {
        try {
            return this.mService.hasShareTargets(this.mContext.getPackageName(), str, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void pushDynamicShortcut(android.content.pm.ShortcutInfo shortcutInfo) {
        try {
            this.mService.pushDynamicShortcut(this.mContext.getPackageName(), shortcutInfo, injectMyUserId());
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static <T> T getFutureOrThrow(com.android.internal.infra.AndroidFuture<T> androidFuture) {
        try {
            return androidFuture.get();
        } catch (java.lang.Throwable th) {
            th = th;
            if (th instanceof java.util.concurrent.ExecutionException) {
                th = th.getCause();
            }
            if (th instanceof java.lang.RuntimeException) {
                throw ((java.lang.RuntimeException) th);
            }
            if (th instanceof java.lang.Error) {
                throw ((java.lang.Error) th);
            }
            throw new java.lang.RuntimeException(th);
        }
    }
}
