package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IGnssCallback extends android.os.IInterface {
    public static final int CAPABILITY_ANTENNA_INFO = 2048;
    public static final int CAPABILITY_CORRELATION_VECTOR = 4096;
    public static final int CAPABILITY_GEOFENCING = 32;
    public static final int CAPABILITY_LOW_POWER_MODE = 256;
    public static final int CAPABILITY_MEASUREMENTS = 64;
    public static final int CAPABILITY_MEASUREMENT_CORRECTIONS = 1024;
    public static final int CAPABILITY_MEASUREMENT_CORRECTIONS_FOR_DRIVING = 16384;
    public static final int CAPABILITY_MSA = 4;
    public static final int CAPABILITY_MSB = 2;
    public static final int CAPABILITY_NAV_MESSAGES = 128;
    public static final int CAPABILITY_ON_DEMAND_TIME = 16;
    public static final int CAPABILITY_SATELLITE_BLOCKLIST = 512;
    public static final int CAPABILITY_SATELLITE_PVT = 8192;
    public static final int CAPABILITY_SCHEDULING = 1;
    public static final int CAPABILITY_SINGLE_SHOT = 8;
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IGnssCallback".replace('$', '.');
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface GnssStatusValue {
        public static final int ENGINE_OFF = 4;
        public static final int ENGINE_ON = 3;
        public static final int NONE = 0;
        public static final int SESSION_BEGIN = 1;
        public static final int SESSION_END = 2;
    }

    public @interface GnssSvFlags {
        public static final int HAS_ALMANAC_DATA = 2;
        public static final int HAS_CARRIER_FREQUENCY = 8;
        public static final int HAS_EPHEMERIS_DATA = 1;
        public static final int NONE = 0;
        public static final int USED_IN_FIX = 4;
    }

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void gnssAcquireWakelockCb() throws android.os.RemoteException;

    void gnssLocationCb(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException;

    void gnssNmeaCb(long j, java.lang.String str) throws android.os.RemoteException;

    void gnssReleaseWakelockCb() throws android.os.RemoteException;

    void gnssRequestLocationCb(boolean z, boolean z2) throws android.os.RemoteException;

    void gnssRequestTimeCb() throws android.os.RemoteException;

    void gnssSetCapabilitiesCb(int i) throws android.os.RemoteException;

    void gnssSetSystemInfoCb(android.hardware.gnss.IGnssCallback.GnssSystemInfo gnssSystemInfo) throws android.os.RemoteException;

    void gnssStatusCb(int i) throws android.os.RemoteException;

    void gnssSvStatusCb(android.hardware.gnss.IGnssCallback.GnssSvInfo[] gnssSvInfoArr) throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IGnssCallback {
        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSetCapabilitiesCb(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssStatusCb(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSvStatusCb(android.hardware.gnss.IGnssCallback.GnssSvInfo[] gnssSvInfoArr) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssLocationCb(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssNmeaCb(long j, java.lang.String str) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssAcquireWakelockCb() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssReleaseWakelockCb() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssSetSystemInfoCb(android.hardware.gnss.IGnssCallback.GnssSystemInfo gnssSystemInfo) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssRequestTimeCb() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public void gnssRequestLocationCb(boolean z, boolean z2) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnssCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssCallback
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IGnssCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssAcquireWakelockCb = 6;
        static final int TRANSACTION_gnssLocationCb = 4;
        static final int TRANSACTION_gnssNmeaCb = 5;
        static final int TRANSACTION_gnssReleaseWakelockCb = 7;
        static final int TRANSACTION_gnssRequestLocationCb = 10;
        static final int TRANSACTION_gnssRequestTimeCb = 9;
        static final int TRANSACTION_gnssSetCapabilitiesCb = 1;
        static final int TRANSACTION_gnssSetSystemInfoCb = 8;
        static final int TRANSACTION_gnssStatusCb = 2;
        static final int TRANSACTION_gnssSvStatusCb = 3;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IGnssCallback asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IGnssCallback)) {
                return (android.hardware.gnss.IGnssCallback) queryLocalInterface;
            }
            return new android.hardware.gnss.IGnssCallback.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "gnssSetCapabilitiesCb";
                case 2:
                    return "gnssStatusCb";
                case 3:
                    return "gnssSvStatusCb";
                case 4:
                    return "gnssLocationCb";
                case 5:
                    return "gnssNmeaCb";
                case 6:
                    return "gnssAcquireWakelockCb";
                case 7:
                    return "gnssReleaseWakelockCb";
                case 8:
                    return "gnssSetSystemInfoCb";
                case 9:
                    return "gnssRequestTimeCb";
                case 10:
                    return "gnssRequestLocationCb";
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
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
            java.lang.String str = DESCRIPTOR;
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
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssSetCapabilitiesCb(readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    gnssStatusCb(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.gnss.IGnssCallback.GnssSvInfo[] gnssSvInfoArr = (android.hardware.gnss.IGnssCallback.GnssSvInfo[]) parcel.createTypedArray(android.hardware.gnss.IGnssCallback.GnssSvInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssSvStatusCb(gnssSvInfoArr);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.hardware.gnss.GnssLocation gnssLocation = (android.hardware.gnss.GnssLocation) parcel.readTypedObject(android.hardware.gnss.GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssLocationCb(gnssLocation);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    long readLong = parcel.readLong();
                    java.lang.String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    gnssNmeaCb(readLong, readString);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    gnssAcquireWakelockCb();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    gnssReleaseWakelockCb();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    android.hardware.gnss.IGnssCallback.GnssSystemInfo gnssSystemInfo = (android.hardware.gnss.IGnssCallback.GnssSystemInfo) parcel.readTypedObject(android.hardware.gnss.IGnssCallback.GnssSystemInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    gnssSetSystemInfoCb(gnssSystemInfo);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    gnssRequestTimeCb();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    gnssRequestLocationCb(readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.gnss.IGnssCallback {
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
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSetCapabilitiesCb(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssSetCapabilitiesCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssStatusCb(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssStatusCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSvStatusCb(android.hardware.gnss.IGnssCallback.GnssSvInfo[] gnssSvInfoArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(gnssSvInfoArr, 0);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssSvStatusCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssLocationCb(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssLocationCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssNmeaCb(long j, java.lang.String str) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssNmeaCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssAcquireWakelockCb() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssAcquireWakelockCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssReleaseWakelockCb() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssReleaseWakelockCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssSetSystemInfoCb(android.hardware.gnss.IGnssCallback.GnssSystemInfo gnssSystemInfo) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssSystemInfo, 0);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssSetSystemInfoCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssRequestTimeCb() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssRequestTimeCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public void gnssRequestLocationCb(boolean z, boolean z2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method gnssRequestLocationCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssCallback
            public int getInterfaceVersion() throws android.os.RemoteException {
                if (this.mCachedVersion == -1) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
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

            @Override // android.hardware.gnss.IGnssCallback
            public synchronized java.lang.String getInterfaceHash() throws android.os.RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                    android.os.Parcel obtain2 = android.os.Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
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
                return this.mCachedHash;
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }
    }

    public static class GnssSvInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssCallback.GnssSvInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssCallback.GnssSvInfo>() { // from class: android.hardware.gnss.IGnssCallback.GnssSvInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssCallback.GnssSvInfo createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssCallback.GnssSvInfo gnssSvInfo = new android.hardware.gnss.IGnssCallback.GnssSvInfo();
                gnssSvInfo.readFromParcel(parcel);
                return gnssSvInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssCallback.GnssSvInfo[] newArray(int i) {
                return new android.hardware.gnss.IGnssCallback.GnssSvInfo[i];
            }
        };
        public int constellation;
        public int svid = 0;
        public float cN0Dbhz = 0.0f;
        public float basebandCN0DbHz = 0.0f;
        public float elevationDegrees = 0.0f;
        public float azimuthDegrees = 0.0f;
        public long carrierFrequencyHz = 0;
        public int svFlag = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.svid);
            parcel.writeInt(this.constellation);
            parcel.writeFloat(this.cN0Dbhz);
            parcel.writeFloat(this.basebandCN0DbHz);
            parcel.writeFloat(this.elevationDegrees);
            parcel.writeFloat(this.azimuthDegrees);
            parcel.writeLong(this.carrierFrequencyHz);
            parcel.writeInt(this.svFlag);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.svid = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.constellation = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.cN0Dbhz = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.basebandCN0DbHz = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.elevationDegrees = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.azimuthDegrees = parcel.readFloat();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.carrierFrequencyHz = parcel.readLong();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.svFlag = parcel.readInt();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static class GnssSystemInfo implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnssCallback.GnssSystemInfo> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnssCallback.GnssSystemInfo>() { // from class: android.hardware.gnss.IGnssCallback.GnssSystemInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssCallback.GnssSystemInfo createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnssCallback.GnssSystemInfo gnssSystemInfo = new android.hardware.gnss.IGnssCallback.GnssSystemInfo();
                gnssSystemInfo.readFromParcel(parcel);
                return gnssSystemInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnssCallback.GnssSystemInfo[] newArray(int i) {
                return new android.hardware.gnss.IGnssCallback.GnssSystemInfo[i];
            }
        };
        public java.lang.String name;
        public int yearOfHw = 0;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.yearOfHw);
            parcel.writeString(this.name);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(android.os.Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new android.os.BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.yearOfHw = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.name = parcel.readString();
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                }
            } catch (java.lang.Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }
}
