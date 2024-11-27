package android.window;

/* loaded from: classes4.dex */
public class ImeOnBackInvokedDispatcher implements android.window.OnBackInvokedDispatcher, android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.window.ImeOnBackInvokedDispatcher> CREATOR = new android.os.Parcelable.Creator<android.window.ImeOnBackInvokedDispatcher>() { // from class: android.window.ImeOnBackInvokedDispatcher.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ImeOnBackInvokedDispatcher createFromParcel(android.os.Parcel parcel) {
            return new android.window.ImeOnBackInvokedDispatcher(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.window.ImeOnBackInvokedDispatcher[] newArray(int i) {
            return new android.window.ImeOnBackInvokedDispatcher[i];
        }
    };
    static final int RESULT_CODE_REGISTER = 0;
    static final int RESULT_CODE_UNREGISTER = 1;
    static final java.lang.String RESULT_KEY_CALLBACK = "callback";
    static final java.lang.String RESULT_KEY_ID = "id";
    static final java.lang.String RESULT_KEY_PRIORITY = "priority";
    private static final java.lang.String TAG = "ImeBackDispatcher";
    private final android.os.ResultReceiver mResultReceiver;
    private final android.window.BackProgressAnimator mProgressAnimator = new android.window.BackProgressAnimator();
    private final java.util.ArrayList<android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback> mImeCallbacks = new java.util.ArrayList<>();

    public ImeOnBackInvokedDispatcher(android.os.Handler handler) {
        this.mResultReceiver = new android.os.ResultReceiver(handler) { // from class: android.window.ImeOnBackInvokedDispatcher.1
            @Override // android.os.ResultReceiver
            public void onReceiveResult(int i, android.os.Bundle bundle) {
                android.window.WindowOnBackInvokedDispatcher receivingDispatcher = android.window.ImeOnBackInvokedDispatcher.this.getReceivingDispatcher();
                if (receivingDispatcher != null) {
                    android.window.ImeOnBackInvokedDispatcher.this.receive(i, bundle, receivingDispatcher);
                }
            }
        };
    }

    protected android.window.WindowOnBackInvokedDispatcher getReceivingDispatcher() {
        return null;
    }

    ImeOnBackInvokedDispatcher(android.os.Parcel parcel) {
        this.mResultReceiver = (android.os.ResultReceiver) parcel.readTypedObject(android.os.ResultReceiver.CREATOR);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void registerOnBackInvokedCallback(int i, android.window.OnBackInvokedCallback onBackInvokedCallback) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putBinder(RESULT_KEY_CALLBACK, new android.window.WindowOnBackInvokedDispatcher.OnBackInvokedCallbackWrapper(onBackInvokedCallback, this.mProgressAnimator, false).asBinder());
        bundle.putInt("priority", i);
        bundle.putInt("id", onBackInvokedCallback.hashCode());
        this.mResultReceiver.send(0, bundle);
    }

    @Override // android.window.OnBackInvokedDispatcher
    public void unregisterOnBackInvokedCallback(android.window.OnBackInvokedCallback onBackInvokedCallback) {
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putInt("id", onBackInvokedCallback.hashCode());
        this.mResultReceiver.send(1, bundle);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeTypedObject(this.mResultReceiver, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void receive(int i, android.os.Bundle bundle, android.window.WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
        int i2 = bundle.getInt("id");
        if (i == 0) {
            registerReceivedCallback(android.window.IOnBackInvokedCallback.Stub.asInterface(bundle.getBinder(RESULT_KEY_CALLBACK)), bundle.getInt("priority"), i2, windowOnBackInvokedDispatcher);
        } else if (i == 1) {
            unregisterReceivedCallback(i2, windowOnBackInvokedDispatcher);
        }
    }

    private void registerReceivedCallback(android.window.IOnBackInvokedCallback iOnBackInvokedCallback, int i, int i2, android.window.WindowOnBackInvokedDispatcher windowOnBackInvokedDispatcher) {
        android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback imeOnBackInvokedCallback = new android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback(iOnBackInvokedCallback, i2, i);
        this.mImeCallbacks.add(imeOnBackInvokedCallback);
        windowOnBackInvokedDispatcher.registerOnBackInvokedCallbackUnchecked(imeOnBackInvokedCallback, i);
    }

    private void unregisterReceivedCallback(int i, android.window.OnBackInvokedDispatcher onBackInvokedDispatcher) {
        android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback imeOnBackInvokedCallback;
        java.util.Iterator<android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (true) {
            if (!it.hasNext()) {
                imeOnBackInvokedCallback = null;
                break;
            } else {
                imeOnBackInvokedCallback = it.next();
                if (imeOnBackInvokedCallback.getId() == i) {
                    break;
                }
            }
        }
        if (imeOnBackInvokedCallback == null) {
            android.util.Log.e(TAG, "Ime callback not found. Ignoring unregisterReceivedCallback. callbackId: " + i);
        } else {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(imeOnBackInvokedCallback);
            this.mImeCallbacks.remove(imeOnBackInvokedCallback);
        }
    }

    public void clear() {
        if (getReceivingDispatcher() != null) {
            java.util.Iterator<android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
            while (it.hasNext()) {
                getReceivingDispatcher().unregisterOnBackInvokedCallback(it.next());
            }
        }
        this.mImeCallbacks.clear();
        android.os.Handler main = android.os.Handler.getMain();
        android.window.BackProgressAnimator backProgressAnimator = this.mProgressAnimator;
        java.util.Objects.requireNonNull(backProgressAnimator);
        main.post(new android.window.ImeOnBackInvokedDispatcher$$ExternalSyntheticLambda0(backProgressAnimator));
    }

    static class ImeOnBackInvokedCallback implements android.window.OnBackInvokedCallback {
        private final android.window.IOnBackInvokedCallback mIOnBackInvokedCallback;
        private final int mId;
        private final int mPriority;

        ImeOnBackInvokedCallback(android.window.IOnBackInvokedCallback iOnBackInvokedCallback, int i, int i2) {
            this.mIOnBackInvokedCallback = iOnBackInvokedCallback;
            this.mId = i;
            this.mPriority = i2;
        }

        @Override // android.window.OnBackInvokedCallback
        public void onBackInvoked() {
            try {
                if (this.mIOnBackInvokedCallback != null) {
                    this.mIOnBackInvokedCallback.onBackInvoked();
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(android.window.ImeOnBackInvokedDispatcher.TAG, "Exception when invoking forwarded callback. e: ", e);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getId() {
            return this.mId;
        }

        android.window.IOnBackInvokedCallback getIOnBackInvokedCallback() {
            return this.mIOnBackInvokedCallback;
        }

        public java.lang.String toString() {
            return "ImeCallback=ImeOnBackInvokedCallback@" + this.mId + " Callback=" + this.mIOnBackInvokedCallback;
        }
    }

    public void switchRootView(android.view.ViewRootImpl viewRootImpl, android.view.ViewRootImpl viewRootImpl2) {
        java.util.Iterator<android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback> it = this.mImeCallbacks.iterator();
        while (it.hasNext()) {
            android.window.ImeOnBackInvokedDispatcher.ImeOnBackInvokedCallback next = it.next();
            if (viewRootImpl != null) {
                viewRootImpl.getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(next);
            }
            if (viewRootImpl2 != null) {
                viewRootImpl2.getOnBackInvokedDispatcher().registerOnBackInvokedCallbackUnchecked(next, next.mPriority);
            }
        }
    }
}
