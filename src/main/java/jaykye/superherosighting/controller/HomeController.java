package jaykye.superherosighting.controller;

import jaykye.superherosighting.dao.*;
import jaykye.superherosighting.model.Hero;
import jaykye.superherosighting.model.Organization;
import jaykye.superherosighting.model.Sighting;
import jaykye.superherosighting.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

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


    @GetMapping("/")
    public String displayHomepage(Model model) {
        List<Sighting> last10Sightings = sightingDao.getLast10Sightings();
        model.addAttribute("recentSightings", last10Sightings);
        return "home.html";
    }
}
