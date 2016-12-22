package local.tux.app.web.rest.oa;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import local.tux.app.domain.oa.WorkOvertime;
<<<<<<< HEAD
import local.tux.app.repository.UserRepository;
import local.tux.app.repository.WorkOvertimeRepository;
import local.tux.app.security.AuthoritiesConstants;
import local.tux.app.service.oa.WorkOvertimeService;
import local.tux.app.web.rest.dto.ManagedUserDTO;
import local.tux.app.web.rest.dto.UserDTO;
=======
import local.tux.app.security.AuthoritiesConstants;
import local.tux.app.service.oa.WorkOvertimeService;
import local.tux.app.web.rest.dto.ManagedUserDTO;
>>>>>>> 765facbabe17cf4c94d017da300aef7ef3dbe7ce
import local.tux.app.web.rest.dto.oa.WorkOvertimeDTO;
import local.tux.app.web.rest.util.HeaderUtil;
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
	@Inject
    private WorkOvertimeRepository workOvertimeRepository;
	
	
	@RequestMapping(value = "/workOvertime",
			method = RequestMethod.POST, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity<?> createWorkOvertime(@RequestBody WorkOvertimeDTO workOvertimeDTO,
			HttpServletRequest request) throws URISyntaxException {
<<<<<<< HEAD
		 WorkOvertime newWorkOvertime = workOvertimeService.create(workOvertimeDTO);
         String baseUrl = request.getScheme() + // "http"
         "://" +                                // "://"
         request.getServerName() +              // "myhost"
         ":" +                                  // ":"
         request.getServerPort() +              // "80"
         request.getContextPath();     
         
         return ResponseEntity.created(new URI("/api/workOvertime/" + newWorkOvertime.getId()))
                 .headers(HeaderUtil.createAlert( "oa-workOvertime.created", newWorkOvertime.getId().toString()))
                 .body(newWorkOvertime);
       
=======
		log.debug("REST request to save WorkOvertime : {}", workOvertimeDTO);
		return Optional.ofNullable(workOvertimeService.create(workOvertimeDTO))
				.map(user -> new ResponseEntity<String>(HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
>>>>>>> 765facbabe17cf4c94d017da300aef7ef3dbe7ce
	}
	
	/**
     * PUT  /users -> Updates an existing User.
     */
    @RequestMapping(value = "/workOvertime",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<WorkOvertime> updateWorkOvertimeById(@RequestBody WorkOvertimeDTO workOvertimeDTO) throws URISyntaxException {
        log.debug("REST request to update WorkOvertime : {}", workOvertimeDTO);
//        Optional<User> existingUser = userRepository.findOneByEmail(managedUserDTO.getEmail());
        WorkOvertime workOvertime= workOvertimeService.updateWorkOvertimeById(workOvertimeDTO);
            
        return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("user-management.updated", workOvertime.getId().toString()))
                .body(workOvertime);
                
               
        //return new ResponseEntity<>(HttpStatus.OK)count.; 
           /*     return ResponseEntity.ok()
                .headers(HeaderUtil.createAlert("user-management.updated", managedUserDTO.getLogin()))
                .body(new ManagedUserDTO(userRepository
                    .findOne(managedUserDTO.getId())));*/
    }
    
	/**
     * GET  /workOvertime -> get all workOvertime.
     */
	@RequestMapping(value = "/workOvertime", 
			method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	@Transactional(readOnly = true)
	public ResponseEntity<List<WorkOvertime>> getAllWorkOvertime(Pageable pageable) throws URISyntaxException {
		Page<WorkOvertime> page = workOvertimeService.findAll(pageable); 
		List<WorkOvertime> workOvertime = page.getContent();
		log.debug(" ");
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/workOvertime");
		return new ResponseEntity<>(workOvertime, headers, HttpStatus.OK);
	}
<<<<<<< HEAD
	/**
     * GET  /workOvertime/:id -> get the "id" workOvertime.
     */
    @RequestMapping(value = "/workOvertime/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<WorkOvertime> getWorkOvertime(@PathVariable long id) {
        log.debug("REST request to get workOvertime : {}", id);
        return Optional.ofNullable(workOvertimeService.findWorkOvertimeById(id))
                .map(workOvertime -> new ResponseEntity<>(workOvertime, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        
    }
    /**
     * POST  /workOvertime -> Creates a new workOvertime.
     * <p>
     * Creates a new workOvertime if the login and email are not already used, and sends an
     * mail with an activation link.
     * The workOvertime needs to be activated on creation.
     * </p>
     */
=======
	
	
	 @RequestMapping(value = "/workOvertime/{id}",
		method = RequestMethod.GET,
		produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    public ResponseEntity<WorkOvertime> getWorkOvertime(@PathVariable long id) {
	        log.debug("REST request to get workOvertime : {}", id);
	        /*return workOvertimeService.findWorkOvertimeById(id)
	                .map(WorkOvertime::new)
	                .map(WorkOvertime -> new ResponseEntity<>(WorkOvertime, HttpStatus.OK))
	                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
	                
	        return Optional.ofNullable(workOvertimeService.findWorkOvertimeById(id))
	                .map(workOvertime -> new ResponseEntity<>(workOvertime, HttpStatus.OK))
	                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
	        
	    }
	 
	    
	    /**
	     * PUT  /users -> Updates an existing User.
	     */
	  /* @RequestMapping(value = "/workOvertime",
	        method = RequestMethod.PUT,
	        produces = MediaType.APPLICATION_JSON_VALUE)
	    @Timed
	    @Transactional
	    @Secured(AuthoritiesConstants.ADMIN)
	    public ResponseEntity<WorkOvertime> updateUser(@RequestBody WorkOvertime workOvertime) throws URISyntaxException {
	        log.debug("REST request to update User : {}", workOvertime);
	        Optional<User> existingUser = userRepository.findOneByEmail(workOvertime.getEmail());
	        
	        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDTO.getId()))) {
	            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "emailexists", "E-mail already in use")).body(null);
	        }
	        existingUser = userRepository.findOneByLogin(managedUserDTO.getLogin());
	        if (existingUser.isPresent() && (!existingUser.get().getId().equals(managedUserDTO.getId()))) {
	            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user-management", "userexists", "Login already in use")).body(null);
	        }
	        return workOvertimeService
	            .findWorkOvertimeById(workOvertime.getId())
	            .map(user -> {
	                user.setLogin(managedUserDTO.getLogin());
	                user.setFirstName(managedUserDTO.getFirstName());
	                user.setLastName(managedUserDTO.getLastName());
	                user.setEmail(managedUserDTO.getEmail());
	                user.setActivated(managedUserDTO.isActivated());
	                user.setLangKey(managedUserDTO.getLangKey());
	                Set<Authority> authorities = user.getAuthorities();
	                authorities.clear();
	                managedUserDTO.getAuthorities().stream().forEach(
	                    authority -> authorities.add(authorityRepository.findOne(authority))
	                );
	                return ResponseEntity.ok()
	                    .headers(HeaderUtil.createAlert("user-management.updated", managedUserDTO.getLogin()))
	                    .body(new ManagedUserDTO(userRepository
	                        .findOne(managedUserDTO.getId())));
	            })
	            .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));

	    }
*/
>>>>>>> 765facbabe17cf4c94d017da300aef7ef3dbe7ce
}
