package nobugs.team.shopping.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import nobugs.team.shopping.R;


public class AutoClearEditView  extends EditText {
    private Drawable clearButton;
    
    public Drawable getClearButton() {
		return clearButton;
	}

	public void setClearButton(Drawable clearButton) {
		this.clearButton = clearButton;
	}

	public AutoClearEditView(Context context) {
        super(context);
    }

    public AutoClearEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        clearButton = getContext().getResources().getDrawable(R.mipmap.shopping_delete_key);
        
        setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				System.out.println("AutoClearEditView onFocusChange-"+hasFocus);
				if(hasFocus){
					EditText me = ((EditText)v);
					if(me!=null && me.getText().toString().length()>0){
						showClearButton(clearButton);
					}
				}else{
					 showClearButton(null);
				}
			}
		});
        addTextChangedListener(new TextWatcher() {
        	
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (s.toString().length() > 0) {
                        showClearButton(clearButton);
                    } else {
                        showClearButton(null);
                    }
                }           	
            }
        });
        
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    private boolean hasIcon = false;
    private boolean mIsClearButton = false;
    private int mMoveCount;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int left = getWidth() - getHeight();
        int top = 0;
        int right = getWidth();
        int bottom = getHeight();
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            float x = event.getX();
            float y = event.getY();
            if (x > left && x < right && y > top && y < bottom && hasIcon ) {
            	
                mIsClearButton = true;
            }
            break;
        case MotionEvent.ACTION_MOVE:
            this.mMoveCount++;
            if(this.mMoveCount > 10) {
                mIsClearButton = false;
            }
            break;
        case MotionEvent.ACTION_UP:
            mMoveCount = 0;
            if(mIsClearButton) {
                onClearButtonClick();
                mIsClearButton = false;
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    private void onClearButtonClick() {
    	
        this.setText("");
        showClearButton(null);
    }
    public void showClearButton(Drawable icon) {
        if (icon != null) {
            icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
            //mIsClearButton=true;
            hasIcon=true;
        }else{
        	mIsClearButton = false;
        	hasIcon = false;
//        	this.clearFocus();
        }
        setCompoundDrawables(getCompoundDrawables()[0], null, icon, null);
    }
}
