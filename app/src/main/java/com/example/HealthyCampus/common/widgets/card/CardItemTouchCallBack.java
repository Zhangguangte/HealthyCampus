package com.example.HealthyCampus.common.widgets.card;

import android.graphics.Canvas;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.HealthyCampus.common.adapter.CardAdapter;

import java.util.List;

public class CardItemTouchCallBack extends ItemTouchHelper.Callback {

    private RecyclerView mRecyclerView;
    private CardAdapter mAdapter;
    private List<DailyCard> mDatas;

    public CardItemTouchCallBack(RecyclerView recyclerView, CardAdapter adapter, List<DailyCard> datas) {
        this.mRecyclerView = recyclerView;
        this.mAdapter = adapter;
        this.mDatas = datas;
    }

    /**
     * 是否开启长按拖拽
     * true，开启
     * false,不开启长按退拽
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * 是否开启滑动
     * true，开启
     * false,不开启长按退拽
     *
     * @return
     */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * ItemTouchHelper支持设置事件方向，并且必须重写当前getMovementFlags来指定支持的方向
     * dragFlags  表示拖拽的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
     * swipeFlags 表示滑动的方向，有六个类型的值：LEFT、RIGHT、START、END、UP、DOWN
     * 最后要通过makeMovementFlags（dragFlag，swipe）创建方向的Flag
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        /**
         * 由于我们不需要长按拖拽，所以直接传入0即可，传入0代表不监听
         */

        if (viewHolder.getLayoutPosition() != 0)        //如果不是第一个item，不可以滑动
            return makeMovementFlags(0, 0);
        else {
            int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            return makeMovementFlags(0, swipeFlags);
        }
    }

    /**
     * 长按item就可以拖动，然后拖动到其他item的时候触发onMove
     * 这里我们不需要
     *
     * @param recyclerView
     * @param viewHolder   拖动的viewholder
     * @param target       目标位置的viewholder
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    /**
     * 把item滑走(飞出屏幕)的时候调用
     *
     * @param viewHolder 滑动的viewholder
     * @param direction  滑动的方向
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        DailyCard dailyCard = mDatas.remove(viewHolder.getLayoutPosition());            //移除第一条数据
        mDatas.add(dailyCard);                      //添加至尾部
        mAdapter.notifyDataSetChanged();            //刷新
        viewHolder.itemView.setRotation(0);             //复位
    }

    /**
     * 只要拖动、滑动了item,就会触发这个方法，而且是动的过程中会一直触发
     * 所以动画效果就是在这个方法中来实现的
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        double swipeValue = Math.sqrt(dX * dX + dY * dY);   //滑动离中心的距离
        double fraction = swipeValue / (mRecyclerView.getWidth() * 0.5f);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }

        /**
         * 调整每个子view的缩放、位移之类的
         */
        int childCount = recyclerView.getChildCount();  //拿到子view的数量
//        isUpOrDown(mRecyclerView.getChildAt(childCount - 1));
        for (int i = 0; i < childCount; i++) {
            /** 拿到子view 注意这里,先绘制的i=0，所以最下面一层view的i=0,最上面的i=3*/
            View childView = recyclerView.getChildAt(i);
            int level = childCount - i - 1;  //转换一下,level代表层数,最上面是第0层
            if (level > 0) {
                //下面层,每一层的水平方向都要增大
                childView.setScaleX((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    //1 2层
                    childView.setScaleY((float) (1 - CardConfig.SCALE_GAP * level + fraction * CardConfig.SCALE_GAP));
                    childView.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                    childView.setTranslationZ((float) (CardConfig.TRANS_Z_GAP * (CardConfig.MAX_SHOW_COUNT - 1 - level)
                            + fraction * CardConfig.TRANS_Z_GAP));
                } else {
                    //最下面一层,3层,这层不用变,所以这里不用写
                }
            } else {
                //第0层
                //拿到水平方向的偏移比率
                float xFraction = dX / (mRecyclerView.getWidth() * 0.5f);
                //边界修正,有正有负，因为旋转有两个方向
                if (xFraction > 1) {
                    xFraction = 1;
                } else if (xFraction < -1) {
                    xFraction = -1;
                }
                //第一层左右滑动的时候稍微有点旋转
                childView.setRotation(xFraction * 15);  //这里最多旋转15度
            }
        }
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
//        Log.i(TAG, "getSwipeThreshold: ");
//        if (isUpOrDown(viewHolder.itemView)) { //如果是向上或者向下滑动
//            return Float.MAX_VALUE; //就返回阈值为很大
//        }
        return super.getSwipeThreshold(viewHolder);
    }

    /**
     * 获得逃脱(swipe)速度
     *
     * @param defaultValue
     * @return
     */
    @Override
    public float getSwipeEscapeVelocity(float defaultValue) {
//        Log.e(TAG, "getSwipeEscapeVelocity: " + defaultValue);
//        View topView = mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1);
//        if (isUpOrDown(topView)) { //如果是向上或者向下滑动
//            return Float.MAX_VALUE; //就返回阈值为很大
//        }
        return super.getSwipeEscapeVelocity(defaultValue);
    }


    /**
     * 获得swipe的速度阈值
     *
     * @param defaultValue
     * @return
     */
    @Override
    public float getSwipeVelocityThreshold(float defaultValue) {
//        Log.e(TAG, "getSwipeVelocityThreshold: " + defaultValue);
//        View topView = mRecyclerView.getChildAt(mRecyclerView.getChildCount() - 1);
//        if (isUpOrDown(topView)) { //如果是向上或者向下滑动
//            return Float.MAX_VALUE; //就返回阈值为很大
//        }
        return super.getSwipeVelocityThreshold(defaultValue);
    }

    /**
     * 判断是否是向上滑或者向下滑
     */
    private boolean isUpOrDown(View topView) {
        float x = topView.getX();
        float y = topView.getY();
        int left = topView.getLeft();
        int top = topView.getTop();
        if (Math.pow(x - left, 2) > Math.pow(y - top, 2)) {
            //水平方向大于垂直方向
//            Log.i(TAG, "isUpOrDown: 不是");
            return false;
        } else {
            return true;
//            Log.i(TAG, "isUpOrDown: 是");
        }
    }
}
