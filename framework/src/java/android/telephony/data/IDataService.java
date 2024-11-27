package android.telephony.data;

/* loaded from: classes3.dex */
public interface IDataService extends android.os.IInterface {
    void cancelHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void createDataServiceProvider(int i) throws android.os.RemoteException;

    void deactivateDataCall(int i, int i2, int i3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void registerForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void registerForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void removeDataServiceProvider(int i) throws android.os.RemoteException;

    void requestDataCallList(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void requestNetworkValidation(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException;

    void setDataProfile(int i, java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void setInitialAttachApn(int i, android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void setupDataCall(int i, int i2, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i3, android.net.LinkProperties linkProperties, int i4, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void startHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void unregisterForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    void unregisterForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException;

    public static class Default implements android.telephony.data.IDataService {
        @Override // android.telephony.data.IDataService
        public void createDataServiceProvider(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void removeDataServiceProvider(int i) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setupDataCall(int i, int i2, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i3, android.net.LinkProperties linkProperties, int i4, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void deactivateDataCall(int i, int i2, int i3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setInitialAttachApn(int i, android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void setDataProfile(int i, java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void requestDataCallList(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void registerForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void startHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void cancelHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void registerForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void unregisterForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
        }

        @Override // android.telephony.data.IDataService
        public void requestNetworkValidation(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.data.IDataService {
        public static final java.lang.String DESCRIPTOR = "android.telephony.data.IDataService";
        static final int TRANSACTION_cancelHandover = 11;
        static final int TRANSACTION_createDataServiceProvider = 1;
        static final int TRANSACTION_deactivateDataCall = 4;
        static final int TRANSACTION_registerForDataCallListChanged = 8;
        static final int TRANSACTION_registerForUnthrottleApn = 12;
        static final int TRANSACTION_removeDataServiceProvider = 2;
        static final int TRANSACTION_requestDataCallList = 7;
        static final int TRANSACTION_requestNetworkValidation = 14;
        static final int TRANSACTION_setDataProfile = 6;
        static final int TRANSACTION_setInitialAttachApn = 5;
        static final int TRANSACTION_setupDataCall = 3;
        static final int TRANSACTION_startHandover = 10;
        static final int TRANSACTION_unregisterForDataCallListChanged = 9;
        static final int TRANSACTION_unregisterForUnthrottleApn = 13;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.data.IDataService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.data.IDataService)) {
                return (android.telephony.data.IDataService) queryLocalInterface;
            }
            return new android.telephony.data.IDataService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createDataServiceProvider";
                case 2:
                    return "removeDataServiceProvider";
                case 3:
                    return "setupDataCall";
                case 4:
                    return "deactivateDataCall";
                case 5:
                    return "setInitialAttachApn";
                case 6:
                    return "setDataProfile";
                case 7:
                    return "requestDataCallList";
                case 8:
                    return "registerForDataCallListChanged";
                case 9:
                    return "unregisterForDataCallListChanged";
                case 10:
                    return "startHandover";
                case 11:
                    return "cancelHandover";
                case 12:
                    return "registerForUnthrottleApn";
                case 13:
                    return "unregisterForUnthrottleApn";
                case 14:
                    return "requestNetworkValidation";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    createDataServiceProvider(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeDataServiceProvider(readInt2);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    android.telephony.data.DataProfile dataProfile = (android.telephony.data.DataProfile) parcel.readTypedObject(android.telephony.data.DataProfile.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    android.net.LinkProperties linkProperties = (android.net.LinkProperties) parcel.readTypedObject(android.net.LinkProperties.CREATOR);
                    int readInt6 = parcel.readInt();
                    android.telephony.data.NetworkSliceInfo networkSliceInfo = (android.telephony.data.NetworkSliceInfo) parcel.readTypedObject(android.telephony.data.NetworkSliceInfo.CREATOR);
                    android.telephony.data.TrafficDescriptor trafficDescriptor = (android.telephony.data.TrafficDescriptor) parcel.readTypedObject(android.telephony.data.TrafficDescriptor.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    android.telephony.data.IDataServiceCallback asInterface = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setupDataCall(readInt3, readInt4, dataProfile, readBoolean, readBoolean2, readInt5, linkProperties, readInt6, networkSliceInfo, trafficDescriptor, readBoolean3, asInterface);
                    return true;
                case 4:
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface2 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deactivateDataCall(readInt7, readInt8, readInt9, asInterface2);
                    return true;
                case 5:
                    int readInt10 = parcel.readInt();
                    android.telephony.data.DataProfile dataProfile2 = (android.telephony.data.DataProfile) parcel.readTypedObject(android.telephony.data.DataProfile.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    android.telephony.data.IDataServiceCallback asInterface3 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setInitialAttachApn(readInt10, dataProfile2, readBoolean4, asInterface3);
                    return true;
                case 6:
                    int readInt11 = parcel.readInt();
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.telephony.data.DataProfile.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    android.telephony.data.IDataServiceCallback asInterface4 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setDataProfile(readInt11, createTypedArrayList, readBoolean5, asInterface4);
                    return true;
                case 7:
                    int readInt12 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface5 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestDataCallList(readInt12, asInterface5);
                    return true;
                case 8:
                    int readInt13 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface6 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForDataCallListChanged(readInt13, asInterface6);
                    return true;
                case 9:
                    int readInt14 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface7 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForDataCallListChanged(readInt14, asInterface7);
                    return true;
                case 10:
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface8 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    startHandover(readInt15, readInt16, asInterface8);
                    return true;
                case 11:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface9 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    cancelHandover(readInt17, readInt18, asInterface9);
                    return true;
                case 12:
                    int readInt19 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface10 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForUnthrottleApn(readInt19, asInterface10);
                    return true;
                case 13:
                    int readInt20 = parcel.readInt();
                    android.telephony.data.IDataServiceCallback asInterface11 = android.telephony.data.IDataServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterForUnthrottleApn(readInt20, asInterface11);
                    return true;
                case 14:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    com.android.internal.telephony.IIntegerConsumer asInterface12 = com.android.internal.telephony.IIntegerConsumer.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestNetworkValidation(readInt21, readInt22, asInterface12);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.data.IDataService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.data.IDataService.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.data.IDataService
            public void createDataServiceProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void removeDataServiceProvider(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setupDataCall(int i, int i2, android.telephony.data.DataProfile dataProfile, boolean z, boolean z2, int i3, android.net.LinkProperties linkProperties, int i4, android.telephony.data.NetworkSliceInfo networkSliceInfo, android.telephony.data.TrafficDescriptor trafficDescriptor, boolean z3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(dataProfile, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    obtain.writeInt(i3);
                    obtain.writeTypedObject(linkProperties, 0);
                    obtain.writeInt(i4);
                    obtain.writeTypedObject(networkSliceInfo, 0);
                    obtain.writeTypedObject(trafficDescriptor, 0);
                    obtain.writeBoolean(z3);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void deactivateDataCall(int i, int i2, int i3, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setInitialAttachApn(int i, android.telephony.data.DataProfile dataProfile, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(dataProfile, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void setDataProfile(int i, java.util.List<android.telephony.data.DataProfile> list, boolean z, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestDataCallList(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForDataCallListChanged(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void startHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void cancelHandover(int i, int i2, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void registerForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void unregisterForUnthrottleApn(int i, android.telephony.data.IDataServiceCallback iDataServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iDataServiceCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.data.IDataService
            public void requestNetworkValidation(int i, int i2, com.android.internal.telephony.IIntegerConsumer iIntegerConsumer) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.telephony.data.IDataService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iIntegerConsumer);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 13;
        }
    }
}
