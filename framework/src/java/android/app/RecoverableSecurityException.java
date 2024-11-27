package android.app;

/* loaded from: classes.dex */
public final class RecoverableSecurityException extends java.lang.SecurityException implements android.os.Parcelable {
    public static final android.os.Parcelable.Creator<android.app.RecoverableSecurityException> CREATOR = new android.os.Parcelable.Creator<android.app.RecoverableSecurityException>() { // from class: android.app.RecoverableSecurityException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RecoverableSecurityException createFromParcel(android.os.Parcel parcel) {
            return new android.app.RecoverableSecurityException(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public android.app.RecoverableSecurityException[] newArray(int i) {
            return new android.app.RecoverableSecurityException[i];
        }
    };
    private static final java.lang.String TAG = "RecoverableSecurityException";
    private final android.app.RemoteAction mUserAction;
    private final java.lang.CharSequence mUserMessage;

    public RecoverableSecurityException(android.os.Parcel parcel) {
        this(new java.lang.SecurityException(parcel.readString()), parcel.readCharSequence(), android.app.RemoteAction.CREATOR.createFromParcel(parcel));
    }

    public RecoverableSecurityException(java.lang.Throwable th, java.lang.CharSequence charSequence, android.app.RemoteAction remoteAction) {
        super(th.getMessage());
        this.mUserMessage = (java.lang.CharSequence) java.util.Objects.requireNonNull(charSequence);
        this.mUserAction = (android.app.RemoteAction) java.util.Objects.requireNonNull(remoteAction);
    }

    public java.lang.CharSequence getUserMessage() {
        return this.mUserMessage;
    }

    public android.app.RemoteAction getUserAction() {
        return this.mUserAction;
    }

    public void showAsNotification(android.content.Context context, java.lang.String str) {
        ((android.app.NotificationManager) context.getSystemService(android.app.NotificationManager.class)).notify(TAG, this.mUserAction.getActionIntent().getCreatorUid(), new android.app.Notification.Builder(context, str).setSmallIcon(com.android.internal.R.drawable.ic_print_error).setContentTitle(this.mUserAction.getTitle()).setContentText(this.mUserMessage).setContentIntent(this.mUserAction.getActionIntent()).setCategory(android.app.Notification.CATEGORY_ERROR).build());
    }

    public void showAsDialog(android.app.Activity activity) {
        android.app.RecoverableSecurityException.LocalDialog localDialog = new android.app.RecoverableSecurityException.LocalDialog();
        android.os.Bundle bundle = new android.os.Bundle();
        bundle.putParcelable(TAG, this);
        localDialog.setArguments(bundle);
        java.lang.String str = "RecoverableSecurityException_" + this.mUserAction.getActionIntent().getCreatorUid();
        android.app.FragmentManager fragmentManager = activity.getFragmentManager();
        android.app.FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        android.app.Fragment findFragmentByTag = fragmentManager.findFragmentByTag(str);
        if (findFragmentByTag != null) {
            beginTransaction.remove(findFragmentByTag);
        }
        beginTransaction.add(localDialog, str);
        beginTransaction.commitAllowingStateLoss();
    }

    public static class LocalDialog extends android.app.DialogFragment {
        @Override // android.app.DialogFragment
        public android.app.Dialog onCreateDialog(android.os.Bundle bundle) {
            final android.app.RecoverableSecurityException recoverableSecurityException = (android.app.RecoverableSecurityException) getArguments().getParcelable(android.app.RecoverableSecurityException.TAG, android.app.RecoverableSecurityException.class);
            return new android.app.AlertDialog.Builder(getActivity()).setMessage(recoverableSecurityException.mUserMessage).setPositiveButton(recoverableSecurityException.mUserAction.getTitle(), new android.content.DialogInterface.OnClickListener() { // from class: android.app.RecoverableSecurityException$LocalDialog$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(android.content.DialogInterface dialogInterface, int i) {
                    android.app.RecoverableSecurityException.this.mUserAction.getActionIntent().send();
                }
            }).setNegativeButton(17039360, (android.content.DialogInterface.OnClickListener) null).create();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(getMessage());
        parcel.writeCharSequence(this.mUserMessage);
        this.mUserAction.writeToParcel(parcel, i);
    }
}
