package android.telecom;

/* loaded from: classes3.dex */
public class TelecomManager {
    public static final java.lang.String ACTION_CHANGE_DEFAULT_DIALER = "android.telecom.action.CHANGE_DEFAULT_DIALER";
    public static final java.lang.String ACTION_CHANGE_PHONE_ACCOUNTS = "android.telecom.action.CHANGE_PHONE_ACCOUNTS";
    public static final java.lang.String ACTION_CONFIGURE_PHONE_ACCOUNT = "android.telecom.action.CONFIGURE_PHONE_ACCOUNT";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CURRENT_TTY_MODE_CHANGED = "android.telecom.action.CURRENT_TTY_MODE_CHANGED";
    public static final java.lang.String ACTION_DEFAULT_CALL_SCREENING_APP_CHANGED = "android.telecom.action.DEFAULT_CALL_SCREENING_APP_CHANGED";
    public static final java.lang.String ACTION_DEFAULT_DIALER_CHANGED = "android.telecom.action.DEFAULT_DIALER_CHANGED";
    public static final java.lang.String ACTION_INCOMING_CALL = "android.telecom.action.INCOMING_CALL";
    public static final java.lang.String ACTION_NEW_UNKNOWN_CALL = "android.telecom.action.NEW_UNKNOWN_CALL";
    public static final java.lang.String ACTION_PHONE_ACCOUNT_REGISTERED = "android.telecom.action.PHONE_ACCOUNT_REGISTERED";
    public static final java.lang.String ACTION_PHONE_ACCOUNT_UNREGISTERED = "android.telecom.action.PHONE_ACCOUNT_UNREGISTERED";
    public static final java.lang.String ACTION_POST_CALL = "android.telecom.action.POST_CALL";
    public static final java.lang.String ACTION_SHOW_CALL_ACCESSIBILITY_SETTINGS = "android.telecom.action.SHOW_CALL_ACCESSIBILITY_SETTINGS";
    public static final java.lang.String ACTION_SHOW_CALL_SETTINGS = "android.telecom.action.SHOW_CALL_SETTINGS";
    public static final java.lang.String ACTION_SHOW_MISSED_CALLS_NOTIFICATION = "android.telecom.action.SHOW_MISSED_CALLS_NOTIFICATION";
    public static final java.lang.String ACTION_SHOW_RESPOND_VIA_SMS_SETTINGS = "android.telecom.action.SHOW_RESPOND_VIA_SMS_SETTINGS";
    public static final java.lang.String ACTION_SHOW_SWITCH_TO_WORK_PROFILE_FOR_CALL_DIALOG = "android.telecom.action.SHOW_SWITCH_TO_WORK_PROFILE_FOR_CALL_DIALOG";

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_TTY_PREFERRED_MODE_CHANGED = "android.telecom.action.TTY_PREFERRED_MODE_CHANGED";
    public static final int AUDIO_OUTPUT_DEFAULT = 0;
    public static final int AUDIO_OUTPUT_DISABLE_SPEAKER = 1;
    public static final int AUDIO_OUTPUT_ENABLE_SPEAKER = 0;
    public static final java.lang.String CALL_AUTO_DISCONNECT_MESSAGE_STRING = "Call dropped by lower layers";

    @android.annotation.SystemApi
    public static final int CALL_SOURCE_EMERGENCY_DIALPAD = 1;

    @android.annotation.SystemApi
    public static final int CALL_SOURCE_EMERGENCY_SHORTCUT = 2;

