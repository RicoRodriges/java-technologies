package config;

import dao.UniversityDAO;
import dao.UserDAO;
import entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

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
            is.setGroups(Arrays.asList(g3373, g3374));

            Department apu = new Department();
            apu.setName("АПУ");

            Faculty fkti = new Faculty();
            fkti.setName("ФКТИ");
            fkti.setDepartments(Arrays.asList(is, apu));

            Faculty fel = new Faculty();
            fel.setName("ФЭЛ");

            University leti = new University();
            leti.setName("ЛЭТИ");
            leti.setFaculties(Arrays.asList(fkti, fel));

            fel.setUniversity(leti);
            fkti.setUniversity(leti);

            is.setFaculty(fkti);
            apu.setFaculty(fkti);

            g3373.setDepartment(is);
            g3374.setDepartment(is);

            University itmo = new University();
            itmo.setName("ИТМО");

            universityDAO.saveAll(Arrays.asList(leti, itmo));
            isInit = true;
        }
    }
}
