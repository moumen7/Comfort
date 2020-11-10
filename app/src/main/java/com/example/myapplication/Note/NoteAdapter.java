package com.example.myapplication.Note;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.noteholder> {
    private List<Note> notes =  new ArrayList<>();
    private List<Note> CurrentNotesFilter = new  ArrayList<>();
    private OnItemClicklistener listener;
    private Context mcontext;
    public NoteAdapter(Context context) {

        mcontext = context;
    }



    @NonNull
    @Override
    public noteholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid,parent,false);
        return new noteholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull noteholder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);


        Note currentNote = notes.get(position);
        Glide.with(mcontext)
                .load(BitmapFactory.decodeByteArray(currentNote.getbArray(), 0, currentNote.getbArray().length))
                .apply(requestOptions)
                .into(holder.imageView);
        holder.textviewtitle.setText(currentNote.getTitle());

}

    @Override
    public int getItemCount()
    {

        return notes.size();
    }
    public void setNotes(List<Note> notes) {
        this.notes = notes;
        CurrentNotesFilter = notes;
        notifyDataSetChanged();
    }
    public Note getatnote(int position)
    {
        return notes.get(position);
    }

    class noteholder extends RecyclerView.ViewHolder
    {
        private TextView textviewtitle;
        private ImageView imageView;

        public noteholder(@NonNull View itemView) {
            super(itemView);
            textviewtitle = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageview_widget);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!= null && position!= RecyclerView.NO_POSITION)
                    listener.OnItemClick(notes.get(position));
                }
            });

        }
    }
    public interface OnItemClicklistener
    {
        void OnItemClick(Note note);
    }
    public void SetOnItemClicklistener(OnItemClicklistener listener)
    {
        this.listener = listener;
    }

    public Filter getFilter() {
        return examplefilter;
    }

    public Filter examplefilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Note> filteredList = new ArrayList<>();
            if(TextUtils.isEmpty(constraint) || constraint == null || constraint.length()==0)
            {
                filteredList.addAll(CurrentNotesFilter);
            }
            else
            {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(Note item :CurrentNotesFilter)
                {
                    if(item.getTitle().toLowerCase().contains(filterpattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                notes =  (List<Note>) results.values;
                notifyDataSetChanged();

        }
    };
}
