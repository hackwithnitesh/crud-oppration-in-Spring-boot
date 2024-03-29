package com.crud.controller;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import com.crud.models.Product;
import com.crud.models.ProductDto;
import com.crud.repository.ProductRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductRepository repo;
	
	@GetMapping("")
	public ModelAndView show() {
		ModelAndView mav=new ModelAndView("products/index");
		mav.addObject("products", this.repo.findAll());
		return mav;
		
	}
	
	
	@GetMapping("/create")
	public String showCreatePage(Model model) {
		ProductDto productDto =new ProductDto();
		model.addAttribute("productDto",productDto);
		return "products/createproduct";
	}
	
	
	@PostMapping("/create")
	public String CreateProduct(
		@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
	
		
		Product product=new Product();
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(productDto.getCategory());
		product.setPrice(productDto.getPrice());
		product.setDescription(productDto.getDescription());
		
		repo.save(product);
		
		
		return "redirect:/products";
	}
	
	
	@GetMapping("/edit")
	public String showEditpage(Model model, @RequestParam int id) {
		try {
			Product product=repo.findById(id).get();
			model.addAttribute("product",product);
			
			ProductDto productDto=new ProductDto();
			productDto.setName(product.getName());
			productDto.setBrand(product.getBrand());
			productDto.setCategory(product.getCategory());
			productDto.setPrice(product.getPrice());
			productDto.setDescription(product.getDescription());
			model.addAttribute("productDto",productDto);
		}catch(Exception ex){
			System.out.println("exception" + ex.getMessage());
			return "redirect:/products";
		}
		return "products/EditProduct";
	}
	
	
	@PostMapping("/edit")
	public String updateProduct(
		Model model,
		@RequestParam int id, @Valid @ModelAttribute ProductDto productDto,
		BindingResult result
	){
		
		try {
			Product product=repo.findById(id).get();
			model.addAttribute("product",product);
			if(result.hasErrors()) {
				return "products/EditProduct";
			}
			
			product.setName(productDto.getName());
			product.setBrand(productDto.getBrand());
			product.setCategory(productDto.getCategory());
			product.setPrice(productDto.getPrice());
			product.setDescription(productDto.getDescription());
			
			repo.save(product);
			
		} catch (Exception e) {
			System.out.println("Exception" + e.getMessage());
		}
		return "redirect:/products";
	}
	
	@GetMapping("/delete")
	public String deleteProduct(@RequestParam int id) {
		 try {
			Product product =repo.findById(id).get();
			repo.delete(product);
			 
		} catch (Exception e) {
			System.out.println("exception:"+e.getMessage());
		}
		 return "redirect:/products";
		
	}
}
