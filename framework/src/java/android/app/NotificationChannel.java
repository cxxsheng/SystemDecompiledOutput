package android.app;

/* loaded from: classes.dex */
public final class NotificationChannel implements android.os.Parcelable {
    public static final int ALLOW_BUBBLE_OFF = 0;
    public static final int ALLOW_BUBBLE_ON = 1;
    private static final java.lang.String ATT_ALLOW_BUBBLE = "allow_bubbles";
    private static final java.lang.String ATT_BLOCKABLE_SYSTEM = "blockable_system";
    private static final java.lang.String ATT_CONTENT_TYPE = "content_type";
    private static final java.lang.String ATT_CONVERSATION_ID = "conv_id";
    private static final java.lang.String ATT_DELETED = "deleted";
    private static final java.lang.String ATT_DELETED_TIME_MS = "del_time";
    private static final java.lang.String ATT_DEMOTE = "dem";
    private static final java.lang.String ATT_DESC = "desc";
    private static final java.lang.String ATT_FG_SERVICE_SHOWN = "fgservice";
    private static final java.lang.String ATT_FLAGS = "flags";
    private static final java.lang.String ATT_GROUP = "group";
    private static final java.lang.String ATT_ID = "id";
    private static final java.lang.String ATT_IMPORTANCE = "importance";
    private static final java.lang.String ATT_IMP_CONVERSATION = "imp_conv";
    private static final java.lang.String ATT_LIGHTS = "lights";
    private static final java.lang.String ATT_LIGHT_COLOR = "light_color";
    private static final java.lang.String ATT_NAME = "name";
    private static final java.lang.String ATT_ORIG_IMP = "orig_imp";
    private static final java.lang.String ATT_PARENT_CHANNEL = "parent";
    private static final java.lang.String ATT_PRIORITY = "priority";
    private static final java.lang.String ATT_SHOW_BADGE = "show_badge";
    private static final java.lang.String ATT_SOUND = "sound";
    private static final java.lang.String ATT_USAGE = "usage";
    private static final java.lang.String ATT_USER_LOCKED = "locked";
    private static final java.lang.String ATT_VIBRATION = "vibration";
    private static final java.lang.String ATT_VIBRATION_EFFECT = "vibration_effect";
    private static final java.lang.String ATT_VIBRATION_ENABLED = "vibration_enabled";
    private static final java.lang.String ATT_VISIBILITY = "visibility";
    public static final java.lang.String CONVERSATION_CHANNEL_ID_FORMAT = "%1$s : %2$s";
    public static final int DEFAULT_ALLOW_BUBBLE = -1;
    public static final java.lang.String DEFAULT_CHANNEL_ID = "miscellaneous";
    private static final boolean DEFAULT_DELETED = false;
    private static final long DEFAULT_DELETION_TIME_MS = -1;
    private static final int DEFAULT_IMPORTANCE = -1000;
    private static final int DEFAULT_LIGHT_COLOR = 0;
    private static final boolean DEFAULT_SHOW_BADGE = true;
    private static final int DEFAULT_VISIBILITY = -1000;
    private static final java.lang.String DELIMITER = ",";
    public static final java.lang.String EDIT_CONVERSATION = "conversation";
    public static final java.lang.String EDIT_IMPORTANCE = "importance";
    public static final java.lang.String EDIT_LAUNCHER = "launcher";
    public static final java.lang.String EDIT_LOCKED_DEVICE = "locked";
    public static final java.lang.String EDIT_SOUND = "sound";
    public static final java.lang.String EDIT_VIBRATION = "vibration";
    public static final java.lang.String EDIT_ZEN = "zen";
    public static final int MAX_TEXT_LENGTH = 1000;
    public static final int MAX_VIBRATION_LENGTH = 1000;
    public static final java.lang.String PLACEHOLDER_CONVERSATION_ID = ":placeholder_id";
    private static final java.lang.String TAG = "NotificationChannel";
    private static final java.lang.String TAG_CHANNEL = "channel";
    public static final int USER_LOCKED_ALLOW_BUBBLE = 256;
    public static final int USER_LOCKED_IMPORTANCE = 4;
    public static final int USER_LOCKED_LIGHTS = 8;
    public static final int USER_LOCKED_PRIORITY = 1;
    public static final int USER_LOCKED_SHOW_BADGE = 128;

