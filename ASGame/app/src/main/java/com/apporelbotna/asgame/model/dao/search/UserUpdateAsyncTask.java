package com.apporelbotna.asgame.model.dao.search;

import android.util.Pair;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.dao.ConvertableTO;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Jandol on 02/14/2018.
 */

public class UserUpdateAsyncTask extends AbstractAsyncTask<Pair<String, String>, Void, UserUpdateAsyncTask.UserUpdateResponse>
{
    public UserUpdateAsyncTask(WeakReference ref)
    {
        super(ref);
    }

    public class UserUpdateResponse implements ConvertableTO
    {

        @Override
        public Object toObject() {
            return this;
        }
    }

    @Override
    protected UserUpdateResponse doInBackground(Pair<String, String>... pairs)
    {
        // TODO finish me
        /*
        URL url = null;
        try {
            url = new URL(ref.getContext().getString(R.string.stucomGamesWSGetConcreteUser, Integer.parseInt(pairs[0].second)));
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            List<Pair<String, String>> params = new ArrayList<>();
            params.add(new Pair<>("key", ref.getContext().getString(R.string.stucomGamesApiKey)));


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
        }
        catch (Exception ignored) {  }

        return null

        // OLD
        InputStream in = null;
        try {
            String urlStr = ref.getContext().getString(R.string.stucomGamesWSGetConcreteUser, ints[0]);

            // OPEN CONNECTION
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(2000);
            conn.setReadTimeout(1000);

            // DOWNLOAD JSON
            in = url.openStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int nRead;
            while ((nRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, nRead);
            }
            String json = new String(out.toByteArray());

            // DECODE JSON
            Gson gson = new Gson();
            return gson.fromJson(json, UserUpdateResponse.class);
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }*/
        return null;
    }
}
