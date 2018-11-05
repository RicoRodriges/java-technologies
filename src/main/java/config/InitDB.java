package config;

import dao.UniversityDAO;
import dao.UserDAO;
import entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@RequiredArgsConstructor
public class InitDB {

    private static boolean isInit = false;

    private final UserDAO userDAO;
    private final UniversityDAO universityDAO;
    private final String tutorName;
    private final String tutorPassword;

    @EventListener
    @Transactional
    public void init(ContextRefreshedEvent event) {
        if (!isInit) {
            User user = new User(tutorName, tutorPassword, true, null);
            user.setId(1L);
            userDAO.save(user);

            GroupEntity g3373 = new GroupEntity();
            g3373.setName("3373");

            GroupEntity g3374 = new GroupEntity();
            g3374.setName("3374");

            Department is = new Department();
            is.setName("ИС");
            is.setGroups(new HashSet<>(Arrays.asList(g3373, g3374)));

            Faculty fkti = new Faculty();
            fkti.setName("ФКТИ");
            fkti.setDepartments(new HashSet<>(Collections.singletonList(is)));

            University leti = new University();
            leti.setName("ЛЭТИ");
            leti.setFaculties(new HashSet<>(Collections.singletonList(fkti)));

            fkti.setUniversity(leti);

            is.setFaculty(fkti);

            g3373.setDepartment(is);
            g3374.setDepartment(is);

            University itmo = new University();
            itmo.setName("ИТМО");

            universityDAO.saveAll(Arrays.asList(leti, itmo));
            isInit = true;
        }
    }
}
