package local.tux.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import local.tux.app.domain.oa.TakeVacationDetail;
import local.tux.app.web.rest.dto.oa.TakeVacationDetailDTO;

/**
 * Spring Data JPA repository for the TakeVacationDetail entity.
 */
public interface TakeVacationDetailRepository extends JpaRepository<TakeVacationDetail, Long> {

	//void findAll(Specification<WorkOvertime> specification, Pageable page);

	Page<TakeVacationDetail> findAll(Specification<TakeVacationDetailDTO> spec, Pageable pageable); // 分页按条件查询
	
	Optional<TakeVacationDetail> findOneById(Long userId);

}
