package android.text.method;

/* loaded from: classes3.dex */
public class CharacterPickerDialog extends android.app.Dialog implements android.widget.AdapterView.OnItemClickListener, android.view.View.OnClickListener {
    private android.widget.Button mCancelButton;
    private android.view.LayoutInflater mInflater;
    private boolean mInsert;
    private java.lang.String mOptions;
    private android.text.Editable mText;
    private android.view.View mView;

    public CharacterPickerDialog(android.content.Context context, android.view.View view, android.text.Editable editable, java.lang.String str, boolean z) {
        super(context, 16973913);
        this.mView = view;
        this.mText = editable;
        this.mOptions = str;
        this.mInsert = z;
        this.mInflater = android.view.LayoutInflater.from(context);
    }

    @Override // android.app.Dialog
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        android.view.WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.token = this.mView.getApplicationWindowToken();
        attributes.type = 1003;
        attributes.flags |= 1;
        setContentView(com.android.internal.R.layout.character_picker);
        android.widget.GridView gridView = (android.widget.GridView) findViewById(com.android.internal.R.id.characterPicker);
        gridView.setAdapter((android.widget.ListAdapter) new android.text.method.CharacterPickerDialog.OptionsAdapter(getContext()));
        gridView.setOnItemClickListener(this);
        this.mCancelButton = (android.widget.Button) findViewById(com.android.internal.R.id.cancel);
        this.mCancelButton.setOnClickListener(this);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(android.widget.AdapterView adapterView, android.view.View view, int i, long j) {
        replaceCharacterAndClose(java.lang.String.valueOf(this.mOptions.charAt(i)));
    }

    private void replaceCharacterAndClose(java.lang.CharSequence charSequence) {
        int selectionEnd = android.text.Selection.getSelectionEnd(this.mText);
        if (this.mInsert || selectionEnd == 0) {
            this.mText.insert(selectionEnd, charSequence);
        } else {
            this.mText.replace(selectionEnd - 1, selectionEnd, charSequence);
        }
        dismiss();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(android.view.View view) {
        if (view == this.mCancelButton) {
            dismiss();
        } else if (view instanceof android.widget.Button) {
            replaceCharacterAndClose(((android.widget.Button) view).getText());
        }
    }

    private class OptionsAdapter extends android.widget.BaseAdapter {
        public OptionsAdapter(android.content.Context context) {
        }

        @Override // android.widget.Adapter
        public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
            android.widget.Button button = (android.widget.Button) android.text.method.CharacterPickerDialog.this.mInflater.inflate(com.android.internal.R.layout.character_picker_button, (android.view.ViewGroup) null);
            button.lambda$setTextAsync$0(java.lang.String.valueOf(android.text.method.CharacterPickerDialog.this.mOptions.charAt(i)));
            button.setOnClickListener(android.text.method.CharacterPickerDialog.this);
            return button;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return android.text.method.CharacterPickerDialog.this.mOptions.length();
        }

        @Override // android.widget.Adapter
        public final java.lang.Object getItem(int i) {
            return java.lang.String.valueOf(android.text.method.CharacterPickerDialog.this.mOptions.charAt(i));
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }
    }
}
