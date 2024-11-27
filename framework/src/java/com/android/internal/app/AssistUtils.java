package com.android.internal.app;

/* loaded from: classes4.dex */
public class AssistUtils {
    public static final int INVOCATION_TYPE_ASSIST_BUTTON = 7;
    public static final int INVOCATION_TYPE_GESTURE = 1;
    public static final int INVOCATION_TYPE_HOME_BUTTON_LONG_PRESS = 5;
    public static final java.lang.String INVOCATION_TYPE_KEY = "invocation_type";
    public static final int INVOCATION_TYPE_NAV_HANDLE_LONG_PRESS = 8;
    public static final int INVOCATION_TYPE_PHYSICAL_GESTURE = 2;
    public static final int INVOCATION_TYPE_POWER_BUTTON_LONG_PRESS = 6;
    public static final int INVOCATION_TYPE_QUICK_SEARCH_BAR = 4;
    public static final int INVOCATION_TYPE_UNKNOWN = 0;
    public static final int INVOCATION_TYPE_VOICE = 3;
    private static final java.lang.String TAG = "AssistUtils";
    private final android.content.Context mContext;
    private final com.android.internal.app.IVoiceInteractionManagerService mVoiceInteractionManagerService = com.android.internal.app.IVoiceInteractionManagerService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.VOICE_INTERACTION_MANAGER_SERVICE));

    public AssistUtils(android.content.Context context) {
        this.mContext = context;
    }

    @java.lang.Deprecated
    public boolean showSessionForActiveService(android.os.Bundle bundle, int i, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) {
        return showSessionForActiveServiceInternal(bundle, i, null, iVoiceInteractionSessionShowCallback, iBinder);
    }

    public boolean showSessionForActiveService(android.os.Bundle bundle, int i, java.lang.String str, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) {
        return showSessionForActiveServiceInternal(bundle, i, str, iVoiceInteractionSessionShowCallback, iBinder);
    }

    private boolean showSessionForActiveServiceInternal(android.os.Bundle bundle, int i, java.lang.String str, com.android.internal.app.IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback, android.os.IBinder iBinder) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                return this.mVoiceInteractionManagerService.showSessionForActiveService(bundle, i, str, iVoiceInteractionSessionShowCallback, iBinder);
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call showSessionForActiveService", e);
            return false;
        }
    }

    public void getActiveServiceSupportedActions(java.util.Set<java.lang.String> set, com.android.internal.app.IVoiceActionCheckCallback iVoiceActionCheckCallback) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.getActiveServiceSupportedActions(new java.util.ArrayList(set), iVoiceActionCheckCallback);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call activeServiceSupportedActions", e);
            try {
                iVoiceActionCheckCallback.onComplete(null);
            } catch (android.os.RemoteException e2) {
            }
        }
    }

    public void launchVoiceAssistFromKeyguard() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.launchVoiceAssistFromKeyguard();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call launchVoiceAssistFromKeyguard", e);
        }
    }

    public boolean activeServiceSupportsAssistGesture() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                return this.mVoiceInteractionManagerService.activeServiceSupportsAssist();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call activeServiceSupportsAssistGesture", e);
            return false;
        }
    }

    public boolean activeServiceSupportsLaunchFromKeyguard() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                return this.mVoiceInteractionManagerService.activeServiceSupportsLaunchFromKeyguard();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call activeServiceSupportsLaunchFromKeyguard", e);
            return false;
        }
    }

    public android.content.ComponentName getActiveServiceComponentName() {
        try {
            if (this.mVoiceInteractionManagerService == null) {
                return null;
            }
            return this.mVoiceInteractionManagerService.getActiveServiceComponentName();
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call getActiveServiceComponentName", e);
            return null;
        }
    }

    public boolean isSessionRunning() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                return this.mVoiceInteractionManagerService.isSessionRunning();
            }
            return false;
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call isSessionRunning", e);
            return false;
        }
    }

    public void hideCurrentSession() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.hideCurrentSession();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call hideCurrentSession", e);
        }
    }

    public void onLockscreenShown() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.onLockscreenShown();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to call onLockscreenShown", e);
        }
    }

    public void registerVoiceInteractionSessionListener(com.android.internal.app.IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.registerVoiceInteractionSessionListener(iVoiceInteractionSessionListener);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to register voice interaction listener", e);
        }
    }

    public void subscribeVisualQueryRecognitionStatus(com.android.internal.app.IVisualQueryRecognitionStatusListener iVisualQueryRecognitionStatusListener) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.subscribeVisualQueryRecognitionStatus(iVisualQueryRecognitionStatusListener);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to register visual query detection start listener", e);
        }
    }

    public void enableVisualQueryDetection(com.android.internal.app.IVisualQueryDetectionAttentionListener iVisualQueryDetectionAttentionListener) {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.enableVisualQueryDetection(iVisualQueryDetectionAttentionListener);
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to register visual query detection attention listener", e);
        }
    }

    public void disableVisualQueryDetection() {
        try {
            if (this.mVoiceInteractionManagerService != null) {
                this.mVoiceInteractionManagerService.disableVisualQueryDetection();
            }
        } catch (android.os.RemoteException e) {
            android.util.Log.w(TAG, "Failed to register visual query detection attention listener", e);
        }
    }

    public android.content.ComponentName getAssistComponentForUser(int i) {
        java.lang.String stringForUser = android.provider.Settings.Secure.getStringForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.ASSISTANT, i);
        if (stringForUser != null) {
            return android.content.ComponentName.unflattenFromString(stringForUser);
        }
        return null;
    }

    public static boolean isPreinstalledAssistant(android.content.Context context, android.content.ComponentName componentName) {
        if (componentName == null) {
            return false;
        }
        try {
            android.content.pm.ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(componentName.getPackageName(), 0);
            return applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp();
        } catch (android.content.pm.PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isDisclosureEnabled(android.content.Context context) {
        return android.provider.Settings.Secure.getInt(context.getContentResolver(), android.provider.Settings.Secure.ASSIST_DISCLOSURE_ENABLED, 0) != 0;
    }

    public static boolean shouldDisclose(android.content.Context context, android.content.ComponentName componentName) {
        return (allowDisablingAssistDisclosure(context) && !isDisclosureEnabled(context) && isPreinstalledAssistant(context, componentName)) ? false : true;
    }

    public static boolean allowDisablingAssistDisclosure(android.content.Context context) {
        return context.getResources().getBoolean(com.android.internal.R.bool.config_allowDisablingAssistDisclosure);
    }
}
