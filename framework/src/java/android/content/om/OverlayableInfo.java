package android.content.om;

/* loaded from: classes.dex */
public final class OverlayableInfo {
    public final java.lang.String actor;
    public final java.lang.String name;

    public OverlayableInfo(java.lang.String str, java.lang.String str2) {
        this.name = str;
        com.android.internal.util.AnnotationValidations.validate((java.lang.Class<android.annotation.NonNull>) android.annotation.NonNull.class, (android.annotation.NonNull) null, (java.lang.Object) str);
        this.actor = str2;
    }

    public boolean equals(java.lang.Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        android.content.om.OverlayableInfo overlayableInfo = (android.content.om.OverlayableInfo) obj;
        if (java.util.Objects.equals(this.name, overlayableInfo.name) && java.util.Objects.equals(this.actor, overlayableInfo.actor)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((java.util.Objects.hashCode(this.name) + 31) * 31) + java.util.Objects.hashCode(this.actor);
    }

    @java.lang.Deprecated
    private void __metadata() {
    }
}
