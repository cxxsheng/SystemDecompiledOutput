package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SeekBarDialogPreference extends android.preference.DialogPreference {
    private final android.graphics.drawable.Drawable mMyIcon;

    public SeekBarDialogPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        createActionButtons();
        this.mMyIcon = getDialogIcon();
        setDialogIcon((android.graphics.drawable.Drawable) null);
    }

    public SeekBarDialogPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarDialogPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.seekBarDialogPreferenceStyle);
    }

    public SeekBarDialogPreference(android.content.Context context) {
        this(context, null);
    }

    public void createActionButtons() {
        setPositiveButtonText(17039370);
        setNegativeButtonText(17039360);
    }

    @Override // android.preference.DialogPreference
    protected void onBindDialogView(android.view.View view) {
        super.onBindDialogView(view);
        android.widget.ImageView imageView = (android.widget.ImageView) view.findViewById(16908294);
        if (this.mMyIcon != null) {
            imageView.setImageDrawable(this.mMyIcon);
        } else {
            imageView.setVisibility(8);
        }
    }

    protected static android.widget.SeekBar getSeekBar(android.view.View view) {
        return (android.widget.SeekBar) view.findViewById(com.android.internal.R.id.seekbar);
    }
}
