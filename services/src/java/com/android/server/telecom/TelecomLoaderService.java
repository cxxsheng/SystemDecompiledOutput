package com.android.server.telecom;

/* loaded from: classes2.dex */
public class TelecomLoaderService extends com.android.server.SystemService {
    private static final java.lang.String SERVICE_ACTION = "com.android.ITelecomService";
    private static final android.content.ComponentName SERVICE_COMPONENT = new android.content.ComponentName("com.android.server.telecom", "com.android.server.telecom.components.TelecomService");
    private static final java.lang.String TAG = "TelecomLoaderService";
    private final android.content.Context mContext;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private android.util.IntArray mDefaultSimCallManagerRequests;
    private final java.lang.Object mLock;

    @com.android.internal.annotations.GuardedBy({"mLock"})
    private com.android.server.telecom.TelecomLoaderService.TelecomServiceConnection mServiceConnection;
    private com.android.server.telecom.InternalServiceRepository mServiceRepo;

    private class TelecomServiceConnection implements android.content.ServiceConnection {
        private TelecomServiceConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(android.content.ComponentName componentName, android.os.IBinder iBinder) {
            android.telecom.PhoneAccountHandle simCallManager;
            try {
                com.android.internal.telecom.ITelecomService createTelecomService = com.android.internal.telecom.ITelecomLoader.Stub.asInterface(iBinder).createTelecomService(com.android.server.telecom.TelecomLoaderService.this.mServiceRepo);
                com.android.internal.telephony.SmsApplication.getDefaultMmsApplication(com.android.server.telecom.TelecomLoaderService.this.mContext, false);
                android.os.ServiceManager.addService("telecom", createTelecomService.asBinder());
                synchronized (com.android.server.telecom.TelecomLoaderService.this.mLock) {
                    try {
                        com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
                        if (com.android.server.telecom.TelecomLoaderService.this.mDefaultSimCallManagerRequests != null && (simCallManager = ((android.telecom.TelecomManager) com.android.server.telecom.TelecomLoaderService.this.mContext.getSystemService("telecom")).getSimCallManager()) != null) {
                            int size = com.android.server.telecom.TelecomLoaderService.this.mDefaultSimCallManagerRequests.size();
                            java.lang.String packageName = simCallManager.getComponentName().getPackageName();
                            for (int i = size - 1; i >= 0; i--) {
                                int i2 = com.android.server.telecom.TelecomLoaderService.this.mDefaultSimCallManagerRequests.get(i);
                                com.android.server.telecom.TelecomLoaderService.this.mDefaultSimCallManagerRequests.remove(i);
                                legacyPermissionManagerInternal.grantDefaultPermissionsToDefaultSimCallManager(packageName, i2);
                            }
                        }
                    } finally {
                    }
                }
            } catch (android.os.RemoteException e) {
                android.util.Slog.w(com.android.server.telecom.TelecomLoaderService.TAG, "Failed linking to death.");
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(android.content.ComponentName componentName) {
            com.android.server.telecom.TelecomLoaderService.this.connectToTelecom();
        }
    }

    public TelecomLoaderService(android.content.Context context) {
        super(context);
        this.mLock = new java.lang.Object();
        this.mContext = context;
        registerDefaultAppProviders();
    }

    @Override // com.android.server.SystemService
    public void onStart() {
    }

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
        if (i == 550) {
            registerDefaultAppNotifier();
            registerCarrierConfigChangedReceiver();
            setupServiceRepository();
            connectToTelecom();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectToTelecom() {
        synchronized (this.mLock) {
            try {
                if (this.mServiceConnection != null) {
                    this.mContext.unbindService(this.mServiceConnection);
                    this.mServiceConnection = null;
                }
                com.android.server.telecom.TelecomLoaderService.TelecomServiceConnection telecomServiceConnection = new com.android.server.telecom.TelecomLoaderService.TelecomServiceConnection();
                android.content.Intent intent = new android.content.Intent(SERVICE_ACTION);
                intent.setComponent(SERVICE_COMPONENT);
                if (this.mContext.bindServiceAsUser(intent, telecomServiceConnection, 67108929, android.os.UserHandle.SYSTEM)) {
                    this.mServiceConnection = telecomServiceConnection;
                }
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void setupServiceRepository() {
        this.mServiceRepo = new com.android.server.telecom.InternalServiceRepository((com.android.server.DeviceIdleInternal) getLocalService(com.android.server.DeviceIdleInternal.class));
    }

    private void registerDefaultAppProviders() {
        com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
        legacyPermissionManagerInternal.setSmsAppPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda0
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$registerDefaultAppProviders$0;
                lambda$registerDefaultAppProviders$0 = com.android.server.telecom.TelecomLoaderService.this.lambda$registerDefaultAppProviders$0(i);
                return lambda$registerDefaultAppProviders$0;
            }
        });
        legacyPermissionManagerInternal.setDialerAppPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda1
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$registerDefaultAppProviders$1;
                lambda$registerDefaultAppProviders$1 = com.android.server.telecom.TelecomLoaderService.this.lambda$registerDefaultAppProviders$1(i);
                return lambda$registerDefaultAppProviders$1;
            }
        });
        legacyPermissionManagerInternal.setSimCallManagerPackagesProvider(new com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider() { // from class: com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda2
            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal.PackagesProvider
            public final java.lang.String[] getPackages(int i) {
                java.lang.String[] lambda$registerDefaultAppProviders$2;
                lambda$registerDefaultAppProviders$2 = com.android.server.telecom.TelecomLoaderService.this.lambda$registerDefaultAppProviders$2(i);
                return lambda$registerDefaultAppProviders$2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$registerDefaultAppProviders$0(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mServiceConnection == null) {
                    return null;
                }
                android.content.ComponentName defaultSmsApplication = com.android.internal.telephony.SmsApplication.getDefaultSmsApplication(this.mContext, true);
                if (defaultSmsApplication != null) {
                    return new java.lang.String[]{defaultSmsApplication.getPackageName()};
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$registerDefaultAppProviders$1(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mServiceConnection == null) {
                    return null;
                }
                java.lang.String defaultDialerApplication = android.telecom.DefaultDialerManager.getDefaultDialerApplication(this.mContext);
                if (defaultDialerApplication != null) {
                    return new java.lang.String[]{defaultDialerApplication};
                }
                return null;
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ java.lang.String[] lambda$registerDefaultAppProviders$2(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mServiceConnection == null) {
                    if (this.mDefaultSimCallManagerRequests == null) {
                        this.mDefaultSimCallManagerRequests = new android.util.IntArray();
                    }
                    this.mDefaultSimCallManagerRequests.add(i);
                    return null;
                }
                android.telephony.SubscriptionManager subscriptionManager = (android.telephony.SubscriptionManager) this.mContext.getSystemService(android.telephony.SubscriptionManager.class);
                if (subscriptionManager == null) {
                    return null;
                }
                android.telecom.TelecomManager telecomManager = (android.telecom.TelecomManager) this.mContext.getSystemService("telecom");
                java.util.ArrayList arrayList = new java.util.ArrayList();
                for (int i2 : subscriptionManager.getActiveSubscriptionIdList()) {
                    android.telecom.PhoneAccountHandle simCallManagerForSubscription = telecomManager.getSimCallManagerForSubscription(i2);
                    if (simCallManagerForSubscription != null) {
                        arrayList.add(simCallManagerForSubscription.getComponentName().getPackageName());
                    }
                }
                return (java.lang.String[]) arrayList.toArray(new java.lang.String[0]);
            } catch (java.lang.Throwable th) {
                throw th;
            }
        }
    }

    private void registerDefaultAppNotifier() {
        ((android.app.role.RoleManager) this.mContext.getSystemService(android.app.role.RoleManager.class)).addOnRoleHoldersChangedListenerAsUser(this.mContext.getMainExecutor(), new android.app.role.OnRoleHoldersChangedListener() { // from class: com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda3
            public final void onRoleHoldersChanged(java.lang.String str, android.os.UserHandle userHandle) {
                com.android.server.telecom.TelecomLoaderService.this.lambda$registerDefaultAppNotifier$3(str, userHandle);
            }
        }, android.os.UserHandle.ALL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerDefaultAppNotifier$3(java.lang.String str, android.os.UserHandle userHandle) {
        updateSimCallManagerPermissions(userHandle.getIdentifier());
    }

    private void registerCarrierConfigChangedReceiver() {
        this.mContext.registerReceiverAsUser(new android.content.BroadcastReceiver() { // from class: com.android.server.telecom.TelecomLoaderService.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(android.content.Context context, android.content.Intent intent) {
                if (intent.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                    for (int i : com.android.server.pm.UserManagerService.getInstance().getUserIds()) {
                        com.android.server.telecom.TelecomLoaderService.this.updateSimCallManagerPermissions(i);
                    }
                }
            }
        }, android.os.UserHandle.ALL, new android.content.IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), null, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSimCallManagerPermissions(int i) {
        com.android.server.pm.permission.LegacyPermissionManagerInternal legacyPermissionManagerInternal = (com.android.server.pm.permission.LegacyPermissionManagerInternal) com.android.server.LocalServices.getService(com.android.server.pm.permission.LegacyPermissionManagerInternal.class);
        android.telecom.PhoneAccountHandle simCallManager = ((android.telecom.TelecomManager) this.mContext.getSystemService("telecom")).getSimCallManager(i);
        if (simCallManager != null) {
            android.util.Slog.i(TAG, "updating sim call manager permissions for userId:" + i);
            legacyPermissionManagerInternal.grantDefaultPermissionsToDefaultSimCallManager(simCallManager.getComponentName().getPackageName(), i);
        }
    }
}
