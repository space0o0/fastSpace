package space.learning.baselibrary.photoChooser.ui;

import android.app.AlertDialog;
import android.content.Context;

public class SimpleChooseView extends AlertDialog.Builder implements IChooseView {

    public SimpleChooseView(Context context) {
        super(context);
    }

    public SimpleChooseView(Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init(){

    }


    @Override
    public void clickTakePhoto() {

    }

    @Override
    public void clickGallery() {

    }

}