    @android.annotation.SystemApi
    public static final int USER_LOCKED_SOUND = 32;
    public static final int USER_LOCKED_VIBRATION = 16;
    public static final int USER_LOCKED_VISIBILITY = 2;
    private int mAllowBubbles;
    private android.media.AudioAttributes mAudioAttributes;
    private boolean mBlockableSystem;
    private boolean mBypassDnd;
    private java.lang.String mConversationId;
    private boolean mDeleted;
    private long mDeletedTime;
    private boolean mDemoted;
    private java.lang.String mDesc;
    private java.lang.String mGroup;
    private java.lang.String mId;
    private int mImportance;
    private boolean mImportanceLockedDefaultApp;
    private boolean mImportantConvo;
    private long mLastNotificationUpdateTimeMs;
    private int mLightColor;
    private boolean mLights;
    private int mLockscreenVisibility;
    private java.lang.String mName;
    private int mOriginalImportance;
    private java.lang.String mParentId;
    private boolean mShowBadge;
    private android.net.Uri mSound;
    private boolean mSoundRestored;
    private int mUserLockedFields;
    private boolean mUserVisibleTaskShown;
    private android.os.VibrationEffect mVibrationEffect;
    private boolean mVibrationEnabled;
    private long[] mVibrationPattern;
    public static final int[] LOCKABLE_FIELDS = {1, 2, 4, 8, 16, 32, 128, 256};
    public static final android.os.Parcelable.Creator<android.app.NotificationChannel> CREATOR = new android.os.Parcelable.Creator<android.app.NotificationChannel>() { // from class: android.app.NotificationChannel.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationChannel createFromParcel(android.os.Parcel parcel) {
            return new android.app.NotificationChannel(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationChannel[] newArray(int i) {
            return new android.app.NotificationChannel[i];
        }
    };

    public NotificationChannel(java.lang.String str, java.lang.CharSequence charSequence, int i) {
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        this.mId = getTrimmedString(str);
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
        this.mImportance = i;
    }

    protected NotificationChannel(android.os.Parcel parcel) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        this.mImportance = -1000;
        this.mOriginalImportance = -1000;
        this.mLockscreenVisibility = -1000;
        this.mSound = android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
        this.mSoundRestored = false;
        this.mLightColor = 0;
        this.mShowBadge = true;
        this.mDeleted = false;
        this.mAudioAttributes = android.app.Notification.AUDIO_ATTRIBUTES_DEFAULT;
        this.mBlockableSystem = false;
        this.mAllowBubbles = -1;
        this.mParentId = null;
        this.mConversationId = null;
        this.mDemoted = false;
        this.mImportantConvo = false;
        this.mDeletedTime = -1L;
        this.mLastNotificationUpdateTimeMs = 0L;
        if (parcel.readByte() != 0) {
            this.mId = getTrimmedString(parcel.readString());
        } else {
            this.mId = null;
        }
        if (parcel.readByte() != 0) {
            this.mName = getTrimmedString(parcel.readString());
        } else {
            this.mName = null;
        }
        if (parcel.readByte() != 0) {
            this.mDesc = getTrimmedString(parcel.readString());
        } else {
            this.mDesc = null;
        }
        this.mImportance = parcel.readInt();
        if (parcel.readByte() == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mBypassDnd = z;
        this.mLockscreenVisibility = parcel.readInt();
        if (parcel.readByte() != 0) {
            this.mSound = android.net.Uri.CREATOR.createFromParcel(parcel);
            this.mSound = android.net.Uri.parse(getTrimmedString(this.mSound.toString()));
        } else {
            this.mSound = null;
        }
        if (parcel.readByte() == 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mLights = z2;
        this.mVibrationPattern = parcel.createLongArray();
        if (this.mVibrationPattern != null && this.mVibrationPattern.length > 1000) {
            this.mVibrationPattern = java.util.Arrays.copyOf(this.mVibrationPattern, 1000);
        }
        if (android.app.Flags.notificationChannelVibrationEffectApi()) {
            this.mVibrationEffect = parcel.readInt() != 0 ? android.os.VibrationEffect.CREATOR.createFromParcel(parcel) : null;
        }
        this.mUserLockedFields = parcel.readInt();
        if (parcel.readByte() == 0) {
            z3 = false;
        } else {
            z3 = true;
        }
        this.mUserVisibleTaskShown = z3;
        if (parcel.readByte() == 0) {
            z4 = false;
        } else {
            z4 = true;
        }
        this.mVibrationEnabled = z4;
        if (parcel.readByte() == 0) {
            z5 = false;
        } else {
            z5 = true;
        }
        this.mShowBadge = z5;
        this.mDeleted = parcel.readByte() != 0;
        if (parcel.readByte() != 0) {
            this.mGroup = getTrimmedString(parcel.readString());
        } else {
            this.mGroup = null;
        }
        this.mAudioAttributes = parcel.readInt() > 0 ? android.media.AudioAttributes.CREATOR.createFromParcel(parcel) : null;
        this.mLightColor = parcel.readInt();
        this.mBlockableSystem = parcel.readBoolean();
        this.mAllowBubbles = parcel.readInt();
        this.mOriginalImportance = parcel.readInt();
        this.mParentId = getTrimmedString(parcel.readString());
        this.mConversationId = getTrimmedString(parcel.readString());
        this.mDemoted = parcel.readBoolean();
        this.mImportantConvo = parcel.readBoolean();
        this.mDeletedTime = parcel.readLong();
        this.mImportanceLockedDefaultApp = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        if (this.mId != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mId);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mName != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mName);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mDesc != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mDesc);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeInt(this.mImportance);
        parcel.writeByte(this.mBypassDnd ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.mLockscreenVisibility);
        if (this.mSound != null) {
            parcel.writeByte((byte) 1);
            this.mSound.writeToParcel(parcel, 0);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeByte(this.mLights ? (byte) 1 : (byte) 0);
        parcel.writeLongArray(this.mVibrationPattern);
        if (android.app.Flags.notificationChannelVibrationEffectApi()) {
            if (this.mVibrationEffect != null) {
                parcel.writeInt(1);
                this.mVibrationEffect.writeToParcel(parcel, 0);
            } else {
                parcel.writeInt(0);
            }
        }
        parcel.writeInt(this.mUserLockedFields);
        parcel.writeByte(this.mUserVisibleTaskShown ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mVibrationEnabled ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mShowBadge ? (byte) 1 : (byte) 0);
        parcel.writeByte(this.mDeleted ? (byte) 1 : (byte) 0);
        if (this.mGroup != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mGroup);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mAudioAttributes != null) {
            parcel.writeInt(1);
            this.mAudioAttributes.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mLightColor);
        parcel.writeBoolean(this.mBlockableSystem);
        parcel.writeInt(this.mAllowBubbles);
        parcel.writeInt(this.mOriginalImportance);
        parcel.writeString(this.mParentId);
        parcel.writeString(this.mConversationId);
        parcel.writeBoolean(this.mDemoted);
        parcel.writeBoolean(this.mImportantConvo);
        parcel.writeLong(this.mDeletedTime);
        parcel.writeBoolean(this.mImportanceLockedDefaultApp);
    }

