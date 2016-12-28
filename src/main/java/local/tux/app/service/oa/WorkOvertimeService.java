package local.tux.app.service.oa;

import java.util.Optional;

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

import local.tux.app.domain.User;
import local.tux.app.domain.oa.TakeVacation;
import local.tux.app.domain.oa.WorkOvertime;
import local.tux.app.repository.WorkOvertimeRepository;
import local.tux.app.security.SecurityUtils;
import local.tux.app.web.rest.dto.oa.EOAConstantHelper.EStatus;
import local.tux.app.web.rest.dto.oa.WorkOvertimeDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class WorkOvertimeService {

	@Inject
	private WorkOvertimeRepository workOvertimeRepository;

	@Inject
	private TakeVacationService takeVacationService;

	private final Logger log = LoggerFactory.getLogger(WorkOvertimeService.class);

	public WorkOvertime create(WorkOvertimeDTO workOvertimeDTO) {

		WorkOvertime workOvertime = new WorkOvertime();

		workOvertime.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setStatus(0);
		Long temp = workOvertimeDTO.getEndDate().getTime() - workOvertimeDTO.getStartDate().getTime(); // 相差毫秒数
		Long hours = temp / 1000 / 3600;
		workOvertime.setTimeLength(hours.intValue());
		workOvertime.setRemark(workOvertimeDTO.getRemark());

		workOvertimeRepository.save(workOvertime);
		log.debug("Created Information for workOvertime: {}", workOvertime);
		return workOvertime;
	}

	public WorkOvertime modify(WorkOvertimeDTO workOvertimeDTO) {

		WorkOvertime workOvertime = new WorkOvertime();
		workOvertime.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setTimeLength(workOvertimeDTO.getTimeLength());

		log.debug("Modify Information for workOvertime: {}", workOvertime);
		workOvertimeRepository.save(workOvertime);
		workOvertimeRepository.flush();
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

	public WorkOvertime findWorkOvertimeById(Long id) {
		WorkOvertime workOvertime = workOvertimeRepository.findOne(id);
		return workOvertime;
	}

	public WorkOvertime updateWorkOvertimeById(WorkOvertimeDTO workOvertimeDTO) {
		WorkOvertime workOvertime = workOvertimeRepository.findOne(workOvertimeDTO.getId());
		workOvertime.setStartDate(workOvertimeDTO.getStartDate());
		workOvertime.setEndDate(workOvertimeDTO.getEndDate());
		Long temp = workOvertimeDTO.getEndDate().getTime() - workOvertimeDTO.getStartDate().getTime(); // 相差毫秒数
		Long hours = temp / 1000 / 3600; // 相差小时数
		workOvertime.setTimeLength(hours.intValue());
		workOvertime.setStatus(EStatus.APPLY.ordinal());
		workOvertime.setRemark(workOvertimeDTO.getRemark());
		
		workOvertimeRepository.save(workOvertime);
		// workOvertimeRepository.saveAndFlush(workOvertime);
		return workOvertime;
	}
	
	@Transactional
	public void deleteWorkOvertimeInformation(Long id) {
		workOvertimeRepository.findOneById(id).ifPresent(u -> {
			workOvertimeRepository.delete(u);
			log.debug("Deleted WorkOvertime: {}", u);
		});
	}

	public void verify(WorkOvertimeDTO workOvertimeDTO) {

		WorkOvertime t = workOvertimeRepository.findOne(workOvertimeDTO.getId());
		if (t.getStatus() != EStatus.APPLY.ordinal()) {
			return;
		}
		int row = workOvertimeRepository.updateStatusByKey(workOvertimeDTO.getStatus().ordinal(),
				workOvertimeDTO.getRemark() == null ? "" : workOvertimeDTO.getRemark(), workOvertimeDTO.getId());
		// 如果审核通过，更新汇总表
		if (row == 1 && workOvertimeDTO.getStatus() == EStatus.PASS) {

			takeVacationService.updateById4WorkOvertime(t.getCreatedBy(), t.getTimeLength());
		}

	}

}
