package com.quatidianStore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.quatidianStore.entity.ImageModel;
import com.quatidianStore.entity.Product;
import com.quatidianStore.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PreAuthorize("hasRole('Admin')")
	@PostMapping(value = { "/addNewProduct" }, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public Product addNewProduct(@RequestPart("product") Product product,
			@RequestPart("imageFile") MultipartFile[] file) {

		try {
			Set<ImageModel> images = uploadImage(file);
			product.setProductImages(images);
			return productService.addnewProduct(product);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	// to process the image in storing manner
	public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws java.io.IOException {
		Set<ImageModel> imageModels = new HashSet<>();
		for (MultipartFile file : multipartFiles) {
			ImageModel imageModel = new ImageModel(file.getOriginalFilename(), file.getContentType(), file.getBytes());

			imageModels.add(imageModel);
		}

		return imageModels;
	}

	@GetMapping({ "/getAllProducts" })
	public java.util.List<Product> getAllProduct(@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "") String searchKey) {

		return productService.getAllProducts(pageNumber, searchKey);
	}

	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping({ "/deleteProductDetails/{ProductId}" })
	public void deleteProductDetails(@PathVariable("ProductId") Integer productId) {
		productService.deleteProductDetails(productId);
	}

	@GetMapping({ "/getProductByDetailsId/{productId}" })
	public Product getProductDetailsById(@PathVariable("productId") Integer productId) {
		return productService.getProductDetailById(productId);
	}

	@PreAuthorize("hasRole('User')")
	@GetMapping({ "/getProductDetails/{isSingleProductCheckout}/{productId}" })
	public List<Product> getProductDetails(
			@PathVariable(name = "isSingleProductCheckout") boolean isSingleProductCheckout,
			@PathVariable(name = "productId") Integer productId) {
		return productService.getProductDetails(isSingleProductCheckout, productId);

	}

}
