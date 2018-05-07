package com.apporelbotna.asgame.model.dao.search;

import android.content.Context;
import android.os.AsyncTask;

import com.apporelbotna.asgame.model.dao.ConvertableTO;

/**
 * Created by Jandol on 02/13/2018.
 */

public abstract class AbstractAsyncTask<Input, Progress, Return extends ConvertableTO> extends AsyncTask<Input, Progress, Return>
{
    public interface WeakReference
    {
        Context getContext();

        /**
         * Sends a message to the WeakReference instance to notify the end of the search task
         * @param callback The object obtained after the search task
         * @param <T> The type of the above mentioned object
         */
        <T extends ConvertableTO> void finished(T callback);
    }

    protected WeakReference ref;
    public AbstractAsyncTask(WeakReference ref)
    {
        super();
        this.ref = ref;
    }

    @Override
    protected void onPostExecute(Return result)
    {
        super.onPostExecute(result);
        ref.finished(result);
    }
}