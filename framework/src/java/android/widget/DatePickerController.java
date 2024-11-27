package android.widget;

/* loaded from: classes4.dex */
interface DatePickerController {
    java.util.Calendar getSelectedDay();

    void onYearSelected(int i);

    void registerOnDateChangedListener(android.widget.OnDateChangedListener onDateChangedListener);

    void tryVibrate();
}
