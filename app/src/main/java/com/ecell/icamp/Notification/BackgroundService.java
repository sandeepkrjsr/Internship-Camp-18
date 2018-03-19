package com.ecell.icamp.Notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ecell.icamp.R;
import com.mongodb.Cursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 02-Jan-18.
 */

public class BackgroundService extends Service {

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private Document myDoc;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mongo = new MongoClient("ecell.org.in", 27017);
        new connection().execute();
    }

    public class connection extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection("notification");

                FindIterable<Document> iterDoc = collection.find(eq("target", "all"));
                for(Document doc : iterDoc) {
                    myDoc = doc;
                }

                JSONObject jsonObject=new JSONObject(myDoc.toJson());
                String title = jsonObject.getString("title");
                String msg = jsonObject.getString("message");

                long count = collection.count();
                postNotify(title, msg);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private void postNotify(String title, String msg) {
        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(R.drawable.icon)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, notification);
        startForeground(0, notification);
    }
}
