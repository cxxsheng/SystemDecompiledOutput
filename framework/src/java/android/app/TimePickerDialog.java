package android.app;

/* loaded from: classes.dex */
public class TimePickerDialog extends android.app.AlertDialog implements android.content.DialogInterface.OnClickListener, android.widget.TimePicker.OnTimeChangedListener {
    private static final java.lang.String HOUR = "hour";
    private static final java.lang.String IS_24_HOUR = "is24hour";
    private static final java.lang.String MINUTE = "minute";
    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final boolean mIs24HourView;
    private final android.widget.TimePicker mTimePicker;
    private final android.app.TimePickerDialog.OnTimeSetListener mTimeSetListener;

    public interface OnTimeSetListener {
        void onTimeSet(android.widget.TimePicker timePicker, int i, int i2);
    }

    public TimePickerDialog(android.content.Context context, android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener, int i, int i2, boolean z) {
        this(context, 0, onTimeSetListener, i, i2, z);
    }

    static int resolveDialogTheme(android.content.Context context, int i) {
        if (i == 0) {
            android.util.TypedValue typedValue = new android.util.TypedValue();
            context.getTheme().resolveAttribute(16843934, typedValue, true);
            return typedValue.resourceId;
        }
        return i;
    }

    public TimePickerDialog(android.content.Context context, int i, android.app.TimePickerDialog.OnTimeSetListener onTimeSetListener, int i2, int i3, boolean z) {
        super(context, resolveDialogTheme(context, i));
        this.mTimeSetListener = onTimeSetListener;
        this.mInitialHourOfDay = i2;
        this.mInitialMinute = i3;
        this.mIs24HourView = z;
        android.content.Context context2 = getContext();
        android.view.View inflate = android.view.LayoutInflater.from(context2).inflate(com.android.internal.R.layout.time_picker_dialog, (android.view.ViewGroup) null);
        setView(inflate);
        setButton(-1, context2.getString(17039370), this);
        setButton(-2, context2.getString(17039360), this);
        setButtonPanelLayoutHint(1);
        this.mTimePicker = (android.widget.TimePicker) inflate.findViewById(com.android.internal.R.id.timePicker);
        this.mTimePicker.setIs24HourView(java.lang.Boolean.valueOf(this.mIs24HourView));
        this.mTimePicker.setCurrentHour(java.lang.Integer.valueOf(this.mInitialHourOfDay));
        this.mTimePicker.setCurrentMinute(java.lang.Integer.valueOf(this.mInitialMinute));
        this.mTimePicker.setOnTimeChangedListener(this);
    }

    public android.widget.TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    @Override // android.widget.TimePicker.OnTimeChangedListener
    public void onTimeChanged(android.widget.TimePicker timePicker, int i, int i2) {
    }

    @Override // android.app.Dialog
    public void show() {
        super.show();
        getButton(-1).setOnClickListener(new android.view.View.OnClickListener() { // from class: android.app.TimePickerDialog.1
            @Override // android.view.View.OnClickListener
            public void onClick(android.view.View view) {
                if (android.app.TimePickerDialog.this.mTimePicker.validateInput()) {
                    android.app.TimePickerDialog.this.onClick(android.app.TimePickerDialog.this, -1);
                    android.app.TimePickerDialog.this.mTimePicker.clearFocus();
                    android.app.TimePickerDialog.this.dismiss();
                }
            }
        });
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(android.content.DialogInterface dialogInterface, int i) {
        switch (i) {
            case -2:
                cancel();
                break;
            case -1:
                if (this.mTimeSetListener != null) {
                    this.mTimeSetListener.onTimeSet(this.mTimePicker, this.mTimePicker.getCurrentHour().intValue(), this.mTimePicker.getCurrentMinute().intValue());
                    break;
                }
                break;
        }
    }

    public void updateTime(int i, int i2) {
        this.mTimePicker.setCurrentHour(java.lang.Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(java.lang.Integer.valueOf(i2));
    }

    @Override // android.app.Dialog
    public android.os.Bundle onSaveInstanceState() {
        android.os.Bundle onSaveInstanceState = super.onSaveInstanceState();
        onSaveInstanceState.putInt(HOUR, this.mTimePicker.getCurrentHour().intValue());
        onSaveInstanceState.putInt(MINUTE, this.mTimePicker.getCurrentMinute().intValue());
        onSaveInstanceState.putBoolean(IS_24_HOUR, this.mTimePicker.is24HourView());
        return onSaveInstanceState;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(android.os.Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        int i = bundle.getInt(HOUR);
        int i2 = bundle.getInt(MINUTE);
        this.mTimePicker.setIs24HourView(java.lang.Boolean.valueOf(bundle.getBoolean(IS_24_HOUR)));
        this.mTimePicker.setCurrentHour(java.lang.Integer.valueOf(i));
        this.mTimePicker.setCurrentMinute(java.lang.Integer.valueOf(i2));
    }
}
