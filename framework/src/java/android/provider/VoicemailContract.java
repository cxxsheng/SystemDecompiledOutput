package android.provider;

/* loaded from: classes3.dex */
public class VoicemailContract {
    public static final java.lang.String ACTION_FETCH_VOICEMAIL = "android.intent.action.FETCH_VOICEMAIL";
    public static final java.lang.String ACTION_NEW_VOICEMAIL = "android.intent.action.NEW_VOICEMAIL";
    public static final java.lang.String ACTION_SYNC_VOICEMAIL = "android.provider.action.SYNC_VOICEMAIL";
    public static final java.lang.String ACTION_VOICEMAIL_SMS_RECEIVED = "com.android.internal.provider.action.VOICEMAIL_SMS_RECEIVED";
    public static final java.lang.String AUTHORITY = "com.android.voicemail";
    public static final java.lang.String EXTRA_PHONE_ACCOUNT_HANDLE = "android.provider.extra.PHONE_ACCOUNT_HANDLE";
    public static final java.lang.String EXTRA_SELF_CHANGE = "com.android.voicemail.extra.SELF_CHANGE";
    public static final java.lang.String EXTRA_TARGET_PACKAGE = "android.provider.extra.TARGET_PACAKGE";
    public static final java.lang.String EXTRA_VOICEMAIL_SMS = "android.provider.extra.VOICEMAIL_SMS";
    public static final java.lang.String PARAM_KEY_SOURCE_PACKAGE = "source_package";
    public static final java.lang.String SOURCE_PACKAGE_FIELD = "source_package";

    private VoicemailContract() {
    }

    public static final class Voicemails implements android.provider.BaseColumns, android.provider.OpenableColumns {
        public static final java.lang.String ARCHIVED = "archived";
        public static final java.lang.String BACKED_UP = "backed_up";
        public static final java.lang.String DATE = "date";
        public static final java.lang.String DELETED = "deleted";
        public static final java.lang.String DIRTY = "dirty";
        public static final int DIRTY_RETAIN = -1;
        public static final java.lang.String DIR_TYPE = "vnd.android.cursor.dir/voicemails";
        public static final java.lang.String DURATION = "duration";
        public static final java.lang.String HAS_CONTENT = "has_content";
        public static final java.lang.String IS_OMTP_VOICEMAIL = "is_omtp_voicemail";
        public static final java.lang.String IS_READ = "is_read";
        public static final java.lang.String ITEM_TYPE = "vnd.android.cursor.item/voicemail";
        public static final java.lang.String LAST_MODIFIED = "last_modified";
        public static final java.lang.String MIME_TYPE = "mime_type";
        public static final java.lang.String NEW = "new";
        public static final java.lang.String NUMBER = "number";
        public static final java.lang.String PHONE_ACCOUNT_COMPONENT_NAME = "subscription_component_name";
        public static final java.lang.String PHONE_ACCOUNT_ID = "subscription_id";
        public static final java.lang.String RESTORED = "restored";
        public static final java.lang.String SOURCE_DATA = "source_data";
        public static final java.lang.String SOURCE_PACKAGE = "source_package";
        public static final java.lang.String STATE = "state";
        public static final java.lang.String TRANSCRIPTION = "transcription";
        public static final int TRANSCRIPTION_AVAILABLE = 3;
        public static final int TRANSCRIPTION_FAILED = 2;
        public static final int TRANSCRIPTION_IN_PROGRESS = 1;
        public static final int TRANSCRIPTION_NOT_STARTED = 0;
        public static final java.lang.String TRANSCRIPTION_STATE = "transcription_state";
        public static final java.lang.String _DATA = "_data";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.voicemail/voicemail");
        public static int STATE_INBOX = 0;
        public static int STATE_DELETED = 1;
        public static int STATE_UNDELETED = 2;

        private Voicemails() {
        }

        public static android.net.Uri buildSourceUri(java.lang.String str) {
            return CONTENT_URI.buildUpon().appendQueryParameter("source_package", str).build();
        }

        public static android.net.Uri insert(android.content.Context context, android.telecom.Voicemail voicemail) {
            return context.getContentResolver().insert(buildSourceUri(context.getPackageName()), getContentValues(voicemail));
        }

