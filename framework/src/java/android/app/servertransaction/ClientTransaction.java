package android.app.servertransaction;

/* loaded from: classes.dex */
public class ClientTransaction implements android.os.Parcelable, android.app.servertransaction.ObjectPoolItem {
    public static final android.os.Parcelable.Creator<android.app.servertransaction.ClientTransaction> CREATOR = new android.os.Parcelable.Creator<android.app.servertransaction.ClientTransaction>() { // from class: android.app.servertransaction.ClientTransaction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ClientTransaction createFromParcel(android.os.Parcel parcel) {
            return new android.app.servertransaction.ClientTransaction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.servertransaction.ClientTransaction[] newArray(int i) {
            return new android.app.servertransaction.ClientTransaction[i];
        }
    };

    @java.lang.Deprecated
    private java.util.List<android.app.servertransaction.ClientTransactionItem> mActivityCallbacks;
    private android.os.IBinder mActivityToken;
    private android.app.IApplicationThread mClient;
    private android.app.servertransaction.ActivityLifecycleItem mLifecycleStateRequest;
    private java.util.List<android.app.servertransaction.ClientTransactionItem> mTransactionItems;

    public android.app.IApplicationThread getClient() {
        return this.mClient;
    }

    public void addTransactionItem(android.app.servertransaction.ClientTransactionItem clientTransactionItem) {
        if (com.android.window.flags.Flags.bundleClientTransactionFlag()) {
            if (this.mTransactionItems == null) {
                this.mTransactionItems = new java.util.ArrayList();
            }
            this.mTransactionItems.add(clientTransactionItem);
        }
        if (clientTransactionItem.isActivityLifecycleItem()) {
            setLifecycleStateRequest((android.app.servertransaction.ActivityLifecycleItem) clientTransactionItem);
        } else {
            addCallback(clientTransactionItem);
        }
    }

    public java.util.List<android.app.servertransaction.ClientTransactionItem> getTransactionItems() {
        return this.mTransactionItems;
    }

    @java.lang.Deprecated
    private void addCallback(android.app.servertransaction.ClientTransactionItem clientTransactionItem) {
        if (this.mActivityCallbacks == null) {
            this.mActivityCallbacks = new java.util.ArrayList();
        }
        this.mActivityCallbacks.add(clientTransactionItem);
        setActivityTokenIfNotSet(clientTransactionItem);
    }

    @java.lang.Deprecated
    public java.util.List<android.app.servertransaction.ClientTransactionItem> getCallbacks() {
        return this.mActivityCallbacks;
    }

    @java.lang.Deprecated
    public android.os.IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @java.lang.Deprecated
    public android.app.servertransaction.ActivityLifecycleItem getLifecycleStateRequest() {
        return this.mLifecycleStateRequest;
    }

    @java.lang.Deprecated
    private void setLifecycleStateRequest(android.app.servertransaction.ActivityLifecycleItem activityLifecycleItem) {
        if (this.mLifecycleStateRequest != null) {
            return;
        }
        this.mLifecycleStateRequest = activityLifecycleItem;
        setActivityTokenIfNotSet(activityLifecycleItem);
    }

    private void setActivityTokenIfNotSet(android.app.servertransaction.ClientTransactionItem clientTransactionItem) {
        if (this.mActivityToken == null && clientTransactionItem != null) {
            this.mActivityToken = clientTransactionItem.getActivityToken();
        }
    }

    public void preExecute(android.app.ClientTransactionHandler clientTransactionHandler) {
        int i = 0;
        if (this.mTransactionItems != null) {
            int size = this.mTransactionItems.size();
            while (i < size) {
                this.mTransactionItems.get(i).preExecute(clientTransactionHandler);
                i++;
            }
            return;
        }
        if (this.mActivityCallbacks != null) {
            int size2 = this.mActivityCallbacks.size();
            while (i < size2) {
                this.mActivityCallbacks.get(i).preExecute(clientTransactionHandler);
                i++;
            }
        }
        if (this.mLifecycleStateRequest != null) {
            this.mLifecycleStateRequest.preExecute(clientTransactionHandler);
        }
    }

    public void schedule() throws android.os.RemoteException {
        this.mClient.scheduleTransaction(this);
    }

    private ClientTransaction() {
    }

    public static android.app.servertransaction.ClientTransaction obtain(android.app.IApplicationThread iApplicationThread) {
        android.app.servertransaction.ClientTransaction clientTransaction = (android.app.servertransaction.ClientTransaction) android.app.servertransaction.ObjectPool.obtain(android.app.servertransaction.ClientTransaction.class);
        if (clientTransaction == null) {
            clientTransaction = new android.app.servertransaction.ClientTransaction();
        }
        clientTransaction.mClient = iApplicationThread;
        return clientTransaction;
    }

