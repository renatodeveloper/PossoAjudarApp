package com.possoajudar.app.infrastructure.helper;

import android.content.Context;
import android.content.Intent;

import com.possoajudar.app.application.ui.activities.MainActivity;

/**
 * Created by renato on 06/07/2017.
 */

public class ActivityUtil {
    private Context context;

    public ActivityUtil() {
    }

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startMainActivity(){
        this.context.startActivity(new Intent(context, MainActivity.class));
    }
}
