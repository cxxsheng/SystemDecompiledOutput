package android.content;

/* loaded from: classes.dex */
public class ClipDescription implements android.os.Parcelable {
    public static final int CLASSIFICATION_COMPLETE = 3;
    public static final int CLASSIFICATION_NOT_COMPLETE = 1;
    public static final int CLASSIFICATION_NOT_PERFORMED = 2;
    public static final android.os.Parcelable.Creator<android.content.ClipDescription> CREATOR = new android.os.Parcelable.Creator<android.content.ClipDescription>() { // from class: android.content.ClipDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ClipDescription createFromParcel(android.os.Parcel parcel) {
            return new android.content.ClipDescription(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.content.ClipDescription[] newArray(int i) {
            return new android.content.ClipDescription[i];
        }
    };
    public static final java.lang.String EXTRA_ACTIVITY_OPTIONS = "android.intent.extra.ACTIVITY_OPTIONS";
    public static final java.lang.String EXTRA_IS_REMOTE_DEVICE = "android.content.extra.IS_REMOTE_DEVICE";
    public static final java.lang.String EXTRA_IS_SENSITIVE = "android.content.extra.IS_SENSITIVE";
    public static final java.lang.String EXTRA_LOGGING_INSTANCE_ID = "android.intent.extra.LOGGING_INSTANCE_ID";
    public static final java.lang.String EXTRA_PENDING_INTENT = "android.intent.extra.PENDING_INTENT";
    public static final java.lang.String MIMETYPE_APPLICATION_ACTIVITY = "application/vnd.android.activity";
    public static final java.lang.String MIMETYPE_APPLICATION_SHORTCUT = "application/vnd.android.shortcut";
    public static final java.lang.String MIMETYPE_APPLICATION_TASK = "application/vnd.android.task";
    public static final java.lang.String MIMETYPE_TEXT_HTML = "text/html";
    public static final java.lang.String MIMETYPE_TEXT_INTENT = "text/vnd.android.intent";
    public static final java.lang.String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final java.lang.String MIMETYPE_TEXT_URILIST = "text/uri-list";
    public static final java.lang.String MIMETYPE_UNKNOWN = "application/octet-stream";
    private int mClassificationStatus;
    private final android.util.ArrayMap<java.lang.String, java.lang.Float> mEntityConfidence;
    private android.os.PersistableBundle mExtras;
    private boolean mIsStyledText;
    final java.lang.CharSequence mLabel;
    private final java.util.ArrayList<java.lang.String> mMimeTypes;
    private long mTimeStamp;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    @interface ClassificationStatus {
    }

    public ClipDescription(java.lang.CharSequence charSequence, java.lang.String[] strArr) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mClassificationStatus = 1;
        if (strArr == null) {
            throw new java.lang.NullPointerException("mimeTypes is null");
        }
        this.mLabel = charSequence;
        this.mMimeTypes = new java.util.ArrayList<>(java.util.Arrays.asList(strArr));
    }

