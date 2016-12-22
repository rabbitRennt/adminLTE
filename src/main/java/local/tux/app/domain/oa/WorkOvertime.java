package local.tux.app.domain.oa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "oa_work_overtime")
@Document(indexName = "overtime")
public class WorkOvertime implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //

	@NotNull
	@Column(name = "CREATED_BY", length = 100, unique = true)
	private String createdBy; // '加班早请人',

	@CreatedDate
	@Column(name = "CREATED_DATE", nullable = false)
	private Long createdDate; // '加班申请时间',

	@NotNull
	private Integer status; // '状态@申请中:通过:驳回',

	@CreatedDate
	@NotNull
	@Column(name = "start_date", nullable = false)
	private Date startDate; // '开始时间',

	@CreatedDate
	@NotNull
	@Column(name = "end_date", nullable = false)
	private Date endDate; // '结束时间',

	@NotNull
	@Column(name = "time_length", nullable = false)
	private Integer timeLength; // '时长(H)',

	@CreatedBy
	@Column(name = "remark" ,nullable = false, length = 128, updatable = false)
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

	public Long getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Long createdDate) {
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
