package android.hardware.radio;

/* loaded from: classes2.dex */
public interface IRadioService extends android.os.IInterface {
    android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) throws android.os.RemoteException;

    java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() throws android.os.RemoteException;

    android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException;

    public static class Default implements android.hardware.radio.IRadioService {
        @Override // android.hardware.radio.IRadioService
        public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.radio.IRadioService
        public android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
            return null;
        }

        @Override // android.hardware.radio.IRadioService
        public android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) throws android.os.RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.radio.IRadioService {
        public static final java.lang.String DESCRIPTOR = "android.hardware.radio.IRadioService";
        static final int TRANSACTION_addAnnouncementListener = 3;
        static final int TRANSACTION_listModules = 1;
        static final int TRANSACTION_openTuner = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.radio.IRadioService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.radio.IRadioService)) {
                return (android.hardware.radio.IRadioService) queryLocalInterface;
            }
            return new android.hardware.radio.IRadioService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "listModules";
                case 2:
                    return "openTuner";
                case 3:
                    return "addAnnouncementListener";
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
                    java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules = listModules();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listModules, 1);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    android.hardware.radio.RadioManager.BandConfig bandConfig = (android.hardware.radio.RadioManager.BandConfig) parcel.readTypedObject(android.hardware.radio.RadioManager.BandConfig.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    android.hardware.radio.ITunerCallback asInterface = android.hardware.radio.ITunerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.radio.ITuner openTuner = openTuner(readInt, bandConfig, readBoolean, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(openTuner);
                    return true;
                case 3:
                    int[] createIntArray = parcel.createIntArray();
                    android.hardware.radio.IAnnouncementListener asInterface2 = android.hardware.radio.IAnnouncementListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    android.hardware.radio.ICloseHandle addAnnouncementListener = addAnnouncementListener(createIntArray, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(addAnnouncementListener);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.radio.IRadioService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.radio.IRadioService.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.radio.IRadioService
            public java.util.List<android.hardware.radio.RadioManager.ModuleProperties> listModules() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.IRadioService.Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(android.hardware.radio.RadioManager.ModuleProperties.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.IRadioService
            public android.hardware.radio.ITuner openTuner(int i, android.hardware.radio.RadioManager.BandConfig bandConfig, boolean z, android.hardware.radio.ITunerCallback iTunerCallback) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.IRadioService.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bandConfig, 0);
                    obtain.writeBoolean(z);
                    obtain.writeStrongInterface(iTunerCallback);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.radio.ITuner.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.radio.IRadioService
            public android.hardware.radio.ICloseHandle addAnnouncementListener(int[] iArr, android.hardware.radio.IAnnouncementListener iAnnouncementListener) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.radio.IRadioService.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeStrongInterface(iAnnouncementListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return android.hardware.radio.ICloseHandle.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