    public ClipDescription(android.content.ClipDescription clipDescription) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mClassificationStatus = 1;
        this.mLabel = clipDescription.mLabel;
        this.mMimeTypes = new java.util.ArrayList<>(clipDescription.mMimeTypes);
        this.mTimeStamp = clipDescription.mTimeStamp;
    }

    public static boolean compareMimeTypes(java.lang.String str, java.lang.String str2) {
        int length = str2.length();
        if (length == 3 && str2.equals("*/*")) {
            return true;
        }
        int indexOf = str2.indexOf(47);
        if (indexOf > 0) {
            if (length == indexOf + 2) {
                int i = indexOf + 1;
                if (str2.charAt(i) == '*') {
                    if (str2.regionMatches(0, str, 0, i)) {
                        return true;
                    }
                }
            }
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void setTimestamp(long j) {
        this.mTimeStamp = j;
    }

    public long getTimestamp() {
        return this.mTimeStamp;
    }

    public java.lang.CharSequence getLabel() {
        return this.mLabel;
    }

    public boolean hasMimeType(java.lang.String str) {
        int size = this.mMimeTypes.size();
        for (int i = 0; i < size; i++) {
            if (compareMimeTypes(this.mMimeTypes.get(i), str)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasMimeType(java.lang.String[] strArr) {
        for (java.lang.String str : strArr) {
            if (hasMimeType(str)) {
                return true;
            }
        }
        return false;
    }

    public java.lang.String[] filterMimeTypes(java.lang.String str) {
        int size = this.mMimeTypes.size();
        java.util.ArrayList arrayList = null;
        for (int i = 0; i < size; i++) {
            if (compareMimeTypes(this.mMimeTypes.get(i), str)) {
                if (arrayList == null) {
                    arrayList = new java.util.ArrayList();
                }
                arrayList.add(this.mMimeTypes.get(i));
            }
        }
        if (arrayList == null) {
            return null;
        }
        java.lang.String[] strArr = new java.lang.String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }

    public int getMimeTypeCount() {
        return this.mMimeTypes.size();
    }

    public java.lang.String getMimeType(int i) {
        return this.mMimeTypes.get(i);
    }

    void addMimeTypes(java.lang.String[] strArr) {
        for (int i = 0; i != strArr.length; i++) {
            java.lang.String str = strArr[i];
            if (!this.mMimeTypes.contains(str)) {
                this.mMimeTypes.add(str);
            }
        }
    }

    public android.os.PersistableBundle getExtras() {
        return this.mExtras;
    }

    public void setExtras(android.os.PersistableBundle persistableBundle) {
        this.mExtras = new android.os.PersistableBundle(persistableBundle);
    }

    public void validate() {
        if (this.mMimeTypes == null) {
            throw new java.lang.NullPointerException("null mime types");
        }
        int size = this.mMimeTypes.size();
        if (size <= 0) {
            throw new java.lang.IllegalArgumentException("must have at least 1 mime type");
        }
        for (int i = 0; i < size; i++) {
            if (this.mMimeTypes.get(i) == null) {
                throw new java.lang.NullPointerException("mime type at " + i + " is null");
            }
        }
    }

    public boolean isStyledText() {
        return this.mIsStyledText;
    }

    void setIsStyledText(boolean z) {
        this.mIsStyledText = z;
    }

    public void setClassificationStatus(int i) {
        this.mClassificationStatus = i;
    }

    public float getConfidenceScore(java.lang.String str) {
        if (this.mClassificationStatus != 3) {
            throw new java.lang.IllegalStateException("Classification not complete");
        }
        return this.mEntityConfidence.getOrDefault(str, java.lang.Float.valueOf(0.0f)).floatValue();
    }

    public int getClassificationStatus() {
        return this.mClassificationStatus;
    }

    public void setConfidenceScores(java.util.Map<java.lang.String, java.lang.Float> map) {
        this.mEntityConfidence.clear();
        this.mEntityConfidence.putAll(map);
        this.mClassificationStatus = 3;
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder(128);
        sb.append("ClipDescription { ");
        toShortString(sb, true);
        sb.append(" }");
        return sb.toString();
    }

    public boolean toShortString(java.lang.StringBuilder sb, boolean z) {
        boolean z2 = !toShortStringTypesOnly(sb);
        boolean z3 = false;
        if (this.mLabel != null) {
            if (!z2) {
                sb.append(' ');
            }
            if (z) {
                sb.append("hasLabel(").append(this.mLabel.length()).append(')');
            } else {
                sb.append('\"').append(this.mLabel).append('\"');
            }
            z2 = false;
        }
        if (this.mExtras != null) {
            if (!z2) {
                sb.append(' ');
            }
            if (z) {
                if (this.mExtras.isParcelled()) {
                    sb.append("hasExtras");
                } else {
                    sb.append("hasExtras(").append(this.mExtras.size()).append(')');
                }
            } else {
                sb.append(this.mExtras.toString());
            }
            z2 = false;
        }
        if (this.mTimeStamp <= 0) {
            z3 = z2;
        } else {
            if (!z2) {
                sb.append(' ');
            }
            sb.append('<');
            sb.append(android.util.TimeUtils.logTimeOfDay(this.mTimeStamp));
            sb.append('>');
        }
        return !z3;
    }

    public boolean toShortStringTypesOnly(java.lang.StringBuilder sb) {
        int size = this.mMimeTypes.size();
        boolean z = true;
        int i = 0;
        while (i < size) {
            if (!z) {
                sb.append(' ');
            }
            sb.append(this.mMimeTypes.get(i));
            i++;
            z = false;
        }
        return !z;
    }

    public void dumpDebug(android.util.proto.ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        int size = this.mMimeTypes.size();
        for (int i = 0; i < size; i++) {
            protoOutputStream.write(2237677961217L, this.mMimeTypes.get(i));
        }
        if (this.mLabel != null) {
            protoOutputStream.write(1138166333442L, this.mLabel.toString());
        }
        if (this.mExtras != null) {
            this.mExtras.dumpDebug(protoOutputStream, 1146756268035L);
        }
        if (this.mTimeStamp > 0) {
            protoOutputStream.write(1112396529668L, this.mTimeStamp);
        }
        protoOutputStream.end(start);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        android.text.TextUtils.writeToParcel(this.mLabel, parcel, i);
        parcel.writeStringList(this.mMimeTypes);
        parcel.writePersistableBundle(this.mExtras);
        parcel.writeLong(this.mTimeStamp);
        parcel.writeBoolean(this.mIsStyledText);
        parcel.writeInt(this.mClassificationStatus);
        parcel.writeBundle(confidencesToBundle());
    }

    private android.os.Bundle confidencesToBundle() {
        android.os.Bundle bundle = new android.os.Bundle();
        int size = this.mEntityConfidence.size();
        for (int i = 0; i < size; i++) {
            bundle.putFloat(this.mEntityConfidence.keyAt(i), this.mEntityConfidence.valueAt(i).floatValue());
        }
        return bundle;
    }

    private void readBundleToConfidences(android.os.Bundle bundle) {
        for (java.lang.String str : bundle.keySet()) {
            this.mEntityConfidence.put(str, java.lang.Float.valueOf(bundle.getFloat(str)));
        }
    }

    ClipDescription(android.os.Parcel parcel) {
        this.mEntityConfidence = new android.util.ArrayMap<>();
        this.mClassificationStatus = 1;
        this.mLabel = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mMimeTypes = parcel.createStringArrayList();
        this.mExtras = parcel.readPersistableBundle();
        this.mTimeStamp = parcel.readLong();
        this.mIsStyledText = parcel.readBoolean();
        this.mClassificationStatus = parcel.readInt();
        readBundleToConfidences(parcel.readBundle());
    }
}
