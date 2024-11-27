package android.media.tv;

/* loaded from: classes2.dex */
public final class TvContract {

    @android.annotation.SystemApi
    public static final java.lang.String ACTION_CHANNEL_BROWSABLE_REQUESTED = "android.media.tv.action.CHANNEL_BROWSABLE_REQUESTED";
    public static final java.lang.String ACTION_INITIALIZE_PROGRAMS = "android.media.tv.action.INITIALIZE_PROGRAMS";
    public static final java.lang.String ACTION_PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT = "android.media.tv.action.PREVIEW_PROGRAM_ADDED_TO_WATCH_NEXT";
    public static final java.lang.String ACTION_PREVIEW_PROGRAM_BROWSABLE_DISABLED = "android.media.tv.action.PREVIEW_PROGRAM_BROWSABLE_DISABLED";
    public static final java.lang.String ACTION_REQUEST_CHANNEL_BROWSABLE = "android.media.tv.action.REQUEST_CHANNEL_BROWSABLE";
    public static final java.lang.String ACTION_WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED = "android.media.tv.action.WATCH_NEXT_PROGRAM_BROWSABLE_DISABLED";
    public static final java.lang.String AUTHORITY = "android.media.tv";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_BLOCKED_PACKAGES = "android.media.tv.extra.BLOCKED_PACKAGES";
    public static final java.lang.String EXTRA_CHANNEL_ID = "android.media.tv.extra.CHANNEL_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_COLUMN_NAME = "android.media.tv.extra.COLUMN_NAME";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DATA_TYPE = "android.media.tv.extra.DATA_TYPE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_DEFAULT_VALUE = "android.media.tv.extra.DEFAULT_VALUE";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_EXISTING_COLUMN_NAMES = "android.media.tv.extra.EXISTING_COLUMN_NAMES";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_PACKAGE_NAME = "android.media.tv.extra.PACKAGE_NAME";
    public static final java.lang.String EXTRA_PREVIEW_PROGRAM_ID = "android.media.tv.extra.PREVIEW_PROGRAM_ID";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_RESULT_CODE = "android.media.tv.extra.RESULT_CODE";
    public static final java.lang.String EXTRA_WATCH_NEXT_PROGRAM_ID = "android.media.tv.extra.WATCH_NEXT_PROGRAM_ID";

    @android.annotation.SystemApi
    public static final java.lang.String METHOD_ADD_COLUMN = "add_column";

    @android.annotation.SystemApi
    public static final java.lang.String METHOD_BLOCK_PACKAGE = "block_package";

    @android.annotation.SystemApi
    public static final java.lang.String METHOD_GET_BLOCKED_PACKAGES = "get_blocked_packages";

    @android.annotation.SystemApi
    public static final java.lang.String METHOD_GET_COLUMNS = "get_columns";

    @android.annotation.SystemApi
    public static final java.lang.String METHOD_UNBLOCK_PACKAGE = "unblock_package";
    public static final java.lang.String PARAM_BROWSABLE_ONLY = "browsable_only";
    public static final java.lang.String PARAM_CANONICAL_GENRE = "canonical_genre";
    public static final java.lang.String PARAM_CHANNEL = "channel";
    public static final java.lang.String PARAM_END_TIME = "end_time";
    public static final java.lang.String PARAM_INPUT = "input";
    public static final java.lang.String PARAM_PACKAGE = "package";
    public static final java.lang.String PARAM_PREVIEW = "preview";
    public static final java.lang.String PARAM_START_TIME = "start_time";
    private static final java.lang.String PATH_CHANNEL = "channel";
    private static final java.lang.String PATH_PASSTHROUGH = "passthrough";
    private static final java.lang.String PATH_PREVIEW_PROGRAM = "preview_program";
    private static final java.lang.String PATH_PROGRAM = "program";
    private static final java.lang.String PATH_RECORDED_PROGRAM = "recorded_program";
    private static final java.lang.String PATH_WATCH_NEXT_PROGRAM = "watch_next_program";
    public static final java.lang.String PERMISSION_READ_TV_LISTINGS = "android.permission.READ_TV_LISTINGS";

    @android.annotation.SystemApi
    public static final int RESULT_ERROR_INVALID_ARGUMENT = 2;

    @android.annotation.SystemApi
    public static final int RESULT_ERROR_IO = 1;

    @android.annotation.SystemApi
    public static final int RESULT_OK = 0;