    @android.annotation.SystemApi
    public static final int CALL_SOURCE_UNSPECIFIED = 0;
    public static final char DTMF_CHARACTER_PAUSE = ',';
    public static final char DTMF_CHARACTER_WAIT = ';';
    public static final int DURATION_LONG = 3;
    public static final int DURATION_MEDIUM = 2;
    public static final int DURATION_SHORT = 1;
    public static final int DURATION_VERY_SHORT = 0;
    public static final long ENABLE_GET_CALL_STATE_PERMISSION_PROTECTION = 157233955;
    public static final long ENABLE_GET_PHONE_ACCOUNT_PERMISSION_PROTECTION = 183407956;
    public static final java.lang.String EXTRA_CALL_ANSWERED_TIME_MILLIS = "android.telecom.extra.CALL_ANSWERED_TIME_MILLIS";
    public static final java.lang.String EXTRA_CALL_AUDIO_STATE = "android.telecom.extra.CALL_AUDIO_STATE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALL_BACK_INTENT = "android.telecom.extra.CALL_BACK_INTENT";
    public static final java.lang.String EXTRA_CALL_BACK_NUMBER = "android.telecom.extra.CALL_BACK_NUMBER";
    public static final java.lang.String EXTRA_CALL_CREATED_EPOCH_TIME_MILLIS = "android.telecom.extra.CALL_CREATED_EPOCH_TIME_MILLIS";
    public static final java.lang.String EXTRA_CALL_CREATED_TIME_MILLIS = "android.telecom.extra.CALL_CREATED_TIME_MILLIS";
    public static final java.lang.String EXTRA_CALL_DISCONNECT_CAUSE = "android.telecom.extra.CALL_DISCONNECT_CAUSE";
    public static final java.lang.String EXTRA_CALL_DISCONNECT_MESSAGE = "android.telecom.extra.CALL_DISCONNECT_MESSAGE";
    public static final java.lang.String EXTRA_CALL_DURATION = "android.telecom.extra.CALL_DURATION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALL_HAS_IN_BAND_RINGTONE = "android.telecom.extra.CALL_HAS_IN_BAND_RINGTONE";
    public static final java.lang.String EXTRA_CALL_LOG_URI = "android.telecom.extra.CALL_LOG_URI";
    public static final java.lang.String EXTRA_CALL_NETWORK_TYPE = "android.telecom.extra.CALL_NETWORK_TYPE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALL_SOURCE = "android.telecom.extra.CALL_SOURCE";
    public static final java.lang.String EXTRA_CALL_SUBJECT = "android.telecom.extra.CALL_SUBJECT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CALL_TECHNOLOGY_TYPE = "android.telecom.extra.CALL_TECHNOLOGY_TYPE";
    public static final java.lang.String EXTRA_CALL_TELECOM_ROUTING_END_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_END_TIME_MILLIS";
    public static final java.lang.String EXTRA_CALL_TELECOM_ROUTING_START_TIME_MILLIS = "android.telecom.extra.CALL_TELECOM_ROUTING_START_TIME_MILLIS";
    public static final java.lang.String EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME = "android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME";

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final java.lang.String EXTRA_CLEAR_MISSED_CALLS_INTENT = "android.telecom.extra.CLEAR_MISSED_CALLS_INTENT";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CONNECTION_SERVICE = "android.telecom.extra.CONNECTION_SERVICE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_CURRENT_TTY_MODE = "android.telecom.extra.CURRENT_TTY_MODE";
    public static final java.lang.String EXTRA_DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME = "android.telecom.extra.DEFAULT_CALL_SCREENING_APP_COMPONENT_NAME";
    public static final java.lang.String EXTRA_DISCONNECT_CAUSE = "android.telecom.extra.DISCONNECT_CAUSE";
    public static final java.lang.String EXTRA_DO_NOT_LOG_CALL = "android.telecom.extra.DO_NOT_LOG_CALL";
    public static final java.lang.String EXTRA_HANDLE = "android.telecom.extra.HANDLE";
    public static final java.lang.String EXTRA_HANDOVER_FROM_PHONE_ACCOUNT = "android.telecom.extra.HANDOVER_FROM_PHONE_ACCOUNT";
    public static final java.lang.String EXTRA_HAS_PICTURE = "android.telecom.extra.HAS_PICTURE";
    public static final java.lang.String EXTRA_INCOMING_CALL_ADDRESS = "android.telecom.extra.INCOMING_CALL_ADDRESS";
    public static final java.lang.String EXTRA_INCOMING_CALL_EXTRAS = "android.telecom.extra.INCOMING_CALL_EXTRAS";
    public static final java.lang.String EXTRA_INCOMING_VIDEO_STATE = "android.telecom.extra.INCOMING_VIDEO_STATE";
    public static final java.lang.String EXTRA_IS_DEFAULT_CALL_SCREENING_APP = "android.telecom.extra.IS_DEFAULT_CALL_SCREENING_APP";
    public static final java.lang.String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    public static final java.lang.String EXTRA_IS_HANDOVER_CONNECTION = "android.telecom.extra.IS_HANDOVER_CONNECTION";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_IS_USER_INTENT_EMERGENCY_CALL = "android.telecom.extra.IS_USER_INTENT_EMERGENCY_CALL";
    public static final java.lang.String EXTRA_LOCATION = "android.telecom.extra.LOCATION";
    public static final java.lang.String EXTRA_MANAGED_PROFILE_USER_ID = "android.telecom.extra.MANAGED_PROFILE_USER_ID";
    public static final java.lang.String EXTRA_NEW_OUTGOING_CALL_CANCEL_TIMEOUT = "android.telecom.extra.NEW_OUTGOING_CALL_CANCEL_TIMEOUT";
    public static final java.lang.String EXTRA_NOTIFICATION_COUNT = "android.telecom.extra.NOTIFICATION_COUNT";
    public static final java.lang.String EXTRA_NOTIFICATION_PHONE_NUMBER = "android.telecom.extra.NOTIFICATION_PHONE_NUMBER";
    public static final java.lang.String EXTRA_OUTGOING_CALL_EXTRAS = "android.telecom.extra.OUTGOING_CALL_EXTRAS";
    public static final java.lang.String EXTRA_OUTGOING_PICTURE = "android.telecom.extra.OUTGOING_PICTURE";
    public static final java.lang.String EXTRA_PHONE_ACCOUNT_HANDLE = "android.telecom.extra.PHONE_ACCOUNT_HANDLE";
    public static final java.lang.String EXTRA_PICTURE_URI = "android.telecom.extra.PICTURE_URI";
    public static final java.lang.String EXTRA_PRIORITY = "android.telecom.extra.PRIORITY";
    public static final java.lang.String EXTRA_START_CALL_WITH_RTT = "android.telecom.extra.START_CALL_WITH_RTT";
    public static final java.lang.String EXTRA_START_CALL_WITH_SPEAKERPHONE = "android.telecom.extra.START_CALL_WITH_SPEAKERPHONE";
    public static final java.lang.String EXTRA_START_CALL_WITH_VIDEO_STATE = "android.telecom.extra.START_CALL_WITH_VIDEO_STATE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_TTY_PREFERRED_MODE = "android.telecom.extra.TTY_PREFERRED_MODE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_UNKNOWN_CALL_HANDLE = "android.telecom.extra.UNKNOWN_CALL_HANDLE";
    public static final java.lang.String EXTRA_USE_ASSISTED_DIALING = "android.telecom.extra.USE_ASSISTED_DIALING";
    public static final java.lang.String GATEWAY_ORIGINAL_ADDRESS = "android.telecom.extra.GATEWAY_ORIGINAL_ADDRESS";
    public static final java.lang.String GATEWAY_PROVIDER_PACKAGE = "android.telecom.extra.GATEWAY_PROVIDER_PACKAGE";
    public static final long MEDIUM_CALL_TIME_MS = 120000;
    public static final java.lang.String METADATA_INCLUDE_EXTERNAL_CALLS = "android.telecom.INCLUDE_EXTERNAL_CALLS";
    public static final java.lang.String METADATA_INCLUDE_SELF_MANAGED_CALLS = "android.telecom.INCLUDE_SELF_MANAGED_CALLS";
    public static final java.lang.String METADATA_IN_CALL_SERVICE_CAR_MODE_UI = "android.telecom.IN_CALL_SERVICE_CAR_MODE_UI";
    public static final java.lang.String METADATA_IN_CALL_SERVICE_RINGING = "android.telecom.IN_CALL_SERVICE_RINGING";
    public static final java.lang.String METADATA_IN_CALL_SERVICE_UI = "android.telecom.IN_CALL_SERVICE_UI";
    public static final int PRESENTATION_ALLOWED = 1;
    public static final int PRESENTATION_PAYPHONE = 4;
    public static final int PRESENTATION_RESTRICTED = 2;
    public static final int PRESENTATION_UNAVAILABLE = 5;
    public static final int PRESENTATION_UNKNOWN = 3;
    public static final int PRIORITY_NORMAL = 0;
    public static final int PRIORITY_URGENT = 1;
    public static final java.lang.String PROPERTY_VIDEOCALL_AUDIO_OUTPUT = "persist.radio.call.audio.output";
    public static final long SHORT_CALL_TIME_MS = 60000;
    private static final java.lang.String TAG = "TelecomManager";
    public static final int TELECOM_TRANSACTION_SUCCESS = 0;
    public static final java.lang.String TRANSACTION_CALL_ID_KEY = "TelecomCallId";

