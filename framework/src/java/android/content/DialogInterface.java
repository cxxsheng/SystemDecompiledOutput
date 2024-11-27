package android.content;

/* loaded from: classes.dex */
public interface DialogInterface {

    @java.lang.Deprecated
    public static final int BUTTON1 = -1;

    @java.lang.Deprecated
    public static final int BUTTON2 = -2;

    @java.lang.Deprecated
    public static final int BUTTON3 = -3;
    public static final int BUTTON_NEGATIVE = -2;
    public static final int BUTTON_NEUTRAL = -3;
    public static final int BUTTON_POSITIVE = -1;

    public interface OnCancelListener {
        void onCancel(android.content.DialogInterface dialogInterface);
    }

    public interface OnClickListener {
        void onClick(android.content.DialogInterface dialogInterface, int i);
    }

    public interface OnDismissListener {
        void onDismiss(android.content.DialogInterface dialogInterface);
    }

    public interface OnKeyListener {
        boolean onKey(android.content.DialogInterface dialogInterface, int i, android.view.KeyEvent keyEvent);
    }

    public interface OnMultiChoiceClickListener {
        void onClick(android.content.DialogInterface dialogInterface, int i, boolean z);
    }

    public interface OnShowListener {
        void onShow(android.content.DialogInterface dialogInterface);
    }

    void cancel();

    void dismiss();
}
