package android.hardware.broadcastradio;

/* loaded from: classes.dex */
public interface IBroadcastRadio extends android.os.IInterface {
    public static final int ANTENNA_STATE_CHANGE_TIMEOUT_MS = 100;
    public static final java.lang.String DESCRIPTOR = "android$hardware$broadcastradio$IBroadcastRadio".replace('$', '.');
    public static final java.lang.String HASH = "bff68a8bc8b7cc191ab62bee10f7df8e79494467";
    public static final int INVALID_IMAGE = 0;
    public static final int LIST_COMPLETE_TIMEOUT_MS = 300000;
    public static final int TUNER_TIMEOUT_MS = 30000;
    public static final int VERSION = 2;

    void cancel() throws android.os.RemoteException;

    android.hardware.broadcastradio.AmFmRegionConfig getAmFmRegionConfig(boolean z) throws android.os.RemoteException;

    android.hardware.broadcastradio.DabTableEntry[] getDabRegionConfig() throws android.os.RemoteException;

    byte[] getImage(int i) throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    android.hardware.broadcastradio.VendorKeyValue[] getParameters(java.lang.String[] strArr) throws android.os.RemoteException;

    android.hardware.broadcastradio.Properties getProperties() throws android.os.RemoteException;

    boolean isConfigFlagSet(int i) throws android.os.RemoteException;

    android.hardware.broadcastradio.ICloseHandle registerAnnouncementListener(android.hardware.broadcastradio.IAnnouncementListener iAnnouncementListener, byte[] bArr) throws android.os.RemoteException;

    void seek(boolean z, boolean z2) throws android.os.RemoteException;

    void setConfigFlag(int i, boolean z) throws android.os.RemoteException;

