package jaykye.superherosighting.controller;

import jaykye.superherosighting.dao.*;
import jaykye.superherosighting.model.Location;
import jaykye.superherosighting.model.Location;
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

public class LocationController {
    @Autowired
    LocationDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;


    @GetMapping("locations")
    public String displayLocations(Model model) {
        // Display all locations.

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(Location location, HttpServletRequest request) {
        locationDao.addLocation(location);

        return "redirect:/locations";
    }

//    @GetMapping("locationDetail")
//    public String locationDetail(Integer id, Model model) { // HttpServletRequest 안쓰고 바로 id 빼올 수 있네...
//        Location location = locationDao.getLocationById(id);
//        model.addAttribute("location", location);
//        return "locationDetail";
//    }
//
    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {
        locationDao.deleteLocationById(id);
        return "redirect:/locations";
    }

//    @GetMapping("editLocation")
//    public String editLocation(Integer id, Model model) {
//        Location location = locationDao.getLocationById(id);
//        List<Organization> organizations = organizationDao.getAllOrganizations();
//        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
//        model.addAttribute("location", location);
//        model.addAttribute("organizations", organizations);
//        model.addAttribute("superpowers", superpowers);
//        return "editLocation";
//    }
//
//    // Location 의 validation 은 이전과는 다르다. 여기서는 데이터를 variable 이나 DTO object 가 아닌 HttpServletRequest 로 보내기 때문.
//    @PostMapping("editLocation")
//    public String performEditLocation(@Valid Location location, BindingResult result,
//                                  HttpServletRequest request, Model model) {
//        String superpowerId = request.getParameter("superpowerId");
//        String[] organizationIds = request.getParameterValues("organizationId");
//
//        location.setSuperpower(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
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
//            FieldError error = new FieldError("location", "organizations", "Must include one organization");
//            result.addError(error);
//        }
//        location.setOrganizations(organizations);
//
//        if(result.hasErrors()) {
//            model.addAttribute("superpowers", superpowerDao.getAllSuperpowers());
//            model.addAttribute("organizations", organizationDao.getAllOrganizations());
//            model.addAttribute("location", location);
//            return "editLocation";
//        }
//
//        locationDao.updateLocation(location);
//
//        return "redirect:/locationes";
//    }
}
