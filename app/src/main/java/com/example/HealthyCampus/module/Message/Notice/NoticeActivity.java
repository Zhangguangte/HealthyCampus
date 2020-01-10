package com.example.HealthyCampus.module.Message.Notice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.NoticeAdapter;
import com.example.HealthyCampus.common.data.Bean.ChatItemBean;
import com.example.HealthyCampus.common.helper.GreenDaoHelper;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.PictureUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.RecycleOnscrollListener;
import com.example.HealthyCampus.common.widgets.custom_dialog.MapDialog;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.greendao.NoticeBeanDao;
import com.example.HealthyCampus.greendao.SearchAddDao;
import com.example.HealthyCampus.greendao.model.NoticeBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static com.example.HealthyCampus.common.helper.SPHelper.NOTICE_DATE;


public class NoticeActivity extends BaseActivity<NoticeContract.View, NoticeContract.Presenter> implements NoticeContract.View, NoticeAdapter.onItemClick {

    //标题栏
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvFunction)
    TextView tvClear;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.noNoticeLayout)
    RelativeLayout noNoticeLayout;

    @BindView(R.id.rvNotice)
    RecyclerView rvNotice;

    private NoticeAdapter noticeAdapter;
    private List<NoticeVo> mData = new ArrayList<>();

    private NoticeBeanDao dao;
    private int row = 0;
    private Long count;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.message_notice);
    }

    @Override
    protected NoticeContract.Presenter createPresenter() {
        return new NoticePresenter();
    }

    @Override
    protected void initView() {
        showProgressDialog(getString(R.string.loading_footer_tips));
        setCustomActionBar();
        initRecycleView();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        //初始化
        GreenDaoHelper daoHelper = new GreenDaoHelper(this);
        dao = daoHelper.initDao().getNoticeBeanDao();
        mPresenter.getAllNotice();
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                switch (response.code) {
                    case 1013:
                        break;
                    case 1000:
                        ToastUtil.show(getContext(), "Bad Server");
                        break;
                    case 1003:
                        ToastUtil.show(getContext(), "Invalid Parameter");
                        break;
                    default:
                        ToastUtil.show(getContext(), "未知错误1:" + throwable.getMessage());
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }

    }

    @Override
    public void showSuccess(List<NoticeVo> noticeVos) {
        Log.e("NoticeActivity" + "123456", "0000000000000000000");
        NoticeBean noticeBean;

        for (NoticeVo noticeVo : noticeVos) {
            noticeBean = new NoticeBean();
            noticeBean.setId(null);
            noticeBean.setCreate_time(noticeVo.getCreate_time());
            noticeBean.setContent(noticeVo.getContent());
            noticeBean.setStatus(noticeVo.getStatus());
            noticeBean.setNoticeType(noticeVo.getNoticeType());
            noticeBean.setN_id(noticeVo.getId());
            dao.insert(noticeBean);
        }
        Log.e("NoticeActivity" + "123456", "........................");
    }

    @Override
    public void showSuccess(String message) {
        ToastUtil.show(getContext(), message);
    }

    private void initRecycleView() {
        noticeAdapter = new NoticeAdapter(this, mData, this);
        rvNotice.setLayoutManager(new MyLinearLayoutManager(this));
        rvNotice.setHasFixedSize(true);
        rvNotice.setAdapter(noticeAdapter);
        rvNotice.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (noticeAdapter.isLoad())
                    loadMore();
            }
        });
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.notice);
        tvClear.setText(R.string.clear);
        tvClear.setVisibility(View.VISIBLE);
        tvClear.setClickable(false);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.tvFunction)
    public void tvFunction(View view) {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this);
        materialDialog.title("点击确定删除所有通知")
                .negativeText("取消")
                .positiveText("确定")
                .onPositive((dialog, which) -> mPresenter.clearNotice());
        MaterialDialog materialDialog1 = materialDialog.build();
        materialDialog1.show();
    }

    @SuppressLint("InflateParams")
    @Override
    public void deleteNotice(int position) {
        View view = getLayoutInflater().inflate(R.layout.dialog_delete, null);
        MapDialog mapDialog = new MapDialog(getContext(), view, R.style.DialogMap);
        Objects.requireNonNull(mapDialog.getWindow()).findViewById(R.id.tvDelete).setOnClickListener(v -> {
            if ("ALL".equals(mData.get(position).getNoticeType())) {
                //删除本地数据库（sqlite）内数据
                dao.deleteByKey(mData.get(position).getN_id());
                mData.remove(position);
                noticeAdapter.notifyItemRemoved(position);
                mapDialog.dismiss();
            } else {
                mPresenter.deleteNotice(mData.get(position).getId());
                dao.deleteByKey(mData.get(position).getN_id());
                mData.remove(position);
                noticeAdapter.notifyItemRemoved(position);
                mapDialog.dismiss();
            }
        });
        mapDialog.show();
    }

    @Override
    public void lookNotice(int position) {
        mPresenter.lookNotice(mData.get(position).getId());
        NoticeBean noticeBean =dao.load(mData.get(position).getN_id());
        noticeBean.setStatus("READ");
        dao.update(noticeBean);
    }

    @Override
    public void clearSuccess() {
        mData.clear();
        noticeAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData() {

        tvClear.setClickable(true);
        SPHelper.setString(NOTICE_DATE, DateUtils.getStringDate());
        count = dao.count();
        loadMore();
        dismissProgressDialog();
    }

    private void loadMore() {
        Log.e("NoticeActivity" + "123456", "http:***********************");
        Log.e("NoticeActivity" + "123456", "http:count:" + count);
        List<NoticeBean> list = dao.queryBuilder().orderDesc(NoticeBeanDao.Properties.Create_time).offset(row++ * 15).limit(15).list();
        for (NoticeBean bean : list) {
            mData.add(new NoticeVo(bean));
        }
        if (0 == mData.size()) {
            noNoticeLayout.setVisibility(View.VISIBLE);
            rvNotice.setVisibility(View.GONE);
            dismissProgressDialog();
        }
        noticeAdapter.setLoad(count > mData.size());
        noticeAdapter.notifyDataSetChanged();
    }


}
