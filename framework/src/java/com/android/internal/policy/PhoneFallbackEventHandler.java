package com.android.internal.policy;

/* loaded from: classes5.dex */
public class PhoneFallbackEventHandler implements android.view.FallbackEventHandler {
    private static final boolean DEBUG = false;
    private static java.lang.String TAG = "PhoneFallbackEventHandler";
    android.media.AudioManager mAudioManager;
    android.content.Context mContext;
    android.app.KeyguardManager mKeyguardManager;
    android.media.session.MediaSessionManager mMediaSessionManager;
    android.app.SearchManager mSearchManager;
    android.telephony.TelephonyManager mTelephonyManager;
    android.view.View mView;

    public PhoneFallbackEventHandler(android.content.Context context) {
        this.mContext = context;
    }

    @Override // android.view.FallbackEventHandler
    public void setView(android.view.View view) {
        this.mView = view;
    }

    @Override // android.view.FallbackEventHandler
    public void preDispatchKeyEvent(android.view.KeyEvent keyEvent) {
        getAudioManager().preDispatchKeyEvent(keyEvent, Integer.MIN_VALUE);
    }

    @Override // android.view.FallbackEventHandler
    public boolean dispatchKeyEvent(android.view.KeyEvent keyEvent) {
        int action = keyEvent.getAction();
        int keyCode = keyEvent.getKeyCode();
        if (action == 0) {
            return onKeyDown(keyCode, keyEvent);
        }
        return onKeyUp(keyCode, keyEvent);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    boolean onKeyDown(int i, android.view.KeyEvent keyEvent) {
        android.view.KeyEvent.DispatcherState keyDispatcherState = this.mView.getKeyDispatcherState();
        switch (i) {
            case 5:
                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                    if (keyEvent.getRepeatCount() == 0) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                        keyDispatcherState.performedLongPress(keyEvent);
                        if (isUserSetupComplete()) {
                            this.mView.performHapticFeedback(0);
                            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VOICE_COMMAND);
                            intent.setFlags(268435456);
                            try {
                                this.mContext.startActivity(intent);
                            } catch (android.content.ActivityNotFoundException e) {
                                startCallActivity();
                            }
                        } else {
                            android.util.Log.i(TAG, "Not starting call activity because user setup is in progress.");
                        }
                    }
                    return true;
                }
                return false;
            case 24:
            case 25:
            case 164:
                handleVolumeKeyEvent(keyEvent);
                return true;
            case 27:
                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                    if (keyEvent.getRepeatCount() == 0) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                        keyDispatcherState.performedLongPress(keyEvent);
                    }
                    return true;
                }
                return false;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 126:
            case 127:
            case 130:
            case 222:
                handleMediaKeyEvent(keyEvent);
                return true;
            case 84:
                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                    if (keyEvent.getRepeatCount() == 0) {
                        keyDispatcherState.startTracking(keyEvent, this);
                    } else if (keyEvent.isLongPress() && keyDispatcherState.isTracking(keyEvent)) {
                        android.content.res.Configuration configuration = this.mContext.getResources().getConfiguration();
                        if (configuration.keyboard == 1 || configuration.hardKeyboardHidden == 2) {
                            if (isUserSetupComplete()) {
                                android.content.Intent intent2 = new android.content.Intent(android.content.Intent.ACTION_SEARCH_LONG_PRESS);
                                intent2.setFlags(268435456);
                                try {
                                    this.mView.performHapticFeedback(0);
                                    getSearchManager().stopSearch();
                                    this.mContext.startActivity(intent2);
                                    keyDispatcherState.performedLongPress(keyEvent);
                                    return true;
                                } catch (android.content.ActivityNotFoundException e2) {
                                }
                            } else {
                                android.util.Log.i(TAG, "Not dispatching SEARCH long press because user setup is in progress.");
                            }
                        }
                    }
                }
                return false;
            default:
                return false;
        }
    }

    private boolean isNotInstantAppAndKeyguardRestricted(android.view.KeyEvent.DispatcherState dispatcherState) {
        return !this.mContext.getPackageManager().isInstantApp() && (getKeyguardManager().inKeyguardRestrictedInputMode() || dispatcherState == null);
    }

    boolean onKeyUp(int i, android.view.KeyEvent keyEvent) {
        android.view.KeyEvent.DispatcherState keyDispatcherState = this.mView.getKeyDispatcherState();
        if (keyDispatcherState != null) {
            keyDispatcherState.handleUpEvent(keyEvent);
        }
        switch (i) {
            case 5:
                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        if (isUserSetupComplete()) {
                            startCallActivity();
                            break;
                        } else {
                            android.util.Log.i(TAG, "Not starting call activity because user setup is in progress.");
                            break;
                        }
                    }
                }
                break;
            case 24:
            case 25:
            case 164:
                if (!keyEvent.isCanceled()) {
                    handleVolumeKeyEvent(keyEvent);
                    break;
                }
                break;
            case 27:
                if (!isNotInstantAppAndKeyguardRestricted(keyDispatcherState)) {
                    if (keyEvent.isTracking()) {
                        keyEvent.isCanceled();
                        break;
                    }
                }
                break;
            case 79:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 126:
            case 127:
            case 130:
            case 222:
                handleMediaKeyEvent(keyEvent);
                break;
        }
        return true;
    }

    void startCallActivity() {
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_CALL_BUTTON);
        intent.setFlags(268435456);
        try {
            this.mContext.startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            android.util.Log.w(TAG, "No activity found for android.intent.action.CALL_BUTTON.");
        }
    }

    android.app.SearchManager getSearchManager() {
        if (this.mSearchManager == null) {
            this.mSearchManager = (android.app.SearchManager) this.mContext.getSystemService("search");
        }
        return this.mSearchManager;
    }

    android.telephony.TelephonyManager getTelephonyManager() {
        if (this.mTelephonyManager == null) {
            this.mTelephonyManager = (android.telephony.TelephonyManager) this.mContext.getSystemService("phone");
        }
        return this.mTelephonyManager;
    }

    android.app.KeyguardManager getKeyguardManager() {
        if (this.mKeyguardManager == null) {
            this.mKeyguardManager = (android.app.KeyguardManager) this.mContext.getSystemService(android.content.Context.KEYGUARD_SERVICE);
        }
        return this.mKeyguardManager;
    }

    android.media.AudioManager getAudioManager() {
        if (this.mAudioManager == null) {
            this.mAudioManager = (android.media.AudioManager) this.mContext.getSystemService("audio");
        }
        return this.mAudioManager;
    }

    android.media.session.MediaSessionManager getMediaSessionManager() {
        if (this.mMediaSessionManager == null) {
            this.mMediaSessionManager = (android.media.session.MediaSessionManager) this.mContext.getSystemService(android.content.Context.MEDIA_SESSION_SERVICE);
        }
        return this.mMediaSessionManager;
    }

    private void handleVolumeKeyEvent(android.view.KeyEvent keyEvent) {
        getMediaSessionManager().dispatchVolumeKeyEventAsSystemService(keyEvent, Integer.MIN_VALUE);
    }

    private void handleMediaKeyEvent(android.view.KeyEvent keyEvent) {
        getMediaSessionManager().dispatchMediaKeyEventAsSystemService(keyEvent);
    }

    private boolean isUserSetupComplete() {
        return android.provider.Settings.Secure.getInt(this.mContext.getContentResolver(), android.provider.Settings.Secure.USER_SETUP_COMPLETE, 0) != 0;
    }
}
