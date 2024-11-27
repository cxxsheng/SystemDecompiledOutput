package android.service.controls.templates;

/* loaded from: classes3.dex */
public final class ControlTemplateWrapper implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.controls.templates.ControlTemplateWrapper> CREATOR = new android.os.Parcelable.Creator<android.service.controls.templates.ControlTemplateWrapper>() { // from class: android.service.controls.templates.ControlTemplateWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.templates.ControlTemplateWrapper createFromParcel(android.os.Parcel parcel) {
            return new android.service.controls.templates.ControlTemplateWrapper(android.service.controls.templates.ControlTemplate.createTemplateFromBundle(parcel.readBundle()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.controls.templates.ControlTemplateWrapper[] newArray(int i) {
            return new android.service.controls.templates.ControlTemplateWrapper[i];
        }
    };
    private final android.service.controls.templates.ControlTemplate mControlTemplate;

    public ControlTemplateWrapper(android.service.controls.templates.ControlTemplate controlTemplate) {
        com.android.internal.util.Preconditions.checkNotNull(controlTemplate);
        this.mControlTemplate = controlTemplate;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public android.service.controls.templates.ControlTemplate getWrappedTemplate() {
        return this.mControlTemplate;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeBundle(this.mControlTemplate.getDataBundle());
    }
}
