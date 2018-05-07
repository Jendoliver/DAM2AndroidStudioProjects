package com.apporelbotna.lego;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.apporelbotna.lego.model.LegoSetList;
import com.apporelbotna.lego.model.LegoSet;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jandol on 11/22/2017.
 */

public class LegoSetSearchTask extends AsyncTask<String, Integer, LegoSetList>
{
    interface WeakReference {
        Context getContext();
        ProgressBar getProgressBar();
        void finished(LegoSetList legoSetList);
    }
    private WeakReference ref;

    public LegoSetSearchTask(WeakReference ref) {
        super();
        this.ref = ref;
    }

    @Override
    public void onPreExecute() {
        Log.d("Jendoliver", "onPreExecute()");
        ProgressBar pb = ref.getProgressBar();
        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected LegoSetList doInBackground(String... strings) {
        Log.d("Jendoliver", "doInBackground()");
        InputStream in = null;
        // BUILD URL
        try {
            String search = strings[0];
            search = URLEncoder.encode(search, "utf-8");
            String apiKey = ref.getContext().getString(R.string.apiKey);
            String urlStr = ref.getContext().getString(R.string.searchUrl, apiKey, search);
            Log.d("Jendoliver", "URL " + urlStr);

            // OPEN CONNECTION
            URL url = new URL(urlStr);
            URLConnection connection = url.openConnection();
            int length = connection.getContentLength();
            Log.d("Jendoliver", "ContentLength = " + length);

            // DOWNLOAD JSON
            in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int total = 0, nRead;
            while((nRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, nRead);
                total += nRead;
            }
            String json = new String(out.toByteArray());
            Log.d("Jendoliver", "JSON = " + json);

            // DECODE JSON
            Gson gson = new Gson();
            LegoSetList list = gson.fromJson(json, LegoSetList.class);
            Log.d("Jendoliver", list.getCount() + " SETS");
            LegoSet[] legoSets = list.getResults();
            for(LegoSet legoSet : legoSets) {
                Log.d("Jendoliver", "NAME: " + legoSet.name + "\n" +
                        "NUM PARTS: " + legoSet.numParts + "\n" +
                        "IMG URL: " + legoSet.imgUrl + "\n" +
                        "ID: " + legoSet.id + "\n" +
                        "YEAR: " + legoSet.year + "\n");
            }
            return list;

        } catch (IOException e) {
            Log.e("JendolivError", e.getMessage());
            return null;
        } finally {
            try { if (in != null) in.close(); } catch (IOException e) { }
        }
    }

    @Override
    public void onPostExecute(LegoSetList result) {
        Log.d("Jendoliver", "onPostExecute()");
        ProgressBar pb = ref.getProgressBar();
        pb.setVisibility(View.INVISIBLE);
        ref.finished(result);
    }
}