    public interface BaseTvColumns extends android.provider.BaseColumns {
        public static final java.lang.String COLUMN_PACKAGE_NAME = "package_name";
    }

    interface PreviewProgramColumns {
        public static final int ASPECT_RATIO_16_9 = 0;
        public static final int ASPECT_RATIO_1_1 = 3;
        public static final int ASPECT_RATIO_2_3 = 4;
        public static final int ASPECT_RATIO_3_2 = 1;
        public static final int ASPECT_RATIO_4_3 = 2;
        public static final int AVAILABILITY_AVAILABLE = 0;
        public static final int AVAILABILITY_FREE_WITH_SUBSCRIPTION = 1;
        public static final int AVAILABILITY_PAID_CONTENT = 2;
        public static final java.lang.String COLUMN_AUTHOR = "author";
        public static final java.lang.String COLUMN_AVAILABILITY = "availability";
        public static final java.lang.String COLUMN_BROWSABLE = "browsable";
        public static final java.lang.String COLUMN_CONTENT_ID = "content_id";
        public static final java.lang.String COLUMN_DURATION_MILLIS = "duration_millis";
        public static final java.lang.String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        public static final java.lang.String COLUMN_INTENT_URI = "intent_uri";
        public static final java.lang.String COLUMN_INTERACTION_COUNT = "interaction_count";
        public static final java.lang.String COLUMN_INTERACTION_TYPE = "interaction_type";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final java.lang.String COLUMN_ITEM_COUNT = "item_count";
        public static final java.lang.String COLUMN_LAST_PLAYBACK_POSITION_MILLIS = "last_playback_position_millis";
        public static final java.lang.String COLUMN_LIVE = "live";
        public static final java.lang.String COLUMN_LOGO_URI = "logo_uri";
        public static final java.lang.String COLUMN_OFFER_PRICE = "offer_price";
        public static final java.lang.String COLUMN_POSTER_ART_ASPECT_RATIO = "poster_art_aspect_ratio";
        public static final java.lang.String COLUMN_PREVIEW_VIDEO_URI = "preview_video_uri";
        public static final java.lang.String COLUMN_RELEASE_DATE = "release_date";
        public static final java.lang.String COLUMN_STARTING_PRICE = "starting_price";
        public static final java.lang.String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final java.lang.String COLUMN_THUMBNAIL_ASPECT_RATIO = "poster_thumbnail_aspect_ratio";
        public static final java.lang.String COLUMN_TRANSIENT = "transient";
        public static final java.lang.String COLUMN_TYPE = "type";
        public static final int INTERACTION_TYPE_FANS = 3;
        public static final int INTERACTION_TYPE_FOLLOWERS = 2;
        public static final int INTERACTION_TYPE_LIKES = 4;
        public static final int INTERACTION_TYPE_LISTENS = 1;
        public static final int INTERACTION_TYPE_THUMBS = 5;
        public static final int INTERACTION_TYPE_VIEWERS = 6;
        public static final int INTERACTION_TYPE_VIEWS = 0;
        public static final int TYPE_ALBUM = 8;
        public static final int TYPE_ARTIST = 9;
        public static final int TYPE_CHANNEL = 6;
        public static final int TYPE_CLIP = 4;
        public static final int TYPE_EVENT = 5;
        public static final int TYPE_MOVIE = 0;
        public static final int TYPE_PLAYLIST = 10;
        public static final int TYPE_STATION = 11;
        public static final int TYPE_TRACK = 7;
        public static final int TYPE_TV_EPISODE = 3;
        public static final int TYPE_TV_SEASON = 2;
        public static final int TYPE_TV_SERIES = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface AspectRatio {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Availability {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface InteractionType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Type {
        }
    }

    interface ProgramColumns {
        public static final java.lang.String COLUMN_AUDIO_LANGUAGE = "audio_language";
        public static final java.lang.String COLUMN_CANONICAL_GENRE = "canonical_genre";
        public static final java.lang.String COLUMN_CONTENT_RATING = "content_rating";
        public static final java.lang.String COLUMN_EPISODE_DISPLAY_NUMBER = "episode_display_number";
        public static final java.lang.String COLUMN_EPISODE_TITLE = "episode_title";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
        public static final java.lang.String COLUMN_LONG_DESCRIPTION = "long_description";
        public static final java.lang.String COLUMN_POSTER_ART_URI = "poster_art_uri";
        public static final java.lang.String COLUMN_REVIEW_RATING = "review_rating";
        public static final java.lang.String COLUMN_REVIEW_RATING_STYLE = "review_rating_style";
        public static final java.lang.String COLUMN_SEARCHABLE = "searchable";
        public static final java.lang.String COLUMN_SEASON_DISPLAY_NUMBER = "season_display_number";
        public static final java.lang.String COLUMN_SEASON_TITLE = "season_title";
        public static final java.lang.String COLUMN_SERIES_ID = "series_id";
        public static final java.lang.String COLUMN_SHORT_DESCRIPTION = "short_description";
        public static final java.lang.String COLUMN_SPLIT_ID = "split_id";
        public static final java.lang.String COLUMN_THUMBNAIL_URI = "thumbnail_uri";
        public static final java.lang.String COLUMN_TITLE = "title";
        public static final java.lang.String COLUMN_VERSION_NUMBER = "version_number";
        public static final java.lang.String COLUMN_VIDEO_HEIGHT = "video_height";
        public static final java.lang.String COLUMN_VIDEO_WIDTH = "video_width";
        public static final int REVIEW_RATING_STYLE_PERCENTAGE = 2;
        public static final int REVIEW_RATING_STYLE_STARS = 0;
        public static final int REVIEW_RATING_STYLE_THUMBS_UP_DOWN = 1;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ReviewRatingStyle {
        }
    }

    public static java.lang.String buildInputId(android.content.ComponentName componentName) {
        return componentName.flattenToShortString();
    }

    public static android.net.Uri buildChannelUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.Channels.CONTENT_URI, j);
    }

