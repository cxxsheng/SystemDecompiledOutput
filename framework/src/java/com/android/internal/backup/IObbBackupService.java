package com.android.internal.backup;

/* loaded from: classes4.dex */
public interface IObbBackupService extends android.os.IInterface {
    void backupObbs(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException;

    void restoreObbFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException;

    public static class Default implements com.android.internal.backup.IObbBackupService {
        @Override // com.android.internal.backup.IObbBackupService
        public void backupObbs(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
        }

        @Override // com.android.internal.backup.IObbBackupService
        public void restoreObbFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.backup.IObbBackupService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.backup.IObbBackupService";
        static final int TRANSACTION_backupObbs = 1;
        static final int TRANSACTION_restoreObbFile = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.backup.IObbBackupService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.backup.IObbBackupService)) {
                return (com.android.internal.backup.IObbBackupService) queryLocalInterface;
            }
            return new com.android.internal.backup.IObbBackupService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "backupObbs";
                case 2:
                    return "restoreObbFile";
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
                    java.lang.String readString = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt = parcel.readInt();
                    android.app.backup.IBackupManager asInterface = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    backupObbs(readString, parcelFileDescriptor, readInt, asInterface);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor2 = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    long readLong = parcel.readLong();
                    int readInt2 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    long readLong3 = parcel.readLong();
                    int readInt3 = parcel.readInt();
                    android.app.backup.IBackupManager asInterface2 = android.app.backup.IBackupManager.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    restoreObbFile(readString2, parcelFileDescriptor2, readLong, readInt2, readString3, readLong2, readLong3, readInt3, asInterface2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.backup.IObbBackupService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.backup.IObbBackupService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.backup.IObbBackupService
            public void backupObbs(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IObbBackupService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.backup.IObbBackupService
            public void restoreObbFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, long j, int i, java.lang.String str2, long j2, long j3, int i2, android.app.backup.IBackupManager iBackupManager) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.backup.IObbBackupService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iBackupManager);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
