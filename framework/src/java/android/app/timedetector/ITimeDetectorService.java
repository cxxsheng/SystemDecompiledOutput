package android.app.timedetector;

/* loaded from: classes.dex */
public interface ITimeDetectorService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.app.timedetector.ITimeDetectorService";

    void addListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException;

    boolean confirmTime(android.app.time.UnixEpochTime unixEpochTime) throws android.os.RemoteException;

    android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException;

    android.app.time.TimeState getTimeState() throws android.os.RemoteException;

    android.app.time.UnixEpochTime latestNetworkTime() throws android.os.RemoteException;

    void removeListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException;

    boolean setManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException;

    void suggestExternalTime(android.app.time.ExternalTimeSuggestion externalTimeSuggestion) throws android.os.RemoteException;

    boolean suggestManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException;

    void suggestTelephonyTime(android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) throws android.os.RemoteException;

    boolean updateConfiguration(android.app.time.TimeConfiguration timeConfiguration) throws android.os.RemoteException;

    public static class Default implements android.app.timedetector.ITimeDetectorService {
        @Override // android.app.timedetector.ITimeDetectorService
        public android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void addListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void removeListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean updateConfiguration(android.app.time.TimeConfiguration timeConfiguration) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public android.app.time.TimeState getTimeState() throws android.os.RemoteException {
            return null;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean confirmTime(android.app.time.UnixEpochTime unixEpochTime) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean setManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void suggestExternalTime(android.app.time.ExternalTimeSuggestion externalTimeSuggestion) throws android.os.RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public boolean suggestManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException {
            return false;
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public void suggestTelephonyTime(android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) throws android.os.RemoteException {
        }

        @Override // android.app.timedetector.ITimeDetectorService
        public android.app.time.UnixEpochTime latestNetworkTime() throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.app.timedetector.ITimeDetectorService {
        static final int TRANSACTION_addListener = 2;
        static final int TRANSACTION_confirmTime = 6;
        static final int TRANSACTION_getCapabilitiesAndConfig = 1;
        static final int TRANSACTION_getTimeState = 5;
        static final int TRANSACTION_latestNetworkTime = 11;
        static final int TRANSACTION_removeListener = 3;
        static final int TRANSACTION_setManualTime = 7;
        static final int TRANSACTION_suggestExternalTime = 8;
        static final int TRANSACTION_suggestManualTime = 9;
        static final int TRANSACTION_suggestTelephonyTime = 10;
        static final int TRANSACTION_updateConfiguration = 4;

        public Stub() {
            attachInterface(this, android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
        }

        public static android.app.timedetector.ITimeDetectorService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.app.timedetector.ITimeDetectorService)) {
                return (android.app.timedetector.ITimeDetectorService) queryLocalInterface;
            }
            return new android.app.timedetector.ITimeDetectorService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getCapabilitiesAndConfig";
                case 2:
                    return "addListener";
                case 3:
                    return "removeListener";
                case 4:
                    return "updateConfiguration";
                case 5:
                    return "getTimeState";
                case 6:
                    return "confirmTime";
                case 7:
                    return "setManualTime";
                case 8:
                    return "suggestExternalTime";
                case 9:
                    return "suggestManualTime";
                case 10:
                    return "suggestTelephonyTime";
                case 11:
                    return "latestNetworkTime";
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
                parcel.enforceInterface(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    android.app.time.TimeCapabilitiesAndConfig capabilitiesAndConfig = getCapabilitiesAndConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesAndConfig, 1);
                    return true;
                case 2:
                    android.app.time.ITimeDetectorListener asInterface = android.app.time.ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    android.app.time.ITimeDetectorListener asInterface2 = android.app.time.ITimeDetectorListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    android.app.time.TimeConfiguration timeConfiguration = (android.app.time.TimeConfiguration) parcel.readTypedObject(android.app.time.TimeConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean updateConfiguration = updateConfiguration(timeConfiguration);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfiguration);
                    return true;
                case 5:
                    android.app.time.TimeState timeState = getTimeState();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(timeState, 1);
                    return true;
                case 6:
                    android.app.time.UnixEpochTime unixEpochTime = (android.app.time.UnixEpochTime) parcel.readTypedObject(android.app.time.UnixEpochTime.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean confirmTime = confirmTime(unixEpochTime);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(confirmTime);
                    return true;
                case 7:
                    android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion = (android.app.timedetector.ManualTimeSuggestion) parcel.readTypedObject(android.app.timedetector.ManualTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean manualTime = setManualTime(manualTimeSuggestion);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(manualTime);
                    return true;
                case 8:
                    android.app.time.ExternalTimeSuggestion externalTimeSuggestion = (android.app.time.ExternalTimeSuggestion) parcel.readTypedObject(android.app.time.ExternalTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestExternalTime(externalTimeSuggestion);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion2 = (android.app.timedetector.ManualTimeSuggestion) parcel.readTypedObject(android.app.timedetector.ManualTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean suggestManualTime = suggestManualTime(manualTimeSuggestion2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(suggestManualTime);
                    return true;
                case 10:
                    android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion = (android.app.timedetector.TelephonyTimeSuggestion) parcel.readTypedObject(android.app.timedetector.TelephonyTimeSuggestion.CREATOR);
                    parcel.enforceNoDataAvail();
                    suggestTelephonyTime(telephonyTimeSuggestion);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    android.app.time.UnixEpochTime latestNetworkTime = latestNetworkTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(latestNetworkTime, 1);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.app.timedetector.ITimeDetectorService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.app.timedetector.ITimeDetectorService.DESCRIPTOR;
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public android.app.time.TimeCapabilitiesAndConfig getCapabilitiesAndConfig() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.time.TimeCapabilitiesAndConfig) obtain2.readTypedObject(android.app.time.TimeCapabilitiesAndConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void addListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void removeListener(android.app.time.ITimeDetectorListener iTimeDetectorListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeStrongInterface(iTimeDetectorListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean updateConfiguration(android.app.time.TimeConfiguration timeConfiguration) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(timeConfiguration, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public android.app.time.TimeState getTimeState() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.time.TimeState) obtain2.readTypedObject(android.app.time.TimeState.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean confirmTime(android.app.time.UnixEpochTime unixEpochTime) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(unixEpochTime, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean setManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestExternalTime(android.app.time.ExternalTimeSuggestion externalTimeSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(externalTimeSuggestion, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public boolean suggestManualTime(android.app.timedetector.ManualTimeSuggestion manualTimeSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(manualTimeSuggestion, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public void suggestTelephonyTime(android.app.timedetector.TelephonyTimeSuggestion telephonyTimeSuggestion) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    obtain.writeTypedObject(telephonyTimeSuggestion, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.timedetector.ITimeDetectorService
            public android.app.time.UnixEpochTime latestNetworkTime() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.app.timedetector.ITimeDetectorService.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (android.app.time.UnixEpochTime) obtain2.readTypedObject(android.app.time.UnixEpochTime.CREATOR);
                } finally {
                    obtain2.recycle();
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
