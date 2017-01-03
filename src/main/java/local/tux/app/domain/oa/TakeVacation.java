package local.tux.app.domain.oa;

import java.io.Serializable;
import java.security.Timestamp;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "oa_take_vacation")
@Document(indexName = "takeVacation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TakeVacation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8181926604496656212L;
	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	private Long userId;

	@Column(name = "CREATED_BY", nullable = false, length = 19)
	private ZonedDateTime createdBy = ZonedDateTime.now();

	@Column(name = "TOTAL_HOUR", nullable = false)
	private Integer totalHour;

	@Column(name = "USABLE", nullable = false)
	private Integer usable;

	@Column(name = "USED", nullable = false)
	private Integer used;

	@Column(name = "LAST_MIDFY_DATE", nullable = false, length = 19)
	private ZonedDateTime lastMidfyDate	= ZonedDateTime.now();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public ZonedDateTime getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(ZonedDateTime createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getTotalHour() {
		return totalHour;
	}

	public void setTotalHour(Integer totalHour) {
		this.totalHour = totalHour;
	}

	public Integer getUsable() {
		return usable;
	}

	public void setUsable(Integer usable) {
		this.usable = usable;
	}

	public Integer getUsed() {
		return used;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public ZonedDateTime getLastMidfyDate() {
		return lastMidfyDate;
	}

	public void setLastMidfyDate(ZonedDateTime lastMidfyDate) {
		this.lastMidfyDate = lastMidfyDate;
	}

	

}
