package controllers;

import config.security.SpringUser;
import dao.*;
import dto.UserDto;
import entity.Department;
import entity.Faculty;
import entity.GroupEntity;
import entity.University;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.api.UserService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class Registration {

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String REPASSWORD = "passwordRepeat";
    private static final String GROUP = "groups";
    private static final String UNIVERSITY = "univer";
    private static final String REGISTRATION_JSP = "registration";
    private static final String LOGIN_JSP = "login";
    private static final String FLAG = "flag";
    private static final String LOCALE = "locale";

    private final UserService userService;
    private final GroupsDAO groupsDAO;
    private final UniversityDAO universityDAO;
    private final FacultyDAO facultyDAO;
    private final DepartmentDAO departmentDAO;

    @PostMapping("/registration")
    protected String doPost(@RequestParam(USER) String userNameCred,
                            @RequestParam(PASSWORD) String userPassCred,
                            @RequestParam(REPASSWORD) String userRePassCred,
                            @RequestParam(GROUP) Long groupId,
                            HttpSession session,
                            Model model) {
        String language = (String) session.getAttribute(LOCALE);
        if (language == null) {
            language = "en";
        }
        ResourceBundle r = ResourceBundle.getBundle("internationalization", new Locale(language));

        if (isEmptyFields(userNameCred, userPassCred, userRePassCred)) {
            model.addAttribute(FLAG, r.getString("registrationservlet.fill"));
            return REGISTRATION_JSP;
        } else {
            Optional<GroupEntity> optional = groupsDAO.findById(groupId);
            if (optional.isPresent()) {
                UserDto user = new UserDto(null, userNameCred, userPassCred, false, optional.get());
                if (userService.isAlreadyExists(user)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.exists"));
                    return REGISTRATION_JSP;
                } else if (!userPassCred.equals(userRePassCred)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.wrong"));
                    return REGISTRATION_JSP;
                } else {
                    userService.registerUser(user);
                    return LOGIN_JSP;
                }
            } else {
                model.addAttribute(FLAG, "Bad group");
                return REGISTRATION_JSP;
            }
        }
    }

    @GetMapping("/registration")
    protected String doGet() {
        return REGISTRATION_JSP;
    }

    @GetMapping("/registerTutor")
    protected String doGetTutorPage(Model model) {
        model.addAttribute("universities", universityDAO.findAll());
        return "regTutor";
    }

    @PostMapping("/registerTutor")
    protected String doGetTutorProcess(@RequestParam(USER) String userNameCred,
                                       @RequestParam(PASSWORD) String userPassCred,
                                       @RequestParam(UNIVERSITY) Long univerId) {
        University univer = universityDAO.findById(univerId).get();
        userService.registerUser(new UserDto(null, userNameCred, userPassCred, true, univer.getFaculties()
                .iterator().next().getDepartments()
                .iterator().next().getGroups()
                .iterator().next()));
        return "redirect:/";
    }

    @GetMapping("/registerUniver")
    protected String doGetUniverPage(Model model) {
        model.addAttribute("universities", universityDAO.findAll());
        return "regUniver";
    }

    @PostMapping("/registerUniver")
    protected String doGetUniverProcess(@RequestParam("name") String univerName) {
        University university = new University();
        university.setName(univerName);
        universityDAO.save(university);
        return "redirect:/";
    }

    @GetMapping("/registerGroups")
    protected String doGetGroupPage(Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        boolean need = user.getGroupEntity() == null;
        if (!need) {
            model.addAttribute("needUniver", false);
            model.addAttribute("faculties", facultyDAO.findAll());
        } else {
            model.addAttribute("needUniver", true);
            model.addAttribute("universities", universityDAO.findAll());
        }
        return "regGroups";
    }

    @PostMapping("/registerGroups")
    protected String doGetGroupProcess(@RequestParam(value = "univer", required = false) String univer,
                                       @RequestParam("fac") String fac,
                                       @RequestParam("dep") String dep,
                                       @RequestParam("group") String gro,
                                       Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        University university;
        if (user.getGroupEntity() != null)
            university = universityDAO.findById(user.getGroupEntity().getDepartment().getFaculty().getUniversity().getId()).get();
        else {
            university = universityDAO.findByName(univer);
        }

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(gro);

        Department d = departmentDAO.findByName(dep);
        if (d == null) {
            d = new Department();
            d.setName(dep);
            d.setGroups(new HashSet<>());
        }

        Faculty f = facultyDAO.findByName(fac);
        if (f == null) {
            f = new Faculty();
            f.setName(fac);
            f.setUniversity(university);
            f.setDepartments(new HashSet<>());
        }

        groupEntity.setDepartment(d);

        d.setFaculty(f);
        d.getGroups().add(groupEntity);

        f.getDepartments().add(d);

        university.getFaculties().add(f);

        universityDAO.save(university);

        return doGetGroupPage(model);
    }

    private boolean isEmptyFields(String userNameCred, String userPassCred, String userRePassCred) {
        return userNameCred.isEmpty() || userPassCred.isEmpty() || userRePassCred.isEmpty();
    }
}
