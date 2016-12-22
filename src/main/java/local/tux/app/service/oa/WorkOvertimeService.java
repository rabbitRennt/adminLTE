package local.tux.app.service.oa;

import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.tux.app.domain.Authority;
import local.tux.app.domain.User;
import local.tux.app.domain.oa.WorkOvertime;
import local.tux.app.repository.WorkOvertimeRepository;
import local.tux.app.security.SecurityUtils;
import local.tux.app.service.util.RandomUtil;
import local.tux.app.web.rest.dto.ManagedUserDTO;
import local.tux.app.web.rest.dto.oa.WorkOvertimeDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class WorkOvertimeService {

	@Inject
	private WorkOvertimeRepository workOvertimeRepository;

	private final Logger log = LoggerFactory.getLogger(WorkOvertimeService.class);

	public WorkOvertime create(WorkOvertimeDTO workOvertimeDTO) {

		WorkOvertime workOvertime = new WorkOvertime();

		workOvertime.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setStatus(0);
		workOvertime.setTimeLength(workOvertimeDTO.getTimeLength());
<<<<<<< HEAD
=======
		workOvertime.setRemark(workOvertimeDTO.getRemark());
		//Date createDate = new Date(0L);
		//workOvertime.setCreatedDate(createDate);
		
>>>>>>> 765facbabe17cf4c94d017da300aef7ef3dbe7ce

		workOvertimeRepository.save(workOvertime);
		log.debug("Created Information for workOvertime: {}", workOvertime);
		return workOvertime;
	}

	public WorkOvertime modify(WorkOvertimeDTO workOvertimeDTO) {

		WorkOvertime workOvertime = new WorkOvertime();
		workOvertime.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setStatus(workOvertimeDTO.getStatus());
		workOvertime.setTimeLength(workOvertimeDTO.getTimeLength());
		log.debug("Modify Information for workOvertime: {}", workOvertime);
		return workOvertime;
	}

	public Page<WorkOvertime> page(WorkOvertimeDTO workOvertimeDTO, Pageable page) {

		return workOvertimeRepository.findAll(new Specification<WorkOvertimeDTO>() {
			@Override
			public Predicate toPredicate(Root<WorkOvertimeDTO> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> namePath = root.get("name");
				Path<String> nicknamePath = root.get("nickname");
				query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%")); // 这里可以设置任意条查询条件

				return null;
			}

		}, page);

	}

	public Page<WorkOvertime> findAll(Pageable pageable) {

		return workOvertimeRepository.findAll(pageable);
	}
	
	 @Transactional(readOnly = true)
	    public WorkOvertime findWorkOvertimeById(Long id) {
			  WorkOvertime workOvertime = workOvertimeRepository.findOne(id);
//			  workOvertime.getAuthorities().size(); // eagerly load the association
	        return workOvertime;
	    }

	@Transactional(readOnly = true)
	public WorkOvertime findWorkOvertimeById(Long id) {
		WorkOvertime workOvertime = workOvertimeRepository.findOne(id);
		// workOvertime.getAuthorities().size(); // eagerly load the association
		return workOvertime;
	}

	public WorkOvertime updateWorkOvertimeById(WorkOvertimeDTO workOvertimeDTO) {
		WorkOvertime workOvertime = workOvertimeRepository.findOne(workOvertimeDTO.getId());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		workOvertime.setRemark(workOvertimeDTO.getRemark());
		workOvertimeRepository.saveAndFlush(workOvertime);
		return workOvertime;
	}

}
