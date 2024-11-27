package android.media.soundtrigger_middleware;

/* loaded from: classes2.dex */
public interface IAcknowledgeEvent extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "android.media.soundtrigger_middleware.IAcknowledgeEvent";

    void eventReceived() throws android.os.RemoteException;

    public static class Default implements android.media.soundtrigger_middleware.IAcknowledgeEvent {
        @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
        public void eventReceived() throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.media.soundtrigger_middleware.IAcknowledgeEvent {
        static final int TRANSACTION_eventReceived = 1;

        public Stub() {
            attachInterface(this, android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR);
        }

        public static android.media.soundtrigger_middleware.IAcknowledgeEvent asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.media.soundtrigger_middleware.IAcknowledgeEvent)) {
                return (android.media.soundtrigger_middleware.IAcknowledgeEvent) queryLocalInterface;
            }
            return new android.media.soundtrigger_middleware.IAcknowledgeEvent.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, android.os.Parcel parcel, android.os.Parcel parcel2, int i2) throws android.os.RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    eventReceived();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.media.soundtrigger_middleware.IAcknowledgeEvent {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IAcknowledgeEvent
            public void eventReceived() throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.media.soundtrigger_middleware.IAcknowledgeEvent.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
