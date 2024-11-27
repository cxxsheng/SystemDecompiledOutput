package android.companion.datatransfer;

/* loaded from: classes.dex */
public class PermissionSyncRequest extends android.companion.datatransfer.SystemDataTransferRequest implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.companion.datatransfer.PermissionSyncRequest> CREATOR = new android.os.Parcelable.Creator<android.companion.datatransfer.PermissionSyncRequest>() { // from class: android.companion.datatransfer.PermissionSyncRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.datatransfer.PermissionSyncRequest createFromParcel(android.os.Parcel parcel) {
            return new android.companion.datatransfer.PermissionSyncRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.companion.datatransfer.PermissionSyncRequest[] newArray(int i) {
            return new android.companion.datatransfer.PermissionSyncRequest[i];
        }
    };

    public PermissionSyncRequest(int i) {
        super(i, 1);
    }

    public java.lang.String toString() {
        return "SystemDataTransferRequest(associationId=" + this.mAssociationId + ", userId=" + this.mUserId + ", isUserConsented=" + this.mUserConsented + android.inputmethodservice.navigationbar.NavigationBarInflaterView.KEY_CODE_END;
    }

    PermissionSyncRequest(android.os.Parcel parcel) {
        super(parcel);
    }

    @Override // android.companion.datatransfer.SystemDataTransferRequest
    public android.companion.datatransfer.PermissionSyncRequest copyWithNewId(int i) {
        android.companion.datatransfer.PermissionSyncRequest permissionSyncRequest = new android.companion.datatransfer.PermissionSyncRequest(i);
        permissionSyncRequest.mUserId = this.mUserId;
        permissionSyncRequest.mUserConsented = this.mUserConsented;
        return permissionSyncRequest;
    }
}