    public static android.net.Uri buildChannelUriForPassthroughInput(java.lang.String str) {
        return new android.net.Uri.Builder().scheme("content").authority(AUTHORITY).appendPath(PATH_PASSTHROUGH).appendPath(str).build();
    }

    public static android.net.Uri buildChannelLogoUri(long j) {
        return buildChannelLogoUri(buildChannelUri(j));
    }

    public static android.net.Uri buildChannelLogoUri(android.net.Uri uri) {
        if (!isChannelUriForTunerInput(uri)) {
            throw new java.lang.IllegalArgumentException("Not a channel: " + uri);
        }
        return android.net.Uri.withAppendedPath(uri, android.media.tv.TvContract.Channels.Logo.CONTENT_DIRECTORY);
    }

    public static android.net.Uri buildChannelsUriForInput(java.lang.String str) {
        return buildChannelsUriForInput(str, false);
    }

    @android.annotation.SystemApi
    public static android.net.Uri buildChannelsUriForInput(java.lang.String str, boolean z) {
        android.net.Uri.Builder buildUpon = android.media.tv.TvContract.Channels.CONTENT_URI.buildUpon();
        if (str != null) {
            buildUpon.appendQueryParameter("input", str);
        }
        return buildUpon.appendQueryParameter(PARAM_BROWSABLE_ONLY, java.lang.String.valueOf(z)).build();
    }

    @android.annotation.SystemApi
    public static android.net.Uri buildChannelsUriForInput(java.lang.String str, java.lang.String str2, boolean z) {
        if (str2 == null) {
            return buildChannelsUriForInput(str, z);
        }
        if (!android.media.tv.TvContract.Programs.Genres.isCanonical(str2)) {
            throw new java.lang.IllegalArgumentException("Not a canonical genre: '" + str2 + "'");
        }
        return buildChannelsUriForInput(str, z).buildUpon().appendQueryParameter("canonical_genre", str2).build();
    }

