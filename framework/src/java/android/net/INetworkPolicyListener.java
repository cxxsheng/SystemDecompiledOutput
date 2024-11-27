package android.net;

/* loaded from: classes2.dex */
public interface INetworkPolicyListener extends android.os.IInterface {
    void onAllowedTransportsChanged(int[] iArr, long[] jArr) throws android.os.RemoteException;

    void onBlockedReasonChanged(int i, int i2, int i3) throws android.os.RemoteException;

    void onMeteredIfacesChanged(java.lang.String[] strArr) throws android.os.RemoteException;

    void onRestrictBackgroundChanged(boolean z) throws android.os.RemoteException;

    void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws android.os.RemoteException;

    void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) throws android.os.RemoteException;

    void onUidPoliciesChanged(int i, int i2) throws android.os.RemoteException;

    void onUidRulesChanged(int i, int i2) throws android.os.RemoteException;

    public static class Default implements android.net.INetworkPolicyListener {
        @Override // android.net.INetworkPolicyListener
        public void onUidRulesChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onMeteredIfacesChanged(java.lang.String[] strArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onRestrictBackgroundChanged(boolean z) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onUidPoliciesChanged(int i, int i2) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onBlockedReasonChanged(int i, int i2, int i3) throws android.os.RemoteException {
        }

        @Override // android.net.INetworkPolicyListener
        public void onAllowedTransportsChanged(int[] iArr, long[] jArr) throws android.os.RemoteException {
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends android.os.Binder implements android.net.INetworkPolicyListener {
        public static final java.lang.String DESCRIPTOR = "android.net.INetworkPolicyListener";
        static final int TRANSACTION_onAllowedTransportsChanged = 8;
        static final int TRANSACTION_onBlockedReasonChanged = 7;
        static final int TRANSACTION_onMeteredIfacesChanged = 2;
        static final int TRANSACTION_onRestrictBackgroundChanged = 3;
        static final int TRANSACTION_onSubscriptionOverride = 5;
        static final int TRANSACTION_onSubscriptionPlansChanged = 6;
        static final int TRANSACTION_onUidPoliciesChanged = 4;
        static final int TRANSACTION_onUidRulesChanged = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static android.net.INetworkPolicyListener asInterface(android.os.IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            android.os.IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof android.net.INetworkPolicyListener)) {
                return (android.net.INetworkPolicyListener) queryLocalInterface;
            }
            return new android.net.INetworkPolicyListener.Stub.Proxy(iBinder);
        }

        @Override // android.os.IInterface
        public android.os.IBinder asBinder() {
            return this;
        }

        public static java.lang.String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onUidRulesChanged";
                case 2:
                    return "onMeteredIfacesChanged";
                case 3:
                    return "onRestrictBackgroundChanged";
                case 4:
                    return "onUidPoliciesChanged";
                case 5:
                    return "onSubscriptionOverride";
                case 6:
                    return "onSubscriptionPlansChanged";
                case 7:
                    return "onBlockedReasonChanged";
                case 8:
                    return "onAllowedTransportsChanged";
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
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidRulesChanged(readInt, readInt2);
                    return true;
                case 2:
                    java.lang.String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    onMeteredIfacesChanged(createStringArray);
                    return true;
                case 3:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onRestrictBackgroundChanged(readBoolean);
                    return true;
                case 4:
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onUidPoliciesChanged(readInt3, readInt4);
                    return true;
                case 5:
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int[] createIntArray = parcel.createIntArray();
                    parcel.enforceNoDataAvail();
                    onSubscriptionOverride(readInt5, readInt6, readInt7, createIntArray);
                    return true;
                case 6:
                    int readInt8 = parcel.readInt();
                    android.telephony.SubscriptionPlan[] subscriptionPlanArr = (android.telephony.SubscriptionPlan[]) parcel.createTypedArray(android.telephony.SubscriptionPlan.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSubscriptionPlansChanged(readInt8, subscriptionPlanArr);
                    return true;
                case 7:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onBlockedReasonChanged(readInt9, readInt10, readInt11);
                    return true;
                case 8:
                    int[] createIntArray2 = parcel.createIntArray();
                    long[] createLongArray = parcel.createLongArray();
                    parcel.enforceNoDataAvail();
                    onAllowedTransportsChanged(createIntArray2, createLongArray);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements android.net.INetworkPolicyListener {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public android.os.IBinder asBinder() {
                return this.mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return android.net.INetworkPolicyListener.Stub.DESCRIPTOR;
            }

            @Override // android.net.INetworkPolicyListener
            public void onUidRulesChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onMeteredIfacesChanged(java.lang.String[] strArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onRestrictBackgroundChanged(boolean z) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onUidPoliciesChanged(int i, int i2) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionOverride(int i, int i2, int i3, int[] iArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onSubscriptionPlansChanged(int i, android.telephony.SubscriptionPlan[] subscriptionPlanArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(subscriptionPlanArr, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onBlockedReasonChanged(int i, int i2, int i3) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.INetworkPolicyListener
            public void onAllowedTransportsChanged(int[] iArr, long[] jArr) throws android.os.RemoteException {
                android.os.Parcel obtain = android.os.Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(android.net.INetworkPolicyListener.Stub.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    obtain.writeLongArray(jArr);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }
    }
}
