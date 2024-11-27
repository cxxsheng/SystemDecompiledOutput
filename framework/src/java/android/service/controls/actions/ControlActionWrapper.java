package android.service.controls.actions;

/* loaded from: classes3.dex */
public final class ControlActionWrapper implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.controls.actions.ControlActionWrapper> CREATOR = new android.os.Parcelable.Creator<android.service.controls.actions.ControlActionWrapper>() { // from class: android.service.controls.actions.ControlActionWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.actions.ControlActionWrapper createFromParcel(android.os.Parcel parcel) {
            return new android.service.controls.actions.ControlActionWrapper(android.service.controls.actions.ControlAction.createActionFromBundle(parcel.readBundle()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.actions.ControlActionWrapper[] newArray(int i) {
            return new android.service.controls.actions.ControlActionWrapper[i];
        }
    };
    private final android.service.controls.actions.ControlAction mControlAction;

    public ControlActionWrapper(android.service.controls.actions.ControlAction controlAction) {
        com.android.internal.util.Preconditions.checkNotNull(controlAction);
        this.mControlAction = controlAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mControlAction.getDataBundle());
    }

    public android.service.controls.actions.ControlAction getWrappedAction() {
        return this.mControlAction;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
