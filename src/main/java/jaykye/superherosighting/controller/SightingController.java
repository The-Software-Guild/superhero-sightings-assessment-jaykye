package jaykye.superherosighting.controller;

import jaykye.superherosighting.dao.*;
import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Location;
import jaykye.superherosighting.model.Sighting;
import jaykye.superherosighting.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller

public class SightingController {
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


    @GetMapping("sightings")
    public String displaySightings(Model model) {
        // Display all sightings.

        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locationDao.getAllLocations();
        List<Hero> heroes = heroDao.getAllHeroes();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("heroes", heroes);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {
        String locationId = request.getParameter("locationId");
        String heroId = request.getParameter("heroId");
        String dateString = request.getParameter("dateString");

        sighting.setLocation(locationDao.getLocationById(Integer.parseInt(locationId)));
        sighting.setHero(heroDao.getHeroById(Integer.parseInt(heroId)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sighting.setDate(LocalDate.parse(dateString, formatter));
        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) { // HttpServletRequest ????????? ?????? id ?????? ??? ??????...
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        return "sightingDetail";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {
        sightingDao.deleteSightingById(id);
        return "redirect:/sightings";
    }

//    @GetMapping("editSighting")
//    public String editSighting(Integer id, Model model) {
//        Sighting sighting = sightingDao.getSightingById(id);
//        List<Organization> organizations = organizationDao.getAllOrganizations();
//        List<Sighting> sightings = sightingDao.getAllSightings();
//        model.addAttribute("sighting", sighting);
//        model.addAttribute("organizations", organizations);
//        model.addAttribute("sightings", sightings);
//        return "editSighting";
//    }
//
//    // Sighting ??? validation ??? ???????????? ?????????. ???????????? ???????????? variable ?????? DTO object ??? ?????? HttpServletRequest ??? ????????? ??????.
//    @PostMapping("editSighting")
//    public String performEditSighting(@Valid Sighting sighting, BindingResult result,
//                                  HttpServletRequest request, Model model) {
//        String sightingId = request.getParameter("sightingId");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
//        sighting.setSighting(sightingDao.getSightingById(Integer.parseInt(sightingId)));
//
//        List<Organization> organizations = new ArrayList<>(); // ?????? ?????? ??????????
//        if(organizationIds != null) {
//            for(String organizationId : organizationIds) {
//                organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
//            }
//        } else {
//            // ?????? @Valid parameter(DTO obj) ??? validate ??? ????????? #fields.hasError ?????? ???????????? html ???????????? ????????? ??? ??? ?????????.
//            // @Valid(????????? ?????? BindingResult ??? ???????????? ???.) ??? ?????? ???????????? ???????????? obj - ?????? pair ??? ?????????.
//            // ??? ?????? DTO ??? ?????? HttpServletRequest ??? ???????????? ???????????? ????????? ????????? ?????????. -- ???????????? ???????????????.
//            /*
//            The BindingResult uses FieldErrors to keep track of which field has errors in our object.
//            Lucky for us, we can create our own FieldError and add it to the BindingResult.
//             */
//            FieldError error = new FieldError("sighting", "organizations", "Must include one organization");
//            result.addError(error);
//        }
//        sighting.setOrganizations(organizations);
//
//        if(result.hasErrors()) {
//            model.addAttribute("sightings", sightingDao.getAllSightings());
//            model.addAttribute("organizations", organizationDao.getAllOrganizations());
//            model.addAttribute("sighting", sighting);
//            return "editSighting";
//        }
//
//        sightingDao.updateSighting(sighting);
//
//        return "redirect:/sightinges";
//    }
}
