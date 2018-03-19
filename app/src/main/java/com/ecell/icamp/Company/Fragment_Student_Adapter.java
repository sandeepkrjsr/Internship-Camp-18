package com.ecell.icamp.Company;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.List;

import static com.ecell.icamp.Company.Company_Dashboard.user;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Fragment_Student_Adapter extends RecyclerView.Adapter<Fragment_Student_Adapter.ViewHolder> {

    private LinearLayout st_card, st_status;
    private TextView st_name, st_skillset, st_college, st_branch, st_year;
    private ImageView st_email, st_mobile, radio;

    private List<String> li_id, li_name, li_skillset, li_college, li_branch, li_year, li_email, li_mobile;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            st_card = (LinearLayout) view.findViewById(R.id.st_card);
            st_name = (TextView) view.findViewById(R.id.st_name);
            st_skillset = (TextView) view.findViewById(R.id.st_skillset);
            st_college = (TextView) view.findViewById(R.id.st_college);
            st_branch = (TextView) view.findViewById(R.id.st_branch);
            st_year = (TextView) view.findViewById(R.id.st_year);
            st_email = (ImageView) view.findViewById(R.id.st_email);
            st_mobile = (ImageView) view.findViewById(R.id.st_mobile);
            st_status = (LinearLayout) view.findViewById(R.id.st_status);
            radio = (ImageView) view.findViewById(R.id.radio);
        }
    }

    public Fragment_Student_Adapter(List<String> li_id, List<String> li_name, List<String> li_skillset, List<String> li_college, List<String> li_branch, List<String> li_year, List<String> li_email, List<String> li_mobile) {
        this.li_id = li_id;
        this.li_name = li_name;
        this.li_skillset = li_skillset;
        this.li_college = li_college;
        this.li_branch = li_branch;
        this.li_year = li_year;
        this.li_email = li_email;
        this.li_mobile = li_mobile;
    }

    @Override
    public Fragment_Student_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_student, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        st_name.setText(li_name.get(position));
        st_skillset.setText(li_skillset.get(position));
        st_college.setText("  " + li_college.get(position));
        st_branch.setText("  " + li_branch.get(position));
        st_year.setText("  " + li_year.get(position) + " Year");

        st_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", li_email.get(position), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "ICamp 18 - Company Name");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear Applicant,\n\n");
                view.getContext().startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        st_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + li_mobile.get(position)));
                view.getContext().startActivity(intent);
            }
        });

        st_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence choices[] = view.getResources().getStringArray(R.array.status);
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("So what is your decision?");
                builder.setItems(choices, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(view.getContext(),""+choices[which],Toast.LENGTH_SHORT).show();
                        //radio.setBackground(view.getResources().getDrawable(R.drawable.ic_select));
                        if (choices[which].equals("Select"))
                            new connection("selected", li_id.get(position)).execute();
                        if (choices[which].equals("Hold"))
                            new connection("onhold", li_id.get(position)).execute();
                        if (choices[which].equals("Reject"))
                            new connection("rejected", li_id.get(position)).execute();

                    }
                });
                builder.create().show();
            }
        });

        st_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Activity_Student_Resume.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return li_id.size();
       // return 3;
    }

    public class connection extends AsyncTask<String , Void, String> {

        String choice, id;

        public connection(String choice, String id) {
            this.choice = choice;
            this.id = id;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection(user);

                collection.updateOne(eq("id", Company_Dashboard.id), Updates.pull("selected", id));
                collection.updateOne(eq("id", Company_Dashboard.id), Updates.pull("onhold", id));
                collection.updateOne(eq("id", Company_Dashboard.id), Updates.pull("rejected", id));

                collection.updateOne(eq("id", Company_Dashboard.id), Updates.push(choice, id));
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
}
