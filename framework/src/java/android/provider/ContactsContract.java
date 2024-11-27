package android.provider;

/* loaded from: classes3.dex */
public final class ContactsContract {
    public static final java.lang.String AUTHORITY = "com.android.contacts";
    public static final android.net.Uri AUTHORITY_URI = android.net.Uri.parse("content://com.android.contacts");
    public static final java.lang.String CALLER_IS_SYNCADAPTER = "caller_is_syncadapter";
    public static final java.lang.String DEFERRED_SNIPPETING = "deferred_snippeting";
    public static final java.lang.String DEFERRED_SNIPPETING_QUERY = "deferred_snippeting_query";
    public static final java.lang.String DIRECTORY_PARAM_KEY = "directory";
    public static final java.lang.String HIDDEN_COLUMN_PREFIX = "x_";
    public static final java.lang.String LIMIT_PARAM_KEY = "limit";
    public static final java.lang.String PRIMARY_ACCOUNT_NAME = "name_for_primary_account";
    public static final java.lang.String PRIMARY_ACCOUNT_TYPE = "type_for_primary_account";
    public static final java.lang.String REMOVE_DUPLICATE_ENTRIES = "remove_duplicate_entries";
    public static final java.lang.String STREQUENT_PHONE_ONLY = "strequent_phone_only";

    public static final class Authorization {
        public static final java.lang.String AUTHORIZATION_METHOD = "authorize";
        public static final java.lang.String KEY_AUTHORIZED_URI = "authorized_uri";
        public static final java.lang.String KEY_URI_TO_AUTHORIZE = "uri_to_authorize";
    }

    protected interface BaseSyncColumns {
        public static final java.lang.String SYNC1 = "sync1";
        public static final java.lang.String SYNC2 = "sync2";
        public static final java.lang.String SYNC3 = "sync3";
        public static final java.lang.String SYNC4 = "sync4";
    }

    interface ContactCounts {
        public static final java.lang.String EXTRA_ADDRESS_BOOK_INDEX = "android.provider.extra.ADDRESS_BOOK_INDEX";
        public static final java.lang.String EXTRA_ADDRESS_BOOK_INDEX_COUNTS = "android.provider.extra.ADDRESS_BOOK_INDEX_COUNTS";
        public static final java.lang.String EXTRA_ADDRESS_BOOK_INDEX_TITLES = "android.provider.extra.ADDRESS_BOOK_INDEX_TITLES";
    }

    protected interface ContactNameColumns {
        public static final java.lang.String DISPLAY_NAME_ALTERNATIVE = "display_name_alt";
        public static final java.lang.String DISPLAY_NAME_PRIMARY = "display_name";
        public static final java.lang.String DISPLAY_NAME_SOURCE = "display_name_source";
        public static final java.lang.String PHONETIC_NAME = "phonetic_name";
        public static final java.lang.String PHONETIC_NAME_STYLE = "phonetic_name_style";
        public static final java.lang.String SORT_KEY_ALTERNATIVE = "sort_key_alt";
        public static final java.lang.String SORT_KEY_PRIMARY = "sort_key";
    }

    protected interface ContactOptionsColumns {
        public static final java.lang.String CUSTOM_RINGTONE = "custom_ringtone";

        @java.lang.Deprecated
        public static final java.lang.String LAST_TIME_CONTACTED = "last_time_contacted";
        public static final java.lang.String LR_LAST_TIME_CONTACTED = "last_time_contacted";
        public static final java.lang.String LR_TIMES_CONTACTED = "times_contacted";
        public static final java.lang.String PINNED = "pinned";
        public static final java.lang.String RAW_LAST_TIME_CONTACTED = "x_last_time_contacted";
        public static final java.lang.String RAW_TIMES_CONTACTED = "x_times_contacted";
        public static final java.lang.String SEND_TO_VOICEMAIL = "send_to_voicemail";
        public static final java.lang.String STARRED = "starred";

        @java.lang.Deprecated
        public static final java.lang.String TIMES_CONTACTED = "times_contacted";
    }

    protected interface ContactStatusColumns {
        public static final java.lang.String CONTACT_CHAT_CAPABILITY = "contact_chat_capability";
        public static final java.lang.String CONTACT_PRESENCE = "contact_presence";
        public static final java.lang.String CONTACT_STATUS = "contact_status";
        public static final java.lang.String CONTACT_STATUS_ICON = "contact_status_icon";
        public static final java.lang.String CONTACT_STATUS_LABEL = "contact_status_label";
        public static final java.lang.String CONTACT_STATUS_RES_PACKAGE = "contact_status_res_package";
        public static final java.lang.String CONTACT_STATUS_TIMESTAMP = "contact_status_ts";
    }

    protected interface ContactsColumns {
        public static final java.lang.String CONTACT_LAST_UPDATED_TIMESTAMP = "contact_last_updated_timestamp";
        public static final java.lang.String DISPLAY_NAME = "display_name";
        public static final java.lang.String HAS_PHONE_NUMBER = "has_phone_number";
        public static final java.lang.String IN_DEFAULT_DIRECTORY = "in_default_directory";
        public static final java.lang.String IN_VISIBLE_GROUP = "in_visible_group";
        public static final java.lang.String IS_USER_PROFILE = "is_user_profile";
        public static final java.lang.String LOOKUP_KEY = "lookup";
        public static final java.lang.String NAME_RAW_CONTACT_ID = "name_raw_contact_id";
        public static final java.lang.String PHOTO_FILE_ID = "photo_file_id";
        public static final java.lang.String PHOTO_ID = "photo_id";
        public static final java.lang.String PHOTO_THUMBNAIL_URI = "photo_thumb_uri";
        public static final java.lang.String PHOTO_URI = "photo_uri";
    }

    protected interface DataColumns {

        @java.lang.Deprecated
        public static final java.lang.String CARRIER_PRESENCE = "carrier_presence";

        @java.lang.Deprecated
        public static final int CARRIER_PRESENCE_VT_CAPABLE = 1;
        public static final java.lang.String DATA1 = "data1";
        public static final java.lang.String DATA10 = "data10";
        public static final java.lang.String DATA11 = "data11";
        public static final java.lang.String DATA12 = "data12";
        public static final java.lang.String DATA13 = "data13";
        public static final java.lang.String DATA14 = "data14";
        public static final java.lang.String DATA15 = "data15";
        public static final java.lang.String DATA2 = "data2";
        public static final java.lang.String DATA3 = "data3";
        public static final java.lang.String DATA4 = "data4";
        public static final java.lang.String DATA5 = "data5";
        public static final java.lang.String DATA6 = "data6";
        public static final java.lang.String DATA7 = "data7";
        public static final java.lang.String DATA8 = "data8";
        public static final java.lang.String DATA9 = "data9";
        public static final java.lang.String DATA_VERSION = "data_version";

        @java.lang.Deprecated
        public static final java.lang.String HASH_ID = "hash_id";
        public static final java.lang.String IS_PHONE_ACCOUNT_MIGRATION_PENDING = "is_preferred_phone_account_migration_pending";
        public static final java.lang.String IS_PRIMARY = "is_primary";
        public static final java.lang.String IS_READ_ONLY = "is_read_only";
        public static final java.lang.String IS_SUPER_PRIMARY = "is_super_primary";
        public static final java.lang.String MIMETYPE = "mimetype";
        public static final java.lang.String PREFERRED_PHONE_ACCOUNT_COMPONENT_NAME = "preferred_phone_account_component_name";
        public static final java.lang.String PREFERRED_PHONE_ACCOUNT_ID = "preferred_phone_account_id";
        public static final java.lang.String RAW_CONTACT_ID = "raw_contact_id";
        public static final java.lang.String RES_PACKAGE = "res_package";
        public static final java.lang.String SYNC1 = "data_sync1";
        public static final java.lang.String SYNC2 = "data_sync2";
        public static final java.lang.String SYNC3 = "data_sync3";
        public static final java.lang.String SYNC4 = "data_sync4";
    }

    protected interface DataColumnsWithJoins extends android.provider.BaseColumns, android.provider.ContactsContract.DataColumns, android.provider.ContactsContract.StatusColumns, android.provider.ContactsContract.RawContactsColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactNameColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactStatusColumns, android.provider.ContactsContract.DataUsageStatColumns {
    }

