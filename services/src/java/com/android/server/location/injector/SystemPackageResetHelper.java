package com.android.server.location.injector;

/* loaded from: classes2.dex */
public class SystemPackageResetHelper extends com.android.server.location.injector.PackageResetHelper {
    private final android.content.Context mContext;

    @android.annotation.Nullable
    private android.content.BroadcastReceiver mReceiver;

    public SystemPackageResetHelper(android.content.Context context) {
        this.mContext = context;
    }

    @Override // com.android.server.location.injector.PackageResetHelper
    protected void onRegister() {
        com.android.internal.util.Preconditions.checkState(this.mReceiver == null);
        this.mReceiver = new com.android.server.location.injector.SystemPackageResetHelper.Receiver();
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addDataScheme(com.android.server.pm.Settings.ATTR_PACKAGE);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    @Override // com.android.server.location.injector.PackageResetHelper
    protected void onUnregister() {
        com.android.internal.util.Preconditions.checkState(this.mReceiver != null);
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mReceiver = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Receiver extends android.content.BroadcastReceiver {
        private Receiver() {
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            android.net.Uri data;
            final java.lang.String schemeSpecificPart;
            char c;
            java.lang.String action = intent.getAction();
            if (action == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                return;
            }
            int i = 0;
            switch (action.hashCode()) {
                case -1072806502:
                    if (action.equals("android.intent.action.QUERY_PACKAGE_RESTART")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -757780528:
                    if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 172491798:
                    if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    java.lang.String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
                    if (stringArrayExtra != null) {
                        int length = stringArrayExtra.length;
                        while (i < length) {
                            if (!com.android.server.location.injector.SystemPackageResetHelper.this.queryResetableForPackage(stringArrayExtra[i])) {
                                i++;
                            } else {
                                setResultCode(-1);
                                break;
                            }
                        }
                        break;
                    }
                    break;
                case 1:
                    java.lang.String[] stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                    if (stringArrayExtra2 != null) {
                        int length2 = stringArrayExtra2.length;
                        int i2 = 0;
                        while (true) {
                            if (i2 < length2) {
                                if (!schemeSpecificPart.equals(stringArrayExtra2[i2])) {
                                    i2++;
                                } else {
                                    i = 1;
                                }
                            }
                        }
                    }
                    if (i != 0) {
                        try {
                            if (!context.getPackageManager().getApplicationInfo(schemeSpecificPart, android.content.pm.PackageManager.ApplicationInfoFlags.of(0L)).enabled) {
                                com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemPackageResetHelper$Receiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        com.android.server.location.injector.SystemPackageResetHelper.Receiver.this.lambda$onReceive$0(schemeSpecificPart);
                                    }
                                });
                                break;
                            }
                        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                            return;
                        }
                    }
                    break;
                case 2:
                case 3:
                    com.android.server.FgThread.getExecutor().execute(new java.lang.Runnable() { // from class: com.android.server.location.injector.SystemPackageResetHelper$Receiver$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.server.location.injector.SystemPackageResetHelper.Receiver.this.lambda$onReceive$1(schemeSpecificPart);
                        }
                    });
                    break;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$0(java.lang.String str) {
            com.android.server.location.injector.SystemPackageResetHelper.this.notifyPackageReset(str);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onReceive$1(java.lang.String str) {
            com.android.server.location.injector.SystemPackageResetHelper.this.notifyPackageReset(str);
        }
    }
}
