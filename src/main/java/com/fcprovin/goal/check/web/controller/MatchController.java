package com.fcprovin.goal.check.web.controller;

import com.fcprovin.goal.check.web.dto.form.GoalAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchAddForm;
import com.fcprovin.goal.check.web.dto.form.MatchModifyForm;
import com.fcprovin.goal.check.web.service.MatchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static java.time.Month.JANUARY;
import static java.time.format.DateTimeFormatter.ofPattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/match")
public class MatchController {

	private final MatchService matchService;
	private final LocalDate startDate = LocalDate.of(2024, 1, 1);

	@ModelAttribute("servletPath")
	public String servletPath(HttpServletRequest request) {
		log.info("request.getServletPath() = {}", request.getServletPath());
		return request.getServletPath();
	}

	@ModelAttribute("yearList")
	public List<String> yearList() {
		return startDate
				.datesUntil(LocalDate.now(), Period.ofYears(1))
				.map(d -> d.format(ofPattern("yyyy")))
				.toList();
	}

	@GetMapping
	public String list(@RequestParam(required = false, defaultValue = "2024")
						   int year, Model model) {
		model.addAttribute("list", matchService.list(LocalDate.of(year, JANUARY, 1)));
		return "match/list";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("item", matchService.detail(id));
		return "match/detail";
	}

	@GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("item", new MatchAddForm());
        return "match/add";
    }

	@PostMapping("/add")
    public String add(@Validated @ModelAttribute("item") MatchAddForm form, RedirectAttributes attributes) {
        attributes.addAttribute("id", matchService.add(form).getId());
        return "redirect:/match/{id}";
    }

	@PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id, @Validated @ModelAttribute("item") MatchModifyForm form) {
        matchService.modify(id, form);
        return "redirect:/match/{id}";
    }

	@PostMapping("/remove/{id}")
	public String remove(@PathVariable Long id, RedirectAttributes attributes) {
		matchService.remove(id);

		attributes.addAttribute("list", matchService.list(startDate));
		return "redirect:/match";
	}

	@GetMapping("/{matchId}/goal/add")
    public String goalAdd(@PathVariable Long matchId, Model model) {
		model.addAttribute("memberList", matchService.memberList());
        model.addAttribute("item", new GoalAddForm());
        return "match/goal/add";
    }

	@PostMapping("/{matchId}/goal/add")
    public String goalAdd(@PathVariable Long matchId,
						  @Validated @ModelAttribute("item") GoalAddForm form,
						  RedirectAttributes attributes) {
        attributes.addAttribute("id", matchService.goalAdd(matchId, form).getId());
        return "redirect:/match/{id}";
    }

	@PostMapping("/goal/remove/{id}")
	public String goalRemove(@PathVariable Long id, RedirectAttributes attributes) {
		attributes.addAttribute("id", matchService.goalRemove(id).getId());
		return "redirect:/match/{id}";
	}
}
