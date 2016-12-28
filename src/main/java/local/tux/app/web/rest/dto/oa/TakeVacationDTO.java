package local.tux.app.web.rest.dto.oa;

public class TakeVacationDTO {
	
	/**
	 * 调休人
	 */
	public String userName;
	
	//加班时长
	public Integer WOHourLength;
	//调休时间
	public Integer TVHourLength;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getWOHourLength() {
		return WOHourLength;
	}
	public void setWOHourLength(Integer wOHourLength) {
		WOHourLength = wOHourLength;
	}
	public Integer getTVHourLength() {
		return TVHourLength;
	}
	public void setTVHourLength(Integer tVHourLength) {
		TVHourLength = tVHourLength;
	}
	
	
}