    @android.annotation.SystemApi
    public static final int TTY_MODE_FULL = 1;

    @android.annotation.SystemApi
    public static final int TTY_MODE_HCO = 2;

    @android.annotation.SystemApi
    public static final int TTY_MODE_OFF = 0;

    @android.annotation.SystemApi
    public static final int TTY_MODE_VCO = 3;
    public static final long VERY_SHORT_CALL_TIME_MS = 3000;
    private static com.android.internal.telecom.ITelecomService sTelecomService;
    private final android.content.Context mContext;
    private final com.android.internal.telecom.ITelecomService mTelecomServiceOverride;
    private final com.android.internal.telecom.ClientTransactionalServiceRepository mTransactionalServiceRepository;
    public static final android.content.ComponentName EMERGENCY_DIALER_COMPONENT = android.content.ComponentName.createRelative("com.android.phone", ".EmergencyDialer");
    private static final java.lang.Object CACHE_LOCK = new java.lang.Object();
    private static final android.telecom.TelecomManager.DeathRecipient SERVICE_DEATH = new android.telecom.TelecomManager.DeathRecipient();

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Presentation {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface TtyMode {
    }

    public static android.telecom.TelecomManager from(android.content.Context context) {
        return (android.telecom.TelecomManager) context.getSystemService(android.content.Context.TELECOM_SERVICE);
    }

    public TelecomManager(android.content.Context context) {
        this(context, null);
    }

    public TelecomManager(android.content.Context context, com.android.internal.telecom.ITelecomService iTelecomService) {
        this.mTransactionalServiceRepository = new com.android.internal.telecom.ClientTransactionalServiceRepository();
        android.content.Context applicationContext = context.getApplicationContext();
        if (applicationContext != null && java.util.Objects.equals(context.getAttributionTag(), applicationContext.getAttributionTag())) {
            this.mContext = applicationContext;
        } else {
            this.mContext = context;
        }
        this.mTelecomServiceOverride = iTelecomService;
    }

    public android.telecom.PhoneAccountHandle getDefaultOutgoingPhoneAccount(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getDefaultOutgoingPhoneAccount(str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getDefaultOutgoingPhoneAccount", e);
                return null;
            }
        }
        return null;
    }

