package de.nrw.hbz.labelsV2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.nrw.hbz.labelsV2.entity.Label;
import de.nrw.hbz.labelsV2.repository.LabelsRepository;

@Controller
public class LabelsController {

  @Autowired
  private LabelsRepository labelsRepository;
  
  @GetMapping("/labels")
  public String getAll(Model model, @RequestParam(required = false) String keyword,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "id,asc") String[] sort) {
    try {
      List<Label> labels = new ArrayList<Label>();
     
      String sortField = sort[0];
      String sortDirection = sort[1];
      
      Direction direction = sortDirection.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
      Order order = new Order(direction, sortField);
      
      Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

      Page<Label> pageLabel;
      if (keyword == null) {
        pageLabel = labelsRepository.findAll(pageable);
      } else {
    	pageLabel = labelsRepository.searchAllFieldsByKeyword(keyword, pageable);
        model.addAttribute("keyword", keyword);
      }

      labels = pageLabel.getContent();

      model.addAttribute("mainTitle", "Labels");
      model.addAttribute("labels", labels);
      model.addAttribute("currentPage", pageLabel.getNumber() + 1);
      model.addAttribute("totalItems", pageLabel.getTotalElements());
      model.addAttribute("totalPages", pageLabel.getTotalPages());
      model.addAttribute("pageSize", size);
      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDirection", sortDirection);
      model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
    } catch (Exception e) {
      model.addAttribute("message", e.getMessage());
    }

    return "labels";
  }

  @GetMapping("/labels/new")
  public String addLabel(Model model) {
    
    model.addAttribute("label", new Label());
    model.addAttribute("pageTitle", "Create new Label");

    return "label_form";
  }

  @PostMapping("/labels/save")
  public String saveLabel(Label label, RedirectAttributes redirectAttributes) {
    try {
      labelsRepository.save(label);

      redirectAttributes.addFlashAttribute("message", "The Label has been saved successfully!");
    } catch (Exception e) {
      redirectAttributes.addAttribute("message", e.getMessage());
    }

    return "redirect:/labels";
  }

  @GetMapping("/labels/{id}")
  public String editLabel(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Label label = labelsRepository.findById(id).get();

      model.addAttribute("label", label);
      model.addAttribute("pageTitle", "Edit Label (ID: " + id + ")");

      return "label_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/labels";
    }
  }

  @GetMapping("/labels/delete/{id}")
  public String deleteLabel(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      labelsRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Label with id: " + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/labels";
  }

  @GetMapping("/label")
  @ResponseBody
  public Label getLabelByGroupAndName(Model model, @RequestParam String group, @RequestParam String name) {
	  return labelsRepository.findByGroupAndJsonConfName(group, name);
  }
}
