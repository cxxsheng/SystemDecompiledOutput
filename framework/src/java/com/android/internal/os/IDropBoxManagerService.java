package com.android.internal.os;

/* loaded from: classes4.dex */
public interface IDropBoxManagerService extends android.os.IInterface {
    void addData(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException;

    void addFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException;

    android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException;

    android.os.DropBoxManager.Entry getNextEntryWithAttribution(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException;

    boolean isTagEnabled(java.lang.String str) throws android.os.RemoteException;

    public static class Default implements com.android.internal.os.IDropBoxManagerService {
        @Override // com.android.internal.os.IDropBoxManagerService
        public void addData(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public void addFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public boolean isTagEnabled(java.lang.String str) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException {
            return null;
        }

        @Override // com.android.internal.os.IDropBoxManagerService
        public android.os.DropBoxManager.Entry getNextEntryWithAttribution(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.os.IDropBoxManagerService {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.os.IDropBoxManagerService";
        static final int TRANSACTION_addData = 1;
        static final int TRANSACTION_addFile = 2;
        static final int TRANSACTION_getNextEntry = 4;
        static final int TRANSACTION_getNextEntryWithAttribution = 5;
        static final int TRANSACTION_isTagEnabled = 3;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.os.IDropBoxManagerService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.os.IDropBoxManagerService)) {
                return (com.android.internal.os.IDropBoxManagerService) queryLocalInterface;
            }
            return new com.android.internal.os.IDropBoxManagerService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addData";
                case 2:
                    return "addFile";
                case 3:
                    return "isTagEnabled";
                case 4:
                    return "getNextEntry";
                case 5:
                    return "getNextEntryWithAttribution";
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
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addData(readString, createByteArray, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    android.os.ParcelFileDescriptor parcelFileDescriptor = (android.os.ParcelFileDescriptor) parcel.readTypedObject(android.os.ParcelFileDescriptor.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addFile(readString2, parcelFileDescriptor, readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isTagEnabled = isTagEnabled(readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTagEnabled);
                    return true;
                case 4:
                    java.lang.String readString4 = parcel.readString();
                    long readLong = parcel.readLong();
                    java.lang.String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.DropBoxManager.Entry nextEntry = getNextEntry(readString4, readLong, readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nextEntry, 1);
                    return true;
                case 5:
                    java.lang.String readString6 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    java.lang.String readString7 = parcel.readString();
                    java.lang.String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.os.DropBoxManager.Entry nextEntryWithAttribution = getNextEntryWithAttribution(readString6, readLong2, readString7, readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(nextEntryWithAttribution, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.os.IDropBoxManagerService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addData(java.lang.String str, byte[] bArr, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public void addFile(java.lang.String str, android.os.ParcelFileDescriptor parcelFileDescriptor, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parcelFileDescriptor, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public boolean isTagEnabled(java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public android.os.DropBoxManager.Entry getNextEntry(java.lang.String str, long j, java.lang.String str2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.DropBoxManager.Entry) obtain2.readTypedObject(android.os.DropBoxManager.Entry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.os.IDropBoxManagerService
            public android.os.DropBoxManager.Entry getNextEntryWithAttribution(java.lang.String str, long j, java.lang.String str2, java.lang.String str3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.os.IDropBoxManagerService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.os.DropBoxManager.Entry) obtain2.readTypedObject(android.os.DropBoxManager.Entry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }
    }
}
