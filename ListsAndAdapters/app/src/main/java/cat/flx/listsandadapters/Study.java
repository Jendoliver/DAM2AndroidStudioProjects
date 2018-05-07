package cat.flx.listsandadapters;

import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.Map;

class Study {
    int id;
    String title;
    String fullTitle;
    int course;
    int hours;
    String teacher;
    Drawable photo;

    Study(int id, String title, String fullTitle, int course, int hours, String teacher, Drawable photo) {
        this.id = id;
        this.title = title;
        this.fullTitle = fullTitle;
        this.course = course;
        this.hours = hours;
        this.teacher = teacher;
        this.photo = photo;
    }

    Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("title", title);
        map.put("fullTitle", fullTitle);
        map.put("course", course);
        map.put("hours", hours);
        map.put("teacher", teacher);
        map.put("photo", photo);
        return map;
    }
}