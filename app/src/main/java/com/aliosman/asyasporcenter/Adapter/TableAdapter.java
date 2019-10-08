package com.aliosman.asyasporcenter.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.evrencoskun.tableview.adapter.AbstractTableAdapter;
import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.aliosman.asyasporcenter.R;
import com.aliosman.asyasporcenter.Values.TableCell;

//5050
public class TableAdapter extends AbstractTableAdapter<TableCell,TableCell,TableCell> {
    public TableAdapter(Context context) {
        super(context);
    }

    @Override
    public int getColumnHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getRowHeaderItemViewType(int position) {
        return 0;
    }

    @Override
    public int getCellItemViewType(int position) {
        return 0;
    }

    @Override
    public AbstractViewHolder onCreateCellViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_tableview_cell,
                parent, false);
        return new CellViewHolder(layout);

    }

    @Override
    public void onBindCellViewHolder(AbstractViewHolder holder, Object cellItemModel, int columnPosition, int rowPosition) {
        TableCell cell = (TableCell) cellItemModel;

        // Get the holder to update cell item text
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData().length()==0 ? " " :cell.getData());
        //Log.e(TAG, "onBindCellViewHolder: "+cell.getData() );
        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.cell_container.getLayoutParams().width =LinearLayout.LayoutParams.MATCH_PARENT;
        viewHolder.cell_textview.requestLayout();
    }

    @Override
    public AbstractViewHolder onCreateColumnHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_tableview_cell,
                parent, false);
        return new CellViewHolder(layout);
    }

    @Override
    public void onBindColumnHeaderViewHolder(AbstractViewHolder holder, Object columnHeaderItemModel, int columnPosition) {
        TableCell cell = (TableCell) columnHeaderItemModel;

        // Get the holder to update cell item text
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData());

        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        //viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        viewHolder.cell_textview.requestLayout();
    }

    @Override
    public AbstractViewHolder onCreateRowHeaderViewHolder(ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_tableview_cell,
                parent, false);
        return new CellViewHolder(layout);
    }
    private String TAG = getClass().getName();
    @Override
    public void onBindRowHeaderViewHolder(AbstractViewHolder holder, Object rowHeaderItemModel, int rowPosition) {
        TableCell cell = (TableCell) rowHeaderItemModel;

        // Get the holder to update cell item text
        CellViewHolder viewHolder = (CellViewHolder) holder;
        viewHolder.cell_textview.setText(cell.getData());
        //Log.e(TAG, "onBindRowHeaderViewHolder: "+viewHolder.cell_textview.getText().toString());
        // If your TableView should have auto resize for cells & columns.
        // Then you should consider the below lines. Otherwise, you can ignore them.

        // It is necessary to remeasure itself.
        viewHolder.cell_container.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        viewHolder.cell_textview.requestLayout();
    }

    @Override
    public View onCreateCornerView() {
        return LayoutInflater.from(mContext).inflate(R.layout.layout_tableview_corner, null);
    }
}
