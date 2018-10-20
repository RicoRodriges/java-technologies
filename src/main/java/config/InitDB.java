package config;

import dao.UserDAO;
import entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@RequiredArgsConstructor
public class InitDB {

    private final static Logger log = LogManager.getLogger(InitDB.class);

    private final UserDAO userDAO;
    private final String tutorName;
    private final String tutorPassword;

    @EventListener
    public void init(ContextRefreshedEvent event) {
        User user = new User(tutorName, tutorPassword, true);
        user.setId(1L);
        userDAO.save(user);
        log.info("Database successfully initialized");
    }
}
