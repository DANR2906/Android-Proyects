package com.dreamdiary.Adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dreamdiary.Database.AdminSQLiteOpenHelper;
import com.dreamdiary.Database.DataBaseController;
import com.dreamdiary.EditDreamNote;
import com.dreamdiary.MainActivity;
import com.dreamdiary.Model.Dream;
import com.dreamdiary.R;

import java.util.ArrayList;
import java.util.List;


public class DreamAdapter extends RecyclerView.Adapter<DreamAdapter.ViewHolder> {

    private final Context context;
    private final List<Dream> dreamList;
    DataBaseController dataBaseController;

    public DreamAdapter(Context context, List<Dream> dreamList) {
        this.context = context;
        this.dreamList = dreamList;
        dataBaseController = new DataBaseController(this.context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.dream_item, parent, false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Dream dream = dreamList.get(position);
        String[] date = dream.getDate().split("/");
        holder.day.setText(date[0]);
        holder.month.setText(date[1]);
        holder.year.setText(date[2].substring(2));

        if(dream.getTitle().length() < 25)
            holder.title.setText(dream.getTitle());
        else{
            String largeTittle = dream.getTitle().substring(0,21) + "...";
            holder.title.setText(largeTittle);
        }



        //Action when the dream are pressed
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!holder.isSelected)
                    renderSetting(holder);
                else
                    renderDream(holder);

                holder.setSelected(!holder.isSelected);
            }
        });

        holder.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderDream(holder);
                holder.setSelected(!holder.isSelected);
            }
        });

        holder.deleteDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete dream by id
                boolean delete = dataBaseController.deleteDreamByID(String.valueOf(dream.getId()));
                Intent intent = new Intent(context, MainActivity.class);
                MainActivity.myActivity.finish();
                context.startActivity(intent);

            }
        });

        holder.editDream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open the new activity to edit the dream
                Intent intent = new Intent(context, EditDreamNote.class);
                intent.putExtra("Date_", dream.getDate());
                intent.putExtra("Title_", dream.getTitle());
                intent.putExtra("DreamText_", dream.getText());
                intent.putExtra("DreamId_", String.valueOf(dream.getId()));
                context.startActivity(intent);
            }
        });


        //set The interaction menu with the dreams
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                LayoutInflater inflater = MainActivity.myActivity.getLayoutInflater();

                View view = inflater.inflate(R.layout.fragment_view_dream, null);

                alert.setView(view);

                final AlertDialog dialog = alert.create();

                dialog.show();

                final EditText fieldDate;
                final EditText fieldTittle;
                final TextView fieldDreamText;

                fieldDate = view.findViewById(R.id.field_date);
                fieldTittle = view.findViewById(R.id.field_tittle);
                fieldDreamText = view.findViewById(R.id.field_dream_text);

                fieldDate.setText(dream.getDate());
                fieldTittle.setText(dream.getTitle());
                fieldDreamText.setText(dream.getText());

                final Button buttonContinue;

                 buttonContinue = view.findViewById(R.id.button_continue_view_dream);

                 buttonContinue.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dialog.cancel();
                     }
                 });

                return false;
            }
        });


    }


    public void renderSetting(ViewHolder holder){
        holder.day.setVisibility(View.INVISIBLE);
        holder.month.setVisibility(View.INVISIBLE);
        holder.year.setVisibility(View.INVISIBLE);
        holder.title.setVisibility(View.INVISIBLE);

        holder.goBack.setVisibility(View.VISIBLE);
        holder.deleteDream.setVisibility(View.VISIBLE);
        holder.editDream.setVisibility(View.VISIBLE);
    }

    public void renderDream(ViewHolder holder){
        holder.day.setVisibility(View.VISIBLE);
        holder.month.setVisibility(View.VISIBLE);
        holder.year.setVisibility(View.VISIBLE);
        holder.title.setVisibility(View.VISIBLE);

        holder.goBack.setVisibility(View.INVISIBLE);
        holder.deleteDream.setVisibility(View.INVISIBLE);
        holder.editDream.setVisibility(View.INVISIBLE);

    }



    @Override
    public int getItemCount() {
        return dreamList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView day;
        public TextView month;
        public TextView year;
        public TextView title;
        public RelativeLayout relativeLayout;

        public ImageButton goBack;
        public ImageButton deleteDream;
        public ImageButton editDream;
        public boolean isSelected = false;

        public ViewHolder(final View itemView){
            super(itemView);

            day = itemView.findViewById(R.id.text_view_day);
            month = itemView.findViewById(R.id.text_view_month);
            year = itemView.findViewById(R.id.text_view_year);
            title = itemView.findViewById(R.id.text_view_tittle);
            relativeLayout = itemView.findViewById(R.id.relative_layout);

            goBack = itemView.findViewById(R.id.image_button_cancel);
            deleteDream = itemView.findViewById(R.id.image_button_delete);
            editDream = itemView.findViewById(R.id.image_button_edit);



        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

    }
}
