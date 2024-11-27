package android.hardware.location;

/* loaded from: classes2.dex */
public interface IActivityRecognitionHardwareSink extends android.os.IInterface {
    void onActivityChanged(android.hardware.location.ActivityChangedEvent activityChangedEvent) throws android.os.RemoteException;

    public static class Default implements android.hardware.location.IActivityRecognitionHardwareSink {
        @Override // android.hardware.location.IActivityRecognitionHardwareSink
        public void onActivityChanged(android.hardware.location.ActivityChangedEvent activityChangedEvent) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.hardware.location.IActivityRecognitionHardwareSink {
        public static final java.lang.String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareSink";
        static final int TRANSACTION_onActivityChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.hardware.location.IActivityRecognitionHardwareSink asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.hardware.location.IActivityRecognitionHardwareSink)) {
                return (android.hardware.location.IActivityRecognitionHardwareSink) queryLocalInterface;
            }
            return new android.hardware.location.IActivityRecognitionHardwareSink.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onActivityChanged";
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
                    android.hardware.location.ActivityChangedEvent activityChangedEvent = (android.hardware.location.ActivityChangedEvent) parcel.readTypedObject(android.hardware.location.ActivityChangedEvent.CREATOR);
                    parcel.enforceNoDataAvail();
                    onActivityChanged(activityChangedEvent);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.hardware.location.IActivityRecognitionHardwareSink {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.hardware.location.IActivityRecognitionHardwareSink.Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IActivityRecognitionHardwareSink
            public void onActivityChanged(android.hardware.location.ActivityChangedEvent activityChangedEvent) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(android.hardware.location.IActivityRecognitionHardwareSink.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(activityChangedEvent, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }
    }
}
