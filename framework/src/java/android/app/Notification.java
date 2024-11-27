package android.app;

/* loaded from: classes.dex */
public class Notification implements android.os.Parcelable {
    public static final android.media.AudioAttributes AUDIO_ATTRIBUTES_DEFAULT;
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final java.lang.String CATEGORY_ALARM = "alarm";
    public static final java.lang.String CATEGORY_CALL = "call";

    @android.annotation.SystemApi
    public static final java.lang.String CATEGORY_CAR_EMERGENCY = "car_emergency";

    @android.annotation.SystemApi
    public static final java.lang.String CATEGORY_CAR_INFORMATION = "car_information";

    @android.annotation.SystemApi
    public static final java.lang.String CATEGORY_CAR_WARNING = "car_warning";
    public static final java.lang.String CATEGORY_EMAIL = "email";
    public static final java.lang.String CATEGORY_ERROR = "err";
    public static final java.lang.String CATEGORY_EVENT = "event";
    public static final java.lang.String CATEGORY_LOCATION_SHARING = "location_sharing";
    public static final java.lang.String CATEGORY_MESSAGE = "msg";
    public static final java.lang.String CATEGORY_MISSED_CALL = "missed_call";
    public static final java.lang.String CATEGORY_NAVIGATION = "navigation";
    public static final java.lang.String CATEGORY_PROGRESS = "progress";
    public static final java.lang.String CATEGORY_PROMO = "promo";
    public static final java.lang.String CATEGORY_RECOMMENDATION = "recommendation";
    public static final java.lang.String CATEGORY_REMINDER = "reminder";
    public static final java.lang.String CATEGORY_SERVICE = "service";
    public static final java.lang.String CATEGORY_SOCIAL = "social";
    public static final java.lang.String CATEGORY_STATUS = "status";
    public static final java.lang.String CATEGORY_STOPWATCH = "stopwatch";
    public static final java.lang.String CATEGORY_SYSTEM = "sys";
    public static final java.lang.String CATEGORY_TRANSPORT = "transport";
    public static final java.lang.String CATEGORY_VOICEMAIL = "voicemail";
    public static final java.lang.String CATEGORY_WORKOUT = "workout";
    public static final int COLOR_DEFAULT = 0;
    public static final int COLOR_INVALID = 1;
    public static final android.os.Parcelable.Creator<android.app.Notification> CREATOR;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_ALLOW_DURING_SETUP = "android.allowDuringSetup";
    public static final java.lang.String EXTRA_ANSWER_COLOR = "android.answerColor";
    public static final java.lang.String EXTRA_ANSWER_INTENT = "android.answerIntent";
    public static final java.lang.String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final java.lang.String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final java.lang.String EXTRA_BIG_TEXT = "android.bigText";
    public static final java.lang.String EXTRA_BUILDER_APPLICATION_INFO = "android.appInfo";
    public static final java.lang.String EXTRA_CALL_IS_VIDEO = "android.callIsVideo";
    public static final java.lang.String EXTRA_CALL_PERSON = "android.callPerson";
    public static final java.lang.String EXTRA_CALL_TYPE = "android.callType";
    public static final java.lang.String EXTRA_CHANNEL_GROUP_ID = "android.intent.extra.CHANNEL_GROUP_ID";
    public static final java.lang.String EXTRA_CHANNEL_ID = "android.intent.extra.CHANNEL_ID";
    public static final java.lang.String EXTRA_CHRONOMETER_COUNT_DOWN = "android.chronometerCountDown";
    public static final java.lang.String EXTRA_COLORIZED = "android.colorized";
    public static final java.lang.String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final java.lang.String EXTRA_CONTAINS_CUSTOM_VIEW = "android.contains.customView";
    public static final java.lang.String EXTRA_CONVERSATION_ICON = "android.conversationIcon";
    public static final java.lang.String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final java.lang.String EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT = "android.conversationUnreadMessageCount";
    public static final java.lang.String EXTRA_DECLINE_COLOR = "android.declineColor";
    public static final java.lang.String EXTRA_DECLINE_INTENT = "android.declineIntent";
    public static final java.lang.String EXTRA_FOREGROUND_APPS = "android.foregroundApps";
    public static final java.lang.String EXTRA_HANG_UP_INTENT = "android.hangUpIntent";
    public static final java.lang.String EXTRA_HIDE_SMART_REPLIES = "android.hideSmartReplies";
    public static final java.lang.String EXTRA_HISTORIC_MESSAGES = "android.messages.historic";
    public static final java.lang.String EXTRA_INFO_TEXT = "android.infoText";
    public static final java.lang.String EXTRA_IS_GROUP_CONVERSATION = "android.isGroupConversation";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final java.lang.String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final java.lang.String EXTRA_MEDIA_REMOTE_DEVICE = "android.mediaRemoteDevice";
    public static final java.lang.String EXTRA_MEDIA_REMOTE_ICON = "android.mediaRemoteIcon";
    public static final java.lang.String EXTRA_MEDIA_REMOTE_INTENT = "android.mediaRemoteIntent";
    public static final java.lang.String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final java.lang.String EXTRA_MESSAGES = "android.messages";
    public static final java.lang.String EXTRA_MESSAGING_PERSON = "android.messagingUser";
    public static final java.lang.String EXTRA_NOTIFICATION_ID = "android.intent.extra.NOTIFICATION_ID";
    public static final java.lang.String EXTRA_NOTIFICATION_TAG = "android.intent.extra.NOTIFICATION_TAG";
    public static final java.lang.String EXTRA_PEOPLE = "android.people";
    public static final java.lang.String EXTRA_PEOPLE_LIST = "android.people.list";
    public static final java.lang.String EXTRA_PICTURE = "android.picture";
    public static final java.lang.String EXTRA_PICTURE_CONTENT_DESCRIPTION = "android.pictureContentDescription";
    public static final java.lang.String EXTRA_PICTURE_ICON = "android.pictureIcon";
    public static final java.lang.String EXTRA_PROGRESS = "android.progress";
    public static final java.lang.String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final java.lang.String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final java.lang.String EXTRA_REDUCED_IMAGES = "android.reduced.images";
    public static final java.lang.String EXTRA_REMOTE_INPUT_DRAFT = "android.remoteInputDraft";
    public static final java.lang.String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final java.lang.String EXTRA_REMOTE_INPUT_HISTORY_ITEMS = "android.remoteInputHistoryItems";
    public static final java.lang.String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final java.lang.String EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED = "android.showBigPictureWhenCollapsed";
    public static final java.lang.String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final java.lang.String EXTRA_SHOW_REMOTE_INPUT_SPINNER = "android.remoteInputSpinner";
    public static final java.lang.String EXTRA_SHOW_WHEN = "android.showWhen";

    @java.lang.Deprecated
    public static final java.lang.String EXTRA_SMALL_ICON = "android.icon";

    @android.annotation.SystemApi
    public static final java.lang.String EXTRA_SUBSTITUTE_APP_NAME = "android.substName";
    public static final java.lang.String EXTRA_SUB_TEXT = "android.subText";
    public static final java.lang.String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final java.lang.String EXTRA_TEMPLATE = "android.template";
    public static final java.lang.String EXTRA_TEXT = "android.text";
    public static final java.lang.String EXTRA_TEXT_LINES = "android.textLines";
    public static final java.lang.String EXTRA_TITLE = "android.title";
    public static final java.lang.String EXTRA_TITLE_BIG = "android.title.big";
    public static final java.lang.String EXTRA_VERIFICATION_ICON = "android.verificationIcon";
    public static final java.lang.String EXTRA_VERIFICATION_TEXT = "android.verificationText";

    @android.annotation.SystemApi
    public static final int FLAG_AUTOGROUP_SUMMARY = 1024;
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_BUBBLE = 4096;
    public static final int FLAG_CAN_COLORIZE = 2048;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_FSI_REQUESTED_BUT_DENIED = 16384;
    public static final int FLAG_GROUP_SUMMARY = 512;

    @java.lang.Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LIFETIME_EXTENDED_BY_DIRECT_REPLY = 65536;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_NO_DISMISS = 8192;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;

    @java.lang.Deprecated
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int FLAG_USER_INITIATED_JOB = 32768;
    public static final int FOREGROUND_SERVICE_DEFAULT = 0;
    public static final int FOREGROUND_SERVICE_DEFERRED = 2;
    public static final int FOREGROUND_SERVICE_IMMEDIATE = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    public static final java.lang.String GROUP_KEY_SILENT = "silent";
    public static final java.lang.String INTENT_CATEGORY_NOTIFICATION_PREFERENCES = "android.intent.category.NOTIFICATION_PREFERENCES";
    public static final int MAX_ACTION_BUTTONS = 3;
    private static final int MAX_CHARSEQUENCE_LENGTH = 1024;
    private static final float MAX_LARGE_ICON_ASPECT_RATIO = 1.7777778f;
    private static final int MAX_REPLY_HISTORY = 5;
    private static final java.util.List<java.lang.Class<? extends android.app.Notification.Style>> PLATFORM_STYLE_CLASSES;

    @java.lang.Deprecated
    public static final int PRIORITY_DEFAULT = 0;

    @java.lang.Deprecated
    public static final int PRIORITY_HIGH = 1;

    @java.lang.Deprecated
    public static final int PRIORITY_LOW = -1;

    @java.lang.Deprecated
    public static final int PRIORITY_MAX = 2;

    @java.lang.Deprecated
    public static final int PRIORITY_MIN = -2;
    private static final android.util.ArraySet<java.lang.Integer> STANDARD_LAYOUTS = new android.util.ArraySet<>();

    @java.lang.Deprecated
    public static final int STREAM_DEFAULT = -1;
    private static final java.lang.String TAG = "Notification";
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;
    static final long WEARABLE_EXTENDER_BACKGROUND_BLOCKED = 270551184;
    public static android.os.IBinder processAllowlistToken;
    public android.app.Notification.Action[] actions;
    public android.util.ArraySet<android.app.PendingIntent> allPendingIntents;

    @java.lang.Deprecated
    public android.media.AudioAttributes audioAttributes;

    @java.lang.Deprecated
    public int audioStreamType;

    @java.lang.Deprecated
    public android.widget.RemoteViews bigContentView;
    public java.lang.String category;
    public int color;
    public android.app.PendingIntent contentIntent;

    @java.lang.Deprecated
    public android.widget.RemoteViews contentView;
    private long creationTime;

    @java.lang.Deprecated
    public int defaults;
    public android.app.PendingIntent deleteIntent;
    public android.os.Bundle extras;
    public int flags;
    public android.app.PendingIntent fullScreenIntent;

    @java.lang.Deprecated
    public android.widget.RemoteViews headsUpContentView;

    @java.lang.Deprecated
    public int icon;
    public int iconLevel;

    @java.lang.Deprecated
    public android.graphics.Bitmap largeIcon;

    @java.lang.Deprecated
    public int ledARGB;

    @java.lang.Deprecated
    public int ledOffMS;

    @java.lang.Deprecated
    public int ledOnMS;
    private boolean mAllowSystemGeneratedContextualActions;
    private android.os.IBinder mAllowlistToken;
    private int mBadgeIcon;
    private android.app.Notification.BubbleMetadata mBubbleMetadata;
    private java.lang.String mChannelId;
    private int mFgsDeferBehavior;
    private int mGroupAlertBehavior;
    private java.lang.String mGroupKey;
    private android.graphics.drawable.Icon mLargeIcon;
    private android.content.LocusId mLocusId;
    private java.lang.CharSequence mSettingsText;
    private java.lang.String mShortcutId;
    private android.graphics.drawable.Icon mSmallIcon;
    private java.lang.String mSortKey;
    private long mTimeout;
    private boolean mUsesStandardHeader;
    public int number;

    @java.lang.Deprecated
    public int priority;
    public android.app.Notification publicVersion;

    @java.lang.Deprecated
    public android.net.Uri sound;
    public java.lang.CharSequence tickerText;

    @java.lang.Deprecated
    public android.widget.RemoteViews tickerView;

    @java.lang.Deprecated
    public long[] vibrate;
    public int visibility;
    public long when;

