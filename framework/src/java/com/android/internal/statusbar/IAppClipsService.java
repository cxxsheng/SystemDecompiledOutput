package com.android.internal.statusbar;

/* loaded from: classes5.dex */
public interface IAppClipsService extends android.os.IInterface {
    public static final java.lang.String DESCRIPTOR = "com.android.internal.statusbar.IAppClipsService";

    boolean canLaunchCaptureContentActivityForNote(int i) throws android.os.RemoteException;

    int canLaunchCaptureContentActivityForNoteInternal(int i) throws android.os.RemoteException;

    public static class Default implements com.android.internal.statusbar.IAppClipsService {
        @Override // com.android.internal.statusbar.IAppClipsService
        public boolean canLaunchCaptureContentActivityForNote(int i) throws android.os.RemoteException {
            return false;
        }

        @Override // com.android.internal.statusbar.IAppClipsService
        public int canLaunchCaptureContentActivityForNoteInternal(int i) throws android.os.RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements com.android.internal.statusbar.IAppClipsService {
        static final int TRANSACTION_canLaunchCaptureContentActivityForNote = 1;
        static final int TRANSACTION_canLaunchCaptureContentActivityForNoteInternal = 2;

        public Stub() {
            attachInterface(this, com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
        }

        public static com.android.internal.statusbar.IAppClipsService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof com.android.internal.statusbar.IAppClipsService)) {
                return (com.android.internal.statusbar.IAppClipsService) queryLocalInterface;
            }
            return new com.android.internal.statusbar.IAppClipsService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "canLaunchCaptureContentActivityForNote";
                case 2:
                    return "canLaunchCaptureContentActivityForNoteInternal";
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
                parcel.enforceInterface(com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canLaunchCaptureContentActivityForNote = canLaunchCaptureContentActivityForNote(readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canLaunchCaptureContentActivityForNote);
                    return true;
                case 2:
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int canLaunchCaptureContentActivityForNoteInternal = canLaunchCaptureContentActivityForNoteInternal(readInt2);
                    parcel2.writeNoException();
                    parcel2.writeInt(canLaunchCaptureContentActivityForNoteInternal);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements com.android.internal.statusbar.IAppClipsService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return com.android.internal.statusbar.IAppClipsService.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public boolean canLaunchCaptureContentActivityForNote(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.statusbar.IAppClipsService
            public int canLaunchCaptureContentActivityForNoteInternal(int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                android.os.Parcel obtain2 = android.os.Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(com.android.internal.statusbar.IAppClipsService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
