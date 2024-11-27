package android.service.autofill;

/* loaded from: classes3.dex */
public final class SavedDatasetsInfo {
    public static final java.lang.String TYPE_OTHER = "other";
    public static final java.lang.String TYPE_PASSWORDS = "passwords";
    private final int mCount;
    private final java.lang.String mType;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public SavedDatasetsInfo(java.lang.String str, int i) {
        this.mType = str;
        if (!java.util.Objects.equals(this.mType, "other") && !java.util.Objects.equals(this.mType, TYPE_PASSWORDS)) {
            throw new java.lang.IllegalArgumentException("type was " + this.mType + " but must be one of: TYPE_OTHER(other), TYPE_PASSWORDS(" + TYPE_PASSWORDS + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END);
        }
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) this.mType);
        this.mCount = i;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.IntRange>) android.annotation.IntRange.class, (android.annotation.IntRange) null, this.mCount, "from", 0L);
    }

    public java.lang.String getType() {
        return this.mType;
    }

    public int getCount() {
        return this.mCount;
    }

    public java.lang.String toString() {
        return "SavedDatasetsInfo { type = " + this.mType + ", count = " + this.mCount + " }";
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.service.autofill.SavedDatasetsInfo savedDatasetsInfo = (android.service.autofill.SavedDatasetsInfo) obj;
        if (java.util.Objects.equals(this.mType, savedDatasetsInfo.mType) && this.mCount == savedDatasetsInfo.mCount) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.mType) + 31) * 31) + this.mCount;
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
