package android.provider;

/* loaded from: classes3.dex */
public final class Downloads {
    public static final java.lang.String CALL_CREATE_EXTERNAL_PUBLIC_DIR = "create_external_public_dir";
    public static final java.lang.String CALL_MEDIASTORE_DOWNLOADS_DELETED = "mediastore_downloads_deleted";
    public static final java.lang.String CALL_REVOKE_MEDIASTORE_URI_PERMS = "revoke_mediastore_uri_perms";
    public static final java.lang.String DIR_TYPE = "dir_type";
    public static final java.lang.String EXTRA_IDS = "ids";
    public static final java.lang.String EXTRA_MIME_TYPES = "mime_types";
    private static final java.lang.String QUERY_WHERE_CLAUSE = "notificationpackage=? AND notificationclass=?";

    private Downloads() {
    }

    public static final class Impl implements android.provider.BaseColumns {
        public static final java.lang.String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
        public static final java.lang.String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
        public static final java.lang.String AUTHORITY = "downloads";
        public static final java.lang.String COLUMN_ALLOWED_NETWORK_TYPES = "allowed_network_types";
        public static final java.lang.String COLUMN_ALLOW_METERED = "allow_metered";
        public static final java.lang.String COLUMN_ALLOW_ROAMING = "allow_roaming";
        public static final java.lang.String COLUMN_ALLOW_WRITE = "allow_write";
        public static final java.lang.String COLUMN_APP_DATA = "entity";
        public static final java.lang.String COLUMN_BYPASS_RECOMMENDED_SIZE_LIMIT = "bypass_recommended_size_limit";
        public static final java.lang.String COLUMN_CONTROL = "control";
        public static final java.lang.String COLUMN_COOKIE_DATA = "cookiedata";
        public static final java.lang.String COLUMN_CURRENT_BYTES = "current_bytes";
        public static final java.lang.String COLUMN_DELETED = "deleted";
        public static final java.lang.String COLUMN_DESCRIPTION = "description";
        public static final java.lang.String COLUMN_DESTINATION = "destination";
        public static final java.lang.String COLUMN_ERROR_MSG = "errorMsg";
        public static final java.lang.String COLUMN_FAILED_CONNECTIONS = "numfailed";
        public static final java.lang.String COLUMN_FILE_NAME_HINT = "hint";
        public static final java.lang.String COLUMN_FLAGS = "flags";
        public static final java.lang.String COLUMN_IS_PUBLIC_API = "is_public_api";
        public static final java.lang.String COLUMN_IS_VISIBLE_IN_DOWNLOADS_UI = "is_visible_in_downloads_ui";
        public static final java.lang.String COLUMN_LAST_MODIFICATION = "lastmod";
        public static final java.lang.String COLUMN_LAST_UPDATESRC = "lastUpdateSrc";
        public static final java.lang.String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";
        public static final java.lang.String COLUMN_MEDIASTORE_URI = "mediastore_uri";
        public static final java.lang.String COLUMN_MEDIA_SCANNED = "scanned";
        public static final java.lang.String COLUMN_MIME_TYPE = "mimetype";
        public static final java.lang.String COLUMN_NOTIFICATION_CLASS = "notificationclass";
        public static final java.lang.String COLUMN_NOTIFICATION_EXTRAS = "notificationextras";
        public static final java.lang.String COLUMN_NOTIFICATION_PACKAGE = "notificationpackage";
        public static final java.lang.String COLUMN_NO_INTEGRITY = "no_integrity";
        public static final java.lang.String COLUMN_OTHER_UID = "otheruid";
        public static final java.lang.String COLUMN_REFERER = "referer";
        public static final java.lang.String COLUMN_STATUS = "status";
        public static final java.lang.String COLUMN_TITLE = "title";
        public static final java.lang.String COLUMN_TOTAL_BYTES = "total_bytes";
        public static final java.lang.String COLUMN_URI = "uri";
        public static final java.lang.String COLUMN_USER_AGENT = "useragent";
        public static final java.lang.String COLUMN_VISIBILITY = "visibility";
        public static final int CONTROL_PAUSED = 1;
        public static final int CONTROL_RUN = 0;
        public static final int DESTINATION_CACHE_PARTITION = 1;
        public static final int DESTINATION_CACHE_PARTITION_NOROAMING = 3;
        public static final int DESTINATION_CACHE_PARTITION_PURGEABLE = 2;
        public static final int DESTINATION_EXTERNAL = 0;
        public static final int DESTINATION_FILE_URI = 4;
        public static final int DESTINATION_NON_DOWNLOADMANAGER_DOWNLOAD = 6;

