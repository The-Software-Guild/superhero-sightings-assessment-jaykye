package jaykye.superherosighting.controller;

import jaykye.superherosighting.dao.*;
import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Organization;
import jaykye.superherosighting.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HeroController {
    Set<ConstraintViolation<Hero>> violations = new HashSet<>();

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;


    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        // Display all heroes.
        // Going to let them select for adding.

        List<Hero> heroes = heroDao.getAllHeroes();
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("heroes", heroes);
        model.addAttribute("organizations", organizations);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("errors", violations);

        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(Hero hero, BindingResult result, HttpServletRequest request) {
        String superpowerId = request.getParameter("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        hero.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));

        List<Organization> organizations = new ArrayList<>();

        if(organizationIds != null) {
            for(String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("hero", "organizations", "Must include one organization");
            result.addError(error);
        }
        hero.setOrganizations(organizations);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(hero);
        if(violations.isEmpty()) {
            heroDao.addHero(hero);
        } // error 들은 html 페이지에 errors 라는 항목으로 model 에 넣어서 전달한다.

        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) { // HttpServletRequest 안쓰고 바로 id 빼올 수 있네...
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        return "heroDetail";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {
        heroDao.deleteHeroById(id);
        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        List<Organization> organizations = organizationDao.getAllOrganizations();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("hero", hero);
        model.addAttribute("organizations", organizations);
        model.addAttribute("superpowers", superpowers);
        return "editHero";
    }

    // Hero 의 validation 은 이전과는 다르다. 여기서는 데이터를 variable 이나 DTO object 가 아닌 HttpServletRequest 로 보내기 때문.
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result,
                                    HttpServletRequest request, Model model) {
        String superpowerId = request.getParameter("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        hero.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));

        List<Organization> organizations = new ArrayList<>(); // 아예 새로 만드네?
        if(organizationIds != null) {
            for(String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            // 아까 @Valid parameter(DTO obj) 로 validate 한 애들은 #fields.hasError 함수 사용해서 html 파일에서 에러를 볼 수 있었다.
            // @Valid(이거는 항상 BindingResult 가 따라와야 함.) 의 경우 자동으로 어딘가에 obj - 에러 pair 가 저장됨.
            // 이 경우 DTO 가 아닌 HttpServletRequest 로 데이터를 들여오기 때문에 그렇게 못한다. -- 수동으로 넣어줘야함.
            /*
            The BindingResult uses FieldErrors to keep track of which field has errors in our object.
            Lucky for us, we can create our own FieldError and add it to the BindingResult.
             */
            FieldError error = new FieldError("hero", "organizations", "Must include one organization");
            result.addError(error);
        }
        hero.setOrganizations(organizations);

        if(result.hasErrors()) {
            model.addAttribute("superpowers", superpowerDao.getAllSuperpowers());
            model.addAttribute("organizations", organizationDao.getAllOrganizations());
            model.addAttribute("hero", hero);
            return "editHero";
        }

        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }
}
