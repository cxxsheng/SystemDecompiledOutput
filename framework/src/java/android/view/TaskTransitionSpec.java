package android.view;

/* loaded from: classes4.dex */
public class TaskTransitionSpec implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.view.TaskTransitionSpec> CREATOR = new android.os.Parcelable.Creator<android.view.TaskTransitionSpec>() { // from class: android.view.TaskTransitionSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.TaskTransitionSpec createFromParcel(android.os.Parcel parcel) {
            return new android.view.TaskTransitionSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.view.TaskTransitionSpec[] newArray(int i) {
            return new android.view.TaskTransitionSpec[i];
        }
    };
    public final int backgroundColor;

    public TaskTransitionSpec(int i) {
        this.backgroundColor = i;
    }

    public TaskTransitionSpec(android.os.Parcel parcel) {
        this.backgroundColor = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.backgroundColor);
    }
}
