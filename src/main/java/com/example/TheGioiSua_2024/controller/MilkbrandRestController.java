package com.example.TheGioiSua_2024.controller;

import com.example.TheGioiSua_2024.entity.Milkbrand;
import com.example.TheGioiSua_2024.security.JwtUtilities;
import com.example.TheGioiSua_2024.service.MilkbrandService;
import com.example.TheGioiSua_2024.service.MilkdetailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Milkbrand")
public class MilkbrandRestController {

  @Autowired
  private JwtUtilities jwtUtilities;
  @Autowired
  private MilkbrandService milkbrandService;
  @Autowired
  private MilkdetailService milkdetailService;

  //http://localhost:1234/api/Milkbrand/lst
  @GetMapping("/lst")
  public List<Milkbrand> lst() {
    return milkbrandService.getAllMilkbrands();
  }

  @GetMapping("/lst/{id}")
  public Milkbrand getMilkbrandById(@PathVariable Long id) {
    return milkbrandService.getMilkbrandById(id);
  }

  //http://localhost:1234/api/Milkbrand/add
  @PostMapping("/add")
  public ResponseEntity<?> add(@NonNull HttpServletRequest request, @RequestBody Milkbrand milkbrand) {
    String token = jwtUtilities.getToken(request);
return milkbrandService.addMilkbrand(token, milkbrand);
  }


  //http://localhost:1234/api/Milkbrand/update/{id}
  @PutMapping("/update/{id}")
  public ResponseEntity<?> update(@NonNull HttpServletRequest request, @PathVariable Long id,
    @RequestBody  Milkbrand milkbrand) {
    String token = jwtUtilities.getToken(request);
  return milkbrandService.updateMilkbrand(token, id, milkbrand);
  }

  //http://localhost:1234/api/Milkbrand/delete/{id}
  @DeleteMapping("/delete/{id}") // Change to DELETE method
  public ResponseEntity<?> delete(@NonNull HttpServletRequest request,
    @PathVariable("id") Long id) {
    String token = jwtUtilities.getToken(request);
  return milkbrandService.deleteMilkbrand(token, id);
  }

  // http://localhost:1234/api/Milkdetail/getMilkDetailPage

  @GetMapping("/getMilkBrandPage")
  public Page<Milkbrand> getMilkBrandPage(@RequestParam("page") int page,
    @RequestParam("size") int size) {
    Pageable pageable = PageRequest.of(page, size);
    return milkbrandService.getMilkbrandPage(pageable);
  }
  @GetMapping("/getMilkBrandsearch")
  public Page<Milkbrand> getMilkBrandsearch(
          @RequestParam(value = "milkbrandname", required = false) String milkbrandname,
          @RequestParam(value = "status", required = false) String status,
          @RequestParam("page") int page,
          @RequestParam("size") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return milkbrandService.getMilkbrandsearch(milkbrandname, status, pageable);
  }

}
