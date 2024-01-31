package com.fcprovin.goal.check.web.controller;

import com.fcprovin.goal.check.config.security.CustomUserDetails;
import com.fcprovin.goal.check.web.dto.form.AttendAddForm;
import com.fcprovin.goal.check.web.dto.form.MemberPasswordForm;
import com.fcprovin.goal.check.web.service.MeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MeController {

    private final MeService meService;

    @ModelAttribute("servletPath")
	public String servletPath(HttpServletRequest request) {
		log.info("request.getServletPath() = {}", request.getServletPath());
		return request.getServletPath();
	}

    @GetMapping({"/me", "/"})
    public String me(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("member", meService.getMember(userDetails));
        model.addAttribute("matches", meService.getMatches(userDetails));

        return "me/index";
    }

    @PostMapping("/me/password")
    public String password(@AuthenticationPrincipal CustomUserDetails userDetails, MemberPasswordForm form) {
        meService.password(userDetails, form);
        return "redirect:/login";
    }

    @PostMapping("/me/attend")
    public String attend(@AuthenticationPrincipal CustomUserDetails userDetails, AttendAddForm form) {
        meService.attend(userDetails, form);
        return "redirect:/me";
    }
}
