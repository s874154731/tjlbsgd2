package com.sgd.tjlb.zhxf.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.sgd.tjlb.zhxf.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/***
 * 图片加载工具类
 */
public class GlideUtils {

    /*** 占位图 */
    public static int placeholderImage = R.mipmap.img_loading_loading;
    /*** 错误图 */
    public static int errorImage = R.mipmap.img_loading_fail;
    /*** logo */
    public static int logo = R.mipmap.logo_icon_tjlbsgd;

    /**
     * glide 4.12 使用概况
     *
     * @param context   上下
     * @param imageUrl  地址
     * @param imageView imageview
     */
    public static void loadImageEg(Context context, String imageUrl, ImageView imageView) {
        Glide.with(context)
                //  .asBitmap()//只允许加载静态图片，若传入gif图会展示第一帧（要在load之前）
                //  .asGif()//指定gif格式（要在load之前）
                //  .asDrawable()//指定drawable格式（要在load之前）
                .load(imageUrl)//被加载图像的url地址
                .placeholder(placeholderImage)//占位图片
                .error(errorImage)//错误图片
//                .transition(GenericTransitionOptions.with(R.anim.ucrop_item_animation_fall_down))//图片动画
                .override(800, 800)//设置加载尺寸
                .skipMemoryCache(true)//禁用内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存任何内容
                // .diskCacheStrategy(DiskCacheStrategy.DATA)//只缓存原始图片
                // .diskCacheStrategy(DiskCacheStrategy.RESOURCE)//只缓存转换后的图片
                // .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存所有
                // .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)//Glide根据图片资源智能地选择使用哪一种缓存策略（默认）
                .listener(new RequestListener<Drawable>() {//监听图片加载状态
                    //图片加载完成
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    //图片加载失败
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);//图片最终要展示的地方
    }

    /**
     * 加载图片(默认)
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     */
    public static void loadImage(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载图片(默认)
     *
     * @param context     上下文
     * @param url         链接
     * @param imageView   ImageView
     * @param resourcesID 本地图片 默认
     */
    public static void loadImage(Context context, Object url, ImageView imageView, int resourcesID) {
        RequestOptions options = new RequestOptions()
                .placeholder(resourcesID) //占位图
                .error(resourcesID);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     * @param width     图片宽度
     * @param height    图片高度
     */
    public static void loadImageSize(Context context, String url, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImage) //占位图
                .error(errorImage)             //错误图
                .override(width, height)
                .priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载图片指定宽度
     *
     * @param context   上下文
     * @param url       地址
     * @param imageView imageView
     * @param width     宽度
     */
    public static void loadImageWithWidth(Context context, Object url, ImageView imageView, int width) {
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
//                .dontAnimate();

        Glide.with(context)
                .load(url).error(errorImage)
                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .override(width, width)
//                .fallback(imgId)
                .error(errorImage)
                .transition(DrawableTransitionOptions.withCrossFade())
//                .thumbnail(0.5f)
                .into(imageView);
    }

    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     */

    public static void loadImageSizeKipMemoryCache(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImage) //占位图
                .error(errorImage)             //错误图
                .skipMemoryCache(true);          //禁用掉Glide的内存缓存功能
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     *
     * @param context 上下文
     * @param url     链接
     */
    public static void preloadImage(Context context, String url) {
        Glide.with(context).load(url).preload();
    }

    /**
     * 加载圆形图片
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(placeholderImage) //占位图
                .error(errorImage)             //错误图
                .priority(Priority.HIGH);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆形带边框图片
     *
     * @param context     上下文
     * @param url         链接
     * @param imageView   ImageView
     * @param borderSize  边框宽度 px
     * @param borderColor 边框颜色
     */
    public static void loadCircleWithBorderImage(Context context, String url, ImageView imageView,
                                                 float borderSize, @ColorInt int borderColor) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop()
//                        ,
//                        new CropCircleWithBorderTransformation(SizeUtils.px2dp(context, borderSize), borderColor)
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     * @param radius    圆角 px
     */
    public static void loadRoundCircleImage(Context context, String url, ImageView imageView,
                                            float radius) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners(SizeUtils.dp2px(context, radius))
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);

    }

    /**
     * 加载圆角图片
     *
     * @param context     上下文
     * @param resourcesID 资源文件地址
     * @param url         链接
     * @param imageView   ImageView
     * @param radius      圆角 px
     */
    public static void loadRoundCircleImage(Context context, int resourcesID, String url, ImageView imageView,
                                            float radius) {
        placeholderImage = resourcesID;
        errorImage = resourcesID;

        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners(SizeUtils.dp2px(context, radius))
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);//错误图

        Glide.with(context)
                .load(url)
                .apply(options)
                .thumbnail(loadTransform(imageView.getContext(), resourcesID, radius))
                .into(imageView);

    }


