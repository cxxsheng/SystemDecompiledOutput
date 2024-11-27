package com.android.ims.internal;

/* loaded from: classes4.dex */
public interface IImsVideoCallProvider extends android.os.IInterface {
    void requestCallDataUsage() throws android.os.RemoteException;

    void requestCameraCapabilities() throws android.os.RemoteException;

    void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException;

    void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException;

    void setCallback(com.android.ims.internal.IImsVideoCallCallback iImsVideoCallCallback) throws android.os.RemoteException;

    void setCamera(java.lang.String str, int i) throws android.os.RemoteException;

    void setDeviceOrientation(int i) throws android.os.RemoteException;

    void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException;

    void setPauseImage(android.net.Uri uri) throws android.os.RemoteException;

    void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException;

    void setZoom(float f) throws android.os.RemoteException;

    public static class Default implements com.android.ims.internal.IImsVideoCallProvider {
        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCallback(com.android.ims.internal.IImsVideoCallCallback iImsVideoCallCallback) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setCamera(java.lang.String str, int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setDeviceOrientation(int i) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setZoom(float f) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCameraCapabilities() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void requestCallDataUsage() throws android.os.RemoteException {
        }

        @Override // com.android.ims.internal.IImsVideoCallProvider
        public void setPauseImage(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.ims.internal.IImsVideoCallProvider {
        public static final java.lang.String DESCRIPTOR = "com.android.ims.internal.IImsVideoCallProvider";
        static final int TRANSACTION_requestCallDataUsage = 10;
        static final int TRANSACTION_requestCameraCapabilities = 9;
        static final int TRANSACTION_sendSessionModifyRequest = 7;
        static final int TRANSACTION_sendSessionModifyResponse = 8;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setCamera = 2;
        static final int TRANSACTION_setDeviceOrientation = 5;
        static final int TRANSACTION_setDisplaySurface = 4;
        static final int TRANSACTION_setPauseImage = 11;
        static final int TRANSACTION_setPreviewSurface = 3;
        static final int TRANSACTION_setZoom = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.ims.internal.IImsVideoCallProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.ims.internal.IImsVideoCallProvider)) {
                return (com.android.ims.internal.IImsVideoCallProvider) queryLocalInterface;
            }
            return new com.android.ims.internal.IImsVideoCallProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setCallback";
                case 2:
                    return "setCamera";
                case 3:
                    return "setPreviewSurface";
                case 4:
                    return "setDisplaySurface";
                case 5:
                    return "setDeviceOrientation";
                case 6:
                    return "setZoom";
                case 7:
                    return "sendSessionModifyRequest";
                case 8:
                    return "sendSessionModifyResponse";
                case 9:
                    return "requestCameraCapabilities";
                case 10:
                    return "requestCallDataUsage";
                case 11:
                    return "setPauseImage";
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
                    com.android.ims.internal.IImsVideoCallCallback asInterface = com.android.ims.internal.IImsVideoCallCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    return true;
                case 2:
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCamera(readString, readInt);
                    return true;
                case 3:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(surface);
                    return true;
                case 4:
                    android.view.Surface surface2 = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(surface2);
                    return true;
                case 5:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(readInt2);
                    return true;
                case 6:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(readFloat);
                    return true;
                case 7:
                    android.telecom.VideoProfile videoProfile = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    android.telecom.VideoProfile videoProfile2 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyRequest(videoProfile, videoProfile2);
                    return true;
                case 8:
                    android.telecom.VideoProfile videoProfile3 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyResponse(videoProfile3);
                    return true;
                case 9:
                    requestCameraCapabilities();
                    return true;
                case 10:
                    requestCallDataUsage();
                    return true;
                case 11:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPauseImage(uri);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.ims.internal.IImsVideoCallProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setCallback(com.android.ims.internal.IImsVideoCallCallback iImsVideoCallCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsVideoCallCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setCamera(java.lang.String str, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setDeviceOrientation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setZoom(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    obtain.writeTypedObject(videoProfile2, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void requestCameraCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void requestCallDataUsage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsVideoCallProvider
            public void setPauseImage(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.ims.internal.IImsVideoCallProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 10;
        }
    }
}
