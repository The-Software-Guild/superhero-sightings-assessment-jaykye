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
        } // error ?????? html ???????????? errors ?????? ???????????? model ??? ????????? ????????????.

        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) { // HttpServletRequest ????????? ?????? id ?????? ??? ??????...
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

    // Hero ??? validation ??? ???????????? ?????????. ???????????? ???????????? variable ?????? DTO object ??? ?????? HttpServletRequest ??? ????????? ??????.
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result,
                                    HttpServletRequest request, Model model) {
        String superpowerId = request.getParameter("superpowerId");
        String[] organizationIds = request.getParameterValues("organizationId");

        hero.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));

        List<Organization> organizations = new ArrayList<>(); // ?????? ?????? ??????????
        if(organizationIds != null) {
            for(String organizationId : organizationIds) {
                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            // ?????? @Valid parameter(DTO obj) ??? validate ??? ????????? #fields.hasError ?????? ???????????? html ???????????? ????????? ??? ??? ?????????.
            // @Valid(????????? ?????? BindingResult ??? ???????????? ???.) ??? ?????? ???????????? ???????????? obj - ?????? pair ??? ?????????.
            // ??? ?????? DTO ??? ?????? HttpServletRequest ??? ???????????? ???????????? ????????? ????????? ?????????. -- ???????????? ???????????????.
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