    /**
     * 占位图加载
     *
     * @param context   上下
     * @param url       图片网络地址
     * @param imageView iv
     * @param maxWith   最大
     * @param maxHeight 最小
     * @param radius    圆角
     */
    public static void loadRoundCircleImageNoPaceholder(Context context, String url, ImageView imageView,
                                                        int maxWith, int maxHeight, float radius) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new RoundedCorners(SizeUtils.dp2px(context, radius))//圆角
                ))
                .override(SizeUtils.dp2px(context, maxWith), SizeUtils.dp2px(context, maxHeight))//大小
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     * @param maxWith   宽度 dp
     * @param maxHeight 高度 dp
     * @param radius    圆角 dp
     */
    public static void loadRoundCircleImage(Context context, String url, ImageView imageView,
                                            int maxWith, int maxHeight, float radius) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new RoundedCorners(SizeUtils.dp2px(context, radius))//圆角
                ))
                .override(SizeUtils.dp2px(context, maxWith), SizeUtils.dp2px(context, maxHeight))//大小
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadRoundCircleImage2(Context context, String url, ImageView imageView,
                                             int maxWith, int maxHeight, float radius) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new RoundedCorners(SizeUtils.dp2px(context, radius))//圆角
                ))
                .override(SizeUtils.dp2px(context, maxWith), SizeUtils.dp2px(context, maxHeight))//大小
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 在更新网络图片后会导致图片按占位符的比例缩放，只有重新加载后图片才正常显示
                        // 这里通过移除占位符后再重新加载图片实现图片按比例显示，但是会导致短暂的闪烁
                        imageView.setImageResource(0);
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     * @param blur      模糊度，一般1-100够了，越大越模糊
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int blur) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop(),
                        new BlurTransformation(context, blur)
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     * @param blur      模糊度，一般1-100够了，越大越模糊
     * @param sampling  取样
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int blur, int sampling) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop(),
                        new BlurTransformation(context, sampling)
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * 加载灰度(黑白)图片（自定义透明度）
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     */
    public static void loadBlackImage(Context context, String url, ImageView imageView) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop()
//                        ,
//                        new GrayscaleTransformation()
                ))
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context   上下文
     * @param url       链接
     * @param imageView ImageView
     */
    public static void loadGif(Context context, Object url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context)
                .load(url)
                .apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);

    }

    /**
     * 下载图片
     * 在RequestListener的onResourceReady方法里面获取下载File图片
     * new RequestListener<File>() {
     * *@Override
     * public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
     * return false;
     * }
     * <p>
     * *@Override
     * public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
     * //resource即为下载取得的图片File
     * return false;
     * }
     * }
     *
     * @param context         上下文
     * @param url             下载链接
     * @param requestListener 下载监听
     */
    public void downloadImage(final Context context, final String url, RequestListener<File> requestListener) {
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .addListener(requestListener).preload();
    }


    /**
     * 加载第2秒的帧数作为封面
     * url就是视频的地址
     * 版本4+之后才有的功能
     */
    public static void loadVideoUrl(Context context, String url, ImageView imageView,
                                    int maxWith, int maxHeight, float radius) {
        RequestOptions options = RequestOptions
                .bitmapTransform(
                        new MultiTransformation<>(
                                new RoundedCorners(SizeUtils.dp2px(context, radius))//圆角
                        ))
                .override(SizeUtils.dp2px(context, maxWith), SizeUtils.dp2px(context, maxHeight))//大小
                .placeholder(placeholderImage) //占位图
                .error(errorImage);            //错误图
        Glide.with(context)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(1000000)
//                                .override(SizeUtils.dp2px(context, maxWith), SizeUtils.dp2px(context, maxHeight))
                                .override(maxWith, maxHeight)
                                .error(errorImage)//可以忽略
                                .placeholder(placeholderImage)//可以忽略
                )
                .load(url)
                .into(imageView);
    }


    /**
     * 根据url获取Bitmap
     *
     * @param context 上下
     * @param url     地址
     * @return 获取Bitmap
     */
    public static Bitmap getBitmap(Context context, String url) {
        try {
            Bitmap bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .centerCrop()
                    .override(500, 500)
                    .into(500, 500)
                    .get();
            return bitmap;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对占位图做圆角处理
     *
     * @param context       上下
     * @param placeholderId id
     * @param radius        圆角
     * @return
     */
    private static RequestBuilder<Drawable> loadTransform(Context context, @DrawableRes int placeholderId, float radius) {
        RequestOptions options = RequestOptions.bitmapTransform(
                new MultiTransformation<>(
                        new CenterCrop(),
                        new RoundedCorners(SizeUtils.dp2px(context, radius))
                ));
        return Glide.with(context)
                .load(placeholderId)
                .apply(options);
    }

    /***
     * 获取图片的缓存地址
     * @param url
     * @return
     */
    public static String getImgPathFromCache(Context context, String url) {
        FutureTarget<File> future = Glide.with(context)
                .load(url)
                .downloadOnly(100, 100);
        try {
            File cacheFile = future.get();
            String path = cacheFile.getAbsolutePath();
            return path;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