    public android.telecom.PhoneAccountHandle getUserSelectedOutgoingPhoneAccount() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getUserSelectedOutgoingPhoneAccount(this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getUserSelectedOutgoingPhoneAccount", e);
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public void setUserSelectedOutgoingPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.setUserSelectedOutgoingPhoneAccount(phoneAccountHandle);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#setUserSelectedOutgoingPhoneAccount");
            }
        }
    }

    public android.telecom.PhoneAccountHandle getSimCallManager() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSimCallManager(android.telephony.SubscriptionManager.getDefaultSubscriptionId(), this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
                return null;
            }
        }
        return null;
    }

    public android.telecom.PhoneAccountHandle getSimCallManagerForSubscription(int i) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSimCallManager(i, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManager");
                return null;
            }
        }
        return null;
    }

    public android.telecom.PhoneAccountHandle getSimCallManager(int i) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSimCallManagerForUser(i, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getSimCallManagerForUser");
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public android.telecom.PhoneAccountHandle getConnectionManager() {
        return getSimCallManager();
    }

    @android.annotation.SystemApi
    public java.util.List<android.telecom.PhoneAccountHandle> getPhoneAccountsSupportingScheme(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getPhoneAccountsSupportingScheme(str, this.mContext.getOpPackageName()).getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsSupportingScheme", e);
            }
        }
        return new java.util.ArrayList();
    }

    public java.util.List<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts() {
        return getCallCapablePhoneAccounts(false);
    }

    public java.util.List<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccountsAcrossProfiles() {
        return getCallCapablePhoneAccountsAcrossProfiles(false);
    }

    public java.util.List<android.telecom.PhoneAccountHandle> getSelfManagedPhoneAccounts() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSelfManagedPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getSelfManagedPhoneAccounts()", e);
            }
        }
        return new java.util.ArrayList();
    }

    public java.util.List<android.telecom.PhoneAccountHandle> getOwnSelfManagedPhoneAccounts() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getOwnSelfManagedPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.IllegalStateException("Telecom is not available");
    }

    public java.util.List<android.telecom.PhoneAccount> getRegisteredPhoneAccounts() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getRegisteredPhoneAccounts(this.mContext.getOpPackageName(), this.mContext.getAttributionTag()).getList();
            } catch (android.os.RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        throw new java.lang.IllegalStateException("Telecom is not available");
    }

    @android.annotation.SystemApi
    public java.util.List<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccounts(boolean z) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getCallCapablePhoneAccounts(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), false).getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getCallCapablePhoneAccounts(" + z + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END, e);
            }
        }
        return new java.util.ArrayList();
    }

    @android.annotation.SystemApi
    public java.util.List<android.telecom.PhoneAccountHandle> getCallCapablePhoneAccountsAcrossProfiles(boolean z) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            throw new java.lang.IllegalStateException("telecom service is null.");
        }
        try {
            return telecomService.getCallCapablePhoneAccounts(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag(), true).getList();
        } catch (android.os.RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public java.util.List<android.telecom.PhoneAccountHandle> getPhoneAccountsForPackage() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getPhoneAccountsForPackage(this.mContext.getPackageName()).getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccountsForPackage", e);
                return null;
            }
        }
        return null;
    }

    public android.telecom.PhoneAccount getPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getPhoneAccount(phoneAccountHandle, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getPhoneAccount", e);
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public int getAllPhoneAccountsCount() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getAllPhoneAccountsCount();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountsCount", e);
                return 0;
            }
        }
        return 0;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telecom.PhoneAccount> getAllPhoneAccounts() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getAllPhoneAccounts().getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccounts", e);
            }
        }
        return java.util.Collections.EMPTY_LIST;
    }

    @android.annotation.SystemApi
    public java.util.List<android.telecom.PhoneAccountHandle> getAllPhoneAccountHandles() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getAllPhoneAccountHandles().getList();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAllPhoneAccountHandles", e);
            }
        }
        return java.util.Collections.EMPTY_LIST;
    }

    public void registerPhoneAccount(android.telecom.PhoneAccount phoneAccount) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.registerPhoneAccount(phoneAccount, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#registerPhoneAccount", e);
            }
        }
    }

    public void unregisterPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.unregisterPhoneAccount(phoneAccountHandle, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#unregisterPhoneAccount", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void clearPhoneAccounts() {
        clearAccounts();
    }

    @android.annotation.SystemApi
    public void clearAccounts() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.clearAccounts(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#clearAccounts", e);
            }
        }
    }

    public void clearAccountsForPackage(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                if (!android.text.TextUtils.isEmpty(str)) {
                    telecomService.clearAccounts(str);
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#clearAccountsForPackage", e);
            }
        }
    }

    @android.annotation.SystemApi
    public android.content.ComponentName getDefaultPhoneApp() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getDefaultPhoneApp();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get the default phone app.", e);
                return null;
            }
        }
        return null;
    }

    public java.lang.String getDefaultDialerPackage() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getDefaultDialerPackage(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get the default dialer package name.", e);
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    public java.lang.String getDefaultDialerPackage(android.os.UserHandle userHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getDefaultDialerPackageForUser(userHandle.getIdentifier());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get the default dialer package name.", e);
                return null;
            }
        }
        return null;
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public boolean setDefaultDialer(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.setDefaultDialer(str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to set the default dialer.", e);
                return false;
            }
        }
        return false;
    }

    public java.lang.String getSystemDialerPackage() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getSystemDialerPackage(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get the system dialer package name.", e);
                return null;
            }
        }
        return null;
    }

    public boolean isVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle, java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isVoiceMailNumber(phoneAccountHandle, str, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling ITelecomService#isVoiceMailNumber.", e);
                return false;
            }
        }
        return false;
    }

    public java.lang.String getVoiceMailNumber(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getVoiceMailNumber(phoneAccountHandle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling ITelecomService#hasVoiceMailNumber.", e);
                return null;
            }
        }
        return null;
    }

    @java.lang.Deprecated
    public java.lang.String getLine1Number(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getLine1Number(phoneAccountHandle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling ITelecomService#getLine1Number.", e);
                return null;
            }
        }
        return null;
    }

    public boolean isInCall() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInCall(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling isInCall().", e);
                return false;
            }
        }
        return false;
    }

    public boolean hasManageOngoingCallsPermission() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.hasManageOngoingCallsPermission(this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling hasManageOngoingCallsPermission().", e);
                if (!isSystemProcess()) {
                    e.rethrowAsRuntimeException();
                    return false;
                }
                return false;
            }
        }
        return false;
    }

    public boolean isInManagedCall() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInManagedCall(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException calling isInManagedCall().", e);
                return false;
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public int getCallState() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getCallStateUsingPackage(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.d(TAG, "RemoteException calling getCallState().", e);
                return 0;
            }
        }
        return 0;
    }

    @android.annotation.SystemApi
    public boolean isRinging() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isRinging(this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get ringing state of phone app.", e);
                return false;
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public boolean endCall() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.endCall(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#endCall", e);
                return false;
            }
        }
        return false;
    }

    @java.lang.Deprecated
    public void acceptRingingCall() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptRingingCall(this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#acceptRingingCall", e);
            }
        }
    }

    @java.lang.Deprecated
    public void acceptRingingCall(int i) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptRingingCallWithVideoState(this.mContext.getPackageName(), i);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#acceptRingingCallWithVideoState", e);
            }
        }
    }

    public void silenceRinger() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.silenceRinger(this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#silenceRinger", e);
            }
        }
    }

    public boolean isTtySupported() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isTtySupported(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get TTY supported state.", e);
                return false;
            }
        }
        return false;
    }

    @android.annotation.SystemApi
    public int getCurrentTtyMode() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.getCurrentTtyMode(this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException attempting to get the current TTY mode.", e);
                return 0;
            }
        }
        return 0;
    }

    public void addNewIncomingCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle != null) {
                try {
                    if (bundle.getBoolean("android.telecom.extra.IS_HANDOVER") && this.mContext.getApplicationContext().getApplicationInfo().targetSdkVersion > 27) {
                        android.util.Log.e("TAG", "addNewIncomingCall failed. Use public api acceptHandover for API > O-MR1");
                        return;
                    }
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new incoming call: " + phoneAccountHandle, e);
                    return;
                }
            }
            if (bundle == null) {
                bundle = new android.os.Bundle();
            }
            telecomService.addNewIncomingCall(phoneAccountHandle, bundle, this.mContext.getPackageName());
        }
    }

    public void addNewIncomingConference(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle == null) {
                try {
                    bundle = new android.os.Bundle();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new incoming conference: " + phoneAccountHandle, e);
                    return;
                }
            }
            telecomService.addNewIncomingConference(phoneAccountHandle, bundle, this.mContext.getPackageName());
        }
    }

    @android.annotation.SystemApi
    public void addNewUnknownCall(android.telecom.PhoneAccountHandle phoneAccountHandle, android.os.Bundle bundle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (bundle == null) {
                try {
                    bundle = new android.os.Bundle();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "RemoteException adding a new unknown call: " + phoneAccountHandle, e);
                    return;
                }
            }
            telecomService.addNewUnknownCall(phoneAccountHandle, bundle);
        }
    }

    public boolean handleMmi(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.handlePinMmi(str, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#handlePinMmi", e);
                return false;
            }
        }
        return false;
    }

    public boolean handleMmi(java.lang.String str, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.handlePinMmiForPhoneAccount(phoneAccountHandle, str, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#handlePinMmi", e);
                return false;
            }
        }
        return false;
    }

    public android.net.Uri getAdnUriForPhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null && phoneAccountHandle != null) {
            try {
                return telecomService.getAdnUriForPhoneAccount(phoneAccountHandle, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#getAdnUriForPhoneAccount", e);
            }
        }
        return android.net.Uri.parse("content://icc/adn");
    }

    public void cancelMissedCallsNotification() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.cancelMissedCallsNotification(this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#cancelMissedCallsNotification", e);
            }
        }
    }

    public void showInCallScreen(boolean z) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.showInCallScreen(z, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#showCallScreen", e);
            }
        }
    }

    public void placeCall(android.net.Uri uri, android.os.Bundle bundle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            if (uri == null) {
                android.util.Log.w(TAG, "Cannot place call to empty address.");
            }
            if (bundle == null) {
                try {
                    bundle = new android.os.Bundle();
                } catch (android.os.RemoteException e) {
                    android.util.Log.e(TAG, "Error calling ITelecomService#placeCall", e);
                    return;
                }
            }
            telecomService.placeCall(uri, bundle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
        }
    }

    public void startConference(java.util.List<android.net.Uri> list, android.os.Bundle bundle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.startConference(list, bundle, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#placeCall", e);
            }
        }
    }

    @android.annotation.SystemApi
    public void enablePhoneAccount(android.telecom.PhoneAccountHandle phoneAccountHandle, boolean z) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.enablePhoneAccount(phoneAccountHandle, z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error enablePhoneAbbount", e);
            }
        }
    }

    @android.annotation.SystemApi
    public android.telecom.TelecomAnalytics dumpAnalytics() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.dumpCallAnalytics();
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error dumping call analytics", e);
            }
        }
        return null;
    }

    public android.content.Intent createManageBlockedNumbersIntent() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        android.content.Intent intent = null;
        if (telecomService != null) {
            try {
                intent = telecomService.createManageBlockedNumbersIntent(this.mContext.getPackageName());
                if (intent != null) {
                    intent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                }
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error calling ITelecomService#createManageBlockedNumbersIntent", e);
            }
        }
        return intent;
    }

    @android.annotation.SystemApi
    public android.content.Intent createLaunchEmergencyDialerIntent(java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            android.util.Log.w(TAG, "createLaunchEmergencyDialerIntent - Telecom service not available.");
        } else {
            try {
                android.content.Intent createLaunchEmergencyDialerIntent = telecomService.createLaunchEmergencyDialerIntent(str);
                if (createLaunchEmergencyDialerIntent != null) {
                    createLaunchEmergencyDialerIntent.prepareToEnterProcess(32, this.mContext.getAttributionSource());
                }
                return createLaunchEmergencyDialerIntent;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error createLaunchEmergencyDialerIntent", e);
            }
        }
        android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_DIAL_EMERGENCY);
        if (!android.text.TextUtils.isEmpty(str) && android.text.TextUtils.isDigitsOnly(str)) {
            intent.setData(android.net.Uri.fromParts(android.telecom.PhoneAccount.SCHEME_TEL, str, null));
        }
        return intent;
    }

    public boolean isIncomingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService;
        if (phoneAccountHandle != null && (telecomService = getTelecomService()) != null) {
            try {
                return telecomService.isIncomingCallPermitted(phoneAccountHandle, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error isIncomingCallPermitted", e);
            }
        }
        return false;
    }

    public boolean isOutgoingCallPermitted(android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isOutgoingCallPermitted(phoneAccountHandle, this.mContext.getOpPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "Error isOutgoingCallPermitted", e);
                return false;
            }
        }
        return false;
    }

    public void acceptHandover(android.net.Uri uri, int i, android.telecom.PhoneAccountHandle phoneAccountHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.acceptHandover(uri, i, phoneAccountHandle, this.mContext.getPackageName());
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException acceptHandover: " + e);
            }
        }
    }

    @android.annotation.SystemApi
    public boolean isInEmergencyCall() {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService == null) {
            return false;
        }
        try {
            return telecomService.isInEmergencyCall();
        } catch (android.os.RemoteException e) {
            android.util.Log.e(TAG, "RemoteException isInEmergencyCall: " + e);
            return false;
        }
    }

    @android.annotation.SystemApi
    public boolean isInSelfManagedCall(java.lang.String str, android.os.UserHandle userHandle) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInSelfManagedCall(str, userHandle, this.mContext.getOpPackageName(), false);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException isInSelfManagedCall: " + e);
                e.rethrowFromSystemServer();
                return false;
            }
        }
        throw new java.lang.IllegalStateException("Telecom service is not present");
    }

    @android.annotation.SystemApi
    public boolean isInSelfManagedCall(java.lang.String str, boolean z) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                return telecomService.isInSelfManagedCall(str, null, this.mContext.getOpPackageName(), z);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException isInSelfManagedCall: " + e);
                e.rethrowFromSystemServer();
                return false;
            }
        }
        throw new java.lang.IllegalStateException("Telecom service is not present");
    }

    public void addCall(android.telecom.CallAttributes callAttributes, java.util.concurrent.Executor executor, android.os.OutcomeReceiver<android.telecom.CallControl, android.telecom.CallException> outcomeReceiver, android.telecom.CallControlCallback callControlCallback, android.telecom.CallEventCallback callEventCallback) {
        java.util.Objects.requireNonNull(callAttributes);
        java.util.Objects.requireNonNull(executor);
        java.util.Objects.requireNonNull(outcomeReceiver);
        java.util.Objects.requireNonNull(callControlCallback);
        java.util.Objects.requireNonNull(callEventCallback);
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                com.android.internal.telecom.ClientTransactionalServiceWrapper addNewCallForTransactionalServiceWrapper = this.mTransactionalServiceRepository.addNewCallForTransactionalServiceWrapper(callAttributes.getPhoneAccountHandle());
                telecomService.addCall(callAttributes, addNewCallForTransactionalServiceWrapper.getCallEventCallback(), addNewCallForTransactionalServiceWrapper.trackCall(callAttributes, executor, outcomeReceiver, callControlCallback, callEventCallback), this.mContext.getOpPackageName());
                return;
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException addCall: " + e);
                e.rethrowFromSystemServer();
                return;
            }
        }
        throw new java.lang.IllegalStateException("Telecom service is not present");
    }

    public void handleCallIntent(android.content.Intent intent, java.lang.String str) {
        com.android.internal.telecom.ITelecomService telecomService = getTelecomService();
        if (telecomService != null) {
            try {
                telecomService.handleCallIntent(intent, str);
            } catch (android.os.RemoteException e) {
                android.util.Log.e(TAG, "RemoteException handleCallIntent: " + e);
            }
        }
    }

    private boolean isSystemProcess() {
        return android.os.Process.myUid() == 1000;
    }

    private com.android.internal.telecom.ITelecomService getTelecomService() {
        if (this.mTelecomServiceOverride != null) {
            return this.mTelecomServiceOverride;
        }
        if (sTelecomService == null) {
            com.android.internal.telecom.ITelecomService asInterface = com.android.internal.telecom.ITelecomService.Stub.asInterface(android.os.ServiceManager.getService(android.content.Context.TELECOM_SERVICE));
            synchronized (CACHE_LOCK) {
                if (sTelecomService == null && asInterface != null) {
                    try {
                        sTelecomService = asInterface;
                        sTelecomService.asBinder().linkToDeath(SERVICE_DEATH, 0);
                    } catch (java.lang.Exception e) {
                        sTelecomService = null;
                    }
                }
            }
        }
        return sTelecomService;
    }

    private static class DeathRecipient implements android.os.IBinder.DeathRecipient {
        private DeathRecipient() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            android.telecom.TelecomManager.resetServiceCache();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void resetServiceCache() {
        synchronized (CACHE_LOCK) {
            if (sTelecomService != null) {
                sTelecomService.asBinder().unlinkToDeath(SERVICE_DEATH, 0);
                sTelecomService = null;
            }
        }
    }
}
