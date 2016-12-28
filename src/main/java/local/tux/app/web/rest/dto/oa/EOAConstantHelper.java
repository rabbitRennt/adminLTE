package local.tux.app.web.rest.dto.oa;

/**
 *  ok模块常量帮助类
 * @author fwg
 *
 */
public abstract interface EOAConstantHelper {

	/**
	 * 调休/加班 状态
	 * @author Administrator
	 *
	 */
	public enum EStatus{
		
		APPLY,//申请中
		PASS,
		DISABLED,
		CANCLE
		
	}
}
