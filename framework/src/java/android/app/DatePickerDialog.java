package android.app;

/* loaded from: classes.dex */
public class DatePickerDialog extends android.app.AlertDialog implements android.content.DialogInterface.OnClickListener, android.widget.DatePicker.OnDateChangedListener {
    private static final java.lang.String DAY = "day";
    private static final java.lang.String MONTH = "month";
    private static final java.lang.String YEAR = "year";
    private final android.widget.DatePicker mDatePicker;
    private android.app.DatePickerDialog.OnDateSetListener mDateSetListener;
    private final android.widget.DatePicker.ValidationCallback mValidationCallback;

    public interface OnDateSetListener {
        void onDateSet(android.widget.DatePicker datePicker, int i, int i2, int i3);
    }

    public DatePickerDialog(android.content.Context context) {
        this(context, 0, null, java.util.Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(android.content.Context context, int i) {
        this(context, i, null, java.util.Calendar.getInstance(), -1, -1, -1);
    }

    public DatePickerDialog(android.content.Context context, android.app.DatePickerDialog.OnDateSetListener onDateSetListener, int i, int i2, int i3) {
        this(context, 0, onDateSetListener, null, i, i2, i3);
    }

    public DatePickerDialog(android.content.Context context, int i, android.app.DatePickerDialog.OnDateSetListener onDateSetListener, int i2, int i3, int i4) {
        this(context, i, onDateSetListener, null, i2, i3, i4);
    }

    private DatePickerDialog(android.content.Context context, int i, android.app.DatePickerDialog.OnDateSetListener onDateSetListener, java.util.Calendar calendar, int i2, int i3, int i4) {
        super(context, resolveDialogTheme(context, i));
        this.mValidationCallback = new android.widget.DatePicker.ValidationCallback() { // from class: android.app.DatePickerDialog.1
            @Override // android.widget.DatePicker.ValidationCallback
            public void onValidationChanged(boolean z) {
                android.widget.Button button = android.app.DatePickerDialog.this.getButton(-1);
                if (button != null) {
                    button.setEnabled(z);
                }
            }
        };
        android.content.Context context2 = getContext();
        android.view.View inflate = android.view.LayoutInflater.from(context2).inflate(com.android.internal.R.layout.date_picker_dialog, (android.view.ViewGroup) null);
        setView(inflate);
        setButton(-1, context2.getString(17039370), this);
        setButton(-2, context2.getString(17039360), this);
        setButtonPanelLayoutHint(1);
        if (calendar != null) {
            i2 = calendar.get(1);
            i3 = calendar.get(2);
            i4 = calendar.get(5);
        }
        this.mDatePicker = (android.widget.DatePicker) inflate.findViewById(com.android.internal.R.id.datePicker);
        this.mDatePicker.init(i2, i3, i4, this);
        this.mDatePicker.setValidationCallback(this.mValidationCallback);
        this.mDateSetListener = onDateSetListener;
    }

    static int resolveDialogTheme(android.content.Context context, int i) {
        if (i == 0) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            context.getTheme().resolveAttribute(16843948, typedValue, true);
            return typedValue.resourceId;
        }
        return i;
    }

    @Override // android.widget.DatePicker.OnDateChangedListener
    public void onDateChanged(android.widget.DatePicker datePicker, int i, int i2, int i3) {
        this.mDatePicker.init(i, i2, i3, this);
    }

    public void setOnDateSetListener(android.app.DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.mDateSetListener = onDateSetListener;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        switch (i) {
            case -2:
                cancel();
                break;
            case -1:
                if (this.mDateSetListener != null) {
                    this.mDatePicker.clearFocus();
                    this.mDateSetListener.onDateSet(this.mDatePicker, this.mDatePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
                    break;
                }
                break;
        }
    }

    public android.widget.DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public void updateDate(int i, int i2, int i3) {
        this.mDatePicker.updateDate(i, i2, i3);
    }

    @Override // android.app.Dialog
    public android.os.Bundle onSaveInstanceState() {
        android.os.Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(YEAR, this.mDatePicker.getYear());
        onSaveInstanceState.putInt(MONTH, this.mDatePicker.getMonth());
        onSaveInstanceState.putInt(DAY, this.mDatePicker.getDayOfMonth());
        return onSaveInstanceState;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(android.os.Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        this.mDatePicker.init(bundle.getInt(YEAR), bundle.getInt(MONTH), bundle.getInt(DAY), this);
    }
}
