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
	public void updateById4TakeVacation(String username, Integer timeLength) {
		Optional<User> user = userRepository.findOneByLogin(username);
		TakeVacation t = takeVacationRepository.findOneByUserId(user.get().getId());
		int row = takeVacationRepository.update(-timeLength, 0, timeLength, t.getUserId());
		if (row == 0)
			throw new RuntimeException(" this user data error..");

	}

	@Transactional(readOnly = true)
	public void updateById4WorkOvertime(String username, Integer timeLength) {
		Optional<User> user = userRepository.findOneByLogin(username);
		TakeVacation t = takeVacationRepository.findOneByUserId(user.get().getId());
		if (t == null) {
			t = new TakeVacation();
			t.setTotalHour(timeLength);
			t.setUsable(timeLength);
			t.setUserId(user.get().getId());
			t.setUsed(0);
			takeVacationRepository.save(t);
		} else {
			if (t.getUsable() < timeLength) {
				throw new RuntimeException("no userable time..");
			}
			int row = takeVacationRepository.update(timeLength, timeLength, 0, t.getUserId());
			if (row == 0)
				throw new RuntimeException(" this user data error..");
		}

	}

	public int checkUsableTimeByUserNmae(String username) {
		Optional<User> user = userRepository.findOneByLogin(username);
		TakeVacation t = takeVacationRepository.findOneByUserId(user.get().getId());
		if (t == null)
			return 0;
		return t.getUsable();
	}

}
