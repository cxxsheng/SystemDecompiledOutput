package android.hardware.camera2.extension;

/* loaded from: classes.dex */
public interface ISessionProcessorImpl extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.hardware.camera2.extension.ISessionProcessorImpl";

    void deInitSession(android.os.IBinder iBinder) throws android.os.RemoteException;

    android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException;

    android.hardware.camera2.extension.CameraSessionConfig initSession(android.os.IBinder iBinder, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map, android.hardware.camera2.extension.OutputSurface outputSurface, android.hardware.camera2.extension.OutputSurface outputSurface2, android.hardware.camera2.extension.OutputSurface outputSurface3) throws android.os.RemoteException;

    void onCaptureSessionEnd() throws android.os.RemoteException;

    void onCaptureSessionStart(android.hardware.camera2.extension.IRequestProcessorImpl iRequestProcessorImpl, java.lang.String str) throws android.os.RemoteException;

    void setParameters(android.hardware.camera2.CaptureRequest captureRequest) throws android.os.RemoteException;

    int startCapture(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback, boolean z) throws android.os.RemoteException;

    int startRepeating(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException;

    int startTrigger(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException;

    void stopRepeating() throws android.os.RemoteException;

    public static class Default implements android.hardware.camera2.extension.ISessionProcessorImpl {
        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public android.hardware.camera2.extension.CameraSessionConfig initSession(android.os.IBinder iBinder, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map, android.hardware.camera2.extension.OutputSurface outputSurface, android.hardware.camera2.extension.OutputSurface outputSurface2, android.hardware.camera2.extension.OutputSurface outputSurface3) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void deInitSession(android.os.IBinder iBinder) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionStart(android.hardware.camera2.extension.IRequestProcessorImpl iRequestProcessorImpl, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void onCaptureSessionEnd() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startRepeating(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void stopRepeating() throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startCapture(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback, boolean z) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public void setParameters(android.hardware.camera2.CaptureRequest captureRequest) throws android.os.RemoteException {
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public int startTrigger(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.hardware.camera2.extension.ISessionProcessorImpl
        public android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.camera2.extension.ISessionProcessorImpl {
        static final int TRANSACTION_deInitSession = 2;
        static final int TRANSACTION_getRealtimeCaptureLatency = 10;
        static final int TRANSACTION_initSession = 1;
        static final int TRANSACTION_onCaptureSessionEnd = 4;
        static final int TRANSACTION_onCaptureSessionStart = 3;
        static final int TRANSACTION_setParameters = 8;
        static final int TRANSACTION_startCapture = 7;
        static final int TRANSACTION_startRepeating = 5;
        static final int TRANSACTION_startTrigger = 9;
        static final int TRANSACTION_stopRepeating = 6;

        public Stub() {
            attachInterface(this, android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
        }

        public static android.hardware.camera2.extension.ISessionProcessorImpl asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.camera2.extension.ISessionProcessorImpl)) {
                return (android.hardware.camera2.extension.ISessionProcessorImpl) queryLocalInterface;
            }
            return new android.hardware.camera2.extension.ISessionProcessorImpl.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "initSession";
                case 2:
                    return "deInitSession";
                case 3:
                    return "onCaptureSessionStart";
                case 4:
                    return "onCaptureSessionEnd";
                case 5:
                    return "startRepeating";
                case 6:
                    return "stopRepeating";
                case 7:
                    return "startCapture";
                case 8:
                    return "setParameters";
                case 9:
                    return "startTrigger";
                case 10:
                    return "getRealtimeCaptureLatency";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public java.lang.String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.os.IBinder readStrongBinder = parcel.readStrongBinder();
                    java.lang.String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    final java.util.HashMap hashMap = readInt < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt).forEach(new java.util.function.IntConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), (android.hardware.camera2.impl.CameraMetadataNative) android.os.Parcel.this.readTypedObject(android.hardware.camera2.impl.CameraMetadataNative.CREATOR));
                        }
                    });
                    android.hardware.camera2.extension.OutputSurface outputSurface = (android.hardware.camera2.extension.OutputSurface) parcel.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                    android.hardware.camera2.extension.OutputSurface outputSurface2 = (android.hardware.camera2.extension.OutputSurface) parcel.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                    android.hardware.camera2.extension.OutputSurface outputSurface3 = (android.hardware.camera2.extension.OutputSurface) parcel.readTypedObject(android.hardware.camera2.extension.OutputSurface.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.camera2.extension.CameraSessionConfig initSession = initSession(readStrongBinder, readString, hashMap, outputSurface, outputSurface2, outputSurface3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(initSession, 1);
                    return true;
                case 2:
                    android.os.IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    deInitSession(readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.camera2.extension.IRequestProcessorImpl asInterface = android.hardware.camera2.extension.IRequestProcessorImpl.Stub.asInterface(parcel.readStrongBinder());
                    java.lang.String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onCaptureSessionStart(asInterface, readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    onCaptureSessionEnd();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    android.hardware.camera2.extension.ICaptureCallback asInterface2 = android.hardware.camera2.extension.ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startRepeating = startRepeating(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startRepeating);
                    return true;
                case 6:
                    stopRepeating();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    android.hardware.camera2.extension.ICaptureCallback asInterface3 = android.hardware.camera2.extension.ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startCapture = startCapture(asInterface3, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startCapture);
                    return true;
                case 8:
                    android.hardware.camera2.CaptureRequest captureRequest = (android.hardware.camera2.CaptureRequest) parcel.readTypedObject(android.hardware.camera2.CaptureRequest.CREATOR);
                    parcel.enforceNoDataAvail();
                    setParameters(captureRequest);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.hardware.camera2.CaptureRequest captureRequest2 = (android.hardware.camera2.CaptureRequest) parcel.readTypedObject(android.hardware.camera2.CaptureRequest.CREATOR);
                    android.hardware.camera2.extension.ICaptureCallback asInterface4 = android.hardware.camera2.extension.ICaptureCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startTrigger = startTrigger(captureRequest2, asInterface4);
                    parcel2.writeNoException();
                    parcel2.writeInt(startTrigger);
                    return true;
                case 10:
                    android.hardware.camera2.extension.LatencyPair realtimeCaptureLatency = getRealtimeCaptureLatency();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(realtimeCaptureLatency, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.hardware.camera2.extension.ISessionProcessorImpl {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR;
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public android.hardware.camera2.extension.CameraSessionConfig initSession(android.os.IBinder iBinder, java.lang.String str, java.util.Map<java.lang.String, android.hardware.camera2.impl.CameraMetadataNative> map, android.hardware.camera2.extension.OutputSurface outputSurface, android.hardware.camera2.extension.OutputSurface outputSurface2, android.hardware.camera2.extension.OutputSurface outputSurface3) throws android.os.RemoteException {
                final android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new java.util.function.BiConsumer() { // from class: android.hardware.camera2.extension.ISessionProcessorImpl$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.hardware.camera2.extension.ISessionProcessorImpl.Stub.Proxy.lambda$initSession$0(android.os.Parcel.this, (java.lang.String) obj, (android.hardware.camera2.impl.CameraMetadataNative) obj2);
                            }
                        });
                    }
                    obtain.writeTypedObject(outputSurface, 0);
                    obtain.writeTypedObject(outputSurface2, 0);
                    obtain.writeTypedObject(outputSurface3, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.CameraSessionConfig) obtain2.readTypedObject(android.hardware.camera2.extension.CameraSessionConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$initSession$0(android.os.Parcel parcel, java.lang.String str, android.hardware.camera2.impl.CameraMetadataNative cameraMetadataNative) {
                parcel.writeString(str);
                parcel.writeTypedObject(cameraMetadataNative, 0);
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void deInitSession(android.os.IBinder iBinder) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionStart(android.hardware.camera2.extension.IRequestProcessorImpl iRequestProcessorImpl, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iRequestProcessorImpl);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void onCaptureSessionEnd() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startRepeating(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void stopRepeating() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startCapture(android.hardware.camera2.extension.ICaptureCallback iCaptureCallback, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeStrongInterface(iCaptureCallback);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public void setParameters(android.hardware.camera2.CaptureRequest captureRequest) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public int startTrigger(android.hardware.camera2.CaptureRequest captureRequest, android.hardware.camera2.extension.ICaptureCallback iCaptureCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    obtain.writeTypedObject(captureRequest, 0);
                    obtain.writeStrongInterface(iCaptureCallback);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.camera2.extension.ISessionProcessorImpl
            public android.hardware.camera2.extension.LatencyPair getRealtimeCaptureLatency() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.camera2.extension.ISessionProcessorImpl.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.hardware.camera2.extension.LatencyPair) obtain2.readTypedObject(android.hardware.camera2.extension.LatencyPair.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }
    }
}