    public static android.net.Uri buildProgramUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.Programs.CONTENT_URI, j);
    }

    public static android.net.Uri buildProgramsUriForChannel(long j) {
        return android.media.tv.TvContract.Programs.CONTENT_URI.buildUpon().appendQueryParameter("channel", java.lang.String.valueOf(j)).build();
    }

    public static android.net.Uri buildProgramsUriForChannel(android.net.Uri uri) {
        if (!isChannelUriForTunerInput(uri)) {
            throw new java.lang.IllegalArgumentException("Not a channel: " + uri);
        }
        return buildProgramsUriForChannel(android.content.ContentUris.parseId(uri));
    }

    public static android.net.Uri buildProgramsUriForChannel(long j, long j2, long j3) {
        return buildProgramsUriForChannel(j).buildUpon().appendQueryParameter(PARAM_START_TIME, java.lang.String.valueOf(j2)).appendQueryParameter(PARAM_END_TIME, java.lang.String.valueOf(j3)).build();
    }

    public static android.net.Uri buildProgramsUriForChannel(android.net.Uri uri, long j, long j2) {
        if (!isChannelUriForTunerInput(uri)) {
            throw new java.lang.IllegalArgumentException("Not a channel: " + uri);
        }
        return buildProgramsUriForChannel(android.content.ContentUris.parseId(uri), j, j2);
    }

    public static android.net.Uri buildRecordedProgramUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.RecordedPrograms.CONTENT_URI, j);
    }

    public static android.net.Uri buildPreviewProgramUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.PreviewPrograms.CONTENT_URI, j);
    }

    public static android.net.Uri buildPreviewProgramsUriForChannel(long j) {
        return android.media.tv.TvContract.PreviewPrograms.CONTENT_URI.buildUpon().appendQueryParameter("channel", java.lang.String.valueOf(j)).build();
    }

    public static android.net.Uri buildPreviewProgramsUriForChannel(android.net.Uri uri) {
        if (!isChannelUriForTunerInput(uri)) {
            throw new java.lang.IllegalArgumentException("Not a channel: " + uri);
        }
        return buildPreviewProgramsUriForChannel(android.content.ContentUris.parseId(uri));
    }

    public static android.net.Uri buildWatchNextProgramUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.WatchNextPrograms.CONTENT_URI, j);
    }

    public static android.net.Uri buildWatchedProgramUri(long j) {
        return android.content.ContentUris.withAppendedId(android.media.tv.TvContract.WatchedPrograms.CONTENT_URI, j);
    }

    private static boolean isTvUri(android.net.Uri uri) {
        return uri != null && "content".equals(uri.getScheme()) && AUTHORITY.equals(uri.getAuthority());
    }

    private static boolean isTwoSegmentUriStartingWith(android.net.Uri uri, java.lang.String str) {
        java.util.List<java.lang.String> pathSegments = uri.getPathSegments();
        return pathSegments.size() == 2 && str.equals(pathSegments.get(0));
    }

    public static boolean isChannelUri(android.net.Uri uri) {
        return isChannelUriForTunerInput(uri) || isChannelUriForPassthroughInput(uri);
    }

    public static boolean isChannelUriForTunerInput(android.net.Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, "channel");
    }

    public static boolean isChannelUriForPassthroughInput(android.net.Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_PASSTHROUGH);
    }

    public static boolean isProgramUri(android.net.Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_PROGRAM);
    }

    public static boolean isRecordedProgramUri(android.net.Uri uri) {
        return isTvUri(uri) && isTwoSegmentUriStartingWith(uri, PATH_RECORDED_PROGRAM);
    }

    public static void requestChannelBrowsable(android.content.Context context, long j) {
        android.media.tv.TvInputManager tvInputManager = (android.media.tv.TvInputManager) context.getSystemService(android.content.Context.TV_INPUT_SERVICE);
        if (tvInputManager != null) {
            tvInputManager.requestChannelBrowsable(buildChannelUri(j));
        }
    }

    private TvContract() {
    }

    public static final class Channels implements android.media.tv.TvContract.BaseTvColumns {
        public static final int BROADCAST_VISIBILITY_TYPE_INVISIBLE = 2;
        public static final int BROADCAST_VISIBILITY_TYPE_NUMERIC_SELECTABLE_ONLY = 1;
        public static final int BROADCAST_VISIBILITY_TYPE_VISIBLE = 0;
        public static final java.lang.String COLUMN_APP_LINK_COLOR = "app_link_color";
        public static final java.lang.String COLUMN_APP_LINK_ICON_URI = "app_link_icon_uri";
        public static final java.lang.String COLUMN_APP_LINK_INTENT_URI = "app_link_intent_uri";
        public static final java.lang.String COLUMN_APP_LINK_POSTER_ART_URI = "app_link_poster_art_uri";
        public static final java.lang.String COLUMN_APP_LINK_TEXT = "app_link_text";
        public static final java.lang.String COLUMN_BROADCAST_GENRE = "broadcast_genre";
        public static final java.lang.String COLUMN_BROADCAST_VISIBILITY_TYPE = "broadcast_visibility_type";
        public static final java.lang.String COLUMN_BROWSABLE = "browsable";
        public static final java.lang.String COLUMN_CHANNEL_LIST_ID = "channel_list_id";
        public static final java.lang.String COLUMN_DESCRIPTION = "description";
        public static final java.lang.String COLUMN_DISPLAY_NAME = "display_name";
        public static final java.lang.String COLUMN_DISPLAY_NUMBER = "display_number";
        public static final java.lang.String COLUMN_GLOBAL_CONTENT_ID = "global_content_id";
        public static final java.lang.String COLUMN_INPUT_ID = "input_id";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_DATA = "internal_provider_data";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG1 = "internal_provider_flag1";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG2 = "internal_provider_flag2";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG3 = "internal_provider_flag3";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_FLAG4 = "internal_provider_flag4";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final java.lang.String COLUMN_LOCKED = "locked";
        public static final java.lang.String COLUMN_NETWORK_AFFILIATION = "network_affiliation";
        public static final java.lang.String COLUMN_ORIGINAL_NETWORK_ID = "original_network_id";
        public static final java.lang.String COLUMN_REMOTE_CONTROL_KEY_PRESET_NUMBER = "remote_control_key_preset_number";
        public static final java.lang.String COLUMN_SCRAMBLED = "scrambled";
        public static final java.lang.String COLUMN_SEARCHABLE = "searchable";
        public static final java.lang.String COLUMN_SERVICE_ID = "service_id";
        public static final java.lang.String COLUMN_SERVICE_TYPE = "service_type";
        public static final java.lang.String COLUMN_TRANSIENT = "transient";
        public static final java.lang.String COLUMN_TRANSPORT_STREAM_ID = "transport_stream_id";
        public static final java.lang.String COLUMN_TYPE = "type";
        public static final java.lang.String COLUMN_VERSION_NUMBER = "version_number";
        public static final java.lang.String COLUMN_VIDEO_FORMAT = "video_format";
        public static final java.lang.String COLUMN_VIDEO_RESOLUTION = "video_resolution";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/channel";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/channel";
        public static final java.lang.String SERVICE_TYPE_AUDIO = "SERVICE_TYPE_AUDIO";
        public static final java.lang.String SERVICE_TYPE_AUDIO_VIDEO = "SERVICE_TYPE_AUDIO_VIDEO";
        public static final java.lang.String SERVICE_TYPE_OTHER = "SERVICE_TYPE_OTHER";
        public static final java.lang.String TYPE_1SEG = "TYPE_1SEG";
        public static final java.lang.String TYPE_ATSC3_T = "TYPE_ATSC3_T";
        public static final java.lang.String TYPE_ATSC_C = "TYPE_ATSC_C";
        public static final java.lang.String TYPE_ATSC_M_H = "TYPE_ATSC_M_H";
        public static final java.lang.String TYPE_ATSC_T = "TYPE_ATSC_T";
        public static final java.lang.String TYPE_CMMB = "TYPE_CMMB";
        public static final java.lang.String TYPE_DTMB = "TYPE_DTMB";
        public static final java.lang.String TYPE_DVB_C = "TYPE_DVB_C";
        public static final java.lang.String TYPE_DVB_C2 = "TYPE_DVB_C2";
        public static final java.lang.String TYPE_DVB_H = "TYPE_DVB_H";
        public static final java.lang.String TYPE_DVB_S = "TYPE_DVB_S";
        public static final java.lang.String TYPE_DVB_S2 = "TYPE_DVB_S2";
        public static final java.lang.String TYPE_DVB_SH = "TYPE_DVB_SH";
        public static final java.lang.String TYPE_DVB_T = "TYPE_DVB_T";
        public static final java.lang.String TYPE_DVB_T2 = "TYPE_DVB_T2";
        public static final java.lang.String TYPE_ISDB_C = "TYPE_ISDB_C";
        public static final java.lang.String TYPE_ISDB_S = "TYPE_ISDB_S";
        public static final java.lang.String TYPE_ISDB_S3 = "TYPE_ISDB_S3";
        public static final java.lang.String TYPE_ISDB_T = "TYPE_ISDB_T";
        public static final java.lang.String TYPE_ISDB_TB = "TYPE_ISDB_TB";
        public static final java.lang.String TYPE_NTSC = "TYPE_NTSC";
        public static final java.lang.String TYPE_OTHER = "TYPE_OTHER";
        public static final java.lang.String TYPE_PAL = "TYPE_PAL";
        public static final java.lang.String TYPE_PREVIEW = "TYPE_PREVIEW";
        public static final java.lang.String TYPE_SECAM = "TYPE_SECAM";
        public static final java.lang.String TYPE_S_DMB = "TYPE_S_DMB";
        public static final java.lang.String TYPE_T_DMB = "TYPE_T_DMB";
        public static final java.lang.String VIDEO_FORMAT_1080I = "VIDEO_FORMAT_1080I";
        public static final java.lang.String VIDEO_FORMAT_1080P = "VIDEO_FORMAT_1080P";
        public static final java.lang.String VIDEO_FORMAT_2160P = "VIDEO_FORMAT_2160P";
        public static final java.lang.String VIDEO_FORMAT_240P = "VIDEO_FORMAT_240P";
        public static final java.lang.String VIDEO_FORMAT_360P = "VIDEO_FORMAT_360P";
        public static final java.lang.String VIDEO_FORMAT_4320P = "VIDEO_FORMAT_4320P";
        public static final java.lang.String VIDEO_FORMAT_480I = "VIDEO_FORMAT_480I";
        public static final java.lang.String VIDEO_FORMAT_480P = "VIDEO_FORMAT_480P";
        public static final java.lang.String VIDEO_FORMAT_576I = "VIDEO_FORMAT_576I";
        public static final java.lang.String VIDEO_FORMAT_576P = "VIDEO_FORMAT_576P";
        public static final java.lang.String VIDEO_FORMAT_720P = "VIDEO_FORMAT_720P";
        public static final java.lang.String VIDEO_RESOLUTION_ED = "VIDEO_RESOLUTION_ED";
        public static final java.lang.String VIDEO_RESOLUTION_FHD = "VIDEO_RESOLUTION_FHD";
        public static final java.lang.String VIDEO_RESOLUTION_HD = "VIDEO_RESOLUTION_HD";
        public static final java.lang.String VIDEO_RESOLUTION_SD = "VIDEO_RESOLUTION_SD";
        public static final java.lang.String VIDEO_RESOLUTION_UHD = "VIDEO_RESOLUTION_UHD";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/channel");
        private static final java.util.Map<java.lang.String, java.lang.String> VIDEO_FORMAT_TO_RESOLUTION_MAP = new java.util.HashMap();

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface BroadcastVisibilityType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ServiceType {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface Type {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface VideoFormat {
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface VideoResolution {
        }

        static {
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_480I, VIDEO_RESOLUTION_SD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_480P, VIDEO_RESOLUTION_ED);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_576I, VIDEO_RESOLUTION_SD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_576P, VIDEO_RESOLUTION_ED);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_720P, VIDEO_RESOLUTION_HD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_1080I, VIDEO_RESOLUTION_HD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_1080P, VIDEO_RESOLUTION_FHD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_2160P, VIDEO_RESOLUTION_UHD);
            VIDEO_FORMAT_TO_RESOLUTION_MAP.put(VIDEO_FORMAT_4320P, VIDEO_RESOLUTION_UHD);
        }

        public static final java.lang.String getVideoResolution(java.lang.String str) {
            return VIDEO_FORMAT_TO_RESOLUTION_MAP.get(str);
        }

        private Channels() {
        }

        public static final class Logo {
            public static final java.lang.String CONTENT_DIRECTORY = "logo";

            private Logo() {
            }
        }
    }

    public static final class Programs implements android.media.tv.TvContract.BaseTvColumns, android.media.tv.TvContract.ProgramColumns {
        public static final java.lang.String COLUMN_BROADCAST_GENRE = "broadcast_genre";
        public static final java.lang.String COLUMN_CHANNEL_ID = "channel_id";
        public static final java.lang.String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";

        @java.lang.Deprecated
        public static final java.lang.String COLUMN_EPISODE_NUMBER = "episode_number";
        public static final java.lang.String COLUMN_EVENT_ID = "event_id";
        public static final java.lang.String COLUMN_GLOBAL_CONTENT_ID = "global_content_id";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final java.lang.String COLUMN_MULTI_SERIES_ID = "multi_series_id";
        public static final java.lang.String COLUMN_RECORDING_PROHIBITED = "recording_prohibited";
        public static final java.lang.String COLUMN_SCRAMBLED = "scrambled";

        @java.lang.Deprecated
        public static final java.lang.String COLUMN_SEASON_NUMBER = "season_number";
        public static final java.lang.String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/program";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/program";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/program");

        private Programs() {
        }

        public static final class Genres {
            public static final java.lang.String ANIMAL_WILDLIFE = "ANIMAL_WILDLIFE";
            public static final java.lang.String ARTS = "ARTS";
            private static final android.util.ArraySet<java.lang.String> CANONICAL_GENRES = new android.util.ArraySet<>();
            public static final java.lang.String COMEDY = "COMEDY";
            private static final char COMMA = ',';
            private static final java.lang.String DELIMITER = ",";
            private static final char DOUBLE_QUOTE = '\"';
            public static final java.lang.String DRAMA = "DRAMA";
            public static final java.lang.String EDUCATION = "EDUCATION";
            private static final java.lang.String[] EMPTY_STRING_ARRAY;
            public static final java.lang.String ENTERTAINMENT = "ENTERTAINMENT";
            public static final java.lang.String FAMILY_KIDS = "FAMILY_KIDS";
            public static final java.lang.String GAMING = "GAMING";
            public static final java.lang.String LIFE_STYLE = "LIFE_STYLE";
            public static final java.lang.String MOVIES = "MOVIES";
            public static final java.lang.String MUSIC = "MUSIC";
            public static final java.lang.String NEWS = "NEWS";
            public static final java.lang.String PREMIER = "PREMIER";
            public static final java.lang.String SHOPPING = "SHOPPING";
            public static final java.lang.String SPORTS = "SPORTS";
            public static final java.lang.String TECH_SCIENCE = "TECH_SCIENCE";
            public static final java.lang.String TRAVEL = "TRAVEL";

            @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
            public @interface Genre {
            }

            static {
                CANONICAL_GENRES.add(FAMILY_KIDS);
                CANONICAL_GENRES.add(SPORTS);
                CANONICAL_GENRES.add(SHOPPING);
                CANONICAL_GENRES.add(MOVIES);
                CANONICAL_GENRES.add(COMEDY);
                CANONICAL_GENRES.add(TRAVEL);
                CANONICAL_GENRES.add(DRAMA);
                CANONICAL_GENRES.add(EDUCATION);
                CANONICAL_GENRES.add(ANIMAL_WILDLIFE);
                CANONICAL_GENRES.add(NEWS);
                CANONICAL_GENRES.add(GAMING);
                CANONICAL_GENRES.add(ARTS);
                CANONICAL_GENRES.add(ENTERTAINMENT);
                CANONICAL_GENRES.add(LIFE_STYLE);
                CANONICAL_GENRES.add(MUSIC);
                CANONICAL_GENRES.add(PREMIER);
                CANONICAL_GENRES.add(TECH_SCIENCE);
                EMPTY_STRING_ARRAY = new java.lang.String[0];
            }

            private Genres() {
            }

            public static java.lang.String encode(java.lang.String... strArr) {
                if (strArr == null) {
                    return null;
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                int length = strArr.length;
                java.lang.String str = "";
                int i = 0;
                while (i < length) {
                    sb.append(str).append(encodeToCsv(strArr[i]));
                    i++;
                    str = ",";
                }
                return sb.toString();
            }

            private static java.lang.String encodeToCsv(java.lang.String str) {
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    char charAt = str.charAt(i);
                    switch (charAt) {
                        case '\"':
                            sb.append(DOUBLE_QUOTE);
                            break;
                        case ',':
                            sb.append(DOUBLE_QUOTE);
                            break;
                    }
                    sb.append(charAt);
                }
                return sb.toString();
            }

            public static java.lang.String[] decode(java.lang.String str) {
                if (android.text.TextUtils.isEmpty(str)) {
                    return EMPTY_STRING_ARRAY;
                }
                if (str.indexOf(44) == -1 && str.indexOf(34) == -1) {
                    return new java.lang.String[]{str.trim()};
                }
                java.lang.StringBuilder sb = new java.lang.StringBuilder();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                int length = str.length();
                boolean z = false;
                for (int i = 0; i < length; i++) {
                    char charAt = str.charAt(i);
                    switch (charAt) {
                        case '\"':
                            if (!z) {
                                z = true;
                                continue;
                            }
                            break;
                        case ',':
                            if (!z) {
                                java.lang.String trim = sb.toString().trim();
                                if (trim.length() > 0) {
                                    arrayList.add(trim);
                                }
                                sb = new java.lang.StringBuilder();
                                continue;
                            }
                            break;
                    }
                    sb.append(charAt);
                    z = false;
                }
                java.lang.String trim2 = sb.toString().trim();
                if (trim2.length() > 0) {
                    arrayList.add(trim2);
                }
                return (java.lang.String[]) arrayList.toArray(new java.lang.String[arrayList.size()]);
            }

            public static boolean isCanonical(java.lang.String str) {
                return CANONICAL_GENRES.contains(str);
            }
        }
    }

    public static final class RecordedPrograms implements android.media.tv.TvContract.BaseTvColumns, android.media.tv.TvContract.ProgramColumns {
        public static final java.lang.String COLUMN_BROADCAST_GENRE = "broadcast_genre";
        public static final java.lang.String COLUMN_CHANNEL_ID = "channel_id";
        public static final java.lang.String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        public static final java.lang.String COLUMN_INPUT_ID = "input_id";
        public static final java.lang.String COLUMN_INTERNAL_PROVIDER_ID = "internal_provider_id";
        public static final java.lang.String COLUMN_MULTI_SERIES_ID = "multi_series_id";
        public static final java.lang.String COLUMN_RECORDING_DATA_BYTES = "recording_data_bytes";
        public static final java.lang.String COLUMN_RECORDING_DATA_URI = "recording_data_uri";
        public static final java.lang.String COLUMN_RECORDING_DURATION_MILLIS = "recording_duration_millis";
        public static final java.lang.String COLUMN_RECORDING_EXPIRE_TIME_UTC_MILLIS = "recording_expire_time_utc_millis";
        public static final java.lang.String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/recorded_program";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/recorded_program";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/recorded_program");

        private RecordedPrograms() {
        }
    }

    public static final class PreviewPrograms implements android.media.tv.TvContract.BaseTvColumns, android.media.tv.TvContract.ProgramColumns, android.media.tv.TvContract.PreviewProgramColumns {
        public static final java.lang.String COLUMN_CHANNEL_ID = "channel_id";
        public static final java.lang.String COLUMN_WEIGHT = "weight";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/preview_program";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/preview_program";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/preview_program");

        private PreviewPrograms() {
        }
    }

    public static final class WatchNextPrograms implements android.media.tv.TvContract.BaseTvColumns, android.media.tv.TvContract.ProgramColumns, android.media.tv.TvContract.PreviewProgramColumns {
        public static final java.lang.String COLUMN_LAST_ENGAGEMENT_TIME_UTC_MILLIS = "last_engagement_time_utc_millis";
        public static final java.lang.String COLUMN_WATCH_NEXT_TYPE = "watch_next_type";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/watch_next_program";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/watch_next_program";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/watch_next_program");
        public static final int WATCH_NEXT_TYPE_CONTINUE = 0;
        public static final int WATCH_NEXT_TYPE_NEW = 2;
        public static final int WATCH_NEXT_TYPE_NEXT = 1;
        public static final int WATCH_NEXT_TYPE_WATCHLIST = 3;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface WatchNextType {
        }

        private WatchNextPrograms() {
        }
    }

    @android.annotation.SystemApi
    public static final class WatchedPrograms implements android.media.tv.TvContract.BaseTvColumns {
        public static final java.lang.String COLUMN_CHANNEL_ID = "channel_id";
        public static final java.lang.String COLUMN_DESCRIPTION = "description";
        public static final java.lang.String COLUMN_END_TIME_UTC_MILLIS = "end_time_utc_millis";
        public static final java.lang.String COLUMN_INTERNAL_SESSION_TOKEN = "session_token";
        public static final java.lang.String COLUMN_INTERNAL_TUNE_PARAMS = "tune_params";
        public static final java.lang.String COLUMN_START_TIME_UTC_MILLIS = "start_time_utc_millis";
        public static final java.lang.String COLUMN_TITLE = "title";
        public static final java.lang.String COLUMN_WATCH_END_TIME_UTC_MILLIS = "watch_end_time_utc_millis";
        public static final java.lang.String COLUMN_WATCH_START_TIME_UTC_MILLIS = "watch_start_time_utc_millis";
        public static final java.lang.String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/watched_program";
        public static final java.lang.String CONTENT_TYPE = "vnd.android.cursor.dir/watched_program";
        public static final android.net.Uri CONTENT_URI = android.net.Uri.parse("content://android.media.tv/watched_program");

        private WatchedPrograms() {
        }
    }
}
