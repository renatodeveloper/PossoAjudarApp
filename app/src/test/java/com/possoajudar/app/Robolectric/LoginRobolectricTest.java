package com.possoajudar.app.Robolectric;

import android.support.v7.widget.AppCompatButton;
import android.widget.Button;
import android.widget.TextView;

import com.possoajudar.app.BuildConfig;
import com.possoajudar.app.R;
import com.possoajudar.app.application.ui.activities.Login;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.Assert.*;
import org.robolectric.annotation.Config;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Renato on 23/07/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class LoginRobolectricTest {
    @Test
    public void shouldNotBeNull(){
        Login activity = Robolectric.setupActivity(Login.class);
        assertNotNull(activity);
    }

    @Test
    public void clickingButton_shouldChangeResultsViewText() throws Exception {
        Login activity = Robolectric.setupActivity(Login.class);

        AppCompatButton button = (AppCompatButton) activity.findViewById(R.id.btn_login);
        TextView results = (TextView) activity.findViewById(R.id.sucessologin);

        button.performClick();
        assertThat(results.getText().toString()).isEqualTo("STATUS");
    }

    /*
    String buildVariant = (BuildConfig.FLAVOR.isEmpty() ? "" : BuildConfig.FLAVOR + "/") + BuildConfig.BUILD_TYPE;
        System.setProperty("android.package", BuildConfig.APPLICATION_ID);
        System.setProperty("android.manifest", "build/intermediates/manifests/full/" + buildVariant + "/AndroidManifest.xml");
        System.setProperty("android.resources", "build/intermediates/res/" + buildVariant);
        System.setProperty("android.assets", "build/intermediates/assets/" + buildVariant);
     */
}
