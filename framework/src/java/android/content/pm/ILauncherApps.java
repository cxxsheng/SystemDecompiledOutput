package android.content.pm;

/* loaded from: classes.dex */
public interface ILauncherApps extends android.os.IInterface {
    void addOnAppsChangedListener(java.lang.String str, android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException;

    void cacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException;

    android.app.PendingIntent getActivityLaunchIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfoInternal> getActivityOverrides(java.lang.String str, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAllSessions(java.lang.String str) throws android.os.RemoteException;

    android.content.IntentSender getAppMarketActivityIntent(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.LauncherApps.AppUsageLimit getAppUsageLimit(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, java.lang.String str2, int i, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getLauncherActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.LauncherUserInfo getLauncherUserInfo(android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.util.List<java.lang.String> getPreInstalledSystemPackages(android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.IntentSender getPrivateSpaceSettingsIntent() throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getShortcutConfigActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.IntentSender getShortcutConfigActivityIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    int getShortcutIconResId(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    java.lang.String getShortcutIconUri(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException;

    android.app.PendingIntent getShortcutIntent(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void getShortcutsAsync(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) throws android.os.RemoteException;

    android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    java.util.List<android.os.UserHandle> getUserProfiles() throws android.os.RemoteException;

    boolean hasShortcutHostPermission(java.lang.String str) throws android.os.RemoteException;

    boolean isActivityEnabled(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean isPackageEnabled(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void pinShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void registerDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException;

    void registerPackageInstallerCallback(java.lang.String str, android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException;

    void registerShortcutChangeCallback(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException;

    void removeOnAppsChangedListener(android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException;

    android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void setArchiveCompatibilityOptions(boolean z, boolean z2) throws android.os.RemoteException;

    boolean shouldHideFromSuggestions(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void showAppDetailsAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException;

    void startSessionDetailsActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.pm.PackageInstaller.SessionInfo sessionInfo, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException;

    boolean startShortcut(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i) throws android.os.RemoteException;

    void unRegisterDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException;

    void uncacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException;

    void unregisterShortcutChangeCallback(java.lang.String str, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException;

    public static class Default implements android.content.pm.ILauncherApps {
        @Override // android.content.pm.ILauncherApps
        public void addOnAppsChangedListener(java.lang.String str, android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void removeOnAppsChangedListener(android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.ParceledListSlice getLauncherActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void startSessionDetailsActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.pm.PackageInstaller.SessionInfo sessionInfo, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public android.app.PendingIntent getActivityLaunchIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.LauncherUserInfo getLauncherUserInfo(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public java.util.List<java.lang.String> getPreInstalledSystemPackages(android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.IntentSender getAppMarketActivityIntent(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.IntentSender getPrivateSpaceSettingsIntent() throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void showAppDetailsAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public boolean isPackageEnabled(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean isActivityEnabled(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, java.lang.String str2, int i, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.LauncherApps.AppUsageLimit getAppUsageLimit(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void getShortcutsAsync(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void pinShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public boolean startShortcut(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public int getShortcutIconResId(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.content.pm.ILauncherApps
        public android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean hasShortcutHostPermission(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public boolean shouldHideFromSuggestions(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return false;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.ParceledListSlice getShortcutConfigActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.IntentSender getShortcutConfigActivityIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public android.app.PendingIntent getShortcutIntent(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void registerPackageInstallerCallback(java.lang.String str, android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public android.content.pm.ParceledListSlice getAllSessions(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void registerShortcutChangeCallback(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void unregisterShortcutChangeCallback(java.lang.String str, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void cacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void uncacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public java.lang.String getShortcutIconUri(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfoInternal> getActivityOverrides(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.content.pm.ILauncherApps
        public void registerDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void unRegisterDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public void setArchiveCompatibilityOptions(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.content.pm.ILauncherApps
        public java.util.List<android.os.UserHandle> getUserProfiles() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.content.pm.ILauncherApps {
        public static final java.lang.String DESCRIPTOR = "android.content.pm.ILauncherApps";
        static final int TRANSACTION_addOnAppsChangedListener = 1;
        static final int TRANSACTION_cacheShortcuts = 33;
        static final int TRANSACTION_getActivityLaunchIntent = 7;
        static final int TRANSACTION_getActivityOverrides = 36;
        static final int TRANSACTION_getAllSessions = 30;
        static final int TRANSACTION_getAppMarketActivityIntent = 10;
        static final int TRANSACTION_getAppUsageLimit = 17;
        static final int TRANSACTION_getApplicationInfo = 16;
        static final int TRANSACTION_getLauncherActivities = 3;
        static final int TRANSACTION_getLauncherUserInfo = 8;
        static final int TRANSACTION_getPreInstalledSystemPackages = 9;
        static final int TRANSACTION_getPrivateSpaceSettingsIntent = 11;
        static final int TRANSACTION_getShortcutConfigActivities = 26;
        static final int TRANSACTION_getShortcutConfigActivityIntent = 27;
        static final int TRANSACTION_getShortcutIconFd = 23;
        static final int TRANSACTION_getShortcutIconResId = 22;
        static final int TRANSACTION_getShortcutIconUri = 35;
        static final int TRANSACTION_getShortcutIntent = 28;
        static final int TRANSACTION_getShortcuts = 18;
        static final int TRANSACTION_getShortcutsAsync = 19;
        static final int TRANSACTION_getSuspendedPackageLauncherExtras = 14;
        static final int TRANSACTION_getUserProfiles = 40;
        static final int TRANSACTION_hasShortcutHostPermission = 24;
        static final int TRANSACTION_isActivityEnabled = 15;
        static final int TRANSACTION_isPackageEnabled = 13;
        static final int TRANSACTION_pinShortcuts = 20;
        static final int TRANSACTION_registerDumpCallback = 37;
        static final int TRANSACTION_registerPackageInstallerCallback = 29;
        static final int TRANSACTION_registerShortcutChangeCallback = 31;
        static final int TRANSACTION_removeOnAppsChangedListener = 2;
        static final int TRANSACTION_resolveLauncherActivityInternal = 4;
        static final int TRANSACTION_setArchiveCompatibilityOptions = 39;
        static final int TRANSACTION_shouldHideFromSuggestions = 25;
        static final int TRANSACTION_showAppDetailsAsUser = 12;
        static final int TRANSACTION_startActivityAsUser = 6;
        static final int TRANSACTION_startSessionDetailsActivityAsUser = 5;
        static final int TRANSACTION_startShortcut = 21;
        static final int TRANSACTION_unRegisterDumpCallback = 38;
        static final int TRANSACTION_uncacheShortcuts = 34;
        static final int TRANSACTION_unregisterShortcutChangeCallback = 32;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.content.pm.ILauncherApps asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.content.pm.ILauncherApps)) {
                return (android.content.pm.ILauncherApps) queryLocalInterface;
            }
            return new android.content.pm.ILauncherApps.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addOnAppsChangedListener";
                case 2:
                    return "removeOnAppsChangedListener";
                case 3:
                    return "getLauncherActivities";
                case 4:
                    return "resolveLauncherActivityInternal";
                case 5:
                    return "startSessionDetailsActivityAsUser";
                case 6:
                    return "startActivityAsUser";
                case 7:
                    return "getActivityLaunchIntent";
                case 8:
                    return "getLauncherUserInfo";
                case 9:
                    return "getPreInstalledSystemPackages";
                case 10:
                    return "getAppMarketActivityIntent";
                case 11:
                    return "getPrivateSpaceSettingsIntent";
                case 12:
                    return "showAppDetailsAsUser";
                case 13:
                    return "isPackageEnabled";
                case 14:
                    return "getSuspendedPackageLauncherExtras";
                case 15:
                    return "isActivityEnabled";
                case 16:
                    return "getApplicationInfo";
                case 17:
                    return "getAppUsageLimit";
                case 18:
                    return "getShortcuts";
                case 19:
                    return "getShortcutsAsync";
                case 20:
                    return "pinShortcuts";
                case 21:
                    return "startShortcut";
                case 22:
                    return "getShortcutIconResId";
                case 23:
                    return "getShortcutIconFd";
                case 24:
                    return "hasShortcutHostPermission";
                case 25:
                    return "shouldHideFromSuggestions";
                case 26:
                    return "getShortcutConfigActivities";
                case 27:
                    return "getShortcutConfigActivityIntent";
                case 28:
                    return "getShortcutIntent";
                case 29:
                    return "registerPackageInstallerCallback";
                case 30:
                    return "getAllSessions";
                case 31:
                    return "registerShortcutChangeCallback";
                case 32:
                    return "unregisterShortcutChangeCallback";
                case 33:
                    return "cacheShortcuts";
                case 34:
                    return "uncacheShortcuts";
                case 35:
                    return "getShortcutIconUri";
                case 36:
                    return "getActivityOverrides";
                case 37:
                    return "registerDumpCallback";
                case 38:
                    return "unRegisterDumpCallback";
                case 39:
                    return "setArchiveCompatibilityOptions";
                case 40:
                    return "getUserProfiles";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, final android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    java.lang.String readString = parcel.readString();
                    android.content.pm.IOnAppsChangedListener asInterface = android.content.pm.IOnAppsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addOnAppsChangedListener(readString, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    android.content.pm.IOnAppsChangedListener asInterface2 = android.content.pm.IOnAppsChangedListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeOnAppsChangedListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString2 = parcel.readString();
                    java.lang.String readString3 = parcel.readString();
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice launcherActivities = getLauncherActivities(readString2, readString3, userHandle);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launcherActivities, 1);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    android.content.ComponentName componentName = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal = resolveLauncherActivityInternal(readString4, componentName, userHandle2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(resolveLauncherActivityInternal, 1);
                    return true;
                case 5:
                    android.app.IApplicationThread asInterface3 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString5 = parcel.readString();
                    java.lang.String readString6 = parcel.readString();
                    android.content.pm.PackageInstaller.SessionInfo sessionInfo = (android.content.pm.PackageInstaller.SessionInfo) parcel.readTypedObject(android.content.pm.PackageInstaller.SessionInfo.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startSessionDetailsActivityAsUser(asInterface3, readString5, readString6, sessionInfo, rect, bundle, userHandle3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.app.IApplicationThread asInterface4 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    android.content.ComponentName componentName2 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.os.Bundle bundle2 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.UserHandle userHandle4 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUser(asInterface4, readString7, readString8, componentName2, rect2, bundle2, userHandle4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    java.lang.String readString9 = parcel.readString();
                    android.content.ComponentName componentName3 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle5 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent activityLaunchIntent = getActivityLaunchIntent(readString9, componentName3, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activityLaunchIntent, 1);
                    return true;
                case 8:
                    android.os.UserHandle userHandle6 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.LauncherUserInfo launcherUserInfo = getLauncherUserInfo(userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(launcherUserInfo, 1);
                    return true;
                case 9:
                    android.os.UserHandle userHandle7 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    java.util.List<java.lang.String> preInstalledSystemPackages = getPreInstalledSystemPackages(userHandle7);
                    parcel2.writeNoException();
                    parcel2.writeStringList(preInstalledSystemPackages);
                    return true;
                case 10:
                    java.lang.String readString10 = parcel.readString();
                    java.lang.String readString11 = parcel.readString();
                    android.os.UserHandle userHandle8 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.IntentSender appMarketActivityIntent = getAppMarketActivityIntent(readString10, readString11, userHandle8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appMarketActivityIntent, 1);
                    return true;
                case 11:
                    android.content.IntentSender privateSpaceSettingsIntent = getPrivateSpaceSettingsIntent();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(privateSpaceSettingsIntent, 1);
                    return true;
                case 12:
                    android.app.IApplicationThread asInterface5 = android.app.IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString12 = parcel.readString();
                    java.lang.String readString13 = parcel.readString();
                    android.content.ComponentName componentName4 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.graphics.Rect rect3 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.os.Bundle bundle3 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.UserHandle userHandle9 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    showAppDetailsAsUser(asInterface5, readString12, readString13, componentName4, rect3, bundle3, userHandle9);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String readString15 = parcel.readString();
                    android.os.UserHandle userHandle10 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabled = isPackageEnabled(readString14, readString15, userHandle10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabled);
                    return true;
                case 14:
                    java.lang.String readString16 = parcel.readString();
                    android.os.UserHandle userHandle11 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.os.Bundle suspendedPackageLauncherExtras = getSuspendedPackageLauncherExtras(readString16, userHandle11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(suspendedPackageLauncherExtras, 1);
                    return true;
                case 15:
                    java.lang.String readString17 = parcel.readString();
                    android.content.ComponentName componentName5 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle12 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isActivityEnabled = isActivityEnabled(readString17, componentName5, userHandle12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isActivityEnabled);
                    return true;
                case 16:
                    java.lang.String readString18 = parcel.readString();
                    java.lang.String readString19 = parcel.readString();
                    int readInt = parcel.readInt();
                    android.os.UserHandle userHandle13 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ApplicationInfo applicationInfo = getApplicationInfo(readString18, readString19, readInt, userHandle13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(applicationInfo, 1);
                    return true;
                case 17:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    android.os.UserHandle userHandle14 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.LauncherApps.AppUsageLimit appUsageLimit = getAppUsageLimit(readString20, readString21, userHandle14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appUsageLimit, 1);
                    return true;
                case 18:
                    java.lang.String readString22 = parcel.readString();
                    android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper = (android.content.pm.ShortcutQueryWrapper) parcel.readTypedObject(android.content.pm.ShortcutQueryWrapper.CREATOR);
                    android.os.UserHandle userHandle15 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice shortcuts = getShortcuts(readString22, shortcutQueryWrapper, userHandle15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcuts, 1);
                    return true;
                case 19:
                    java.lang.String readString23 = parcel.readString();
                    android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper2 = (android.content.pm.ShortcutQueryWrapper) parcel.readTypedObject(android.content.pm.ShortcutQueryWrapper.CREATOR);
                    android.os.UserHandle userHandle16 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getShortcutsAsync(readString23, shortcutQueryWrapper2, userHandle16, androidFuture);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    java.lang.String readString24 = parcel.readString();
                    java.lang.String readString25 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    android.os.UserHandle userHandle17 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    pinShortcuts(readString24, readString25, createStringArrayList, userHandle17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    java.lang.String readString26 = parcel.readString();
                    java.lang.String readString27 = parcel.readString();
                    java.lang.String readString28 = parcel.readString();
                    java.lang.String readString29 = parcel.readString();
                    android.graphics.Rect rect4 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    android.os.Bundle bundle4 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean startShortcut = startShortcut(readString26, readString27, readString28, readString29, rect4, bundle4, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startShortcut);
                    return true;
                case 22:
                    java.lang.String readString30 = parcel.readString();
                    java.lang.String readString31 = parcel.readString();
                    java.lang.String readString32 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int shortcutIconResId = getShortcutIconResId(readString30, readString31, readString32, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(shortcutIconResId);
                    return true;
                case 23:
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    java.lang.String readString35 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.os.ParcelFileDescriptor shortcutIconFd = getShortcutIconFd(readString33, readString34, readString35, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIconFd, 1);
                    return true;
                case 24:
                    java.lang.String readString36 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean hasShortcutHostPermission = hasShortcutHostPermission(readString36);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasShortcutHostPermission);
                    return true;
                case 25:
                    java.lang.String readString37 = parcel.readString();
                    android.os.UserHandle userHandle18 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean shouldHideFromSuggestions = shouldHideFromSuggestions(readString37, userHandle18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHideFromSuggestions);
                    return true;
                case 26:
                    java.lang.String readString38 = parcel.readString();
                    java.lang.String readString39 = parcel.readString();
                    android.os.UserHandle userHandle19 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice shortcutConfigActivities = getShortcutConfigActivities(readString38, readString39, userHandle19);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutConfigActivities, 1);
                    return true;
                case 27:
                    java.lang.String readString40 = parcel.readString();
                    android.content.ComponentName componentName6 = (android.content.ComponentName) parcel.readTypedObject(android.content.ComponentName.CREATOR);
                    android.os.UserHandle userHandle20 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.content.IntentSender shortcutConfigActivityIntent = getShortcutConfigActivityIntent(readString40, componentName6, userHandle20);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutConfigActivityIntent, 1);
                    return true;
                case 28:
                    java.lang.String readString41 = parcel.readString();
                    java.lang.String readString42 = parcel.readString();
                    java.lang.String readString43 = parcel.readString();
                    android.os.Bundle bundle5 = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    android.os.UserHandle userHandle21 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.app.PendingIntent shortcutIntent = getShortcutIntent(readString41, readString42, readString43, bundle5, userHandle21);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(shortcutIntent, 1);
                    return true;
                case 29:
                    java.lang.String readString44 = parcel.readString();
                    android.content.pm.IPackageInstallerCallback asInterface6 = android.content.pm.IPackageInstallerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerPackageInstallerCallback(readString44, asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice allSessions = getAllSessions(readString45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allSessions, 1);
                    return true;
                case 31:
                    java.lang.String readString46 = parcel.readString();
                    android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper3 = (android.content.pm.ShortcutQueryWrapper) parcel.readTypedObject(android.content.pm.ShortcutQueryWrapper.CREATOR);
                    android.content.pm.IShortcutChangeCallback asInterface7 = android.content.pm.IShortcutChangeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerShortcutChangeCallback(readString46, shortcutQueryWrapper3, asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    java.lang.String readString47 = parcel.readString();
                    android.content.pm.IShortcutChangeCallback asInterface8 = android.content.pm.IShortcutChangeCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterShortcutChangeCallback(readString47, asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    java.lang.String readString48 = parcel.readString();
                    java.lang.String readString49 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    android.os.UserHandle userHandle22 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cacheShortcuts(readString48, readString49, createStringArrayList2, userHandle22, readInt5);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString50 = parcel.readString();
                    java.lang.String readString51 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList3 = parcel.createStringArrayList();
                    android.os.UserHandle userHandle23 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    uncacheShortcuts(readString50, readString51, createStringArrayList3, userHandle23, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    java.lang.String readString52 = parcel.readString();
                    java.lang.String readString53 = parcel.readString();
                    java.lang.String readString54 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.lang.String shortcutIconUri = getShortcutIconUri(readString52, readString53, readString54, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeString(shortcutIconUri);
                    return true;
                case 36:
                    java.lang.String readString55 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfoInternal> activityOverrides = getActivityOverrides(readString55, readInt8);
                    parcel2.writeNoException();
                    if (activityOverrides == null) {
                        parcel2.writeInt(-1);
                    } else {
                        parcel2.writeInt(activityOverrides.size());
                        activityOverrides.forEach(new java.util.function.BiConsumer() { // from class: android.content.pm.ILauncherApps$Stub$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.content.pm.ILauncherApps.Stub.lambda$onTransact$0(android.os.Parcel.this, (java.lang.String) obj, (android.content.pm.LauncherActivityInfoInternal) obj2);
                            }
                        });
                    }
                    return true;
                case 37:
                    android.window.IDumpCallback asInterface9 = android.window.IDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDumpCallback(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    android.window.IDumpCallback asInterface10 = android.window.IDumpCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unRegisterDumpCallback(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setArchiveCompatibilityOptions(readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 40:
                    java.util.List<android.os.UserHandle> userProfiles = getUserProfiles();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(userProfiles, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(android.os.Parcel parcel, java.lang.String str, android.content.pm.LauncherActivityInfoInternal launcherActivityInfoInternal) {
            parcel.writeString(str);
            parcel.writeTypedObject(launcherActivityInfoInternal, 1);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.content.pm.ILauncherApps {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.content.pm.ILauncherApps.Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.ILauncherApps
            public void addOnAppsChangedListener(java.lang.String str, android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iOnAppsChangedListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void removeOnAppsChangedListener(android.content.pm.IOnAppsChangedListener iOnAppsChangedListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iOnAppsChangedListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.ParceledListSlice getLauncherActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.LauncherActivityInfoInternal resolveLauncherActivityInternal(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.LauncherActivityInfoInternal) obtain2.readTypedObject(android.content.pm.LauncherActivityInfoInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void startSessionDetailsActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.pm.PackageInstaller.SessionInfo sessionInfo, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(sessionInfo, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void startActivityAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.app.PendingIntent getActivityLaunchIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.LauncherUserInfo getLauncherUserInfo(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.LauncherUserInfo) obtain2.readTypedObject(android.content.pm.LauncherUserInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public java.util.List<java.lang.String> getPreInstalledSystemPackages(android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.IntentSender getAppMarketActivityIntent(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.IntentSender getPrivateSpaceSettingsIntent() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void showAppDetailsAsUser(android.app.IApplicationThread iApplicationThread, java.lang.String str, java.lang.String str2, android.content.ComponentName componentName, android.graphics.Rect rect, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean isPackageEnabled(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.os.Bundle getSuspendedPackageLauncherExtras(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.Bundle) obtain2.readTypedObject(android.os.Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean isActivityEnabled(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.ApplicationInfo getApplicationInfo(java.lang.String str, java.lang.String str2, int i, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ApplicationInfo) obtain2.readTypedObject(android.content.pm.ApplicationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.LauncherApps.AppUsageLimit getAppUsageLimit(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.LauncherApps.AppUsageLimit) obtain2.readTypedObject(android.content.pm.LauncherApps.AppUsageLimit.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.ParceledListSlice getShortcuts(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutQueryWrapper, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void getShortcutsAsync(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture<java.util.List<android.content.pm.ShortcutInfo>> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutQueryWrapper, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void pinShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean startShortcut(java.lang.String str, java.lang.String str2, java.lang.String str3, java.lang.String str4, android.graphics.Rect rect, android.os.Bundle bundle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public int getShortcutIconResId(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.os.ParcelFileDescriptor getShortcutIconFd(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.ParcelFileDescriptor) obtain2.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean hasShortcutHostPermission(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public boolean shouldHideFromSuggestions(java.lang.String str, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.ParceledListSlice getShortcutConfigActivities(java.lang.String str, java.lang.String str2, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.IntentSender getShortcutConfigActivityIntent(java.lang.String str, android.content.ComponentName componentName, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.IntentSender) obtain2.readTypedObject(android.content.IntentSender.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.app.PendingIntent getShortcutIntent(java.lang.String str, java.lang.String str2, java.lang.String str3, android.os.Bundle bundle, android.os.UserHandle userHandle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.PendingIntent) obtain2.readTypedObject(android.app.PendingIntent.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerPackageInstallerCallback(java.lang.String str, android.content.pm.IPackageInstallerCallback iPackageInstallerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iPackageInstallerCallback);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public android.content.pm.ParceledListSlice getAllSessions(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerShortcutChangeCallback(java.lang.String str, android.content.pm.ShortcutQueryWrapper shortcutQueryWrapper, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(shortcutQueryWrapper, 0);
                    obtain.writeStrongInterface(iShortcutChangeCallback);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void unregisterShortcutChangeCallback(java.lang.String str, android.content.pm.IShortcutChangeCallback iShortcutChangeCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iShortcutChangeCallback);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void cacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void uncacheShortcuts(java.lang.String str, java.lang.String str2, java.util.List<java.lang.String> list, android.os.UserHandle userHandle, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringList(list);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public java.lang.String getShortcutIconUri(java.lang.String str, java.lang.String str2, java.lang.String str3, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public java.util.Map<java.lang.String, android.content.pm.LauncherActivityInfoInternal> getActivityOverrides(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                final android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.content.pm.ILauncherApps$Stub$Proxy$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i2) {
                            hashMap.put(r0.readString(), (android.content.pm.LauncherActivityInfoInternal) android.os.Parcel.this.readTypedObject(android.content.pm.LauncherActivityInfoInternal.CREATOR));
                        }
                    });
                    return hashMap;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void registerDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDumpCallback);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void unRegisterDumpCallback(android.window.IDumpCallback iDumpCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iDumpCallback);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public void setArchiveCompatibilityOptions(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ILauncherApps
            public java.util.List<android.os.UserHandle> getUserProfiles() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.content.pm.ILauncherApps.Stub.DESCRIPTOR);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.os.UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 39;
        }
    }
}
