package android.companion.virtual.audio;

/* loaded from: classes.dex */
final class UserRestrictionsDetector extends android.content.BroadcastReceiver {
    private static final java.lang.String TAG = "UserRestrictionsDetector";
    private final android.content.Context mContext;
    private boolean mIsUnmuteMicDisallowed;
    private final java.lang.Object mLock = new java.lang.Object();
    private final android.os.UserManager mUserManager;
    private android.companion.virtual.audio.UserRestrictionsDetector.UserRestrictionsCallback mUserRestrictionsCallback;

    interface UserRestrictionsCallback {
        void onMicrophoneRestrictionChanged(boolean z);
    }

    UserRestrictionsDetector(android.content.Context context) {
        this.mContext = context;
        this.mUserManager = (android.os.UserManager) context.getSystemService(android.os.UserManager.class);
    }

    boolean isUnmuteMicrophoneDisallowed() {
        return this.mUserManager.getUserRestrictions().getBoolean(android.os.UserManager.DISALLOW_UNMUTE_MICROPHONE);
    }

    void register(android.companion.virtual.audio.UserRestrictionsDetector.UserRestrictionsCallback userRestrictionsCallback) {
        this.mUserRestrictionsCallback = userRestrictionsCallback;
        android.content.IntentFilter intentFilter = new android.content.IntentFilter();
        intentFilter.addAction(android.os.UserManager.ACTION_USER_RESTRICTIONS_CHANGED);
        this.mContext.registerReceiver(this, intentFilter);
        synchronized (this.mLock) {
            this.mIsUnmuteMicDisallowed = isUnmuteMicrophoneDisallowed();
        }
    }

    void unregister() {
        if (this.mUserRestrictionsCallback != null) {
            this.mUserRestrictionsCallback = null;
            this.mContext.unregisterReceiver(this);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        if (android.os.UserManager.ACTION_USER_RESTRICTIONS_CHANGED.equals(intent.getAction())) {
            boolean isUnmuteMicrophoneDisallowed = isUnmuteMicrophoneDisallowed();
            synchronized (this.mLock) {
                if (isUnmuteMicrophoneDisallowed == this.mIsUnmuteMicDisallowed) {
                    return;
                }
                this.mIsUnmuteMicDisallowed = isUnmuteMicrophoneDisallowed;
                if (this.mUserRestrictionsCallback != null) {
                    this.mUserRestrictionsCallback.onMicrophoneRestrictionChanged(isUnmuteMicrophoneDisallowed);
                }
            }
        }
    }
}
