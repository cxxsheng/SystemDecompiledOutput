package android.telephony.mbms.vendor;

/* loaded from: classes3.dex */
public interface IMbmsStreamingService extends android.os.IInterface {
    void dispose(int i) throws android.os.RemoteException;

    android.net.Uri getPlaybackUri(int i, java.lang.String str) throws android.os.RemoteException;

    int initialize(android.telephony.mbms.IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws android.os.RemoteException;

    int requestUpdateStreamingServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException;

    int startStreaming(int i, java.lang.String str, android.telephony.mbms.IStreamingServiceCallback iStreamingServiceCallback) throws android.os.RemoteException;

    void stopStreaming(int i, java.lang.String str) throws android.os.RemoteException;

    public static class Default implements android.telephony.mbms.vendor.IMbmsStreamingService {
        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int initialize(android.telephony.mbms.IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int requestUpdateStreamingServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public int startStreaming(int i, java.lang.String str, android.telephony.mbms.IStreamingServiceCallback iStreamingServiceCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public android.net.Uri getPlaybackUri(int i, java.lang.String str) throws android.os.RemoteException {
            return null;
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public void stopStreaming(int i, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.telephony.mbms.vendor.IMbmsStreamingService
        public void dispose(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.telephony.mbms.vendor.IMbmsStreamingService {
        public static final java.lang.String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsStreamingService";
        static final int TRANSACTION_dispose = 6;
        static final int TRANSACTION_getPlaybackUri = 4;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_requestUpdateStreamingServices = 2;
        static final int TRANSACTION_startStreaming = 3;
        static final int TRANSACTION_stopStreaming = 5;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.telephony.mbms.vendor.IMbmsStreamingService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.telephony.mbms.vendor.IMbmsStreamingService)) {
                return (android.telephony.mbms.vendor.IMbmsStreamingService) queryLocalInterface;
            }
            return new android.telephony.mbms.vendor.IMbmsStreamingService.Stub.Proxy(iBinder);
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
                    return "requestUpdateStreamingServices";
                case 3:
                    return "startStreaming";
                case 4:
                    return "getPlaybackUri";
                case 5:
                    return "stopStreaming";
                case 6:
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.telephony.mbms.IMbmsStreamingSessionCallback asInterface = android.telephony.mbms.IMbmsStreamingSessionCallback.Stub.asInterface(parcel.readStrongBinder());
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialize = initialize(asInterface, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialize);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    java.util.ArrayList<java.lang.String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int requestUpdateStreamingServices = requestUpdateStreamingServices(readInt2, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(requestUpdateStreamingServices);
                    return true;
                case 3:
                    int readInt3 = parcel.readInt();
                    java.lang.String readString = parcel.readString();
                    android.telephony.mbms.IStreamingServiceCallback asInterface2 = android.telephony.mbms.IStreamingServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startStreaming = startStreaming(readInt3, readString, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startStreaming);
                    return true;
                case 4:
                    int readInt4 = parcel.readInt();
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    android.net.Uri playbackUri = getPlaybackUri(readInt4, readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(playbackUri, 1);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    java.lang.String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    stopStreaming(readInt5, readString3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    dispose(readInt6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.telephony.mbms.vendor.IMbmsStreamingService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR;
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int initialize(android.telephony.mbms.IMbmsStreamingSessionCallback iMbmsStreamingSessionCallback, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iMbmsStreamingSessionCallback);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int requestUpdateStreamingServices(int i, java.util.List<java.lang.String> list) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public int startStreaming(int i, java.lang.String str, android.telephony.mbms.IStreamingServiceCallback iStreamingServiceCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iStreamingServiceCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public android.net.Uri getPlaybackUri(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.net.Uri) obtain2.readTypedObject(android.net.Uri.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public void stopStreaming(int i, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.mbms.vendor.IMbmsStreamingService
            public void dispose(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.telephony.mbms.vendor.IMbmsStreamingService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
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
