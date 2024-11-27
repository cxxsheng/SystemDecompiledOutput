package android.service.carrier;

/* loaded from: classes3.dex */
public interface ICarrierMessagingService extends android.os.IInterface {
    void downloadMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    void filterSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    void sendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    void sendMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    void sendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    void sendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException;

    public static class Default implements android.service.carrier.ICarrierMessagingService {
        @Override // android.service.carrier.ICarrierMessagingService
        public void filterSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void sendMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingService
        public void downloadMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.carrier.ICarrierMessagingService {
        public static final java.lang.String DESCRIPTOR = "android.service.carrier.ICarrierMessagingService";
        static final int TRANSACTION_downloadMms = 6;
        static final int TRANSACTION_filterSms = 1;
        static final int TRANSACTION_sendDataSms = 3;
        static final int TRANSACTION_sendMms = 5;
        static final int TRANSACTION_sendMultipartTextSms = 4;
        static final int TRANSACTION_sendTextSms = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.carrier.ICarrierMessagingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.carrier.ICarrierMessagingService)) {
                return (android.service.carrier.ICarrierMessagingService) queryLocalInterface;
            }
            return new android.service.carrier.ICarrierMessagingService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "filterSms";
                case 2:
                    return "sendTextSms";
                case 3:
                    return "sendDataSms";
                case 4:
                    return "sendMultipartTextSms";
                case 5:
                    return "sendMms";
                case 6:
                    return "downloadMms";
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
                    android.service.carrier.MessagePdu messagePdu = (android.service.carrier.MessagePdu) parcel.readTypedObject(android.service.carrier.MessagePdu.CREATOR);
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    android.service.carrier.ICarrierMessagingCallback asInterface = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    filterSms(messagePdu, readString, readInt, readInt2, asInterface);
                    return true;
                case 2:
                    java.lang.String readString2 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    android.service.carrier.ICarrierMessagingCallback asInterface2 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendTextSms(readString2, readInt3, readString3, readInt4, asInterface2);
                    return true;
                case 3:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt5 = parcel.readInt();
                    java.lang.String readString4 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    android.service.carrier.ICarrierMessagingCallback asInterface3 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendDataSms(createByteArray, readInt5, readString4, readInt6, readInt7, asInterface3);
                    return true;
                case 4:
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    int readInt8 = parcel.readInt();
                    java.lang.String readString5 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    android.service.carrier.ICarrierMessagingCallback asInterface4 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendMultipartTextSms(createStringArrayList, readInt8, readString5, readInt9, asInterface4);
                    return true;
                case 5:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt10 = parcel.readInt();
                    android.net.Uri uri2 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.service.carrier.ICarrierMessagingCallback asInterface5 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    sendMms(uri, readInt10, uri2, asInterface5);
                    return true;
                case 6:
                    android.net.Uri uri3 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    int readInt11 = parcel.readInt();
                    android.net.Uri uri4 = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    android.service.carrier.ICarrierMessagingCallback asInterface6 = android.service.carrier.ICarrierMessagingCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    downloadMms(uri3, readInt11, uri4, asInterface6);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.carrier.ICarrierMessagingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR;
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void filterSms(android.service.carrier.MessagePdu messagePdu, java.lang.String str, int i, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(messagePdu, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendTextSms(java.lang.String str, int i, java.lang.String str2, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendDataSms(byte[] bArr, int i, java.lang.String str, int i2, int i3, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendMultipartTextSms(java.util.List<java.lang.String> list, int i, java.lang.String str, int i2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void sendMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingService
            public void downloadMms(android.net.Uri uri, int i, android.net.Uri uri2, android.service.carrier.ICarrierMessagingCallback iCarrierMessagingCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.carrier.ICarrierMessagingService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri2, 0);
                    obtain.writeStrongInterface(iCarrierMessagingCallback);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
