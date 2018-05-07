package com.apporelbotna.asgame.model.dao.search;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.dao.StucomGamesDAOImpl;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Jandol on 02/14/2018.
 */

public class SearchPlayersFromGameAsyncTask extends AbstractAsyncTask<Integer, Void, StucomGamesDAOImpl.GameRankingTO>
{
    public SearchPlayersFromGameAsyncTask(WeakReference ref)
    {
        super(ref);
    }

    @Override
    protected StucomGamesDAOImpl.GameRankingTO doInBackground(Integer... ints)
    {
        InputStream in = null;
        try {
            String urlStr = ref.getContext().getString(R.string.stucomGamesWSGetConcreteGame, ints[0]);

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
            return gson.fromJson(json, StucomGamesDAOImpl.GameRankingTO.class);
        }
        catch (Exception e) {
            return null;
        }
        finally {
            try { if (in != null) in.close(); } catch (Exception ignored) { }
        }
    }
}
