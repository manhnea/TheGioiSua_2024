package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Usagecapacity;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.UsagecapacityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/Usagecapacity")
public class UsagecapacityRestController {

  @Autowired
  private UsagecapacityService usagecapacityService;
  @Autowired
  private JwtUtilities jwtUtilities;

  @GetMapping("/lst")
    public List<Usagecapacity> getAllUsagecapacity() {
        return usagecapacityService.getAllUsagecapacity();
    }

  //http://localhost:1234/api/Usagecapacity/add
  @PostMapping("/add")
  public ResponseEntity<?> addUsagecapacity(@NonNull HttpServletRequest request,
      @RequestBody  Usagecapacity usagecapacity) {
   
    String token = jwtUtilities.getToken(request);
    return usagecapacityService.addUsagecapacity(token, usagecapacity);
  }

  @GetMapping("/lst/{id}")
  public Usagecapacity getUsagecapacity(@PathVariable("id") Long id) {
    return usagecapacityService.getUsagecapacityById(id);
  }

  //http://localhost:1234/api/Usagecapacity/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateUsagecapacity(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id,
      @RequestBody  Usagecapacity usagecapacity) {
   
    String token = jwtUtilities.getToken(request);
   return usagecapacityService.updateUsagecapacity(token, id, usagecapacity);
  }

  //http://localhost:1234/api/Usagecapacity/delete/{id}
  @DeleteMapping("/delete/{id}")
  public ResponseEntity<?> deleteUsagecapacity(@NonNull HttpServletRequest request,
      @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
    return  usagecapacityService.deleteUsagecapacity(token, id);
  }
  @GetMapping("/getUsagecapacityPage")
  public Page<Usagecapacity> getUsagecapacityPage(@RequestParam("page") int page, @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return usagecapacityService.getUsagecapacityPage(pageable);
  }
}
