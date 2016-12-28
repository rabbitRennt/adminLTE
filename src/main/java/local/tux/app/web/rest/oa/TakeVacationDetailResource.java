package local.tux.app.web.rest.oa;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import local.tux.app.domain.Authority;
import local.tux.app.domain.User;
import local.tux.app.domain.oa.TakeVacationDetail;
import local.tux.app.domain.oa.WorkOvertime;
import local.tux.app.repository.AuthorityRepository;
import local.tux.app.repository.TakeVacationDetailRepository;
import local.tux.app.repository.UserRepository;
import local.tux.app.repository.search.UserSearchRepository;
import local.tux.app.security.AuthoritiesConstants;
import local.tux.app.service.MailService;
import local.tux.app.service.UserService;
import local.tux.app.service.oa.TakeVacationDetailService;
import local.tux.app.web.rest.dto.ManagedUserDTO;
import local.tux.app.web.rest.dto.oa.TakeVacationDetailDTO;
import local.tux.app.web.rest.util.HeaderUtil;
import local.tux.app.web.rest.util.PaginationUtil;

/**
 * REST controller for managing users.
 *
 * <p>This class accesses the User entity, and needs to fetch its collection of authorities.</p>
 * <p>
 * For a normal use-case, it would be better to have an eager relationship between User and Authority,
 * and send everything to the client side: there would be no DTO, a lot less code, and an outer-join
 * which would be good for performance.
 * </p>
 * <p>
 * We use a DTO for 3 reasons:
 * <ul>
 * <li>We want to keep a lazy association between the user and the authorities, because people will
 * quite often do relationships with the user, and we don't want them to get the authorities all
 * the time for nothing (for performance reasons). This is the #1 goal: we should not impact our users'
 * application because of this use-case.</li>
 * <li> Not having an outer join causes n+1 requests to the database. This is not a real issue as
 * we have by default a second-level cache. This means on the first HTTP call we do the n+1 requests,
 * but then all authorities come from the cache, so in fact it's much better than doing an outer join
 * (which will get lots of data from the database, for each HTTP call).</li>
 * <li> As this manages users, for security reasons, we'd rather have a DTO layer.</li>
 * </p>
 * <p>Another option would be to have a specific JPA entity graph to handle this case.</p>
 */

@RestController
@RequestMapping("/api")
public class TakeVacationDetailResource {

    private final Logger log = LoggerFactory.getLogger(TakeVacationDetailResource.class);

    @Inject
    private TakeVacationDetailService takeVacationDetailService;

    
    @Inject
    private TakeVacationDetailRepository takeVacationDetailRepository;
    

    /**
     * POST  /users -> Creates a new user.
     * <p>
     * Creates a new user if the login and email are not already used, and sends an
     * mail with an activation link.
     * The user needs to be activated on creation.
     * </p>
     */
    @RequestMapping(value = "/takeVacationDetail",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<?> createTakeVacationDetail(@RequestBody TakeVacationDetailDTO takeVacationDetailDTO, HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to save takeVacationDetail : {}", takeVacationDetailDTO);
        TakeVacationDetail takeVacationDetail = takeVacationDetailService.create(takeVacationDetailDTO);
        String baseUrl = request.getScheme() + // "http"
        "://" +                                // "://"
        request.getServerName() +              // "myhost"
        ":" +                                  // ":"
        request.getServerPort() +              // "80"
        request.getContextPath();     
        
        return ResponseEntity.created(new URI("/api/workOvertime/" + takeVacationDetail.getId()))
                .headers(HeaderUtil.createAlert( "oa-workOvertime.created", takeVacationDetail.getId().toString()))
                .body(takeVacationDetail);
    }

    /**
     * PUT  /users -> Updates an existing User.
     */
    @RequestMapping(value = "/takeVacationDetail",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<TakeVacationDetail> updateTakeVacationDetail(@RequestBody TakeVacationDetailDTO takeVacationDetailDTO) throws URISyntaxException {
        log.debug("REST request to update TakeVacationDetail : {}", takeVacationDetailDTO);
        
        TakeVacationDetail takeVacationDetail= takeVacationDetailService.updateTakeVacationDetailById(takeVacationDetailDTO);
        
        return  ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("user-management.updated", takeVacationDetail.getId().toString()))
                .body(takeVacationDetail);

    }

    /**
     * GET  /users -> get all users.
     */
    @RequestMapping(value = "/takeVacationDetail",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public ResponseEntity<List<TakeVacationDetail>> getAllTakeVacationDetail(Pageable pageable)
        throws URISyntaxException {
    	Page<TakeVacationDetail> page = takeVacationDetailService.findAll(pageable); 
		List<TakeVacationDetail> takeVacationDetail = page.getContent();
		log.debug(" ");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workOvertime");
		return new ResponseEntity<>(takeVacationDetail, headers, HttpStatus.OK);
    }

    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/takeVacationDetail/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TakeVacationDetail> getTakeVacationDetail(@PathVariable long id) {
        log.debug("REST request to get TakeVacationDetail : {}", id);
        return Optional.ofNullable(takeVacationDetailService.findTakeVacationDetailById(id))
                .map(takeVacationDetail -> new ResponseEntity<>(takeVacationDetail, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        
    }
    /**
     * DELETE  USER :login -> delete the "login" User.
     */
    
    @RequestMapping(value = "/takeVacationDetail/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Void> deleteTakeVacationDetail(@PathVariable Long id) {
        log.debug("REST request to delete TakeVacationDetail: {}", id);
        takeVacationDetailService.deleteTakeVacationDetail(id);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert( "takeVacationDetail.deleted", id.toString())).build();
    }

   
}
