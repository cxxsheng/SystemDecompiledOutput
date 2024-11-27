package android.preference;

@java.lang.Deprecated
/* loaded from: classes3.dex */
public class SeekBarPreference extends android.preference.Preference implements android.widget.SeekBar.OnSeekBarChangeListener {
    private int mMax;
    private int mProgress;
    private boolean mTrackingTouch;

    public SeekBarPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        android.content.res.TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.ProgressBar, i, i2);
        setMax(obtainStyledAttributes.getInt(2, this.mMax));
        obtainStyledAttributes.recycle();
        android.content.res.TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.SeekBarPreference, i, i2);
        int resourceId = obtainStyledAttributes2.getResourceId(0, com.android.internal.R.layout.preference_widget_seekbar);
        obtainStyledAttributes2.recycle();
        setLayoutResource(resourceId);
    }

    public SeekBarPreference(android.content.Context context, android.util.AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeekBarPreference(android.content.Context context, android.util.AttributeSet attributeSet) {
        this(context, attributeSet, com.android.internal.R.attr.seekBarPreferenceStyle);
    }

    public SeekBarPreference(android.content.Context context) {
        this(context, null);
    }

    @Override // android.preference.Preference
    protected void onBindView(android.view.View view) {
        super.onBindView(view);
        android.widget.SeekBar seekBar = (android.widget.SeekBar) view.findViewById(com.android.internal.R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(this.mMax);
        seekBar.setProgress(this.mProgress);
        seekBar.setEnabled(isEnabled());
    }

    @Override // android.preference.Preference
    protected void onSetInitialValue(boolean z, java.lang.Object obj) {
        setProgress(z ? getPersistedInt(this.mProgress) : ((java.lang.Integer) obj).intValue());
    }

    @Override // android.preference.Preference
    protected java.lang.Object onGetDefaultValue(android.content.res.TypedArray typedArray, int i) {
        return java.lang.Integer.valueOf(typedArray.getInt(i, 0));
    }

    @Override // android.preference.Preference
    public boolean onKey(android.view.View view, int i, android.view.KeyEvent keyEvent) {
        android.widget.SeekBar seekBar;
        if (keyEvent.getAction() == 0 && (seekBar = (android.widget.SeekBar) view.findViewById(com.android.internal.R.id.seekbar)) != null) {
            return seekBar.onKeyDown(i, keyEvent);
        }
        return false;
    }

    public void setMax(int i) {
        if (i != this.mMax) {
            this.mMax = i;
            notifyChanged();
        }
    }

    public void setProgress(int i) {
        setProgress(i, true);
    }

    private void setProgress(int i, boolean z) {
        if (i > this.mMax) {
            i = this.mMax;
        }
        if (i < 0) {
            i = 0;
        }
        if (i != this.mProgress) {
            this.mProgress = i;
            persistInt(i);
            if (z) {
                notifyChanged();
            }
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    void syncProgress(android.widget.SeekBar seekBar) {
        int progress = seekBar.getProgress();
        if (progress != this.mProgress) {
            if (callChangeListener(java.lang.Integer.valueOf(progress))) {
                setProgress(progress, false);
            } else {
                seekBar.setProgress(this.mProgress);
            }
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onProgressChanged(android.widget.SeekBar seekBar, int i, boolean z) {
        if (z && !this.mTrackingTouch) {
            syncProgress(seekBar);
        }
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
        this.mTrackingTouch = true;
    }

    @Override // android.widget.SeekBar.OnSeekBarChangeListener
    public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
        this.mTrackingTouch = false;
        if (seekBar.getProgress() != this.mProgress) {
            syncProgress(seekBar);
        }
    }

    @Override // android.preference.Preference
    protected android.os.Parcelable onSaveInstanceState() {
        android.os.Parcelable onSaveInstanceState = super.onSaveInstanceState();
        if (isPersistent()) {
            return onSaveInstanceState;
        }
        android.preference.SeekBarPreference.SavedState savedState = new android.preference.SeekBarPreference.SavedState(onSaveInstanceState);
        savedState.progress = this.mProgress;
        savedState.max = this.mMax;
        return savedState;
    }

    @Override // android.preference.Preference
    protected void onRestoreInstanceState(android.os.Parcelable parcelable) {
        if (!parcelable.getClass().equals(android.preference.SeekBarPreference.SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        android.preference.SeekBarPreference.SavedState savedState = (android.preference.SeekBarPreference.SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mProgress = savedState.progress;
        this.mMax = savedState.max;
        notifyChanged();
    }

    private static class SavedState extends android.preference.Preference.BaseSavedState {
        public static final android.os.Parcelable.Creator<android.preference.SeekBarPreference.SavedState> CREATOR = new android.os.Parcelable.Creator<android.preference.SeekBarPreference.SavedState>() { // from class: android.preference.SeekBarPreference.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.SeekBarPreference.SavedState createFromParcel(android.os.Parcel parcel) {
                return new android.preference.SeekBarPreference.SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public android.preference.SeekBarPreference.SavedState[] newArray(int i) {
                return new android.preference.SeekBarPreference.SavedState[i];
            }
        };
        int max;
        int progress;

        public SavedState(android.os.Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.max = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(android.os.Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
        }

        public SavedState(android.os.Parcelable parcelable) {
            super(parcelable);
        }
    }
}
