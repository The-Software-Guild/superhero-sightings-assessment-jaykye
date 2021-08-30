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
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller

public class OrganizationController {
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


    @GetMapping("organizations")
    public String displayOrganization(Model model) {
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(Organization organization, HttpServletRequest request) {

        List<Hero> members = heroDao.getMembersForOrganization(organization);
        for (Hero hero : members){
            System.out.println(hero.getName());
        }
        organization.setMembers(members);

        organizationDao.addOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) { // HttpServletRequest 안쓰고 바로 id 빼올 수 있네...
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
//
//    @GetMapping("editOrganization")
//    public String editOrganization(Integer id, Model model) {
//        Organization organization = organizationDao.getOrganizationById(id);
//        List<Organization> organizations = organizationDao.getAllOrganizations();
//        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
//        model.addAttribute("organization", organization);
//        model.addAttribute("organizations", organizations);
//        model.addAttribute("superpowers", superpowers);
//        return "editOrganization";
//    }
//
//    // Organization 의 validation 은 이전과는 다르다. 여기서는 데이터를 variable 이나 DTO object 가 아닌 HttpServletRequest 로 보내기 때문.
//    @PostMapping("editOrganization")
//    public String performEditOrganization(@Valid Organization organization, BindingResult result,
//                                  HttpServletRequest request, Model model) {
//        String superpowerId = request.getParameter("superpowerId");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
//        organization.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
//
//        List<Organization> organizations = new ArrayList<>(); // 아예 새로 만드네?
//        if(organizationIds != null) {
//            for(String organizationId : organizationIds) {
//                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
//            }
//        } else {
//            // 아까 @Valid parameter(DTO obj) 로 validate 한 애들은 #fields.hasError 함수 사용해서 html 파일에서 에러를 볼 수 있었다.
//            // @Valid(이거는 항상 BindingResult 가 따라와야 함.) 의 경우 자동으로 어딘가에 obj - 에러 pair 가 저장됨.
//            // 이 경우 DTO 가 아닌 HttpServletRequest 로 데이터를 들여오기 때문에 그렇게 못한다. -- 수동으로 넣어줘야함.
//            /*
//            The BindingResult uses FieldErrors to keep track of which field has errors in our object.
//            Lucky for us, we can create our own FieldError and add it to the BindingResult.
//             */
//            FieldError error = new FieldError("organization", "organizations", "Must include one organization");
//            result.addError(error);
//        }
//        organization.setOrganizations(organizations);
//
//        if(result.hasErrors()) {
//            model.addAttribute("superpowers", superpowerDao.getAllSuperpowers());
//            model.addAttribute("organizations", organizationDao.getAllOrganizations());
//            model.addAttribute("organization", organization);
//            return "editOrganization";
//        }
//
//        organizationDao.updateOrganization(organization);
//
//        return "redirect:/organizationes";
//    }
}
