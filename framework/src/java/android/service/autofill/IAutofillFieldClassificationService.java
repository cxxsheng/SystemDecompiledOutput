package android.service.autofill;

/* loaded from: classes3.dex */
public interface IAutofillFieldClassificationService extends android.os.IInterface {
    void calculateScores(android.os.RemoteCallback remoteCallback, java.util.List<android.view.autofill.AutofillValue> list, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) throws android.os.RemoteException;

    public static class Default implements android.service.autofill.IAutofillFieldClassificationService {
        @Override // android.service.autofill.IAutofillFieldClassificationService
        public void calculateScores(android.os.RemoteCallback remoteCallback, java.util.List<android.view.autofill.AutofillValue> list, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.service.autofill.IAutofillFieldClassificationService {
        public static final java.lang.String DESCRIPTOR = "android.service.autofill.IAutofillFieldClassificationService";
        static final int TRANSACTION_calculateScores = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.service.autofill.IAutofillFieldClassificationService asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.service.autofill.IAutofillFieldClassificationService)) {
                return (android.service.autofill.IAutofillFieldClassificationService) queryLocalInterface;
            }
            return new android.service.autofill.IAutofillFieldClassificationService.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "calculateScores";
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
                    android.os.RemoteCallback remoteCallback = (android.os.RemoteCallback) parcel.readTypedObject(android.os.RemoteCallback.CREATOR);
                    java.util.ArrayList createTypedArrayList = parcel.createTypedArrayList(android.view.autofill.AutofillValue.CREATOR);
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    java.lang.String[] createStringArray2 = parcel.createStringArray();
                    java.lang.String readString = parcel.readString();
                    android.os.Bundle bundle = (android.os.Bundle) parcel.readTypedObject(android.os.Bundle.CREATOR);
                    java.lang.ClassLoader classLoader = getClass().getClassLoader();
                    java.util.HashMap readHashMap = parcel.readHashMap(classLoader);
                    java.util.HashMap readHashMap2 = parcel.readHashMap(classLoader);
                    parcel.enforceNoDataAvail();
                    calculateScores(remoteCallback, createTypedArrayList, createStringArray, createStringArray2, readString, bundle, readHashMap, readHashMap2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.service.autofill.IAutofillFieldClassificationService {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.service.autofill.IAutofillFieldClassificationService.Stub.DESCRIPTOR;
            }

            @Override // android.service.autofill.IAutofillFieldClassificationService
            public void calculateScores(android.os.RemoteCallback remoteCallback, java.util.List<android.view.autofill.AutofillValue> list, java.lang.String[] strArr, java.lang.String[] strArr2, java.lang.String str, android.os.Bundle bundle, java.util.Map map, java.util.Map map2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.service.autofill.IAutofillFieldClassificationService.Stub.DESCRIPTOR);
                    obtain.writeTypedObject(remoteCallback, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeString(str);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeMap(map);
                    obtain.writeMap(map2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
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
