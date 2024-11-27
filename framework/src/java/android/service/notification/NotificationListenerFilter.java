package android.service.notification;

/* loaded from: classes3.dex */
public class NotificationListenerFilter implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.service.notification.NotificationListenerFilter> CREATOR = new android.os.Parcelable.Creator<android.service.notification.NotificationListenerFilter>() { // from class: android.service.notification.NotificationListenerFilter.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationListenerFilter createFromParcel(android.os.Parcel parcel) {
            return new android.service.notification.NotificationListenerFilter(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.service.notification.NotificationListenerFilter[] newArray(int i) {
            return new android.service.notification.NotificationListenerFilter[i];
        }
    };
    private static final int DEFAULT_TYPES = 15;
    private int mAllowedNotificationTypes;
    private android.util.ArraySet<android.content.pm.VersionedPackage> mDisallowedPackages;

    public NotificationListenerFilter() {
        this.mAllowedNotificationTypes = 15;
        this.mDisallowedPackages = new android.util.ArraySet<>();
    }

    public NotificationListenerFilter(int i, android.util.ArraySet<android.content.pm.VersionedPackage> arraySet) {
        this.mAllowedNotificationTypes = i;
        this.mDisallowedPackages = arraySet;
    }

    protected NotificationListenerFilter(android.os.Parcel parcel) {
        this.mAllowedNotificationTypes = parcel.readInt();
        this.mDisallowedPackages = parcel.readArraySet(android.content.pm.VersionedPackage.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeInt(this.mAllowedNotificationTypes);
        parcel.writeArraySet(this.mDisallowedPackages);
    }

    public boolean isTypeAllowed(int i) {
        return (i & this.mAllowedNotificationTypes) != 0;
    }

    public boolean areAllTypesAllowed() {
        return 15 == this.mAllowedNotificationTypes;
    }

    public boolean isPackageAllowed(android.content.pm.VersionedPackage versionedPackage) {
        return !this.mDisallowedPackages.contains(versionedPackage);
    }

    public int getTypes() {
        return this.mAllowedNotificationTypes;
    }

    public android.util.ArraySet<android.content.pm.VersionedPackage> getDisallowedPackages() {
        return this.mDisallowedPackages;
    }

    public void setTypes(int i) {
        this.mAllowedNotificationTypes = i;
    }

    public void setDisallowedPackages(android.util.ArraySet<android.content.pm.VersionedPackage> arraySet) {
        this.mDisallowedPackages = arraySet;
    }

    public void removePackage(android.content.pm.VersionedPackage versionedPackage) {
        this.mDisallowedPackages.remove(versionedPackage);
    }

    public void addPackage(android.content.pm.VersionedPackage versionedPackage) {
        this.mDisallowedPackages.add(versionedPackage);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
