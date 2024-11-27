package com.android.server.os;

/* loaded from: classes2.dex */
public final class DeviceIdentifiersPolicyService extends com.android.server.SystemService {
    public DeviceIdentifiersPolicyService(android.content.Context context) {
        super(context);
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("device_identifiers", new com.android.server.os.DeviceIdentifiersPolicyService.DeviceIdentifiersPolicy(getContext()));
    }

    private static final class DeviceIdentifiersPolicy extends android.os.IDeviceIdentifiersPolicyService.Stub {

        @android.annotation.NonNull
        private final android.content.Context mContext;

        public DeviceIdentifiersPolicy(android.content.Context context) {
            this.mContext = context;
        }

        @android.annotation.Nullable
        public java.lang.String getSerial() throws android.os.RemoteException {
            return !com.android.internal.telephony.TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers(this.mContext, (java.lang.String) null, (java.lang.String) null, "getSerial") ? "unknown" : android.os.SystemProperties.get("ro.serialno", "unknown");
        }

        @android.annotation.Nullable
        public java.lang.String getSerialForPackage(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
            if (checkPackageBelongsToCaller(str)) {
                return !com.android.internal.telephony.TelephonyPermissions.checkCallingOrSelfReadDeviceIdentifiers(this.mContext, str, str2, "getSerial") ? "unknown" : android.os.SystemProperties.get("ro.serialno", "unknown");
            }
            throw new java.lang.IllegalArgumentException("Invalid callingPackage or callingPackage does not belong to caller's uid:" + android.os.Binder.getCallingUid());
        }

        private boolean checkPackageBelongsToCaller(java.lang.String str) {
            int callingUid = android.os.Binder.getCallingUid();
            try {
                return this.mContext.getPackageManager().getPackageUidAsUser(str, android.os.UserHandle.getUserId(callingUid)) == callingUid;
            } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                return false;
            }
        }
    }
}
