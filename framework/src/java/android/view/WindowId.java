package android.view;

/* loaded from: classes4.dex */
public class WindowId implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.WindowId> CREATOR = new android.os.Parcelable.Creator<android.view.WindowId>() { // from class: android.view.WindowId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowId createFromParcel(android.os.Parcel parcel) {
            android.os.IBinder readStrongBinder = parcel.readStrongBinder();
            if (readStrongBinder != null) {
                return new android.view.WindowId(readStrongBinder);
            }
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.WindowId[] newArray(int i) {
            return new android.view.WindowId[i];
        }
    };
    private final android.view.IWindowId mToken;

    public static abstract class FocusObserver {
        final android.view.IWindowFocusObserver.Stub mIObserver = new android.view.IWindowFocusObserver.Stub() { // from class: android.view.WindowId.FocusObserver.1
            @Override // android.view.IWindowFocusObserver
            public void focusGained(android.os.IBinder iBinder) {
                android.view.WindowId windowId;
                synchronized (android.view.WindowId.FocusObserver.this.mRegistrations) {
                    windowId = android.view.WindowId.FocusObserver.this.mRegistrations.get(iBinder);
                }
                if (android.view.WindowId.FocusObserver.this.mHandler != null) {
                    android.view.WindowId.FocusObserver.this.mHandler.sendMessage(android.view.WindowId.FocusObserver.this.mHandler.obtainMessage(1, windowId));
                } else {
                    android.view.WindowId.FocusObserver.this.onFocusGained(windowId);
                }
            }

            @Override // android.view.IWindowFocusObserver
            public void focusLost(android.os.IBinder iBinder) {
                android.view.WindowId windowId;
                synchronized (android.view.WindowId.FocusObserver.this.mRegistrations) {
                    windowId = android.view.WindowId.FocusObserver.this.mRegistrations.get(iBinder);
                }
                if (android.view.WindowId.FocusObserver.this.mHandler != null) {
                    android.view.WindowId.FocusObserver.this.mHandler.sendMessage(android.view.WindowId.FocusObserver.this.mHandler.obtainMessage(2, windowId));
                } else {
                    android.view.WindowId.FocusObserver.this.onFocusLost(windowId);
                }
            }
        };
        final java.util.HashMap<android.os.IBinder, android.view.WindowId> mRegistrations = new java.util.HashMap<>();
        final android.os.Handler mHandler = new android.view.WindowId.FocusObserver.H();

        public abstract void onFocusGained(android.view.WindowId windowId);

        public abstract void onFocusLost(android.view.WindowId windowId);

        class H extends android.os.Handler {
            H() {
            }

            @Override // android.os.Handler
            public void handleMessage(android.os.Message message) {
                switch (message.what) {
                    case 1:
                        android.view.WindowId.FocusObserver.this.onFocusGained((android.view.WindowId) message.obj);
                        break;
                    case 2:
                        android.view.WindowId.FocusObserver.this.onFocusLost((android.view.WindowId) message.obj);
                        break;
                    default:
                        super.handleMessage(message);
                        break;
                }
            }
        }
    }

    public boolean isFocused() {
        try {
            return this.mToken.isFocused();
        } catch (android.os.RemoteException e) {
            return false;
        }
    }

    public void registerFocusObserver(android.view.WindowId.FocusObserver focusObserver) {
        synchronized (focusObserver.mRegistrations) {
            if (focusObserver.mRegistrations.containsKey(this.mToken.asBinder())) {
                throw new java.lang.IllegalStateException("Focus observer already registered with input token");
            }
            focusObserver.mRegistrations.put(this.mToken.asBinder(), this);
            try {
                this.mToken.registerFocusObserver(focusObserver.mIObserver);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public void unregisterFocusObserver(android.view.WindowId.FocusObserver focusObserver) {
        synchronized (focusObserver.mRegistrations) {
            if (focusObserver.mRegistrations.remove(this.mToken.asBinder()) == null) {
                throw new java.lang.IllegalStateException("Focus observer not registered with input token");
            }
            try {
                this.mToken.unregisterFocusObserver(focusObserver.mIObserver);
            } catch (android.os.RemoteException e) {
            }
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj instanceof android.view.WindowId) {
            return this.mToken.asBinder().equals(((android.view.WindowId) obj).mToken.asBinder());
        }
        return false;
    }

    public int hashCode() {
        return this.mToken.asBinder().hashCode();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("IntentSender{");
        sb.append(java.lang.Integer.toHexString(java.lang.System.identityHashCode(this)));
        sb.append(": ");
        sb.append(this.mToken.asBinder());
        sb.append('}');
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken.asBinder());
    }

    public android.view.IWindowId getTarget() {
        return this.mToken;
    }

    public WindowId(android.view.IWindowId iWindowId) {
        this.mToken = iWindowId;
    }

    public WindowId(android.os.IBinder iBinder) {
        this.mToken = android.view.IWindowId.Stub.asInterface(iBinder);
    }
}