    public interface Extender {
        android.app.Notification.Builder extend(android.app.Notification.Builder builder);
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface GroupAlertBehavior {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotificationFlags {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface NotificationVisibilityOverride {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Priority {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ServiceNotificationPolicy {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    static {
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_base));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_heads_up_base));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_base));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_picture));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_text));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_inbox));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_messaging));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_messaging));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_conversation));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_media));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_media));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_call));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_material_big_call));
        STANDARD_LAYOUTS.add(java.lang.Integer.valueOf(com.android.internal.R.layout.notification_template_header));
        AUDIO_ATTRIBUTES_DEFAULT = new android.media.AudioAttributes.Builder().setContentType(4).setUsage(5).build();
        PLATFORM_STYLE_CLASSES = java.util.Arrays.asList(android.app.Notification.BigTextStyle.class, android.app.Notification.BigPictureStyle.class, android.app.Notification.InboxStyle.class, android.app.Notification.MediaStyle.class, android.app.Notification.DecoratedCustomViewStyle.class, android.app.Notification.DecoratedMediaCustomViewStyle.class, android.app.Notification.MessagingStyle.class, android.app.Notification.CallStyle.class);
        CREATOR = new android.os.Parcelable.Creator<android.app.Notification>() { // from class: android.app.Notification.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification createFromParcel(android.os.Parcel parcel) {
                return new android.app.Notification(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification[] newArray(int i) {
                return new android.app.Notification[i];
            }
        };
    }

    public java.lang.String getGroup() {
        return this.mGroupKey;
    }

    public java.lang.String getSortKey() {
        return this.mSortKey;
    }

    public static class Action implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.Notification.Action> CREATOR = new android.os.Parcelable.Creator<android.app.Notification.Action>() { // from class: android.app.Notification.Action.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification.Action createFromParcel(android.os.Parcel parcel) {
                return new android.app.Notification.Action(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification.Action[] newArray(int i) {
                return new android.app.Notification.Action[i];
            }
        };
        private static final java.lang.String EXTRA_DATA_ONLY_INPUTS = "android.extra.DATA_ONLY_INPUTS";
        public static final int SEMANTIC_ACTION_ARCHIVE = 5;
        public static final int SEMANTIC_ACTION_CALL = 10;

        @android.annotation.SystemApi
        public static final int SEMANTIC_ACTION_CONVERSATION_IS_PHISHING = 12;
        public static final int SEMANTIC_ACTION_DELETE = 4;
        public static final int SEMANTIC_ACTION_MARK_AS_READ = 2;
        public static final int SEMANTIC_ACTION_MARK_AS_UNREAD = 3;

        @android.annotation.SystemApi
        public static final int SEMANTIC_ACTION_MARK_CONVERSATION_AS_PRIORITY = 11;
        public static final int SEMANTIC_ACTION_MUTE = 6;
        public static final int SEMANTIC_ACTION_NONE = 0;
        public static final int SEMANTIC_ACTION_REPLY = 1;
        public static final int SEMANTIC_ACTION_THUMBS_DOWN = 9;
        public static final int SEMANTIC_ACTION_THUMBS_UP = 8;
        public static final int SEMANTIC_ACTION_UNMUTE = 7;
        public android.app.PendingIntent actionIntent;

        @java.lang.Deprecated
        public int icon;
        private boolean mAllowGeneratedReplies;
        private boolean mAuthenticationRequired;
        private final android.os.Bundle mExtras;
        private android.graphics.drawable.Icon mIcon;
        private final boolean mIsContextual;
        private final android.app.RemoteInput[] mRemoteInputs;
        private final int mSemanticAction;
        public java.lang.CharSequence title;

        public interface Extender {
            android.app.Notification.Action.Builder extend(android.app.Notification.Action.Builder builder);
        }

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface SemanticAction {
        }

        private Action(android.os.Parcel parcel) {
            this.mAllowGeneratedReplies = true;
            if (parcel.readInt() != 0) {
                this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
                if (this.mIcon.getType() == 2) {
                    this.icon = this.mIcon.getResId();
                }
            }
            this.title = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            if (parcel.readInt() == 1) {
                this.actionIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mExtras = android.os.Bundle.setDefusable(parcel.readBundle(), true);
            this.mRemoteInputs = (android.app.RemoteInput[]) parcel.createTypedArray(android.app.RemoteInput.CREATOR);
            this.mAllowGeneratedReplies = parcel.readInt() == 1;
            this.mSemanticAction = parcel.readInt();
            this.mIsContextual = parcel.readInt() == 1;
            this.mAuthenticationRequired = parcel.readInt() == 1;
        }

        @java.lang.Deprecated
        public Action(int i, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
            this(android.graphics.drawable.Icon.createWithResource("", i), charSequence, pendingIntent, new android.os.Bundle(), null, true, 0, false, false);
        }

        private Action(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent, android.os.Bundle bundle, android.app.RemoteInput[] remoteInputArr, boolean z, int i, boolean z2, boolean z3) {
            this.mAllowGeneratedReplies = true;
            this.mIcon = icon;
            if (icon != null && icon.getType() == 2) {
                this.icon = icon.getResId();
            }
            this.title = charSequence;
            this.actionIntent = pendingIntent;
            this.mExtras = bundle == null ? new android.os.Bundle() : bundle;
            this.mRemoteInputs = remoteInputArr;
            this.mAllowGeneratedReplies = z;
            this.mSemanticAction = i;
            this.mIsContextual = z2;
            this.mAuthenticationRequired = z3;
        }

        public android.graphics.drawable.Icon getIcon() {
            if (this.mIcon == null && this.icon != 0) {
                this.mIcon = android.graphics.drawable.Icon.createWithResource("", this.icon);
            }
            return this.mIcon;
        }

        public android.os.Bundle getExtras() {
            return this.mExtras;
        }

        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public android.app.RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        public int getSemanticAction() {
            return this.mSemanticAction;
        }

        public boolean isContextual() {
            return this.mIsContextual;
        }

        public android.app.RemoteInput[] getDataOnlyRemoteInputs() {
            return (android.app.RemoteInput[]) android.app.Notification.getParcelableArrayFromBundle(this.mExtras, EXTRA_DATA_ONLY_INPUTS, android.app.RemoteInput.class);
        }

        public boolean isAuthenticationRequired() {
            return this.mAuthenticationRequired;
        }

        public static final class Builder {
            private boolean mAllowGeneratedReplies;
            private boolean mAuthenticationRequired;
            private final android.os.Bundle mExtras;
            private final android.graphics.drawable.Icon mIcon;
            private final android.app.PendingIntent mIntent;
            private boolean mIsContextual;
            private java.util.ArrayList<android.app.RemoteInput> mRemoteInputs;
            private int mSemanticAction;
            private final java.lang.CharSequence mTitle;

            @java.lang.Deprecated
            public Builder(int i, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
                this(android.graphics.drawable.Icon.createWithResource("", i), charSequence, pendingIntent);
            }

            public Builder(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
                this(icon, charSequence, pendingIntent, new android.os.Bundle(), null, true, 0, false);
            }

            public Builder(android.app.Notification.Action action) {
                this(action.getIcon(), action.title, action.actionIntent, new android.os.Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies(), action.getSemanticAction(), action.isAuthenticationRequired());
            }

            private Builder(android.graphics.drawable.Icon icon, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent, android.os.Bundle bundle, android.app.RemoteInput[] remoteInputArr, boolean z, int i, boolean z2) {
                this.mAllowGeneratedReplies = true;
                this.mIcon = icon;
                this.mTitle = charSequence;
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                if (remoteInputArr != null) {
                    this.mRemoteInputs = new java.util.ArrayList<>(remoteInputArr.length);
                    java.util.Collections.addAll(this.mRemoteInputs, remoteInputArr);
                }
                this.mAllowGeneratedReplies = z;
                this.mSemanticAction = i;
                this.mAuthenticationRequired = z2;
            }

            public android.app.Notification.Action.Builder addExtras(android.os.Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            public android.os.Bundle getExtras() {
                return this.mExtras;
            }

            public android.app.Notification.Action.Builder addRemoteInput(android.app.RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new java.util.ArrayList<>();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            public android.app.Notification.Action.Builder setAllowGeneratedReplies(boolean z) {
                this.mAllowGeneratedReplies = z;
                return this;
            }

            public android.app.Notification.Action.Builder setSemanticAction(int i) {
                this.mSemanticAction = i;
                return this;
            }

            public android.app.Notification.Action.Builder setContextual(boolean z) {
                this.mIsContextual = z;
                return this;
            }

            public android.app.Notification.Action.Builder extend(android.app.Notification.Action.Extender extender) {
                extender.extend(this);
                return this;
            }

            public android.app.Notification.Action.Builder setAuthenticationRequired(boolean z) {
                this.mAuthenticationRequired = z;
                return this;
            }

            private void checkContextualActionNullFields() {
                if (this.mIsContextual) {
                    if (this.mIcon == null) {
                        throw new java.lang.NullPointerException("Contextual Actions must contain a valid icon");
                    }
                    if (this.mIntent == null) {
                        throw new java.lang.NullPointerException("Contextual Actions must contain a valid PendingIntent");
                    }
                }
            }

            public android.app.Notification.Action build() {
                checkContextualActionNullFields();
                java.util.ArrayList arrayList = new java.util.ArrayList();
                android.app.RemoteInput[] remoteInputArr = (android.app.RemoteInput[]) android.app.Notification.getParcelableArrayFromBundle(this.mExtras, android.app.Notification.Action.EXTRA_DATA_ONLY_INPUTS, android.app.RemoteInput.class);
                if (remoteInputArr != null) {
                    for (android.app.RemoteInput remoteInput : remoteInputArr) {
                        arrayList.add(remoteInput);
                    }
                }
                java.util.ArrayList arrayList2 = new java.util.ArrayList();
                if (this.mRemoteInputs != null) {
                    java.util.Iterator<android.app.RemoteInput> it = this.mRemoteInputs.iterator();
                    while (it.hasNext()) {
                        android.app.RemoteInput next = it.next();
                        if (next.isDataOnly()) {
                            arrayList.add(next);
                        } else {
                            arrayList2.add(next);
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    this.mExtras.putParcelableArray(android.app.Notification.Action.EXTRA_DATA_ONLY_INPUTS, (android.app.RemoteInput[]) arrayList.toArray(new android.app.RemoteInput[arrayList.size()]));
                }
                return new android.app.Notification.Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrayList2.isEmpty() ? null : (android.app.RemoteInput[]) arrayList2.toArray(new android.app.RemoteInput[arrayList2.size()]), this.mAllowGeneratedReplies, this.mSemanticAction, this.mIsContextual, this.mAuthenticationRequired);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            android.app.Notification.visitIconUri(consumer, getIcon());
            if (this.actionIntent != null) {
                this.actionIntent.visitUris(consumer);
            }
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.app.Notification.Action m396clone() {
            return new android.app.Notification.Action(getIcon(), this.title, this.actionIntent, this.mExtras == null ? new android.os.Bundle() : new android.os.Bundle(this.mExtras), getRemoteInputs(), getAllowGeneratedReplies(), getSemanticAction(), isContextual(), isAuthenticationRequired());
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            android.graphics.drawable.Icon icon = getIcon();
            if (icon != null) {
                parcel.writeInt(1);
                icon.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
            android.text.TextUtils.writeToParcel(this.title, parcel, i);
            if (this.actionIntent != null) {
                parcel.writeInt(1);
                this.actionIntent.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
            parcel.writeBundle(this.mExtras);
            parcel.writeTypedArray(this.mRemoteInputs, i);
            parcel.writeInt(this.mAllowGeneratedReplies ? 1 : 0);
            parcel.writeInt(this.mSemanticAction);
            parcel.writeInt(this.mIsContextual ? 1 : 0);
            parcel.writeInt(this.mAuthenticationRequired ? 1 : 0);
        }

        public static final class WearableExtender implements android.app.Notification.Action.Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final java.lang.String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final java.lang.String KEY_CANCEL_LABEL = "cancelLabel";
            private static final java.lang.String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final java.lang.String KEY_FLAGS = "flags";
            private static final java.lang.String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private java.lang.CharSequence mCancelLabel;
            private java.lang.CharSequence mConfirmLabel;
            private int mFlags;
            private java.lang.CharSequence mInProgressLabel;

            public WearableExtender() {
                this.mFlags = 1;
            }

            public WearableExtender(android.app.Notification.Action action) {
                this.mFlags = 1;
                android.os.Bundle bundle = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (bundle != null) {
                    this.mFlags = bundle.getInt("flags", 1);
                    this.mInProgressLabel = bundle.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = bundle.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = bundle.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            @Override // android.app.Notification.Action.Extender
            public android.app.Notification.Action.Builder extend(android.app.Notification.Action.Builder builder) {
                android.os.Bundle bundle = new android.os.Bundle();
                if (this.mFlags != 1) {
                    bundle.putInt("flags", this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            /* renamed from: clone, reason: merged with bridge method [inline-methods] */
            public android.app.Notification.Action.WearableExtender m397clone() {
                android.app.Notification.Action.WearableExtender wearableExtender = new android.app.Notification.Action.WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            public android.app.Notification.Action.WearableExtender setAvailableOffline(boolean z) {
                setFlag(1, z);
                return this;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            private void setFlag(int i, boolean z) {
                if (z) {
                    this.mFlags = i | this.mFlags;
                } else {
                    this.mFlags = (~i) & this.mFlags;
                }
            }

            @java.lang.Deprecated
            public android.app.Notification.Action.WearableExtender setInProgressLabel(java.lang.CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }

            @java.lang.Deprecated
            public java.lang.CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            @java.lang.Deprecated
            public android.app.Notification.Action.WearableExtender setConfirmLabel(java.lang.CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            @java.lang.Deprecated
            public java.lang.CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            @java.lang.Deprecated
            public android.app.Notification.Action.WearableExtender setCancelLabel(java.lang.CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            @java.lang.Deprecated
            public java.lang.CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public android.app.Notification.Action.WearableExtender setHintLaunchesActivity(boolean z) {
                setFlag(2, z);
                return this;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public android.app.Notification.Action.WearableExtender setHintDisplayActionInline(boolean z) {
                setFlag(4, z);
                return this;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }
        }
    }

    public Notification() {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new android.os.Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        this.when = java.lang.System.currentTimeMillis();
        this.creationTime = java.lang.System.currentTimeMillis();
        this.priority = 0;
    }

    public Notification(android.content.Context context, int i, java.lang.CharSequence charSequence, long j, java.lang.CharSequence charSequence2, java.lang.CharSequence charSequence3, android.content.Intent intent) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new android.os.Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        new android.app.Notification.Builder(context).setWhen(j).setSmallIcon(i).setTicker(charSequence).setContentTitle(charSequence2).setContentText(charSequence3).setContentIntent(android.app.PendingIntent.getActivity(context, 0, intent, 33554432)).buildInto(this);
    }

    @java.lang.Deprecated
    public Notification(int i, java.lang.CharSequence charSequence, long j) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new android.os.Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        this.icon = i;
        this.tickerText = charSequence;
        this.when = j;
        this.creationTime = java.lang.System.currentTimeMillis();
    }

    public Notification(android.os.Parcel parcel) {
        this.number = 0;
        this.audioStreamType = -1;
        this.audioAttributes = AUDIO_ATTRIBUTES_DEFAULT;
        this.color = 0;
        this.extras = new android.os.Bundle();
        this.mGroupAlertBehavior = 0;
        this.mBadgeIcon = 0;
        this.mAllowSystemGeneratedContextualActions = true;
        readFromParcelImpl(parcel);
        this.allPendingIntents = parcel.readArraySet(null);
    }

    private void readFromParcelImpl(android.os.Parcel parcel) {
        parcel.readInt();
        this.mAllowlistToken = parcel.readStrongBinder();
        if (this.mAllowlistToken == null) {
            this.mAllowlistToken = processAllowlistToken;
        }
        parcel.setClassCookie(android.app.PendingIntent.class, this.mAllowlistToken);
        this.when = parcel.readLong();
        this.creationTime = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mSmallIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
            if (this.mSmallIcon.getType() == 2) {
                this.icon = this.mSmallIcon.getResId();
            }
        }
        this.number = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.contentIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.deleteIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.tickerView = android.widget.RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.contentView = android.widget.RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.mLargeIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
        }
        this.defaults = parcel.readInt();
        this.flags = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.sound = android.net.Uri.CREATOR.createFromParcel(parcel);
        }
        this.audioStreamType = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.audioAttributes = android.media.AudioAttributes.CREATOR.createFromParcel(parcel);
        }
        this.vibrate = parcel.createLongArray();
        this.ledARGB = parcel.readInt();
        this.ledOnMS = parcel.readInt();
        this.ledOffMS = parcel.readInt();
        this.iconLevel = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.fullScreenIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
        }
        this.priority = parcel.readInt();
        this.category = parcel.readString8();
        this.mGroupKey = parcel.readString8();
        this.mSortKey = parcel.readString8();
        this.extras = android.os.Bundle.setDefusable(parcel.readBundle(), true);
        fixDuplicateExtras();
        this.actions = (android.app.Notification.Action[]) parcel.createTypedArray(android.app.Notification.Action.CREATOR);
        if (parcel.readInt() != 0) {
            this.bigContentView = android.widget.RemoteViews.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.headsUpContentView = android.widget.RemoteViews.CREATOR.createFromParcel(parcel);
        }
        this.visibility = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.publicVersion = CREATOR.createFromParcel(parcel);
        }
        this.color = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mChannelId = parcel.readString8();
        }
        this.mTimeout = parcel.readLong();
        if (parcel.readInt() != 0) {
            this.mShortcutId = parcel.readString8();
        }
        if (parcel.readInt() != 0) {
            this.mLocusId = android.content.LocusId.CREATOR.createFromParcel(parcel);
        }
        this.mBadgeIcon = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mSettingsText = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        this.mGroupAlertBehavior = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mBubbleMetadata = android.app.Notification.BubbleMetadata.CREATOR.createFromParcel(parcel);
        }
        this.mAllowSystemGeneratedContextualActions = parcel.readBoolean();
        this.mFgsDeferBehavior = parcel.readInt();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.app.Notification m392clone() {
        android.app.Notification notification = new android.app.Notification();
        cloneInto(notification, true);
        return notification;
    }

    public void cloneInto(android.app.Notification notification, boolean z) {
        notification.mAllowlistToken = this.mAllowlistToken;
        notification.when = this.when;
        notification.creationTime = this.creationTime;
        notification.mSmallIcon = this.mSmallIcon;
        notification.number = this.number;
        notification.contentIntent = this.contentIntent;
        notification.deleteIntent = this.deleteIntent;
        notification.fullScreenIntent = this.fullScreenIntent;
        if (this.tickerText != null) {
            notification.tickerText = this.tickerText.toString();
        }
        if (z && this.tickerView != null) {
            notification.tickerView = this.tickerView.mo424clone();
        }
        if (z && this.contentView != null) {
            notification.contentView = this.contentView.mo424clone();
        }
        if (z && this.mLargeIcon != null) {
            notification.mLargeIcon = this.mLargeIcon;
        }
        notification.iconLevel = this.iconLevel;
        notification.sound = this.sound;
        notification.audioStreamType = this.audioStreamType;
        if (this.audioAttributes != null) {
            notification.audioAttributes = new android.media.AudioAttributes.Builder(this.audioAttributes).build();
        }
        long[] jArr = this.vibrate;
        if (jArr != null) {
            int length = jArr.length;
            long[] jArr2 = new long[length];
            notification.vibrate = jArr2;
            java.lang.System.arraycopy(jArr, 0, jArr2, 0, length);
        }
        notification.ledARGB = this.ledARGB;
        notification.ledOnMS = this.ledOnMS;
        notification.ledOffMS = this.ledOffMS;
        notification.defaults = this.defaults;
        notification.flags = this.flags;
        notification.priority = this.priority;
        notification.category = this.category;
        notification.mGroupKey = this.mGroupKey;
        notification.mSortKey = this.mSortKey;
        if (this.extras != null) {
            try {
                notification.extras = new android.os.Bundle(this.extras);
                notification.extras.size();
            } catch (android.os.BadParcelableException e) {
                android.util.Log.e(TAG, "could not unparcel extras from notification: " + this, e);
                notification.extras = null;
            }
        }
        if (!com.android.internal.util.ArrayUtils.isEmpty(this.allPendingIntents)) {
            notification.allPendingIntents = new android.util.ArraySet<>((android.util.ArraySet) this.allPendingIntents);
        }
        if (this.actions != null) {
            notification.actions = new android.app.Notification.Action[this.actions.length];
            for (int i = 0; i < this.actions.length; i++) {
                if (this.actions[i] != null) {
                    notification.actions[i] = this.actions[i].m396clone();
                }
            }
        }
        if (z && this.bigContentView != null) {
            notification.bigContentView = this.bigContentView.mo424clone();
        }
        if (z && this.headsUpContentView != null) {
            notification.headsUpContentView = this.headsUpContentView.mo424clone();
        }
        notification.visibility = this.visibility;
        if (this.publicVersion != null) {
            notification.publicVersion = new android.app.Notification();
            this.publicVersion.cloneInto(notification.publicVersion, z);
        }
        notification.color = this.color;
        notification.mChannelId = this.mChannelId;
        notification.mTimeout = this.mTimeout;
        notification.mShortcutId = this.mShortcutId;
        notification.mLocusId = this.mLocusId;
        notification.mBadgeIcon = this.mBadgeIcon;
        notification.mSettingsText = this.mSettingsText;
        notification.mGroupAlertBehavior = this.mGroupAlertBehavior;
        notification.mFgsDeferBehavior = this.mFgsDeferBehavior;
        notification.mBubbleMetadata = this.mBubbleMetadata;
        notification.mAllowSystemGeneratedContextualActions = this.mAllowSystemGeneratedContextualActions;
        if (!z) {
            notification.lightenPayload();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void visitIconUri(java.util.function.Consumer<android.net.Uri> consumer, android.graphics.drawable.Icon icon) {
        if (icon == null) {
            return;
        }
        int type = icon.getType();
        if (type == 4 || type == 6) {
            consumer.accept(icon.getUri());
        }
    }

    public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
        if (this.publicVersion != null) {
            this.publicVersion.visitUris(consumer);
        }
        consumer.accept(this.sound);
        if (this.tickerView != null) {
            this.tickerView.visitUris(consumer);
        }
        if (this.contentView != null) {
            this.contentView.visitUris(consumer);
        }
        if (this.bigContentView != null) {
            this.bigContentView.visitUris(consumer);
        }
        if (this.headsUpContentView != null) {
            this.headsUpContentView.visitUris(consumer);
        }
        visitIconUri(consumer, this.mSmallIcon);
        visitIconUri(consumer, this.mLargeIcon);
        if (this.actions != null) {
            for (android.app.Notification.Action action : this.actions) {
                action.visitUris(consumer);
            }
        }
        for (android.app.PendingIntent pendingIntent : java.util.Arrays.asList(this.contentIntent, this.deleteIntent, this.fullScreenIntent)) {
            if (pendingIntent != null) {
                pendingIntent.visitUris(consumer);
            }
        }
        if (this.mBubbleMetadata != null) {
            this.mBubbleMetadata.visitUris(consumer);
        }
        if (this.extras != null) {
            visitIconUri(consumer, (android.graphics.drawable.Icon) this.extras.getParcelable(EXTRA_LARGE_ICON_BIG, android.graphics.drawable.Icon.class));
            visitIconUri(consumer, (android.graphics.drawable.Icon) this.extras.getParcelable(EXTRA_PICTURE_ICON, android.graphics.drawable.Icon.class));
            java.lang.Object obj = this.extras.get(EXTRA_AUDIO_CONTENTS_URI);
            if (obj instanceof android.net.Uri) {
                consumer.accept((android.net.Uri) obj);
            } else if (obj instanceof java.lang.String) {
                consumer.accept(android.net.Uri.parse((java.lang.String) obj));
            }
            if (this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI)) {
                consumer.accept(android.net.Uri.parse(this.extras.getString(EXTRA_BACKGROUND_IMAGE_URI)));
            }
            java.util.ArrayList parcelableArrayList = this.extras.getParcelableArrayList(EXTRA_PEOPLE_LIST, android.app.Person.class);
            if (parcelableArrayList != null && !parcelableArrayList.isEmpty()) {
                java.util.Iterator it = parcelableArrayList.iterator();
                while (it.hasNext()) {
                    ((android.app.Person) it.next()).visitUris(consumer);
                }
            }
            android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr = (android.app.RemoteInputHistoryItem[]) this.extras.getParcelableArray(EXTRA_REMOTE_INPUT_HISTORY_ITEMS, android.app.RemoteInputHistoryItem.class);
            if (remoteInputHistoryItemArr != null) {
                for (android.app.RemoteInputHistoryItem remoteInputHistoryItem : remoteInputHistoryItemArr) {
                    if (remoteInputHistoryItem.getUri() != null) {
                        consumer.accept(remoteInputHistoryItem.getUri());
                    }
                }
            }
            android.app.Person person = (android.app.Person) this.extras.getParcelable(EXTRA_MESSAGING_PERSON, android.app.Person.class);
            if (person != null) {
                person.visitUris(consumer);
            }
            android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) this.extras.getParcelableArray(EXTRA_MESSAGES, android.os.Parcelable.class);
            if (!com.android.internal.util.ArrayUtils.isEmpty(parcelableArr)) {
                java.util.Iterator<android.app.Notification.MessagingStyle.Message> it2 = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr).iterator();
                while (it2.hasNext()) {
                    it2.next().visitUris(consumer);
                }
            }
            android.os.Parcelable[] parcelableArr2 = (android.os.Parcelable[]) this.extras.getParcelableArray(EXTRA_HISTORIC_MESSAGES, android.os.Parcelable.class);
            if (!com.android.internal.util.ArrayUtils.isEmpty(parcelableArr2)) {
                java.util.Iterator<android.app.Notification.MessagingStyle.Message> it3 = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr2).iterator();
                while (it3.hasNext()) {
                    it3.next().visitUris(consumer);
                }
            }
            visitIconUri(consumer, (android.graphics.drawable.Icon) this.extras.getParcelable(EXTRA_CONVERSATION_ICON, android.graphics.drawable.Icon.class));
            android.app.Person person2 = (android.app.Person) this.extras.getParcelable(EXTRA_CALL_PERSON, android.app.Person.class);
            if (person2 != null) {
                person2.visitUris(consumer);
            }
            visitIconUri(consumer, (android.graphics.drawable.Icon) this.extras.getParcelable(EXTRA_VERIFICATION_ICON, android.graphics.drawable.Icon.class));
            android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) this.extras.getParcelable(EXTRA_MEDIA_REMOTE_INTENT, android.app.PendingIntent.class);
            if (pendingIntent2 != null) {
                pendingIntent2.visitUris(consumer);
            }
            if (this.extras.containsKey("android.wearable.EXTENSIONS")) {
                new android.app.Notification.WearableExtender(this).visitUris(consumer);
            }
            if (this.extras.containsKey("android.tv.EXTENSIONS")) {
                new android.app.Notification.TvExtender(this).visitUris(consumer);
            }
            if (this.extras.containsKey("android.car.EXTENSIONS")) {
                new android.app.Notification.CarExtender(this).visitUris(consumer);
            }
        }
    }

    public java.lang.String loadHeaderAppName(android.content.Context context) {
        java.lang.CharSequence charSequence;
        android.content.pm.ApplicationInfo applicationInfo;
        android.os.Trace.beginSection("Notification#loadHeaderAppName");
        try {
            if (this.extras.containsKey(EXTRA_SUBSTITUTE_APP_NAME)) {
                charSequence = this.extras.getString(EXTRA_SUBSTITUTE_APP_NAME);
                if (!android.text.TextUtils.isEmpty(charSequence)) {
                    return charSequence.toString();
                }
            } else {
                charSequence = null;
            }
            if (context == null) {
                return null;
            }
            android.content.pm.PackageManager packageManager = context.getPackageManager();
            if (android.text.TextUtils.isEmpty(charSequence) && this.extras.containsKey(EXTRA_BUILDER_APPLICATION_INFO) && (applicationInfo = (android.content.pm.ApplicationInfo) this.extras.getParcelable(EXTRA_BUILDER_APPLICATION_INFO, android.content.pm.ApplicationInfo.class)) != null) {
                charSequence = packageManager.getApplicationLabel(applicationInfo);
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                charSequence = packageManager.getApplicationLabel(context.getApplicationInfo());
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                return null;
            }
            return charSequence.toString();
        } finally {
            android.os.Trace.endSection();
        }
    }

    public final void lightenPayload() {
        java.lang.Object obj;
        this.tickerView = null;
        this.contentView = null;
        this.bigContentView = null;
        this.headsUpContentView = null;
        this.mLargeIcon = null;
        if (this.extras != null && !this.extras.isEmpty()) {
            java.util.Set<java.lang.String> keySet = this.extras.keySet();
            int size = keySet.size();
            java.lang.String[] strArr = (java.lang.String[]) keySet.toArray(new java.lang.String[size]);
            for (int i = 0; i < size; i++) {
                java.lang.String str = strArr[i];
                if (!"android.tv.EXTENSIONS".equals(str) && (obj = this.extras.get(str)) != null && ((obj instanceof android.os.Parcelable) || (obj instanceof android.os.Parcelable[]) || (obj instanceof android.util.SparseArray) || (obj instanceof java.util.ArrayList))) {
                    this.extras.remove(str);
                }
            }
        }
    }

    public static java.lang.CharSequence safeCharSequence(java.lang.CharSequence charSequence) {
        if (charSequence == null) {
            return charSequence;
        }
        if (charSequence.length() > 1024) {
            charSequence = charSequence.subSequence(0, 1024);
        }
        if (charSequence instanceof android.os.Parcelable) {
            android.util.Log.e(TAG, "warning: " + charSequence.getClass().getCanonicalName() + " instance is a custom Parcelable and not allowed in Notification");
            return charSequence.toString();
        }
        return removeTextSizeSpans(charSequence);
    }

    private static java.lang.CharSequence removeTextSizeSpans(java.lang.CharSequence charSequence) {
        java.lang.Object obj;
        java.lang.Object obj2;
        if (charSequence instanceof android.text.Spanned) {
            android.text.Spanned spanned = (android.text.Spanned) charSequence;
            java.lang.Object[] spans = spanned.getSpans(0, spanned.length(), java.lang.Object.class);
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder(spanned.toString());
            for (java.lang.Object obj3 : spans) {
                if (!(obj3 instanceof android.text.style.CharacterStyle)) {
                    obj = obj3;
                } else {
                    obj = ((android.text.style.CharacterStyle) obj3).getUnderlying();
                }
                if (!(obj instanceof android.text.style.TextAppearanceSpan)) {
                    if (!(obj instanceof android.text.style.RelativeSizeSpan) && !(obj instanceof android.text.style.AbsoluteSizeSpan)) {
                        obj2 = obj3;
                    }
                } else {
                    android.text.style.TextAppearanceSpan textAppearanceSpan = (android.text.style.TextAppearanceSpan) obj;
                    obj2 = new android.text.style.TextAppearanceSpan(textAppearanceSpan.getFamily(), textAppearanceSpan.getTextStyle(), -1, textAppearanceSpan.getTextColor(), textAppearanceSpan.getLinkTextColor());
                }
                spannableStringBuilder.setSpan(obj2, spanned.getSpanStart(obj3), spanned.getSpanEnd(obj3), spanned.getSpanFlags(obj3));
            }
            return spannableStringBuilder;
        }
        return charSequence;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(final android.os.Parcel parcel, int i) {
        android.app.PendingIntent.OnMarshaledListener onMarshaledListener;
        if (this.allPendingIntents != null) {
            onMarshaledListener = null;
        } else {
            onMarshaledListener = new android.app.PendingIntent.OnMarshaledListener() { // from class: android.app.Notification$$ExternalSyntheticLambda0
                @Override // android.app.PendingIntent.OnMarshaledListener
                public final void onMarshaled(android.app.PendingIntent pendingIntent, android.os.Parcel parcel2, int i2) {
                    android.app.Notification.this.lambda$writeToParcel$0(parcel, pendingIntent, parcel2, i2);
                }
            };
            android.app.PendingIntent.addOnMarshaledListener(onMarshaledListener);
        }
        try {
            writeToParcelImpl(parcel, i);
            synchronized (this) {
                parcel.writeArraySet(this.allPendingIntents);
            }
        } finally {
            if (onMarshaledListener != null) {
                android.app.PendingIntent.removeOnMarshaledListener(onMarshaledListener);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$writeToParcel$0(android.os.Parcel parcel, android.app.PendingIntent pendingIntent, android.os.Parcel parcel2, int i) {
        if (parcel == parcel2) {
            synchronized (this) {
                if (this.allPendingIntents == null) {
                    this.allPendingIntents = new android.util.ArraySet<>();
                }
                this.allPendingIntents.add(pendingIntent);
            }
        }
    }

    private void writeToParcelImpl(android.os.Parcel parcel, int i) {
        parcel.writeInt(1);
        parcel.writeStrongBinder(this.mAllowlistToken);
        parcel.writeLong(this.when);
        parcel.writeLong(this.creationTime);
        if (this.mSmallIcon == null && this.icon != 0) {
            this.mSmallIcon = android.graphics.drawable.Icon.createWithResource("", this.icon);
        }
        if (this.mSmallIcon != null) {
            parcel.writeInt(1);
            this.mSmallIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.number);
        if (this.contentIntent != null) {
            parcel.writeInt(1);
            this.contentIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.deleteIntent != null) {
            parcel.writeInt(1);
            this.deleteIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerText != null) {
            parcel.writeInt(1);
            android.text.TextUtils.writeToParcel(this.tickerText, parcel, i);
        } else {
            parcel.writeInt(0);
        }
        if (this.tickerView != null) {
            parcel.writeInt(1);
            this.tickerView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.contentView != null) {
            parcel.writeInt(1);
            this.contentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLargeIcon == null && this.largeIcon != null) {
            this.mLargeIcon = android.graphics.drawable.Icon.createWithBitmap(this.largeIcon);
        }
        if (this.mLargeIcon != null) {
            parcel.writeInt(1);
            this.mLargeIcon.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.defaults);
        parcel.writeInt(this.flags);
        if (this.sound != null) {
            parcel.writeInt(1);
            this.sound.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.audioStreamType);
        if (this.audioAttributes != null) {
            parcel.writeInt(1);
            this.audioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLongArray(this.vibrate);
        parcel.writeInt(this.ledARGB);
        parcel.writeInt(this.ledOnMS);
        parcel.writeInt(this.ledOffMS);
        parcel.writeInt(this.iconLevel);
        if (this.fullScreenIntent != null) {
            parcel.writeInt(1);
            this.fullScreenIntent.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.priority);
        parcel.writeString8(this.category);
        parcel.writeString8(this.mGroupKey);
        parcel.writeString8(this.mSortKey);
        parcel.writeBundle(this.extras);
        parcel.writeTypedArray(this.actions, 0);
        if (this.bigContentView != null) {
            parcel.writeInt(1);
            this.bigContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.headsUpContentView != null) {
            parcel.writeInt(1);
            this.headsUpContentView.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.visibility);
        if (this.publicVersion != null) {
            parcel.writeInt(1);
            this.publicVersion.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.color);
        if (this.mChannelId != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mChannelId);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeLong(this.mTimeout);
        if (this.mShortcutId != null) {
            parcel.writeInt(1);
            parcel.writeString8(this.mShortcutId);
        } else {
            parcel.writeInt(0);
        }
        if (this.mLocusId != null) {
            parcel.writeInt(1);
            this.mLocusId.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mBadgeIcon);
        if (this.mSettingsText != null) {
            parcel.writeInt(1);
            android.text.TextUtils.writeToParcel(this.mSettingsText, parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mGroupAlertBehavior);
        if (this.mBubbleMetadata != null) {
            parcel.writeInt(1);
            this.mBubbleMetadata.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mAllowSystemGeneratedContextualActions);
        parcel.writeInt(this.mFgsDeferBehavior);
    }

    public static boolean areActionsVisiblyDifferent(android.app.Notification notification, android.app.Notification notification2) {
        android.app.Notification.Action[] actionArr = notification.actions;
        android.app.Notification.Action[] actionArr2 = notification2.actions;
        if ((actionArr == null && actionArr2 != null) || (actionArr != null && actionArr2 == null)) {
            return true;
        }
        if (actionArr != null && actionArr2 != null) {
            if (actionArr.length != actionArr2.length) {
                return true;
            }
            for (int i = 0; i < actionArr.length; i++) {
                if (!java.util.Objects.equals(java.lang.String.valueOf(actionArr[i].title), java.lang.String.valueOf(actionArr2[i].title))) {
                    return true;
                }
                android.app.RemoteInput[] remoteInputs = actionArr[i].getRemoteInputs();
                android.app.RemoteInput[] remoteInputs2 = actionArr2[i].getRemoteInputs();
                if (remoteInputs == null) {
                    remoteInputs = new android.app.RemoteInput[0];
                }
                if (remoteInputs2 == null) {
                    remoteInputs2 = new android.app.RemoteInput[0];
                }
                if (remoteInputs.length != remoteInputs2.length) {
                    return true;
                }
                for (int i2 = 0; i2 < remoteInputs.length; i2++) {
                    if (!java.util.Objects.equals(java.lang.String.valueOf(remoteInputs[i2].getLabel()), java.lang.String.valueOf(remoteInputs2[i2].getLabel()))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean areIconsDifferent(android.app.Notification notification, android.app.Notification notification2) {
        return areIconsMaybeDifferent(notification.getSmallIcon(), notification2.getSmallIcon()) || areIconsMaybeDifferent(notification.getLargeIcon(), notification2.getLargeIcon());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean areIconsMaybeDifferent(android.graphics.drawable.Icon icon, android.graphics.drawable.Icon icon2) {
        if (icon == icon2) {
            return false;
        }
        if (icon == null || icon2 == null) {
            return true;
        }
        if (icon.sameAs(icon2)) {
            return false;
        }
        int type = icon.getType();
        if (type != icon2.getType()) {
            return true;
        }
        if (type != 1 && type != 5) {
            return true;
        }
        android.graphics.Bitmap bitmap = icon.getBitmap();
        android.graphics.Bitmap bitmap2 = icon2.getBitmap();
        if (bitmap.getWidth() == bitmap2.getWidth() && bitmap.getHeight() == bitmap2.getHeight() && bitmap.getConfig() == bitmap2.getConfig() && bitmap.getGenerationId() == bitmap2.getGenerationId()) {
            return false;
        }
        return true;
    }

    public static boolean areStyledNotificationsVisiblyDifferent(android.app.Notification.Builder builder, android.app.Notification.Builder builder2) {
        if (builder.getStyle() == null) {
            return builder2.getStyle() != null;
        }
        if (builder2.getStyle() == null) {
            return true;
        }
        return builder.getStyle().areNotificationsVisiblyDifferent(builder2.getStyle());
    }

    public static boolean areRemoteViewsChanged(android.app.Notification.Builder builder, android.app.Notification.Builder builder2) {
        return !java.util.Objects.equals(java.lang.Boolean.valueOf(builder.usesStandardHeader()), java.lang.Boolean.valueOf(builder2.usesStandardHeader())) || areRemoteViewsChanged(builder.mN.contentView, builder2.mN.contentView) || areRemoteViewsChanged(builder.mN.bigContentView, builder2.mN.bigContentView) || areRemoteViewsChanged(builder.mN.headsUpContentView, builder2.mN.headsUpContentView);
    }

    private static boolean areRemoteViewsChanged(android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2) {
        if (remoteViews == null && remoteViews2 == null) {
            return false;
        }
        if ((remoteViews != null || remoteViews2 == null) && ((remoteViews == null || remoteViews2 != null) && java.util.Objects.equals(java.lang.Integer.valueOf(remoteViews.getLayoutId()), java.lang.Integer.valueOf(remoteViews2.getLayoutId())) && java.util.Objects.equals(java.lang.Integer.valueOf(remoteViews.getSequenceNumber()), java.lang.Integer.valueOf(remoteViews2.getSequenceNumber())))) {
            return false;
        }
        return true;
    }

    private void fixDuplicateExtras() {
        if (this.extras != null) {
            fixDuplicateExtra(this.mLargeIcon, EXTRA_LARGE_ICON);
        }
    }

    private void fixDuplicateExtra(android.os.Parcelable parcelable, java.lang.String str) {
        if (parcelable != null && this.extras.getParcelable(str, android.os.Parcelable.class) != null) {
            this.extras.putParcelable(str, parcelable);
        }
    }

    @java.lang.Deprecated
    public void setLatestEventInfo(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, android.app.PendingIntent pendingIntent) {
        if (context.getApplicationInfo().targetSdkVersion > 22) {
            android.util.Log.e(TAG, "setLatestEventInfo() is deprecated and you should feel deprecated.", new java.lang.Throwable());
        }
        if (context.getApplicationInfo().targetSdkVersion < 24) {
            this.extras.putBoolean(EXTRA_SHOW_WHEN, true);
        }
        android.app.Notification.Builder builder = new android.app.Notification.Builder(context, this);
        if (charSequence != null) {
            builder.setContentTitle(charSequence);
        }
        if (charSequence2 != null) {
            builder.setContentText(charSequence2);
        }
        builder.setContentIntent(pendingIntent);
        builder.build();
    }

    public void overrideAllowlistToken(android.os.IBinder iBinder) {
        this.mAllowlistToken = iBinder;
        if (this.publicVersion != null) {
            this.publicVersion.overrideAllowlistToken(iBinder);
        }
    }

    public static void addFieldsFromContext(android.content.Context context, android.app.Notification notification) {
        addFieldsFromContext(context.getApplicationInfo(), notification);
    }

    public static void addFieldsFromContext(android.content.pm.ApplicationInfo applicationInfo, android.app.Notification notification) {
        notification.extras.putParcelable(EXTRA_BUILDER_APPLICATION_INFO, applicationInfo);
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, getChannelId());
        protoOutputStream.write(1133871366146L, this.tickerText != null);
        protoOutputStream.write(1120986464259L, this.flags);
        protoOutputStream.write(1120986464260L, this.color);
        protoOutputStream.write(1138166333445L, this.category);
        protoOutputStream.write(1138166333446L, this.mGroupKey);
        protoOutputStream.write(1138166333447L, this.mSortKey);
        if (this.actions != null) {
            protoOutputStream.write(1120986464264L, this.actions.length);
        }
        if (this.visibility >= -1 && this.visibility <= 1) {
            protoOutputStream.write(1159641169929L, this.visibility);
        }
        if (this.publicVersion != null) {
            this.publicVersion.dumpDebug(protoOutputStream, 1146756268042L);
        }
        protoOutputStream.end(start);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append("Notification(channel=");
        sb.append(getChannelId());
        sb.append(" shortcut=");
        sb.append(getShortcutId());
        sb.append(" contentView=");
        if (this.contentView != null) {
            sb.append(this.contentView.getPackage());
            sb.append("/0x");
            sb.append(java.lang.Integer.toHexString(this.contentView.getLayoutId()));
        } else {
            sb.append("null");
        }
        sb.append(" vibrate=");
        if ((this.defaults & 2) != 0) {
            sb.append("default");
        } else if (this.vibrate != null) {
            int length = this.vibrate.length - 1;
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_START);
            for (int i = 0; i < length; i++) {
                sb.append(this.vibrate[i]);
                sb.append(',');
            }
            if (length != -1) {
                sb.append(this.vibrate[length]);
            }
            sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            sb.append("null");
        }
        sb.append(" sound=");
        if ((this.defaults & 1) != 0) {
            sb.append("default");
        } else if (this.sound != null) {
            sb.append(this.sound.toString());
        } else {
            sb.append("null");
        }
        if (this.tickerText != null) {
            sb.append(" tick");
        }
        sb.append(" defaults=0x");
        sb.append(java.lang.Integer.toHexString(this.defaults));
        sb.append(" flags=0x");
        sb.append(java.lang.Integer.toHexString(this.flags));
        sb.append(java.lang.String.format(" color=0x%08x", java.lang.Integer.valueOf(this.color)));
        if (this.category != null) {
            sb.append(" category=");
            sb.append(this.category);
        }
        if (this.mGroupKey != null) {
            sb.append(" groupKey=");
            sb.append(this.mGroupKey);
        }
        if (this.mSortKey != null) {
            sb.append(" sortKey=");
            sb.append(this.mSortKey);
        }
        if (this.actions != null) {
            sb.append(" actions=");
            sb.append(this.actions.length);
        }
        sb.append(" vis=");
        sb.append(visibilityToString(this.visibility));
        if (this.publicVersion != null) {
            sb.append(" publicVersion=");
            sb.append(this.publicVersion.toString());
        }
        if (this.mLocusId != null) {
            sb.append(" locusId=");
            sb.append(this.mLocusId);
        }
        sb.append(android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        return sb.toString();
    }

    public static java.lang.String visibilityToString(int i) {
        switch (i) {
            case -1:
                return "SECRET";
            case 0:
                return "PRIVATE";
            case 1:
                return "PUBLIC";
            default:
                return "UNKNOWN(" + java.lang.String.valueOf(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public static java.lang.String priorityToString(int i) {
        switch (i) {
            case -2:
                return "MIN";
            case -1:
                return "LOW";
            case 0:
                return "DEFAULT";
            case 1:
                return "HIGH";
            case 2:
                return "MAX";
            default:
                return "UNKNOWN(" + java.lang.String.valueOf(i) + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
        }
    }

    public boolean hasCompletedProgress() {
        return this.extras.containsKey(EXTRA_PROGRESS) && this.extras.containsKey(EXTRA_PROGRESS_MAX) && this.extras.getInt(EXTRA_PROGRESS_MAX) != 0 && this.extras.getInt(EXTRA_PROGRESS) == this.extras.getInt(EXTRA_PROGRESS_MAX);
    }

    @java.lang.Deprecated
    public java.lang.String getChannel() {
        return this.mChannelId;
    }

    public java.lang.String getChannelId() {
        return this.mChannelId;
    }

    @java.lang.Deprecated
    public long getTimeout() {
        return this.mTimeout;
    }

    public long getTimeoutAfter() {
        return this.mTimeout;
    }

    public int getBadgeIconType() {
        return this.mBadgeIcon;
    }

    public java.lang.String getShortcutId() {
        return this.mShortcutId;
    }

    public android.content.LocusId getLocusId() {
        return this.mLocusId;
    }

    public java.lang.CharSequence getSettingsText() {
        return this.mSettingsText;
    }

    public int getGroupAlertBehavior() {
        return this.mGroupAlertBehavior;
    }

    public android.app.Notification.BubbleMetadata getBubbleMetadata() {
        return this.mBubbleMetadata;
    }

    public void setBubbleMetadata(android.app.Notification.BubbleMetadata bubbleMetadata) {
        this.mBubbleMetadata = bubbleMetadata;
    }

    public boolean getAllowSystemGeneratedContextualActions() {
        return this.mAllowSystemGeneratedContextualActions;
    }

    public android.graphics.drawable.Icon getSmallIcon() {
        return this.mSmallIcon;
    }

    public void setSmallIcon(android.graphics.drawable.Icon icon) {
        this.mSmallIcon = icon;
    }

    public android.graphics.drawable.Icon getLargeIcon() {
        return this.mLargeIcon;
    }

    public boolean isGroupSummary() {
        return (this.mGroupKey == null || (this.flags & 512) == 0) ? false : true;
    }

    public boolean isGroupChild() {
        return this.mGroupKey != null && (this.flags & 512) == 0;
    }

    public boolean suppressAlertingDueToGrouping() {
        if (isGroupSummary() && getGroupAlertBehavior() == 2) {
            return true;
        }
        return isGroupChild() && getGroupAlertBehavior() == 1;
    }

    public android.util.Pair<android.app.RemoteInput, android.app.Notification.Action> findRemoteInputActionPair(boolean z) {
        if (this.actions == null) {
            return null;
        }
        for (android.app.Notification.Action action : this.actions) {
            if (action.getRemoteInputs() != null) {
                android.app.RemoteInput remoteInput = null;
                for (android.app.RemoteInput remoteInput2 : action.getRemoteInputs()) {
                    if (remoteInput2.getAllowFreeFormInput() || !z) {
                        remoteInput = remoteInput2;
                    }
                }
                if (remoteInput != null) {
                    return android.util.Pair.create(remoteInput, action);
                }
            }
        }
        return null;
    }

    public java.util.List<android.app.Notification.Action> getContextualActions() {
        if (this.actions == null) {
            return java.util.Collections.emptyList();
        }
        java.util.ArrayList arrayList = new java.util.ArrayList();
        for (android.app.Notification.Action action : this.actions) {
            if (action.isContextual()) {
                arrayList.add(action);
            }
        }
        return arrayList;
    }

    public static class Builder {
        public static final java.lang.String EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.bigViewActionCount";
        public static final java.lang.String EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.contentViewActionCount";
        public static final java.lang.String EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT = "android.rebuild.hudViewActionCount";
        private static final int LIGHTNESS_TEXT_DIFFERENCE_DARK = -10;
        private static final int LIGHTNESS_TEXT_DIFFERENCE_LIGHT = 20;
        private static final boolean USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY = android.os.SystemProperties.getBoolean("notifications.only_title", true);
        private java.util.ArrayList<android.app.Notification.Action> mActions;
        private com.android.internal.util.ContrastColorUtil mColorUtil;
        android.app.Notification.Colors mColors;
        private android.content.Context mContext;
        private boolean mInNightMode;
        private boolean mIsLegacy;
        private boolean mIsLegacyInitialized;
        private android.app.Notification mN;
        android.app.Notification.StandardTemplateParams mParams;
        private java.util.ArrayList<android.app.Person> mPersonList;
        private android.app.Notification.Style mStyle;
        private boolean mTintActionButtons;
        private android.os.Bundle mUserExtras;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Builder(android.content.Context context, java.lang.String str) {
            this(context, (android.app.Notification) null);
            this.mN.mChannelId = str;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        @java.lang.Deprecated
        public Builder(android.content.Context context) {
            this(context, (android.app.Notification) null);
        }

        public Builder(android.content.Context context, android.app.Notification notification) {
            java.util.ArrayList parcelableArrayList;
            this.mUserExtras = new android.os.Bundle();
            this.mActions = new java.util.ArrayList<>(3);
            this.mPersonList = new java.util.ArrayList<>();
            this.mParams = new android.app.Notification.StandardTemplateParams();
            this.mColors = new android.app.Notification.Colors();
            this.mContext = context;
            android.content.res.Resources resources = this.mContext.getResources();
            this.mTintActionButtons = resources.getBoolean(com.android.internal.R.bool.config_tintNotificationActionButtons);
            if (resources.getBoolean(com.android.internal.R.bool.config_enableNightMode)) {
                this.mInNightMode = (resources.getConfiguration().uiMode & 48) == 32;
            }
            if (notification == null) {
                this.mN = new android.app.Notification();
                if (context.getApplicationInfo().targetSdkVersion < 24) {
                    this.mN.extras.putBoolean(android.app.Notification.EXTRA_SHOW_WHEN, true);
                }
                this.mN.priority = 0;
                this.mN.visibility = 0;
                return;
            }
            this.mN = notification;
            if (this.mN.actions != null) {
                java.util.Collections.addAll(this.mActions, this.mN.actions);
            }
            if (this.mN.extras.containsKey(android.app.Notification.EXTRA_PEOPLE_LIST) && (parcelableArrayList = this.mN.extras.getParcelableArrayList(android.app.Notification.EXTRA_PEOPLE_LIST, android.app.Person.class)) != null && !parcelableArrayList.isEmpty()) {
                this.mPersonList.addAll(parcelableArrayList);
            }
            if (this.mN.getSmallIcon() == null && this.mN.icon != 0) {
                setSmallIcon(this.mN.icon);
            }
            if (this.mN.getLargeIcon() == null && this.mN.largeIcon != null) {
                setLargeIcon(this.mN.largeIcon);
            }
            java.lang.String string = this.mN.extras.getString(android.app.Notification.EXTRA_TEMPLATE);
            if (!android.text.TextUtils.isEmpty(string)) {
                java.lang.Class<? extends android.app.Notification.Style> notificationStyleClass = android.app.Notification.getNotificationStyleClass(string);
                if (notificationStyleClass == null) {
                    android.util.Log.d(android.app.Notification.TAG, "Unknown style class: " + string);
                    return;
                }
                try {
                    java.lang.reflect.Constructor<? extends android.app.Notification.Style> declaredConstructor = notificationStyleClass.getDeclaredConstructor(new java.lang.Class[0]);
                    declaredConstructor.setAccessible(true);
                    android.app.Notification.Style newInstance = declaredConstructor.newInstance(new java.lang.Object[0]);
                    newInstance.restoreFromExtras(this.mN.extras);
                    if (newInstance != null) {
                        setStyle(newInstance);
                    }
                } catch (java.lang.Throwable th) {
                    android.util.Log.e(android.app.Notification.TAG, "Could not create Style", th);
                }
            }
        }

        private com.android.internal.util.ContrastColorUtil getColorUtil() {
            if (this.mColorUtil == null) {
                this.mColorUtil = com.android.internal.util.ContrastColorUtil.getInstance(this.mContext);
            }
            return this.mColorUtil;
        }

        public android.app.Notification.Builder setShortcutId(java.lang.String str) {
            this.mN.mShortcutId = str;
            return this;
        }

        public android.app.Notification.Builder setLocusId(android.content.LocusId locusId) {
            this.mN.mLocusId = locusId;
            return this;
        }

        public android.app.Notification.Builder setBadgeIconType(int i) {
            this.mN.mBadgeIcon = i;
            return this;
        }

        public android.app.Notification.Builder setGroupAlertBehavior(int i) {
            this.mN.mGroupAlertBehavior = i;
            return this;
        }

        public android.app.Notification.Builder setBubbleMetadata(android.app.Notification.BubbleMetadata bubbleMetadata) {
            this.mN.mBubbleMetadata = bubbleMetadata;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setChannel(java.lang.String str) {
            this.mN.mChannelId = str;
            return this;
        }

        public android.app.Notification.Builder setChannelId(java.lang.String str) {
            this.mN.mChannelId = str;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setTimeout(long j) {
            this.mN.mTimeout = j;
            return this;
        }

        public android.app.Notification.Builder setTimeoutAfter(long j) {
            this.mN.mTimeout = j;
            return this;
        }

        public android.app.Notification.Builder setWhen(long j) {
            this.mN.when = j;
            return this;
        }

        public android.app.Notification.Builder setShowWhen(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_SHOW_WHEN, z);
            return this;
        }

        public android.app.Notification.Builder setUsesChronometer(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_SHOW_CHRONOMETER, z);
            return this;
        }

        public android.app.Notification.Builder setChronometerCountDown(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_CHRONOMETER_COUNT_DOWN, z);
            return this;
        }

        public android.app.Notification.Builder setSmallIcon(int i) {
            android.graphics.drawable.Icon icon;
            if (i != 0) {
                icon = android.graphics.drawable.Icon.createWithResource(this.mContext, i);
            } else {
                icon = null;
            }
            return setSmallIcon(icon);
        }

        public android.app.Notification.Builder setSmallIcon(int i, int i2) {
            this.mN.iconLevel = i2;
            return setSmallIcon(i);
        }

        public android.app.Notification.Builder setSmallIcon(android.graphics.drawable.Icon icon) {
            this.mN.setSmallIcon(icon);
            if (icon != null && icon.getType() == 2) {
                this.mN.icon = icon.getResId();
            }
            return this;
        }

        public android.app.Notification.Builder setSilent(boolean z) {
            if (!z) {
                return this;
            }
            if (this.mN.isGroupSummary()) {
                setGroupAlertBehavior(2);
            } else {
                setGroupAlertBehavior(1);
            }
            setVibrate(null);
            setSound(null);
            this.mN.defaults &= -2;
            this.mN.defaults &= -3;
            setDefaults(this.mN.defaults);
            if (android.text.TextUtils.isEmpty(this.mN.mGroupKey)) {
                setGroup("silent");
            }
            return this;
        }

        public android.app.Notification.Builder setContentTitle(java.lang.CharSequence charSequence) {
            this.mN.extras.putCharSequence(android.app.Notification.EXTRA_TITLE, android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.Builder setContentText(java.lang.CharSequence charSequence) {
            this.mN.extras.putCharSequence(android.app.Notification.EXTRA_TEXT, android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.Builder setSubText(java.lang.CharSequence charSequence) {
            this.mN.extras.putCharSequence(android.app.Notification.EXTRA_SUB_TEXT, android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.Builder setSettingsText(java.lang.CharSequence charSequence) {
            this.mN.mSettingsText = android.app.Notification.safeCharSequence(charSequence);
            return this;
        }

        public android.app.Notification.Builder setRemoteInputHistory(java.lang.CharSequence[] charSequenceArr) {
            if (charSequenceArr == null) {
                this.mN.extras.putCharSequenceArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY, null);
            } else {
                int min = java.lang.Math.min(5, charSequenceArr.length);
                java.lang.CharSequence[] charSequenceArr2 = new java.lang.CharSequence[min];
                android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr = new android.app.RemoteInputHistoryItem[min];
                for (int i = 0; i < min; i++) {
                    charSequenceArr2[i] = android.app.Notification.safeCharSequence(charSequenceArr[i]);
                    remoteInputHistoryItemArr[i] = new android.app.RemoteInputHistoryItem(charSequenceArr[i]);
                }
                this.mN.extras.putCharSequenceArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY, charSequenceArr2);
                this.mN.extras.putParcelableArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, remoteInputHistoryItemArr);
            }
            return this;
        }

        public android.app.Notification.Builder setRemoteInputHistory(android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr) {
            if (remoteInputHistoryItemArr == null) {
                this.mN.extras.putParcelableArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, null);
            } else {
                int min = java.lang.Math.min(5, remoteInputHistoryItemArr.length);
                android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr2 = new android.app.RemoteInputHistoryItem[min];
                for (int i = 0; i < min; i++) {
                    remoteInputHistoryItemArr2[i] = remoteInputHistoryItemArr[i];
                }
                this.mN.extras.putParcelableArray(android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, remoteInputHistoryItemArr2);
            }
            return this;
        }

        public android.app.Notification.Builder setShowRemoteInputSpinner(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER, z);
            return this;
        }

        public android.app.Notification.Builder setHideSmartReplies(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_HIDE_SMART_REPLIES, z);
            return this;
        }

        public android.app.Notification.Builder setNumber(int i) {
            this.mN.number = i;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setContentInfo(java.lang.CharSequence charSequence) {
            this.mN.extras.putCharSequence(android.app.Notification.EXTRA_INFO_TEXT, android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.Builder setProgress(int i, int i2, boolean z) {
            this.mN.extras.putInt(android.app.Notification.EXTRA_PROGRESS, i2);
            this.mN.extras.putInt(android.app.Notification.EXTRA_PROGRESS_MAX, i);
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_PROGRESS_INDETERMINATE, z);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setContent(android.widget.RemoteViews remoteViews) {
            return setCustomContentView(remoteViews);
        }

        public android.app.Notification.Builder setCustomContentView(android.widget.RemoteViews remoteViews) {
            this.mN.contentView = remoteViews;
            return this;
        }

        public android.app.Notification.Builder setCustomBigContentView(android.widget.RemoteViews remoteViews) {
            this.mN.bigContentView = remoteViews;
            return this;
        }

        public android.app.Notification.Builder setCustomHeadsUpContentView(android.widget.RemoteViews remoteViews) {
            this.mN.headsUpContentView = remoteViews;
            return this;
        }

        public android.app.Notification.Builder setContentIntent(android.app.PendingIntent pendingIntent) {
            this.mN.contentIntent = pendingIntent;
            return this;
        }

        public android.app.Notification.Builder setDeleteIntent(android.app.PendingIntent pendingIntent) {
            this.mN.deleteIntent = pendingIntent;
            return this;
        }

        public android.app.Notification.Builder setFullScreenIntent(android.app.PendingIntent pendingIntent, boolean z) {
            this.mN.fullScreenIntent = pendingIntent;
            setFlag(128, z);
            return this;
        }

        public android.app.Notification.Builder setTicker(java.lang.CharSequence charSequence) {
            this.mN.tickerText = android.app.Notification.safeCharSequence(charSequence);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setTicker(java.lang.CharSequence charSequence, android.widget.RemoteViews remoteViews) {
            setTicker(charSequence);
            return this;
        }

        public android.app.Notification.Builder setLargeIcon(android.graphics.Bitmap bitmap) {
            return setLargeIcon(bitmap != null ? android.graphics.drawable.Icon.createWithBitmap(bitmap) : null);
        }

        public android.app.Notification.Builder setLargeIcon(android.graphics.drawable.Icon icon) {
            this.mN.mLargeIcon = icon;
            this.mN.extras.putParcelable(android.app.Notification.EXTRA_LARGE_ICON, icon);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setSound(android.net.Uri uri) {
            this.mN.sound = uri;
            this.mN.audioAttributes = android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setSound(android.net.Uri uri, int i) {
            android.media.PlayerBase.deprecateStreamTypeForPlayback(i, android.app.Notification.TAG, "setSound()");
            this.mN.sound = uri;
            this.mN.audioStreamType = i;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setSound(android.net.Uri uri, android.media.AudioAttributes audioAttributes) {
            this.mN.sound = uri;
            this.mN.audioAttributes = audioAttributes;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setVibrate(long[] jArr) {
            this.mN.vibrate = jArr;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setLights(int i, int i2, int i3) {
            this.mN.ledARGB = i;
            this.mN.ledOnMS = i2;
            this.mN.ledOffMS = i3;
            if (i2 != 0 || i3 != 0) {
                this.mN.flags |= 1;
            }
            return this;
        }

        public android.app.Notification.Builder setOngoing(boolean z) {
            setFlag(2, z);
            return this;
        }

        public android.app.Notification.Builder setColorized(boolean z) {
            this.mN.extras.putBoolean(android.app.Notification.EXTRA_COLORIZED, z);
            return this;
        }

        public android.app.Notification.Builder setOnlyAlertOnce(boolean z) {
            setFlag(8, z);
            return this;
        }

        public android.app.Notification.Builder setForegroundServiceBehavior(int i) {
            this.mN.mFgsDeferBehavior = i;
            return this;
        }

        public android.app.Notification.Builder setAutoCancel(boolean z) {
            setFlag(16, z);
            return this;
        }

        public android.app.Notification.Builder setLocalOnly(boolean z) {
            setFlag(256, z);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setDefaults(int i) {
            this.mN.defaults = i;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder setPriority(int i) {
            this.mN.priority = i;
            return this;
        }

        public android.app.Notification.Builder setCategory(java.lang.String str) {
            this.mN.category = str;
            return this;
        }

        public android.app.Notification.Builder addPerson(java.lang.String str) {
            addPerson(new android.app.Person.Builder().setUri(str).build());
            return this;
        }

        public android.app.Notification.Builder addPerson(android.app.Person person) {
            this.mPersonList.add(person);
            return this;
        }

        public android.app.Notification.Builder setGroup(java.lang.String str) {
            this.mN.mGroupKey = str;
            return this;
        }

        public android.app.Notification.Builder setGroupSummary(boolean z) {
            setFlag(512, z);
            return this;
        }

        public android.app.Notification.Builder setSortKey(java.lang.String str) {
            this.mN.mSortKey = str;
            return this;
        }

        public android.app.Notification.Builder addExtras(android.os.Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras.putAll(bundle);
            }
            return this;
        }

        public android.app.Notification.Builder setExtras(android.os.Bundle bundle) {
            if (bundle != null) {
                this.mUserExtras = bundle;
            }
            return this;
        }

        public android.os.Bundle getExtras() {
            return this.mUserExtras;
        }

        @java.lang.Deprecated
        public android.app.Notification.Builder addAction(int i, java.lang.CharSequence charSequence, android.app.PendingIntent pendingIntent) {
            this.mActions.add(new android.app.Notification.Action(i, android.app.Notification.safeCharSequence(charSequence), pendingIntent));
            return this;
        }

        public android.app.Notification.Builder addAction(android.app.Notification.Action action) {
            if (action != null) {
                this.mActions.add(action);
            }
            return this;
        }

        public android.app.Notification.Builder setActions(android.app.Notification.Action... actionArr) {
            this.mActions.clear();
            for (int i = 0; i < actionArr.length; i++) {
                if (actionArr[i] != null) {
                    this.mActions.add(actionArr[i]);
                }
            }
            return this;
        }

        public android.app.Notification.Builder setStyle(android.app.Notification.Style style) {
            if (this.mStyle != style) {
                this.mStyle = style;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                    this.mN.extras.putString(android.app.Notification.EXTRA_TEMPLATE, style.getClass().getName());
                } else {
                    this.mN.extras.remove(android.app.Notification.EXTRA_TEMPLATE);
                }
            }
            return this;
        }

        public android.app.Notification.Style getStyle() {
            return this.mStyle;
        }

        public android.app.Notification.Builder setVisibility(int i) {
            this.mN.visibility = i;
            return this;
        }

        public android.app.Notification.Builder setPublicVersion(android.app.Notification notification) {
            if (notification != null) {
                this.mN.publicVersion = new android.app.Notification();
                notification.cloneInto(this.mN.publicVersion, true);
            } else {
                this.mN.publicVersion = null;
            }
            return this;
        }

        public android.app.Notification.Builder extend(android.app.Notification.Extender extender) {
            extender.extend(this);
            return this;
        }

        public android.app.Notification.Builder setFlag(int i, boolean z) {
            if (z) {
                android.app.Notification notification = this.mN;
                notification.flags = i | notification.flags;
            } else {
                android.app.Notification notification2 = this.mN;
                notification2.flags = (~i) & notification2.flags;
            }
            return this;
        }

        public android.app.Notification.Builder setColor(int i) {
            this.mN.color = i;
            sanitizeColor();
            return this;
        }

        private void bindPhishingAlertIcon(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            remoteViews.setDrawableTint(com.android.internal.R.id.phishing_alert, false, getColors(standardTemplateParams).getErrorColor(), android.graphics.PorterDuff.Mode.SRC_ATOP);
        }

        private android.graphics.drawable.Drawable getProfileBadgeDrawable() {
            if (this.mContext.getUserId() == 0) {
                return null;
            }
            return ((android.app.admin.DevicePolicyManager) this.mContext.getSystemService(android.app.admin.DevicePolicyManager.class)).getResources().getDrawable(getUpdatableProfileBadgeId(), android.app.admin.DevicePolicyResources.Drawables.Style.SOLID_COLORED, android.app.admin.DevicePolicyResources.Drawables.Source.NOTIFICATION, new java.util.function.Supplier() { // from class: android.app.Notification$Builder$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final java.lang.Object get() {
                    android.graphics.drawable.Drawable defaultProfileBadgeDrawable;
                    defaultProfileBadgeDrawable = android.app.Notification.Builder.this.getDefaultProfileBadgeDrawable();
                    return defaultProfileBadgeDrawable;
                }
            });
        }

        private java.lang.String getUpdatableProfileBadgeId() {
            return ((android.os.UserManager) this.mContext.getSystemService(android.os.UserManager.class)).isManagedProfile() ? android.app.admin.DevicePolicyResources.Drawables.WORK_PROFILE_ICON : android.app.admin.DevicePolicyResources.UNDEFINED;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.graphics.drawable.Drawable getDefaultProfileBadgeDrawable() {
            return this.mContext.getPackageManager().getUserBadgeForDensityNoBackground(new android.os.UserHandle(this.mContext.getUserId()), 0);
        }

        private android.graphics.Bitmap getProfileBadge() {
            android.graphics.drawable.Drawable profileBadgeDrawable = getProfileBadgeDrawable();
            if (profileBadgeDrawable == null) {
                return null;
            }
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_badge_size);
            android.graphics.Bitmap createBitmap = android.graphics.Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, android.graphics.Bitmap.Config.ARGB_8888);
            android.graphics.Canvas canvas = new android.graphics.Canvas(createBitmap);
            profileBadgeDrawable.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            profileBadgeDrawable.draw(canvas);
            return createBitmap;
        }

        private void bindProfileBadge(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            android.graphics.Bitmap profileBadge = getProfileBadge();
            if (profileBadge != null) {
                remoteViews.setImageViewBitmap(com.android.internal.R.id.profile_badge, profileBadge);
                remoteViews.setViewVisibility(com.android.internal.R.id.profile_badge, 0);
                if (isBackgroundColorized(standardTemplateParams)) {
                    remoteViews.setDrawableTint(com.android.internal.R.id.profile_badge, false, getPrimaryTextColor(standardTemplateParams), android.graphics.PorterDuff.Mode.SRC_ATOP);
                }
            }
        }

        private void bindAlertedIcon(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            remoteViews.setDrawableTint(com.android.internal.R.id.alerted_icon, false, getColors(standardTemplateParams).getSecondaryTextColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        }

        public boolean usesStandardHeader() {
            if (this.mN.mUsesStandardHeader) {
                return true;
            }
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 24 && this.mN.contentView == null && this.mN.bigContentView == null) {
                return true;
            }
            return (this.mN.contentView == null || android.app.Notification.STANDARD_LAYOUTS.contains(java.lang.Integer.valueOf(this.mN.contentView.getLayoutId()))) && (this.mN.bigContentView == null || android.app.Notification.STANDARD_LAYOUTS.contains(java.lang.Integer.valueOf(this.mN.bigContentView.getLayoutId())));
        }

        private void resetStandardTemplate(android.widget.RemoteViews remoteViews) {
            resetNotificationHeader(remoteViews);
            remoteViews.setViewVisibility(com.android.internal.R.id.right_icon, 8);
            remoteViews.setViewVisibility(16908310, 8);
            remoteViews.setTextViewText(16908310, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.text, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.text, null);
        }

        private void resetNotificationHeader(android.widget.RemoteViews remoteViews) {
            remoteViews.setBoolean(com.android.internal.R.id.expand_button, "setExpanded", false);
            remoteViews.setViewVisibility(com.android.internal.R.id.app_name_text, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.app_name_text, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.chronometer, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.header_text, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text_secondary, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.header_text_secondary, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text_divider, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text_secondary_divider, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.time_divider, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.time, 8);
            remoteViews.setImageViewIcon(com.android.internal.R.id.profile_badge, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.profile_badge, 8);
            this.mN.mUsesStandardHeader = false;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.widget.RemoteViews applyStandardTemplate(int i, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
            boolean z = true;
            standardTemplateParams.headerless(i == getBaseLayoutResource() || i == getHeadsUpBaseLayoutResource() || i == getMessagingLayoutResource() || i == 17367238);
            android.app.Notification.BuilderRemoteViews builderRemoteViews = new android.app.Notification.BuilderRemoteViews(this.mContext.getApplicationInfo(), i);
            resetStandardTemplate(builderRemoteViews);
            android.os.Bundle bundle = this.mN.extras;
            updateBackgroundColor(builderRemoteViews, standardTemplateParams);
            bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            bindLargeIconAndApplyMargin(builderRemoteViews, standardTemplateParams, templateBindResult);
            boolean handleProgressBar = handleProgressBar(builderRemoteViews, bundle, standardTemplateParams);
            if (standardTemplateParams.hasTitle()) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTitleViewId, 0);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTitleViewId, ensureColorSpanContrast(standardTemplateParams.mTitle, standardTemplateParams));
                setTextViewColorPrimary(builderRemoteViews, standardTemplateParams.mTitleViewId, standardTemplateParams);
            } else if (standardTemplateParams.mTitleViewId != 16908310) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTitleViewId, 8);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTitleViewId, null);
            }
            if (standardTemplateParams.mText != null && standardTemplateParams.mText.length() != 0 && (!handleProgressBar || standardTemplateParams.mAllowTextWithProgress)) {
                builderRemoteViews.setViewVisibility(standardTemplateParams.mTextViewId, 0);
                builderRemoteViews.setTextViewText(standardTemplateParams.mTextViewId, ensureColorSpanContrast(standardTemplateParams.mText, standardTemplateParams));
                setTextViewColorSecondary(builderRemoteViews, standardTemplateParams.mTextViewId, standardTemplateParams);
            } else {
                if (standardTemplateParams.mTextViewId != 16909624) {
                    builderRemoteViews.setViewVisibility(standardTemplateParams.mTextViewId, 8);
                    builderRemoteViews.setTextViewText(standardTemplateParams.mTextViewId, null);
                }
                z = handleProgressBar;
            }
            setHeaderlessVerticalMargins(builderRemoteViews, standardTemplateParams, z);
            return builderRemoteViews;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void setHeaderlessVerticalMargins(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, boolean z) {
            int i;
            if (!standardTemplateParams.mHeaderless) {
                return;
            }
            if (z) {
                i = com.android.internal.R.dimen.notification_headerless_margin_twoline;
            } else {
                i = com.android.internal.R.dimen.notification_headerless_margin_oneline;
            }
            remoteViews.setViewLayoutMarginDimen(com.android.internal.R.id.notification_headerless_view_column, 1, i);
            remoteViews.setViewLayoutMarginDimen(com.android.internal.R.id.notification_headerless_view_column, 3, i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorPrimary(android.widget.RemoteViews remoteViews, int i, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextColor(i, getPrimaryTextColor(standardTemplateParams));
        }

        public int getPrimaryTextColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getPrimaryTextColor();
        }

        public int getSecondaryTextColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getSecondaryTextColor();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTextViewColorSecondary(android.widget.RemoteViews remoteViews, int i, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            remoteViews.setTextColor(i, getSecondaryTextColor(standardTemplateParams));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.app.Notification.Colors getColors(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            this.mColors.resolvePalette(this.mContext, this.mN.color, isBackgroundColorized(standardTemplateParams), this.mInNightMode);
            return this.mColors;
        }

        private android.app.Notification.Colors getColors(boolean z) {
            this.mColors.resolvePalette(this.mContext, this.mN.color, !z && this.mN.isColorized(), this.mInNightMode);
            return this.mColors;
        }

        private void updateBackgroundColor(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            if (isBackgroundColorized(standardTemplateParams)) {
                remoteViews.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setBackgroundColor", getBackgroundColor(standardTemplateParams));
            } else {
                remoteViews.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setBackgroundResource", 0);
            }
        }

        private boolean handleProgressBar(android.widget.RemoteViews remoteViews, android.os.Bundle bundle, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            int i = bundle.getInt(android.app.Notification.EXTRA_PROGRESS_MAX, 0);
            int i2 = bundle.getInt(android.app.Notification.EXTRA_PROGRESS, 0);
            boolean z = bundle.getBoolean(android.app.Notification.EXTRA_PROGRESS_INDETERMINATE);
            if (!standardTemplateParams.mHideProgress && (i != 0 || z)) {
                remoteViews.setViewVisibility(16908301, 0);
                remoteViews.setProgressBar(16908301, i, i2, z);
                remoteViews.setProgressBackgroundTintList(16908301, this.mContext.getColorStateList(com.android.internal.R.color.notification_progress_background_color));
                android.content.res.ColorStateList valueOf = android.content.res.ColorStateList.valueOf(getPrimaryAccentColor(standardTemplateParams));
                remoteViews.setProgressTintList(16908301, valueOf);
                remoteViews.setProgressIndeterminateTintList(16908301, valueOf);
                return true;
            }
            remoteViews.setViewVisibility(16908301, 8);
            return false;
        }

        private void bindLargeIconAndApplyMargin(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
            if (templateBindResult == null) {
                templateBindResult = new android.app.Notification.TemplateBindResult();
            }
            bindLargeIcon(remoteViews, standardTemplateParams, templateBindResult);
            if (!standardTemplateParams.mHeaderless) {
                templateBindResult.mHeadingExtraMarginSet.applyToView(remoteViews, com.android.internal.R.id.notification_header);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, 16908310);
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, standardTemplateParams.mTextViewId);
                remoteViews.setInt(standardTemplateParams.mTextViewId, "setNumIndentLines", !standardTemplateParams.hasTitle() ? 1 : 0);
            }
        }

        private void calculateRightIconDimens(android.graphics.drawable.Icon icon, boolean z, android.app.Notification.TemplateBindResult templateBindResult) {
            float f;
            android.graphics.drawable.Drawable loadDrawable;
            int intrinsicWidth;
            int intrinsicHeight;
            android.content.res.Resources resources = this.mContext.getResources();
            float f2 = resources.getDisplayMetrics().density;
            float dimension = resources.getDimension(com.android.internal.R.dimen.notification_right_icon_content_margin) / f2;
            float dimension2 = (resources.getDimension(com.android.internal.R.dimen.notification_header_expand_icon_size) / f2) - (resources.getDimension(com.android.internal.R.dimen.notification_content_margin_end) / f2);
            float dimension3 = resources.getDimension(com.android.internal.R.dimen.notification_right_icon_size) / f2;
            if (icon != null && ((z || this.mContext.getApplicationInfo().targetSdkVersion >= 31) && (loadDrawable = icon.loadDrawable(this.mContext)) != null && (intrinsicWidth = loadDrawable.getIntrinsicWidth()) > (intrinsicHeight = loadDrawable.getIntrinsicHeight()) && intrinsicHeight > 0)) {
                f = java.lang.Math.min((intrinsicWidth * dimension3) / intrinsicHeight, 1.7777778f * dimension3);
            } else {
                f = dimension3;
            }
            templateBindResult.setRightIconState(icon != null, f, dimension3, f + dimension, dimension2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v7 */
        /* JADX WARN: Type inference failed for: r0v8, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r0v9 */
        private void bindLargeIcon(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
            android.graphics.drawable.Icon icon;
            if (this.mN.mLargeIcon == null && this.mN.largeIcon != null) {
                this.mN.mLargeIcon = android.graphics.drawable.Icon.createWithBitmap(this.mN.largeIcon);
            }
            android.graphics.drawable.Icon icon2 = standardTemplateParams.mHideLeftIcon ? null : this.mN.mLargeIcon;
            if (standardTemplateParams.mHideRightIcon) {
                icon = null;
            } else {
                icon = standardTemplateParams.mPromotedPicture != null ? standardTemplateParams.mPromotedPicture : this.mN.mLargeIcon;
            }
            if (icon2 != icon || icon2 == null) {
                remoteViews.setImageViewIcon(com.android.internal.R.id.left_icon, icon2);
                remoteViews.setIntTag(com.android.internal.R.id.left_icon, com.android.internal.R.id.tag_uses_right_icon_drawable, 0);
            } else {
                remoteViews.setIntTag(com.android.internal.R.id.left_icon, com.android.internal.R.id.tag_uses_right_icon_drawable, 1);
            }
            ?? r0 = standardTemplateParams.mPromotedPicture != null ? 1 : 0;
            calculateRightIconDimens(icon, r0, templateBindResult);
            if (icon != null) {
                remoteViews.setViewLayoutWidth(com.android.internal.R.id.right_icon, templateBindResult.mRightIconWidthDp, 1);
                remoteViews.setViewLayoutHeight(com.android.internal.R.id.right_icon, templateBindResult.mRightIconHeightDp, 1);
                remoteViews.setViewVisibility(com.android.internal.R.id.right_icon, 0);
                remoteViews.setImageViewIcon(com.android.internal.R.id.right_icon, icon);
                remoteViews.setIntTag(com.android.internal.R.id.right_icon, com.android.internal.R.id.tag_keep_when_showing_left_icon, r0);
                processLargeLegacyIcon(icon, remoteViews, standardTemplateParams);
                return;
            }
            remoteViews.setImageViewIcon(com.android.internal.R.id.right_icon, null);
            remoteViews.setIntTag(com.android.internal.R.id.right_icon, com.android.internal.R.id.tag_keep_when_showing_left_icon, 0);
        }

        private void bindNotificationHeader(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            bindSmallIcon(remoteViews, standardTemplateParams);
            boolean bindHeaderAppName = bindHeaderAppName(remoteViews, standardTemplateParams, false);
            boolean bindHeaderTextSecondary = bindHeaderAppName | bindHeaderTextSecondary(remoteViews, standardTemplateParams, bindHeaderAppName);
            boolean bindHeaderText = bindHeaderTextSecondary | bindHeaderText(remoteViews, standardTemplateParams, bindHeaderTextSecondary);
            if (!bindHeaderText) {
                bindHeaderText |= bindHeaderAppName(remoteViews, standardTemplateParams, true);
            }
            bindHeaderChronometerAndTime(remoteViews, standardTemplateParams, bindHeaderText);
            bindPhishingAlertIcon(remoteViews, standardTemplateParams);
            bindProfileBadge(remoteViews, standardTemplateParams);
            bindAlertedIcon(remoteViews, standardTemplateParams);
            bindExpandButton(remoteViews, standardTemplateParams);
            this.mN.mUsesStandardHeader = true;
        }

        private void bindExpandButton(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            int backgroundColor = getBackgroundColor(standardTemplateParams);
            int flattenAlpha = android.app.Notification.Colors.flattenAlpha(getColors(standardTemplateParams).getProtectionColor(), backgroundColor);
            int flattenAlpha2 = android.app.Notification.Colors.flattenAlpha(getPrimaryTextColor(standardTemplateParams), flattenAlpha);
            remoteViews.setInt(com.android.internal.R.id.expand_button, "setDefaultTextColor", flattenAlpha2);
            remoteViews.setInt(com.android.internal.R.id.expand_button, "setDefaultPillColor", flattenAlpha);
            if (standardTemplateParams.mHighlightExpander) {
                flattenAlpha = android.app.Notification.Colors.flattenAlpha(getColors(standardTemplateParams).getTertiaryFixedDimAccentColor(), backgroundColor);
                flattenAlpha2 = android.app.Notification.Colors.flattenAlpha(getColors(standardTemplateParams).getOnTertiaryFixedAccentTextColor(), flattenAlpha);
            }
            remoteViews.setInt(com.android.internal.R.id.expand_button, "setHighlightTextColor", flattenAlpha2);
            remoteViews.setInt(com.android.internal.R.id.expand_button, "setHighlightPillColor", flattenAlpha);
        }

        private void bindHeaderChronometerAndTime(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, boolean z) {
            if (!standardTemplateParams.mHideTime && showsTimeOrChronometer()) {
                if (z) {
                    remoteViews.setViewVisibility(com.android.internal.R.id.time_divider, 0);
                    setTextViewColorSecondary(remoteViews, com.android.internal.R.id.time_divider, standardTemplateParams);
                }
                if (this.mN.extras.getBoolean(android.app.Notification.EXTRA_SHOW_CHRONOMETER)) {
                    remoteViews.setViewVisibility(com.android.internal.R.id.chronometer, 0);
                    remoteViews.setLong(com.android.internal.R.id.chronometer, "setBase", this.mN.when + (android.os.SystemClock.elapsedRealtime() - java.lang.System.currentTimeMillis()));
                    remoteViews.setBoolean(com.android.internal.R.id.chronometer, "setStarted", true);
                    remoteViews.setChronometerCountDown(com.android.internal.R.id.chronometer, this.mN.extras.getBoolean(android.app.Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
                    setTextViewColorSecondary(remoteViews, com.android.internal.R.id.chronometer, standardTemplateParams);
                    return;
                }
                remoteViews.setViewVisibility(com.android.internal.R.id.time, 0);
                remoteViews.setLong(com.android.internal.R.id.time, "setTime", this.mN.when);
                setTextViewColorSecondary(remoteViews, com.android.internal.R.id.time, standardTemplateParams);
                return;
            }
            remoteViews.setLong(com.android.internal.R.id.time, "setTime", this.mN.when != 0 ? this.mN.when : this.mN.creationTime);
            setTextViewColorSecondary(remoteViews, com.android.internal.R.id.time, standardTemplateParams);
        }

        private boolean bindHeaderText(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, boolean z) {
            if (standardTemplateParams.mHideSubText) {
                return false;
            }
            java.lang.CharSequence charSequence = standardTemplateParams.mSubText;
            if (charSequence == null && this.mStyle != null && this.mStyle.mSummaryTextSet && this.mStyle.hasSummaryInHeader()) {
                charSequence = this.mStyle.mSummaryText;
            }
            if (charSequence == null && this.mContext.getApplicationInfo().targetSdkVersion < 24 && this.mN.extras.getCharSequence(android.app.Notification.EXTRA_INFO_TEXT) != null) {
                charSequence = this.mN.extras.getCharSequence(android.app.Notification.EXTRA_INFO_TEXT);
            }
            if (android.text.TextUtils.isEmpty(charSequence)) {
                return false;
            }
            remoteViews.setTextViewText(com.android.internal.R.id.header_text, ensureColorSpanContrast(processLegacyText(charSequence), standardTemplateParams));
            setTextViewColorSecondary(remoteViews, com.android.internal.R.id.header_text, standardTemplateParams);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text, 0);
            if (z) {
                remoteViews.setViewVisibility(com.android.internal.R.id.header_text_divider, 0);
                setTextViewColorSecondary(remoteViews, com.android.internal.R.id.header_text_divider, standardTemplateParams);
                return true;
            }
            return true;
        }

        private boolean bindHeaderTextSecondary(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, boolean z) {
            if (standardTemplateParams.mHideSubText || android.text.TextUtils.isEmpty(standardTemplateParams.mHeaderTextSecondary)) {
                return false;
            }
            remoteViews.setTextViewText(com.android.internal.R.id.header_text_secondary, ensureColorSpanContrast(processLegacyText(standardTemplateParams.mHeaderTextSecondary), standardTemplateParams));
            setTextViewColorSecondary(remoteViews, com.android.internal.R.id.header_text_secondary, standardTemplateParams);
            remoteViews.setViewVisibility(com.android.internal.R.id.header_text_secondary, 0);
            if (z) {
                remoteViews.setViewVisibility(com.android.internal.R.id.header_text_secondary_divider, 0);
                setTextViewColorSecondary(remoteViews, com.android.internal.R.id.header_text_secondary_divider, standardTemplateParams);
                return true;
            }
            return true;
        }

        public java.lang.String loadHeaderAppName() {
            return this.mN.loadHeaderAppName(this.mContext);
        }

        private boolean bindHeaderAppName(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams, boolean z) {
            if (standardTemplateParams.mViewType == android.app.Notification.StandardTemplateParams.VIEW_TYPE_MINIMIZED && !z) {
                return false;
            }
            if (standardTemplateParams.mHeaderless && standardTemplateParams.hasTitle()) {
                return true;
            }
            if (standardTemplateParams.mHideAppName) {
                return standardTemplateParams.hasTitle();
            }
            remoteViews.setViewVisibility(com.android.internal.R.id.app_name_text, 0);
            remoteViews.setTextViewText(com.android.internal.R.id.app_name_text, loadHeaderAppName());
            remoteViews.setTextColor(com.android.internal.R.id.app_name_text, getSecondaryTextColor(standardTemplateParams));
            return true;
        }

        private boolean isBackgroundColorized(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return standardTemplateParams.allowColorization && this.mN.isColorized();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isCallActionColorCustomizable() {
            return this.mN.isColorized() && this.mContext.getResources().getBoolean(com.android.internal.R.bool.config_callNotificationActionColorsRequireColorized);
        }

        private void bindSmallIcon(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            if (this.mN.mSmallIcon == null && this.mN.icon != 0) {
                this.mN.mSmallIcon = android.graphics.drawable.Icon.createWithResource(this.mContext, this.mN.icon);
            }
            remoteViews.setImageViewIcon(16908294, this.mN.mSmallIcon);
            remoteViews.setInt(16908294, "setImageLevel", this.mN.iconLevel);
            processSmallIconColor(this.mN.mSmallIcon, remoteViews, standardTemplateParams);
        }

        private boolean showsTimeOrChronometer() {
            return this.mN.showsTime() || this.mN.showsChronometer();
        }

        private void resetStandardTemplateWithActions(android.widget.RemoteViews remoteViews) {
            remoteViews.setViewVisibility(com.android.internal.R.id.actions, 8);
            remoteViews.removeAllViews(com.android.internal.R.id.actions);
            remoteViews.setViewVisibility(com.android.internal.R.id.notification_material_reply_container, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.notification_material_reply_text_1, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_1_container, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.notification_material_reply_progress, 8);
            remoteViews.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_2, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.notification_material_reply_text_2, null);
            remoteViews.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_3, 8);
            remoteViews.setTextViewText(com.android.internal.R.id.notification_material_reply_text_3, null);
            remoteViews.setViewLayoutMarginDimen(com.android.internal.R.id.notification_action_list_margin_target, 3, com.android.internal.R.dimen.notification_content_margin);
        }

        private void bindSnoozeAction(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            boolean z = this.mN.isFgsOrUij() || this.mN.fullScreenIntent != null || isBackgroundColorized(standardTemplateParams) || standardTemplateParams.mViewType != android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG;
            remoteViews.setBoolean(com.android.internal.R.id.snooze_button, "setEnabled", !z);
            if (z) {
                remoteViews.setViewVisibility(com.android.internal.R.id.snooze_button, 8);
            }
            if ((z || this.mContext.getContentResolver() == null || !isSnoozeSettingEnabled()) ? false : true) {
                remoteViews.setViewLayoutMarginDimen(com.android.internal.R.id.notification_action_list_margin_target, 3, 0);
            }
        }

        private boolean isSnoozeSettingEnabled() {
            try {
                return android.provider.Settings.Secure.getIntForUser(this.mContext.getContentResolver(), android.provider.Settings.Secure.SHOW_NOTIFICATION_SNOOZE, 0, -2) == 1;
            } catch (java.lang.SecurityException e) {
                return false;
            }
        }

        private java.util.List<android.app.Notification.Action> getNonContextualActions() {
            if (this.mActions == null) {
                return java.util.Collections.emptyList();
            }
            java.util.ArrayList arrayList = new java.util.ArrayList();
            java.util.Iterator<android.app.Notification.Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                android.app.Notification.Action next = it.next();
                if (!next.isContextual()) {
                    arrayList.add(next);
                }
            }
            return arrayList;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public android.widget.RemoteViews applyStandardTemplateWithActions(int i, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
            boolean z;
            android.widget.RemoteViews applyStandardTemplate = applyStandardTemplate(i, standardTemplateParams, templateBindResult);
            resetStandardTemplateWithActions(applyStandardTemplate);
            bindSnoozeAction(applyStandardTemplate, standardTemplateParams);
            android.content.res.ColorStateList valueOf = android.content.res.ColorStateList.valueOf(getStandardActionColor(standardTemplateParams));
            applyStandardTemplate.setColorStateList(com.android.internal.R.id.snooze_button, "setImageTintList", valueOf);
            applyStandardTemplate.setColorStateList(com.android.internal.R.id.bubble_button, "setImageTintList", valueOf);
            java.util.List<android.app.Notification.Action> nonContextualActions = getNonContextualActions();
            int min = java.lang.Math.min(nonContextualActions.size(), 3);
            boolean z2 = (this.mN.fullScreenIntent == null && !standardTemplateParams.mCallStyleActions && (this.mN.flags & 16384) == 0) ? false : true;
            if (standardTemplateParams.mCallStyleActions) {
                applyStandardTemplate.setViewPadding(com.android.internal.R.id.actions, 0, 0, 0, 0);
                applyStandardTemplate.setInt(com.android.internal.R.id.actions, "setCollapsibleIndentDimen", com.android.internal.R.dimen.call_notification_collapsible_indent);
                if (android.app.Flags.evenlyDividedCallStyleActionLayout()) {
                    android.util.Log.d(android.app.Notification.TAG, "setting evenly divided mode on action list");
                    applyStandardTemplate.setBoolean(com.android.internal.R.id.actions, "setEvenlyDividedMode", true);
                }
            }
            applyStandardTemplate.setBoolean(com.android.internal.R.id.actions, "setEmphasizedMode", z2);
            if (min > 0 && !standardTemplateParams.mHideActions) {
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.actions_container, 0);
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.actions, 0);
                applyStandardTemplate.setViewLayoutMarginDimen(com.android.internal.R.id.notification_action_list_margin_target, 3, 0);
                z = false;
                for (int i2 = 0; i2 < min; i2++) {
                    android.app.Notification.Action action = nonContextualActions.get(i2);
                    boolean hasValidRemoteInput = hasValidRemoteInput(action);
                    z |= hasValidRemoteInput;
                    android.widget.RemoteViews generateActionButton = generateActionButton(action, z2, standardTemplateParams);
                    if (hasValidRemoteInput && !z2) {
                        generateActionButton.setInt(com.android.internal.R.id.action0, "setBackgroundResource", 0);
                    }
                    if (z2 && i2 > 0) {
                        generateActionButton.setViewLayoutMarginDimen(com.android.internal.R.id.action0, 4, 0);
                    }
                    applyStandardTemplate.addView(com.android.internal.R.id.actions, generateActionButton);
                }
            } else {
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.actions_container, 8);
                z = false;
            }
            android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr = (android.app.RemoteInputHistoryItem[]) android.app.Notification.getParcelableArrayFromBundle(this.mN.extras, android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, android.app.RemoteInputHistoryItem.class);
            if (z && remoteInputHistoryItemArr != null && remoteInputHistoryItemArr.length > 0 && !android.text.TextUtils.isEmpty(remoteInputHistoryItemArr[0].getText()) && standardTemplateParams.maxRemoteInputHistory > 0) {
                boolean z3 = this.mN.extras.getBoolean(android.app.Notification.EXTRA_SHOW_REMOTE_INPUT_SPINNER);
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.notification_material_reply_container, 0);
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_1_container, 0);
                applyStandardTemplate.setTextViewText(com.android.internal.R.id.notification_material_reply_text_1, ensureColorSpanContrast(remoteInputHistoryItemArr[0].getText(), standardTemplateParams));
                setTextViewColorSecondary(applyStandardTemplate, com.android.internal.R.id.notification_material_reply_text_1, standardTemplateParams);
                applyStandardTemplate.setViewVisibility(com.android.internal.R.id.notification_material_reply_progress, z3 ? 0 : 8);
                applyStandardTemplate.setProgressIndeterminateTintList(com.android.internal.R.id.notification_material_reply_progress, android.content.res.ColorStateList.valueOf(getPrimaryAccentColor(standardTemplateParams)));
                if (remoteInputHistoryItemArr.length > 1 && !android.text.TextUtils.isEmpty(remoteInputHistoryItemArr[1].getText()) && standardTemplateParams.maxRemoteInputHistory > 1) {
                    applyStandardTemplate.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_2, 0);
                    applyStandardTemplate.setTextViewText(com.android.internal.R.id.notification_material_reply_text_2, ensureColorSpanContrast(remoteInputHistoryItemArr[1].getText(), standardTemplateParams));
                    setTextViewColorSecondary(applyStandardTemplate, com.android.internal.R.id.notification_material_reply_text_2, standardTemplateParams);
                    if (remoteInputHistoryItemArr.length > 2 && !android.text.TextUtils.isEmpty(remoteInputHistoryItemArr[2].getText()) && standardTemplateParams.maxRemoteInputHistory > 2) {
                        applyStandardTemplate.setViewVisibility(com.android.internal.R.id.notification_material_reply_text_3, 0);
                        applyStandardTemplate.setTextViewText(com.android.internal.R.id.notification_material_reply_text_3, ensureColorSpanContrast(remoteInputHistoryItemArr[2].getText(), standardTemplateParams));
                        setTextViewColorSecondary(applyStandardTemplate, com.android.internal.R.id.notification_material_reply_text_3, standardTemplateParams);
                    }
                }
            }
            return applyStandardTemplate;
        }

        private boolean hasValidRemoteInput(android.app.Notification.Action action) {
            android.app.RemoteInput[] remoteInputs;
            if (android.text.TextUtils.isEmpty(action.title) || action.actionIntent == null || (remoteInputs = action.getRemoteInputs()) == null) {
                return false;
            }
            for (android.app.RemoteInput remoteInput : remoteInputs) {
                java.lang.CharSequence[] choices = remoteInput.getChoices();
                if (remoteInput.getAllowFreeFormInput()) {
                    return true;
                }
                if (choices != null && choices.length != 0) {
                    return true;
                }
            }
            return false;
        }

        public android.widget.RemoteViews createContentView() {
            return createContentView(false);
        }

        private boolean fullyCustomViewRequiresDecoration(boolean z) {
            return !(z && android.app.Notification.PLATFORM_STYLE_CLASSES.contains(this.mStyle.getClass())) && this.mContext.getApplicationInfo().targetSdkVersion >= 31;
        }

        private android.widget.RemoteViews minimallyDecoratedContentView(android.widget.RemoteViews remoteViews) {
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(1).fillTextsFrom(this);
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.widget.RemoteViews applyStandardTemplate = applyStandardTemplate(getBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        private android.widget.RemoteViews minimallyDecoratedBigContentView(android.widget.RemoteViews remoteViews) {
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).decorationType(1).fillTextsFrom(this);
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.widget.RemoteViews applyStandardTemplateWithActions = applyStandardTemplateWithActions(getBigBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            makeHeaderExpanded(applyStandardTemplateWithActions);
            return applyStandardTemplateWithActions;
        }

        private android.widget.RemoteViews minimallyDecoratedHeadsUpContentView(android.widget.RemoteViews remoteViews) {
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(1).fillTextsFrom(this);
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.widget.RemoteViews applyStandardTemplateWithActions = applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        public android.widget.RemoteViews createContentView(boolean z) {
            android.widget.RemoteViews makeContentView;
            if (useExistingRemoteView(this.mN.contentView)) {
                return fullyCustomViewRequiresDecoration(false) ? minimallyDecoratedContentView(this.mN.contentView) : this.mN.contentView;
            }
            if (this.mStyle != null && (makeContentView = this.mStyle.makeContentView(z)) != null) {
                if (!fullyCustomViewRequiresDecoration(true)) {
                    return makeContentView;
                }
                return minimallyDecoratedContentView(makeContentView);
            }
            return applyStandardTemplate(getBaseLayoutResource(), this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this), null);
        }

        private boolean useExistingRemoteView(android.widget.RemoteViews remoteViews) {
            if (remoteViews == null || styleDisplaysCustomViewInline()) {
                return false;
            }
            if (fullyCustomViewRequiresDecoration(false) && android.app.Notification.STANDARD_LAYOUTS.contains(java.lang.Integer.valueOf(remoteViews.getLayoutId()))) {
                android.util.Log.w(android.app.Notification.TAG, "For apps targeting S, a custom content view that is a modified version of any standard layout is disallowed.");
                return false;
            }
            return true;
        }

        public android.widget.RemoteViews createBigContentView() {
            android.widget.RemoteViews remoteViews;
            if (useExistingRemoteView(this.mN.bigContentView)) {
                return fullyCustomViewRequiresDecoration(false) ? minimallyDecoratedBigContentView(this.mN.bigContentView) : this.mN.bigContentView;
            }
            if (this.mStyle == null) {
                remoteViews = null;
            } else {
                remoteViews = this.mStyle.makeBigContentView();
                if (fullyCustomViewRequiresDecoration(true)) {
                    remoteViews = minimallyDecoratedBigContentView(remoteViews);
                }
            }
            if (remoteViews == null && bigContentViewRequired()) {
                remoteViews = applyStandardTemplateWithActions(getBigBaseLayoutResource(), this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).allowTextWithProgress(true).fillTextsFrom(this), null);
            }
            makeHeaderExpanded(remoteViews);
            return remoteViews;
        }

        private boolean bigContentViewRequired() {
            if (this.mContext.getApplicationInfo().targetSdkVersion >= 31) {
                return true;
            }
            return !(this.mN.contentView != null && this.mN.bigContentView == null && this.mStyle == null && this.mActions.size() == 0);
        }

        public android.widget.RemoteViews makeNotificationGroupHeader() {
            return makeNotificationHeader(this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_GROUP_HEADER).fillTextsFrom(this));
        }

        private android.widget.RemoteViews makeNotificationHeader(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            standardTemplateParams.disallowColorization();
            android.app.Notification.BuilderRemoteViews builderRemoteViews = new android.app.Notification.BuilderRemoteViews(this.mContext.getApplicationInfo(), com.android.internal.R.layout.notification_template_header);
            resetNotificationHeader(builderRemoteViews);
            bindNotificationHeader(builderRemoteViews, standardTemplateParams);
            return builderRemoteViews;
        }

        public android.widget.RemoteViews makeAmbientNotification() {
            android.widget.RemoteViews createHeadsUpContentView = createHeadsUpContentView(false);
            if (createHeadsUpContentView != null) {
                return createHeadsUpContentView;
            }
            return createContentView();
        }

        public static void makeHeaderExpanded(android.widget.RemoteViews remoteViews) {
            if (remoteViews != null) {
                remoteViews.setBoolean(com.android.internal.R.id.expand_button, "setExpanded", true);
            }
        }

        public android.widget.RemoteViews createHeadsUpContentView(boolean z) {
            if (useExistingRemoteView(this.mN.headsUpContentView)) {
                if (fullyCustomViewRequiresDecoration(false)) {
                    return minimallyDecoratedHeadsUpContentView(this.mN.headsUpContentView);
                }
                return this.mN.headsUpContentView;
            }
            if (this.mStyle != null) {
                android.widget.RemoteViews makeHeadsUpContentView = this.mStyle.makeHeadsUpContentView(z);
                if (makeHeadsUpContentView != null) {
                    if (!fullyCustomViewRequiresDecoration(true)) {
                        return makeHeadsUpContentView;
                    }
                    return minimallyDecoratedHeadsUpContentView(makeHeadsUpContentView);
                }
            } else if (this.mActions.size() == 0) {
                return null;
            }
            return applyStandardTemplateWithActions(getHeadsUpBaseLayoutResource(), this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this).setMaxRemoteInputHistory(1), null);
        }

        public android.widget.RemoteViews createHeadsUpContentView() {
            return createHeadsUpContentView(false);
        }

        public android.widget.RemoteViews makePublicContentView(boolean z) {
            if (this.mN.publicVersion != null) {
                return recoverBuilder(this.mContext, this.mN.publicVersion).createContentView();
            }
            android.os.Bundle bundle = this.mN.extras;
            android.app.Notification.Style style = this.mStyle;
            this.mStyle = null;
            android.graphics.drawable.Icon icon = this.mN.mLargeIcon;
            this.mN.mLargeIcon = null;
            android.graphics.Bitmap bitmap = this.mN.largeIcon;
            this.mN.largeIcon = null;
            java.util.ArrayList<android.app.Notification.Action> arrayList = this.mActions;
            this.mActions = new java.util.ArrayList<>();
            android.os.Bundle bundle2 = new android.os.Bundle();
            bundle2.putBoolean(android.app.Notification.EXTRA_SHOW_WHEN, bundle.getBoolean(android.app.Notification.EXTRA_SHOW_WHEN));
            bundle2.putBoolean(android.app.Notification.EXTRA_SHOW_CHRONOMETER, bundle.getBoolean(android.app.Notification.EXTRA_SHOW_CHRONOMETER));
            bundle2.putBoolean(android.app.Notification.EXTRA_CHRONOMETER_COUNT_DOWN, bundle.getBoolean(android.app.Notification.EXTRA_CHRONOMETER_COUNT_DOWN));
            java.lang.String string = bundle.getString(android.app.Notification.EXTRA_SUBSTITUTE_APP_NAME);
            if (string != null) {
                bundle2.putString(android.app.Notification.EXTRA_SUBSTITUTE_APP_NAME, string);
            }
            this.mN.extras = bundle2;
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_PUBLIC).fillTextsFrom(this);
            if (z) {
                fillTextsFrom.highlightExpander(false);
            }
            android.widget.RemoteViews makeNotificationHeader = makeNotificationHeader(fillTextsFrom);
            makeNotificationHeader.setBoolean(com.android.internal.R.id.notification_header, "setExpandOnlyOnButton", true);
            this.mN.extras = bundle;
            this.mN.mLargeIcon = icon;
            this.mN.largeIcon = bitmap;
            this.mActions = arrayList;
            this.mStyle = style;
            return makeNotificationHeader;
        }

        public android.widget.RemoteViews makeLowPriorityContentView(boolean z) {
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_MINIMIZED).highlightExpander(false).fillTextsFrom(this);
            if (!z || android.text.TextUtils.isEmpty(fillTextsFrom.mSubText)) {
                fillTextsFrom.summaryText(createSummaryText());
            }
            android.widget.RemoteViews makeNotificationHeader = makeNotificationHeader(fillTextsFrom);
            makeNotificationHeader.setBoolean(com.android.internal.R.id.notification_header, "setAcceptAllTouches", true);
            makeNotificationHeader.setBoolean(com.android.internal.R.id.notification_header, "styleTextAsTitle", true);
            return makeNotificationHeader;
        }

        private java.lang.CharSequence createSummaryText() {
            java.lang.CharSequence charSequence = this.mN.extras.getCharSequence(android.app.Notification.EXTRA_TITLE);
            if (USE_ONLY_TITLE_IN_LOW_PRIORITY_SUMMARY) {
                return charSequence;
            }
            android.text.SpannableStringBuilder spannableStringBuilder = new android.text.SpannableStringBuilder();
            if (charSequence == null) {
                charSequence = this.mN.extras.getCharSequence(android.app.Notification.EXTRA_TITLE_BIG);
            }
            android.text.BidiFormatter bidiFormatter = android.text.BidiFormatter.getInstance();
            if (charSequence != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence));
            }
            java.lang.CharSequence charSequence2 = this.mN.extras.getCharSequence(android.app.Notification.EXTRA_TEXT);
            if (charSequence != null && charSequence2 != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(this.mContext.getText(com.android.internal.R.string.notification_header_divider_symbol_with_spaces)));
            }
            if (charSequence2 != null) {
                spannableStringBuilder.append(bidiFormatter.unicodeWrap(charSequence2));
            }
            return spannableStringBuilder;
        }

        private android.widget.RemoteViews generateActionButton(android.app.Notification.Action action, boolean z, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            java.lang.CharSequence ensureColorSpanContrast;
            boolean z2 = action.actionIntent == null;
            android.app.Notification.BuilderRemoteViews builderRemoteViews = new android.app.Notification.BuilderRemoteViews(this.mContext.getApplicationInfo(), getActionButtonLayoutResource(z, z2));
            if (!z2) {
                builderRemoteViews.setOnClickPendingIntent(com.android.internal.R.id.action0, action.actionIntent);
            }
            builderRemoteViews.setContentDescription(com.android.internal.R.id.action0, action.title);
            if (action.mRemoteInputs != null) {
                builderRemoteViews.setRemoteInputs(com.android.internal.R.id.action0, action.mRemoteInputs);
            }
            if (!z) {
                builderRemoteViews.setTextViewText(com.android.internal.R.id.action0, ensureColorSpanContrast(action.title, standardTemplateParams));
                builderRemoteViews.setTextColor(com.android.internal.R.id.action0, getStandardActionColor(standardTemplateParams));
            } else {
                java.lang.CharSequence charSequence = action.title;
                int secondaryAccentColor = getColors(standardTemplateParams).getSecondaryAccentColor();
                if (z2) {
                    secondaryAccentColor = setAlphaComponentByFloatDimen(this.mContext, com.android.internal.util.ContrastColorUtil.resolveSecondaryColor(this.mContext, getColors(standardTemplateParams).getBackgroundColor(), this.mInNightMode), com.android.internal.R.dimen.notification_action_disabled_container_alpha);
                }
                if (isLegacy()) {
                    ensureColorSpanContrast = com.android.internal.util.ContrastColorUtil.clearColorSpans(charSequence);
                } else {
                    java.lang.Integer fullLengthSpanColor = getFullLengthSpanColor(charSequence);
                    if (fullLengthSpanColor != null) {
                        secondaryAccentColor = ensureButtonFillContrast(fullLengthSpanColor.intValue(), getColors(standardTemplateParams).getBackgroundColor());
                    }
                    ensureColorSpanContrast = com.android.internal.util.ContrastColorUtil.ensureColorSpanContrast(charSequence, secondaryAccentColor);
                }
                java.lang.CharSequence ensureColorSpanContrast2 = ensureColorSpanContrast(ensureColorSpanContrast, standardTemplateParams);
                if (standardTemplateParams.mCallStyleActions && android.app.Flags.evenlyDividedCallStyleActionLayout()) {
                    android.util.Log.d(android.app.Notification.TAG, "new action layout enabled, gluing instead of setting text");
                    builderRemoteViews.setCharSequence(com.android.internal.R.id.action0, "glueLabel", ensureColorSpanContrast2);
                } else {
                    builderRemoteViews.setTextViewText(com.android.internal.R.id.action0, ensureColorSpanContrast2);
                }
                int resolvePrimaryColor = com.android.internal.util.ContrastColorUtil.resolvePrimaryColor(this.mContext, secondaryAccentColor, this.mInNightMode);
                if (z2) {
                    resolvePrimaryColor = setAlphaComponentByFloatDimen(this.mContext, com.android.internal.util.ContrastColorUtil.resolveSecondaryColor(this.mContext, getColors(standardTemplateParams).getBackgroundColor(), this.mInNightMode), com.android.internal.R.dimen.notification_action_disabled_content_alpha);
                }
                builderRemoteViews.setTextColor(com.android.internal.R.id.action0, resolvePrimaryColor);
                builderRemoteViews.setColorStateList(com.android.internal.R.id.action0, "setRippleColor", android.content.res.ColorStateList.valueOf((resolvePrimaryColor & 16777215) | android.media.audio.Enums.AUDIO_FORMAT_DTS_UHD_P2));
                builderRemoteViews.setColorStateList(com.android.internal.R.id.action0, "setButtonBackground", android.content.res.ColorStateList.valueOf(secondaryAccentColor));
                if (standardTemplateParams.mCallStyleActions) {
                    if (!android.app.Flags.evenlyDividedCallStyleActionLayout()) {
                        builderRemoteViews.setImageViewIcon(com.android.internal.R.id.action0, action.getIcon());
                    } else {
                        android.util.Log.d(android.app.Notification.TAG, "new action layout enabled, gluing instead of setting icon");
                        builderRemoteViews.setIcon(com.android.internal.R.id.action0, "glueIcon", action.getIcon());
                    }
                    boolean z3 = action.getExtras().getBoolean("key_action_priority");
                    builderRemoteViews.setBoolean(com.android.internal.R.id.action0, "setIsPriority", z3);
                    builderRemoteViews.setIntDimen(com.android.internal.R.id.action0, "setMinimumWidth", z3 ? com.android.internal.R.dimen.call_notification_system_action_min_width : 0);
                }
            }
            int indexOf = this.mActions.indexOf(action);
            if (indexOf != -1) {
                builderRemoteViews.setIntTag(com.android.internal.R.id.action0, com.android.internal.R.id.notification_action_index_tag, indexOf);
            }
            return builderRemoteViews;
        }

        private int getActionButtonLayoutResource(boolean z, boolean z2) {
            return z ? z2 ? getEmphasizedTombstoneActionLayoutResource() : getEmphasizedActionLayoutResource() : z2 ? getActionTombstoneLayoutResource() : getActionLayoutResource();
        }

        private static int setAlphaComponentByFloatDimen(android.content.Context context, int i, int i2) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            context.getResources().getValue(i2, typedValue, true);
            return com.android.internal.graphics.ColorUtils.setAlphaComponent(i, java.lang.Math.round(typedValue.getFloat() * 255.0f));
        }

        public static java.lang.Integer getFullLengthSpanColor(java.lang.CharSequence charSequence) {
            java.lang.Integer num = null;
            if (charSequence instanceof android.text.Spanned) {
                android.text.Spanned spanned = (android.text.Spanned) charSequence;
                for (java.lang.Object obj : spanned.getSpans(0, spanned.length(), java.lang.Object.class)) {
                    if (spanned.getSpanEnd(obj) - spanned.getSpanStart(obj) == charSequence.length()) {
                        if (obj instanceof android.text.style.TextAppearanceSpan) {
                            android.content.res.ColorStateList textColor = ((android.text.style.TextAppearanceSpan) obj).getTextColor();
                            if (textColor != null) {
                                num = java.lang.Integer.valueOf(textColor.getDefaultColor());
                            }
                        } else if (obj instanceof android.text.style.ForegroundColorSpan) {
                            num = java.lang.Integer.valueOf(((android.text.style.ForegroundColorSpan) obj).getForegroundColor());
                        }
                    }
                }
            }
            return num;
        }

        public java.lang.CharSequence ensureColorSpanContrast(java.lang.CharSequence charSequence, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return com.android.internal.util.ContrastColorUtil.ensureColorSpanContrast(charSequence, getBackgroundColor(standardTemplateParams));
        }

        public static boolean isColorDark(int i) {
            return com.android.internal.util.ContrastColorUtil.calculateLuminance(i) <= 0.17912878474d;
        }

        public static int ensureButtonFillContrast(int i, int i2) {
            if (isColorDark(i2)) {
                return com.android.internal.util.ContrastColorUtil.findContrastColorAgainstDark(i, i2, true, 1.3d);
            }
            return com.android.internal.util.ContrastColorUtil.findContrastColor(i, i2, true, 1.3d);
        }

        private boolean isLegacy() {
            if (!this.mIsLegacyInitialized) {
                this.mIsLegacy = this.mContext.getApplicationInfo().targetSdkVersion < 21;
                this.mIsLegacyInitialized = true;
            }
            return this.mIsLegacy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public java.lang.CharSequence processLegacyText(java.lang.CharSequence charSequence) {
            if (isLegacy() || textColorsNeedInversion()) {
                return getColorUtil().invertCharSequenceColors(charSequence);
            }
            return charSequence;
        }

        private void processSmallIconColor(android.graphics.drawable.Icon icon, android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            boolean z = !isLegacy() || getColorUtil().isGrayscaleIcon(this.mContext, icon);
            int smallIconColor = getSmallIconColor(standardTemplateParams);
            remoteViews.setInt(16908294, "setBackgroundColor", getBackgroundColor(standardTemplateParams));
            remoteViews.setInt(16908294, "setOriginalIconColor", z ? smallIconColor : 1);
        }

        private void processLargeLegacyIcon(android.graphics.drawable.Icon icon, android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            if (icon != null && isLegacy() && getColorUtil().isGrayscaleIcon(this.mContext, icon)) {
                remoteViews.setInt(16908294, "setOriginalIconColor", getSmallIconColor(standardTemplateParams));
            }
        }

        private void sanitizeColor() {
            if (this.mN.color != 0) {
                this.mN.color |= -16777216;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getStandardActionColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return (this.mTintActionButtons || isBackgroundColorized(standardTemplateParams)) ? getPrimaryAccentColor(standardTemplateParams) : getSecondaryTextColor(standardTemplateParams);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getSmallIconColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getContrastColor();
        }

        public int getSmallIconColor(boolean z) {
            return getColors(z).getContrastColor();
        }

        public int getBackgroundColor(boolean z) {
            return getColors(z).getBackgroundColor();
        }

        private int getPrimaryAccentColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getPrimaryAccentColor();
        }

        public android.app.Notification buildUnstyled() {
            if (this.mActions.size() > 0) {
                this.mN.actions = new android.app.Notification.Action[this.mActions.size()];
                this.mActions.toArray(this.mN.actions);
            }
            if (!this.mPersonList.isEmpty()) {
                this.mN.extras.putParcelableArrayList(android.app.Notification.EXTRA_PEOPLE_LIST, this.mPersonList);
            }
            if (this.mN.bigContentView != null || this.mN.contentView != null || this.mN.headsUpContentView != null) {
                this.mN.extras.putBoolean(android.app.Notification.EXTRA_CONTAINS_CUSTOM_VIEW, true);
            }
            return this.mN;
        }

        public static android.app.Notification.Builder recoverBuilder(android.content.Context context, android.app.Notification notification) {
            android.os.Trace.beginSection("Notification.Builder#recoverBuilder");
            try {
                android.content.pm.ApplicationInfo applicationInfo = (android.content.pm.ApplicationInfo) notification.extras.getParcelable(android.app.Notification.EXTRA_BUILDER_APPLICATION_INFO, android.content.pm.ApplicationInfo.class);
                if (applicationInfo != null) {
                    try {
                        context = context.createApplicationContext(applicationInfo, 4);
                    } catch (android.content.pm.PackageManager.NameNotFoundException e) {
                        android.util.Log.e(android.app.Notification.TAG, "ApplicationInfo " + applicationInfo + " not found");
                    }
                }
                return new android.app.Notification.Builder(context, notification);
            } finally {
                android.os.Trace.endSection();
            }
        }

        public android.app.Notification.Builder setAllowSystemGeneratedContextualActions(boolean z) {
            this.mN.mAllowSystemGeneratedContextualActions = z;
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification getNotification() {
            return build();
        }

        public android.app.Notification build() {
            if (this.mN.mShortcutId != null && this.mN.mBubbleMetadata != null && this.mN.mBubbleMetadata.getShortcutId() != null && !this.mN.mShortcutId.equals(this.mN.mBubbleMetadata.getShortcutId())) {
                throw new java.lang.IllegalArgumentException("Notification and BubbleMetadata shortcut id's don't match, notification: " + this.mN.mShortcutId + " vs bubble: " + this.mN.mBubbleMetadata.getShortcutId());
            }
            if (this.mUserExtras != null) {
                android.os.Bundle bundle = (android.os.Bundle) this.mUserExtras.clone();
                if (android.os.SystemProperties.getBoolean("persist.sysui.notification.builder_extras_override", false)) {
                    this.mN.extras.putAll(bundle);
                } else {
                    bundle.putAll(this.mN.extras);
                    this.mN.extras = bundle;
                }
            }
            this.mN.creationTime = java.lang.System.currentTimeMillis();
            android.app.Notification.addFieldsFromContext(this.mContext, this.mN);
            buildUnstyled();
            if (this.mStyle != null) {
                this.mStyle.reduceImageSizes(this.mContext);
                this.mStyle.purgeResources();
                this.mStyle.validate(this.mContext);
                this.mStyle.buildStyled(this.mN);
            }
            this.mN.reduceImageSizes(this.mContext);
            if (this.mContext.getApplicationInfo().targetSdkVersion < 24 && !styleDisplaysCustomViewInline()) {
                android.widget.RemoteViews remoteViews = this.mN.contentView;
                android.widget.RemoteViews remoteViews2 = this.mN.bigContentView;
                android.widget.RemoteViews remoteViews3 = this.mN.headsUpContentView;
                if (remoteViews == null) {
                    remoteViews = createContentView();
                    this.mN.extras.putInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, remoteViews.getSequenceNumber());
                }
                if (remoteViews2 == null && (remoteViews2 = createBigContentView()) != null) {
                    this.mN.extras.putInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, remoteViews2.getSequenceNumber());
                }
                if (remoteViews3 == null && (remoteViews3 = createHeadsUpContentView()) != null) {
                    this.mN.extras.putInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, remoteViews3.getSequenceNumber());
                }
                this.mN.contentView = remoteViews;
                this.mN.bigContentView = remoteViews2;
                this.mN.headsUpContentView = remoteViews3;
            }
            if ((this.mN.defaults & 4) != 0) {
                this.mN.flags |= 1;
            }
            this.mN.allPendingIntents = null;
            return this.mN;
        }

        private boolean styleDisplaysCustomViewInline() {
            return this.mStyle != null && this.mStyle.displayCustomViewInline();
        }

        public android.app.Notification buildInto(android.app.Notification notification) {
            build().cloneInto(notification, true);
            return notification;
        }

        public static android.app.Notification maybeCloneStrippedForDelivery(android.app.Notification notification) {
            java.lang.String string = notification.extras.getString(android.app.Notification.EXTRA_TEMPLATE);
            if (!android.text.TextUtils.isEmpty(string) && android.app.Notification.getNotificationStyleClass(string) == null) {
                return notification;
            }
            boolean z = (notification.contentView instanceof android.app.Notification.BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT, -1) == notification.contentView.getSequenceNumber();
            boolean z2 = (notification.bigContentView instanceof android.app.Notification.BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT, -1) == notification.bigContentView.getSequenceNumber();
            boolean z3 = (notification.headsUpContentView instanceof android.app.Notification.BuilderRemoteViews) && notification.extras.getInt(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT, -1) == notification.headsUpContentView.getSequenceNumber();
            if (!z && !z2 && !z3) {
                return notification;
            }
            android.app.Notification m392clone = notification.m392clone();
            if (z) {
                m392clone.contentView = null;
                m392clone.extras.remove(EXTRA_REBUILD_CONTENT_VIEW_ACTION_COUNT);
            }
            if (z2) {
                m392clone.bigContentView = null;
                m392clone.extras.remove(EXTRA_REBUILD_BIG_CONTENT_VIEW_ACTION_COUNT);
            }
            if (z3) {
                m392clone.headsUpContentView = null;
                m392clone.extras.remove(EXTRA_REBUILD_HEADS_UP_CONTENT_VIEW_ACTION_COUNT);
            }
            return m392clone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBaseLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getHeadsUpBaseLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_heads_up_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigBaseLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_big_base;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigPictureLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_big_picture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigTextLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_big_text;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getInboxLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_inbox;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getMessagingLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBigMessagingLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_big_messaging;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getConversationLayoutResource() {
            return com.android.internal.R.layout.notification_template_material_conversation;
        }

        private int getActionLayoutResource() {
            return com.android.internal.R.layout.notification_material_action;
        }

        private int getEmphasizedActionLayoutResource() {
            return com.android.internal.R.layout.notification_material_action_emphasized;
        }

        private int getEmphasizedTombstoneActionLayoutResource() {
            return com.android.internal.R.layout.notification_material_action_emphasized_tombstone;
        }

        private int getActionTombstoneLayoutResource() {
            return com.android.internal.R.layout.notification_material_action_tombstone;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public int getBackgroundColor(android.app.Notification.StandardTemplateParams standardTemplateParams) {
            return getColors(standardTemplateParams).getBackgroundColor();
        }

        private boolean textColorsNeedInversion() {
            int i;
            return this.mStyle != null && android.app.Notification.MediaStyle.class.equals(this.mStyle.getClass()) && (i = this.mContext.getApplicationInfo().targetSdkVersion) > 23 && i < 26;
        }

        public java.lang.CharSequence getHeadsUpStatusBarText(boolean z) {
            if (this.mStyle != null && !z) {
                java.lang.CharSequence headsUpStatusBarText = this.mStyle.getHeadsUpStatusBarText();
                if (!android.text.TextUtils.isEmpty(headsUpStatusBarText)) {
                    return headsUpStatusBarText;
                }
            }
            return loadHeaderAppName();
        }

        public boolean usesTemplate() {
            return (this.mN.contentView == null && this.mN.headsUpContentView == null && this.mN.bigContentView == null) || styleDisplaysCustomViewInline();
        }
    }

    void reduceImageSizes(android.content.Context context) {
        int i;
        if (this.extras.getBoolean(EXTRA_REDUCED_IMAGES)) {
            return;
        }
        boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
        if (this.mSmallIcon != null && (this.mSmallIcon.getType() == 1 || this.mSmallIcon.getType() == 5)) {
            int dimensionPixelSize = context.getResources().getDimensionPixelSize(isLowRamDeviceStatic ? com.android.internal.R.dimen.notification_small_icon_size_low_ram : com.android.internal.R.dimen.notification_small_icon_size);
            this.mSmallIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
        }
        if (this.mLargeIcon != null || this.largeIcon != null) {
            android.content.res.Resources resources = context.getResources();
            getNotificationStyle();
            if (isLowRamDeviceStatic) {
                i = com.android.internal.R.dimen.notification_right_icon_size_low_ram;
            } else {
                i = com.android.internal.R.dimen.notification_right_icon_size;
            }
            int dimensionPixelSize2 = resources.getDimensionPixelSize(i);
            if (this.mLargeIcon != null) {
                this.mLargeIcon.scaleDownIfNecessary(dimensionPixelSize2, dimensionPixelSize2);
            }
            if (this.largeIcon != null) {
                this.largeIcon = android.graphics.drawable.Icon.scaleDownIfNecessary(this.largeIcon, dimensionPixelSize2, dimensionPixelSize2);
            }
        }
        reduceImageSizesForRemoteView(this.contentView, context, isLowRamDeviceStatic);
        reduceImageSizesForRemoteView(this.headsUpContentView, context, isLowRamDeviceStatic);
        reduceImageSizesForRemoteView(this.bigContentView, context, isLowRamDeviceStatic);
        this.extras.putBoolean(EXTRA_REDUCED_IMAGES, true);
    }

    private void reduceImageSizesForRemoteView(android.widget.RemoteViews remoteViews, android.content.Context context, boolean z) {
        int i;
        int i2;
        if (remoteViews != null) {
            android.content.res.Resources resources = context.getResources();
            if (z) {
                i = com.android.internal.R.dimen.notification_custom_view_max_image_width_low_ram;
            } else {
                i = com.android.internal.R.dimen.notification_custom_view_max_image_width;
            }
            int dimensionPixelSize = resources.getDimensionPixelSize(i);
            if (z) {
                i2 = com.android.internal.R.dimen.notification_custom_view_max_image_height_low_ram;
            } else {
                i2 = com.android.internal.R.dimen.notification_custom_view_max_image_height;
            }
            remoteViews.reduceImageSizes(dimensionPixelSize, resources.getDimensionPixelSize(i2));
        }
    }

    public boolean isForegroundService() {
        return (this.flags & 64) != 0;
    }

    public boolean isUserInitiatedJob() {
        return (this.flags & 32768) != 0;
    }

    public boolean isFgsOrUij() {
        return isForegroundService() || isUserInitiatedJob();
    }

    public boolean shouldShowForegroundImmediately() {
        if (this.mFgsDeferBehavior == 1) {
            return true;
        }
        if (this.mFgsDeferBehavior == 2) {
            return false;
        }
        return isMediaNotification() || "call".equals(this.category) || CATEGORY_NAVIGATION.equals(this.category) || (this.actions != null && this.actions.length > 0);
    }

    public boolean isForegroundDisplayForceDeferred() {
        return 2 == this.mFgsDeferBehavior;
    }

    public java.lang.Class<? extends android.app.Notification.Style> getNotificationStyle() {
        java.lang.String string = this.extras.getString(EXTRA_TEMPLATE);
        if (!android.text.TextUtils.isEmpty(string)) {
            return getNotificationStyleClass(string);
        }
        return null;
    }

    public boolean isStyle(java.lang.Class<? extends android.app.Notification.Style> cls) {
        return java.util.Objects.equals(this.extras.getString(EXTRA_TEMPLATE), cls.getName());
    }

    public boolean isColorized() {
        return this.extras.getBoolean(EXTRA_COLORIZED) && (hasColorizedPermission() || isFgsOrUij());
    }

    public boolean hasColorizedPermission() {
        return (this.flags & 2048) != 0;
    }

    public boolean isMediaNotification() {
        java.lang.Class<? extends android.app.Notification.Style> notificationStyle = getNotificationStyle();
        return (android.app.Notification.MediaStyle.class.equals(notificationStyle) || android.app.Notification.DecoratedMediaCustomViewStyle.class.equals(notificationStyle)) && (this.extras.getParcelable(EXTRA_MEDIA_SESSION, android.media.session.MediaSession.Token.class) != null);
    }

    public java.lang.Boolean isCustomNotification() {
        if (this.contentView == null && this.bigContentView == null && this.headsUpContentView == null) {
            return false;
        }
        return true;
    }

    public boolean isBubbleNotification() {
        return (this.flags & 4096) != 0;
    }

    private boolean hasLargeIcon() {
        return (this.mLargeIcon == null && this.largeIcon == null) ? false : true;
    }

    public boolean showsTime() {
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_WHEN);
    }

    public boolean showsChronometer() {
        return this.when != 0 && this.extras.getBoolean(EXTRA_SHOW_CHRONOMETER);
    }

    public boolean hasImage() {
        if (!isStyle(android.app.Notification.MessagingStyle.class) || this.extras == null) {
            return hasLargeIcon() || this.extras.containsKey(EXTRA_BACKGROUND_IMAGE_URI);
        }
        android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) this.extras.getParcelableArray(EXTRA_MESSAGES, android.os.Parcelable.class);
        if (!com.android.internal.util.ArrayUtils.isEmpty(parcelableArr)) {
            for (android.app.Notification.MessagingStyle.Message message : android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray(parcelableArr)) {
                if (message.getDataUri() != null && message.getDataMimeType() != null && message.getDataMimeType().startsWith(com.android.internal.widget.MessagingMessage.IMAGE_MIME_TYPE_PREFIX)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    @android.annotation.SystemApi
    public static java.lang.Class<? extends android.app.Notification.Style> getNotificationStyleClass(java.lang.String str) {
        for (java.lang.Class<? extends android.app.Notification.Style> cls : PLATFORM_STYLE_CLASSES) {
            if (str.equals(cls.getName())) {
                return cls;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void buildCustomContentIntoTemplate(android.content.Context context, android.widget.RemoteViews remoteViews, android.widget.RemoteViews remoteViews2, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
        int i;
        if (remoteViews2 == null) {
            i = -1;
        } else {
            android.widget.RemoteViews mo424clone = remoteViews2.mo424clone();
            if (standardTemplateParams.mHeaderless) {
                remoteViews.removeFromParent(com.android.internal.R.id.notification_top_line);
                android.app.Notification.Builder.setHeaderlessVerticalMargins(remoteViews, standardTemplateParams, true);
            } else {
                android.content.res.Resources resources = context.getResources();
                templateBindResult.mTitleMarginSet.applyToView(remoteViews, com.android.internal.R.id.notification_main_column, resources.getDimension(com.android.internal.R.dimen.notification_content_margin_end) / resources.getDisplayMetrics().density);
            }
            remoteViews.removeAllViewsExceptId(com.android.internal.R.id.notification_main_column, 16908301);
            i = 0;
            remoteViews.addView(com.android.internal.R.id.notification_main_column, mo424clone, 0);
            remoteViews.addFlags(1);
        }
        remoteViews.setIntTag(com.android.internal.R.id.notification_main_column, com.android.internal.R.id.notification_custom_view_index_tag, i);
    }

    public static abstract class Style {
        static final int MAX_REMOTE_INPUT_HISTORY_LINES = 3;
        private java.lang.CharSequence mBigContentTitle;
        protected android.app.Notification.Builder mBuilder;
        protected java.lang.CharSequence mSummaryText = null;
        protected boolean mSummaryTextSet = false;

        public abstract boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style);

        protected void internalSetBigContentTitle(java.lang.CharSequence charSequence) {
            this.mBigContentTitle = charSequence;
        }

        protected void internalSetSummaryText(java.lang.CharSequence charSequence) {
            this.mSummaryText = charSequence;
            this.mSummaryTextSet = true;
        }

        public void setBuilder(android.app.Notification.Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        protected void checkBuilder() {
            if (this.mBuilder == null) {
                throw new java.lang.IllegalArgumentException("Style requires a valid Builder object");
            }
        }

        protected android.widget.RemoteViews getStandardView(int i) {
            return getStandardView(i, this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_UNSPECIFIED).fillTextsFrom(this.mBuilder), null);
        }

        protected android.widget.RemoteViews getStandardView(int i, android.app.Notification.StandardTemplateParams standardTemplateParams, android.app.Notification.TemplateBindResult templateBindResult) {
            checkBuilder();
            if (this.mBigContentTitle != null) {
                standardTemplateParams.mTitle = this.mBigContentTitle;
            }
            return this.mBuilder.applyStandardTemplateWithActions(i, standardTemplateParams, templateBindResult);
        }

        public android.widget.RemoteViews makeContentView(boolean z) {
            return null;
        }

        public android.widget.RemoteViews makeBigContentView() {
            return null;
        }

        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            return null;
        }

        public void addExtras(android.os.Bundle bundle) {
            if (this.mSummaryTextSet) {
                bundle.putCharSequence(android.app.Notification.EXTRA_SUMMARY_TEXT, this.mSummaryText);
            }
            if (this.mBigContentTitle != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_TITLE_BIG, this.mBigContentTitle);
            }
            bundle.putString(android.app.Notification.EXTRA_TEMPLATE, getClass().getName());
        }

        protected void restoreFromExtras(android.os.Bundle bundle) {
            if (bundle.containsKey(android.app.Notification.EXTRA_SUMMARY_TEXT)) {
                this.mSummaryText = bundle.getCharSequence(android.app.Notification.EXTRA_SUMMARY_TEXT);
                this.mSummaryTextSet = true;
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_TITLE_BIG)) {
                this.mBigContentTitle = bundle.getCharSequence(android.app.Notification.EXTRA_TITLE_BIG);
            }
        }

        public android.app.Notification buildStyled(android.app.Notification notification) {
            addExtras(notification.extras);
            return notification;
        }

        public void purgeResources() {
        }

        public android.app.Notification build() {
            checkBuilder();
            return this.mBuilder.build();
        }

        public boolean hasSummaryInHeader() {
            return true;
        }

        public boolean displayCustomViewInline() {
            return false;
        }

        public void reduceImageSizes(android.content.Context context) {
        }

        public void validate(android.content.Context context) {
        }

        public java.lang.CharSequence getHeadsUpStatusBarText() {
            return null;
        }
    }

    public static class BigPictureStyle extends android.app.Notification.Style {
        public static final int MIN_ASHMEM_BITMAP_SIZE = 131072;
        private android.graphics.drawable.Icon mBigLargeIcon;
        private boolean mBigLargeIconSet = false;
        private java.lang.CharSequence mPictureContentDescription;
        private android.graphics.drawable.Icon mPictureIcon;
        private boolean mShowBigPictureWhenCollapsed;

        public BigPictureStyle() {
        }

        @java.lang.Deprecated
        public BigPictureStyle(android.app.Notification.Builder builder) {
            setBuilder(builder);
        }

        public android.app.Notification.BigPictureStyle setBigContentTitle(java.lang.CharSequence charSequence) {
            internalSetBigContentTitle(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.BigPictureStyle setSummaryText(java.lang.CharSequence charSequence) {
            internalSetSummaryText(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.BigPictureStyle setContentDescription(java.lang.CharSequence charSequence) {
            this.mPictureContentDescription = charSequence;
            return this;
        }

        public android.graphics.drawable.Icon getBigPicture() {
            if (this.mPictureIcon != null) {
                return this.mPictureIcon;
            }
            return null;
        }

        public android.app.Notification.BigPictureStyle bigPicture(android.graphics.Bitmap bitmap) {
            this.mPictureIcon = bitmap == null ? null : android.graphics.drawable.Icon.createWithBitmap(bitmap);
            return this;
        }

        public android.app.Notification.BigPictureStyle bigPicture(android.graphics.drawable.Icon icon) {
            this.mPictureIcon = icon;
            return this;
        }

        public android.app.Notification.BigPictureStyle showBigPictureWhenCollapsed(boolean z) {
            this.mShowBigPictureWhenCollapsed = z;
            return this;
        }

        public android.app.Notification.BigPictureStyle bigLargeIcon(android.graphics.Bitmap bitmap) {
            return bigLargeIcon(bitmap != null ? android.graphics.drawable.Icon.createWithBitmap(bitmap) : null);
        }

        public android.app.Notification.BigPictureStyle bigLargeIcon(android.graphics.drawable.Icon icon) {
            this.mBigLargeIconSet = true;
            this.mBigLargeIcon = icon;
            return this;
        }

        @Override // android.app.Notification.Style
        public void purgeResources() {
            super.purgeResources();
            if (this.mPictureIcon != null) {
                this.mPictureIcon.convertToAshmem();
            }
            if (this.mBigLargeIcon != null) {
                this.mBigLargeIcon.convertToAshmem();
            }
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(android.content.Context context) {
            int i;
            int i2;
            int i3;
            super.reduceImageSizes(context);
            android.content.res.Resources resources = context.getResources();
            boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
            if (this.mPictureIcon != null) {
                if (isLowRamDeviceStatic) {
                    i2 = com.android.internal.R.dimen.notification_big_picture_max_height_low_ram;
                } else {
                    i2 = com.android.internal.R.dimen.notification_big_picture_max_height;
                }
                int dimensionPixelSize = resources.getDimensionPixelSize(i2);
                if (isLowRamDeviceStatic) {
                    i3 = com.android.internal.R.dimen.notification_big_picture_max_width_low_ram;
                } else {
                    i3 = com.android.internal.R.dimen.notification_big_picture_max_width;
                }
                this.mPictureIcon.scaleDownIfNecessary(resources.getDimensionPixelSize(i3), dimensionPixelSize);
            }
            if (this.mBigLargeIcon != null) {
                if (isLowRamDeviceStatic) {
                    i = com.android.internal.R.dimen.notification_right_icon_size_low_ram;
                } else {
                    i = com.android.internal.R.dimen.notification_right_icon_size;
                }
                int dimensionPixelSize2 = resources.getDimensionPixelSize(i);
                this.mBigLargeIcon.scaleDownIfNecessary(dimensionPixelSize2, dimensionPixelSize2);
            }
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeContentView(z);
            }
            return getStandardView(this.mBuilder.getBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon), null);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            if (this.mPictureIcon == null || !this.mShowBigPictureWhenCollapsed) {
                return super.makeHeadsUpContentView(z);
            }
            return getStandardView(this.mBuilder.getHeadsUpBaseLayoutResource(), this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP).fillTextsFrom(this.mBuilder).promotedPicture(this.mPictureIcon), null);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            android.graphics.drawable.Icon icon;
            android.graphics.Bitmap bitmap;
            if (!this.mBigLargeIconSet) {
                icon = null;
                bitmap = null;
            } else {
                icon = this.mBuilder.mN.mLargeIcon;
                this.mBuilder.mN.mLargeIcon = this.mBigLargeIcon;
                bitmap = this.mBuilder.mN.largeIcon;
                this.mBuilder.mN.largeIcon = null;
            }
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).fillTextsFrom(this.mBuilder);
            android.widget.RemoteViews standardView = getStandardView(this.mBuilder.getBigPictureLayoutResource(), fillTextsFrom, null);
            if (this.mSummaryTextSet) {
                standardView.setTextViewText(com.android.internal.R.id.text, this.mBuilder.ensureColorSpanContrast(this.mBuilder.processLegacyText(this.mSummaryText), fillTextsFrom));
                this.mBuilder.setTextViewColorSecondary(standardView, com.android.internal.R.id.text, fillTextsFrom);
                standardView.setViewVisibility(com.android.internal.R.id.text, 0);
            }
            if (this.mBigLargeIconSet) {
                this.mBuilder.mN.mLargeIcon = icon;
                this.mBuilder.mN.largeIcon = bitmap;
            }
            standardView.setImageViewIcon(com.android.internal.R.id.big_picture, this.mPictureIcon);
            if (this.mPictureContentDescription != null) {
                standardView.setContentDescription(com.android.internal.R.id.big_picture, this.mPictureContentDescription);
            }
            return standardView;
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            if (this.mBigLargeIconSet) {
                bundle.putParcelable(android.app.Notification.EXTRA_LARGE_ICON_BIG, this.mBigLargeIcon);
            }
            if (this.mPictureContentDescription != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION, this.mPictureContentDescription);
            }
            bundle.putBoolean(android.app.Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED, this.mShowBigPictureWhenCollapsed);
            if (this.mPictureIcon == null) {
                bundle.remove(android.app.Notification.EXTRA_PICTURE_ICON);
                bundle.remove(android.app.Notification.EXTRA_PICTURE);
            } else if (this.mPictureIcon.getType() == 1) {
                bundle.putParcelable(android.app.Notification.EXTRA_PICTURE, this.mPictureIcon.getBitmap());
                bundle.putParcelable(android.app.Notification.EXTRA_PICTURE_ICON, null);
            } else {
                bundle.putParcelable(android.app.Notification.EXTRA_PICTURE, null);
                bundle.putParcelable(android.app.Notification.EXTRA_PICTURE_ICON, this.mPictureIcon);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(android.app.Notification.EXTRA_LARGE_ICON_BIG)) {
                this.mBigLargeIconSet = true;
                this.mBigLargeIcon = (android.graphics.drawable.Icon) bundle.getParcelable(android.app.Notification.EXTRA_LARGE_ICON_BIG, android.graphics.drawable.Icon.class);
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION)) {
                this.mPictureContentDescription = bundle.getCharSequence(android.app.Notification.EXTRA_PICTURE_CONTENT_DESCRIPTION);
            }
            this.mShowBigPictureWhenCollapsed = bundle.getBoolean(android.app.Notification.EXTRA_SHOW_BIG_PICTURE_WHEN_COLLAPSED);
            this.mPictureIcon = getPictureIcon(bundle);
        }

        public static android.graphics.drawable.Icon getPictureIcon(android.os.Bundle bundle) {
            if (bundle == null) {
                return null;
            }
            android.graphics.Bitmap bitmap = (android.graphics.Bitmap) bundle.getParcelable(android.app.Notification.EXTRA_PICTURE, android.graphics.Bitmap.class);
            if (bitmap != null) {
                return android.graphics.drawable.Icon.createWithBitmap(bitmap);
            }
            return (android.graphics.drawable.Icon) bundle.getParcelable(android.app.Notification.EXTRA_PICTURE_ICON, android.graphics.drawable.Icon.class);
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return android.app.Notification.areIconsMaybeDifferent(getBigPicture(), ((android.app.Notification.BigPictureStyle) style).getBigPicture());
        }
    }

    public static class BigTextStyle extends android.app.Notification.Style {
        private java.lang.CharSequence mBigText;

        public BigTextStyle() {
        }

        @java.lang.Deprecated
        public BigTextStyle(android.app.Notification.Builder builder) {
            setBuilder(builder);
        }

        public android.app.Notification.BigTextStyle setBigContentTitle(java.lang.CharSequence charSequence) {
            internalSetBigContentTitle(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.BigTextStyle setSummaryText(java.lang.CharSequence charSequence) {
            internalSetSummaryText(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.BigTextStyle bigText(java.lang.CharSequence charSequence) {
            this.mBigText = android.app.Notification.safeCharSequence(charSequence);
            return this;
        }

        public java.lang.CharSequence getBigText() {
            return this.mBigText;
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            bundle.putCharSequence(android.app.Notification.EXTRA_BIG_TEXT, this.mBigText);
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mBigText = bundle.getCharSequence(android.app.Notification.EXTRA_BIG_TEXT);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            if (z) {
                java.util.ArrayList arrayList = this.mBuilder.mActions;
                this.mBuilder.mActions = new java.util.ArrayList();
                android.widget.RemoteViews makeBigContentView = makeBigContentView();
                this.mBuilder.mActions = arrayList;
                return makeBigContentView;
            }
            return super.makeContentView(z);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            if (z && this.mBuilder.mActions.size() > 0) {
                return makeBigContentView();
            }
            return super.makeHeadsUpContentView(z);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).allowTextWithProgress(true).textViewId(com.android.internal.R.id.big_text).fillTextsFrom(this.mBuilder);
            java.lang.CharSequence processLegacyText = this.mBuilder.processLegacyText(this.mBigText);
            if (!android.text.TextUtils.isEmpty(processLegacyText)) {
                fillTextsFrom.text(processLegacyText);
            }
            return getStandardView(this.mBuilder.getBigTextLayoutResource(), fillTextsFrom, null);
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return !java.util.Objects.equals(java.lang.String.valueOf(getBigText()), java.lang.String.valueOf(((android.app.Notification.BigTextStyle) style).getBigText()));
        }
    }

    public static class MessagingStyle extends android.app.Notification.Style {
        public static final int CONVERSATION_TYPE_IMPORTANT = 2;
        public static final int CONVERSATION_TYPE_LEGACY = 0;
        public static final int CONVERSATION_TYPE_NORMAL = 1;
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        java.lang.CharSequence mConversationTitle;
        int mConversationType;
        java.util.List<android.app.Notification.MessagingStyle.Message> mHistoricMessages;
        boolean mIsGroupConversation;
        java.util.List<android.app.Notification.MessagingStyle.Message> mMessages;
        android.graphics.drawable.Icon mShortcutIcon;
        int mUnreadMessageCount;
        android.app.Person mUser;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface ConversationType {
        }

        MessagingStyle() {
            this.mMessages = new java.util.ArrayList();
            this.mHistoricMessages = new java.util.ArrayList();
            this.mConversationType = 0;
        }

        public MessagingStyle(java.lang.CharSequence charSequence) {
            this(new android.app.Person.Builder().setName(charSequence).build());
        }

        public MessagingStyle(android.app.Person person) {
            this.mMessages = new java.util.ArrayList();
            this.mHistoricMessages = new java.util.ArrayList();
            this.mConversationType = 0;
            this.mUser = person;
        }

        @Override // android.app.Notification.Style
        public void validate(android.content.Context context) {
            super.validate(context);
            if (context.getApplicationInfo().targetSdkVersion >= 28) {
                if (this.mUser == null || this.mUser.getName() == null) {
                    throw new java.lang.RuntimeException("User must be valid and have a name.");
                }
            }
        }

        @Override // android.app.Notification.Style
        public java.lang.CharSequence getHeadsUpStatusBarText() {
            java.lang.CharSequence charSequence;
            if (!android.text.TextUtils.isEmpty(((android.app.Notification.Style) this).mBigContentTitle)) {
                charSequence = ((android.app.Notification.Style) this).mBigContentTitle;
            } else {
                charSequence = this.mConversationTitle;
            }
            if (this.mConversationType == 0 && !android.text.TextUtils.isEmpty(charSequence) && !hasOnlyWhiteSpaceSenders()) {
                return charSequence;
            }
            return null;
        }

        public android.app.Person getUser() {
            return this.mUser;
        }

        public java.lang.CharSequence getUserDisplayName() {
            return this.mUser.getName();
        }

        public android.app.Notification.MessagingStyle setConversationTitle(java.lang.CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        public java.lang.CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public android.app.Notification.MessagingStyle setShortcutIcon(android.graphics.drawable.Icon icon) {
            this.mShortcutIcon = icon;
            return this;
        }

        public android.graphics.drawable.Icon getShortcutIcon() {
            return this.mShortcutIcon;
        }

        public android.app.Notification.MessagingStyle setConversationType(int i) {
            this.mConversationType = i;
            return this;
        }

        public int getConversationType() {
            return this.mConversationType;
        }

        public int getUnreadMessageCount() {
            return this.mUnreadMessageCount;
        }

        public android.app.Notification.MessagingStyle setUnreadMessageCount(int i) {
            this.mUnreadMessageCount = i;
            return this;
        }

        public android.app.Notification.MessagingStyle addMessage(java.lang.CharSequence charSequence, long j, java.lang.CharSequence charSequence2) {
            return addMessage(charSequence, j, charSequence2 == null ? null : new android.app.Person.Builder().setName(charSequence2).build());
        }

        public android.app.Notification.MessagingStyle addMessage(java.lang.CharSequence charSequence, long j, android.app.Person person) {
            return addMessage(new android.app.Notification.MessagingStyle.Message(charSequence, j, person));
        }

        public android.app.Notification.MessagingStyle addMessage(android.app.Notification.MessagingStyle.Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public android.app.Notification.MessagingStyle addHistoricMessage(android.app.Notification.MessagingStyle.Message message) {
            this.mHistoricMessages.add(message);
            if (this.mHistoricMessages.size() > 25) {
                this.mHistoricMessages.remove(0);
            }
            return this;
        }

        public java.util.List<android.app.Notification.MessagingStyle.Message> getMessages() {
            return this.mMessages;
        }

        public java.util.List<android.app.Notification.MessagingStyle.Message> getHistoricMessages() {
            return this.mHistoricMessages;
        }

        public android.app.Notification.MessagingStyle setGroupConversation(boolean z) {
            this.mIsGroupConversation = z;
            return this;
        }

        public boolean isGroupConversation() {
            if (this.mBuilder == null || this.mBuilder.mContext.getApplicationInfo().targetSdkVersion >= 28) {
                return this.mIsGroupConversation;
            }
            return this.mConversationTitle != null;
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            addExtras(bundle, false, 0);
        }

        public void addExtras(android.os.Bundle bundle, boolean z, int i) {
            if (this.mUser != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_SELF_DISPLAY_NAME, this.mUser.getName());
                bundle.putParcelable(android.app.Notification.EXTRA_MESSAGING_PERSON, this.mUser);
            }
            if (this.mConversationTitle != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(android.app.Notification.EXTRA_MESSAGES, getBundleArrayForMessages(this.mMessages, z, i));
            }
            if (!this.mHistoricMessages.isEmpty()) {
                bundle.putParcelableArray(android.app.Notification.EXTRA_HISTORIC_MESSAGES, getBundleArrayForMessages(this.mHistoricMessages, z, i));
            }
            if (this.mShortcutIcon != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_CONVERSATION_ICON, this.mShortcutIcon);
            }
            bundle.putInt(android.app.Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT, this.mUnreadMessageCount);
            fixTitleAndTextExtras(bundle);
            bundle.putBoolean(android.app.Notification.EXTRA_IS_GROUP_CONVERSATION, this.mIsGroupConversation);
        }

        private static android.os.Bundle[] getBundleArrayForMessages(java.util.List<android.app.Notification.MessagingStyle.Message> list, boolean z, int i) {
            android.os.Bundle[] bundleArr = new android.os.Bundle[list.size()];
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                android.app.Notification.MessagingStyle.Message message = list.get(i2);
                if (z) {
                    message.ensureColorContrast(i);
                }
                bundleArr[i2] = message.toBundle();
            }
            return bundleArr;
        }

        private void fixTitleAndTextExtras(android.os.Bundle bundle) {
            android.app.Notification.MessagingStyle.Message findLatestIncomingMessage = findLatestIncomingMessage();
            java.lang.CharSequence charSequence = null;
            java.lang.CharSequence charSequence2 = findLatestIncomingMessage == null ? null : findLatestIncomingMessage.mText;
            if (findLatestIncomingMessage != null) {
                charSequence = ((findLatestIncomingMessage.mSender == null || android.text.TextUtils.isEmpty(findLatestIncomingMessage.mSender.getName())) ? this.mUser : findLatestIncomingMessage.mSender).getName();
            }
            if (!android.text.TextUtils.isEmpty(this.mConversationTitle)) {
                if (!android.text.TextUtils.isEmpty(charSequence)) {
                    android.text.BidiFormatter bidiFormatter = android.text.BidiFormatter.getInstance();
                    charSequence = this.mBuilder.mContext.getString(com.android.internal.R.string.notification_messaging_title_template, bidiFormatter.unicodeWrap(this.mConversationTitle), bidiFormatter.unicodeWrap(charSequence));
                } else {
                    charSequence = this.mConversationTitle;
                }
            }
            if (charSequence != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_TITLE, charSequence);
            }
            if (charSequence2 != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_TEXT, charSequence2);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            android.app.Person person = (android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_MESSAGING_PERSON, android.app.Person.class);
            if (person == null) {
                this.mUser = new android.app.Person.Builder().setName(bundle.getCharSequence(android.app.Notification.EXTRA_SELF_DISPLAY_NAME)).build();
            } else {
                this.mUser = person;
            }
            this.mConversationTitle = bundle.getCharSequence(android.app.Notification.EXTRA_CONVERSATION_TITLE);
            this.mMessages = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray((android.os.Parcelable[]) bundle.getParcelableArray(android.app.Notification.EXTRA_MESSAGES, android.os.Parcelable.class));
            this.mHistoricMessages = android.app.Notification.MessagingStyle.Message.getMessagesFromBundleArray((android.os.Parcelable[]) bundle.getParcelableArray(android.app.Notification.EXTRA_HISTORIC_MESSAGES, android.os.Parcelable.class));
            this.mIsGroupConversation = bundle.getBoolean(android.app.Notification.EXTRA_IS_GROUP_CONVERSATION);
            this.mUnreadMessageCount = bundle.getInt(android.app.Notification.EXTRA_CONVERSATION_UNREAD_MESSAGE_COUNT);
            this.mShortcutIcon = (android.graphics.drawable.Icon) bundle.getParcelable(android.app.Notification.EXTRA_CONVERSATION_ICON, android.graphics.drawable.Icon.class);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            java.util.ArrayList arrayList = this.mBuilder.mActions;
            try {
                this.mBuilder.mActions = new java.util.ArrayList();
                return makeMessagingView(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL);
            } finally {
                this.mBuilder.mActions = arrayList;
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            java.lang.CharSequence name;
            java.lang.CharSequence name2;
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            java.util.List<android.app.Notification.MessagingStyle.Message> messages = getMessages();
            java.util.List<android.app.Notification.MessagingStyle.Message> messages2 = ((android.app.Notification.MessagingStyle) style).getMessages();
            if (messages == null || messages2 == null) {
                messages2 = new java.util.ArrayList<>();
            }
            int size = messages.size();
            if (size != messages2.size()) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                android.app.Notification.MessagingStyle.Message message = messages.get(i);
                android.app.Notification.MessagingStyle.Message message2 = messages2.get(i);
                if (!java.util.Objects.equals(java.lang.String.valueOf(message.getText()), java.lang.String.valueOf(message2.getText())) || !java.util.Objects.equals(message.getDataUri(), message2.getDataUri())) {
                    return true;
                }
                if (message.getSenderPerson() == null) {
                    name = message.getSender();
                } else {
                    name = message.getSenderPerson().getName();
                }
                java.lang.String valueOf = java.lang.String.valueOf(name);
                if (message2.getSenderPerson() == null) {
                    name2 = message2.getSender();
                } else {
                    name2 = message2.getSenderPerson().getName();
                }
                if (!java.util.Objects.equals(valueOf, java.lang.String.valueOf(name2))) {
                    return true;
                }
                if (!java.util.Objects.equals(message.getSenderPerson() == null ? null : message.getSenderPerson().getKey(), message2.getSenderPerson() != null ? message2.getSenderPerson().getKey() : null)) {
                    return true;
                }
            }
            return false;
        }

        private android.app.Notification.MessagingStyle.Message findLatestIncomingMessage() {
            return findLatestIncomingMessage(this.mMessages);
        }

        public static android.app.Notification.MessagingStyle.Message findLatestIncomingMessage(java.util.List<android.app.Notification.MessagingStyle.Message> list) {
            for (int size = list.size() - 1; size >= 0; size--) {
                android.app.Notification.MessagingStyle.Message message = list.get(size);
                if (message.mSender != null && !android.text.TextUtils.isEmpty(message.mSender.getName())) {
                    return message;
                }
            }
            if (!list.isEmpty()) {
                return list.get(list.size() - 1);
            }
            return null;
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            return makeMessagingView(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG);
        }

        /* JADX WARN: Multi-variable type inference failed */
        private android.widget.RemoteViews makeMessagingView(int i) {
            java.lang.CharSequence charSequence;
            boolean z;
            java.lang.CharSequence charSequence2;
            int bigMessagingLayoutResource;
            boolean z2 = i != android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG;
            byte b = i != android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL;
            boolean z3 = this.mConversationType != 0;
            boolean z4 = this.mConversationType == 2;
            byte b2 = !z3 && z2;
            if (!android.text.TextUtils.isEmpty(((android.app.Notification.Style) this).mBigContentTitle)) {
                charSequence = ((android.app.Notification.Style) this).mBigContentTitle;
            } else {
                charSequence = this.mConversationTitle;
            }
            byte b3 = 0;
            if ((this.mBuilder.mContext.getApplicationInfo().targetSdkVersion >= 28) == false) {
                boolean isEmpty = android.text.TextUtils.isEmpty(charSequence);
                if (!hasOnlyWhiteSpaceSenders()) {
                    z = isEmpty;
                    charSequence2 = null;
                } else {
                    z = true;
                    charSequence2 = charSequence;
                    charSequence = null;
                }
            } else {
                z = !isGroupConversation();
                charSequence2 = null;
            }
            if (b2 != false && z && android.text.TextUtils.isEmpty(charSequence)) {
                charSequence = getOtherPersonName();
            }
            android.graphics.drawable.Icon icon = this.mBuilder.mN.mLargeIcon;
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.app.Notification.StandardTemplateParams headerTextSecondary = this.mBuilder.mParams.reset().viewType(i).highlightExpander(z3).hideProgress(true).title(b2 != false ? charSequence : null).text(null).hideLeftIcon(z).hideRightIcon(b == true || z).headerTextSecondary(b2 == true ? null : charSequence);
            android.app.Notification.Builder builder = this.mBuilder;
            if (z3) {
                bigMessagingLayoutResource = this.mBuilder.getConversationLayoutResource();
            } else if (z2) {
                bigMessagingLayoutResource = this.mBuilder.getMessagingLayoutResource();
            } else {
                bigMessagingLayoutResource = this.mBuilder.getBigMessagingLayoutResource();
            }
            android.widget.RemoteViews applyStandardTemplateWithActions = builder.applyStandardTemplateWithActions(bigMessagingLayoutResource, headerTextSecondary, templateBindResult);
            if (z3) {
                this.mBuilder.setTextViewColorPrimary(applyStandardTemplateWithActions, com.android.internal.R.id.conversation_text, headerTextSecondary);
                this.mBuilder.setTextViewColorSecondary(applyStandardTemplateWithActions, com.android.internal.R.id.app_name_divider, headerTextSecondary);
            }
            addExtras(this.mBuilder.mN.extras, true, this.mBuilder.getBackgroundColor(headerTextSecondary));
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(headerTextSecondary));
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setSenderTextColor", this.mBuilder.getPrimaryTextColor(headerTextSecondary));
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setMessageTextColor", this.mBuilder.getSecondaryTextColor(headerTextSecondary));
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(headerTextSecondary));
            applyStandardTemplateWithActions.setBoolean(com.android.internal.R.id.status_bar_latest_event_content, "setIsCollapsed", z2);
            applyStandardTemplateWithActions.setIcon(com.android.internal.R.id.status_bar_latest_event_content, "setAvatarReplacement", this.mBuilder.mN.mLargeIcon);
            applyStandardTemplateWithActions.setCharSequence(com.android.internal.R.id.status_bar_latest_event_content, "setNameReplacement", charSequence2);
            applyStandardTemplateWithActions.setBoolean(com.android.internal.R.id.status_bar_latest_event_content, "setIsOneToOne", z);
            applyStandardTemplateWithActions.setCharSequence(com.android.internal.R.id.status_bar_latest_event_content, "setConversationTitle", charSequence);
            if (z3) {
                applyStandardTemplateWithActions.setIcon(com.android.internal.R.id.status_bar_latest_event_content, "setShortcutIcon", this.mShortcutIcon);
                applyStandardTemplateWithActions.setBoolean(com.android.internal.R.id.status_bar_latest_event_content, "setIsImportantConversation", z4);
            }
            if (b2 != false) {
                applyStandardTemplateWithActions.setInt(com.android.internal.R.id.notification_messaging, "setMaxDisplayedLines", 1);
            }
            applyStandardTemplateWithActions.setIcon(com.android.internal.R.id.status_bar_latest_event_content, "setLargeIcon", icon);
            applyStandardTemplateWithActions.setBundle(com.android.internal.R.id.status_bar_latest_event_content, "setData", this.mBuilder.mN.extras);
            return applyStandardTemplateWithActions;
        }

        private java.lang.CharSequence getKey(android.app.Person person) {
            if (person == null) {
                return null;
            }
            return person.getKey() == null ? person.getName() : person.getKey();
        }

        private java.lang.CharSequence getOtherPersonName() {
            java.lang.CharSequence key = getKey(this.mUser);
            for (int size = this.mMessages.size() - 1; size >= 0; size--) {
                android.app.Person senderPerson = this.mMessages.get(size).getSenderPerson();
                if (senderPerson != null && !android.text.TextUtils.equals(key, getKey(senderPerson))) {
                    return senderPerson.getName();
                }
            }
            return null;
        }

        private boolean hasOnlyWhiteSpaceSenders() {
            for (int i = 0; i < this.mMessages.size(); i++) {
                android.app.Person senderPerson = this.mMessages.get(i).getSenderPerson();
                if (senderPerson != null && !isWhiteSpace(senderPerson.getName())) {
                    return false;
                }
            }
            return true;
        }

        private boolean isWhiteSpace(java.lang.CharSequence charSequence) {
            if (android.text.TextUtils.isEmpty(charSequence) || charSequence.toString().matches("^\\s*$")) {
                return true;
            }
            for (int i = 0; i < charSequence.length(); i++) {
                if (charSequence.charAt(i) != 8203) {
                    return false;
                }
            }
            return true;
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            return makeMessagingView(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP);
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(android.content.Context context) {
            super.reduceImageSizes(context);
            android.content.res.Resources resources = context.getResources();
            boolean isLowRamDeviceStatic = android.app.ActivityManager.isLowRamDeviceStatic();
            if (this.mShortcutIcon != null) {
                int dimensionPixelSize = resources.getDimensionPixelSize(isLowRamDeviceStatic ? com.android.internal.R.dimen.notification_small_icon_size_low_ram : com.android.internal.R.dimen.notification_small_icon_size);
                this.mShortcutIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
            int dimensionPixelSize2 = resources.getDimensionPixelSize(isLowRamDeviceStatic ? com.android.internal.R.dimen.notification_person_icon_max_size_low_ram : com.android.internal.R.dimen.notification_person_icon_max_size);
            if (this.mUser != null && this.mUser.getIcon() != null) {
                this.mUser.getIcon().scaleDownIfNecessary(dimensionPixelSize2, dimensionPixelSize2);
            }
            reduceMessagesIconSizes(this.mMessages, dimensionPixelSize2);
            reduceMessagesIconSizes(this.mHistoricMessages, dimensionPixelSize2);
        }

        private static void reduceMessagesIconSizes(java.util.List<android.app.Notification.MessagingStyle.Message> list, int i) {
            android.graphics.drawable.Icon icon;
            if (list == null) {
                return;
            }
            java.util.Iterator<android.app.Notification.MessagingStyle.Message> it = list.iterator();
            while (it.hasNext()) {
                android.app.Person person = it.next().mSender;
                if (person != null && (icon = person.getIcon()) != null) {
                    icon.scaleDownIfNecessary(i, i);
                }
            }
        }

        public static final class Message {
            static final java.lang.String KEY_DATA_MIME_TYPE = "type";
            static final java.lang.String KEY_DATA_URI = "uri";
            static final java.lang.String KEY_EXTRAS_BUNDLE = "extras";
            static final java.lang.String KEY_REMOTE_INPUT_HISTORY = "remote_input_history";
            static final java.lang.String KEY_SENDER = "sender";
            static final java.lang.String KEY_SENDER_PERSON = "sender_person";
            public static final java.lang.String KEY_TEXT = "text";
            static final java.lang.String KEY_TIMESTAMP = "time";
            private java.lang.String mDataMimeType;
            private android.net.Uri mDataUri;
            private android.os.Bundle mExtras;
            private final boolean mRemoteInputHistory;
            private final android.app.Person mSender;
            private java.lang.CharSequence mText;
            private final long mTimestamp;

            public Message(java.lang.CharSequence charSequence, long j, java.lang.CharSequence charSequence2) {
                this(charSequence, j, charSequence2 == null ? null : new android.app.Person.Builder().setName(charSequence2).build());
            }

            public Message(java.lang.CharSequence charSequence, long j, android.app.Person person) {
                this(charSequence, j, person, false);
            }

            public Message(java.lang.CharSequence charSequence, long j, android.app.Person person, boolean z) {
                this.mExtras = new android.os.Bundle();
                this.mText = android.app.Notification.safeCharSequence(charSequence);
                this.mTimestamp = j;
                this.mSender = person;
                this.mRemoteInputHistory = z;
            }

            public android.app.Notification.MessagingStyle.Message setData(java.lang.String str, android.net.Uri uri) {
                this.mDataMimeType = str;
                this.mDataUri = uri;
                return this;
            }

            public void ensureColorContrast(int i) {
                this.mText = com.android.internal.util.ContrastColorUtil.ensureColorSpanContrast(this.mText, i);
            }

            public java.lang.CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public android.os.Bundle getExtras() {
                return this.mExtras;
            }

            public java.lang.CharSequence getSender() {
                if (this.mSender == null) {
                    return null;
                }
                return this.mSender.getName();
            }

            public android.app.Person getSenderPerson() {
                return this.mSender;
            }

            public java.lang.String getDataMimeType() {
                return this.mDataMimeType;
            }

            public android.net.Uri getDataUri() {
                return this.mDataUri;
            }

            public boolean isRemoteInputHistory() {
                return this.mRemoteInputHistory;
            }

            public android.os.Bundle toBundle() {
                android.os.Bundle bundle = new android.os.Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence("text", this.mText);
                }
                bundle.putLong("time", this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence("sender", android.app.Notification.safeCharSequence(this.mSender.getName()));
                    bundle.putParcelable(KEY_SENDER_PERSON, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString("type", this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable("uri", this.mDataUri);
                }
                if (this.mExtras != null) {
                    bundle.putBundle("extras", this.mExtras);
                }
                if (this.mRemoteInputHistory) {
                    bundle.putBoolean(KEY_REMOTE_INPUT_HISTORY, this.mRemoteInputHistory);
                }
                return bundle;
            }

            public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
                consumer.accept(getDataUri());
                if (this.mSender != null) {
                    this.mSender.visitUris(consumer);
                }
            }

            public static java.util.List<android.app.Notification.MessagingStyle.Message> getMessagesFromBundleArray(android.os.Parcelable[] parcelableArr) {
                android.app.Notification.MessagingStyle.Message messageFromBundle;
                if (parcelableArr == null) {
                    return new java.util.ArrayList();
                }
                java.util.ArrayList arrayList = new java.util.ArrayList(parcelableArr.length);
                for (int i = 0; i < parcelableArr.length; i++) {
                    if ((parcelableArr[i] instanceof android.os.Bundle) && (messageFromBundle = getMessageFromBundle((android.os.Bundle) parcelableArr[i])) != null) {
                        arrayList.add(messageFromBundle);
                    }
                }
                return arrayList;
            }

            public static android.app.Notification.MessagingStyle.Message getMessageFromBundle(android.os.Bundle bundle) {
                android.app.Person person;
                java.lang.CharSequence charSequence;
                try {
                    if (bundle.containsKey("text") && bundle.containsKey("time")) {
                        android.app.Person person2 = (android.app.Person) bundle.getParcelable(KEY_SENDER_PERSON, android.app.Person.class);
                        if (person2 == null && (charSequence = bundle.getCharSequence("sender")) != null) {
                            person = new android.app.Person.Builder().setName(charSequence).build();
                        } else {
                            person = person2;
                        }
                        android.app.Notification.MessagingStyle.Message message = new android.app.Notification.MessagingStyle.Message(bundle.getCharSequence("text"), bundle.getLong("time"), person, bundle.getBoolean(KEY_REMOTE_INPUT_HISTORY, false));
                        if (bundle.containsKey("type") && bundle.containsKey("uri")) {
                            message.setData(bundle.getString("type"), (android.net.Uri) bundle.getParcelable("uri", android.net.Uri.class));
                        }
                        if (bundle.containsKey("extras")) {
                            message.getExtras().putAll(bundle.getBundle("extras"));
                        }
                        return message;
                    }
                    return null;
                } catch (java.lang.ClassCastException e) {
                    return null;
                }
            }
        }
    }

    public static class InboxStyle extends android.app.Notification.Style {
        private static final int NUMBER_OF_HISTORY_ALLOWED_UNTIL_REDUCTION = 1;
        private java.util.ArrayList<java.lang.CharSequence> mTexts = new java.util.ArrayList<>(5);

        public InboxStyle() {
        }

        @java.lang.Deprecated
        public InboxStyle(android.app.Notification.Builder builder) {
            setBuilder(builder);
        }

        public android.app.Notification.InboxStyle setBigContentTitle(java.lang.CharSequence charSequence) {
            internalSetBigContentTitle(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.InboxStyle setSummaryText(java.lang.CharSequence charSequence) {
            internalSetSummaryText(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public android.app.Notification.InboxStyle addLine(java.lang.CharSequence charSequence) {
            this.mTexts.add(android.app.Notification.safeCharSequence(charSequence));
            return this;
        }

        public java.util.ArrayList<java.lang.CharSequence> getLines() {
            return this.mTexts;
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            bundle.putCharSequenceArray(android.app.Notification.EXTRA_TEXT_LINES, (java.lang.CharSequence[]) this.mTexts.toArray(new java.lang.CharSequence[this.mTexts.size()]));
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mTexts.clear();
            if (bundle.containsKey(android.app.Notification.EXTRA_TEXT_LINES)) {
                java.util.Collections.addAll(this.mTexts, bundle.getCharSequenceArray(android.app.Notification.EXTRA_TEXT_LINES));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00e2 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00e7  */
        @Override // android.app.Notification.Style
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public android.widget.RemoteViews makeBigContentView() {
            boolean z;
            int i;
            int i2;
            java.lang.CharSequence charSequence;
            android.app.Notification.StandardTemplateParams text = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).fillTextsFrom(this.mBuilder).text(null);
            android.widget.RemoteViews standardView = getStandardView(this.mBuilder.getInboxLayoutResource(), text, new android.app.Notification.TemplateBindResult());
            int i3 = 7;
            int[] iArr = {com.android.internal.R.id.inbox_text0, com.android.internal.R.id.inbox_text1, com.android.internal.R.id.inbox_text2, com.android.internal.R.id.inbox_text3, com.android.internal.R.id.inbox_text4, com.android.internal.R.id.inbox_text5, com.android.internal.R.id.inbox_text6};
            for (int i4 = 0; i4 < 7; i4++) {
                standardView.setViewVisibility(iArr[i4], 8);
            }
            int dimensionPixelSize = this.mBuilder.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_inbox_item_top_padding);
            if (this.mBuilder.mActions.size() > 0) {
                i3 = 6;
            }
            android.app.RemoteInputHistoryItem[] remoteInputHistoryItemArr = (android.app.RemoteInputHistoryItem[]) android.app.Notification.getParcelableArrayFromBundle(this.mBuilder.mN.extras, android.app.Notification.EXTRA_REMOTE_INPUT_HISTORY_ITEMS, android.app.RemoteInputHistoryItem.class);
            if (remoteInputHistoryItemArr != null && remoteInputHistoryItemArr.length > 1) {
                int size = (this.mTexts.size() + java.lang.Math.min(remoteInputHistoryItemArr.length, 3)) - 1;
                if (size > i3) {
                    int i5 = size - i3;
                    if (this.mTexts.size() > i3) {
                        i3 -= i5;
                        z = true;
                        i = 0;
                        i2 = 0;
                    } else {
                        z = true;
                        i2 = i5;
                        i = 0;
                    }
                    while (i2 < this.mTexts.size() && i2 < i3) {
                        charSequence = this.mTexts.get(i2);
                        if (android.text.TextUtils.isEmpty(charSequence)) {
                            standardView.setViewVisibility(iArr[i2], 0);
                            standardView.setTextViewText(iArr[i2], this.mBuilder.ensureColorSpanContrast(this.mBuilder.processLegacyText(charSequence), text));
                            this.mBuilder.setTextViewColorSecondary(standardView, iArr[i2], text);
                            standardView.setViewPadding(iArr[i2], 0, dimensionPixelSize, 0, 0);
                            if (z) {
                                i = iArr[i2];
                            } else {
                                i = 0;
                            }
                            z = false;
                        }
                        i2++;
                    }
                    if (i != 0) {
                        standardView.setViewPadding(i, 0, this.mBuilder.mContext.getResources().getDimensionPixelSize(com.android.internal.R.dimen.notification_text_margin_top), 0, 0);
                    }
                    return standardView;
                }
            }
            z = true;
            i = 0;
            i2 = 0;
            while (i2 < this.mTexts.size()) {
                charSequence = this.mTexts.get(i2);
                if (android.text.TextUtils.isEmpty(charSequence)) {
                }
                i2++;
            }
            if (i != 0) {
            }
            return standardView;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            java.util.ArrayList<java.lang.CharSequence> lines = getLines();
            java.util.ArrayList<java.lang.CharSequence> lines2 = ((android.app.Notification.InboxStyle) style).getLines();
            int size = lines.size();
            if (size != lines2.size()) {
                return true;
            }
            for (int i = 0; i < size; i++) {
                if (!java.util.Objects.equals(java.lang.String.valueOf(lines.get(i)), java.lang.String.valueOf(lines2.get(i)))) {
                    return true;
                }
            }
            return false;
        }
    }

    public static class MediaStyle extends android.app.Notification.Style {
        static final int MAX_MEDIA_BUTTONS = 5;
        static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
        private static final int[] MEDIA_BUTTON_IDS = {com.android.internal.R.id.action0, com.android.internal.R.id.action1, com.android.internal.R.id.action2, com.android.internal.R.id.action3, com.android.internal.R.id.action4};
        private int[] mActionsToShowInCompact = null;
        private int mDeviceIcon;
        private android.app.PendingIntent mDeviceIntent;
        private java.lang.CharSequence mDeviceName;
        private android.media.session.MediaSession.Token mToken;

        public MediaStyle() {
        }

        @java.lang.Deprecated
        public MediaStyle(android.app.Notification.Builder builder) {
            setBuilder(builder);
        }

        public android.app.Notification.MediaStyle setShowActionsInCompactView(int... iArr) {
            this.mActionsToShowInCompact = iArr;
            return this;
        }

        public android.app.Notification.MediaStyle setMediaSession(android.media.session.MediaSession.Token token) {
            this.mToken = token;
            return this;
        }

        public android.app.Notification.MediaStyle setRemotePlaybackInfo(java.lang.CharSequence charSequence, int i, android.app.PendingIntent pendingIntent) {
            this.mDeviceName = charSequence;
            this.mDeviceIcon = i;
            this.mDeviceIntent = pendingIntent;
            return this;
        }

        @Override // android.app.Notification.Style
        public android.app.Notification buildStyled(android.app.Notification notification) {
            super.buildStyled(notification);
            if (notification.category == null) {
                notification.category = android.app.Notification.CATEGORY_TRANSPORT;
            }
            return notification;
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            return makeMediaBigContentView(null);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            return makeMediaContentView(null);
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            if (this.mToken != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_MEDIA_SESSION, this.mToken);
            }
            if (this.mActionsToShowInCompact != null) {
                bundle.putIntArray(android.app.Notification.EXTRA_COMPACT_ACTIONS, this.mActionsToShowInCompact);
            }
            if (this.mDeviceName != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_MEDIA_REMOTE_DEVICE, this.mDeviceName);
            }
            if (this.mDeviceIcon > 0) {
                bundle.putInt(android.app.Notification.EXTRA_MEDIA_REMOTE_ICON, this.mDeviceIcon);
            }
            if (this.mDeviceIntent != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_MEDIA_REMOTE_INTENT, this.mDeviceIntent);
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            if (bundle.containsKey(android.app.Notification.EXTRA_MEDIA_SESSION)) {
                this.mToken = (android.media.session.MediaSession.Token) bundle.getParcelable(android.app.Notification.EXTRA_MEDIA_SESSION, android.media.session.MediaSession.Token.class);
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_COMPACT_ACTIONS)) {
                this.mActionsToShowInCompact = bundle.getIntArray(android.app.Notification.EXTRA_COMPACT_ACTIONS);
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_MEDIA_REMOTE_DEVICE)) {
                this.mDeviceName = bundle.getCharSequence(android.app.Notification.EXTRA_MEDIA_REMOTE_DEVICE);
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_MEDIA_REMOTE_ICON)) {
                this.mDeviceIcon = bundle.getInt(android.app.Notification.EXTRA_MEDIA_REMOTE_ICON);
            }
            if (bundle.containsKey(android.app.Notification.EXTRA_MEDIA_REMOTE_INTENT)) {
                this.mDeviceIntent = (android.app.PendingIntent) bundle.getParcelable(android.app.Notification.EXTRA_MEDIA_REMOTE_INTENT, android.app.PendingIntent.class);
            }
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return false;
        }

        private void bindMediaActionButton(android.widget.RemoteViews remoteViews, int i, android.app.Notification.Action action, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            boolean z = action.actionIntent == null;
            remoteViews.setViewVisibility(i, 0);
            remoteViews.setImageViewIcon(i, action.getIcon());
            int standardActionColor = this.mBuilder.getStandardActionColor(standardTemplateParams);
            remoteViews.setDrawableTint(i, false, standardActionColor, android.graphics.PorterDuff.Mode.SRC_ATOP);
            remoteViews.setRippleDrawableColor(i, android.content.res.ColorStateList.valueOf(android.graphics.Color.argb(this.mBuilder.getColors(standardTemplateParams).getRippleAlpha(), android.graphics.Color.red(standardActionColor), android.graphics.Color.green(standardActionColor), android.graphics.Color.blue(standardActionColor))));
            if (!z) {
                remoteViews.setOnClickPendingIntent(i, action.actionIntent);
            }
            remoteViews.setContentDescription(i, action.title);
        }

        protected android.widget.RemoteViews makeMediaContentView(android.widget.RemoteViews remoteViews) {
            int size = this.mBuilder.mActions.size();
            int min = java.lang.Math.min(this.mActionsToShowInCompact == null ? 0 : this.mActionsToShowInCompact.length, 3);
            if (min > size) {
                throw new java.lang.IllegalArgumentException(java.lang.String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", java.lang.Integer.valueOf(size), java.lang.Integer.valueOf(size - 1)));
            }
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL).hideTime(min > 1).hideSubText(min > 1).hideLeftIcon(false).hideRightIcon(min > 0).hideProgress(true).fillTextsFrom(this.mBuilder);
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.widget.RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(com.android.internal.R.layout.notification_template_material_media, fillTextsFrom, null);
            for (int i = 0; i < 3; i++) {
                if (i < min) {
                    bindMediaActionButton(applyStandardTemplate, MEDIA_BUTTON_IDS[i], (android.app.Notification.Action) this.mBuilder.mActions.get(this.mActionsToShowInCompact[i]), fillTextsFrom);
                } else {
                    applyStandardTemplate.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            applyStandardTemplate.setViewVisibility(com.android.internal.R.id.media_actions, min != 0 ? 0 : 8);
            android.app.Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        protected android.widget.RemoteViews makeMediaBigContentView(android.widget.RemoteViews remoteViews) {
            int min = java.lang.Math.min(this.mBuilder.mActions.size(), 5);
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).hideProgress(true).fillTextsFrom(this.mBuilder);
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.widget.RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(com.android.internal.R.layout.notification_template_material_big_media, fillTextsFrom, templateBindResult);
            for (int i = 0; i < 5; i++) {
                if (i < min) {
                    bindMediaActionButton(applyStandardTemplate, MEDIA_BUTTON_IDS[i], (android.app.Notification.Action) this.mBuilder.mActions.get(i), fillTextsFrom);
                } else {
                    applyStandardTemplate.setViewVisibility(MEDIA_BUTTON_IDS[i], 8);
                }
            }
            android.app.Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }
    }

    public static class CallStyle extends android.app.Notification.Style {
        public static final int CALL_TYPE_INCOMING = 1;
        public static final int CALL_TYPE_ONGOING = 2;
        public static final int CALL_TYPE_SCREENING = 3;
        public static final int CALL_TYPE_UNKNOWN = 0;
        public static final boolean DEBUG_NEW_ACTION_LAYOUT = true;
        private static final java.lang.String KEY_ACTION_PRIORITY = "key_action_priority";
        private java.lang.Integer mAnswerButtonColor;
        private android.app.PendingIntent mAnswerIntent;
        private int mCallType;
        private java.lang.Integer mDeclineButtonColor;
        private android.app.PendingIntent mDeclineIntent;
        private android.app.PendingIntent mHangUpIntent;
        private boolean mIsVideo;
        private android.app.Person mPerson;
        private android.graphics.drawable.Icon mVerificationIcon;
        private java.lang.CharSequence mVerificationText;

        @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
        public @interface CallType {
        }

        CallStyle() {
        }

        public static android.app.Notification.CallStyle forIncomingCall(android.app.Person person, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
            return new android.app.Notification.CallStyle(1, person, null, (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent, "declineIntent is required"), (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent2, "answerIntent is required"));
        }

        public static android.app.Notification.CallStyle forOngoingCall(android.app.Person person, android.app.PendingIntent pendingIntent) {
            return new android.app.Notification.CallStyle(2, person, (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent, "hangUpIntent is required"), null, null);
        }

        public static android.app.Notification.CallStyle forScreeningCall(android.app.Person person, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2) {
            return new android.app.Notification.CallStyle(3, person, (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent, "hangUpIntent is required"), null, (android.app.PendingIntent) java.util.Objects.requireNonNull(pendingIntent2, "answerIntent is required"));
        }

        private CallStyle(int i, android.app.Person person, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, android.app.PendingIntent pendingIntent3) {
            if (person == null || android.text.TextUtils.isEmpty(person.getName())) {
                throw new java.lang.IllegalArgumentException("person must have a non-empty a name");
            }
            this.mCallType = i;
            this.mPerson = person;
            this.mAnswerIntent = pendingIntent3;
            this.mDeclineIntent = pendingIntent2;
            this.mHangUpIntent = pendingIntent;
        }

        public android.app.Notification.CallStyle setIsVideo(boolean z) {
            this.mIsVideo = z;
            return this;
        }

        public android.app.Notification.CallStyle setVerificationIcon(android.graphics.drawable.Icon icon) {
            this.mVerificationIcon = icon;
            return this;
        }

        public android.app.Notification.CallStyle setVerificationText(java.lang.CharSequence charSequence) {
            this.mVerificationText = android.app.Notification.safeCharSequence(charSequence);
            return this;
        }

        public android.app.Notification.CallStyle setAnswerButtonColorHint(int i) {
            this.mAnswerButtonColor = java.lang.Integer.valueOf(i);
            return this;
        }

        public android.app.Notification.CallStyle setDeclineButtonColorHint(int i) {
            this.mDeclineButtonColor = java.lang.Integer.valueOf(i);
            return this;
        }

        @Override // android.app.Notification.Style
        public android.app.Notification buildStyled(android.app.Notification notification) {
            android.app.Notification buildStyled = super.buildStyled(notification);
            this.mBuilder.mActions = getActionsListWithSystemActions();
            buildStyled.actions = new android.app.Notification.Action[this.mBuilder.mActions.size()];
            this.mBuilder.mActions.toArray(buildStyled.actions);
            return buildStyled;
        }

        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.Style
        public void purgeResources() {
            super.purgeResources();
            if (this.mVerificationIcon != null) {
                this.mVerificationIcon.convertToAshmem();
            }
        }

        @Override // android.app.Notification.Style
        public void reduceImageSizes(android.content.Context context) {
            int i;
            super.reduceImageSizes(context);
            if (this.mVerificationIcon != null) {
                android.content.res.Resources resources = context.getResources();
                if (android.app.ActivityManager.isLowRamDeviceStatic()) {
                    i = com.android.internal.R.dimen.notification_right_icon_size_low_ram;
                } else {
                    i = com.android.internal.R.dimen.notification_right_icon_size;
                }
                int dimensionPixelSize = resources.getDimensionPixelSize(i);
                this.mVerificationIcon.scaleDownIfNecessary(dimensionPixelSize, dimensionPixelSize);
            }
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            return makeCallLayout(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            return makeCallLayout(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            return makeCallLayout(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG);
        }

        private android.app.Notification.Action makeNegativeAction() {
            if (this.mDeclineIntent == null) {
                return makeAction(com.android.internal.R.drawable.ic_call_decline, com.android.internal.R.string.call_notification_hang_up_action, this.mDeclineButtonColor, com.android.internal.R.color.call_notification_decline_color, this.mHangUpIntent);
            }
            return makeAction(com.android.internal.R.drawable.ic_call_decline, com.android.internal.R.string.call_notification_decline_action, this.mDeclineButtonColor, com.android.internal.R.color.call_notification_decline_color, this.mDeclineIntent);
        }

        private android.app.Notification.Action makeAnswerAction() {
            if (this.mAnswerIntent == null) {
                return null;
            }
            return makeAction(this.mIsVideo ? com.android.internal.R.drawable.ic_call_answer_video : com.android.internal.R.drawable.ic_call_answer, this.mIsVideo ? 17039782 : 17039781, this.mAnswerButtonColor, com.android.internal.R.color.call_notification_answer_color, this.mAnswerIntent);
        }

        private android.app.Notification.Action makeAction(int i, int i2, java.lang.Integer num, int i3, android.app.PendingIntent pendingIntent) {
            if (num == null || !this.mBuilder.isCallActionColorCustomizable()) {
                num = java.lang.Integer.valueOf(this.mBuilder.mContext.getColor(i3));
            }
            android.app.Notification.Action build = new android.app.Notification.Action.Builder(android.graphics.drawable.Icon.createWithResource("", i), new android.text.SpannableStringBuilder().append(this.mBuilder.mContext.getString(i2), new android.text.style.ForegroundColorSpan(num.intValue()), 18), pendingIntent).build();
            build.getExtras().putBoolean(KEY_ACTION_PRIORITY, true);
            return build;
        }

        private boolean isActionAddedByCallStyle(android.app.Notification.Action action) {
            return action != null && action.getExtras().getBoolean(KEY_ACTION_PRIORITY);
        }

        public java.util.ArrayList<android.app.Notification.Action> getActionsListWithSystemActions() {
            android.app.Notification.Action makeNegativeAction = makeNegativeAction();
            android.app.Notification.Action makeAnswerAction = makeAnswerAction();
            java.util.ArrayList<android.app.Notification.Action> arrayList = new java.util.ArrayList<>(3);
            arrayList.add(makeNegativeAction);
            int i = 2;
            if (this.mBuilder.mActions != null) {
                java.util.Iterator it = this.mBuilder.mActions.iterator();
                while (it.hasNext()) {
                    android.app.Notification.Action action = (android.app.Notification.Action) it.next();
                    if (action.isContextual()) {
                        arrayList.add(action);
                    } else if (!isActionAddedByCallStyle(action)) {
                        arrayList.add(action);
                        i--;
                    }
                    if (makeAnswerAction != null && i == 1) {
                        arrayList.add(makeAnswerAction);
                        i--;
                    }
                }
            }
            if (makeAnswerAction != null && i >= 1) {
                arrayList.add(makeAnswerAction);
            }
            return arrayList;
        }

        private android.widget.RemoteViews makeCallLayout(int i) {
            android.widget.RemoteViews applyStandardTemplateWithActions;
            boolean z = i == android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL;
            android.os.Bundle bundle = this.mBuilder.mN.extras;
            java.lang.CharSequence name = this.mPerson != null ? this.mPerson.getName() : null;
            java.lang.CharSequence processLegacyText = this.mBuilder.processLegacyText(bundle.getCharSequence(android.app.Notification.EXTRA_TEXT));
            if (processLegacyText == null) {
                processLegacyText = getDefaultText();
            }
            android.app.Notification.StandardTemplateParams summaryText = this.mBuilder.mParams.reset().viewType(i).callStyleActions(true).allowTextWithProgress(true).hideLeftIcon(true).hideRightIcon(true).hideAppName(z).titleViewId(com.android.internal.R.id.conversation_text).title(name).text(processLegacyText).summaryText(this.mBuilder.processLegacyText(this.mVerificationText));
            this.mBuilder.mActions = getActionsListWithSystemActions();
            if (z) {
                applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplate(com.android.internal.R.layout.notification_template_material_call, summaryText, null);
            } else {
                applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(com.android.internal.R.layout.notification_template_material_big_call, summaryText, null);
            }
            if (!summaryText.mHideAppName) {
                this.mBuilder.setTextViewColorSecondary(applyStandardTemplateWithActions, com.android.internal.R.id.app_name_divider, summaryText);
                applyStandardTemplateWithActions.setViewVisibility(com.android.internal.R.id.app_name_divider, 0);
            }
            bindCallerVerification(applyStandardTemplateWithActions, summaryText);
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setLayoutColor", this.mBuilder.getSmallIconColor(summaryText));
            applyStandardTemplateWithActions.setInt(com.android.internal.R.id.status_bar_latest_event_content, "setNotificationBackgroundColor", this.mBuilder.getBackgroundColor(summaryText));
            applyStandardTemplateWithActions.setIcon(com.android.internal.R.id.status_bar_latest_event_content, "setLargeIcon", this.mBuilder.mN.mLargeIcon);
            applyStandardTemplateWithActions.setBundle(com.android.internal.R.id.status_bar_latest_event_content, "setData", this.mBuilder.mN.extras);
            return applyStandardTemplateWithActions;
        }

        private void bindCallerVerification(android.widget.RemoteViews remoteViews, android.app.Notification.StandardTemplateParams standardTemplateParams) {
            boolean z;
            java.lang.String str;
            java.lang.String str2 = null;
            if (this.mVerificationIcon != null) {
                remoteViews.setImageViewIcon(com.android.internal.R.id.verification_icon, this.mVerificationIcon);
                remoteViews.setDrawableTint(com.android.internal.R.id.verification_icon, false, this.mBuilder.getSecondaryTextColor(standardTemplateParams), android.graphics.PorterDuff.Mode.SRC_ATOP);
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_icon, 0);
                str = this.mBuilder.mContext.getString(com.android.internal.R.string.notification_verified_content_description);
                z = false;
            } else {
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_icon, 8);
                z = true;
                str = null;
            }
            if (!android.text.TextUtils.isEmpty(this.mVerificationText)) {
                remoteViews.setTextViewText(com.android.internal.R.id.verification_text, this.mVerificationText);
                this.mBuilder.setTextViewColorSecondary(remoteViews, com.android.internal.R.id.verification_text, standardTemplateParams);
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_text, 0);
            } else {
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_text, 8);
                str2 = str;
                z = false;
            }
            remoteViews.setContentDescription(com.android.internal.R.id.verification_icon, str2);
            if (z) {
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_divider, 0);
                this.mBuilder.setTextViewColorSecondary(remoteViews, com.android.internal.R.id.verification_divider, standardTemplateParams);
            } else {
                remoteViews.setViewVisibility(com.android.internal.R.id.verification_divider, 8);
            }
        }

        private java.lang.String getDefaultText() {
            switch (this.mCallType) {
                case 1:
                    return this.mBuilder.mContext.getString(com.android.internal.R.string.call_notification_incoming_text);
                case 2:
                    return this.mBuilder.mContext.getString(com.android.internal.R.string.call_notification_ongoing_text);
                case 3:
                    return this.mBuilder.mContext.getString(com.android.internal.R.string.call_notification_screening_text);
                default:
                    return null;
            }
        }

        @Override // android.app.Notification.Style
        public void addExtras(android.os.Bundle bundle) {
            super.addExtras(bundle);
            bundle.putInt(android.app.Notification.EXTRA_CALL_TYPE, this.mCallType);
            bundle.putBoolean(android.app.Notification.EXTRA_CALL_IS_VIDEO, this.mIsVideo);
            bundle.putParcelable(android.app.Notification.EXTRA_CALL_PERSON, this.mPerson);
            if (this.mVerificationIcon != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_VERIFICATION_ICON, this.mVerificationIcon);
            }
            if (this.mVerificationText != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_VERIFICATION_TEXT, this.mVerificationText);
            }
            if (this.mAnswerIntent != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_ANSWER_INTENT, this.mAnswerIntent);
            }
            if (this.mDeclineIntent != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_DECLINE_INTENT, this.mDeclineIntent);
            }
            if (this.mHangUpIntent != null) {
                bundle.putParcelable(android.app.Notification.EXTRA_HANG_UP_INTENT, this.mHangUpIntent);
            }
            if (this.mAnswerButtonColor != null) {
                bundle.putInt(android.app.Notification.EXTRA_ANSWER_COLOR, this.mAnswerButtonColor.intValue());
            }
            if (this.mDeclineButtonColor != null) {
                bundle.putInt(android.app.Notification.EXTRA_DECLINE_COLOR, this.mDeclineButtonColor.intValue());
            }
            fixTitleAndTextExtras(bundle);
        }

        private void fixTitleAndTextExtras(android.os.Bundle bundle) {
            java.lang.CharSequence name = this.mPerson != null ? this.mPerson.getName() : null;
            if (name != null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_TITLE, name);
            }
            if (bundle.getCharSequence(android.app.Notification.EXTRA_TEXT) == null) {
                bundle.putCharSequence(android.app.Notification.EXTRA_TEXT, getDefaultText());
            }
        }

        @Override // android.app.Notification.Style
        protected void restoreFromExtras(android.os.Bundle bundle) {
            super.restoreFromExtras(bundle);
            this.mCallType = bundle.getInt(android.app.Notification.EXTRA_CALL_TYPE);
            this.mIsVideo = bundle.getBoolean(android.app.Notification.EXTRA_CALL_IS_VIDEO);
            this.mPerson = (android.app.Person) bundle.getParcelable(android.app.Notification.EXTRA_CALL_PERSON, android.app.Person.class);
            this.mVerificationIcon = (android.graphics.drawable.Icon) bundle.getParcelable(android.app.Notification.EXTRA_VERIFICATION_ICON, android.graphics.drawable.Icon.class);
            this.mVerificationText = bundle.getCharSequence(android.app.Notification.EXTRA_VERIFICATION_TEXT);
            this.mAnswerIntent = (android.app.PendingIntent) bundle.getParcelable(android.app.Notification.EXTRA_ANSWER_INTENT, android.app.PendingIntent.class);
            this.mDeclineIntent = (android.app.PendingIntent) bundle.getParcelable(android.app.Notification.EXTRA_DECLINE_INTENT, android.app.PendingIntent.class);
            this.mHangUpIntent = (android.app.PendingIntent) bundle.getParcelable(android.app.Notification.EXTRA_HANG_UP_INTENT, android.app.PendingIntent.class);
            this.mAnswerButtonColor = bundle.containsKey(android.app.Notification.EXTRA_ANSWER_COLOR) ? java.lang.Integer.valueOf(bundle.getInt(android.app.Notification.EXTRA_ANSWER_COLOR)) : null;
            this.mDeclineButtonColor = bundle.containsKey(android.app.Notification.EXTRA_DECLINE_COLOR) ? java.lang.Integer.valueOf(bundle.getInt(android.app.Notification.EXTRA_DECLINE_COLOR)) : null;
        }

        @Override // android.app.Notification.Style
        public boolean hasSummaryInHeader() {
            return false;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            android.app.Notification.CallStyle callStyle = (android.app.Notification.CallStyle) style;
            return (java.util.Objects.equals(java.lang.Integer.valueOf(this.mCallType), java.lang.Integer.valueOf(callStyle.mCallType)) && java.util.Objects.equals(this.mPerson, callStyle.mPerson) && java.util.Objects.equals(this.mVerificationText, callStyle.mVerificationText)) ? false : true;
        }
    }

    public static class DecoratedCustomViewStyle extends android.app.Notification.Style {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            return makeStandardTemplateWithCustomContent(this.mBuilder.mN.contentView);
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            return makeDecoratedBigContentView();
        }

        @Override // android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            return makeDecoratedHeadsUpContentView();
        }

        private android.widget.RemoteViews makeDecoratedHeadsUpContentView() {
            android.widget.RemoteViews remoteViews;
            if (this.mBuilder.mN.headsUpContentView == null) {
                remoteViews = this.mBuilder.mN.contentView;
            } else {
                remoteViews = this.mBuilder.mN.headsUpContentView;
            }
            if (remoteViews == null) {
                return null;
            }
            if (this.mBuilder.mActions.size() == 0) {
                return makeStandardTemplateWithCustomContent(remoteViews);
            }
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_HEADS_UP).decorationType(2).fillTextsFrom(this.mBuilder);
            android.widget.RemoteViews applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getHeadsUpBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        private android.widget.RemoteViews makeStandardTemplateWithCustomContent(android.widget.RemoteViews remoteViews) {
            if (remoteViews == null) {
                return null;
            }
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_NORMAL).decorationType(2).fillTextsFrom(this.mBuilder);
            android.widget.RemoteViews applyStandardTemplate = this.mBuilder.applyStandardTemplate(this.mBuilder.getBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplate, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplate;
        }

        private android.widget.RemoteViews makeDecoratedBigContentView() {
            android.widget.RemoteViews remoteViews;
            if (this.mBuilder.mN.bigContentView == null) {
                remoteViews = this.mBuilder.mN.contentView;
            } else {
                remoteViews = this.mBuilder.mN.bigContentView;
            }
            if (remoteViews == null) {
                return null;
            }
            android.app.Notification.TemplateBindResult templateBindResult = new android.app.Notification.TemplateBindResult();
            android.app.Notification.StandardTemplateParams fillTextsFrom = this.mBuilder.mParams.reset().viewType(android.app.Notification.StandardTemplateParams.VIEW_TYPE_BIG).decorationType(2).fillTextsFrom(this.mBuilder);
            android.widget.RemoteViews applyStandardTemplateWithActions = this.mBuilder.applyStandardTemplateWithActions(this.mBuilder.getBigBaseLayoutResource(), fillTextsFrom, templateBindResult);
            android.app.Notification.buildCustomContentIntoTemplate(this.mBuilder.mContext, applyStandardTemplateWithActions, remoteViews, fillTextsFrom, templateBindResult);
            return applyStandardTemplateWithActions;
        }

        @Override // android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return false;
        }
    }

    public static class DecoratedMediaCustomViewStyle extends android.app.Notification.MediaStyle {
        @Override // android.app.Notification.Style
        public boolean displayCustomViewInline() {
            return true;
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public android.widget.RemoteViews makeContentView(boolean z) {
            return makeMediaContentView(this.mBuilder.mN.contentView);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public android.widget.RemoteViews makeBigContentView() {
            android.widget.RemoteViews remoteViews;
            if (this.mBuilder.mN.bigContentView != null) {
                remoteViews = this.mBuilder.mN.bigContentView;
            } else {
                remoteViews = this.mBuilder.mN.contentView;
            }
            return makeMediaBigContentView(remoteViews);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public android.widget.RemoteViews makeHeadsUpContentView(boolean z) {
            android.widget.RemoteViews remoteViews;
            if (this.mBuilder.mN.headsUpContentView != null) {
                remoteViews = this.mBuilder.mN.headsUpContentView;
            } else {
                remoteViews = this.mBuilder.mN.contentView;
            }
            return makeMediaBigContentView(remoteViews);
        }

        @Override // android.app.Notification.MediaStyle, android.app.Notification.Style
        public boolean areNotificationsVisiblyDifferent(android.app.Notification.Style style) {
            if (style == null || getClass() != style.getClass()) {
                return true;
            }
            return false;
        }
    }

    public static final class BubbleMetadata implements android.os.Parcelable {
        public static final android.os.Parcelable.Creator<android.app.Notification.BubbleMetadata> CREATOR = new android.os.Parcelable.Creator<android.app.Notification.BubbleMetadata>() { // from class: android.app.Notification.BubbleMetadata.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification.BubbleMetadata createFromParcel(android.os.Parcel parcel) {
                return new android.app.Notification.BubbleMetadata(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.app.Notification.BubbleMetadata[] newArray(int i) {
                return new android.app.Notification.BubbleMetadata[i];
            }
        };
        public static final int FLAG_AUTO_EXPAND_BUBBLE = 1;
        public static final int FLAG_SUPPRESSABLE_BUBBLE = 4;
        public static final int FLAG_SUPPRESS_BUBBLE = 8;
        public static final int FLAG_SUPPRESS_NOTIFICATION = 2;
        private android.app.PendingIntent mDeleteIntent;
        private int mDesiredHeight;
        private int mDesiredHeightResId;
        private int mFlags;
        private android.graphics.drawable.Icon mIcon;
        private android.app.PendingIntent mPendingIntent;
        private java.lang.String mShortcutId;

        private BubbleMetadata(android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, android.graphics.drawable.Icon icon, int i, int i2, java.lang.String str) {
            this.mPendingIntent = pendingIntent;
            this.mIcon = icon;
            this.mDesiredHeight = i;
            this.mDesiredHeightResId = i2;
            this.mDeleteIntent = pendingIntent2;
            this.mShortcutId = str;
        }

        private BubbleMetadata(android.os.Parcel parcel) {
            if (parcel.readInt() != 0) {
                this.mPendingIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
            }
            if (parcel.readInt() != 0) {
                this.mIcon = android.graphics.drawable.Icon.CREATOR.createFromParcel(parcel);
            }
            this.mDesiredHeight = parcel.readInt();
            this.mFlags = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mDeleteIntent = android.app.PendingIntent.CREATOR.createFromParcel(parcel);
            }
            this.mDesiredHeightResId = parcel.readInt();
            if (parcel.readInt() != 0) {
                this.mShortcutId = parcel.readString8();
            }
        }

        public java.lang.String getShortcutId() {
            return this.mShortcutId;
        }

        public android.app.PendingIntent getIntent() {
            return this.mPendingIntent;
        }

        public android.app.PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public android.graphics.drawable.Icon getIcon() {
            return this.mIcon;
        }

        public int getDesiredHeight() {
            return this.mDesiredHeight;
        }

        public int getDesiredHeightResId() {
            return this.mDesiredHeightResId;
        }

        public boolean getAutoExpandBubble() {
            return (this.mFlags & 1) != 0;
        }

        public boolean isNotificationSuppressed() {
            return (this.mFlags & 2) != 0;
        }

        public boolean isBubbleSuppressable() {
            return (this.mFlags & 4) != 0;
        }

        public boolean isBubbleSuppressed() {
            return (this.mFlags & 8) != 0;
        }

        public void setSuppressNotification(boolean z) {
            if (z) {
                this.mFlags |= 2;
            } else {
                this.mFlags &= -3;
            }
        }

        public void setSuppressBubble(boolean z) {
            if (z) {
                this.mFlags |= 8;
            } else {
                this.mFlags &= -9;
            }
        }

        public void setFlags(int i) {
            this.mFlags = i;
        }

        public int getFlags() {
            return this.mFlags;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            parcel.writeInt(this.mPendingIntent != null ? 1 : 0);
            if (this.mPendingIntent != null) {
                this.mPendingIntent.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mIcon != null ? 1 : 0);
            if (this.mIcon != null) {
                this.mIcon.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mDesiredHeight);
            parcel.writeInt(this.mFlags);
            parcel.writeInt(this.mDeleteIntent != null ? 1 : 0);
            if (this.mDeleteIntent != null) {
                this.mDeleteIntent.writeToParcel(parcel, 0);
            }
            parcel.writeInt(this.mDesiredHeightResId);
            parcel.writeInt(!android.text.TextUtils.isEmpty(this.mShortcutId) ? 1 : 0);
            if (!android.text.TextUtils.isEmpty(this.mShortcutId)) {
                parcel.writeString8(this.mShortcutId);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            android.app.Notification.visitIconUri(consumer, getIcon());
            if (this.mPendingIntent != null) {
                this.mPendingIntent.visitUris(consumer);
            }
            if (this.mDeleteIntent != null) {
                this.mDeleteIntent.visitUris(consumer);
            }
        }

        public static final class Builder {
            private android.app.PendingIntent mDeleteIntent;
            private int mDesiredHeight;
            private int mDesiredHeightResId;
            private int mFlags;
            private android.graphics.drawable.Icon mIcon;
            private android.app.PendingIntent mPendingIntent;
            private java.lang.String mShortcutId;

            @java.lang.Deprecated
            public Builder() {
            }

            public Builder(java.lang.String str) {
                if (android.text.TextUtils.isEmpty(str)) {
                    throw new java.lang.NullPointerException("Bubble requires a non-null shortcut id");
                }
                this.mShortcutId = str;
            }

            public Builder(android.app.PendingIntent pendingIntent, android.graphics.drawable.Icon icon) {
                if (pendingIntent == null) {
                    throw new java.lang.NullPointerException("Bubble requires non-null pending intent");
                }
                if (icon == null) {
                    throw new java.lang.NullPointerException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    android.util.Log.w(android.app.Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mPendingIntent = pendingIntent;
                this.mIcon = icon;
            }

            public android.app.Notification.BubbleMetadata.Builder setIntent(android.app.PendingIntent pendingIntent) {
                if (this.mShortcutId != null) {
                    throw new java.lang.IllegalStateException("Created as a shortcut bubble, cannot set a PendingIntent. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (pendingIntent == null) {
                    throw new java.lang.NullPointerException("Bubble requires non-null pending intent");
                }
                this.mPendingIntent = pendingIntent;
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setIcon(android.graphics.drawable.Icon icon) {
                if (this.mShortcutId != null) {
                    throw new java.lang.IllegalStateException("Created as a shortcut bubble, cannot set an Icon. Consider using BubbleMetadata.Builder(PendingIntent,Icon) instead.");
                }
                if (icon == null) {
                    throw new java.lang.NullPointerException("Bubbles require non-null icon");
                }
                if (icon.getType() != 6 && icon.getType() != 4) {
                    android.util.Log.w(android.app.Notification.TAG, "Bubbles work best with icons of TYPE_URI or TYPE_URI_ADAPTIVE_BITMAP. In the future, using an icon of this type will be required.");
                }
                this.mIcon = icon;
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setDesiredHeight(int i) {
                this.mDesiredHeight = java.lang.Math.max(i, 0);
                this.mDesiredHeightResId = 0;
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setDesiredHeightResId(int i) {
                this.mDesiredHeightResId = i;
                this.mDesiredHeight = 0;
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setAutoExpandBubble(boolean z) {
                setFlag(1, z);
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setSuppressNotification(boolean z) {
                setFlag(2, z);
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setSuppressableBubble(boolean z) {
                setFlag(4, z);
                return this;
            }

            public android.app.Notification.BubbleMetadata.Builder setDeleteIntent(android.app.PendingIntent pendingIntent) {
                this.mDeleteIntent = pendingIntent;
                return this;
            }

            public android.app.Notification.BubbleMetadata build() {
                if (this.mShortcutId == null && this.mPendingIntent == null) {
                    throw new java.lang.NullPointerException("Must supply pending intent or shortcut to bubble");
                }
                if (this.mShortcutId == null && this.mIcon == null) {
                    throw new java.lang.NullPointerException("Must supply an icon or shortcut for the bubble");
                }
                android.app.Notification.BubbleMetadata bubbleMetadata = new android.app.Notification.BubbleMetadata(this.mPendingIntent, this.mDeleteIntent, this.mIcon, this.mDesiredHeight, this.mDesiredHeightResId, this.mShortcutId);
                bubbleMetadata.setFlags(this.mFlags);
                return bubbleMetadata;
            }

            public android.app.Notification.BubbleMetadata.Builder setFlag(int i, boolean z) {
                if (z) {
                    this.mFlags = i | this.mFlags;
                } else {
                    this.mFlags = (~i) & this.mFlags;
                }
                return this;
            }
        }
    }

    public static final class WearableExtender implements android.app.Notification.Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final java.lang.String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final java.lang.String KEY_ACTIONS = "actions";
        static final java.lang.String KEY_BACKGROUND = "background";
        private static final java.lang.String KEY_BRIDGE_TAG = "bridgeTag";
        private static final java.lang.String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final java.lang.String KEY_CONTENT_ICON = "contentIcon";
        private static final java.lang.String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final java.lang.String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final java.lang.String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final java.lang.String KEY_DISMISSAL_ID = "dismissalId";
        static final java.lang.String KEY_DISPLAY_INTENT = "displayIntent";
        private static final java.lang.String KEY_FLAGS = "flags";
        private static final java.lang.String KEY_GRAVITY = "gravity";
        private static final java.lang.String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final java.lang.String KEY_PAGES = "pages";

        @java.lang.Deprecated
        public static final int SCREEN_TIMEOUT_LONG = -1;

        @java.lang.Deprecated
        public static final int SCREEN_TIMEOUT_SHORT = 0;

        @java.lang.Deprecated
        public static final int SIZE_DEFAULT = 0;

        @java.lang.Deprecated
        public static final int SIZE_FULL_SCREEN = 5;

        @java.lang.Deprecated
        public static final int SIZE_LARGE = 4;

        @java.lang.Deprecated
        public static final int SIZE_MEDIUM = 3;

        @java.lang.Deprecated
        public static final int SIZE_SMALL = 2;

        @java.lang.Deprecated
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private java.util.ArrayList<android.app.Notification.Action> mActions;
        private android.graphics.Bitmap mBackground;
        private java.lang.String mBridgeTag;
        private int mContentActionIndex;
        private int mContentIcon;
        private int mContentIconGravity;
        private int mCustomContentHeight;
        private int mCustomSizePreset;
        private java.lang.String mDismissalId;
        private android.app.PendingIntent mDisplayIntent;
        private int mFlags;
        private int mGravity;
        private int mHintScreenTimeout;
        private java.util.ArrayList<android.app.Notification> mPages;

        public WearableExtender() {
            this.mActions = new java.util.ArrayList<>();
            this.mFlags = 1;
            this.mPages = new java.util.ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
        }

        public WearableExtender(android.app.Notification notification) {
            this.mActions = new java.util.ArrayList<>();
            this.mFlags = 1;
            this.mPages = new java.util.ArrayList<>();
            this.mContentIconGravity = 8388613;
            this.mContentActionIndex = -1;
            this.mCustomSizePreset = 0;
            this.mGravity = 80;
            android.os.Bundle bundle = notification.extras.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (bundle != null) {
                java.util.ArrayList parcelableArrayList = bundle.getParcelableArrayList("actions", android.app.Notification.Action.class);
                if (parcelableArrayList != null) {
                    this.mActions.addAll(parcelableArrayList);
                }
                this.mFlags = bundle.getInt("flags", 1);
                this.mDisplayIntent = (android.app.PendingIntent) bundle.getParcelable(KEY_DISPLAY_INTENT, android.app.PendingIntent.class);
                android.app.Notification[] notificationArr = (android.app.Notification[]) android.app.Notification.getParcelableArrayFromBundle(bundle, KEY_PAGES, android.app.Notification.class);
                if (notificationArr != null) {
                    java.util.Collections.addAll(this.mPages, notificationArr);
                }
                this.mBackground = (android.graphics.Bitmap) bundle.getParcelable("background", android.graphics.Bitmap.class);
                this.mContentIcon = bundle.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = bundle.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = bundle.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = bundle.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = bundle.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = bundle.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = bundle.getInt(KEY_HINT_SCREEN_TIMEOUT);
                this.mDismissalId = bundle.getString(KEY_DISMISSAL_ID);
                this.mBridgeTag = bundle.getString(KEY_BRIDGE_TAG);
            }
        }

        @Override // android.app.Notification.Extender
        public android.app.Notification.Builder extend(android.app.Notification.Builder builder) {
            android.os.Bundle bundle = new android.os.Bundle();
            if (!this.mActions.isEmpty()) {
                bundle.putParcelableArrayList("actions", this.mActions);
            }
            if (this.mFlags != 1) {
                bundle.putInt("flags", this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                bundle.putParcelableArray(KEY_PAGES, (android.os.Parcelable[]) this.mPages.toArray(new android.app.Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                if (android.app.compat.CompatChanges.isChangeEnabled(android.app.Notification.WEARABLE_EXTENDER_BACKGROUND_BLOCKED)) {
                    android.util.Log.d(android.app.Notification.TAG, "Use of background in WearableExtenders has been deprecated and will not be populated anymore.");
                } else {
                    bundle.putParcelable("background", this.mBackground);
                }
            }
            if (this.mContentIcon != 0) {
                bundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                bundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                bundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                bundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.app.Notification.WearableExtender m433clone() {
            android.app.Notification.WearableExtender wearableExtender = new android.app.Notification.WearableExtender();
            wearableExtender.mActions = new java.util.ArrayList<>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new java.util.ArrayList<>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        public android.app.Notification.WearableExtender addAction(android.app.Notification.Action action) {
            this.mActions.add(action);
            return this;
        }

        public android.app.Notification.WearableExtender addActions(java.util.List<android.app.Notification.Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        public android.app.Notification.WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public java.util.List<android.app.Notification.Action> getActions() {
            return this.mActions;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setDisplayIntent(android.app.PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        @java.lang.Deprecated
        public android.app.PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender addPage(android.app.Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender addPages(java.util.List<android.app.Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        @java.lang.Deprecated
        public java.util.List<android.app.Notification> getPages() {
            return this.mPages;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setBackground(android.graphics.Bitmap bitmap) {
            if (android.app.compat.CompatChanges.isChangeEnabled(android.app.Notification.WEARABLE_EXTENDER_BACKGROUND_BLOCKED)) {
                android.util.Log.d(android.app.Notification.TAG, "Use of background in WearableExtenders has been deprecated and will not be populated anymore.");
            } else {
                this.mBackground = bitmap;
            }
            return this;
        }

        @java.lang.Deprecated
        public android.graphics.Bitmap getBackground() {
            android.util.Log.w(android.app.Notification.TAG, "Use of background in WearableExtender has been removed, returning null.");
            return this.mBackground;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setContentIcon(int i) {
            this.mContentIcon = i;
            return this;
        }

        @java.lang.Deprecated
        public int getContentIcon() {
            return this.mContentIcon;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setContentIconGravity(int i) {
            this.mContentIconGravity = i;
            return this;
        }

        @java.lang.Deprecated
        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public android.app.Notification.WearableExtender setContentAction(int i) {
            this.mContentActionIndex = i;
            return this;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setGravity(int i) {
            this.mGravity = i;
            return this;
        }

        @java.lang.Deprecated
        public int getGravity() {
            return this.mGravity;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setCustomSizePreset(int i) {
            this.mCustomSizePreset = i;
            return this;
        }

        @java.lang.Deprecated
        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setCustomContentHeight(int i) {
            this.mCustomContentHeight = i;
            return this;
        }

        @java.lang.Deprecated
        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public android.app.Notification.WearableExtender setStartScrollBottom(boolean z) {
            setFlag(8, z);
            return this;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public android.app.Notification.WearableExtender setContentIntentAvailableOffline(boolean z) {
            setFlag(1, z);
            return this;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setHintHideIcon(boolean z) {
            setFlag(2, z);
            return this;
        }

        @java.lang.Deprecated
        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setHintShowBackgroundOnly(boolean z) {
            setFlag(4, z);
            return this;
        }

        @java.lang.Deprecated
        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setHintAvoidBackgroundClipping(boolean z) {
            setFlag(16, z);
            return this;
        }

        @java.lang.Deprecated
        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 16) != 0;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setHintScreenTimeout(int i) {
            this.mHintScreenTimeout = i;
            return this;
        }

        @java.lang.Deprecated
        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        @java.lang.Deprecated
        public android.app.Notification.WearableExtender setHintAmbientBigPicture(boolean z) {
            setFlag(32, z);
            return this;
        }

        @java.lang.Deprecated
        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 32) != 0;
        }

        public android.app.Notification.WearableExtender setHintContentIntentLaunchesActivity(boolean z) {
            setFlag(64, z);
            return this;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 64) != 0;
        }

        public android.app.Notification.WearableExtender setDismissalId(java.lang.String str) {
            this.mDismissalId = str;
            return this;
        }

        public java.lang.String getDismissalId() {
            return this.mDismissalId;
        }

        public android.app.Notification.WearableExtender setBridgeTag(java.lang.String str) {
            this.mBridgeTag = str;
            return this;
        }

        public java.lang.String getBridgeTag() {
            return this.mBridgeTag;
        }

        private void setFlag(int i, boolean z) {
            if (z) {
                this.mFlags = i | this.mFlags;
            } else {
                this.mFlags = (~i) & this.mFlags;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mDisplayIntent != null) {
                this.mDisplayIntent.visitUris(consumer);
            }
            java.util.Iterator<android.app.Notification.Action> it = this.mActions.iterator();
            while (it.hasNext()) {
                it.next().visitUris(consumer);
            }
        }
    }

    public static final class CarExtender implements android.app.Notification.Extender {
        private static final java.lang.String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final java.lang.String EXTRA_COLOR = "app_color";
        private static final java.lang.String EXTRA_CONVERSATION = "car_conversation";
        private static final java.lang.String EXTRA_LARGE_ICON = "large_icon";
        private static final java.lang.String TAG = "CarExtender";
        private int mColor;
        private android.graphics.Bitmap mLargeIcon;
        private android.app.Notification.CarExtender.UnreadConversation mUnreadConversation;

        public CarExtender() {
            this.mColor = 0;
        }

        public CarExtender(android.app.Notification notification) {
            this.mColor = 0;
            android.os.Bundle bundle = notification.extras == null ? null : notification.extras.getBundle(EXTRA_CAR_EXTENDER);
            if (bundle != null) {
                this.mLargeIcon = (android.graphics.Bitmap) bundle.getParcelable(EXTRA_LARGE_ICON, android.graphics.Bitmap.class);
                this.mColor = bundle.getInt(EXTRA_COLOR, 0);
                this.mUnreadConversation = android.app.Notification.CarExtender.UnreadConversation.getUnreadConversationFromBundle(bundle.getBundle(EXTRA_CONVERSATION));
            }
        }

        @Override // android.app.Notification.Extender
        public android.app.Notification.Builder extend(android.app.Notification.Builder builder) {
            android.os.Bundle bundle = new android.os.Bundle();
            if (this.mLargeIcon != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
            }
            if (this.mColor != 0) {
                bundle.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                bundle.putBundle(EXTRA_CONVERSATION, this.mUnreadConversation.getBundleForUnreadConversation());
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
            return builder;
        }

        public android.app.Notification.CarExtender setColor(int i) {
            this.mColor = i;
            return this;
        }

        public int getColor() {
            return this.mColor;
        }

        public android.app.Notification.CarExtender setLargeIcon(android.graphics.Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public android.graphics.Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public android.app.Notification.CarExtender setUnreadConversation(android.app.Notification.CarExtender.UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public android.app.Notification.CarExtender.UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mUnreadConversation != null) {
                this.mUnreadConversation.visitUris(consumer);
            }
        }

        public static class UnreadConversation {
            private static final java.lang.String KEY_AUTHOR = "author";
            private static final java.lang.String KEY_MESSAGES = "messages";
            static final java.lang.String KEY_ON_READ = "on_read";
            static final java.lang.String KEY_ON_REPLY = "on_reply";
            private static final java.lang.String KEY_PARTICIPANTS = "participants";
            static final java.lang.String KEY_REMOTE_INPUT = "remote_input";
            private static final java.lang.String KEY_TEXT = "text";
            private static final java.lang.String KEY_TIMESTAMP = "timestamp";
            private final long mLatestTimestamp;
            private final java.lang.String[] mMessages;
            private final java.lang.String[] mParticipants;
            private final android.app.PendingIntent mReadPendingIntent;
            private final android.app.RemoteInput mRemoteInput;
            private final android.app.PendingIntent mReplyPendingIntent;

            UnreadConversation(java.lang.String[] strArr, android.app.RemoteInput remoteInput, android.app.PendingIntent pendingIntent, android.app.PendingIntent pendingIntent2, java.lang.String[] strArr2, long j) {
                this.mMessages = strArr;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = strArr2;
                this.mLatestTimestamp = j;
            }

            public java.lang.String[] getMessages() {
                return this.mMessages;
            }

            public android.app.RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            public android.app.PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public android.app.PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            public java.lang.String[] getParticipants() {
                return this.mParticipants;
            }

            public java.lang.String getParticipant() {
                if (this.mParticipants.length > 0) {
                    return this.mParticipants[0];
                }
                return null;
            }

            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            android.os.Bundle getBundleForUnreadConversation() {
                java.lang.String str;
                android.os.Bundle bundle = new android.os.Bundle();
                if (this.mParticipants != null && this.mParticipants.length > 1) {
                    str = this.mParticipants[0];
                } else {
                    str = null;
                }
                int length = this.mMessages.length;
                android.os.Parcelable[] parcelableArr = new android.os.Parcelable[length];
                for (int i = 0; i < length; i++) {
                    android.os.Bundle bundle2 = new android.os.Bundle();
                    bundle2.putString("text", this.mMessages[i]);
                    bundle2.putString("author", str);
                    parcelableArr[i] = bundle2;
                }
                bundle.putParcelableArray(KEY_MESSAGES, parcelableArr);
                if (this.mRemoteInput != null) {
                    bundle.putParcelable(KEY_REMOTE_INPUT, this.mRemoteInput);
                }
                bundle.putParcelable(KEY_ON_REPLY, this.mReplyPendingIntent);
                bundle.putParcelable(KEY_ON_READ, this.mReadPendingIntent);
                bundle.putStringArray(KEY_PARTICIPANTS, this.mParticipants);
                bundle.putLong("timestamp", this.mLatestTimestamp);
                return bundle;
            }

            static android.app.Notification.CarExtender.UnreadConversation getUnreadConversationFromBundle(android.os.Bundle bundle) {
                java.lang.String[] strArr;
                if (bundle == null) {
                    return null;
                }
                android.os.Parcelable[] parcelableArr = (android.os.Parcelable[]) bundle.getParcelableArray(KEY_MESSAGES, android.os.Parcelable.class);
                if (parcelableArr == null) {
                    strArr = null;
                } else {
                    int length = parcelableArr.length;
                    java.lang.String[] strArr2 = new java.lang.String[length];
                    boolean z = false;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            z = true;
                            break;
                        }
                        if (!(parcelableArr[i] instanceof android.os.Bundle)) {
                            break;
                        }
                        strArr2[i] = ((android.os.Bundle) parcelableArr[i]).getString("text");
                        if (strArr2[i] == null) {
                            break;
                        }
                        i++;
                    }
                    if (!z) {
                        return null;
                    }
                    strArr = strArr2;
                }
                android.app.PendingIntent pendingIntent = (android.app.PendingIntent) bundle.getParcelable(KEY_ON_READ, android.app.PendingIntent.class);
                android.app.PendingIntent pendingIntent2 = (android.app.PendingIntent) bundle.getParcelable(KEY_ON_REPLY, android.app.PendingIntent.class);
                android.app.RemoteInput remoteInput = (android.app.RemoteInput) bundle.getParcelable(KEY_REMOTE_INPUT, android.app.RemoteInput.class);
                java.lang.String[] stringArray = bundle.getStringArray(KEY_PARTICIPANTS);
                if (stringArray == null || stringArray.length != 1) {
                    return null;
                }
                return new android.app.Notification.CarExtender.UnreadConversation(strArr, remoteInput, pendingIntent2, pendingIntent, stringArray, bundle.getLong("timestamp"));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
                if (this.mReadPendingIntent != null) {
                    this.mReadPendingIntent.visitUris(consumer);
                }
                if (this.mReplyPendingIntent != null) {
                    this.mReplyPendingIntent.visitUris(consumer);
                }
            }
        }

        public static class Builder {
            private long mLatestTimestamp;
            private final java.util.List<java.lang.String> mMessages = new java.util.ArrayList();
            private final java.lang.String mParticipant;
            private android.app.PendingIntent mReadPendingIntent;
            private android.app.RemoteInput mRemoteInput;
            private android.app.PendingIntent mReplyPendingIntent;

            public Builder(java.lang.String str) {
                this.mParticipant = str;
            }

            public android.app.Notification.CarExtender.Builder addMessage(java.lang.String str) {
                this.mMessages.add(str);
                return this;
            }

            public android.app.Notification.CarExtender.Builder setReplyAction(android.app.PendingIntent pendingIntent, android.app.RemoteInput remoteInput) {
                this.mRemoteInput = remoteInput;
                this.mReplyPendingIntent = pendingIntent;
                return this;
            }

            public android.app.Notification.CarExtender.Builder setReadPendingIntent(android.app.PendingIntent pendingIntent) {
                this.mReadPendingIntent = pendingIntent;
                return this;
            }

            public android.app.Notification.CarExtender.Builder setLatestTimestamp(long j) {
                this.mLatestTimestamp = j;
                return this;
            }

            public android.app.Notification.CarExtender.UnreadConversation build() {
                return new android.app.Notification.CarExtender.UnreadConversation((java.lang.String[]) this.mMessages.toArray(new java.lang.String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, new java.lang.String[]{this.mParticipant}, this.mLatestTimestamp);
            }
        }
    }

    public static final class TvExtender implements android.app.Notification.Extender {
        private static final java.lang.String EXTRA_CHANNEL_ID = "channel_id";
        static final java.lang.String EXTRA_CONTENT_INTENT = "content_intent";
        static final java.lang.String EXTRA_DELETE_INTENT = "delete_intent";
        private static final java.lang.String EXTRA_FLAGS = "flags";
        private static final java.lang.String EXTRA_SUPPRESS_SHOW_OVER_APPS = "suppressShowOverApps";
        private static final java.lang.String EXTRA_TV_EXTENDER = "android.tv.EXTENSIONS";
        private static final int FLAG_AVAILABLE_ON_TV = 1;
        private static final java.lang.String TAG = "TvExtender";
        private java.lang.String mChannelId;
        private android.app.PendingIntent mContentIntent;
        private android.app.PendingIntent mDeleteIntent;
        private int mFlags;
        private boolean mSuppressShowOverApps;

        public TvExtender() {
            this.mFlags = 1;
        }

        public TvExtender(android.app.Notification notification) {
            android.os.Bundle bundle = notification.extras == null ? null : notification.extras.getBundle(EXTRA_TV_EXTENDER);
            if (bundle != null) {
                this.mFlags = bundle.getInt("flags");
                this.mChannelId = bundle.getString("channel_id");
                this.mSuppressShowOverApps = bundle.getBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS);
                this.mContentIntent = (android.app.PendingIntent) bundle.getParcelable(EXTRA_CONTENT_INTENT, android.app.PendingIntent.class);
                this.mDeleteIntent = (android.app.PendingIntent) bundle.getParcelable(EXTRA_DELETE_INTENT, android.app.PendingIntent.class);
            }
        }

        @Override // android.app.Notification.Extender
        public android.app.Notification.Builder extend(android.app.Notification.Builder builder) {
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putInt("flags", this.mFlags);
            bundle.putString("channel_id", this.mChannelId);
            bundle.putBoolean(EXTRA_SUPPRESS_SHOW_OVER_APPS, this.mSuppressShowOverApps);
            if (this.mContentIntent != null) {
                bundle.putParcelable(EXTRA_CONTENT_INTENT, this.mContentIntent);
            }
            if (this.mDeleteIntent != null) {
                bundle.putParcelable(EXTRA_DELETE_INTENT, this.mDeleteIntent);
            }
            builder.getExtras().putBundle(EXTRA_TV_EXTENDER, bundle);
            return builder;
        }

        public boolean isAvailableOnTv() {
            return (this.mFlags & 1) != 0;
        }

        @android.annotation.SystemApi
        public android.app.Notification.TvExtender setChannel(java.lang.String str) {
            this.mChannelId = str;
            return this;
        }

        public android.app.Notification.TvExtender setChannelId(java.lang.String str) {
            this.mChannelId = str;
            return this;
        }

        @android.annotation.SystemApi
        @java.lang.Deprecated
        public java.lang.String getChannel() {
            return this.mChannelId;
        }

        public java.lang.String getChannelId() {
            return this.mChannelId;
        }

        public android.app.Notification.TvExtender setContentIntent(android.app.PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public android.app.PendingIntent getContentIntent() {
            return this.mContentIntent;
        }

        public android.app.Notification.TvExtender setDeleteIntent(android.app.PendingIntent pendingIntent) {
            this.mDeleteIntent = pendingIntent;
            return this;
        }

        public android.app.PendingIntent getDeleteIntent() {
            return this.mDeleteIntent;
        }

        public android.app.Notification.TvExtender setSuppressShowOverApps(boolean z) {
            this.mSuppressShowOverApps = z;
            return this;
        }

        @android.annotation.SystemApi
        public boolean getSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }

        public boolean isSuppressShowOverApps() {
            return this.mSuppressShowOverApps;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void visitUris(java.util.function.Consumer<android.net.Uri> consumer) {
            if (this.mContentIntent != null) {
                this.mContentIntent.visitUris(consumer);
            }
            if (this.mDeleteIntent != null) {
                this.mDeleteIntent.visitUris(consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends android.os.Parcelable> T[] getParcelableArrayFromBundle(android.os.Bundle bundle, java.lang.String str, java.lang.Class<T> cls) {
        T[] tArr = (T[]) ((android.os.Parcelable[]) bundle.getParcelableArray(str, android.os.Parcelable.class));
        if (java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, 0).getClass().isInstance(tArr) || tArr == null) {
            return tArr;
        }
        T[] tArr2 = (T[]) ((android.os.Parcelable[]) java.lang.reflect.Array.newInstance((java.lang.Class<?>) cls, tArr.length));
        for (int i = 0; i < tArr.length; i++) {
            tArr2[i] = tArr[i];
        }
        bundle.putParcelableArray(str, tArr2);
        return tArr2;
    }

    private static class BuilderRemoteViews extends android.widget.RemoteViews {
        public BuilderRemoteViews(android.os.Parcel parcel) {
            super(parcel);
        }

        public BuilderRemoteViews(android.content.pm.ApplicationInfo applicationInfo, int i) {
            super(applicationInfo, i);
        }

        @Override // android.widget.RemoteViews
        /* renamed from: clone, reason: merged with bridge method [inline-methods] */
        public android.app.Notification.BuilderRemoteViews mo424clone() {
            android.os.Parcel obtain = android.os.Parcel.obtain();
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            android.app.Notification.BuilderRemoteViews builderRemoteViews = new android.app.Notification.BuilderRemoteViews(obtain);
            obtain.recycle();
            return builderRemoteViews;
        }

        @Override // android.widget.RemoteViews
        protected boolean shouldUseStaticFilter() {
            return true;
        }
    }

    private static class TemplateBindResult {
        public final android.app.Notification.TemplateBindResult.MarginSet mHeadingExtraMarginSet;
        public final android.app.Notification.TemplateBindResult.MarginSet mHeadingFullMarginSet;
        float mRightIconHeightDp;
        boolean mRightIconVisible;
        float mRightIconWidthDp;
        public final android.app.Notification.TemplateBindResult.MarginSet mTitleMarginSet;

        private TemplateBindResult() {
            this.mHeadingExtraMarginSet = new android.app.Notification.TemplateBindResult.MarginSet();
            this.mHeadingFullMarginSet = new android.app.Notification.TemplateBindResult.MarginSet();
            this.mTitleMarginSet = new android.app.Notification.TemplateBindResult.MarginSet();
        }

        public void setRightIconState(boolean z, float f, float f2, float f3, float f4) {
            this.mRightIconVisible = z;
            this.mRightIconWidthDp = f;
            this.mRightIconHeightDp = f2;
            this.mHeadingExtraMarginSet.setValues(0.0f, f3);
            float f5 = f3 + f4;
            this.mHeadingFullMarginSet.setValues(f4, f5);
            this.mTitleMarginSet.setValues(0.0f, f5);
        }

        private class MarginSet {
            private float mValueIfGone;
            private float mValueIfVisible;

            private MarginSet() {
            }

            public void setValues(float f, float f2) {
                this.mValueIfGone = f;
                this.mValueIfVisible = f2;
            }

            public void applyToView(android.widget.RemoteViews remoteViews, int i) {
                applyToView(remoteViews, i, 0.0f);
            }

            public void applyToView(android.widget.RemoteViews remoteViews, int i, float f) {
                float dpValue = getDpValue() + f;
                if (i == 16909314) {
                    remoteViews.setFloat(com.android.internal.R.id.notification_header, "setTopLineExtraMarginEndDp", dpValue);
                } else if (i != 16909624 && i != 16908834) {
                    remoteViews.setViewLayoutMargin(i, 5, dpValue, 1);
                } else {
                    if (this.mValueIfGone != 0.0f) {
                        throw new java.lang.RuntimeException("Programming error: `text` and `big_text` use ImageFloatingTextView which can either show a margin or not; thus mValueIfGone must be 0, but it was " + this.mValueIfGone);
                    }
                    remoteViews.setFloat(i, "setImageEndMarginDp", this.mValueIfVisible);
                    remoteViews.setBoolean(i, "setHasImage", android.app.Notification.TemplateBindResult.this.mRightIconVisible);
                    remoteViews.setViewLayoutMargin(i, 5, f, 1);
                }
                if (android.app.Notification.TemplateBindResult.this.mRightIconVisible) {
                    remoteViews.setIntTag(i, com.android.internal.R.id.tag_margin_end_when_icon_visible, android.util.TypedValue.createComplexDimension(this.mValueIfVisible + f, 1));
                    remoteViews.setIntTag(i, com.android.internal.R.id.tag_margin_end_when_icon_gone, android.util.TypedValue.createComplexDimension(this.mValueIfGone + f, 1));
                }
            }

            public float getDpValue() {
                return android.app.Notification.TemplateBindResult.this.mRightIconVisible ? this.mValueIfVisible : this.mValueIfGone;
            }
        }
    }

    private static class StandardTemplateParams {
        public static final int DECORATION_MINIMAL = 1;
        public static final int DECORATION_PARTIAL = 2;
        boolean allowColorization;
        boolean mAllowTextWithProgress;
        boolean mCallStyleActions;
        java.lang.CharSequence mHeaderTextSecondary;
        boolean mHeaderless;
        boolean mHideActions;
        boolean mHideAppName;
        boolean mHideLeftIcon;
        boolean mHideProgress;
        boolean mHideRightIcon;
        boolean mHideSnoozeButton;
        boolean mHideSubText;
        boolean mHideTime;
        boolean mHideTitle;
        boolean mHighlightExpander;
        android.graphics.drawable.Icon mPromotedPicture;
        java.lang.CharSequence mSubText;
        java.lang.CharSequence mText;
        int mTextViewId;
        java.lang.CharSequence mTitle;
        int mTitleViewId;
        int mViewType;
        int maxRemoteInputHistory;
        public static int VIEW_TYPE_UNSPECIFIED = 0;
        public static int VIEW_TYPE_NORMAL = 1;
        public static int VIEW_TYPE_BIG = 2;
        public static int VIEW_TYPE_HEADS_UP = 3;
        public static int VIEW_TYPE_MINIMIZED = 4;
        public static int VIEW_TYPE_PUBLIC = 5;
        public static int VIEW_TYPE_GROUP_HEADER = 6;

        private StandardTemplateParams() {
            this.mViewType = VIEW_TYPE_UNSPECIFIED;
            this.maxRemoteInputHistory = 3;
            this.allowColorization = true;
            this.mHighlightExpander = false;
        }

        final android.app.Notification.StandardTemplateParams reset() {
            this.mViewType = VIEW_TYPE_UNSPECIFIED;
            this.mHeaderless = false;
            this.mHideAppName = false;
            this.mHideTitle = false;
            this.mHideSubText = false;
            this.mHideTime = false;
            this.mHideActions = false;
            this.mHideProgress = false;
            this.mHideSnoozeButton = false;
            this.mHideLeftIcon = false;
            this.mHideRightIcon = false;
            this.mPromotedPicture = null;
            this.mCallStyleActions = false;
            this.mAllowTextWithProgress = false;
            this.mTitleViewId = 16908310;
            this.mTextViewId = com.android.internal.R.id.text;
            this.mTitle = null;
            this.mText = null;
            this.mSubText = null;
            this.mHeaderTextSecondary = null;
            this.maxRemoteInputHistory = 3;
            this.allowColorization = true;
            this.mHighlightExpander = false;
            return this;
        }

        final boolean hasTitle() {
            return (android.text.TextUtils.isEmpty(this.mTitle) || this.mHideTitle) ? false : true;
        }

        final android.app.Notification.StandardTemplateParams viewType(int i) {
            this.mViewType = i;
            return this;
        }

        public android.app.Notification.StandardTemplateParams headerless(boolean z) {
            this.mHeaderless = z;
            return this;
        }

        public android.app.Notification.StandardTemplateParams hideAppName(boolean z) {
            this.mHideAppName = z;
            return this;
        }

        public android.app.Notification.StandardTemplateParams hideSubText(boolean z) {
            this.mHideSubText = z;
            return this;
        }

        public android.app.Notification.StandardTemplateParams hideTime(boolean z) {
            this.mHideTime = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideActions(boolean z) {
            this.mHideActions = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideProgress(boolean z) {
            this.mHideProgress = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideTitle(boolean z) {
            this.mHideTitle = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams callStyleActions(boolean z) {
            this.mCallStyleActions = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams allowTextWithProgress(boolean z) {
            this.mAllowTextWithProgress = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideSnoozeButton(boolean z) {
            this.mHideSnoozeButton = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams promotedPicture(android.graphics.drawable.Icon icon) {
            this.mPromotedPicture = icon;
            return this;
        }

        public android.app.Notification.StandardTemplateParams titleViewId(int i) {
            this.mTitleViewId = i;
            return this;
        }

        public android.app.Notification.StandardTemplateParams textViewId(int i) {
            this.mTextViewId = i;
            return this;
        }

        final android.app.Notification.StandardTemplateParams title(java.lang.CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        final android.app.Notification.StandardTemplateParams text(java.lang.CharSequence charSequence) {
            this.mText = charSequence;
            return this;
        }

        final android.app.Notification.StandardTemplateParams summaryText(java.lang.CharSequence charSequence) {
            this.mSubText = charSequence;
            return this;
        }

        final android.app.Notification.StandardTemplateParams headerTextSecondary(java.lang.CharSequence charSequence) {
            this.mHeaderTextSecondary = charSequence;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideLeftIcon(boolean z) {
            this.mHideLeftIcon = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams hideRightIcon(boolean z) {
            this.mHideRightIcon = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams disallowColorization() {
            this.allowColorization = false;
            return this;
        }

        final android.app.Notification.StandardTemplateParams highlightExpander(boolean z) {
            this.mHighlightExpander = z;
            return this;
        }

        final android.app.Notification.StandardTemplateParams fillTextsFrom(android.app.Notification.Builder builder) {
            android.os.Bundle bundle = builder.mN.extras;
            this.mTitle = builder.processLegacyText(bundle.getCharSequence(android.app.Notification.EXTRA_TITLE));
            this.mText = builder.processLegacyText(bundle.getCharSequence(android.app.Notification.EXTRA_TEXT));
            this.mSubText = bundle.getCharSequence(android.app.Notification.EXTRA_SUB_TEXT);
            return this;
        }

        public android.app.Notification.StandardTemplateParams setMaxRemoteInputHistory(int i) {
            this.maxRemoteInputHistory = i;
            return this;
        }

        public android.app.Notification.StandardTemplateParams decorationType(int i) {
            boolean z = true;
            hideTitle(true);
            if (i > 1) {
                z = false;
            }
            hideLeftIcon(false);
            hideRightIcon(z);
            hideProgress(z);
            hideActions(z);
            return this;
        }
    }

    public static class Colors {
        private int mPaletteIsForRawColor = 1;
        private boolean mPaletteIsForColorized = false;
        private boolean mPaletteIsForNightMode = false;
        private int mBackgroundColor = 1;
        private int mProtectionColor = 1;
        private int mPrimaryTextColor = 1;
        private int mSecondaryTextColor = 1;
        private int mPrimaryAccentColor = 1;
        private int mSecondaryAccentColor = 1;
        private int mTertiaryAccentColor = 1;
        private int mOnTertiaryAccentTextColor = 1;
        private int mTertiaryFixedDimAccentColor = 1;
        private int mOnTertiaryFixedAccentTextColor = 1;
        private int mErrorColor = 1;
        private int mContrastColor = 1;
        private int mRippleAlpha = 51;

        private static android.content.res.TypedArray obtainDayNightAttributes(android.content.Context context, int[] iArr) {
            if (context.getTheme() == null) {
                return null;
            }
            return new android.view.ContextThemeWrapper(context, 16974563).getTheme().obtainStyledAttributes(iArr);
        }

        private static int getColor(android.content.res.TypedArray typedArray, int i, int i2) {
            return typedArray == null ? i2 : typedArray.getColor(i, i2);
        }

        public void resolvePalette(android.content.Context context, int i, boolean z, boolean z2) {
            android.content.res.TypedArray obtainDayNightAttributes;
            if (this.mPaletteIsForRawColor == i && this.mPaletteIsForColorized == z && this.mPaletteIsForNightMode == z2) {
                return;
            }
            this.mPaletteIsForRawColor = i;
            this.mPaletteIsForColorized = z;
            this.mPaletteIsForNightMode = z2;
            if (z) {
                if (i == 0) {
                    obtainDayNightAttributes = obtainDayNightAttributes(context, new int[]{com.android.internal.R.attr.materialColorSecondary});
                    try {
                        this.mBackgroundColor = getColor(obtainDayNightAttributes, 0, -1);
                        if (obtainDayNightAttributes != null) {
                            obtainDayNightAttributes.close();
                        }
                    } finally {
                    }
                } else {
                    this.mBackgroundColor = i;
                }
                this.mPrimaryTextColor = com.android.internal.util.ContrastColorUtil.findAlphaToMeetContrast(com.android.internal.util.ContrastColorUtil.resolvePrimaryColor(context, this.mBackgroundColor, z2), this.mBackgroundColor, 4.5d);
                this.mSecondaryTextColor = com.android.internal.util.ContrastColorUtil.findAlphaToMeetContrast(com.android.internal.util.ContrastColorUtil.resolveSecondaryColor(context, this.mBackgroundColor, z2), this.mBackgroundColor, 4.5d);
                this.mContrastColor = this.mPrimaryTextColor;
                this.mPrimaryAccentColor = this.mPrimaryTextColor;
                this.mSecondaryAccentColor = this.mSecondaryTextColor;
                this.mTertiaryAccentColor = flattenAlpha(this.mPrimaryTextColor, this.mBackgroundColor);
                this.mOnTertiaryAccentTextColor = this.mBackgroundColor;
                this.mTertiaryFixedDimAccentColor = this.mTertiaryAccentColor;
                this.mOnTertiaryFixedAccentTextColor = this.mOnTertiaryAccentTextColor;
                this.mErrorColor = this.mPrimaryTextColor;
                this.mRippleAlpha = 51;
            } else {
                obtainDayNightAttributes = obtainDayNightAttributes(context, new int[]{com.android.internal.R.attr.materialColorSurfaceContainerHigh, com.android.internal.R.attr.materialColorOnSurface, com.android.internal.R.attr.materialColorOnSurfaceVariant, com.android.internal.R.attr.materialColorPrimary, com.android.internal.R.attr.materialColorSecondary, com.android.internal.R.attr.materialColorTertiary, com.android.internal.R.attr.materialColorOnTertiary, com.android.internal.R.attr.materialColorTertiaryFixedDim, com.android.internal.R.attr.materialColorOnTertiaryFixed, 16844099, 16843820});
                try {
                    this.mBackgroundColor = getColor(obtainDayNightAttributes, 0, z2 ? -16777216 : -1);
                    this.mPrimaryTextColor = getColor(obtainDayNightAttributes, 1, 1);
                    this.mSecondaryTextColor = getColor(obtainDayNightAttributes, 2, 1);
                    this.mPrimaryAccentColor = getColor(obtainDayNightAttributes, 3, 1);
                    this.mSecondaryAccentColor = getColor(obtainDayNightAttributes, 4, 1);
                    this.mTertiaryAccentColor = getColor(obtainDayNightAttributes, 5, 1);
                    this.mOnTertiaryAccentTextColor = getColor(obtainDayNightAttributes, 6, 1);
                    this.mTertiaryFixedDimAccentColor = getColor(obtainDayNightAttributes, 7, 1);
                    this.mOnTertiaryFixedAccentTextColor = getColor(obtainDayNightAttributes, 8, 1);
                    this.mErrorColor = getColor(obtainDayNightAttributes, 9, 1);
                    this.mRippleAlpha = android.graphics.Color.alpha(getColor(obtainDayNightAttributes, 10, 872415231));
                    if (obtainDayNightAttributes != null) {
                        obtainDayNightAttributes.close();
                    }
                    this.mContrastColor = calculateContrastColor(context, i, this.mPrimaryAccentColor, this.mBackgroundColor, z2);
                    if (this.mPrimaryTextColor == 1) {
                        this.mPrimaryTextColor = com.android.internal.util.ContrastColorUtil.resolvePrimaryColor(context, this.mBackgroundColor, z2);
                    }
                    if (this.mSecondaryTextColor == 1) {
                        this.mSecondaryTextColor = com.android.internal.util.ContrastColorUtil.resolveSecondaryColor(context, this.mBackgroundColor, z2);
                    }
                    if (this.mPrimaryAccentColor == 1) {
                        this.mPrimaryAccentColor = this.mContrastColor;
                    }
                    if (this.mSecondaryAccentColor == 1) {
                        this.mSecondaryAccentColor = this.mContrastColor;
                    }
                    if (this.mTertiaryAccentColor == 1) {
                        this.mTertiaryAccentColor = this.mContrastColor;
                    }
                    if (this.mOnTertiaryAccentTextColor == 1) {
                        this.mOnTertiaryAccentTextColor = com.android.internal.graphics.ColorUtils.setAlphaComponent(com.android.internal.util.ContrastColorUtil.resolvePrimaryColor(context, this.mTertiaryAccentColor, z2), 255);
                    }
                    if (this.mTertiaryFixedDimAccentColor == 1) {
                        this.mTertiaryFixedDimAccentColor = this.mContrastColor;
                    }
                    if (this.mOnTertiaryFixedAccentTextColor == 1) {
                        this.mOnTertiaryFixedAccentTextColor = com.android.internal.graphics.ColorUtils.setAlphaComponent(com.android.internal.util.ContrastColorUtil.resolvePrimaryColor(context, this.mTertiaryFixedDimAccentColor, z2), 255);
                    }
                    if (this.mErrorColor == 1) {
                        this.mErrorColor = this.mPrimaryTextColor;
                    }
                } finally {
                }
            }
            this.mProtectionColor = com.android.internal.graphics.ColorUtils.blendARGB(this.mPrimaryTextColor, this.mBackgroundColor, 0.9f);
        }

        private static int calculateContrastColor(android.content.Context context, int i, int i2, int i3, boolean z) {
            if (i == 0) {
                if (i2 == 1) {
                    i2 = com.android.internal.util.ContrastColorUtil.resolveDefaultColor(context, i3, z);
                }
            } else {
                i2 = com.android.internal.util.ContrastColorUtil.resolveContrastColor(context, i, i3, z);
            }
            return flattenAlpha(i2, i3);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static int flattenAlpha(int i, int i2) {
            if (android.graphics.Color.alpha(i) == 255) {
                return i;
            }
            return com.android.internal.util.ContrastColorUtil.compositeColors(i, i2);
        }

        public int getBackgroundColor() {
            return this.mBackgroundColor;
        }

        public int getProtectionColor() {
            return this.mProtectionColor;
        }

        public int getPrimaryTextColor() {
            return this.mPrimaryTextColor;
        }

        public int getSecondaryTextColor() {
            return this.mSecondaryTextColor;
        }

        public int getPrimaryAccentColor() {
            return this.mPrimaryAccentColor;
        }

        public int getSecondaryAccentColor() {
            return this.mSecondaryAccentColor;
        }

        public int getTertiaryAccentColor() {
            return this.mTertiaryAccentColor;
        }

        public int getOnTertiaryAccentTextColor() {
            return this.mOnTertiaryAccentTextColor;
        }

        public int getTertiaryFixedDimAccentColor() {
            return this.mTertiaryFixedDimAccentColor;
        }

        public int getOnTertiaryFixedAccentTextColor() {
            return this.mOnTertiaryFixedAccentTextColor;
        }

        public int getContrastColor() {
            return this.mContrastColor;
        }

        public int getErrorColor() {
            return this.mErrorColor;
        }

        public int getRippleAlpha() {
            return this.mRippleAlpha;
        }
    }
}
