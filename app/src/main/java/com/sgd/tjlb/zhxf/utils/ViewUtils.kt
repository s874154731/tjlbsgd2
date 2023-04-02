package com.sgd.tjlb.zhxf.utils

import android.content.res.Resources

/**
 *
 * @ProjectName: tjlb
 * @Package: com.sgd.tjlb.zhxf.utils
 * @ClassName: ViewUtils
 * @Description: java类作用描述
 * @CreateDate: 2023/3/10/010 14:22
 * @UpdateUser: shi
 * @UpdateDate: 2023/3/10/010 14:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ViewUtils {
    companion object{
        @JvmStatic
        fun getStatusBarHeight(resources: Resources): Int {
            var result = 0
            val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }
}