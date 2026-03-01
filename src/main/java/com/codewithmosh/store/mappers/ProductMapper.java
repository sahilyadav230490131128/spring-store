package com.codewithmosh.store.mappers;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import com.codewithmosh.store.dtos.ProductDto;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category_id",source = "category.id")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto createProductRequest);
    @Mapping(target = "id",ignore = true)
     void update(ProductDto productDto, @MappingTarget Product product);


}
