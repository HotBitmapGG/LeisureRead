package com.hotbitmapgg.leisureread.widget.recycler.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hcc on 2016/12/28 13:35
 * 100332338@qq.com
 * LeisureRead
 *
 * @HotBitmapGG RecycleView通用适配器
 */
public abstract class AbsRecyclerViewAdapter<T>
    extends RecyclerView.Adapter<AbsRecyclerViewAdapter.ClickableViewHolder> {

  private Context context;

  protected RecyclerView mRecyclerView;

  public List<T> mDataSources;

  private List<RecyclerView.OnScrollListener> mListeners = new ArrayList<>();


  public AbsRecyclerViewAdapter(RecyclerView recyclerView) {

    this.mRecyclerView = recyclerView;
    this.mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

      @Override
      public void onScrollStateChanged(RecyclerView rv, int newState) {

        for (RecyclerView.OnScrollListener listener : mListeners) {
          listener.onScrollStateChanged(rv, newState);
        }
      }


      @Override
      public void onScrolled(RecyclerView rv, int dx, int dy) {

        for (RecyclerView.OnScrollListener listener : mListeners) {
          listener.onScrolled(rv, dx, dy);
        }
      }
    });
  }


  public void setDataSources(List<T> dataSources) {

    this.mDataSources = dataSources;
  }


  @Override
  public int getItemCount() {

    return mDataSources == null ? 0 : mDataSources.size();
  }


  public void addOnScrollListener(RecyclerView.OnScrollListener listener) {

    mListeners.add(listener);
  }


  public interface OnItemClickListener {

    void onItemClick(int position, ClickableViewHolder holder);
  }

  public interface OnItemLongClickListener {

    boolean onItemLongClick(int position, ClickableViewHolder holder);
  }

  private OnItemClickListener itemClickListener;

  private OnItemLongClickListener itemLongClickListener;


  public void setOnItemClickListener(OnItemClickListener listener) {

    this.itemClickListener = listener;
  }


  public void setOnItemLongClickListener(OnItemLongClickListener listener) {

    this.itemLongClickListener = listener;
  }


  public void bindContext(Context context) {

    this.context = context;
  }


  public Context getContext() {

    return this.context;
  }


  @Override
  public void onBindViewHolder(final ClickableViewHolder holder, final int position) {

    holder.getParentView().setOnClickListener(v -> {

      if (itemClickListener != null) {
        itemClickListener.onItemClick(position, holder);
      }
    });
    holder.getParentView()
        .setOnLongClickListener(v -> itemLongClickListener != null &&
            itemLongClickListener.onItemLongClick(position, holder));
  }


  public static class ClickableViewHolder extends RecyclerView.ViewHolder {

    private View parentView;


    public ClickableViewHolder(View itemView) {

      super(itemView);
      this.parentView = itemView;
    }


    View getParentView() {

      return parentView;
    }


    public <T extends View> T $(@IdRes int id) {

      return (T) parentView.findViewById(id);
    }
  }
}