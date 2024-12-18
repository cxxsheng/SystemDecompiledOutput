package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class DialogFragment extends android.app.Fragment implements android.content.DialogInterface.OnCancelListener, android.content.DialogInterface.OnDismissListener {
    private static final java.lang.String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final java.lang.String SAVED_CANCELABLE = "android:cancelable";
    private static final java.lang.String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final java.lang.String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final java.lang.String SAVED_STYLE = "android:style";
    private static final java.lang.String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    android.app.Dialog mDialog;
    boolean mDismissed;
    boolean mShownByMe;
    boolean mViewDestroyed;
    int mStyle = 0;
    int mTheme = 0;
    boolean mCancelable = true;
    boolean mShowsDialog = true;
    int mBackStackId = -1;

    public void setStyle(int i, int i2) {
        this.mStyle = i;
        if (this.mStyle == 2 || this.mStyle == 3) {
            this.mTheme = com.android.internal.R.style.Theme_DeviceDefault_Dialog_NoFrame;
        }
        if (i2 != 0) {
            this.mTheme = i2;
        }
    }

    public void show(android.app.FragmentManager fragmentManager, java.lang.String str) {
        this.mDismissed = false;
        this.mShownByMe = true;
        android.app.FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commit();
    }

    public void showAllowingStateLoss(android.app.FragmentManager fragmentManager, java.lang.String str) {
        this.mDismissed = false;
        this.mShownByMe = true;
        android.app.FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commitAllowingStateLoss();
    }

    public int show(android.app.FragmentTransaction fragmentTransaction, java.lang.String str) {
        this.mDismissed = false;
        this.mShownByMe = true;
        fragmentTransaction.add(this, str);
        this.mViewDestroyed = false;
        this.mBackStackId = fragmentTransaction.commit();
        return this.mBackStackId;
    }

    public void dismiss() {
        dismissInternal(false);
    }

    public void dismissAllowingStateLoss() {
        dismissInternal(true);
    }

    void dismissInternal(boolean z) {
        if (this.mDismissed) {
            return;
        }
        this.mDismissed = true;
        this.mShownByMe = false;
        if (this.mDialog != null) {
            this.mDialog.dismiss();
            this.mDialog = null;
        }
        this.mViewDestroyed = true;
        if (this.mBackStackId >= 0) {
            getFragmentManager().popBackStack(this.mBackStackId, 1);
            this.mBackStackId = -1;
            return;
        }
        android.app.FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
        beginTransaction.remove(this);
        if (z) {
            beginTransaction.commitAllowingStateLoss();
        } else {
            beginTransaction.commit();
        }
    }

    public android.app.Dialog getDialog() {
        return this.mDialog;
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setCancelable(boolean z) {
        this.mCancelable = z;
        if (this.mDialog != null) {
            this.mDialog.setCancelable(z);
        }
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setShowsDialog(boolean z) {
        this.mShowsDialog = z;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    @Override // android.app.Fragment
    public void onAttach(android.content.Context context) {
        super.onAttach(context);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    @Override // android.app.Fragment
    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
    }

    @Override // android.app.Fragment
    public void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        this.mShowsDialog = this.mContainerId == 0;
        if (bundle != null) {
            this.mStyle = bundle.getInt(SAVED_STYLE, 0);
            this.mTheme = bundle.getInt(SAVED_THEME, 0);
            this.mCancelable = bundle.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = bundle.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = bundle.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    @Override // android.app.Fragment
    public android.view.LayoutInflater onGetLayoutInflater(android.os.Bundle bundle) {
        if (!this.mShowsDialog) {
            return super.onGetLayoutInflater(bundle);
        }
        this.mDialog = onCreateDialog(bundle);
        switch (this.mStyle) {
            case 3:
                this.mDialog.getWindow().addFlags(24);
            case 1:
            case 2:
                this.mDialog.requestWindowFeature(1);
                break;
        }
        if (this.mDialog != null) {
            return (android.view.LayoutInflater) this.mDialog.getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
        }
        return (android.view.LayoutInflater) this.mHost.getContext().getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
    }

    public android.app.Dialog onCreateDialog(android.os.Bundle bundle) {
        return new android.app.Dialog(getActivity(), getTheme());
    }

    @Override // android.content.DialogInterface.OnCancelListener
    public void onCancel(android.content.DialogInterface dialogInterface) {
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(android.content.DialogInterface dialogInterface) {
        if (!this.mViewDestroyed) {
            dismissInternal(true);
        }
    }

    @Override // android.app.Fragment
    public void onActivityCreated(android.os.Bundle bundle) {
        android.os.Bundle bundle2;
        super.onActivityCreated(bundle);
        if (!this.mShowsDialog) {
            return;
        }
        android.view.View view = getView();
        if (view != null) {
            if (view.getParent() != null) {
                throw new java.lang.IllegalStateException("DialogFragment can not be attached to a container view");
            }
            this.mDialog.setContentView(view);
        }
        android.app.Activity activity = getActivity();
        if (activity != null) {
            this.mDialog.setOwnerActivity(activity);
        }
        this.mDialog.setCancelable(this.mCancelable);
        if (!this.mDialog.takeCancelAndDismissListeners("DialogFragment", this, this)) {
            throw new java.lang.IllegalStateException("You can not set Dialog's OnCancelListener or OnDismissListener");
        }
        if (bundle != null && (bundle2 = bundle.getBundle(SAVED_DIALOG_STATE_TAG)) != null) {
            this.mDialog.onRestoreInstanceState(bundle2);
        }
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        if (this.mDialog != null) {
            this.mViewDestroyed = false;
            this.mDialog.show();
        }
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(android.os.Bundle bundle) {
        android.os.Bundle onSaveInstanceState;
        super.onSaveInstanceState(bundle);
        if (this.mDialog != null && (onSaveInstanceState = this.mDialog.onSaveInstanceState()) != null) {
            bundle.putBundle(SAVED_DIALOG_STATE_TAG, onSaveInstanceState);
        }
        if (this.mStyle != 0) {
            bundle.putInt(SAVED_STYLE, this.mStyle);
        }
        if (this.mTheme != 0) {
            bundle.putInt(SAVED_THEME, this.mTheme);
        }
        if (!this.mCancelable) {
            bundle.putBoolean(SAVED_CANCELABLE, this.mCancelable);
        }
        if (!this.mShowsDialog) {
            bundle.putBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
        }
        if (this.mBackStackId != -1) {
            bundle.putInt(SAVED_BACK_STACK_ID, this.mBackStackId);
        }
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        if (this.mDialog != null) {
            this.mDialog.hide();
        }
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.mDialog != null) {
            this.mViewDestroyed = true;
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    @Override // android.app.Fragment
    public void dump(java.lang.String str, java.io.FileDescriptor fileDescriptor, java.io.PrintWriter printWriter, java.lang.String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.println("DialogFragment:");
        printWriter.print(str);
        printWriter.print("  mStyle=");
        printWriter.print(this.mStyle);
        printWriter.print(" mTheme=0x");
        printWriter.println(java.lang.Integer.toHexString(this.mTheme));
        printWriter.print(str);
        printWriter.print("  mCancelable=");
        printWriter.print(this.mCancelable);
        printWriter.print(" mShowsDialog=");
        printWriter.print(this.mShowsDialog);
        printWriter.print(" mBackStackId=");
        printWriter.println(this.mBackStackId);
        printWriter.print(str);
        printWriter.print("  mDialog=");
        printWriter.println(this.mDialog);
        printWriter.print(str);
        printWriter.print("  mViewDestroyed=");
        printWriter.print(this.mViewDestroyed);
        printWriter.print(" mDismissed=");
        printWriter.print(this.mDismissed);
        printWriter.print(" mShownByMe=");
        printWriter.println(this.mShownByMe);
    }
}
