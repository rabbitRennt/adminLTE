package local.tux.app.service.oa;


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

import local.tux.app.domain.oa.TakeVacationDetail;
import local.tux.app.repository.TakeVacationDetailRepository;
import local.tux.app.security.SecurityUtils;
import local.tux.app.web.rest.dto.oa.TakeVacationDetailDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class TakeVacationDetailService {

	@Inject
	private TakeVacationDetailRepository takeVacationDetailRepository;

	private final Logger log = LoggerFactory.getLogger(TakeVacationDetailService.class);

	public TakeVacationDetail create(TakeVacationDetailDTO takeVacationDetailDTO) {

		TakeVacationDetail takeVacationDetail = new TakeVacationDetail();
		takeVacationDetail.setAuditId("1");
		takeVacationDetail.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		takeVacationDetail.setEndDate(takeVacationDetailDTO.getEndDate());
		takeVacationDetail.setStartDate(takeVacationDetailDTO.getStartDate());
		takeVacationDetail.setStatus(0);
		takeVacationDetail.setTimeLength(takeVacationDetailDTO.getTimeLength());
		takeVacationDetail.setRemark(takeVacationDetailDTO.getRemark());
		
		takeVacationDetailRepository.save(takeVacationDetail);
		log.debug("Created Information for takeVacationDetail: {}", takeVacationDetail);
		return takeVacationDetail;
	}

	public TakeVacationDetail modify(TakeVacationDetailDTO takeVacationDetailDTO) {

		TakeVacationDetail takeVacationDetail = new TakeVacationDetail();
		takeVacationDetail.setCreatedBy(SecurityUtils.getCurrentUser().getUsername());
		takeVacationDetail.setEndDate(takeVacationDetailDTO.getEndDate());
		takeVacationDetail.setStartDate(takeVacationDetailDTO.getStartDate());
		takeVacationDetail.setStatus(takeVacationDetailDTO.getStatus());
		takeVacationDetail.setTimeLength(takeVacationDetailDTO.getTimeLength());
		log.debug("Modify Information for takeVacationDetail: {}", takeVacationDetail);
		return takeVacationDetail;
	}

	public Page<TakeVacationDetail> page(TakeVacationDetailDTO takeVacationDetailDTO,Pageable page) {

		return takeVacationDetailRepository.findAll(new Specification<TakeVacationDetailDTO>() {
			@Override
			public Predicate toPredicate(Root<TakeVacationDetailDTO> root, javax.persistence.criteria.CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> namePath = root.get("name");
				Path<String> nicknamePath = root.get("nickname");
				query.where(cb.like(namePath, "%李%"), cb.like(nicknamePath, "%王%")); // 这里可以设置任意条查询条件

				return null;
			}

		}, page);


	}

	public Page<TakeVacationDetail> findAll(Pageable pageable) {

		return takeVacationDetailRepository.findAll(pageable);
	}
	
	  @Transactional(readOnly = true)
    public TakeVacationDetail findTakeVacationDetailById(Long id) {
		  TakeVacationDetail takeVacationDetail = takeVacationDetailRepository.findOne(id);
//		  workOvertime.getAuthorities().size(); // eagerly load the association
        return takeVacationDetail;
    }
	  @Transactional(readOnly = true)
	public TakeVacationDetail updateTakeVacationDetailById(TakeVacationDetailDTO takeVacationDetailDTO) {
		  TakeVacationDetail  takeVacationDetail= takeVacationDetailRepository.findOne(takeVacationDetailDTO.getId());
		  takeVacationDetail.setStartDate(takeVacationDetailDTO.getStartDate());
		  takeVacationDetail.setEndDate(takeVacationDetailDTO.getEndDate());
		  takeVacationDetail.setRemark(takeVacationDetailDTO.getRemark());
		  takeVacationDetailRepository.save(takeVacationDetail);
//		  takeVacationDetailRepository.saveAndFlush(takeVacationDetail);
		return takeVacationDetail;
	}
	  
	  public void deleteTakeVacationDetail(Long id) {
	        takeVacationDetailRepository.findOneById(id).ifPresent(u -> {
	        	takeVacationDetailRepository.delete(u);
	            log.debug("Deleted User: {}", u);
	        });
	    }

}
