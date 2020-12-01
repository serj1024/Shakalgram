package com.ShakalStudio.shakalgram;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AdManager {
    public String AdImageURL = "https://i.ytimg.com/vi/8B8DV_k5IR0/maxresdefault.jpg";
    public String DownloadLink = "https://github.com/serj1024/Shakalgram";
    private String _packageNameAdApp = "com.igd.appcats";

    public void showAdDownloadLink(Context context) {
        try {
            new ActivityNotFoundException();
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(DownloadLink));
            context.startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    public void showAdApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(_packageNameAdApp);
        try{
            context.startActivity(intent);
        }
        catch(Exception e){
            Toast.makeText(context, "No application can handle this request."
                    + " Please install a " + getName(_packageNameAdApp),  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private String getName(String packageName) {
        String[] splitPackageName = packageName.split("\\.");
        String nameApp = splitPackageName[splitPackageName.length-1];
        String capitalizedName = nameApp.substring(0, 1).toUpperCase() + nameApp.substring(1).toLowerCase();
        return capitalizedName;
    }
}