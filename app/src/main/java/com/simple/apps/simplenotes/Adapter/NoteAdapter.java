package com.simple.apps.simplenotes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simple.apps.simplenotes.Helper.DatabaseHelper;
import com.simple.apps.simplenotes.Listener.OnNoteGestureListener;
import com.simple.apps.simplenotes.MainActivity;
import com.simple.apps.simplenotes.Models.Note;
import com.simple.apps.simplenotes.NoteActivity;
import com.simple.apps.simplenotes.R;

import java.util.List;

/**
 * Created by anthony on 15.06.16.
 */
public class NoteAdapter extends BaseAdapter {
    /**  Data used to display */
    private List<Note> notes;

    /** */
    private Context context;

    private DatabaseHelper database;

    public NoteAdapter(Context context, List<Note> notes){
        this.context = context;
        this.notes = notes;
        database = new DatabaseHelper(context);
    }

    public void addAll(List<Note> notes){
        this.notes.addAll(notes);
    }

    public void add(Note note){
        notes.add(note);
    }

    public void clear(){
        notes.clear();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return notes.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder{
        LinearLayout container;
        TextView title;
        GestureDetectorCompat gesture;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = LayoutInflater.from(context).inflate(R.layout.listitem_note, null);
        holder.title = (TextView)row.findViewById(R.id.textView_note_item);
        holder.title.setText(notes.get(position).getTitle());
        holder.container = (LinearLayout)row.findViewById(R.id.layout_container);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                intent.putExtra(Note.ID_KEY, notes.get(position).getId());
                ((Activity)context).startActivityForResult(intent, MainActivity.REQUEST_EDIT_NOTE);
            }
        });
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure that you wish to delete this Note?");
                builder.setPositiveButton("Delete", null);
                builder.setNegativeButton("Cancel", null);
                final AlertDialog dialog = builder.create();
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(final DialogInterface dialog1) {
                        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database.deleteNote(notes.remove(position));
                                notifyDataSetChanged();
                                dialog1.dismiss();
                            }
                        });
                        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                    }
                });
                dialog.show();

                return true;
            }
        });


        return row;
    }
}