        public static int insert(android.content.Context context, java.util.List<android.telecom.Voicemail> list) {
            android.content.ContentResolver contentResolver = context.getContentResolver();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                contentResolver.insert(buildSourceUri(context.getPackageName()), getContentValues(list.get(i)));
            }
            return size;
        }

        public static int deleteAll(android.content.Context context) {
            return context.getContentResolver().delete(buildSourceUri(context.getPackageName()), "", new java.lang.String[0]);
        }

        private static android.content.ContentValues getContentValues(android.telecom.Voicemail voicemail) {
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put("date", java.lang.String.valueOf(voicemail.getTimestampMillis()));
            contentValues.put("number", voicemail.getNumber());
            contentValues.put("duration", java.lang.String.valueOf(voicemail.getDuration()));
            contentValues.put("source_package", voicemail.getSourcePackage());
            contentValues.put(SOURCE_DATA, voicemail.getSourceData());
            contentValues.put("is_read", java.lang.Integer.valueOf(voicemail.isRead() ? 1 : 0));
            android.telecom.PhoneAccountHandle phoneAccount = voicemail.getPhoneAccount();
            if (phoneAccount != null) {
                contentValues.put("subscription_component_name", phoneAccount.getComponentName().flattenToString());
                contentValues.put("subscription_id", phoneAccount.getId());
            }
            if (voicemail.getTranscription() != null) {
                contentValues.put("transcription", voicemail.getTranscription());
            }
            return contentValues;
        }
    }

    public static final class Status implements android.provider.BaseColumns {
        public static final java.lang.String CONFIGURATION_STATE = "configuration_state";
        public static final int CONFIGURATION_STATE_CAN_BE_CONFIGURED = 2;
        public static final int CONFIGURATION_STATE_CONFIGURING = 3;
        public static final int CONFIGURATION_STATE_DISABLED = 5;
        public static final int CONFIGURATION_STATE_FAILED = 4;
        public static final int CONFIGURATION_STATE_NOT_CONFIGURED = 1;
        public static final int CONFIGURATION_STATE_OK = 0;
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://com.android.voicemail/status");
        public static final java.lang.String DATA_CHANNEL_STATE = "data_channel_state";
        public static final int DATA_CHANNEL_STATE_BAD_CONFIGURATION = 3;
        public static final int DATA_CHANNEL_STATE_COMMUNICATION_ERROR = 4;
        public static final int DATA_CHANNEL_STATE_NO_CONNECTION = 1;
        public static final int DATA_CHANNEL_STATE_NO_CONNECTION_CELLULAR_REQUIRED = 2;
        public static final int DATA_CHANNEL_STATE_OK = 0;
        public static final int DATA_CHANNEL_STATE_SERVER_CONNECTION_ERROR = 6;
        public static final int DATA_CHANNEL_STATE_SERVER_ERROR = 5;
        public static final java.lang.String DIR_TYPE = "vnd.android.cursor.dir/voicemail.source.status";
        public static final java.lang.String ITEM_TYPE = "vnd.android.cursor.item/voicemail.source.status";
        public static final java.lang.String NOTIFICATION_CHANNEL_STATE = "notification_channel_state";
        public static final int NOTIFICATION_CHANNEL_STATE_MESSAGE_WAITING = 2;
        public static final int NOTIFICATION_CHANNEL_STATE_NO_CONNECTION = 1;
        public static final int NOTIFICATION_CHANNEL_STATE_OK = 0;
        public static final java.lang.String PHONE_ACCOUNT_COMPONENT_NAME = "phone_account_component_name";
        public static final java.lang.String PHONE_ACCOUNT_ID = "phone_account_id";
        public static final java.lang.String QUOTA_OCCUPIED = "quota_occupied";
        public static final java.lang.String QUOTA_TOTAL = "quota_total";
        public static final int QUOTA_UNAVAILABLE = -1;
        public static final java.lang.String SETTINGS_URI = "settings_uri";
        public static final java.lang.String SOURCE_PACKAGE = "source_package";
        public static final java.lang.String SOURCE_TYPE = "source_type";
        public static final java.lang.String VOICEMAIL_ACCESS_URI = "voicemail_access_uri";

        private Status() {
        }

        public static android.net.Uri buildSourceUri(java.lang.String str) {
            return CONTENT_URI.buildUpon().appendQueryParameter("source_package", str).build();
        }
    }
}
