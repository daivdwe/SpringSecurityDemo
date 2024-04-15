package org.example;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
class Controller {
	@GetMapping(value = {"/", "/public"})
	public String publicPage() {
		return "public";
	}

	@GetMapping("/private")
	public String privatePage() {
		return "private";
	}
}
