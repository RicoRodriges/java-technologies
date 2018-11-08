package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.security.SpringUser;
import dao.DepartmentDAO;
import dao.FacultyDAO;
import dao.GroupDAO;
import dao.UniversityDAO;
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
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

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
    private final GroupDAO groupsDAO;
    private final UniversityDAO universityDAO;
    private final FacultyDAO facultyDAO;
    private final DepartmentDAO departmentDAO;
    private final ObjectMapper objectMapper;

    @PostMapping("/registration")
    protected String doPost(@RequestParam(USER) String userNameCred,
                            @RequestParam(PASSWORD) String userPassCred,
                            @RequestParam(REPASSWORD) String userRePassCred,
                            @RequestParam(GROUP) Long groupId,
                            HttpSession session,
                            Model model) throws JsonProcessingException {
        String language = (String) session.getAttribute(LOCALE);
        if (language == null) {
            language = "en";
        }
        ResourceBundle r = ResourceBundle.getBundle("internationalization", new Locale(language));

        if (isEmptyFields(userNameCred, userPassCred, userRePassCred)) {
            model.addAttribute(FLAG, r.getString("registrationservlet.fill"));
            return doGet(model);
        } else {
            Optional<GroupEntity> optional = groupsDAO.findById(groupId);
            if (optional.isPresent()) {
                UserDto user = new UserDto(null, userNameCred, userPassCred, false, optional.get(), null);
                if (userService.isAlreadyExists(user)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.exists"));
                    return doGet(model);
                } else if (!userPassCred.equals(userRePassCred)) {
                    model.addAttribute(FLAG, r.getString("registrationservlet.wrong"));
                    return doGet(model);
                } else {
                    userService.registerUser(user);
                    return "redirect:/" + LOGIN_JSP;
                }
            } else {
                model.addAttribute(FLAG, "Bad group");
                return doGet(model);
            }
        }
    }

    @GetMapping("/registration")
    protected String doGet(Model model) throws JsonProcessingException {
        model.addAttribute("universities", objectMapper.writeValueAsString(universityDAO.findAll()));
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
                                       @RequestParam(UNIVERSITY) Long univerId,
                                       Model model) {
        University univer = universityDAO.findById(univerId).get();
        UserDto newUser = new UserDto(null, userNameCred, userPassCred, true, null, univer);
        if (!userService.isAlreadyExists(newUser)) {
            userService.registerUser(newUser);
            return "redirect:/";
        } else {
            model.addAttribute("error", "User has already existed");
            return doGetTutorPage(model);
        }
    }

    @GetMapping("/registerUniver")
    protected String doGetUniverPage(Model model) {
        model.addAttribute("universities", universityDAO.findAll());
        return "regUniver";
    }

    @PostMapping("/registerUniver")
    protected String doGetUniverProcess(@RequestParam("name") String univerName,
                                        Model model) {
        University byName = universityDAO.findByName(univerName.trim());
        if (byName == null) {
            University university = new University();
            university.setName(univerName.trim());
            universityDAO.save(university);
            return "redirect:/";
        } else {
            model.addAttribute("error", "University has already existed");
            return doGetUniverPage(model);
        }
    }

    @GetMapping("/registerGroups")
    protected String doGetGroupPage(Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        boolean need = user.getUniversity() == null;
        if (!need) {
            model.addAttribute("needUniver", false);
            model.addAttribute("faculties", facultyDAO.findAllByUniversityId(user.getUniversity().getId()));
        } else {
            model.addAttribute("needUniver", true);
            model.addAttribute("universities", universityDAO.findAll());
        }
        return "regGroups";
    }

    @PostMapping("/registerGroups")
    protected String doGetGroupProcess(@RequestParam(value = "univer", required = false) Long univer,
                                       @RequestParam("fac") String fac,
                                       @RequestParam("dep") String dep,
                                       @RequestParam("group") String gro,
                                       Model model) {
        UserDto user = ((SpringUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        University university;
        if (user.getUniversity() != null)
            university = universityDAO.findById(user.getUniversity().getId()).get();
        else {
            university = universityDAO.findById(univer).get();
        }

        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(gro.trim());

        Faculty f = facultyDAO.findAllByUniversityId(university.getId()).stream()
                .filter(fa -> fa.getName().equalsIgnoreCase(fac.trim()))
                .findFirst().orElse(null);
        if (f == null) {
            f = new Faculty();
            f.setName(fac.trim());
            f.setUniversity(university);
            f.setDepartments(new HashSet<>());
        }

        Department d;
        if (f.getId() == null) {
            d = new Department();
            d.setName(dep.trim());
            d.setGroups(new HashSet<>());
        } else {
            d = departmentDAO.findAllByFacultyId(f.getId()).stream()
                    .filter(de -> de.getName().equalsIgnoreCase(dep.trim()))
                    .findFirst().orElse(null);
            if (d == null) {
                d = new Department();
                d.setName(dep.trim());
                d.setGroups(new HashSet<>());
            }
        }

        if (d.getId() != null) {
            GroupEntity entity = groupsDAO.findAllByDepartmentId(d.getId()).stream()
                    .filter(gr -> gr.getName().equalsIgnoreCase(gro.trim()))
                    .findFirst().orElse(null);
            if (entity != null) {
                model.addAttribute("error", "Group has already existed");
                return doGetGroupPage(model);
            }
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
