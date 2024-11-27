package com.android.server.backup;

/* loaded from: classes.dex */
public class TransportManager {
    private static final boolean MORE_DEBUG = false;

    @com.android.internal.annotations.VisibleForTesting
    public static final java.lang.String SERVICE_ACTION_TRANSPORT_HOST = "android.backup.TRANSPORT_HOST";
    private static final java.lang.String TAG = "BackupTransportManager";

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    @android.annotation.Nullable
    private volatile java.lang.String mCurrentTransportName;
    private final android.content.pm.PackageManager mPackageManager;
    private final com.android.server.backup.transport.TransportConnectionManager mTransportConnectionManager;
    private final java.util.Set<android.content.ComponentName> mTransportWhitelist;
    private final int mUserId;
    private final android.content.Intent mTransportServiceIntent = new android.content.Intent(SERVICE_ACTION_TRANSPORT_HOST);
    private com.android.server.backup.transport.OnTransportRegisteredListener mOnTransportRegisteredListener = new com.android.server.backup.transport.OnTransportRegisteredListener() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda0
        @Override // com.android.server.backup.transport.OnTransportRegisteredListener
        public final void onTransportRegistered(java.lang.String str, java.lang.String str2) {
            com.android.server.backup.TransportManager.lambda$new$0(str, str2);
        }
    };
    private final java.lang.Object mTransportLock = new java.lang.Object();

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    private final java.util.Map<android.content.ComponentName, com.android.server.backup.TransportManager.TransportDescription> mRegisteredTransportsDescriptionMap = new android.util.ArrayMap();
    private final com.android.server.backup.transport.TransportStats mTransportStats = new com.android.server.backup.transport.TransportStats();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$0(java.lang.String str, java.lang.String str2) {
    }

    TransportManager(int i, android.content.Context context, java.util.Set<android.content.ComponentName> set, java.lang.String str) {
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
        this.mTransportWhitelist = (java.util.Set) com.android.internal.util.Preconditions.checkNotNull(set);
        this.mCurrentTransportName = str;
        this.mTransportConnectionManager = new com.android.server.backup.transport.TransportConnectionManager(this.mUserId, context, this.mTransportStats);
    }

    @com.android.internal.annotations.VisibleForTesting
    TransportManager(int i, android.content.Context context, java.util.Set<android.content.ComponentName> set, java.lang.String str, com.android.server.backup.transport.TransportConnectionManager transportConnectionManager) {
        this.mUserId = i;
        this.mPackageManager = context.getPackageManager();
        this.mTransportWhitelist = (java.util.Set) com.android.internal.util.Preconditions.checkNotNull(set);
        this.mCurrentTransportName = str;
        this.mTransportConnectionManager = transportConnectionManager;
    }

    public void setOnTransportRegisteredListener(com.android.server.backup.transport.OnTransportRegisteredListener onTransportRegisteredListener) {
        this.mOnTransportRegisteredListener = onTransportRegisteredListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$onPackageAdded$1(android.content.ComponentName componentName) {
        return true;
    }

    void onPackageAdded(java.lang.String str) {
        registerTransportsFromPackage(str, new java.util.function.Predicate() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$onPackageAdded$1;
                lambda$onPackageAdded$1 = com.android.server.backup.TransportManager.lambda$onPackageAdded$1((android.content.ComponentName) obj);
                return lambda$onPackageAdded$1;
            }
        });
    }

    void onPackageRemoved(java.lang.String str) {
        synchronized (this.mTransportLock) {
            this.mRegisteredTransportsDescriptionMap.keySet().removeIf(fromPackageFilter(str));
        }
    }

    void onPackageEnabled(java.lang.String str) {
        onPackageAdded(str);
    }

    void onPackageDisabled(java.lang.String str) {
        onPackageRemoved(str);
    }

    void onPackageChanged(java.lang.String str, java.lang.String... strArr) {
        if (strArr.length == 1 && strArr[0].equals(str)) {
            try {
                int applicationEnabledSetting = this.mPackageManager.getApplicationEnabledSetting(str);
                switch (applicationEnabledSetting) {
                    case 0:
                        onPackageEnabled(str);
                        return;
                    case 1:
                        onPackageEnabled(str);
                        return;
                    case 2:
                        onPackageDisabled(str);
                        return;
                    case 3:
                        onPackageDisabled(str);
                        return;
                    default:
                        android.util.Slog.w(TAG, addUserIdToLogMessage(this.mUserId, "Package " + str + " enabled setting: " + applicationEnabledSetting));
                        return;
                }
            } catch (java.lang.IllegalArgumentException e) {
                return;
            }
        }
        final android.util.ArraySet arraySet = new android.util.ArraySet(strArr.length);
        for (java.lang.String str2 : strArr) {
            arraySet.add(new android.content.ComponentName(str, str2));
        }
        if (arraySet.isEmpty()) {
            return;
        }
        synchronized (this.mTransportLock) {
            java.util.Set<android.content.ComponentName> keySet = this.mRegisteredTransportsDescriptionMap.keySet();
            java.util.Objects.requireNonNull(arraySet);
            keySet.removeIf(new java.util.function.Predicate() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(java.lang.Object obj) {
                    return arraySet.contains((android.content.ComponentName) obj);
                }
            });
        }
        java.util.Objects.requireNonNull(arraySet);
        registerTransportsFromPackage(str, new java.util.function.Predicate() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                return arraySet.contains((android.content.ComponentName) obj);
            }
        });
    }

    android.content.ComponentName[] getRegisteredTransportComponents() {
        android.content.ComponentName[] componentNameArr;
        synchronized (this.mTransportLock) {
            componentNameArr = (android.content.ComponentName[]) this.mRegisteredTransportsDescriptionMap.keySet().toArray(new android.content.ComponentName[this.mRegisteredTransportsDescriptionMap.size()]);
        }
        return componentNameArr;
    }

    java.lang.String[] getRegisteredTransportNames() {
        java.lang.String[] strArr;
        synchronized (this.mTransportLock) {
            try {
                strArr = new java.lang.String[this.mRegisteredTransportsDescriptionMap.size()];
                java.util.Iterator<com.android.server.backup.TransportManager.TransportDescription> it = this.mRegisteredTransportsDescriptionMap.values().iterator();
                int i = 0;
                while (it.hasNext()) {
                    strArr[i] = it.next().name;
                    i++;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return strArr;
    }

    java.util.Set<android.content.ComponentName> getTransportWhitelist() {
        return this.mTransportWhitelist;
    }

    @android.annotation.Nullable
    public java.lang.String getCurrentTransportName() {
        return this.mCurrentTransportName;
    }

    @android.annotation.Nullable
    public android.content.ComponentName getCurrentTransportComponent() throws com.android.server.backup.transport.TransportNotRegisteredException {
        synchronized (this.mTransportLock) {
            try {
                if (this.mCurrentTransportName == null) {
                    return null;
                }
                return getRegisteredTransportComponentOrThrowLocked(this.mCurrentTransportName);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public java.lang.String getTransportName(android.content.ComponentName componentName) throws com.android.server.backup.transport.TransportNotRegisteredException {
        java.lang.String str;
        synchronized (this.mTransportLock) {
            str = getRegisteredTransportDescriptionOrThrowLocked(componentName).name;
        }
        return str;
    }

    public java.lang.String getTransportDirName(android.content.ComponentName componentName) throws com.android.server.backup.transport.TransportNotRegisteredException {
        java.lang.String str;
        synchronized (this.mTransportLock) {
            str = getRegisteredTransportDescriptionOrThrowLocked(componentName).transportDirName;
        }
        return str;
    }

    public java.lang.String getTransportDirName(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        java.lang.String str2;
        synchronized (this.mTransportLock) {
            str2 = getRegisteredTransportDescriptionOrThrowLocked(str).transportDirName;
        }
        return str2;
    }

    @android.annotation.Nullable
    public android.content.Intent getTransportConfigurationIntent(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        android.content.Intent intent;
        synchronized (this.mTransportLock) {
            intent = getRegisteredTransportDescriptionOrThrowLocked(str).configurationIntent;
        }
        return intent;
    }

    public java.lang.String getTransportCurrentDestinationString(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        java.lang.String str2;
        synchronized (this.mTransportLock) {
            str2 = getRegisteredTransportDescriptionOrThrowLocked(str).currentDestinationString;
        }
        return str2;
    }

    @android.annotation.Nullable
    public android.content.Intent getTransportDataManagementIntent(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        android.content.Intent intent;
        synchronized (this.mTransportLock) {
            intent = getRegisteredTransportDescriptionOrThrowLocked(str).dataManagementIntent;
        }
        return intent;
    }

    @android.annotation.Nullable
    public java.lang.CharSequence getTransportDataManagementLabel(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        java.lang.CharSequence charSequence;
        synchronized (this.mTransportLock) {
            charSequence = getRegisteredTransportDescriptionOrThrowLocked(str).dataManagementLabel;
        }
        return charSequence;
    }

    public boolean isTransportRegistered(java.lang.String str) {
        boolean z;
        synchronized (this.mTransportLock) {
            z = getRegisteredTransportEntryLocked(str) != null;
        }
        return z;
    }

    public void forEachRegisteredTransport(java.util.function.Consumer<java.lang.String> consumer) {
        synchronized (this.mTransportLock) {
            try {
                java.util.Iterator<com.android.server.backup.TransportManager.TransportDescription> it = this.mRegisteredTransportsDescriptionMap.values().iterator();
                while (it.hasNext()) {
                    consumer.accept(it.next().name);
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    public void updateTransportAttributes(android.content.ComponentName componentName, java.lang.String str, @android.annotation.Nullable android.content.Intent intent, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent2, @android.annotation.Nullable java.lang.CharSequence charSequence) {
        synchronized (this.mTransportLock) {
            try {
                com.android.server.backup.TransportManager.TransportDescription transportDescription = this.mRegisteredTransportsDescriptionMap.get(componentName);
                if (transportDescription == null) {
                    android.util.Slog.e(TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered tried to change description"));
                    return;
                }
                transportDescription.name = str;
                transportDescription.configurationIntent = intent;
                transportDescription.currentDestinationString = str2;
                transportDescription.dataManagementIntent = intent2;
                transportDescription.dataManagementLabel = charSequence;
                android.util.Slog.d(TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " updated its attributes"));
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    private android.content.ComponentName getRegisteredTransportComponentOrThrowLocked(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        android.content.ComponentName registeredTransportComponentLocked = getRegisteredTransportComponentLocked(str);
        if (registeredTransportComponentLocked == null) {
            throw new com.android.server.backup.transport.TransportNotRegisteredException(str);
        }
        return registeredTransportComponentLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    private com.android.server.backup.TransportManager.TransportDescription getRegisteredTransportDescriptionOrThrowLocked(android.content.ComponentName componentName) throws com.android.server.backup.transport.TransportNotRegisteredException {
        com.android.server.backup.TransportManager.TransportDescription transportDescription = this.mRegisteredTransportsDescriptionMap.get(componentName);
        if (transportDescription == null) {
            throw new com.android.server.backup.transport.TransportNotRegisteredException(componentName);
        }
        return transportDescription;
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    private com.android.server.backup.TransportManager.TransportDescription getRegisteredTransportDescriptionOrThrowLocked(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        com.android.server.backup.TransportManager.TransportDescription registeredTransportDescriptionLocked = getRegisteredTransportDescriptionLocked(str);
        if (registeredTransportDescriptionLocked == null) {
            throw new com.android.server.backup.transport.TransportNotRegisteredException(str);
        }
        return registeredTransportDescriptionLocked;
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    @android.annotation.Nullable
    private android.content.ComponentName getRegisteredTransportComponentLocked(java.lang.String str) {
        java.util.Map.Entry<android.content.ComponentName, com.android.server.backup.TransportManager.TransportDescription> registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
        if (registeredTransportEntryLocked == null) {
            return null;
        }
        return registeredTransportEntryLocked.getKey();
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    @android.annotation.Nullable
    private com.android.server.backup.TransportManager.TransportDescription getRegisteredTransportDescriptionLocked(java.lang.String str) {
        java.util.Map.Entry<android.content.ComponentName, com.android.server.backup.TransportManager.TransportDescription> registeredTransportEntryLocked = getRegisteredTransportEntryLocked(str);
        if (registeredTransportEntryLocked == null) {
            return null;
        }
        return registeredTransportEntryLocked.getValue();
    }

    @com.android.internal.annotations.GuardedBy({"mTransportLock"})
    @android.annotation.Nullable
    private java.util.Map.Entry<android.content.ComponentName, com.android.server.backup.TransportManager.TransportDescription> getRegisteredTransportEntryLocked(java.lang.String str) {
        for (java.util.Map.Entry<android.content.ComponentName, com.android.server.backup.TransportManager.TransportDescription> entry : this.mRegisteredTransportsDescriptionMap.entrySet()) {
            if (str.equals(entry.getValue().name)) {
                return entry;
            }
        }
        return null;
    }

    @android.annotation.Nullable
    public com.android.server.backup.transport.TransportConnection getTransportClient(java.lang.String str, java.lang.String str2) {
        try {
            return getTransportClientOrThrow(str, str2);
        } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
            android.util.Slog.w(TAG, addUserIdToLogMessage(this.mUserId, "Transport " + str + " not registered"));
            return null;
        }
    }

    public com.android.server.backup.transport.TransportConnection getTransportClientOrThrow(java.lang.String str, java.lang.String str2) throws com.android.server.backup.transport.TransportNotRegisteredException {
        com.android.server.backup.transport.TransportConnection transportClient;
        synchronized (this.mTransportLock) {
            try {
                android.content.ComponentName registeredTransportComponentLocked = getRegisteredTransportComponentLocked(str);
                if (registeredTransportComponentLocked == null) {
                    throw new com.android.server.backup.transport.TransportNotRegisteredException(str);
                }
                transportClient = this.mTransportConnectionManager.getTransportClient(registeredTransportComponentLocked, str2);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return transportClient;
    }

    @android.annotation.Nullable
    public com.android.server.backup.transport.TransportConnection getCurrentTransportClient(java.lang.String str) {
        com.android.server.backup.transport.TransportConnection transportClient;
        if (this.mCurrentTransportName == null) {
            throw new java.lang.IllegalStateException("No transport selected");
        }
        synchronized (this.mTransportLock) {
            transportClient = getTransportClient(this.mCurrentTransportName, str);
        }
        return transportClient;
    }

    public com.android.server.backup.transport.TransportConnection getCurrentTransportClientOrThrow(java.lang.String str) throws com.android.server.backup.transport.TransportNotRegisteredException {
        com.android.server.backup.transport.TransportConnection transportClientOrThrow;
        if (this.mCurrentTransportName == null) {
            throw new java.lang.IllegalStateException("No transport selected");
        }
        synchronized (this.mTransportLock) {
            transportClientOrThrow = getTransportClientOrThrow(this.mCurrentTransportName, str);
        }
        return transportClientOrThrow;
    }

    public void disposeOfTransportClient(com.android.server.backup.transport.TransportConnection transportConnection, java.lang.String str) {
        this.mTransportConnectionManager.disposeOfTransportClient(transportConnection, str);
    }

    @android.annotation.Nullable
    @java.lang.Deprecated
    java.lang.String selectTransport(java.lang.String str) {
        java.lang.String str2;
        synchronized (this.mTransportLock) {
            str2 = this.mCurrentTransportName;
            this.mCurrentTransportName = str;
        }
        return str2;
    }

    public int registerAndSelectTransport(android.content.ComponentName componentName) {
        synchronized (this.mTransportLock) {
            try {
                try {
                    selectTransport(getTransportName(componentName));
                } catch (com.android.server.backup.transport.TransportNotRegisteredException e) {
                    int registerTransport = registerTransport(componentName);
                    if (registerTransport != 0) {
                        return registerTransport;
                    }
                    synchronized (this.mTransportLock) {
                        try {
                            try {
                                selectTransport(getTransportName(componentName));
                                return 0;
                            } catch (com.android.server.backup.transport.TransportNotRegisteredException e2) {
                                android.util.Slog.wtf(TAG, addUserIdToLogMessage(this.mUserId, "Transport got unregistered"));
                                return -1;
                            }
                        } finally {
                        }
                    }
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$registerTransports$2(android.content.ComponentName componentName) {
        return true;
    }

    public void registerTransports() {
        registerTransportsForIntent(this.mTransportServiceIntent, new java.util.function.Predicate() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$registerTransports$2;
                lambda$registerTransports$2 = com.android.server.backup.TransportManager.lambda$registerTransports$2((android.content.ComponentName) obj);
                return lambda$registerTransports$2;
            }
        });
    }

    private void registerTransportsFromPackage(java.lang.String str, java.util.function.Predicate<android.content.ComponentName> predicate) {
        try {
            this.mPackageManager.getPackageInfoAsUser(str, 0, this.mUserId);
            registerTransportsForIntent(new android.content.Intent(this.mTransportServiceIntent).setPackage(str), predicate.and(fromPackageFilter(str)));
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.e(TAG, addUserIdToLogMessage(this.mUserId, "Trying to register transports from package not found " + str));
        }
    }

    private void registerTransportsForIntent(android.content.Intent intent, java.util.function.Predicate<android.content.ComponentName> predicate) {
        java.util.List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(intent, 0, this.mUserId);
        if (queryIntentServicesAsUser == null) {
            return;
        }
        java.util.Iterator it = queryIntentServicesAsUser.iterator();
        while (it.hasNext()) {
            android.content.ComponentName componentName = ((android.content.pm.ResolveInfo) it.next()).serviceInfo.getComponentName();
            if (predicate.test(componentName) && isTransportTrusted(componentName)) {
                registerTransport(componentName);
            }
        }
    }

    private boolean isTransportTrusted(android.content.ComponentName componentName) {
        if (!this.mTransportWhitelist.contains(componentName)) {
            android.util.Slog.w(TAG, addUserIdToLogMessage(this.mUserId, "BackupTransport " + componentName.flattenToShortString() + " not whitelisted."));
            return false;
        }
        try {
            if ((this.mPackageManager.getPackageInfoAsUser(componentName.getPackageName(), 0, this.mUserId).applicationInfo.privateFlags & 8) == 0) {
                android.util.Slog.w(TAG, addUserIdToLogMessage(this.mUserId, "Transport package " + componentName.getPackageName() + " not privileged"));
                return false;
            }
            return true;
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            android.util.Slog.w(TAG, addUserIdToLogMessage(this.mUserId, "Package not found."), e);
            return false;
        }
    }

    private int registerTransport(android.content.ComponentName componentName) {
        java.lang.String name;
        java.lang.String transportDirName;
        checkCanUseTransport();
        if (!isTransportTrusted(componentName)) {
            return -2;
        }
        java.lang.String flattenToShortString = componentName.flattenToShortString();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBoolean("android.app.backup.extra.TRANSPORT_REGISTRATION", true);
        com.android.server.backup.transport.TransportConnection transportClient = this.mTransportConnectionManager.getTransportClient(componentName, bundle, "TransportManager.registerTransport()");
        int i = -1;
        try {
            com.android.server.backup.transport.BackupTransportClient connectOrThrow = transportClient.connectOrThrow("TransportManager.registerTransport()");
            try {
                name = connectOrThrow.name();
                transportDirName = connectOrThrow.transportDirName();
            } catch (android.os.RemoteException e) {
                android.util.Slog.e(TAG, addUserIdToLogMessage(this.mUserId, "Transport " + flattenToShortString + " died while registering"));
            }
            if (name == null || transportDirName == null) {
                return -2;
            }
            registerTransport(componentName, connectOrThrow);
            android.util.Slog.d(TAG, addUserIdToLogMessage(this.mUserId, "Transport " + flattenToShortString + " registered"));
            this.mOnTransportRegisteredListener.onTransportRegistered(name, transportDirName);
            i = 0;
            this.mTransportConnectionManager.disposeOfTransportClient(transportClient, "TransportManager.registerTransport()");
            return i;
        } catch (com.android.server.backup.transport.TransportNotAvailableException e2) {
            android.util.Slog.e(TAG, addUserIdToLogMessage(this.mUserId, "Couldn't connect to transport " + flattenToShortString + " for registration"));
            this.mTransportConnectionManager.disposeOfTransportClient(transportClient, "TransportManager.registerTransport()");
            return -1;
        }
    }

    private void registerTransport(android.content.ComponentName componentName, com.android.server.backup.transport.BackupTransportClient backupTransportClient) throws android.os.RemoteException {
        checkCanUseTransport();
        com.android.server.backup.TransportManager.TransportDescription transportDescription = new com.android.server.backup.TransportManager.TransportDescription(backupTransportClient.name(), backupTransportClient.transportDirName(), backupTransportClient.configurationIntent(), backupTransportClient.currentDestinationString(), backupTransportClient.dataManagementIntent(), backupTransportClient.dataManagementIntentLabel());
        synchronized (this.mTransportLock) {
            this.mRegisteredTransportsDescriptionMap.put(componentName, transportDescription);
        }
    }

    private void checkCanUseTransport() {
        com.android.internal.util.Preconditions.checkState(!java.lang.Thread.holdsLock(this.mTransportLock), "Can't call transport with transport lock held");
    }

    public void dumpTransportClients(java.io.PrintWriter printWriter) {
        this.mTransportConnectionManager.dump(printWriter);
    }

    public void dumpTransportStats(java.io.PrintWriter printWriter) {
        this.mTransportStats.dump(printWriter);
    }

    private static java.util.function.Predicate<android.content.ComponentName> fromPackageFilter(final java.lang.String str) {
        return new java.util.function.Predicate() { // from class: com.android.server.backup.TransportManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(java.lang.Object obj) {
                boolean lambda$fromPackageFilter$3;
                lambda$fromPackageFilter$3 = com.android.server.backup.TransportManager.lambda$fromPackageFilter$3(str, (android.content.ComponentName) obj);
                return lambda$fromPackageFilter$3;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$fromPackageFilter$3(java.lang.String str, android.content.ComponentName componentName) {
        return str.equals(componentName.getPackageName());
    }

    private static class TransportDescription {

        @android.annotation.Nullable
        private android.content.Intent configurationIntent;
        private java.lang.String currentDestinationString;

        @android.annotation.Nullable
        private android.content.Intent dataManagementIntent;

        @android.annotation.Nullable
        private java.lang.CharSequence dataManagementLabel;
        private java.lang.String name;
        private final java.lang.String transportDirName;

        private TransportDescription(java.lang.String str, java.lang.String str2, @android.annotation.Nullable android.content.Intent intent, java.lang.String str3, @android.annotation.Nullable android.content.Intent intent2, @android.annotation.Nullable java.lang.CharSequence charSequence) {
            this.name = str;
            this.transportDirName = str2;
            this.configurationIntent = intent;
            this.currentDestinationString = str3;
            this.dataManagementIntent = intent2;
            this.dataManagementLabel = charSequence;
        }
    }

    private static java.lang.String addUserIdToLogMessage(int i, java.lang.String str) {
        return "[UserID:" + i + "] " + str;
    }
}
