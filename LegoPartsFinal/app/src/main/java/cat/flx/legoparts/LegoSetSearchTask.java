package cat.flx.legoparts;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import cat.flx.legoparts.model.LegoSet;
import cat.flx.legoparts.model.LegoSetList;

public class LegoSetSearchTask extends AsyncTask<String,Integer,LegoSetList> {

    interface WeakReference {
        Context getContext();
        ProgressBar getProgressBar();
        void finished(LegoSetList list);
    }
    private WeakReference ref;
    public LegoSetSearchTask(WeakReference ref) {
        super();
        this.ref = ref;
    }

    @Override
    public void onPreExecute() {
        // Log.d("flx", "onPreExecute()");
        ProgressBar pb = ref.getProgressBar();
        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);
    }

    @Override
    protected LegoSetList doInBackground(String... strings) {
        // Log.d("flx", "doInBackground()");

        InputStream in = null;
        try {
            // BUILD URL
            String search = strings[0];
            search = URLEncoder.encode(search, "utf-8");
            String apiKey = ref.getContext().getString(R.string.apiKey);
            String urlStr = ref.getContext().getString(R.string.searchUrl, apiKey, search);
            Log.d("flx", "URL " + urlStr);

            // OPEN CONNECTION
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(1000);
            int length = conn.getContentLength();
            // Log.d("flx", "ContentLength = " + length);

            // DOWNLOAD JSON
            in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int total = 0, nRead;
            while ((nRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, nRead);
                total += nRead;
            }
            String json = new String(out.toByteArray());
            Log.d("flx", "JSON = " + json);

            // DECODE JSON
            Gson gson = new Gson();
            LegoSetList list = gson.fromJson(json, LegoSetList.class);
            Log.d("flx",  list.getCount() + " SETS");
            // LegoSet[] sets = list.getResults();
            // for(LegoSet set: sets) {
            //     Log.d("flx", set.getSetNum() + " : " + set.getName());
            // }
            return list;
        }
        catch (Exception e) {
            Log.e("flx", e.getMessage());
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }

    @Override
    public void onPostExecute(LegoSetList result) {
        // Log.d("flx", "onPostExecute()");
        ProgressBar pb = ref.getProgressBar();
        pb.setVisibility(View.INVISIBLE);
        ref.finished(result);
    }

}
