package android.flags;

/* loaded from: classes.dex */
public interface Flag<T> {
    android.flags.Flag<T> defineMetaData(java.lang.String str, java.lang.String str2, java.lang.String str3);

    T getDefault();

    java.lang.String getName();

    java.lang.String getNamespace();

    default boolean isDynamic() {
        return false;
    }

    default java.lang.String getLabel() {
        return getName();
    }

    default java.lang.String getDescription() {
        return null;
    }

    default java.lang.String getCategoryName() {
        return null;
    }
}
