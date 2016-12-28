package local.tux.app.service.oa;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import local.tux.app.domain.User;
import local.tux.app.domain.oa.TakeVacation;
import local.tux.app.repository.UserRepository;
import local.tux.app.repository.oa.TakeVacationRepository;
import local.tux.app.web.rest.dto.oa.TakeVacationDTO;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class TakeVacationService {

	@Inject
	private TakeVacationRepository takeVacationRepository;

	@Inject
	private UserRepository userRepository;

	private final Logger log = LoggerFactory.getLogger(TakeVacationService.class);

	public TakeVacation create(TakeVacation takeVacation) {

		TakeVacation oldTakeVacation = findTakeVacationlByUserId(takeVacation.getUserId());

		if (oldTakeVacation == null) {
			takeVacationRepository.save(takeVacation);
		} else {

			log.error("Created Information for takeVacation: error", takeVacation);
			throw new RuntimeException("create object error ,object exists..");
		}

		log.debug("Created Information for takeVacation: {}", takeVacation);
		return takeVacation;
	}

	@Transactional(readOnly = true)
	public TakeVacation findTakeVacationlByUserId(Long userId) {
		TakeVacation takeVacation = takeVacationRepository.findOne(userId);
		return takeVacation;
	}

	@Transactional(readOnly = true)
	public void updateTakeVacationById(TakeVacationDTO dto) {
		Optional<User> user = userRepository.findOneByLogin(dto.getUserName());
		TakeVacation t = takeVacationRepository.findOneByUserId(user.get().getId());
		int row=0;
		if (t == null) {
			throw new RuntimeException("no userabled time length");
		} else {
			if (dto.getTVHourLength() != null)
				row=takeVacationRepository.update(-dto.getTVHourLength(), 0, dto.getTVHourLength(), t.getUserId());
			else if (dto.getWOHourLength() != null) {
				row=takeVacationRepository.update(dto.getWOHourLength(), dto.getWOHourLength(), 0, t.getUserId());
			}
			if (row==0){
				throw new RuntimeException(" this user data error..");
			}
		}

	}

}