    public void lockFields(int i) {
        this.mUserLockedFields = i | this.mUserLockedFields;
    }

    public void unlockFields(int i) {
        this.mUserLockedFields = (~i) & this.mUserLockedFields;
    }

    public void setUserVisibleTaskShown(boolean z) {
        this.mUserVisibleTaskShown = z;
    }

    public void setDeleted(boolean z) {
        this.mDeleted = z;
    }

    public void setDeletedTimeMs(long j) {
        this.mDeletedTime = j;
    }

    public void setImportantConversation(boolean z) {
        this.mImportantConvo = z;
    }

    public void setBlockable(boolean z) {
        this.mBlockableSystem = z;
    }

    public void setName(java.lang.CharSequence charSequence) {
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
    }

    public void setDescription(java.lang.String str) {
        this.mDesc = getTrimmedString(str);
    }

    private java.lang.String getTrimmedString(java.lang.String str) {
        if (str != null && str.length() > 1000) {
            return str.substring(0, 1000);
        }
        return str;
    }

    public void setId(java.lang.String str) {
        this.mId = str;
    }

    public void setGroup(java.lang.String str) {
        this.mGroup = str;
    }

    public void setShowBadge(boolean z) {
        this.mShowBadge = z;
    }

    public void setSound(android.net.Uri uri, android.media.AudioAttributes audioAttributes) {
        this.mSound = uri;
        this.mAudioAttributes = audioAttributes;
    }

    public void enableLights(boolean z) {
        this.mLights = z;
    }

    public void setLightColor(int i) {
        this.mLightColor = i;
    }

    public void enableVibration(boolean z) {
        this.mVibrationEnabled = z;
    }

    public void setVibrationPattern(long[] jArr) {
        this.mVibrationEnabled = jArr != null && jArr.length > 0;
        this.mVibrationPattern = jArr;
        if (android.app.Flags.notificationChannelVibrationEffectApi()) {
            try {
                this.mVibrationEffect = android.os.VibrationEffect.createWaveform(jArr, -1);
            } catch (java.lang.IllegalArgumentException | java.lang.NullPointerException e) {
                this.mVibrationEffect = null;
            }
        }
    }

    public void setVibrationEffect(android.os.VibrationEffect vibrationEffect) {
        this.mVibrationEnabled = vibrationEffect != null;
        this.mVibrationEffect = vibrationEffect;
        this.mVibrationPattern = vibrationEffect == null ? null : vibrationEffect.computeCreateWaveformOffOnTimingsOrNull();
    }

    public void setImportance(int i) {
        this.mImportance = i;
    }

    public void setBypassDnd(boolean z) {
        this.mBypassDnd = z;
    }

    public void setLockscreenVisibility(int i) {
        this.mLockscreenVisibility = i;
    }

    public void setAllowBubbles(boolean z) {
        this.mAllowBubbles = z ? 1 : 0;
    }

    public void setAllowBubbles(int i) {
        this.mAllowBubbles = i;
    }

