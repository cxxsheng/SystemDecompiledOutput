package android.app;

/* loaded from: classes.dex */
public final class ApplicationStartInfo implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.ApplicationStartInfo> CREATOR = new android.os.Parcelable.Creator<android.app.ApplicationStartInfo>() { // from class: android.app.ApplicationStartInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ApplicationStartInfo createFromParcel(android.os.Parcel parcel) {
            return new android.app.ApplicationStartInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.ApplicationStartInfo[] newArray(int i) {
            return new android.app.ApplicationStartInfo[i];
        }
    };
    public static final int LAUNCH_MODE_SINGLE_INSTANCE = 2;
    public static final int LAUNCH_MODE_SINGLE_INSTANCE_PER_TASK = 4;
    public static final int LAUNCH_MODE_SINGLE_TASK = 3;
    public static final int LAUNCH_MODE_SINGLE_TOP = 1;
    public static final int LAUNCH_MODE_STANDARD = 0;
    private static final java.lang.String PROTO_SERIALIZER_ATTRIBUTE_INTENT = "intent";
    private static final java.lang.String PROTO_SERIALIZER_ATTRIBUTE_KEY = "key";
    private static final java.lang.String PROTO_SERIALIZER_ATTRIBUTE_TIMESTAMP = "timestamp";
    private static final java.lang.String PROTO_SERIALIZER_ATTRIBUTE_TIMESTAMPS = "timestamps";
    private static final java.lang.String PROTO_SERIALIZER_ATTRIBUTE_TS = "ts";
    public static final int STARTUP_STATE_ERROR = 1;
    public static final int STARTUP_STATE_FIRST_FRAME_DRAWN = 2;
    public static final int STARTUP_STATE_STARTED = 0;
    public static final int START_REASON_ALARM = 0;
    public static final int START_REASON_BACKUP = 1;
    public static final int START_REASON_BOOT_COMPLETE = 2;
    public static final int START_REASON_BROADCAST = 3;
    public static final int START_REASON_CONTENT_PROVIDER = 4;
    public static final int START_REASON_JOB = 5;
    public static final int START_REASON_LAUNCHER = 6;
    public static final int START_REASON_LAUNCHER_RECENTS = 7;
    public static final int START_REASON_OTHER = 8;
    public static final int START_REASON_PUSH = 9;
    public static final int START_REASON_SERVICE = 10;
    public static final int START_REASON_START_ACTIVITY = 11;
    public static final int START_TIMESTAMP_APPLICATION_ONCREATE = 2;
    public static final int START_TIMESTAMP_BIND_APPLICATION = 3;
    public static final int START_TIMESTAMP_FIRST_FRAME = 4;
    public static final int START_TIMESTAMP_FORK = 1;
    public static final int START_TIMESTAMP_FULLY_DRAWN = 5;
    public static final int START_TIMESTAMP_INITIAL_RENDERTHREAD_FRAME = 6;
    public static final int START_TIMESTAMP_LAUNCH = 0;
    public static final int START_TIMESTAMP_RESERVED_RANGE_DEVELOPER = 30;
    public static final int START_TIMESTAMP_RESERVED_RANGE_DEVELOPER_START = 21;
    public static final int START_TIMESTAMP_RESERVED_RANGE_SYSTEM = 20;
    public static final int START_TIMESTAMP_SURFACEFLINGER_COMPOSITION_COMPLETE = 7;
    public static final int START_TYPE_COLD = 1;
    public static final int START_TYPE_HOT = 3;
    public static final int START_TYPE_UNSET = 0;
    public static final int START_TYPE_WARM = 2;
    private int mDefiningUid;
    private int mLaunchMode;
    private java.lang.String mPackageName;
    private int mPackageUid;
    private int mPid;
    private java.lang.String mProcessName;
    private int mRealUid;
    private int mReason;
    private android.content.Intent mStartIntent;
    private int mStartType;
    private int mStartupState;
    private android.util.ArrayMap<java.lang.Integer, java.lang.Long> mStartupTimestampsNs;
    private boolean mWasForceStopped;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface LaunchMode {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartReason {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartType {
    }

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface StartupState {
    }

    public void setStartupState(int i) {
        this.mStartupState = i;
    }

    public void setPid(int i) {
        this.mPid = i;
    }

    public void setRealUid(int i) {
        this.mRealUid = i;
    }

    public void setPackageUid(int i) {
        this.mPackageUid = i;
    }

    public void setDefiningUid(int i) {
        this.mDefiningUid = i;
    }

    public void setPackageName(java.lang.String str) {
        this.mPackageName = intern(str);
    }

    public void setProcessName(java.lang.String str) {
        this.mProcessName = intern(str);
    }

    public void setReason(int i) {
        this.mReason = i;
    }

    public void addStartupTimestamp(int i, long j) {
        if (i < 0 || i > 30) {
            return;
        }
        if (this.mStartupTimestampsNs == null) {
            this.mStartupTimestampsNs = new android.util.ArrayMap<>();
        }
        this.mStartupTimestampsNs.put(java.lang.Integer.valueOf(i), java.lang.Long.valueOf(j));
    }

    public void setStartType(int i) {
        this.mStartType = i;
    }

    public void setIntent(android.content.Intent intent) {
        if (intent != null) {
            this.mStartIntent = intent.maybeStripForHistory();
        }
    }

    public void setLaunchMode(int i) {
        this.mLaunchMode = i;
    }

    public void setForceStopped(boolean z) {
        this.mWasForceStopped = z;
    }

    public int getStartupState() {
        return this.mStartupState;
    }

    public int getPid() {
        return this.mPid;
    }

    public int getRealUid() {
        return this.mRealUid;
    }

    public int getPackageUid() {
        return this.mPackageUid;
    }

    public int getDefiningUid() {
        return this.mDefiningUid;
    }

    public java.lang.String getPackageName() {
        return this.mPackageName;
    }

    public java.lang.String getProcessName() {
        return this.mProcessName;
    }

    public int getReason() {
        return this.mReason;
    }

    public java.util.Map<java.lang.Integer, java.lang.Long> getStartupTimestamps() {
        if (this.mStartupTimestampsNs == null) {
            this.mStartupTimestampsNs = new android.util.ArrayMap<>();
        }
        return this.mStartupTimestampsNs;
    }

    public int getStartType() {
        return this.mStartType;
    }

    public android.content.Intent getIntent() {
        return this.mStartIntent;
    }

    public int getLaunchMode() {
        return this.mLaunchMode;
    }

    public boolean wasForceStopped() {
        return this.mWasForceStopped;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mStartupState);
        parcel.writeInt(this.mPid);
        parcel.writeInt(this.mRealUid);
        parcel.writeInt(this.mPackageUid);
        parcel.writeInt(this.mDefiningUid);
        parcel.writeString(this.mPackageName);
        parcel.writeString(this.mProcessName);
        parcel.writeInt(this.mReason);
        parcel.writeInt(this.mStartupTimestampsNs == null ? 0 : this.mStartupTimestampsNs.size());
        if (this.mStartupTimestampsNs != null) {
            for (int i2 = 0; i2 < this.mStartupTimestampsNs.size(); i2++) {
                parcel.writeInt(this.mStartupTimestampsNs.keyAt(i2).intValue());
                parcel.writeLong(this.mStartupTimestampsNs.valueAt(i2).longValue());
            }
        }
        parcel.writeInt(this.mStartType);
        parcel.writeParcelable(this.mStartIntent, i);
        parcel.writeInt(this.mLaunchMode);
        parcel.writeBoolean(this.mWasForceStopped);
    }

    public ApplicationStartInfo() {
    }

    public ApplicationStartInfo(android.app.ApplicationStartInfo applicationStartInfo) {
        this.mStartupState = applicationStartInfo.mStartupState;
        this.mPid = applicationStartInfo.mPid;
        this.mRealUid = applicationStartInfo.mRealUid;
        this.mPackageUid = applicationStartInfo.mPackageUid;
        this.mDefiningUid = applicationStartInfo.mDefiningUid;
        this.mPackageName = applicationStartInfo.mPackageName;
        this.mProcessName = applicationStartInfo.mProcessName;
        this.mReason = applicationStartInfo.mReason;
        this.mStartupTimestampsNs = applicationStartInfo.mStartupTimestampsNs;
        this.mStartType = applicationStartInfo.mStartType;
        this.mStartIntent = applicationStartInfo.mStartIntent;
        this.mLaunchMode = applicationStartInfo.mLaunchMode;
        this.mWasForceStopped = applicationStartInfo.mWasForceStopped;
    }

    private ApplicationStartInfo(android.os.Parcel parcel) {
        this.mStartupState = parcel.readInt();
        this.mPid = parcel.readInt();
        this.mRealUid = parcel.readInt();
        this.mPackageUid = parcel.readInt();
        this.mDefiningUid = parcel.readInt();
        this.mPackageName = intern(parcel.readString());
        this.mProcessName = intern(parcel.readString());
        this.mReason = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            addStartupTimestamp(parcel.readInt(), parcel.readLong());
        }
        this.mStartType = parcel.readInt();
        this.mStartIntent = (android.content.Intent) parcel.readParcelable(android.content.Intent.class.getClassLoader(), android.content.Intent.class);
        this.mLaunchMode = parcel.readInt();
        this.mWasForceStopped = parcel.readBoolean();
    }

    private static java.lang.String intern(java.lang.String str) {
        if (str != null) {
            return str.intern();
        }
        return null;
    }

    public void writeToProto(android.util.proto.ProtoOutputStream protoOutputStream, long j) throws java.io.IOException {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mPid);
        protoOutputStream.write(1120986464258L, this.mRealUid);
        protoOutputStream.write(1120986464259L, this.mPackageUid);
        protoOutputStream.write(1120986464260L, this.mDefiningUid);
        protoOutputStream.write(1138166333445L, this.mProcessName);
        protoOutputStream.write(1159641169926L, this.mStartupState);
        protoOutputStream.write(1159641169927L, this.mReason);
        if (this.mStartupTimestampsNs != null && this.mStartupTimestampsNs.size() > 0) {
            java.io.ByteArrayOutputStream byteArrayOutputStream = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream objectOutputStream = new java.io.ObjectOutputStream(byteArrayOutputStream);
            com.android.modules.utils.TypedXmlSerializer resolveSerializer = android.util.Xml.resolveSerializer(objectOutputStream);
            resolveSerializer.startDocument(null, true);
            resolveSerializer.startTag(null, "timestamps");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                resolveSerializer.startTag(null, "timestamp");
                resolveSerializer.attributeInt(null, "key", this.mStartupTimestampsNs.keyAt(i).intValue());
                resolveSerializer.attributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS, this.mStartupTimestampsNs.valueAt(i).longValue());
                resolveSerializer.endTag(null, "timestamp");
            }
            resolveSerializer.endTag(null, "timestamps");
            resolveSerializer.endDocument();
            protoOutputStream.write(1151051235336L, byteArrayOutputStream.toByteArray());
            objectOutputStream.close();
        }
        protoOutputStream.write(1159641169929L, this.mStartType);
        if (this.mStartIntent != null) {
            java.io.ByteArrayOutputStream byteArrayOutputStream2 = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream objectOutputStream2 = new java.io.ObjectOutputStream(byteArrayOutputStream2);
            com.android.modules.utils.TypedXmlSerializer resolveSerializer2 = android.util.Xml.resolveSerializer(objectOutputStream2);
            resolveSerializer2.startDocument(null, true);
            resolveSerializer2.startTag(null, "intent");
            this.mStartIntent.saveToXml(resolveSerializer2);
            resolveSerializer2.endTag(null, "intent");
            resolveSerializer2.endDocument();
            protoOutputStream.write(android.app.ApplicationStartInfoProto.START_INTENT, byteArrayOutputStream2.toByteArray());
            objectOutputStream2.close();
        }
        protoOutputStream.write(1159641169931L, this.mLaunchMode);
        protoOutputStream.write(1133871366156L, this.mWasForceStopped);
        protoOutputStream.end(start);
    }

    public void readFromProto(android.util.proto.ProtoInputStream protoInputStream, long j) throws java.io.IOException, android.util.proto.WireTypeMismatchException, java.lang.ClassNotFoundException {
        long start = protoInputStream.start(j);
        while (protoInputStream.nextField() != -1) {
            switch (protoInputStream.getFieldNumber()) {
                case 1:
                    this.mPid = protoInputStream.readInt(1120986464257L);
                    break;
                case 2:
                    this.mRealUid = protoInputStream.readInt(1120986464258L);
                    break;
                case 3:
                    this.mPackageUid = protoInputStream.readInt(1120986464259L);
                    break;
                case 4:
                    this.mDefiningUid = protoInputStream.readInt(1120986464260L);
                    break;
                case 5:
                    this.mProcessName = intern(protoInputStream.readString(1138166333445L));
                    break;
                case 6:
                    this.mStartupState = protoInputStream.readInt(1159641169926L);
                    break;
                case 7:
                    this.mReason = protoInputStream.readInt(1159641169927L);
                    break;
                case 8:
                    java.io.ObjectInputStream objectInputStream = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(protoInputStream.readBytes(1151051235336L)));
                    this.mStartupTimestampsNs = new android.util.ArrayMap<>();
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser = android.util.Xml.resolvePullParser(objectInputStream);
                        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser, "timestamps");
                        int depth = resolvePullParser.getDepth();
                        while (com.android.internal.util.XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                            if ("timestamp".equals(resolvePullParser.getName())) {
                                this.mStartupTimestampsNs.put(java.lang.Integer.valueOf(resolvePullParser.getAttributeInt(null, "key")), java.lang.Long.valueOf(resolvePullParser.getAttributeLong(null, PROTO_SERIALIZER_ATTRIBUTE_TS)));
                            }
                        }
                    } catch (org.xmlpull.v1.XmlPullParserException e) {
                    }
                    objectInputStream.close();
                    break;
                case 9:
                    this.mStartType = protoInputStream.readInt(1159641169929L);
                    break;
                case 10:
                    java.io.ObjectInputStream objectInputStream2 = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(protoInputStream.readBytes(android.app.ApplicationStartInfoProto.START_INTENT)));
                    try {
                        com.android.modules.utils.TypedXmlPullParser resolvePullParser2 = android.util.Xml.resolvePullParser(objectInputStream2);
                        com.android.internal.util.XmlUtils.beginDocument(resolvePullParser2, "intent");
                        this.mStartIntent = android.content.Intent.restoreFromXml(resolvePullParser2);
                    } catch (org.xmlpull.v1.XmlPullParserException e2) {
                    }
                    objectInputStream2.close();
                    break;
                case 11:
                    this.mLaunchMode = protoInputStream.readInt(1159641169931L);
                    break;
                case 12:
                    this.mWasForceStopped = protoInputStream.readBoolean(1133871366156L);
                    break;
            }
        }
        protoInputStream.end(start);
    }

    public void dump(java.io.PrintWriter printWriter, java.lang.String str, java.lang.String str2, android.icu.text.SimpleDateFormat simpleDateFormat) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        sb.append(str).append("ApplicationStartInfo ").append(str2).append(com.android.internal.accessibility.common.ShortcutConstants.SERVICES_SEPARATOR).append('\n').append(" pid=").append(this.mPid).append(" realUid=").append(this.mRealUid).append(" packageUid=").append(this.mPackageUid).append(" definingUid=").append(this.mDefiningUid).append(" user=").append(android.os.UserHandle.getUserId(this.mPackageUid)).append('\n').append(" package=").append(this.mPackageName).append(" process=").append(this.mProcessName).append(" startupState=").append(this.mStartupState).append(" reason=").append(reasonToString(this.mReason)).append(" startType=").append(startTypeToString(this.mStartType)).append(" launchMode=").append(this.mLaunchMode).append(" wasForceStopped=").append(this.mWasForceStopped).append('\n');
        if (this.mStartIntent != null) {
            sb.append(" intent=").append(this.mStartIntent.toString()).append('\n');
        }
        if (this.mStartupTimestampsNs != null && this.mStartupTimestampsNs.size() > 0) {
            sb.append(" timestamps: ");
            for (int i = 0; i < this.mStartupTimestampsNs.size(); i++) {
                sb.append(this.mStartupTimestampsNs.keyAt(i)).append("=").append(this.mStartupTimestampsNs.valueAt(i)).append(" ");
            }
            sb.append('\n');
        }
        printWriter.print(sb.toString());
    }

    private static java.lang.String reasonToString(int i) {
        switch (i) {
            case 0:
                return "ALARM";
            case 1:
                return "BACKUP";
            case 2:
                return "BOOT COMPLETE";
            case 3:
                return "BROADCAST";
            case 4:
                return "CONTENT PROVIDER";
            case 5:
                return "JOB";
            case 6:
                return "LAUNCHER";
            case 7:
                return "LAUNCHER RECENTS";
            case 8:
                return "OTHER";
            case 9:
                return "PUSH";
            case 10:
                return "SERVICE";
            case 11:
                return "START ACTIVITY";
            default:
                return "";
        }
    }

    private static java.lang.String startTypeToString(int i) {
        switch (i) {
            case 0:
                return "UNSET";
            case 1:
                return "COLD";
            case 2:
                return "WARM";
            case 3:
                return "HOT";
            default:
                return "";
        }
    }

    public boolean equals(java.lang.Object obj) {
        if (obj == null || !(obj instanceof android.app.ApplicationStartInfo)) {
            return false;
        }
        android.app.ApplicationStartInfo applicationStartInfo = (android.app.ApplicationStartInfo) obj;
        return this.mPid == applicationStartInfo.mPid && this.mRealUid == applicationStartInfo.mRealUid && this.mPackageUid == applicationStartInfo.mPackageUid && this.mDefiningUid == applicationStartInfo.mDefiningUid && this.mReason == applicationStartInfo.mReason && this.mStartupState == applicationStartInfo.mStartupState && this.mStartType == applicationStartInfo.mStartType && this.mLaunchMode == applicationStartInfo.mLaunchMode && android.text.TextUtils.equals(this.mProcessName, applicationStartInfo.mProcessName) && timestampsEquals(applicationStartInfo) && this.mWasForceStopped == applicationStartInfo.mWasForceStopped;
    }

    public int hashCode() {
        return java.util.Objects.hash(java.lang.Integer.valueOf(this.mPid), java.lang.Integer.valueOf(this.mRealUid), java.lang.Integer.valueOf(this.mPackageUid), java.lang.Integer.valueOf(this.mDefiningUid), java.lang.Integer.valueOf(this.mReason), java.lang.Integer.valueOf(this.mStartupState), java.lang.Integer.valueOf(this.mStartType), java.lang.Integer.valueOf(this.mLaunchMode), this.mProcessName, this.mStartupTimestampsNs);
    }

    private boolean timestampsEquals(android.app.ApplicationStartInfo applicationStartInfo) {
        if (this.mStartupTimestampsNs == null && applicationStartInfo.mStartupTimestampsNs == null) {
            return true;
        }
        if (this.mStartupTimestampsNs == null || applicationStartInfo.mStartupTimestampsNs == null) {
            return false;
        }
        return this.mStartupTimestampsNs.equals(applicationStartInfo.mStartupTimestampsNs);
    }
}
