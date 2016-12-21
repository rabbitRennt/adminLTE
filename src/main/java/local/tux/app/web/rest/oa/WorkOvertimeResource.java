package local.tux.app.web.rest.oa;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import local.tux.app.domain.oa.WorkOvertime;
import local.tux.app.service.oa.WorkOvertimeService;
import local.tux.app.web.rest.dto.oa.WorkOvertimeDTO;
import local.tux.app.web.rest.util.PaginationUtil;

/**
 * REST controller for managing users.
 *
 * <p>
 * This class accesses the User entity, and needs to fetch its collection of
 * authorities.
 * </p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship
 * between User and Authority, and send everything to the client side: there
 * would be no DTO, a lot less code, and an outer-join which would be good for
 * performance.
 * </p>
 * <p>
 * We use a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities,
 * because people will quite often do relationships with the user, and we don't
 * want them to get the authorities all the time for nothing (for performance
 * reasons). This is the #1 goal: we should not impact our users' application
 * because of this use-case.</li>
 * <li>Not having an outer join causes n+1 requests to the database. This is not
 * a real issue as we have by default a second-level cache. This means on the
 * first HTTP call we do the n+1 requests, but then all authorities come from
 * the cache, so in fact it's much better than doing an outer join (which will
 * get lots of data from the database, for each HTTP call).</li>
 * <li>As this manages users, for security reasons, we'd rather have a DTO
 * layer.</li>
 * </p>
 * <p>
 * Another option would be to have a specific JPA entity graph to handle this
 * case.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class WorkOvertimeResource {

	private final Logger log = LoggerFactory.getLogger(WorkOvertimeResource.class);

	@Inject
	private WorkOvertimeService workOvertimeService;

	@RequestMapping(value = "/workOvertime", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> createWorkOvertime(@RequestBody WorkOvertimeDTO workOvertimeDTO,
			HttpServletRequest request) throws URISyntaxException {

		return Optional.ofNullable(workOvertimeService.create(workOvertimeDTO))
				.map(user -> new ResponseEntity<String>(HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@RequestMapping(value = "/workOvertime", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<WorkOvertime>> getAllWorkOvertime(Pageable pageable) throws URISyntaxException {
		Page<WorkOvertime> page = workOvertimeService.findAll(pageable); 
		List<WorkOvertime> workOvertime = page.getContent();
		log.debug(" ");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workOvertime");
		return new ResponseEntity<>(workOvertime, headers, HttpStatus.OK);
	}
}
