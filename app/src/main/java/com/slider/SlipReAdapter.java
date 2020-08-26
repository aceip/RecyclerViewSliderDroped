package com.slider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.slider.SlipReAdapter.RViewHolder;
public class SlipReAdapter extends RecyclerView.Adapter<RViewHolder> {

  private RecyclerView.Adapter mAdapter;
  private ISlipClickAction mISlipClickAction;
  private int mSlipViewId;
  public final static int MODE_DELETE = 0;
  public final static int MODE_CLICK = 0;
  private int mMode = MODE_DELETE;
  private int mSlipWidth = 0;

  public static class Builder {

    private RecyclerView.Adapter mAdapter;
    private ISlipClickAction mISlipClickAction;
    private int mSlipViewId;
    private int mMode;
    private int mSlipWidth;

    public Builder setAdapter(RecyclerView.Adapter adapter) {
      mAdapter = adapter;
      return this;
    }

    public Builder setISlipClickAction(
        ISlipClickAction ISlipClickAction) {
      mISlipClickAction = ISlipClickAction;
      return this;
    }

    public Builder setSlipViewId(int slipViewId) {
      mSlipViewId = slipViewId;
      return this;
    }

    public Builder setMode(int mode) {
      mMode = mode;
      return this;
    }

    public Builder setSlipWidth(float slipWidth) {
      mSlipWidth = (int) slipWidth;
      return this;
    }

    SlipReAdapter build() {
      SlipReAdapter slipReAdapter = new SlipReAdapter();
      slipReAdapter.setAdapter(mAdapter);
      slipReAdapter.setISlipClickAction(mISlipClickAction);
      slipReAdapter.setMode(mMode);
      slipReAdapter.setSlipViewId(mSlipViewId);
      slipReAdapter.setSlipWidth(mSlipWidth);
      return slipReAdapter;
    }
  }

  public SlipReAdapter() {

  }

  public void setAdapter(RecyclerView.Adapter adapter) {
    mAdapter = adapter;
  }

  public void setISlipClickAction(
      ISlipClickAction ISlipClickAction) {
    mISlipClickAction = ISlipClickAction;
  }

  public void setSlipViewId(int slipViewId) {
    mSlipViewId = slipViewId;
  }

  public void setMode(int mode) {
    mMode = mode;
  }

  public void setSlipWidth(int slipWidth) {
    mSlipWidth = slipWidth;
  }

  @Override
  public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slip, parent, false);
    LinearLayout contentLL = view.findViewById(R.id.content_ll);
    LinearLayout deleteLl = view.findViewById(R.id.delete_ll);
    View delete = LayoutInflater.from(parent.getContext()).inflate(mSlipViewId, null, false);
    deleteLl.addView(delete);

    LinearLayout.LayoutParams layoutParams = new LayoutParams(
        parent.getResources().getDisplayMetrics().widthPixels,
        ViewGroup.LayoutParams.WRAP_CONTENT);
    ViewHolder viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
    viewHolder.itemView.setLayoutParams(layoutParams);
    contentLL.addView(viewHolder.itemView);

    return new RViewHolder(view, viewHolder, mSlipWidth);
  }

  @Override
  public void onBindViewHolder(final RViewHolder holder, final int position) {
    mAdapter.onBindViewHolder(holder.mViewHolder, position);
    holder.deleteLl.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mISlipClickAction.onAction(holder.getAdapterPosition());
        holder.mElasticHorizontalScrollView.reset();
        if (mMode == MODE_DELETE) {
          notifyItemRemoved(holder.getAdapterPosition());
        } else if (mMode == MODE_CLICK) {
          notifyItemChanged(holder.getAdapterPosition());
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mAdapter != null ? mAdapter.getItemCount() : 0;
  }

  public static class RViewHolder extends RecyclerView.ViewHolder {

    private View deleteLl;
    private ElasticHorizontalScrollView mElasticHorizontalScrollView;
    private ViewHolder mViewHolder;

    public RViewHolder(View itemView, ViewHolder viewHolder, int threshold) {
      super(itemView);
      mViewHolder = viewHolder;
      deleteLl = itemView.findViewById(R.id.delete_ll);
      mElasticHorizontalScrollView = itemView.findViewById(R.id.ElasticHorizontalScrollView);
      if (threshold != 0) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(threshold,
            ViewGroup.LayoutParams.WRAP_CONTENT);
        deleteLl.setLayoutParams(layoutParams);
        mElasticHorizontalScrollView.setThreshold(threshold);
      } else {
        deleteLl.post(new Runnable() {
          @Override
          public void run() {
            int width = deleteLl.getWidth();
            mElasticHorizontalScrollView.setThreshold(width);
          }
        });
      }
    }
  }

  public interface ISlipClickAction {

      public void onAction(int position);
  }


}
