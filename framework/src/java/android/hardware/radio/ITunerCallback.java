package android.hardware.radio;

/* loaded from: classes2.dex */
public interface ITunerCallback extends android.os.IInterface {
    void onAntennaState(boolean z) throws android.os.RemoteException;

    void onBackgroundScanAvailabilityChange(boolean z) throws android.os.RemoteException;

    void onBackgroundScanComplete() throws android.os.RemoteException;

    void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException;

    void onConfigurationChanged(android.hardware.radio.RadioManager.BandConfig bandConfig) throws android.os.RemoteException;

    void onCurrentProgramInfoChanged(android.hardware.radio.RadioManager.ProgramInfo programInfo) throws android.os.RemoteException;

    void onEmergencyAnnouncement(boolean z) throws android.os.RemoteException;

    void onError(int i) throws android.os.RemoteException;

    void onParametersUpdated(java.util.Map<java.lang.String, java.lang.String> map) throws android.os.RemoteException;

    void onProgramListChanged() throws android.os.RemoteException;

    void onProgramListUpdated(android.hardware.radio.ProgramList.Chunk chunk) throws android.os.RemoteException;

    void onTrafficAnnouncement(boolean z) throws android.os.RemoteException;

    void onTuneFailed(int i, android.hardware.radio.ProgramSelector programSelector) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.ITunerCallback {
        @Override // android.hardware.radio.ITunerCallback
        public void onError(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onTuneFailed(int i, android.hardware.radio.ProgramSelector programSelector) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onConfigurationChanged(android.hardware.radio.RadioManager.BandConfig bandConfig) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onCurrentProgramInfoChanged(android.hardware.radio.RadioManager.ProgramInfo programInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onTrafficAnnouncement(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onEmergencyAnnouncement(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onAntennaState(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onBackgroundScanAvailabilityChange(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onBackgroundScanComplete() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onProgramListChanged() throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onProgramListUpdated(android.hardware.radio.ProgramList.Chunk chunk) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.radio.ITunerCallback
        public void onParametersUpdated(java.util.Map<java.lang.String, java.lang.String> map) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.ITunerCallback {
        public static final java.lang.String DESCRIPTOR = "android.hardware.radio.ITunerCallback";
        static final int TRANSACTION_onAntennaState = 7;
        static final int TRANSACTION_onBackgroundScanAvailabilityChange = 8;
        static final int TRANSACTION_onBackgroundScanComplete = 9;
        static final int TRANSACTION_onConfigFlagUpdated = 12;
        static final int TRANSACTION_onConfigurationChanged = 3;
        static final int TRANSACTION_onCurrentProgramInfoChanged = 4;
        static final int TRANSACTION_onEmergencyAnnouncement = 6;
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onParametersUpdated = 13;
        static final int TRANSACTION_onProgramListChanged = 10;
        static final int TRANSACTION_onProgramListUpdated = 11;
        static final int TRANSACTION_onTrafficAnnouncement = 5;
        static final int TRANSACTION_onTuneFailed = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.ITunerCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.ITunerCallback)) {
                return (android.hardware.radio.ITunerCallback) queryLocalInterface;
            }
            return new android.hardware.radio.ITunerCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onError";
                case 2:
                    return "onTuneFailed";
                case 3:
                    return "onConfigurationChanged";
                case 4:
                    return "onCurrentProgramInfoChanged";
                case 5:
                    return "onTrafficAnnouncement";
                case 6:
                    return "onEmergencyAnnouncement";
                case 7:
                    return "onAntennaState";
                case 8:
                    return "onBackgroundScanAvailabilityChange";
                case 9:
                    return "onBackgroundScanComplete";
                case 10:
                    return "onProgramListChanged";
                case 11:
                    return "onProgramListUpdated";
                case 12:
                    return "onConfigFlagUpdated";
                case 13:
                    return "onParametersUpdated";
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
                    onError(readInt);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    android.hardware.radio.ProgramSelector programSelector = (android.hardware.radio.ProgramSelector) parcel.readTypedObject(android.hardware.radio.ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTuneFailed(readInt2, programSelector);
                    return true;
                case 3:
                    android.hardware.radio.RadioManager.BandConfig bandConfig = (android.hardware.radio.RadioManager.BandConfig) parcel.readTypedObject(android.hardware.radio.RadioManager.BandConfig.CREATOR);
                    parcel.enforceNoDataAvail();
                    onConfigurationChanged(bandConfig);
                    return true;
                case 4:
                    android.hardware.radio.RadioManager.ProgramInfo programInfo = (android.hardware.radio.RadioManager.ProgramInfo) parcel.readTypedObject(android.hardware.radio.RadioManager.ProgramInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCurrentProgramInfoChanged(programInfo);
                    return true;
                case 5:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onTrafficAnnouncement(readBoolean);
                    return true;
                case 6:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onEmergencyAnnouncement(readBoolean2);
                    return true;
                case 7:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onAntennaState(readBoolean3);
                    return true;
                case 8:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onBackgroundScanAvailabilityChange(readBoolean4);
                    return true;
                case 9:
                    onBackgroundScanComplete();
                    return true;
                case 10:
                    onProgramListChanged();
                    return true;
                case 11:
                    android.hardware.radio.ProgramList.Chunk chunk = (android.hardware.radio.ProgramList.Chunk) parcel.readTypedObject(android.hardware.radio.ProgramList.Chunk.CREATOR);
                    parcel.enforceNoDataAvail();
                    onProgramListUpdated(chunk);
                    return true;
                case 12:
                    int readInt3 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onConfigFlagUpdated(readInt3, readBoolean5);
                    return true;
                case 13:
                    int readInt4 = parcel.readInt();
                    final java.util.HashMap hashMap = readInt4 < 0 ? null : new java.util.HashMap();
                    java.util.stream.IntStream.range(0, readInt4).forEach(new java.util.function.IntConsumer() { // from class: android.hardware.radio.ITunerCallback$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), android.os.Parcel.this.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    onParametersUpdated(hashMap);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements android.hardware.radio.ITunerCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onError(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onTuneFailed(int i, android.hardware.radio.ProgramSelector programSelector) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(programSelector, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onConfigurationChanged(android.hardware.radio.RadioManager.BandConfig bandConfig) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bandConfig, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onCurrentProgramInfoChanged(android.hardware.radio.RadioManager.ProgramInfo programInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(programInfo, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onTrafficAnnouncement(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onEmergencyAnnouncement(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onAntennaState(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onBackgroundScanAvailabilityChange(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onBackgroundScanComplete() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onProgramListChanged() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onProgramListUpdated(android.hardware.radio.ProgramList.Chunk chunk) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(chunk, 0);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onConfigFlagUpdated(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.ITunerCallback
            public void onParametersUpdated(java.util.Map<java.lang.String, java.lang.String> map) throws android.os.RemoteException {
                final android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.ITunerCallback.Stub.DESCRIPTOR);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new java.util.function.BiConsumer() { // from class: android.hardware.radio.ITunerCallback$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(java.lang.Object obj, java.lang.Object obj2) {
                                android.hardware.radio.ITunerCallback.Stub.Proxy.lambda$onParametersUpdated$0(android.os.Parcel.this, (java.lang.String) obj, (java.lang.String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$onParametersUpdated$0(android.os.Parcel parcel, java.lang.String str, java.lang.String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }
    }
}
