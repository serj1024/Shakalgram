package com.ShakalStudio.shakalgram.MVP;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.ShakalStudio.shakalgram.R;

public class AdManager {
    public String AdImageURL;
    public String DownloadLink;
    private String packageNameAdApp;

    public AdManager(Context context) {
        AdImageURL = context.getString(R.string.ad_image_url);
        DownloadLink = context.getString(R.string.ad_download_link);
        packageNameAdApp = context.getString(R.string.package_name_ad_app);
    }

    public void showAdDownloadLink(Context context) {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DownloadLink));
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void showAdApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageNameAdApp);
        try{
            context.startActivity(intent);
        }
        catch(Exception e){
            //можно вынести в событие и подписаться на него в презентере и дергать методы вьюхи
            Toast.makeText(context, "No application can handle this request."
                    + " Please install a " + getAppName(packageNameAdApp),  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String getAppName(String packageName) {
        String[] splitPackageName = packageName.split("\\.");
        String nameApp = splitPackageName[splitPackageName.length-1];
        String capitalizedName = nameApp.substring(0, 1).toUpperCase() + nameApp.substring(1).toLowerCase();
        return capitalizedName;
    }
}