package edu.harvard.cs50.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private Drawable icon;
    private final ColorDrawable background = new ColorDrawable(Color.RED);
    private NotesAdapter notesAdapter;

    public SwipeToDeleteCallback(NotesAdapter notesAdapter, Context context) {
        super(0, ItemTouchHelper.LEFT );
        this.notesAdapter = notesAdapter;
        icon = ContextCompat.getDrawable(context, R.drawable.ic_baseline_delete_36);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View  itemView = viewHolder.itemView;
        int backgroundOffSet = 20;

        if (dX < 0) {
            background.setBounds(itemView.getRight() + ((int)dX) - backgroundOffSet
                    , itemView.getTop()
                    , itemView.getRight()
                    ,itemView.getBottom());
        } else {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);

        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

        if (dX < 0) {
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicHeight();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft,iconTop,iconRight,iconBottom);

            background.setBounds(itemView.getRight() + ((int)dX) - backgroundOffSet
                    , itemView.getTop()
                    , itemView.getRight()
                    ,itemView.getBottom());
        } else {
            background.setBounds(0,0,0,0);
        }
        background.draw(c);
        icon.draw(c);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        notesAdapter.deleteItem(position);
        notesAdapter.reload();
    }
}
