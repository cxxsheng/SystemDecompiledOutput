package android.view.inputmethod;

/* loaded from: classes4.dex */
public class ExtractedTextRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.inputmethod.ExtractedTextRequest> CREATOR = new android.os.Parcelable.Creator<android.view.inputmethod.ExtractedTextRequest>() { // from class: android.view.inputmethod.ExtractedTextRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ExtractedTextRequest createFromParcel(android.os.Parcel parcel) {
            android.view.inputmethod.ExtractedTextRequest extractedTextRequest = new android.view.inputmethod.ExtractedTextRequest();
            extractedTextRequest.token = parcel.readInt();
            extractedTextRequest.flags = parcel.readInt();
            extractedTextRequest.hintMaxLines = parcel.readInt();
            extractedTextRequest.hintMaxChars = parcel.readInt();
            return extractedTextRequest;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.inputmethod.ExtractedTextRequest[] newArray(int i) {
            return new android.view.inputmethod.ExtractedTextRequest[i];
        }
    };
    public int flags;
    public int hintMaxChars;
    public int hintMaxLines;
    public int token;

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.token);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.hintMaxLines);
        parcel.writeInt(this.hintMaxChars);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
