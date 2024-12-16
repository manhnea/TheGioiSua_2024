package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Targetuser;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.TargetuserService;
import com.example.TheGioiSua_2024.Validator.TargetuserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Targetuser")
public class TargetuserRestController {

  @Autowired
  private TargetuserService targetuserService;
  @Autowired
  private JwtUtilities jwtUtilities;

  //http://localhost:1234/api/Targetuser/lst
  @GetMapping("/lst")
  public List<Targetuser> lst() {
    return targetuserService.getAllTargetuser();
  }

  //http://localhost:1234/api/Targetuser/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request,
    @RequestBody  Targetuser targetuser) {
    String token = jwtUtilities.getToken(request);
   return targetuserService.addTargetuser(token, targetuser);
  }

  @GetMapping("/lst/{id}")
  public Targetuser get(@PathVariable("id") Long id) {
    return targetuserService.getTargetuserById(id);
  }

  //http://localhost:1234/api/Targetuser/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request,
    @RequestBody  Targetuser targetuser,
     @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    return targetuserService.updateTargetuser(token, id, targetuser);

  }

  //http://localhost:1234/api/Targetuser/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
  return targetuserService.deleteTargetuser(token, id);
  }

  @GetMapping("/getTargetuserPage")
  public Page<Targetuser> targetusersPage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return targetuserService.getTargetuserPage(pageable);
  }

    @GetMapping("/getTargetusersearch")
    public Page<Targetuser> targetusersPage(@RequestParam("page") int page,
      @RequestParam("size") int size, @RequestParam("targetname") String targetname) {
      Pageable pageable = PageRequest.of(page, size);
      return targetuserService.getTargetusersearch(targetname, pageable);
    }
}