        @java.lang.Deprecated
        public static final int DESTINATION_SYSTEMCACHE_PARTITION = 5;
        public static final int FLAG_REQUIRES_CHARGING = 1;
        public static final int FLAG_REQUIRES_DEVICE_IDLE = 2;
        public static final int LAST_UPDATESRC_DONT_NOTIFY_DOWNLOADSVC = 1;
        public static final int LAST_UPDATESRC_NOT_RELEVANT = 0;
        public static final int MEDIA_NOT_SCANNABLE = 2;
        public static final int MEDIA_NOT_SCANNED = 0;
        public static final int MEDIA_SCANNED = 1;
        public static final int MIN_ARTIFICIAL_ERROR_STATUS = 488;
        public static final java.lang.String PERMISSION_ACCESS = "android.permission.ACCESS_DOWNLOAD_MANAGER";
        public static final java.lang.String PERMISSION_ACCESS_ADVANCED = "android.permission.ACCESS_DOWNLOAD_MANAGER_ADVANCED";
        public static final java.lang.String PERMISSION_ACCESS_ALL = "android.permission.ACCESS_ALL_DOWNLOADS";
        public static final java.lang.String PERMISSION_CACHE = "android.permission.ACCESS_CACHE_FILESYSTEM";
        public static final java.lang.String PERMISSION_CACHE_NON_PURGEABLE = "android.permission.DOWNLOAD_CACHE_NON_PURGEABLE";
        public static final java.lang.String PERMISSION_NO_NOTIFICATION = "android.permission.DOWNLOAD_WITHOUT_NOTIFICATION";
        public static final java.lang.String PERMISSION_SEND_INTENTS = "android.permission.SEND_DOWNLOAD_COMPLETED_INTENTS";
        public static final java.lang.String PUBLICLY_ACCESSIBLE_DOWNLOADS_URI_SEGMENT = "public_downloads";
        public static final int STATUS_BAD_REQUEST = 400;

        @java.lang.Deprecated
        public static final int STATUS_BLOCKED = 498;
        public static final int STATUS_CANCELED = 490;
        public static final int STATUS_CANNOT_RESUME = 489;
        public static final int STATUS_DEVICE_NOT_FOUND_ERROR = 199;
        public static final int STATUS_FILE_ALREADY_EXISTS_ERROR = 488;
        public static final int STATUS_FILE_ERROR = 492;
        public static final int STATUS_HTTP_DATA_ERROR = 495;
        public static final int STATUS_HTTP_EXCEPTION = 496;
        public static final int STATUS_INSUFFICIENT_SPACE_ERROR = 198;
        public static final int STATUS_LENGTH_REQUIRED = 411;
        public static final int STATUS_NOT_ACCEPTABLE = 406;
        public static final int STATUS_PAUSED_BY_APP = 193;
        public static final int STATUS_PAUSED_MANUAL = 197;
        public static final int STATUS_PENDING = 190;
        public static final int STATUS_PRECONDITION_FAILED = 412;
        public static final int STATUS_QUEUED_FOR_WIFI = 196;
        public static final int STATUS_RUNNING = 192;
        public static final int STATUS_SUCCESS = 200;
        public static final int STATUS_TOO_MANY_REDIRECTS = 497;
        public static final int STATUS_UNHANDLED_HTTP_CODE = 494;
        public static final int STATUS_UNHANDLED_REDIRECT = 493;
        public static final int STATUS_UNKNOWN_ERROR = 491;
        public static final int STATUS_WAITING_FOR_NETWORK = 195;
        public static final int STATUS_WAITING_TO_RETRY = 194;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE = 0;
        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final java.lang.String _DATA = "_data";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://downloads/my_downloads");
        public static final android.net.Uri ALL_DOWNLOADS_CONTENT_URI = android.net.Uri.parse("content://downloads/all_downloads");
        public static final android.net.Uri PUBLICLY_ACCESSIBLE_DOWNLOADS_URI = android.net.Uri.parse("content://downloads/public_downloads");

