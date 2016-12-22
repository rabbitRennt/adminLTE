package local.tux.app.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import local.tux.app.domain.User;
import local.tux.app.domain.oa.WorkOvertime;
import local.tux.app.web.rest.dto.oa.WorkOvertimeDTO;

/**
 * Spring Data JPA repository for the WorkOvertime entity.
 */
public interface WorkOvertimeRepository extends JpaRepository<WorkOvertime, Long> {

	//void findAll(Specification<WorkOvertime> specification, Pageable page);

	Page<WorkOvertime> findAll(Specification<WorkOvertimeDTO> spec, Pageable pageable); // 分页按条件查询
	
	Optional<WorkOvertime> findOneById(Long userId);
<<<<<<< HEAD
=======
	
	

>>>>>>> 765facbabe17cf4c94d017da300aef7ef3dbe7ce

}
