package android.permission;

/* loaded from: classes3.dex */
public interface IPermissionController extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.permission.IPermissionController";

    void applyStagedRuntimePermissionBackup(java.lang.String str, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void countPermissionApps(java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getAppPermissions(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getGroupOfPlatformPermission(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    void getHibernationEligibility(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getPermissionUsages(boolean z, long j, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void getPlatformPermissionsForGroup(java.lang.String str, com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture) throws android.os.RemoteException;

    void getPrivilegesDescriptionStringForProfile(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException;

    void getRuntimePermissionBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void getUnusedAppCount(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void grantOrUpgradeDefaultRuntimePermissions(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void notifyOneTimePermissionSessionTimeout(java.lang.String str, int i) throws android.os.RemoteException;

    void revokeRuntimePermission(java.lang.String str, java.lang.String str2) throws android.os.RemoteException;

    void revokeRuntimePermissions(android.os.Bundle bundle, boolean z, int i, java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void revokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void setRuntimePermissionGrantStateByDeviceAdminFromParams(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    void stageAndApplyRuntimePermissionsBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException;

    void updateUserSensitiveForApp(int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException;

    public static class Default implements android.permission.IPermissionController {
        @Override // android.permission.IPermissionController
        public void revokeRuntimePermissions(android.os.Bundle bundle, boolean z, int i, java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getRuntimePermissionBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void stageAndApplyRuntimePermissionsBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void applyStagedRuntimePermissionBackup(java.lang.String str, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getAppPermissions(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void revokeRuntimePermission(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void countPermissionApps(java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPermissionUsages(boolean z, long j, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void setRuntimePermissionGrantStateByDeviceAdminFromParams(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void grantOrUpgradeDefaultRuntimePermissions(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void notifyOneTimePermissionSessionTimeout(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void updateUserSensitiveForApp(int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPrivilegesDescriptionStringForProfile(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getPlatformPermissionsForGroup(java.lang.String str, com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getGroupOfPlatformPermission(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getUnusedAppCount(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void getHibernationEligibility(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.permission.IPermissionController
        public void revokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.permission.IPermissionController {
        static final int TRANSACTION_applyStagedRuntimePermissionBackup = 4;
        static final int TRANSACTION_countPermissionApps = 7;
        static final int TRANSACTION_getAppPermissions = 5;
        static final int TRANSACTION_getGroupOfPlatformPermission = 15;
        static final int TRANSACTION_getHibernationEligibility = 17;
        static final int TRANSACTION_getPermissionUsages = 8;
        static final int TRANSACTION_getPlatformPermissionsForGroup = 14;
        static final int TRANSACTION_getPrivilegesDescriptionStringForProfile = 13;
        static final int TRANSACTION_getRuntimePermissionBackup = 2;
        static final int TRANSACTION_getUnusedAppCount = 16;
        static final int TRANSACTION_grantOrUpgradeDefaultRuntimePermissions = 10;
        static final int TRANSACTION_notifyOneTimePermissionSessionTimeout = 11;
        static final int TRANSACTION_revokeRuntimePermission = 6;
        static final int TRANSACTION_revokeRuntimePermissions = 1;
        static final int TRANSACTION_revokeSelfPermissionsOnKill = 18;
        static final int TRANSACTION_setRuntimePermissionGrantStateByDeviceAdminFromParams = 9;
        static final int TRANSACTION_stageAndApplyRuntimePermissionsBackup = 3;
        static final int TRANSACTION_updateUserSensitiveForApp = 12;

        public Stub() {
            attachInterface(this, android.permission.IPermissionController.DESCRIPTOR);
        }

        public static android.permission.IPermissionController asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.permission.IPermissionController.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.permission.IPermissionController)) {
                return (android.permission.IPermissionController) queryLocalInterface;
            }
            return new android.permission.IPermissionController.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "revokeRuntimePermissions";
                case 2:
                    return "getRuntimePermissionBackup";
                case 3:
                    return "stageAndApplyRuntimePermissionsBackup";
                case 4:
                    return "applyStagedRuntimePermissionBackup";
                case 5:
                    return "getAppPermissions";
                case 6:
                    return "revokeRuntimePermission";
                case 7:
                    return "countPermissionApps";
                case 8:
                    return "getPermissionUsages";
                case 9:
                    return "setRuntimePermissionGrantStateByDeviceAdminFromParams";
                case 10:
                    return "grantOrUpgradeDefaultRuntimePermissions";
                case 11:
                    return "notifyOneTimePermissionSessionTimeout";
                case 12:
                    return "updateUserSensitiveForApp";
                case 13:
                    return "getPrivilegesDescriptionStringForProfile";
                case 14:
                    return "getPlatformPermissionsForGroup";
                case 15:
                    return "getGroupOfPlatformPermission";
                case 16:
                    return "getUnusedAppCount";
                case 17:
                    return "getHibernationEligibility";
                case 18:
                    return "revokeSelfPermissionsOnKill";
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
                parcel.enforceInterface(android.permission.IPermissionController.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.permission.IPermissionController.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    com.android.internal.infra.AndroidFuture androidFuture = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermissions(bundle, readBoolean, readInt, readString, androidFuture);
                    return true;
                case 2:
                    android.os.UserHandle userHandle = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    getRuntimePermissionBackup(userHandle, parcelFileDescriptor);
                    return true;
                case 3:
                    android.os.UserHandle userHandle2 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    parcel.enforceNoDataAvail();
                    stageAndApplyRuntimePermissionsBackup(userHandle2, parcelFileDescriptor2);
                    return true;
                case 4:
                    java.lang.String readString2 = parcel.readString();
                    android.os.UserHandle userHandle3 = (android.os.UserHandle) parcel.readTypedObject(android.os.UserHandle.CREATOR);
                    com.android.internal.infra.AndroidFuture androidFuture2 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyStagedRuntimePermissionBackup(readString2, userHandle3, androidFuture2);
                    return true;
                case 5:
                    java.lang.String readString3 = parcel.readString();
                    com.android.internal.infra.AndroidFuture androidFuture3 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getAppPermissions(readString3, androidFuture3);
                    return true;
                case 6:
                    java.lang.String readString4 = parcel.readString();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    revokeRuntimePermission(readString4, readString5);
                    return true;
                case 7:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt2 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture4 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    countPermissionApps(createStringArrayList, readInt2, androidFuture4);
                    return true;
                case 8:
                    boolean readBoolean2 = parcel.readBoolean();
                    long readLong = parcel.readLong();
                    com.android.internal.infra.AndroidFuture androidFuture5 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPermissionUsages(readBoolean2, readLong, androidFuture5);
                    return true;
                case 9:
                    java.lang.String readString6 = parcel.readString();
                    android.permission.AdminPermissionControlParams adminPermissionControlParams = (android.permission.AdminPermissionControlParams) parcel.readTypedObject(android.permission.AdminPermissionControlParams.CREATOR);
                    com.android.internal.infra.AndroidFuture androidFuture6 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    setRuntimePermissionGrantStateByDeviceAdminFromParams(readString6, adminPermissionControlParams, androidFuture6);
                    return true;
                case 10:
                    com.android.internal.infra.AndroidFuture androidFuture7 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    grantOrUpgradeDefaultRuntimePermissions(androidFuture7);
                    return true;
                case 11:
                    java.lang.String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyOneTimePermissionSessionTimeout(readString7, readInt3);
                    return true;
                case 12:
                    int readInt4 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture8 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateUserSensitiveForApp(readInt4, androidFuture8);
                    return true;
                case 13:
                    java.lang.String readString8 = parcel.readString();
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture9 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPrivilegesDescriptionStringForProfile(readString8, androidFuture9);
                    return true;
                case 14:
                    java.lang.String readString9 = parcel.readString();
                    com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture10 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getPlatformPermissionsForGroup(readString9, androidFuture10);
                    return true;
                case 15:
                    java.lang.String readString10 = parcel.readString();
                    com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture11 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getGroupOfPlatformPermission(readString10, androidFuture11);
                    return true;
                case 16:
                    com.android.internal.infra.AndroidFuture androidFuture12 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getUnusedAppCount(androidFuture12);
                    return true;
                case 17:
                    java.lang.String readString11 = parcel.readString();
                    com.android.internal.infra.AndroidFuture androidFuture13 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    getHibernationEligibility(readString11, androidFuture13);
                    return true;
                case 18:
                    java.lang.String readString12 = parcel.readString();
                    java.util.ArrayList<java.lang.String> createStringArrayList2 = parcel.createStringArrayList();
                    int readInt5 = parcel.readInt();
                    com.android.internal.infra.AndroidFuture androidFuture14 = (com.android.internal.infra.AndroidFuture) parcel.readTypedObject(com.android.internal.infra.AndroidFuture.CREATOR);
                    parcel.enforceNoDataAvail();
                    revokeSelfPermissionsOnKill(readString12, createStringArrayList2, readInt5, androidFuture14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.permission.IPermissionController {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.permission.IPermissionController.DESCRIPTOR;
            }

            @Override // android.permission.IPermissionController
            public void revokeRuntimePermissions(android.os.Bundle bundle, boolean z, int i, java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getRuntimePermissionBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void stageAndApplyRuntimePermissionsBackup(android.os.UserHandle userHandle, android.os.ParcelFileDescriptor parcelFileDescriptor) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void applyStagedRuntimePermissionBackup(java.lang.String str, android.os.UserHandle userHandle, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getAppPermissions(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void revokeRuntimePermission(java.lang.String str, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void countPermissionApps(java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPermissionUsages(boolean z, long j, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeLong(j);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void setRuntimePermissionGrantStateByDeviceAdminFromParams(java.lang.String str, android.permission.AdminPermissionControlParams adminPermissionControlParams, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(adminPermissionControlParams, 0);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void grantOrUpgradeDefaultRuntimePermissions(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void notifyOneTimePermissionSessionTimeout(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void updateUserSensitiveForApp(int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPrivilegesDescriptionStringForProfile(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getPlatformPermissionsForGroup(java.lang.String str, com.android.internal.infra.AndroidFuture<java.util.List<java.lang.String>> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getGroupOfPlatformPermission(java.lang.String str, com.android.internal.infra.AndroidFuture<java.lang.String> androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getUnusedAppCount(com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void getHibernationEligibility(java.lang.String str, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.permission.IPermissionController
            public void revokeSelfPermissionsOnKill(java.lang.String str, java.util.List<java.lang.String> list, int i, com.android.internal.infra.AndroidFuture androidFuture) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.permission.IPermissionController.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(androidFuture, 0);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
