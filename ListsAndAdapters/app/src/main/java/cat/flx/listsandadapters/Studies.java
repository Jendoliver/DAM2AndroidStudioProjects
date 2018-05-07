package cat.flx.listsandadapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Studies extends ArrayList<Study> {

    void loadData(Context ctx, int rawResource) {
        Resources res = ctx.getResources();
        InputStream inputStream = res.openRawResource(rawResource);
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream));
            String line;
            this.clear();
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(":");
                int id = R.drawable.unknown;
                try { id = res.getIdentifier(parts[6], "drawable", ctx.getPackageName()); }
                catch(Exception e) { Log.e("flx", "Drawable not found"); }
                Drawable photo = res.getDrawable(id);
                Study study = new Study(Integer.parseInt(parts[0]),
                        parts[1], parts[2], Integer.parseInt(parts[3]),
                        Integer.parseInt(parts[4]), parts[5], photo);
                this.add(study);
            }
        }
        catch (IOException ioe) {
            Log.e("flx", "Failed to load data!");
        }
    }

    List<Map<String, Object>> toListOfMaps() {
        List<Map<String, Object>> data = new ArrayList<>();
        for(Study study: this) {
            Map<String, Object> item = study.toMap();
            data.add(item);
        }
        return data;
    }
}