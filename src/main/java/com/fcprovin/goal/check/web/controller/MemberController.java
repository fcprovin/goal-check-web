package com.fcprovin.goal.check.web.controller;

import com.fcprovin.goal.check.web.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

	@ModelAttribute("servletPath")
	public String servletPath(HttpServletRequest request) {
		log.info("request.getServletPath() = {}", request.getServletPath());
		return request.getServletPath();
	}

	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", memberService.list());
		return "/member/list";
	}

	@GetMapping("/goals/{id}")
	public String goals(@PathVariable Long id, Model model) {
		model.addAttribute("list", memberService.goals(id));
		return "/member/goals";
	}

	@GetMapping("/assists/{id}")
	public String assists(@PathVariable Long id, Model model) {
		model.addAttribute("list", memberService.assists(id));
		return "/member/assists";
	}
}