    public void setConversationId(java.lang.String str, java.lang.String str2) {
        this.mParentId = str;
        this.mConversationId = str2;
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public java.lang.String getDescription() {
        return this.mDesc;
    }

    public int getImportance() {
        return this.mImportance;
    }

    public boolean canBypassDnd() {
        return this.mBypassDnd;
    }

    public boolean isConversation() {
        return !android.text.TextUtils.isEmpty(getConversationId());
    }

    public boolean isImportantConversation() {
        return this.mImportantConvo;
    }

    public android.net.Uri getSound() {
        return this.mSound;
    }

    public android.media.AudioAttributes getAudioAttributes() {
        return this.mAudioAttributes;
    }

    public boolean shouldShowLights() {
        return this.mLights;
    }

    public int getLightColor() {
        return this.mLightColor;
    }

    public boolean shouldVibrate() {
        return this.mVibrationEnabled;
    }

    public long[] getVibrationPattern() {
        return this.mVibrationPattern;
    }

    public android.os.VibrationEffect getVibrationEffect() {
        return this.mVibrationEffect;
    }

    public int getLockscreenVisibility() {
        return this.mLockscreenVisibility;
    }

    public boolean canShowBadge() {
        return this.mShowBadge;
    }

    public java.lang.String getGroup() {
        return this.mGroup;
    }

    public boolean canBubble() {
        return this.mAllowBubbles == 1;
    }

    public int getAllowBubbles() {
        return this.mAllowBubbles;
    }

    public java.lang.String getParentChannelId() {
        return this.mParentId;
    }

    public java.lang.String getConversationId() {
        return this.mConversationId;
    }

    @android.annotation.SystemApi
    public boolean isDeleted() {
        return this.mDeleted;
    }

    public long getDeletedTimeMs() {
        return this.mDeletedTime;
    }

    @android.annotation.SystemApi
    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public boolean isUserVisibleTaskShown() {
        return this.mUserVisibleTaskShown;
    }

    public boolean isBlockable() {
        return this.mBlockableSystem;
    }

    public void setImportanceLockedByCriticalDeviceFunction(boolean z) {
        this.mImportanceLockedDefaultApp = z;
    }

    public boolean isImportanceLockedByCriticalDeviceFunction() {
        return this.mImportanceLockedDefaultApp;
    }

    public int getOriginalImportance() {
        return this.mOriginalImportance;
    }

    public void setOriginalImportance(int i) {
        this.mOriginalImportance = i;
    }

    public void setDemoted(boolean z) {
        this.mDemoted = z;
    }

    public boolean isDemoted() {
        return this.mDemoted;
    }

    public boolean hasUserSetImportance() {
        return (this.mUserLockedFields & 4) != 0;
    }

    public boolean hasUserSetSound() {
        return (this.mUserLockedFields & 32) != 0;
    }

    public long getLastNotificationUpdateTimeMs() {
        return this.mLastNotificationUpdateTimeMs;
    }

    public void setLastNotificationUpdateTimeMs(long j) {
        this.mLastNotificationUpdateTimeMs = j;
    }

    public void populateFromXmlForRestore(org.xmlpull.v1.XmlPullParser xmlPullParser, boolean z, android.content.Context context) {
        populateFromXml(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser), true, z, context);
    }

    @android.annotation.SystemApi
    public void populateFromXml(org.xmlpull.v1.XmlPullParser xmlPullParser) {
        populateFromXml(com.android.internal.util.XmlUtils.makeTyped(xmlPullParser), false, true, null);
    }

