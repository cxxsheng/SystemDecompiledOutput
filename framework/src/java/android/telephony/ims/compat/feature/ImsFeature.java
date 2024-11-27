package android.telephony.ims.compat.feature;

/* loaded from: classes3.dex */
public abstract class ImsFeature {
    public static final int EMERGENCY_MMTEL = 0;
    public static final int INVALID = -1;
    private static final java.lang.String LOG_TAG = "ImsFeature";
    public static final int MAX = 3;
    public static final int MMTEL = 1;
    public static final int RCS = 2;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_NOT_AVAILABLE = 0;
    public static final int STATE_READY = 2;
    protected android.content.Context mContext;
    private final java.util.Set<com.android.ims.internal.IImsFeatureStatusCallback> mStatusCallbacks = java.util.Collections.newSetFromMap(new java.util.WeakHashMap());
    private int mState = 0;
    private int mSlotId = -1;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ImsState {
    }

    public abstract android.os.IInterface getBinder();

    public abstract void onFeatureReady();

    public abstract void onFeatureRemoved();

    public void setContext(android.content.Context context) {
        this.mContext = context;
    }

    public void setSlotId(int i) {
        this.mSlotId = i;
    }

    public int getFeatureState() {
        return this.mState;
    }

    protected final void setFeatureState(int i) {
        if (this.mState != i) {
            this.mState = i;
            notifyFeatureState(i);
        }
    }

    public void addImsFeatureStatusCallback(com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        try {
            iImsFeatureStatusCallback.notifyImsFeatureStatus(this.mState);
            synchronized (this.mStatusCallbacks) {
                this.mStatusCallbacks.add(iImsFeatureStatusCallback);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
        }
    }

    public void removeImsFeatureStatusCallback(com.android.ims.internal.IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        if (iImsFeatureStatusCallback == null) {
            return;
        }
        synchronized (this.mStatusCallbacks) {
            this.mStatusCallbacks.remove(iImsFeatureStatusCallback);
        }
    }

    private void notifyFeatureState(int i) {
        synchronized (this.mStatusCallbacks) {
            java.util.Iterator<com.android.ims.internal.IImsFeatureStatusCallback> it = this.mStatusCallbacks.iterator();
            while (it.hasNext()) {
                com.android.ims.internal.IImsFeatureStatusCallback next = it.next();
                try {
                    android.util.Log.i(LOG_TAG, "notifying ImsFeatureState=" + i);
                    next.notifyImsFeatureStatus(i);
                } catch (android.os.RemoteException e) {
                    it.remove();
                    android.util.Log.w(LOG_TAG, "Couldn't notify feature state: " + e.getMessage());
                }
            }
        }
    }
}
