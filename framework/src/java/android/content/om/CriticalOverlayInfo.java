package android.content.om;

/* loaded from: classes.dex */
public interface CriticalOverlayInfo {
    android.content.om.OverlayIdentifier getOverlayIdentifier();

    java.lang.String getOverlayName();

    java.lang.String getPackageName();

    java.lang.String getTargetOverlayableName();

    java.lang.String getTargetPackageName();

    boolean isFabricated();
}
