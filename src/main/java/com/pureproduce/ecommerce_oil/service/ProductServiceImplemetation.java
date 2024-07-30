package com.pureproduce.ecommerce_oil.service;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.pureproduce.ecommerce_oil.exception.ProductException;
import com.pureproduce.ecommerce_oil.model.Category;
import com.pureproduce.ecommerce_oil.model.Product;
import com.pureproduce.ecommerce_oil.model.Size;
import com.pureproduce.ecommerce_oil.repository.CategoryRepository;
import com.pureproduce.ecommerce_oil.repository.ProductRepository;
import com.pureproduce.ecommerce_oil.request.CreateProductRequest;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductServiceImplemetation implements ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;


    @Override
    public Product createProduct(CreateProductRequest req) {
        Category toplevel = categoryRepository.findByName(req.getTopLevelCategory());
        
        if(toplevel== null){
            Category topLevelCategory = new Category();
            topLevelCategory.setName(req.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            toplevel = categoryRepository.save(topLevelCategory);    
        }


        Category secondLevel = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), toplevel.getName());

        if(secondLevel== null){
            Category secondLevelCategory= new Category();
            secondLevelCategory.setName(req.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(toplevel);
            secondLevelCategory.setLevel(2);

            secondLevel= categoryRepository.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevel.getName());

        if(thirdLevel == null){
            Category thirdLevelCategory = new Category();
            thirdLevelCategory.setName(req.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);

            thirdLevel = categoryRepository.save(thirdLevelCategory);
        }

        Product product= new Product();
        product.setTitle(req.getTitle());
        product.setBrand(req.getBrand());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountPercent(req.getDiscountPercent());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLevel);
        product.setImageURl(req.getImageUrl());
        product.setCreatedAt(LocalDateTime.now());


         Set<Size> sizes = new HashSet<>();
    for (Size sizeRequest : req.getSize()) {
        Size size = new Size();
        size.setValue(sizeRequest.getValue());
        size.setQuantity(sizeRequest.getQuantity());
        sizes.add(size);
    }
    product.setSizes(sizes);

        
        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        
        Product product= findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);
        return "Product deleted succefully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product =findProductById(productId);
        if(req.getQuantity()!=0){
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long productId) throws ProductException {
        Optional<Product> opt= productRepository.findById(productId);
        if(opt.isPresent()){
            return opt.get();

        }
        throw new ProductException("Product Not Found with ID-"+productId);

    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return productRepository.findByCategory(category);
    }



    @Override
public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes,
                                   Integer minPrice, Integer maxPrice, Integer minDiscount,
                                   String sort, String stock, Integer pageNumber, Integer pageSize) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize);

     // Log input parameters
     System.out.println("Category: " + category);
     System.out.println("Colors: " + colors);
     System.out.println("Sizes: " + sizes);
     System.out.println("Min Price: " + minPrice);
     System.out.println("Max Price: " + maxPrice);
     System.out.println("Min Discount: " + minDiscount);
     System.out.println("Sort: " + sort);
     System.out.println("Stock: " + stock);
     System.out.println("Page Number: " + pageNumber);
     System.out.println("Page Size: " + pageSize);

    // Retrieve products from repository based on category and price filters
    List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);


    // Apply additional filters for colors and stock
    if (!colors.isEmpty()) {
        products = products.stream()
                .filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor())))
                .collect(Collectors.toList());
    }

    if (stock != null) {
        if (stock.equals("in_stock")) {
            products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
        } else if (stock.equals("out_of_stock")) {
            products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
        }
    }

    int startIndex = (int) pageable.getOffset();
    int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

    List<Product> pageContent = products.subList(startIndex, endIndex);

    return  new PageImpl<>(pageContent, pageable, products.size());
}



  
    @Override
    public List<Product> recentlyAddedProducts() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

}
