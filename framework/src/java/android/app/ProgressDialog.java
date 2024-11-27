package android.app;

@java.lang.Deprecated
/* loaded from: classes.dex */
public class ProgressDialog extends android.app.AlertDialog {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    private boolean mHasStarted;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private boolean mIndeterminate;
    private android.graphics.drawable.Drawable mIndeterminateDrawable;
    private int mMax;
    private java.lang.CharSequence mMessage;
    private android.widget.TextView mMessageView;
    private android.widget.ProgressBar mProgress;
    private android.graphics.drawable.Drawable mProgressDrawable;
    private android.widget.TextView mProgressNumber;
    private java.lang.String mProgressNumberFormat;
    private android.widget.TextView mProgressPercent;
    private java.text.NumberFormat mProgressPercentFormat;
    private int mProgressStyle;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private android.os.Handler mViewUpdateHandler;

    public ProgressDialog(android.content.Context context) {
        super(context);
        this.mProgressStyle = 0;
        initFormats();
    }

    public ProgressDialog(android.content.Context context, int i) {
        super(context, i);
        this.mProgressStyle = 0;
        initFormats();
    }

    private void initFormats() {
        this.mProgressNumberFormat = "%1d/%2d";
        this.mProgressPercentFormat = java.text.NumberFormat.getPercentInstance();
        this.mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    public static android.app.ProgressDialog show(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2) {
        return show(context, charSequence, charSequence2, false);
    }

    public static android.app.ProgressDialog show(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z) {
        return show(context, charSequence, charSequence2, z, false, null);
    }

    public static android.app.ProgressDialog show(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z, boolean z2) {
        return show(context, charSequence, charSequence2, z, z2, null);
    }

    public static android.app.ProgressDialog show(android.content.Context context, java.lang.CharSequence charSequence, java.lang.CharSequence charSequence2, boolean z, boolean z2, android.content.DialogInterface.OnCancelListener onCancelListener) {
        android.app.ProgressDialog progressDialog = new android.app.ProgressDialog(context);
        progressDialog.setTitle(charSequence);
        progressDialog.setMessage(charSequence2);
        progressDialog.setIndeterminate(z);
        progressDialog.setCancelable(z2);
        progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.show();
        return progressDialog;
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        android.view.LayoutInflater from = android.view.LayoutInflater.from(this.mContext);
        android.content.res.TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, com.android.internal.R.styleable.AlertDialog, 16842845, 0);
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new android.os.Handler() { // from class: android.app.ProgressDialog.1
                @Override // android.os.Handler
                public void handleMessage(android.os.Message message) {
                    super.handleMessage(message);
                    int progress = android.app.ProgressDialog.this.mProgress.getProgress();
                    int max = android.app.ProgressDialog.this.mProgress.getMax();
                    if (android.app.ProgressDialog.this.mProgressNumberFormat != null) {
                        android.app.ProgressDialog.this.mProgressNumber.lambda$setTextAsync$0(java.lang.String.format(android.app.ProgressDialog.this.mProgressNumberFormat, java.lang.Integer.valueOf(progress), java.lang.Integer.valueOf(max)));
                    } else {
                        android.app.ProgressDialog.this.mProgressNumber.lambda$setTextAsync$0("");
                    }
                    if (android.app.ProgressDialog.this.mProgressPercentFormat != null) {
                        android.text.SpannableString spannableString = new android.text.SpannableString(android.app.ProgressDialog.this.mProgressPercentFormat.format(progress / max));
                        spannableString.setSpan(new android.text.style.StyleSpan(1), 0, spannableString.length(), 33);
                        android.app.ProgressDialog.this.mProgressPercent.lambda$setTextAsync$0(spannableString);
                        return;
                    }
                    android.app.ProgressDialog.this.mProgressPercent.lambda$setTextAsync$0("");
                }
            };
            android.view.View inflate = from.inflate(obtainStyledAttributes.getResourceId(13, com.android.internal.R.layout.alert_dialog_progress), (android.view.ViewGroup) null);
            this.mProgress = (android.widget.ProgressBar) inflate.findViewById(16908301);
            this.mProgressNumber = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.progress_number);
            this.mProgressPercent = (android.widget.TextView) inflate.findViewById(com.android.internal.R.id.progress_percent);
            setView(inflate);
        } else {
            android.view.View inflate2 = from.inflate(obtainStyledAttributes.getResourceId(18, com.android.internal.R.layout.progress_dialog), (android.view.ViewGroup) null);
            this.mProgress = (android.widget.ProgressBar) inflate2.findViewById(16908301);
            this.mMessageView = (android.widget.TextView) inflate2.findViewById(16908299);
            setView(inflate2);
        }
        obtainStyledAttributes.recycle();
        if (this.mMax > 0) {
            setMax(this.mMax);
        }
        if (this.mProgressVal > 0) {
            setProgress(this.mProgressVal);
        }
        if (this.mSecondaryProgressVal > 0) {
            setSecondaryProgress(this.mSecondaryProgressVal);
        }
        if (this.mIncrementBy > 0) {
            incrementProgressBy(this.mIncrementBy);
        }
        if (this.mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(this.mIncrementSecondaryBy);
        }
        if (this.mProgressDrawable != null) {
            setProgressDrawable(this.mProgressDrawable);
        }
        if (this.mIndeterminateDrawable != null) {
            setIndeterminateDrawable(this.mIndeterminateDrawable);
        }
        if (this.mMessage != null) {
            setMessage(this.mMessage);
        }
        setIndeterminate(this.mIndeterminate);
        onProgressChanged();
        super.onCreate(bundle);
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        this.mHasStarted = true;
    }

    @Override // android.app.Dialog
    protected void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setProgress(int i) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(i);
            onProgressChanged();
        } else {
            this.mProgressVal = i;
        }
    }

    public void setSecondaryProgress(int i) {
        if (this.mProgress != null) {
            this.mProgress.setSecondaryProgress(i);
            onProgressChanged();
        } else {
            this.mSecondaryProgressVal = i;
        }
    }

    public int getProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public int getMax() {
        if (this.mProgress != null) {
            return this.mProgress.getMax();
        }
        return this.mMax;
    }

    public void setMax(int i) {
        if (this.mProgress != null) {
            this.mProgress.setMax(i);
            onProgressChanged();
        } else {
            this.mMax = i;
        }
    }

    public void incrementProgressBy(int i) {
        if (this.mProgress != null) {
            this.mProgress.incrementProgressBy(i);
            onProgressChanged();
        } else {
            this.mIncrementBy += i;
        }
    }

    public void incrementSecondaryProgressBy(int i) {
        if (this.mProgress != null) {
            this.mProgress.incrementSecondaryProgressBy(i);
            onProgressChanged();
        } else {
            this.mIncrementSecondaryBy += i;
        }
    }

    public void setProgressDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mProgress != null) {
            this.mProgress.setProgressDrawable(drawable);
        } else {
            this.mProgressDrawable = drawable;
        }
    }

    public void setIndeterminateDrawable(android.graphics.drawable.Drawable drawable) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminateDrawable(drawable);
        } else {
            this.mIndeterminateDrawable = drawable;
        }
    }

    public void setIndeterminate(boolean z) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminate(z);
        } else {
            this.mIndeterminate = z;
        }
    }

    public boolean isIndeterminate() {
        if (this.mProgress != null) {
            return this.mProgress.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    @Override // android.app.AlertDialog
    public void setMessage(java.lang.CharSequence charSequence) {
        if (this.mProgress != null) {
            if (this.mProgressStyle == 1) {
                super.setMessage(charSequence);
                return;
            } else {
                this.mMessageView.lambda$setTextAsync$0(charSequence);
                return;
            }
        }
        this.mMessage = charSequence;
    }

    public void setProgressStyle(int i) {
        this.mProgressStyle = i;
    }

    public void setProgressNumberFormat(java.lang.String str) {
        this.mProgressNumberFormat = str;
        onProgressChanged();
    }

    public void setProgressPercentFormat(java.text.NumberFormat numberFormat) {
        this.mProgressPercentFormat = numberFormat;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (this.mProgressStyle == 1 && this.mViewUpdateHandler != null && !this.mViewUpdateHandler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }
}
