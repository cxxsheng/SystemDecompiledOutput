package android.service.contentcapture;

@android.annotation.SystemApi
/* loaded from: classes3.dex */
public final class SnapshotData implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.contentcapture.SnapshotData> CREATOR = new android.os.Parcelable.Creator<android.service.contentcapture.SnapshotData>() { // from class: android.service.contentcapture.SnapshotData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.SnapshotData createFromParcel(android.os.Parcel parcel) {
            return new android.service.contentcapture.SnapshotData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.contentcapture.SnapshotData[] newArray(int i) {
            return new android.service.contentcapture.SnapshotData[i];
        }
    };
    private final android.app.assist.AssistContent mAssistContent;
    private final android.os.Bundle mAssistData;
    private final android.app.assist.AssistStructure mAssistStructure;

    public SnapshotData(android.os.Bundle bundle, android.app.assist.AssistStructure assistStructure, android.app.assist.AssistContent assistContent) {
        this.mAssistData = bundle;
        this.mAssistStructure = assistStructure;
        this.mAssistContent = assistContent;
    }

    SnapshotData(android.os.Parcel parcel) {
        this.mAssistData = parcel.readBundle();
        this.mAssistStructure = (android.app.assist.AssistStructure) parcel.readParcelable(null, android.app.assist.AssistStructure.class);
        this.mAssistContent = (android.app.assist.AssistContent) parcel.readParcelable(null, android.app.assist.AssistContent.class);
    }

    public android.os.Bundle getAssistData() {
        return this.mAssistData;
    }

    public android.app.assist.AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    public android.app.assist.AssistContent getAssistContent() {
        return this.mAssistContent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mAssistData);
        parcel.writeParcelable(this.mAssistStructure, i);
        parcel.writeParcelable(this.mAssistContent, i);
    }
}
