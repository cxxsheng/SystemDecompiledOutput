package android.app.contentsuggestions;

@android.annotation.SystemApi
/* loaded from: classes.dex */
public final class SelectionsRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.contentsuggestions.SelectionsRequest> CREATOR = new android.os.Parcelable.Creator<android.app.contentsuggestions.SelectionsRequest>() { // from class: android.app.contentsuggestions.SelectionsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.SelectionsRequest createFromParcel(android.os.Parcel parcel) {
            return new android.app.contentsuggestions.SelectionsRequest(parcel.readInt(), (android.graphics.Point) parcel.readTypedObject(android.graphics.Point.CREATOR), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.contentsuggestions.SelectionsRequest[] newArray(int i) {
            return new android.app.contentsuggestions.SelectionsRequest[i];
        }
    };
    private final android.os.Bundle mExtras;
    private final android.graphics.Point mInterestPoint;
    private final int mTaskId;

    private SelectionsRequest(int i, android.graphics.Point point, android.os.Bundle bundle) {
        this.mTaskId = i;
        this.mInterestPoint = point;
        this.mExtras = bundle;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public android.graphics.Point getInterestPoint() {
        return this.mInterestPoint;
    }

    public android.os.Bundle getExtras() {
        return this.mExtras == null ? new android.os.Bundle() : this.mExtras;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeTypedObject(this.mInterestPoint, i);
        parcel.writeBundle(this.mExtras);
    }

    @android.annotation.SystemApi
    public static final class Builder {
        private android.os.Bundle mExtras;
        private android.graphics.Point mInterestPoint;
        private final int mTaskId;

        public Builder(int i) {
            this.mTaskId = i;
        }

        public android.app.contentsuggestions.SelectionsRequest.Builder setExtras(android.os.Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public android.app.contentsuggestions.SelectionsRequest.Builder setInterestPoint(android.graphics.Point point) {
            this.mInterestPoint = point;
            return this;
        }

        public android.app.contentsuggestions.SelectionsRequest build() {
            return new android.app.contentsuggestions.SelectionsRequest(this.mTaskId, this.mInterestPoint, this.mExtras);
        }
    }
}
