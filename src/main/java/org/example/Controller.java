package org.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
class Controller {
    @GetMapping(value = {"/", "/public"})
    public String publicPage(Model model, Authentication authentication) {
        model.addAttribute("name", getName(authentication));
        return "public";
    }

    @GetMapping("/private")
    public String privatePage(Model model, Authentication authentication) {
        // TODO 4. Greet logged in user
        model.addAttribute("name", getName(authentication));
        return "private";
    }

    private String getName(Authentication authentication) {
        if (authentication == null) {
            return "Stranger";
        }
        if (authentication.getPrincipal() instanceof OidcUser oidcUser) {
            return oidcUser.getEmail();
        }
        return authentication.getName();
    }
}
