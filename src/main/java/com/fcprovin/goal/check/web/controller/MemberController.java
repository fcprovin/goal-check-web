package com.fcprovin.goal.check.web.controller;

import com.fcprovin.goal.check.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;

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