    android.hardware.broadcastradio.VendorKeyValue[] setParameters(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException;

    void setTunerCallback(android.hardware.broadcastradio.ITunerCallback iTunerCallback) throws android.os.RemoteException;

    void startProgramListUpdates(android.hardware.broadcastradio.ProgramFilter programFilter) throws android.os.RemoteException;

    void step(boolean z) throws android.os.RemoteException;

    void stopProgramListUpdates() throws android.os.RemoteException;

    void tune(android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException;

    void unsetTunerCallback() throws android.os.RemoteException;

    public static class Default implements android.hardware.broadcastradio.IBroadcastRadio {
        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.Properties getProperties() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.AmFmRegionConfig getAmFmRegionConfig(boolean z) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.DabTableEntry[] getDabRegionConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void setTunerCallback(android.hardware.broadcastradio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void unsetTunerCallback() throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void tune(android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void seek(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void step(boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void cancel() throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void startProgramListUpdates(android.hardware.broadcastradio.ProgramFilter programFilter) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void stopProgramListUpdates() throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public boolean isConfigFlagSet(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public void setConfigFlag(int i, boolean z) throws android.os.RemoteException {
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.VendorKeyValue[] setParameters(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.VendorKeyValue[] getParameters(java.lang.String[] strArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public byte[] getImage(int i) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public android.hardware.broadcastradio.ICloseHandle registerAnnouncementListener(android.hardware.broadcastradio.IAnnouncementListener iAnnouncementListener, byte[] bArr) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.broadcastradio.IBroadcastRadio
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.broadcastradio.IBroadcastRadio {
        static final int TRANSACTION_cancel = 9;
        static final int TRANSACTION_getAmFmRegionConfig = 2;
        static final int TRANSACTION_getDabRegionConfig = 3;
        static final int TRANSACTION_getImage = 16;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getParameters = 15;
        static final int TRANSACTION_getProperties = 1;
        static final int TRANSACTION_isConfigFlagSet = 12;
        static final int TRANSACTION_registerAnnouncementListener = 17;
        static final int TRANSACTION_seek = 7;
        static final int TRANSACTION_setConfigFlag = 13;
        static final int TRANSACTION_setParameters = 14;
        static final int TRANSACTION_setTunerCallback = 4;
        static final int TRANSACTION_startProgramListUpdates = 10;
        static final int TRANSACTION_step = 8;
        static final int TRANSACTION_stopProgramListUpdates = 11;
        static final int TRANSACTION_tune = 6;
        static final int TRANSACTION_unsetTunerCallback = 5;

        public Stub() {
            markVintfStability();
            attachInterface(this, android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
        }

        public static android.hardware.broadcastradio.IBroadcastRadio asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.broadcastradio.IBroadcastRadio)) {
                return (android.hardware.broadcastradio.IBroadcastRadio) queryLocalInterface;
            }
            return new android.hardware.broadcastradio.IBroadcastRadio.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            java.lang.String str = android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    android.hardware.broadcastradio.Properties properties = getProperties();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(properties, 1);
                    return true;
                case 2:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    android.hardware.broadcastradio.AmFmRegionConfig amFmRegionConfig = getAmFmRegionConfig(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(amFmRegionConfig, 1);
                    return true;
                case 3:
                    android.hardware.broadcastradio.DabTableEntry[] dabRegionConfig = getDabRegionConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dabRegionConfig, 1);
                    return true;
                case 4:
                    android.hardware.broadcastradio.ITunerCallback asInterface = android.hardware.broadcastradio.ITunerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setTunerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    unsetTunerCallback();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    android.hardware.broadcastradio.ProgramSelector programSelector = (android.hardware.broadcastradio.ProgramSelector) parcel.readTypedObject(android.hardware.broadcastradio.ProgramSelector.CREATOR);
                    parcel.enforceNoDataAvail();
                    tune(programSelector);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    seek(readBoolean2, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    step(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    cancel();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    android.hardware.broadcastradio.ProgramFilter programFilter = (android.hardware.broadcastradio.ProgramFilter) parcel.readTypedObject(android.hardware.broadcastradio.ProgramFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    startProgramListUpdates(programFilter);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    stopProgramListUpdates();
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConfigFlagSet = isConfigFlagSet(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConfigFlagSet);
                    return true;
                case 13:
                    int readInt2 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setConfigFlag(readInt2, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr = (android.hardware.broadcastradio.VendorKeyValue[]) parcel.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
                    parcel.enforceNoDataAvail();
                    android.hardware.broadcastradio.VendorKeyValue[] parameters = setParameters(vendorKeyValueArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(parameters, 1);
                    return true;
                case 15:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.broadcastradio.VendorKeyValue[] parameters2 = getParameters(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(parameters2, 1);
                    return true;
                case 16:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] image = getImage(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(image);
                    return true;
                case 17:
                    android.hardware.broadcastradio.IAnnouncementListener asInterface2 = android.hardware.broadcastradio.IAnnouncementListener.Stub.asInterface(parcel.readStrongBinder());
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    android.hardware.broadcastradio.ICloseHandle registerAnnouncementListener = registerAnnouncementListener(asInterface2, createByteArray);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(registerAnnouncementListener);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.broadcastradio.IBroadcastRadio {
            private android.os.IBinder mRemote;
            private int mCachedVersion = -1;
            private java.lang.String mCachedHash = "-1";

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR;
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.Properties getProperties() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getProperties is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.broadcastradio.Properties) obtain2.readTypedObject(android.hardware.broadcastradio.Properties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.AmFmRegionConfig getAmFmRegionConfig(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getAmFmRegionConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.broadcastradio.AmFmRegionConfig) obtain2.readTypedObject(android.hardware.broadcastradio.AmFmRegionConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.DabTableEntry[] getDabRegionConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getDabRegionConfig is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.broadcastradio.DabTableEntry[]) obtain2.createTypedArray(android.hardware.broadcastradio.DabTableEntry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void setTunerCallback(android.hardware.broadcastradio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeStrongInterface(iTunerCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setTunerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void unsetTunerCallback() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method unsetTunerCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void tune(android.hardware.broadcastradio.ProgramSelector programSelector) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeTypedObject(programSelector, 0);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method tune is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void seek(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method seek is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void step(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method step is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void cancel() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method cancel is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void startProgramListUpdates(android.hardware.broadcastradio.ProgramFilter programFilter) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeTypedObject(programFilter, 0);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method startProgramListUpdates is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void stopProgramListUpdates() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopProgramListUpdates is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public boolean isConfigFlagSet(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method isConfigFlagSet is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public void setConfigFlag(int i, boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setConfigFlag is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.VendorKeyValue[] setParameters(android.hardware.broadcastradio.VendorKeyValue[] vendorKeyValueArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeTypedArray(vendorKeyValueArr, 0);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setParameters is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.broadcastradio.VendorKeyValue[]) obtain2.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.VendorKeyValue[] getParameters(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getParameters is unimplemented.");
                    }
                    obtain2.readException();
                    return (android.hardware.broadcastradio.VendorKeyValue[]) obtain2.createTypedArray(android.hardware.broadcastradio.VendorKeyValue.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public byte[] getImage(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getImage is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public android.hardware.broadcastradio.ICloseHandle registerAnnouncementListener(android.hardware.broadcastradio.IAnnouncementListener iAnnouncementListener, byte[] bArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                    obtain.writeStrongInterface(iAnnouncementListener);
                    obtain.writeByteArray(bArr);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method registerAnnouncementListener is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.broadcastradio.ICloseHandle.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.broadcastradio.IBroadcastRadio
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                        android.os.Parcel obtain2 = android.os.Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(android.hardware.broadcastradio.IBroadcastRadio.DESCRIPTOR);
                            this.mRemote.transact(android.hardware.broadcastradio.IBroadcastRadio.Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
                            obtain2.readException();
                            this.mCachedHash = obtain2.readString();
                            obtain2.recycle();
                            obtain.recycle();
                        } catch (java.lang.Throwable th) {
                            obtain2.recycle();
                            obtain.recycle();
                            throw th;
                        }
                    }
                } catch (java.lang.Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }
        }
    }
}
