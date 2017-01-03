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
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import local.tux.app.domain.util.CustomJsonDateDeserializer;


@Entity
@Table(name = "oa_take_vacation_detail")
@Document(indexName = "takeVacationDetail")
public class TakeVacationDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -723851336920135480L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //
	
	@Column(name = "AUDIT_ID", nullable = false)
	private String auditId; 

	@NotNull
	@Column(name = "CREATED_BY", length = 100, unique = true)
	private String createdBy; // '请假人',

	@CreatedDate
	@NotNull
	@Column(name = "CREATED_DATE", nullable = false, length = 19)
	private ZonedDateTime createdDate = ZonedDateTime.now();
	
	@NotNull
	@Column(name = "STATUS")
	private Integer status; // '状态@申请中:通过:驳回',

	@NotNull
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "START_DATE", nullable = false)
	private Date startDate; // '开始时间',

	
	@NotNull
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "END_DATE", nullable = false)
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
	
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
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
