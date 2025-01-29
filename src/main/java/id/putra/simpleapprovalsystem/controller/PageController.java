package id.putra.simpleapprovalsystem.controller;

import id.putra.simpleapprovalsystem.request.ItemRequest;
import id.putra.simpleapprovalsystem.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    @Autowired
    private ApprovalService approvalService;

    @GetMapping("")
    public ModelAndView index(ModelMap model) {
        model.put("approvals", approvalService.getApprovals());
        model.put("item", new ItemRequest());
        return new ModelAndView("index", model);
    }
}