        public static class RequestHeaders {
            public static final java.lang.String COLUMN_DOWNLOAD_ID = "download_id";
            public static final java.lang.String COLUMN_HEADER = "header";
            public static final java.lang.String COLUMN_VALUE = "value";
            public static final java.lang.String HEADERS_DB_TABLE = "request_headers";
            public static final java.lang.String INSERT_KEY_PREFIX = "http_header_";
            public static final java.lang.String URI_SEGMENT = "headers";
        }

        private Impl() {
        }

        public static boolean isStatusInformational(int i) {
            return i >= 100 && i < 200;
        }

        public static boolean isStatusSuccess(int i) {
            return i >= 200 && i < 300;
        }

        public static boolean isStatusError(int i) {
            return i >= 400 && i < 600;
        }

        public static boolean isStatusClientError(int i) {
            return i >= 400 && i < 500;
        }

        public static boolean isStatusServerError(int i) {
            return i >= 500 && i < 600;
        }

        public static boolean isNotificationToBeDisplayed(int i) {
            return i == 1 || i == 3;
        }

        public static boolean isStatusCompleted(int i) {
            return (i >= 200 && i < 300) || (i >= 400 && i < 600);
        }

        public static java.lang.String statusToString(int i) {
            switch (i) {
                case 190:
                    return "PENDING";
                case 192:
                    return "RUNNING";
                case 193:
                    return "PAUSED_BY_APP";
                case 194:
                    return "WAITING_TO_RETRY";
                case 195:
                    return "WAITING_FOR_NETWORK";
                case 196:
                    return "QUEUED_FOR_WIFI";
                case 198:
                    return "INSUFFICIENT_SPACE_ERROR";
                case 199:
                    return "DEVICE_NOT_FOUND_ERROR";
                case 200:
                    return android.service.timezone.TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
                case 400:
                    return "BAD_REQUEST";
                case 406:
                    return "NOT_ACCEPTABLE";
                case 411:
                    return "LENGTH_REQUIRED";
                case 412:
                    return "PRECONDITION_FAILED";
                case 488:
                    return "FILE_ALREADY_EXISTS_ERROR";
                case 489:
                    return "CANNOT_RESUME";
                case 490:
                    return "CANCELED";
                case 491:
                    return "UNKNOWN_ERROR";
                case 492:
                    return "FILE_ERROR";
                case 493:
                    return "UNHANDLED_REDIRECT";
                case 494:
                    return "UNHANDLED_HTTP_CODE";
                case 495:
                    return "HTTP_DATA_ERROR";
                case 496:
                    return "HTTP_EXCEPTION";
                case 497:
                    return "TOO_MANY_REDIRECTS";
                case 498:
                    return "BLOCKED";
                default:
                    return java.lang.Integer.toString(i);
            }
        }
    }

    public static final void removeAllDownloadsByPackage(android.content.Context context, java.lang.String str, java.lang.String str2) {
        context.getContentResolver().delete(android.provider.Downloads.Impl.CONTENT_URI, QUERY_WHERE_CLAUSE, new java.lang.String[]{str, str2});
    }
}
