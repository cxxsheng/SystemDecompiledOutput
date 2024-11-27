package android.view.autofill;

/* loaded from: classes4.dex */
public interface IAutofillWindowPresenter extends android.os.IInterface {
    void hide(android.graphics.Rect rect) throws android.os.RemoteException;

    void show(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) throws android.os.RemoteException;

    public static class Default implements android.view.autofill.IAutofillWindowPresenter {
        @Override // android.view.autofill.IAutofillWindowPresenter
        public void show(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) throws android.os.RemoteException {
        }

        @Override // android.view.autofill.IAutofillWindowPresenter
        public void hide(android.graphics.Rect rect) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.view.autofill.IAutofillWindowPresenter {
        public static final java.lang.String DESCRIPTOR = "android.view.autofill.IAutofillWindowPresenter";
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_show = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.view.autofill.IAutofillWindowPresenter asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.view.autofill.IAutofillWindowPresenter)) {
                return (android.view.autofill.IAutofillWindowPresenter) queryLocalInterface;
            }
            return new android.view.autofill.IAutofillWindowPresenter.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return android.view.ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 2:
                    return "hide";
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
                    android.view.WindowManager.LayoutParams layoutParams = (android.view.WindowManager.LayoutParams) parcel.readTypedObject(android.view.WindowManager.LayoutParams.CREATOR);
                    android.graphics.Rect rect = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    show(layoutParams, rect, readBoolean, readInt);
                    return true;
                case 2:
                    android.graphics.Rect rect2 = (android.graphics.Rect) parcel.readTypedObject(android.graphics.Rect.CREATOR);
                    parcel.enforceNoDataAvail();
                    hide(rect2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.view.autofill.IAutofillWindowPresenter {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.view.autofill.IAutofillWindowPresenter.Stub.DESCRIPTOR;
            }

            @Override // android.view.autofill.IAutofillWindowPresenter
            public void show(android.view.WindowManager.LayoutParams layoutParams, android.graphics.Rect rect, boolean z, int i) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutofillWindowPresenter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(layoutParams, 0);
                    obtain.writeTypedObject(rect, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.view.autofill.IAutofillWindowPresenter
            public void hide(android.graphics.Rect rect) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.view.autofill.IAutofillWindowPresenter.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(rect, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
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
