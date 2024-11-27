package android.os;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class IncidentReportArgs implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.os.IncidentReportArgs> CREATOR = new android.os.Parcelable.Creator<android.os.IncidentReportArgs>() { // from class: android.os.IncidentReportArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.IncidentReportArgs createFromParcel(android.os.Parcel parcel) {
            return new android.os.IncidentReportArgs(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.os.IncidentReportArgs[] newArray(int i) {
            return new android.os.IncidentReportArgs[i];
        }
    };
    private boolean mAll;
    private final java.util.ArrayList<byte[]> mHeaders;
    private int mPrivacyPolicy;
    private java.lang.String mReceiverCls;
    private java.lang.String mReceiverPkg;
    private final android.util.IntArray mSections;

    public IncidentReportArgs() {
        this.mSections = new android.util.IntArray();
        this.mHeaders = new java.util.ArrayList<>();
        this.mPrivacyPolicy = 200;
    }

    public IncidentReportArgs(android.os.Parcel parcel) {
        this.mSections = new android.util.IntArray();
        this.mHeaders = new java.util.ArrayList<>();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAll ? 1 : 0);
        int size = this.mSections.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(this.mSections.get(i2));
        }
        int size2 = this.mHeaders.size();
        parcel.writeInt(size2);
        for (int i3 = 0; i3 < size2; i3++) {
            parcel.writeByteArray(this.mHeaders.get(i3));
        }
        parcel.writeInt(this.mPrivacyPolicy);
        parcel.writeString(this.mReceiverPkg);
        parcel.writeString(this.mReceiverCls);
    }

    public void readFromParcel(android.os.Parcel parcel) {
        this.mAll = parcel.readInt() != 0;
        this.mSections.clear();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mSections.add(parcel.readInt());
        }
        this.mHeaders.clear();
        int readInt2 = parcel.readInt();
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.mHeaders.add(parcel.createByteArray());
        }
        this.mPrivacyPolicy = parcel.readInt();
        this.mReceiverPkg = parcel.readString();
        this.mReceiverCls = parcel.readString();
    }

    public java.lang.String toString() {
        java.lang.StringBuilder sb = new java.lang.StringBuilder("Incident(");
        if (this.mAll) {
            sb.append("all");
        } else {
            int size = this.mSections.size();
            if (size > 0) {
                sb.append(this.mSections.get(0));
            }
            for (int i = 1; i < size; i++) {
                sb.append(" ");
                sb.append(this.mSections.get(i));
            }
        }
        sb.append(", ");
        sb.append(this.mHeaders.size());
        sb.append(" headers), ");
        sb.append("privacy: ").append(this.mPrivacyPolicy);
        sb.append("receiver pkg: ").append(this.mReceiverPkg);
        sb.append("receiver cls: ").append(this.mReceiverCls);
        return sb.toString();
    }

    public void setAll(boolean z) {
        this.mAll = z;
        if (z) {
            this.mSections.clear();
        }
    }

    public void setPrivacyPolicy(int i) {
        switch (i) {
            case 0:
            case 100:
            case 200:
                this.mPrivacyPolicy = i;
                break;
            default:
                this.mPrivacyPolicy = 200;
                break;
        }
    }

    public void addSection(int i) {
        if (!this.mAll && i > 1) {
            this.mSections.add(i);
        }
    }

    public boolean isAll() {
        return this.mAll;
    }

    public boolean containsSection(int i) {
        return this.mAll || this.mSections.indexOf(i) >= 0;
    }

    public int sectionCount() {
        return this.mSections.size();
    }

    public void addHeader(byte[] bArr) {
        this.mHeaders.add(bArr);
    }
}
