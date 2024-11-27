package com.android.server.companion;

/* loaded from: classes.dex */
public interface AssociationStore {
    public static final int CHANGE_TYPE_ADDED = 0;
    public static final int CHANGE_TYPE_REMOVED = 1;
    public static final int CHANGE_TYPE_UPDATED_ADDRESS_CHANGED = 2;
    public static final int CHANGE_TYPE_UPDATED_ADDRESS_UNCHANGED = 3;

    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.SOURCE)
    public @interface ChangeType {
    }

    @android.annotation.Nullable
    android.companion.AssociationInfo getAssociationById(int i);

    @android.annotation.NonNull
    java.util.Collection<android.companion.AssociationInfo> getAssociations();

    @android.annotation.NonNull
    java.util.List<android.companion.AssociationInfo> getAssociationsByAddress(@android.annotation.NonNull java.lang.String str);

    @android.annotation.NonNull
    java.util.List<android.companion.AssociationInfo> getAssociationsForPackage(int i, @android.annotation.NonNull java.lang.String str);

    @android.annotation.Nullable
    android.companion.AssociationInfo getAssociationsForPackageWithAddress(int i, @android.annotation.NonNull java.lang.String str, @android.annotation.NonNull java.lang.String str2);

    @android.annotation.NonNull
    java.util.List<android.companion.AssociationInfo> getAssociationsForUser(int i);

    void registerListener(@android.annotation.NonNull com.android.server.companion.AssociationStore.OnChangeListener onChangeListener);

    void unregisterListener(@android.annotation.NonNull com.android.server.companion.AssociationStore.OnChangeListener onChangeListener);

    public interface OnChangeListener {
        default void onAssociationChanged(int i, android.companion.AssociationInfo associationInfo) {
            switch (i) {
                case 0:
                    onAssociationAdded(associationInfo);
                    break;
                case 1:
                    onAssociationRemoved(associationInfo);
                    break;
                case 2:
                    onAssociationUpdated(associationInfo, true);
                    break;
                case 3:
                    onAssociationUpdated(associationInfo, false);
                    break;
            }
        }

        default void onAssociationAdded(android.companion.AssociationInfo associationInfo) {
        }

        default void onAssociationRemoved(android.companion.AssociationInfo associationInfo) {
        }

        default void onAssociationUpdated(android.companion.AssociationInfo associationInfo, boolean z) {
        }
    }

    static java.lang.String changeTypeToString(int i) {
        switch (i) {
            case 0:
                return "ASSOCIATION_ADDED";
            case 1:
                return "ASSOCIATION_REMOVED";
            case 2:
                return "ASSOCIATION_UPDATED";
            case 3:
                return "ASSOCIATION_UPDATED_ADDRESS_UNCHANGED";
            default:
                return "Unknown (" + i + ")";
        }
    }
}
