package de.hbz.nrw.to.science.labels.v2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import de.hbz.nrw.to.science.labels.v2.entity.Label;
import de.hbz.nrw.to.science.labels.v2.repository.LabelsRepository;

@Controller
public class LabelsController {

  @Autowired
  private LabelsRepository labelsRepository;
  
  @GetMapping
  public String getAll(Model model) {
    
	  List<Label> labels = new ArrayList<Label>();
	  labels = labelsRepository.findAll();
	  model.addAttribute("mainTitle", "Labels");
	  model.addAttribute("labels", labels);
	  return "labels";
  }

  @GetMapping("/label")
  public String addLabel(Model model) {
	  
	  model.addAttribute("label", new Label());
	  model.addAttribute("pageTitle", "Create new Label");
	  return "label_form";
  }

  @PostMapping("/save")
  public String saveLabel(Label label, RedirectAttributes redirectAttributes) {
	try {
	  labelsRepository.save(label);
	  redirectAttributes.addFlashAttribute("message", "The Label has been saved/updated successfully!");
	} catch (Exception e) {
	  redirectAttributes.addAttribute("message", e.getMessage());
	}
	return "redirect:/";
  }

  @GetMapping("/label/{id}")
  public String editLabel(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      Label label = labelsRepository.findById(id).get();

      model.addAttribute("label", label);
      model.addAttribute("pageTitle", "Edit Label (ID: " + id + ")");

      return "label_form";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());

      return "redirect:/";
    }
  }

  @GetMapping("/delete/label/{id}")
  public String deleteLabel(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
    try {
      labelsRepository.deleteById(id);

      redirectAttributes.addFlashAttribute("message", "The Label with id: " + id + " has been deleted successfully!");
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message", e.getMessage());
    }

    return "redirect:/";
  }

  @GetMapping("/find")
  @ResponseBody
  public Label getLabelByGroupAndName(Model model, @RequestParam String group, @RequestParam String name) {
	  return labelsRepository.findByGroupAndJsonConfName(group, name);
  }
}