    @Override // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        int i = 0;
        if (this.mTransactionItems != null) {
            int size = this.mTransactionItems.size();
            while (i < size) {
                this.mTransactionItems.get(i).recycle();
                i++;
            }
            this.mTransactionItems = null;
            this.mActivityCallbacks = null;
            this.mLifecycleStateRequest = null;
        } else {
            if (this.mActivityCallbacks != null) {
                int size2 = this.mActivityCallbacks.size();
                while (i < size2) {
                    this.mActivityCallbacks.get(i).recycle();
                    i++;
                }
                this.mActivityCallbacks = null;
            }
            if (this.mLifecycleStateRequest != null) {
                this.mLifecycleStateRequest.recycle();
                this.mLifecycleStateRequest = null;
            }
        }
        this.mClient = null;
        this.mActivityToken = null;
        android.app.servertransaction.ObjectPool.recycle(this);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        boolean z = this.mTransactionItems != null;
        parcel.writeBoolean(z);
        if (z) {
            parcel.writeParcelableList(this.mTransactionItems, i);
            return;
        }
        parcel.writeParcelable(this.mLifecycleStateRequest, i);
        boolean z2 = this.mActivityCallbacks != null;
        parcel.writeBoolean(z2);
        if (z2) {
            parcel.writeParcelableList(this.mActivityCallbacks, i);
        }
    }

    private ClientTransaction(android.os.Parcel parcel) {
        int i = 0;
        if (parcel.readBoolean()) {
            this.mTransactionItems = new java.util.ArrayList();
            parcel.readParcelableList(this.mTransactionItems, getClass().getClassLoader(), android.app.servertransaction.ClientTransactionItem.class);
            int size = this.mTransactionItems.size();
            while (i < size) {
                android.app.servertransaction.ClientTransactionItem clientTransactionItem = this.mTransactionItems.get(i);
                if (clientTransactionItem.isActivityLifecycleItem()) {
                    setLifecycleStateRequest((android.app.servertransaction.ActivityLifecycleItem) clientTransactionItem);
                } else {
                    addCallback(clientTransactionItem);
                }
                i++;
            }
            return;
        }
        this.mLifecycleStateRequest = (android.app.servertransaction.ActivityLifecycleItem) parcel.readParcelable(getClass().getClassLoader(), android.app.servertransaction.ActivityLifecycleItem.class);
        setActivityTokenIfNotSet(this.mLifecycleStateRequest);
        if (parcel.readBoolean()) {
            this.mActivityCallbacks = new java.util.ArrayList();
            parcel.readParcelableList(this.mActivityCallbacks, getClass().getClassLoader(), android.app.servertransaction.ClientTransactionItem.class);
            int size2 = this.mActivityCallbacks.size();
            while (this.mActivityToken == null && i < size2) {
                setActivityTokenIfNotSet(this.mActivityCallbacks.get(i));
                i++;
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.servertransaction.ClientTransaction clientTransaction = (android.app.servertransaction.ClientTransaction) obj;
        if (java.util.Objects.equals(this.mTransactionItems, clientTransaction.mTransactionItems) && java.util.Objects.equals(this.mActivityCallbacks, clientTransaction.mActivityCallbacks) && java.util.Objects.equals(this.mLifecycleStateRequest, clientTransaction.mLifecycleStateRequest) && this.mClient == clientTransaction.mClient && java.util.Objects.equals(this.mActivityToken, clientTransaction.mActivityToken)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((527 + java.util.Objects.hashCode(this.mTransactionItems)) * 31) + java.util.Objects.hashCode(this.mActivityCallbacks)) * 31) + java.util.Objects.hashCode(this.mLifecycleStateRequest)) * 31) + java.util.Objects.hashCode(this.mClient)) * 31) + java.util.Objects.hashCode(this.mActivityToken);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("ClientTransaction{");
        int i = 0;
        if (this.mTransactionItems != null) {
            sb.append("\n  transactionItems=[");
            int size = this.mTransactionItems.size();
            while (i < size) {
                sb.append("\n    ").append(this.mTransactionItems.get(i));
                i++;
            }
            sb.append("\n  ]");
        } else {
            sb.append("\n  callbacks=[");
            int size2 = this.mActivityCallbacks != null ? this.mActivityCallbacks.size() : 0;
            while (i < size2) {
                sb.append("\n    ").append(this.mActivityCallbacks.get(i));
                i++;
            }
            sb.append("\n  ]");
            sb.append("\n  stateRequest=").append(this.mLifecycleStateRequest);
        }
        sb.append("\n}");
        return sb.toString();
    }

    void dump(java.lang.String str, java.io.PrintWriter printWriter, android.app.ClientTransactionHandler clientTransactionHandler) {
        printWriter.append((java.lang.CharSequence) str).println("ClientTransaction{");
        int i = 0;
        if (this.mTransactionItems != null) {
            printWriter.append((java.lang.CharSequence) str).print("  transactionItems=[");
            java.lang.String str2 = str + "    ";
            int size = this.mTransactionItems.size();
            if (size > 0) {
                printWriter.println();
                while (i < size) {
                    this.mTransactionItems.get(i).dump(str2, printWriter, clientTransactionHandler);
                    i++;
                }
                printWriter.append((java.lang.CharSequence) str).println("  ]");
            } else {
                printWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
            }
            printWriter.append((java.lang.CharSequence) str).println("}");
            return;
        }
        printWriter.append((java.lang.CharSequence) str).print("  callbacks=[");
        java.lang.String str3 = str + "    ";
        int size2 = this.mActivityCallbacks != null ? this.mActivityCallbacks.size() : 0;
        if (size2 > 0) {
            printWriter.println();
            while (i < size2) {
                this.mActivityCallbacks.get(i).dump(str3, printWriter, clientTransactionHandler);
                i++;
            }
            printWriter.append((java.lang.CharSequence) str).println("  ]");
        } else {
            printWriter.println(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        }
        printWriter.append((java.lang.CharSequence) str).println("  stateRequest=");
        if (this.mLifecycleStateRequest != null) {
            this.mLifecycleStateRequest.dump(str3, printWriter, clientTransactionHandler);
        } else {
            printWriter.append((java.lang.CharSequence) str3).println("null");
        }
        printWriter.append((java.lang.CharSequence) str).println("}");
    }
}
