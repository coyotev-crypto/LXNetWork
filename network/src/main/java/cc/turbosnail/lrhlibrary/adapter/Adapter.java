package cc.turbosnail.lrhlibrary.adapter;

import cc.turbosnail.lrhlibrary.netinterface.IntranetBypassInterface;
import cc.turbosnail.lrhlibrary.netinterface.RetrofitInterface;
import cc.turbosnail.lrhlibrary.netinterface.ServiceInterface;
import cc.turbosnail.lrhlibrary.netinterface.ThreadSwitching;

/**
 * @ProjectName LXNetHttp
 * @Package cc.turbosnail.lrhlibrary.netinterface
 * @Describe 适配器
 * @Author wolf king
 * @editor 修改人
 * @CreateDate 2021/5/28
 * @UpdateDate 2021/5/28 更新时间
 */
public interface Adapter extends IntranetBypassInterface, RetrofitInterface, ServiceInterface,
        ThreadSwitching {
}
