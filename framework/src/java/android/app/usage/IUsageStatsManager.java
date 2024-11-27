package android.app.usage;

/* loaded from: classes.dex */
public interface IUsageStatsManager extends android.os.IInterface {
    void clearBroadcastEvents(java.lang.String str, int i) throws android.os.RemoteException;

    void clearBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException;

    void forceUsageSourceSettingRead() throws android.os.RemoteException;

    int getAppMinStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    int getAppStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice getAppStandbyBuckets(java.lang.String str, int i) throws android.os.RemoteException;

    java.lang.String getAppStandbyConstant(java.lang.String str) throws android.os.RemoteException;

    long getLastTimeAnyComponentUsed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    int getUsageSource() throws android.os.RemoteException;

    boolean isAppInactive(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException;

    boolean isAppStandbyEnabled() throws android.os.RemoteException;

    boolean isPackageExemptedFromBroadcastResponseStats(java.lang.String str, int i) throws android.os.RemoteException;

    void onCarrierPrivilegedAppsChanged() throws android.os.RemoteException;

    android.app.usage.BroadcastResponseStatsList queryBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryConfigurationStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryEventStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException;

    android.app.usage.UsageEvents queryEvents(long j, long j2, java.lang.String str) throws android.os.RemoteException;

    android.app.usage.UsageEvents queryEventsForPackage(long j, long j2, java.lang.String str) throws android.os.RemoteException;

    android.app.usage.UsageEvents queryEventsForPackageForUser(long j, long j2, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    android.app.usage.UsageEvents queryEventsForUser(long j, long j2, int i, java.lang.String str) throws android.os.RemoteException;

    android.app.usage.UsageEvents queryEventsWithFilter(android.app.usage.UsageEventsQuery usageEventsQuery, java.lang.String str) throws android.os.RemoteException;

    android.content.pm.ParceledListSlice queryUsageStats(int i, long j, long j2, java.lang.String str, int i2) throws android.os.RemoteException;

    void registerAppUsageLimitObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException;

    void registerAppUsageObserver(int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException;

    void registerUsageSessionObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, java.lang.String str) throws android.os.RemoteException;

    void reportChooserSelection(java.lang.String str, int i, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3) throws android.os.RemoteException;

    void reportPastUsageStart(android.os.IBinder iBinder, java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException;

    void reportUsageStart(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void reportUsageStop(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void reportUserInteraction(java.lang.String str, int i) throws android.os.RemoteException;

    void reportUserInteractionWithBundle(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException;

    void setAppInactive(java.lang.String str, boolean z, int i) throws android.os.RemoteException;

    void setAppStandbyBucket(java.lang.String str, int i, int i2) throws android.os.RemoteException;

    void setAppStandbyBuckets(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException;

    void setEstimatedLaunchTime(java.lang.String str, long j, int i) throws android.os.RemoteException;

    void setEstimatedLaunchTimes(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException;

    void unregisterAppUsageLimitObserver(int i, java.lang.String str) throws android.os.RemoteException;

    void unregisterAppUsageObserver(int i, java.lang.String str) throws android.os.RemoteException;

    void unregisterUsageSessionObserver(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.app.usage.IUsageStatsManager {
        @Override // android.app.usage.IUsageStatsManager
        public android.content.pm.ParceledListSlice queryUsageStats(int i, long j, long j2, java.lang.String str, int i2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.content.pm.ParceledListSlice queryConfigurationStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.content.pm.ParceledListSlice queryEventStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.UsageEvents queryEvents(long j, long j2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.UsageEvents queryEventsForPackage(long j, long j2, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.UsageEvents queryEventsForUser(long j, long j2, int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.UsageEvents queryEventsForPackageForUser(long j, long j2, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.UsageEvents queryEventsWithFilter(android.app.usage.UsageEventsQuery usageEventsQuery, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppInactive(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppStandbyEnabled() throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isAppInactive(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void onCarrierPrivilegedAppsChanged() throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportChooserSelection(java.lang.String str, int i, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBucket(java.lang.String str, int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.content.pm.ParceledListSlice getAppStandbyBuckets(java.lang.String str, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setAppStandbyBuckets(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getAppMinStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTime(java.lang.String str, long j, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void setEstimatedLaunchTimes(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageObserver(int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageObserver(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerUsageSessionObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterUsageSessionObserver(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void registerAppUsageLimitObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void unregisterAppUsageLimitObserver(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStart(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportPastUsageStart(android.os.IBinder iBinder, java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUsageStop(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteraction(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void reportUserInteractionWithBundle(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public int getUsageSource() throws android.os.RemoteException {
            return 0;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void forceUsageSourceSettingRead() throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public long getLastTimeAnyComponentUsed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            return 0L;
        }

        @Override // android.app.usage.IUsageStatsManager
        public android.app.usage.BroadcastResponseStatsList queryBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public void clearBroadcastEvents(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.app.usage.IUsageStatsManager
        public boolean isPackageExemptedFromBroadcastResponseStats(java.lang.String str, int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.usage.IUsageStatsManager
        public java.lang.String getAppStandbyConstant(java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.usage.IUsageStatsManager {
        public static final java.lang.String DESCRIPTOR = "android.app.usage.IUsageStatsManager";
        static final int TRANSACTION_clearBroadcastEvents = 37;
        static final int TRANSACTION_clearBroadcastResponseStats = 36;
        static final int TRANSACTION_forceUsageSourceSettingRead = 33;
        static final int TRANSACTION_getAppMinStandbyBucket = 18;
        static final int TRANSACTION_getAppStandbyBucket = 14;
        static final int TRANSACTION_getAppStandbyBuckets = 16;
        static final int TRANSACTION_getAppStandbyConstant = 39;
        static final int TRANSACTION_getLastTimeAnyComponentUsed = 34;
        static final int TRANSACTION_getUsageSource = 32;
        static final int TRANSACTION_isAppInactive = 11;
        static final int TRANSACTION_isAppStandbyEnabled = 10;
        static final int TRANSACTION_isPackageExemptedFromBroadcastResponseStats = 38;
        static final int TRANSACTION_onCarrierPrivilegedAppsChanged = 12;
        static final int TRANSACTION_queryBroadcastResponseStats = 35;
        static final int TRANSACTION_queryConfigurationStats = 2;
        static final int TRANSACTION_queryEventStats = 3;
        static final int TRANSACTION_queryEvents = 4;
        static final int TRANSACTION_queryEventsForPackage = 5;
        static final int TRANSACTION_queryEventsForPackageForUser = 7;
        static final int TRANSACTION_queryEventsForUser = 6;
        static final int TRANSACTION_queryEventsWithFilter = 8;
        static final int TRANSACTION_queryUsageStats = 1;
        static final int TRANSACTION_registerAppUsageLimitObserver = 25;
        static final int TRANSACTION_registerAppUsageObserver = 21;
        static final int TRANSACTION_registerUsageSessionObserver = 23;
        static final int TRANSACTION_reportChooserSelection = 13;
        static final int TRANSACTION_reportPastUsageStart = 28;
        static final int TRANSACTION_reportUsageStart = 27;
        static final int TRANSACTION_reportUsageStop = 29;
        static final int TRANSACTION_reportUserInteraction = 30;
        static final int TRANSACTION_reportUserInteractionWithBundle = 31;
        static final int TRANSACTION_setAppInactive = 9;
        static final int TRANSACTION_setAppStandbyBucket = 15;
        static final int TRANSACTION_setAppStandbyBuckets = 17;
        static final int TRANSACTION_setEstimatedLaunchTime = 19;
        static final int TRANSACTION_setEstimatedLaunchTimes = 20;
        static final int TRANSACTION_unregisterAppUsageLimitObserver = 26;
        static final int TRANSACTION_unregisterAppUsageObserver = 22;
        static final int TRANSACTION_unregisterUsageSessionObserver = 24;
        private final android.os.PermissionEnforcer mEnforcer;

        public Stub(android.os.PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new java.lang.IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @java.lang.Deprecated
        public Stub() {
            this(android.os.PermissionEnforcer.fromContext(android.app.ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static android.app.usage.IUsageStatsManager asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.usage.IUsageStatsManager)) {
                return (android.app.usage.IUsageStatsManager) queryLocalInterface;
            }
            return new android.app.usage.IUsageStatsManager.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "queryUsageStats";
                case 2:
                    return "queryConfigurationStats";
                case 3:
                    return "queryEventStats";
                case 4:
                    return "queryEvents";
                case 5:
                    return "queryEventsForPackage";
                case 6:
                    return "queryEventsForUser";
                case 7:
                    return "queryEventsForPackageForUser";
                case 8:
                    return "queryEventsWithFilter";
                case 9:
                    return "setAppInactive";
                case 10:
                    return "isAppStandbyEnabled";
                case 11:
                    return "isAppInactive";
                case 12:
                    return "onCarrierPrivilegedAppsChanged";
                case 13:
                    return "reportChooserSelection";
                case 14:
                    return "getAppStandbyBucket";
                case 15:
                    return "setAppStandbyBucket";
                case 16:
                    return "getAppStandbyBuckets";
                case 17:
                    return "setAppStandbyBuckets";
                case 18:
                    return "getAppMinStandbyBucket";
                case 19:
                    return "setEstimatedLaunchTime";
                case 20:
                    return "setEstimatedLaunchTimes";
                case 21:
                    return "registerAppUsageObserver";
                case 22:
                    return "unregisterAppUsageObserver";
                case 23:
                    return "registerUsageSessionObserver";
                case 24:
                    return "unregisterUsageSessionObserver";
                case 25:
                    return "registerAppUsageLimitObserver";
                case 26:
                    return "unregisterAppUsageLimitObserver";
                case 27:
                    return "reportUsageStart";
                case 28:
                    return "reportPastUsageStart";
                case 29:
                    return "reportUsageStop";
                case 30:
                    return "reportUserInteraction";
                case 31:
                    return "reportUserInteractionWithBundle";
                case 32:
                    return "getUsageSource";
                case 33:
                    return "forceUsageSourceSettingRead";
                case 34:
                    return "getLastTimeAnyComponentUsed";
                case 35:
                    return "queryBroadcastResponseStats";
                case 36:
                    return "clearBroadcastResponseStats";
                case 37:
                    return "clearBroadcastEvents";
                case 38:
                    return "isPackageExemptedFromBroadcastResponseStats";
                case 39:
                    return "getAppStandbyConstant";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryUsageStats = queryUsageStats(readInt, readLong, readLong2, readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryUsageStats, 1);
                    return true;
                case 2:
                    int readInt3 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryConfigurationStats = queryConfigurationStats(readInt3, readLong3, readLong4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryConfigurationStats, 1);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    long readLong6 = parcel.readLong();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice queryEventStats = queryEventStats(readInt4, readLong5, readLong6, readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventStats, 1);
                    return true;
                case 4:
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    java.lang.String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.UsageEvents queryEvents = queryEvents(readLong7, readLong8, readString4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEvents, 1);
                    return true;
                case 5:
                    long readLong9 = parcel.readLong();
                    long readLong10 = parcel.readLong();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.UsageEvents queryEventsForPackage = queryEventsForPackage(readLong9, readLong10, readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForPackage, 1);
                    return true;
                case 6:
                    long readLong11 = parcel.readLong();
                    long readLong12 = parcel.readLong();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.UsageEvents queryEventsForUser = queryEventsForUser(readLong11, readLong12, readInt5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForUser, 1);
                    return true;
                case 7:
                    long readLong13 = parcel.readLong();
                    long readLong14 = parcel.readLong();
                    int readInt6 = parcel.readInt();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.UsageEvents queryEventsForPackageForUser = queryEventsForPackageForUser(readLong13, readLong14, readInt6, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsForPackageForUser, 1);
                    return true;
                case 8:
                    android.app.usage.UsageEventsQuery usageEventsQuery = (android.app.usage.UsageEventsQuery) parcel.readTypedObject(android.app.usage.UsageEventsQuery.CREATOR);
                    java.lang.String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.app.usage.UsageEvents queryEventsWithFilter = queryEventsWithFilter(usageEventsQuery, readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryEventsWithFilter, 1);
                    return true;
                case 9:
                    java.lang.String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppInactive(readString10, readBoolean, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean isAppStandbyEnabled = isAppStandbyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppStandbyEnabled);
                    return true;
                case 11:
                    java.lang.String readString11 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAppInactive = isAppInactive(readString11, readInt8, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAppInactive);
                    return true;
                case 12:
                    onCarrierPrivilegedAppsChanged();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    java.lang.String readString13 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    java.lang.String readString14 = parcel.readString();
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    java.lang.String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportChooserSelection(readString13, readInt9, readString14, createStringArray, readString15);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    java.lang.String readString16 = parcel.readString();
                    java.lang.String readString17 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appStandbyBucket = getAppStandbyBucket(readString16, readString17, readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(appStandbyBucket);
                    return true;
                case 15:
                    java.lang.String readString18 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBucket(readString18, readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    java.lang.String readString19 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.content.pm.ParceledListSlice appStandbyBuckets = getAppStandbyBuckets(readString19, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appStandbyBuckets, 1);
                    return true;
                case 17:
                    android.content.pm.ParceledListSlice parceledListSlice = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setAppStandbyBuckets(parceledListSlice, readInt14);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    java.lang.String readString20 = parcel.readString();
                    java.lang.String readString21 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appMinStandbyBucket = getAppMinStandbyBucket(readString20, readString21, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeInt(appMinStandbyBucket);
                    return true;
                case 19:
                    java.lang.String readString22 = parcel.readString();
                    long readLong15 = parcel.readLong();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTime(readString22, readLong15, readInt16);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.content.pm.ParceledListSlice parceledListSlice2 = (android.content.pm.ParceledListSlice) parcel.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEstimatedLaunchTimes(parceledListSlice2, readInt17);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    int readInt18 = parcel.readInt();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    long readLong16 = parcel.readLong();
                    android.app.PendingIntent pendingIntent = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    java.lang.String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageObserver(readInt18, createStringArray2, readLong16, pendingIntent, readString23);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt19 = parcel.readInt();
                    java.lang.String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageObserver(readInt19, readString24);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    int readInt20 = parcel.readInt();
                    java.lang.String[] createStringArray3 = parcel.createStringArray();
                    long readLong17 = parcel.readLong();
                    long readLong18 = parcel.readLong();
                    android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    android.app.PendingIntent pendingIntent3 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    java.lang.String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerUsageSessionObserver(readInt20, createStringArray3, readLong17, readLong18, pendingIntent2, pendingIntent3, readString25);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt21 = parcel.readInt();
                    java.lang.String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterUsageSessionObserver(readInt21, readString26);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt22 = parcel.readInt();
                    java.lang.String[] createStringArray4 = parcel.createStringArray();
                    long readLong19 = parcel.readLong();
                    long readLong20 = parcel.readLong();
                    android.app.PendingIntent pendingIntent4 = (android.app.PendingIntent) parcel.readTypedObject(android.app.PendingIntent.CREATOR);
                    java.lang.String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerAppUsageLimitObserver(readInt22, createStringArray4, readLong19, readLong20, pendingIntent4, readString27);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    int readInt23 = parcel.readInt();
                    java.lang.String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterAppUsageLimitObserver(readInt23, readString28);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString29 = parcel.readString();
                    java.lang.String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStart(readStrongBinder, readString29, readString30);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    java.lang.String readString31 = parcel.readString();
                    long readLong21 = parcel.readLong();
                    java.lang.String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportPastUsageStart(readStrongBinder2, readString31, readLong21, readString32);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    android.os.IBinder readStrongBinder3 = parcel.readStrongBinder();
                    java.lang.String readString33 = parcel.readString();
                    java.lang.String readString34 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    reportUsageStop(readStrongBinder3, readString33, readString34);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    java.lang.String readString35 = parcel.readString();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    reportUserInteraction(readString35, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    java.lang.String readString36 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    android.os.PersistableBundle persistableBundle = (android.os.PersistableBundle) parcel.readTypedObject(android.os.PersistableBundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    reportUserInteractionWithBundle(readString36, readInt25, persistableBundle);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int usageSource = getUsageSource();
                    parcel2.writeNoException();
                    parcel2.writeInt(usageSource);
                    return true;
                case 33:
                    forceUsageSourceSettingRead();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    java.lang.String readString37 = parcel.readString();
                    java.lang.String readString38 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long lastTimeAnyComponentUsed = getLastTimeAnyComponentUsed(readString37, readString38);
                    parcel2.writeNoException();
                    parcel2.writeLong(lastTimeAnyComponentUsed);
                    return true;
                case 35:
                    java.lang.String readString39 = parcel.readString();
                    long readLong22 = parcel.readLong();
                    java.lang.String readString40 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.app.usage.BroadcastResponseStatsList queryBroadcastResponseStats = queryBroadcastResponseStats(readString39, readLong22, readString40, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryBroadcastResponseStats, 1);
                    return true;
                case 36:
                    java.lang.String readString41 = parcel.readString();
                    long readLong23 = parcel.readLong();
                    java.lang.String readString42 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastResponseStats(readString41, readLong23, readString42, readInt27);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    java.lang.String readString43 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearBroadcastEvents(readString43, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    java.lang.String readString44 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageExemptedFromBroadcastResponseStats = isPackageExemptedFromBroadcastResponseStats(readString44, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageExemptedFromBroadcastResponseStats);
                    return true;
                case 39:
                    java.lang.String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    java.lang.String appStandbyConstant = getAppStandbyConstant(readString45);
                    parcel2.writeNoException();
                    parcel2.writeString(appStandbyConstant);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.usage.IUsageStatsManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR;
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.content.pm.ParceledListSlice queryUsageStats(int i, long j, long j2, java.lang.String str, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.content.pm.ParceledListSlice queryConfigurationStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.content.pm.ParceledListSlice queryEventStats(int i, long j, long j2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.UsageEvents queryEvents(long j, long j2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.UsageEvents) obtain2.readTypedObject(android.app.usage.UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.UsageEvents queryEventsForPackage(long j, long j2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.UsageEvents) obtain2.readTypedObject(android.app.usage.UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.UsageEvents queryEventsForUser(long j, long j2, int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.UsageEvents) obtain2.readTypedObject(android.app.usage.UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.UsageEvents queryEventsForPackageForUser(long j, long j2, int i, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.UsageEvents) obtain2.readTypedObject(android.app.usage.UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.UsageEvents queryEventsWithFilter(android.app.usage.UsageEventsQuery usageEventsQuery, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(usageEventsQuery, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.UsageEvents) obtain2.readTypedObject(android.app.usage.UsageEvents.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppInactive(java.lang.String str, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppStandbyEnabled() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isAppInactive(java.lang.String str, int i, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void onCarrierPrivilegedAppsChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportChooserSelection(java.lang.String str, int i, java.lang.String str2, java.lang.String[] strArr, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    obtain.writeString(str3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBucket(java.lang.String str, int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.content.pm.ParceledListSlice getAppStandbyBuckets(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.content.pm.ParceledListSlice) obtain2.readTypedObject(android.content.pm.ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setAppStandbyBuckets(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getAppMinStandbyBucket(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTime(java.lang.String str, long j, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void setEstimatedLaunchTimes(android.content.pm.ParceledListSlice parceledListSlice, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageObserver(int i, java.lang.String[] strArr, long j, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageObserver(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerUsageSessionObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeTypedObject(pendingIntent2, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterUsageSessionObserver(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void registerAppUsageLimitObserver(int i, java.lang.String[] strArr, long j, long j2, android.app.PendingIntent pendingIntent, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringArray(strArr);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void unregisterAppUsageLimitObserver(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStart(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportPastUsageStart(android.os.IBinder iBinder, java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUsageStop(android.os.IBinder iBinder, java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteraction(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void reportUserInteractionWithBundle(java.lang.String str, int i, android.os.PersistableBundle persistableBundle) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(persistableBundle, 0);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public int getUsageSource() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void forceUsageSourceSettingRead() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public long getLastTimeAnyComponentUsed(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public android.app.usage.BroadcastResponseStatsList queryBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.usage.BroadcastResponseStatsList) obtain2.readTypedObject(android.app.usage.BroadcastResponseStatsList.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastResponseStats(java.lang.String str, long j, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public void clearBroadcastEvents(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public boolean isPackageExemptedFromBroadcastResponseStats(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.usage.IUsageStatsManager
            public java.lang.String getAppStandbyConstant(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.usage.IUsageStatsManager.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void setAppStandbyBucket_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setAppStandbyBuckets_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CHANGE_APP_IDLE_STATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTime_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }

        protected void setEstimatedLaunchTimes_enforcePermission() throws java.lang.SecurityException {
            this.mEnforcer.enforcePermission(android.Manifest.permission.CHANGE_APP_LAUNCH_TIME_ESTIMATE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