    private void populateFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, boolean z, boolean z2, android.content.Context context) {
        android.os.VibrationEffect safeVibrationEffect;
        com.android.internal.util.Preconditions.checkArgument((z && context == null) ? false : true, "forRestore is true but got null context");
        setDescription(typedXmlPullParser.getAttributeValue(null, ATT_DESC));
        setBypassDnd(safeInt(typedXmlPullParser, "priority", 0) != 0);
        setLockscreenVisibility(safeInt(typedXmlPullParser, "visibility", -1000));
        android.net.Uri safeUri = safeUri(typedXmlPullParser, "sound");
        android.media.AudioAttributes safeAudioAttributes = safeAudioAttributes(typedXmlPullParser);
        int usage = safeAudioAttributes.getUsage();
        if (z) {
            safeUri = restoreSoundUri(context, safeUri, z2, usage);
        }
        setSound(safeUri, safeAudioAttributes);
        enableLights(safeBool(typedXmlPullParser, "lights", false));
        setLightColor(safeInt(typedXmlPullParser, ATT_LIGHT_COLOR, 0));
        setVibrationPattern(safeLongArray(typedXmlPullParser, "vibration", null));
        if (android.app.Flags.notificationChannelVibrationEffectApi() && (safeVibrationEffect = safeVibrationEffect(typedXmlPullParser, ATT_VIBRATION_EFFECT)) != null) {
            setVibrationEffect(safeVibrationEffect);
        }
        enableVibration(safeBool(typedXmlPullParser, ATT_VIBRATION_ENABLED, false));
        setShowBadge(safeBool(typedXmlPullParser, ATT_SHOW_BADGE, false));
        setDeleted(safeBool(typedXmlPullParser, "deleted", false));
        setDeletedTimeMs(com.android.internal.util.XmlUtils.readLongAttribute(typedXmlPullParser, ATT_DELETED_TIME_MS, -1L));
        setGroup(typedXmlPullParser.getAttributeValue(null, ATT_GROUP));
        lockFields(safeInt(typedXmlPullParser, "locked", 0));
        setUserVisibleTaskShown(safeBool(typedXmlPullParser, ATT_FG_SERVICE_SHOWN, false));
        setBlockable(safeBool(typedXmlPullParser, ATT_BLOCKABLE_SYSTEM, false));
        setAllowBubbles(safeInt(typedXmlPullParser, ATT_ALLOW_BUBBLE, -1));
        setOriginalImportance(safeInt(typedXmlPullParser, ATT_ORIG_IMP, -1000));
        setConversationId(typedXmlPullParser.getAttributeValue(null, "parent"), typedXmlPullParser.getAttributeValue(null, ATT_CONVERSATION_ID));
        setDemoted(safeBool(typedXmlPullParser, ATT_DEMOTE, false));
        setImportantConversation(safeBool(typedXmlPullParser, ATT_IMP_CONVERSATION, false));
    }

    public boolean isSoundRestored() {
        return this.mSoundRestored;
    }

    private android.net.Uri getCanonicalizedSoundUri(android.content.ContentResolver contentResolver, android.net.Uri uri) {
        if (android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri)) {
            return uri;
        }
        if (android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme())) {
            try {
                contentResolver.getResourceId(uri);
                return uri;
            } catch (java.io.FileNotFoundException e) {
                return null;
            }
        }
        if ("file".equals(uri.getScheme())) {
            return uri;
        }
        return contentResolver.canonicalize(uri);
    }

    private android.net.Uri getUncanonicalizedSoundUri(android.content.ContentResolver contentResolver, android.net.Uri uri, int i) {
        if (android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.equals(uri) || android.content.ContentResolver.SCHEME_ANDROID_RESOURCE.equals(uri.getScheme()) || "file".equals(uri.getScheme())) {
            return uri;
        }
        int i2 = 4;
        if (4 != i) {
            if (6 == i) {
                i2 = 1;
            } else {
                i2 = 2;
            }
        }
        try {
            return android.media.RingtoneManager.getRingtoneUriForRestore(contentResolver, uri.toString(), i2);
        } catch (java.lang.Exception e) {
            android.util.Log.e(TAG, "Failed to uncanonicalized sound uri for " + uri + " " + e);
            return android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
        }
    }

    public android.net.Uri restoreSoundUri(android.content.Context context, android.net.Uri uri, boolean z, int i) {
        if (uri == null || android.net.Uri.EMPTY.equals(uri)) {
            return null;
        }
        android.content.ContentResolver contentResolver = context.getContentResolver();
        android.net.Uri canonicalizedSoundUri = getCanonicalizedSoundUri(contentResolver, uri);
        if (canonicalizedSoundUri == null) {
            if (!this.mSoundRestored && z) {
                this.mSoundRestored = true;
                return android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
            }
            this.mSoundRestored = false;
            return uri;
        }
        this.mSoundRestored = true;
        return getUncanonicalizedSoundUri(contentResolver, canonicalizedSoundUri, i);
    }

    @android.annotation.SystemApi
    public void writeXml(org.xmlpull.v1.XmlSerializer xmlSerializer) throws java.io.IOException {
        writeXml(com.android.internal.util.XmlUtils.makeTyped(xmlSerializer), false, null);
    }

    public void writeXmlForBackup(org.xmlpull.v1.XmlSerializer xmlSerializer, android.content.Context context) throws java.io.IOException {
        writeXml(com.android.internal.util.XmlUtils.makeTyped(xmlSerializer), true, context);
    }

    private android.net.Uri getSoundForBackup(android.content.Context context) {
        android.net.Uri sound = getSound();
        if (sound == null || android.net.Uri.EMPTY.equals(sound)) {
            return null;
        }
        android.net.Uri canonicalizedSoundUri = getCanonicalizedSoundUri(context.getContentResolver(), sound);
        if (canonicalizedSoundUri == null) {
            return android.provider.Settings.System.DEFAULT_NOTIFICATION_URI;
        }
        return canonicalizedSoundUri;
    }

    private void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer, boolean z, android.content.Context context) throws java.io.IOException {
        com.android.internal.util.Preconditions.checkArgument((z && context == null) ? false : true, "forBackup is true but got null context");
        typedXmlSerializer.startTag(null, "channel");
        typedXmlSerializer.attribute(null, "id", getId());
        if (getName() != null) {
            typedXmlSerializer.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            typedXmlSerializer.attribute(null, ATT_DESC, getDescription());
        }
        if (getImportance() != -1000) {
            typedXmlSerializer.attributeInt(null, "importance", getImportance());
        }
        if (canBypassDnd()) {
            typedXmlSerializer.attributeInt(null, "priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            typedXmlSerializer.attributeInt(null, "visibility", getLockscreenVisibility());
        }
        android.net.Uri soundForBackup = z ? getSoundForBackup(context) : getSound();
        if (soundForBackup != null) {
            typedXmlSerializer.attribute(null, "sound", soundForBackup.toString());
        }
        if (getAudioAttributes() != null) {
            typedXmlSerializer.attributeInt(null, ATT_USAGE, getAudioAttributes().getUsage());
            typedXmlSerializer.attributeInt(null, ATT_CONTENT_TYPE, getAudioAttributes().getContentType());
            typedXmlSerializer.attributeInt(null, "flags", getAudioAttributes().getFlags());
        }
        if (shouldShowLights()) {
            typedXmlSerializer.attributeBoolean(null, "lights", shouldShowLights());
        }
        if (getLightColor() != 0) {
            typedXmlSerializer.attributeInt(null, ATT_LIGHT_COLOR, getLightColor());
        }
        if (shouldVibrate()) {
            typedXmlSerializer.attributeBoolean(null, ATT_VIBRATION_ENABLED, shouldVibrate());
        }
        if (getVibrationPattern() != null) {
            typedXmlSerializer.attribute(null, "vibration", longArrayToString(getVibrationPattern()));
        }
        if (getVibrationEffect() != null) {
            typedXmlSerializer.attribute(null, ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        if (getUserLockedFields() != 0) {
            typedXmlSerializer.attributeInt(null, "locked", getUserLockedFields());
        }
        if (isUserVisibleTaskShown()) {
            typedXmlSerializer.attributeBoolean(null, ATT_FG_SERVICE_SHOWN, isUserVisibleTaskShown());
        }
        if (canShowBadge()) {
            typedXmlSerializer.attributeBoolean(null, ATT_SHOW_BADGE, canShowBadge());
        }
        if (isDeleted()) {
            typedXmlSerializer.attributeBoolean(null, "deleted", isDeleted());
        }
        if (getDeletedTimeMs() >= 0) {
            typedXmlSerializer.attributeLong(null, ATT_DELETED_TIME_MS, getDeletedTimeMs());
        }
        if (getGroup() != null) {
            typedXmlSerializer.attribute(null, ATT_GROUP, getGroup());
        }
        if (isBlockable()) {
            typedXmlSerializer.attributeBoolean(null, ATT_BLOCKABLE_SYSTEM, isBlockable());
        }
        if (getAllowBubbles() != -1) {
            typedXmlSerializer.attributeInt(null, ATT_ALLOW_BUBBLE, getAllowBubbles());
        }
        if (getOriginalImportance() != -1000) {
            typedXmlSerializer.attributeInt(null, ATT_ORIG_IMP, getOriginalImportance());
        }
        if (getParentChannelId() != null) {
            typedXmlSerializer.attribute(null, "parent", getParentChannelId());
        }
        if (getConversationId() != null) {
            typedXmlSerializer.attribute(null, ATT_CONVERSATION_ID, getConversationId());
        }
        if (isDemoted()) {
            typedXmlSerializer.attributeBoolean(null, ATT_DEMOTE, isDemoted());
        }
        if (isImportantConversation()) {
            typedXmlSerializer.attributeBoolean(null, ATT_IMP_CONVERSATION, isImportantConversation());
        }
        typedXmlSerializer.endTag(null, "channel");
    }

    @android.annotation.SystemApi
    public org.json.JSONObject toJson() throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        jSONObject.put(ATT_DESC, getDescription());
        if (getImportance() != -1000) {
            jSONObject.put("importance", android.service.notification.NotificationListenerService.Ranking.importanceToString(getImportance()));
        }
        if (canBypassDnd()) {
            jSONObject.put("priority", 2);
        }
        if (getLockscreenVisibility() != -1000) {
            jSONObject.put("visibility", android.app.Notification.visibilityToString(getLockscreenVisibility()));
        }
        if (getSound() != null) {
            jSONObject.put("sound", getSound().toString());
        }
        if (getAudioAttributes() != null) {
            jSONObject.put(ATT_USAGE, java.lang.Integer.toString(getAudioAttributes().getUsage()));
            jSONObject.put(ATT_CONTENT_TYPE, java.lang.Integer.toString(getAudioAttributes().getContentType()));
            jSONObject.put("flags", java.lang.Integer.toString(getAudioAttributes().getFlags()));
        }
        jSONObject.put("lights", java.lang.Boolean.toString(shouldShowLights()));
        jSONObject.put(ATT_LIGHT_COLOR, java.lang.Integer.toString(getLightColor()));
        jSONObject.put(ATT_VIBRATION_ENABLED, java.lang.Boolean.toString(shouldVibrate()));
        jSONObject.put("locked", java.lang.Integer.toString(getUserLockedFields()));
        jSONObject.put(ATT_FG_SERVICE_SHOWN, java.lang.Boolean.toString(isUserVisibleTaskShown()));
        jSONObject.put("vibration", longArrayToString(getVibrationPattern()));
        if (getVibrationEffect() != null) {
            jSONObject.put(ATT_VIBRATION_EFFECT, vibrationToString(getVibrationEffect()));
        }
        jSONObject.put(ATT_SHOW_BADGE, java.lang.Boolean.toString(canShowBadge()));
        jSONObject.put("deleted", java.lang.Boolean.toString(isDeleted()));
        jSONObject.put(ATT_DELETED_TIME_MS, java.lang.Long.toString(getDeletedTimeMs()));
        jSONObject.put(ATT_GROUP, getGroup());
        jSONObject.put(ATT_BLOCKABLE_SYSTEM, isBlockable());
        jSONObject.put(ATT_ALLOW_BUBBLE, getAllowBubbles());
        return jSONObject;
    }

    private static android.media.AudioAttributes safeAudioAttributes(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        int safeInt = safeInt(typedXmlPullParser, ATT_USAGE, 5);
        int safeInt2 = safeInt(typedXmlPullParser, ATT_CONTENT_TYPE, 4);
        return new android.media.AudioAttributes.Builder().setUsage(safeInt).setContentType(safeInt2).setFlags(safeInt(typedXmlPullParser, "flags", 0)).build();
    }

    private static android.net.Uri safeUri(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue == null) {
            return null;
        }
        return android.net.Uri.parse(attributeValue);
    }

    private static java.lang.String vibrationToString(android.os.VibrationEffect vibrationEffect) {
        java.io.StringWriter stringWriter = new java.io.StringWriter();
        try {
            android.os.vibrator.persistence.VibrationXmlSerializer.serialize(vibrationEffect, stringWriter, 1);
        } catch (java.io.IOException e) {
            android.util.Log.e(TAG, "Unable to serialize vibration: " + vibrationEffect, e);
        }
        return stringWriter.toString();
    }

    private static android.os.VibrationEffect safeVibrationEffect(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (attributeValue != null) {
            try {
                return android.os.vibrator.persistence.VibrationXmlParser.parseVibrationEffect(new java.io.StringReader(attributeValue), 1);
            } catch (java.io.IOException e) {
                android.util.Log.e(TAG, "Unable to read serialized vibration effect", e);
            }
        }
        return null;
    }

    private static int safeInt(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, int i) {
        return typedXmlPullParser.getAttributeInt(null, str, i);
    }

    private static boolean safeBool(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, boolean z) {
        return typedXmlPullParser.getAttributeBoolean(null, str, z);
    }

    private static long[] safeLongArray(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser, java.lang.String str, long[] jArr) {
        java.lang.String attributeValue = typedXmlPullParser.getAttributeValue(null, str);
        if (android.text.TextUtils.isEmpty(attributeValue)) {
            return jArr;
        }
        java.lang.String[] split = attributeValue.split(",");
        long[] jArr2 = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                jArr2[i] = java.lang.Long.parseLong(split[i]);
            } catch (java.lang.NumberFormatException e) {
                jArr2[i] = 0;
            }
        }
        return jArr2;
    }

    private static java.lang.String longArrayToString(long[] jArr) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        if (jArr != null && jArr.length > 0) {
            for (int i = 0; i < jArr.length - 1; i++) {
                sb.append(jArr[i]).append(",");
            }
            sb.append(jArr[jArr.length - 1]);
        }
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.app.NotificationChannel notificationChannel = (android.app.NotificationChannel) obj;
        if (getImportance() == notificationChannel.getImportance() && this.mBypassDnd == notificationChannel.mBypassDnd && getLockscreenVisibility() == notificationChannel.getLockscreenVisibility() && this.mLights == notificationChannel.mLights && getLightColor() == notificationChannel.getLightColor() && getUserLockedFields() == notificationChannel.getUserLockedFields() && isUserVisibleTaskShown() == notificationChannel.isUserVisibleTaskShown() && this.mVibrationEnabled == notificationChannel.mVibrationEnabled && this.mShowBadge == notificationChannel.mShowBadge && isDeleted() == notificationChannel.isDeleted() && getDeletedTimeMs() == notificationChannel.getDeletedTimeMs() && isBlockable() == notificationChannel.isBlockable() && this.mAllowBubbles == notificationChannel.mAllowBubbles && java.util.Objects.equals(getId(), notificationChannel.getId()) && java.util.Objects.equals(getName(), notificationChannel.getName()) && java.util.Objects.equals(this.mDesc, notificationChannel.mDesc) && java.util.Objects.equals(getSound(), notificationChannel.getSound()) && java.util.Arrays.equals(this.mVibrationPattern, notificationChannel.mVibrationPattern) && java.util.Objects.equals(getVibrationEffect(), notificationChannel.getVibrationEffect()) && java.util.Objects.equals(getGroup(), notificationChannel.getGroup()) && java.util.Objects.equals(getAudioAttributes(), notificationChannel.getAudioAttributes()) && this.mImportanceLockedDefaultApp == notificationChannel.mImportanceLockedDefaultApp && this.mOriginalImportance == notificationChannel.mOriginalImportance && java.util.Objects.equals(getParentChannelId(), notificationChannel.getParentChannelId()) && java.util.Objects.equals(getConversationId(), notificationChannel.getConversationId()) && isDemoted() == notificationChannel.isDemoted() && isImportantConversation() == notificationChannel.isImportantConversation()) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (java.util.Objects.hash(getId(), getName(), this.mDesc, java.lang.Integer.valueOf(getImportance()), java.lang.Boolean.valueOf(this.mBypassDnd), java.lang.Integer.valueOf(getLockscreenVisibility()), getSound(), java.lang.Boolean.valueOf(this.mLights), java.lang.Integer.valueOf(getLightColor()), java.lang.Integer.valueOf(getUserLockedFields()), java.lang.Boolean.valueOf(isUserVisibleTaskShown()), java.lang.Boolean.valueOf(this.mVibrationEnabled), java.lang.Boolean.valueOf(this.mShowBadge), java.lang.Boolean.valueOf(isDeleted()), java.lang.Long.valueOf(getDeletedTimeMs()), getGroup(), getAudioAttributes(), java.lang.Boolean.valueOf(isBlockable()), java.lang.Integer.valueOf(this.mAllowBubbles), java.lang.Boolean.valueOf(this.mImportanceLockedDefaultApp), java.lang.Integer.valueOf(this.mOriginalImportance), getVibrationEffect(), this.mParentId, this.mConversationId, java.lang.Boolean.valueOf(this.mDemoted), java.lang.Boolean.valueOf(this.mImportantConvo)) * 31) + java.util.Arrays.hashCode(this.mVibrationPattern);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, boolean z) {
        printWriter.println(str + ("NotificationChannel{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mName=" + (z ? (java.lang.String) android.text.TextUtils.trimToLengthWithEllipsis(this.mName, 3) : this.mName) + getFieldsString() + '}'));
    }

    public java.lang.String toString() {
        return "NotificationChannel{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mName=" + this.mName + getFieldsString() + '}';
    }

    private java.lang.String getFieldsString() {
        return ", mDescription=" + (!android.text.TextUtils.isEmpty(this.mDesc) ? "hasDescription " : "") + ", mImportance=" + this.mImportance + ", mBypassDnd=" + this.mBypassDnd + ", mLockscreenVisibility=" + this.mLockscreenVisibility + ", mSound=" + this.mSound + ", mLights=" + this.mLights + ", mLightColor=" + this.mLightColor + ", mVibrationPattern=" + java.util.Arrays.toString(this.mVibrationPattern) + ", mVibrationEffect=" + (this.mVibrationEffect == null ? "null" : this.mVibrationEffect.toString()) + ", mUserLockedFields=" + java.lang.Integer.toHexString(this.mUserLockedFields) + ", mUserVisibleTaskShown=" + this.mUserVisibleTaskShown + ", mVibrationEnabled=" + this.mVibrationEnabled + ", mShowBadge=" + this.mShowBadge + ", mDeleted=" + this.mDeleted + ", mDeletedTimeMs=" + this.mDeletedTime + ", mGroup='" + this.mGroup + android.text.format.DateFormat.QUOTE + ", mAudioAttributes=" + this.mAudioAttributes + ", mBlockableSystem=" + this.mBlockableSystem + ", mAllowBubbles=" + this.mAllowBubbles + ", mImportanceLockedDefaultApp=" + this.mImportanceLockedDefaultApp + ", mOriginalImp=" + this.mOriginalImportance + ", mParent=" + this.mParentId + ", mConversationId=" + this.mConversationId + ", mDemoted=" + this.mDemoted + ", mImportantConvo=" + this.mImportantConvo + ", mLastNotificationUpdateTimeMs=" + this.mLastNotificationUpdateTimeMs;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName);
        protoOutputStream.write(1138166333443L, this.mDesc);
        protoOutputStream.write(1120986464260L, this.mImportance);
        protoOutputStream.write(1133871366149L, this.mBypassDnd);
        protoOutputStream.write(1120986464262L, this.mLockscreenVisibility);
        if (this.mSound != null) {
            protoOutputStream.write(1138166333447L, this.mSound.toString());
        }
        protoOutputStream.write(1133871366152L, this.mLights);
        protoOutputStream.write(1120986464265L, this.mLightColor);
        if (this.mVibrationPattern != null) {
            for (long j2 : this.mVibrationPattern) {
                protoOutputStream.write(android.app.NotificationChannelProto.VIBRATION, j2);
            }
        }
        protoOutputStream.write(1120986464267L, this.mUserLockedFields);
        protoOutputStream.write(1133871366162L, this.mUserVisibleTaskShown);
        protoOutputStream.write(1133871366156L, this.mVibrationEnabled);
        protoOutputStream.write(1133871366157L, this.mShowBadge);
        protoOutputStream.write(1133871366158L, this.mDeleted);
        protoOutputStream.write(1138166333455L, this.mGroup);
        if (this.mAudioAttributes != null) {
            this.mAudioAttributes.dumpDebug(protoOutputStream, 1146756268048L);
        }
        protoOutputStream.write(1133871366161L, this.mBlockableSystem);
        protoOutputStream.write(1133871366163L, this.mAllowBubbles);
        protoOutputStream.end(start);
    }
}
