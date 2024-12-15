package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milktaste;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilktasteService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Milktaste")
public class MilktasteRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilktasteService milktasteService;

  //http://localhost:1234/api/Milktaste/lst
  @GetMapping("/lst")
  public List<Milktaste> lst() {
    return milktasteService.getAllMilktaste();
  }

  @GetMapping("/lst/{id}")
  public Milktaste lst(@PathVariable("id") Long id) {
    return milktasteService.getMilktasteById(id);
  }

  //http://localhost:1234/api/Milktaste/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request,
    @RequestBody  Milktaste milktaste) {
    String token = jwtUtilities.getToken(request);
    return milktasteService.addMilktaste(token, milktaste);
  }

  //http://localhost:1234/api/Milktaste/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@PathVariable("id") Long id,
    @RequestBody  Milktaste milktaste) {
    return milktasteService.updateMilktaste(id, milktaste);
  }

  //http://localhost:1234/api/Milktaste/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
   return milktasteService.deleteMilktaste(token, id);
  }

  @GetMapping("/getMilktastePage")
  public Page<Milktaste> getMilktastePage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milktasteService.getMilktastePage(pageable);
  }

  @GetMapping("/getMilktastePageByName")
    public Page<Milktaste> getMilktastePageByName(@RequestParam("page") int page,
        @RequestParam("size") int size, @RequestParam("milktasteName") String milktasteName) {
        Pageable pageable = PageRequest.of(page, size);
        return milktasteService.getMilktastePageByName(milktasteName, pageable);
    }

}
