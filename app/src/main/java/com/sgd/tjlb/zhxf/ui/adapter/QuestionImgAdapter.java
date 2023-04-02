package com.sgd.tjlb.zhxf.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.sgd.tjlb.zhxf.R;
import com.sgd.tjlb.zhxf.app.AppAdapter;
import com.sgd.tjlb.zhxf.entity.QuestionImgData;
import com.sgd.tjlb.zhxf.http.glide.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class QuestionImgAdapter extends AppAdapter<QuestionImgData> {

    private List<QuestionImgData> mDatas = new ArrayList<>();

    public QuestionImgAdapter(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuestionImgHolder();
    }

    public void initData(List<QuestionImgData> data) {
        if (data != null) {
            mDatas = data;
        }
        setData(data);
    }

    private final class QuestionImgHolder extends ViewHolder {

        private AppCompatImageView mIvQuestionImg;
        private AppCompatImageView ivClose;

        private Context mContext;

        public QuestionImgHolder() {
            super(R.layout.item_question_img);
            mContext = getContext();
            mIvQuestionImg = (AppCompatImageView) findViewById(R.id.iv_item_question_img);
            ivClose = (AppCompatImageView) findViewById(R.id.iv_close);

        }

        @Override
        public void onBindView(int position) {

            QuestionImgData imgData = mDatas.get(position);

            switch (imgData.getType()) {
                case QuestionImgData.Add_Gone:
                    GlideApp.with(mContext)
                            .load(imgData.getUrl())
                            .transform(new MultiTransformation<>(new CenterCrop(),
                                    new RoundedCorners((int) getResources().getDimension(R.dimen.dp_4))))
                            .into(mIvQuestionImg);
//                    GlideUtils.loadRoundCircleImage(mContext, imgData.getUrl(), mIvQuestionImg, 4);
                    ivClose.setVisibility(View.VISIBLE);
                    break;
                case QuestionImgData.Add_Visible:
                    GlideApp.with(mContext)
                            .load(R.mipmap.img_ioc_gray_add)
                            .transform(new MultiTransformation<>(new CenterCrop(),
                                    new RoundedCorners((int) getResources().getDimension(R.dimen.dp_4))))
                            .into(mIvQuestionImg);
                    ivClose.setVisibility(View.GONE);
                    break;
//                case QuestionImgData.Minus_Visible:
//                    mIvQuestionImg.setSelected(false);
//                    break;

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position);
                }
            });

            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onCloseItemClick(position);
                }
            });

        }


    }

    public OnItemClick itemClickListener;

    public void setItemClickListener(OnItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClick {
        void onItemClick(int position);
        void onCloseItemClick(int position);
    }
}
