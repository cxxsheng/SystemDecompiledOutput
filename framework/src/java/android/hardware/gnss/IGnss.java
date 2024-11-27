package android.hardware.gnss;

/* loaded from: classes2.dex */
public interface IGnss extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android$hardware$gnss$IGnss".replace('$', '.');
    public static final int ERROR_ALREADY_INIT = 2;
    public static final int ERROR_GENERIC = 3;
    public static final int ERROR_INVALID_ARGUMENT = 1;
    public static final java.lang.String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface GnssAidingData {
        public static final int ALL = 65535;
        public static final int ALMANAC = 2;
        public static final int CELLDB_INFO = 32768;
        public static final int EPHEMERIS = 1;
        public static final int HEALTH = 64;
        public static final int IONO = 16;
        public static final int POSITION = 4;
        public static final int RTI = 1024;
        public static final int SADATA = 512;
        public static final int SVDIR = 128;
        public static final int SVSTEER = 256;
        public static final int TIME = 8;
        public static final int UTC = 32;
    }

    public @interface GnssPositionMode {
        public static final int MS_ASSISTED = 2;
        public static final int MS_BASED = 1;
        public static final int STANDALONE = 0;
    }

    public @interface GnssPositionRecurrence {
        public static final int RECURRENCE_PERIODIC = 0;
        public static final int RECURRENCE_SINGLE = 1;
    }

    void close() throws android.os.RemoteException;

    void deleteAidingData(int i) throws android.os.RemoteException;

    android.hardware.gnss.IAGnss getExtensionAGnss() throws android.os.RemoteException;

    android.hardware.gnss.IAGnssRil getExtensionAGnssRil() throws android.os.RemoteException;

    android.hardware.gnss.IGnssAntennaInfo getExtensionGnssAntennaInfo() throws android.os.RemoteException;

    android.hardware.gnss.IGnssBatching getExtensionGnssBatching() throws android.os.RemoteException;

    android.hardware.gnss.IGnssConfiguration getExtensionGnssConfiguration() throws android.os.RemoteException;

    android.hardware.gnss.IGnssDebug getExtensionGnssDebug() throws android.os.RemoteException;

    android.hardware.gnss.IGnssGeofence getExtensionGnssGeofence() throws android.os.RemoteException;

    android.hardware.gnss.IGnssMeasurementInterface getExtensionGnssMeasurement() throws android.os.RemoteException;

    android.hardware.gnss.IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws android.os.RemoteException;

    android.hardware.gnss.IGnssPowerIndication getExtensionGnssPowerIndication() throws android.os.RemoteException;

    android.hardware.gnss.visibility_control.IGnssVisibilityControl getExtensionGnssVisibilityControl() throws android.os.RemoteException;

    android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws android.os.RemoteException;

    android.hardware.gnss.IGnssPsds getExtensionPsds() throws android.os.RemoteException;

    java.lang.String getInterfaceHash() throws android.os.RemoteException;

    int getInterfaceVersion() throws android.os.RemoteException;

    void injectBestLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException;

    void injectLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException;

    void injectTime(long j, long j2, int i) throws android.os.RemoteException;

    void setCallback(android.hardware.gnss.IGnssCallback iGnssCallback) throws android.os.RemoteException;

    void setPositionMode(android.hardware.gnss.IGnss.PositionModeOptions positionModeOptions) throws android.os.RemoteException;

    void start() throws android.os.RemoteException;

    void startNmea() throws android.os.RemoteException;

    void startSvStatus() throws android.os.RemoteException;

    void stop() throws android.os.RemoteException;

    void stopNmea() throws android.os.RemoteException;

    void stopSvStatus() throws android.os.RemoteException;

    public static class Default implements android.hardware.gnss.IGnss {
        @Override // android.hardware.gnss.IGnss
        public void setCallback(android.hardware.gnss.IGnssCallback iGnssCallback) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void close() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssPsds getExtensionPsds() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssConfiguration getExtensionGnssConfiguration() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssMeasurementInterface getExtensionGnssMeasurement() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssPowerIndication getExtensionGnssPowerIndication() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssBatching getExtensionGnssBatching() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssGeofence getExtensionGnssGeofence() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IAGnss getExtensionAGnss() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IAGnssRil getExtensionAGnssRil() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssDebug getExtensionGnssDebug() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.visibility_control.IGnssVisibilityControl getExtensionGnssVisibilityControl() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public void start() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stop() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void injectTime(long j, long j2, int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void injectLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void injectBestLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void deleteAidingData(int i) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void setPositionMode(android.hardware.gnss.IGnss.PositionModeOptions positionModeOptions) throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.IGnssAntennaInfo getExtensionGnssAntennaInfo() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.gnss.IGnss
        public void startSvStatus() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stopSvStatus() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void startNmea() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public void stopNmea() throws android.os.RemoteException {
        }

        @Override // android.hardware.gnss.IGnss
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnss
        public java.lang.String getInterfaceHash() {
            return "";
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.gnss.IGnss {
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_deleteAidingData = 19;
        static final int TRANSACTION_getExtensionAGnss = 10;
        static final int TRANSACTION_getExtensionAGnssRil = 11;
        static final int TRANSACTION_getExtensionGnssAntennaInfo = 21;
        static final int TRANSACTION_getExtensionGnssBatching = 7;
        static final int TRANSACTION_getExtensionGnssConfiguration = 4;
        static final int TRANSACTION_getExtensionGnssDebug = 12;
        static final int TRANSACTION_getExtensionGnssGeofence = 8;
        static final int TRANSACTION_getExtensionGnssMeasurement = 5;
        static final int TRANSACTION_getExtensionGnssNavigationMessage = 9;
        static final int TRANSACTION_getExtensionGnssPowerIndication = 6;
        static final int TRANSACTION_getExtensionGnssVisibilityControl = 13;
        static final int TRANSACTION_getExtensionMeasurementCorrections = 22;
        static final int TRANSACTION_getExtensionPsds = 3;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_injectBestLocation = 18;
        static final int TRANSACTION_injectLocation = 17;
        static final int TRANSACTION_injectTime = 16;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setPositionMode = 20;
        static final int TRANSACTION_start = 14;
        static final int TRANSACTION_startNmea = 25;
        static final int TRANSACTION_startSvStatus = 23;
        static final int TRANSACTION_stop = 15;
        static final int TRANSACTION_stopNmea = 26;
        static final int TRANSACTION_stopSvStatus = 24;

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.gnss.IGnss asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.gnss.IGnss)) {
                return (android.hardware.gnss.IGnss) queryLocalInterface;
            }
            return new android.hardware.gnss.IGnss.Stub.Proxy(iBinder);
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
                    return "close";
                case 3:
                    return "getExtensionPsds";
                case 4:
                    return "getExtensionGnssConfiguration";
                case 5:
                    return "getExtensionGnssMeasurement";
                case 6:
                    return "getExtensionGnssPowerIndication";
                case 7:
                    return "getExtensionGnssBatching";
                case 8:
                    return "getExtensionGnssGeofence";
                case 9:
                    return "getExtensionGnssNavigationMessage";
                case 10:
                    return "getExtensionAGnss";
                case 11:
                    return "getExtensionAGnssRil";
                case 12:
                    return "getExtensionGnssDebug";
                case 13:
                    return "getExtensionGnssVisibilityControl";
                case 14:
                    return "start";
                case 15:
                    return "stop";
                case 16:
                    return "injectTime";
                case 17:
                    return "injectLocation";
                case 18:
                    return "injectBestLocation";
                case 19:
                    return "deleteAidingData";
                case 20:
                    return "setPositionMode";
                case 21:
                    return "getExtensionGnssAntennaInfo";
                case 22:
                    return "getExtensionMeasurementCorrections";
                case 23:
                    return "startSvStatus";
                case 24:
                    return "stopSvStatus";
                case 25:
                    return "startNmea";
                case 26:
                    return "stopNmea";
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
                    android.hardware.gnss.IGnssCallback asInterface = android.hardware.gnss.IGnssCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.hardware.gnss.IGnssPsds extensionPsds = getExtensionPsds();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionPsds);
                    return true;
                case 4:
                    android.hardware.gnss.IGnssConfiguration extensionGnssConfiguration = getExtensionGnssConfiguration();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssConfiguration);
                    return true;
                case 5:
                    android.hardware.gnss.IGnssMeasurementInterface extensionGnssMeasurement = getExtensionGnssMeasurement();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssMeasurement);
                    return true;
                case 6:
                    android.hardware.gnss.IGnssPowerIndication extensionGnssPowerIndication = getExtensionGnssPowerIndication();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssPowerIndication);
                    return true;
                case 7:
                    android.hardware.gnss.IGnssBatching extensionGnssBatching = getExtensionGnssBatching();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssBatching);
                    return true;
                case 8:
                    android.hardware.gnss.IGnssGeofence extensionGnssGeofence = getExtensionGnssGeofence();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssGeofence);
                    return true;
                case 9:
                    android.hardware.gnss.IGnssNavigationMessageInterface extensionGnssNavigationMessage = getExtensionGnssNavigationMessage();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssNavigationMessage);
                    return true;
                case 10:
                    android.hardware.gnss.IAGnss extensionAGnss = getExtensionAGnss();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionAGnss);
                    return true;
                case 11:
                    android.hardware.gnss.IAGnssRil extensionAGnssRil = getExtensionAGnssRil();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionAGnssRil);
                    return true;
                case 12:
                    android.hardware.gnss.IGnssDebug extensionGnssDebug = getExtensionGnssDebug();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssDebug);
                    return true;
                case 13:
                    android.hardware.gnss.visibility_control.IGnssVisibilityControl extensionGnssVisibilityControl = getExtensionGnssVisibilityControl();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssVisibilityControl);
                    return true;
                case 14:
                    start();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 16:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    injectTime(readLong, readLong2, readInt);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    android.hardware.gnss.GnssLocation gnssLocation = (android.hardware.gnss.GnssLocation) parcel.readTypedObject(android.hardware.gnss.GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectLocation(gnssLocation);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    android.hardware.gnss.GnssLocation gnssLocation2 = (android.hardware.gnss.GnssLocation) parcel.readTypedObject(android.hardware.gnss.GnssLocation.CREATOR);
                    parcel.enforceNoDataAvail();
                    injectBestLocation(gnssLocation2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    deleteAidingData(readInt2);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    android.hardware.gnss.IGnss.PositionModeOptions positionModeOptions = (android.hardware.gnss.IGnss.PositionModeOptions) parcel.readTypedObject(android.hardware.gnss.IGnss.PositionModeOptions.CREATOR);
                    parcel.enforceNoDataAvail();
                    setPositionMode(positionModeOptions);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    android.hardware.gnss.IGnssAntennaInfo extensionGnssAntennaInfo = getExtensionGnssAntennaInfo();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionGnssAntennaInfo);
                    return true;
                case 22:
                    android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface extensionMeasurementCorrections = getExtensionMeasurementCorrections();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(extensionMeasurementCorrections);
                    return true;
                case 23:
                    startSvStatus();
                    parcel2.writeNoException();
                    return true;
                case 24:
                    stopSvStatus();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    startNmea();
                    parcel2.writeNoException();
                    return true;
                case 26:
                    stopNmea();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.gnss.IGnss {
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

            @Override // android.hardware.gnss.IGnss
            public void setCallback(android.hardware.gnss.IGnssCallback iGnssCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iGnssCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void close() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method close is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssPsds getExtensionPsds() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionPsds is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssPsds.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssConfiguration getExtensionGnssConfiguration() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssConfiguration is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssConfiguration.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssMeasurementInterface getExtensionGnssMeasurement() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssMeasurement is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssMeasurementInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssPowerIndication getExtensionGnssPowerIndication() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssPowerIndication is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssPowerIndication.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssBatching getExtensionGnssBatching() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssBatching is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssBatching.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssGeofence getExtensionGnssGeofence() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssGeofence is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssGeofence.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssNavigationMessageInterface getExtensionGnssNavigationMessage() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssNavigationMessage is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssNavigationMessageInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IAGnss getExtensionAGnss() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionAGnss is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IAGnss.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IAGnssRil getExtensionAGnssRil() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionAGnssRil is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IAGnssRil.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssDebug getExtensionGnssDebug() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssDebug is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssDebug.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.visibility_control.IGnssVisibilityControl getExtensionGnssVisibilityControl() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssVisibilityControl is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.visibility_control.IGnssVisibilityControl.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void start() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method start is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stop() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stop is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectTime(long j, long j2, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method injectTime is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method injectLocation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void injectBestLocation(android.hardware.gnss.GnssLocation gnssLocation) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssLocation, 0);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method injectBestLocation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void deleteAidingData(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method deleteAidingData is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void setPositionMode(android.hardware.gnss.IGnss.PositionModeOptions positionModeOptions) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(positionModeOptions, 0);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method setPositionMode is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.IGnssAntennaInfo getExtensionGnssAntennaInfo() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionGnssAntennaInfo is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.IGnssAntennaInfo.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface getExtensionMeasurementCorrections() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method getExtensionMeasurementCorrections is unimplemented.");
                    }
                    obtain2.readException();
                    return android.hardware.gnss.measurement_corrections.IMeasurementCorrectionsInterface.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void startSvStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method startSvStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stopSvStatus() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopSvStatus is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void startNmea() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method startNmea is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
            public void stopNmea() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(26, obtain, obtain2, 0)) {
                        throw new android.os.RemoteException("Method stopNmea is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnss
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

            @Override // android.hardware.gnss.IGnss
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

    public static class PositionModeOptions implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.hardware.gnss.IGnss.PositionModeOptions> CREATOR = new android.os.Parcelable.Creator<android.hardware.gnss.IGnss.PositionModeOptions>() { // from class: android.hardware.gnss.IGnss.PositionModeOptions.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnss.PositionModeOptions createFromParcel(android.os.Parcel parcel) {
                android.hardware.gnss.IGnss.PositionModeOptions positionModeOptions = new android.hardware.gnss.IGnss.PositionModeOptions();
                positionModeOptions.readFromParcel(parcel);
                return positionModeOptions;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.hardware.gnss.IGnss.PositionModeOptions[] newArray(int i) {
                return new android.hardware.gnss.IGnss.PositionModeOptions[i];
            }
        };
        public int mode;
        public int recurrence;
        public int minIntervalMs = 0;
        public int preferredAccuracyMeters = 0;
        public int preferredTimeMs = 0;
        public boolean lowPowerMode = false;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(android.os.Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.mode);
            parcel.writeInt(this.recurrence);
            parcel.writeInt(this.minIntervalMs);
            parcel.writeInt(this.preferredAccuracyMeters);
            parcel.writeInt(this.preferredTimeMs);
            parcel.writeBoolean(this.lowPowerMode);
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
                this.mode = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.recurrence = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.minIntervalMs = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.preferredAccuracyMeters = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                    return;
                }
                this.preferredTimeMs = parcel.readInt();
                if (parcel.dataPosition() - dataPosition >= readInt) {
                    if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new android.os.BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(dataPosition + readInt);
                } else {
                    this.lowPowerMode = parcel.readBoolean();
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
