package android.telephony.mbms.vendor;

/* loaded from: classes3.dex */
public interface IMbmsGroupCallService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsGroupCallService";

    void dispose(int i) throws android.os.RemoteException;

    int initialize(android.telephony.mbms.IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws android.os.RemoteException;

    int startGroupCall(int i, long j, java.util.List list, java.util.List list2, android.telephony.mbms.IGroupCallCallback iGroupCallCallback) throws android.os.RemoteException;

    void stopGroupCall(int i, long j) throws android.os.RemoteException;

    void updateGroupCall(int i, long j, java.util.List list, java.util.List list2) throws android.os.RemoteException;

    public static class Default implements android.telephony.mbms.vendor.IMbmsGroupCallService {
        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int initialize(android.telephony.mbms.IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void stopGroupCall(int i, long j) throws android.os.RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void updateGroupCall(int i, long j, java.util.List list, java.util.List list2) throws android.os.RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public int startGroupCall(int i, long j, java.util.List list, java.util.List list2, android.telephony.mbms.IGroupCallCallback iGroupCallCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
        public void dispose(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.mbms.vendor.IMbmsGroupCallService {
        static final int TRANSACTION_dispose = 5;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_startGroupCall = 4;
        static final int TRANSACTION_stopGroupCall = 2;
        static final int TRANSACTION_updateGroupCall = 3;

        public Stub() {
            attachInterface(this, android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
        }

        public static android.telephony.mbms.vendor.IMbmsGroupCallService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.mbms.vendor.IMbmsGroupCallService)) {
                return (android.telephony.mbms.vendor.IMbmsGroupCallService) queryLocalInterface;
            }
            return new android.telephony.mbms.vendor.IMbmsGroupCallService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.content.ContentResolver.SYNC_EXTRAS_INITIALIZE;
                case 2:
                    return "stopGroupCall";
                case 3:
                    return "updateGroupCall";
                case 4:
                    return "startGroupCall";
                case 5:
                    return "dispose";
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
                parcel.enforceInterface(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.mbms.IMbmsGroupCallSessionCallback asInterface = android.telephony.mbms.IMbmsGroupCallSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialize = initialize(asInterface, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialize);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    stopGroupCall(readInt2, readLong);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    java.lang.ClassLoader classLoader = getClass().getClassLoader();
                    java.util.ArrayList readArrayList = parcel.readArrayList(classLoader);
                    java.util.ArrayList readArrayList2 = parcel.readArrayList(classLoader);
                    parcel.enforceNoDataAvail();
                    updateGroupCall(readInt3, readLong2, readArrayList, readArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    java.lang.ClassLoader classLoader2 = getClass().getClassLoader();
                    java.util.ArrayList readArrayList3 = parcel.readArrayList(classLoader2);
                    java.util.ArrayList readArrayList4 = parcel.readArrayList(classLoader2);
                    android.telephony.mbms.IGroupCallCallback asInterface2 = android.telephony.mbms.IGroupCallCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startGroupCall = startGroupCall(readInt4, readLong3, readArrayList3, readArrayList4, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startGroupCall);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispose(readInt5);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.mbms.vendor.IMbmsGroupCallService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public int initialize(android.telephony.mbms.IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeStrongInterface(iMbmsGroupCallSessionCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void stopGroupCall(int i, long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void updateGroupCall(int i, long j, java.util.List list, java.util.List list2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public int startGroupCall(int i, long j, java.util.List list, java.util.List list2, android.telephony.mbms.IGroupCallCallback iGroupCallCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeList(list);
                    obtain.writeList(list2);
                    obtain.writeStrongInterface(iGroupCallCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsGroupCallService
            public void dispose(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsGroupCallService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
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
