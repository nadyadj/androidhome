package com.example.nadya.homelightcontroller;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {
    public static String FACEBOOK_URL = "https://www.facebook.com/nadya.regina.92";
    public static String FACEBOOK_PAGE_ID = "nadya.regina.92";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button ig, fb;

        ig = findViewById(R.id.btn_instagram);
        fb = findViewById(R.id.btn_facebook);

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://instagram.com/_u/reginanadya");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/reginanadya")));
                }
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent facebookIntent = getOpenFacebookIntent(AboutActivity.this);
//                startActivity(facebookIntent);
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(AboutActivity.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });
    }

    public String getFacebookPageURL(Context context) { //This method will return the correct url for app if installed or web url if app is not installed.
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL; //normal web url
        }
    }

//    public static Intent getOpenFacebookIntent(Context context) {
//
//        try {
//            context.getPackageManager()
//                    .getPackageInfo("com.facebook.katana", 0); //Checks if FB is even installed.
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("fb://profile/254175194653125")); //Trys to make intent with FB's URI
//        } catch (Exception e) {
//            return new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("https://www.facebook.com/nadya.regina.92")); //catches and opens a url to the desired page
//        }
//    }

}
