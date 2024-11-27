package android.app;

/* loaded from: classes.dex */
public final class NotificationChannelGroup implements android.os.Parcelable {
    private static final java.lang.String ATT_BLOCKED = "blocked";
    private static final java.lang.String ATT_DESC = "desc";
    private static final java.lang.String ATT_ID = "id";
    private static final java.lang.String ATT_NAME = "name";
    private static final java.lang.String ATT_USER_LOCKED = "locked";
    public static final android.os.Parcelable.Creator<android.app.NotificationChannelGroup> CREATOR = new android.os.Parcelable.Creator<android.app.NotificationChannelGroup>() { // from class: android.app.NotificationChannelGroup.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationChannelGroup createFromParcel(android.os.Parcel parcel) {
            return new android.app.NotificationChannelGroup(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.NotificationChannelGroup[] newArray(int i) {
            return new android.app.NotificationChannelGroup[i];
        }
    };
    public static final int MAX_TEXT_LENGTH = 1000;
    private static final java.lang.String TAG_GROUP = "channelGroup";
    public static final int USER_LOCKED_BLOCKED_STATE = 1;
    private boolean mBlocked;
    private java.util.List<android.app.NotificationChannel> mChannels;
    private java.lang.String mDescription;
    private final java.lang.String mId;
    private java.lang.CharSequence mName;
    private int mUserLockedFields;

    public NotificationChannelGroup(java.lang.String str, java.lang.CharSequence charSequence) {
        this.mChannels = new java.util.ArrayList();
        this.mId = getTrimmedString(str);
        this.mName = charSequence != null ? getTrimmedString(charSequence.toString()) : null;
    }

    protected NotificationChannelGroup(android.os.Parcel parcel) {
        this.mChannels = new java.util.ArrayList();
        if (parcel.readByte() != 0) {
            this.mId = getTrimmedString(parcel.readString());
        } else {
            this.mId = null;
        }
        if (parcel.readByte() != 0) {
            this.mName = getTrimmedString(parcel.readString());
        } else {
            this.mName = "";
        }
        if (parcel.readByte() != 0) {
            this.mDescription = getTrimmedString(parcel.readString());
        } else {
            this.mDescription = null;
        }
        if (parcel.readByte() != 0) {
            this.mChannels = ((android.content.pm.ParceledListSlice) parcel.readParcelable(android.app.NotificationChannelGroup.class.getClassLoader(), android.content.pm.ParceledListSlice.class)).getList();
        } else {
            this.mChannels = new java.util.ArrayList();
        }
        this.mBlocked = parcel.readBoolean();
        this.mUserLockedFields = parcel.readInt();
    }

    private java.lang.String getTrimmedString(java.lang.String str) {
        if (str != null && str.length() > 1000) {
            return str.substring(0, 1000);
        }
        return str;
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
            parcel.writeString(this.mName.toString());
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mDescription != null) {
            parcel.writeByte((byte) 1);
            parcel.writeString(this.mDescription);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mChannels != null) {
            parcel.writeByte((byte) 1);
            parcel.writeParcelable(new android.content.pm.ParceledListSlice(this.mChannels), i);
        } else {
            parcel.writeByte((byte) 0);
        }
        parcel.writeBoolean(this.mBlocked);
        parcel.writeInt(this.mUserLockedFields);
    }

    public java.lang.String getId() {
        return this.mId;
    }

    public java.lang.CharSequence getName() {
        return this.mName;
    }

    public java.lang.String getDescription() {
        return this.mDescription;
    }

    public java.util.List<android.app.NotificationChannel> getChannels() {
        return this.mChannels;
    }

    public boolean isBlocked() {
        return this.mBlocked;
    }

    public void setDescription(java.lang.String str) {
        this.mDescription = getTrimmedString(str);
    }

    public void setBlocked(boolean z) {
        this.mBlocked = z;
    }

    public void addChannel(android.app.NotificationChannel notificationChannel) {
        this.mChannels.add(notificationChannel);
    }

