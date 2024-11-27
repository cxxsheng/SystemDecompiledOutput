package com.android.server.security;

/* loaded from: classes2.dex */
public class KeyAttestationApplicationIdProviderService extends android.security.keystore.IKeyAttestationApplicationIdProvider.Stub {
    private android.content.pm.PackageManager mPackageManager;

    public KeyAttestationApplicationIdProviderService(android.content.Context context) {
        this.mPackageManager = context.getPackageManager();
    }

    @Override // android.security.keystore.IKeyAttestationApplicationIdProvider
    public android.security.keystore.KeyAttestationApplicationId getKeyAttestationApplicationId(int i) throws android.os.RemoteException {
        int callingUid = android.os.Binder.getCallingUid();
        if (callingUid != 1017 && callingUid != 1076) {
            throw new java.lang.SecurityException("This service can only be used by Keystore or Credstore");
        }
        long clearCallingIdentity = android.os.Binder.clearCallingIdentity();
        try {
            try {
                java.lang.String[] packagesForUid = this.mPackageManager.getPackagesForUid(i);
                if (packagesForUid == null) {
                    throw new android.os.ServiceSpecificException(1, "No package for uid: " + i);
                }
                int userId = android.os.UserHandle.getUserId(i);
                android.security.keystore.KeyAttestationPackageInfo[] keyAttestationPackageInfoArr = new android.security.keystore.KeyAttestationPackageInfo[packagesForUid.length];
                for (int i2 = 0; i2 < packagesForUid.length; i2++) {
                    android.content.pm.PackageInfo packageInfoAsUser = this.mPackageManager.getPackageInfoAsUser(packagesForUid[i2], 64, userId);
                    android.security.keystore.KeyAttestationPackageInfo keyAttestationPackageInfo = new android.security.keystore.KeyAttestationPackageInfo();
                    keyAttestationPackageInfo.packageName = new java.lang.String(packagesForUid[i2]);
                    keyAttestationPackageInfo.versionCode = packageInfoAsUser.getLongVersionCode();
                    keyAttestationPackageInfo.signatures = new android.security.keystore.Signature[packageInfoAsUser.signatures.length];
                    for (int i3 = 0; i3 < packageInfoAsUser.signatures.length; i3++) {
                        android.security.keystore.Signature signature = new android.security.keystore.Signature();
                        signature.data = packageInfoAsUser.signatures[i3].toByteArray();
                        keyAttestationPackageInfo.signatures[i3] = signature;
                    }
                    keyAttestationPackageInfoArr[i2] = keyAttestationPackageInfo;
                }
                android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
                android.security.keystore.KeyAttestationApplicationId keyAttestationApplicationId = new android.security.keystore.KeyAttestationApplicationId();
                keyAttestationApplicationId.packageInfos = keyAttestationPackageInfoArr;
                return keyAttestationApplicationId;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                throw new android.os.RemoteException(e.getMessage());
            }
        } catch (java.lang.Throwable th) {
            android.os.Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }
}
