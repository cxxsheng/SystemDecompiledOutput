package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsVideoCallCallback extends android.os.IInterface {
    void changeCallDataUsage(long j) throws android.os.RemoteException;

    void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) throws android.os.RemoteException;

    void changePeerDimensions(int i, int i2) throws android.os.RemoteException;

    void changeVideoQuality(int i) throws android.os.RemoteException;

    void handleCallSessionEvent(int i) throws android.os.RemoteException;

    void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException;

    void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsVideoCallCallback {
        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void handleCallSessionEvent(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void changePeerDimensions(int i, int i2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void changeCallDataUsage(long j) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallCallback
        public void changeVideoQuality(int i) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsVideoCallCallback {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsVideoCallCallback";
        static final int TRANSACTION_changeCallDataUsage = 5;
        static final int TRANSACTION_changeCameraCapabilities = 6;
        static final int TRANSACTION_changePeerDimensions = 4;
        static final int TRANSACTION_changeVideoQuality = 7;
        static final int TRANSACTION_handleCallSessionEvent = 3;
        static final int TRANSACTION_receiveSessionModifyRequest = 1;
        static final int TRANSACTION_receiveSessionModifyResponse = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsVideoCallCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsVideoCallCallback)) {
                return (com.android.ims.internal.IImsVideoCallCallback) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsVideoCallCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "receiveSessionModifyRequest";
                case 2:
                    return "receiveSessionModifyResponse";
                case 3:
                    return "handleCallSessionEvent";
                case 4:
                    return "changePeerDimensions";
                case 5:
                    return "changeCallDataUsage";
                case 6:
                    return "changeCameraCapabilities";
                case 7:
                    return "changeVideoQuality";
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
                    android.telecom.VideoProfile videoProfile = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyRequest(videoProfile);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.telecom.VideoProfile videoProfile2 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    android.telecom.VideoProfile videoProfile3 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    receiveSessionModifyResponse(readInt, videoProfile2, videoProfile3);
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleCallSessionEvent(readInt2);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changePeerDimensions(readInt3, readInt4);
                    return true;
                case 5:
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    changeCallDataUsage(readLong);
                    return true;
                case 6:
                    android.telecom.VideoProfile.CameraCapabilities cameraCapabilities = (android.telecom.VideoProfile.CameraCapabilities) parcel.readTypedObject(android.telecom.VideoProfile.CameraCapabilities.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeCameraCapabilities(cameraCapabilities);
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeVideoQuality(readInt5);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsVideoCallCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void receiveSessionModifyRequest(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void receiveSessionModifyResponse(int i, android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(videoProfile, 0);
                    obtain.writeTypedObject(videoProfile2, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void handleCallSessionEvent(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void changePeerDimensions(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void changeCallDataUsage(long j) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void changeCameraCapabilities(android.telecom.VideoProfile.CameraCapabilities cameraCapabilities) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(cameraCapabilities, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallCallback
            public void changeVideoQuality(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }
    }
}
