package android.service.notification;

/* loaded from: classes3.dex */
public final class Condition implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.Condition> CREATOR = new android.os.Parcelable.Creator<android.service.notification.Condition>() { // from class: android.service.notification.Condition.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.Condition createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.Condition(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.Condition[] newArray(int i) {
            return new android.service.notification.Condition[i];
        }
    };
    public static final int FLAG_RELEVANT_ALWAYS = 2;
    public static final int FLAG_RELEVANT_NOW = 1;
    public static final int MAX_STRING_LENGTH = 1000;
    public static final java.lang.String SCHEME = "condition";
    public static final int SOURCE_CONTEXT = 3;
    public static final int SOURCE_SCHEDULE = 2;
    public static final int SOURCE_UNKNOWN = 0;
    public static final int SOURCE_USER_ACTION = 1;
    public static final int STATE_ERROR = 3;
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;
    public static final int STATE_UNKNOWN = 2;
    public final int flags;
    public final int icon;
    public final android.net.Uri id;
    public final java.lang.String line1;
    public final java.lang.String line2;
    public final int source;
    public final int state;
    public final java.lang.String summary;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Source {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface State {
    }

    public Condition(android.net.Uri uri, java.lang.String str, int i) {
        this(uri, str, "", "", -1, i, 0, 2);
    }

    public Condition(android.net.Uri uri, java.lang.String str, int i, int i2) {
        this(uri, str, "", "", -1, i, i2, 2);
    }

    public Condition(android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3) {
        this(uri, str, str2, str3, i, i2, 0, i3);
    }

    public Condition(android.net.Uri uri, java.lang.String str, java.lang.String str2, java.lang.String str3, int i, int i2, int i3, int i4) {
        if (uri == null) {
            throw new java.lang.IllegalArgumentException("id is required");
        }
        if (str == null) {
            throw new java.lang.IllegalArgumentException("summary is required");
        }
        if (!isValidState(i2)) {
            throw new java.lang.IllegalArgumentException("state is invalid: " + i2);
        }
        this.id = getTrimmedUri(uri);
        this.summary = getTrimmedString(str);
        this.line1 = getTrimmedString(str2);
        this.line2 = getTrimmedString(str3);
        this.icon = i;
        this.state = i2;
        this.source = checkValidSource(i3);
        this.flags = i4;
    }

    public Condition(android.os.Parcel parcel) {
        this((android.net.Uri) parcel.readParcelable(android.service.notification.Condition.class.getClassLoader(), android.net.Uri.class), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), android.app.Flags.modesApi() ? parcel.readInt() : 0, parcel.readInt());
    }

    public void validate() {
        if (android.app.Flags.modesApi()) {
            checkValidSource(this.source);
        }
    }

    private static boolean isValidState(int i) {
        return i >= 0 && i <= 3;
    }

    private static int checkValidSource(int i) {
        if (android.app.Flags.modesApi()) {
            com.android.internal.util.Preconditions.checkArgument(i >= 0 && i <= 3, "Condition source must be one of SOURCE_UNKNOWN, SOURCE_USER_ACTION, SOURCE_SCHEDULE, or SOURCE_CONTEXT");
        }
        return i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeParcelable(this.id, 0);
        parcel.writeString(this.summary);
        parcel.writeString(this.line1);
        parcel.writeString(this.line2);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.state);
        if (android.app.Flags.modesApi()) {
            parcel.writeInt(this.source);
        }
        parcel.writeInt(this.flags);
    }

    public java.lang.String toString() {
        java.lang.StringBuilder append = new java.lang.StringBuilder(android.service.notification.Condition.class.getSimpleName()).append('[').append("state=").append(stateToString(this.state)).append(",id=").append(this.id).append(",summary=").append(this.summary).append(",line1=").append(this.line1).append(",line2=").append(this.line2).append(",icon=").append(this.icon);
        if (android.app.Flags.modesApi()) {
            append.append(",source=").append(sourceToString(this.source));
        }
        return append.append(",flags=").append(this.flags).append(']').toString();
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1138166333441L, this.id.toString());
        protoOutputStream.write(1138166333442L, this.summary);
        protoOutputStream.write(1138166333443L, this.line1);
        protoOutputStream.write(1138166333444L, this.line2);
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1159641169926L, this.state);
        protoOutputStream.write(1120986464263L, this.flags);
        protoOutputStream.end(start);
    }

    public static java.lang.String stateToString(int i) {
        if (i == 0) {
            return "STATE_FALSE";
        }
        if (i == 1) {
            return "STATE_TRUE";
        }
        if (i == 2) {
            return "STATE_UNKNOWN";
        }
        if (i == 3) {
            return "STATE_ERROR";
        }
        throw new java.lang.IllegalArgumentException("state is invalid: " + i);
    }

    public static java.lang.String sourceToString(int i) {
        if (i == 0) {
            return "SOURCE_UNKNOWN";
        }
        if (i == 1) {
            return "SOURCE_USER_ACTION";
        }
        if (i == 2) {
            return "SOURCE_SCHEDULE";
        }
        if (i == 3) {
            return "SOURCE_CONTEXT";
        }
        throw new java.lang.IllegalArgumentException("source is invalid: " + i);
    }

    public static java.lang.String relevanceToString(int i) {
        boolean z = (i & 1) != 0;
        boolean z2 = (i & 2) != 0;
        return (z || z2) ? (z && z2) ? "NOW, ALWAYS" : z ? "NOW" : "ALWAYS" : android.security.keystore.KeyProperties.DIGEST_NONE;
    }

    public boolean equals(java.lang.Object obj) {
        if (!(obj instanceof android.service.notification.Condition)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        android.service.notification.Condition condition = (android.service.notification.Condition) obj;
        boolean z = java.util.Objects.equals(condition.id, this.id) && java.util.Objects.equals(condition.summary, this.summary) && java.util.Objects.equals(condition.line1, this.line1) && java.util.Objects.equals(condition.line2, this.line2) && condition.icon == this.icon && condition.state == this.state && condition.flags == this.flags;
        if (android.app.Flags.modesApi()) {
            return z && condition.source == this.source;
        }
        return z;
    }

    public int hashCode() {
        if (android.app.Flags.modesApi()) {
            return java.util.Objects.hash(this.id, this.summary, this.line1, this.line2, java.lang.Integer.valueOf(this.icon), java.lang.Integer.valueOf(this.state), java.lang.Integer.valueOf(this.source), java.lang.Integer.valueOf(this.flags));
        }
        return java.util.Objects.hash(this.id, this.summary, this.line1, this.line2, java.lang.Integer.valueOf(this.icon), java.lang.Integer.valueOf(this.state), java.lang.Integer.valueOf(this.flags));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.service.notification.Condition copy() {
        android.os.Parcel obtain = android.os.Parcel.obtain();
        try {
            writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return new android.service.notification.Condition(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public static android.net.Uri.Builder newId(android.content.Context context) {
        return new android.net.Uri.Builder().scheme("condition").authority(context.getPackageName());
    }

    public static boolean isValidId(android.net.Uri uri, java.lang.String str) {
        return uri != null && "condition".equals(uri.getScheme()) && str.equals(uri.getAuthority());
    }

    private static java.lang.String getTrimmedString(java.lang.String str) {
        if (str != null && str.length() > 1000) {
            return str.substring(0, 1000);
        }
        return str;
    }

    private static android.net.Uri getTrimmedUri(android.net.Uri uri) {
        if (uri != null && uri.toString().length() > 1000) {
            return android.net.Uri.parse(getTrimmedString(uri.toString()));
        }
        return uri;
    }
}
