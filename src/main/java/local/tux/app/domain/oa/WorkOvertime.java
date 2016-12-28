package local.tux.app.domain.oa;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(name = "oa_work_overtime")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "overtime")
public class WorkOvertime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1995067848374761711L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //

	@Column(name = "CREATED_BY", length = 64)
	private String createdBy; // '加班早请人',

    @CreatedDate
    @NotNull
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
    private ZonedDateTime createdDate = ZonedDateTime.now();

	@NotNull
	@Column(name = "STATUS")
	private Integer status; // '状态@申请中:通过:驳回',

	@NotNull
	@Column(name = "START_DATE", length = 19)
	private Date startDate; // '开始时间',

	@NotNull
	@Column(name = "END_DATE", length = 19)
	private Date endDate; // '结束时间',

	@NotNull
	@Column(name = "TIME_LENGTH", nullable = false)
	private Integer timeLength; // '时长(H)',

	@Column(name = "REMARK", length = 128)
	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public ZonedDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(ZonedDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}
