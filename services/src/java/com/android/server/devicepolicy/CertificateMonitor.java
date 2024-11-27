package com.android.server.devicepolicy;

/* loaded from: classes.dex */
public class CertificateMonitor {
    protected static final int MONITORING_CERT_NOTIFICATION_ID = 33;
    private final android.os.Handler mHandler;
    private final com.android.server.devicepolicy.DevicePolicyManagerService.Injector mInjector;
    private final android.content.BroadcastReceiver mRootCaReceiver = new android.content.BroadcastReceiver() { // from class: com.android.server.devicepolicy.CertificateMonitor.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(android.content.Context context, android.content.Intent intent) {
            com.android.server.devicepolicy.CertificateMonitor.this.updateInstalledCertificates(android.os.UserHandle.of(intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId())));
        }
    };
    private final com.android.server.devicepolicy.DevicePolicyManagerService mService;

    public CertificateMonitor(com.android.server.devicepolicy.DevicePolicyManagerService devicePolicyManagerService, com.android.server.devicepolicy.DevicePolicyManagerService.Injector injector, android.os.Handler handler) {
        this.mService = devicePolicyManagerService;
        this.mInjector = injector;
        this.mHandler = handler;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction("android.intent.action.USER_STARTED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.security.action.TRUST_STORE_CHANGED");
        intentFilter.setPriority(1000);
        this.mInjector.mContext.registerReceiverAsUser(this.mRootCaReceiver, android.os.UserHandle.ALL, intentFilter, null, this.mHandler);
    }

    public java.lang.String installCaCert(android.os.UserHandle userHandle, byte[] bArr) {
        try {
            byte[] convertToPem = android.security.Credentials.convertToPem(new java.security.cert.Certificate[]{parseCert(bArr)});
            try {
                android.security.KeyChain.KeyChainConnection keyChainBindAsUser = this.mInjector.keyChainBindAsUser(userHandle);
                try {
                    java.lang.String installCaCertificate = keyChainBindAsUser.getService().installCaCertificate(convertToPem);
                    keyChainBindAsUser.close();
                    return installCaCertificate;
                } catch (java.lang.Throwable th) {
                    if (keyChainBindAsUser != null) {
                        try {
                            keyChainBindAsUser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (android.os.RemoteException e) {
                com.android.server.utils.Slogf.e("DevicePolicyManager", "installCaCertsToKeyChain(): ", e);
                return null;
            } catch (java.lang.InterruptedException e2) {
                com.android.server.utils.Slogf.w("DevicePolicyManager", "installCaCertsToKeyChain(): ", e2);
                java.lang.Thread.currentThread().interrupt();
                return null;
            }
        } catch (java.io.IOException | java.security.cert.CertificateException e3) {
            com.android.server.utils.Slogf.e("DevicePolicyManager", "Problem converting cert", e3);
            return null;
        }
    }

    public void uninstallCaCerts(android.os.UserHandle userHandle, java.lang.String[] strArr) {
        try {
            android.security.KeyChain.KeyChainConnection keyChainBindAsUser = this.mInjector.keyChainBindAsUser(userHandle);
            for (java.lang.String str : strArr) {
                try {
                    keyChainBindAsUser.getService().deleteCaCertificate(str);
                } catch (java.lang.Throwable th) {
                    if (keyChainBindAsUser != null) {
                        try {
                            keyChainBindAsUser.close();
                        } catch (java.lang.Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
            if (keyChainBindAsUser != null) {
                keyChainBindAsUser.close();
            }
        } catch (android.os.RemoteException e) {
            com.android.server.utils.Slogf.e("DevicePolicyManager", "from CaCertUninstaller: ", e);
        } catch (java.lang.InterruptedException e2) {
            com.android.server.utils.Slogf.w("DevicePolicyManager", "CaCertUninstaller: ", e2);
            java.lang.Thread.currentThread().interrupt();
        }
    }

    private java.util.List<java.lang.String> getInstalledCaCertificates(android.os.UserHandle userHandle) throws android.os.RemoteException, java.lang.RuntimeException {
        try {
            android.security.KeyChain.KeyChainConnection keyChainBindAsUser = this.mInjector.keyChainBindAsUser(userHandle);
            try {
                java.util.List<java.lang.String> list = keyChainBindAsUser.getService().getUserCaAliases().getList();
                keyChainBindAsUser.close();
                return list;
            } finally {
            }
        } catch (java.lang.AssertionError e) {
            throw new java.lang.RuntimeException(e);
        } catch (java.lang.InterruptedException e2) {
            java.lang.Thread.currentThread().interrupt();
            throw new java.lang.RuntimeException(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCertificateApprovalsChanged$0(int i) {
        updateInstalledCertificates(android.os.UserHandle.of(i));
    }

    public void onCertificateApprovalsChanged(final int i) {
        this.mHandler.post(new java.lang.Runnable() { // from class: com.android.server.devicepolicy.CertificateMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                com.android.server.devicepolicy.CertificateMonitor.this.lambda$onCertificateApprovalsChanged$0(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInstalledCertificates(android.os.UserHandle userHandle) {
        int identifier = userHandle.getIdentifier();
        if (!this.mInjector.getUserManager().isUserUnlocked(identifier)) {
            return;
        }
        try {
            java.util.List<java.lang.String> installedCaCertificates = getInstalledCaCertificates(userHandle);
            this.mService.onInstalledCertificatesChanged(userHandle, installedCaCertificates);
            int size = installedCaCertificates.size() - this.mService.getAcceptedCaCertificates(userHandle).size();
            if (size == 0) {
                this.mInjector.getNotificationManager().cancelAsUser("DevicePolicyManager", 33, userHandle);
            } else {
                this.mInjector.getNotificationManager().notifyAsUser("DevicePolicyManager", 33, buildNotification(userHandle, size), userHandle);
            }
        } catch (android.os.RemoteException | java.lang.RuntimeException e) {
            com.android.server.utils.Slogf.e("DevicePolicyManager", e, "Could not retrieve certificates from KeyChain service for user %d", java.lang.Integer.valueOf(identifier));
        }
    }

    private android.app.Notification buildNotification(android.os.UserHandle userHandle, int i) {
        java.lang.String string;
        int i2;
        try {
            android.content.Context createContextAsUser = this.mInjector.createContextAsUser(userHandle);
            android.content.res.Resources resources = this.mInjector.getResources();
            int identifier = userHandle.getIdentifier();
            if (this.mService.lambda$isProfileOwner$72(userHandle.getIdentifier()) != null) {
                string = resources.getString(android.R.string.silent_mode_vibrate, this.mService.getProfileOwnerName(userHandle.getIdentifier()));
                identifier = this.mService.getProfileParentId(userHandle.getIdentifier());
                i2 = 17303893;
            } else if (this.mService.getDeviceOwnerUserId() == userHandle.getIdentifier()) {
                string = resources.getString(android.R.string.silent_mode_vibrate, this.mService.getDeviceOwnerName());
                i2 = 17303893;
            } else {
                string = resources.getString(android.R.string.silent_mode_silent);
                i2 = 17301642;
            }
            android.content.Intent intent = new android.content.Intent("com.android.settings.MONITORING_CERT_INFO");
            intent.setFlags(268468224);
            intent.putExtra("android.settings.extra.number_of_certificates", i);
            intent.putExtra("android.intent.extra.USER_ID", userHandle.getIdentifier());
            android.content.pm.ActivityInfo resolveActivityInfo = intent.resolveActivityInfo(this.mInjector.getPackageManager(), 1048576);
            if (resolveActivityInfo != null) {
                intent.setComponent(resolveActivityInfo.getComponentName());
            }
            android.app.PendingIntent pendingIntentGetActivityAsUser = this.mInjector.pendingIntentGetActivityAsUser(createContextAsUser, 0, intent, android.hardware.audio.common.V2_0.AudioFormat.DTS_HD, null, android.os.UserHandle.of(identifier));
            java.util.HashMap hashMap = new java.util.HashMap();
            hashMap.put(com.android.server.am.AssistDataRequester.KEY_RECEIVER_EXTRA_COUNT, java.lang.Integer.valueOf(i));
            return new android.app.Notification.Builder(createContextAsUser, com.android.internal.notification.SystemNotificationChannels.SECURITY).setSmallIcon(i2).setContentTitle(android.util.PluralsMessageFormatter.format(resources, hashMap, android.R.string.sim_added_message)).setContentText(string).setContentIntent(pendingIntentGetActivityAsUser).setShowWhen(false).setColor(android.R.color.system_notification_accent_color).build();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            com.android.server.utils.Slogf.e("DevicePolicyManager", e, "Create context as %s failed", userHandle);
            return null;
        }
    }

    private static java.security.cert.X509Certificate parseCert(byte[] bArr) throws java.security.cert.CertificateException {
        return (java.security.cert.X509Certificate) java.security.cert.CertificateFactory.getInstance("X.509").generateCertificate(new java.io.ByteArrayInputStream(bArr));
    }
}
