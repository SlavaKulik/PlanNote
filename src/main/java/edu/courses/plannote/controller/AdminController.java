package edu.courses.plannote.controller;

import edu.courses.plannote.dto.UserDto;
import edu.courses.plannote.service.CustomUserDetailsService;
import edu.courses.plannote.utils.CodeDecodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static edu.courses.plannote.utils.EnrichControllerMethods.enrichModelForAdmin;

@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(GeneralController.class);

    private final CustomUserDetailsService customUserDetailsService;

    public AdminController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping(value = "/admin-panel")
    public ModelAndView updateUser(
            @ModelAttribute("model")ModelMap model) {
        List<UserDto> users = customUserDetailsService.userList();
        model.addAttribute("userList", users);
        return new ModelAndView("/pages/users/admin_panel", model);
    }

    @GetMapping(value = "/admin-panel/{userId}/edit-score")
    public ModelAndView updateAccountScore(
            @ModelAttribute("model") ModelMap model,
            @PathVariable(value = "userId") String user) {
        model.addAttribute("userId", user);
        return new ModelAndView("redirect:/admin-panel", model);
    }

    @PostMapping(value = "/admin-panel/{userId}/edit-score")
    public ModelAndView updateAccountScore(
            @ModelAttribute("model") ModelMap model,
            @RequestParam(value = "userScore") String score,
            @PathVariable(value = "userId") String user) {
        String decodedId = CodeDecodeId.idDecoder(user);
        UserDto userDto = UserDto.builder()
                .id(decodedId)
                .userScore(score)
                .build();
        customUserDetailsService.updateUserByAdmin(userDto);
        log.info("User score updated");
        enrichModelForAdmin(model, user);
        return new ModelAndView("redirect:/admin-panel", model);
    }
    @GetMapping(value = "/admin-panel/{userId}/edit-status")
    public ModelAndView updateAccountStatus(
            @ModelAttribute("model") ModelMap model,
            @PathVariable(value = "userId") String user) {
        enrichModelForAdmin(model, user);
        return new ModelAndView("redirect:/admin-panel", model);
    }

    @PostMapping(value = "/admin-panel/{userId}/edit-status")
    public ModelAndView updateAccountStatus(
            @ModelAttribute("model") ModelMap model,
            @RequestParam(value = "accStatus") String accStatus,
            @PathVariable(value = "userId") String user) {
        String decodedId = CodeDecodeId.idDecoder(user);
        UserDto userDto = UserDto.builder()
                .id(decodedId)
                .accStatus(accStatus)
                .build();
        customUserDetailsService.updateUserByAdmin(userDto);
        log.info("User status updated");
        enrichModelForAdmin(model, user);
        return new ModelAndView("redirect:/admin-panel", model);
    }
}