    public void setChannels(java.util.List<android.app.NotificationChannel> list) {
        this.mChannels = list;
    }

    public void lockFields(int i) {
        this.mUserLockedFields = i | this.mUserLockedFields;
    }

    public void unlockFields(int i) {
        this.mUserLockedFields = (~i) & this.mUserLockedFields;
    }

    public int getUserLockedFields() {
        return this.mUserLockedFields;
    }

    public void populateFromXml(com.android.modules.utils.TypedXmlPullParser typedXmlPullParser) {
        setDescription(typedXmlPullParser.getAttributeValue(null, ATT_DESC));
        setBlocked(typedXmlPullParser.getAttributeBoolean(null, "blocked", false));
    }

    public void writeXml(com.android.modules.utils.TypedXmlSerializer typedXmlSerializer) throws java.io.IOException {
        typedXmlSerializer.startTag(null, TAG_GROUP);
        typedXmlSerializer.attribute(null, "id", getId());
        if (getName() != null) {
            typedXmlSerializer.attribute(null, "name", getName().toString());
        }
        if (getDescription() != null) {
            typedXmlSerializer.attribute(null, ATT_DESC, getDescription().toString());
        }
        typedXmlSerializer.attributeBoolean(null, "blocked", isBlocked());
        typedXmlSerializer.attributeInt(null, "locked", this.mUserLockedFields);
        typedXmlSerializer.endTag(null, TAG_GROUP);
    }

    @android.annotation.SystemApi
    public org.json.JSONObject toJson() throws org.json.JSONException {
        org.json.JSONObject jSONObject = new org.json.JSONObject();
        jSONObject.put("id", getId());
        jSONObject.put("name", getName());
        jSONObject.put(ATT_DESC, getDescription());
        jSONObject.put("blocked", isBlocked());
        jSONObject.put("locked", this.mUserLockedFields);
        return jSONObject;
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
        android.app.NotificationChannelGroup notificationChannelGroup = (android.app.NotificationChannelGroup) obj;
        if (isBlocked() == notificationChannelGroup.isBlocked() && this.mUserLockedFields == notificationChannelGroup.mUserLockedFields && java.util.Objects.equals(getId(), notificationChannelGroup.getId()) && java.util.Objects.equals(getName(), notificationChannelGroup.getName()) && java.util.Objects.equals(getDescription(), notificationChannelGroup.getDescription()) && java.util.Objects.equals(getChannels(), notificationChannelGroup.getChannels())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return java.util.Objects.hash(getId(), getName(), getDescription(), java.lang.Boolean.valueOf(isBlocked()), getChannels(), java.lang.Integer.valueOf(this.mUserLockedFields));
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public android.app.NotificationChannelGroup m434clone() {
        android.app.NotificationChannelGroup notificationChannelGroup = new android.app.NotificationChannelGroup(getId(), getName());
        notificationChannelGroup.setDescription(getDescription());
        notificationChannelGroup.setBlocked(isBlocked());
        notificationChannelGroup.setChannels(getChannels());
        notificationChannelGroup.lockFields(this.mUserLockedFields);
        return notificationChannelGroup;
    }

    public java.lang.String toString() {
        return "NotificationChannelGroup{mId='" + this.mId + android.text.format.DateFormat.QUOTE + ", mName=" + ((java.lang.Object) this.mName) + ", mDescription=" + (!android.text.TextUtils.isEmpty(this.mDescription) ? "hasDescription " : "") + ", mBlocked=" + this.mBlocked + ", mChannels=" + this.mChannels + ", mUserLockedFields=" + this.mUserLockedFields + '}';
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.mId);
        protoOutputStream.write(1138166333442L, this.mName.toString());
        protoOutputStream.write(1138166333443L, this.mDescription);
        protoOutputStream.write(1133871366148L, this.mBlocked);
        java.util.Iterator<android.app.NotificationChannel> it = this.mChannels.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895813L);
        }
        protoOutputStream.end(start);
    }
}
