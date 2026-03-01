package com.codewithmosh.store.Controllers;
import java.util.List;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.mapstruct.MappingTarget;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class productController {
    private final  ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    @GetMapping
    public List<ProductDto> getProducts(
            @RequestParam(required = false, defaultValue = "", name = "category_id") Byte category_id
    )
    {
        if(category_id!=null)
        {
            return productRepository.getProductByCategory_id(category_id).stream().map(productMapper::toDto).toList();
        }
        return productRepository.findAll().stream().map(productMapper::toDto).toList();
    }
    @GetMapping("{id}")
    public  ResponseEntity<ProductDto> getProductsById(@PathVariable Long id)
    {

        var product =  productRepository.findById(id).orElse(null);
        if(product==null)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }
    @PostMapping
    public  ResponseEntity<ProductDto> createProductRequest(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder builder
    )
    {
         var category = categoryRepository.findById(productDto.getCategory_id()).orElse(null);
         if(category == null)
         {
            return ResponseEntity.badRequest().build();
         }
         var product = productMapper.toEntity(productDto); //deserialization i.e  json -> java object
         product.setCategory(category);
         productRepository.save(product);
          var productEntityToDto = productMapper.toDto(product); //serialization i.e java object-> json
         var uri = builder.path("/products/{id}").buildAndExpand(productEntityToDto.getId()).toUri();
         return  ResponseEntity.created(uri).body(productEntityToDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto
    )
    {
        var category = categoryRepository.findById(productDto.getCategory_id()).orElse(null);
        if(category==null)
        {
            return ResponseEntity.badRequest().build();
        }
        var product = productRepository.findById(id).orElse(null);
        if(product == null)
        {
            return ResponseEntity.notFound().build();
        }
        productMapper.update(productDto,product);
        product.setCategory(category);
        productRepository.save(product);
        return ResponseEntity.ok(productMapper.toDto(product));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(
            @PathVariable Long id
    )
    {
         var product = productRepository.findById(id).orElse(null);
         if(product==null)
         {
             return ResponseEntity.notFound().build();
         }
         productRepository.delete(product);
         return ResponseEntity.noContent().build();
    }

}