    @java.lang.Deprecated
    public static final class DataUsageFeedback {
        public static final java.lang.String USAGE_TYPE = "type";
        public static final java.lang.String USAGE_TYPE_CALL = "call";
        public static final java.lang.String USAGE_TYPE_LONG_TEXT = "long_text";
        public static final java.lang.String USAGE_TYPE_SHORT_TEXT = "short_text";
        public static final android.net.Uri FEEDBACK_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, "usagefeedback");
        public static final android.net.Uri DELETE_USAGE_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Contacts.CONTENT_URI, "delete_usage");
    }

    protected interface DataUsageStatColumns {

        @java.lang.Deprecated
        public static final java.lang.String LAST_TIME_USED = "last_time_used";
        public static final java.lang.String LR_LAST_TIME_USED = "last_time_used";
        public static final java.lang.String LR_TIMES_USED = "times_used";
        public static final java.lang.String RAW_LAST_TIME_USED = "x_last_time_used";
        public static final java.lang.String RAW_TIMES_USED = "x_times_used";

        @java.lang.Deprecated
        public static final java.lang.String TIMES_USED = "times_used";
    }

    protected interface DeletedContactsColumns {
        public static final java.lang.String CONTACT_DELETED_TIMESTAMP = "contact_deleted_timestamp";
        public static final java.lang.String CONTACT_ID = "contact_id";
    }

    public interface DisplayNameSources {
        public static final int EMAIL = 10;
        public static final int NICKNAME = 35;
        public static final int ORGANIZATION = 30;
        public static final int PHONE = 20;
        public static final int STRUCTURED_NAME = 40;
        public static final int STRUCTURED_PHONETIC_NAME = 37;
        public static final int UNDEFINED = 0;
    }

    public interface FullNameStyle {
        public static final int CHINESE = 3;
        public static final int CJK = 2;
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int UNDEFINED = 0;
        public static final int WESTERN = 1;
    }

    protected interface GroupsColumns {
        public static final java.lang.String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final java.lang.String AUTO_ADD = "auto_add";
        public static final java.lang.String DATA_SET = "data_set";
        public static final java.lang.String DELETED = "deleted";
        public static final java.lang.String FAVORITES = "favorites";
        public static final java.lang.String GROUP_IS_READ_ONLY = "group_is_read_only";
        public static final java.lang.String GROUP_VISIBLE = "group_visible";
        public static final java.lang.String NOTES = "notes";
        public static final java.lang.String PARAM_RETURN_GROUP_COUNT_PER_ACCOUNT = "return_group_count_per_account";
        public static final java.lang.String RES_PACKAGE = "res_package";
        public static final java.lang.String SHOULD_SYNC = "should_sync";
        public static final java.lang.String SUMMARY_COUNT = "summ_count";
        public static final java.lang.String SUMMARY_GROUP_COUNT_PER_ACCOUNT = "group_count_per_account";
        public static final java.lang.String SUMMARY_WITH_PHONES = "summ_phones";
        public static final java.lang.String SYSTEM_ID = "system_id";
        public static final java.lang.String TITLE = "title";
        public static final java.lang.String TITLE_RES = "title_res";
    }

    public static final class Intents {
        public static final java.lang.String ACTION_GET_MULTIPLE_PHONES = "com.android.contacts.action.GET_MULTIPLE_PHONES";
        public static final java.lang.String ACTION_PROFILE_CHANGED = "android.provider.Contacts.PROFILE_CHANGED";
        public static final java.lang.String ACTION_VOICE_SEND_MESSAGE_TO_CONTACTS = "android.provider.action.VOICE_SEND_MESSAGE_TO_CONTACTS";
        public static final java.lang.String ATTACH_IMAGE = "com.android.contacts.action.ATTACH_IMAGE";
        public static final java.lang.String CONTACTS_DATABASE_CREATED = "android.provider.Contacts.DATABASE_CREATED";
        public static final java.lang.String EXTRA_CREATE_DESCRIPTION = "com.android.contacts.action.CREATE_DESCRIPTION";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_EXCLUDE_MIMES = "exclude_mimes";
        public static final java.lang.String EXTRA_FORCE_CREATE = "com.android.contacts.action.FORCE_CREATE";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_MODE = "mode";
        public static final java.lang.String EXTRA_PHONE_URIS = "com.android.contacts.extra.PHONE_URIS";
        public static final java.lang.String EXTRA_RECIPIENT_CONTACT_CHAT_ID = "android.provider.extra.RECIPIENT_CONTACT_CHAT_ID";
        public static final java.lang.String EXTRA_RECIPIENT_CONTACT_NAME = "android.provider.extra.RECIPIENT_CONTACT_NAME";
        public static final java.lang.String EXTRA_RECIPIENT_CONTACT_URI = "android.provider.extra.RECIPIENT_CONTACT_URI";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_TARGET_RECT = "target_rect";
        public static final java.lang.String INVITE_CONTACT = "com.android.contacts.action.INVITE_CONTACT";
        public static final java.lang.String METADATA_ACCOUNT_TYPE = "android.provider.account_type";
        public static final java.lang.String METADATA_MIMETYPE = "android.provider.mimetype";

        @java.lang.Deprecated
        public static final int MODE_LARGE = 3;

        @java.lang.Deprecated
        public static final int MODE_MEDIUM = 2;

        @java.lang.Deprecated
        public static final int MODE_SMALL = 1;
        public static final java.lang.String SEARCH_SUGGESTION_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CLICKED";
        public static final java.lang.String SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_CREATE_CONTACT_CLICKED";
        public static final java.lang.String SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED = "android.provider.Contacts.SEARCH_SUGGESTION_DIAL_NUMBER_CLICKED";
        public static final java.lang.String SHOW_OR_CREATE_CONTACT = "com.android.contacts.action.SHOW_OR_CREATE_CONTACT";

        public static final class Insert {
            public static final java.lang.String ACTION = "android.intent.action.INSERT";
            public static final java.lang.String COMPANY = "company";
            public static final java.lang.String DATA = "data";
            public static final java.lang.String EMAIL = "email";
            public static final java.lang.String EMAIL_ISPRIMARY = "email_isprimary";
            public static final java.lang.String EMAIL_TYPE = "email_type";
            public static final java.lang.String EXTRA_ACCOUNT = "android.provider.extra.ACCOUNT";
            public static final java.lang.String EXTRA_DATA_SET = "android.provider.extra.DATA_SET";
            public static final java.lang.String FULL_MODE = "full_mode";
            public static final java.lang.String IM_HANDLE = "im_handle";
            public static final java.lang.String IM_ISPRIMARY = "im_isprimary";
            public static final java.lang.String IM_PROTOCOL = "im_protocol";
            public static final java.lang.String JOB_TITLE = "job_title";
            public static final java.lang.String NAME = "name";
            public static final java.lang.String NOTES = "notes";
            public static final java.lang.String PHONE = "phone";
            public static final java.lang.String PHONETIC_NAME = "phonetic_name";
            public static final java.lang.String PHONE_ISPRIMARY = "phone_isprimary";
            public static final java.lang.String PHONE_TYPE = "phone_type";
            public static final java.lang.String POSTAL = "postal";
            public static final java.lang.String POSTAL_ISPRIMARY = "postal_isprimary";
            public static final java.lang.String POSTAL_TYPE = "postal_type";
            public static final java.lang.String SECONDARY_EMAIL = "secondary_email";
            public static final java.lang.String SECONDARY_EMAIL_TYPE = "secondary_email_type";
            public static final java.lang.String SECONDARY_PHONE = "secondary_phone";
            public static final java.lang.String SECONDARY_PHONE_TYPE = "secondary_phone_type";
            public static final java.lang.String TERTIARY_EMAIL = "tertiary_email";
            public static final java.lang.String TERTIARY_EMAIL_TYPE = "tertiary_email_type";
            public static final java.lang.String TERTIARY_PHONE = "tertiary_phone";
            public static final java.lang.String TERTIARY_PHONE_TYPE = "tertiary_phone_type";
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    protected interface MetadataSyncColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String DATA = "data";
        public static final java.lang.String DATA_SET = "data_set";
        public static final java.lang.String DELETED = "deleted";
        public static final java.lang.String RAW_CONTACT_BACKUP_ID = "raw_contact_backup_id";
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    protected interface MetadataSyncStateColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String DATA_SET = "data_set";
        public static final java.lang.String STATE = "state";
    }

    protected interface PhoneLookupColumns {
        public static final java.lang.String CONTACT_ID = "contact_id";
        public static final java.lang.String DATA_ID = "data_id";
        public static final java.lang.String LABEL = "label";
        public static final java.lang.String NORMALIZED_NUMBER = "normalized_number";
        public static final java.lang.String NUMBER = "number";
        public static final java.lang.String TYPE = "type";
    }

    public interface PhoneticNameStyle {
        public static final int JAPANESE = 4;
        public static final int KOREAN = 5;
        public static final int PINYIN = 3;
        public static final int UNDEFINED = 0;
    }

    protected interface PhotoFilesColumns {
        public static final java.lang.String FILESIZE = "filesize";
        public static final java.lang.String HEIGHT = "height";
        public static final java.lang.String WIDTH = "width";
    }

    protected interface PresenceColumns {
        public static final java.lang.String CUSTOM_PROTOCOL = "custom_protocol";
        public static final java.lang.String DATA_ID = "presence_data_id";
        public static final java.lang.String IM_ACCOUNT = "im_account";
        public static final java.lang.String IM_HANDLE = "im_handle";
        public static final java.lang.String PROTOCOL = "protocol";
    }

    protected interface RawContactsColumns {
        public static final java.lang.String ACCOUNT_TYPE_AND_DATA_SET = "account_type_and_data_set";
        public static final java.lang.String AGGREGATION_MODE = "aggregation_mode";
        public static final java.lang.String BACKUP_ID = "backup_id";
        public static final java.lang.String CONTACT_ID = "contact_id";
        public static final java.lang.String DATA_SET = "data_set";
        public static final java.lang.String DELETED = "deleted";

        @java.lang.Deprecated
        public static final java.lang.String METADATA_DIRTY = "metadata_dirty";
        public static final java.lang.String RAW_CONTACT_IS_READ_ONLY = "raw_contact_is_read_only";
        public static final java.lang.String RAW_CONTACT_IS_USER_PROFILE = "raw_contact_is_user_profile";
    }

    public static class SearchSnippets {
        public static final java.lang.String DEFERRED_SNIPPETING_KEY = "deferred_snippeting";
        public static final java.lang.String SNIPPET = "snippet";
        public static final java.lang.String SNIPPET_ARGS_PARAM_KEY = "snippet_args";
    }

    protected interface SettingsColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String ANY_UNSYNCED = "any_unsynced";
        public static final java.lang.String DATA_SET = "data_set";
        public static final java.lang.String IS_DEFAULT = "x_is_default";
        public static final java.lang.String SHOULD_SYNC = "should_sync";
        public static final java.lang.String UNGROUPED_COUNT = "summ_count";
        public static final java.lang.String UNGROUPED_VISIBLE = "ungrouped_visible";
        public static final java.lang.String UNGROUPED_WITH_PHONES = "summ_phones";
    }

    protected interface StatusColumns {
        public static final int AVAILABLE = 5;
        public static final int AWAY = 2;
        public static final int CAPABILITY_HAS_CAMERA = 4;
        public static final int CAPABILITY_HAS_VIDEO = 2;
        public static final int CAPABILITY_HAS_VOICE = 1;
        public static final java.lang.String CHAT_CAPABILITY = "chat_capability";
        public static final int DO_NOT_DISTURB = 4;
        public static final int IDLE = 3;
        public static final int INVISIBLE = 1;
        public static final int OFFLINE = 0;
        public static final java.lang.String PRESENCE = "mode";

        @java.lang.Deprecated
        public static final java.lang.String PRESENCE_CUSTOM_STATUS = "status";

        @java.lang.Deprecated
        public static final java.lang.String PRESENCE_STATUS = "mode";
        public static final java.lang.String STATUS = "status";
        public static final java.lang.String STATUS_ICON = "status_icon";
        public static final java.lang.String STATUS_LABEL = "status_label";
        public static final java.lang.String STATUS_RES_PACKAGE = "status_res_package";
        public static final java.lang.String STATUS_TIMESTAMP = "status_ts";
    }

    @java.lang.Deprecated
    protected interface StreamItemPhotosColumns {

        @java.lang.Deprecated
        public static final java.lang.String PHOTO_FILE_ID = "photo_file_id";

        @java.lang.Deprecated
        public static final java.lang.String PHOTO_URI = "photo_uri";

        @java.lang.Deprecated
        public static final java.lang.String SORT_INDEX = "sort_index";

        @java.lang.Deprecated
        public static final java.lang.String STREAM_ITEM_ID = "stream_item_id";

        @java.lang.Deprecated
        public static final java.lang.String SYNC1 = "stream_item_photo_sync1";

        @java.lang.Deprecated
        public static final java.lang.String SYNC2 = "stream_item_photo_sync2";

        @java.lang.Deprecated
        public static final java.lang.String SYNC3 = "stream_item_photo_sync3";

        @java.lang.Deprecated
        public static final java.lang.String SYNC4 = "stream_item_photo_sync4";
    }

    @java.lang.Deprecated
    protected interface StreamItemsColumns {

        @java.lang.Deprecated
        public static final java.lang.String ACCOUNT_NAME = "account_name";

        @java.lang.Deprecated
        public static final java.lang.String ACCOUNT_TYPE = "account_type";

        @java.lang.Deprecated
        public static final java.lang.String COMMENTS = "comments";

        @java.lang.Deprecated
        public static final java.lang.String CONTACT_ID = "contact_id";

        @java.lang.Deprecated
        public static final java.lang.String CONTACT_LOOKUP_KEY = "contact_lookup";

        @java.lang.Deprecated
        public static final java.lang.String DATA_SET = "data_set";

        @java.lang.Deprecated
        public static final java.lang.String RAW_CONTACT_ID = "raw_contact_id";

        @java.lang.Deprecated
        public static final java.lang.String RAW_CONTACT_SOURCE_ID = "raw_contact_source_id";

        @java.lang.Deprecated
        public static final java.lang.String RES_ICON = "icon";

        @java.lang.Deprecated
        public static final java.lang.String RES_LABEL = "label";

        @java.lang.Deprecated
        public static final java.lang.String RES_PACKAGE = "res_package";

        @java.lang.Deprecated
        public static final java.lang.String SYNC1 = "stream_item_sync1";

        @java.lang.Deprecated
        public static final java.lang.String SYNC2 = "stream_item_sync2";

        @java.lang.Deprecated
        public static final java.lang.String SYNC3 = "stream_item_sync3";

        @java.lang.Deprecated
        public static final java.lang.String SYNC4 = "stream_item_sync4";

        @java.lang.Deprecated
        public static final java.lang.String TEXT = "text";

        @java.lang.Deprecated
        public static final java.lang.String TIMESTAMP = "timestamp";
    }

    protected interface SyncColumns extends android.provider.ContactsContract.BaseSyncColumns {
        public static final java.lang.String ACCOUNT_NAME = "account_name";
        public static final java.lang.String ACCOUNT_TYPE = "account_type";
        public static final java.lang.String DIRTY = "dirty";
        public static final java.lang.String SOURCE_ID = "sourceid";
        public static final java.lang.String VERSION = "version";
    }

    @java.lang.Deprecated
    public interface SyncStateColumns extends android.provider.SyncStateContract.Columns {
    }

    public static final class Directory implements android.provider.BaseColumns {
        public static final java.lang.String ACCOUNT_NAME = "accountName";
        public static final java.lang.String ACCOUNT_TYPE = "accountType";
        public static final java.lang.String CALLER_PACKAGE_PARAM_KEY = "callerPackage";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_directory";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact_directories";
        public static final long DEFAULT = 0;
        public static final java.lang.String DIRECTORY_AUTHORITY = "authority";
        public static final java.lang.String DISPLAY_NAME = "displayName";
        public static final long ENTERPRISE_DEFAULT = 1000000000;
        public static final long ENTERPRISE_DIRECTORY_ID_BASE = 1000000000;
        public static final long ENTERPRISE_LOCAL_INVISIBLE = 1000000001;
        public static final java.lang.String EXPORT_SUPPORT = "exportSupport";
        public static final int EXPORT_SUPPORT_ANY_ACCOUNT = 2;
        public static final int EXPORT_SUPPORT_NONE = 0;
        public static final int EXPORT_SUPPORT_SAME_ACCOUNT_ONLY = 1;
        public static final long LOCAL_INVISIBLE = 1;
        public static final java.lang.String PACKAGE_NAME = "packageName";
        public static final java.lang.String PHOTO_SUPPORT = "photoSupport";
        public static final int PHOTO_SUPPORT_FULL = 3;
        public static final int PHOTO_SUPPORT_FULL_SIZE_ONLY = 2;
        public static final int PHOTO_SUPPORT_NONE = 0;
        public static final int PHOTO_SUPPORT_THUMBNAIL_ONLY = 1;
        public static final java.lang.String SHORTCUT_SUPPORT = "shortcutSupport";
        public static final int SHORTCUT_SUPPORT_DATA_ITEMS_ONLY = 1;
        public static final int SHORTCUT_SUPPORT_FULL = 2;
        public static final int SHORTCUT_SUPPORT_NONE = 0;
        public static final java.lang.String TYPE_RESOURCE_ID = "typeResourceId";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "directories");
        public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "directories_enterprise");
        public static final android.net.Uri ENTERPRISE_FILE_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "directory_file_enterprise");

        private Directory() {
        }

        public static boolean isRemoteDirectoryId(long j) {
            return (j == 0 || j == 1 || j == 1000000000 || j == ENTERPRISE_LOCAL_INVISIBLE) ? false : true;
        }

        public static boolean isRemoteDirectory(long j) {
            return isRemoteDirectoryId(j);
        }

        public static boolean isEnterpriseDirectoryId(long j) {
            return j >= 1000000000;
        }

        public static void notifyDirectoryChange(android.content.ContentResolver contentResolver) {
            contentResolver.update(CONTENT_URI, new android.content.ContentValues(), null, null);
        }
    }

    public static final class SyncState implements android.provider.SyncStateContract.Columns {
        public static final java.lang.String CONTENT_DIRECTORY = "syncstate";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "syncstate");

        private SyncState() {
        }

        public static byte[] get(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static android.util.Pair<android.net.Uri, byte[]> getWithUri(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static void set(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account, byte[] bArr) throws android.os.RemoteException {
            android.provider.SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, bArr);
        }

        public static android.content.ContentProviderOperation newSetOperation(android.accounts.Account account, byte[] bArr) {
            return android.provider.SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, bArr);
        }
    }

    public static final class ProfileSyncState implements android.provider.SyncStateContract.Columns {
        public static final java.lang.String CONTENT_DIRECTORY = "syncstate";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Profile.CONTENT_URI, "syncstate");

        private ProfileSyncState() {
        }

        public static byte[] get(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.get(contentProviderClient, CONTENT_URI, account);
        }

        public static android.util.Pair<android.net.Uri, byte[]> getWithUri(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account) throws android.os.RemoteException {
            return android.provider.SyncStateContract.Helpers.getWithUri(contentProviderClient, CONTENT_URI, account);
        }

        public static void set(android.content.ContentProviderClient contentProviderClient, android.accounts.Account account, byte[] bArr) throws android.os.RemoteException {
            android.provider.SyncStateContract.Helpers.set(contentProviderClient, CONTENT_URI, account, bArr);
        }

        public static android.content.ContentProviderOperation newSetOperation(android.accounts.Account account, byte[] bArr) {
            return android.provider.SyncStateContract.Helpers.newSetOperation(CONTENT_URI, account, bArr);
        }
    }

    public static class Contacts implements android.provider.BaseColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactNameColumns, android.provider.ContactsContract.ContactStatusColumns, android.provider.ContactsContract.ContactCounts {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact";
        public static final java.lang.String CONTENT_VCARD_TYPE = "text/x-vcard";
        public static final java.lang.String QUERY_PARAMETER_VCARD_NO_PHOTO = "no_photo";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, android.provider.Contacts.AUTHORITY);
        public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, android.telephony.data.ApnSetting.TYPE_ENTERPRISE_STRING);
        public static final android.net.Uri CORP_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "contacts_corp");
        public static final android.net.Uri CONTENT_LOOKUP_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "lookup");
        public static final android.net.Uri CONTENT_VCARD_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "as_vcard");
        public static final android.net.Uri CONTENT_MULTI_VCARD_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "as_multi_vcard");
        public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter");
        public static final android.net.Uri ENTERPRISE_CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
        public static final android.net.Uri CONTENT_STREQUENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "strequent");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_FREQUENT_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "frequent");
        public static final android.net.Uri CONTENT_STREQUENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_STREQUENT_URI, "filter");
        public static final android.net.Uri CONTENT_GROUP_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "group");
        public static long ENTERPRISE_CONTACT_ID_BASE = 1000000000;
        public static java.lang.String ENTERPRISE_CONTACT_LOOKUP_PREFIX = "c-";

        private Contacts() {
        }

        public static android.net.Uri getLookupUri(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            android.database.Cursor query = contentResolver.query(uri, new java.lang.String[]{"lookup", "_id"}, null, null, null);
            if (query == null) {
                return null;
            }
            try {
                if (!query.moveToFirst()) {
                    return null;
                }
                return getLookupUri(query.getLong(1), query.getString(0));
            } finally {
                query.close();
            }
        }

        public static android.net.Uri getLookupUri(long j, java.lang.String str) {
            if (android.text.TextUtils.isEmpty(str)) {
                return null;
            }
            return android.content.ContentUris.withAppendedId(android.net.Uri.withAppendedPath(CONTENT_LOOKUP_URI, str), j);
        }

        public static android.net.Uri lookupContact(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            android.database.Cursor query;
            if (uri == null || (query = contentResolver.query(uri, new java.lang.String[]{"_id"}, null, null, null)) == null) {
                return null;
            }
            try {
                if (!query.moveToFirst()) {
                    return null;
                }
                return android.content.ContentUris.withAppendedId(CONTENT_URI, query.getLong(0));
            } finally {
                query.close();
            }
        }

        @java.lang.Deprecated
        public static void markAsContacted(android.content.ContentResolver contentResolver, long j) {
        }

        public static boolean isEnterpriseContactId(long j) {
            return j >= ENTERPRISE_CONTACT_ID_BASE && j < android.provider.ContactsContract.Profile.MIN_ID;
        }

        public static final class Data implements android.provider.BaseColumns, android.provider.ContactsContract.DataColumns {
            public static final java.lang.String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class Entity implements android.provider.BaseColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactNameColumns, android.provider.ContactsContract.RawContactsColumns, android.provider.ContactsContract.BaseSyncColumns, android.provider.ContactsContract.SyncColumns, android.provider.ContactsContract.DataColumns, android.provider.ContactsContract.StatusColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactStatusColumns, android.provider.ContactsContract.DataUsageStatColumns {
            public static final java.lang.String CONTENT_DIRECTORY = "entities";
            public static final java.lang.String DATA_ID = "data_id";
            public static final java.lang.String RAW_CONTACT_ID = "raw_contact_id";

            private Entity() {
            }
        }

        @java.lang.Deprecated
        public static final class StreamItems implements android.provider.ContactsContract.StreamItemsColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "stream_items";

            @java.lang.Deprecated
            private StreamItems() {
            }
        }

        public static final class AggregationSuggestions implements android.provider.BaseColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactStatusColumns {
            public static final java.lang.String CONTENT_DIRECTORY = "suggestions";
            public static final java.lang.String PARAMETER_MATCH_NAME = "name";

            private AggregationSuggestions() {
            }

            public static final class Builder {
                private long mContactId;
                private int mLimit;
                private final java.util.ArrayList<java.lang.String> mValues = new java.util.ArrayList<>();

                public android.provider.ContactsContract.Contacts.AggregationSuggestions.Builder setContactId(long j) {
                    this.mContactId = j;
                    return this;
                }

                public android.provider.ContactsContract.Contacts.AggregationSuggestions.Builder addNameParameter(java.lang.String str) {
                    this.mValues.add(str);
                    return this;
                }

                public android.provider.ContactsContract.Contacts.AggregationSuggestions.Builder setLimit(int i) {
                    this.mLimit = i;
                    return this;
                }

                public android.net.Uri build() {
                    android.net.Uri.Builder buildUpon = android.provider.ContactsContract.Contacts.CONTENT_URI.buildUpon();
                    buildUpon.appendEncodedPath(java.lang.String.valueOf(this.mContactId));
                    buildUpon.appendPath(android.provider.ContactsContract.Contacts.AggregationSuggestions.CONTENT_DIRECTORY);
                    if (this.mLimit != 0) {
                        buildUpon.appendQueryParameter("limit", java.lang.String.valueOf(this.mLimit));
                    }
                    int size = this.mValues.size();
                    for (int i = 0; i < size; i++) {
                        buildUpon.appendQueryParameter("query", "name:" + this.mValues.get(i));
                    }
                    return buildUpon.build();
                }
            }

            public static final android.provider.ContactsContract.Contacts.AggregationSuggestions.Builder builder() {
                return new android.provider.ContactsContract.Contacts.AggregationSuggestions.Builder();
            }
        }

        public static final class Photo implements android.provider.BaseColumns, android.provider.ContactsContract.DataColumnsWithJoins {
            public static final java.lang.String CONTENT_DIRECTORY = "photo";
            public static final java.lang.String DISPLAY_PHOTO = "display_photo";
            public static final java.lang.String PHOTO = "data15";
            public static final java.lang.String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        public static java.io.InputStream openContactPhotoInputStream(android.content.ContentResolver contentResolver, android.net.Uri uri, boolean z) {
            if (z) {
                try {
                    android.content.res.AssetFileDescriptor openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(android.net.Uri.withAppendedPath(uri, "display_photo"), "r");
                    if (openAssetFileDescriptor != null) {
                        return openAssetFileDescriptor.createInputStream();
                    }
                } catch (java.io.IOException e) {
                }
            }
            android.net.Uri withAppendedPath = android.net.Uri.withAppendedPath(uri, "photo");
            if (withAppendedPath == null) {
                return null;
            }
            android.database.Cursor query = contentResolver.query(withAppendedPath, new java.lang.String[]{"data15"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToNext()) {
                        byte[] blob = query.getBlob(0);
                        if (blob == null) {
                            return null;
                        }
                        java.io.ByteArrayInputStream byteArrayInputStream = new java.io.ByteArrayInputStream(blob);
                        if (query != null) {
                            query.close();
                        }
                        return byteArrayInputStream;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
            if (query != null) {
                query.close();
            }
            return null;
        }

        public static java.io.InputStream openContactPhotoInputStream(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            return openContactPhotoInputStream(contentResolver, uri, false);
        }

        public static android.net.Uri createCorpLookupUriFromEnterpriseLookupUri(android.net.Uri uri) {
            java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
            if (pathSegments == null || pathSegments.size() <= 2) {
                return null;
            }
            java.lang.String str = pathSegments.get(2);
            if (android.text.TextUtils.isEmpty(str) || !str.startsWith(ENTERPRISE_CONTACT_LOOKUP_PREFIX)) {
                return null;
            }
            return android.net.Uri.withAppendedPath(CONTENT_LOOKUP_URI, str.substring(ENTERPRISE_CONTACT_LOOKUP_PREFIX.length()));
        }
    }

    public static final class Profile implements android.provider.BaseColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactNameColumns, android.provider.ContactsContract.ContactStatusColumns {
        public static final long MIN_ID = 9223372034707292160L;
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, android.media.MediaFormat.KEY_PROFILE);
        public static final android.net.Uri CONTENT_VCARD_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "as_vcard");
        public static final android.net.Uri CONTENT_RAW_CONTACTS_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "raw_contacts");

        private Profile() {
        }
    }

    public static boolean isProfileId(long j) {
        return j >= android.provider.ContactsContract.Profile.MIN_ID;
    }

    public static final class DeletedContacts implements android.provider.ContactsContract.DeletedContactsColumns {
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "deleted_contacts");
        private static final int DAYS_KEPT = 30;
        public static final long DAYS_KEPT_MILLISECONDS = 2592000000L;

        private DeletedContacts() {
        }
    }

    public static final class RawContacts implements android.provider.BaseColumns, android.provider.ContactsContract.RawContactsColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactNameColumns, android.provider.ContactsContract.SyncColumns {
        public static final int AGGREGATION_MODE_DEFAULT = 0;
        public static final int AGGREGATION_MODE_DISABLED = 3;

        @java.lang.Deprecated
        public static final int AGGREGATION_MODE_IMMEDIATE = 1;
        public static final int AGGREGATION_MODE_SUSPENDED = 2;
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/raw_contact";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "raw_contacts");

        private RawContacts() {
        }

        public static android.net.Uri getContactLookupUri(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            android.database.Cursor query = contentResolver.query(android.net.Uri.withAppendedPath(uri, "data"), new java.lang.String[]{"contact_id", "lookup"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        android.net.Uri lookupUri = android.provider.ContactsContract.Contacts.getLookupUri(query.getLong(0), query.getString(1));
                        if (query != null) {
                            query.close();
                        }
                        return lookupUri;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
        }

        public static java.lang.String getLocalAccountName(android.content.Context context) {
            return android.text.TextUtils.nullIfEmpty(context.getString(com.android.internal.R.string.config_rawContactsLocalAccountName));
        }

        public static java.lang.String getLocalAccountType(android.content.Context context) {
            return android.text.TextUtils.nullIfEmpty(context.getString(com.android.internal.R.string.config_rawContactsLocalAccountType));
        }

        public static final class Data implements android.provider.BaseColumns, android.provider.ContactsContract.DataColumns {
            public static final java.lang.String CONTENT_DIRECTORY = "data";

            private Data() {
            }
        }

        public static final class Entity implements android.provider.BaseColumns, android.provider.ContactsContract.DataColumns {
            public static final java.lang.String CONTENT_DIRECTORY = "entity";
            public static final java.lang.String DATA_ID = "data_id";

            private Entity() {
            }
        }

        @java.lang.Deprecated
        public static final class StreamItems implements android.provider.BaseColumns, android.provider.ContactsContract.StreamItemsColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "stream_items";

            @java.lang.Deprecated
            private StreamItems() {
            }
        }

        public static final class DisplayPhoto {
            public static final java.lang.String CONTENT_DIRECTORY = "display_photo";

            private DisplayPhoto() {
            }
        }

        public static android.content.EntityIterator newEntityIterator(android.database.Cursor cursor) {
            return new android.provider.ContactsContract.RawContacts.EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends android.content.CursorEntityIterator {
            private static final java.lang.String[] DATA_KEYS = {"data1", "data2", "data3", "data4", "data5", "data6", "data7", "data8", "data9", "data10", "data11", android.provider.ContactsContract.DataColumns.DATA12, android.provider.ContactsContract.DataColumns.DATA13, "data14", "data15", android.provider.ContactsContract.DataColumns.SYNC1, android.provider.ContactsContract.DataColumns.SYNC2, android.provider.ContactsContract.DataColumns.SYNC3, android.provider.ContactsContract.DataColumns.SYNC4};

            public EntityIteratorImpl(android.database.Cursor cursor) {
                super(cursor);
            }

            @Override // android.content.CursorEntityIterator
            public android.content.Entity getEntityAndIncrementCursor(android.database.Cursor cursor) throws android.os.RemoteException {
                int columnIndexOrThrow = cursor.getColumnIndexOrThrow("_id");
                long j = cursor.getLong(columnIndexOrThrow);
                android.content.ContentValues contentValues = new android.content.ContentValues();
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "data_set");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "_id");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "version");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sourceid");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync1");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync2");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync3");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync4");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "deleted");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "contact_id");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "starred");
                android.content.Entity entity = new android.content.Entity(contentValues);
                while (j == cursor.getLong(columnIndexOrThrow)) {
                    android.content.ContentValues contentValues2 = new android.content.ContentValues();
                    contentValues2.put("_id", java.lang.Long.valueOf(cursor.getLong(cursor.getColumnIndexOrThrow("data_id"))));
                    android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, "res_package");
                    android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, "mimetype");
                    android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, android.provider.ContactsContract.DataColumns.IS_PRIMARY);
                    android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, android.provider.ContactsContract.DataColumns.IS_SUPER_PRIMARY);
                    android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues2, android.provider.ContactsContract.DataColumns.DATA_VERSION);
                    android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, android.provider.ContactsContract.CommonDataKinds.GroupMembership.GROUP_SOURCE_ID);
                    android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues2, android.provider.ContactsContract.DataColumns.DATA_VERSION);
                    for (java.lang.String str : DATA_KEYS) {
                        int columnIndexOrThrow2 = cursor.getColumnIndexOrThrow(str);
                        switch (cursor.getType(columnIndexOrThrow2)) {
                            case 0:
                                break;
                            case 1:
                            case 2:
                            case 3:
                                contentValues2.put(str, cursor.getString(columnIndexOrThrow2));
                                break;
                            case 4:
                                contentValues2.put(str, cursor.getBlob(columnIndexOrThrow2));
                                break;
                            default:
                                throw new java.lang.IllegalStateException("Invalid or unhandled data type");
                        }
                    }
                    entity.addSubValue(android.provider.ContactsContract.Data.CONTENT_URI, contentValues2);
                    if (!cursor.moveToNext()) {
                        return entity;
                    }
                }
                return entity;
            }
        }
    }

    @java.lang.Deprecated
    public static final class StreamItems implements android.provider.BaseColumns, android.provider.ContactsContract.StreamItemsColumns {

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item";

        @java.lang.Deprecated
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item";

        @java.lang.Deprecated
        public static final java.lang.String MAX_ITEMS = "max_items";

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "stream_items");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_PHOTO_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "photo");

        @java.lang.Deprecated
        public static final android.net.Uri CONTENT_LIMIT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "stream_items_limit");

        @java.lang.Deprecated
        private StreamItems() {
        }

        @java.lang.Deprecated
        public static final class StreamItemPhotos implements android.provider.BaseColumns, android.provider.ContactsContract.StreamItemPhotosColumns {

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_DIRECTORY = "photo";

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/stream_item_photo";

            @java.lang.Deprecated
            public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/stream_item_photo";

            @java.lang.Deprecated
            private StreamItemPhotos() {
            }
        }
    }

    @java.lang.Deprecated
    public static final class StreamItemPhotos implements android.provider.BaseColumns, android.provider.ContactsContract.StreamItemPhotosColumns {

        @java.lang.Deprecated
        public static final java.lang.String PHOTO = "photo";

        @java.lang.Deprecated
        private StreamItemPhotos() {
        }
    }

    public static final class PhotoFiles implements android.provider.BaseColumns, android.provider.ContactsContract.PhotoFilesColumns {
        private PhotoFiles() {
        }
    }

    public static final class Data implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/data";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "data");
        static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "data_enterprise");
        public static final java.lang.String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";

        private Data() {
        }

        public static android.net.Uri getContactLookupUri(android.content.ContentResolver contentResolver, android.net.Uri uri) {
            android.database.Cursor query = contentResolver.query(uri, new java.lang.String[]{"contact_id", "lookup"}, null, null, null);
            if (query != null) {
                try {
                    if (query.moveToFirst()) {
                        android.net.Uri lookupUri = android.provider.ContactsContract.Contacts.getLookupUri(query.getLong(0), query.getString(1));
                        if (query != null) {
                            query.close();
                        }
                        return lookupUri;
                    }
                } finally {
                    if (query != null) {
                        query.close();
                    }
                }
            }
        }
    }

    public static final class RawContactsEntity implements android.provider.BaseColumns, android.provider.ContactsContract.DataColumns, android.provider.ContactsContract.RawContactsColumns {
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/raw_contact_entity";
        public static final java.lang.String DATA_ID = "data_id";
        public static final java.lang.String FOR_EXPORT_ONLY = "for_export_only";
        private static final java.lang.String TAG = "ContactsContract.RawContactsEntity";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "raw_contact_entities");
        public static final android.net.Uri CORP_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "raw_contact_entities_corp");
        public static final android.net.Uri PROFILE_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Profile.CONTENT_URI, "raw_contact_entities");

        private RawContactsEntity() {
        }

        @android.annotation.SystemApi(client = android.annotation.SystemApi.Client.MODULE_LIBRARIES)
        public static java.util.Map<java.lang.String, java.util.List<android.content.ContentValues>> queryRawContactEntity(android.content.ContentResolver contentResolver, long j) {
            android.net.Uri uri;
            android.net.Uri uri2 = CONTENT_URI;
            if (android.provider.ContactsContract.Contacts.isEnterpriseContactId(j)) {
                android.net.Uri uri3 = CORP_CONTENT_URI;
                j -= android.provider.ContactsContract.Contacts.ENTERPRISE_CONTACT_ID_BASE;
                uri = uri3;
            } else {
                uri = uri2;
            }
            java.util.HashMap hashMap = new java.util.HashMap();
            android.content.EntityIterator entityIterator = null;
            try {
                android.content.EntityIterator newEntityIterator = android.provider.ContactsContract.RawContacts.newEntityIterator(contentResolver.query(uri, null, "contact_id=?", new java.lang.String[]{java.lang.String.valueOf(j)}, null));
                if (newEntityIterator == null) {
                    android.util.Log.e(TAG, "EntityIterator is null");
                    if (newEntityIterator != null) {
                        newEntityIterator.close();
                    }
                    return hashMap;
                }
                if (!newEntityIterator.hasNext()) {
                    android.util.Log.w(TAG, "Data does not exist. contactId: " + j);
                    if (newEntityIterator != null) {
                        newEntityIterator.close();
                    }
                    return hashMap;
                }
                while (newEntityIterator.hasNext()) {
                    java.util.Iterator<android.content.Entity.NamedContentValues> it = newEntityIterator.next().getSubValues().iterator();
                    while (it.hasNext()) {
                        android.content.ContentValues contentValues = it.next().values;
                        java.lang.String asString = contentValues.getAsString("mimetype");
                        if (asString != null) {
                            java.util.List list = (java.util.List) hashMap.get(asString);
                            if (list == null) {
                                list = new java.util.ArrayList();
                                hashMap.put(asString, list);
                            }
                            list.add(contentValues);
                        }
                    }
                }
                if (newEntityIterator != null) {
                    newEntityIterator.close();
                }
                return hashMap;
            } catch (java.lang.Throwable th) {
                if (0 != 0) {
                    entityIterator.close();
                }
                throw th;
            }
        }
    }

    public static final class PhoneLookup implements android.provider.BaseColumns, android.provider.ContactsContract.PhoneLookupColumns, android.provider.ContactsContract.ContactsColumns, android.provider.ContactsContract.ContactOptionsColumns, android.provider.ContactsContract.ContactNameColumns {
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/phone_lookup";
        public static final java.lang.String QUERY_PARAMETER_SIP_ADDRESS = "sip";
        public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "phone_lookup");
        public static final android.net.Uri ENTERPRISE_CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "phone_lookup_enterprise");

        private PhoneLookup() {
        }
    }

    public static class StatusUpdates implements android.provider.ContactsContract.StatusColumns, android.provider.ContactsContract.PresenceColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/status-update";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/status-update";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "status_updates");
        public static final android.net.Uri PROFILE_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Profile.CONTENT_URI, "status_updates");

        private StatusUpdates() {
        }

        public static final int getPresenceIconResourceId(int i) {
            switch (i) {
                case 1:
                    return 17301609;
                case 2:
                case 3:
                    return 17301607;
                case 4:
                    return 17301608;
                case 5:
                    return 17301611;
                default:
                    return 17301610;
            }
        }

        public static final int getPresencePrecedence(int i) {
            return i;
        }
    }

    @java.lang.Deprecated
    public static final class Presence extends android.provider.ContactsContract.StatusUpdates {
        public Presence() {
            super();
        }
    }

    public static final class CommonDataKinds {
        public static final java.lang.String PACKAGE_COMMON = "common";

        public interface BaseTypes {
            public static final int TYPE_CUSTOM = 0;
        }

        public static final class Callable implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, "callables");
            public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter");
            public static final android.net.Uri ENTERPRISE_CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");
        }

        protected interface CommonColumns extends android.provider.ContactsContract.CommonDataKinds.BaseTypes {
            public static final java.lang.String DATA = "data1";
            public static final java.lang.String LABEL = "data3";
            public static final java.lang.String TYPE = "data2";
        }

        public static final class Contactables implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String VISIBLE_CONTACTS_ONLY = "visible_contacts_only";
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, "contactables");
            public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter");
        }

        private CommonDataKinds() {
        }

        public static final class StructuredName implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/name";
            public static final java.lang.String DISPLAY_NAME = "data1";
            public static final java.lang.String FAMILY_NAME = "data3";
            public static final java.lang.String FULL_NAME_STYLE = "data10";
            public static final java.lang.String GIVEN_NAME = "data2";
            public static final java.lang.String MIDDLE_NAME = "data5";
            public static final java.lang.String PHONETIC_FAMILY_NAME = "data9";
            public static final java.lang.String PHONETIC_GIVEN_NAME = "data7";
            public static final java.lang.String PHONETIC_MIDDLE_NAME = "data8";
            public static final java.lang.String PHONETIC_NAME_STYLE = "data11";
            public static final java.lang.String PREFIX = "data4";
            public static final java.lang.String SUFFIX = "data6";

            private StructuredName() {
            }
        }

        public static final class Nickname implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/nickname";
            public static final java.lang.String NAME = "data1";
            public static final int TYPE_DEFAULT = 1;
            public static final int TYPE_INITIALS = 5;
            public static final int TYPE_MAIDEN_NAME = 3;

            @java.lang.Deprecated
            public static final int TYPE_MAINDEN_NAME = 3;
            public static final int TYPE_OTHER_NAME = 2;
            public static final int TYPE_SHORT_NAME = 4;

            private Nickname() {
            }
        }

        public static final class Phone implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/phone_v2";
            public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/phone_v2";
            public static final java.lang.String NORMALIZED_NUMBER = "data4";
            public static final java.lang.String NUMBER = "data1";
            public static final java.lang.String SEARCH_DISPLAY_NAME_KEY = "search_display_name";
            public static final java.lang.String SEARCH_PHONE_NUMBER_KEY = "search_phone_number";
            public static final int TYPE_ASSISTANT = 19;
            public static final int TYPE_CALLBACK = 8;
            public static final int TYPE_CAR = 9;
            public static final int TYPE_COMPANY_MAIN = 10;
            public static final int TYPE_FAX_HOME = 5;
            public static final int TYPE_FAX_WORK = 4;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_ISDN = 11;
            public static final int TYPE_MAIN = 12;
            public static final int TYPE_MMS = 20;
            public static final int TYPE_MOBILE = 2;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_OTHER_FAX = 13;
            public static final int TYPE_PAGER = 6;
            public static final int TYPE_RADIO = 14;
            public static final int TYPE_TELEX = 15;
            public static final int TYPE_TTY_TDD = 16;
            public static final int TYPE_WORK = 3;
            public static final int TYPE_WORK_MOBILE = 17;
            public static final int TYPE_WORK_PAGER = 18;
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, android.provider.Contacts.People.Phones.CONTENT_DIRECTORY);
            public static final android.net.Uri ENTERPRISE_CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.ENTERPRISE_CONTENT_URI, android.provider.Contacts.People.Phones.CONTENT_DIRECTORY);
            public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter");
            public static final android.net.Uri ENTERPRISE_CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");

            private Phone() {
            }

            @java.lang.Deprecated
            public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, java.lang.CharSequence charSequence, java.lang.CharSequence[] charSequenceArr) {
                return getTypeLabel(context.getResources(), i, charSequence);
            }

            @java.lang.Deprecated
            public static final java.lang.CharSequence getDisplayLabel(android.content.Context context, int i, java.lang.CharSequence charSequence) {
                return getTypeLabel(context.getResources(), i, charSequence);
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.phoneTypeHome;
                    case 2:
                        return com.android.internal.R.string.phoneTypeMobile;
                    case 3:
                        return com.android.internal.R.string.phoneTypeWork;
                    case 4:
                        return com.android.internal.R.string.phoneTypeFaxWork;
                    case 5:
                        return com.android.internal.R.string.phoneTypeFaxHome;
                    case 6:
                        return com.android.internal.R.string.phoneTypePager;
                    case 7:
                        return com.android.internal.R.string.phoneTypeOther;
                    case 8:
                        return com.android.internal.R.string.phoneTypeCallback;
                    case 9:
                        return com.android.internal.R.string.phoneTypeCar;
                    case 10:
                        return com.android.internal.R.string.phoneTypeCompanyMain;
                    case 11:
                        return com.android.internal.R.string.phoneTypeIsdn;
                    case 12:
                        return com.android.internal.R.string.phoneTypeMain;
                    case 13:
                        return com.android.internal.R.string.phoneTypeOtherFax;
                    case 14:
                        return com.android.internal.R.string.phoneTypeRadio;
                    case 15:
                        return com.android.internal.R.string.phoneTypeTelex;
                    case 16:
                        return com.android.internal.R.string.phoneTypeTtyTdd;
                    case 17:
                        return com.android.internal.R.string.phoneTypeWorkMobile;
                    case 18:
                        return com.android.internal.R.string.phoneTypeWorkPager;
                    case 19:
                        return com.android.internal.R.string.phoneTypeAssistant;
                    case 20:
                        return com.android.internal.R.string.phoneTypeMms;
                    default:
                        return com.android.internal.R.string.phoneTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if ((i == 0 || i == 19) && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class Email implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String ADDRESS = "data1";
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/email_v2";
            public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/email_v2";
            public static final java.lang.String DISPLAY_NAME = "data4";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_MOBILE = 4;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, "emails");
            public static final android.net.Uri CONTENT_LOOKUP_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "lookup");
            public static final android.net.Uri ENTERPRISE_CONTENT_LOOKUP_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "lookup_enterprise");
            public static final android.net.Uri CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter");
            public static final android.net.Uri ENTERPRISE_CONTENT_FILTER_URI = android.net.Uri.withAppendedPath(CONTENT_URI, "filter_enterprise");

            private Email() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.emailTypeHome;
                    case 2:
                        return com.android.internal.R.string.emailTypeWork;
                    case 3:
                        return com.android.internal.R.string.emailTypeOther;
                    case 4:
                        return com.android.internal.R.string.emailTypeMobile;
                    default:
                        return com.android.internal.R.string.emailTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class StructuredPostal implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CITY = "data7";
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/postal-address_v2";
            public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/postal-address_v2";
            public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Data.CONTENT_URI, "postals");
            public static final java.lang.String COUNTRY = "data10";
            public static final java.lang.String FORMATTED_ADDRESS = "data1";
            public static final java.lang.String NEIGHBORHOOD = "data6";
            public static final java.lang.String POBOX = "data5";
            public static final java.lang.String POSTCODE = "data9";
            public static final java.lang.String REGION = "data8";
            public static final java.lang.String STREET = "data4";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private StructuredPostal() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.postalTypeHome;
                    case 2:
                        return com.android.internal.R.string.postalTypeWork;
                    case 3:
                        return com.android.internal.R.string.postalTypeOther;
                    default:
                        return com.android.internal.R.string.postalTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        @java.lang.Deprecated
        public static final class Im implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/im";
            public static final java.lang.String CUSTOM_PROTOCOL = "data6";
            public static final java.lang.String PROTOCOL = "data5";

            @java.lang.Deprecated
            public static final int PROTOCOL_AIM = 0;
            public static final int PROTOCOL_CUSTOM = -1;

            @java.lang.Deprecated
            public static final int PROTOCOL_GOOGLE_TALK = 5;

            @java.lang.Deprecated
            public static final int PROTOCOL_ICQ = 6;

            @java.lang.Deprecated
            public static final int PROTOCOL_JABBER = 7;

            @java.lang.Deprecated
            public static final int PROTOCOL_MSN = 1;

            @java.lang.Deprecated
            public static final int PROTOCOL_NETMEETING = 8;

            @java.lang.Deprecated
            public static final int PROTOCOL_QQ = 4;

            @java.lang.Deprecated
            public static final int PROTOCOL_SKYPE = 3;

            @java.lang.Deprecated
            public static final int PROTOCOL_YAHOO = 2;
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private Im() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.imTypeHome;
                    case 2:
                        return com.android.internal.R.string.imTypeWork;
                    case 3:
                        return com.android.internal.R.string.imTypeOther;
                    default:
                        return com.android.internal.R.string.imTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }

            public static final int getProtocolLabelResource(int i) {
                switch (i) {
                    case 0:
                        return com.android.internal.R.string.imProtocolAim;
                    case 1:
                        return com.android.internal.R.string.imProtocolMsn;
                    case 2:
                        return com.android.internal.R.string.imProtocolYahoo;
                    case 3:
                        return com.android.internal.R.string.imProtocolSkype;
                    case 4:
                        return com.android.internal.R.string.imProtocolQq;
                    case 5:
                        return com.android.internal.R.string.imProtocolGoogleTalk;
                    case 6:
                        return com.android.internal.R.string.imProtocolIcq;
                    case 7:
                        return com.android.internal.R.string.imProtocolJabber;
                    case 8:
                        return com.android.internal.R.string.imProtocolNetMeeting;
                    default:
                        return com.android.internal.R.string.imProtocolCustom;
                }
            }

            public static final java.lang.CharSequence getProtocolLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == -1 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getProtocolLabelResource(i));
            }
        }

        public static final class Organization implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String COMPANY = "data1";
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/organization";
            public static final java.lang.String DEPARTMENT = "data5";
            public static final java.lang.String JOB_DESCRIPTION = "data6";
            public static final java.lang.String OFFICE_LOCATION = "data9";
            public static final java.lang.String PHONETIC_NAME = "data8";
            public static final java.lang.String PHONETIC_NAME_STYLE = "data10";
            public static final java.lang.String SYMBOL = "data7";
            public static final java.lang.String TITLE = "data4";
            public static final int TYPE_OTHER = 2;
            public static final int TYPE_WORK = 1;

            private Organization() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.orgTypeWork;
                    case 2:
                        return com.android.internal.R.string.orgTypeOther;
                    default:
                        return com.android.internal.R.string.orgTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class Relation implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/relation";
            public static final java.lang.String NAME = "data1";
            public static final int TYPE_ASSISTANT = 1;
            public static final int TYPE_BROTHER = 2;
            public static final int TYPE_CHILD = 3;
            public static final int TYPE_DOMESTIC_PARTNER = 4;
            public static final int TYPE_FATHER = 5;
            public static final int TYPE_FRIEND = 6;
            public static final int TYPE_MANAGER = 7;
            public static final int TYPE_MOTHER = 8;
            public static final int TYPE_PARENT = 9;
            public static final int TYPE_PARTNER = 10;
            public static final int TYPE_REFERRED_BY = 11;
            public static final int TYPE_RELATIVE = 12;
            public static final int TYPE_SISTER = 13;
            public static final int TYPE_SPOUSE = 14;

            private Relation() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.relationTypeAssistant;
                    case 2:
                        return com.android.internal.R.string.relationTypeBrother;
                    case 3:
                        return com.android.internal.R.string.relationTypeChild;
                    case 4:
                        return com.android.internal.R.string.relationTypeDomesticPartner;
                    case 5:
                        return com.android.internal.R.string.relationTypeFather;
                    case 6:
                        return com.android.internal.R.string.relationTypeFriend;
                    case 7:
                        return com.android.internal.R.string.relationTypeManager;
                    case 8:
                        return com.android.internal.R.string.relationTypeMother;
                    case 9:
                        return com.android.internal.R.string.relationTypeParent;
                    case 10:
                        return com.android.internal.R.string.relationTypePartner;
                    case 11:
                        return com.android.internal.R.string.relationTypeReferredBy;
                    case 12:
                        return com.android.internal.R.string.relationTypeRelative;
                    case 13:
                        return com.android.internal.R.string.relationTypeSister;
                    case 14:
                        return com.android.internal.R.string.relationTypeSpouse;
                    default:
                        return com.android.internal.R.string.orgTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class Event implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_event";
            public static final java.lang.String START_DATE = "data1";
            public static final int TYPE_ANNIVERSARY = 1;
            public static final int TYPE_BIRTHDAY = 3;
            public static final int TYPE_OTHER = 2;

            private Event() {
            }

            public static int getTypeResource(java.lang.Integer num) {
                if (num == null) {
                    return com.android.internal.R.string.eventTypeOther;
                }
                switch (num.intValue()) {
                }
                return com.android.internal.R.string.eventTypeOther;
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeResource(java.lang.Integer.valueOf(i)));
            }
        }

        public static final class Photo implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/photo";
            public static final java.lang.String PHOTO = "data15";
            public static final java.lang.String PHOTO_FILE_ID = "data14";

            private Photo() {
            }
        }

        public static final class Note implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/note";
            public static final java.lang.String NOTE = "data1";

            private Note() {
            }
        }

        public static final class GroupMembership implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group_membership";
            public static final java.lang.String GROUP_ROW_ID = "data1";
            public static final java.lang.String GROUP_SOURCE_ID = "group_sourceid";

            private GroupMembership() {
            }
        }

        public static final class Website implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/website";
            public static final int TYPE_BLOG = 2;
            public static final int TYPE_FTP = 6;
            public static final int TYPE_HOME = 4;
            public static final int TYPE_HOMEPAGE = 1;
            public static final int TYPE_OTHER = 7;
            public static final int TYPE_PROFILE = 3;
            public static final int TYPE_WORK = 5;
            public static final java.lang.String URL = "data1";

            private Website() {
            }
        }

        @java.lang.Deprecated
        public static final class SipAddress implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.CommonDataKinds.CommonColumns, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/sip_address";
            public static final java.lang.String SIP_ADDRESS = "data1";
            public static final int TYPE_HOME = 1;
            public static final int TYPE_OTHER = 3;
            public static final int TYPE_WORK = 2;

            private SipAddress() {
            }

            public static final int getTypeLabelResource(int i) {
                switch (i) {
                    case 1:
                        return com.android.internal.R.string.sipAddressTypeHome;
                    case 2:
                        return com.android.internal.R.string.sipAddressTypeWork;
                    case 3:
                        return com.android.internal.R.string.sipAddressTypeOther;
                    default:
                        return com.android.internal.R.string.sipAddressTypeCustom;
                }
            }

            public static final java.lang.CharSequence getTypeLabel(android.content.res.Resources resources, int i, java.lang.CharSequence charSequence) {
                if (i == 0 && !android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence;
                }
                return resources.getText(getTypeLabelResource(i));
            }
        }

        public static final class Identity implements android.provider.ContactsContract.DataColumnsWithJoins, android.provider.ContactsContract.ContactCounts {
            public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/identity";
            public static final java.lang.String IDENTITY = "data1";
            public static final java.lang.String NAMESPACE = "data2";

            private Identity() {
            }
        }
    }

    public static final class Groups implements android.provider.BaseColumns, android.provider.ContactsContract.GroupsColumns, android.provider.ContactsContract.SyncColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/group";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/group";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "groups");
        public static final android.net.Uri CONTENT_SUMMARY_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "groups_summary");

        private Groups() {
        }

        public static android.content.EntityIterator newEntityIterator(android.database.Cursor cursor) {
            return new android.provider.ContactsContract.Groups.EntityIteratorImpl(cursor);
        }

        private static class EntityIteratorImpl extends android.content.CursorEntityIterator {
            public EntityIteratorImpl(android.database.Cursor cursor) {
                super(cursor);
            }

            @Override // android.content.CursorEntityIterator
            public android.content.Entity getEntityAndIncrementCursor(android.database.Cursor cursor) throws android.os.RemoteException {
                android.content.ContentValues contentValues = new android.content.ContentValues();
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "_id");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_name");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "account_type");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "dirty");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "version");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sourceid");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "res_package");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "title");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.ContactsContract.GroupsColumns.TITLE_RES);
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, android.provider.ContactsContract.GroupsColumns.GROUP_VISIBLE);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync1");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync2");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync3");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "sync4");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "system_id");
                android.database.DatabaseUtils.cursorLongToContentValuesIfPresent(cursor, contentValues, "deleted");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "notes");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, "should_sync");
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.ContactsContract.GroupsColumns.FAVORITES);
                android.database.DatabaseUtils.cursorStringToContentValuesIfPresent(cursor, contentValues, android.provider.ContactsContract.GroupsColumns.AUTO_ADD);
                cursor.moveToNext();
                return new android.content.Entity(contentValues);
            }
        }
    }

    public static final class AggregationExceptions implements android.provider.BaseColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/aggregation_exception";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/aggregation_exception";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "aggregation_exceptions");
        public static final java.lang.String RAW_CONTACT_ID1 = "raw_contact_id1";
        public static final java.lang.String RAW_CONTACT_ID2 = "raw_contact_id2";
        public static final java.lang.String TYPE = "type";
        public static final int TYPE_AUTOMATIC = 0;
        public static final int TYPE_KEEP_SEPARATE = 2;
        public static final int TYPE_KEEP_TOGETHER = 1;

        private AggregationExceptions() {
        }
    }

    public static final class SimContacts {
        public static final java.lang.String ACTION_SIM_ACCOUNTS_CHANGED = "android.provider.action.SIM_ACCOUNTS_CHANGED";
        public static final java.lang.String ADD_SIM_ACCOUNT_METHOD = "addSimAccount";
        public static final java.lang.String KEY_ACCOUNT_NAME = "key_sim_account_name";
        public static final java.lang.String KEY_ACCOUNT_TYPE = "key_sim_account_type";
        public static final java.lang.String KEY_SIM_ACCOUNTS = "key_sim_accounts";
        public static final java.lang.String KEY_SIM_EF_TYPE = "key_sim_ef_type";
        public static final java.lang.String KEY_SIM_SLOT_INDEX = "key_sim_slot_index";
        public static final java.lang.String QUERY_SIM_ACCOUNTS_METHOD = "querySimAccounts";
        public static final java.lang.String REMOVE_SIM_ACCOUNT_METHOD = "removeSimAccount";

        private SimContacts() {
        }

        @android.annotation.SystemApi
        public static void addSimAccount(android.content.ContentResolver contentResolver, java.lang.String str, java.lang.String str2, int i, int i2) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Sim slot is negative");
            }
            if (!android.provider.ContactsContract.SimAccount.getValidEfTypes().contains(java.lang.Integer.valueOf(i2))) {
                throw new java.lang.IllegalArgumentException("Invalid EF type");
            }
            if (android.text.TextUtils.isEmpty(str) || android.text.TextUtils.isEmpty(str2)) {
                throw new java.lang.IllegalArgumentException("Account name or type is empty");
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(KEY_SIM_SLOT_INDEX, i);
            bundle.putInt(KEY_SIM_EF_TYPE, i2);
            bundle.putString(KEY_ACCOUNT_NAME, str);
            bundle.putString(KEY_ACCOUNT_TYPE, str2);
            android.provider.ContactsContract.nullSafeCall(contentResolver, android.provider.ContactsContract.AUTHORITY_URI, ADD_SIM_ACCOUNT_METHOD, null, bundle);
        }

        @android.annotation.SystemApi
        public static void removeSimAccounts(android.content.ContentResolver contentResolver, int i) {
            if (i < 0) {
                throw new java.lang.IllegalArgumentException("Sim slot is negative");
            }
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt(KEY_SIM_SLOT_INDEX, i);
            android.provider.ContactsContract.nullSafeCall(contentResolver, android.provider.ContactsContract.AUTHORITY_URI, REMOVE_SIM_ACCOUNT_METHOD, null, bundle);
        }

        public static java.util.List<android.provider.ContactsContract.SimAccount> getSimAccounts(android.content.ContentResolver contentResolver) {
            java.util.ArrayList parcelableArrayList = android.provider.ContactsContract.nullSafeCall(contentResolver, android.provider.ContactsContract.AUTHORITY_URI, QUERY_SIM_ACCOUNTS_METHOD, null, null).getParcelableArrayList(KEY_SIM_ACCOUNTS, android.provider.ContactsContract.SimAccount.class);
            if (parcelableArrayList == null) {
                return new java.util.ArrayList();
            }
            return parcelableArrayList;
        }
    }

    public static final class SimAccount implements android.os.Parcelable {
        public static final int ADN_EF_TYPE = 1;
        public static final android.os.Parcelable.Creator<android.provider.ContactsContract.SimAccount> CREATOR = new android.os.Parcelable.Creator<android.provider.ContactsContract.SimAccount>() { // from class: android.provider.ContactsContract.SimAccount.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.ContactsContract.SimAccount createFromParcel(android.os.Parcel parcel) {
                return new android.provider.ContactsContract.SimAccount(parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.provider.ContactsContract.SimAccount[] newArray(int i) {
                return new android.provider.ContactsContract.SimAccount[i];
            }
        };
        public static final int FDN_EF_TYPE = 2;
        public static final int SDN_EF_TYPE = 3;
        public static final int UNKNOWN_EF_TYPE = 0;
        private final java.lang.String mAccountName;
        private final java.lang.String mAccountType;
        private final int mEfType;
        private final int mSimSlotIndex;

        public static java.util.Set<java.lang.Integer> getValidEfTypes() {
            return com.google.android.collect.Sets.newArraySet(1, 3, 2);
        }

        public SimAccount(java.lang.String str, java.lang.String str2, int i, int i2) {
            this.mAccountName = str;
            this.mAccountType = str2;
            this.mSimSlotIndex = i;
            this.mEfType = i2;
        }

        public java.lang.String getAccountName() {
            return this.mAccountName;
        }

        public java.lang.String getAccountType() {
            return this.mAccountType;
        }

        public int getSimSlotIndex() {
            return this.mSimSlotIndex;
        }

        public int getEfType() {
            return this.mEfType;
        }

        public int hashCode() {
            return java.util.Objects.hash(this.mAccountName, this.mAccountType, java.lang.Integer.valueOf(this.mSimSlotIndex), java.lang.Integer.valueOf(this.mEfType));
        }

        public boolean equals(java.lang.Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            try {
                android.provider.ContactsContract.SimAccount simAccount = (android.provider.ContactsContract.SimAccount) obj;
                if (this.mSimSlotIndex != simAccount.mSimSlotIndex || this.mEfType != simAccount.mEfType || !java.util.Objects.equals(this.mAccountName, simAccount.mAccountName) || !java.util.Objects.equals(this.mAccountType, simAccount.mAccountType)) {
                    return false;
                }
                return true;
            } catch (java.lang.ClassCastException e) {
                return false;
            }
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeString(this.mAccountName);
            parcel.writeString(this.mAccountType);
            parcel.writeInt(this.mSimSlotIndex);
            parcel.writeInt(this.mEfType);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }
    }

    public static final class Settings implements android.provider.ContactsContract.SettingsColumns {
        public static final java.lang.String ACTION_SET_DEFAULT_ACCOUNT = "android.provider.action.SET_DEFAULT_ACCOUNT";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/setting";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/setting";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "settings");
        public static final java.lang.String KEY_DEFAULT_ACCOUNT = "key_default_account";
        public static final java.lang.String QUERY_DEFAULT_ACCOUNT_METHOD = "queryDefaultAccount";
        public static final java.lang.String SET_DEFAULT_ACCOUNT_METHOD = "setDefaultAccount";

        private Settings() {
        }

        public static android.accounts.Account getDefaultAccount(android.content.ContentResolver contentResolver) {
            return (android.accounts.Account) contentResolver.call(android.provider.ContactsContract.AUTHORITY_URI, QUERY_DEFAULT_ACCOUNT_METHOD, (java.lang.String) null, (android.os.Bundle) null).getParcelable(KEY_DEFAULT_ACCOUNT, android.accounts.Account.class);
        }

        @android.annotation.SystemApi
        public static void setDefaultAccount(android.content.ContentResolver contentResolver, android.accounts.Account account) {
            android.os.Bundle bundle = new android.os.Bundle();
            if (account != null) {
                bundle.putString("account_name", account.name);
                bundle.putString("account_type", account.type);
            }
            contentResolver.call(android.provider.ContactsContract.AUTHORITY_URI, SET_DEFAULT_ACCOUNT_METHOD, (java.lang.String) null, bundle);
        }
    }

    public static final class ProviderStatus {
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/provider_status";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "provider_status");
        public static final java.lang.String DATABASE_CREATION_TIMESTAMP = "database_creation_timestamp";
        public static final java.lang.String STATUS = "status";
        public static final int STATUS_BUSY = 1;
        public static final int STATUS_EMPTY = 2;
        public static final int STATUS_NORMAL = 0;

        private ProviderStatus() {
        }
    }

    public static final class PinnedPositions {
        public static final int DEMOTED = -1;
        public static final java.lang.String UNDEMOTE_METHOD = "undemote";
        public static final int UNPINNED = 0;

        public static void undemote(android.content.ContentResolver contentResolver, long j) {
            android.provider.ContactsContract.nullSafeCall(contentResolver, android.provider.ContactsContract.AUTHORITY_URI, UNDEMOTE_METHOD, java.lang.String.valueOf(j), null);
        }

        public static void pin(android.content.ContentResolver contentResolver, long j, int i) {
            android.net.Uri withAppendedPath = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Contacts.CONTENT_URI, java.lang.String.valueOf(j));
            android.content.ContentValues contentValues = new android.content.ContentValues();
            contentValues.put(android.provider.ContactsContract.ContactOptionsColumns.PINNED, java.lang.Integer.valueOf(i));
            contentResolver.update(withAppendedPath, contentValues, null, null);
        }
    }

    public static final class QuickContact {
        public static final java.lang.String ACTION_QUICK_CONTACT = "android.provider.action.QUICK_CONTACT";
        public static final java.lang.String EXTRA_EXCLUDE_MIMES = "android.provider.extra.EXCLUDE_MIMES";
        public static final java.lang.String EXTRA_MODE = "android.provider.extra.MODE";
        public static final java.lang.String EXTRA_PRIORITIZED_MIMETYPE = "android.provider.extra.PRIORITIZED_MIMETYPE";

        @java.lang.Deprecated
        public static final java.lang.String EXTRA_TARGET_RECT = "android.provider.extra.TARGET_RECT";
        public static final int MODE_DEFAULT = 3;
        public static final int MODE_LARGE = 3;
        public static final int MODE_MEDIUM = 2;
        public static final int MODE_SMALL = 1;

        public static android.content.Intent composeQuickContactsIntent(android.content.Context context, android.view.View view, android.net.Uri uri, int i, java.lang.String[] strArr) {
            float f = context.getResources().getCompatibilityInfo().applicationScale;
            view.getLocationOnScreen(new int[2]);
            android.graphics.Rect rect = new android.graphics.Rect();
            rect.left = (int) ((r1[0] * f) + 0.5f);
            rect.top = (int) ((r1[1] * f) + 0.5f);
            rect.right = (int) (((r1[0] + view.getWidth()) * f) + 0.5f);
            rect.bottom = (int) (((r1[1] + view.getHeight()) * f) + 0.5f);
            return composeQuickContactsIntent(context, rect, uri, i, strArr);
        }

        public static android.content.Intent composeQuickContactsIntent(android.content.Context context, android.graphics.Rect rect, android.net.Uri uri, int i, java.lang.String[] strArr) {
            while ((context instanceof android.content.ContextWrapper) && !(context instanceof android.app.Activity)) {
                context = ((android.content.ContextWrapper) context).getBaseContext();
            }
            android.content.Intent addFlags = new android.content.Intent(ACTION_QUICK_CONTACT).addFlags((context instanceof android.app.Activity ? 0 : 268468224) | 536870912);
            addFlags.setData(uri);
            addFlags.setSourceBounds(rect);
            addFlags.putExtra(EXTRA_MODE, i);
            addFlags.putExtra(EXTRA_EXCLUDE_MIMES, strArr);
            return addFlags;
        }

        public static android.content.Intent rebuildManagedQuickContactsIntent(java.lang.String str, long j, boolean z, long j2, android.content.Intent intent) {
            android.net.Uri uri;
            android.content.Intent intent2 = new android.content.Intent(ACTION_QUICK_CONTACT);
            if (android.text.TextUtils.isEmpty(str)) {
                uri = null;
            } else if (z) {
                uri = android.net.Uri.withAppendedPath(android.provider.ContactsContract.Contacts.CONTENT_LOOKUP_URI, str);
            } else {
                uri = android.provider.ContactsContract.Contacts.getLookupUri(j, str);
            }
            if (uri != null && j2 != 0) {
                uri = uri.buildUpon().appendQueryParameter("directory", java.lang.String.valueOf(j2)).build();
            }
            intent2.setData(uri);
            intent2.setFlags(intent.getFlags() | 268435456);
            intent2.setSourceBounds(intent.getSourceBounds());
            intent2.putExtra(EXTRA_MODE, intent.getIntExtra(EXTRA_MODE, 3));
            intent2.putExtra(EXTRA_EXCLUDE_MIMES, intent.getStringArrayExtra(EXTRA_EXCLUDE_MIMES));
            return intent2;
        }

        public static void showQuickContact(android.content.Context context, android.view.View view, android.net.Uri uri, int i, java.lang.String[] strArr) {
            android.provider.ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent(context, view, uri, i, strArr));
        }

        public static void showQuickContact(android.content.Context context, android.graphics.Rect rect, android.net.Uri uri, int i, java.lang.String[] strArr) {
            android.provider.ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent(context, rect, uri, i, strArr));
        }

        public static void showQuickContact(android.content.Context context, android.view.View view, android.net.Uri uri, java.lang.String[] strArr, java.lang.String str) {
            android.content.Intent composeQuickContactsIntent = composeQuickContactsIntent(context, view, uri, 3, strArr);
            composeQuickContactsIntent.putExtra(EXTRA_PRIORITIZED_MIMETYPE, str);
            android.provider.ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent);
        }

        public static void showQuickContact(android.content.Context context, android.graphics.Rect rect, android.net.Uri uri, java.lang.String[] strArr, java.lang.String str) {
            android.content.Intent composeQuickContactsIntent = composeQuickContactsIntent(context, rect, uri, 3, strArr);
            composeQuickContactsIntent.putExtra(EXTRA_PRIORITIZED_MIMETYPE, str);
            android.provider.ContactsInternal.startQuickContactWithErrorToast(context, composeQuickContactsIntent);
        }
    }

    public static final class DisplayPhoto {
        public static final java.lang.String DISPLAY_MAX_DIM = "display_max_dim";
        public static final java.lang.String THUMBNAIL_MAX_DIM = "thumbnail_max_dim";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "display_photo");
        public static final android.net.Uri CONTENT_MAX_DIMENSIONS_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.AUTHORITY_URI, "photo_dimensions");

        private DisplayPhoto() {
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final class MetadataSync implements android.provider.BaseColumns, android.provider.ContactsContract.MetadataSyncColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata";
        public static final java.lang.String METADATA_AUTHORITY = "com.android.contacts.metadata";
        public static final android.net.Uri METADATA_AUTHORITY_URI = android.net.Uri.parse("content://com.android.contacts.metadata");
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(METADATA_AUTHORITY_URI, "metadata_sync");

        private MetadataSync() {
        }
    }

    @android.annotation.SystemApi
    @java.lang.Deprecated
    public static final class MetadataSyncState implements android.provider.BaseColumns, android.provider.ContactsContract.MetadataSyncStateColumns {
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/contact_metadata_sync_state";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/contact_metadata_sync_state";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.withAppendedPath(android.provider.ContactsContract.MetadataSync.METADATA_AUTHORITY_URI, "metadata_sync_state");

        private MetadataSyncState() {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static android.os.Bundle nullSafeCall(android.content.ContentResolver contentResolver, android.net.Uri uri, java.lang.String str, java.lang.String str2, android.os.Bundle bundle) {
        try {
            android.content.ContentProviderClient acquireContentProviderClient = contentResolver.acquireContentProviderClient(uri);
            try {
                android.os.Bundle call = acquireContentProviderClient.call(str, str2, bundle);
                if (acquireContentProviderClient != null) {
                    acquireContentProviderClient.close();
                }
                return call;
            } finally {
            }
        } catch (android.os.RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }
}
