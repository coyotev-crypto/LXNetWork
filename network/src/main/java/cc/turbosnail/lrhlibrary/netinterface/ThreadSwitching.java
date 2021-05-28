package cc.turbosnail.lrhlibrary.netinterface;

import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;

/**
 * @ProjectName LXNetHttp
 * @Package cc.turbosnail.lrhlibrary.netinterface
 * @Describe 描述
 * @Author wolf king
 * @editor 修改人
 * @CreateDate 2021/5/28
 * @UpdateDate 2021/5/28 更新时间
 */
public interface ThreadSwitching {
    /**
     * 取消网络请求
     */
    void cancelRequest(Observer... observer);

    <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer);
}
