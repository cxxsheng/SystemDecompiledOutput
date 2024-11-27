package com.android.internal.telecom;

/* loaded from: classes5.dex */
public interface IVideoProvider extends android.os.IInterface {
    void addVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException;

    void removeVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException;

    void requestCallDataUsage() throws android.os.RemoteException;

    void requestCameraCapabilities() throws android.os.RemoteException;

    void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException;

    void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException;

    void setCamera(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException;

    void setDeviceOrientation(int i) throws android.os.RemoteException;

    void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException;

    void setPauseImage(android.net.Uri uri) throws android.os.RemoteException;

    void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException;

    void setZoom(float f) throws android.os.RemoteException;

    public static class Default implements com.android.internal.telecom.IVideoProvider {
        @Override // com.android.internal.telecom.IVideoProvider
        public void addVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void removeVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setCamera(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setDeviceOrientation(int i) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setZoom(float f) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void requestCameraCapabilities() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void requestCallDataUsage() throws android.os.RemoteException {
        }

        @Override // com.android.internal.telecom.IVideoProvider
        public void setPauseImage(android.net.Uri uri) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.telecom.IVideoProvider {
        public static final java.lang.String DESCRIPTOR = "com.android.internal.telecom.IVideoProvider";
        static final int TRANSACTION_addVideoCallback = 1;
        static final int TRANSACTION_removeVideoCallback = 2;
        static final int TRANSACTION_requestCallDataUsage = 11;
        static final int TRANSACTION_requestCameraCapabilities = 10;
        static final int TRANSACTION_sendSessionModifyRequest = 8;
        static final int TRANSACTION_sendSessionModifyResponse = 9;
        static final int TRANSACTION_setCamera = 3;
        static final int TRANSACTION_setDeviceOrientation = 6;
        static final int TRANSACTION_setDisplaySurface = 5;
        static final int TRANSACTION_setPauseImage = 12;
        static final int TRANSACTION_setPreviewSurface = 4;
        static final int TRANSACTION_setZoom = 7;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static com.android.internal.telecom.IVideoProvider asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.telecom.IVideoProvider)) {
                return (com.android.internal.telecom.IVideoProvider) queryLocalInterface;
            }
            return new com.android.internal.telecom.IVideoProvider.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addVideoCallback";
                case 2:
                    return "removeVideoCallback";
                case 3:
                    return "setCamera";
                case 4:
                    return "setPreviewSurface";
                case 5:
                    return "setDisplaySurface";
                case 6:
                    return "setDeviceOrientation";
                case 7:
                    return "setZoom";
                case 8:
                    return "sendSessionModifyRequest";
                case 9:
                    return "sendSessionModifyResponse";
                case 10:
                    return "requestCameraCapabilities";
                case 11:
                    return "requestCallDataUsage";
                case 12:
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
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    addVideoCallback(readStrongBinder);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    removeVideoCallback(readStrongBinder2);
                    return true;
                case 3:
                    java.lang.String readString = parcel.readString();
                    java.lang.String readString2 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setCamera(readString, readString2, readInt);
                    return true;
                case 4:
                    android.view.Surface surface = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPreviewSurface(surface);
                    return true;
                case 5:
                    android.view.Surface surface2 = (android.view.Surface) parcel.readTypedObject(android.view.Surface.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDisplaySurface(surface2);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setDeviceOrientation(readInt2);
                    return true;
                case 7:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setZoom(readFloat);
                    return true;
                case 8:
                    android.telecom.VideoProfile videoProfile = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    android.telecom.VideoProfile videoProfile2 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyRequest(videoProfile, videoProfile2);
                    return true;
                case 9:
                    android.telecom.VideoProfile videoProfile3 = (android.telecom.VideoProfile) parcel.readTypedObject(android.telecom.VideoProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendSessionModifyResponse(videoProfile3);
                    return true;
                case 10:
                    requestCameraCapabilities();
                    return true;
                case 11:
                    requestCallDataUsage();
                    return true;
                case 12:
                    android.net.Uri uri = (android.net.Uri) parcel.readTypedObject(android.net.Uri.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPauseImage(uri);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.telecom.IVideoProvider {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void addVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void removeVideoCallback(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setCamera(java.lang.String str, java.lang.String str2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPreviewSurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDisplaySurface(android.view.Surface surface) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(surface, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setDeviceOrientation(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setZoom(float f) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeFloat(f);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyRequest(android.telecom.VideoProfile videoProfile, android.telecom.VideoProfile videoProfile2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    obtain.writeTypedObject(videoProfile2, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void sendSessionModifyResponse(android.telecom.VideoProfile videoProfile) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(videoProfile, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCameraCapabilities() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void requestCallDataUsage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.IVideoProvider
            public void setPauseImage(android.net.Uri uri) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(com.android.internal.telecom.IVideoProvider.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(uri, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }
    }
}
