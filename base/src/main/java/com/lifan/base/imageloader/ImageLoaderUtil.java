package com.lifan.base.imageloader;

import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.lifan.base.R;

public class ImageLoaderUtil {

    /**
     * 常规使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))//交叉渐入渐出(Cross fade from the placeholder)
                .into(imageView);
    }


    /**
     * 自定义RequestOptions使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(context).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }

    /**
     * fragment自定义RequestOptions使用
     */
    public static void LoadImage(Fragment fragment, Object url, ImageView imageView, RequestOptions requestOptions) {
        Glide.with(fragment).load(url)
                .apply(requestOptions)
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageView);
    }


    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageViewTarget imageViewTarget) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .into(imageViewTarget);
    }

    /**
     * 需要回调时使用
     */
    public static void LoadImage(Context context, Object url, ImageView imageView, RequestListener listener) {
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .transition(new DrawableTransitionOptions().crossFade(800))
                .listener(listener)
                .into(imageView);
    }

//
//    mImageView = findViewById(R.id.imageview);
//    String url = "http://pic15.nipic.com/20110727/8044451_163345277152_2.jpg";
//        Glide.with(this).load(url).into(mImageView);
//        ImageLoaderUtil.LoadImage(this,url,mImageView);

//    /**
//     * 不做缓存的圆形头像
//     */
//    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
//            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
//            .skipMemoryCache(true);//不做内存缓存
//        ImageLoaderUtil.LoadImage(this,url,mImageView,mRequestOptions);

//        ImageLoaderUtil.LoadImage(this, url, mImageView, new RequestListener() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target,
//                                        boolean isFirstResource) {
//                return false;//失败时调用
//            }
//
//            @Override
//            public boolean onResourceReady(Object resource, Object model, Target target,
//                                           DataSource dataSource, boolean isFirstResource) {
//                return false;//成功时调用
//            }
//        });

//        ImageLoaderUtil.LoadImage(this, url, new ImageViewTarget(mImageView) {
//            @Override
//            protected void setResource(@Nullable Object resource) {
//
//            }
//        });


}
