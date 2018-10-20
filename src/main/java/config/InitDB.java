package config;

import entity.User;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class InitDB {

    private final static Logger log = LogManager.getLogger(InitDB.class);

    private final SessionFactory sessionFactory;
    private final String tutorName;
    private final String tutorPassword;

    @PostConstruct
    public void init() {
        User user = new User(tutorName, tutorPassword, true);
        user.setId(1L);
        Session session = sessionFactory.openSession();
        session.getTransaction().begin();
        session.merge(user);
        session.getTransaction().commit();
        session.close();
        log.info("Database successfully initialized");
    }
}
